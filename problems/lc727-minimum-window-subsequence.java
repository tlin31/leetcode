727. Minimum Window Subsequence - Hard

Given strings S and T, find the minimum (contiguous) substring W of S, so that T is a subsequence of W.

If there is no such window in S that covers all characters in T, return the empty string "". 
If there are multiple such minimum -length windows, return the one with the left - most starting index.

Example 1:

Input: 
S = "abcdebdde", T = "bde"
Output: "bcde"

Explanation: 
"bcde" is the answer because it occurs before "bdde" which has the same length.
"deb" is not a smaller window because the elements of T in the window must occur in order.
 

Note:

All the strings in the input will only contain lowercase letters.
The length of S will be in the range [1, 20000].
The length of T will be in the range [1, 100].


******************************************************
key:
	- sliding window /  two pointers
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1 - brute force:

Stats:

	- 
	- 

Method:

    -   
    -   

class Solution {
    public String minWindow(String S, String T) {        
        int min = 200001;
        String res = "";
        for(int i = 0; i <= S.length() - T.length(); i++) {

        	// find the same equal char
            while(i <= S.length() - T.length() && S.charAt(i) != T.charAt(0)) 
            	i++;

            String win = S.substring(i, Math.min(S.length(), i + min));

            // len stores the smallest index that contains all chars of T in string S
            int len = containSub(win, T);

            // update min length
            if(len != -1 && len < min) {                    
                min = len;
                res = S.substring(i, i + min);
            }
        }        
        return res;
    }
    
    public int containSub(String S, String T) {
        int p1 = 0, p2 = 0;
        while(p1 < S.length()) {

        	// if there is a match
            if(S.charAt(p1) == T.charAt(p2)) {
                p1++;
                p2++;

                if(p2 == T.length()) 
                	return p1;
            }            
            else p1++;
        }
        return -1;
    }
}



=======================================================================================================
method 2:

Stats:
	- Time complexity: worst case O(mn). 
			Every char in S is a match, so we need to move back n step every time, equals to DP case.

Method:

    -   Two pointers
    -   Scan from left to right in S to find a qualified tail of matching T.
        If find a tail: Scan from right to left, starting from tail to find a closest head, 
                        S[head..tail] is a solution candidate;
        Next round scan starts from head+1 to include all possibilities.
	



class Solution {
public String minWindow(String S, String T) {
        if (S == null || S.length() == 0)
            return "";

        int i = 0, j = 0;
        int start_idx = -1;
        int min_len = S.length();

        while (i < S.length()) {
            if (S.charAt(i) == T.charAt(j)) {
                i++;
                j++;

                // find a qualified tail 
                if (j == T.length()) {
                    int tail = i-1;

                    // try to find the closest head reversely
                    // -1 because start from the second last and go to the begining of str
                    int head = tail;
                    int reversePtrInT = T.length()-1;;
                    while (reversePtrInT>=0) {
                        if (S.charAt(head) == T.charAt(reversePtrInT)) {
                            reversePtrInT--;
                            head--;
                        } else {
                            head--;
                        }
                    }

                    // find the closest head coreespond to tail in S
                    head = head +1;
                    int cur_len = tail - head + 1;
                    if (cur_len < min_len) {
                        min_len = cur_len;
                        start_idx = head;
                    }

                    // reset j to find next qualified tail
                    j = 0;  

                    // next round should start with next idx after head
                    i = head + 1; 
                }
            } 

            else {
                i++;
            }
        }

        if (start_idx == -1) {
            return "";
        } else {
            return S.substring(start_idx, start_idx + min_len);
        }
    }
}


=======================================================================================================
method 3: brute force

Method:

	-	
	-	


Stats:

	- 
	- 

class Solution {
    public String minWindow(String S, String T) {
        int i=-1;
        String res="";
        while (true){
            for (char c: T.toCharArray()){
                i = S.indexOf(c, i+1);
                if (i==-1) 
                    return res;
            }
            int I = ++i;
            for (int j = T.length()-1; j>-1; j--){
                i = S.lastIndexOf(T.charAt(j), i-1);
            }
            if (res=="" || res.length()>I-i) 
                res = S.substring(i++, I);
        }
    }
}
