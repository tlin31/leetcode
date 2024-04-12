Google--interview:

1. Range Maximum Query with Node Update
	-- Segment_tree.md

2. Split array into groups of EXACTLY W consecutive numbers.
	-- 846. Hand of Straights
   	-- rearrange the cards into groups so that each group is size W, and consists of EXACTLY W consecutive cards.
	-- Return true if and only if she can.
	-- TreeMap, delete one by one

3. Split array into groups of AT LEAST K consecutive numbers
	-- 659. Split Array into Consecutive Subsequences 
	-- Given an array nums sorted in ascending order, return true if and only if you can split it into 1 or more subsequences such that each subsequence consists of consecutive integers and has length AT LEAST 3.


4. Find max sum path in tree (from root)
 	-- geeks-find-the-maximum-sum-path-in-a-binary-tree
 	-- use recursion, store the deepest leaf node, store a max_so_far
 	-- use print function (leaf node, root)

 	|23|[113. Path Sum II exactly = k](https://leetcode.com/problems/path-sum-ii/)|[Java](problems/lc113-path-sum-ii.java)|| binary tree, dfs, start from root & end at leaf | Medium
	|24|[437. Path Sum III exactly = k](https://leetcode.com/problems/path-sum-iii/)|[Java](problems/lc437-path-sum-iii.java)|| ！！！prefix sum hashmap, start & end at any node | Easy

5. find in matrix, sum < k的rectangle
	-- 363. Max Sum of Rectangle No Larger Than K
	-- Given a non-empty 2D matrix matrix and an integer k, find the max sum of a rectangle in the matrix such that its sum is no larger than k.
	-- tree set 
	-- similar: 1074

6. longest subarray with sum < k
	--
	```java
  	public static int getMaximumOutfits(List<Integer> outfits, int money) {
        int curSum = 0; 
        int length = 0, 
            maxlength = 0;
        for (int i = 0; i < outfits.size(); i++) { 
            if ((curSum + outfits.get(i)) <= money) { 
                curSum += outfits.get(i);  
                length++; 
            }  
            else if (curSum!=0) {
                // sliding window
                firstNum = outfits.get(i - length);
                // need to + outfits.get(i) to ensure continous subarray
                curSum = curSum - firstNum + outfits.get(i); 
            }
            maxlength = Math.max(length, maxlength);  
        } 
        return maxlength; 
    }
	```



7. shortest time take to get to furtherst node in a graph
	-- 743. Network Delay Time - Medium
	-- dijkstra



8. max sum of node in binary tree with no adjacent nodes included (can't choose children/parent)
	-- Geeks: geeks-maximum-sum-nodes-binary-tree-no-two-adjacent.java
	-- use hashamp to store node & sum of that node --> dynamic programming

9. 给list of 点，每个点有x，y坐标，求最大的凸边形的上边缘。
	-- Geeks: find convex
	-- geeks-convex-hull-set-1-jarviss-algorithm-or-wrapping.java
	-- O(n^2) or O(nlogn)

10. find difference for each pair in array
	-- Geeks - Sum of absolute differences of all pairs in a given array
	-- sum = sum + (i*a[i]) – (n-1-i)*a[i].

11. output closest next greater element in the array
	-- Geeks - Next Greater Element
	-- use stack 

12. split array into subarrays min(max(sum of subarray))
	-- 410. Split Array Largest Sum - Hard
	-- Given an array which consists of non-negative integers and an integer m, you can split the array 
		into m non-empty continuous subarrays. Write an algorithm to minimize the largest sum among these 
		m subarrays.
	-- use dp: dp[s,j] is the solution for splitting subarray n[j]...n[L-1] into s parts.
			   dp[s+1,i] = min{ max(dp[s,j], n[i]+...+n[j-1]) }, i+1 <= j <= L-s


13. largest sum subarray (with negative values)
	-- 53. Maximum Subarray - Easy
	-- Given an integer array nums, find the contiguous subarray (containing at least 
		one number) which has the largest sum and return its sum.
	-- 用一个一维数组 dp [ i ] 表示以下标 i 结尾的子数组的元素的最大的和，也就是这个子数组最后一个元素
		是下边为 i 的元素，并且这个子数组是所有以 i 结尾的子数组中，和最大的。
	-- 如果 dp [ i - 1 ] < 0，那么 dp [ i ] = nums [ i ]。
	-- 果 dp [ i - 1 ] >= 0，那么 dp [ i ] = dp [ i - 1 ] + nums [ i ]。


14. Matrix with 0 and 1, flip one 1 to 0
	-- 给一个matrix,里面全是0,1。 0可以走， 1不能走，叫找到左上到右下能否走到，我内心狂喜想着这不是BFS吗
	-- 真正题目是把其中一个1 flip 成 0， 求flip哪一个可以找到最短距离。所以是遍历所有1，然后对起点和终点分别BFS找这个点。

15. Count num of squares in matrix (1x1, 2x2, ...)
	-- 1277. Count Square Submatrices with All Ones - Medium
	-- 	A[i][j] = min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1 is the maximum size of square that we can find.
	-- res += A[i][j]; return res

	-- 221. maximal square (size)
	--  b[i][j] = Math.min(Math.min(b[i][j-1], b[i-1][j-1]), b[i-1][j]) + 1;
	-- result = Math.max (b[i][j], result), return result * result = area size


16. maintain order in matrix, but replace to smaller number
	-- 一个2d matrix，每行每列有数字，都不相同，然后让重新编辑，就是每个数字换成一个新的正整数，要求保持行列顺序，且用到的正整数尽可能小。记得之前地里出现过，就是拓扑排序
	-- 每行每列 从较小的数到较大的有一条有向边，然后就是常规tp sort

	-- 比如
		[[6, 4, 10],
		  [3, 5, 20]]
		变成
		[[2, 1, 3],
		[1, 2, 4]]
		每一行每一列原来的大小关系不变，用的数字尽可能小

	-- note: when 2 nodes all have 0 outgoing edge at one point, assign them as the same value

17. missing ranges
	-- lc 163


18. number of closed islands (matrix with 0 & 1, island = place entirely surronded by 1's)
	-- lc 1254
	-- flood fill the edge, then dfs

19. 793. Preimage Size of Factorial Zeroes Function - Hard
	-- find pattern --> only check muptilples of 5
	-- binary search


20. in infinite boards, find min steps for a knight to move to a position
	-- lc1197-minimum-knight-moves.java
	-- bfs (hash into the set/or use matrix)

21. match string with pattern. ex. "hello" -> "heeellooo", "hi" -> "hiiii
	-- 809. Expressive Words - Medium
	-- 2 pointers (compress duplicates)

22. longest substr with at most k 不同char
	-- 340. Longest Substring with At Most K Distinct Characters 

23. min distance to all amenities
	-- Google-block-with-min-distance 
	-- Assume you are looking to move, and have a set of amenities that you want to have easy access to 
		from your new home. You have found a neighborhood you like, each block of which has zero or more 
		amenities.

24. Google - 2 string, cut to make palindrome
	-- google-cut-str-to-palindrome.java
	-- follow up: can be at different places


25. print multiply of (some number) x and y
	-- 264. Ugly Number II - Medium
	-- Write a program to find the n-th ugly number.
	-- Ugly numbers are positive numbers whose prime factors only include 2, 3, 5. 




========
follow up，array换成matrix，找最大边长的正方形，使得正方形内部数字求和小于等于K，数字都是non-negative，两个for循环，不断更新最大边长解决。
sum部分可以通过保存每个点到左上角的求和来O(1)时间计算sum。


 









