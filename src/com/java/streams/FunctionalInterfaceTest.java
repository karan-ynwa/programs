package com.java.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;


public class FunctionalInterfaceTest {
	public static void main(String[] args) {
		//predicate
		Predicate<Integer> p= x-> x<2;
		System.out.println("predicate: "+p.test(1));
		List<Integer> l= Arrays.asList(1,2,3,6,5,6);
		l.stream().filter(p).forEach(System.out::println);
		
		List<String> st=Arrays.asList("a","b","c","d");
		
		
		Function<Integer, String> f= x -> x.toString() + "hi"; 
		l.stream().map(f).forEach(System.out::println);
		BiFunction<Integer, Integer, String> bf= (x,y) -> x.toString()+y.toString();
		BinaryOperator<String> bo= (x,y)->x.toString()+y;
		String s=st.stream().reduce("", bo);
		System.out.println(s);
	//	IntStream.generate(() -> { return getRandom();} ).filter(x ->x>40).forEach(System.out::println);;
		l.stream().sorted().forEach(System.out::println);
	//	Comparator<Object> c=Comparator.comparingInt((x,y)-> y.compareTo(x));
		int i=l.stream().sorted((x,y) -> y.compareTo(x)).distinct().skip(1).findFirst().orElse(-1);
		System.out.println("second highest : "+i);
		l.stream().sorted(Comparator.reverseOrder()).distinct().skip(1).findFirst();
		System.out.println(IntStream.of(1,2,3,4,5).filter(x->x<5).average());
		System.out.println(l.stream().filter(x-> x<5).mapToInt(x->x).average());
		
	}

	private static int getRandom() {
		Random r=new Random();
		
		return  r.nextInt()/10000000;
	}
	
}
interface customPred{
	boolean custTest();
}


