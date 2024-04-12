/**
https://www.geeksforgeeks.org/sorted-order-printing-of-an-array-that-represents-a-bst/
Given an array that stores a complete Binary Search Tree, write a function that efficiently prints the 
given array in ascending order.
For example, given an array [4, 2, 5, 1, 3], the function should print 1, 2, 3, 4, 5
O(n)

method: Inorder traversal of BST prints it in ascending order. The only trick is to modify recursion 
termination condition in standard Inorder Tree Traversal.

!!! when we put a BST in the array, could simply use indexes to access the node
**/


// JAVA Code for Sorted order printing of a 
// given array that represents a BST 
class GFG{ 
	
private static void printSorted(int[] arr, int start, 
										int end) { 
		if(start > end) 
			return; 
			
		// print left subtree 
		printSorted(arr, start*2 + 1, end); 				// key concept: in an array, the node's left child is at 2*index +1
			
		// print root 
		System.out.print(arr[start] + " "); 
			
		// print right subtree 
		printSorted(arr, start*2 + 2, end); 
		} 
		
	// driver program to test above function 
	public static void main(String[] args) { 
		int arr[] = {4, 2, 5, 1, 3}; 
			
		printSorted(arr, 0, arr.length-1); 
	} 
} 
	
// This code is contributed by Arnav Kr. Mandal. 
