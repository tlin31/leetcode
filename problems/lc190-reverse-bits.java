190. Reverse Bits - Easy


Reverse bits of a given 32 bits unsigned integer.

 

Example 1:

Input: 00000010100101000001111010011100
Output: 00111001011110000010100101000000
Explanation: The input binary string 00000010100101000001111010011100 represents the unsigned integer 
43261596, so return 964176192 which its binary representation is 00111001011110000010100101000000.



Example 2:

Input: 11111111111111111111111111111101
Output: 10111111111111111111111111111111
Explanation: The input binary string 11111111111111111111111111111101 represents the unsigned integer 
4294967293, so return 3221225471 which its binary representation is 10111111111111111111111111111111.
 

Note:

Note that in some languages such as Java, there is no unsigned integer type. In this case, both input 
and output will be given as signed integer type and should not affect your implementation, as the 
internal binary representation of the integer is the same whether it is signed or unsigned.

In Java, the compiler represents the signed integers using 2s complement notation. Therefore, in 
Example 2 above the input represents the signed integer -3 and the output represents the signed integer
-1073741825.
 

Follow up:

If this function is called many times, how would you optimize it?



******************************************************
key:
	- bit
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	- iterate from 0 to 31 (an integer has 32 bits). 
	  In each iteration:
		1. We first shift result to the left by 1 bit.

		2. if the last digit of input n is 1, we add 1 to result. --> (n & 1)
			Example, if n=5 (101), n&1 = 101 & 001 = 001 = 1;
			however, if n = 2 (10), n&1 = 10 & 01 = 00 = 0).

		3.  update n by shifting it to the right by 1 (n >>= 1). 
		     This is because the last digit is already taken care of, so we need to drop it 

	-	

	public int reverseBits(int n) {
	    if (n == 0) return 0;
	    
	    int result = 0;
	    for (int i = 0; i < 32; i++) {
	        result <<= 1;
	        if ((n & 1) == 1) 
	        	result++;
	        n >>= 1;
	    }
	    return result;
	}

----------------
    public int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result += n & 1;
            n >>>= 1;   // CATCH: must do unsigned shift
            if (i < 31) // CATCH: for last digit, don't shift!
                result <<= 1;
        }
        return result;
    }

------------

How to optimize if this function is called multiple times? 
	- We can divide an int into 4 bytes, and reverse each byte then combine into an int. 
	  For each byte, we can use cache to improve performance.

Class Solution{
	private final Map<Byte, Integer> cache = new HashMap<Byte, Integer>();

	public int reverseBits(int n) {
	    byte[] bytes = new byte[4];
	    for (int i = 0; i < 4; i++) // convert int into 4 bytes
	        bytes[i] = (byte)((n >>> 8*i) & 0xFF);

	    int result = 0;
	    for (int i = 0; i < 4; i++) {
	        result += reverseByte(bytes[i]); // reverse per byte
	        if (i < 3)
	            result <<= 8;
	    }
	    return result;
	}

	private int reverseByte(byte b) {
	    Integer value = cache.get(b); // first look up from cache
	    if (value != null)
	        return value;

	    value = 0;

	    // reverse by bit
	    for (int i = 0; i < 8; i++) {
	        value += ((b >>> i) & 1);
	        if (i < 7)
	            value <<= 1;
	    }
	    cache.put(b, value);
	    return value;
	}

}

ex.


Example, if input n = 13 (represented in binary as
0000_0000_0000_0000_0000_0000_0000_1101, the "_" is for readability),
calling reverseBits(13) should return:
1011_0000_0000_0000_0000_0000_0000_0000

Here is how our algorithm would work for input n = 13:

Initially, result = 0 = 0000_0000_0000_0000_0000_0000_0000_0000,
               n = 13 = 0000_0000_0000_0000_0000_0000_0000_1101

Starting for loop:
i = 0:
	result = result << 1 = 0000_0000_0000_0000_0000_0000_0000_0000.
	n&1 =   0000_0000_0000_0000_0000_0000_0000_1101
	      & 0000_0000_0000_0000_0000_0000_0000_0001
	    =   0000_0000_0000_0000_0000_0000_0000_0001 = 1

	therefore result = result + 1 =
			0000_0000_0000_0000_0000_0000_0000_0000
		  + 0000_0000_0000_0000_0000_0000_0000_0001
		  = 0000_0000_0000_0000_0000_0000_0000_0001 = 1

Next, we right shift n by 1 (n >>= 1) (i.e. we drop the least significant bit) to get:
	n = 0000_0000_0000_0000_0000_0000_0000_0110.

i = 1:
	result = result << 1 = 0000_0000_0000_0000_0000_0000_0000_0010;
	n & 1 = 0000_0000_0000_0000_0000_0000_0000_0110 &
	        0000_0000_0000_0000_0000_0000_0000_0001
	      = 0000_0000_0000_0000_0000_0000_0000_0000 = 0;
	therefore we do not increment result.
	We right shift n by 1 (n >>= 1) to get:
	n = 0000_0000_0000_0000_0000_0000_0000_0011.
	We then go to the next iteration.

i = 2:
result = result << 1 = 0000_0000_0000_0000_0000_0000_0000_0100.
n&1 = 0000_0000_0000_0000_0000_0000_0000_0011 &
0000_0000_0000_0000_0000_0000_0000_0001 =
0000_0000_0000_0000_0000_0000_0000_0001 = 1
therefore result = result + 1 =
0000_0000_0000_0000_0000_0000_0000_0100 +
0000_0000_0000_0000_0000_0000_0000_0001 =
result = 0000_0000_0000_0000_0000_0000_0000_0101
We right shift n by 1 to get:
n = 0000_0000_0000_0000_0000_0000_0000_0001.
We then go to the next iteration.

i = 3:
result = result << 1 = 0000_0000_0000_0000_0000_0000_0000_1010.
n&1 = 0000_0000_0000_0000_0000_0000_0000_0001 &
0000_0000_0000_0000_0000_0000_0000_0001 =
0000_0000_0000_0000_0000_0000_0000_0001 = 1
therefore result = result + 1 =
= 0000_0000_0000_0000_0000_0000_0000_1011
We right shift n by 1 to get:
n = 0000_0000_0000_0000_0000_0000_0000_0000 = 0.

Now, from here to the end of the iteration, n is 0, so (n&1)
will always be 0 and and n >>=1 will not change n. The only change
will be for result <<=1, i.e. shifting result to the left by 1 digit.
Since there we have i=4 to i = 31 iterations left, this will result
in padding 28 0s to the right of result. i.e at the end, we get
result = 1011_0000_0000_0000_0000_0000_0000_0000

~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	










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

