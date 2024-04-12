665. Non-decreasing Array - Easy


Given an array nums with n integers, your task is to check if it could become non-decreasing by 
modifying at most 1 element.

We define an array is non-decreasing if nums[i] <= nums[i + 1] holds for every i (0-based) such that 
(0 <= i <= n - 2).

 

Example 1:

Input: nums = [4,2,3]
Output: true
Explanation: You could modify the first 4 to 1 to get a non-decreasing array.


Example 2:

Input: nums = [4,2,1]
Output: false
Explanation: You cant get a non-decreasing array by modify at most one element.
 

Constraints:

1 <= n <= 10 ^ 4
- 10 ^ 5 <= nums[i] <= 10 ^ 5


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	- When you find nums[i-1] > nums[i] for some i, you will prefer to change nums[i-1]'s value, 
	  since a larger nums[i] will give you more risks that you get inversion errors after position i. 

	  But, if you also find nums[i-2] > nums[i], then you have to change nums[i]'s value instead, or 
	  else you need to change both of nums[i-2]'s and nums[i-1]'s values.

Examples:
 0  ...  i ...
 1 1 2 4[2]5 6  - in this case we can just raise a[i] to 4;
         4

 1 1 2[4]2 3 4  - in this case lower a[i-1] is better;
       2


 public boolean checkPossibility(int[] nums) {

 		//the number of changes
        int cnt = 0;                                                                    
        for(int i = 1; i < nums.length && cnt<=1 ; i++){
            if(nums[i-1] > nums[i]){
            	cnt++;

                if (cnt > 0) 
                	return false;

                //modify nums[i-1] of a priority
                if(i-2<0 || nums[i-2] <= nums[i])
                	nums[i-1] = nums[i];  

                //have to modify nums[i]                  
                else 
                	nums[i] = nums[i-1];                                                
            }
        }
        return cnt<=1; 
    }



class Solution {
    public boolean checkPossibility(int[] a) {
        int modified = 0;
        int prev = a[0]
        for (int i = 1; i < a.length; i++) {
            if (a[i] < prev) {

                if (modified++ > 0) 
                	return false;

                // change nums[i]
                if (i - 2 >= 0 && a[i - 2] > a[i]) 
                	continue;
            }
            prev = a[i];
        }
        return true;
    }
}




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

	- First, find a pair where the order is wrong. 
	- 2 cases:
		1) either the first in the pair can be modified 
		2) second can be modified to create a valid sequence. 

	- We simply modify both of them and check for validity of the modified arrays by comparing with 
	  the array after sorting.


class Solution(object):
    def checkPossibility(self, nums):
        """
        :type nums: List[int]
        :rtype: bool
        """
        one, two = nums[:], nums[:]
        for i in range(len(nums) - 1):
            if nums[i] > nums[i + 1]:
                one[i] = nums[i + 1]
                two[i + 1] = nums[i]
                break

        return one == sorted(one) or two == sorted(two)


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	










~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


