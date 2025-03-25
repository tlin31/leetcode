212. Word Search II - Hard

Given a 2D board and a list of words from the dictionary, find all words in the board.

Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" 
cells are those horizontally or vertically neighboring. The same letter cell may not be used 
more than once in a word.

!! 可以拐弯~

Example:

Input: 
board = [
  ['o','a','a','n'],
  ['e','t','a','e'],
  ['i','h','k','r'],
  ['i','f','l','v']
]
words = ["oath","pea","eat","rain"]

Output: ["eat","oath"]
 

Note:

All inputs are consist of lowercase letters a-z.
The values of words are distinct.

******************************************************
key:
	- dfs + trie
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- Backtracking + Trie
	- build a trie based on the input word list
	- run dfs on every cell

Tips:
	1. No need to store character at TrieNode. c.next[i] != null is enough.
	2. No need to use O(n^2) extra space visited[m][n] --> simply store the string
	3. No need to use StringBuilder. Storing word itself at leaf node is enough.
	4. No need to use HashSet to de-duplicate. Use "one time search" trie.
	5. Gradually prune the nodes in Trie during the backtracking.

	6. Use search and startsWith in Trie c_lass like this popular solution.
	7. Remove Trie c_lass which unnecessarily starts from root in every dfs call.
	8. Use w.toCharArray() instead of w.charAt(i).
	9. Remove visited[m][n] completely by modifying board[i][j] = '#' directly.
	10. check validity, e.g., if(i > 0) dfs(...), before going to the next dfs.
	11. De-duplicate c - a with one variable i.

stats:

	- Runtime: 8 ms, faster than 97.97% of Java online submissions for Word Search II.
	- Memory Usage: 47.5 MB, less than 55.56% of Java online submissions for Word Search II.



class TrieNode {
    TrieNode[] next = new TrieNode[26];
    String word;
}

// main function
public List<String> findWords(char[][] board, String[] words) {
    List<String> res = new ArrayList<>();
    TrieNode root = buildTrie(words);
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            dfs (board, i, j, root, res);
        }
    }
    return res;
}

public TrieNode buildTrie(String[] words) {
    TrieNode root = new TrieNode();
    for (String w : words) {
        TrieNode p = root;
        for (char c : w.toCharArray()) {
            int i = c - 'a';
            if (p.next[i] == null) 
            	p.next[i] = new TrieNode();
            p = p.next[i];
       }
       p.word = w;
    }
    return root;



public void dfs(char[][] board, int i, int j, TrieNode p, List<String> res) {
    char c = board[i][j];
    if (c == '#' || p.next[c - 'a'] == null) 
    	return;

    p = p.next[c - 'a'];

    if (p.word != null) {   // found one
        res.add(p.word);

        // prune this nodes
        p.word = null;     // de-duplicate
    }

    // backtrack:
    board[i][j] = '#';

    // run dfs on all its neighbors 
    if (i > 0) dfs(board, i - 1, j ,p, res); 
    if (j > 0) dfs(board, i, j - 1, p, res);
    if (i < board.length - 1) dfs(board, i + 1, j, p, res); 
    if (j < board[0].length - 1) dfs(board, i, j + 1, p, res); 

    board[i][j] = c;
}



=======================================================================================================
method 2: interview friendly easier to come up ver.

method:

	- 
	- 

stats:

	- 
	- 

public List<String> findWords(char[][] board, String[] words) {
    Trie trie = buildTrie(words);
    Set<String> res = new HashSet<>();
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            dfs(board, trie, res, i, j);
        }
    }
    return new ArrayList<>(res);
}

public void dfs(char[][] board, Trie node, Set<String> res, int i, int j) {
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || 
        board[i][j] == '#' || node.next[board[i][j] - 'a'] == null) {
            return;
    }
    
    if (node.next[board[i][j] - 'a'].word != null) {
        res.add(node.next[board[i][j] - 'a'].word);
    }

    // Go to next char
    node = node.next[board[i][j] - 'a']; 
    char c = board[i][j];
    board[i][j] = '#';
    dfs(board, node, res, i - 1, j);
    dfs(board, node, res, i + 1, j);
    dfs(board, node, res, i, j - 1);
    dfs(board, node, res, i, j + 1);
    board[i][j] = c;
}   

public Trie buildTrie(String[] words) {
    Trie root = new Trie();
    for (String w : words) {
        Trie p = root;
        for (char c : w.toCharArray()) {
            if (p.next[c - 'a'] == null) {
                p.next[c - 'a'] = new Trie();
            }
            p = p.next[c - 'a'];  // will point to curr char
        }
        p.word = w;
    }
    return root;
}

private class Trie {
    Trie[] next = new Trie[26];
    String word = null;
}

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 




