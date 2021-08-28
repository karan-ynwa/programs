package com.java.streams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class FlatMapOperations {

	public static void main(String[] args) {
		Product p1=new Product();
		p1.setName("p1");
		Product p2=new Product();
		p2.setName("p2");
		Product p3=new Product();
		p3.setName("p6");
		OrderLine o1=new OrderLine();
		o1.setProd(p1);
		OrderLine o2=new OrderLine();
		o2.setProd(p2);
		OrderLine o3=new OrderLine();
		o3.setProd(p3);
		Set<OrderLine> set= new HashSet<OrderLine>();
		set.add(o1);
		set.add(o2);
		set.add(o3);
		Order or=new Order();
		or.setId("1");
		or.setSet(set);
		
		Product p4=new Product();
		p4.setName("p1");
		Product p5=new Product();
		p5.setName("p4");
		Product p6=new Product();
		p6.setName("p6");
		OrderLine o4=new OrderLine();
		o4.setProd(p4);
		OrderLine o5=new OrderLine();
		o5.setProd(p5);
		OrderLine o6=new OrderLine();
		o6.setProd(p6);
		Set<OrderLine> set2= new HashSet<OrderLine>();
		set.add(o4);
		set.add(o5);
		set.add(o6);
		Order or2=new Order();
		or2.setId("2");
		or2.setSet(set2);
		List<Order> l=new ArrayList<Order>();
		l.add(or);
		l.add(or2);
		
//		l.stream().flatMap(e ->e.getSet().stream()).forEach(e ->System.out.print(e.getProd().getName()));
		l.stream().flatMap(e -> e.getSet().stream()).map(OrderLine::getProd)
		.collect(Collectors.groupingBy(Product::getName,Collectors.counting()))
		.entrySet().stream().forEach(e -> System.out.println(e.getKey()+" "+e.getValue()));
	
		}

}
class Order{
	
	Set<OrderLine> set;
	String id;
	public Set<OrderLine> getSet() {
		return set;
	}
	public void setSet(Set<OrderLine> set) {
		this.set = set;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
class OrderLine{
	Product prod;

	/**
	 * @return the prod
	 */
	public Product getProd() {
		return prod;
	}

	/**
	 * @param prod the prod to set
	 */
	public void setProd(Product prod) {
		this.prod = prod;
	}
	
}
class Product{
	String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}