/**
Print path from root to a given node in a binary tree
https://www.geeksforgeeks.org/print-path-root-given-node-binary-tree/

time compexity: O(n)

Question： Given a binary tree with distinct nodes(no two nodes have the same have data values). The problem is 
to print the path from root to a given node x. If node x is not present then print “No Path”.

Method: Create a recursive function that traverses the different path in the binary tree to find the required 
node x. If node x is present then it returns true and accumulates the path nodes in some array arr[]. Else it 
returns false.

Following are the cases during the traversal:
1) If root = NULL, return false.
2) push the root’s data into arr[].
3) if root’s data = x, return true.
4) if node x is present in root’s left or right subtree, return true.
Else remove root’s data value from arr[] and return false.

This recursive function can be accessed from other function to check whether node x is present or not and 
if it is present, then the path nodes can be accessed from arr[]. You can define arr[]globally or pass its 
reference to the recursive function.
**/

// Java implementation to print the path from root 
// to a given node in a binary tree 
import java.util.ArrayList; 
public class PrintPath { 

	// Returns true if there is a path from root 
	// to the given node. It also populates 
	// 'arr' with the given path 
	public static boolean hasPath(Node root, ArrayList<Integer> arr, int x) 
	{ 
		// if root is NULL 
		// there is no path 
		if (root==null) 
			return false; 
		
		// push the node's value in 'arr' 
		arr.add(root.data);	 
		
		// if it is the required node 
		// return true 
		if (root.data == x)	 
			return true; 
		
		// else check whether the required node lies 
		// in the left subtree or right subtree of 
		// the current node 
		if (hasPath(root.left, arr, x) || 
			hasPath(root.right, arr, x)) 
			return true; 
		
		// !!! clean up is important!!!
		// required node does not lie either in the 
		// left or right subtree of the current node 
		// Thus, remove current node's value from 
		// 'arr' and then return false	 
		arr.remove(arr.size()-1); 
		return false;			 
	} 

	// function to print the path from root to the 
	// given node if the node lies in the binary tree 
	public static void printPath(Node root, int x) 
	{ 
		// ArrayList to store the path 
		ArrayList<Integer> arr = new ArrayList<>(); 
	
		// if required node 'x' is present 
		// then print the path 
		if (hasPath(root, arr, x)) 
		{ 
			for (int i=0; i<arr.size()-1; i++)	 
				System.out.print(arr.get(i)+"->"); 
			System.out.print(arr.get(arr.size() - 1));	 
		} 
		
		// 'x' is not present in the binary tree 
		else
			System.out.print("No Path"); 
	} 

	public static void main(String args[]) { 
		Node root=new Node(1); 
		root.left = new Node(2); 
		root.right = new Node(3); 
		root.left.left = new Node(4); 
		root.left.right = new Node(5); 
		root.right.left = new Node(6); 
		root.right.right = new Node(7); 
		int x=5; 
		printPath(root, x); 
	} 
} 

// A node of binary tree 
class Node 
{ 
	int data; 
	Node left, right; 
	Node(int data) 
	{ 
		this.data=data; 
		left=right=null; 
	} 
}; 
