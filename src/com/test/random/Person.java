package com.test.random;

 /**
 * 
 * FILE_NAME: Person.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author ekxrxax, Date: Mar 14, 2021 12:23:29 AM 2021
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class Person {
private	String firstName;
private String lastName;
private int age;
/**
 * @return the firstName
 */
public String getFirstName() {
	return firstName;
}
/**
 * @param firstName the firstName to set
 */
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
/**
 * @return the lastName
 */
public String getLastName() {
	return lastName;
}
/**
 * @param lastName the lastName to set
 */
public void setLastName(String lastName) {
	this.lastName = lastName;
}
/**
 * @return the age
 */
public int getAge() {
	return age;
}
/**
 * @param age the age to set
 */
public void setAge(int age) {
	this.age = age;
}
/**
 *
 * Purpose: TODO
 *
 * Date: Mar 14, 2021 12:24:34 AM 2021
 * 
 * US/D/F Number: 
 *
 * @param firstName
 * @param lastName
 * @param age 
 */
public Person(String firstName, String lastName, int age) {
	super();
	this.firstName = firstName;
	this.lastName = lastName;
	this.age = age;
}
/**
 * User: ekxrxax , Date: Mar 14, 2021 12:24:47 AM 2021
 *
 * Purpose: TODO
 *
 * US/D/F Number: 
 *
 * @return
 */
@Override
public String toString() {
	return "Person [firstName=" + firstName + ", lastName=" + lastName
			+ ", age=" + age + "]";
}

	
	
}
