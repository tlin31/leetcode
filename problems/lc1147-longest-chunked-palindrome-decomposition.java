1147. Longest Chunked Palindrome Decomposition - Hard


Return the largest possible k such that there exists a_1, a_2, ..., a_k such that:

	1. Each a_i is a non-empty string;
	2. Their concatenation a_1 + a_2 + ... + a_k is equal to text;
	3. For all 1 <= i <= k,  a_i = a_{k+1 - i}.
 

Example 1:

Input: text = "ghiabcdefhelloadamhelloabcdefghi"
Output: 7
Explanation: We can split the string on "(ghi)(abcdef)(hello)(adam)(hello)(abcdef)(ghi)".


Example 2:

Input: text = "merchant"
Output: 1
Explanation: We can split the string on "(merchant)".


Example 3:

Input: text = "antaprezatepzapreanta"
Output: 11
Explanation: We can split the string on "(a)(nt)(a)(pre)(za)(tpe)(za)(pre)(a)(nt)(a)".


Example 4:

Input: text = "aaa"
Output: 3
Explanation: We can split the string on "(a)(a)(a)".
 

Constraints:

text consists only of lowercase English characters.
1 <= text.length <= 1000



******************************************************
key:
	- Greedy --> always better to break it down to smaller groups
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: Recursion


Stats:

	- 
	- 


Method:

	- Traverse from 0 to n/2 to find the smallest left side chunk and right side chunk which are equal.
	- Once you find it(🧒 👤.........👤 🧒), add 2 (🧒🧒) to solution and then recursively call 
	  the function for remaining string(👤..........👤 ).
	
	- Terminating conditions:
		a. Either string will be empty : return 0
		b. there are no more equal right and left chunks left (just a single lonely chunk 🙇‍♂️ ): return 1

 	public int longestDecomposition(String text) {
        int n = text.length();
        int result = 0;   

        // go through all possible positions in text
        for (int i = 0; i < n/2; i++) {
            if (text.substring(0, i + 1).equals(text.substring(n-1-i, n))) {
            	int innerStr = longestDecomposition(text.substring(i+1, n-1-i));

            	return 2+innerStr;
            }
         
		}

		if (n==0)
			return 0;
		else 
			// the case 'merchant' --> can't be split
			return 1;
    }








~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:

Stats:

	- Time O(N) * O(string)
	- Space O(N)



Method:

	-	If we have long prefix matched and a shorter prefix matched at the same time.
		The longer prefix can always be divided in to smaller part.
	-	




    public int longestDecomposition(String S) {
        int res = 0, n = S.length();
        String l = "", r = "";
        for (int i = 0; i < n; ++i) {
            l = l + S.charAt(i);
            r = S.charAt(n - i - 1) + r;
            if (l.equals(r)) {
                ++res;
                l = "";
                r = "";
            }
        }
        return res;
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

