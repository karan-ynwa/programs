package com.java.hackerrank;


public class BreakingRecords {

	public static void main(String[] args) {
		int[]scores={10 ,5, 20, 20, 4, 5, 2, 25, 1};
		int min=scores[0];
		int max=scores[0];
		int countMin=0;
		int countMax=0;
		for (int i = 1; i < scores.length; i++) {
			if(scores[i]>max){
				max=scores[i];
				countMax++;
			}
			if(scores[i]<min){
				min=scores[i];
				countMin++;
			}
		}
		System.out.println(countMax +" "+countMin);
	}

}
