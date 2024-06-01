209. Minimum Size Subarray Sum - Medium

Given an array of n positive integers and a positive integer s, find the minimal length of a 
contiguous subarray of which the sum ≥ s. 

If there is not one, return 0 instead.

Example:

Input: s = 7, nums = [2,3,1,2,4,3]
Output: 2
Explanation: the subarray [4,3] has the minimal length under the problem constraint.

Follow up:
If you have figured out the O(n) solution, try coding another solution 
of which the time complexity is O(n log n). 

******************************************************
key:
  - 2 pointers
  - edge case:
    1) empty string, return empty
    2)

******************************************************



=======================================================================================================
method 1:

method:

  - 2 pointers
  - 

stats:

  - 
  - O(n)


    public int minSubArrayLen(int s, int[] nums) {
        int sum = 0, 
            left = 0, 
            win = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            // shrink size of the window
            while (sum >= s) {
                win = Math.min(win, i - left + 1);
                sum -= nums[left];
                left++;
            }
        }
        return (win == Integer.MAX_VALUE) ? 0 : win;
    }


=======================================================================================================
method 2:

method:

  - binary search
  - 

stats:

  - Since all elements are positive, the cumulative sum must be strictly increasing. Then, a 
    subarray sum can expressed as the difference between two cumulative sum. Hence, given a 
    start index for the cumulative sum array, the other end index can be searched using binary search.
  - 



  /**
 * This solution uses binary search method on window size and tries to find if a
 * window of size is available in the nums array or not.
 *
 * This solution only works if there are only positive numbers.
 *
 * T(k) = T(k/2) + O(N) ==> T(k) = O(N log(k)). Here k is N. Thus the time
 * complexity will be O(N log(N)).
 *
 * Time Complexity: O(N log(N))
 *
 * Space Complexity: O(1)
 *
 * N = Length of input array.
 */
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        if (nums == null || target < 0) {
            throw new IllegalArgumentException("Input array is null");
        }

        int len = nums.length;
        int start = 1;
        int end = len;
        int minLen = len + 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            int foundWindowSize = windowExists(nums, target, mid);
            if (foundWindowSize > 0) {
                end = foundWindowSize - 1;
                minLen = foundWindowSize;
            } else {
                start = mid + 1;
            }
        }

        return minLen % (len + 1);
    }

    private int windowExists(int[] nums, int target, int maxWindowSize) {
        int foundWindowSize = 0;
        for (int i = 0; i < nums.length; i++) {
            target -= nums[i];
            foundWindowSize++;

            if (i >= maxWindowSize) {
                foundWindowSize--;
                target += nums[i - maxWindowSize];
            }

            if (target <= 0) {
                return foundWindowSize;
            }
        }

        return -1;
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



