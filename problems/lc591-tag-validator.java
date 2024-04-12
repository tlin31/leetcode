591. Tag Validator - Hard

Given a string representing a code snippet, you need to implement a tag validator to parse the code and return whether it is valid. A code snippet is valid if all the following rules hold:

The code must be wrapped in a valid closed tag. Otherwise, the code is invalid.
A closed tag (not necessarily valid) has exactly the following format : <TAG_NAME>TAG_CONTENT</TAG_NAME>. Among them, <TAG_NAME> is the start tag, and </TAG_NAME> is the end tag. The TAG_NAME in start and end tags should be the same. A closed tag is valid if and only if the TAG_NAME and TAG_CONTENT are valid.
A valid TAG_NAME only contain upper-case letters, and has length in range [1,9]. Otherwise, the TAG_NAME is invalid.
A valid TAG_CONTENT may contain other valid closed tags, cdata and any characters (see note1) EXCEPT unmatched <, unmatched start and end tag, and unmatched or closed tags with invalid TAG_NAME. Otherwise, the TAG_CONTENT is invalid.
A start tag is unmatched if no end tag exists with the same TAG_NAME, and vice versa. However, you also need to consider the issue of unbalanced when tags are nested.
A < is unmatched if you cannot find a subsequent >. And when you find a < or </, all the subsequent characters until the next > should be parsed as TAG_NAME (not necessarily valid).
The cdata has the following format : <![CDATA[CDATA_CONTENT]]>. The range of CDATA_CONTENT is defined as the characters between <![CDATA[ and the first subsequent ]]>.
CDATA_CONTENT may contain any characters. The function of cdata is to forbid the validator to parse CDATA_CONTENT, so even it has some characters that can be parsed as tag (no matter valid or invalid), you should treat it as regular characters.
Valid Code Examples:
Input: "<DIV>This is the first line <![CDATA[<div>]]></DIV>"

Output: True

Explanation: 

The code is wrapped in a closed tag : <DIV> and </DIV>. 

The TAG_NAME is valid, the TAG_CONTENT consists of some characters and cdata. 

Although CDATA_CONTENT has unmatched start tag with invalid TAG_NAME, it should be considered as plain text, not parsed as tag.

So TAG_CONTENT is valid, and then the code is valid. Thus return true.


Input: "<DIV>>>  ![cdata[]] <![CDATA[<div>]>]]>]]>>]</DIV>"

Output: True

Explanation:

We first separate the code into : start_tag|tag_content|end_tag.

start_tag -> "<DIV>"

end_tag -> "</DIV>"

tag_content could also be separated into : text1|cdata|text2.

text1 -> ">>  ![cdata[]] "

cdata -> "<![CDATA[<div>]>]]>", where the CDATA_CONTENT is "<div>]>"

text2 -> "]]>>]"


The reason why start_tag is NOT "<DIV>>>" is because of the rule 6.
The reason why cdata is NOT "<![CDATA[<div>]>]]>]]>" is because of the rule 7.
Invalid Code Examples:
Input: "<A>  <B> </A>   </B>"
Output: False
Explanation: Unbalanced. If "<A>" is closed, then "<B>" must be unmatched, and vice versa.

Input: "<DIV>  div tag is not closed  <DIV>"
Output: False

Input: "<DIV>  unmatched <  </DIV>"
Output: False

Input: "<DIV> closed tags with invalid tag name  <b>123</b> </DIV>"
Output: False

Input: "<DIV> unmatched tags with invalid tag name  </1234567890> and <CDATA[[]]>  </DIV>"
Output: False

Input: "<DIV>  unmatched start tag <B>  and unmatched end tag </C>  </DIV>"
Output: False
Note:
For simplicity, you could assume the input code (including the any characters mentioned above) only contain letters, digits, '<','>','/','!','[',']' and ' '.





******************************************************
key:
	- stack or regex
	- edge case:
		1) empty string, return empty
		2) many edge cases

******************************************************



=======================================================================================================
method 1:

