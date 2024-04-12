1170. Compare Strings by Frequency of the Smallest Character - Easy

Let us define a function f(s) over a non-empty string s, which calculates the frequency of 
the smallest character in s. For example, if s = "dcce" then f(s) = 2 because the smallest 
character is "c" and its frequency is 2.

Now, given string arrays queries and words, return an integer array answer, where each 
answer[i] is the number of words such that f(queries[i]) < f(W), where W is a word in words.

 

Example 1:
Input: queries = ["cbd"], words = ["zaaaz"]
Output: [1]
Explanation: On the first query we have f("cbd") = 1, f("zaaaz") = 3 so f("cbd") < f("zaaaz").
optpo9
Example 2:
Input: queries = ["bbb","cc"], words = ["a","aa","aaa","aaaa"]
Output: [1,2]
Explanation: On the first query only f("bbb") < f("aaaa"). On the second query both f("aaa") and f("aaaa") are both > f("cc").
 

Constraints:

1 <= queries.length <= 2000
1 <= words.length <= 2000
1 <= queries[i].length, words[i].length <= 10
queries[i][j], words[i][j] are English lowercase letters.


******************************************************
key:
	- count or binary search
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- For each word in words array find the fCount and increment the fCount array. 
	  The fCount array holds the number of words with current fCount.

	- For the fCount array find the running sum (a sum array). This is the second for loop. At each index 
	  of running sum this is the number of words with fcount of [0, i].

	- Find the fCount for the word in queries. Since we want the number of fCount words that 
	  are greater than fCount of query the answer will be fCount[fCount.length -1] which is the 
	  number of all Fcounts - the number of all fCounts of queries of i.
	- 

stats:

	- O(n)
	- 






class Solution {
    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        
        int [] fCount = new int[11];
        
        for(String word : words){
            int count = getFCount(word);
            fCount[count]++;
        }
        
        
        int sum = 0;
        for(int i=0;i<fCount.length;i++){
            sum += fCount[i];
            fCount[i] = sum;
        }
        
        int [] res = new int[queries.length];
        
        for(int i=0;i<queries.length;i++){
            
            int count = getFCount(queries[i]);
            res[i] = fCount[fCount.length -1] - fCount[count];
        }
        
        return res; 
    }
    
    public int getFCount(String word){
        int [] count = new int[26];
            
        for(int i=0;i<word.length();i++){
            count[word.charAt(i)-'a']++;
        }

        // get frequency of smallest char
        for(int i=0;i<count.length;i++){            
            if(count[i] != 0){
                return count[i];
            }
        }
        
        return 0;
    }
}

=======================================================================================================
method 2:

method:

	- binary search
	- We count func for every element of queries and words. Then for every query word we 
	  count how many of func words elements satisfies criteria. 
	  For that because we only care about the number of elements - sort the array and use binary search.


stats:

	- 
	- 


    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        //create arrays to save func for each query and word
        int[] q = new int[queries.length];
        int[] w = new int[words.length];

        //fill q and w for each array member
        for (int i = 0; i < q.length; i++) {
            q[i] = getFunc(queries[i]);
        }
        for (int i = 0; i < w.length; i++) {
            w[i] = getFunc(words[i]);
        }

        //because we care only of the number of elements we can utilize binary search
        //we sort the array of funcs for words and then find first element that is greater
        //then q[i]. Then based on indexes we can conclud how much elements are greater or 
        //smaller
        Arrays.sort(w);
        int[] res = new int[q.length];
        for (int i = 0; i < q.length; i++) {
            int l = 0, r = w.length - 1;
            while (l <= r) {
                int m = (r + l) / 2;
                if (w[m] <= q[i]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
            res[i] = w.length - l;
        }
        return res;
    }
    
    int getFunc(String s) {
        char chr = 'z';
        int count = 0;

        // iterate over every character, keep current running smallest character. 
        // If we found the same one increment count, if finda smaller char, then start counter from 1. 
        // If this character is greater - ignore it
        for (char ch : s.toCharArray()) {
            if (ch < chr) {
                count = 1;
                chr = ch;
            } else if (chr == ch) {
                count++;
            }
        }
        return count;
    }

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



