package com.java.leetcode;

public class LongestPalindrome {

	public static void main(String[] args) {
		String s="abccb";
		System.out.println(longestPalindromeBrute(s));
	//	String lpExpandFromMid=lpExpandFromMid(s);
	//	System.out.println(lpExpandFromMid);
	}

	private static String lpExpandFromMid(String s) {
		String lp2="";
		int start=0;
		int end=0;
		int len=0;
		for (int j = 0; j < s.length(); j++) {
			len=Math.max(expandFromMid(s,j,j), expandFromMid(s,j,j+1));
			if(len>end-start){
			start = j-(len-1)/2;
			end = j+(len/2);
			}
		}
		
		return s.substring(start,end+1);
	}

	private static int expandFromMid(String s, int j, int j2) {
		int left=j;
		int right=j2;
		while(left>=0 && right <s.length() && s.charAt(left)==s.charAt(right)){
			left--;
			right++;
		}
		
		return right-left-1;
	}

	private static String longestPalindromeBrute(String s) {
		String lp="";
		if(s.length()==1){
			return s;
		}
		for (int i = 0; i < s.length(); i++) {
			for (int j = i+1; j <= s.length(); j++) {
				
				int len=checkPal(s.substring(i,j));
				if(len>lp.length()){
					lp=s.substring(i,j);
				}
			}
		}	
		
		return lp;
	}

	private static int checkPal(String s) {
		boolean flag=true;
		for (int i = 0; i < s.length()/2; i++) {
			if(s.charAt(i)!=s.charAt(s.length()-1-i)){
				flag=false;
			}
		}
		if(flag){
			return s.length();
		}
		return 0;
	}
	
}
