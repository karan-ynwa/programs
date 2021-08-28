package com.java.ds.bst;


public class BinaryTree {
	 Node root;
	 
	    BinaryTree() { 
	    	root = null; 
	    	}
	    
	    void printPreorder(Node node)
	    {
	        if (node == null)
	            return;
	 
	    //     first print data of node 
	        System.out.print(node.key + " ");
	 
	      //   then recur on left sutree 
	        printPreorder(node.left);
	 
	        // now recur on right subtree 
	        printPreorder(node.right);
	    }
	 
	  //  Given a binary tree, print its nodes in inorder
	    void printInorder(Node node)
	    {
	        if (node == null)
	            return;
	 
	    //     first recur on left child 
	        printInorder(node.left);
	 
	      //   then print the data of node 
	        System.out.print(node.key + " ");
	 
	       //  now recur on right child 
	        printInorder(node.right);
	    }
	    void printPostorder(Node node){
	    	if(node==null){
	    		return;
	    	}
	    	printPostorder(node.left);
	    	printPostorder(node.right);
	    	System.out.print(node.key);
	    }
	    /* Given a binary tree, print its nodes according to the
	      "bottom-up" postorder traversal. */
	   /* void printPostorder(Node node)
	    {
	        if (node == null)
	            return;
	 
	        // first recur on left subtree
	        printPostorder(node.left);
	 
	        // then recur on right subtree
	        printPostorder(node.right);
	 
	        // now deal with the node
	        System.out.print(node.key + " ");
	    }
	 
	    */
	    public static void main(String[] args)
	    {
	        BinaryTree tree = new BinaryTree();
	        tree.root = new Node(1);
	        tree.root.left = new Node(2);
	        tree.root.right = new Node(3);
	        tree.root.left.left = new Node(4);
	        tree.root.left.right = new Node(5);
	 
	        System.out.println(
	            "Preorder traversal of binary tree is ");
	        tree.printPreorder(tree.root);
	 
	        System.out.println(
	            "\nInorder traversal of binary tree is ");
	        tree.printInorder(tree.root);
	 
	        System.out.println(
	            "\nPostorder traversal of binary tree is ");
	        tree.printPostorder(tree.root);
	    }

}
class Node {
    int key;
    Node left, right;
 
    public Node(int item)
    {
        key = item;
        left = right = null;
    }
}