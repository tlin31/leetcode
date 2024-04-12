472. Concatenated Words - Hard

Given a list of words (without duplicates), please write a program that returns all concatenated 
words in the given list of words.

A concatenated word is defined as a string that is comprised entirely of at least two shorter words in the given array.

Example:
Input: ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]

Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]

Explanation: "catsdogcats" can be concatenated by "cats", "dog" and "cats"; 
 "dogcatsdog" can be concatenated by "dog", "cats" and "dog"; 
"ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".

Note:
The number of elements of the given array will not exceed 10,000
The length sum of elements in the given array will not exceed 600,000.
All the input string will only include lower case letters.
The returned elements order does not matter.


******************************************************
key:
	- dfs + Trie
	- dp
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- a concatenated word is a word add other word(words) as prefix. 
	- use count to count number of concatenated words so far, need to have count >= 2

	- We have to make a decision when we meet a node that meets the end of a word (with en in 
	  the example below). Then either:
  		1. take the current node associated word as prefix (and restart at root for another word) 
  		2. not take the  current node associated word as prefix (i.e. move further in the same branch).

			For example,
			    root           
			     /\
			    c  d
			    /   \
			    a    o
			   /      \
			  t(en)    g(en)
			  /         
			 s(en)    
 
		To concatenate catsdogcats using {cat, cats, dog}
		search tree: 
		    root - c - a - t(en)  - X [to take cat as prefix, x work]
		                          - s(en) - d - o - g(en) - c - a - t  - s(en) DONE!
		                                                  - X [not to take dog as prefix, x work]
		                                  -  [not to take cats as prefix, x work]



Pseudo-code:
    func findAllConcatenatedWordsInADict():
        build Trie using words
        for word in words
            test isConcatenation(0, word, root])
    
    func boolean isConcatenation(index, word, cnt): // backtrack
        ptr = root
        for (i = index; i < word.length; i++)
            if (ptr.children[word[i] - 'a'] == null)
                return false

            // move 1 level down
            ptr = ptr.children[word[i] - 'a'];

            //reach the end of this branch
            if (ptr.isEnd) 

                // find one valid prefix
                if (i = word.length - 1)
                    return cnt >= 1

                // if more left, check for the ending substring
                if (isConcatenation(wi + 1, word, cnt + 1))
                    return true;
            
        return false;
	

stats:

	- Runtime: 51 ms, faster than 79.42% of Java online submissions for Concatenated Words.
	- Memory Usage: 56.7 MB, less than 9.52%


private static Node root;
    
    // main function!
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        
        if (words == null || words.length == 0)
            return new ArrayList<>();
        
        List<String> result = new LinkedList<>();
        
        root = new Node();
        buildTrie(words);
        
        for (String word : words) {
            if (isConcatenation(0, word, 0))
                result.add(word);
        }
        
        return result;
    }
    
    private boolean isConcatenation(int index, String word, int count) {
        
        Node ptr = root;
        
        for (int i = index; i < word.length(); i++) { 
        	// early fail  
            if (ptr.children[word.charAt(i) - 'a'] == null) 
                return false;

            // move down 1 level
            ptr = ptr.children[word.charAt(i) - 'a'];

            // when reach an end
            if (ptr.isWordEnd) {

            	// 1. if reach the end of the string
                if (i == word.length() - 1) {
                    return count >= 1;
                }

                // check the substring (i+1, word.length()-1) is a word or not
                if (isConcatenation(i + 1, word, count + 1))
                    return true;
            }     
        }
        
        return false;
    }
    class Node {
        Node[] children;
        boolean isWordEnd;
        
        public Node() {
            children = new Node[26];
            isWordEnd = false;
        }
    }

    private void buildTrie(String[] words) {
        Node ptr;
        for (String word : words) {
            ptr = root;
            for (char ch : word.toCharArray()) {
                int order = ch - 'a';
                if (ptr.children[order] == null) {
                    ptr.children[order] = new Node();
                } 
                ptr = ptr.children[order];
            }
            ptr.isWordEnd = true;
        }
    }
    


=======================================================================================================
method 2:

method:

	- dp
	- We iterate through each word and see if it can be formed by using other words.
	- first sort the input by length of each word, and only try to form one word by using words 
	  in front of it.

stats:

	- Runtime: 352 ms, faster than 15.59% of Java online submissions for Concatenated Words.
	- Memory Usage: 45.8 MB, less than 95.24%


public class Solution {
    public static List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> result = new ArrayList<>();
        Set<String> preWords = new HashSet<>();
        Arrays.sort(words, new Comparator<String>() {
            public int compare (String s1, String s2) {
                return s1.length() - s2.length();
            }
        });
        
        for (int i = 0; i < words.length; i++) {
            if (canForm(words[i], preWords)) {
                result.add(words[i]);
            }

            // add the word after processing it
            preWords.add(words[i]);
        }
        
        return result;
    }
	
    private static boolean canForm(String word, Set<String> dict) {
        if (dict.isEmpty()) return false;
    	boolean[] dp = new boolean[word.length() + 1];
    	dp[0] = true;

        // check possible substrings
    	for (int i = 1; i <= word.length(); i++) {
    	    for (int j = 0; j < i; j++) {

                // check if the prefix exists, if so then proceed to check the concatenated
                // parts in hashmap
        		if (!dp[j]) 
                    continue;
        		if (dict.contains(word.substring(j, i))) {
        		    dp[i] = true;
        		    break;
        		}
    	    }
    	}
    	return dp[word.length()];
    }
}


 ----------------
| hashmap + dfs |
 ----------------

Stats:
	- Runtime: 59 ms, faster than 67.28% of Java online submissions for Concatenated Words.
	- Memory Usage: 48.1 MB, less than 85.71%

public List<String> findAllConcatenatedWordsInADict(String[] words) {
    List<String> result = new ArrayList<String>();
    Set<String> set = new HashSet(Arrays.asList(words));

    for(String word : words) {
        set.remove(word);

        if(dfs(word, set, "")) 
        	result.add(word);

        set.add(word);
    }
    return result;
}

private boolean dfs(String word, Set<String> set, String previous) {
    // if has a prefix / count >= 2
    if(!previous.equals("")) 
    	set.add(previous);

    if(set.contains(word)) 
    	return true;
    
    for(int i = 1; i < word.length(); i++) {
        String prefix = word.substring(0,i);
        if(set.contains(prefix) && dfs(word.substring(i, word.length()), set, previous+prefix)) {
            return true;
        }
    }
    return false;
}


=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



