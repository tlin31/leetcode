796. Rotate String - Easy

We are given two strings, A and B.

A shift on A consists of taking string A and moving the leftmost character to the rightmost position. 

For example, if A = 'abcde', then it will be 'bcdea' after one shift on A. Return True if and only if A 
can become B after some number of shifts on A.

Example 1:
Input: A = 'abcde', B = 'cdeab'
Output: true

Example 2:
Input: A = 'abcde', B = 'abced'
Output: false


Note:
A and B will have length at most 100.

method1:


public boolean rotateString(String A, String B) {
    return A.length() == B.length() && (A + A).contains(B);
}



method 2:
public boolean rotateString(String A, String B) {
        
        if(A.length() != B.length()) {
            return false;
        }
        
        if(A == null || B == null || A.length() == 0 || B.length() == 0) {
            return true;
        }
        
        char c = A.charAt(0);
        
        int i=0;
        while(i < B.length()) {
            
            // Find the start A char in B
            while(i < B.length() && B.charAt(i) != c) {
                i++;
            }
            
            if(i == B.length()) {
                return false;
            }
            
            // Iterate A from 0 and B from j to continue match characters
            for(int j=0; j < B.length(); j++) {
                
                if(A.charAt(j) != B.charAt((j + i) % B.length())) {
                    break;
                }
                
                // It it's the last element match for B, it's matched completely
                if(j == B.length() - 1) {
                    return true;
                }
            }
            
            i++;
        }
        
        return false;
    }