package com.java.core;


public class RevStrWithSpaces {


	public static void main(String[] args) {
		String str="hi mnb";
        String[] arr = str.split(" ");
		String temp = str.replaceAll(" ", "");
		StringBuilder sb = new StringBuilder();
		sb.append(temp);
		sb.reverse();
		String rev = sb.toString();
		int start = 0;
		for(int i=0;i<arr.length;i++)
		{System.out.print(rev.substring(start, start + arr[i].length()) + " ");
		start = start + arr[i].length();
		}
		
	}

}
