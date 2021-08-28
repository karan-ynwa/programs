package com.java.st.test;

import java.util.concurrent.PriorityBlockingQueue;


public class Cisco2 {

	static int count=1;
	public static void main(String[] args) {
		//producer consumer 1-100 
		PriorityBlockingQueue<Integer> bq=new PriorityBlockingQueue<Integer>(100);
		
		Runnable prod=()->{
			while(count<=100){
				bq.add(count);
				count++;
			}
		};
		
		Runnable con=()->{
			try {
				while(bq.size()>0){
					int i=bq.take();
					System.out.println(i);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		Thread t1=new Thread(prod);
		Thread t2=new Thread(con);
		t1.start();
		t2.start();
	}

}
