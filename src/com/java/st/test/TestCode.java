package com.java.st.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class TestCode {
 
	public static void main(String[] args) {
		 /*
		  * student fnames,lnames, college name
		  * 10000 , karan-10, bhavin-2, pankaj -20
		  * 
		  * select fname from students groupBy(fname) order by desc limit 1; 
		  */
			List<Student> st=new ArrayList<Student>();
			Student st1=new Student("Karan", "Singh");
			Student st2=new Student("Karan", "Singh");
			Student st3=new Student("Bhavin", "Shah");
			st.add(st3);
			st.add(st2);
			st.add(st1);
		 List<String> l= Arrays.asList("Karan","Karan","Karan","Pankaj","Pankaj","Bhavin","Bhavin","Bhavin","Bhavin");
		 Map<String,Integer> map=new HashMap<>();
		 for (String s : l) {
			if(map.get(s)!=null){
				map.put(s, map.get(s)+1);
			}
			else{
				map.put(s, 1);
			}
		}
		 int max=0;
		 String re="";
		 for (String k : map.keySet()) {
			if(map.get(k)>max){
				max=map.get(k);
				re=k;
			}
		}
		 System.out.println(re);
		 System.out.println(l.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting())));
		 System.out.println(st.stream().collect(Collectors.groupingBy(Student::getFname,Collectors.counting())));
		 System.out.println(st.stream().collect(Collectors.counting()));
	}

}
class Student{
	
	
	public Student(String fname, String lname) {
		super();
		this.fname = fname;
		this.lname = lname;
	}
	String fname;
	String lname;
	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}
	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}
	/**
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}
	/**
	 * @param lname the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}
	
}