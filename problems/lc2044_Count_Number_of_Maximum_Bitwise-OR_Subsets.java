2044. Count Number of Maximum Bitwise-OR Subsets

Medium

Given an integer array nums, find the maximum possible bitwise OR of a subset of nums and 
return the number of different non-empty subsets with the maximum bitwise OR.

An array a is a subset of an array b if a can be obtained from b by deleting some (possibly 
zero) elements of b. Two subsets are considered different if the indices of the elements 
chosen are different.

The bitwise OR of an array a is equal to a[0] OR a[1] OR ... OR a[a.length - 1] (0-indexed).

 

Example 1:

Input: nums = [3,1]
Output: 2
Explanation: The maximum possible bitwise OR of a subset is 3. There are 2 subsets with a bitwise OR of 3:
- [3]
- [3,1]

Example 2:

Input: nums = [2,2,2]
Output: 7
Explanation: All non-empty subsets of [2,2,2] have a bitwise OR of 2. There are 23 - 1 = 7 total subsets.

Example 3:

Input: nums = [3,2,1,5]
Output: 6
Explanation: The maximum possible bitwise OR of a subset is 7. There are 6 subsets with a bitwise OR of 7:
- [3,5]
- [3,1,5]
- [3,2,5]
- [3,2,1,5]
- [2,5]
- [2,1,5]
 

Constraints:

1 <= nums.length <= 16
1 <= nums[i] <= 105

******************************************************
key:
	- The key insight here is that the maximum OR value will always be the result of OR-ing all the numbers in the array. Why? Because OR is an operation that only adds bits, it never removes them. So including more numbers can only increase (or keep the same) the OR value, never decrease it.

For example, consider 3 numbers: 1 (001), 4 (100), and 2 (010).

ORing the three numbers means we look at the bits in each position and combine them using the OR operation to get the resultant bit. Notice that the resultant bit will be 0 only when all the bits at that position are 0, otherwise, it will always be 1. This means that the worst-case scenario is that the bit remains the same, and in all other cases, the bit increases in value.


	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Explanation
	dp[sum] means the number of subsets with bitwise-or sum.


Complexity
	Time O(mn), where m = max(A)
	Space O(m)




    public int countMaxOrSubsets(int[] A) {
        int max = 0, dp[] = new int[1 << 17];

        dp[0] = 1;
        for (int a : A) {
            for (int i = max; i >= 0; --i)
                dp[i | a] += dp[i];
            max |= a;
        }
        return dp[max];
    }




class Solution {

    public int countMaxOrSubsets(int[] nums) {
        int n = nums.length;
        int maxOrValue = 0;

        // Calculate the maximum OR value
        for (int num : nums) {
            maxOrValue |= num;
        }

        Integer[][] memo = new Integer[n][maxOrValue + 1];

        return countSubsetsRecursive(nums, 0, 0, maxOrValue, memo);
    }

    private int countSubsetsRecursive(
        int[] nums, int index, int currentOr, int targetOr, Integer[][] memo) {
        
        // Base case: reached the end of the array
        if (index == nums.length) {
            return (currentOr == targetOr) ? 1 : 0;
        }

        // Check if the result for this state is already memoized
        if (memo[index][currentOr] != null) {
            return memo[index][currentOr];
        }

        // Don't include the current number
        int countWithout = countSubsetsRecursive(
            nums,
            index + 1,
            currentOr,
            targetOr,
            memo
        );

        // Include the current number
        int countWith = countSubsetsRecursive(
            nums,
            index + 1,
            currentOr | nums[index],
            targetOr,
            memo
        );

        // Memoize and return the result
        return memo[index][currentOr] = countWithout + countWith;
    }
}


