3191. Minimum Operations to Make Binary Array Elements Equal to One I - Medium

You are given a binary array nums.

You can do the following operation on the array any number of times (possibly zero):

Choose any 3 consecutive elements from the array and flip all of them.
Flipping an element means changing its value from 0 to 1, and from 1 to 0.

Return the minimum number of operations required to make all elements in nums equal to 1. If it is impossible, return -1.

 

Example 1:

Input: nums = [0,1,1,1,0,0]

Output: 3

Explanation:
We can do the following operations:

Choose the elements at indices 0, 1 and 2. The resulting array is nums = [1,0,0,1,0,0].
Choose the elements at indices 1, 2 and 3. The resulting array is nums = [1,1,1,0,0,0].
Choose the elements at indices 3, 4 and 5. The resulting array is nums = [1,1,1,1,1,1].
Example 2:

Input: nums = [0,1,1,1]

Output: -1

Explanation:
It is impossible to make all elements equal to 1.

 

Constraints:

3 <= nums.length <= 105
0 <= nums[i] <= 1


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

  This approach uses a simple greedy algorithm that works by flipping any 0 we encounter 
	as we iterate through the array from left to right. The key insight is that once we've 
	processed index i, we never need to revisit it - either it's already 1, or we've just 
	flipped it to 1.

Steps:

Iterate through the array from index 0 to n-3 (inclusive).
Whenever we encounter a 0, flip that element and the next two elements (using XOR operation).
Count each flip operation.
After processing, check if the last two elements are both 1:
If yes, return the count of operations.
If not, return -1 (it's impossible to make all elements 1).
This works because any valid solution must leave the last two elements as 1s. If they're 
not both 1 after our greedy process, no solution exists.



Stats:

	- 
	- 


class Solution {
    public int minOperations(int[] nums) {
        int count = 0;
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            if (nums[i] == 0) {
                nums[i] ^= 1;
                nums[i + 1] ^= 1;
                nums[i + 2] ^= 1;
                count++;
            }
        }

        return (nums[n - 2] == 1 && nums[n - 1] == 1) ? count : -1;
    }
}






