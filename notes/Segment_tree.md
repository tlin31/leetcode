# Segment Tree:

## Representation of Segment trees
1. Leaf Nodes are the elements of the input array.
2. Each internal node represents some merging of the leaf nodes. The merging may be different for different problems. For this problem, merging is sum of leaves under a node.

An array representation of tree is used to represent Segment Trees. For each node at index i, the left child is at index: 2*i+1, right child at 2*i+2 and the parent is at floor((i-1)/2).



## How does above segment tree look in memory?
Segmeent tree is rather a full binary tree (every node has 0 or 2 children) and all levels are filled except possibly the last level. Unlike Heap, the last level may have gaps between nodes. 


## Question list:

	1. Sum of given range
	2. Range Maximum Query with Node Update
	3. Range min query
	4. XOR  --> https://www.geeksforgeeks.org/segment-tree-set-3-xor-given-range/


=========================================================================================================
## Example 1: Sum of given range

Question: 
We have an array arr[0 . . . n-1]. We should be able to
	1. Find the sum of elements from index l to r where 0 <= l <= r <= n-1
	2. Change value of a specified element of the array to a new value x. We need to do arr[i] = x where 0 <= i <= n-1.



### Time complexity:

- Time Complexity for tree construction is O(n). There are total 2n-1 nodes, and value of every node is calculated only once in tree construction.

- Time complexity to query is O(Logn). To query a sum, we process at most four nodes at every level and number of levels is O(Logn).

- The time complexity of update is also O(Logn). To update a leaf value, we process one node at every level and number of levels is O(Logn).


### Representation of Segment trees

1. Leaf Nodes are the elements of the input array.
2. Each internal node represents some merging of the leaf nodes. For this problem, merging is sum of leaves under a node.



### Construction of Segment Tree from given array

We start with a segment arr[0 . . . n-1]. and every time we divide the current segment into two halves(if it has not yet become a segment of length 1), and then call the same procedure on both halves, and for each such segment, we store the sum in the corresponding node.

All levels of the constructed segment tree will be completely filled except the last level. Also, the tree will be a Full Binary Tree because we always divide segments in two halves at every level. Since the constructed tree is always a full binary tree with n leaves, there will be n-1 internal nodes. So the total number of nodes will be 2*n – 1. Note that this does not include dummy nodes


What is the total size of the array representing segment tree?

- If n is a power of 2, then there are no dummy nodes. So the size of the segment tree is 2n-1 (n leaf nodes and n-1) internal nodes. 
- If n is not a power of 2, then the size of the tree will be 2*x – 1 where x is the smallest power of 2 greater than n. For example, when n = 10, then size of array representing segment tree is 2*16-1 = 31.

- An alternate explanation for size is based on heignt. Height of the segment tree will be log_2 n. Since the tree is represented using array and relation between parent and child indexes must be maintained, size of memory allocated for segment tree will be 2 * 2^(log_2 n) - 1.

### Query for Sum of given range


int getSum(node, l, r) 
{
   if the range of the node is within l and r
        return value in the node
   else if the range of the node is completely outside l and r
        return 0
   else
    	return getSum(node's left child, l, r) + getSum(node's right child, l, r)
}



### Update a value
- recursion
- We are given an index which needs to be updated. Let diff be the value to be added. We start from the root of the segment tree and add diff to all nodes which have given index in their range. If a node doesn’t have a given index in its range, we don’t make any changes to that node.

### Implementation

