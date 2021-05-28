package com.java.quiz;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.IntStream;

 /**
 * 
 * FILE_NAME: ArrayGoodBad.java
 * 
 * MODULE DESCRIPTION: Array is good if the elements of array are unique, not sorted and less than
 * the given number 
 *
 * @author ekxrxax, Date: Apr 15, 2021 4:34:11 PM 2021
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class ArrayGoodBad {

	
	public static void main(String[] args) {
		int n=3;
		int[] ar={1,2,4};
		Set<Integer> set= new LinkedHashSet<>();
		for (Integer i : ar) {
			set.add(i);
		}
	int count=	(int) set.stream().filter(i -> i> n).count();
		System.out.println(count==0);
		System.out.println(IntStream.range(0, n-1).allMatch(i -> ar[i]<ar[i+1]));
		System.out.println(set.size()==n);	
	}

}
