300. Longest Increasing Subsequence - Medium

Given an unsorted array of integers, find the length of longest increasing subsequence.

Example:

Input: [10,9,2,5,3,7,101,18]
Output: 4 
Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4. 
Note:

There may be more than one LIS combination, it is only necessary for you to return the length.
Your algorithm should run in O(n2) complexity.

******************************************************
key:
	- DP.
	- edge case:
		1) empty string, return empty
		2)

******************************************************



解法一： DP， 时间复杂度 O(N^2)

    dp[i] 表示以 nums[i] 这个数结尾的最长递增子序列的长度。
    比如nums = [1,4,3,4,2,3], 则dp【5】 = 3，因为index 5结尾的最长递增子序列是【1，2,3】
    既然是递增子序列，我们只要找到前面那些结尾⽐3 小的⼦序列，然后把 3 接到最后，就是一个新的递增子序列
    显然，可能形成很多种新的子序列，但是我们只选择最长的那⼀个，把最⻓子序列的长度作为 dp[5] 的值即可。

    for (int j = 0; j < i; j++) {
        if (nums[i] > nums[j])
            dp[i] = Math.max(dp[i], dp[j] + 1);
    }



public int lengthOfLIS(int[] nums) { 
    int[] dp = new int[nums.length]; 

    // base case:dp 数组全都初始化为 1 
    Arrays.fill(dp, 1);
    
    for (int i = 0; i < nums.length; i++) {
        for (int j = 0; j < i; j++) {
            if (nums[i] > nums[j])
                dp[i] = Math.max(dp[i], dp[j] + 1);
        } 
    }
    int res = 0;
    for (int i = 0; i < dp.length; i++) {
        res = Math.max(res, dp[i]);
    }
    return res; 
}


解法二： binary search， 时间复杂度 O(N*log N)

labuladong+的算法小抄（零一二）.pdf @ Page 215

    patience sorting(耐心排序)
    假设数组是一堆纸牌：
    只能把点数小的牌压到点数比它大的牌上;如果当前牌点数较大没有可以放置的堆，则新建一个堆，把
    这张牌放进去;如果当前牌有多个堆可供选择，则选择最左边的那一堆放置。

    因为牌堆顶的牌是有序的，所以能用二分查找来搜索当前牌应放置的位置。

    按照上述规则执行，可以算出最长递增子序列，牌的堆数就是最长递增子序列的长度

public int lengthOfLIS(int[] nums) { 
    int[] top = new int[nums.length]; 
    // 牌堆数初始化为 0
    int piles = 0;

    for (int i = 0; i < nums.length; i++) { 
        // 要处理的扑克牌
        int poker = nums[i];
        /***** 搜索左侧边界的⼆分查找 *****/ 
        int left = 0, right = piles; 
        while (left < right) {
            int mid = (left + right) / 2;
            if (top[mid] > poker) {
                right = mid;
            } else if (top[mid] < poker) {
                left = mid + 1;
            } else {
                right = mid; 
            }
        }
        /*********************************/
        // 没找到合适的牌堆，新建⼀一堆 
        if (left == piles) piles++; 
            // 把这张牌放到牌堆顶 
            top[left] = poker;
    }
    // 牌堆数就是 LIS ⻓长度
    return piles;
}


=======================================================================================================
method 1:

method:

	- DP
	- Let arr[0..n-1] be the input array and L(i) be the length of the LIS ending at index i such 
	  that arr[i] is the last element of the LIS.

	Then, L(i) can be recursively written as:
			L(i) = 1 + max( L(j) ) where 0 < j < i and arr[j] < arr[i]; or
			L(i) = 1, if no such j exists.

	To find the LIS for a given array, we need to return max(L(i)) where 0 < i < n.

stats:

	- O(n^2)
	- 

class Solution {
    public int lengthOfLIS(int[] nums) {
        int length = nums.length;
        int max = 0;
        
        //memo[i] = longest subseq ending at i
        int[] memo = new int[length];
        for (int i = 0; i < length; i++) {
            memo[i] = 1;
        }
        
        // i = ending index 
        for (int i = 1; i < length; i++) {
            
            // j = start index
            for(int j = 0; j<i; j++) {

            	// increasing seq AND generate longer sequence
                if (nums[i] > nums[j] && memo[i] < memo[j] + 1) {
                    memo[i] = memo[j]+1; 
                }
            }
            
        }
        
        for ( int i = 0; i < length; i++ ) 
              if ( max < memo[i] ) 
                 max = memo[i]; 
  
        
        return max;
    }
}


=======================================================================================================
method 2:

method:
    - kind of greedy --> always want to store the smallest ones
	- tails is an array storing the smallest tail of all increasing subsequences with length i+1 in 
	  tails[i].
	- Each time we only do one of the two:

		(1) if x is larger than all tails, append it, increase the size by 1
		(2) if tails[i-1] < x <= tails[i], update tails[i], because we always want a smaller num

stats:

	- O(n logn )
	- 

https://www.geeksforgeeks.org/longest-monotonically-increasing-subsequence-size-n-log-n/


public int lengthOfLIS(int[] nums) {
    int[] tails = new int[nums.length];

    // size of the tail table
    int size = 0;
    for (int x : nums) {
        int i = 0, 
        	j = size;

        // binary search on where x should be in the tail table
        while (i != j) {
            int m = (i + j) / 2;
            if (tails[m] < x)
                i = m + 1;
            else
                j = m;
        }
        tails[i] = x;

        if (i == size) 
        	++size;
    }
    return size;
}

input: [10,9,2,5,3,7,101,18]

run 0
updates at 0 with value: 10
tail table = [10, 0, 0, 0, 0, 0, 0, 0]

run 1
updates at 0 with value: 9
tail table = [9, 0, 0, 0, 0, 0, 0, 0]
run 1
updates at 0 with value: 2
tail table = [2, 0, 0, 0, 0, 0, 0, 0]
run 1
updates at 1 with value: 5
tail table = [2, 5, 0, 0, 0, 0, 0, 0]


run 2
updates at 1 with value: 3
tail table = [2, 3, 0, 0, 0, 0, 0, 0]
run 2
updates at 2 with value: 7
tail table = [2, 3, 7, 0, 0, 0, 0, 0]

run 3
updates at 3 with value: 101
tail table = [2, 3, 7, 101, 0, 0, 0, 0]

run 4
updates at 3 with value: 18
tail table = [2, 3, 7, 18, 0, 0, 0, 0]



=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 

public class Solution {
    public int lengthOfLIS(int[] nums) {            
        int[] dp = new int[nums.length];
        int len = 0;

        for(int x : nums) {
            int i = Arrays.binarySearch(dp, 0, len, x);
            if(i < 0) 
            	i = -(i + 1);
            dp[i] = x;
            if(i == len) 
            	len++;
        }

        return len;
    }
}

