package com.java.streams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.test.random.Person;


public class ComparatorChaining {

	
	public static void main(String[] args) {
		Comparator<Person> comp=Comparator.comparing(Person::getFirstName);
		Comparator<Person> compLast=Comparator.comparing(Person::getLastName);
		Comparator<Person> compInt=Comparator.comparingInt(Person::getAge);
		Person p3=new Person("airstName", "lastName", 1);
		Person p2=new Person("birstName", "zastName", 2);
		Person p1=new Person("birstName", "lastName", 2);
		List<Person> list= new ArrayList<Person>();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.sort(comp.thenComparing(compInt).thenComparing(compLast));
		for (Person person : list) {
			System.out.println(person);
		}
	}

}
