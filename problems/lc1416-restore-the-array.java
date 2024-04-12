1416. Restore The Array - Hard


A program was supposed to print an array of integers. The program forgot to print whitespaces 
and the array is printed as a string of digits and all we know is that all integers in the array 
were in the range [1, k] and there are no leading zeros in the array.

Given the string s and the integer k. There can be multiple ways to restore the array.

Return the number of possible array that can be printed as a string s using the mentioned program.

The number of ways could be very large so return it modulo 10^9 + 7

 

Example 1:

Input: s = "1000", k = 10000
Output: 1
Explanation: The only possible array is [1000]
Example 2:

Input: s = "1000", k = 10
Output: 0
Explanation: There cannot be an array that was printed this way and has all integer >= 1 and <= 10.
Example 3:

Input: s = "1317", k = 2000
Output: 8
Explanation: Possible arrays are [1317],[131,7],[13,17],[1,317],[13,1,7],[1,31,7],[1,3,17],[1,3,1,7]


Example 4:

Input: s = "2020", k = 30
Output: 1
Explanation: The only possible array is [20,20]. [2020] is invalid because 2020 > 30. [2,020] is ivalid because 020 contains leading zeros.


Example 5:

Input: s = "1234567890", k = 90
Output: 34
 

Constraints:

1 <= s.length <= 10^5.
s consists of only digits and doesnt contain leading zeros.
1 <= k <= 10^9.


******************************************************
key:
	- DP or DFS
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP


Stats:

	- Time: O(s * log k).
	- Memory: O(s) for tabulation. Comparing to top-down DP, we do not need memory for the stack.



Method:

	-	dp[i] = number of ways to print valid arrays from string s start at i 
	-	

public int numberOfArrays(String s, int k) {
    int dp[] = new int[s.length() + 1];
    dp[s.length()] = 1;
    for (int i = s.length() - 1; i >= 0; --i) {
        if (s.charAt(i) == '0')
            continue;
        long n = 0;
        for (int len = 1; i + len <= s.length(); ++len) {
            n = n * 10 + s.charAt(i + len - 1) - '0';
            if (n > k)
                break;
            dp[i] = (dp[i] + dp[i + len]) % 1000000007;
        }
    }
    return dp[0];
}




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def numberOfArrays(self, s: str, k: int) -> int:
        n = len(s)
        t = len(str(k))
        count = [0] * (n+1)
        count[0] = 1
        count[1] = 1
        for i in range(1, n):
            for j in range(t):
                if i-j >= 0 and 1 <= int(s[i-j:i+1]) <= k and s[i-j:i+1][0] != "0":
                    count[i+1] += count[i-j]
        return count[-1] % 1000000007

=======================================================================================================
method 2: DFS

Stats:

	- Time: O(n * log10(k)), where n <= 10^5 is the length of string s, k <= 10^9 => log10(k) <= 9
		Explain: There are total n states, each state needs maximum log10(k) iteration loops 
		         to calculate the result.

	- Space: O(n)



Method:

	- dp[i] = number of ways to print valid arrays from string s start at i
	-	


class Solution {
	int MOD = 1_000_000_007;
    public int numberOfArrays(String s, int k) {
        Integer[] dp = new Integer[s.length()]; 
        return dfs(s, k, 0, dp);
    }

    int dfs(String s, long k, int i, Integer[] dp) {
        // base case -> Found a valid way
        if (i == s.length()) 
        	return 1; 

        // numbers starting with 0 mean invalid!
        if (s.charAt(i) == '0') 
        	return 0; 

        // check in the memo
        if (dp[i] != null) 
        	return dp[i];

        int ans = 0;
        long num = 0;
        for (int endIdx = i; endIdx < s.length(); endIdx++) {
        	// num is the value of the substring s[i..j]
            num = num * 10 + s.charAt(endIdx) - '0'; 

            // num must be in range [1, k]
            if (num > k) 
            	break; 
            ans += dfs(s, k, endIdx + 1, dp);
            ans %= MOD;
        }
        return dp[i] = ans;
    }
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def numberOfArrays(self, s: str, k: int) -> int:
        dp = [-1] * len(s)
        return self.dfs(s, k, 0, dp)
    
    def dfs(self, s: str, k: int, start: int, dp: List[int]) -> int:
        if start == len(s):
            return 1
        if s[start] == '0':
            return 0
        if dp[start] != -1:
            return dp[start]
        
        res, num = 0, 0
        
        for i in range(start, len(s)):
            num = num * 10 + (ord(s[i]) - ord('0'))
            
            if num > k:
                break 
            
            res += self.dfs(s, k, i + 1, dp)
            res %= 10**9 + 7
            
        dp[start] = res
        return res
=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

