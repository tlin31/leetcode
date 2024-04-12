648. Replace Words - Medium

In English, we have a concept called root, which can be followed by some other words to form 
another longer word - let us call this word successor. For example, the root an, followed by other, 
which can form another word another.

Now, given a dictionary consisting of many roots and a sentence. You need to replace all the 
successor in the sentence with the root forming it. If a successor has many roots can form it, 
replace it with the root with the shortest length.

You need to output the sentence after the replacement.

Example 1:

Input: dict = ["cat", "bat", "rat"]
sentence = "the cattle was rattled by the battery"
Output: "the cat was rat by the bat"
 

Note:

The input will only have lower-case letters.
1 <= dict words number <= 1000
1 <= sentence words number <= 1000
1 <= root length <= 100
1 <= sentence words length <= 1000
 
 ******************************************************
key:
	- Trie
	- edge case:
		1) 
		2)

******************************************************

form trie based on input, mark word end!
then run the sentence based on each word (distinguished by space)
	1. if word is in the dict --> as long as reach a word, return this & add to the output, 
	2. if not in dict = finish reading the word, just add this word to output.

=======================================================================================================
method 1:

method:

	- For each word in the sentence, look at successive prefixes and see if we saw them before.

	- Store all the roots in a Set structure. Then for each word, look at successive prefixes of 
		that word. 

		If you find a prefix that is a root, replace the word with that prefix. Otherwise, the 
		prefix will just be the word itself, and we should add that to the final sentence answer.



stats:

	- Time Complexity: O(sum (Wi)^2), Wi is the length of the i-th word. We might check every 
		prefix, the i-th of which is O(Wi^2)work.
	- Space Complexity: O(N) where N is the length of our sentence; the space used by rootset.
	- Runtime: 172 ms, faster than 9.80% of Java online submissions for Replace Words.
	- Memory Usage: 49 MB, less than 100.00%


class Solution {
    public String replaceWords(List<String> roots, String sentence) {
        Set<String> rootset = new HashSet();
        for (String root: roots) 
        	rootset.add(root);

        StringBuilder ans = new StringBuilder();

        //	\\s表示空格
        for (String word: sentence.split("\\s+")) {
            String prefix = "";
            for (int i = 1; i <= word.length(); ++i) {
                prefix = word.substring(0, i);
                if (rootset.contains(prefix)) 
                	break;
            }
            if (ans.length() > 0) 
            	ans.append(" ");

            ans.append(prefix);
        }
        return ans.toString();
    }
}



=======================================================================================================
method 2:

method:

	- Trie
	- 

stats:

	- Time Complexity: O(N) where N is the length of the sentence. Every query of a word is in 
		linear time.
	- Space Complexity: O(N)O(N), the size of our trie.


class Solution {
    public String replaceWords(List<String> roots, String sentence) {
        TrieNode trie = new TrieNode();
        for (String root: roots) {
            TrieNode cur = trie;
            for (char letter: root.toCharArray()) {
                if (cur.children[letter - 'a'] == null)
                    cur.children[letter - 'a'] = new TrieNode();
                cur = cur.children[letter - 'a'];
            }
            cur.word = root;
        }

        StringBuilder ans = new StringBuilder();

        for (String word: sentence.split("\\s+")) {
            if (ans.length() > 0)
                ans.append(" ");

            TrieNode cur = trie;
            for (char letter: word.toCharArray()) {
                if (cur.children[letter - 'a'] == null || cur.word != null)
                    break;
                cur = cur.children[letter - 'a'];
            }
            ans.append(cur.word != null ? cur.word : word);
        }
        return ans.toString();
    }
}

class TrieNode {
    TrieNode[] children;
    String word;
    TrieNode() {
        children = new TrieNode[26];
    }
}

=======================================================================================================
method 3:

method:

	- Since we are dealing with prefixes, a Trie (prefix tree) is a natural data structure to 
		approach this problem. For every node of the trie corresponding to some prefix, we will 
		remember the desired answer (score) and store it at this node. 

	- As in Approach #2, this involves modifying each node by delta = val - map[key].
Construct a Trie. Then, for the given input prefix, find all the possible terms and sum up the weight using a helper function.



stats:

	- Time : Every insert operation is O(K), where K is the length of the key. 
			 Every sum operation is O(K).
	- Space Complexity: The space used is linear in the size of the total input.


class MapSum {
    HashMap<String, Integer> map;
    TrieNode root;
    public MapSum() {
        map = new HashMap();
        root = new TrieNode();
    }
    public void insert(String key, int val) {
        int delta = val - map.getOrDefault(key, 0);
        map.put(key, val);
        TrieNode cur = root;
        cur.score += delta;
        for (char c: key.toCharArray()) {
            cur.children.putIfAbsent(c, new TrieNode());
            cur = cur.children.get(c);
            cur.score += delta;
        }
    }
    public int sum(String prefix) {
        TrieNode cur = root;
        for (char c: prefix.toCharArray()) {
            cur = cur.children.get(c);
            if (cur == null) return 0;
        }
        return cur.score;
    }
}
class TrieNode {
    Map<Character, TrieNode> children = new HashMap();
    int score;
}


class MapSum {

    private static final class Node {
        Node[] nodes = new Node[26];
        int val = 0;
    }
    private final Node root;
    
    public MapSum() { this.root = new Node(); }
    
    public void insert(String key, int val) {
        Node cur = root;
        for (char c : key.toCharArray()) {
            Node next = cur.nodes[c - 'a'] == null ? new Node() : cur.nodes[c - 'a'];
            cur = cur.nodes[c - 'a'] = next;
        }
        cur.val = val;
    }
    
    public int sum(String prefix) {
        return aggregate(findPrefix(prefix, root, 0));
    }
    
    private int aggregate(Node node) {
        if (node == null) return 0;
        int sum = node.val;
        for (Node child : node.nodes) {
            sum += aggregate(child);
        }
        return sum;
    }
    
    private Node findPrefix(String prefix, Node node, int pos) {
        if (node == null || pos == prefix.length()) return node == null ? null : node;
        return findPrefix(prefix, node.nodes[prefix.charAt(pos) - 'a'], pos + 1);
    }
}

