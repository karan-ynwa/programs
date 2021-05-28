package com.java.mt;



public class PrintEvenOddwithtwoThreads {
	static boolean isEven=true;
	static int counter=0;
	public static void main(String[] args) {
		Object lock=new Object();
		
		Runnable even= ()-> {
			
			synchronized (lock) {
				
				while(counter<10){
					isEven=counter%2==0?true:false;
					if(!isEven){

						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}					
					}
					System.out.println("even "+counter);
					counter++;	
					lock.notify();
				}
			}
			
			
		};
		Runnable odd=()-> {
			
			synchronized (lock) {
				while(counter<10){
				isEven=counter%2==0?true:false;
				if(isEven){

					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				System.out.println("odd "+counter);
				counter++;	
				lock.notify();
			}}
			
			
		};
		Thread t1=new Thread(even);
		Thread t2= new Thread(odd);
		t1.start();
		t2.start();
	}

}
