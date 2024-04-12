320. Generalized Abbreviation - Medium

Write a function to generate the generalized abbreviations of a word. 

Note: The order of the output does not matter.

Example:

Input: "word"
Output:
["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", 
"2r1", "3d", "w3", "4"]


******************************************************
key:
	- Backtrack
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	for every character, we can keep it or abbreviate it.
		1) To keep it, we add it to the current solution and carry on backtracking. 
		2) To abbreviate it, we omit it in the current solution, but increment the count, which 
			indicates how many characters have we abbreviated. 
	-	When we reach the end or need to put a character in the current solution, and count is 
	    bigger than zero, we add the number into the solution.


Stats:

	- Time complexity : O(n*2^n) For each call to backtrack, it either returns without branching, 
	  					or it branches into two recursive calls. 
	  					All these recursive calls form a complete binary recursion tree with 2^n 
	  					leaves and 2^n - 1 inner nodes. 
	  					For each leaf node, it needs O(n) time for converting builder to String; for
	  					each internal node, it needs only constant time. 
	- Space complexity : O(n). If the return list doesn't count, we only need O(n) auxiliary space 
						to store the characters in StringBuilder and the O(n) space used by system
						stack. 
						In a recursive program, the space of system stack is linear to the maximum
						recursion depth which is nn in our problem.



public List<String> generateAbbreviations(String word) {
    List<String> res = new ArrayList<>();
    DFS(res, new StringBuilder(), word.toCharArray(), 0, 0);
    return res;
}

public void DFS(List<String> res, StringBuilder sb, char[] c, int i, int num) {
    int len = sb.length();  
    if(i == c.length) {
        if(num != 0) sb.append(num);
        res.add(sb.toString());
    } else {
        DFS(res, sb, c, i + 1, num + 1);               // abbr c[i]

        if(num != 0) sb.append(num);                   // not abbr c[i]
        DFS(res, sb.append(c[i]), c, i + 1, 0);        
    }

    // recover 
    sb.setLength(len); 
}



=======================================================================================================
method 2:

Method:

	-	
	-	


Stats:

	- 
	- 



=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



