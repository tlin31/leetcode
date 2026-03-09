1249. Minimum Remove to Make Valid Parentheses - Medium

Given a string s of '(' , ')' and lowercase English characters.

Your task is to remove the minimum number of parentheses ( '(' or ')', in any positions ) so that the resulting parentheses string is valid and return any valid string.

Formally, a parentheses string is valid if and only if:

It is the empty string, contains only lowercase characters, or
It can be written as AB (A concatenated with B), where A and B are valid strings, or
It can be written as (A), where A is a valid string.
 

Example 1:

Input: s = "lee(t(c)o)de)"
Output: "lee(t(c)o)de"
Explanation: "lee(t(co)de)" , "lee(t(c)ode)" would also be accepted.
Example 2:

Input: s = "a)b(c)d"
Output: "ab(c)d"
Example 3:

Input: s = "))(("
Output: ""
Explanation: An empty string is also valid.
 

Constraints:

1 <= s.length <= 105
s[i] is either '(' , ')', or lowercase English letter.


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

	-	



Stats:

	- time & space: O(n)
	- 

public String minRemoveToMakeValid(String s) {
    Set<Integer> indexesToRemove = new HashSet<>();
    Deque<Integer> stack = new ArrayDeque<>();
    
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (c == '(') {
            stack.push(i);
        } else if (c == ')') {
            if (stack.isEmpty()) {
                indexesToRemove.add(i); // Unmatched ')'
            } else {
                stack.pop(); // Found a pair
            }
        }
    }
    // Any remaining indices in stack are unmatched '('
    while (!stack.isEmpty()) 
    	indexesToRemove.add(stack.pop());
    
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
        if (!indexesToRemove.contains(i)) {
            sb.append(s.charAt(i));
        }
    }
    return sb.toString();
}



Python Solution (Two-Pass with Counter)
A highly efficient Python approach uses two passes and a counter to avoid the overhead 
of a stack and set

if sees "(", count++， str append“（“
if sees ")", count--, 
	if count>0, valid, str append ")"
	if count<0, ）在这里不合法，什么都不做到下一轮
其他非括号，添加

def minRemoveToMakeValid(s: str) -> str:
    # First pass: remove unmatched ')' from left to right
    first_pass = []
    balance = 0
    for char in s:
        if char == '(':
            balance += 1
            first_pass.append(char)

        elif char == ')':
            if balance > 0:
                balance -= 1
                first_pass.append(char)
        else:
            first_pass.append(char)
            
    # Second pass: remove unmatched '(' from right to left
    result = []
    balance = 0
    for char in reversed(first_pass):
        if char == ')':
            balance += 1
            result.append(char)
        elif char == '(':
            if balance > 0:
                balance -= 1
                result.append(char)
        else:
            result.append(char)
            
    return "".join(reversed(result))


