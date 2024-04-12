Count set bits in an integer

Write an efficient program to count number of 1s in binary representation of an integer.

Examples :

Input : n = 6
Output : 2
Binary representation of 6 is 110 and has 2 set bits

Input : n = 13
Output : 3
Binary representation of 13 is 1101 and has 3 set bits

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

	- Brian Kernighan’s Algorithm:
		Subtracting 1 from a decimal number flips all the bits after the rightmost set bit(which is 1) 
		including the rightmost set bit.
		
		for example :
		10 in binary is 00001010
		9 in binary is 00001001
		8 in binary is 00001000
		7 in binary is 00000111
		So if we subtract a number by 1 and do bitwise & with itself (n & (n-1)), we unset the rightmost set bit. If we do n & (n-1) in a loop and count the no of times loop executes we get the set bit count.
		The beauty of this solution is the number of times it loops is equal to the number of set bits in a given integer.

 
   1  Initialize count: = 0
   2  If integer n is not zero
      (a) Do bitwise & with (n-1) and assign the value back to n
          n: = n&(n-1)
      (b) Increment count by 1
      (c) go to step 2
   3  Else return count
	- 
 n =  9 (1001)
   count = 0

   Since 9 > 0, subtract by 1 and do bitwise & with (9-1)
   n = 9&8  (1001 & 1000)
   n = 8
   count  = 1

   Since 8 > 0, subtract by 1 and do bitwise & with (8-1)
   n = 8&7  (1000 & 0111)
   n = 0
   count = 2

   Since n = 0, return count which is 2 now.
Time Complexity: O(logn)
stats:

	- 
	- 


 static int countSetBits(int n) 
    { 
        int count = 0; 
        while (n > 0) 
        { 
            n &= (n - 1) ; 
            count++; 
        } 
        return count; 
    } 

=======================================================================================================
method 2:

method:

	-  Loop through all bits in an integer, check if a bit is set and if it is then increment the set bit count. 
  

	- 

stats:

	- (-)(logn) (Theta of logn)
	- 


    /* Function to get no of set  
    bits in binary representation  
    of positive integer n */
    static int countSetBits(int n) 
    { 
        int count = 0; 
        while (n > 0) 
        { 
            count += n & 1; 
            n >>= 1; 
        } 
        return count; 
    } 
    

 Recursive:
 public static int countSetBits(int n) { 
          
        // base case 
        if (n == 0) 
            return 0; 
      
        else
      
            // if last bit set add 1 else add 0 
            return (n & 1) + countSetBits(n >> 1); 
    } 
=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



