package com.java.ds.bst;

import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstandDepthFirst {
	public static void main(String[] args) {
													// 5
		BinaryTree tree = new BinaryTree(); 	// 3        6
		tree.root = new Node(5); 			// 2     4
		tree.root.left = new Node(3);
		tree.root.right = new Node(6);
		tree.root.left.left = new Node(2);
		tree.root.left.right = new Node(4);
		printBf(tree.root); //5 3 6 2 4 
	//	System.out.println(st);
		//for depth first print any pre,In or post order
	}

	
	private static void printBf(Node root) {
		Queue<Node> q1=new LinkedList<Node>();
		Queue<Node> q2=new LinkedList<Node>();
		q1.add(root);
		while(!q1.isEmpty()){
			Node n=q1.poll();
			q2.add(n);
			if(n.left!=null){
				q1.add(n.left);
			}
			if(n.right!=null){
				q1.add(n.right);
			}
		}
		while(!q2.isEmpty()){
			System.out.print(q2.poll().key);
		}
	}

}
