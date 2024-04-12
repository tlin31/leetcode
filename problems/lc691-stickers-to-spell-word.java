691. Stickers to Spell Word - Hard


We are given N different types of stickers. Each sticker has a lowercase English word on it.

You would like to spell out the given target string by cutting individual letters from your collection of stickers and rearranging them.

You can use each sticker more than once if you want, and you have infinite quantities of each sticker.

What is the minimum number of stickers that you need to spell out the target? If the task is impossible, return -1.

Example 1:

Input:

["with", "example", "science"], "thehat"
Output:

3
Explanation:

We can use 2 "with" stickers, and 1 "example" sticker.
After cutting and rearrange the letters of those stickers, we can form the target "thehat".
Also, this is the minimum number of stickers necessary to form the target string.
Example 2:

Input:

["notice", "possible"], "basicbasic"
Output:

-1
Explanation:

We can not form the target "basicbasic" from cutting letters from the given stickers.
Note:

stickers has length in the range [1, 50].
stickers consists of lowercase English words (without apostrophes).
target has length in the range [1, 15], and consists of lowercase English letters.
In all test cases, all words were chosen randomly from the 1000 most common US English words, and the target was chosen as a concatenation of two random words.
The time limit may be more challenging than usual. It is expected that a 50 sticker test case can be solved within 35ms on average.


******************************************************
key:
	- DP , string
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DFS


Stats:





Method:

	-	sort target string s
	-	dp[string] = the minimum stickers required for string s (-1 if impossible). 
		
		Base case:
			dp[""] = 0

		Result:
			dp[target]

		dp[s] = min(1+dp[reduced_s]) for all stickers, 
									 reduced_s is a new string after certain sticker applied

	- Optimization: If the target can be spelled out by a group of stickers, at least one of them has 
	  to contain character target[0]. So I explicitly require next sticker containing target[0], 
	  which significantly reduced the search space.



class Solution {
    public int minStickers(String[] stickers, String target) {
        int m = stickers.length;
        int[][] mp = new int[m][26];
        Map<String, Integer> dp = new HashMap<>();

        // iterate all stickers, store char count map in mp[i]
        for (int i = 0; i < m; i++) 
            for (char c:stickers[i].toCharArray()) 
            	mp[i][c-'a']++;

        dp.put("", 0);

        return helper(dp, mp, target);
    }

    private int helper(Map<String, Integer> dp, int[][] mp, String target) {
        
        if (dp.containsKey(target)) 
        	return dp.get(target);

        int ans = Integer.MAX_VALUE, 
              n = mp.length;

        // put target string as count map array
        int[] tar = new int[26];
        for (char c:target.toCharArray()) 
        	tar[c-'a']++;

        // try every sticker
        for (int i = 0; i < n; i++) {

            // optimization, if doesn't contain first char, skip this sticker
            if (mp[i][target.charAt(0)-'a'] == 0) 
            	continue;

            StringBuilder sb = new StringBuilder();

            // apply a sticker on every character a-z
            for (int j = 0; j < 26; j++) {
                if (tar[j] > 0 ) 

                	// sb = not find with this sticker, need to pass on to next iteration
                    for (int k = 0; k < Math.max(0, tar[j]-mp[i][j]); k++)
                        sb.append((char)('a'+j));
            }

            String s = sb.toString();
            int tmp = helper(dp, mp, s);

            // if find a valid string, update result
            if (tmp != -1) 
            	ans = Math.min(ans, 1+tmp);
        }

        dp.put(target, ans == Integer.MAX_VALUE? -1:ans);

        return dp.get(target);
    }
}


ex. ["with","example","science"], target = "thehat"

output:
append: for i = 0, k = 0, j = a
append: for i = 0, k = 0, j = e
append: for i = 0, k = 0, j = h
append: for i = 0, k = 0, j = t
----for i = 0, s = aeht 		--> use 'with'

append: for i = 1, k = 0, j = h
append: for i = 1, k = 0, j = t
----for i = 1, s = ht			--> use 'example'

----for i = 0, s = 


dp = {=0, ht=1}
dp = {=0, ht=1, aeht=2}
dp = {=0, thehat=3, ht=1, aeht=2}




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:DP

Stats:

    - Time Complexity: O(2^T * S * T) where S be the total number of letters in all stickers, and T 
                       be the number of letters in the target word. We can examine each loop carefully 
                       to arrive at this conclusion.
    - Space Complexity: O(2^T),the space used by dp.


