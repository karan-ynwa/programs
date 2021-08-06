package com.java.quiz;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.sun.glass.ui.View.Capability;


public class LRUCache {
	static int capacity=2;
	static Deque<Integer> deq= new LinkedList<>();
	static Map<Integer,Integer> map= new HashMap<Integer, Integer>();
	public static void main(String[] args) {
		
	
		addTocache(1);
		addTocache(2);
		addTocache(3);
		System.out.println(getFromCache(2));
		addTocache(3);
		System.out.println(getFromCache(1));
		addTocache(1);
		addTocache(4);
		addTocache(5);
		System.out.println(getFromCache(2));
		
	}
	private static int getFromCache(Integer i) {
		if(map.containsKey(i)){
			deq.removeLast();
			deq.addFirst(i);
			return map.get(i);
		}
		return -1;
	}

	private static void addTocache(Integer i) {
		
			if(deq.size()==capacity){
				map.remove(deq.removeLast());
			}
			deq.push(i);
			map.put(i, i);
		}
		

}
