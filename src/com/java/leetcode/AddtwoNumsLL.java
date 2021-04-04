package com.java.leetcode;

public class AddtwoNumsLL {

	public static void main(String[] args) {
		AddtwoNumsLL t= new AddtwoNumsLL();
		ListNode l=new ListNode(2);
		ListNode ln=new ListNode(4,l);
		ListNode l1=new ListNode(3,ln);
		ListNode lx=new ListNode(5);
		ListNode lxn=new ListNode(6,lx);
		ListNode l2=new ListNode(4,lxn);
		ListNode li=t.addTwoNumbers(l1, l2);
		while(li!=null){
			System.out.println(li.val);
			li=li.next;
		}
	}
	 public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		 ListNode l=new ListNode();
		 ListNode c=l;
		 int carry=0;
		 while(l1 !=null || l2 !=null){
			int x= l1!=null?l1.val:0;
			int y= l2!=null?l2.val:0;
			int sum=carry+x+y;
			carry=sum/10;
			c.next=new ListNode(sum%10);
			c=c.next;
			if(l1!=null)
			{l1=l1.next;}
			
			if(l2!=null)
			{l2=l2.next;}
		 }
			if(carry>0){
				c.next=new ListNode(carry);
			}
		
		 return l.next;
	    }
}
 class ListNode {
	      int val;
	      ListNode next;
	      ListNode() {}
	      ListNode(int val) { this.val = val; }
	      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
	  }