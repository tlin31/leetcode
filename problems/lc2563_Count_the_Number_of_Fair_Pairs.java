2563. Count the Number of Fair Pairs - Medium

Given a 0-indexed integer array nums of size n and two integers lower and upper, return the 
number of fair pairs.

A pair (i, j) is fair if:

0 <= i < j < n, and
lower <= nums[i] + nums[j] <= upper
 

Example 1:

Input: nums = [0,1,7,4,4,5], lower = 3, upper = 6
Output: 6
Explanation: There are 6 fair pairs: (0,3), (0,4), (0,5), (1,3), (1,4), and (1,5).
Example 2:

Input: nums = [1,7,9,2,5], lower = 11, upper = 11
Output: 1
Explanation: There is a single fair pair: (2,3).
 

Constraints:

1 <= nums.length <= 105
nums.length == n
-109 <= nums[i] <= 109
-109 <= lower <= upper <= 109


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1: 2 pointers

Method:

	-	Because nums[i] + nums[j] == nums[j] + nums[i] the i < j condition degrades to i != j.

So, we can sort the array, and use the two-pointer approach to count pairs less than a certain value.

We do it twice for uppper and lower, and return the difference.

Time complexity: O(sort)



Stats:

	- 
	- 


long countLess(int[] nums, int val) {
    long res = 0;
    for (int i = 0, j = nums.length - 1; i < j; ++i) {
        while(i < j && nums[i] + nums[j] > val)
            --j;
        res += j - i;
    }        
    return res;        
}
public long countFairPairs(int[] nums, int lower, int upper) {
    Arrays.sort(nums);
    return countLess(nums, upper) - countLess(nums, lower - 1);
}

===================================================================================================
binary search

import java.util.Arrays;

class Solution {
    public long countFairPairs(int[] v, int lower, int upper) {
        Arrays.sort(v);
        long ans = 0;
        for (int i = 0; i < v.length - 1; i++) {
            int low = lowerBound(v, i + 1, v.length, lower - v[i]);
            int up = upperBound(v, i + 1, v.length, upper - v[i]);
            ans += up - low;
        }
        return ans;
    }
  
    private int lowerBound(int[] v, int start, int end, int target) {
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (v[mid] >= target) end = mid;
            else start = mid + 1;
        }
        return start;
    }

    private int upperBound(int[] v, int start, int end, int target) {
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (v[mid] > target) end = mid;
            else start = mid + 1;
        }
        return start;
    }
}




