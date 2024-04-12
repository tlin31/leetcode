1199. Minimum Time to Build Blocks - Hard & Huffman


You are given a list of blocks, where blocks[i] = t means that the i-th block needs t units of time 
to be built. A block can only be built by exactly one worker.

A worker can either split into two workers (number of workers increases by one) or build a block then go home. Both decisions cost some time.

The time cost of spliting one worker into two workers is given as an integer split. Note that if two workers split at the same time, they split in parallel so the cost would be split.

Output the minimum time needed to build all blocks.

Initially, there is only one worker.

 

Example 1:

Input: blocks = [1], split = 1
Output: 1
Explanation: We use 1 worker to build 1 block in 1 time unit.


Example 2:

Input: blocks = [1,2], split = 5
Output: 7
Explanation: We split the worker into 2 workers in 5 time units then assign each of them to a block so the cost is 5 + max(1, 2) = 7.


Example 3:
Input: blocks = [1,2,3], split = 1

Output: 4
Explanation: Split 1 worker into 2, then assign the first worker to the last block and split the second worker into 2.
Then, use the two unassigned workers to build the first two blocks.
The cost is 1 + max(3, 1 + max(1, 2)) = 4.
 

Constraints:

1 <= blocks.length <= 1000
1 <= blocks[i] <= 10^5
1 <= split <= 100



******************************************************
key:
	- Huffman
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- It is O(n-log-n) because we are making 2n - 1 insertions into a heap and n removals, and 
	  both heap insertion and removal have a cost of O(log-n). We drop the constants, giving a 
	  final cost of O(n-log-n).

	- Time O(NlogN), Space O(N)



Method:
	- For example, a possible (not optimal) tree for the data set
		[1, 2, 4, 7, 10] with split cost 3 is:

			       3
			     /    \
			     3     \
			  /     \    \
			 3       3    \
			/  \    / \    \
			1   2   4   7   10

		This tree has a maximum depth of 16 (3 -> 3 -> 3 -> 7).

	-  So, how can we optimise the construction of this tree? Huffman algorithm!
	-  Instead of actually building the whole tree, we can just keep track of the nodes the huffman 
	   algorithm still needs to consider, as the maximum depth below them. So put all the leaf 
	   node (blocks) onto a priority queue, and then repeatedly take the 2 smallest off and add 
	   split onto the biggest of the 2 (this is Huffman's algorithm, it's greedy) and put the 
	   result back onto the priority queue.

	-  Once there is only one left, this is the depth of the root node, and we return it.
	-  only add y b/c we know in the priority queue, the later element (y) must be >= earlier elem (x)


class Solution {
    public int minBuildTime(int[] blocks, int split) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int b : blocks) pq.add(b);
        while (pq.size() > 1) {
            int x = pq.poll(), 
                y = pq.poll();
            pq.add(y + split);
        }
        return pq.poll();
    }
}






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

 def minBuildTime(self, A, split):
        heapq.heapify(A)
        while len(A) > 1:
            x, y = heapq.heappop(A), heapq.heappop(A)
            heapq.heappush(A, y + split)
        return heapq.heappop(A)


=======================================================================================================
Huffman Algorithm:


Steps to build Huffman Tree
	- Input is an array of unique characters along with their frequency of occurrences and output 
	  is Huffman Tree.

	1. Create a leaf node for each unique character and build a min heap of all leaf nodes (Min 
	   Heap is used as a priority queue. The value of frequency field is used to compare two 
	   nodes in min heap. Initially, the least frequent character is at root)

	2. Extract two nodes with the minimum frequency from the min heap.

	3. Create a new internal node with a frequency equal to the sum of the two nodes frequencies. 
	   Make the first extracted node as its left child and the other extracted node as its right 
	   child. Add this node to the min heap.

	4. Repeat steps#2 and #3 until the heap contains only one node. The remaining node is the root 
	   node and the tree is complete.


