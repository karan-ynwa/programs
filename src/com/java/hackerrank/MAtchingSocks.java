package com.java.hackerrank;

import java.util.HashSet;
import java.util.Set;

public class MAtchingSocks {


	public static void main(String[] args) {
		int[] ar={10, 20, 20, 10, 10, 30, 50, 10, 20};
		int count=0;
		Set<Integer> set=new HashSet<>();
		for (int i = 0; i < ar.length; i++) {
			if(set.contains(ar[i])){
				set.remove(ar[i]);
				count++;
			}
			else{
				set.add(ar[i]);
			}
		}
		System.out.println(count);
	}

}
