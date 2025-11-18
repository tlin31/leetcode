2698. Find the Punishment Number of an Integer - Medium

Given a positive integer n, return the punishment number of n.

The punishment number of n is defined as the sum of the squares of all integers i such that:

1 <= i <= n

The decimal representation of i * i can be partitioned into contiguous substrings such that the 
sum of the integer values of these substrings equals i.
 

Example 1:

Input: n = 10
Output: 182
Explanation: There are exactly 3 integers i in the range [1, 10] that satisfy the conditions in 
the statement:
- 1 since 1 * 1 = 1
- 9 since 9 * 9 = 81 and 81 can be partitioned into 8 and 1 with a sum equal to 8 + 1 == 9.
- 10 since 10 * 10 = 100 and 100 can be partitioned into 10 and 0 with a sum equal to 10 + 0 == 10.
Hence, the punishment number of 10 is 1 + 81 + 100 = 182

Example 2:

Input: n = 37
Output: 1478
Explanation: There are exactly 4 integers i in the range [1, 37] that satisfy the conditions in the 
statement:
- 1 since 1 * 1 = 1. 
- 9 since 9 * 9 = 81 and 81 can be partitioned into 8 + 1. 
- 10 since 10 * 10 = 100 and 100 can be partitioned into 10 + 0. 
- 36 since 36 * 36 = 1296 and 1296 can be partitioned into 1 + 29 + 6.
Hence, the punishment number of 37 is 1 + 81 + 100 + 1296 = 1478
 

Constraints:

1 <= n <= 1000


******************************************************
key:
	- DP 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1: recursion

Method:

	-	



Stats:

	- Runtime 147 ms，Beats 19.47%
	- 

class Solution {

    public int punishmentNumber(int n) {
        int punishmentNum = 0;

        // Iterate through numbers in range [1, n]
        for (int currentNum = 1; currentNum <= n; currentNum++) {
            int squareNum = currentNum * currentNum;

            // Check if valid partition can be found and add squared number if so
            if (canPartition(Integer.toString(squareNum), currentNum)) {
                punishmentNum += squareNum;
            }
        }
        return punishmentNum;
    }

    public boolean canPartition(String stringNum, int target) {
        // Valid Partition Found
        if (stringNum.isEmpty() && target == 0) {
            return true;
        }

        // Invalid Partition Found
        if (target < 0) {
            return false;
        }

        // Recursively check all partitions for a valid partition
        for (int index = 0; index < stringNum.length(); index++) {
            String left = stringNum.substring(0, index + 1);
            String right = stringNum.substring(index + 1);
            int leftNum = Integer.parseInt(left);

            if (canPartition(right, target - leftNum)) {
                return true;
            }
        }
        return false;
    }
}




===================================================================================================
Method 2: Memo

We use a 2D array memo[startIndex][sum] to store the results of previously computed states. 
Here, startIndex represents our current position in the string, and sum represents the accumulated 
sum of selected partitions. If a state has already been computed, we can return the stored result 
immediately, avoiding redundant calculations.

With this strategy in mind, we iterate through numbers from 1 to n, square each number, and check 
if it can be partitioned using the recursive function findPartitions(). Before each call, we reset 
the DP array to ensure we do not mix results across different numbers. Then, our recursive function 
attempts to extract substrings, add them to the sum, and continue exploring further partitions. 
If a valid partition is found, we add squareNum to our total punishment sum.


class Solution {

    public int punishmentNumber(int n) {
        int punishmentNum = 0;
        // Iterate through numbers in range [1, n]
        for (int currentNum = 1; currentNum <= n; currentNum++) {
            int squareNum = currentNum * currentNum;
            String stringNum = Integer.toString(squareNum);

            // Initialize values in memoization array
            int[][] memoArray = new int[stringNum.length()][currentNum + 1];
            for (int[] row : memoArray) {
                java.util.Arrays.fill(row, -1);
            }

            // Check if valid partition can be found and add squared number if so
            if (findPartitions(0, 0, stringNum, currentNum, memoArray)) {
                punishmentNum += squareNum;
            }
        }
        return punishmentNum;
    }

    public boolean findPartitions(
        int startIndex,
        int sum,
        String stringNum,
        int target,
        int[][] memo
    ) {
        // Check if partition is valid
        if (startIndex == stringNum.length()) {
            return sum == target;
        }

        // Invalid partition found, so we return false
        if (sum > target) return false;

        // If the result for this state is already calculated, return it
        if (memo[startIndex][sum] != -1) return memo[startIndex][sum] == 1;

        boolean partitionFound = false;

        // Iterate through all possible substrings starting with startIndex
        for (
            int currentIndex = startIndex;
            currentIndex < stringNum.length();
            currentIndex++
        ) {
            // Create partition
            String currentString = stringNum.substring(
                startIndex,
                currentIndex + 1
            );
            int addend = Integer.parseInt(currentString);

            // Recursively check if valid partition can be found
            partitionFound =
                partitionFound ||
                findPartitions(
                    currentIndex + 1,
                    sum + addend,
                    stringNum,
                    target,
                    memo
                );
            if (partitionFound) {
                memo[startIndex][sum] = 1;
                return true;
            }
        }

        // Memoize the result for future reference and return its result
        memo[startIndex][sum] = 0;
        return false;
    }


}


