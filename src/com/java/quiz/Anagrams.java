package com.java.quiz;

import java.util.Arrays;


public class Anagrams {

	
	public static void main(String[] args) {
		char str1[] = { 't', 'e', 's', 't' };
        char str2[] = { 't', 't', 'e', 's' };
        isAnagram(str1,str2);
	}

	private static void isAnagram(char[] str1, char[] str2) {
		Arrays.sort(str1);
		Arrays.sort(str2);
		int i=0,j=0;
		boolean flag=true;
		while(i<str1.length && j<str2.length){
			if(str1[i] == str2[j]){
				i++;
				j++;
			}
			else{
				flag=false;
				break;
			}
		}
		System.out.println(flag);
	}

}
