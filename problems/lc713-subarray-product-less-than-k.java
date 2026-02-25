713. Subarray Product Less Than K - Medium

Your are given an array of positive integers nums.

Count and print the number of (contiguous) subarrays where the product of all the elements in the 
subarray is less than k.

Example 1:
Input: nums = [10, 5, 2, 6], k = 100
Output: 8
Explanation: The 8 subarrays that have product less than 100 are: [10], [5], [2], [6], [10, 5], [5, 2], 
[2, 6], [5, 2, 6].

Note that [10, 5, 2] is not included as the product of 100 is not strictly less than k.
Note:

0 < nums.length <= 50000.
0 < nums[i] < 1000.
0 <= k < 10^6.

******************************************************
key:
	- binary search or sliding window
	- !!! sliding window, everytime add (j+1-i) 个 new subarrays
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:
	- 	sliding window
	- 	keep an max-product-window less than K
		Every time shift window by adding a new number on the right(j), if the product is greater than k,
		then try to reduce numbers on the left(i), until the subarray product fit less than k again, 
		(subarray could be empty);

		Each step introduces x 个new subarrays, where x is the size of the current window (j + 1 - i);

			for window (5, 2), when 6 is introduced, it add 3 new subarray: (5, (2, (6)))
			        (6)
			     (2, 6)
			  (5, 2, 6)

stats:
	- Time Complexity: O(N), where N is the length of nums. left can only be incremented at most N times.
	- Space Complexity: O(1), the space used by prod, left, and ans.

	- Runtime: 10 ms, faster than 29.44% of Java online submissions for Subarray Product Less Than K.
	- Memory Usage: 51.8 MB, less than 100.00%


class Solution {
	public int numSubarrayProductLessThanK(int[] nums, int k) {
	    // 1. 边界处理 (Edge Case: k <= 1, as nums are positive, product can't be < 1)
	    if (k <= 1) return 0;

	    int count = 0;
	    int prod = 1;
	    int left = 0;

	    for (int right = 0; right < nums.length; right++) {
	        // 2. 进窗 (Expand Window)
	        prod *= nums[right];

	        // 3. 缩窗 (Shrink Window)
	        while (prod >= k) {
	            prod /= nums[left];
	            left++;
	        }

	        // 4. 批量计数 (Batch Counting)
	        // 关键逻辑：新增的子数组个数等于当前窗口的长度
	        count += (right - left + 1);
	    }

	    return count;
	}

}



=======================================================================================================
method 2:

method:
	- 	Because log(∏ Xi)=∑_i log(Xi), we can reduce the problem to subarray sums instead of subarray 
		products. The motivation for this is that the product of some arbitrary subarray may be way too 
		large (potentially 1000^50000), and also dealing with sums gives us some more familiarity as 
		it becomes similar to other problems we may have solved before.

	- 	After this transformation where every value x becomes log(x), let us take prefix sums 
		prefix[i+1] = nums[0] + nums[1] + ... + nums[i]. Now we are left with the problem of finding, 
		for each i, the largest j so that nums[i] + ... + nums[j] = prefix[j] - prefix[i] < k.

	- 	Because prefix is a monotone increasing array, this can be solved with binary search. We add the 
		width of the interval [i, j] to our answer, which counts all subarrays [i, k] with k <= j.


stats:

	- Time Complexity: O(NlogN), where N is the length of nums. 
	 				   Inside our for loop, each binary search operation takes O(logN) time.

	- Space Complexity: O(N), the space used by prefix.



class Solution {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k == 0) return 0;
        double logk = Math.log(k);
        double[] prefix = new double[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefix[i+1] = prefix[i] + Math.log(nums[i]);
        }

        int ans = 0;
        for (int i = 0; i < prefix.length; i++) {
            int lo = i + 1, hi = prefix.length;
            while (lo < hi) {
                int mi = lo + (hi - lo) / 2;
                if (prefix[mi] < prefix[i] + logk - 1e-9) lo = mi + 1;
                else hi = mi;
            }
            ans += lo - i - 1;
        }
        return ans;
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



