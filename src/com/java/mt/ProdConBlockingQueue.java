package com.java.mt;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProdConBlockingQueue {

	private static BlockingQueue<Integer> bq= new ArrayBlockingQueue<>(3);
	static int j=-1;
	public static void main(String[] args)  {
		Runnable prod =() ->{
			for (int i = 0; i < 6 ; i++) {
				try {
					bq.put(i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		Runnable con= ()->{
			try {
				while(j!=5){
				j=bq.take();
				System.out.println(j);
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		Thread t1=new Thread(prod);
		Thread t2=new Thread(con);
		t1.start();
		t2.start();
	}


}
