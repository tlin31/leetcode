1048. Longest String Chain - Medium

Given a list of words, each word consists of English lowercase letters.

Lets say word1 is a predecessor of word2 if and only if we can add exactly one letter anywhere in 
word1 to make it equal to word2.  For example, "abc" is a predecessor of "abac".

A word chain is a sequence of words [word_1, word_2, ..., word_k] with k >= 1, where word_1 is a
predecessor of word_2, word_2 is a predecessor of word_3, and so on.

Return the longest possible length of a word chain with words chosen from the given list of words.

 

Example 1:

Input: ["a","b","ba","bca","bda","bdca"]
Output: 4
Explanation: one of the longest word chain is "a","ba","bda","bdca".
 

Note:

1 <= words.length <= 1000
1 <= words[i].length <= 16
words[i] only consists of English lowercase letters.



******************************************************
key:
	- sort with length & order first， Hashmap
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time O(NlogN) for sorting, Time O(NSS) for the for loop, where the second S refers to the string generation and S <= 16.
	- Space O(NS)



Method:

	-	Sort the words by words length. 
		For each word, loop on all possible previous word with 1 letter missing.
		If we have seen this previous word, update the longest chain for the current word.
		Finally return the longest word chain.


  public int longestStrChain(String[] words) {
        Map<String, Integer> dp = new HashMap<>();
        Arrays.sort(words, (a, b)->a.length() - b.length());
        int res = 0;
        for (String word : words) {
            int best = 0;

            // delete one char from current word to find the previous string
            for (int i = 0; i < word.length(); ++i) {
                String prev = word.substring(0, i) + word.substring(i + 1);
                best = Math.max(best, dp.getOrDefault(prev, 0) + 1);
            }
            dp.put(word, best);
            res = Math.max(res, best);
        }
        return res;
    }


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



