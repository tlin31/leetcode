
Assumption:
1. Input is a list of words, return a list of pair that can form a palindrom
2. The given list does not contain duplicate word
3. The given list may have empty string
4. Does not allow word to form palindrome with itself

Approach:
    - Two word can form a palindrome if one word is symetric with another, or symetric with
      some prefix or postfix of another and the rest of it is a palindrom.
    - store the reverse of all words in a hash table.
        for each word, try all prefix and postfix, 
            if prefix in the table and postfix is palindrom, 
                we find a pair. 
            if postfix in the table and prefix is palindrome, 
                we also find a pair.


Ex:
    - "abc","ba"  ba is symetric with prefix ab and c is a palindrome, therefore, the two words 
       can form a palindrome.

Time: O(n*l^2)
O(nl) to create map, O(n*l*l) to check
average length of each word: l
length of list: n
Space: O(n)
O(n) to store the reverse string
===================================


import java.util.*;

public class BPalindromPair {
    public static void main(String[] args) {
        BPalindromPair sol = new BPalindromPair();
        List<String> words = Arrays.asList("abc", "ab", "ba", "cba" );
        List<List<String>> res = sol.findPair(words);
        System.out.println(res);
    }

    public List<List<String>> findPair(List<String> words) {
        Map<String, Integer> map = new HashMap<>();
        List<List<String>> res = new ArrayList<>();

        // put reverse words in map, use i to make sure it's not combining with itself
        for (int i = 0; i < words.size(); i++) {
            map.put(reverse(words.get(i)), i);
        }

        // loop through all words in the list.
        for (int i = 0; i < words.size(); i++) {
            String cur = words.get(i);

            // if word itself is "", check if a single word is a palindrome
            if (cur.equals("")) {   
                for (int j = 0; j < words.size(); j++) {
                    if (i != j && isPalindrom(words.get(j))) {
                        res.add(Arrays.asList("", words.get(j)));
                    }
                }
            }
            // check all possible prefix and postfix
            for (int j = 0; j < cur.length(); j++) {
                String prefix = cur.substring(0, j);
                String postfix = cur.substring(j, cur.length());

                // need to check it's not itself!!
                if (map.containsKey(prefix) && i != map.get(prefix) && isPalindrom(postfix)) {
                    res.add(Arrays.asList(words.get(i), reverse(prefix)));
                }
                if (map.containsKey(postfix) && i != map.get(postfix) && isPalindrom(prefix)) {
                    res.add(Arrays.asList(reverse(postfix), words.get(i)));
                }
            }
        }
        return res;
    }

    private String reverse(String input) {
        StringBuilder sb = new StringBuilder(input);
        return sb.reverse().toString();
    }

    private boolean isPalindrom(String input) {
        return input.equals(reverse(input));
    }
}




==========================================================
method 2: 
    - trie 

Stats:
    -   - O(m * n ^ 2) where m is the length of the list and the n is the length of the word.

private static class TrieNode {
    TrieNode[] next;
    int index;
    List<Integer> list;
    	
    TrieNode() {
    	next = new TrieNode[26];
    	index = -1;
    	list = new ArrayList<>();
    }
}
    
public List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> res = new ArrayList<>();

    TrieNode root = new TrieNode();
		
    for (int i = 0; i < words.length; i++) {
        addWord(root, words[i], i);
    }
		
    for (int i = 0; i < words.length; i++) {
        search(words, i, root, res);
    }
    
    return res;
}
    
private void addWord(TrieNode root, String word, int index) {
    for (int i = word.length() - 1; i >= 0; i--) {
        int j = word.charAt(i) - 'a';
				
        if (root.next[j] == null) {
            root.next[j] = new TrieNode();
        }
				
        if (isPalindrome(word, 0, i)) {
            root.list.add(index);
        }
				
        root = root.next[j];
    }
    	
    root.list.add(index);
    root.index = index;
}
    
private void search(String[] words, int i, TrieNode root, List<List<Integer>> res) {
    for (int j = 0; j < words[i].length(); j++) {	
    	if (root.index >= 0 && root.index != i && isPalindrome(words[i], j, words[i].length() - 1)) {
    	    res.add(Arrays.asList(i, root.index));
    	}
    	
    	//move to the next one
    	root = root.next[words[i].charAt(j) - 'a'];
      	if (root == null) return;
    }
    	
    for (int j : root.list) {
    	if (i == j) continue;
    	res.add(Arrays.asList(i, j));
    }
}
    
private boolean isPalindrome(String word, int i, int j) {
    while (i < j) {
    	if (word.charAt(i++) != word.charAt(j--)) return false;
    }
    	
    return true;
}

