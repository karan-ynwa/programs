package com.java.st.test;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntTest {

	public static void main(String[] args) {
		Integer[] ar={2,1,2,3,4,5,3,21,5,6,6,21,21,6};
		int max=ar[0];    
		int secMax=ar[0]; 
			
			for(int i=1;i<ar.length;i++){
				if(ar[i] >max){
					secMax=max;
					max=ar[i];
				}
				if(ar[i]> secMax && ar[i]!=max){
					secMax=ar[i];
				}
			}
			System.out.println(secMax);
			Stream.of(ar).collect(Collectors.groupingBy(Function.identity(),Collectors.counting())).forEach((k,v)-> System.out.println(k+" "+v));
	}

}
