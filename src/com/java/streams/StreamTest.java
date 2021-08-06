package com.java.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.test.random.Person;


public class StreamTest {

	
	public static void main(String[] args) {
		List<Person> list= Arrays.asList(new Person("Charles","Dickens",60),
				 new Person("Lewis","Carroll",42),
				 new Person("Thomas","Carlyle",51),
				 new Person("Thomas","Dickens",52),
				 new Person("Lewis","Carlyle",52));
		
		list.stream().sorted(Comparator.comparing(Person::getAge).reversed()).skip(3).limit(1).forEach(System.out::print);;
		list.stream().sorted(Comparator.comparing(Person::getLastName)).map(e -> e.getFirstName().toUpperCase()).forEach(System.out::println);
		System.out.println("------------");
		list.stream().sorted(Comparator.comparing(Person::getLastName)).forEach(e -> System.out.println(e.getFirstName().toUpperCase()));
		
	//boolean flag=	
			list.stream().anyMatch(p -> p.getFirstName().startsWith("C"));
	List<Person> streamedList= list.stream().
			filter(p -> p.getLastName().startsWith("C")).
			collect(Collectors.toList());
	//List<String> names=
	//list.stream().filter(e -> e.getAge()> 50).map(a -> a.getFirstName()).collect(Collectors.toList()).forEach(System.out::println);
	Map<String,List<Person>> mapGroupedBy= list.parallelStream().collect(Collectors.groupingBy(Person::getLastName));
	System.out.println(mapGroupedBy);
	System.out.println("===========");
	mapGroupedBy.forEach((k,v) -> System.out.println(k+" "+v));
	Map<String,Integer> ln=new HashMap<>();
	for (Entry<String, List<Person>> person : mapGroupedBy.entrySet()) {
		for( Person p:person.getValue()){
			if(ln.get(person.getKey())==null){
			ln.put(person.getKey(), p.getAge());
			}
			else{
				ln.put(person.getKey(), ln.get(person.getKey())+p.getAge());
			}
		}
	}
	System.out.println("map is"+ ln);
	List<String> lastnames=list.stream().map(p -> p.getLastName()).distinct().collect(Collectors.toList());
//	lastnames.stream().distinct().forEach(p -> System.out.println(p)); 
	//int age=
			list.stream().mapToInt(p -> p.getAge()).min().ifPresent(System.out::println);
	//System.out.println("age is "+age);
	String name=list.stream().map(p -> p.getFirstName()).collect(Collectors.toList()).get(0);
//	System.out.println(name);
	int count=Stream.of(2,3,4).reduce(0, (Integer a , Integer b)-> a+b);
//	System.out.println(count);
//	System.out.println(mapGroupedBy);
//	System.out.println(streamedList);
	List<Person> fromMap=mapGroupedBy.get("Dickens");
//	System.out.println(fromMap);
//	System.out.println(flag);
	List<Integer> intList= Stream.of(1,2,3,15,16,17,18).filter(x -> x.intValue()>15).collect(Collectors.toList());
	//intList.forEach(System.out::println);	
	list.stream().sorted();
Map<String, Optional<Person>> l=list.stream().collect(Collectors.groupingBy(Person::getFirstName,Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Person::getAge)))));
System.out.println("printing age wise");
	l.entrySet().stream().forEach(System.out::println);
	Map<String, Long> m=list.stream().collect(Collectors.groupingBy(Person::getFirstName,Collectors.counting()));
	System.out.println("count of names "+m);
	
	//printing first names
	list.stream().map(x -> x.getFirstName()).distinct().forEach(System.out::println);
	//avg age
	Map<String, Double> avgAge=list.stream().collect(Collectors.groupingBy(Person::getFirstName,Collectors.averagingInt(Person::getAge)));
	System.out.println("avg ages:: "+avgAge);
	//max age
	Person maxage=list.stream().collect(Collectors.maxBy(Comparator.comparing(Person::getAge))).orElse(null);
	System.out.println("max age :: "+maxage);
	System.out.println("=======================----------==================");
	list.stream().sorted(Comparator.comparing(Person::getAge)).forEach(System.out::println);
	//min age of Lewis
	Optional<Person> p=list.stream().filter(x -> x.getFirstName().equals("Lewis")).min(Comparator.comparing(Person::getAge));
	System.out.println("details of youngest Lewis ::"+p);
	
	Map<String, Map<String, Long>> groupingonname=list.stream().collect(Collectors.groupingBy(Person::getFirstName,Collectors.groupingBy(Person::getLastName,Collectors.counting())));
	System.out.println(groupingonname);
	
	list.stream().map(e -> e.getAge()).collect(Collectors.toList()).forEach(System.out::println);;
		
	list.stream().max(Comparator.comparing(Person::getAge).reversed()).ifPresent(x -> System.out.println(x.getFirstName()));
	
	}

}