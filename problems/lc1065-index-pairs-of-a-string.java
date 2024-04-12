1065. Index Pairs of a String - Easy

Given a text string and words (a list of strings), return all index pairs [i, j] so that the 
substring text[i]...text[j] is in the list of words.

 

Example 1:
Input: text = "thestoryofleetcodeandme", words = ["story","fleet","leetcode"]
Output: [[3,7],[9,13],[10,17]]

Example 2:
Input: text = "ababa", words = ["aba","ab"]
Output: [[0,1],[0,2],[2,3],[2,4]]

Explanation: 
Notice that matches can overlap, see "aba" is found in [0,2] and [2,4].
 

Note:

All strings contains only lowercase English letters.
It is guaranteed that all strings in words are different.
1 <= text.length <= 100
1 <= words.length <= 20
1 <= words[i].length <= 50
Return the pairs [i,j] in sorted order (i.e. sort them by their first coordinate in case of ties sort them by their second coordinate).

******************************************************
key:
	- Trie
	- edge case:
		1) empty text, return []
		2) empty words, return []?

******************************************************



=======================================================================================================
method 1:

method:

	- Trie
	- 

stats:

	- 
	- 


class Solution {

    class Node {
        char c;

        // store a word at the leaf node
        String word;

        Node[] children;
        public Node(char c) {
            this.c = c;
            this.word = null;
            this.children = new Node[26];
        }
    }
    
    public int[][] indexPairs(String text, String[] words) {
        Node root = new Node(' ');

        //construct trie with words
        for (String word : words) {
            insert(root, word);
        }

        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            for (int j = i + 1; j <= text.length(); j++) {
                if (search(text.substring(i, j), root)) {
                    list.add(new int[]{i, j - 1});
                }
            }
        }

        int[][] ans = new int[list.size()][2];

        for (int i = 0; i < list.size(); i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }
    
    public void insert(Node node, String word) {
        char[] ch = word.toCharArray();

        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (node.children[c - 'a'] == null) {
                node.children[c - 'a'] = new Node(c);
            }

            if (i == ch.length - 1) {
                node.children[c - 'a'].word = word;
            }
            node = node.children[c - 'a'];
        }
    }
    
    public boolean search(String target, Node node) {
        char[] ch = target.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (node.children[c - 'a'] != null) {
                node = node.children[c - 'a'];
            }
            if (i == ch.length - 1 && target.equals(node.word)) {
                return true;
            }
        }
        return false;
    }
}

=======================================================================================================
method 2:

method:

	- use startsWith()
	- 

stats:

	- Runtime: 3 ms, faster than 56.48% of Java online submissions for Index Pairs of a String.
	- Memory Usage: 36.7 MB, less than 50.00% 



class Solution {
    public int[][] indexPairs(String text, String[] words) {
        List<int[]> ans = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            for (String p : words) {
                if (text.startsWith(p, i)) {
                    ans.add(new int[]{i, i + p.length() - 1});
                }
            }
        }

        Collections.sort(ans, new Comparator<int[]>() {
            @Override
            public int compare(int[] x, int[] y) {
                int ret = x[0] - y[0];
                if (ret == 0) ret = x[1] - y[1];
                return ret;
            }
        });

        return ans.toArray(new int[0][]);
    }
}

