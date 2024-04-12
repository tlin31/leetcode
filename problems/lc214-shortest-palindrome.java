214. Shortest Palindrome - Hard


Given a string s, you are allowed to convert it to a palindrome by adding characters in front of it. 
Find and return the shortest palindrome you can find by performing this transformation.

Example 1:

Input: "aacecaaa"
Output: "aaacecaaa"

Example 2:

Input: "abcd"
Output: "dcbabcd"


******************************************************
key:
	- KMP, REDO
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

	=== KMP algorithm ===
	1. 	build a look up table that records the match result of prefix and postfix.
	   	Value in the table is the max len of matching substring that exists in both prefix and postfix.
		In the prefix this substring should starts from 0, while in the postfix this substring should ends at current index.

		ex. string "ababc"
			The KMP table will look like this:

			a b a b c

			0 0 1 2 0

			(Note: we will not match substring with itself, so we will skip index 0)

	2. if we are trying to match a char after postfix with target string and failed, then we can 
	   smartly shift the string, so that the matching string in prefix will replace postfix and 
	   now we can try to match the char after prefix with this char in target.

		ex. match string "ababc" with "abababc".

		We will initially have match as below

		a b a b a b c (string x)

		a b a b c (string y)

		0 1 2 3 4 5 6

	  We found char at index 4 does not match-->use lookup table and shift the string y wisely.
	  We found table[3] = 2, which means we can shift the string y rightward by 2, and still have 
	  same but shorter prefix before index 4, like this:

	a b a b a b c (string x)

	___a b a b c (string y)

	0 1 2 3 4 5 6

	If there is a long gap between prefix and postfix, this shift can help us save a lot of time.
	In the brute force way, we cannot do that because we have no information of the string. We have to compare each possible pair of chars. While in kmp, we know the information of string y so we can move smartly. We can directly jump to the next possible matching pair while discard useless pair of chars.

	We are almost done with KMP, but we still have one special case that needs to be taken care of.

	Say now we have a input like this:

	a a b a a a (input String)

	0 1 2 3 4 5 (index)

	0 1 0 1 2 ? (KMP table)

	How should we build the KMP table for this string?

	Say the pointer in prefix is "x", which is at index 2 now and the pointer in postfix is "y" which is at index 5 now. we need to match "b" pointed by x with "a" pointed by y. It is an unmatched pair, how should we update the cell?

	Well, we really don't need to reset it to 0, that will make us skip a valid shorter matching substring "aa".
	What we do now is just to shorten the length of substring by 1 unit and try to match a shorter substring "aa". This can be done by moving pointer x to the index recorded in [indexOf(x)-1] while keep pointer y stay still. This is because by following the value in KMP table we can always make sure previous part of prefix and postfix is matched even we have shorten their length, so we only need to care about the char after matched part in prefix and postfix.

	Use above example:

	Firstly we try to compare prefix "aab" with postfix "aaa", pointer in prefix now points to "b" while pointer in postfix now points to "a". So this means current len of postfix/prefix will not give a match, we need to shorten it.

	So in the second step, we will fix pointer in postfix, and move pointer in prefix so that we can compare shorter prefix and postfix. The movement of pointer in prefix (say at index x) is done by using KMP table. We will set pointer in prefix to be table [indexOf(x)-1]. In this case, we will move prefix pointer to index 1. So now we try to compare prefix "aa" with postfix "aa".

	Finally, we found the matching prefix and postfix, we just update the cell accordingly.

	Above is my understanding of KMP algorithm, so how could we apply KMP to this problem

	========================== I am just a splitter =================================

	This problem asks us to add string before the input so the result string will be a palindrome.
	We can convert it to an alternative problem"find the longest palindrome substring starts from index 0".
	If we can get the length of such substring, then we can easily build a palindrome string by inserting the reverse part of substring after such substring before the original string.

	Example:

	input string:

	abacd

	longest palindrome substring starts from 0:

	aba

	Insert the reverse part of substring after palindrome substring before the head:

	dcabacd

	Now the problem becomes how to find the longest palindrome substring starts from 0.
	We can solve it by using a trick + KMP.

	The trick is to build a temp string like this:

	s + "#" + reverse(s)

	Then we run KMP on it, the value in last cell will be our solution. In this problem, we don't need to use KMP
	to match strings but instead we use the lookup table in KMP to find the palindrome.

	We add "#" here to force the match in reverse(s) starts from its first index
	What we do in KMP here is trying to find a match between prefix in s and a postfix in reverse(s). The match part will be palindrome substring.

	Example:
	input:

	catacb

	Temp String:

	catacb # bcatac

	KMP table:

	c a t a c b # b c a t a c

	0 0 0 0 1 0 0 0 1 2 3 4 5

	In the last cell, we got a value 5. It means in s we have a substring of length 5 that is palindrome. 
public class Solution {
	public String shortestPalindrome(String s) {
	    String temp = s + "#" + new StringBuilder(s).reverse().toString();
	    int[] table = getTable(temp);

	    //get the maximum palin part in s starts from 0
	    return new StringBuilder(s.substring(table[table.length - 1])).reverse().toString() + s;
	}

	public int[] getTable(String s){
	    int[] table = new int[s.length()];

	    int index = 0;
	    for(int i = 1; i < s.length(); ){
	        if(s.charAt(index) == s.charAt(i)){
	            table[i] = ++index;
	            i++;
	        } else {
	            if(index > 0){
	                index = table[index-1];
	            } else {
	                index = 0;
	                i ++;
	            }
	        }
	    }
	    return table;
	}
}




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: brute force

Stats:

	- 
	- 


Method:

	-	
	-  find the longest palindrome starting from the first character, and then reverse the remaining 
	   part as the prefix to s. 


public String shortestPalindrome(String s) {
    int i = 0, 
    	j = end,
    	end = s.length() - 1; 
    char chs[] = s.toCharArray();

    while(i < j) {
         if (chs[i] == chs[j]) {
             i++; 
             j--;
         } else { 
             i = 0; 
             end--; 
             j = end;
         }
    }
    return new StringBuilder(s.substring(end+1)).reverse().toString() + s;
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

