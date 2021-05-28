package com.java.mt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ExecutorsTest {

	public static void main(String[] args) {
		ExecutorService exFx=Executors.newFixedThreadPool(4);
		ExecutorService exSt=Executors.newSingleThreadExecutor();
		ExecutorService exCt=Executors.newCachedThreadPool();
		for (int i = 0; i < 8; i++) {
	//		exSt.submit(new Task());
			exFx.execute(new Task());
		}
		exFx.shutdown();
		exFx.execute(new Task());
	}

}
class Task implements Runnable{
	ThreadLocal<Integer> t=ThreadLocal.withInitial(()-> 20);
	int i=1;
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+" "+ t.get());
		System.out.println(Thread.currentThread().getName()+" i was "+i);
		if(Thread.currentThread().getId()%2==0){
			t.set(10);
			
		}
		i++;
		System.out.println(Thread.currentThread().getName()+" "+ t.get());
		System.out.println(Thread.currentThread().getName()+" i is "+i);
	}
	
}