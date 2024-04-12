1055. Shortest Way to Form String - Medium

From any string, we can form a subsequence of that string by deleting some number of characters 
(possibly no deletions).

Given two strings source and target, return the minimum number of subsequences of source such that 
their concatenation equals target. If the task is impossible, return -1.

 

Example 1:

Input: source = "abc", target = "abcbc"
Output: 2
Explanation: The target "abcbc" can be formed by "abc" and "bc", which are subsequences of source "abc".


Example 2:
Input: source = "abc", target = "acdbc"
Output: -1
Explanation: The target string cannot be constructed from the subsequences of source string due to 
the character "d" in target string.

Example 3:
Input: source = "xyz", target = "xzyxz"
Output: 3
Explanation: The target string can be constructed as follows "xz" + "y" + "xz".
 

Constraints:

Both the source and target strings consist of only lowercase English letters from "a"-"z".
The lengths of source and target string are between 1 and 1000.


******************************************************
key:
	- binary search, 2 pointers, flatten map
	- edge case:
		1) check source includes all char in target
		2)

******************************************************



=======================================================================================================
method 1: Greedy

method:

	- two pointer, one for src, another for tar. 
	- for each tar char, we move src_iter until src[src_iter] == tar[i], if src_iter == src.length, 
	  res++, src_iter = 0;
	- we greedy match as many chars from src to tar as possible which can lead mininum use of src.
	- also build a set to save all the char in src, if there exists a char from tar which not exists 
	  in set, return -1.
	- 

stats:

	- 
	- 



public int shortestWay(String source, String target) {
	char[] cs = source.toCharArray(), 
	       ts = target.toCharArray();

	boolean[] map = new boolean[26];

	// add source to map
	for (int i = 0; i < cs.length; i++) 
		map[cs[i] - 'a'] = true;

	int src_iter = 0, res = 1;
	
	for (int target_iter = 0; target_iter < ts.length; target_iter++, src_iter++) {

		// target's char is not in source
		if (!map[ts[target_iter] - 'a']) 
			return -1;

		while (src_iter < cs.length && cs[src_iter] != ts[target_iter]) {
			src_iter++;
		}

		// reach the end of the source string, means we need another concatenation of source
		// so res++ & reset src_iter to -1 & step back target_iter by 1
		if (src_iter == cs.length) {
			src_iter = -1;
			res++;
			target_iter--;
		}
	}
	return res;
}


=======================================================================================================
follow up 1:  O(1) space, which mean without set

method:

	- check there is a char which not in src --> iterate src
	  if the j not move, then we can return -1.
	- Key: in the first for loop, we dont increment i, we keep it as a global variable

stats:

	- Time: O(MN)
	- Space: O(1)



public int shortestWay(String source, String target) {
	char[] cs = source.toCharArray(), 
	       ts = target.toCharArray();
	int res = 0;
	for (int i = 0; i < ts.length; ) {
		int oriI = i;
		for (int j = 0; j < cs.length; j++) {
			if (i < ts.length && cs[j] == ts[i])
				i++;
		}

		// after looping through scource, doesn't have a match in target
		if (i == oriI) 
			return -1;

		res++;
	}
	return res;
}

=======================================================================================================
follow up 2: improve time complexity 


method:

	- binary search, for each char in target, we need loop from j to end, to find a char same as tar[i]
	- we can build a map which key is from 'a' -> 'z', the value is idx for this char in src. 
	  because idx is add from small to big, when we iterate tar[i], we can easily to find the 
	  tar[i]s idx list. (build map is O(M))
	- to search if there is a idx  larger or equal than j+1. it is O(log M). and we have N char in 
	  tar, so the time complexity is N * logM
	- binary search returns index of the search key, if it is contained in the array, else it returns (-(insertion point) - 1). The insertion point is the point at which the key would be inserted into the array: the index of the first element greater than the key, or a.length if all elements in the array are less than the specified key.

	- j stores where we have go through in the source




stats:
	- time complexity O(N * logM);
	- 

public int shortestWay(String source, String target) {
	char[] cs = source.toCharArray(), 
	       ts = target.toCharArray();
	int res = 1;

	// build the map
	List<Integer>[] idx = new List[26];
	for (int i = 0; i < 26; i++) 
		idx[i] = new ArrayList<>();

	for (int i = 0; i < cs.length; i++) 
		idx[cs[i] - 'a'].add(i);


	int j = 0;
	for (int i = 0; i < ts.length; ) {
		List<Integer> tar = idx[ts[i] - 'a'];

        System.out.println("==================");
        System.out.println("tar is: "+tar);
        System.out.println("char is: "+ts[i]);

		// can't find char in source
		if (tar.isEmpty()) 
			return -1;

		int k = Collections.binarySearch(tar,j);
        System.out.println("k is: "+k);


		// didn't find k, means skip one or several char in source
		// recover k, now k is where j should in tar
		if (k < 0) 
			k = -k - 1;

		// if the position of this target char == last possible char in source
		// then we increment result, and start from the first in the source again.
		// note here we don't increment i, so it'll go through current char in target agian
		// and try to match from the start of the source
		if (k == tar.size()) {
			res++;
			j = 0;
		} else {
			// j jumps to the next character after index of k
			j = tar.get(k) + 1;
			i++;
		}
		System.out.println("j is: "+j);


	}
	return res;
}

"xyz"
"xzyxz"

