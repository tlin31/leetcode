140. Word Break II - Hard

Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, add 
spaces in s to construct a sentence where each word is a valid dictionary word. Return all such 
possible sentences.

Note:

The same word in the dictionary may be reused multiple times in the segmentation.
You may assume the dictionary does not contain duplicate words.

Example 1:
Input:
s = "catsanddog"
wordDict = ["cat", "cats", "and", "sand", "dog"]
Output:
[
  "cats and dog",
  "cat sand dog"
]


Example 2:
Input:
s = "pineapplepenapple"
wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
Output:
[
  "pine apple pen apple",
  "pineapple pen apple",
  "pine applepen apple"
]
Explanation: Note that you are allowed to reuse a dictionary word.


Example 3:
Input:
s = "catsandog"
wordDict = ["cats", "dog", "sand", "and", "cat"]
Output:
[]


******************************************************
key:
	- backtrack, since need all combination
	- DP
	- edge case:
		1) empty string, return empty
		2) empty wordDict

******************************************************

状态定义（非常重要）
dfs(start) = 从 start 开始能构成的所有句子

递归逻辑

    在位置 start：

        枚举所有 end

        如果 s[start..end) 在字典中：

            递归 dfs(end)

        当前单词 + 子结果拼接

记忆化（关键）
    memo[start] = dfs(start) 的结果


👉 防止重复计算同一个子串



class Solution {
    Set<String> dict;
    Map<Integer, List<String>> memo = new HashMap<>();
    String s;

    public List<String> wordBreak(String s, List<String> wordDict) {
        this.s = s;
        this.dict = new HashSet<>(wordDict);
        return dfs(0);
    }

    private List<String> dfs(int start) {
        if (memo.containsKey(start)) {
            return memo.get(start);
        }

        List<String> res = new ArrayList<>();
        if (start == s.length()) {
            res.add(""); // base case
            return res;
        }

        for (int end = start + 1; end <= s.length(); end++) {
            String word = s.substring(start, end);
            if (dict.contains(word)) {
                List<String> sub = dfs(end);
                for (String tail : sub) {
                    if (tail.isEmpty()) {
                        res.add(word);
                    } else {
                        res.add(word + " " + tail);
                    }
                }
            }
        }

        memo.put(start, res);
        return res;
    }
}

=======================================================================================================
Trie optimization

class TrieNode {

    boolean isEnd;
    TrieNode[] children;

    TrieNode() {
        isEnd = false;
        children = new TrieNode[26];
    }
}

class Trie {

    TrieNode root;

    Trie() {
        root = new TrieNode();
    }

    void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isEnd = true;
    }
}

class Solution {

    public List<String> wordBreak(String s, List<String> wordDict) {
        // Build the Trie from the word dictionary
        Trie trie = new Trie();
        for (String word : wordDict) {
            trie.insert(word);
        }

        // Map to store results of subproblems
        Map<Integer, List<String>> dp = new HashMap<>();

        // Iterate from the end of the string to the beginning
        for (int startIdx = s.length(); startIdx >= 0; startIdx--) {
            // List to store valid sentences starting from startIdx
            List<String> validSentences = new ArrayList<>();

            // Initialize current node to the root of the trie
            TrieNode currentNode = trie.root;

            // Iterate from startIdx to the end of the string
            for (int endIdx = startIdx; endIdx < s.length(); endIdx++) {
                char c = s.charAt(endIdx);
                int index = c - 'a';

                // Check if the current character exists in the trie
                if (currentNode.children[index] == null) {
                    break;
                }

                // Move to the next node in the trie
                currentNode = currentNode.children[index];

                // Check if we have found a valid word
                if (currentNode.isEnd) {
                    String currentWord = s.substring(startIdx, endIdx + 1);

                    // If it's the last word, add it as a valid sentence
                    if (endIdx == s.length() - 1) {
                        validSentences.add(currentWord);
                    } else {
                        // If it's not the last word, append it to each sentence formed by the remaining substring
                        List<String> sentencesFromNextIndex = dp.get(
                            endIdx + 1
                        );
                        for (String sentence : sentencesFromNextIndex) {
                            validSentences.add(currentWord + " " + sentence);
                        }
                    }
                }
            }

            // Store the valid sentences in dp
            dp.put(startIdx, validSentences);
        }

        // Return the sentences formed from the entire string
        return dp.getOrDefault(0, new ArrayList<>());
    }
}
=======================================================================================================


method 1:

method:

	- DFS + HashMap to save the previous results to prune duplicated branches
	- check tail, then recurse on head part, because when construct string, just append the tail

stats:

	- Runtime: 9 ms, faster than 56.13% of Java online submissions for Word Break II.
	- Memory Usage: 41.4 MB, less than 6.56%


import java.io.*; 
import java.util.*; 

public class Solution {
    HashMap<String,List<String>> map = new HashMap<String,List<String>>();
    
    public List<String> wordBreak(String s, List<String> wordDict) {

        Set<String> setDict = new TreeSet<>();
        setDict.addAll(wordDict);
        return wordBreakHelper(s,setDict);
       
    }

