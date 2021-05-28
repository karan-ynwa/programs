package com.java.leetcode;


public class PalindromeNum {

	public static void main(String[] args) {
		int x=121;
		int num=x;
		int d=0;
		int rev=0;
		/*if(x<0){
			return false;
		}
		if(x==0){
			return true;
		}
		*/
		while(x!=0){
			rev= rev*10+x%10;
			x=x/10;
		}
		System.out.println(rev==num);
	}

}
