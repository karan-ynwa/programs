package com.java.hackerrank;

 import java.util.Arrays;
/**
 * 
 * FILE_NAME: MinMaxSum.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author ekxrxax, Date: Mar 16, 2021 12:50:20 AM 2021
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
import java.util.Scanner;
import java.util.stream.Collectors;

public class MinMaxSum {

    // Complete the miniMaxSum function below.
    static void miniMaxSum(int[] arr) {
    	
        long min= Arrays.stream(arr).mapToLong(x -> (long)x).sorted().limit(arr.length-1).reduce(0, (long a ,long b)-> a+b);
        long max= Arrays.stream(arr).mapToLong(x -> (long)x).reduce(0, (long a ,long b)-> a+b);
        long i=Arrays.stream(arr).sorted().limit(1).sum();
        max=max-i;
       System.out.println(min +" "+max);
        
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        long[] arr = {1,3,5,7,9,2};
        long[] arr2={7, 69, 2, 221 ,8974};
        int[] arr3={256741038, 623958417, 467905213, 714532089, 938071625};
        miniMaxSum(arr3);

        
    }
}
