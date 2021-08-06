package com.java.core;

import java.util.stream.Stream;

public class RevString {
//	hi ok i
//	ko 
	public static void main(String[] args) {
		String s ="hi i am k";
		StringBuilder sb=new StringBuilder("hi i am k");
		System.out.println(sb.equals(s));
		System.out.println(s.equals(sb.toString()));
	//	reverseWithSpaces(s);
		thatGuy(s);
		String[] sar=s.split("");
		String[] srev=new String[s.length()];
		int j =0;
		for (int i = 0; i < sar.length; i++) {
			if (sar[i].equals(" ")) {
				srev[i] = " ";
			} else {
				srev[i] = "";
			}
		}
		for (int i = sar.length-1; i >=0 ; i--) {
			if(srev[j].equals(" ") && !sar[i].equals(" ")){
				srev[j+1]=sar[i];
			}
			else if(!srev[j].equals(" ") && !sar[i].equals(" ")){
				srev[j]=sar[i];
			}
			else if(!srev[j].equals(" ") && sar[i].equals(" ")){
				srev[j] =sar[i-1];
			}
			j++;
		}
	//	Stream.of(srev).forEach(System.out::println);
	}

	private static void thatGuy(String str) {
		
		String[] arr = str.split(" ");
		String temp = str.replaceAll(" ", "");
		StringBuilder sb = new StringBuilder();
		sb.append(temp);
		sb.reverse();
		String rev = sb.toString();
		int start = 0;
		for (int i = 0; i < arr.length; i++) 
		{
		System.out.print(rev.substring(start, start + arr[i].length()) + " ");
		start = start + arr[i].length();
		}
	}

	private static void reverseWithSpaces(String s) {
		String[] ar=s.split("");
		int j=s.length()-1;
		for (int i = 0; i < ar.length; i++) {
			if(ar[i].equals(" ")){
				i++;
			}
		}
	}

}


