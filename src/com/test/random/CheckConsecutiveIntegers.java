package com.test.random;

public class CheckConsecutiveIntegers {

	public static void main(String[] args) {
		String s="122123";
		boolean flag=check(s);
		System.out.println(flag);
	}

	
	private static boolean check(String s) {
		String[] sr=s.split("");
		boolean flag=false;
		for (int i = 0; i < sr.length/2; i++) {
			String num =s.substring(0,i+1);
			flag=checkIfConsecutive(Integer.parseInt(num),s);
			if(flag){
				break;
			}
		}
		return flag;
	}

	private static boolean checkIfConsecutive(int num,String s) {
		String st=String.valueOf(num);
		
		while(st.length()<s.length()){
			num++;
			st+=String.valueOf(num);
		}
		return st.equals(s);
	}

}
