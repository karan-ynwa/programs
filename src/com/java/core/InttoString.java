package com.java.core;

public class InttoString {

	public static void main(String[] args) {
		int i=5016;
		String s="50";
	//	System.out.println(String.valueOf(i).startsWith(s));
		System.out.println("transaction_autopay_id".toUpperCase());
		String t="hi"+2+3;
		System.out.println(t);
		short a=1;
		add(a,2);
		try{
			Float f1= new Float("3.0");
			int x =f1.intValue();
			byte b=f1.byteValue();
			double d=f1.doubleValue();
			System.out.println(x+b+d);
		}
		catch(NumberFormatException e){
			
		}
	}

	
	private static void add(int i, int j) {
		System.out.println(i+j);
	}

}
