20. Valid Parentheses 	--- Easy

Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the 
input string is valid.

An input string is valid if:

Open brackets must be closed by the same type of brackets.
Open brackets must be closed in the correct order.
Note that an empty string is also considered valid.

Example 1:
Input: "()"
Output: true

Example 3:
Input: "(]"
Output: false

Example 4:
Input: "{[]}"
Output: true

******************************************************
key:
    - stack
    - edge case:
        1) empty string, return empty
        2)

******************************************************


=========================================================================================================================================================
method 1:

key:
	- The basic idea is to push the right parentheses ')', ']', or '}' into the stack each time 
	  when we encounter left ones. 
	- And if a right bracket appears in the string, we need check if the stack is empty and also 
	  whether the top element is the same with that right bracket. 
	- If not, the string is not a valid one. At last, we also need check if the stack is empty.


Runtime: 2 ms, faster than 61.27% of Java online submissions for Valid Parentheses.
Memory Usage: 34 MB, less than 100.00% of Java online submissions for Valid Parentheses.

public class Solution {
    public boolean isValid(String s) {
        Stack <Character> stack = new Stack < Character > ();
        // Iterate through string until empty
        for (int i = 0; i < s.length(); i++) {
            // Push any open parentheses onto stack
            if (s.charAt(i) == '(' || s.charAt(i) == '[' || s.charAt(i) == '{')
                stack.push(s.charAt(i));

            // Check stack for corresponding closing parentheses, false if not valid
            else if (s.charAt(i) == ')' && !stack.empty() && stack.peek() == '(')
                stack.pop();

            else if (s.charAt(i) == ']' && !stack.empty() && stack.peek() == '[')
                stack.pop();

            else if (s.charAt(i) == '}' && !stack.empty() && stack.peek() == '{')
                stack.pop();

            else
                return false;
        }

        // return true if no open parentheses left in stack
        return stack.empty();
    }
}

=========================================================================================================================================================
!!!! method 2:

key:
	- At any point of time if stack is empty and present character is a closing bracket, 
	  return false immediately
	- use the array as a stack!!!
	- ends with a non-empty array

// Runtime: 0 ms, faster than 100.00% 
// Memory Usage: 34.2 MB, less than 100.00% 

public class Solution {
    public boolean isValid(String s) {
        char[] stack = new char[s.length()];
        int head = 0;

        for (char c: s.toCharArray()) {
            switch (c) {
                case '(':
                    stack[head++] = ')';
                    break;
                case '{':
                    stack[head++] = '}';
                    break;
                case '[':
                    stack[head++] = ']';
                    break;
                    
                default:
                    if (head == 0 || stack[--head] != c) 
                        return false;
                    break;
            }
        }
        return head == 0;
    }
}

=========================================================================================================================================================
method 3:

key:
    - very slow solution...

    
public class Solution {
    public boolean isValid(String s) {
        int length;
        do {
            length = s.length();
            s = s.replace("()", "").replace("{}", "").replace("[]", "");
        } while (length != s.length());
        return s.length() == 0;
    }
}