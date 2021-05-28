package com.java.collections;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.test.random.Person;

public class HashmapOperations {
	
	public static void main(String[] args) {
		Map<Person,String> map=new HashMap<>();
	//	map.put(null, "A");
		Person p1=new Person("firstName", "lastName", 9);
		map.put(p1, "A");
		map.put(new Person("firstName", "lastName", 9), "K");
		map.put(new Person("firstName", "lastName", 9), "K");
		System.out.println(map);
		System.out.println(map.size());
		map.hashCode();
	//	Iterator<Person> i= (Iterator<Person>) map.keySet();
	
		
		Map<Person,String> map1=new LinkedHashMap<>();
		Map<Person,String> syncMap=Collections.synchronizedMap(map);
		
	//	Map<String,Integer> customMap=new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
		Map<String,Integer> customMap=new HashmapCustom();
		customMap.put("Five", 5);
		System.out.println(customMap.get("FIVE"));
		
	}

}
class HashmapCustom extends HashMap<String, Integer>{
	@Override
	public Integer put(String key, Integer val){
		return	super.put(key.toLowerCase(), val);
			
	}
	@Override
	public Integer get(Object key){
		return super.get(key.toString().toLowerCase());
	}
}