method:

	- stack
	- Whenever a < is encountered(unless we are currently inside <![CDATA[...]]>), it indicates the 
	  beginning of either a TAG_NAME(start tag or end tag) or the beginning of cdata as per the 
	  conditions given in the problem statement.
	- If the character immediately following this < is an !, the characters following this < can 
	  not be a part of a valid TAG_NAME, since only upper-case letters(in case of a start tag) or 
	  / followed by upper-case letters(in the case of an end tag). 

	  Thus, the choice now narrows down to only cdata. Thus, we need to check if the current bunch 
	  of characters following <!(including it) constitute a valid cdata. For doing this, firstly 
	  we find out the first matching ]]> following the current <! to mark the ending of cdata. 
	  If no such matching ]]> exists, the codecode string is considered as invalid. Apart from 
	  this, the <! should also be immediately followed by CDATA[ for the cdata to be valid. 
	  The characters lying inside the <![CDATA[ and ]]> do not have any constraints on them.

	- If the character immediately following the < encountered is not an !, this < can only mark 
	  the beginnning of TAG_NAME. Now, since a valid start tag can not contain anything except 
	  upper-case letters, if a / is found after <, the </ pair indicates the beginning of an end 
	  tag. Now, when a < refers to the beginning of a TAG_NAME(either start-tag or end-tag), 
	  we find out the first closing > following the < to find out the substring(say ss), that 
	  constitutes the TAG_NAME. This ss should satisfy all the criterion to constitute a valid 
	  TAG_NAME. Thus, for every such ss, we check if it contains all upper-case letters and also 
	  check its length(It should be between 1 to 9). If any of the criteria is not fulfilled, s 
	  does not constitue a valid TAG_NAME. Hence, the codecode string turns out to be invalid as 
	  well.

	- Apart from checking the validity of the TAG_NAME, we also need to ensure that the tags 
	  always exist in pairs. i.e. for every start-tag, a corresponding end-tag should always exist. 
	  Further, we can note that in case of multiple TAG_NAME's, the TAG_NAME whose start-tag comes 
	  later than the other ones, should have its end-tag appearing before the end-tags of those 
	  other TAG_NAME's. i.e. the tag which starts later should end first.

	- From this, we get the intuition that we can make use of a stack to check the existence of 
	  matching start and end-tags. Thus, whenever we find out a valid start-tag, as mentioned 
	  above, we push its TAG_NAME string onto a stackstack. Now, whenever an end-tag is found, 
	  we compare its TAG_NAME with the TAG_NAME at the top the stackstack and remove this element 
	  from the stackstack. If the two do not match, this implies that either the current end-tag 
	  has no corresponding start-tag or there is a problem with the ordering of the tags. 
	  The two need to match for the tag-pair to be valid, since there can't exist an end-tag 
	  without a corresponding start-tag and vice-versa. Thus, if a match isn't found, we can 
	  conclude that the given codecode string is invalid.

	- Now, after the complete codecode string has been traversed, the stackstack should be empty 
	  if all the start-tags have their corresponding end-tags as well. If the stackstack is not 
	  empty, this implies that some start-tag doesn't have the corresponding end-tag, violating 
	  the closed-tag's validity condition.

	- Further, we also need to ensure that the given code is completely enclosed within 
	  closed tags. For this, we need to ensure that the first cdata found is also inside the 
	  closed tags. Thus, when we find a possibility of the presence of cdata, we proceed further 
	  only if we have already found a start tag, indicated by a non-empty stack. Further, to 
	  ensure that no data lies after the last end-tag, we need to ensure that the stack 
	  does not become empty before we reach the end of the given codecode string, since an 
	  empty stackstack indicates that the last end-tag has been encountered.


stats:

	- Time complexity : O(n). We traverse over the given codecode string of length n.

	- Space:O(n). The stack can grow upto a size of n/3 in the worst case. e.g. In case of 
		<A><B><C><D>, n=12 and number of tags = 12/3 = 4.



public class Solution {

    Stack < String > stack = new Stack < > ();

    boolean contains_tag = false;

    public boolean isValidTagName(String s, boolean ending) {
        // check length
        if (s.length() < 1 || s.length() > 9)
            return false;

        // check uppercase
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isUpperCase(s.charAt(i)))
                return false;
        }

        // check whether the tag name contains \

        // end tag, then peek & pop only if it equals to the most recent start tag
        if (ending) {
            if (!stack.isEmpty() && stack.peek().equals(s))
                stack.pop();
            else
                // return unmatched closing tag
                return false;
        } 

        // if opening tag
        else {
            contains_tag = true;
            stack.push(s);
        }
        return true;
    }

    //passed in the cdata sub string
    public boolean isValidCdata(String s) {
        return s.indexOf("[CDATA[") == 0;
    }

    public boolean isValid(String code) {
        if (code.charAt(0) != '<' || code.charAt(code.length() - 1) != '>')
            return false;

        for (int i = 0; i < code.length(); i++) {
            boolean ending = false;
            int closeindex;

            // when stack is empty, and there isn't any tag
            if(stack.isEmpty() && contains_tag)
                return false;

            if (code.charAt(i) == '<') {

            	// the case of <![CDATA[<div>]]>
                if (!stack.isEmpty() && code.charAt(i + 1) == '!') {
                    closeindex = code.indexOf("]]>", i + 1);
                    if (closeindex < 0 || !isValidCdata(code.substring(i + 2, closeindex)))
                        return false;
                } 


                else {

                	//case of end tag
                    if (code.charAt(i + 1) == '/') {
                        i++;
                        ending = true;
                    }

                    //get the tag's start & end index
                    closeindex = code.indexOf('>', i + 1);

                    //check for valid tag name
                    if (closeindex < 0 || 
                    	!isValidTagName(code.substring(i + 1, closeindex), ending))
                        return false;
                }
                i = closeindex;
            }
        }
        return stack.isEmpty() && contains_tag;
    }
}

