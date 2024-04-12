Minimum Number of Manipulations required to make two Strings Anagram Without Deletion of Character


Given two strings s1 and s2, we need to find the minimum number of manipulations required to make two 
strings anagram without deleting any character.

Input : 
       s1 = "aba"
       s2 = "baa"
Output : 0

Explanation: Both String contains identical characters


Input :
       s1 = "ddcf"
       s2 = "cedk"
Output : 2
Explanation : Here, we need to change two characters
in either of the strings to make them identical. We 
can change 'd' and 'f' in s1 or 'e' and 'k' in s2.

Note:- The anagram strings have same set of characters, sequence of characters can be different.

******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- count sort (variation), hashmap
	- 

stats:

	- Time Complexity : O(n), where n is the length of the string.
	- 


 static int countManipulations(String s1, String s2) 
    { 
        int count = 0; 
  
        // store the count of character 
        int char_count[] = new int[26]; 
  
        // iterate though the first String and update count 
        for (int i = 0; i < s1.length(); i++)  
            char_count[s1.charAt(i) - 'a']++;         
  
        // iterate through the second string update char_count. 
        // if character is not found in char_count then increase count 
        for (int i = 0; i < s2.length(); i++)  
            if (char_count[s2.charAt(i) - 'a']-- <= 0) 
                count++; 
          
        return count; 
    } 



