package com.java.ds.bst;

import java.util.ArrayList;
import java.util.List;


public class IsTreeBST {
	static List<Integer> list=new ArrayList<>();
	public static void main(String[] args) { //        5
		BinaryTree tree = new BinaryTree();  //    3		6
        tree.root = new Node(5);             // 2     4
        tree.root.left = new Node(3);
        tree.root.right = new Node(6);
        tree.root.left.left = new Node(2);
        tree.root.left.right = new Node(4);
        if(isBST(tree.root,Integer.MIN_VALUE,Integer.MAX_VALUE)){
        	System.out.println("tree is BST");
        }
        else{
        	System.out.println("not BST");
        }
    withInorder(tree.root);
    System.out.println(list);
	}

	private static boolean isBST(Node root,int min,int max) {
		if(root==null){
			return true;
		}
		if(root.key<min || root.key>max){
			return false;
		}
		
		return isBST(root.left, min, root.key) && isBST(root.right, root.key, max);
	}

	private static void withInorder(Node root) {
		if(root==null){
			return ;
		}
		
		withInorder(root.left);
		list.add(root.key);
		withInorder(root.right);
		
	}
}
