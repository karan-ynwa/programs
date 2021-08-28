package com.java.mt;

public class PrintABCWith3Threads {
	static volatile boolean flagA=true;
	static volatile boolean flagB=false;
	static volatile boolean flagC=false;
	static volatile int count=0;
	public static void main(String[] args) {
		
		Object lock=new Object();
		Runnable printA=() -> {
		synchronized (lock) {
			while(count<13){
				if(flagA){
					System.out.println("A");
					flagB=true;
					flagC=false;
					flagA=false;
					count++;
					lock.notifyAll();
					}
				else{
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				}
				}
			}
		};
		Runnable printB=() -> {
		synchronized (lock) {	
			while(count<13){
				if(flagB){
					System.out.println("B");
					flagA=false;
					flagC=true;
					flagB=false;
					count++;
					lock.notifyAll();
					}
				else{
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				}
					
				
			}
		};
		Runnable printC=() -> {
			synchronized (lock) {	
				while(count<13){
					if(flagC){
						System.out.println("C");
						flagA=true;
						flagB=false;
						flagC=false;
						count++;
						lock.notifyAll();
						}
					else{
						try {
							lock.wait();
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						}
					}
						
					}
				}
			};
		Thread t1=new Thread(printA,"A");
		Thread t2=new Thread(printB,"B");
		Thread t3=new Thread(printC,"C");
		t1.start();
		t2.start();
		t3.start();
	}
	
	
}
