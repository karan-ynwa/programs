package com.java.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class ArrayIntersection {
	private static Set<Integer> set= new HashSet<>();	
	public static void main(String[] args) {
		int[] a1={4,9,5};
		int[] a2={9,4,9,8,4};
	//	int[] a3=intersectionViaBinarySearch(a1,a2);
		int[] a3=intersection(a1, a2);
	}

	
	private static int[] intersectionViaBinarySearch(int[] a1, int[] a2) {
		
		boolean size=a1.length>=a2.length?true:false;
		if(size){
			Arrays.sort(a2);
			for(int i:a1)
			binarySearch(i,a2,set);
		}
		else{
			Arrays.sort(a1);
		//	a3=binarySearch();
		}
		
		return a2;
		
	}

	
	private static void binarySearch(int i, int[] a2, Set<Integer> set2) {
		
	}


	private static int[] intersection(int[] a1, int[] a2) {
		
		Set<Integer> set1=new HashSet<>();	
		for (Integer integer : a1) {
			set1.add(integer);
		}
		Set<Integer> set2=new HashSet<>();	
		for (Integer integer : a2) {
			set2.add(integer);
		}
		set2.retainAll(set1);
		int a=0;
		int[] a3=new int[set2.size()];
		for(Integer i: set2){
			a3[a]=i;
			a++;
		}
		return a3;
	}

}