Method:
    - Suppose we need dp[state] stickers to satisfy all target[i]s for which the i-th bit of state 
      is set. We would like to know dp[(1 << len(target)) - 1].

    - For each state, lets work with it as now and look at what happens to it after applying a sticker. 
      For each letter in the sticker that can satisfy an unset bit of state, we set the bit 
      (now |= 1 << i). 
      At the end, we know now is the result of applying that sticker to state, and we update our dp 
      appropriately.
    - use unsigned number from 0 to 2^n-1 as bitmap to represent every subset of target;
        then populate all of these subset from 0 to 2^n-1 by trying to apply 1 sticker at each time.
        Eventually you might or might not get the ultimate result 2^n-1, which is target, populated.
        If not, it is -1;


class Solution {
    public int minStickers(String[] stickers, String target) {
        // if target has n chars, there will be m=2^n subset of characters in target
        int n = target.length(), 
            m = 1 << n; 
        int[] dp = new int[m];

        // use index 0 - 2^n as bitmaps to represent each subset of all chars in target
        for (int i = 0; i < m; i++) 
            dp[i] = Integer.MAX_VALUE; 

        // base case: dp[empty set] requires 0 sticker
        dp[0] = 0; 

        // for every subset i, start from 000...000
        for (int i = 0; i < m; i++) { 
            if (dp[i] == Integer.MAX_VALUE) 
                continue;

            // try use each sticker as an char provider to populate 1 of its superset, to do that:
            for (String s : stickers) { 
                int sup = i;

                // for each char in the sticker, try apply it on a missing char in the subset of target
                for (char c : s.toCharArray()) { 
                    for (int r = 0; r < n; r++) {
                        if (target.charAt(r) == c && ((sup >> r) & 1) == 0) {
                            sup |= 1 << r;
                            break;
                        }
                    }
                }
               // after you apply all possible chars in a sticker, you get an superset that take 1 
               // extra sticker than subset would take, update the superset's minsticker number 
                dp[sup] = Math.min(dp[sup], dp[i] + 1);
            }
        }
        return dp[m - 1] != Integer.MAX_VALUE ? dp[m - 1] : -1;
    }
}




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3: BFS

Stats:

	- 
	- 


Method:

	-	
	-	



    // use bfs, put every left string in queue using each sticker
    // when every character number is 0, means we spell the word
    public int minStickers(String[] stickers, String target) {
        int min = 0;
        if (target == null || target.length() == 0) {
            return min;
        }
        Queue queue = new LinkedList<>();
        int[] tMap = new int[26];
        for (int i = 0; i < target.length(); i++) { // construct map for target
            tMap[target.charAt(i) - 'a']++;
        }
        int sLen = stickers.length;
        int[][] sMap = new int[sLen][26];
        for (int i = 0; i < sLen; i++) { // construct map for stickers
            for (int j = 0; j < stickers[i].length(); j++) {
                sMap[i][stickers[i].charAt(j) - 'a']++;
            }
        }
        queue.offer(tMap);
        Set set = new HashSet<>(); // record left over substring we've already spelled
        while (!queue.isEmpty()) {
            min++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cur = queue.poll();
                String curString = toSortedString(cur); // we sort string to be put in the set
                if (set.add(curString)) { // check if we already spell current string
                    for (int j = 0; j < sLen; j++) {
                        if (sMap[j][curString.charAt(0) - 'a'] == 0) { // if current sticker does not have the first char in current string, it is impossible for that sticker to have following char, so continue
                            continue;
                        }
                        int[] temp = cur.clone();
                        for (int k = 0; k < 26; k++) {
                            if (temp[k] > 0) {  // only update temp when the current sticker has the char
                                int left = temp[k] - sMap[j][k];
                                temp[k] = left > 0 ? left : 0;   
                            }
                        }
                        if (wordIsSpelled(temp)) { // if temp doesn't have any char left, it means we spell the word, and the number of stickers we used must be the minimum
                            return min;
                        }
                        queue.offer(temp);
                    }
                }
            }
        }
        return -1;
    }
    private String toSortedString(int[] word) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < word[i]; j++) {
                sb.append((char) (i + 'a'));
            }
        }
        return sb.toString();
    }
    private boolean wordIsSpelled(int[] word) {
        for (int i = 0; i < word.length; i++) {
            if (word[i] > 0) {
                return false;
            }
        }
        return true;
    }
}




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

