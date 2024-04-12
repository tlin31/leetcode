1240. Tiling a Rectangle with the Fewest Squares - Hard

Given a rectangle of size n x m, find the minimum number of integer-sided squares that tile the rectangle.

 

Example 1:


Input: n = 2, m = 3
Output: 3
Explanation: 3 squares are necessary to cover the rectangle.
2 (squares of 1x1)
1 (square of 2x2)

Example 2:
Input: n = 5, m = 8
Output: 5


Example 3:
Input: n = 11, m = 13
Output: 6
 

Constraints:

1 <= n <= 13
1 <= m <= 13


******************************************************
key:
	- backtrack + memo
	- edge case:
		1) empty string, return empty
		2)

******************************************************

=======================================================================================================
method 1:

https://leetcode.com/problems/tiling-a-rectangle-with-the-fewest-squares/discuss/414979/Java-back-tracking-solution

Method:

	-	Go through every point in the rectangle, starting from (0,0), (0,1), ..., (n, m).
If rect[r,..., r+k][c, ..., c+k] is an available area, then cover a k*k square starting at point (r,c).
Try every possible size of square k * k, where k = min(n-r, m-c).
Optimzation: If cnt >= ans, then stop.
	-	


Stats:

	- Runtime: 2 ms, faster than 68.01% of 
	- Memory Usage: 36.3 MB, less than 100.00% 


class Solution {
    int ans = Integer.MAX_VALUE;

    public int tilingRectangle(int n, int m) {
        dfs(0, 0, new boolean[n][m], 0);
        return ans;
    }

    // (r, c) is the starting point for selecting a square
    // rect records the status of current rectangle
    // cnt is the number of squares we have covered currently
    private void dfs(int r, int c, boolean[][] rect, int cnt) {
        int n = rect.length, 
        	m = rect[0].length;

        // optimization 1: The current cnt >= the current answer
        if (cnt >= ans) return;
        
        // Successfully cover the whole rectangle
        if (r >= n) {
            ans = cnt; 
            return;
        }
        
        // Successfully cover the area [0, ..., n][0, ..., c] => Move to next row
        if (c >= m) {
            dfs(r+1, 0, rect, cnt); 
            return;
        }
        
        // If (r, c) is already covered => move to next point (r, c+1)
        if (rect[r][c]) {
            dfs(r, c+1, rect, cnt);
            return;
        }
        
        // Try all the possible size of squares starting from (r, c)
        // start from the largest possible
        for (int k = Math.min(n-r, m-c); k >= 1 && isAvailable(rect, r, c, k); k--) {
            cover(rect, r, c, k);
            dfs(r, c+1, rect, cnt+1);
            uncover(rect, r, c, k);
        }
    }

    // Check if the area [r, ..., r+k][c, ..., c+k] is alreadc covered
    private boolean isAvailable(boolean[][] rect, int r, int c, int k) {
        for (int i = 0; i < k; i++){
            for(int j = 0; j < k; j++){
                if(rect[r+i][c+j]) return false;
            }
        }
        return true;
    }

    // Cover the area [r, ..., r+k][c, ..., c+k] with a k * k square
    private void cover(boolean[][] rect, int r, int c, int k) {
        for(int i = 0; i < k; i++){
            for(int j = 0; j < k; j++){
                rect[r+i][c+j] = true;
            }
        }
    }
    
    // Uncover the area [r, ..., r+k][c, ..., c+k]
    private void uncover(boolean[][] rect, int r, int c, int k) {
        for(int i = 0; i < k; i++){
            for(int j = 0; j < k; j++){
                rect[r+i][c+j] = false;
            }
        }
    }
}

=======================================================================================================
Method 2:

Method:

	-	idea: fill the entire block bottom up. In every step, find the lowest left unfilled square
	    first, and select a square with different possible sizes to fill it. 

	-	We maintain a height array (skyline) with length n while dfs. This skyline is the identity 
	    of the state. 
	    The final result we ask for is the minimum number of squares for the state [m, m, m, m, m, m, m]
	    (The length of this array is n). 

	- 	pruned or optimized by the following three methods:
			1. When the current cnt (that is, the number of squares) of this skyline has exceeded 
			   the value of the current global optimal solution, then return directly.
			2. When the current skyline has been traversed, and the previous cnt is smaller than 
			   the current cnt, then return directly.
			3. !!! When we find the empty square in the lowest left corner, we pick larger size for 
			   the next square first. 
			   This can make the program converge quickly. (It is a very important optimization)


Stats:

	- 
	- 

 class Solution {
    Map<Long, Integer> map = new HashMap<>();
    int res = Integer.MAX_VALUE;

    // main function
    public int tilingRectangle(int n, int m) {
        if (n == m) 
        	return 1;

        if (n > m) 
        	return tilingRectangle(m, n);

        dfs(n, m, new int[n], 0);
        return res;
    }
    
    private void dfs(int n, int m, int[] h, int cnt) {
        
    	//When the current cnt exceeded current global optimal solution, return directly.
        if (cnt >= res) 
        	return;

        int pos = -1, minH = Integer.MAX_VALUE;
        long key = 0, base = 1;
        for (int i = 0; i < n; i++) {
            key += h[i] * base;
            base *= m + 1;
            if (h[i] < minH) {
                pos = i;
                minH = h[i];
            }
        }

        if (minH == m) {
            res = Math.min(cnt, res);
            return;
        }

        if (map.containsKey(key) && map.get(key) <= cnt) 
        	return;

        map.put(key, cnt);
        int end = pos;
        while (end + 1 < n && h[end + 1] == h[pos] && (end + 1 - pos + 1 + minH) <= m) 
        	end++;
        for (int j = end; j >= pos; j--) {
            int[] next = h.clone();
            for (int k = pos; k <= j; k++) next[k] += j - pos + 1;  //mew added squre edge length
            dfs(n, m, next, cnt + 1);
        } 
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



