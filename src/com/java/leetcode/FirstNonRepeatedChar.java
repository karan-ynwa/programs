package com.java.leetcode;



public class FirstNonRepeatedChar {

	public static void main(String[] args) {
		String s="abbca";
		for (int i=0;i<s.length();i++) {
			if(s.indexOf(s.charAt(i))== s.lastIndexOf(s.charAt(i))){
				System.out.println("first non repeated "+ s.charAt(i));
			}
		}
		// else use a hashmap
	}

}
