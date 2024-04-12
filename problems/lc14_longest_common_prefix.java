14. Longest Common prefix --- Easy

Write a function to find the longest common prefix string amongst an array of strings.

If there is no common prefix, return an empty string "".

Example 1:

Input: ["flower","flow","flight"]
Output: "fl"
Example 2:

Input: ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.
Note:

All given inputs are in lowercase letters a-z.

========================================================================================================================
method 1:
key:
	- first sort the array, then just compare the first string and the last string

Runtime: 1 ms, faster than 74.21% of Java 
Memory Usage: 37.4 MB, less than 94.74% 

public String longestCommonPrefix(String[] strs) {
        StringBuilder result = new StringBuilder();

        if (strs != null && strs.length > 0) {
            Arrays.sort(strs);
            char[] a = strs[0].toCharArray();
            char[] b = strs[strs.length - 1].toCharArray();

            // compare 2 strings, and once 1 char is diff, return the result
            for (int i = 0; i < a.length; i++) {
                if (i < b.length && b[i] == a[i]) {
                    result.append(b[i]);
                } else {
                    return result.toString();
                }
            }
            return result.toString();
        }


========================================================================================================================
method 2:
	- set pre = first string, (as base)
	- then check with next strings, if the new string does not start with pre, then reduce 
       the lenghth of pre, to see whether it will match with the current string
    - keep deleting char in base string

Runtime: 0 ms, faster than 100.00% 
Memory Usage: 37.5 MB, less than 88.30% 

public String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) 
        return "";

    String pre = strs[0];
    int i = 1;
    while (i < strs.length) {
        while (strs[i].indexOf(pre) != 0)
            pre = pre.substring(0, pre.length() - 1);

        //move to the next string
        i++;
    }
    return pre;
}