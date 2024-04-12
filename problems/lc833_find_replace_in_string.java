/**
leetcode 833
Question:
To some string S, we will perform some replacement operations that replace groups of letters
 with new ones (not necessarily the same size).

Each replacement operation has 3 parameters: a starting index i, a source word x and a target
word y.  The rule is that if x starts at position i in the original string S, then we will
replace that occurrence of x with y.  If not, we do nothing.

All these operations occur simultaneously.  It's guaranteed that there won't be any overlap in replacement: for example, S = "abc", indexes = [0, 1], sources = ["ab","bc"] is not a valid test case.

Example 1:

Input: S = "abcd", indexes = [0,2], sources = ["a","cd"], targets = ["eee","ffff"]
Output: "eeebffff"
Explanation: "a" starts at index 0 in S, so it's replaced by "eee".
"cd" starts at index 2 in S, so it's replaced by "ffff".
Example 2:

Input: S = "abcd", indexes = [0,2], sources = ["ab","ec"], targets = ["eee","ffff"]
Output: "eeecd"
Explanation: "ab" starts at index 0 in S, so it's replaced by "eee". 
"ec" doesn't starts at index 2 in the original S, so we do nothing.
Notes:

0 <= indexes.length = sources.length = targets.length <= 100
0 < indexes[i] < S.length <= 1000
All characters in given inputs are lowercase letters.
**/

// method 1
// The technique is like "piece table", which is used in editors. We record all the valid 
// operations first and put them into a piece table, then iterate the string index to "apply" these
// operations.


 public String findReplaceString(String S, int[] indexes, String[] sources, String[] targets) {
        Map<Integer, Integer> table = new HashMap<>();

        // step 1: check if there's such source at index, if so, puts into a table
        for (int i=0; i<indexes.length; i++) {
            // if a match is found in the original string, record it
            if (S.startsWith(sources[i], indexes[i])) {
                table.put(indexes[i], i);
            }
        }

        // create new string
        StringBuilder sb = new StringBuilder();

        //step 2: go through the original string, check at each char whether there's a key in table
        for (int i=0; i<S.length(); ) {
            if (table.containsKey(i)) {
                sb.append(targets[table.get(i)]);
                i+=sources[table.get(i)].length();

            } else {
                sb.append(S.charAt(i));
                i++;
            }
        }
        return sb.toString();
    }

//method 2
// construct string from right to left! (Since indexes.length <= 100 is really small)
// In this way, the different length won't bother us anymore.

// Explanation:
// Descending indexes[] with tracking its index.
// Verify equality of subring in S source and source.
// Replace S if necessay.

// Time Complexity:
// O(SN): Since there won't be any overlap in replacement,every character in S will be compared at most once.
// If using StringBuilder, it should be O(NlogN + S).

public String findReplaceString(String S, int[] indexes, String[] sources, String[] targets) {
        
        //sort all the indexes
        List<int[]> sorted = new ArrayList<>();
        for (int i = 0 ; i < indexes.length; i++) sorted.add(new int[]{indexes[i], i});
        Collections.sort(sorted, Comparator.comparing(i -> -i[0]));

    	// go through sorted indexes
        for (int[] ind: sorted) {
            int i = ind[0], 
            	j = ind[1];
            String s = sources[j], 
            	t = targets[j];
            	
            if (S.substring(i, i + s.length()).equals(s)) 
            	S = S.substring(0, i) + t + S.substring(i + s.length());
        }
        return S;
    }    
