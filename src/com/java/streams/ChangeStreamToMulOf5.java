package com.java.streams;

import java.util.Arrays;
import java.util.stream.IntStream;


public class ChangeStreamToMulOf5 {


	public static void main(String[] args) {
		IntStream.range(1, 5).map(x -> x*5).forEach(System.out::println);
		int[] ar={1,2,3,4,5};
		Arrays.stream(ar).map(x -> x*5).forEach(System.out::println);
		
	}

}
