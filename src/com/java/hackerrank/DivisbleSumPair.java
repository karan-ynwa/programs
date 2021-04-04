package com.java.hackerrank;


public class DivisbleSumPair {

	public static void main(String[] args) {
		int[] ar = {1, 3, 2, 6, 1, 2};
		int k =3;
		/*int count=0;
		for (int i = 0; i < ar.length-1; i++) {
			for (int j = i+1; j < ar.length; j++) {
				if((ar[i]+ar[j])%k==0){
					count++;
				}
			}
		}*/
		int [] bucket = new int[k];
	    int count = 0;
	    for (int value : ar) {
	        int modValue = value % k;
	        count += bucket[(k - modValue) % k]; // adds # of elements in complement bucket
	        bucket[modValue]++;                  // saves in bucket
	    }
	//    return count;
		System.out.println(count);
	}

}
