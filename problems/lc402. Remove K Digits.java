402. Remove K Digits - Medium

Given string num representing a non-negative integer num, and an integer k, return the 
smallest possible integer after removing k digits from num.

 

Example 1:

Input: num = "1432219", k = 3
Output: "1219"
Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.
Example 2:

Input: num = "10200", k = 1
Output: "200"
Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.
Example 3:

Input: num = "10", k = 2
Output: "0"
Explanation: Remove all the digits from the number and it is left with nothing which is 0.
 

Constraints:

1 <= k <= num.length <= 105
num consists of only digits.
num does not have any leading zeros except for the zero itself.

******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:Greedy with Stack

Method:

given two sequences of digit of the same length, it is the leftmost distinct digits that 
determine the superior of the two numbers, e.g. for A = 1axxx, B = 1bxxx, if the digits 
a > b, then A > B.

With this insight, the first intuition we got for our problem is that we should iterate 
from the left to right, when removing the digits. The more a digit to the left-hand side, 
the more weight it carries.

rule: 
给定一个数字序列 [D1 D2 D3 ...Dn]，如果数字 D2 小于其左侧邻居 D1，那么我们应该移除左侧邻居 (D1) 以获得最小值。




Stats:

	- 
	- 

Input: num = "1432219", k = 3
Output: "1219"

public class Solution {
    public String removeKdigits(String num, int k) {
        int len = num.length();

        //corner case
        if(k==len)        
            return "0";
            
        Stack<Character> stack = new Stack<>();
        int i =0;
        while(i<num.length()){
            //whenever meet a digit which is less than the previous digit, discard the previous one
            while(k>0 && !stack.isEmpty() && stack.peek()>num.charAt(i)){
                stack.pop();
                k--;
            }
            stack.push(num.charAt(i));
            i++;
        }
        
        // corner case like "1111"
        while(k>0){
            stack.pop();
            k--;            
        }
        
        //construct the number from the stack
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty())
            sb.append(stack.pop());
        sb.reverse();
        
        //remove all the 0 at the head
        while(sb.length()>1 && sb.charAt(0)=='0')
            sb.deleteCharAt(0);
        return sb.toString();
    }
}






