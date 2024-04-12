Binary representation of a given number

https://www.geeksforgeeks.org/binary-representation-of-a-given-number/


Write a program to print Binary representation of a given number.

====================================================================================================
Method 1: Iterative
   - For any number, we can check whether its ‘i’th bit is 0(OFF) or 1(ON) by bitwise ANDing it 
     with “2^i” (2 raise to i) --> so bit must = 1 


	1) Let us take number 'NUM' and we want to check whether the 0th bit is ON or OFF    
	    bit = 2 ^ 0 (0th bit)
	    if  NUM & bit == 1 means 0th bit is ON else 0th bit is OFF

	2) Similarly if we want to check whether 5th bit is ON or OFF    
	    bit = 2 ^ 5 (5th bit)
	    if NUM & bit == 1 means its 5th bit is ON else 5th bit is OFF.

	Let us take unsigned integer (32 bit), which consist of 0-31 bits. To print binary representation 
    of unsigned integer, start from 31th bit, check whether 31th bit is ON or OFF, if it is ON print “1” 
    else print “0”. Now check whether 30th bit is ON or OFF, if it is ON print “1” else print “0”, do this for all bits from 31 to 0, finally we will get binary representation of number.

	void bin(unsigned n) 
	{ 
	    unsigned i; 
	    i = i << 31;

	    for (i; i > 0; i = i / 2) 
	    	if (n & i){
	    	   	printf("1"):
	    	} else {
	    	  	printf("0"); 
	    	}
	} 

==========================================================================================================================================
Method 2:
	- Recursive:
		step 1) if NUM > 1
		    	a) push NUM on stack
		    	b) recursively call function with 'NUM / 2'
		step 2)
		    	a) pop NUM from stack, divide it by 2 and print the remainder.


	class GFG { 
		static void bin(int n) 
		{ 
			/* step 1 */
			if (n > 1) 
				bin(n/2); 

			/* step 2 */
			System.out.print(n % 2); 
		} 

		//Driver code 
		public static void main(String[] args) 
		{ 
			bin(7); 
			System.out.println(); 
			bin(4);	 
		} 
	} 


	- Recursive using bitwise operator

		step 1: Check n > 0
		step 2: Right shift the number by 1 bit and recursive function call
		step 3: Print the bits of number



	class GFG { 

	static void bin(Integer n) { 
		if (n > 1) 
			bin(n >> 1); 
		
		System.out.printf("%d", n & 1); 
	} 

	// Driver code 
	public static void main(String[] args) { 
		bin(131); 
		System.out.printf("\n"); 
		bin(3); 
		} 
	} 









