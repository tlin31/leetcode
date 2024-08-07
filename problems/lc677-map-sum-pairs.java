677. Map Sum Pairs - Medium

Implement a MapSum clas with insert, and sum methods.

For the method insert, you will be given a pair of (string, integer). The string represents the key 
and the integer represents the value. If the key already existed, then the original key-value pair 
will be overridden to the new one.

For the method sum, you will be given a string representing the prefix, and you need to return the 
sum of all the pairs value whose key starts with the prefix.

Example 1:
Input: insert("apple", 3), Output: Null
Input: sum("ap"), Output: 3
Input: insert("app", 2), Output: Null
Input: sum("ap"), Output: 5

******************************************************
key:
	- Trie
	- Hashmap
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- Hashmap /  brute force
	- For each key in the map, if that key starts with the given prefix, then add it to the answer.

stats:

	- Time: Every insert operation is O(1). Every sum operation is O(N * P) where N is the number 
		of items in the map, and P is the length of the input prefix.

	- Space: The space used by map is linear in the size of all input key and val values combined.


class MapSum {
    HashMap<String, Integer> map;
    public MapSum() {
        map = new HashMap<>();
    }
    public void insert(String key, int val) {
        map.put(key, val);
    }
    public int sum(String prefix) {
        int ans = 0;
        for (String key: map.keySet()) {
            if (key.startsWith(prefix)) {
                ans += map.get(key);
            }
        }
        return ans;
    }
}


=======================================================================================================
method 2:

method:

	- prefix hashmap
	- We can remember the answer for all possible prefixes in a HashMap score. When we get a new 
		(key, val) pair, we update every prefix of key appropriately: each prefix will be changed 
		by delta = val - map[key], where map is the previous associated value of key (zero if 
		undefined.)
	- 

stats:

	- Runtime: 48 ms, faster than 48.71% of Java online submissions for Map Sum Pairs.
	- Memory Usage: 35.9 MB, less than 100.00%


class MapSum {
    Map<String, Integer> map;
    Map<String, Integer> score;
    public MapSum() {
        map = new HashMap();
        score = new HashMap();
    }
    public void insert(String key, int val) {
        int delta = val - map.getOrDefault(key, 0);
        map.put(key, val);
        String prefix = "";
        for (char c: key.toCharArray()) {
            prefix += c;
            score.put(prefix, score.getOrDefault(prefix, 0) + delta);
        }
    }
    public int sum(String prefix) {
        return score.getOrDefault(prefix, 0);
    }
}

=======================================================================================================
method 3:

method:

	- Trie
	- Since we are dealing with prefixes, a Trie (prefix tree) is a natural data structure to approach 
	  this problem. For every node of the trie corresponding to some prefix, we will remember the 
	  desired answer (score) and store it at this node. 

	- As in Approach #2, this involves modifying each node by delta = val - map[key].



stats:

	- Time Complexity: Every insert operation is O(K), where K is the length of the key. 
	    			   Every sum operation is O(K)
	- Space Complexity: The space used is linear in the size of the total input.



class TrieNode {
    Map<Character, TrieNode> children = new HashMap();
    int score;
}


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

