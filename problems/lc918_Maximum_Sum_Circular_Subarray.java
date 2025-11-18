918. Maximum Sum Circular Subarray - Medium

Given a circular integer array nums of length n, return the maximum possible sum of a non-empty 
subarray of nums.

A circular array means the end of the array connects to the beginning of the array. Formally, 
the next element of nums[i] is nums[(i + 1) % n] and the previous element of nums[i] is 
nums[(i - 1 + n) % n].

A subarray may only include each element of the fixed buffer nums at most once. Formally, for 
a subarray nums[i], nums[i + 1], ..., nums[j], there does not exist i <= k1, k2 <= j with 
k1 % n == k2 % n.

 

Example 1:

Input: nums = [1,-2,3,-2]
Output: 3
Explanation: Subarray [3] has maximum sum 3.

Example 2:

Input: nums = [5,-3,5]
Output: 10
Explanation: Subarray [5,5] has maximum sum 5 + 5 = 10.
Example 3:

Input: nums = [-3,-2,-3]
Output: -2
Explanation: Subarray [-2] has maximum sum -2.
 

Constraints:

n == nums.length
1 <= n <= 3 * 104
-3 * 104 <= nums[i] <= 3 * 104


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

2 case: involve the edge/circular part， or notignored

转换公式： max(the max subarray sum, the total sum - the min subarray sum)

Just one to pay attention:
If all numbers are negative, maxSum = max(A) and minSum = sum(A).
In this case, max(maxSum, total - minSum) = 0, which means the sum of an empty subarray.
According to the deacription, We need to return the max(A), instead of sum of am empty subarray.
So we return the maxSum to handle this corner case.





Complexity
One pass, time O(N)
No extra space, space O(1)


    public int maxSubarraySumCircular(int[] A) {
        int total = 0, 
        	maxSum = A[0], 
        	curMax = 0, 
        	minSum = A[0], 
        	curMin = 0;

        for (int a : A) {

            curMax = Math.max(curMax + a, a);
            maxSum = Math.max(maxSum, curMax);

            curMin = Math.min(curMin + a, a);
            minSum = Math.min(minSum, curMin);

            total += a;
        }
        
        return maxSum > 0 ? Math.max(maxSum, total - minSum) : maxSum;
    }






