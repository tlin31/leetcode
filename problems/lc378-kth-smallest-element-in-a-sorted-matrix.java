378. Kth Smallest Element in a Sorted Matrix - Medium

Given a n x n matrix where each of the rows and columns are sorted in ascending order, find the 
kth smallest element in the matrix.

Note that it is the kth smallest element in the sorted order, not the kth distinct element.

Example:

matrix = [
   [ 1,  5,  9],
   [10, 11, 13],
   [12, 13, 15]
],
k = 8,

return 13.
Note:
You may assume k is always valid, 1 ≤ k ≤ n2.

******************************************************
key:
	- Heap
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- Heap
	- Algo: 
		1. Build a minHeap of elements from the first row.
		2. Do the following operations k-1 times:
			Every time when you poll out the root(Top Element in Heap), you need to know the row 
			number and column number of that element(so we can create a tuple class here), 
			replace that root with the next element from the same column.

	- can also build a min Heap from the first column, and do the similar operations as above.
		(Replace the root with the next element from the same row)

stats:
	- go through k elements
	- O(min(K,N)+K∗logN)

public class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        PriorityQueue<Tuple> pq = new PriorityQueue<Tuple>();
        for(int j = 0; j <= n-1; j++) 
        	pq.offer(new Tuple(0, j, matrix[0][j]));
        for(int i = 0; i < k-1; i++) {
            Tuple t = pq.poll();

            //the end of the row
            if(t.x == n-1) 
            	continue;

            //add the next element in the same column
            pq.offer(new Tuple(t.x+1, t.y, matrix[t.x+1][t.y]));
        }
        return pq.poll().val;
    }
}

class Tuple implements Comparable<Tuple> {
    int x, y, val;
    public Tuple (int x, int y, int val) {
        this.x = x;
        this.y = y;
        this.val = val;
    }
    
    @Override
    public int compareTo (Tuple that) {
        return this.val - that.val;
    }
}


----
import java.util.*;

class Node {
  int row;
  int col;

  Node(int row, int col) {
    this.row = row;
    this.col = col;
  }
}

class Solution {

  public static int findKthSmallest(int[][] matrix, int k) {
    PriorityQueue<Node> minHeap = new PriorityQueue<Node>((n1, n2) 
    	-> matrix[n1.row][n1.col] - matrix[n2.row][n2.col]);

    // put the 1st element of each row in the min heap we don't need to push more than 
    // 'k' elements in the heap
    for (int i = 0; i < matrix.length && i < k; i++)
      minHeap.add(new Node(i, 0));

    // take the smallest (top) element form the min heap, if the running count is equal to k 
  	// 	return the number
    // if the row of the top element has more elements, add the next element to the heap
    int numberCount = 0, result = 0;
    while (!minHeap.isEmpty()) {
      	Node node = minHeap.poll();
      	result = matrix[node.row][node.col];
      	if (++numberCount == k)
        	break;
      	node.col++;

      // when finish the row, then don't care about the one being popper already, and add the next
      	if (matrix[0].length > node.col)
        	minHeap.add(node);
    }
    return result;
  }
}


=======================================================================================================
method 2: https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/discuss/301357/Java-0ms-(added-Python-and-C%2B%2B)%3A-Easy-to-understand-solutions-using-Heap-and-Binary-Search

method:

	- binary search
	- binary search search on: 
		1. index: if array is sorted in one direction, we can use index as "search space"
			index -- A bunch of examples -- https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/ ( the array is sorted)

		2. range(the range from the smallest number to the biggest number): if array is unsorted and 
			we are going to find a specific number, we can use "range".
			range -- https://leetcode.com/problems/find-the-duplicate-number/ (Unsorted Array)


	- The reason why we did not use index as "search space" for this problem is the matrix is sorted 
		in two directions, we can not find a linear way to map the number and its index.
	- Algo:
		1. Start the Binary Search with start = matrix[0][0] and end = matrix[n-1][n-1].

		2. Find middle of the start and the end. 
			!This middle number is NOT necessarily an element in the matrix.

		3. Count all the numbers smaller than or equal to middle in the matrix. B/c matrix is sorted,
			do this in O(N).

		4. While counting, we can keep track of the “smallest number greater than the middle” (let’s 
			call it n1) and at the same time the “biggest number less than or equal to the middle” 
			(let’s call it n2). 

			These two numbers will be used to adjust the "number range" for the Binary Search in the 
			next iteration.

		5. If the count is equal to ‘K’, n1 will be our required number as it is the “biggest number 
			less than or equal to the middle”, and is definitely present in the matrix.

		6. If the count is less than ‘K’, update start = n2 
		   If the count is greater than ‘K’, update end = n1 to search in the lower part

stats:

	- Runtime: 1 ms, faster than 86.10% of Java online submissions for Kth Smallest Element in a Sorted Matrix.
	- Memory Usage: 44.2 MB, less than 64.86%
	- Main loop is binary search of max - min.
		Swap from left-bottom to right-top can get count <= mid in O(n) time instead of O(nlogn), 
		total complexity will be O(nlogm) while m = max - min.


class Solution {
  public static int findKthSmallest(int[][] matrix, int k) {
    int n = matrix.length;
    int start = matrix[0][0], end = matrix[n - 1][n - 1];
    while (start < end) {
      int mid = start + (end - start) / 2;
      // first number is the smallest and the second number is the largest
      int[] smallLargePair = { matrix[0][0], matrix[n - 1][n - 1] };

      int count = countLessEqual(matrix, mid, smallLargePair);

      if (count == k)
        return smallLargePair[0];

      if (count < k)
        start = smallLargePair[1]; // search higher
      else
        end = smallLargePair[0]; // search lower
    }

    return start;
  }

  private static int countLessEqual(int[][] matrix, int mid, int[] smallLargePair) {
    int count = 0;
    int n = matrix.length, row = n - 1, col = 0;
    while (row >= 0 && col < n) {
      if (matrix[row][col] > mid) {
        // as matrix[row][col] is bigger than the mid, let's keep track of the
        // smallest number greater than the mid
        smallLargePair[1] = Math.min(smallLargePair[1], matrix[row][col]);
        row--;
      } else {
        // as matrix[row][col] is less than or equal to the mid, let's keep track of the
        // biggest number less than or equal to the mid
        smallLargePair[0] = Math.max(smallLargePair[0], matrix[row][col]);
        count += row + 1;
        col++;
      }
    }
    return count;
  }
}




=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



