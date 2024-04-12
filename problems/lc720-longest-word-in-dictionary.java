720. Longest Word in Dictionary - Easy

Given a list of strings words representing an English Dictionary, find the longest word in words 
that can be built one character at a time by other words in words. 

If there is more than one possible answer, return the longest word with the smallest lexicographical 
order.

If there is no answer, return the empty string.

Example 1:
Input: 
words = ["w","wo","wor","worl", "world"]
Output: "world"
Explanation: 
The word "world" can be built one character at a time by "w", "wo", "wor", and "worl".

Example 2:
Input: 
words = ["a", "banana", "app", "appl", "ap", "apply", "apple"]
Output: "apple"
Explanation: 
Both "apply" and "apple" can be built from other words in the dictionary. However, "apple" is lexicographically smaller than "apply".

Note:
All the strings in the input will only contain lowercase letters.
The length of words will be in the range [1, 1000].
The length of words[i] will be in the range [1, 30].

******************************************************
key:
	- trie
	- trie + BFS
	- sort + iteratively check last word is in hashset
	- edge case:
		1) no answer, return empty string
		2) if a word length == 1

******************************************************



=======================================================================================================
method 1:

method:

	- Not using a Trie, use hash set 
	- 1. Sort the words alphabetically, therefore shorter words always comes before longer words;
	  2. for every word, check its prefix (0,n-1) is in the hash set 
	  Along the sorted list, populate the words that can be built;
	  3. Any prefix of a word must comes before that word.
	- 

stats:
	- Runtime: O(nlognL),  n = length of the list and L = average length of a word
	- Runtime: 15 ms, faster than 39.96% of Java online submissions for Longest Word in Dictionary.
	- Memory Usage: 36.8 MB, less than 81.25%


class Solution {
    public String longestWord(String[] words) {
        Arrays.sort(words);
        Set<String> built = new HashSet<String>();
        String res = "";
        for (String w : words) {
            if (w.length() == 1 || built.contains(w.substring(0, w.length() - 1))) {

            	// res = longer words
                res = w.length() > res.length() ? w : res;

                // add this w to the build
                built.add(w);
            }
        }
        return res;
    }
}


=======================================================================================================
method 2:

method:

	- trie + dfs to find the longest continuous downward path from the root.
	- 1. construct a trie for the dictionary. 
	  2. Then we traverse the whole trie to get the longest word.
	  3. DFS or BFS to achieve the trie traversal. 
	  	every time we get a new candidate word, we could replace the old one
	  	scan the children of a trie node from last to first because we want the word with the 
	  	smallest lexicographical order.
	- 

stats:

	- Runtime: 6 ms, faster than 92.26% of Java online submissions for Longest Word in Dictionary.
	- Memory Usage: 37.6 MB, less than 81.25%

class Solution {
    public String longestWord(String[] words) {
        TrieNode root = new TrieNode ();
        root.word = "-";
        for (String word : words)
            root.insert (word);
        return dfs (root, "");
    }

    String dfs (TrieNode node, String accum) {
        if (node == null || node.word.length () == 0)
            return accum;
        String res = "";
        if (!node.word.equals ("-"))
            accum = node.word;
        for (TrieNode child : node.links) {
            String curRes = dfs (child, accum);

            // update longest words
            if (curRes.length () > res.length () || (
            	//when same length, sort by lexi
            	curRes.length () == res.length () && curRes.compareTo (res) < 0))
                res = curRes;
        }
        return res;
    }

    /* Hand write this class every time you need to so you can remember well */
    static class TrieNode {
        String word = "";
        TrieNode[] links = new TrieNode[26];

        void insert (String s) {
            char[] chs = s.toCharArray ();
            TrieNode curNode = this;
            for (int i = 0; i < chs.length; i++) {
                int index = chs[i] - 'a';
                if (curNode.links[index] == null)
                    curNode.links[index] = new TrieNode ();
                curNode = curNode.links[index];
            }
            curNode.word = s;
        }
    }
}




=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- Runtime: 12 ms, faster than 65.75% of Java online submissions for Longest Word in Dictionary.
	- Memory Usage: 36.9 MB, less than 81.25%

Lots of solutions build the Trie and use DFS/BFS to find the deepest node.
But actually, we can just get the longest word while building the Trie.

How to solve without DFS/BFS?
The idea is using a variable maxString to store the longest word.
If it's the last character, we compare the word length with maxString and update maxString if needed.
At the end, we can just return the maxString.

What if two words length are the same?
We sort the words first so it gurantees that we meet the string by lexicographical order.
Therefore, the first word to be updated as maxStrinig is the lexicographically smallest word. No need to modify our algorithm.
e.g. ["a", "ap", "app", "appl", "apple", "apply"]
While building the Trie: "a"->"p"->"p"->"l"->"e"
Here maxString is updated to "apple"
And while inserting "apply": "a"->"p"->"p"->"l"->"y", here maxString will not be updated

Special operation for this problem:
Words are sorted. We are inserting a word into Trie, and we are now traversing to word[i] in the Trie and begin to build for word[i+1], if word[i] is not the last character (i < word.length() - 1) and the Trie node word[i+1] doesn't already exist. This situation means we cannot build for word
e.g. words: ["w", "wo", "wor", "world"]
After inserting "w", "wo" and "wor"
When inserting "world", we traverse the Trie: "w" -> "o" -> "r" and here normally we are going to insert "l" for "world", but not for this problem.
Since there's no "l" node for "r", it means that we cannot build "world" because we lack of the word "worl". "world" is defintely not the answer, so we can just stop insertion.

class Solution {
    public String longestWord(String[] words) {
        if (words == null || words.length == 0)
            return "";
        Trie head = new Trie();
        Arrays.sort(words);
        for (String word : words)
            head.insert(word);
        return head.maxString;
    }
    public class Trie {
        public String maxString = "";
        public Trie[] children = new Trie[26];
        public void insert(String s) {
            Trie cur = this;
            char[] chs = s.toCharArray();
            for (int i = 0; i < chs.length; i++) {
                int idx = chs[i] - 'a';
                if (cur.children[idx] != null) {
                    cur = cur.children[idx];
                } else {
                    if (i < s.length() - 1) //special operation (refer to explaination above)
                        return;
                    cur.children[idx] = new Trie();
                    cur = cur.children[idx];
                    if (s.length() > maxString.length())
                        maxString = s;
                }
            }
        }
    }
}