    private List<String> wordBreakHelper(String s, Set<String> wordDict) {
        List<String> res = new ArrayList<String>();

        if(s == null || s.length() == 0) {
            return res;
        }

        // PRUNING
        if(map.containsKey(s)) {
            return map.get(s);
        }

        if(wordDict.contains(s)) {
            res.add(s);
        }

        for(int i = 1 ; i < s.length() ; i++) {
        	// tailPart = str (i ~ end)
            String tailPart = s.substring(i);
            if(wordDict.contains(tailPart)) {

            	// dfs
                List<String> headPart = wordBreakHelper(s.substring(0 , i) , wordDict);
                if(headPart.size() != 0) {
                    for(int j = 0 ; j < headPart.size() ; j++) {
                        res.add(headPart.get(j) + " " + tailPart);
                    }
                }
            }
        }
        map.put(s , res);
        return res;
    }
    

}


=======================================================================================================
method 2:

method:

	- DP
	- early end: check only for max size of word length in dict
	- ex. s = catsand, wordDict = {cat, cats, and, sand}
	      starts = [null, null, {0}, {0}, null, null, {3,4}]
            //index 0,   1,      2    3

	    in dfs, start from starts[6] = {3,4}, and loops through start{3,4}:
		1. start = 3, then call dfs(result, "sand", s, starts, 3)
			in new dfs call, get starts[3] = 0, then start = 0, call dfs dfs(result, "cat sand", s, starts, 0)
			in new dfs call, since start = 0, return.
			Now result = { "cat sand"}

		2.start = 4, then call dfs(result, "and", s, starts, 4)
			in new dfs call, get starts[4] = 0, then start = 0, call dfs dfs(result, "cats and", s, starts, 0)
			in new dfs call, since start = 0, return.
			Now result = { "cat sand", "cats and"}


stats:

	- Runtime: 7 ms, faster than 76.56% of Java online submissions for Word Break II.
	- Memory Usage: 39.4 MB, less than 32.79% 

public List<String> wordBreak(String s, Set<String> wordDict) {
	// DP: store valid start positions
    List<Integer>[] starts = new List[s.length() + 1]; 
    starts[0] = new ArrayList<Integer>();
    
    //find max length of the word dict
    int maxLen = 0;
    for (String s : wordDict) {
        maxLen = Math.max(maxLen, s.length());
    }


    //fills in the dp
    for (int i = 1; i <= s.length(); i++) {

        //j >= i - maxLen: pruning,只检查有可能变成word的间距
        for (int j = i - 1; j >= i - maxLen && j >= 0; j--) {
            if (starts[j] == null) 
            	continue;

            String word = s.substring(j, i);
            if (wordDict.contains(word)) {
                if (starts[i] == null) {
                    starts[i] = new ArrayList<Integer>();
                }
                starts[i].add(j);
            }
        }
    }
    
    List<String> rst = new ArrayList<>();

    // 如果没有word可以组成到最后一个字符， then can't be parsed
    if (starts[s.length()] == null) {
        return rst;
    }
    
    dfs(rst, "", s, starts, s.length());
    return rst;
}


private void dfs(List<String> rst, String path, String s, List<Integer>[] starts, int end) {
    if (end == 0) {
        //rst加上从index 1之后的所有，即为没有空格“ ”的path
        rst.add(path.substring(1));
        return;
    }
    
    for (Integer start: starts[end]) {
        String word = s.substring(start, end);
        dfs(rst, " " + word + path, s, starts, start);
    }
}

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- Runtime: 9 ms, faster than 56.13% of Java online submissions for Word Break II.
	- Memory Usage: 39.2 MB, less than 37.71%


import java.io.*; 
import java.util.*; 

public class Solution {
    HashMap<Integer, List<String>> dp = new HashMap<>();

    public List<String> wordBreakHelper(String s, Set<String> wordDict) {
        int maxLength = -1;
        for(String ss : wordDict) maxLength = Math.max(maxLength, ss.length());
        return addSpaces(s, wordDict, 0, maxLength);
    }
    
    private List<String> addSpaces(String s, Set<String> wordDict, int start, int max){
        List<String> words = new ArrayList<>();
        if(start == s.length()) {
            words.add("");
            return words;
        }
        for(int i = start + 1; i <= max + start && i <= s.length(); i++){
            String temp = s.substring(start, i);
            if(wordDict.contains(temp)){
                List<String> ll;
                if(dp.containsKey(i)) ll = dp.get(i);
                else ll = addSpaces(s, wordDict, i, max);
                for(String ss : ll) words.add(temp + (ss.equals("") ? "" : " ") + ss);
            }
            
        }
        dp.put(start, words);
        return words;
    }
    
    
    public List<String> wordBreak(String s, List<String> wordDict) {

        Set<String> setDict = new TreeSet<>();
        setDict.addAll(wordDict);
        return wordBreakHelper(s,setDict);
       
    }
}

