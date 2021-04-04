package com.java.leetcode;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

	public static void main(String[] args) {
		int[] nums={3,2,4};
		int target=6;
		/*int[] ar=new int[2];
		Map<Integer,Integer> map=new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			map.put(nums[i],i);
			int comp=target-nums[i];
			if(map.get(comp)!=null){
				ar[0]=map.get(comp);
                ar[1]=map.get(nums[i]);
			}
		}
		*/
		TwoSum t=new TwoSum();
		int[] ans=t.twoSum(nums, target);
		System.out.println(ans[0]+" "+ans[1]);
		
	}
	
/*	 for (int i = 0; i < nums.length; i++) {
	        int complement = target - nums[i];
	        if (map.containsKey(complement)) {
	            return new int[] { map.get(complement), i };
	        }
	        map.put(nums[i], i);
	    }*/
	 public int[] twoSum(int[] nums, int target) {
	        Map<Integer,Integer> map=new HashMap<>();
	        int[] ar=new int[2];
	       for (int i = 0; i < nums.length; i++) {
				int comp=target-nums[i];
				if(map.get(comp)!=null && map.get(comp)!=i){
					ar[0]=map.get(comp);
	                ar[1]=i;
	                break;
				}
				map.put(nums[i],i);
			}
	        return ar; 
	    }
}
