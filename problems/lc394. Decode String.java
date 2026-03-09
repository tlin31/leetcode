394. Decode String - Medium

Given an encoded string, return its decoded string.

The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.

You may assume that the input string is always valid; there are no extra white spaces, square brackets are well-formed, etc. Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there will not be input like 3a or 2[4].

The test cases are generated so that the length of the output will never exceed 105.

 

Example 1:

Input: s = "3[a]2[bc]"
Output: "aaabcbc"
Example 2:

Input: s = "3[a2[c]]"
Output: "accaccacc"
Example 3:

Input: s = "2[abc]3[cd]ef"
Output: "abcabccdcdcdef"
 

Constraints:

1 <= s.length <= 30
s consists of lowercase English letters, digits, and square brackets '[]'.
s is guaranteed to be a valid input.
All the integers in s are in the range [1, 300].


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

	-	



Stats:

	- 
	- 
1. Java Solution (Double Stack)
Using two separate stacks—one for repeat counts (k) and one for the strings built so far—makes the logic very clean. 
java
public String decodeString(String s) {
    Deque<Integer> countStack = new ArrayDeque<>();
    Deque<StringBuilder> stringStack = new ArrayDeque<>();
    StringBuilder currentStr = new StringBuilder();
    int k = 0;

    for (char c : s.toCharArray()) {
        if (Character.isDigit(c)) {
            k = k * 10 + (c - '0'); // Handle multi-digit k
        } 

        else if (c == '[') {
            countStack.push(k);
            stringStack.push(currentStr);
            currentStr = new StringBuilder(); // Reset for nested content
            k = 0;
        } 

        else if (c == ']') {
            StringBuilder decoded = stringStack.pop();
            int repeatTimes = countStack.pop();
            for (int i = 0; i < repeatTimes; i++) decoded.append(currentStr);
            currentStr = decoded;
        } 

        else {
            currentStr.append(c);
        }
    }
    return currentStr.toString();
}

Time Complexity: O(L), where L is the length of the decoded string. We must touch every character in the final output.
Space Complexity: O(M+N), where M is the number of digits/brackets (stack depth) and N is the length of the string. 





2. Python Solution (Single Stack)
Python's dynamic typing allows us to mix integers and strings in a single list,
 simplifying the stack management.


def decodeString(s: str) -> str:
    stack = []
    current_num = 0
    current_str = ''
    
    for char in s:
        if char.isdigit():
            current_num = current_num * 10 + int(char)

        elif char == '[':
            stack.append((current_str, current_num))
            current_str, current_num = '', 0

        elif char == ']':
            last_str, num = stack.pop()
            current_str = last_str + (current_str * num)

        else:
            current_str += char
            
    return current_str



Time Complexity: O(L)

