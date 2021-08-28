package com.java.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Print3InLine {

	public static void main(String[] args) {
		List<String> l=new ArrayList<>();
		l.add("1");
		l.add("2");
		l.add("3");
		l.add("4");
		l.add("5");
		l.add("6");
		l.add("7");
		l.add("8");
		//IntStream.range(0, l.len)
	IntStream.range(0, l.size()).forEach(e -> {
								  if(e%3==0){
									  System.out.println();
								  }
								  System.out.print(l.get(e));
								  
								  });
	}

}
