package com.java.quiz;


public class ReverseLL {


	public static void main(String[] args) {
		ListNode l3 = new ListNode(3);
		ListNode l = new ListNode(4,l3);
		ListNode ln = new ListNode(3, l);
		ListNode l1 = new ListNode(2, ln);
		ListNode l2 = new ListNode(1, l1);
		ListNode rev=reverseLL(l2);
		while(rev!=null){
			System.out.println(rev.val);
			rev=rev.next;
			
		}
	}


	private static ListNode reverseLL(ListNode head) {
		if(head==null){
			return null;
		}
		ListNode prev=null;
		ListNode curr=head;
		ListNode nl;
		
		while(curr!=null){
			nl=curr.next;
			curr.next=prev;
			prev=curr;
			curr=nl;
		}
		
		
		return prev;
		
	}

}
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}