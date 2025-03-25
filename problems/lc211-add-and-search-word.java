211. Add and Search Word - Data structure design - Medium

Design a data structure that supports the following two operations:

void addWord(word)
bool search(word)
search(word) can search a literal word or a regular expression string containing only letters 
a-z or .. A . means it can represent any one letter.

Example:

addWord("bad")
addWord("dad")
addWord("mad")
search("pad") -> false
search("bad") -> true
search(".ad") -> true
search("b..") -> true


******************************************************
key:
	- Trie
	- edge case:
		1) 
		2)

******************************************************


class WordDictionary {
    private WordDictionary[] children;
    boolean isEndOfWord;
    // Initialize your data structure here. 
    public WordDictionary() {
        children = new WordDictionary[26];
        isEndOfWord = false;
    }
    
    // Adds a word into the data structure. 
    public void addWord(String word) {
        WordDictionary curr = this;
        for(char c: word.toCharArray()){
            if(curr.children[c - 'a'] == null)
                curr.children[c - 'a'] = new WordDictionary();
            curr = curr.children[c - 'a'];
        }
        curr.isEndOfWord = true;
    }
    
    // Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. 
    public boolean search(String word) {
        WordDictionary curr = this;
        for(int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            if(c == '.'){
                for(WordDictionary ch: curr.children)
                    if(ch != null && ch.search(word.substring(i+1))) return true;
                return false;
            }

            if(curr.children[c - 'a'] == null) return false;

            curr = curr.children[c - 'a'];
        }
        return curr != null && curr.isEndOfWord;
    }
}

=======================================================================================================
method 1:

method:

	- Trie + backtrack in match()
	- 

stats:
	- addWord(): O(n), n = length of the new word
	- search(): Worst case: O(m), m = the total number of characters in the Trie
	- Runtime: 76 ms, faster than 85.03% of Java 
	- Memory Usage: 55.3 MB, less than 81.82% of Java 


public class WordDictionary {

    // we will never change root!
    private TrieNode root = new TrieNode();

    public class TrieNode {
        public TrieNode[] children = new TrieNode[26];
        public String item = "";
    }
    

    public void addWord(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (node.children[c - 'a'] == null) {
                node.children[c - 'a'] = new TrieNode();
            }
            node = node.children[c - 'a'];
        }

        // store item at the leaf node 
        node.item = word;
    }

    public boolean search(String word) {
        return match(word.toCharArray(), 0, root);
    }
    
    private boolean match(char[] chs, int k, TrieNode node) {
        if (k == chs.length) 
        	return !node.item.equals("");   

        if (chs[k] != '.') {
            return node.children[chs[k] - 'a'] != null 
                    && match(chs, k + 1, node.children[chs[k] - 'a']);
        } else {
        	// if it's a dot, then try for every child of current layer, and see whether it'll match
            for (int i = 0; i < node.children.length; i++) {
                if (node.children[i] != null) {
                    if (match(chs, k + 1, node.children[i])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}


=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 


=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



