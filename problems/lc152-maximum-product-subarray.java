152. Maximum Product Subarray - Medium

Given an integer array nums, find the contiguous subarray within an array (containing at least 
one number) which has the largest product.

Example 1:

Input: [2,3,-2,4]
Output: 6
Explanation: [2,3] has the largest product 6.

Example 2:
Input: [-2,0,-1]
Output: 0
Explanation: The result cannot be 2, because [-2,-1] is not a subarray.


******************************************************
key:
	- DP, prefix & suffix array b/c always reach the end
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	Calculate prefix product in A, Calculate suffix product in A. Return the max.
    -   First, if there is no zero in the array, then the subarray with maximum product must start 
        with the first element or end with the last element. And therefore, the maximum product must 
        be some prefix product or suffix product. 

        So in this solution, we compute the prefix product A and suffix product B, and simply return 
        the maximum of A and B.

    proof:
        ex. subarray A[i : j](i != 0, j != n) and the product of elements inside is P. Take P > 0 
            if A[i] > 0 or A[j] > 0 --> extend this subarray to include A[i] or A[j], reach the end.
            if both A[i] and A[j] are negative, then extending this subarray to include both A[i] and A[j] 
            Repeating this procedure and eventually we will reach the beginning or the end of A.

    -   What if there are zeroes in the array? 
            we can split the array into several smaller ones. That is to say, when the prefix product 
            is 0, we start over and compute prefix profuct from the current element instead. 

            And this is exactly what A[i] *= (A[i - 1]) or 1 does.

Stats:

	- 
	- 

    public int maxProduct(int[] A) {
        int n = A.length, 
        	res = A[0], 
        	l = 0, 
        	r = 0;

        for (int i = 0; i < n; i++) {
            l =  (l == 0 ? 1 : l) * A[i];
            r =  (r == 0 ? 1 : r) * A[n - 1 - i];
            res = Math.max(res, Math.max(l, r));
        }
        return res;
    }


input: [-2,3,-2,4, 2, -3]

output:
l = -2, r = -3
l = -6, r = -6
l = 12, r = -24
l = 48, r = 48
l = 96, r = 144
l = -288, r = -288

Answer:
144

=======================================================================================================
method 2:

Method:

	-	DP
	-	The maximum product may exist in 2 circumstances:
			negative * negative
			postive * postive
	- 	keep track of both maximum positive product and minimum negative product.




Stats:

	- 
	- 


class Solution {
    public int maxProduct(int[] nums) {
        if(nums.length==0) return 0;
        int len = nums.length;
        int ans = nums[0];
        
        //max[i] is the maximum product of subarray that ends at nums[i]
        //min[i] is the minimum product of subarray that ends at nums[i]
        int max[] = new int[len];
        int min[] = new int[len];   
        max[0] = nums[0];
        min[0] = nums[0];

        for(int i=1;i<len;i++){
            max[i] = Math.max(nums[i],Math.max(nums[i]*max[i-1],nums[i]*min[i-1]));
            min[i] = Math.min(nums[i],Math.min(nums[i]*min[i-1],nums[i]*max[i-1]));
            ans = Math.max(ans,Math.max(max[i],min[i]));
        }
        
        return ans;
    }
}
=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



