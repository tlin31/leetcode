Remove duplicates from a linked list (both sorted and not sorted)


******************************************************
key:
	- 2 for loops, or use hashing, or sort the linked list 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1 --> non sorted:

method:

	- hashing
	- We traverse the link list from head to end. For every newly encountered element, we check 
	  whether it is in the hash table: if yes, we remove it; otherwise we put it in the hash table.

stats:

	- 
	- 

// Java program to remove duplicates 
// from unsorted linkedlist 

import java.util.HashSet; 

public class removeDuplicates 
{ 
	static class node 
	{ 
		int val; 
		node next; 

		public node(int val) 
		{ 
			this.val = val; 
		} 
	} 
	
	/* Function to remove duplicates from a 
	unsorted linked list */
	static void removeDuplicate(node head) 
	{ 
		// Hash to store seen values 
		HashSet<Integer> hs = new HashSet<>(); 
	
		/* Pick elements one by one */
		node current = head; 
		node prev = null; 
		while (current != null) 
		{ 
			int curval = current.val; 
			
			// If current value is seen before 
			if (hs.contains(curval)) { 
				prev.next = current.next; 
			} else { 
				hs.add(curval); 
				prev = current; 
			} 
			current = current.next; 
		} 

	} 
	
	/* Function to print nodes in a given linked list */
	static void printList(node head) 
	{ 
		while (head != null) 
		{ 
			System.out.print(head.val + " "); 
			head = head.next; 
		} 
	} 

	



=======================================================================================================
method 2 --> sorted:

Write a function which takes a list sorted in non-decreasing order and deletes any duplicate nodes from 
the list. The list should only be traversed once.

For example if the linked list is 11->11->11->21->43->43->60 then removeDuplicates() should convert the 
list to 11->21->43->60.

Traverse the list from the head (or start) node. While traversing, compare each node with its next node. 
If data of next node is same as current node then delete the next node. Before we delete a node, we need 
to store next pointer of the node

method:

	- 
	- 

stats:
	- Time Complexity: O(n) where n is number of nodes in the given linked list.
	- 

class LinkedList 
{ 
	Node head; // head of list 

	/* Linked list Node*/
	class Node 
	{ 
		int data; 
		Node next; 
		Node(int d) {data = d; next = null; } 
	} 

	void removeDuplicates() 
	{ 
		/*Another reference to head*/
		Node curr = head; 

		/* Traverse list till the last node */
		while (curr != null) { 
			Node temp = curr; 

			// compare current node with  next node and keep on deleting them until it matches 
			// the current node data 
			while(temp!=null && temp.data==curr.data) { 
				temp = temp.next; 
			} 
			// Set current node next to the next different element denoted by temp
			curr.next = temp; 
			curr = curr.next; 
		} 
	} 
					
	/* Utility functions */

	/* Inserts a new Node at front of the list. */
	public void push(int new_data) 
	{ 
		/* 1 & 2: Allocate the Node & 
				Put in the data*/
		Node new_node = new Node(new_data); 

		/* 3. Make next of new Node as head */
		new_node.next = head; 

		/* 4. Move the head to point to new Node */
		head = new_node; 
	} 

	/* Function to print linked list */
	void printList() 
	{ 
		Node temp = head; 
		while (temp != null) 
		{ 
			System.out.print(temp.data+" "); 
			temp = temp.next; 
		} 
		System.out.println(); 
	} 

	/* Drier program to test above functions */
	public static void main(String args[]) 
	{ 
		LinkedList llist = new LinkedList(); 
		llist.push(20); 
		llist.push(13); 
		llist.push(13); 
		llist.push(11); 
		llist.push(11); 
		llist.push(11); 
		
		System.out.println("List before removal of duplicates"); 
		llist.printList(); 
		
		llist.removeDuplicates(); 
		
		System.out.println("List after removal of elements"); 
		llist.printList(); 
	} 
} 
/* This code is contributed by Rajat Mishra */	

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



