package com.test.random;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

 /**
 * 
 * FILE_NAME: Solution.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author ekxrxax, Date: Mar 11, 2021 7:13:37 PM 2021
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class Solution {

	/**
	 * User: ekxrxax , Date: Mar 11, 2021 7:13:37 PM 2021
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
		Solution s=new Solution();
		int[] A={1,1,2,3,4,6,3,3,2,2};
		System.out.println(s.maxTimes(A));
	}
	public int solution(int[] A) {
        // write your code in Java SE 8
        TreeSet<Integer> set= new TreeSet<>();
        for(int i=0;i<A.length;i++){
            set.add(A[i]);
        }
        int a=1;
        int b=0;
        for(Integer i : set){
            if(i>0){
                 if(i!=a)  {
                    b=i;
                     break;
                 }
                 else{
                     a++;
                 }
            }
        }
      return   b;
    }
	public int maxTimes(int[] A){
		int max=0;
		Map<Integer,Integer> map=new TreeMap<Integer, Integer>();
		for (int i = 0; i < A.length; i++) {
			if(map.get(A[i])==null){
				map.put(A[i],1 );
			}
			else{
				map.put(A[i],map.get(A[i])+1 );
			}
				
		}
		for (int i : map.keySet()) {
			if(i==map.get(i)){
				max=i;
			}
		}
		return max;
		
	}
}
