784. Letter Case Permutation - Easy

Given a string S, we can transform every letter individually to be lowercase or uppercase to 
create another string.  

Return a list of all possible strings we could create.

Examples:
Input: S = "a1b2"
Output: ["a1b2", "a1B2", "A1b2", "A1B2"]

Input: S = "3z4"
Output: ["3z4", "3Z4"]

Input: S = "12345"
Output: ["12345"]
Note:

S will be a string with length between 1 and 12.
S will consist only of letters or digits.


******************************************************
key:
	- only do so with letter
	- backtrack / BFS / DFS
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	BFS
	-	

Stats:

	- 
	- 


class Solution {
    public List<String> letterCasePermutation(String S) {
        if (S == null) {
            return new LinkedList<>();
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(S);
        
        for (int i = 0; i < S.length(); i++) {
            if (Character.isDigit(S.charAt(i))) 
            	continue;    

            // size --> one level in BFS        
            int size = queue.size();
            for (int j = 0; j < size; j++) {
                String cur = queue.poll();
                char[] chs = cur.toCharArray();
                
                chs[i] = Character.toUpperCase(chs[i]);
                queue.offer(String.valueOf(chs));
                
                chs[i] = Character.toLowerCase(chs[i]);
                queue.offer(String.valueOf(chs));
            }
        }
        
        return new LinkedList<>(queue);
    }
}

=======================================================================================================
method 2:

Method:

	-	
	-	DFS


Stats:

	- 
	- 
 public List<String> letterCasePermutation(String S) {
        List<String> res = new ArrayList<>();
        char[] a = S.toLowerCase().toCharArray();
        helper(a, 0, res);
        return res;
    }
    
    void helper(char[] a, int pos, List<String> res){
        if(pos==a.length){
            res.add(new String(a));
            return;
        }
        
        helper(a, pos+1, res);
        if(Character.isLetter(a[pos])) {
            a[pos] = Character.toUpperCase(a[pos]);
            helper(a, pos+1, res);
            a[pos] = Character.toLowerCase(a[pos]);
        }
    }




=======================================================================================================
method 3:

Method:

	-	DFS backtrack
	-	


Stats:

	- 
	- 

 class Solution {
    /**  
            a1b2   i=0, when it's at a, since it's a letter, we have two branches: a, A
         /        \
       a1b2       A1b2 i=1 when it's at 1, we only have 1 branch which is itself
        |          |   
       a1b2       A1b2 i=2 when it's at b, we have two branches: b, B
       /  \        / \
      a1b2 a1B2  A1b2 A1B2 i=3  when it's at 2, we only have one branch.
       |    |     |     |
      a1b2 a1B2  A1b2  A1B2 i=4 = S.length(). End recursion, add permutation to ans. 
      
      During this process, we are changing the S char array itself
    */
    public List<String> letterCasePermutation(String S) {
        List<String> ans = new ArrayList<>();
        backtrack(ans, 0, S.toCharArray());
        return ans;
    }
    public void backtrack(List<String> ans, int i, char[] S){
        if(i==S.length)
            ans.add(new String(S));
        else{
            if(Character.isLetter(S[i])){ //If it's letter
                S[i] = Character.toUpperCase(S[i]);
                backtrack(ans, i+1, S); //Upper case branch
                S[i] = Character.toLowerCase(S[i]);
                backtrack(ans, i+1, S); //Lower case branch
            }
            else
                backtrack(ans, i+1, S); 
        }
    }
}

