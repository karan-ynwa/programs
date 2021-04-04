package com.java.hackerrank.ipk;

public class RepeatedStrings {

	public static void main(String[] args) {
		String s="aba";
		int n=10;
		int x=n/s.length();
		int r=n%s.length();
	
		int count=0;
		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i) =='a'){
				count++;
			}
		}
		count=count*x;
		for (int i = 0; i < r; i++) {
			if(s.charAt(i) =='a'){
				count++;
			}
		}
		System.out.println(count);
	}

}
