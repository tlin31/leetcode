410. Split Array Largest Sum - Hard

Given an array which consists of non-negative integers and an integer m, you can split the array 
into m non-empty continuous subarrays. Write an algorithm to minimize the largest sum among these 
m subarrays.

Note:
If n is the length of array, assume the following constraints are satisfied:

1 ≤ n ≤ 1000
1 ≤ m ≤ min(50, n)


Examples:
Input:
nums = [7,2,5,10,8]
m = 2

Output:
18

Explanation:
There are four ways to split nums into two subarrays.
The best way is to split it into [7,2,5] and [10,8],
where the largest sum among the two subarrays is only 18.

Example 2:

Input: nums = [1,2,3,4,5], k = 2
Output: 9
Explanation: There are four ways to split nums into two subarrays.
The best way is to split it into [1,2,3] and [4,5], where the largest sum among the two subarrays is only 9.

******************************************************
key:
	- binary search --> on value
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

🟢 单调性

如果允许的“最大子数组和”上限 cap 很小，可能需要很多段（分得很碎），未必能凑成正好 m 段。

如果允许的 cap 很大，就能用较少的段完成划分。

所以：

cap 越小 → 需要的段数越多
cap 越大 → 需要的段数越少


👉 这是 单调性，可以用 二分搜索。


🟢 二分范围

最小值：max(nums) （至少能装下最大的单个元素）。

最大值：sum(nums) （把所有数作为一个子数组）。


🟢 检查函数（贪心切分）

给定一个 cap，模拟切分：

从左往右累加，如果超过 cap，就切一刀，新开一段；

统计总段数 count；

如果 count > m，说明 cap 太小；否则可行。


	- binary search on value ( find possible min(max subarray) )
    - 
	- The answer is between maximum value of input array numbers and sum of those numbers.

    = Use binary search to approach the correct answer. 
      We have l = max number of array; 
      r = sum of all numbers in the array; Every time we do mid = (l + r) / 2;

	- Use greedy to narrow down left and right boundaries in binary search.
		3.1 Cut the array from left.
		3.2 Try our best to make sure that the sum of numbers between each two cuts (inclusive) 
		    is large enough but still less than mid.
		3.3 end up with two results (with function valid()): 
			1. we can divide the array into more than m subarrays
				- it means that the mid value we pick is too small because we have already tried our 
				  best to make sure each part holds as many non-negative numbers as we can but we 
				  still have numbers left. 
				- So, it is impossible to cut the array into m parts and make sure each parts is no 
				  larger than mid. We should increase m. This leads to l = mid + 1;
			
			2. if valid() return true --> we can divide, push the right boundary to achieve minimum sum.
				- it is either we successfully divide the array into m parts and the sum of each part 
				  is less than mid, or we used up all numbers before we reach m. 
				- Both of them mean that we should lower mid because we need to find the minimum one. 
				  This leads to r = mid - 1;

stats:

	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Split Array Largest Sum.
	- Memory Usage: 34.3 MB, less than 100.00% of Java online submissions for Split Array Largest Sum.



public class Solution {
    public int splitArray(int[] nums, int m) {
        int max = 0; 
        long sum = 0;

        for (int num : nums) {
            max = Math.max(num, max);
            sum += num;
        }


        if (m == 1) 
        	return (int)sum;
        
        // binary search
        // left = max of the subarray
        long l = max; long r = sum;
        while (l <= r) {
            long mid = (l + r)/ 2;

            // we want a minimize the sum
            if (valid(mid, nums, m)) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return (int)l;
    }

    public boolean valid(long target, int[] nums, int m) {
        int count = 1;
        long sumSoFar = 0;
        for(int num : nums) {
            sumSoFar += num;

            // keep adding the next one until it exceeds target, need to make a cut (count++)
            if (sumSoFar > target) {
                total = num;
                count++;

                // when we need to more cut than required, means can't split
                if (count > m) {
                    return false;
                }
            }
        }
        return true;
    }
}

Output:

mid = 21 l = 10 r = 20
mid = 15 l = 16 r = 20
mid = 18 l = 16 r = 17
mid = 16 l = 17 r = 17
mid = 17 l = 18 r = 17


=======================================================================================================
method 2:

method:

	- DP 
	- 

stats:

	- 
dp[s,j] is the solution for splitting subarray n[j]...n[L-1] into s parts.

dp[s+1,i] = min{ max(dp[s,j], n[i]+...+n[j-1]) }, i+1 <= j <= L-s

This solution does not take advantage of the fact that the numbers are non-negative (except to break the 
inner loop early). That is a loss. 
(On the other hand, it can be used for the problem containing arbitrary numbers)

public int splitArray(int[] nums, int m)
{
    int L = nums.length;
    int[] S = new int[L+1];

    // S is the prefix-sum array
    S[0]=0;
    for(int i=0; i<L; i++)
        S[i+1] = S[i]+nums[i];

    int[] dp = new int[L];
    for(int i=0; i<L; i++)
        dp[i] = S[L]-S[i];

    // number of cuts
    for(int s=1; s<m; s++)
    {
        for(int i=0; i<L-s; i++)
        {
            dp[i]=Integer.MAX_VALUE;

            // make the cut
            for(int j=i+1; j<=L-s; j++)
            {
                int t = Math.max(dp[j], S[j]-S[i]);
                if(t<=dp[i])
                    dp[i]=t;
                else
                    break;
            }
        }
    }

    return dp[0];
}

=======================================================================================================
method 3:

method:

	- Backtrack
	- 

stats:

	- 
	- 

dfs + memorization


class Solution {
    public int splitArray(int[] nums, int m) {
        int n = nums.length;
        int[] presum = new int[n+1];
        presum[0] = 0;
        
        for (int i = 1; i <= n; i++) {
            presum[i] += nums[i-1] + presum[i-1];
        }
        
        int[][] visited = new int[n][m+1];
        return dfs(0, m, nums, presum, visited);
    }
    
    private int dfs(int start, int m, int[] nums, int[] presum, int[][] visited) {
        if (m == 1) {
            return presum[nums.length] - presum[start];
        }
        
        if (visited[start][m] != 0) {
            return visited[start][m];
        }
        
        int maxSum = Integer.MAX_VALUE;
        
        for (int i = start; i < nums.length-1; i++) {
            //左边的array sum
            int leftArraySum = presum[i+1] - presum[start];

            int rightIntervalMax = dfs(i+1, m-1, nums, presum, visited);
            maxSum = Math.min(maxSum, Math.max(leftArraySum, rightIntervalMax));
            
        }
        
        visited[start][m] = maxSum;
        return maxSum;
    }
}
