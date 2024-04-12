496. Next Greater Element I - Easy


You are given two arrays (without duplicates) nums1 and nums2 where nums1’s elements are subset 
of nums2. Find all the next greater numbers for nums1s elements in the corresponding places of 
nums2.

The Next Greater Number of a number x in nums1 is the first greater number to its right in nums2. 
If it does not exist, output -1 for this number.

Example 1:
Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
Output: [-1,3,-1]
Explanation:
    For number 4 in the first array, you cannot find the next greater number for it in the 
    second array, so output -1.
    For number 1 in the first array, the next greater number for it in the second array is 3.
    For number 2 in the first array, there is no next greater number for it in the second array, 
    so output -1.


Example 2:
Input: nums1 = [2,4], nums2 = [1,2,3,4].
Output: [3,-1]
Explanation:
    For number 2 in the first array, the next greater number for it in the second array is 3.
    For number 4 in the first array, there is no next greater number for it in the second array, 
    so output -1.


Note:
All elements in nums1 and nums2 are unique.
The length of both nums1 and nums2 would not exceed 1000.


******************************************************
key:
	- stack
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: stack


Stats:

	- 
	- 


Method:

	- We use a stack to keep a decreasing sub-sequence, whenever we see a number x greater than 
	  stack.peek() we pop all elements less than x and for all the popped ones, their next greater 
	  element is x

ex. [9, 8, 7, 3, 2, 1, 6]
	- The stack will first contain [9, 8, 7, 3, 2, 1] and then we see 6 which is greater than 1 so 
	  we pop 1 2 3 whose next greater element should be 6
	- map stores <nums element, the bigger element to its right>


public class Solution {
    public int[] nextGreaterElement(int[] findNums, int[] nums) {
        Stack < Integer > stack = new Stack < > ();
        HashMap < Integer, Integer > map = new HashMap < > ();
        int[] res = new int[findNums.length];
        for (int i = 0; i < nums.length; i++) {
            while (!stack.empty() && nums[i] > stack.peek())
                map.put(stack.pop(), nums[i]);
            stack.push(nums[i]);
        }
        while (!stack.empty())
            map.put(stack.pop(), -1);
        for (int i = 0; i < findNums.length; i++) {
            res[i] = map.get(findNums[i]);
        }
        return res;
    }
}






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:  brute force

Stats:

	- 
	- 


Method:

	-	
	- Instead of searching for the occurence of findNums[i] linearly in the nums array, we can 
	  make use of a hashmap hash to store the elements of nums in the form of (element, index)
	  By doing this, we can find findNums[i]s index in nums array directly and then continue to 
	  search for the next larger element in a linear fashion.



public class Solution {
    public int[] nextGreaterElement(int[] findNums, int[] nums) {
        HashMap < Integer, Integer > hash = new HashMap < > ();
        int[] res = new int[findNums.length];
        int j;
        for (int i = 0; i < nums.length; i++) {
            hash.put(nums[i], i);
        }
        for (int i = 0; i < findNums.length; i++) {
            for (j = hash.get(findNums[i]) + 1; j < nums.length; j++) {

                if (findNums[i] < nums[j]) {
                    res[i] = nums[j];
                    break;
                }
            }
            if (j == nums.length) {
                res[i] = -1;
            }
        }
        return res;
    }
}








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

