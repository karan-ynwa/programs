package com.java.collections;

import java.util.HashMap;
import java.util.Map;

import com.test.random.Person;

public class HashmapOperations {
	
	public static void main(String[] args) {
		Map<Person,String> map=new HashMap<>();
		map.put(null, "A");
		map.put(new Person("firstName", "lastName", 9), "K");
		map.put(new Person("firstName", "lastName", 9), "K");
		System.out.println(map);
		
	}

}

