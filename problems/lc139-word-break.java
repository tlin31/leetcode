139. Word Break - Medium

Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, 
determine if s can be segmented into a space-separated sequence of one or more dictionary words.

Note:

The same word in the dictionary may be reused multiple times in the segmentation.
You may assume the dictionary does not contain duplicate words.
Example 1:

Input: s = "leetcode", wordDict = ["leet", "code"]
Output: true
Explanation: Return true because "leetcode" can be segmented as "leet code".

Example 2:
Input: s = "applepenapple", wordDict = ["apple", "pen"]
Output: true
Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
             Note that you are allowed to reuse a dictionary word.

Example 3:
Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
Output: false


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************

=======================================================================================================
Method 0:
	- DP


public class Solution {
    public boolean wordBreak(String s, Set<String> dict) {
        
        boolean[] dp = new boolean[s.length() + 1];        
        dp[0] = true;
        
        /* First DP
        for(int i = 1; i <= s.length(); i++){
            for(String str: dict){
                if(str.length() <= i){
                    if(f[i - str.length()]){
                        if(s.substring(i-str.length(), i).equals(str)){
                            f[i] = true;
                            break;
                        }
                    }
                }
            }
        }*/
        
        //Second DP
        for(int i=1; i <= s.length(); i++){
            for(int j=0; j < i; j++){
                if(dp[j] && dict.contains(s.substring(j, i))){
                    dp[i] = true;
                    break;
                }
            }
        }
        
        return dp[s.length()];
    }
}

=======================================================================================================
Method 1:

Method:

	-	Trie 


	-	

Stats:

	- Runtime: 1 ms, faster than 98.48% of Java online submissions for Word Break.
	- Memory Usage: 42.3 MB, less than 5.08% of Java online submissions for Word Break.


public class Solution {
    public class TrieNode {
        boolean isWord;
        TrieNode[] c;
        
        public TrieNode(){
            isWord = false;
            c = new TrieNode[128];
        }
    }
    
    public void addWord(TrieNode node, String word) {
        for (int i = 0; i < word.length(); i++) {
            int j = (int)word.charAt(i); 
            if (node.c[j] == null) 
            	node.c[j] = new TrieNode();

            node = node.c[j];
        }
        node.isWord = true;
    }
    
    public boolean wordBreak(String s, Set<String> wordDict) {
        TrieNode node = new TrieNode(), 
                 cur;

        for (String i : wordDict) 
            addWord(node, i);

        char[] str = s.toCharArray();
        int len = str.length;
        boolean[] dp = new boolean[len + 1];
        dp[len] = true;
        
        //从后往前
        for (int i = len - 1; i >= 0; i--) {
            //System.out.println(str[i]);
            cur = node;
            for (int j = i; cur != null && j < len ; j++) {
                cur = cur.c[(int)str[j]];
                if (cur != null && cur.isWord && dp[j + 1]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[0];
    }
}



=======================================================================================================
method 2:

Method:

	-	recursion
	-	


Stats:

	- 
	- 

public class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        return word_Break(s, new HashSet(wordDict), 0, new Boolean[s.length()]);
    }
    public boolean word_Break(String s, Set<String> wordDict, int start, Boolean[] memo) {
        if (start == s.length()) {
            return true;
        }
        if (memo[start] != null) {
            return memo[start];
        }
        for (int end = start + 1; end <= s.length(); end++) {
            if (wordDict.contains(s.substring(start, end)) && word_Break(s, wordDict, end, memo)) {
                return memo[start] = true;
            }
        }
        return memo[start] = false;
    }
}





BFS:
public class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordDictSet=new HashSet(wordDict);
        Queue<Integer> queue = new LinkedList<>();
        int[] visited = new int[s.length()];
        queue.add(0);
        while (!queue.isEmpty()) {
            int start = queue.remove();
            if (visited[start] == 0) {
                for (int end = start + 1; end <= s.length(); end++) {
                    if (wordDictSet.contains(s.substring(start, end))) {
                        queue.add(end);
                        if (end == s.length()) {
                            return true;
                        }
                    }
                }
                visited[start] = 1;
            }
        }
        return false;
    }
}

