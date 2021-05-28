package com.java.quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class SumSet {
    static HashMap<ArrayList,Integer> sum_up_recursive(List<Integer> number, int X, ArrayList<Integer> partial,HashMap<ArrayList,Integer> map) {
       int s = 0;
       for (int x: partial) s += x;
       if (s <= X){
    	   int sum=0;
    		   for (Integer integer : partial) {
				sum+=integer;
			}
       map.put(partial, sum);
       }
       if (s >= X){
            return map;
       }
       for(int i=0;i<number.size();i++) {
             ArrayList<Integer> remaining = new ArrayList<Integer>();
             int n = number.get(i);
             for (int j=i+1; j<number.size();j++) remaining.add(number.get(j));
             ArrayList<Integer> partial_rec = new ArrayList<Integer>(partial);
             partial_rec.add(n);
             sum_up_recursive(remaining,X,partial_rec,map);
       }
       return map;
    }
    static void sum_up(List<Integer> number, int X,int Y) {
    	HashMap<ArrayList,Integer> map= sum_up_recursive(number,X,new ArrayList<Integer>(),new HashMap<ArrayList,Integer>() );
    	HashMap<ArrayList,Integer> map2= sum_up_recursive(number,Y,new ArrayList<Integer>(),new HashMap<ArrayList,Integer>() );
    	int total=0;
    	int max=0;
    	for (ArrayList<Integer> a : map.keySet()) {
			for(ArrayList<Integer> b : map2.keySet()){
				if(map.get(a)+map2.get(b)<=X+Y){
					total=a.size()+b.size();
					max=Math.max(total, max);
					
				}
			}
		}
    	System.out.println(max);
    }
    public static void main(String args[]) {
        int[] numbers = {6, 5, 2, 1, 8};
        int X = 17;
        int Y =5;
        List<Integer> number= new ArrayList<>();
        for(int i: numbers){
            number.add(i);
        }
        sum_up(number,X,Y);
    }
}
