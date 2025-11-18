2501. Longest Square Streak in an Array - Medium

You are given an integer array nums. A subsequence of nums is called a square streak if:

The length of the subsequence is at least 2, and
after sorting the subsequence, each element (except the first element) is the square of the 
previous number.

Return the length of the longest square streak in nums, or return -1 if there is no square streak.

 

Example 1:

Input: nums = [4,3,6,16,8,2]
Output: 3
Explanation: Choose the subsequence [4,16,2]. After sorting it, it becomes [2,4,16].
- 4 = 2 * 2.
- 16 = 4 * 4.
Therefore, [4,16,2] is a square streak.
It can be shown that every subsequence of length 4 is not a square streak.

Example 2:

Input: nums = [2,3,5,6,7]
Output: -1
Explanation: There is no square streak in nums so return -1.
 

Constraints:

2 <= nums.length <= 105
2 <= nums[i] <= 105


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************


set:
class Solution {

    public int longestSquareStreak(int[] nums) {
        int longestStreak = 0;

        // Create a Set to store all unique numbers from the input array
        Set<Integer> uniqueNumbers = new HashSet<>();
        for (int num : nums) {
            uniqueNumbers.add(num);
        }

        // Iterate through each number in the input array
        for (int startNumber : nums) {
            int currentStreak = 0;
            long current = startNumber;

            // Continue the streak as long as we can find the next square in the set
            while (uniqueNumbers.contains((int) current)) {
                currentStreak++;

                // Break if the next square would be larger than 10^5 (problem constraint)
                if (current * current > 1e5) break;

                current *= current;
            }

            // Update the longest streak if necessary
            longestStreak = Math.max(longestStreak, currentStreak);
        }

        // Return -1 if no valid streak found, otherwise return the longest streak
        return longestStreak < 2 ? -1 : longestStreak;
    }
}
===================================================================================================
Method 1:

Method:

	-	
For every number we reach, we just check if its a perfect square:
If its a Perfect Square, we pair up with its Square Root.
Else, we keep it in dp array with length as 1, for pairing up its square number later.


Stats:
	Time : O(nlogn)

	Space : O(n)
	

public int longestSquareStreak(int[] A) {
    HashMap<Integer, Integer> dp = new HashMap<>();
    int res = 0;
    Arrays.sort(A);
    for(var i : A){
        int root = (int)Math.sqrt(i);
        if(root * root == i)  
		   dp.put(i, dp.getOrDefault(root, 0) + 1);
        else  
		   dp.put(i, 1);

        res = Math.max(res, dp.get(i));
    }
    return res < 2 ? -1 : res;
}









