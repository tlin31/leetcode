843. Guess the Word - Hard

This problem is an interactive problem new to the LeetCode platform.

We are given a word list of unique words, each word is 6 letters long, and one word in this list 
is chosen as secret.

You may call master.guess(word) to guess a word.  The guessed word should have type string and 
must be from the original list with 6 lowercase letters.

This function returns an integer type, representing the number of exact matches (value and position) 
of your guess to the secret word.  Also, if your guess is not in the given wordlist, it will 
return -1 instead.

For each test case, you have 10 guesses to guess the word. At the end of any number of calls, 
if you have made 10 or less calls to master.guess and at least one of these guesses was the 
secret, you pass the testcase.

Besides the example test case below, there will be 5 additional test cases, each with 100 words 
in the word list.  The letters of each word in those testcases were chosen independently at 
random from 'a' to 'z', such that every word in the given word lists is unique.

Example 1:
Input: secret = "acckzz", wordlist = ["acckzz","ccbazz","eiowzz","abcczz"]

Explanation:

master.guess("aaaaaa") returns -1, because "aaaaaa" is not in wordlist.
master.guess("acckzz") returns 6, because "acckzz" is secret and has all 6 matches.
master.guess("ccbazz") returns 3, because "ccbazz" has 3 matches.
master.guess("eiowzz") returns 2, because "eiowzz" has 2 matches.
master.guess("abcczz") returns 4, because "abcczz" has 4 matches.


******************************************************
key:
	- minimax, 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- use the answer from master.guess as a filter to shrink the range of candidates, 
	  secret will always pass the filter.

stats:

	- Runtime: 1 ms, faster than 93.91% of Java online submissions for Guess the Word.
	- Memory Usage: 37.5 MB, less than 7.41% of Java online submissions for Guess the Word.


class Solution {
    public void findSecretWord(String[] wordlist, Master master) {

		// transfer String[] to list, so that we can remove item.
	    List<String > list = new ArrayList<>(Arrays.asList(wordlist)); 

	    for(int i =0 ;i<10;i++){
	        String test = list.get(0);
	        int  a = master.guess(test);

	        // get the feedback and use it to shrink the range of candidates.

	        // if answer is right, stop this program.
	        if(a==test.length()) 
	        	return;

	        //answer is wrong, remove the guessed string
	        list.remove(0);

	        int j=0;
	        Collections.sort(list);

			// delete candidates with difference != a个
			// if a = 0, delete any candidates that have > 0 个same char as test
	        while( j<list.size()){
	            if(compare(list.get(j),test)!=a ||(a==0 && compare(list.get(j),test) > a)){
	                list.remove(j);
	            }
	            else 
	            	j++;
	        }
	    }

    }

    // return number of different chars
    private int compare(String s1, String s2){
        int ans =0;
        for(int i =0;i<s1.length();i++){
            if(s1.charAt(i)==s2.charAt(i)) 
            	ans++;
        }
        return ans;
    }
}

=======================================================================================================
method 2:

method:

	- minimax
	- make a choice that minimizes the maximum possible number of choices (worst outcome) we have 
	  to make at each time that we call master.guess(word).
	- Note: caching all the similarity between two words is not necessary because the computation 
	  is constant time given the length of the string is a constant

stats:

	- Time complexity O(N^2)
	- Space complexity O(N)
	- Runtime: 5 ms, faster than 41.14% of Java online submissions for Guess the Word.
	- Memory Usage: 37.9 MB, less than 7.41% of Java online submissions for Guess the Word.


class Solution {
    int N = 6;
    
    public void findSecretWord(String[] words, Master master) {
        Set<Integer> options = new HashSet<>();
        for (int i = 0; i < words.length; i++) 
        	options.add(i);
        while (options.size() > 0) {
            int min = Integer.MAX_VALUE;
            int minIdx = -1;
            for (int i : options) {
                int max = maxLoss(i, words, options);
                if (max < min) {
                    min = max;
                    minIdx = i;
                }
            }
            int match = master.guess(words[minIdx]);

            //made the correct guess, return
            if (match == N) return;

            Set<Integer> next = new HashSet<>();
            for (int i : options) {
                if (similarity(words[minIdx], words[i]) == match) {
                    next.add(i);
                }
            }
            options = next;
        }
    }
    
    private int maxLoss(int wordIdx, String[] words, Set<Integer> options) {
        int[] bucket = new int[N];
        int maxLoss = 0;
        for (int i : options) {
            if (!words[wordIdx].equals(words[i])) {
                int sim = similarity(words[wordIdx], words[i]);
                bucket[sim]++;
                maxLoss = Math.max(maxLoss, bucket[sim]);
            }
        }
        return maxLoss;
    }
    
    private int similarity(String s1, String s2) {
        int match = 0;
        for (int i = 0; i < N; i++) {
            match += s1.charAt(i) == s2.charAt(i) ? 1 : 0;
        }
        return match;
    }
}
=======================================================================================================
method 3:

method:

	- In the previous solution, we compaired each two words.
	- count the occurrence for each character on each position.
	- If we can guess the word that not in the wordlist, we can guess the word based on the 
	   most frequent character on the position.

stats:
	- Time complexity O(N)
	- Space complexity O(N)
	- Runtime: 1 ms, faster than 93.91% of Java online submissions for Guess the Word.
	- Memory Usage: 37.3 MB, less than 7.41% of Java online submissions for Guess the Word.



    public void findSecretWord(String[] wordlist, Master master) {
        for (int t = 0, x = 0; t < 10 && x < 6; ++t) {
            int count[][] = new int[6][26], 
                best = 0;
            for (String w : wordlist)
                for (int i = 0; i < 6; ++i)
                    count[i][w.charAt(i) - 'a']++;
            String guess = wordlist[0];
            for (String w: wordlist) {
                int score = 0;
                for (int i = 0; i < 6; ++i)
                    score += count[i][w.charAt(i) - 'a'];
                if (score > best) {
                    guess = w;
                    best = score;
                }
            }
            x = master.guess(guess);
            List<String> wordlist2 = new ArrayList<String>();
            for (String w : wordlist)
                if (match(guess, w) == x)
                    wordlist2.add(w);
            wordlist = wordlist2.toArray(new String[0]);
        }
    }

