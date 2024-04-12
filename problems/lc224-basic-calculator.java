224. Basic Calculator - Hard

Implement a basic calculator to evaluate a simple expression string.

The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, 
non-negative integers and empty spaces .

Example 1:
Input: "1 + 1"
Output: 2


Example 2:
Input: " 2-1 + 2 "
Output: 3
Example 3:

Input: "(1+(4+5+2)-3)+(6+8)"
Output: 23

******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- 
	- 

stats:

	- 
	- 

public static int calculate(String s) {
	int len = s.length(), sign = 1, result = 0;
	Stack<Integer> stack = new Stack<Integer>();
	for (int i = 0; i < len; i++) {
		if (Character.isDigit(s.charAt(i))) {
			int sum = s.charAt(i) - '0';
			while (i + 1 < len && Character.isDigit(s.charAt(i + 1))) {
				sum = sum * 10 + s.charAt(i + 1) - '0';
				i++;
			}
			result += sum * sign;
		} else if (s.charAt(i) == '+')
			sign = 1;
		else if (s.charAt(i) == '-')
			sign = -1;
		else if (s.charAt(i) == '(') {
			stack.push(result);
			stack.push(sign);
			result = 0;
			sign = 1;
		} else if (s.charAt(i) == ')') {
			result = result * stack.pop() + stack.pop();
		}

	}
	return result;
}


=======================================================================================================
method 2:

method:

	- a lot faster
	- 

stats:

	- Runtime: 1 ms, faster than 100.00% of Java online submissions for Basic Calculator.
	- Memory Usage: 39.6 MB, less than 72.31%



class Solution {
    int i = 0;

    public int calculate(String s) {
        i=0;
        s = "(" + s + ")";
        return helper(s);
    }

    int helper(String s) {
        int num =0;
        int val = 0;
        int presign = 1;
        while(i < s.length()) {
            char c = s.charAt(i);
            if(c==' ') {
                i++;
            }else if(c=='+') {
                val = val + presign * num;

                //reset
                num=0;
                presign = 1;
                i++;
            }else if(c=='-') {
                val = val + presign * num;
                num=0;
                presign = -1;
                i++;
            }else if(c=='(') {
                i++; 
                val = val + presign * helper(s);
            }else if(c==')') {
                val = val + presign * num;
                i++;
                // return to prev recursion stack!!
                return val;
            }else {
                int n = c-'0';
                num = num * 10 + n;
                i++;
            }
        }
        return val;
    }
}

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



