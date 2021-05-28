package com.java.streams;



public class CountOccurance {

	public static void main(String[] args) {
		String s="hi i am imbicile";
		System.out.println((s.chars()).filter(x -> x=='i').count());
		
	}

}
