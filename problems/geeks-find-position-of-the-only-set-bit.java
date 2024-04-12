Geeks - Find position of the only set bit

https://www.geeksforgeeks.org/find-position-of-the-only-set-bit/

Given a number having only one ‘1’ and all other ’0’s in its binary representation, find position of 
the only set bit.

******************************************************
key:
	- use of shift & and 
	- edge case:
		1) number = 0, return false
		2) generalize 1), if number != power of 2, return false

******************************************************



=======================================================================================================
method 1:

method:

	- start from the rightmost bit and one by one check value of every bit. 
		1) If number is power of two then and then only its binary representation contains only one ‘1’. 
		   If given number is not a power of 2, then print error message and exit.

		2) Initialize i = 1 (for looping) and pos = 1 (to find position of set bit)

		3) Inside loop, do bitwise AND of i and number ‘N’. If value of this operation is true, then 
		   “pos” bit is set, so break the loop and return position. 

		   Otherwise, increment “pos” by 1 and left shift i by 1 and repeat the procedure.
	-   Suppose N = 8 = (1000)2 
	    Then N-1 = 7 = (0111)2
	    N & (N-1)= (1000)2 & (0111)2 = (0000)2

	    N = 5 = (0101)2
	    N-1 = 4 = (0100)2
	    N & (N-1) = (0101)2 & (0100)2 = (0001)2

stats:

	- 
	- 


	class GFG { 

		// See http:// goo.gl/17Arj 
		static boolean isPowerOfTwo(int n) 
		{ 
			return (n > 0 && ((n & (n - 1)) == 0)) ? true : false; 
		} 

		// Returns position of the only set bit in 'n' 
		static int findPosition(int n) 
		{ 
			if (!isPowerOfTwo(n)) 
				return -1; 

			int i = 1, pos = 1; 

			// Iterate through bits of n till we find a set bit 
			// i&n will be non-zero only when 'i' and 'n' have a set bit 
			// at same position 
			while ((i & n) == 0) { 
				// Unset current bit and set the next bit in 'i' 
				i = i << 1; 

				// increment position 
				++pos; 
			} 

			return pos; 
		} 

		// Driver code 
		public static void main(String[] args) 
		{ 
			int n = 16; 
			int pos = findPosition(n); 
			if (pos == -1) 
				System.out.println("n = " + n + ", Invalid number"); 
			else
				System.out.println("n = " + n + ", Position " + pos); 

			n = 12; 
			pos = findPosition(n); 
			if (pos == -1) 
				System.out.println("n = " + n + ", Invalid number"); 
			else
				System.out.println("n = " + n + ", Position " + pos); 

			n = 128; 
			pos = findPosition(n); 
			if (pos == -1) 
				System.out.println("n = " + n + ", Invalid number"); 
			else
				System.out.println("n = " + n + ", Position " + pos); 
		} 
	} 

	// This code is contributed by mits 


=======================================================================================================
method 2:

method:

	- one by one right shift the set bit of given number ‘n’ until ‘n’ becomes 0. Count how many times 
	  we shifted to make ‘n’ zero. The final count is the position of the set bit.
	- 

stats:

	- 
	- 



class GFG { 

	// A utility function to check whether 
	// n is power of 2 or not 
	static boolean isPowerOfTwo(int n) 
	{ 
		return n > 0 && ((n & (n - 1)) == 0); 
	} 

	// Returns position of the only set bit in 'n' 
	static int findPosition(int n) 
	{ 
		if (!isPowerOfTwo(n)) 
			return -1; 

		int count = 0; 

		// One by one move the only set bit 
		// to right till it reaches end 
		while (n > 0) { 
			n = n >> 1; 

			// increment count of shifts 
			++count; 
		} 

		return count; 
	} 

	// Driver code 
	public static void main(String[] args) 
	{ 
		int n = 0; 
		int pos = findPosition(n); 
		if (pos == -1) 
			System.out.println("n = " + n + ", Invalid number"); 
		else
			System.out.println("n = " + n + ", Position " + pos); 

		n = 12; 
		pos = findPosition(n); 
		if (pos == -1) 
			System.out.println("n = " + n + ", Invalid number"); 
		else
			System.out.println("n = " + n + ", Position " + pos); 

		n = 128; 
		pos = findPosition(n); 
		if (pos == -1) 
			System.out.println("n = " + n + ", Invalid number"); 
		else
			System.out.println("n = " + n + ", Position " + pos); 
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



