1191. K-Concatenation Maximum Sum - Medium



Given an integer array arr and an integer k, modify the array by repeating it k times.

For example, if arr = [1, 2] and k = 3 then the modified array will be [1, 2, 1, 2, 1, 2].

Return the maximum sub-array sum in the modified array. Note that the length of the sub-array 
can be 0 and its sum in that case is 0.

As the answer can be very large, return the answer modulo 10^9 + 7.

 

Example 1:

Input: arr = [1,2], k = 3
Output: 9

Example 2:

Input: arr = [1,-2,1], k = 5
Output: 2


Example 3:

Input: arr = [-1,-2], k = 7
Output: 0
 

Constraints:

1 <= arr.length <= 10^5
1 <= k <= 10^5
-10^4 <= arr[i] <= 10^4



******************************************************
key:
	- consider positive & negative case --> prefix & suffix
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:  Kadanae algorithm


Stats:

	- O(n)
	- 


Method:

	- The maximum SubArray of concatenated_arr can be the sum of all its elements.
	- For e.g. if A is {3, 2, -1} and K is 3, then B will be {3, 2, -1, 3, 2, -1, 3, 2, -1}.
		The sum of all the elements in concatenated_arr will give us 12. To find this one we dont need 
		to create the array concatenated_arr.
		We can simply find the sum of all the elements in array A and we can mutilply it with K.
		But wait, we can omit the last term in it so that the sum will become 13.
		For this one we can use prefix and suffix calculations.

	Eg:
		A is repeated k times in concatenatedarr.
		Consider the first repetition of A is A1, second is A2 and so on. So now our B array(if K=8) 
		  will be {A1, A2, A3, A4, A5, A6, A7, A8}. If you omit the first two elements in A1 and the 
		  last two elements in A8, you might also get the maxsub array.
	
	Consider 2 cases:
		Case 1: 
			when k == 1, then the question is just asking max subarray sum since there is only 1 array
		
		Case 2: when k > 1:
			Case2(1):
				when sum >= 0: we can use ((k - 2) * sum of array) as 'free' sum since they are 
				                 positive and why not add them to our answer. 
				                 Then, only add max prefix sum and max suffix sum from the rest two 
				                 arrays to add to our final answer.
			Case2(2): 
				when sum < 0: if sum < 0, there is no need to add any of (k - 2) 'free' sum, they are 
				              only going to make our final answer smaller. 
				              So we only pick the max prefix sum + max suffix sum as our answer.

		Note that for both cases in case2, we need also compare final answer with kaden.


class Solution {
    
    private static final int mod  = (int) Math.pow(10,9)+7;
    
    public int kConcatenationMaxSum(int[] nums, int k) {

        // STEP 1: run the kadane's algorithm
        long currentSum=0;
        long maxSum=Integer.MIN_VALUE;
        for(int i=0;i<nums.length;i++){
            currentSum = currentSum > 0 ? currentSum + nums[i]: nums[i];
            maxSum = Math.max(currentSum,maxSum);
        }
        long kAlgoMaxSum = maxSum < 0? 0: maxSum;
        
        // base case
        if(k == 1){
            return (int) kAlgoMaxSum;
        }
        
        // STEP 2: get the maximum prefix sum, suffix sum and total sum
        int n = nums.length;

        // zeroIndexSum[i+1] = sum (a[0]....a[i])
        int[] zeroIndexSum = new int[n+1];

        for(int i =0;i < n;i++)
            zeroIndexSum[i+1] = zeroIndexSum[i] + nums[i];

        long suffixSum = 0;
        long prefixSum = 0;
        for(int i = n;i >= 0;i--){
            suffixSum = Math.max(suffixSum, zeroIndexSum[n] - zeroIndexSum[i]);
            prefixSum = Math.max(prefixSum, zeroIndexSum[i]);
        }

        long sum= zeroIndexSum[n];
        
        // STEP 3: 
        // case 1: total sum is +ve
        if(sum > 0){
            return (int) Math.max(sum * (k-2) % mod + suffixSum + prefixSum, kAlgoMaxSum) % mod;
        } 

        else{
           // case 2: sum is negative, then ignore
            return (int) Math.max( prefixSum + suffixSum, kAlgoMaxSum) % mod;
        }

    }
}









~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	










~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