```java

class SegmentTree 
{ 
	// Global variable The array that stores segment tree nodes
	int st[];  

	// Constructor to construct segment tree from given array. 
	// allocates memory for segment tree and calls constructSTUtil() to fill the allocated memory
	SegmentTree(int arr[], int n) {

		// Allocate memory for segment tree 
		// Height of segment tree 
		int height = (int) (Math.ceil(Math.log(n) / Math.log(2))); 

		//Maximum size of segment tree 
		int max_size = 2 * (int) Math.pow(2, height) - 1; 

		st = new int[max_size]; // Memory allocation 

		constructSTUtil(arr, 0, n - 1, 0); 
	} 


	// A recursive function that constructs Segment Tree for array[ss..se], fills up array st[]
	// si is index of current node in segment tree st (location to store in the st array)
	int constructSTUtil(int arr[], int ss, int se, int si) 
	{ 
		// If there is one element in array, store it in current node of segment tree and return 
		if (ss == se) { 
			st[si] = arr[ss]; 
			return arr[ss]; 
		} 

		// If there are more than one elements, then recur for left and right subtrees and 
		// store the sum of values in this node 
		int mid = getMid(ss, se); 
		st[si] = constructSTUtil(arr, ss, mid, si * 2 + 1) + 
				 constructSTUtil(arr, mid + 1, se, si * 2 + 2); 

		return st[si]; 
	} 


	int getMid(int s, int e) { 
		return s + (e - s) / 2; 
	} 


	// Return sum of elements in range from index qs (quey start) to qe (query end). 
	// use getSumUtil() 
	int getSum(int n, int qs, int qe) { 
		// Check for erroneous input values 
		if (qs < 0 || qe > n - 1 || qs > qe) { 
			System.out.println("Invalid Input"); 
			return -1; 
		} 
		return getSumUtil(0, n - 1, qs, qe, 0); 
	} 


	/* A recursive function to get the sum of values in given range 
		of the array. The following are parameters for this function. 

		st --> Pointer to segment tree 
		si --> Index of current node in the segment tree. Initially 0 is passed as root 
		ss & se --> Starting and ending indexes of the segment represented 
					by current node, i.e., st[si] 
		qs & qe --> Starting and ending indexes of query range 
	*/
	int getSumUtil(int ss, int se, int qs, int qe, int si) 
	{ 

		// if query range covers the sub-range (ss,se), then return the subrange's max
		if (qs <= ss && qe >= se) 
			return st[si]; 

		// If segment of this node is outside the given range 
		if (se < qs || ss > qe) 
			return 0; 

		// If a part of this segment overlaps with the given range 
		int mid = getMid(ss, se); 

		// get partial sum of the left part and right part
		// 2 * si + 1 --> left subtree
		return getSumUtil(ss, mid, qs, qe, 2 * si + 1) + 
				getSumUtil(mid + 1, se, qs, qe, 2 * si + 2); 
	} 


	// Update the value in input array and segment tree at position i to a new_val
	// use updateValueUtil()
	void updateValue(int arr[], int n, int i, int new_val) 
	{ 
		// Check for erroneous input index 
		if (i < 0 || i > n - 1) { 
			System.out.println("Invalid Input"); 
			return; 
		} 

		// Get the difference between new value and old value 
		int diff = new_val - arr[i]; 

		// Update the value in array 
		arr[i] = new_val; 

		// Update the values of nodes in segment tree 
		updateValueUtil(0, n - 1, i, diff, 0); 
	} 

	/* A recursive function to update the nodes which have the given 
		index in their range. The following are parameters 
		st, si, ss and se are same as getSumUtil() 
		i --> index of the element to be updated. This index is in input array. 
		diff --> Value to be added to all nodes which have i in range 
	*/
	void updateValueUtil(int ss, int se, int i, int diff, int si) 
	{ 
		// Base Case: If the input index lies outside the range of this segment 
		if (i < ss || i > se) 
			return; 

		// If the input index is in range of this node, then update the value of the node and its children 
		
		st[si] = st[si] + diff; 

		// recurse
		if (se != ss) { 
			int mid = getMid(ss, se); 
			updateValueUtil(ss, mid, i, diff, 2 * si + 1); 
			updateValueUtil(mid + 1, se, i, diff, 2 * si + 2); 
		} 
	} 




	// Driver program to test above functions 
	public static void main(String args[]) 
	{ 
		int arr[] = {1, 3, 5, 7, 9, 11}; 
		int n = arr.length; 


		// Build segment tree from given array 
		SegmentTree tree = new SegmentTree(arr, n); 


		// Print sum of values in array from index 1 to 3 
		System.out.println("Sum of values in given range = " + tree.getSum(n, 1, 3)); 

		// Update: set arr[1] = 10 and update corresponding segment tree nodes 
		tree.updateValue(arr, n, 1, 10); 

		// Find sum after the value is updated 
		System.out.println("Updated sum of values in given range = " + ree.getSum(n, 1, 3)); 
	} 
} 

```