=======================================================================================================
method 2: https://leetcode.com/problems/tag-validator/Figures/591/591_Tag_Validator.PNG

method:

	- regex
	- please refer to the regex notes for specific tags
	- <([A-Z]{1,9})>([^<]*((<\/?[A-Z]{1,9}>)|(<!\[CDATA\[(.*?)]]>))?[^<]*)*<\/>
	- But, if we make use of back-referencing as mentioned above, the matching process takes a very large amount of CPU time. Thus, we use the regex only to check the validity of the TAG_CONTENT, TAG_NAME and the cdata. We check the presence of the outermost closed tags by making use of a stackstack as done in the last approach.

The rest of the process remains the same as in the last approach, except that we need not manually check the validity of TAG_CONTENT, TAG_NAME and the cdata, since it is already done by the regex expression. We only need to check the presence of inner closed tags.

stats:

	- 
	- 

import java.util.regex.*;
public class Solution {
    Stack < String > stack = new Stack < > ();
    boolean contains_tag = false;
    public boolean isValidTagName(String s, boolean ending) {
        if (ending) {
            if (!stack.isEmpty() && stack.peek().equals(s))
                stack.pop();
            else
                return false;
        } else {
            contains_tag = true;
            stack.push(s);
        }
        return true;
    }
    public boolean isValid(String code) {
        String regex = "<[A-Z]{0,9}>([^<]*(<((\\/?[A-Z]{1,9}>)|(!\\[CDATA\\[(.*?)]]>)))?)*";
        if (!Pattern.matches(regex, code))
            return false;
        for (int i = 0; i < code.length(); i++) {
            boolean ending = false;
            if (stack.isEmpty() && contains_tag)
                return false;
            if (code.charAt(i) == '<') {
                if (code.charAt(i + 1) == '!') {
                    i = code.indexOf("]]>", i + 1);
                    continue;
                }
                if (code.charAt(i + 1) == '/') {
                    i++;
                    ending = true;
                }
                int closeindex = code.indexOf('>', i + 1);
                if (closeindex < 0 || !isValidTagName(code.substring(i + 1, closeindex), ending))
                    return false;
                i = closeindex;
            }
        }
        return stack.isEmpty();
    }
}

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



