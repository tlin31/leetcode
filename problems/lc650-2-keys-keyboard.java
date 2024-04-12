650. 2 Keys Keyboard - Medium


Initially on a notepad only one character 'A' is present. You can perform two operations on this 
notepad for each step:

	1. Copy All: You can copy all the characters present on the notepad (partial copy is not allowed).
	2. Paste: You can paste the characters which are copied last time.
 

Given a number n. You have to get exactly n 'A' on the notepad by performing the minimum number of steps permitted. Output the minimum number of steps to get n 'A'.

Example 1:

Input: 3
Output: 3
Explanation:
Intitally, we have one character 'A'.
In step 1, we use Copy All operation.
In step 2, we use Paste operation to get 'AA'.
In step 3, we use Paste operation to get 'AAA'.
 

Note:

The n will be in the range [1, 1000].



******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP


Stats:

	- 
	- 


Method:

	-	
	-  As per the keyboard operations:
		to get AA from A we need 2 additional steps (copy-all and then paste)
		to get AAA from A we need 3 additional steps (copy-all, then paste, then again paste)
		For generating AAAA we need 2 additional steps from AA.
	
	- however, to get AAAAAAAA, the most optimal way would be from AAAA, with 2 additional steps 
	  (copy-all then paste)
	- Essentially, we find the next smaller length sequence (than the one under consideration) which 
	  can be copied and then pasted over multiple times to generate the desired sequence. The moment 
	  we find a length that divides our required sequence length perfectly, then we dont need to check
	  for any smaller length sequences.



    public int minSteps(int n) {
        int[] dp = new int[n+1];

        for (int i = 2; i <= n; i++) {
            dp[i] = i;
            for (int j = i-1; j > 1; j--) {
				// if sequence of length 'j' can be pasted multiple times to get length 'i' sequence
				if (i % j == 0) {
				    // we just need to paste sequence j (i/j - 1) times, hence additional (i/j) 
				    // times since we need to copy it first as well.
				    dp[i] = dp[j] + (i/j);
				    break;
				}
                
            }
        }
        return dp[n];
    }

=======================================================================================================
method 2:Math

Stats:

	- 
	- 


Method:

	-	
	-	We can break our moves into groups of (copy, paste, ..., paste). Let C denote copying and P
	    denote pasting. Then for example, in the sequence of moves CPPCPPPPCP, the groups would be 
	    [CPP][CPPPP][CP].

	-  	Say these groups have lengths g_1, g_2, .... After parsing the first group, there are g_1 'A's.
	    After parsing the second group, there are g_1 * g_2 'A's, and so on. 
	    At the end, there are g_1 * g_2 * ... * g_n 'A's.

	-	We want exactly N = g_1 * g_2 * ... * g_n. If any of the g_i are composite, say g_i = p * q, 
		then we can split this group into two groups (the first of which has one copy followed by p-1
		pastes, while the second group having one copy and q-1 pastes).

	-	Such a split never uses more moves: we use p+q moves when splitting, and pq moves previously. 
	     As p+q <= pq is equivalent to 1 <= (p-1)(q-1), which is true as long as p >= 2 and q >= 2.

	Algorithm By the above argument, we can suppose g_1, g_2, ... is the prime factorization of N, and the answer is therefore the sum of these prime factors.

class Solution {
    public int minSteps(int n) {
        int ans = 0, d = 2;
        while (n > 1) {
            while (n % d == 0) {
                ans += d;
                n /= d;
            }
            d++;
        }
        return ans;
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



