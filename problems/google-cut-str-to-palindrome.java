Google - 2 string, cut to make palindrome

Given 2 strings a and b with the same length. Strings are alligned one under the other. We can choose 
an index and split both strings into 4 subtrings: a1 + a2 and b1 + b2. 

Find out if it is possible to split a and b such that a1 + b2 or a2 + b1 forms a palindrome.

Example 1:

Input: a = "abcbbbb", b = "xxxbcba"
Output: true
Explanation: 

abc|bbbb
xxx|bcba

We can split the strings at index 3. We will get a1 = "abc", a2 = "bbbb" and b1 = "xxx", b2 = "bcba"
a1 + b2 forms a palidnrome "abcbcba" so return true.



-----------------------
Follow-up:
-----------------------

Now it is allowed to split the strings independently:

a|bcbbbb
xxxbcb|a

So in the exampe above a can be splitted into a1 = "a" a2 = "bcbbbb" and b can be splitted 
 b1 = "xxxbcb" b2 = "a". As a result a1+ b2 forms a palindrome "aa". 

Find the longest palindrome.

=======================================================================================================
method 1:

method:

	- check both combination of a1+b2 and a2+b1
	- 


private  boolean isValidPalindromeSplitPossible(String s1, String s2) {
	if (s1 == null || s2 == null)
		return false;
	if (s1.length() <= 1 && s2.length() <= 1)
		return true;
	s1 = s1.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
	s2 = s2.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

	int i = 0;
	int j = s2.length() - 1;
	int c = 0;
	while (i < j) {
		char a1 = s1.charAt(i);
		char b2 = s2.charAt(j - i);
		if (a1 != b2) {
			break;
		} else {
			c = j - i;
			i++;
		}
	}
	c = c + 1;
	int k = s1.length() - 1;;
	int l = 0;
	int d =0;
	while (l<k) {
		char a2 = s1.charAt(k-l);
		char b1 = s2.charAt(l);
		if (a2 != b1) {
			break;
		} else {
			d=k-l;
			l++;

		}
	}
    d=d+1;
	if ((i > 0 && i == c) || (k > 0 && l == d)) {
		return true;
	} else {
		return false;
	}
}


=======================================================================================================
method 1:

method:

	- 
	- 

stats:

	- 
	- 
Follow up:

The 2nd one: let's say we want to look for the longest palindrome formed by a1 + b2. First we find the longest prefix of a and suffix of b such that they are symmetrical. Then we find the longest palindrome in a, starting from the next char after the prefix of a we found just now, and the longest palindrome in b at symmetrical position. Longest palindrome starting at each index of a string can be found in linear time. So overall it is still linear.

Take the sample data for the followup as an example:
abcbbbb
xxxbcba

try a1 + b2: longest prefix of a / suffix of b which are symmetrical is "abcb" and "bcba". Longest palindrome in a starting from a[4] is "bbb"; longest palindrome in b ending at b[2] is "xxx". "bbb" and "xxx" are of the same length so either is ok - we get longest palindrome formed by a1 + b2: abcbbbbbcba or abcbxxxbcba

try a2 + b1: longest prefix of b / suffix of a which are symmetrical is "". Longest palindrome formed by a2 + b1 is bbbb.

So we get the longest one: abcbbbbbcba or abcbxxxbcba.