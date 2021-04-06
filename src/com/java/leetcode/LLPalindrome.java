package com.java.leetcode;

public class LLPalindrome {

	public static void main(String[] args) {
		ListNode l3 = new ListNode(1);
		ListNode l = new ListNode(2,l3);
		ListNode ln = new ListNode(4, l);
		ListNode l1 = new ListNode(2, ln);
		ListNode l2 = new ListNode(1, l1);
		LLPalindrome llp = new LLPalindrome();
		System.out.println(llp.isPalindrome(l2));
	}

	public boolean isPalindrome(ListNode head) {
		ListNode mid=findMid(head);
		 ListNode curr=head;
		 ListNode rev=reverse(mid);
		 boolean flag=compare(rev,head);
		return flag;
	}
	private boolean compare(ListNode rev, ListNode head) {
		boolean flag=true;
		while(rev!=null && head!=null){
			if(rev.val!=head.val){
				flag=false;
				break;
			}
			rev=rev.next;
			head=head.next;
		}
		return flag;
	}

	private ListNode reverse(ListNode current) {
		ListNode prev=null;
		ListNode next;
		ListNode curr=current;
		while(curr!=null){
			next=curr.next;
			curr.next=prev;
			prev=curr;
			curr=next;
		}
		return prev;
	}

	private ListNode findMid(ListNode head) {
		ListNode slow = head;
		ListNode fast = head;
		int count=1;
		while (fast.next != null) {
			slow = slow.next;
			if (fast.next.next != null) {
				fast = fast.next.next;
				count+=2;
			} else {
				fast = fast.next;
				count++;
			}

		}
		if(count%2==0){
			return slow;
		}
		else{
			return slow.next;
		}
	}
}
