
There is a strange printer with the following two special requirements:

	1. The printer can only print a sequence of the same character each time.
	2. At each turn, the printer can print new characters starting from and ending at any places, 
	   and will cover the original existing characters.

Given a string consists of lower English letters only, your job is to count the minimum number of 
turns the printer needed in order to print it.

Example 1:
Input: "aaabbb"
Output: 2
Explanation: Print "aaa" first and then print "bbb".


Example 2:
Input: "aba"
Output: 2
Explanation: Print "aaa" first and then print "b" from the second place of the string, which will cover the existing character 'a'.


Hint: Length of the given string will not exceed 100.


******************************************************
key:
	- dp
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- O(n^3)
	- 


Method:

	dp[i][j] stands for the minimal turns we need for string from index i to index j.

	Base case:
		dp[i][i] = 1: we need 1 turn to paint a single character.
		dp[i][i + 1]
		dp[i][i + 1] = 1 if s.chartAt(i) == s.charAt(i + 1)
		dp[i][i + 1] = 2 if s.chartAt(i) != s.charAt(i + 1)

	The maximum turns for dp[start][start + len] is len + 1, i.e. print one character each time.
	
	We can further divide the substring to two parts: 
			start -> start+k，  start+k+1 -> start+len. 

			index |start  ...  start + k| |start + k + 1 ... start + len|
			char  |  a    ...       b   | |      c       ...      b     |

	As shown above, if we have s.charAt(start + k) == s.charAt(start + len), we can make it in one 
	turn when we print this character (i.e. b here)

	This case we can reduce our turns to dp[start][start + k] + dp[start + k + 1][start + len] - 1


	-  Say the first print is on [i, k]. We can assume S[i] == S[k]

	   we could print up to the last occurrence of S[i] in [i, k] for the same result.

	-  if S[i] == S[k], then printing [i,k] is the same as printing [i, k-1]. 

	   This is because if S[i] and S[k] get completed in separate steps, we might as well print 
	   them first in one step instead.

	- To compute a recursion for dp(i, j), for every i <= k <= j with S[i] == S[k], we have some 
	  candidate answer dp(i, k-1) + dp(k+1, j), and we take the minimum of these candidates. 

	  Of course, when k = i, the candidate is just 1 + dp(i+1, j).



public int strangePrinter(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int n = s.length();
        int[][] dp = new int[n][n];
        
        // j is the right end, i is the left end
		for (int j = 0; j < n; j++) { 
			for (int i = j; i >= 0; i--) { 
				if (i == j)
					dp[i][j] = 1;
				else
					dp[i][j] = 1 + dp[i + 1][j];

				//starting from left, compare the next element with the first
				for (int k = i + 1; k <= j; k++) {

					//if the same, then we can print them just once meaning we can ignore it
					if (s.charAt(k) == s.charAt(i)) 
						dp[i][j] = Math.min(dp[i][j], dp[i + 1][k - 1] + dp[k][j]);
				}
			}
		}
        return dp[0][n - 1];
    }

=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



