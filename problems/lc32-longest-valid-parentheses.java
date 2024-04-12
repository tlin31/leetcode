32. Longest Valid Parentheses --- Hard

Given a string containing just the characters '(' and ')', find the length of the longest valid 
(well-formed) parentheses substring.

Example 1:
Input: "(()"
Output: 2
Explanation: The longest valid parentheses substring is "()"

Example 2:
Input: ")()())"
Output: 4
Explanation: The longest valid parentheses substring is "()()"


******************************************************
key:
	- stack
	- edge case, empty string, return empty

******************************************************



=======================================================================================================
method 1:

method:
    - stack,从左到右扫描字符串，栈顶保存当前扫描的时候，合法序列前的一个位置位置下标是多少
        我们扫描到左括号，就将当前位置入栈。
        扫描到右括号，就将栈顶出栈（代表栈顶的左括号匹配到了右括号），然后分两种情况。
            1. 栈不空，那么就用当前的位置减去栈顶的存的位置，然后就得到当前合法序列的长度，然后更新最长长度。
            2. 栈是空的，说明之前没有与之匹配的左括号，那么就将当前的位置入栈。
    - record index!

stats:
    - Runtime: 6 ms, faster than 51.96% of Java online submissions for Longest Valid Parentheses.
    - Memory Usage: 37.6 MB, less than 80.39%
    - 时间复杂度： O（n）。
    - 空间复杂度：O（n）。

public int longestValidParentheses(String s) {
    int maxans = 0;
    Stack<Integer> stack = new Stack<>();
    stack.push(-1);
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '(') {
            stack.push(i);
        } else {
            stack.pop();

            // doesn't have a match
            if (stack.empty()) {
                stack.push(i);
            } else {
                maxans = Math.max(maxans, i - stack.peek());
            }
        }
    }
    return maxans;
}



=======================================================================================================
method 2:

method:
    - left 和 right 保存的当前的左括号和右括号的个数，都初始化为 0 。
    - 如果左括号个数等于右括号个数了，那么就更新合法序列的最长长度。
    - 如果左括号个数大于右括号个数了，那么就接着向右边扫描。
    - 如果左括号数目小于右括号个数了，那么后边无论是什么，此时都不可能是合法序列了，
      此时 left 和 right 归 0，然后接着扫描。
    - 从左到右扫描完毕后，同样的方法从右到左再来一次，因为类似这样的情况 ( ( ( ) ) ，
      如果从左到右扫描到最后，left = 3，right = 2，期间不会出现 left == right。但是如果从右向左扫描，
      扫描到倒数第二个位置的时候，就会出现 left = 2，right = 2 ，就会得到一种合法序列。

stats:
    - Runtime: 1 ms, faster than 100.00% of Java online submissions for Longest Valid Parentheses.
    - Memory Usage: 36.2 MB, less than 100.00% 
    - 时间复杂度：O（n）。
    - 空间复杂度：O（1）。

public int longestValidParentheses(String s) {
    int left = 0, right = 0, maxlength = 0;
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '(') {
            left++;
        } 
        else {
            right++;
        }

        // valid sequence
        if (left == right) {
            maxlength = Math.max(maxlength, 2 * right);
        } 
        // break the sequence, because never happen ())
        else if (right >= left) {
            left = right = 0;
        }
    }

    left = right = 0;
    for (int i = s.length() - 1; i >= 0; i--) {
        if (s.charAt(i) == '(') {
            left++;
        } 
        else {
            right++;
        }


        if (left == right) {
            maxlength = Math.max(maxlength, 2 * left);
        } 
        else if (left >= right) {
            left = right = 0;
        }
    }
    return maxlength;
}




=======================================================================================================
method 3: DP

method:
	- dp[i] = length of the longest valid substring ending at i-th index. 

        We initialize the complete dp array with 0's. Now, it's obvious that the valid substrings 
        must end with ‘)’. 
        This further leads to the conclusion that the substrings ending with ‘(’ will always 
        contain '0' at their corresponding dp indices. 

        Thus, we update the dp array ONLY when ‘)’ is encountered.


		1. s[i]=‘)’ and s[i−1]=‘(’ ----> .......()

            dp [ i ] = dp [ i - 2] + 2 （前一个合法序列的长度，加上当前新增的长度 2）
		    ex. dp [ 3 ] = dp [ 3 - 2 ] + 2 = dp [ 1 ] + 2 = 2 + 2 = 4

		2. s[i]=‘)’ and s[i−1]=‘)’, --> .......))
            
            if s[i−dp[i−1]−1]=‘(’ then:

                dp[i]=dp[i−1]+dp[i−dp[i−1]−2]+2

            The reason behind this is that if the 2nd last ‘)’ was a part of a valid substring 
            (say sub_s), for the last ‘)’ to be a part of a larger substring, there must be a 
            corresponding starting ‘(’ which lies before the valid substring of which the 2nd last 
            ‘)’ is a part (i.e. before sub_s). Thus, if the character before sub_s happens to be ‘(’, 
            we update the dp[i] as an addition of 2 in the length of sub_s which is dp[i−1]. 
            To this, we also add the length of the valid substring just before the term "(,sub_s,)" , 
            i.e. dp[i−dp[i−1]−2].
		  
            需要判断 i - dp[i - 1] - 1 （前一个合法序列的前边一个位置） 是不是左括号。

            ()()(())
            01234567

            例如上图的 i = 7 的时候，此时 i - 1 也是右括号，我们需要知道 
              i - dp[i - 1] - 1 = 7 - dp [ 6 ] - 1 = 7 - 2 - 1 = 4 位置的括号的情况。

            而刚好 index = 4 的位置是左括号，此时 
                dp [ i ] = dp [ i - 1 ] + dp [ i - dp [ i - 1] - 2 ] + 2 
                (当前位置的前一个合法序列的长度，加上匹配的左括号前边的合法序列的长度，加上新增的长度 2），
                也就是 dp [ 7 ] = dp [ 7 - 1 ] + dp [ 7 - dp [ 7 - 1] - 2 ] + 2 
                               = dp [ 6 ] + dp [7 - 2 - 2] + 2 
                               = 2 + 4 + 2 
                               = 8。

            如果 index = 4 不是左括号，那么此时位置 7 的右括号没有匹配的左括号，所以 dp [ 7 ] = 0 ，不需要更新。

stats:
	- Runtime: 1 ms, faster than 100.00% of Java online submissions for Longest Valid Parentheses.
	- Memory Usage: 37.6 MB, less than 80.39% 
	- 时间复杂度：遍历了一次，O（n）。
	- 空间复杂度：O（n）

public int longestValidParentheses(String s) {
    int maxans = 0;
    int dp[] = new int[s.length()];
    for (int i = 1; i < s.length(); i++) {
        if (s.charAt(i) == ')') {
            //右括号前边是左括号
            if (s.charAt(i - 1) == '(') {
                dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
            } 

            //右括号前边是右括号，并且除去前边的合法序列的前边是左括号
            else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
            }
            maxans = Math.max(maxans, dp[i]);
        }
    }
    return maxans;
}




=======================================================================================================
method 4:




=======================================================================================================
method 5:












