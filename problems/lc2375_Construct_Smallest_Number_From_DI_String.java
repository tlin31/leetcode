2375. Construct Smallest Number From DI String
Medium

You are given a 0-indexed string pattern of length n consisting of the characters 'I' 
meaning increasing and 'D' meaning decreasing.

A 0-indexed string num of length n + 1 is created using the following conditions:

num consists of the digits '1' to '9', where each digit is used at most once.
If pattern[i] == 'I', then num[i] < num[i + 1].
If pattern[i] == 'D', then num[i] > num[i + 1].
Return the lexicographically smallest possible string num that meets the conditions.

 

Example 1:

Input: pattern = "IIIDIDDD"
Output: "123549876"
Explanation:
At indices 0, 1, 2, and 4 we must have that num[i] < num[i+1].
At indices 3, 5, 6, and 7 we must have that num[i] > num[i+1].
Some possible values of num are "245639871", "135749862", and "123849765".
It can be proven that "123549876" is the smallest possible num that meets the conditions.
Note that "123414321" is not possible because the digit '1' is used more than once.

Example 2:

Input: pattern = "DDD"
Output: "4321"
Explanation:
Some possible values of num are "9876", "7321", and "8742".
It can be proven that "4321" is the smallest possible num that meets the conditions.
 

Constraints:

1 <= pattern.length <= 8
pattern consists of only the letters 'I' and 'D'.


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

	-	



Stats:

	- 
	- 

class Solution {

    public String smallestNumber(String pattern) {
        StringBuilder result = new StringBuilder();

        // Start building the sequence by calling the helper function
        buildSequence(0, 0, pattern.toCharArray(), result);
        // Reverse the final result
        return result.reverse().toString();
    }

    // Recursively build the sequence
    int buildSequence(
        int currentIndex,
        int currentCount,
        char[] patternArray,
        StringBuilder result) {
        if (currentIndex != patternArray.length) {
            if (patternArray[currentIndex] == 'I') 
            	buildSequence(
                // If 'I', increment the count and move to the next index
                currentIndex + 1,
                currentIndex + 1,
                patternArray,
                result
            );

            else currentCount = buildSequence(
                // If 'D', keep the count and move to the next index
                currentIndex + 1,
                currentCount,
                patternArray,
                result
            );

        }

        result.append(currentCount + 1);
        // Return the next count for the sequence
        return currentCount + 1;
    }
}


====================================================================================================
method 2: Greedy Approach with Sliding Window Reversal

1 2 3 4 5 6 7 8 9
D D I D D I D D

Match the input string and the sequence 1,2,3,4,5,6,7,8,9.
Reverse all numbers between "I".

For example:
first 'I' at 3, then reverse 1,2,3, we have 3,2,1
second 'I' at 6, then reverse 4,5,6, we have 6,5,4
Reverse the last group 7,8,9, we have 9,8,7

Final result for D D I D D I D D,
is 3,2,1,6,5,4,9,8,7

We can have multiple approach to implement this process.

Complexity:

	Let n be the length of the input string pattern.

	Time complexity: O(n)

	The algorithm iterates through the input string pattern once, which takes O(n) time. 
	During each iteration, when the character is 'I' or the end of the string is reached, 
	the algorithm reverses a contiguous segment of the result string. While reversing a 
	substring of length k takes O(k) time, each position in the array is reversed at most 
	once throughout the entire process.


	Space complexity: O(n)
    

    public String smallestNumber(String s) {
        StringBuilder res = new StringBuilder(), 
                    stack = new StringBuilder();

        for (int i = 0; i <= s.length(); i++) {
            stack.append((char)('1' + i));

            if (i == s.length() || s.charAt(i) == 'I') {
                res.append(stack.reverse());
                stack = new StringBuilder();
            }
        }
        return res.toString();
    }





