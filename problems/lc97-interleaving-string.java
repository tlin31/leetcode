97. Interleaving String - Hard

Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.

Example 1:

Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
Output: true

Example 2:
Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
Output: false


******************************************************
key:
	- dfs, bfs, or DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************


一、一句话结论（先抓住本质）

    判断 s3 是否能由 s1 和 s2 保持各自字符相对顺序 交错组成。👉 不是子串问题，不是排列问题，是“顺序不变的合并”


二、为什么一定是 DP？

    每个字符 只能来自 s1 或 s2, 当前位置能不能成立，只和前一步是否成立有关 👉 典型二维 DP


三、DP 状态定义（面试最重要）

dp[i][j] = 是否能用 s1 的前 i 个字符 和 s2 的前 j 个字符 
            组成 s3 的前 i + j 个字符


    📌 索引统一：

    s1[i - 1]

    s2[j - 1]

    s3[i + j - 1]

四、状态转移方程（核心）
    两种可能来源

    情况 1：s3 当前字符来自 s1
        dp[i-1][j] == true
        AND
        s1[i-1] == s3[i+j-1]

    情况 2：s3 当前字符来自 s2
        dp[i][j-1] == true
        AND
        s2[j-1] == s3[i+j-1]

    合并
    dp[i][j] =
        (dp[i-1][j] && s1[i-1] == s3[i+j-1]) ||
        (dp[i][j-1] && s2[j-1] == s3[i+j-1]);

五、Base Case（别写错）
    dp[0][0] = true; // 空 + 空 = 空

    第一行（只用 s2）
    dp[0][j] = dp[0][j-1] && s2.charAt(j-1) == s3.charAt(j-1);

    第一列（只用 s1）
    dp[i][0] = dp[i-1][0] && s1.charAt(i-1) == s3.charAt(i-1);



class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {

        if (s1.length() + s2.length() != s3.length()) return false;

        int m = s1.length(), n = s2.length();
        boolean[][] dp = new boolean[m + 1][n + 1];

        dp[0][0] = true;

        // s1 only
        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);
        }

        // s2 only
        for (int j = 1; j <= n; j++) {
            dp[0][j] = dp[0][j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
        }

        // general case
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char c = s3.charAt(i + j - 1);
                dp[i][j] =
                    (dp[i - 1][j] && s1.charAt(i - 1) == c) ||
                    (dp[i][j - 1] && s2.charAt(j - 1) == c);
            }
        }

        return dp[m][n];
    }
}





=======================================================================================================
Method 1:	DFS + memo

Method:

	- To solve this problem, lets look at if s1[0 ~ i] s2[0 ~ j] can be interleaved to s3[0 ~ k].
		1. Start from indices 0, 0, 0 and compare s1[i] == s3[k] or s2[j] == s3[k]
		2. Return valid only if either i or j match k and the remaining is also valid
	- Only need to cache invalid[i][j] since most of the case s1[0 ~ i] and s2[0 ~ j] does not 
      form s3[0 ~ k]. 
	- Also tested caching valid[i][j] the run time is also 1ms

Stats:

	- 
	- 



public boolean isInterleave(String s1, String s2, String s3) {
    char[] c1 = s1.toCharArray(), 
           c2 = s2.toCharArray(), 
           c3 = s3.toCharArray();
	int m = s1.length(), 
        n = s2.length();
	if(m + n != s3.length()) 
        return false;
	return dfs(c1, c2, c3, 0, 0, 0, new boolean[m + 1][n + 1]);
}

public boolean dfs(char[] c1, char[] c2, char[] c3, int i, int j, int k, boolean[][] invalid) {
	if(invalid[i][j]) 
        return false;

	// reach the end
	if(k == c3.length) 
        return true;

	boolean valid = 
	    i < c1.length && c1[i] == c3[k] && dfs(c1, c2, c3, i + 1, j, k + 1, invalid) || 
        j < c2.length && c2[j] == c3[k] && dfs(c1, c2, c3, i, j + 1, k + 1, invalid);

	if(!valid) 
		invalid[i][j] = true;

    return valid;
}

---------------------

BFS:

    -  Imagine a grid with x-axis and y-axis are s1 and s2, matching s3 is the same as finding a 
       path from (0,0) to (len1, len2). 
    -  use a HashSet of coordinates to eliminate duplicated paths.

