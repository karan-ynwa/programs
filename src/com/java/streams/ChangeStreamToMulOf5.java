package com.java.streams;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ChangeStreamToMulOf5 {


	public static void main(String[] args) {
	//	IntStream.range(1, 5).map(x -> x*5).forEach(System.out::println);
		int[] ar={10,7,18};
	//
	//	Arrays.stream(ar).map(x -> x*5).forEach(System.out::println);
	//	System.out.println(Arrays.stream(ar).map(x -> x%7).max().orElse(0));
		int m=13;
		int max=0;
		
		for (int i = 0; i < ar.length; i++) {
			int sum=0;
			for (int j = i; j < ar.length; j++) {
				sum+=ar[j];
				max=Math.max(sum%m, max);
			}
		}
		System.out.println(max);
	}

}
