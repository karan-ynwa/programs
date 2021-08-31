#!/bin/perl
##########################################
# Variables
##########################################
use Getopt::Std;

## Specify logical branchpoints for this repo in $branches - separate by space:
$branches="ebimaster_integrate";

my $identifier;
my $branchpoint;
my $ynPatt = "^[yn]\$";
my $nzPatt = "^.+\$";
my $continue = "y";
my $today = localtime(time);
my $here=`pwd`;
chomp($here);
my $repositorypath="../../";
my $user=$ENV{'USERNAME'};
my @repos;
my $part;
my $repo;
my $devdep;
my $integrationbranch;
my $newbranch;

################################################
# Parameters used
################################################

getopts ("hi:b:");
if ($opt_h) { print("
        This script creates a branch based on CAC syntax
        It also checks your env for dependencies needed
        
        Script takes two parameters:
        A simple identifier, like a TR or a CR
        Branchpoint: usually the integration branch, you can also use a label
        init_new_workarea.pl -i <identifier> -b <branchpoint>
        \n"); exit;}
if ($opt_i) { $identifier=$opt_i; }
if ($opt_b) { $branchpoint=$opt_b; }

#########################################
# Asks for a branchpoint if not supplied
#########################################
if(!$branchpoint) { 
   print("Logical branchpoints: $branches\n"); 
   $branchpoint = getInput("Where do you want to branch off from", $branchpoint);
} 
print("Making sure this branchpoint is valid:\n");
$result=system("git checkout $branchpoint");
if($result) { print("Something seem to be wrong with using $branchpoint as branchpoint"); }
#########################################
# Asks for an identifier if not supplied
#########################################

if(!$identifier) { $identifier="my-tr-or-what"; $identifier = getInput("What will you use your branch for", $identifier);}
$newbranch = lc($branchpoint."_".$identifier."_".$user);

#########################################
# Let's do it
#########################################
print("You are branching off from $branchpoint. \n");
$result=system("git checkout -b $newbranch $branchpoint");
unless($result) { print("You have now created: $newbranch\n");}
$result=system("git push origin $newbranch");
unless($result) { print("You have now created remote branch: $newbranch\n");}
$result=system("git branch --set-upstream $newbranch origin/$newbranch");
unless($result) { print("You have now set up tracking.\n");}

if($devdep) { 
  print("\nChecking your environment\n");
  @repos = split(' ', $devdep);
  foreach $repo (@repos){   
    if($repo =~ /cacpl/) {
      $checkdir="../../$repo";
      if(! -e $checkdir) {
        print("\nWARNING  - Cannot find $repo in your env. Specified as a dependency! Clone it?\n");
        print("           Remember that you need to create $newbranch in $repo before you start working there!\n");
      }
      elsif($repo !~ /3pp/) {
        print("\nMaking sure this branchpoint is valid in $repo:\n");
        $result=system("cd ../../$repo; git checkout $branchpoint; git checkout -b $newbranch $branchpoint; git push origin $newbranch; cd $here");
        if($result) { print("Something seem to be wrong with using $branchpoint as branchpoint in $repo\n"); }
      }
      else {
         print("Found a clone of $repo in your env. Good.\n");
      }
    }
  }
}



sub getInput
{

    # $1 - Text to print to the left (question)
    # $2 - Default value
    # $3 - Pattern the answer should match (default "^.+$")
    my $ynPatt = "^[yn]\$";
    my $nzPatt = "^.+\$";
    
    my $ptxt = $_[0];
    my $dval = $_[1];
    my $patt = $_[2];
    defined($patt) or $patt = $nzPatt;
    my $resp;
    do
    {
        if(!defined($dval) || $dval eq "")
        {
            print($ptxt.": ");
        }
        else
        {
            print($ptxt." [".$dval."]: ");
        }
        $resp = <STDIN>;
        chomp($resp);
        if($resp eq "" && defined($dval))
        {
            $resp = $dval;
        }
    } while(!($resp =~ /$patt/));
    return $resp;
}
