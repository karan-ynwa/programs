package com.java.st.test;

 
public class Oracle {
	static int l=5;
	public static void main(String[] args) {
		printRev("karan");
	}
	static void printRev(String s){
		
		if(s.length() >0){
			System.out.println(s.charAt(--l));
		  printRev(String.valueOf(s.substring(0, l)));
		  
		}
	}
}
