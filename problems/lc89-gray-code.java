89. Gray Code - Medium

The gray code is a binary numeral system where two successive values differ in only one bit.

Given a non-negative integer n representing the total number of bits in the code, print the 
sequence of gray code. A gray code sequence must begin with 0.

Example 1:

Input: 2
Output: [0,1,3,2]

Explanation:
00 - 0
01 - 1
11 - 3
10 - 2

For a given n, a gray code sequence may not be uniquely defined.
For example, [0,2,3,1] is also a valid gray code sequence.

00 - 0
10 - 2
11 - 3
01 - 1
Example 2:

Input: 0
Output: [0]
Explanation: We define the gray code sequence to begin with 0.
             A gray code sequence of n has size = 2n, which for n = 0 the size is 20 = 1.
             Therefore, for n = 0 the gray code sequence is [0].


******************************************************
key:
	- bits + backtrack?
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- generate the sequence iteratively, n = 3 can be built upon n = 2
	- 	0 000
		1 001
		3 011
		2 010

		6 110
		7 111
		5 101
		4 100

		For the pair of (2, 6), (3, 7), (1, 5) and (0, 4), the last 2 bits are the same. The only difference is 6,7,5 and 4 set the first bit on.

		Thus, just flip all the first bits in the result of the previous n, need to start from the tail


	-	n = 0: 0
		n = 1: 0, 1
		n = 2: 00, 01, 11, 10  (0, 1, 3, 2)
		n = 3: 000, 001, 011, 010, 110, 111, 101, 100 (0, 1, 3, 2, 6, 7, 5, 4)

stats:

	- O(2^n)
	- 


public ArrayList<Integer> grayCode(int n) {
    ArrayList<Integer> arr = new ArrayList<Integer>();
    arr.add(0);
    // do it bit by bit
    for(int i=0;i<n;i++){

    	// ex. 1 << 0 = 2^0 = '01', 1<<2 = 2^1 = '10', 1 <<3 = 2^2 = '100'
        int inc = 1<<i;
        for(int j=arr.size()-1;j>=0;j--){
            arr.add(arr.get(j)+inc);
        }
    }
    return arr;
}

ex. n = 2

start: arr = {0}
i = 0: inc = 1, j = 0, add.add(0+1) --> arr = {0,1}

i = 1: inc = 2, j = 1, arr.add(1 + 2) = 3 --> arr = {0,1,3}
			    j = 0, arr.add(0 + 2) = 2 --> arr = {0,1,3,2}

=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 


/*
        The purpose of this function is to convert an unsigned
        binary number to reflected binary Gray code.
 
        The operator >> is shift right. The operator ^ is exclusive or.
*/
unsigned int binaryToGray(unsigned int num)
{
        return (num >> 1) ^ num;
}
 
/*
        The purpose of this function is to convert a reflected binary
        Gray code number to a binary number.
*/
unsigned int grayToBinary(unsigned int num)
{
    unsigned int mask;
    for (mask = num >> 1; mask != 0; mask = mask >> 1)
    {
        num = num ^ mask;
    }
    return num;
}
