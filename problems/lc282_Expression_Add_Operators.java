282. Expression Add Operators - Hard

Given a string num that contains only digits and an integer target, return all possibilities to 
insert the binary operators '+', '-', and/or '*' between the digits of num so that the resultant 
expression evaluates to the target value.

Note that operands in the returned expressions should not contain leading zeros.

 

Example 1:

Input: num = "123", target = 6
Output: ["1*2*3","1+2+3"]
Explanation: Both "1*2*3" and "1+2+3" evaluate to 6.
Example 2:

Input: num = "232", target = 8
Output: ["2*3+2","2+3*2"]
Explanation: Both "2*3+2" and "2+3*2" evaluate to 8.
Example 3:

Input: num = "3456237490", target = 9191
Output: []
Explanation: There are no expressions that can be created from "3456237490" to evaluate to 9191.
 

Constraints:

1 <= num.length <= 10
num consists of only digits.
-231 <= target <= 231 - 1


******************************************************
key:
	- single digit? order matters?
	- 1 * 23 - 4 + 5 + 6 = 30 也算一种
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

	-	edge case:
	1: overflow: we use a long type once it is larger than Integer.MAX_VALUE or minimum, we get over it.
	2: 0 sequence: because we can't have numbers with multiple digits started with zero, we have to deal with it too.
	3:a little trick is that we should save the value that is to be multiplied in the next recursion.

Note:  adding String is extremely expensive. always use StringBuilder or char array instead.

solution: 
	We use backtracking to generate all possible expressions by adding +, -, * to between the digits of s string.
	There is no priority since there are no parentheses ( and ) in our s string, so we can evaluate the expression on the fly to save time.
	There are total 3 operators:
	+ operator: newResult = resSoFar + num
	- operator: newResult = resSoFar - num.
	* operator: We need to keep the prevNum so that to calculate newResult we need to minus prevNum then plus with prevNum * num. So newResult = resSoFar - prevNum + prevNum * num.

The way we incorporate these partitions is by considering a 4th operator as well which simply moves 
one step forward and extends the current operand by one digit. Essentially, going from 12 --> 123 
is a NO OP operand in our implementation. (12 * 10) + 3.


 Stats:

	- 
	- 



---------------------------------------

public List<String> addOperators(String num, int target) {
    List<String> res = new ArrayList<>();
   	StringBuilder sb = new StringBuilder();
    dfs(res, sb, num, 0, target, 0, 0);
    return res;
    
}
public void dfs(List<String> res, StringBuilder sb, String num, int pos, int target, long prev, 
	long multi) { 

	if(pos == num.length()) {
		if(target == prev) res.add(sb.toString());
		return;
	}

	for(int i = pos; i < num.length(); i++) {
		// 0不能作为数字的开头
		if(num.charAt(pos) == '0' && i != pos) break;

		//考虑多位，比如123 --> 1 & 23
		long curr = Long.parseLong(num.substring(pos, i + 1));
		int len = sb.length();

		if(pos == 0) {
			dfs(res, sb.append(curr), num, i + 1, target, curr, curr); 
			sb.setLength(len);
		} 

		else {
			dfs(res, sb.append("+").append(curr), num, i + 1, target, prev + curr, curr); 
			sb.setLength(len);

			dfs(res, sb.append("-").append(curr), num, i + 1, target, prev - curr, -curr); 
			sb.setLength(len);

			dfs(res, sb.append("*").append(curr), num, i + 1, target, prev - multi + multi * curr, multi * curr); 
			sb.setLength(len);
		}
	}
}







