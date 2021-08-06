package com.java.ds;

import java.util.Stack;


public class PrintStackinRev {

	public static void main(String[] args) {
		Stack<Integer> st= new Stack<Integer>();
		st.push(1);
		st.push(2);
		st.push(3);
		st.push(4);
		stackRev(st);
		//System.out.println(st.pop());
	}

	private static void stackRev(Stack<Integer> st) {
		if(!st.isEmpty()){
			int i= st.pop();
			//System.out.println(i);
			stackRev(st);
		//	addInRev(st, i);
			System.out.println(i);
		}
	}

	private static void addInRev(Stack<Integer> st, int i) {
		if(st.isEmpty()){
			st.push(i);
			return;
		}
		int top=st.pop();
		addInRev(st, i);
		st.push(top);
		
	}

}
