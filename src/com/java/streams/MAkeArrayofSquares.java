package com.java.streams;

import java.util.Arrays;




public class MAkeArrayofSquares {


	public static void main(String[] args) {
		int[] ar={1,2,3,4,5};
		Arrays.stream(ar).map(x->x*x).forEach(System.out::println);
		Arrays.asList(Arrays.stream(ar).map(x->x*x).toArray());
		
	}

}
