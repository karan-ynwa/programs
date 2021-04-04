package com.java.hackerrank;

import java.util.Arrays;
import java.util.List;

public class TallestCandlescount {
	
	public static void main(String[] args) {
		int n=4;
		int[] arr={4,2,2,3,3,3,4,4};
		List<Integer> candles= Arrays.asList(4,2,2,3,3,3,4,4);
		int max=candles.stream().sorted().max((x1,x2) -> x1.compareTo(x2) ).orElse(0);
		int count= (int) candles.stream().filter(x -> x==max).count();
		System.out.println(count);
	}
}
