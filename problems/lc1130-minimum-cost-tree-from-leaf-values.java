1130. Minimum Cost Tree From Leaf Values - Medium

Given an array arr of positive integers, consider all binary trees such that:

Each node has either 0 or 2 children;
The values of arr correspond to the values of each leaf in an in-order traversal of the tree.  (Recall 
that a node is a leaf if and only if it has 0 children.)

The value of each non-leaf node is equal to the product of the largest leaf value in its left and right 
subtree respectively.

Among all possible binary trees considered, return the smallest possible sum of the values of each 
non-leaf node.  It is guaranteed this sum fits into a 32-bit integer.

 

Example 1:

Input: arr = [6,2,4]
Output: 32
Explanation:
There are two possible trees.  The first has non-leaf node sum 36, and the second has non-leaf node 
sum 32.

    24            24
   /  \          /  \
  12   4        6    8
 /  \               / \
6    2             2   4
 

Constraints:

2 <= arr.length <= 40
1 <= arr[i] <= 15
It is guaranteed that the answer fits into a 32-bit signed integer (ie. it is less than 2^31).

******************************************************
key:
	- DP, greedy, dfs, stack
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1: stack

method:

	- When we build a node in the tree, we compared the two numbers a and b. In this process, the 
	  smaller one is removed and we will not use it anymore, and the bigger one actually stays.

	- each iteration actually removes a local minimum value.
		For elements: arr[i - 1], arr[i], arr[i + 1],  we assume arr[i] is the local min.
		The product added to final result is arr[i] * min(arr[i - 1], arr[i + 1])
 
	- Thus, the problem = removing all local minium values while finding the first greater element 
	  			          on the left and on the right.

		Given an array A, choose 2 neighbors in the array a and b,
		we can remove the smaller one min(a,b) and the cost is a * b.

		Q: What is the minimum cost to remove the whole array until only one left?
			To remove the number a, it needs a cost a * b, where b >= a.
			minimize this cost = minimize b.

		b has two candidates, the first bigger number on the left, the first bigger number on the right.

		The cost to remove a is 【 a * min(left, right) 】


	- The problem reduces to:
		find the next greater element in the array, on the left and one right.
		Refer to the problem (503. Next Greater Element II)
		[https://leetcode.com/problems/next-greater-element-ii/discuss/98270/JavaC++Python-Loop-Twice]


stats:

	- Time O(N) for one pass
	- Space O(N) for stack in the worst cases
	- Runtime: 1 ms, faster than 96.44% of Java online submissions for Minimum Cost Tree From Leaf Values.
	- Memory Usage: 34.3 MB, less than 100.00%


    public int mctFromLeafValues(int[] A) {
        int res = 0, 
        	n = A.length;
        Stack<Integer> stack = new Stack<>();
        stack.push(Integer.MAX_VALUE);

        for (int a : A) {

            // if value in stack is less than current value, remove it from stack, take the min
            // of current val(arr[i]) & next value in stack (arr[i-1])
            while (stack.peek() <= a) {
                int mid = stack.pop();
                res += mid * Math.min(stack.peek(), a);
            }
            stack.push(a);
        }
        while (stack.size() > 2) {
            res += stack.pop() * stack.peek();
        }
        return res;
    }


[6,2,4]

a = 6 --> stack: 6, MAX_VALUE
a = 2 --> b/c 6 > a,
		  stack: 2, 6, MAX_VALUE
a = 4 --> b/c 2 < 4, mid = 2
		  res = 0 + 2 * min(6, 4) = 8
		  Here we delete 2 as the min, and add the cost to cumulative cost.

		  stack: 4, 6, MAX_VALUE

get into the while loop since stack since stack.size = 3 >2
	--> res = 8 + 4 * 6 = 8 + 24 = 32

=======================================================================================================
method 2: greedy

Pick up the leaf node with minimum value.
Combine it with its inorder neighbor which has smaller value between neighbors.
Once we get the new generated non-leaf node, the node with minimum value is useless (For the new 
    generated subtree will be represented with the largest leaf node value.)
Repeat it until there is only one node.

classSolution:
    def mctFromLeafValues(self, arr: List[int]) -> int:
        res = 0
        while len(arr) > 1:
            mini_idx = arr.index(min(arr))
            if 0 < mini_idx < len(arr) - 1:
                res += min(arr[mini_idx - 1], arr[mini_idx + 1]) * arr[mini_idx]
            else:
                res += arr[1 if mini_idx == 0 else mini_idx - 1] * arr[mini_idx]
            arr.pop(mini_idx)
        return res


public int mctFromLeafValues(int[] a) {
        List<Integer> list = new ArrayList<>();
        for(int v : a)
            list.add(v);
        int sum = 0;
        while(list.size() > 1){
            int minIdx = 0;
            for(int i=1; i < list.size(); i++)
                if(list.get(i) < list.get(minIdx))
                    minIdx = i;
            int leftIdx = minIdx-1, rightIdx = minIdx+1; 
            if(leftIdx < 0)
                sum += list.get(minIdx)*list.get(rightIdx);
            else if(rightIdx == list.size())
                sum += list.get(minIdx)*list.get(leftIdx);
            else{
                if(list.get(leftIdx) < list.get(rightIdx))
                    sum += list.get(minIdx)*list.get(leftIdx);
                else
                    sum += list.get(minIdx)*list.get(rightIdx);
            }
            list.remove(minIdx);
        }
        return sum;
    }
=======================================================================================================
method 2:

method:

	-  dfs 
	- 

stats:

	- 
	- 
class Solution {
    public int mctFromLeafValues(int[] arr) {
        int n = arr.length;
        int[][] dp = new int[n][n];
        return dfs(arr, 0, n - 1, dp);
    }
    
    public int dfs(int[] arr, int s, int e, int[][] dp) {
        if (s == e) 
        	return 0;

        if (dp[s][e] > 0) 
        	return dp[s][e];

        int ans = Integer.MAX_VALUE;
        for (int i = s; i < e; i++) {
        	int maxLeft = 0, 
                maxRight = 0;

            int left = dfs(arr, s, i, dp);
            int right = dfs(arr, i + 1, e, dp);

            for (int j = s; j <= i; j++) 
            	maxLeft = Math.max(maxLeft, arr[j]);

            for (int j = i + 1; j <= e; j++) 
            	maxRight = Math.max(maxRight, arr[j]);

            ans = Math.min(ans, left + right + maxLeft * maxRight);
        }

        dp[s][e] = ans;
        return ans;
    }
}
=======================================================================================================
method 3:

method:

	- dp
	- 

stats:

	- 
	- 

class Solution {
    public int mctFromLeafValues(int[] arr) {
        int[][] dp = new int[arr.length][arr.length];

        // max[i][j] store max of interval from index i to j
        int[][] max = new int[arr.length][arr.length];
        for(int i = 0; i < arr.length; i ++) {
            int localMax = 0;
            for(int j = i; j < arr.length; j ++) {
                if(arr[j] > localMax) {
                    localMax = arr[j];
                }
                max[i][j] = localMax;
            }
        }


        for(int len = 1; len < arr.length; len ++) {
            for(int left = 0; left + len < arr.length; left ++) {
                int right = left + len;
                dp[left][right] = Integer.MAX_VALUE;
                
                // neighbors, have to multiple it
                if(len == 1) {
                    dp[left][right] = arr[left]*arr[right];
                } 

                else {
                    // go through all possible intervals between left & right
                    for(int k = left; k < right; k ++) {
                        dp[left][right] = Math.min(dp[left][right], 
                        	             dp[left][k] + dp[k+1][right] + max[left][k]*max[k+1][right]);
                    }
                }
            }
        }
        return dp[0][arr.length-1];
    }
}

