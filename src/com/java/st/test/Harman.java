package com.java.st.test;


public class Harman {
	/*
	 * Given an array of integers, replace every element with the next greatest element 
	 * (greatest element on the right side) in the array. Since there is no element next
	 *  to the last element, replace it with -1. For example, if the array is {16, 17, 4, 3, 5, 2},
	 *   then it should be modified to {17, 5, 5, 5, 2, -1}.
	 */
	public static void main(String[] args) {
		int[] arr={16, 17, 4, 3, 5, 2};  //              17,5,5,5,2,-1
		for(int i=0;i<arr.length;i++){
			int max=0;
			if(i<arr.length-1){
				 max=arr[i+1];
			}else{
				max=-1;
			}
			for(int j=i+1;j<arr.length;j++){
				if(max<arr[j]){
					max=arr[j];
				}
			}
			if(i==arr.length-1){
				arr[i]=-1;
			}
			else{
			arr[i]=max;
			}
			
		}
		for(int i=0;i<arr.length;i++){
			System.out.println(arr[i]);
		}
	}
	// 3 2 1  // 3 

}
