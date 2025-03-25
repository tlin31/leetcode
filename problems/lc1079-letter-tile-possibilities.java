1079. Letter Tile Possibilities - Medium

You have a set of tiles, where each tile has one letter tiles[i] printed on it.  
Return the number of possible non-empty sequences of letters you can make.

 

Example 1:

Input: "AAB"
Output: 8
Explanation: The possible sequences are "A", "B", "AA", "AB", "BA", "AAB", "ABA", "BAA".

Example 2:
Input: "AAABBC"
Output: 188
 

Note:

1 <= tiles.length <= 7
tiles consists of uppercase English letters.


******************************************************
key:
	- Backtrack
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	We do not need to keep track of the sequence. We only need count
	-	If we implement the above idea by each level (Count all sequence of length 1, then 
        count all sequence of length 2, etc), we have to remember previous sequence.
	-	So we use recursion instead. Just remember to add the count back (arr[i]++).
	-	
arr[i]--; means we are using the i-th tile ('A'+i) as the current tile because there are still remaining ones.
sum++; means with these current tiles (not necessarily all the tiles given) we already have a valid combination.
sum += dfs(arr); means if we still want to add more tiles to the existing combination, we simply do recursion with the tiles left;
arr[i]++; is backtracking because we have finished exploring the possibilities of using the i-th tile and need to restore it and continueto explore other possibilities.


Time complexity: O(n!)

At each recursive call, we try all remaining characters, and the number of choices decreases by 1 each time since we're using frequency counting to handle duplicates. For an input string of length n, at the first level we have n choices, then (n−1) choices, and so on, leading to n⋅(n−1)⋅(n−2)...1 possibilities. This recursive pattern of decreasing choices at each level results in a time complexity of O(n!).

[!NOTE]
A common misconception is that the time complexity of this problem is O(2^n), stemming from the idea that each character has a binary decision, either to include or exclude. This may seem valid in problems involving combinations or subsets, but here, the goal is to generate all possible permutations of the tiles. Since we're considering character frequencies, the complexity grows factorially, not exponentially, leading to O(n!). Each recursive call handles one tile from a decreasing pool of remaining tiles, generating distinct sequences.



Space complexity: O(n)

The space complexity has two parts. First, the fixed-size array charCount takes O(1) space as it always has 26 elements regardless of input size. Second, the recursion stack can go up to depth n as each recursive call uses one character.

Therefore, the total space complexity is O(n).

    public int numTilePossibilities(String tiles) {
        int[] count = new int[26];
        for (char c : tiles.toCharArray()) 
        	count[c - 'A']++;
        return dfs(count);
    }
    
    int dfs(int[] arr) {
        int sum = 0;
        for (int i = 0; i < 26; i++) {
            if (arr[i] == 0) continue;
            sum++;
            arr[i]--;
            sum += dfs(arr);
            arr[i]++;
        }
        return sum;
    }


=======================================================================================================
method 2:

Method:

	-	the for loop always starting from 0
need to check each char in the input string only use once (using vis boolean array)
sort is not needed as order also matters in permutation
	-	


Stats:

	- 
	- 


class Solution {
    Set<String> set = new HashSet();
    boolean[] vis;

    public int numTilePossibilities(String tiles) {
        int n = tiles.length();
        vis = new boolean[n];
        dfs(new StringBuilder(), tiles);
        return set.size();
    }
    void dfs(StringBuilder sb, String tiles){
        if(sb.length()>0) 
            set.add(sb.toString());

        if(sb.length()>=tiles.length()) 
            return;

        for(int i=0;i<tiles.length();i++){
            if(vis[i]) continue;

            vis[i] = true;
            int len = sb.length();
            dfs(sb.append(tiles.charAt(i)), tiles);
            sb.setLength(len);
            vis[i] = false;
        }
    }
}
=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



