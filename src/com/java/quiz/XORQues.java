package com.java.quiz;


public class XORQues {

	public static void main(String[] args) {
		
		int max=0;
		for (int i = 2; i < 23; i++) {
			max=Math.max(max, i^7);
		}
		System.out.println(max);
	}

}
