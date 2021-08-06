package com.java.st.test;



public class PSTest {
	/*
	 * Given an array of positive numbers and a positive number ‘k’, find the maximum sum of any contiguous subarray of size ‘k’.
	Input: [2, 1, 5, 1, 3, 2], k=3
	Output: 9
	Explanation: Subarray with maximum sum is [5, 1, 3]
	 */
	public static void main(String[] args) {
		int[] ar ={2, 1, 5, 1, 3, 2};
		int k=4;
		int max=0;
		int initSum=0;
		for (int i = 0; i < k; i++) {
			initSum+=ar[i];
		}
		max=initSum;
		System.out.println(max);
		int r=k;
		for (int i=1;i < ar.length-(r-1); i++){
			int sum=initSum+ar[k]-ar[i-1];
			System.out.println(sum+" "+ar[k]+" "+ar[i-1]);
			k++;
			if(sum>max){
				max=sum;
			}
			initSum=sum;
		}
		System.out.println(max);
	}

}
