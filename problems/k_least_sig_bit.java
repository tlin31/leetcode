/**
Print ‘K’th least significant bit of a number

A number N is given. We need to print its ‘K’th Least Significant Bit.

Examples :

Input : num = 10, k = 4 
Output : 1
Explanation : Binary Representation of 10 is 1010. 4th LSB is 1.

Input : num = 16, k = 3
Output : 0
Explanation : Binary Representation of 16 is 10000. 3rd LSB is 0.


Method 1:
1. Shift the number ‘1’ (K-1) times left.
2. This will yield a number with all unset bits but the ‘K’th bit. Now, we’ll 
   perform logical AND of the shifted number with given number.
3. All bits except the ‘K’th bit will yield 0, and ‘K’th bit will depend on the number. 
   This is because, 1 AND 1 is 1. 0 AND 1 is 0.
**/

import java .io.*; 
  
class GFG { 
      
    // Function returns 1 if set, 0 if not 
    static boolean LSB(int num, int K) 
    { 
        boolean x = (num & (1 << (K-1))) != 0; 
        return (x); 
    } 
      
    // Driver code 
     public static void main(String[] args)  
    { 
        int num = 10, K = 4; 
          
        //Function call 
        if(LSB(num, K))  
            System.out.println("1") ; 
          
        else
            System.out.println("0"); 
    }  
}   


// Note: Set the K-th bit of a given number --> ((1 << k) | n); 
