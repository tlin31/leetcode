1703. Minimum Adjacent Swaps for K Consecutive Ones
Solved
Hard
Topics
conpanies icon
Companies
Hint
You are given an integer array, nums, and an integer k. nums comprises of only 0's and 1's. In one move, you can choose two adjacent indices and swap their values.

Return the minimum number of moves required so that nums has k consecutive 1's.

 

Example 1:

Input: nums = [1,0,0,1,0,1], k = 2
Output: 1
Explanation: In 1 move, nums could be [1,0,0,0,1,1] and have 2 consecutive 1's.
Example 2:

Input: nums = [1,0,0,0,0,0,1,1], k = 3
Output: 5
Explanation: In 5 moves, the leftmost 1 can be shifted right until nums = [0,0,0,0,0,1,1,1].
Example 3:

Input: nums = [1,1,0,1], k = 2
Output: 0
Explanation: nums already has 2 consecutive 1's.
 

Constraints:

1 <= nums.length <= 105
nums[i] is 0 or 1.
1 <= k <= sum(nums)


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

	-	



Stats:

	- 
	- 


	import java.util.*;

class Solution {
    public int minMoves(int[] nums, int k) {
        List<Long> pos = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                // Transform index to remove the "adjacency" gap requirement
                pos.add((long) i - pos.size());
            }
        }

        int n = pos.size();
        long[] pref = new long[n + 1];
        for (int i = 0; i < n; i++) pref[i + 1] = pref[i] + pos.get(i);

        long minOps = Long.MAX_VALUE;
        for (int i = 0; i <= n - k; i++) {
            int left = i, right = i + k - 1;
            int mid = left + (right - left) / 2;
            long median = pos.get(mid);
            
            // Sum of distances to median in the transformed space
            long leftSum = median * (mid - left) - (pref[mid] - pref[left]);
            long rightSum = (pref[right + 1] - pref[mid + 1]) - median * (right - mid);
            
            minOps = Math.min(minOps, leftSum + rightSum);
        }

        return (int) minOps;
    }
}

/**

Find all index of nums[i] if nums[i] == 1.
Now the problem changes to,
find k consecute element in A,
that has minimum distance to their median sequence.
To solve this, we need to use the prefix sum of A,
which is B in this solution.


Time O(n)
Space O(n)
 */