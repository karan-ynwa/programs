package com.java.quiz;


 /**
 * 
 * FILE_NAME: MaxContinousSum.java
 * 
 * MODULE DESCRIPTION: maximum sum of the continuous elements of array
 *
 * @author ekxrxax, Date: Apr 15, 2021 5:01:32 PM 2021
 * 
 *
 */
public class MaxContinousSum {

	public static void main(String[] args) {
		int[] ar={-2, 1, -3, 4, -1, 2, 1, -5, 4};
		System.out.println("bruteForce ans:: "+bruteForce(ar));
		int max=Integer.MIN_VALUE;
		int cur=0;
		for (int i = 0; i < ar.length; i++) {
			cur=cur+ar[i];
			if(cur>max){
				max=cur;
			}
			if(cur<0){
				cur=0;
			}
		}
		System.out.println("Kaden "+max);
	}
	private static int bruteForce(int[] ar) {
		int max=0;
		for (int i = 0; i < ar.length; i++) {
			int sum=0;
			for (int j = i; j < ar.length; j++) {
				sum+=ar[j];
				max=Math.max(sum, max);
			}
		}
		return max;
	}

}
