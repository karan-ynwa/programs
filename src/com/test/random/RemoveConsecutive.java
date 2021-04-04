package com.test.random;

 /**
 * 
 * FILE_NAME: RemoveConsecutive.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author ekxrxax, Date: Jan 6, 2021 11:51:33 PM 2021
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class RemoveConsecutive {
	static int count=1;
	/**
	 * User: ekxrxax , Date: Jan 6, 2021 11:51:33 PM 2021
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: void
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String s ="aaabcccbbddxxaaa"; 
		
		
		String a=removeConsec(s);
		while(count>2 && a.length()>=3){
			a=	removeConsec(a);
		}
		System.out.println(a);
	}

	/**
	 * User: ekxrxax , Date: Jan 6, 2021 11:58:44 PM 2021
	 *
	 * Purpose: TODO
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: String
	 *
	 * @param s
	 * @return
	 */
	private static String removeConsec(String s) {
		count=1;
		String a="";
		for (int i = 0; i < s.length()-1; i++) {
			if(s.charAt(i)==s.charAt(i+1)){
				count++;
				if (count==3){
					a+=s.substring(i+2,s.length());
					break;
				}
			}
			else{
				a+=s.charAt(i);
			}
		}
		
		return a;
	}

}
