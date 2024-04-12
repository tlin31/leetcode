58. Length of Last Word - Easy

Given a string s consists of upper/lower-case alphabets and empty space characters ' ', 
return the length of last word in the string.

If the last word does not exist, return 0.

Note: A word is defined as a character sequence consists of non-space characters only.

Example:

Input: "Hello World"
Output: 5

******************************************************
key:
	- go from back, first stripe any whitespace, then loop back to the first space
	- edge case:
		1) empty string, return 0
		2) ' ', return 0

******************************************************



=======================================================================================================
method 1:

method:

	- 
	- 

stats:

	- 
	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Length of Last Word.
	- Memory Usage: 35.8 MB, less than 100.00%

	public int lengthOfLastWord(String s) {
	    int count = 0;
	    int end = s.length() - 1;
	    //过滤空格
	    while (true) {
	        if (end < 0 || s.charAt(end) != ' ')
	            break;
	        end--;
	    }

	    //计算最后一个单词的长度
	    for (int i = end; i >= 0; i--) {
	        if (s.charAt(i) == ' ') {
	            break;
	        }
	        count++;
	    }
	    return count;
	}
