package com.java.streams;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.test.random.Person;

 /**
 * 
 * FILE_NAME: StreamTest.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author ekxrxax, Date: Mar 14, 2021 4:50:19 PM 2021
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class StreamTest {

	/**
	 * User: ekxrxax , Date: Mar 14, 2021 4:50:19 PM 2021
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: void
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		List<Person> list= Arrays.asList(new Person("Charles","Dickens",60),
				 new Person("Lewis","Carroll",42),
				 new Person("Thomas","Carlyle",51),
				 new Person("Thomas","Dickens",52));
	boolean flag=	list.stream().anyMatch(p -> p.getFirstName().startsWith("C"));
	List<Person> streamedList= list.stream().filter(p -> p.getLastName().startsWith("C")).collect(Collectors.toList());
	Map<String,List<Person>> mapGroupedBy= list.parallelStream().collect(Collectors.groupingBy(Person::getLastName));
	List<String> lastnames=list.stream().map(p -> p.getLastName()).distinct().collect(Collectors.toList());
//	lastnames.stream().distinct().forEach(p -> System.out.println(p)); 
	int age=list.stream().mapToInt(p -> p.getAge()).min().orElse(0);
	String name=list.stream().map(p -> p.getFirstName()).collect(Collectors.toList()).get(0);
	//list.stream().filter(p -> p.getFirstName().equals("Charles")).map(p -> p.setLastName("Boyle"));
//	System.out.println(name);
	int count=Stream.of(2,3,4).reduce(0, (Integer a , Integer b)-> a+b);
//	System.out.println(count);
//	System.out.println(mapGroupedBy);
//	System.out.println(streamedList);
	List<Person> fromMap=mapGroupedBy.get("Dickens");
//	System.out.println(fromMap);
//	System.out.println(flag);
	List<Integer> intList= Stream.of(1,2,3,15,16,17,18).filter(x -> x.intValue()>15).collect(Collectors.toList());
	intList.forEach(System.out::println);	
	
	
	}

}