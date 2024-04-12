243. Shortest Word Distance - Easy

Given a list of words and two words word1 and word2, return the shortest distance between these 
two words in the list.

Example:
Assume that words = ["practice", "makes", "perfect", "coding", "makes"].

Input: word1 = “coding”, word2 = “practice”
Output: 3
Input: word1 = "makes", word2 = "coding"
Output: 1
Note:
You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:



Stats:

	- 
	- 
public int shortestDistance(String[] words, String word1, String word2) {
    int p1 = -1, p2 = -1, min = Integer.MAX_VALUE;
    
    for (int i = 0; i < words.length; i++) {
        if (words[i].equals(word1)) 
            p1 = i;

        if (words[i].equals(word2)) 
            p2 = i;
            
        // both are already located
        if (p1 != -1 && p2 != -1)
            min = Math.min(min, Math.abs(p1 - p2));
    }
    
    return min;
}



=======================================================================================================
method 2:

Method:
	keeps only 1 index
	Code with single index surely looks neat but may not be great for runtime. String comparison 
	is expensive task. Consider an array of strings that is filled with large size words and 
	filled with a lot of word2.
	
	With two index, at max we will compare each word2 twice where as in this we will compare it 
	three times. Better to pay price of arithmetic operation compared to string equals.

	Also we can break out of the loop if you ever see distance 1, we are never going to find 
	better distance than that.

Stats:

	- 
	- 

public int shortestDistance(String[] words, String word1, String word2) {
   int index = -1, minDistance = Integer.MAX_VALUE;
   for (int i = 0; i < words.length; i++) {
      if (words[i].equals(word1) || words[i].equals(word2)) {
         if (index != -1 && !words[index].equals(words[i])) {
            minDistance = Math.min(minDistance, i - index);
          }
          index = i;
      }
   }
   return minDistance;
}

=======================================================================================================
method 3:

Method:



Stats:

	- 
	- 



