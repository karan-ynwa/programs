package com.java.hackerrank;

import java.util.Arrays;
import java.util.List;

public class ChocolateBarDiv {


	public static void main(String[] args) {

		List<Integer> s=Arrays.asList(2,5,1,3,4,4,3,5,1,1,2,1,4,1,3,3,4,2,1);
		int d = 18;
		int m = 7;
		int count=0;
		if(s.size()==1 && d==s.get(0)){
			count=1;
		}
		for (int i = 0; i <= s.size()-m; i++) {
			int sum=0;
			for (int j = i; j < i+m; j++) {
				sum+=s.get(j);
			}
			if(sum==d){
				count++;
			}
		}
		System.out.println(count);
	}

}
