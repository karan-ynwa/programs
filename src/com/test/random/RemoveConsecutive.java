package com.test.random;

public class RemoveConsecutive {
	static int count=1;
	
	public static void main(String[] args) {
		String s ="aaabcccbbddxxaaa"; 
		
		
		String a=removeConsec(s);
		while(count>2 && a.length()>=3){
			a=	removeConsec(a);
		}
		System.out.println(a);
	}

	
	private static String removeConsec(String s) {
		count=1;
		String a="";
		for (int i = 0; i < s.length()-1; i++) {
			if(s.charAt(i)==s.charAt(i+1)){
				count++;
				if (count==3){
					a+=s.substring(i+2,s.length());
					break;
				}
			}
			else{
				a+=s.charAt(i);
			}
		}
		
		return a;
	}

}
