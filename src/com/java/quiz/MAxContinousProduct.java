package com.java.quiz;

import java.util.Arrays;


public class MAxContinousProduct {

	/**
	 * max continous product 
	 */
	public static void main(String[] args) {
		int[] ar={3,3,-5,4,5,6,-11};
		Arrays.sort(ar);
		int max=Math.max(ar[0]*ar[1]*ar[2], ar[ar.length-1]*ar[ar.length-2]*ar[ar.length-3]);
		System.out.println(max);
	}

}
