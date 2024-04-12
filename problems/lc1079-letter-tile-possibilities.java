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
	-	If we implement the above idea by each level (Count all sequence of length 1, then count all sequence of length 2, etc), we have to remember previous sequence.
	-	So we use recursion instead. Just remember to add the count back (arr[i]++).
	-	
arr[i]--; means we are using the i-th tile ('A'+i) as the current tile because there are still remaining ones.
sum++; means with these current tiles (not necessarily all the tiles given) we already have a valid combination.
sum += dfs(arr); means if we still want to add more tiles to the existing combination, we simply do recursion with the tiles left;
arr[i]++; is backtracking because we have finished exploring the possibilities of using the i-th tile and need to restore it and continue to explore other possibilities.

Stats:

	- 
	- 

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
        if(sb.length()>0) set.add(sb.toString());
        if(sb.length()>=tiles.length()) return;
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



