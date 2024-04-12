809. Expressive Words - Medium

Sometimes people repeat letters to represent extra feeling, such as "hello" -> "heeellooo", "hi" -> 
"hiiii".  In these strings like "heeellooo", we have groups of adjacent letters that are all the same:
"h", "eee", "ll", "ooo".

For some given string S, a query word is stretchy if it can be made to be equal to S by any number of 
applications of the following extension operation: choose a group consisting of characters c, and add 
some number of characters c to the group so that the size of the group is 3 or more.

For example, starting with "hello", we could do an extension on the group "o" to get "hellooo", but we
cannot get "helloo" since the group "oo" has size less than 3.  Also, we could do another extension 
like "ll" -> "lllll" to get "helllllooo".  If S = "helllllooo", then the query word "hello" would be 
stretchy because of these two extension operations: query = "hello" -> "hellooo" -> "helllllooo" = S.

Given a list of query words, return the number of words that are stretchy. 

Example:
Input: 
S = "heeellooo"
words = ["hello", "hi", "helo"]
Output: 1
Explanation: 
We can extend "e" and "o" in the word "hello" to get "heeellooo".
We can not extend "helo" to get "heeellooo" because the group "ll" is not size 3 or more.


******************************************************
key:
	- hashmap or 2 pointers
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- hashmap
	- keep count of each letter "key" of "abcda", and the "count" [1,2,3,4,5].
	- Now, let us say we have individual counts c1 = S.count[i] and c2 = word.count[i].
		1) If c1 < c2, then we can not make the ith group of word equal to the ith word of S by 
		   adding characters.

		2) If c1 >= 3, then we can add letters to the ith group of word to match the ith group 
		   of S, as the latter is extended.

		3) Else, if c1 < 3, then we must have c2 == c1 for the ith groups of word and S to match.

stats:

	- Time Complexity: O(QK), where Q is the length of words (at least 1), and K is the maximum l
					   length of a word.

	- Space Complexity: O(K).


class Solution {
    public int expressiveWords(String S, String[] words) {
        RLE R = new RLE(S);
        int ans = 0;

        search: for (String word: words) {
            RLE R2 = new RLE(word);
            if (!R.key.equals(R2.key)) continue;
            for (int i = 0; i < R.counts.size(); ++i) {
                int c1 = R.counts.get(i);
                int c2 = R2.counts.get(i);
                if (c1 < 3 && c1 != c2 || c1 < c2)
                    continue search;
            }
            ans++;
        }
        return ans;
    }
}

class RLE {
    String key;
    List<Integer> counts;

    public RLE(String S) {
        StringBuilder sb = new StringBuilder();
        counts = new ArrayList();

        char[] ca = S.toCharArray();
        int N = ca.length;
        int prev = -1;
        for (int i = 0; i < N; ++i) {
            if (i == N-1 || ca[i] != ca[i+1]) {
                sb.append(ca[i]);
                counts.add(i - prev);
                prev = i;
            }
        }

        key = sb.toString();
    }
}


=======================================================================================================
method 2:

method:

	- 2 pointers
	- Loop through all words. check(string S, string W) checks if W is stretchy to S.

		In check function, use two pointer:

		If S[i] == W[j, i++, j++
		If S[i - 2] == S[i - 1] == S[i] or S[i - 1] == S[i] == S[i + 1], i++
		return false

stats:

	- 
	- 

 	int expressiveWords(string S, vector<string>& words) {
        int res = 0;
        for (auto &W : words) 
            if (check(S, W)) 
                res++;
        return res;
    }

 	public boolean check(String S, String W) {
        int n = S.length(), 
            m = W.length(), 
            // 2 pointers:
            i = 0, 
            j = 0;
            
        for (int i2 = 0, j2 = 0; i < n && j < m; i = i2, j = j2) {
            if (S.charAt(i) != W.charAt(j)) 
            	return false;

            while (i2 < n && S.charAt(i2) == S.charAt(i)) 
            	i2++;

            while (j2 < m && W.charAt(j2) == W.charAt(j)) 
            	j2++;


            if (i2 - i != j2 - j 					// check if length of repetitive str is diff
            	&& i2 - i < Math.max(3, j2 - j)) 	// AND (test str has rep substr <3 
            										// 	    OR test str has rep < given list str's rep
            	return false;
        }
        return i == n && j == m;
    }



=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



