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
  private int solveNLogN(int s, int[] nums) {
        int[] sums = new int[nums.length + 1];
        for (int i = 1; i < sums.length; i++) 
          sums[i] = sums[i - 1] + nums[i - 1];
        
        int minLen = Integer.MAX_VALUE;
        for (int i = 0; i < sums.length; i++) {
            int end = binarySearch(i + 1, sums.length - 1, sums[i] + s, sums);
            if (end == sums.length) 
              break;
            if (end - i < minLen) 
              minLen = end - i;
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }
    
    private int binarySearch(int lo, int hi, int key, int[] sums) {
        while (lo <= hi) {
           int mid = (lo + hi) / 2;
           if (sums[mid] >= key){
               hi = mid - 1;
           } else {
               lo = mid + 1;
           }
        }
        return lo;
    }

=======================================================================================================
method 3:

method:

  - 
  - 

stats:

  - 
  - 



