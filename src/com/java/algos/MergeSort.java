package com.java.algos;

import java.util.Arrays;

public class MergeSort {

	public static void main(String[] args) {
		int[] a={2,6,3,4,9,1,7,8,5};
		mergeSort(a,0,a.length-1);
		Arrays.stream(a).forEach(System.out::println);
	}

	private static void mergeSort(int[] a, int l, int r) {
		if(l<r){
			int mid=(r+l)/2;
			mergeSort(a, l, mid);
			mergeSort(a, mid+1, r);
			merge(a,l,mid,r);
			
		}
	}
	private static void merge(int[] a, int l, int mid, int r) {
		int j =mid+1;
		int i=l;
		int k=l;
		int[] b=new int[a.length];
		while(i<=mid && j<=r){
			if(a[i]>a[j]){
				b[k]=a[j];
				j++;
			}
			else{
				b[k]=a[i];
				i++;
			}
			k++;
		}
		
	}

}