Ex.

	character   Frequency
	    a            5
	    b           9
	    c           12
	    d           13
	    e           16
	    f           45
	Step 1. Build a min heap that contains 6 nodes where each node represents root of a tree with 
	        single node.

	Step 2 Extract two minimum frequency nodes from min heap. Add a new internal node with frequency 
	       5 + 9 = 14.

	Now min heap contains 5 nodes where 4 nodes are roots of trees with single element each, and 
	one heap node is root of tree with 3 elements

	character           Frequency
	       c               12
	       d               13
	 Internal Node         14
	       e               16
	       f               45

	Step 3: Extract two minimum frequency nodes from heap. Add a new internal node with frequency 
	        12 + 13 = 25

	Now min heap contains 4 nodes where 2 nodes are roots of trees with single element each, and 
	two heap nodes are root of tree with more than one nodes.

	character           Frequency
	Internal Node          14
	       e               16
	Internal Node          25
	       f               45
	Step 4: Extract two minimum frequency nodes. Add a new internal node with frequency 14 + 16 = 30

	Now min heap contains 3 nodes.

	character          Frequency
	Internal Node         25
	Internal Node         30
	      f               45 

	Step 5: Extract two minimum frequency nodes. Add a new internal node with frequency 25 + 30 = 55

	Now min heap contains 2 nodes.

	character     Frequency
	       f         45
	Internal Node    55

	Step 6: Extract two minimum frequency nodes. Add a new internal node with frequency 45 + 55 = 100

	Now min heap contains only one node.

	character      Frequency
	Internal Node    100
	Since the heap contains only one node, the algorithm stops here.


Steps to print codes from Huffman Tree:
	Traverse the tree formed starting from the root. Maintain an auxiliary array. While moving to 
	the left child, write 0 to the array. While moving to the right child, write 1 to the array. 
	Print the array when a leaf node is encountered.


	The codes are as follows:

	character   code-word
	    f          0
	    c          100
	    d          101
	    a          1100
	    b          1101
	    e          111


import java.util.PriorityQueue; 
import java.util.Scanner; 
import java.util.Comparator; 


class HuffmanNode { 

	int data; 
	char c; 

	HuffmanNode left; 
	HuffmanNode right; 
} 


class MyComparator implements Comparator<HuffmanNode> { 
	public int compare(HuffmanNode x, HuffmanNode y) 
	{ 

		return x.data - y.data; 
	} 
} 

public class Huffman { 

	// recursive function to print the huffman-code through the tree traversal. 
	public static void printCode(HuffmanNode root, String s) { 

		// base case: if the left and right are null & it contains a character
		// then its valid huffman coded char & we print the code s generated by traversing the tree. 
		if (root.left == null
			&& root.right == null
			&& Character.isLetter(root.c)) { 

			// c is the character in the node 
			System.out.println(root.c + ":" + s); 

			return; 
		} 

		// if we go to left then add "0" to the code. 
		// if we go to the right add"1" to the code. 

		printCode(root.left, s + "0"); 
		printCode(root.right, s + "1"); 
	} 

	// main function 
	public static void main(String[] args) 
	{ 

		Scanner s = new Scanner(System.in); 

		// number of characters. 
		int n = 6; 
		char[] charArray = { 'a', 'b', 'c', 'd', 'e', 'f' }; 
		int[] charfreq = { 5, 9, 12, 13, 16, 45 }; 

		
		PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(n, new MyComparator()); 

		// add all huffman nodes to the queue
		for (int i = 0; i < n; i++) { 

			// creating a Huffman node object and add it to the priority queue. 
			HuffmanNode hn = new HuffmanNode(); 

			hn.c = charArray[i]; 
			hn.data = charfreq[i]; 

			hn.left = null; 
			hn.right = null; 

			q.add(hn); 
		} 

		// create a root node 
		HuffmanNode root = null; 

		// Here we will extract 2 min value from the heap until its size reduces to 1
		while (q.size() > 1) { 

			// first min extract. 
			HuffmanNode x = q.peek(); 
			q.poll(); 

			// second min extarct. 
			HuffmanNode y = q.peek(); 
			q.poll(); 

			// new node f which is equal 
			HuffmanNode f = new HuffmanNode(); 


			f.data = x.data + y.data; 
			f.c = '-'; 

			// first extracted node as left child. 
			f.left = x; 

			// second extracted node as the right child. 
			f.right = y; 

			// marking the f node as the root node. 
			root = f; 

			// add this node to the priority-queue. 
			q.add(f); 
		} 

		// print the codes by traversing the tree 
		printCode(root, ""); 
	} 
} 



~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

