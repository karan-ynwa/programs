package com.java.collections;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class ArraySorting {

	public static void main(String[] args) {
		int[] ar={4,9,5};
		int[] ar2={9,4,9,8,4};
		Arrays.sort(ar);
	//	Arrays.stream(ar).distinct().sorted();
		Arrays.sort(ar2);
		intersection(ar,ar2);
	}
	private static int[] intersection(int[] ar, int[] ar2) {
		int i=0,j=0;
		Set<Integer> set=new HashSet<>();
		while(i<ar.length && j<ar2.length){
			if(ar[i]>ar2[j]){
				j++;
			}
			else if(ar[i]<ar2[j]){
				i++;
			}
			else{
				set.add(ar[i]);
			//	System.out.println(ar[i]);
				i++;
				j++;
			}
		}
		int[] res=new int[set.size()];
		int x=0;
		for(int in: set){
			res[x]=in;
			x++;
		}
	//	System.out.println(set);
		for (int k = 0; k < res.length; k++) {
			System.out.println(res[k]);
		}
		return res;
	}
	
}
