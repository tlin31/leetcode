1099. Two Sum Less Than K easy

Given an array A of integers and integer K, return the maximum S such that there 
exists i < j with A[i] + A[j] = S and S < K. If no i, j exist satisfying this equation, return -1.

 

Example 1:

Input: A = [34,23,1,24,75,33,54,8], K = 60
Output: 58
Explanation: 
We can use 34 and 24 to sum 58 which is less than 60.


Example 2:

Input: A = [10,20,30], K = 15
Output: -1
Explanation: 
In this case it's not possible to get a pair sum less that 15.
 

Note:

1 <= A.length <= 100
1 <= A[i] <= 1000
1 <= K <= 2000


******************************************************
key:
	- use sort and 2 pointers
	- can use binary search or 2 pointers
	- ?time O(n), space O(1)
	- edge case:
		1) empty array, return -1
		2) is the array sorted?

******************************************************



=======================================================================================================
Method 1:


Stats:

	- O(n*logn)
	- O(1)


Method:

class Solution {
      public int twoSumLessThanK(int[] nums, int k) {
          Arrays.sort(nums);
          int left = 0;
          int right = nums.length - 1;
          int res = -1;
          while (left < right) {
              int sum = nums[left] + nums[right];
              if (sum < k) {
                 res = Math.max(res, sum);
                 left++;
             } else {
                 right--;
             }
         }
         return res;
     }
 }





