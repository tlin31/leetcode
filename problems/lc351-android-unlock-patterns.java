351. Android Unlock Patterns - Medium

Given an Android 3x3 key lock screen and two integers m and n, where 1 ≤ m ≤ n ≤ 9, count the 
total number of unlock patterns of the Android lock screen, which consist of minimum of m keys 
and maximum n keys.

 
Rules for a valid pattern:

	1. Each pattern must connect at least m keys and at most n keys.
	2. All the keys must be distinct.
	3. If the line connecting two consecutive keys in the pattern passes through any other keys, 
		the other keys must have previously selected in the pattern. No jumps through non selected 
		key is allowed.
	4. The order of keys used matters.
 


Explanation:

| 1 | 2 | 3 |
| 4 | 5 | 6 |
| 7 | 8 | 9 |
Invalid move: 4 - 1 - 3 - 6
Line 1 - 3 passes through key 2 which had not been selected in the pattern.

Invalid move: 4 - 1 - 9 - 2
Line 1 - 9 passes through key 5 which had not been selected in the pattern.

Valid move: 2 - 4 - 1 - 3 - 6
Line 1 - 3 is valid because it passes through key 2, which had been selected in the pattern

Valid move: 6 - 5 - 4 - 1 - 9 - 2
Line 1 - 9 is valid because it passes through key 5, which had been selected in the pattern.

 

Example:

Input: m = 1, n = 1
Output: 9


******************************************************
key:
	- need to remember the key passed along the way
	- Backtrack
	- edge case:
		1) empty string, return empty
		2)

******************************************************


=======================================================================================================
method 1:

Method:

	-	Backtrack
	-	The algorithm uses backtracking technique to enumerate all possible k combinations of numbers 
	    1~9 where m≤k≤n. 
	    During the generation of the recursive solution tree, the algorithm cuts all the branches 
	    which lead to patterns which does not satisfy the rules and counts only the valid patterns. 
	    In order to compute a valid pattern, the algorithm performs the following steps:

	1. Select a digit i which is not used in the pattern till this moment. This is done with the 
	   help of a usedused array which stores all available digits.

	2. We need to keep last inserted digit last. The algorithm makes a check whether one of 
	   the following conditions is valid.

		1） There is a knight move (as in chess) from last towards i or last and i are adjacent 
		    digits in a row, in a column. 
		    !!! In this case the sum of both digits should be an odd number.

		2） The middle element mid in the line which connects i and last was previously selected. 
		    In case i and last are positioned at both ends of the diagonal, digit mid = 5 should 
		    be previously selected.

		3） last and i are adjacent digits in a diagonal

	-	In case one of the conditions above is satisfied, digit i becomes part of partially generated 
		valid pattern and the algorithm continues with the next candidate digit till the pattern is 
		fully generated. 
	-	Then it counts it. 
	-	In case none of the conditions are satisfied, the algorithm rejects the current digit i, 
		backtracks and continues to search for other valid digits among the unused ones.


Stats:

	- Time complexity : O(n!), where n is maximum pattern length
						The algo computes each pattern once and no element can appear in the 
						pattern twice. 
						The time complexity is proportional to the number of the computed patterns. 
	- Space complexity : O(n), where n is maximum pattern length.
						 In the worst case the maximum depth of recursion is n. 
						 Therefore we need O(n) space used by the system recursive stack



public class Solution {

    private boolean used[] = new boolean[9];

    public int numberOfPatterns(int m, int n) {	        
        int res = 0;
        for (int len = m; len <= n; len++) {	            
            res += calcPatterns(-1, len);

            // reset used[]
            for (int i = 0; i < 9; i++) {	                
                used[i] = false;
            }            
        }
        return res;
    }

