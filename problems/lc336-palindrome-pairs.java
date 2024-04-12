336. Palindrome Pairs - Hard

Given a list of unique words, find all pairs of distinct indices (i, j) in the given list, so that 
the concatenation of the two words, i.e. words[i] + words[j] is a palindrome.

Example 1:

Input: ["abcd","dcba","lls","s","sssll"]
Output: [[0,1],[1,0],[3,2],[2,4]] 
Explanation: The palindromes are ["dcbaabcd","abcddcba","slls","llssssll"]

Example 2:
Input: ["bat","tab","cat"]
Output: [[0,1],[1,0]] 
Explanation: The palindromes are ["battab","tabbat"]

******************************************************
key:
	- Trie
	- edge case:
		1) empty list, return []
		2) just one word, check whether it is a palindrome
	- Question to ask: all lower case?

******************************************************



=======================================================================================================
method 1:

method:

	- O(n^2 * k) with n the total number of words in the words array and k the average length of each 
		word: for each word, we simply go through the words array and check whether the concatenated 
		string is a palindrome or not.

	- check for palindromes: maintain two pointers i and j, with i pointing to the start of the string 
		and j to the end of the string. Characters pointed by i and j are compared. If at any time the 
		characters pointed by them are not the same, we conclude the string is not a palindrome. 

		Otherwise we move the two pointers towards each other until they meet in the middle and the 
		string is a palindrome.

	- For example, let us say we want to append words to w0, which starts with character 'a'. Then we 
		only need to consider words ending with character 'a', i.e., this will single out all words 
		ending with character 'a'. If the second character of w0 is 'b', for instance, we can further 
		reduce our candidate set to words ending with string "ba", etc. Our naive solution throws away 
		all these useful pieces of information and repeats the comparison, which leads to the undesired 
		O(n^2 * k) time complexity.

	- Now we will rearrange each word into this Trie structure: for each word, simply starting from 
		its last character and identify the node at the next layer by indexing into root s next array 
		with index given by the difference between the ending character and character 'a'. 

		If the indexed node is null, create a new node. Continue to the next layer and towards the 
		beginning of the word in this manner until we are done with the word, at which point we will 
		label the isWord field of the final node as true.

	- After building up the Trie structure, we can proceed to search for pairs of palindromes for 
		each word in the words array. I will use the following example to explain how it works and 
		make possible modifications of the TrieNode we proposed above.

	- Let us say we have these words: ["ba", "a", "aaa"], the Trie structure will be as follows:

		        root (f)
		           | 'a'
		          n1 (t)
		     ------------
		 'b' |          | 'a'
		    n2 (t)    n3 (f)
		                | 'a'
		              n4 (t)

		The letter in parentheses indicates the value of isWord for each node: f ==> false and 
		t ==> true. The letter beside each vertical line denotes the index into the next array of 
		the corresponding node. 

	- improved TrieNode:

		class TrieNode {
		    TrieNode[] next;
		    int index;
		    List<Integer> list;
		            
		    TrieNode() {
		        next = new TrieNode[26];
		        index = -1;
		        list = new ArrayList<>();
		    }
		}



		          root (-1,[1,2])
		            | 'a'
		          n1 (1,[0,1,2])
		    ---------------------
		'b' |                 | 'a'
		n2 (0,[0])       n3 (-1,[2])
		                      | 'a'
		                 n4 (2,[2])

		The first integer in the parentheses = index of the word in the words array (defaulted -1
        The list are the indices of words that can form palindrome with this node

	- Let us continue with the third word "aaa" with this new structure. Indexing into array root.next 
		at index given by 'a' - 'a' = 0 will yield node n1 and n1.index = 1 >= 0, which means we have 
		a valid word now. The index of this word (which is 1) is also different from the index of the 
		word currently being visited, a.k.a "aaa" (which is 2). So pair (2,1) is a possible 
		concatenation to form a palindrome. 

		But still we need to check the rest of "aaa" (excluding the substring represented by current 
		node n1 which is "a" from the beginning of "aaa") to see if it is a palindrome. If so, (2,1) 
		will be a valid combination. 

		We continue in this fashion until we reach the end of "aaa". Lastly we will check n4.list to 
		see if there are any words satisfying the two conditions specified in step 2 which are 
		different from current word, and add the corresponding valid pairs.


stats:
	- O(m * n ^ 2) where m is the length of the list and the n is the length of the word.
	- Runtime: 28 ms, faster than 85.21% of Java online submissions for Palindrome Pairs.
	- Memory Usage: 42.5 MB, less than 100.00% 


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
        
    // main function
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
        
    // create trie
    private void addWord(TrieNode root, String word, int index) {

        // build from end of string
        for (int i = word.length() - 1; i >= 0; i--) {
            int j = word.charAt(i) - 'a';
    				
            if (root.next[j] == null) {
                root.next[j] = new TrieNode();
            }
    		
            // if this word can form a palindrome by itself		
            if (isPalindrome(word, 0, i)) {
                root.list.add(index);
            }
    				
            root = root.next[j];
        }
        	
        root.list.add(index);
        root.index = index;
    }
        
    // i tells which word in list of words[]
    private void search(String[] words, int i, TrieNode root, List<List<Integer>> res) {
        
        // j is the index looping through words[i]
        for (int j = 0; j < words[i].length(); j++) {	

            // 1. root.index >= 0 --> reach end of the word
            // 2. root.index != i --> not repeating itself/concate with itself
            // 3. subproblem is also a palindrome, here already assume stiring i's [0~j] find a palindrome
        	if (root.index >= 0 && root.index != i && isPalindrome(words[i], j, words[i].length() - 1)) {
        	    res.add(Arrays.asList(i, root.index));
        	}
        	
        	//move to the next one
        	root = root.next[words[i].charAt(j) - 'a'];
          	if (root == null) 
                return;
        }
        	
        for (int possibleIndex : root.list) {
        	if (i == possibleIndex) 
                continue;
        	res.add(Arrays.asList(i, possibleIndex));
        }
    }
        
    private boolean isPalindrome(String word, int i, int j) {
        while (i < j) {
        	if (word.charAt(i++) != word.charAt(j--)) 
                return false;
        }
        	
        return true;
    }


=======================================================================================================
method 2:

stats:

    - Runtime: 96 ms, faster than 22.11% of Java online submissions for Palindrome Pairs.
    - 

method:

	- Step 1: store every word with its index into a hash map.
	- Step 2: For each word in the array, split into two parts str1 and str2. Check whether str1 
              and str2 is palindrome
                - If str1 is palindrome, we can use str1 as middle part, str2 as right part, and find 
                  if map contains reversed str2.
                - If contains, then we can use that string as left part, combine with middle part, 
                  right part, it will form a correct palindrome string. 
    - Step 3: do all same operations for str2 (set str2 as middle part) 
 
public List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> result = new ArrayList<>();
    if (words.length == 0) {
        return result;
    }

    // step1
    Map<String, Integer> map = new HashMap<>();
    for (int i = 0; i < words.length; i++) {
        map.put(words[i], i);
    }

    for (int i = 0; i < words.length; i++) {
        for (int j = 0; j <= words[i].length(); j++) {
            String str1 = words[i].substring(0, j);
            String str2 = words[i].substring(j);

            // step 2
            if (isPalindrome(str1)) {
                String reversedStr2 = new StringBuilder(str2).reverse().toString();

                // MUST CHECK whether str.length() is equal to 0 in either if statement, because
                // don't add duplicate pairs (if str1.length() == 0, both of str1 and str2 will from 
                // input array) 
                if (map.containsKey(reversedStr2) && map.get(reversedStr2) != i && str1.length() != 0) {
                    List<Integer> newPair = new ArrayList<>();
                    newPair.add(map.get(reversedStr2));
                    newPair.add(i);
                    result.add(newPair);
                }
            }

            // step 3
            if (isPalindrome(str2)) {
                String reversedStr1 = new StringBuilder(str1).reverse().toString();
                
                if (map.containsKey(reversedStr1) && map.get(reversedStr1) != i) {
                    List<Integer> newPair = new ArrayList<>();
                    newPair.add(i);
                    newPair.add(map.get(reversedStr1));
                    result.add(newPair);
                }
            }
        }
    }
    return result;
}

public boolean isPalindrome(String s) {
    for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
        if (s.charAt(i) != s.charAt(j)) {
            return false;
        }
    }
    return true;
}           


