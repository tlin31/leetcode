1388. Pizza With 3n Slices - Hard


There is a pizza with 3n slices of varying size, you and your friends will take slices of 
pizza as follows:

	You will pick any pizza slice.
	Your friend Alice will pick next slice in anti clockwise direction of your pick. 
	Your friend Bob will pick next slice in clockwise direction of your pick.
	Repeat until there are no more slices of pizzas.

Sizes of Pizza slices is represented by circular array slices in clockwise direction.

Return the maximum possible sum of slice sizes which you can have.

 

Example 1:



Input: slices = [1,2,3,4,5,6]
Output: 10
Explanation: Pick pizza slice of size 4, Alice and Bob will pick slices with size 3 and 5 
respectively. Then Pick slices with size 6, finally Alice and Bob will pick slice of size 2 
and 1 respectively. Total = 4 + 6.


Example 2:

Input: slices = [8,9,8,6,1,1]
Output: 16
Output: Pick pizza slice of size 8 in each turn. If you pick slice with size 9 your partners will pick slices of size 8.


Example 3:

Input: slices = [4,1,2,5,8,3,1,9,7]
Output: 21


Example 4:

Input: slices = [3,1,2]
Output: 3
 

Constraints:

1 <= slices.length <= 500
slices.length % 3 == 0
1 <= slices[i] <= 1000




******************************************************
key:
	- similar to rob houses --> 2 cases consider the 0th & last element
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time & Space:  O(n^2), n is the number of slices in pizza need to pick
	- 


Method:

	-	
	- pick n non-adjacent elements from circular array m=3n elements so that the sum of the elements 
	  is maximum.
	  Note: We can't pick 0th and (m-1)th elements at the same time

		Case 1: Don't pick (m-1)th element
		=> Solve problem: pick n non-adjacent elements from linear array elements in ranges [0..m-2] 
		                  so that the sum of the elements is maximum.

		Case 2: Don't pick 0th element
		=> Solve problem: pick n non-adjacent elements from linear array elements in ranges [1..m-1] 
		                  so that the sum of the elements is maximum.

	- dp[i][j] = maximum sum which we pick `j` 个elements from first i elements of array

		base case:
			Case j = 0 (pick 0 elements): dp[i][0] = 0
			Case i = 0 (array is empty): dp[0][j] = 0



	public int maxSizeSlices(int[] slices) {
		int m = slices.length, n = m / 3;
		int[] slices1 = Arrays.copyOfRange(slices, 0, m-1);
		int[] slices2 = Arrays.copyOfRange(slices, 1, m);
		return Math.max(maxSum(slices1, n), maxSum(slices2, n));
	}

	// max sum when pick `n` non-adjacent elements from `arr`
	int maxSum(int[] arr, int n) { 
		int m = arr.length;
		int[][] dp = new int[m+1][n+1]; 

		for (int i = 1; i <= m; ++i) {
			for (int j = 1; j <= n; ++j) {

				// array has only 1 element --> pick that element
				if (i == 1) { 
					dp[i][j] = arr[0]; 
				} 

				// opt 1: don't pick element i --> dp[i-1][j]: pick j elements in [0 ~ i-1]
				// opt 2: pick i --> dp[i-2][j-1] choose `j-1` elem from array `i-2` elem
				else {
					dp[i][j] = Math.max(
						dp[i-1][j],             
						dp[i-2][j-1] + arr[i-1] 
												
					);
				}
			}
		}
		return dp[m][n];
	}









~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: Greedy

Stats:

	- 
	- 


Method:

	-	use priority queue to hold current optimal result, and give us a chance to revoke our 
	    pick for a better option that is discovered later in the process	

		ex. [8,9,8,1,2,3] 
			9 + 3 < 8 + 8
			when picking 9, remove nodes 8, 9, 8, BUT add a new node 7(8+8-9) into the list
			so that when later 7 is picked, it's equivalent of picking 8 + 8 instead 9


class Solution {
    static class Node {
      public Node prev = null;
      public Node next = null;
      public int value = 0;
      public Node(int value) {
        this.value = value;
      }
    }
    public int maxSizeSlices(int[] slices) {
        int n = slices.length;
        int res = 0;
        
        // build up the doubly linked list and priority queue
        Node head = null, curr = null;
        Queue<Node> pq = new PriorityQueue<Node>((x, y) -> y.value - x.value);

        // construct the priority queue
        for (int value : slices) {
          Node node = new Node(value);

          // add cur node to the linked list
          if (head == null) {
            head = node;
          }
          if (curr == null) {
            curr = node;
          } 
          else {
            curr.next = node;
            node.prev = curr;
            curr = node;
          }
          pq.add(node);
        }


        // create cycle
        head.prev = curr;
        curr.next = head;
        
        
        for (int i = 0; i < n / 3; ++i) {
	        Node node = pq.poll();
	        res += node.value;
	        Node newNode = new Node(node.prev.value + node.next.value - node.value);
	        node.prev.prev.next = newNode;
	        newNode.prev = node.prev.prev;
	        node.next.next.prev = newNode;
	        newNode.next = node.next.next;
	        pq.remove(node.prev);
	        pq.remove(node.next);
	        pq.add(newNode);
        }

        return res;
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

