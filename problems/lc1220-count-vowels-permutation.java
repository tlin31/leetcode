1220. Count Vowels Permutation - Hard


Given an integer n, your task is to count how many strings of length n can be formed under 
the following rules:

	Each character is a lower case vowel ('a', 'e', 'i', 'o', 'u')
	Each vowel 'a' may only be followed by an 'e'.
	Each vowel 'e' may only be followed by an 'a' or an 'i'.
	Each vowel 'i' may not be followed by another 'i'.
	Each vowel 'o' may only be followed by an 'i' or a 'u'.
	Each vowel 'u' may only be followed by an 'a'.


Since the answer may be too large, return it modulo 10^9 + 7.

 

Example 1:

Input: n = 1
Output: 5
Explanation: All possible strings are: "a", "e", "i" , "o" and "u".


Example 2:

Input: n = 2
Output: 10
Explanation: All possible strings are: "ae", "ea", "ei", "ia", "ie", "io", "iu", "oi", "ou" and "ua".


Example 3: 

Input: n = 5
Output: 68
 

Constraints:

1 <= n <= 2 * 10^4


******************************************************
key:
	- form as a graph!
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	- graph problem --> create a directed graph where an edge between characters first and second 
	  imply that it is permissible to write second immediately after first. 

	  Q: Given a directed graph, how many paths of length n are there?


	- dp[n][char] = # of directed paths of length n which end at a particular vertex char. 
	  Then, we know that the last vertex in our path was char. 
	  However, let us focus on the last second vertex. 
	  It could have been any of the vertex which has a direct edge to char. 
	  --> if we can find the # of paths of length n-1 ending at these vertices, then we can append 
	      char at the end of every path and we would have exhausted all possibilites.

		dp[n+1][x] = sum of all dp[n][y] such that there is a directed edge from y to x.

 		

class Solution {
    public int countVowelPermutation(int n) {
        int MOD = (int) (1e9 + 7);
        long[][] dp = new long[n + 1][5];
        
        for (int i = 0; i < 5; i++) {
            dp[1][i] = 1;
        }

        /*
            0: a
            1: e
            2: i
            3: o
            4: u
        */
        // 'a' can follow {'e', 'i', 'u'}
        // 'e' can follow {'a', 'i'}
        // 'i' can follow {'e', 'o'}
        // 'o' can follow {'i'}
        // 'u' can follow {'i', 'o'}
        for (int i = 1; i < n; i++) {
            dp[i+1][0] = (dp[i][1] + dp[i][2] + dp[i][4] ) % MOD;
            dp[i+1][1] = (dp[i][0] + dp[i][2]) % MOD;
            dp[i+1][2] = (dp[i][3] + dp[i][1]) % MOD;
            dp[i+1][3] = (dp[i][2]) % MOD;
            dp[i+1][4] = (dp[i][2] + dp[i][3]) % MOD;
        }

        long ans = 0;
        for (int i = 0; i < 5; i++)
            ans = (ans + dp[n][i]) % MOD;

        return (int) ans;
    }
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~



=======================================================================================================
method 2:

Stats:

	- O(n)
	- O(1)


Method:

	- accumulate combinations that ends with a particular letter. 
	  use array to store counts for each letter.
	  Now, for a string of size n + 1, the combination that ends with 'a', for example, will be 
	  the sum of combinations for letters 'e', 'i' and 'u' for string of size n.

	- Since we only need to know counts for size n to calculate counts for n + 1, we can minimize 
	  our memory complexity by using two arrays.



public int countVowelPermutation(int n) {
  // ex. {1} --> a can be followed by e, thus moves[0] = 1
  //     {0,2} -> e can be followed by a and i, thus moves[1] = {0,2}
  int[][] moves = { {1}, {0, 2}, {0, 1, 3, 4}, {2, 4}, { 0 } };
  int[] v = { 1, 1, 1, 1, 1 };
  while (--n > 0) {
    int[] v1 = { 0, 0, 0, 0, 0 };
    for (int i = 0; i < 5; ++i) {
      for (int j : moves[i])

        // ex. for moves[1] = {0,2}, for new e, add accumulated ways from prev char 'a' & prev char 'i'
        // to form 'xxxxae' & 'xxxxie'
        v1[j] = (v1[j] + v[i]) % 1000000007;
    }
    v = v1;
  }
  return (int)(((long)v[0] + v[1] + v[2] + v[3] + v[4]) % 1000000007);
}




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

https://leetcode.com/problems/count-vowels-permutation/discuss/398286/Simple-Python-(With-Diagram)

Each vowel allows some number of subsequent characters. These transitions are like a tree. 
This problem is asking, "what's the width of the tree with height n?"

My solution keeps track of the number of each vowel at a level in this tree. 
To calculate say 'A', we calculate how many nodes in the previous level produce 'A'. 
This is the number of 'E', 'I', and 'U' nodes.

def count_vowel_permutations(n):
    a, e, i, o, u = 1, 1, 1, 1, 1
    for _ in range(n - 1):
        a, e, i, o, u = e + i + u, a + i, e + o, i, i + o
    return (a + e + i + o + u) % (10**9 + 7)


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

