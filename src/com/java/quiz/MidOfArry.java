package com.java.quiz;


public class MidOfArry {

	public static void main(String[] args) {
		int[] arr={1,2,3,4,5};
		int[] arr1={1,2,3,4,5,6};
		
		int extra= arr1.length%2==0?1:0;
		int star= arr1.length/2-extra;
		int last=arr1.length/2;
		int[] a=new int[extra+1];
		for (int i = 0; i <= last-star; i++) {
			a[i]=arr1[star+i];
		}
		
	}

}
