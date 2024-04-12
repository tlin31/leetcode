Geeks - Maximum sum of nodes in Binary tree such that no two are adjacent

https://www.geeksforgeeks.org/maximum-sum-nodes-binary-tree-no-two-adjacent/


Question: 
Given a binary tree with a value associated with each node, we need to choose a subset of these 
nodes such that sum of chosen nodes is maximum under a constraint that no two chosen node in 
subset should be directly connected that is, if we have taken a node in our sum then we can’t take its 
any children in consideration and vice versa.


Method: 
- A node and its children can’t be in sum at same time:
	1. we take a node into our sum we will call recursively for its grandchildren 
	2. when we don’t take this node we will call for all its children nodes 

	Then choose maximum from both of these results.


- Use a memo: here is map stores result of complete subtree rooted at a node in the map



import java.util.HashMap; 
public class FindSumOfNotAdjacentNodes { 

	// method returns maximum sum possible from subtrees rooted 
	// at grandChildrens of node 'node' 
	public static int sumOfGrandChildren(Node node, HashMap<Node,Integer> mp) 
	{ 
		int sum = 0; 
		// call for children of left child only if it is not NULL 
		if (node.left!=null) 
			sum += getMaxSumUtil(node.left.left, mp) + 
				getMaxSumUtil(node.left.right, mp); 
	
		// call for children of right child only if it is not NULL 
		if (node.right!=null) 
			sum += getMaxSumUtil(node.right.left, mp) + 
				getMaxSumUtil(node.right.right, mp); 
		return sum; 
	} 

	// Utility method to return maximum sum rooted at node 'node' 
	public static int getMaxSumUtil(Node node, HashMap<Node,Integer> mp) 
	{ 
		if (node == null) 
			return 0; 
	
		// If node is already processed then return calculated 
		// value from map 
		if(mp.containsKey(node)) 
			return mp.get(node); 
	
		// take current node value and call for all grand children 
		int incl = node.data + sumOfGrandChildren(node, mp); 
	
		// don't take current node value and call for all children 
		int excl = getMaxSumUtil(node.left, mp) + getMaxSumUtil(node.right, mp); 
	
		// choose maximum from both above calls and store that in map 
		mp.put(node,Math.max(incl, excl)); 
	
		return mp.get(node); 
	} 

	// Returns maximum sum from subset of nodes 
	// of binary tree under given constraints 
	public static int getMaxSum(Node node) 
	{ 
		if (node == null) 
			return 0; 
		HashMap<Node,Integer> mp=new HashMap<>(); 
		return getMaxSumUtil(node, mp); 
	} 

	public static void main(String args[]) 
	{ 
		Node root = new Node(1); 
		root.left = new Node(2); 
		root.right = new Node(3); 
		root.right.left = new Node(4); 
		root.right.right = new Node(5); 
		root.left.left = new Node(1);	 
		System.out.print(getMaxSum(root)); 
	} 
} 

/* A binary tree node structure */
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


Method 2 (Using pair)

Method: 
	Return a pair for each node in the binary tree such that first of the pair indicates maximum sum 
	when the data of node is included and second indicates maximum sum when the data of a particular 
	node is not included.

/* Pair class */
class Pair 
{ 
	int first,second; 
	Pair(int first, int second) 
	{ 
		this.first=first; 
		this.second=second; 
	} 
} 


public class FindSumOfNotAdjacentNodes { 

	public static Pair maxSumHelper(Node root) 
	{ 
		if (root==null) { 
			Pair sum=new Pair(0, 0); 
			return sum; 
		} 
		Pair sum1 = maxSumHelper(root.left); 
		Pair sum2 = maxSumHelper(root.right); 
		Pair sum=new Pair(0,0); 
	
		// This node is included (Left and right children are not included) 
		sum.first = sum1.second + sum2.second + root.data; 
	
		// This node is excluded (Either left or right child is included) 
		sum.second = Math.max(sum1.first, sum1.second) + 
					Math.max(sum2.first, sum2.second); 
	
		return sum; 
	} 

	// Returns maximum sum from subset of nodes 
	// of binary tree under given constraints 
	public static int maxSum(Node root) 
	{ 
		Pair res=maxSumHelper(root); 
		return Math.max(res.first, res.second); 
	} 

	











