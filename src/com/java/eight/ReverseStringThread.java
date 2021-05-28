package com.java.eight;

 
public class ReverseStringThread {

	public static void main(String[] args) throws InterruptedException {
		String s= "abcdefgh";
		Mthread m1=new Mthread(s.substring(0, (s.length()/2)));
		Mthread m2=new Mthread(s.substring( s.length()/2,s.length()));
		Thread t1=new Thread(m1);
		Thread t2=new Thread(m2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		
	}

}
class Mthread implements Runnable{
	
	private String[] s;
	private String rev="";
	
	public Mthread(String s) {
		super();
		this.s = s.split("");
	}

	@Override
	public void run() {
		for (int i = s.length-1; i >=0; i--) {
			rev=s[i];
		}
		System.out.println(rev);
	}
	
}