package com.java.st.test;


public class Interview {

	public static void main(String[] args) {
	//	String s="sirasjdbaskdasjkbdasbcjkasnkadjkasbdjkasbkdabskdbasjkdbcajksbdejk";
		/*boolean flag=true;
		for(int i=0 ; i< s.length()/2;i++){
			if(s.charAt(i)!=s.charAt(s.length()-1-i)){
				flag=false;
				break;
			}
		}
		if(flag){
		System.out.println(s+" is a palindrome");}
		else{
			System.out.println(s+" is not a palindrome");
		}*/
		
	/*	int[] ar=new int[26];
		for (int i = 0; i < s.length(); i++) {
			ar[s.charAt(i)-'a']++;
		}
		for (int i = 0; i < ar.length; i++) {
			if(ar[i]>1){
				System.out.println((char)(i+'a'));
			}
			else{
				System.out.println("not duplicate");
			}
		}*/
		int a =153;
		int n=a;
		int rem=0;
		int cubeTot=0;
		while(n>0){
			rem=n%10;
			n=n/10;
		//	System.out.println(n);
		//	System.out.println(rem);
			int cube=getCube(rem);
			cubeTot+=cube;
		}
		System.out.println(cubeTot);
		if(cubeTot==a){
			System.out.println("is armstrong");
		}
	}

	private static int getCube(int rem) {
		return rem*rem*rem;
	}
}
