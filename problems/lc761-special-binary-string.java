761. Special Binary String - Hard


Special binary strings are binary strings with the following two properties:

	The number of 0's is equal to the number of 1's.
	Every prefix of the binary string has at least as many 1's as 0's.

Given a special string S, a move consists of choosing two consecutive, non-empty, special substrings 
of S, and swapping them. (Two strings are consecutive if the last character of the first string is
exactly one index before the first character of the second string.)

At the end of any number of moves, what is the lexicographically largest resulting string possible?

Example 1:
Input: S = "11011000"
Output: "11100100"
Explanation:
The strings "10" [occuring at S[1]] and "1100" [at S[3]] are swapped.
This is the lexicographically largest string possible after some number of swaps.
Note:

S has length at most 50.
S is guaranteed to be a special binary string as defined above.


******************************************************
key:
	- hard, dont figure it out yet
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

	1. Split S into several special strings (as many as possible).
	2. Special string starts with 1 and ends with 0. Recursion on the middle part.
	3. Sort all special strings in lexicographically largest order.
	4. Join and output all strings.


	- The middle part of a special string may not be another special string.
		ex. 1M0 is a splitted special string. M is its middle part and it must be another special string.

Because:
	The number of 0's is equal to the number of 1's in M
	If there is a prefix P of M which has one less 1's than 0's, 1P will make up a special string. 
	1P will be found as special string before 1M0 in my solution.
	It means that every prefix of M has at least as many 1's as 0's.
	Based on 2 points above, M is a special string.




	public String makeLargestSpecial(String S) {
        
        int count = 0, i = 0;
        List<String> res = new ArrayList<String>();
        for (int j = 0; j < S.length(); ++j) {
          	if (S.charAt(j) == '1') 
          		count++;
          	else 
          		count--;

          	if (count == 0) {
            	res.add('1' + makeLargestSpecial(S.substring(i + 1, j)) + '0');
            	i = j + 1;
          	}
        }
        Collections.sort(res, Collections.reverseOrder());
        return String.join("", res);
    }




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	

If we map '1' to '(', '0's to ')', a Special-String is essentially Valid-Parentheses, therefore share 
all the properties of a Valid-Parentheses

A VP (Valid-Parentheses) have 2 form:

	1. single nested VP like "(())", or "1100";
	2. a number of consecutive sub-VPs like "()(())", or "101100", which contains "()" + "(())" or 
	   "10" + "1100"

And this problem is essentially ask you to reorder the sub-VPs in a VP to make it bigger. 
If we look at this example : "()(())" or "101100", how would you make it bigger?
Answer is, by moving the 2nd sub-string to the front. Because deeply nested VP contains more 
consecutive '('s or '1's in the front. That will make reordered string bigger.

The above example is straitforward, and no recursion is needed. 
But, what if the groups of sub-VPs are not in the root level?
Like if we put another "()(())" inside "()(())", like "()(( ()(()) ))", in this case we will need 
to recursively reorder the children, make them MVP(Max-Valid-Parentheses), then reorder in root.

To summarize, we just need to reorder all groups of VPs or SSs at each level to make them MVP, 
then reorder higher level VPs;



class IntegerPointer{
    int val;
    public IntegerPointer(int val){
        this.val = val;
    }
}



class Solution {
    public String makeLargestSpecial(String s) {
        IntegerPointer i = new IntegerPointer(0);
        
        return dfs(s.toCharArray(), i);
    }
    
    
    private String dfs(char[] str, IntegerPointer i){
        String res = "";
        List<String> tokens = new ArrayList<>();
        while (i.val < str.length && res.length() == 0){
            if (str[i.val++] == '1') 
            	tokens.add(dfs(str, i));
            else 
            	res += "1";
        }
        
        boolean prefix = res.length() > 0;
        Collections.sort(tokens);
        for (int j = tokens.size()-1; j >= 0; j--) 
        	res += tokens.get(j);
        
        if (prefix) 
        	res += '0';
        return res;
    }
}





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

