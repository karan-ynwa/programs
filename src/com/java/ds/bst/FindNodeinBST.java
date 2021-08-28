package com.java.ds.bst;

public class FindNodeinBST {

	public static void main(String[] args) {
		 									  //        5
		BinaryTree tree = new BinaryTree();  //    3		6
        tree.root = new Node(5);             // 2     4
        tree.root.left = new Node(3);
        tree.root.right = new Node(6);
        tree.root.left.left = new Node(2);
        tree.root.left.right = new Node(4);
        
        System.out.println(findNode(tree.root,3));
	}

	private static boolean findNode(Node root, int i) {
		if(root==null){
			return false;
		}
		if(root.key==i){
			return true;
		}
		if(root.key>i ){
		return	findNode(root.left, i);
		}
		if(root.key<i ){
		return	findNode(root.right, i);
		}
		return false;
	
	}

}