public class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        int len1 = s1.length(),
            len2 = s2.length(),
            len3 = s3.length();

        if (len1 + len2 != len3) 
            return false;

        Deque<Integer> queue = new LinkedList<>();
        int matched = 0;
        queue.offer(0);
        Set<Integer> set = new HashSet<>();

        while (queue.size() > 0 && matched < len3) {

            // size of this level
            int size = queue.size();

            for (int i = 0; i < size; i++) {

                int p1 = queue.peek() / len3,
                    p2 = queue.peek() % len3;

                queue.poll();

                if (p1 < len1 && s1.charAt(p1) == s3.charAt(matched)) {
                    int key = (p1 + 1) * len3 + p2;
                    if (!set.contains(key)) {
                        set.add(key);
                        queue.offer(key);
                    }
                }
                if (p2 < len2 && s2.charAt(p2) == s3.charAt(matched)) {
                    int key = p1 * len3 + (p2 + 1);
                    if (!set.contains(key)) {
                        set.add(key);
                        queue.offer(key);
                    }
                }
            }
            matched++;
        }
        return queue.size() > 0 && matched == len3;
    }
}
=======================================================================================================
method 2:   DP

Method:

	-
	-	


Stats:

	- a straight-forward solution should be Time: O(nm), and Space: O(nm). 
      And space could optimize since optimal[i+1][] only depends on optimal[i][], so could 
      use Space O(m)

	- 

public class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        int len1 = s1.length(),
            len2 = s2.length(),
            len3 = s3.length();
            
        if (len1 + len2 != len3) 
        	return false;

        // cache[i][j] == true means first i + j chars are matched by
        // first j chars from s1 and first i chars from s2
        boolean[][] cache = new boolean[len2 + 1][len1 + 1];

        
        cache[0][0] = true; // empty and empty matches empty
        int m3 = 1; // matched length, m1 and m2 are similar
        while (m3 <= len3) {
            // this loop fill in cache matrix from left-top to right-bottom, diagonally.
            // note that loop conditions are pretty tricky here.
            for (int m1 = Math.max(m3 - len2, 0); m1 <= len1 && m1 <= m3; m1++) {
                int m2 = m3 - m1;
                cache[m2][m1] =
                    m1 > 0 && cache[m2][m1 - 1] && s3.charAt(m3 - 1) == s1.charAt(m1 - 1) ||
                    m2 > 0 && cache[m2 - 1][m1] && s3.charAt(m3 - 1) == s2.charAt(m2 - 1);
            }
            m3++;
        }
        return cache[len2][len1];
    }
}

Using dp to tracking select i-th seq and j-th seq in s1 and s2 could match i+j+2 th seq in s3

init optimal[0][*] by check no select from s1
init optimal[*][0] by check no select from s2
do search for every i-th and j-th, could match when meet one of the following two cases:
    if i-th char in s2 equals to the (i+j+1)th char in s3, and previous j-th in s2 is matched 
        without i-th char
    if j-th char in s1 equals to the (i+j+1)th char in s3, and previous j-1th in s2 is matched 
        with i-th char

public static boolean isInterleaveOptz(String s1, String s2, String s3) {
    if (s3.length() != s1.length() + s2.length()) return false;

    boolean[] optimal = new boolean[s2.length() + 1];    //dp optimal
    optimal[0] = true;

    for (int j = 0; j < s2.length(); j++) { //check no s1 char is selected, if s2 could equals to s3
        if (optimal[j] && s2.charAt(j) == s3.charAt(j)) 
            optimal[j + 1] = true;
    }

    for (int i = 0; i < s1.length(); i++) { //check select i-th char in s1
        //no char in s2 is selected
        if (optimal[0] && s1.charAt(i) == s3.charAt(i)) 
            optimal[0] = true;    
        else 
            optimal[0] = false;

        for (int j = 0; j < s2.length(); j++) {  //select j-th char
            if ((s1.charAt(i) == s3.charAt(i + j + 1) && optimal[j + 1]) ||
                    s2.charAt(j) == s3.charAt(i + j + 1) && optimal[j]) {
                optimal[j + 1] = true;
            } else 
                optimal[j + 1] = false;
        }
    }
    return optimal[s2.length()];
}

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



