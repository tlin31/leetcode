238. Product of Array Except Self - Medium


Given an array nums of n integers where n > 1, return an array output such that output[i] is equal 
to the product of all the elements of nums except nums[i].

Example:

Input:  [1,2,3,4]
Output: [24,12,8,6]

Constraint: It is guaranteed that the product of the elements of any prefix or suffix of the array 
(including the whole array) fits in a 32 bit integer.

Note: Please solve it without division and in O(n).

Follow up:
Could you solve it with constant space complexity? (The output array does not count as extra space 
for the purpose of space complexity analysis.)



******************************************************
key:
	- left product and right product
	- edge case:
		1) empty array
		2) bunch of zeros or bunch of 1s

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	-	prefix & suffix
	-	Left array stores product of elememnts to the left of L[i]
			L[i] = nums[i - 1] * L[i - 1];

		Base case: there are no elements to the left, so L[0] would be 1

	-  	R[i + 1] already contains the product of elements to the right of 'i + 1' 
	
            R[i] = nums[i + 1] * R[i + 1];

		Base case:  for the element at index 'length - 1', there are no elements to the right, so 
		            the R[length - 1] would be 1


class Solution {
    public int[] productExceptSelf(int[] nums) {

        int length = nums.length;

        int[] L = new int[length];
        int[] R = new int[length];
        int[] answer = new int[length];

        L[0] = 1;
        for (int i = 1; i < length; i++) {

            // L[i - 1] already contains the product of elements to the left of 'i - 1'
            L[i] = nums[i - 1] * L[i - 1];
        }


        R[length - 1] = 1;
        for (int i = length - 2; i >= 0; i--) {

            R[i] = nums[i + 1] * R[i + 1];
        }

        for (int i = 0; i < length; i++) {
            // For the first element, R[i] would be product except self
            // For the last element of the array, product except self would be L[i]
            // Else, multiple product of all elements to the left and to the right
            answer[i] = L[i] * R[i];
        }

        return answer;
    }
}

input: [4,5,1,8,2]

	 L = [1, 4, 20, 20, 160]
	 R = [80, 16, 16, 2, 1]

answer = [80, 64, 320, 40, 160]

=======================================================================================================
method 2:

Stats:

	- O(n)
	- O(1), as stated in the problem, the returned answer array doesnt count in space complexity


Method:

	-	
	-	

class Solution {
    public int[] productExceptSelf(int[] nums) {

        int length = nums.length;
        int[] answer = new int[length];

        // answer[i] contains the product of all the elements to the left
        // Note: for the element at index '0', there are no elements to the left,
        // so the answer[0] would be 1
        answer[0] = 1;
        for (int i = 1; i < length; i++) {

            // answer[i - 1] already contains the product of elements to the left of 'i - 1'
            // Simply multiplying it with nums[i - 1] would give the product of all 
            // elements to the left of index 'i'
            answer[i] = nums[i - 1] * answer[i - 1];
        }

        // R contains the product of all the elements to the right
        // Note: for the element at index 'length - 1', there are no elements to the right,
        // so the R would be 1
        int R = 1;
        for (int i = length - 1; i >= 0; i--) {

            // For the index 'i', R would contain the 
            // product of all elements to the right. We update R accordingly
            answer[i] = answer[i] * R;
            R *= nums[i];
        }

        return answer;
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



