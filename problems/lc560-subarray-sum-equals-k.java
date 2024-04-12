560. number of continuous Subarray Sum Equals K - Medium


Given an array of integers and an integer k, you need to find the total number of continuous 
subarrays whose sum equals to k.

Example 1:
Input:nums = [1,1,1], k = 2
Output: 2
Note:
The length of the array is in range [1, 20,000].
The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].

******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- we know the key to solve this problem is SUM[i, j]. 
	- So if we know SUM[0, i - 1] and SUM[0, j], then we can easily get SUM[i, j]. 
	- To achieve this, we just need to go through the array, calculate the current sum and save number of 
	  all seen PreSum to a HashMap. 

	- map<sum, number of possibilities>

stats:

	- Time complexity O(n), Space complexity O(n).
	- 

public class Solution {
    public int subarraySum(int[] nums, int k) {
        int sum = 0, result = 0;
        Map<Integer, Integer> preSum = new HashMap<>();
        preSum.put(0, 1);
        
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (preSum.containsKey(sum - k)) {
                result += preSum.get(sum - k);
            }
            preSum.put(sum, preSum.getOrDefault(sum, 0) + 1);
        }
        
        return result;
    }
}



=======================================================================================================
method 2:

method:

	- Using Cummulative sum

	- 	Instead of determining the sum of elements everytime for every new subarray considered, we can 
		make use of a cumulative sum array,sum. 

		Then, in order to calculate the sum of elements lying between two indices, we can subtract the 
        cumulative sum corresponding to the two indices to obtain the sum directly, instead of iterating 
		over the subarray to obtain the sum.

	- cumulative sum array, sum --> sum[i] = cumulative sum of nums array upto the element corresponding 
	  to the (i−1) th index. Thus, to determine the sum of elements for the subarray nums[i:j], we can 
      directly use sum[j+1]−sum[i].


	- 

stats:

	- Time complexity : O(n^2) Considering every possible subarray takes O(n^2) time. 
		Finding out the sum of any subarray takes O(1) time after the initial processing of O(n) 
		for creating the cumulative sum array.

	- Space complexity : O(n). Cumulative sum array sumsum of size n+1n+1 is used. Without space [Accepted]


public class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        int[] sum = new int[nums.length + 1];
        sum[0] = 0;
        for (int i = 1; i <= nums.length; i++)
            sum[i] = sum[i - 1] + nums[i - 1];

        
        for (int start = 0; start < nums.length; start++) {
            for (int end = start + 1; end <= nums.length; end++) {
                if (sum[end] - sum[start] == k)
                    count++;
            }
        }
        return count;
    }
}

=======================================================================================================
method 3:

method:

	-  Without space
	- Instead of considering all the startstart and endend points and then finding the sum for 
      each subarray corresponding to those points, we can directly find the sum on the go while 
      considering different endend points. 
      i.e. We can choose a particular startstart point and while iterating over the endend points, 
      we can add the element corresponding to the endend point to the sum formed till now. 

      Whenver the sumsum equals the required k value, we can update the count value. We do so 
      while iterating over all the endend indices possible for every startstart index. Whenver, 
      we update the startstart index, we need to reset the sumsum value to 0.


stats:

	- Time complexity : O(n^2) We need to consider every subarray possible.
	- Space complexity : O(1). Constant space is used.



public class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        for (int start = 0; start < nums.length; start++) {
            int sum=0;
            for (int end = start; end < nums.length; end++) {
                sum+=nums[end];
                if (sum == k)
                    count++;
            }
        }
        return count;
    }
}


