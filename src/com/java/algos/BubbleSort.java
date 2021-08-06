package com.java.algos;

import java.util.Arrays;
import java.util.stream.Stream;


public class BubbleSort {


	public static void main(String[] args) {
		int ar[] ={2,1,4,5,3,9,7,0};
		bubbleSort(ar);
	}

	private static void bubbleSort(int[] ar) {
		int temp=0;
		for (int i = 0; i < ar.length-1; i++) {
			boolean flag=false;
			for (int j = 0; j < ar.length-1-i; j++) {
				if(ar[j]>ar[j+1]){
					temp=ar[j];
					ar[j]=ar[j+1];
					ar[j+1]=temp;
					flag=true;
				}
			}
			if(!flag){
				break;
			}
		}
		Arrays.stream(ar).forEach(System.out::println);
	}

}
