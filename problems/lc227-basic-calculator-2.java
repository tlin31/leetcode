227. Basic Calculator II - Medium

Implement a basic calculator to evaluate a simple expression string.

The expression string contains only non-negative integers, +, -, *, / operators and empty spaces . 
The integer division should truncate toward zero.

Example 1:

Input: "3+2*2"
Output: 7
Example 2:

Input: " 3/2 "
Output: 1
Example 3:

Input: " 3+5/2 "
Output: 5
Note:

You may assume that the given expression is always valid.
Do not use the eval built-in library function.


******************************************************
key:
	- stack or iterative
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

	- Runtime: 17 ms, faster than 61.61% of Java online submissions for Basic Calculator II.
Memory Usage: 37.7 MB, less than 83.58% 
	- 


public int calculate(String s) {
    int len;

    if(s==null || (len = s.length())==0) return 0;

    Stack<Integer> stack = new Stack<Integer>();
    int num = 0;
    char prevSign = '+';
    for(int i=0;i<len;i++){
        if(Character.isDigit(s.charAt(i))){
            num = num*10+s.charAt(i)-'0';
        }
        if((!Character.isDigit(s.charAt(i)) &&' '!=s.charAt(i)) || i==len-1){
            if(prevSign=='-'){
                stack.push(-num);
            }
            if(prevSign=='+'){
                stack.push(num);
            }
            if(prevSign=='*'){
                stack.push(stack.pop()*num);
            }
            if(prevSign=='/'){
                stack.push(stack.pop()/num);
            }
            prevSign = s.charAt(i);
            num = 0;
        }
    }

    int re = 0;
    for(int i:stack){
        re += i;
    }
    return re;
}


=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- Runtime: 9 ms, faster than 93.69% of Java online submissions for Basic Calculator II.
	- Memory Usage: 36.1 MB, less than 100.00%

public int calculate(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        
        s = s.trim();
        
        long prevNum = 0;
        int sum = 0;
        char prevOp = '+';
        
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == ' ') continue;
            if(Character.isDigit(c)) {
                int val = c - '0';
                while(i + 1 < s.length() && Character.isDigit(s.charAt(i+1))) {
                    val = val * 10 + (s.charAt(i+1) - '0');
                    i++;
                }
                if (prevOp == '+') {
                    sum += prevNum;
                    prevNum = val;
                } else if (prevOp == '-') {
                    sum += prevNum;
                    prevNum = -val;
                } else if (prevOp == '*') {
                    prevNum = prevNum * val;
                } else if (prevOp == '/') {
                    prevNum = prevNum/val;
                }
            } else {
                prevOp = c;
            }
        }
        
        sum += prevNum;
        return sum;
    }

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



