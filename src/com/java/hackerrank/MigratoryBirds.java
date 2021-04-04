package com.java.hackerrank;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

 
public class MigratoryBirds {

	
	public static void main(String[] args) {
		
		List<Integer> arr=Arrays.asList(1, 2, 3, 4, 5, 4, 3, 2, 1, 3, 4);
		Map<Integer,Integer> map=new LinkedHashMap<>();
		int max=0;
		for (Integer x : arr) {
			
			if(map.get(x) != null){
				map.put(x, map.get(x)+1);
				if(max<map.get(x))
				max=map.get(x);
			}
			else{
				map.put(x, 1);
			}
		}
		int num =Collections.max(map.entrySet(),Map.Entry.comparingByValue()).getKey();
/*		for (Integer v : map.keySet()) {
			if(map.get(v)==max){
				System.out.println(v);
				break;
			}
		}
*/	//	int num=map.entrySet().stream().findFirst().filter(x -> x.getValue()==max).map(Map.Entry::getKey).orElse(0);
		System.out.println(num);
	}

}
