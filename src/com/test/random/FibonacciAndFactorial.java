package com.test.random;


public class FibonacciAndFactorial {

	public static void main(String[] args) {
		int x=5;
		System.out.println(calculateFac(5));
		for (int i = 0; i < 5; i++) {
			System.out.println(printFib(i));
		}
	}
	private static int printFib(int i) {
		if(i==0){
			return 1;
		}
		if(i==1){
			return 1;
		}
		return printFib(i-2)+printFib(i-1);
	}

	private static int calculateFac(int i) {
		if(i==0){
			return 1;
		}
		
			return i*calculateFac(i-1);
	}

}
