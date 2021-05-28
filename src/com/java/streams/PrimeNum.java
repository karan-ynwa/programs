package com.java.streams;

import java.util.stream.IntStream;

public class PrimeNum {


	public static void main(String[] args) {
		int n=17;
		System.out.println(IntStream.
				range(2, n/2).
				noneMatch(x -> 
				n%x==0));
		
	}

}
