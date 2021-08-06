package com.java.streams;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sun.scenario.effect.Identity;



public class CountOccurance {

	public static void main(String[] args) {
		String s="hi i am imbicile";
		System.out.println((s.chars()).filter(x -> x=='i').count());
		Stream.of(s.split("")).collect(Collectors.groupingBy(Function.identity(),Collectors.counting())).forEach((k,v) -> System.out.println(k+" "+v));
	}

}
