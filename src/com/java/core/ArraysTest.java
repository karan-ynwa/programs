package com.java.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class ArraysTest {

	/**
	 * max2 elements of an array
	 */
	public static void main(String[] args) {
		int[] arr={4,5,3,2,6,7,1};
	//	Arrays.sort(arr);
	//	System.out.println(arr[arr.length-1]);
		//create a max heap fetch 2 elements
		PriorityQueue<Integer> pq=new PriorityQueue<Integer>(Collections.reverseOrder());
		pq.addAll(Arrays.asList(4,5,3,2,6,7,1));
		System.out.println(pq.poll());
		System.out.println(pq.poll());
	}

}