==================
tar is: [0]
char is: x
k is: 0
j is: 1
==================
tar is: [2]
char is: z
k is: -1
j is: 3
==================
tar is: [1]
char is: y
k is: -2
j is: 0
// here res++
==================
tar is: [1]
char is: y
k is: -1
j is: 2
==================
tar is: [0]
char is: x
k is: -2
j is: 0
==================
tar is: [0]
char is: x
k is: 0
j is: 1
==================
tar is: [2]
char is: z
k is: -1
j is: 3


=======================================================================================================
follow up 3: improve time to O(N)

method:

	- in binary search solution we will have a map like a ->{1,3,7,16} (total src length is 20)
	- now we want to flatten them. For each pos in 20 length, we just save the next idx, we can 
	  use O 1 to find the next J.

	  pattern: store the range from current value to map[value] with the next index

			a -> {1,1,3,3,7,7,7,7,16,16,16,16,16,16,16,16,16,0,0,0}

	- ex.if now j is 4, we can just check map[4] = 7; we know 7 pos have an 'a', so next j will 
	  be 7 + 1.
	  if now j is 17, we get map[17] = 0, we know there is no more j after. so j = 0, res++;


stats:

	- the time complexity is O (N) , and build the map cost 26 * M
	- 


public int shortestWay(String source, String target) {
	char[] cs = source.toCharArray(), 
	       ts = target.toCharArray();
	int[][] idx = new int[26][cs.length];

	// create the index of next char for flatten map
	for (int i = 0; i < cs.length; i++) 
		idx[cs[i] - 'a'][i] = i + 1;

	// create flatten map
	for (int i = 0; i < 26; i++) {
		for (int j = cs.length - 1, pre = 0; j >= 0; j--) {
			if (idx[i][j] == 0) 
				idx[i][j] = pre;
			else 
				pre = idx[i][j];
		}
	}

	// j stores position of source we've gone thru
	int res = 1, j = 0;
	for (int i = 0; i < ts.length; i++) {
		if (j == cs.length) {
			j = 0;
			res++;
		}
		if (idx[ts[i] - 'a'][0] == 0) 
			return -1;

		// position of the next index
		j = idx[ts[i] - 'a'][j];
		if (j == 0 ) {
			res++;
			i--;
		}
	}
	return  res;
}



=======================================
 O(|Σ|*M + N) , where M is length of S and N is length of target).

The main idea behind this code is also to build up an inverted index data structure for the source string and then to greedily use characters from source to build up the target. In this code, it's the dict array. Each character is mapped to an index where it is found at in source. In this code, dict[i][c - 'a'] represents the earliest index >= i where character c occurs in source.

For example, if source = "xyzy", then dict[0]['y' - 'a'] = 1 but dict[2]['y'-'a'] = 3.

Also a value of -1, means that there are no occurrences of character c after the index i.

So, after this inverted data structure is built (which took O(|Σ|*M) time). We iterate through the characters of our target String. The idxOfS represents the current index we are at in source.
For each character c in target, we look for the earliest occurrence of c in source using dict via dict[idxOfS][c - 'a']. If this is -1, then we have not found any other occurrences and hence we need to use a new subsequence of S.

Otherwise, we update idxOfS to be dict[idxOfS][c - 'a'] + 1 since we can only choose characters of source that occur after this character if we wish to use the same current subsequence to build the target.

dict[idxOfS][c-'a'] = N - 1 is used as a marker value to represent that we have finished consuming the entire source and hence need to use a new subsequence to continue.

(I would highly recommend reading @Twohu's examples of how to use the inverted index data structure to greedily build target using the indexes. They go into much more detail).

At the end, the check for (idxOfS == 0? 0 : 1) represents whether or not we were in the middle of matching another subsequence. If we were in the middle of matching it, then we would need an extra subsequence count of 1 since it was never accounted for.

Hopefully, this helped a little. I really liked this solution and it wasn't commented so it may be hard to understand directly.

I included my commented and slightly modified code:

class Solution {
    public int shortestWay(String source, String target) {
        char[] s = source.toCharArray();
        char[] t = target.toCharArray();
        
        int M = s.length;
        int N = t.length;
        
        // Build inverted index for source
        int[][] dict = new int[M][26];
        
        Arrays.fill(dict[M-1], -1); // -1 represents no occurrence of the character
        
        dict[M-1][s[M-1] - 'a'] = M-1;
        for(int x = M - 2; x >= 0; --x) {
            dict[x] = Arrays.copyOf(dict[x+1], 26);
            dict[x][s[x] - 'a'] = x;
        }
        
        int ans = 0;
        int idx = 0;
        // Go through target and account for each character
        for(char c: t) {
            // If there are no occurrences of c with index greater than 0
            // then it doesn't occur at all. Hence, we cannot get that letter from
            // a subsequence of source.
            if(dict[0][c - 'a'] == -1) return -1;
            
            // If there are no c's left in source that occur >= idx
            // but there are c's from earlier in the subsequence
            // add 1 to subsequence count and reset idx of source to 0.
            if(dict[idx][c - 'a'] == -1) {
                ++ans;
                idx = 0;
            }
            
            // Then continue taking letters from the subsequence
            idx = dict[idx][c-'a'] + 1;
            
            if(idx == M) {
                ++ans;
                idx = 0;
            }
        }
        
        return ans + (idx == 0? 0: 1);
    }
}