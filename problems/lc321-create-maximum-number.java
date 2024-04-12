321. Create Maximum Number - Hard

Given 2 arrays of length m and n with digits 0-9 representing two numbers. Create the maximum number 
of length k <= m + n from digits of the two. The relative order of the digits from the same array 
must be preserved. Return an array of the k digits.

Note: You should try to optimize your time and space complexity.

Example 1:

Input:
nums1 = [3, 4, 6, 5]
nums2 = [9, 1, 2, 5, 8, 3]
k = 5

Output:
[9, 8, 6, 5, 3]
Example 2:

Input:
nums1 = [6, 7]
nums2 = [6, 0, 4]
k = 5
Output:
[6, 7, 6, 0, 4]

Example 3:
Input:
nums1 = [3, 9]
nums2 = [8, 9]
k = 3

Output:
[9, 8, 9]

******************************************************
key:
	- break to subproblems: get largest k in each array, then merge
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	subproblem1:
		get the largest k numbers when keeping the relative order

			Initialize a empty stack
			Loop through the array nums
			pop the top of stack if it is smaller than nums[i] until
			stack is empty
			the digits left is not enough to fill the stack to size k
			if stack size < k push nums[i]
			Return stack

		subproblem2:
		merge two arrays which are from subproblem1.

		subproblem3:
		compare two arrays.
	-	

Stats:

	- O((m+n)^3) in the worst case
	- 



public class Solution {
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int[] result = new int[k];
        if(len1+len2 < k) {
        	//bad case
            return result;
        }else if(len1+len2 == k){
        	//edge case
            result = mergeTwoArrays(nums1, nums2, k);
        }else{
        	// general case, check for different possible split of 2 arrays take on the final k nums
            for (int i = 0; i <= k; i++) {
                if(i<=len1 && (k-i)<=len2){
                    int[] maxNumbers1 = maxNumberOfSingleArray(nums1, i);
                    int[] maxNumbers2 = maxNumberOfSingleArray(nums2, k - i);
                    int[] maxNumbers = mergeTwoArrays(maxNumbers1, maxNumbers2, k);

                    // return true if maxnumbers array > result array
                    if (compareTwoArrays(maxNumbers, 0, result, 0)) 
                    	result = maxNumbers;
                }
            }
        }
        return result;
    }
    
    // merge sort
    private int[] mergeTwoArrays(int[] nums1, int[] nums2, int k) {
        int[] result = new int[k];
        int idx1 = 0, idx2 = 0;
        int idx = 0;
        while(idx < k){
            //check the two remain arrays to see which one is larger.
            if(compareTwoArrays(nums1, idx1, nums2, idx2)){
                result[idx] = nums1[idx1++];
            }else{
                result[idx] = nums2[idx2++];
            }
            idx++;
        }
        return result;
    }
    
    //get the largest k numbers when keeping the relative order
    private int[] maxNumberOfSingleArray(int[] nums, int k){
        int[] result = new int[k];
        if(k == 0) return result;

        int len = nums.length;
        int idx = 0;
        for(int i = 0; i < len; i++){

        	// pop the top of stack if it is smaller than nums[i]
        	// until the digits left is not enough to fill the stack to size k
            while((len-i-1) + (idx+1) > k && idx > 0 && nums[i] > result[idx-1]) 
            	idx--;

            // if stack size < k push nums[i]
            if(idx < k) 
            	result[idx++] = nums[i];
        }
        return result;
    }
    
    //compare two arrays at the "start" index, return true if nums1 is greater
    public boolean compareTwoArrays(int[] nums1, int startIdx1, int[] nums2, int startIdx2) {
        int len1 = nums1.length - startIdx1;
        if(len1 <= 0) return false;

        int len2 = nums2.length - startIdx2;
        if(len2 <= 0) return true;

        int len = Math.max(len1, len2);
        for (int i = 0; i< len; i++) {
        	int digit1 = startIdx1 + i < nums1.length ? nums1[startIdx1 + i] : 0;
        	int digit2 = startIdx2 + i < nums2.length ? nums2[startIdx2 + i] : 0;
        	if(digit1 != digit2){
        	    return digit1 > digit2;
        	}
        }
        return true;//equal, choose either one is ok
    }
}


=======================================================================================================
method 2:

Method:

	-	
	-	


Stats:

	- 
	- 

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



