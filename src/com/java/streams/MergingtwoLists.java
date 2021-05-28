package com.java.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class MergingtwoLists {

	public static void main(String[] args) {
		List<Character> l1	=Arrays.asList('a','b','c','d','e');
		List<Integer> l2	=Arrays.asList(1,2,3,4,5);
	//	Stream.of(l1,l2).flatMap(Collection::stream).forEach(System.out::println);;
		Stream.of(l1,l2).
		map(i->i).
		flatMap(Collection::stream).
		forEach(System.out::println);;
		List<Character> l3=new ArrayList<Character>();
		for(int i=0;i<l1.size();i++){
			l3.add(l1.get(i));
			l3.add(String.valueOf(l2.get(i)).charAt(0));
		}
		System.out.println(l3);
		List<String> list=IntStream.range(0, l2.size()).mapToObj(x -> (l1.get(x)).toString()+l2.get(x).toString()).collect(Collectors.toList());
		System.out.println(list);
		l2.stream();
	}

}
