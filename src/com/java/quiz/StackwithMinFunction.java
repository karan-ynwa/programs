package com.java.quiz;

import java.util.Stack;

public class StackwithMinFunction {

	public static void main(String[] args) {
		SpecialStack s = new SpecialStack();
		s.push(6);
		s.push(5);
		s.push(2);
		s.push(1);
		s.push(4);
		s.push(2);
		System.out.println(s.min());
		s.pop();
		System.out.println(s.min());
		s.pop();
		s.pop();
		s.pop();
		
		System.out.println(s.min());
	}

}

class SpecialStack extends Stack<Integer> {
	Stack<Integer> m = new Stack<>();

	public Integer push(Integer i) {
		if (isEmpty()) {
			super.push(i);
			m.push(i);
		} else {
			super.push(i);
			int j = m.peek();
			if (i < j) {
				m.push(i);
			} else {
				m.push(j);
			}
		}
		return i;

	}

	public Integer pop() {
		
		int i=super.pop();
		m.pop();
		return i;

	}

	public Integer min() {
		/*int minimum=m.pop();
		m.push(minimum);*/
		return m.peek();
	}
}