package com.java.st.test;

import java.util.HashSet;
import java.util.Set;

public class Coding {

	public static void main(String[] args) {
//		int[] arr={2,3,4,1,6,-1,-100};
//		//max prod of any 3 nums 
//		Arrays.sort(arr);
//	//	int max = arr[arr.length-1]*arr[arr.length-2]*arr[arr.length-3]>arr[0]*arr[1]*arr[arr.length-1]?;
//		System.out.println(max);
//		int min=2;
//		int a =arr[0];
//		int b=arr[1];
//		int c=arr[2];
//		int neg=arr[i];
//		int neg2=
//		
//		int prod = a*b*c;
		Set<ForSet> set= new HashSet<>();
		set.add(new ForSet(1));
		set.add(new ForSet(1));
		for (ForSet forSet : set) {
			System.out.println(forSet.getI());
		}
	}

}
class ForSet{
	int i;

	
	public ForSet(int i) {
		super();
		this.i = i;
	}


	/**
	 * @return the i
	 */
	public int getI() {
		return i;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		return result;
	}

	
	/*@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return false;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ForSet other = (ForSet) obj;
		if (i != other.i)
			return false;
		return true;
	}*/

	/**
	 * @param i the i to set
	 */
	public void setI(int i) {
		this.i = i;
	}
}
