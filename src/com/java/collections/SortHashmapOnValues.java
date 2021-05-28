package com.java.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortHashmapOnValues {


	public static void main(String[] args) {
		Map<String,String> map=new HashMap<>();
		map.put("a", "b");
		map.put("b", "c");
		map.put("c", "a");
		map.put("d", "b");
		List<Map.Entry<String, String>> l=new ArrayList<>();
		for(Map.Entry<String, String> e :map.entrySet()){
			l.add(e);
		}
		Comparator<Map.Entry<String, String>> c=(e1,e2) -> e1.getValue().compareTo(e2.getValue());
	//	l.sort(c);
		Collections.sort(l, (e1,e2) -> e1.getValue().compareTo(e2.getValue()));
		System.out.println(l);
	}

}
