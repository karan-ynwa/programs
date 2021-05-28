package com.test.random;

import java.io.Serializable;

public class SingletonTest {

	public static void main(String[] args) {
		Singleton.INSTANCE.printSomething();
		SingletonWithInnerClass.getInstance().printSomething();
	}

}

enum Singleton {
	INSTANCE;
	public void printSomething() {
		System.out.println("testing with enum singleton");
	}
}

class SingletonWithInnerClass implements Serializable{

	private static class InnerSingleton {
		private static final SingletonWithInnerClass instance = new SingletonWithInnerClass();
	}

	public static SingletonWithInnerClass getInstance() {
		return InnerSingleton.instance;
	}

	public void printSomething() {
		System.out.println("testing with inner class singleton");
	}
	private Object readResolve(){
		return InnerSingleton.instance;
	}
	

}