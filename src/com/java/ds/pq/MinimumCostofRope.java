package com.java.ds.pq;

import java.util.Arrays;
import java.util.PriorityQueue;
/*
 * 
 * 
 * FILE_NAME: MinimumCostofRope.java
 * 
 * MODULE DESCRIPTION: the program counts the minimum cost of adding ropes . 
 * priority queue uses a min heap internally 
 *
 */
public class MinimumCostofRope {

	public static void main(String[] args) {
		Integer[] ropes={2,5,4,8,6,9};
		int sum =0;
		PriorityQueue<Integer> pq=new PriorityQueue<>();
		pq.addAll(Arrays.asList(ropes));
		while(pq.size()>1){
			int first =pq.poll();
			int second=pq.poll();
			int curSum=first+second;
			sum+=curSum;
			pq.add(curSum);
		}
		System.out.println(sum);
	}

}