    // return number of patterns
    private int calcPatterns(int last, int len) {
        if (len == 0)
            return 1;    
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            if (isValid(i, last)) {
                used[i] = true;
                sum += calcPatterns(i, len - 1);
                used[i] = false;                    
            }
        }
        return sum;
    }

    private boolean isValid(int index, int last) {
        if (used[index])
            return false;

        // first digit of the pattern    
        if (last == -1)
            return true;

        // knight moves or adjacent cells (in a row or in a column)	       
        if ((index + last) % 2 == 1)
            return true;

        // indexes are at both end of the diagonals for example 0,0, and 8,8          
        int mid = (index + last)/2;
        if (mid == 4)
            return used[mid];

        // adjacent cells on diagonal  - for example 0,0 and 1,0 or 2,0 and //1,1
        if ((index%3 != last%3) && (index/3 != last/3)) {
            return true;
        }
        
        // all other cells which are not adjacent
        return used[mid];
    }


}

=======================================================================================================
Method 2:

Method:

	-	The optimization idea is that 1,3,7,9 are symmetric, 2,4,6,8 are also symmetric. 
	    Hence we only calculate one among each group and multiply by 4.
	-	The general idea is DFS all the possible combinations from 1 to 9 and skip invalid moves 
	     along the way.

	-	We can check invalid moves by using a jumping table. e.g. If a move requires a jump and 
	    the key that it is crossing is not visited, then the move is invalid. 
	    Furthermore, we can utilize symmetry to reduce runtime.

Stats:

	- 
	- 


public class Solution {
    // cur: the current position
    // remain: the steps remaining
    int DFS(boolean vis[], int[][] skip, int cur, int remain) {
        if(remain < 0) return 0;
        if(remain == 0) return 1;
        vis[cur] = true;
        int rst = 0;
        for(int i = 1; i <= 9; ++i) {
            // If vis[i] is not visited and (two numbers are adjacent or skip number is already visited)
            if(!vis[i] && (skip[cur][i] == 0 || (vis[skip[cur][i]]))) {
                rst += DFS(vis, skip, i, remain - 1);
            }
        }
        vis[cur] = false;
        return rst;
    }
    
    public int numberOfPatterns(int m, int n) {
        // Skip array represents number to skip between two pairs
        int skip[][] = new int[10][10];
        skip[1][3] = skip[3][1] = 2;
        skip[1][7] = skip[7][1] = 4;
        skip[3][9] = skip[9][3] = 6;
        skip[7][9] = skip[9][7] = 8;
        skip[1][9] = skip[9][1] = skip[2][8] = skip[8][2] = skip[3][7] = skip[7][3] = skip[4][6] = skip[6][4] = 5;
        boolean vis[] = new boolean[10];
        int rst = 0;
        // DFS search each length from m to n
        for(int i = m; i <= n; ++i) {
            rst += DFS(vis, skip, 1, i - 1) * 4;    // 1, 3, 7, 9 are symmetric
            rst += DFS(vis, skip, 2, i - 1) * 4;    // 2, 4, 6, 8 are symmetric
            rst += DFS(vis, skip, 5, i - 1);        // 5
        }
        return rst;
    }
}

------
class Solution {
  public int numberOfPatterns(int m, int n) {
        int[][] skip = new int[10][10];
        skip[1][3] = skip[3][1] = 2;
        skip[1][7] = skip[7][1] = 4;
        skip[3][9] = skip[9][3] = 6;
        skip[7][9] = skip[9][7] = 8;
        skip[1][9] = skip[9][1] = skip[2][8] = skip[8][2] = skip[3][7] = skip[7][3] = skip[4][6] = skip[6][4] = 5;
        boolean[] vis = new boolean[10];
        int[] res = new int[]{0};
        int ret = 0;
        dfs(1, 1, m, n, res, skip, vis);
        ret += res[0] * 4;
        res[0] = 0;
        dfs(2, 1, m, n, res, skip, vis);
        ret += res[0] * 4;
        res[0] = 0;      
        dfs(5, 1, m, n, res, skip, vis);
        ret += res[0];
        return ret;
  }

  private void dfs(int cur, int l, int m, int n, int[] ret, int[][] skip, boolean[] vis) {
    if (l >= m) ret[0]++;
    if (l >= n) return;
    vis[cur] = true;
    for (int next = 1; next <= 9; next++) {
      if (!vis[next] && (skip[cur][next] == 0 || vis[skip[cur][next]])) {
        dfs(next, l+1, m, n, ret, skip, vis);
      }
    }
    vis[cur] = false;
  }
}



=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



