package com.java.ds.stacks;

import java.util.LinkedList;
import java.util.Queue;

public class StackWithQueue {

	public static void main(String[] args) {
		Queue<Integer> q=new LinkedList<>();
		StackImpl s=new StackImpl(q);
		s.push(1);
		
		s.push(2);
		s.push(3);
		System.out.println(s.push(3));
		s.push(4);
		System.out.println(s.pop());
	}
}
class StackImpl{
	Queue<Integer> q= new LinkedList<Integer>();
	
	public StackImpl(Queue<Integer> q) {
		super();
		this.q = q;
	}
	public boolean push(int i){
		q.add(i);
		for (int j = 0; j< q.size()-1; j++) {
			int x=q.poll();
			q.add(x);
			
		}
		
		return false;
	} 	
	public Integer pop(){
		if(!q.isEmpty())
		{
			return q.poll();
		}
		else{
			return -1;
		}
	
	}
}
