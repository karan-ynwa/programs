package com.java.st.test;

public class Kronos {

	public static void main(String[] args) {
		String word="abcdefghijklmnopqrstuvwxyzajsdjasndjkansqjnc";
		byte[] ar=new byte[26];
		for (int i = 0; i < word.length(); i++) {
			char cur=word.charAt(i);
			if(cur >='a' && cur <='z'){
				ar[cur-'a']++;
			}
		}
		for (int i = 0; i < ar.length; i++) {
			char c= (char) ('a'+i);
		//	System.out.println(c+" "+ar[i]);
		}
		
	Singleton si=Singleton.getInstance();
	si.setI(1);
	si.setS("A");
	Singleton si2=Singleton.getInstance();
	si2.setI(2);
	si2.setS("A");
	System.out.println("1st: "+si.hashCode()+" 2nd: "+si2.hashCode());
	}

}
class Phone{
	public int sId;
	String model;
	private Phone(){}
	public static class PhoneBuilder{
		int sId;
		String model;
		public PhoneBuilder setSId(int sId){
			this.sId=sId;
			return this;
		}
		public PhoneBuilder setModel(String model){
			this.model=model;
			return this;
		}
		public PhoneBuilder(){
			
		}
		public Phone build(){
			Phone p=new Phone();
			p.sId=this.sId;
			p.model=this.model;
			return p;
		}
		
	}
	
}

class Singleton{
	private static volatile Singleton instance;
	int i;
	/**
	 * @return the i
	 */
	public int getI() {
		return i;
	}
	/**
	 * @param i the i to set
	 */
	public void setI(int i) {
		this.i = i;
	}
	/**
	 * @return the s
	 */
	public String getS() {
		return s;
	}
	/**
	 * @param s the s to set
	 */
	public void setS(String s) {
		this.s = s;
	}
	String s;
	public static Singleton getInstance(){
		if(instance==null){
			//t1,t2
			synchronized(Singleton.class){
				if(instance==null){
					instance=new Singleton();
					return instance;
				}
			}
		}
		return instance;
		
	}
	
}
