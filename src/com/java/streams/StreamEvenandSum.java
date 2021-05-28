package com.java.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class StreamEvenandSum {


	public static void main(String[] args) {
		List<Integer> list= new ArrayList<Integer>();
		Integer[] i={1,2,3,4,5,6,7,8,9};
		list=Arrays.asList(i);
		List<Integer> l =list.stream().filter(x -> x%2==0).collect(Collectors.toList());
		l.forEach(System.out::println);
		System.out.println(Stream.of(i).filter(x -> x%2==0).mapToInt(x -> x).sum());
		l.stream().mapToInt(x -> x).sum();
		Map<Boolean, Integer> result = list.stream().collect(
			       Collectors.partitioningBy(x -> x%2 == 0, Collectors.summingInt(Integer::intValue)));
		System.out.println(result);
		
	}

}
