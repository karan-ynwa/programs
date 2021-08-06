package com.test.random;

public class DefaultValue {

	long l;
	public static void main(String[] args) {
		String s =null;
		
		System.out.println(s);
		int[] arr = {1,2,3,4};
		call(arr[0],arr);
		System.out.println(arr[0]+","+arr[1]);
		}



		public static void call(int i,int [] arr)
		{
		arr[i] = 5;
		i = 6;
		}

}
