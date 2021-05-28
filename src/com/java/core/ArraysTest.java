package com.java.core;

import java.util.Arrays;

public class ArraysTest {

	/**
	 * max2 elements of an array
	 */
	public static void main(String[] args) {
		int[] arr={4,5,3,2,6,7,1};
		Arrays.sort(arr);
		System.out.println(arr[arr.length-1]);
	}

}
