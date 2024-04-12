471. Encode String with Shortest Length - Hard


Given a non-empty string, encode the string such that its encoded length is the shortest.

The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is 
being repeated exactly k times.

Note:

k will be a positive integer and encoded string will not be empty or have extra space.
You may assume that the input string contains only lowercase English letters. The strings length is 
at most 160.
If an encoding process does not make the string shorter, then do not encode it. If there are several 
solutions, return any of them is fine.
 

Example 1:

Input: "aaa"
Output: "aaa"
Explanation: There is no way to encode it such that it is shorter than the input string, so we do 
not encode it.
 

Example 2:

Input: "aaaaa"
Output: "5[a]"
Explanation: "5[a]" is shorter than "aaaaa" by 1 character.
 

Example 3:

Input: "aaaaaaaaaa"
Output: "10[a]"
Explanation: "a9[a]" or "9[a]a" are also valid solutions, both of them have the same length = 5, 
which is the same as "10[a]".
 

Example 4:

Input: "aabcaabcd"
Output: "2[aabc]d"
Explanation: "aabc" occurs twice, so one answer can be "2[aabc]d".
 

Example 5:

Input: "abbbabbbcabbbabbbc"
Output: "2[2[abbb]c]"
Explanation: "abbbabbbc" occurs twice, but "abbbabbbc" can also be encoded to "2[abbb]c", so one 
answer can be "2[2[abbb]c]".



******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP


Stats:

	- Time Complexity = O(n^3)
	- 


Method:

	-  dp[i][j] = string from index i to index j in encoded form.

	   dp[i][j] = min(dp[i][j], dp[i][k] + dp[k+1][j]) 
	   			  OR if there is pattern in string from i to j with smaller length.


public class Solution {

 public String encode(String s) {

    String[][] dp = new String[s.length()][s.length()];
    
    for(int l=0;l<s.length();l++) {
        for(int i=0;i<s.length()-l;i++) {
            int j = i+l;

            String substr = s.substring(i, j+1);

            // Checking if string length < 5. In that case, we know that encoding will not help.
            if(j - i < 4) {
                dp[i][j] = substr;
            } 

            else {
                dp[i][j] = substr;

                // try all splits in str[i~j] strings into 2 and combine the results of 2 substrings
                for(int k = i; k<j;k++) {
                    if(dp[i][k].length() + dp[k+1][j].length() < dp[i][j].length()){
                        dp[i][j] = dp[i][k] + dp[k+1][j];
                    }
                }
                
                // Loop for checking if string can itself found some pattern in it which could be 
                // repeated.k = length
                for(int k=0;k<substr.length();k++) {
                    String repeatStr = substr.substring(0, k+1);

                    if(repeatStr != null && substr.length()%repeatStr.length() == 0 
                                         && substr.replaceAll(repeatStr, "").length() == 0) {

                          String newStr = substr.length()/repeatStr.length() + "[" + dp[i][i+k] + "]";
                      
                          if(newStr.length() < dp[i][j].length()) {
                            dp[i][j] = newStr;
                          }
                    }
                }
            }
        }
    }
    
    return dp[0][s.length()-1];

	}
}





~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: More intuitive DFS + memo

Stats:

	- 
	- 


Method:

	-	
	-	


import java.util.*;

public class Solution {
    Map<String, String> map = new HashMap<String, String>();
    
    public String encode(String s) {
        if (s == null || s.length() == 0) return "";
        if (s.length() <= 4) return s;
        if (map.containsKey(s)) 
        	return map.get(s);

        String ret = s;
        for (int k = s.length() / 2; k < s.length(); k ++) {
            String pattern = s.substring(k);
            int times = countRepeat(s, pattern);
            if (times * pattern.length() != s.length()) 
            	continue;

            String candidate = Integer.toString(times) + "[" + encode(pattern) + "]";
            
            if (candidate.length() < ret.length()) 
            	ret = candidate;
        }


        for (int i = 1; i < s.length(); i ++) {
            String left = encode(s.substring(0, i));
            String right = encode(s.substring(i));
            String candidate = left + right;
            if (candidate.length() < ret.length()) 
            	ret = candidate;
        }

        map.put(s, ret);
        return ret;
    }

    private int countRepeat(String s, String pattern) {
        int times = 0;
        while (s.length() >= pattern.length()) {
            String sub = s.substring(s.length() - pattern.length());
            if (sub.equals(pattern)) {
                times ++;
                s = s.substring(0, s.length() - pattern.length());
            } else 
            	return times;
        }
        return times;
    }
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

