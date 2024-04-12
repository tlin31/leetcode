72. Edit Distance - Hard

Given two words word1 and word2, find the minimum number of operations required to convert 
word1 to word2.

You have the following 3 operations permitted on a word:

	1. Insert a character
	2. Delete a character
	3. Replace a character

Example 1:
Input: word1 = "horse", word2 = "ros"
Output: 3
Explanation: 
horse -> rorse (replace 'h' with 'r')
rorse -> rose (remove 'r')
rose -> ros (remove 'e')


Example 2:
Input: word1 = "intention", word2 = "execution"
Output: 5
Explanation: 
intention -> inention (remove 't')
inention -> enention (replace 'i' with 'e')
enention -> exention (replace 'n' with 'x')
exention -> exection (replace 'n' with 'c')
exection -> execution (insert 'u')

******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP, bottom up

Method:
	
	-	f(i, j) := minimum cost (or steps) required to convert first i characters of word1 to 
				   first j characters of word2

	-	Base Case: f(0, k) = f(k, 0) = k
	-	Case 1: word1[i] == word2[j]

				f(i, j) = f(i - 1, j - 1)	--> do nothing, equal to subproblem

	-	Case 2: word1[i] != word2[j], then we must insert /delete /replace, whichever is cheaper

				f(i, j) = 1 + min { f(i, j - 1), f(i - 1, j), f(i - 1, j - 1) }

				f(i, j - 1) represents insert operation
				f(i - 1, j) represents delete operation
				f(i - 1, j - 1) represents replace operation

	-	insert op: we insert a new character after word1 that matches the jth character of word2. 
			       So, now have to match i characters of word1 to j - 1 characters of word2. 


	-	Note that the problem is symmetric. The insert operation in one direction (i.e. from word1
		to word2) is same as delete operation in other. So, we could choose any direction.

Stats:

	- Time complexity : If n is the length of word1, m of word2, because of the two indented loops, 
	 					it is O(nm)


	- 


public class Solution {
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        
        int[][] cost = new int[m + 1][n + 1];

        for(int i = 0; i <= m; i++)
            cost[i][0] = i;

        for(int i = 1; i <= n; i++)
            cost[0][i] = i;
        
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(word1.charAt(i) == word2.charAt(j))
                    cost[i + 1][j + 1] = cost[i][j];
                else {
                    int replace  = cost[i][j];
                    int deletion = cost[i][j + 1];
                    int insert   = cost[i + 1][j];
                    int minCost = Math.min(replace, Math.min(deletion,insert));

                    // pick the min-cost, cost = 1 + min(replace, deletion, insert)
                    cost[i + 1][j + 1] = 1 + minCost;
                }
            }
        }
        return cost[m][n];
    }
}



=======================================================================================================
method 2:

Method:

	-	
	-	


Stats:

	- 
	- 

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



