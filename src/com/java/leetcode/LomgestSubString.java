package com.java.leetcode;

import java.util.LinkedHashSet;
import java.util.Set;

public class LomgestSubString {

	public static void main(String[] args) {
		String s ="abcdax";
		int l=0;
		int r=0;
		int max=0;
		Set<Character> set=new LinkedHashSet<>();
		while (r<s.length()){
			if(!set.contains(s.charAt(r))){
				set.add(s.charAt(r));
				r++;
				if(max<set.size()){
					max=set.size();
				}
			}
			else{
				set.remove(s.charAt(l));
				l++;
				
			}
		}
		System.out.println(max);
	}

}
