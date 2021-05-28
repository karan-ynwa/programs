package com.java.leetcode;

import java.util.Stack;

public class LongestValidParenthesis {
	Stack<Character> st= new Stack<>();
	int count=0;
	int max=0;
	public static void main(String[] args) {
		String s = ")()())";
		LongestValidParenthesis l=new LongestValidParenthesis();
		System.out.println(l.longestValidParentheses(s));
	}

	public int longestValidParentheses(String s) {
		for (int i = 0; i < s.length(); i++) {
			check(s.charAt(i));
		}
		
		return max;
	}

	private void check(char x) {
		if(x==')'){
			if( !st.empty() && st.pop().equals('(')){
				count+=2;
				if(max<count){
					max=count;
				}
			}
		}
		else{
			
			if(x==')'){
				count=0;
				st.push(x);
			}
			else{
				if(!st.empty()){
					count=0;
				}
				st.push(x);
			}
		}
	}
}
