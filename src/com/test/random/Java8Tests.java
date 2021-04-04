package com.test.random;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

 /**
 * 
 * FILE_NAME: Java8Tests.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author ekxrxax, Date: Mar 14, 2021 12:15:27 AM 2021
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class Java8Tests {

	/**
	 * User: ekxrxax , Date: Mar 14, 2021 12:15:27 AM 2021
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
										 new Person("Thomas","Carlyle",51));
		//Sort List by last name
		 Collections.sort(list, (p1,p2)->	p1.getLastName().compareTo(p2.getLastName()) );
	//	System.out.println(list);
		//create method that print all element
		 printAll(list);
		
		//create method to print all last names begining with c
		 
	//	printLastnameWithC(list);
		 printLastnameWithC(list, p -> p.getLastName().startsWith("D"));
	}

	/**
	 * User: ekxrxax , Date: Mar 14, 2021 12:43:33 AM 2021
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: void
	 *
	 * @param list
	 */
	private static void printLastnameWithC(List<Person> list,Predicate<Person> c) {
		for (Person person : list) {
		  if(c.test(person))
			System.out.println(person);
		}
	/*	list.forEach(p -> {if(p.getLastName().startsWith("C"))
							{System.out.println(p.getLastName());}
								});*/
		
	}

	/**
	 * User: ekxrxax , Date: Mar 14, 2021 12:37:21 AM 2021
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: void
	 *
	 * @param list
	 */
	private static void printAll(List<Person> list) {
		for (Person person : list) {
			System.out.println(person);
		}
	}
	
}
