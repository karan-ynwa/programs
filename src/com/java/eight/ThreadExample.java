package com.java.eight;

public class ThreadExample {

	
	public static void main(String[] args) {
		Runnable r1= () -> System.out.println("hi");
		Thread t1=new Thread(r1);
		t1.start();
	}

}
