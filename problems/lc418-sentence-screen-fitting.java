418. Sentence Screen Fitting - Medium


Given a rows x cols screen and a sentence represented by a list of non-empty words, find how many 
times the given sentence can be fitted on the screen.

Note:

	A word cannot be split into two lines.
	The order of words in the sentence must remain unchanged.
	Two consecutive words in a line must be separated by a single space.
	Total words in the sentence won't exceed 100.
	Length of each word is greater than 0 and won't exceed 10.
	1 ≤ rows, cols ≤ 20,000.

Example 1:

Input:
rows = 2, cols = 8, sentence = ["hello", "world"]

Output: 
1

Explanation:
hello---
world---

The character '-' signifies an empty space on the screen.
Example 2:

Input:
rows = 3, cols = 6, sentence = ["a", "bcd", "e"]

Output: 
2

Explanation:
a-bcd- 
e-a---
bcd-e-

The character '-' signifies an empty space on the screen.
Example 3:

Input:
rows = 4, cols = 5, sentence = ["I", "had", "apple", "pie"]

Output: 
1

Explanation:
I-had
apple
pie-I
had--

The character '-' signifies an empty space on the screen.


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: normal + memo


Stats:

	- 
	- 


Method:

	-	
	-	


	ex. sentence=["abc", "de", "f"], rows=4, and cols=6.

	"abc de"
	"f abc "
	"de f  "
	"abc de"

	Consider the following repeating sentence string, with positions of the start character of each 
	row on the screen.

	string s = "abc de f " --> length = 9

	"abc de f abc de f abc de f ..."
	 ^      ^     ^    ^      ^
	 0      7     13   18     25

	Our goal is to find the start position of the row next to the last row on the screen, which 
	is 25 here. Since actually it is the length of everything earlier, we can get the answer by 
	dividing this number by the length of (non-repeated) sentence string. 

	Note that the non-repeated sentence string has a space at the end; it is "abc de f " in this example.

	In each iteration, we need to adjust start based on spaces either added or removed.

	"abc de f abc de f abc de f ..." // start=0
	 012345                          // start= start + cols + adjustment
	 									     =0+6+1=7 (1 space removed in screen string)
	        012345                   // start=7+6+0=13
	              012345             // start=13+6-1=18 -->can't start on 'b', go back to the last blank
	                   012345        // start=18+6+1=25 (1 space added)
	                          012345

	- start = counter for how many valid characters from s have been put to our screen.
	- case 1:
		if (s.charAt(start % l) == ' ')
			we dont need an extra space for current row. The current row could be successfully fitted. 
	   		So we need to increase our counter by using start++.
	  case 2:
	  	if the next word cant fit to current row. 
	  	we need to keep going back in this curr word until we reach the last space


	- start / s.length() is (# of valid characters) / our formatted sentence.

	public class Solution {
	    public int wordsTyping(String[] sentence, int rows, int cols) {
	       
	        String s = String.join(" ", sentence) + " ";

	        int start = 0, 
	                l = s.length();

	        for (int i = 0; i < rows; i++) {
	            start += cols;
	            if (s.charAt(start % l) == ' ') {
	                start++;
	            } else {
	                while (start > 0 && s.charAt((start-1) % l) != ' ') {
	                    start--;
	                }
	            }
	        }
	        
	        return start / s.length();
	    }
	}




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

class_Solution(object):
    def wordsTyping(self, sentence, rows, cols):
        s = ' '.join(sentence) + ' '
        start = 0
        for i in xrange(rows):
            start += cols - 1
            if s[start % len(s)] == ' ':
                start += 1
            elif s[(start + 1) % len(s)] == ' ':
                start += 2
            else:
                while start > 0 and s[ (start - 1) % len(s) ] != ' ':
                    start -= 1
        return start/ len(s)

=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	


Given [‘AB’, ‘CDE’, ‘F’, …, ‘YZ’]
Width: w

join the words with empty space
get the index of the end of a screen line w - 1
there are 3 cases:

Case 1:
“AB-CDE-F-….-YZ” (‘-’ denotes a space)
reach to the space before F

Case 2:
“AB-CDE-F-…._YZ” (‘-’ denotes a space)
reach to exactly E

Case 3:
“AB-CDE-F-….-YZ” (‘-’ denotes a space)
reach to D

case 1, I can count one more bit and go to next line
case 2, I can count two more bits and go to next line
case 3, I have to move the cursor back until it reach to some space, and go to next line

When I go through all the rows, how many bits did I counted? Let’s say L, then the answer should be L / length of the string







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

