964. Least Operators to Express Number - Hard


Given a single positive integer x, we will write an expression of the form x (op1) x (op2) x (op3) x ...
where each operator op1, op2, etc. is either addition, subtraction, multiplication, or division (+, -, *, or /).  

For example, with x = 3, we might write 3 * 3 / 3 + 3 - 3 which is a value of 3.

When writing such an expression, we adhere to the following conventions:

	1. The division operator (/) returns rational numbers.
	2. There are no parentheses placed anywhere.
	3. multiplication and division happens before addition and subtraction.
	4. It is not allowed to use the unary negation operator (-).  

We would like to write an expression with the least number of operators such that the expression 
equals the given target.  

Return the least number of operators used.

 

Example 1:
Input: x = 3, target = 19
Output: 5
Explanation: 3 * 3 + 3 * 3 + 3 / 3.  The expression contains 5 operations.

Example 2:
Input: x = 5, target = 501
Output: 8
Explanation: 5 * 5 * 5 * 5 - 5 * 5 * 5 + 5 / 5.  The expression contains 8 operations.

Example 3:
Input: x = 100, target = 100000000
Output: 3
Explanation: 100 * 100 * 100 * 100.  The expression contains 3 operations.
 

Note:

2 <= x <= 100
1 <= target <= 2 * 10^8


******************************************************
key:
	- dfs + memo
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

class Solution {
    Map<Integer, Integer> memo = new HashMap<>();

    public int leastOpsExpressTarget(int x, int target) {
        if (target == 1) 
            return x == 1 ? 0 : 1;

        if (memo.containsKey(target))
            return memo.get(target);

        long product = x;
        int count = 0;

        // if haven't reach the target, first try multiple it
        while (product < target) {
            count++;
            product *= x;
            
        }
        
        // candidate1 : in the form : x*x*...*x - (......) = target
        int cand1 = Integer.MAX_VALUE;
        if (product == target)
            cand1 = count;

        else if (product - target < target)
            cand1 = 1 + count + leastOpsExpressTarget(x, (int)(product - target));
        
        // candidate2 : in the form : x*x*...*x + (......) = target
        int cand2 = Integer.MAX_VALUE;
        product /= x;

		// means x is >= target, so we use x / x + (......) to continue the search, there are two 
		// more operators which are "/" and "+", so we add 2. 
        if (count == 0)
        	cand2 = 2 + leastOpsExpressTarget(x, (int)(target - product));
        
        else
        	// If count != 0, count-1 represents the number of operators used in x * x * ... * x, 
        	// we need one more operator "+" to connect it with (......), so we should add count - 1 + 1 
        	cand2 = count + leastOpsExpressTarget(x, (int)(target - product));
        

        int res = Math.min(cand1, cand2);
        memo.put(target, res);
        return res;
    }
}


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