the seg tree at 0 is: 36
the seg tree at 1 is: 9
the seg tree at 2 is: 27
the seg tree at 3 is: 4
the seg tree at 4 is: 5
the seg tree at 5 is: 16
the seg tree at 6 is: 11
the seg tree at 7 is: 1
the seg tree at 8 is: 3
the seg tree at 9 is: 0
the seg tree at 10 is: 0
the seg tree at 11 is: 7
the seg tree at 12 is: 9
the seg tree at 13 is: 0
the seg tree at 14 is: 0


=========================================================================================================
## Example 2: Range Maximum Query with Node Update
https://www.geeksforgeeks.org/segment-tree-set-2-range-maximum-query-node-update/

### Question: 
We have an array arr[0 . . . n-1]. We should be able to
	1. Find the maximum of elements from index l to r where 0 <= l <= r <= n-1. 
	2. change the value of a specified element of the array to a new value x. We need to do arr[i] = x where 0 <= i <= n-1 and then find the maximum element of given range with updated values.

Example :

Input: {1, 4, 2, 6, 12, 10}

Output:
Max of values in given range = 6
Updated max of values in given range = 8


### Time complexity:

Both operations in O(Logn) time.



### Representation of Segment trees
1. Leaf Nodes are the elements of the input array.
2. Each internal node represents the maximum of all of its child.


### Query for minimum value of given range : 

Once the tree is constructed, below is the algorithm to find maximum of given range.

node--> node number, l --> query start index, r --> query end index;

