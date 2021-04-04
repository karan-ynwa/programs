package com.java.hackerrank;

public class CloudJumps {

	public static void main(String[] args) {
		
		int c[]={0,0,1,0,0,1,0};
		int count=0;
		for (int i = 0; i < c.length-1;) {
			if (i == c.length - 2) {
				 count++;
				 break;
			}
			else{
				if (c[i + 2] == 0) {
					i = i + 2;
				}
				else{i++;}
			}
			count++;
		}
		System.out.println(count);
	}

}
