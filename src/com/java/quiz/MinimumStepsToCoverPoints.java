package com.java.quiz;

import java.util.ArrayList;
import java.util.List;

public class MinimumStepsToCoverPoints {

	public static void main(String[] args) {
		Points p1 = new Points(4, 6);
		Points p2 = new Points(1, 2);
		Points p3 = new Points(4, 5);
		Points p4 = new Points(10, 12);
		Points[] points = { p1, p2, p3, p4 };
		coverPoints(points);
	}

	private static void coverPoints(Points[] points) {
		List<Integer> xl=new ArrayList<>();
		List<Integer> yl=new ArrayList<>();
		for (Points p : points) {
			xl.add(p.x);
			yl.add(p.y);
		}
		int res=0;
		int a=0;
		int b=0;
		for (int i = 0; i < points.length-1; i++) {
			 a =Math.abs(xl.get(i)-xl.get(i+1));
			 b =Math.abs(yl.get(i)-yl.get(i+1));
			res+=Math.max(a, b);
		}
		System.out.println(res);
	}

}

// class denoted as point
class Points {
	int x, y;

	Points(int a, int b) {
		x = a;
		y = b;

	}
}