91. Decode Ways - Medium

A message containing letters from A-Z is being encoded to numbers using the following mapping:

'A' -> 1
'B' -> 2
...
'Z' -> 26

Given a non-empty string containing only digits, determine the total number of ways to decode it.

Example 1:
Input: "12"
Output: 2
Explanation: It could be decoded as "AB" (1 2) or "L" (12).


Example 2:
Input: "226"
Output: 3
Explanation: It could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).


******************************************************
key:
	- backtrack OR DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	backtrack
	-	Enter recursion with the given string i.e. start with index 0.
	-	termination: 
			check for the end of the string. If we have reached the end of the string we return 1.

	- 	Recursion: 
			if the first character is 0 then terminate that path by returning 0. 

	-	Memoization helps to reduce the complexity which would otherwise be exponential. 

	-	number of ways for the given string is determined by making a recursive call to the 
	    function with index + 1 for next substring string and index + 2 after checking for valid
	    2-digit decode. 

Stats:

	- Time Complexity: O(N), where N is length of the string. Memoization helps in pruning the
					   recursion tree and hence decoding for an index only once. 

	- Space Complexity:O(N). The dictionary used for memoization would take the space equal to the
					   length of the string. There would be an entry for each index value. 
					   The recursion stack would also be equal to the length of the string.
	- Runtime: 2 ms, faster than 58.90% of Java online submissions for Decode Ways.
	- Memory Usage: 40.1 MB, less than 5.66% of Java online submissions for Decode Ways.

class Solution {

    HashMap<Integer, Integer> memo = new HashMap<>();

    // main function
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        return recursiveWithMemo(0, s);
    }

    private int recursiveWithMemo(int index, String str) {

        // If you reach the end of the string
        // Return 1 for success.
        if (index == str.length()) {
            return 1;
        }

        // If the string starts with a zero, it can't be decoded
        if (str.charAt(index) == '0') {
            return 0;
        }

        // reach the end
        if (index == str.length()-1) {
            return 1;
        }

        // Memoization is needed since we might encounter the same sub-string.
        if (memo.containsKey(index)) {
            return memo.get(index);
        }

        // get the one-digit case
        int ans = recursiveWithMemo(index+1, str);

        // check for 2-digit case
        if (Integer.parseInt(str.substring(index, index+2)) <= 26) {
             ans += recursiveWithMemo(index+2, str);
         }

        // Save for memoization
        memo.put(index, ans);

        return ans;
    }

}



=======================================================================================================
method 2:

Method:

	-	I used a dp array of size n + 1 to save subproblem solutions. dp[0] = 1 means an empty string 
	    will have one way to decode, dp[1] means the way to decode a string of size 1. 

	-	I then check 1 digit and 2 digit combination and save the results along the way. 
	-	In the end, dp[n] will be the end result.

Stats:

	- 
	- 



public class Solution {
    public int numDecodings(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }

        int n = s.length();
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = s.charAt(0) != '0' ? 1 : 0;

        for(int i = 2; i <= n; i++) {
            int first = Integer.valueOf(s.substring(i-1, i));
            int second = Integer.valueOf(s.substring(i-2, i));
            if(first >= 1 && first <= 9) {
               dp[i] += dp[i-1];  
            }
            if(second >= 10 && second <= 26) {
                dp[i] += dp[i-2];
            }
        }
        return dp[n];
    }
}

=======================================================================================================
method 3:

Method:

	-	
