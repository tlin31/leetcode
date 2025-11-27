1143. Longest Common Subsequence - Medium

Given two strings text1 and text2, return the length of their longest common subsequence.

A subsequence of a string is a new string generated from the original string with some characters
(can be none) deleted without changing the relative order of the remaining characters. (eg, "ace" is 
a subsequence of "abcde" while "aec" is not). 

A common subsequence of two strings is a subsequence that is common to both strings.

If there is no common subsequence, return 0.

 

Example 1:

Input: text1 = "abcde", text2 = "ace" 
Output: 3  
Explanation: The longest common subsequence is "ace" and its length is 3.

Example 2:

Input: text1 = "abc", text2 = "abc"
Output: 3
Explanation: The longest common subsequence is "abc" and its length is 3.

Example 3:

Input: text1 = "abc", text2 = "def"
Output: 0
Explanation: There is no such common subsequence, so the result is 0.
 

Constraints:

1 <= text1.length <= 1000
1 <= text2.length <= 1000
The input strings consist of lowercase English characters only.

******************************************************
key:
	- DP
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
Method 1: Recursion


Stats:

	- time complexity - O(2^(m+n))
	- space complexity - O(m+n)


Method:
	-	recursive solution(Top- down approach)

	public static int LCSM1(char[] X, char[] Y, int i, int j) {
		if (i <= 0 || j <= 0)
			return 0;
		if (X[i - 1] == Y[j - 1])
			return 1 + LCSM1(X, Y, i - 1, j - 1);
		else
			return Math.max(LCSM1(X, Y, i, j - 1), LCSM1(X, Y, i - 1, j));

	}

-------------------
Stats:

	- time complexity - O(m*n)
	- space complexity - O(m*n)


Method:

	-	Method2()- recursive solution with memoization

	public static int LCSM2(char[] X, char[] Y, int i, int j, Integer[][] dp) {
		if (i <= 0 || j <= 0)
			return 0;

		if (dp[i][j] != null)
			return dp[i][j];

		if (X[i - 1] == Y[j - 1])
			return 1 + LCSM2(X, Y, i - 1, j - 1, dp);
		else
			return dp[i][j] = Math.max(LCSM2(X, Y, i, j - 1, dp), LCSM2(X, Y, i - 1, j, dp));

	}
=======================================================================================================
method 2: DP

Stats:

	- time complexity - O(m*n)
	- space complexity - O(m*n)


Method:

	-  DP solution(Bottom up approach)
	
 	public int longestCommonSubsequence(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i < s1.length(); ++i)
            for (int j = 0; j < s2.length(); ++j)

                if (s1.charAt(i) == s2.charAt(j)) 
                	dp[i + 1][j + 1] = 1 + dp[i][j];

                else 
                	dp[i + 1][j + 1] =  Math.max(dp[i][j + 1], dp[i + 1][j]);

        return dp[s1.length()][s2.length()];
    }

---------------------------------
DP solution(Bottom up approach)

	- time complexity - O(m*n)
	- space complexity - O(n)

	public static int LCSM4(char[] X, char[] Y, int m, int n) {
		int memo[] = new int[n + 1];

		for (int i = 1; i <= m; i++) {
			int prev = 0;
			for (int j = 1; j <= n; j++) {
				int temp = memo[j];
				if (X[i - 1] == Y[j - 1]) {
					memo[j] = prev + 1;
				} else {
					memo[j] = Math.max(memo[j], memo[j - 1]);
				}
				prev = temp;
			}

		}
		return memo[n];
	}

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



