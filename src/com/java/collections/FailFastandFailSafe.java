package com.java.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class FailFastandFailSafe {

	public static void main(String[] args) {
	
		Map<Integer,Integer> cMap=new ConcurrentHashMap<>();
		cMap.put(1, 2);
		Iterator<Integer> it= cMap.keySet().iterator();
		while(it.hasNext()){
			if(it.next()==1){
				cMap.put(2, 3);
			}
		}
		System.out.println(cMap);
		List<Integer> l=new ArrayList<Integer>();
		l.add(1);
		l.add(2);
		l.add(3);
		Iterator<Integer> i=l.iterator();
		while(i.hasNext()){
			if(i.next()==3){
				i.remove();	
			//	l.remove(0); //will throw cme cant operate on list. can be removed using iterator
			}
		}
		for (Integer integer : l) {
			if(integer==2){
			//	l.remove(integer); // will throw cme , since foreach creates an iterator internally
			}
		}
		for (int j = 0; j < l.size(); j++) {
			if(l.get(j)==2){
				l.remove(j); 
			}
		}
		System.out.println(l);
	}

}
