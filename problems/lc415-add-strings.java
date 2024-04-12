415. Add Strings - Easy

Given two non-negative integers num1 and num2 represented as string, return the sum of num1 and num2.

Note:

The length of both num1 and num2 is < 5100.
Both num1 and num2 contains only digits 0-9.
Both num1 and num2 does not contain any leading zero.
You must not use any built-in BigInteger library or convert the inputs to integer directly.


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

	- Time Complexity: `O(m + n)` (Average Case) and `O(m + n)` (Worst Case) where `m` and `n` are 
	                   the total number of characters in the first and second input respectively. 
	                   The algorithm evaluate each character for potential carry.

	- Auxiliary Space: `O(m + n)` space is used where `m` and `n` are the total number of characters 
	                   in the first and second input respectively. Converting both input to character
	                   array required extra space.



Method:

	-	The while loop will run as long as there are characters left in one of the strings or when
	    there is a carry in remaining. 

	    Starting from right to left, each character is converted to integer by the mean of offsetting 
	    its ASCII value. If the shorter string is exhausted first, the value will be forced to `0` 
	    as default from there onwards. Sum for that particular position is conveniently calculated 
	    and a modulus of `10` will extract the digit portion in case the sum is bigger than 10. 

	    Carry in is extracted by flooring the number after division by `10`. StringBuilder is used 
	    due to its efficiently in inserting character to existing StringBuilder object. If normal 
	    String is used then each insertion by + operation will have to copy over the immutable 
	    String object which is highly inefficient.


	-	



public class Solution {
    public String addStrings(String num1, String num2) {
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        
        int carry = 0;
        char[] num1Array = num1.toCharArray();
        char[] num2Array = num2.toCharArray();
        StringBuilder sb = new StringBuilder();

        // go back from the back
        while (i >= 0 || j >= 0 || carry == 1) {
            int a = i >= 0 ? (num1Array[i--] - '0') : 0;
            int b = j >= 0 ? (num2Array[j--] - '0') : 0;
            int sum = a + b + carry;
            sb.insert(0, sum % 10);
            carry = sum / 10;
        }
        return sb.toString();
    }
}



=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