int getMax(node, l, r) 
{
   if range of node is within l and r
        return value of node
   else if range of node is completely outside l and r
        return -1
   else
    return max(getMax(node's left child, l, r),
           getMax(node's right child, l, r))
}

```java
!! only write the difference from example 1: 

	int MaxUtil(int* st, int ss, int se, int l,  int r, int node) 
	{ 
	    
	    if (l <= ss && r >= se) 
	        return st[node]; 
	  
	    if (se < l || ss > r) 
	        return -1; 
	  
	    int mid = getMid(ss, se); 
	     
	    /* 
	    	return the max !!!
	    */ 
	    return max(MaxUtil(st, ss, mid, l, r, 2 * node + 1), MaxUtil(st, mid + 1, se, l, r, 2 * node + 2)); 
	} 
	  
	// index -> index of the element to be updated
	// example call:     updateValue(arr, st, 0, n - 1, 1, 8, 0); 
	void updateValue(int arr[], int* st, int ss, int se,  int index, int value, int node) 
	{ 
	    if (index < ss || index > se)  
	    { 
	        cout << "Invalid Input" << endl; 
	        return; 
	    } 
	      
	    if (ss == se) {    
	        // update value in array and in segment tree 
	        arr[index] = value; 
	        st[node] = value; 
	    } else { 
	            int mid = getMid(ss, se); 
	              
	            if (index >= ss && index <= mid) 
	                updateValue(arr, st, ss, mid, index, value, 2 * node + 1); 
	            else
	                updateValue(arr, st, mid + 1, se, index, value, 2 * node + 2); 
	              
	            st[node] = max(st[2 * node + 1],  st[2 * node + 2]); 
	    } 
	    return; 
	} 
	  

	int constructSTUtil(int arr[], int ss, int se, int* st, int si) 
	{ 
	    // If there is one element in array, store 
	    // it in current node of segment tree and return 
	    if (ss == se)  
	    { 
	        st[si] = arr[ss]; 
	        return arr[ss]; 
	    } 
	  
	    int mid = getMid(ss, se); 
	      
	    st[si] = max(constructSTUtil(arr, ss, mid, st,  si * 2 + 1), 
	                 constructSTUtil(arr, mid + 1, se,  st, si * 2 + 2)); 
	      
	    return st[si]; 
	} 
  

```

Output:
Max of values in given range = 7
Updated max of values in given range = 8


12
4
12
4
2
12
10
1
4
0
0
6
12
0
0


=========================================================================================================
## Example 3: Query for minimum value of given range


Question: 
We have an array arr[0 . . . n-1]. We should be able to efficiently find the minimum value from index qs (query start) to qe (query end) where 0 <= qs <= qe <= n-1.



### Time complexity:
- Time Complexity for tree construction is O(n). There are total 2n-1 nodes, and value of every node is calculated only once in tree construction.

- Time complexity to query is O(Logn). To query a range minimum, we process at most two nodes at every level and number of levels is O(Logn).

### Query algo

// qs --> query start index, 
   qe --> query end index
int RMQ(node, qs, qe) 
{
   if range of node is within qs and qe
        return value in node
   else if range of node is completely outside qs and qe
        return INFINITE
   else
    return min(RMQ(node's left child, qs, qe), RMQ(node's right child, qs, qe) )
}

### Implementation:

```java
class SegmentTreeRMQ 
{ 
    int st[]; //array to store segment tree 
  
    // A utility function to get minimum of two numbers 
    int minVal(int x, int y) { 
        return (x < y) ? x : y; 
    } 
  
    // A utility function to get the middle index from corner 
    // indexes. 
    int getMid(int s, int e) { 
        return s + (e - s) / 2; 
    } 
  
    /*  A recursive function to get the minimum value in a given 
        range of array indexes. The following are parameters for 
        this function. 
  
        st    --> Pointer to segment tree 
        index --> Index of current node in the segment tree. Initially 
                   0 is passed as root is always at index 0 
        ss & se  --> Starting and ending indexes of the segment 
                     represented by current node, i.e., st[index] 
        qs & qe  --> Starting and ending indexes of query range */
    int RMQUtil(int ss, int se, int qs, int qe, int index) 
    { 
        // If segment of this node is a part of given range, then 
        // return the min of the segment 
        if (qs <= ss && qe >= se) 
            return st[index]; 
  
        // If segment of this node is outside the given range 
        if (se < qs || ss > qe) 
            return Integer.MAX_VALUE; 
  
        // If a part of this segment overlaps with the given range 
        int mid = getMid(ss, se); 
        return minVal(RMQUtil(ss, mid, qs, qe, 2 * index + 1), 
                RMQUtil(mid + 1, se, qs, qe, 2 * index + 2)); 
    } 
  
    // Return minimum of elements in range from index qs (query 
    // start) to qe (query end).  It mainly uses RMQUtil() 
    int RMQ(int n, int qs, int qe) 
    { 
        // Check for erroneous input values 
        if (qs < 0 || qe > n - 1 || qs > qe) { 
            System.out.println("Invalid Input"); 
            return -1; 
        } 
  
        return RMQUtil(0, n - 1, qs, qe, 0); 
    } 
  
    // A recursive function that constructs Segment Tree for 
    // array[ss..se]. si is index of current node in segment tree st 
    int constructSTUtil(int arr[], int ss, int se, int si) 
    { 
        // If there is one element in array, store it in current 
        //  node of segment tree and return 
        if (ss == se) { 
            st[si] = arr[ss]; 
            return arr[ss]; 
        } 
  
        // If there are more than one elements, then recur for left and 
        // right subtrees and store the minimum of two values in this node 
        int mid = getMid(ss, se); 
        st[si] = minVal(constructSTUtil(arr, ss, mid, si * 2 + 1), 
                constructSTUtil(arr, mid + 1, se, si * 2 + 2)); 
        return st[si]; 
    } 
  
    /* Function to construct segment tree from given array. This function 
       allocates memory for segment tree and calls constructSTUtil() to 
       fill the allocated memory */
    void constructST(int arr[], int n) 
    { 
        // Allocate memory for segment tree 
  
        //Height of segment tree 
        int x = (int) (Math.ceil(Math.log(n) / Math.log(2))); 
  
        //Maximum size of segment tree 
        int max_size = 2 * (int) Math.pow(2, x) - 1; 
        st = new int[max_size]; // allocate memory 
  
        // Fill the allocated memory st 
        constructSTUtil(arr, 0, n - 1, 0); 
    } 
  
    // Driver program to test above functions 
    public static void main(String args[])  
    { 
        int arr[] = {1, 3, 2, 7, 9, 11}; 
        int n = arr.length; 
        SegmentTreeRMQ tree = new SegmentTreeRMQ(); 
  
        // Build segment tree from given array 
        tree.constructST(arr, n); 
  
        int qs = 1;  // Starting index of query range 
        int qe = 5;  // Ending index of query range 
  
        // Print minimum value in arr[qs..qe] 
        System.out.println("Minimum of values in range [" + qs + ", "
                           + qe + "] is = " + tree.RMQ(n, qs, qe)); 
    } 
} 
```

Output:
 
Minimum of values in range [1, 5] is = 2








