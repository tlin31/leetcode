2516. Take K of Each Character From Left and Right - Medium

You are given a string s consisting of the characters 'a', 'b', and 'c' and a non-negative 
integer k. Each minute, you may take either the leftmost character of s, or the rightmost 
character of s.

Return the minimum number of minutes needed for you to take at least k of each character, 
or return -1 if it is not possible to take k of each character.

 

Example 1:

Input: s = "aabaaaacaabc", k = 2
Output: 8
Explanation: 
Take three characters from the left of s. You now have two 'a' characters, and one 'b' character.
Take five characters from the right of s. You now have four 'a' characters, two 'b' characters, 
and two 'c' characters.
A total of 3 + 5 = 8 minutes is needed.
It can be proven that 8 is the minimum number of minutes needed.
Example 2:

Input: s = "a", k = 1
Output: -1
Explanation: It is not possible to take one 'b' or 'c' so return -1.
 

Constraints:

1 <= s.length <= 105
s consists of only the letters 'a', 'b', and 'c'.
0 <= k <= s.length


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

	-	greedy + two pointers

Start from the left and keep adding characters until you've taken at least k of each.

Then, try to replace characters from the left with ones from the right, as long as the 
required counts are still satisfied.

The goal is to minimize the total number of characters taken (i.e., total minutes).




Stats:

	- 
	- 

class Solution {
    public int takeCharacters(String s, int k) {
        // Total counts
        int[] count = new int[3];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        
        if (Math.min(Math.min(count[0], count[1]), count[2]) < k) {
            return -1;
        }
        
        // Sliding Window
        int res = Integer.MAX_VALUE;
        int l = 0;
        for (int r = 0; r < s.length(); r++) {
            count[s.charAt(r) - 'a']--;
            
            //没满足k个，此时从左边开始拿
            while (Math.min(Math.min(count[0], count[1]), count[2]) < k) {
                count[s.charAt(l) - 'a']++;
                l++;
            }
            res = Math.min(res, s.length() - (r - l + 1));
        }
        return res;
    }
}



optimized：
class Solution {
    public int takeCharacters(String s, int k) {
        int[] charCount = new int[3];
        char[] chars = s.toCharArray();
        int length = chars.length;
        
        int left;
        for (left = 0; left < length; left++) {
            charCount[chars[left] - 'a']++;
            if (isValidCount(charCount, k)) {
                break;
            }
        }
        
        if (left == length) {
            return -1;
        }
        
        // 此时的left：前面已经有了满足k个a+k个b+k个c了，后面就是shrink window
        int currentCount = left + 1;
        int minCount = currentCount;
        int right = length - 1;
        
        //left也向左移动
        //Move left backward (start "undoing" what we took from the left).

		// If removing a character at left would break the requirement (i.e., that char's 
		// count is k), we try to compensate from the right.

		// If safe to remove, we reduce the count and try to update minCount.

        while (left >= 0) {
            int currentChar = chars[left] - 'a';
            
            if (charCount[currentChar] == k) {
            	//一直loop直到从右边找到左边的字母currentChar
                while (chars[left] != chars[right]) {

                	//右指针向左移动，拿出去的字母更多了，cur count++
                    charCount[chars[right] - 'a']++;
                    right--;
                    currentCount++;
                }
                right--;
            } 

            //有比k更多的，此时可以减去左边的字母，update mincount
            else {
                charCount[currentChar]--;
                currentCount--;
                minCount = Math.min(currentCount, minCount);
            }
            left--;
        }
        
        return minCount;
    }
    
    private boolean isValidCount(int[] count, int k) {
        return count[0] >= k && count[1] >= k && count[2] >= k;
    }
}
