1032. Stream of Characters - Hard

Implement the StreamChecker clas_s as follows:

	1. StreamChecker(words): Constructor, init the data structure with the given words.
	2. query(letter): returns true if and only if for some k >= 1, the last k characters queried 
	                  (in order from oldest to newest, including this letter just queried) spell 
	                  one of the words in the given list.
 

Example:

StreamChecker streamChecker = new StreamChecker(["cd","f","kl"]); // init the dictionary.
streamChecker.query('a');          // return false
streamChecker.query('b');          // return false
streamChecker.query('c');          // return false
streamChecker.query('d');          // return true, because 'cd' is in the wordlist
streamChecker.query('e');          // return false
streamChecker.query('f');          // return true, because 'f' is in the wordlist
streamChecker.query('g');          // return false
streamChecker.query('h');          // return false
streamChecker.query('i');          // return false
streamChecker.query('j');          // return false
streamChecker.query('k');          // return false
streamChecker.query('l');          // return true, because 'kl' is in the wordlist
 

Note:

1 <= words.length <= 2000
1 <= words[i].length <= 2000
Words will only consist of lowercase English letters.
Queries will only consist of lowercase English letters.
The number of queries is at most 40000.


******************************************************
key:
	- reverse words
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	Store the words in the trie with reverse order, and check the query string from the end
	-	

Stats:
	- Time Complexity: O(QW). O(query) = O(waiting.size) = O(W), W is the maximum length of words.
					   make Q queries 

	- Space Complexity: waiting.size <= W, where W is the maximum length of words. waiting list 
						will take O(W). Assume we have initially N words, at most N leaves in the trie.
						The size of trie is O(NW).

	- Runtime: 87 ms, faster than 62.59% of Java online submissions for Stream of Characters.
	- Memory Usage: 69 MB, less than 100.00%


class StreamChecker {
    
    class TrieNode {
        boolean isWord;
        TrieNode[] next = new TrieNode[26];
    }

    TrieNode root = new TrieNode();
    StringBuilder sb = new StringBuilder();

    public StreamChecker(String[] words) {
        createTrie(words);
    }


    private void createTrie(String[] words) {
        for (String s : words) {
            TrieNode node = root;
            int len = s.length();

            // store the words in reverse
            for (int i = len - 1; i >= 0; i--) {
                char c = s.charAt(i);
                if (node.next[c - 'a'] == null) {
                    node.next[c - 'a'] = new TrieNode();
                }
                node = node.next[c - 'a'];
            }
            node.isWord = true;
        }
    }

    public boolean query(char letter) {
        sb.append(letter);
        TrieNode node = root;
        for (int i = sb.length() - 1; i >= 0 && node != null; i--) {
            char c = sb.charAt(i);
            node = node.next[c - 'a'];
            if (node != null && node.isWord) {
                return true;
            }
        }
        return false;
    }
}



=======================================================================================================
method 2:

Method:

	-	Explanation
Initialization:

Construct a trie
declare a global waiting list.
Query:

for each node in the waiting list,
check if there is child node for the new character.
If so, add it to the new waiting list.
return true if any node in the waitinglist is the end of a word.

	-	


Stats:

	- Runtime: 597 ms, faster than 22.13% of Java online submissions for Stream of Characters.
	- Memory Usage: 74 MB, less than 100.00% 



=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



