package com.java.mt;

public class ThreadLocalTest {
	public static ThreadLocal<String> s= ThreadLocal.withInitial(()-> "karan");
	public static volatile String global="Global";
	public static void main(String[] args) throws InterruptedException {
		Thread t1= new Thread(){
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()+" "+ s.get());
				s.set("Singh");
				System.out.println(Thread.currentThread().getName()+" "+s.get());
				System.out.println(Thread.currentThread().getName()+" "+global);
				changeGlobal("changed");
			}
		};
		Thread t2= new Thread(){
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()+" "+s.get());
				System.out.println(Thread.currentThread().getName()+" "+global);
				changeGlobal("changed again");
				s.remove();
			}
		};
		t1.start();
		t2.start();
	//	t1.start();
		t1.join();
	}
	
	protected static void changeGlobal(String global2) {
		synchronized (global2) {
			global=global2;	
		}
		
	}

}
