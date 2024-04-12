467. Unique Substrings in Wraparound String - Medium


Consider the string s to be the infinite wraparound string of "abcdefghijklmnopqrstuvwxyz", so s 
will look like this: "...zabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd....".

Now we have another string p. Your job is to find out how many unique non-empty substrings of p are 
present in s. In particular, your input is the string p and you need to output the number of different
non-empty substrings of p in the string s.

Note: p consists of only lowercase English letters and the size of p might be over 10000.

Example 1:
Input: "a"
Output: 1

Explanation: Only the substring "a" of string "a" is in the string s.


Example 2:
Input: "cac"
Output: 2
Explanation: There are two substrings "a", "c" of string "cac" in the string s.


Example 3:
Input: "zab"
Output: 6
Explanation: There are six substrings "z", "a", "b", "za", "ab", "zab" of string "zab" in the string s.



******************************************************
key:
	- DP, need to consider overlapping case! thus, takes the max only
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP


Stats:

	- 
	- 


Method:

	-	alphabets[i] is the maximum unique substring end with ith letter.
	-  if we know the max number of unique substrings in p ends with 'a', 'b', ..., 'z', then the 
		summary of them is the answer

	-  The max number of unique substring ends with a letter equals to the length of max contiguous 
		substring ends with that letter. 
		Example "abcd", the max number of unique substring ends with 'd' is 4, apparently they are
		"abcd", "bcd", "cd" and "d".
	
	-  If there are overlapping, we only need to consider the longest one because it covers all the 
		possible substrings. 
		Example: "abcdbcd", the max number of unique substring ends with 'd' is 4 and all substrings 
		   formed by the 2nd "bcd" part are covered in the 4 substrings already.
	
	-  No matter how long is a contiguous substring in p, it is in s since s has infinite length.



public class Solution {
    public int findSubstringInWraproundString(String p) {
        int[] alphabets = new int[26];
        
        if(p.isEmpty()) 
        	return 0;

        int len=1;
        alphabets[p.charAt(0)-'a']=1;

        for(int i=1; i<p.length(); i++){
            int current = p.charAt(i)-'a';
            int prev = p.charAt(i-1)-'a';
            if((prev+1)%26 == current)
             	len++;
            else 
            	len=1;

            alphabets[current] = Math.max(alphabets[current],len);
        }

        int sum=0;
        for(int i:alphabets) 
        	sum+=i;
        return sum;

        
    }
}

=======================================================================================================
method 2:

Stats:

	- very slow
	- 


Method:

	-	
	-	

public class Solution {
    public int findSubstringInWraproundString(String p) {
        int[] count = new int[26];
        for (int i = 0; i < p.length(); i++) {
            int cur = 1;
            int j = i;
            while (j - 1 >= 0 && (p.charAt(j) - p.charAt(j - 1) == 1 || p.charAt(j) - p.charAt(j - 1) == -25)) {
                cur++;
                j--;
            }
            count[p.charAt(i) - 'a'] = Math.max(count[p.charAt(i) - 'a'], cur);
        }
        for (int i = 1; i < count.length; i++) {
            count[0] += count[i];
        }
        return count[0];
    }
}

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



