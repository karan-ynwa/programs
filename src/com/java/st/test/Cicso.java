package com.java.st.test;


public class Cicso {

	public static void main(String[] args) {
		int numOfTyres=3;
		double distance=5;
		calDistance(numOfTyres,distance);
	}

	
 //calculates the most distance that can be covered by number of tyres with 1 tyre as spare
	private static void calDistance(int numOfTyres, double distance) {
		double initDis=distance/(numOfTyres-1);
		double totDis=0;
		for (int i = 0; i < numOfTyres; i++) {
			totDis+=initDis;
		}
		System.out.println("total distnace that can be covered "+totDis);
	}

}
