package com.java.st.test;


public class InterviewCode {

		
	/*array- {1,4,5,2,3,1,4,2};

	int num=4;

	arrOut={1,5,2,3,1,2,4,4};
		
		int[] arrout=new int[arr.length];
		int last=arr.length-1;
		int beg=0;
		for(int i=0;i<arr.length;i++){
			if(arr[i]==num){
				arrOut[last]=num;
				last--;			
			}
			else{
				arrOut[beg]=arr[i];
				beg++;
			}
		}*/
	public static void main(String[] args) {
		int[] arr={4,4,4,4,5};
		int num=4;
		int[] arrOut=new int[arr.length];
		int last=arr.length-1;
		int beg=0;
		for(int i=0;i<arr.length;i++){
			if(arr[i]==num){
				arrOut[last]=num;
				last--;			
			}
			else{
				arrOut[beg]=arr[i];
				beg++;
			}
		}
		for (int i = 0; i < arrOut.length; i++) {
			System.out.print(arrOut[i]+",");
		}
	}

}
