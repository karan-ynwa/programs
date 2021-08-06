package com.test.random;

import java.util.ArrayList;
import java.util.Arrays;

public class FinalTest {

	public static void main(String[] args) {
		final ArrayList<String> l=(ArrayList<String>) Arrays.asList("K","S");
		l.add("K");
		System.out.println(l);
	}

}
