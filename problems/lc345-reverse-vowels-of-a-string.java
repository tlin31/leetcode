345. Reverse Vowels of a String - Easy


Write a function that takes a string as input and reverse only the vowels of a string.

Example 1:

Input: "hello"
Output: "holle"
Example 2:

Input: "leetcode"
Output: "leotcede"
Note:
The vowels does not include the letter "y".


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	-	
	-	




class Solution {
    public String reverseVowels(String s) {
        if (s.length() == 0) return s;
        
        Set<Character> set = new HashSet<Character>(Arrays.asList('a', 'e', 'i', 'o', 'u', 
        														'A', 'E', 'I', 'O', 'U'));
        char[] a = s.toCharArray();
        
        int i = 0,
        	j = s.length() - 1;

        while (i < j) {

            while (i < j && !set.contains(a[i])) {
                i++;
            }
            
            while (i < j && !set.contains(a[j])) {
                j--;
            }
            
            // swap
            char temp = a[i];
            a[i] = a[j];
            a[j] = temp;

            i++;
            j--;
        }
        
        return String.valueOf(a);
    }
}





~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def reverseVowels(self, s):
        s = list(s)
        vows = set('aeiouAEIOU')
        l, r = 0, len(s) - 1
        while l <= r:
            while l <= r and s[l] not in vows: l += 1
            while l <= r and s[r] not in vows: r -= 1
            if l > r: break
            s[l], s[r] = s[r], s[l]
            l, r = l + 1, r - 1
        return ''.join(s)



def reverseVowels(self, s):
    vowels = re.findall('(?i)[aeiou]', s)
    return re.sub('(?i)[aeiou]', lambda m: vowels.pop(), s)

=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	










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

