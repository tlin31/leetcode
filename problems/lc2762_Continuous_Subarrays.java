2762. Continuous Subarrays - Medium 

You are given a 0-indexed integer array nums. A subarray of nums is called continuous if:

Let i, i + 1, ..., j be the indices in the subarray. Then, for each pair of indices i <= i1, 
i2 <= j, 0 <= |nums[i1] - nums[i2]| <= 2.

Return the total number of continuous subarrays.

A subarray is a contiguous non-empty sequence of elements within an array.

 

Example 1:

Input: nums = [5,4,2,4]
Output: 8
Explanation: 
Continuous subarray of size 1: [5], [4], [2], [4].
Continuous subarray of size 2: [5,4], [4,2], [2,4].
Continuous subarray of size 3: [4,2,4].
There are no subarrys of size 4.
Total continuous subarrays = 4 + 3 + 1 = 8.
It can be shown that there are no more continuous subarrays.
 

Example 2:

Input: nums = [1,2,3]
Output: 6
Explanation: 
Continuous subarray of size 1: [1], [2], [3].
Continuous subarray of size 2: [1,2], [2,3].
Continuous subarray of size 3: [1,2,3].
Total continuous subarrays = 3 + 2 + 1 = 6.
 

Constraints:

1 <= nums.length <= 105
1 <= nums[i] <= 109


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

	-	We use a sliding window approach with two pointers: a left pointer (l) and a 
	right pointer (r). As we iterate through the array, we try to expand the window by 
	moving the right pointer. 
	If the difference between the maximum and minimum values in the window exceeds 2, 
	we move the left pointer forward until the condition is satisfied.


	1. Sliding Window:
	The right pointer (r) will iterate over the array, extending the window.
	For each element, we update the window by adding it to our deques (one for the minimums 
		and one for the maximums).

	2. Maintain the Deques:
	The minD deque stores indices of elements in increasing order of their values, ensuring that 
	nums[minD.peekFirst()] is always the smallest value in the window.

	The maxD deque stores indices of elements in decreasing order of their values, ensuring that 
	nums[maxD.peekFirst()] is always the largest value in the window.

	3. Shrinking the Window:
	If the difference between the maximum and minimum values (tracked by the front of the deques) 
	exceeds 2, we increment the left pointer (l) and remove any indices from the deques that are 
	out of the new window.

	4. Count Valid Subarrays:
	For each valid window, the number of subarrays that end at r and start anywhere between l 
	and r is r - l + 1.


Stats:
	Time complexity:
	The main operations involve iterating through the array once (O(n)) and performing constant-time 
	operations (like pushing and popping from deques) for each element.
	Hence, the time complexity is O(n), where n is the length of the input array.

	Space complexity:
	The space complexity is O(n) due to the two deques (minD and maxD), each of which can store at 
	most n elements in the worst case.



class Solution {
    public long continuousSubarrays(int[] nums) {
        int l = 0;
        long res = 0;  // Use long to avoid overflow
        Deque<Integer> minD = new LinkedList<>(), 
        				maxD = new LinkedList<>();

        for (int r = 0; r < nums.length; r++) {
            // Maintain the min deque, take all the smaller ones 
            while (!minD.isEmpty() && nums[minD.peekLast()] >= nums[r]) {
                minD.pollLast();
            }

            // Maintain the max deque
            while (!maxD.isEmpty() && nums[maxD.peekLast()] <= nums[r]) {
                maxD.pollLast();
            }

            // Add the current element index to the deques
            minD.offerLast(r);
            maxD.offerLast(r);

            // Shrink the window from the left until the condition holds
            while (nums[maxD.peekFirst()] - nums[minD.peekFirst()] > 2) {
                l++;
                if (minD.peekFirst() < l) minD.pollFirst();
                if (maxD.peekFirst() < l) maxD.pollFirst();
            }

            // Add the valid subarrays count from l to r
            res += r - l + 1;
        }

        return res;
    }
}






