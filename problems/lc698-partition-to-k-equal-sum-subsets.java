698. Partition to K Equal Sum Subsets - Medium

Given an array of integers nums and a positive integer k, find whether its possible to 
divide this array into k non-empty subsets whose sums are all equal.


Example 1:

Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
Output: True
Explanation: Its possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
 

Note:

1 <= k <= len(nums) <= 16.
0 < nums[i] < 10000.


******************************************************
key:
	- backtrack & sort
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: recursively

Method:

	- we keep an array for sum of each partition and a boolean array to check whether an element 
	  is already taken into some partition or not.
	- If sum of array is not divisible by K, then it is not possible to divide the array. 
	  We will proceed only if k divides sum. Our goal reduces to divide array into K parts where 
	  sum of each part should be array_sum/K
	
	-  base cases:
		- If K is 1, then done, complete array is only subset with same sum.
		- If N < K, then it is not possible to divide array into subsets with equal sum, because 
		  we can’t divide the array into more than N parts.
		
	-  tries to add array element into some subset. If sum of this subset reaches required sum, 
	   we iterate for next part recursively, otherwise we backtrack for different set of elements. 
	-  If number of subsets whose sum reaches the required sum is (K-1), we flag that it is possible 
	   to partition array into K parts with equal sum, because remaining elements already have a 
	   sum equal to required sum.


Stats:

	- Time complexity: O(k * 2^n), at least it's an upper bound. Because it takes the inner recursion
	       2^n time to find a good subset. Once the 1st subset is found, we go on to find the second,
	       which would take 2^n roughly (because some numbers have been marked as visited). 
	       So T = 2^n + 2^n + 2^n + ... = k * 2^n.
	- 

 	public boolean canPartitionKSubsets(int[] A, int k) {
        if (k > A.length) 
        	return false;
        int sum = 0;
        for (int num : A) 
        	sum += num;

        // can't divide into k subsets
        if (sum % k != 0) 
        	return false;

        boolean[] visited = new boolean[A.length];
        Arrays.sort(A);
        return dfs(A, 0, A.length - 1, visited, sum / k, k);
    }

    public boolean dfs(int[] A, int sum, int st, boolean[] visited, int target, int round) {
        if (round == 0) 
        	return true;

        // found a subset, reduce round by 1
        if (sum == target && dfs(A, 0, A.length - 1, visited, target, round - 1))
            return true;

        for (int i = st; i >= 0; --i) {
            if (!visited[i] && sum + A[i] <= target) {
                visited[i] = true;

                // add A[i] to the current subset's sum, and move to the next item (st--)
                if (dfs(A, sum + A[i], i - 1, visited, target, round))
                    return true;
                visited[i] = false;
            }
        }
        return false;
    }


=======================================================================================================
method 2: DP

Method:

	-  exhaustive search, and find target = sum(nums) / k in the same way.

	-  Let used have the i-th bit set if and only if nums[i] has already been used. Our goal is 
	   to use nums in some order so that placing them into groups in that order will be valid. 
	   search(used, ...) will answer the question: can we partition unused elements of nums[i]
	   appropriately?

	-  This will depend on todo, the sum of the remaining unused elements, not crossing multiples 
	   of target within one number. If for example our target is 10, and our elements to be placed 
	   in order are [6, 5, 5, 4], this would not work as 6 + 5 "crosses" 10 prematurely.

		If we could choose the order, then after placing 5, our unused elements are [4, 5, 6]. Using 
		6 would make todo go from 15 to 9, which crosses 10 - something unwanted. However, we could 
		use 5 since todo goes from 15 to 10; then later we could use 4 and 6 as those placements do 
		not cross.

		It turns out the maximum value that can be chosen so as to not cross a multiple of target, 
		is targ = (todo - 1) % target + 1. This is essentially todo % target, plus target if that 
		would be zero.

		Now for each unused number that doesn't cross, we'll search on that state, and we'll return
		true if any of those searches are true.

		Notice that the state todo depends only on the state used, so when memoizing our search, 
		we only need to make lookups by used.

	-	


Stats:

	- 
	- 

class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int N = nums.length;
        Arrays.sort(nums);
        int sum = Arrays.stream(nums).sum();
        int target = sum / k;
        if (sum % k > 0 || nums[N - 1] > target) 
        	return false;

        boolean[] dp = new boolean[1 << N];
        dp[0] = true;
        int[] total = new int[1 << N];

        for (int state = 0; state < (1 << N); state++) {
            if (!dp[state]) continue;
            for (int i = 0; i < N; i++) {
                int future = state | (1 << i);
                if (state != future && !dp[future]) {
                    if (nums[i] <= target - (total[state] % target)) {
                        dp[future] = true;
                        total[future] = total[state] + nums[i];
                    } else {
                        break;
                    }
                }
            }
        }
        return dp[(1 << N) - 1];
    }
}

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



