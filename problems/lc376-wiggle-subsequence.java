376. Wiggle Subsequence  - Medium



A sequence of numbers is called a wiggle sequence if the differences between successive numbers 
strictly alternate between positive and negative. The first difference (if one exists) may be either
positive or negative. A sequence with fewer than two elements is trivially a wiggle sequence.

For example, [1,7,4,9,2,5] is a wiggle sequence because the differences (6,-3,5,-7,3) are alternately 
positive and negative. In contrast, [1,4,7,2,5] and [1,7,4,5,5] are not wiggle sequences, the first 
because its first two differences are positive and the second because its last difference is zero.

Given a sequence of integers, return the length of the longest subsequence that is a wiggle sequence. A 
subsequence is obtained by deleting some number of elements (eventually, also zero) from the original 
sequence, leaving the remaining elements in their original order.

Example 1:

Input: [1,7,4,9,2,5]
Output: 6
Explanation: The entire sequence is a wiggle sequence.


Example 2:
Input: [1,17,5,10,13,15,10,5,16,8]
Output: 7
Explanation: There are several subsequences that achieve this length. One is [1,17,10,13,10,16,8].


Example 3:
Input: [1,2,3,4,5,6,7,8,9]
Output: 2


Follow up:
Can you do it in O(n) time?


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: Greedy


Stats:

	- Time complexity : O(n). We traverse the given array once.
	- Space complexity : O(1). No extra space is used.



Method:

	-  find number of alternating max. and min. peaks in the array. 
	-  because if we choose any other intermediate number to be a part of the current wiggle subsequence,
	   the maximum length of that wiggle subsequence will always be less than or equal to the one 
	   obtained by choosing only the consecutive max. and min. elements.


	-  use prevdiff to indicate whether the current subsequence of numbers lies in an increasing or 
	   decreasing wiggle. If prevdiff > 0, it indicates that we have found the increasing wiggle and 
	   are looking for a decreasing wiggle now. Thus, we update the length of the found subsequence 
	   when diff(nums[i]-nums[i-1]) becomes negative. 
	   Similarly, if prevdiff < 0, we will update the count when diff(nums[i]-nums[i-1]) becomes positive.

	-  When the complete array has been traversed, we get the required count, which represents the length
	   of the longest wiggle subsequence.


public class Solution {
    public int wiggleMaxLength(int[] nums) {
        if (nums.length < 2) 
        	return nums.length;
        int maxLen = 1;
        int sign = 0;
        for (int i = 1; i < nums.length; i++) {

        	//peak
            if (nums[i] < nums[i-1] && sign != -1) {         
                sign = -1;
                maxLen++;
            } 

            //valley
            else if (nums[i] > nums[i-1] && sign != 1) {   
                sign = 1;
                maxLen++;
            }
        }
        return maxLen;
    }
}

=======================================================================================================
method 2: DP

Stats:

	- 
	- 


Method:
	up position, it means nums[i] > nums[i-1]
	down position, it means nums[i] < nums[i-1]
	equals to position, nums[i] == nums[i-1]

	- use two arrays up[] and down[] to record the max wiggle sequence length so far at index i.
	- If nums[i] > nums[i-1], that means it wiggles up. the element before it must be a down position. 
	  so up[i] = down[i-1] + 1; down[i] keeps the same with before.
	- If nums[i] < nums[i-1], that means it wiggles down. the element before it must be a up position. 
	  so down[i] = up[i-1] + 1; up[i] keeps the same with before.
	- If nums[i] == nums[i-1], that means it will not change anything becasue it didnt wiggle at all. 
	  so both down[i] and up[i] keep the same.


public class Solution {
    public int wiggleMaxLength(int[] nums) {
        
        if( nums.length == 0 ) return 0;
        
        int[] up = new int[nums.length];
        int[] down = new int[nums.length];
        
        up[0] = 1;
        down[0] = 1;
        
        for(int i = 1 ; i < nums.length; i++){
            if( nums[i] > nums[i-1] ){
                up[i] = down[i-1]+1;
                down[i] = down[i-1];
            }else if( nums[i] < nums[i-1]){
                down[i] = up[i-1]+1;
                up[i] = up[i-1];
            }else{
                down[i] = down[i-1];
                up[i] = up[i-1];
            }
        }
        
        return Math.max(down[nums.length-1],up[nums.length-1]);
    }
}

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



