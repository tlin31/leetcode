208. Implement Trie (Prefix Tree) - Medium

Implement a trie with insert, search, and startsWith methods.

Example:

Trie trie = new Trie();

trie.insert("apple");
trie.search("apple");   // returns true
trie.search("app");     // returns false
trie.startsWith("app"); // returns true
trie.insert("app");   
trie.search("app");     // returns true
Note:

You may assume that all inputs are consist of lowercase letters a-z.
All inputs are guaranteed to be non-empty strings.

******************************************************
key:
	- stack
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- Search for a key prefix in a trie: 
		- We traverse the trie from the root, till there are no characters left in key prefix or 
			it is impossible to continue the path in the trie with the current key character. 
			The only difference with the mentioned above search for a key algorithm is that when we 
			come to an end of the key prefix, we always return true. 

		- We do not need to consider the isEnd mark of the current trie node, because we are searching 
			for a prefix of a key, not for a whole key.

stats:

	- insert
		- Time complexity : O(m), where m is the key length.
			we either examine or create a node in the trie till we reach the end of the key.
		- Space complexity : O(m).
			worst case newly inserted key does not share a prefix with the the keys already inserted 
			in the trie. We have to add m new nodes, which takes us O(m) space.
	
    - search
		- Time complexity : O(m) 
		- Space complexity : O(1)

class TrieNode {

    // R links to node children
    private TrieNode[] links;

    private final int R = 26;

    private boolean isEnd;

    public TrieNode() {
        links = new TrieNode[R];
    }

    public boolean containsKey(char ch) {
        return links[ch -'a'] != null;
    }
    public TrieNode get(char ch) {
        return links[ch -'a'];
    }
    public void put(char ch, TrieNode node) {
        links[ch -'a'] = node;
    }
    public void setEnd() {
        isEnd = true;
    }
    public boolean isEnd() {
        return isEnd;
    }
}

class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            if (!node.containsKey(currentChar)) {
                node.put(currentChar, new TrieNode());
            }
            //move down one layer
            node = node.get(currentChar);
        }
        node.setEnd();
    }

    private TrieNode searchPrefix(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
           char curLetter = word.charAt(i);
           if (node.containsKey(curLetter)) {
               node = node.get(curLetter);
           } else {
               return null;
           }
        }
        return node;
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
       TrieNode node = searchPrefix(word);
       return node != null && node.isEnd();
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        TrieNode node = searchPrefix(prefix);
        return node != null;
    }
}



=======================================================================================================
method 2:

method:

	- For the TrieNode, each node has two fields:
        a boolean isEnd that stores whether the current character is the end of a word
        a TrieNode[] array of size 26 that stores its children
	- search and startsWith are combined into a helper method search(String str, int type) to save 
        coding.


stats:

	- Runtime: 75 ms, faster than 75.86% of Java online submissions for Implement Trie (Prefix Tree).
	- Memory Usage: 57 MB, less than 15.38% 

class TrieNode {
    boolean isEnd;
    TrieNode[] children;

    public TrieNode() {
        isEnd = true;
        children = new TrieNode[26];
    }
}

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for(int i=0, L=word.length(); i<L; i++) {
            int id = word.charAt(i) - 'a';
            if(current.children[id]==null) {
                current.children[id] = new TrieNode();
                current.children[id].isEnd = false;
            }
            current = current.children[id];
        }
        current.isEnd = true;
    }

    public boolean search(String word) {
        return search(word, 1);
    }
    public boolean startsWith(String prefix) {
        return search(prefix, 2);
    }
    private boolean search(String str, int type) {
        TrieNode current = root;
        int i=-1, L=str.length();
        while(++i<L) {
            int id = str.charAt(i) - 'a';
            if((current=current.children[id]) == null) return false;
        }
        return type==1 ? current.isEnd : true;
    }
}


=======================================================================================================
method 3:

method:
	- Hashmap

stats:

	- Runtime: 86 ms, faster than 27.14% of Java online submissions for Implement Trie (Prefix Tree).
    - Memory Usage: 50.7 MB, less than 100.00%

class TrieNode {
    public Map<Character, TrieNode>children = null;
    public boolean isLeaf = false;
    public char val;
    public TrieNode() {}
    public TrieNode(char val) {
        this.val = val;
    }
}


public class Trie {
    private TrieNode root;
    
    public Trie() {
        root = new TrieNode();
    }    

    public void insert(String word) {
        TrieNode node  = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (node.children == null) 
                node.children = new HashMap<Character, TrieNode>();

            if (!node.children.containsKey(ch)) 
                node.children.put(ch, new TrieNode(ch));     

            node = node.children.get(ch);
        }
        node.isLeaf = true;
    }    

    public boolean search(String word) {
        TrieNode node  = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);

            if (node.children == null || !node.children.containsKey(ch))
                return false;

            node = node.children.get(ch);
        }
        return node.isLeaf;
    }     
    
    public boolean startsWith(String prefix) {
        TrieNode node  = root;
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);

            if (node.children == null || !node.children.containsKey(ch))
                return false;

            node = node.children.get(ch);
        }
        return true;
    }
}


