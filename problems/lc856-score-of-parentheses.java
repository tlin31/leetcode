856. Score of Parentheses - Medium


Given a balanced parentheses string S, compute the score of the string based on the following rule:

	1. () has score 1
	2. AB has score A + B, where A and B are balanced parentheses strings.
	3. (A) has score 2 * A, where A is a balanced parentheses string.
 

Example 1:

Input: "()"
Output: 1

Example 2:

Input: "(())"
Output: 2


Example 3:

Input: "()()"
Output: 2


Example 4:

Input: "(()(()))"
Output: 6
 

Note:

S is a balanced parentheses string, containing only ( and ).
2 <= S.length <= 50




******************************************************
key:
	- stack, array, count layers
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:Stack


Stats:

	- 		Complexity: O(N) time and O(N) space
	- 


Method:

	-	cur record the score at the current layer level.

		If we meet '(',
		we push the current score to stack,
		enter the next inner layer level,
		and reset cur = 0.

		If we meet ')',
		the cur score will be doubled and will be at least 1.
		We exit the current layer level,
		and set cur = stack.pop() + cur *2


    public int scoreOfParentheses(String S) {
        Stack<Integer> stack = new Stack<>();
        int cur = 0;
        for (char c : S.toCharArray()) {
            if (c == '(') {
                stack.push(cur);
                cur = 0;
            } else {
                cur = stack.pop() + Math.max(cur * 2, 1);
            }
        }
        return cur;
    }


    def scoreOfParentheses(self, S):
        stack, cur = [], 0
        for i in S:
            if i == '(':
                stack.append(cur)
                cur = 0
            else:
                cur += stack.pop() + max(cur, 1)
        return cur



Approach 1: Array

We change a pointer instead of pushing/popping repeatedly.

Complexity: O(N) time and O(N) space


    public int scoreOfParentheses(String S) {
        int res[] = new int[30], 
            i = 0;
        for (char c : S.toCharArray())
            if (c == '(') 
            	res[++i] = 0;
            else res[i - 1] += Math.max(res[i--] * 2, 1);
        return res[0];
    }


    def scoreOfParentheses(self, S):
        res, i = [0] * 30, 0
        for c in S:
            i += 1 if c == '(' else -1
            res[i] = res[i] + max(res[i + 1] * 2, 1) if c == ')' else 0
        return res[0]


Approach 2: O(1) Space

We count the number of layers.
If we meet '(' layers number l++
else we meet ')' layers number l--

If we meet "()", we know the number of layer outside,
so we can calculate the score res += 1 << l, which is res += 2^l



    public int scoreOfParentheses(String S) {
        int res = 0, l = 0;
        for (int i = 0; i < S.length(); ++i) {
            if (S.charAt(i) == '(') 
            	l++; 
            else 
            	l--;
            if (S.charAt(i) == ')' && S.charAt(i - 1) == '(') 
            	res += 1 << l;
        }
        return res;
    }





    def scoreOfParentheses(self, S):
        res = l = 0
        for a, b in itertools.izip(S, S[1:]):
            if a + b == '()': res += 2 ** l
            l += 1 if a == '(' else -1
        return res



~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	










~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

