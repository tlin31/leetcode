557. Reverse Words in a String III - Easy

Given a string, you need to reverse the order of characters in each word within a sentence while 
still preserving whitespace and initial word order.

Example 1:
Input: "Let's take LeetCode contest"
Output: "s'teL ekat edoCteeL tsetnoc"

Note: In the string, each word is separated by single space and there will not be any extra 
space in the string.


******************************************************
key:
	- split into string[]
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:



Stats:

	- O(n)
	- O(n)

public String reverseWords(String s) {
        StringBuilder res = new StringBuilder();
        String[] arr = s.split(" ");
        for (String str: arr) {
            StringBuilder sb = new StringBuilder(str);
            sb.reverse();
            res.append(sb.toString());
            res.append(" ");
        }
        return res.toString().trim();
    }


=======================================================================================================
method 2:

Method:

	We can create our own split and reverse function. Split function splits the string based 
	on the delimiter " "(space) and returns the array of words. Reverse function returns the 
	string after reversing the characters.



Stats:

	- O(n)
	- O(n)

public class Solution {
    public String reverseWords(String s) {
        String words[] = split(s);
        StringBuilder res=new StringBuilder();
        for (String word: words)
            res.append(reverse(word) + " ");
        return res.toString().trim();
    }

    public String[] split(String s) {
        ArrayList < String > words = new ArrayList < > ();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                words.add(word.toString());
                word = new StringBuilder();
            } else
                word.append(s.charAt(i));
        }
        words.add(word.toString());
        return words.toArray(new String[words.size()]);
    }

    public String reverse(String s) {
      StringBuilder res=new StringBuilder();
        for (int i = 0; i < s.length(); i++)
            res.insert(0,s.charAt(i));
        return res.toString();
    }
}

=======================================================================================================
method 3:

Method:



Stats:

	- 
	- 



