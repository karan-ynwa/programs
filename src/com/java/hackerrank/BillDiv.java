package com.java.hackerrank;

import java.util.Arrays;
import java.util.List;

public class BillDiv {

	public static void main(String[] args) {
		int skipped=1;
		List<Integer> bill=Arrays.asList(3,10,2,9);
		int paid=12;
		int topay=0;
		int total= bill.stream().filter(x -> x!=bill.get(skipped)).reduce(0, (Integer a ,Integer b) ->a+b );
		if(paid>total/2){
			topay=paid-total/2;
			System.out.println(topay);
		}
		else{
			System.out.println("Bon Appetit");
		}
		
	}

}
