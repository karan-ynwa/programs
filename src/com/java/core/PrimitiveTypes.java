package com.java.core;

import java.util.Arrays;


public class PrimitiveTypes {

	public static void main(String[] args) {
		byte b=1;
		short s=2;
		int i=5;
		long l=10;
		
		
		long l1=i+s+b;
		System.out.println(l1);
		int i1=s+i;
		System.out.println(i1);
		short s1=b;
		System.out.println(s1);
		int i2=(int) l1;
		System.out.println(i2);
		int i3=130;
		byte b1=(byte) i3;
		System.out.println(b1);
		int[] ar={2,3,4,1,5,6};
		Arrays.sort(ar);
		for (int j = 0; j < ar.length; j++) {
			System.out.print(ar[j]+" ");
		}
	}

}
