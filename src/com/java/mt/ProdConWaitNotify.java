package com.java.mt;

import java.util.LinkedList;
import java.util.Queue;


public class ProdConWaitNotify {
	
	static int j=-1;
	public static void main(String[] args)  {
		Queue<Integer> bq= new LinkedList<>();
		int cap=3;
		Runnable prod =() ->{
			for (int i = 0; i < 6 ; i++) {
				synchronized (bq) {
					while(bq.size()==cap){
						try {
							bq.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					bq.add(i);
					System.out.println("Produced : "+ i);
					bq.notifyAll();
				}
				
			}
		};
		
		Runnable con= ()->{
			synchronized (bq) {
				while(j!=5){
					while(bq.isEmpty()){
						try {
							bq.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					j=bq.remove();
					System.out.println("Consumed: "+j);
					bq.notifyAll();
					
					}	
			}
			
		};
		Thread t1=new Thread(prod);
		Thread t2=new Thread(con);
		t1.start();
		t2.start();
	}

}
