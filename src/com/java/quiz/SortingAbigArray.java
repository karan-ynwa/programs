package com.java.quiz;

public class SortingAbigArray {

	/**
	 *elements are in range 1 -100  
	 *if range not specified. use like a hashmap. store occurences as values and then sort the array
	 *better performance as less keys
	 */
	public static void main(String[] args) {
		int[] arr={6,7,8,9,4,5,6,7,8,9,12,3,34,5,34,21,19,10,2,3,6,45,46,67,82,27,54,25,54,23};
		int[] sortedAr=new int[100];
		for (int i = 0; i < arr.length; i++) {
			sortedAr[arr[i]]++;
		}
		for (int i = 0; i < sortedAr.length; i++) {
			for (int j = 0; j < sortedAr[i]; j++) {
				System.out.println(i);
			}
		}
	}

}
