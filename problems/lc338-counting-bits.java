338. Counting Bits - Medium

Given a non negative integer number num. For every numbers i in the range 0 ≤ i ≤ num calculate the 
number of 1s in their binary representation and return them as an array.

Example 1:

Input: 2
Output: [0,1,1]

Example 2:

Input: 5
Output: [0,1,1,2,1,2]

Follow up:

It is very easy to come up with a solution with run time O(n*sizeof(integer)). But can you do it in 
linear time O(n) /possibly in a single pass?

Space complexity should be O(n).

Can you do it like a boss? Do it without using any builtin function like __builtin_popcount in c++ or 

******************************************************
key:
	- bits, DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- Prerequisite: An even number in binary ends with 0, An odd number in binary ends with 1

	- Strategy: Let us denote the number as num:
		If it is even, the ending bit is 0, then that bit can be ignored, countBits(num) is the 
		same as countBits(num >> 1), so countBits(num) = countBits(num >> 1);

			For example:
			num:      101010101010
			num >> 1: 10101010101

		If it is odd, the ending bit is 1, then the number of set bits is that of num - 1 + 1, 
		i.e. countBits(num) = countBits(num - 1) + 1

			For example:

			num:     101010101011
			num - 1: 101010101010



stats:

	- Time complexity: O(n)
	- Space complexity: O(n)


class Solution {
    public int[] countBits(int num) {
        int[] res = new int[num + 1];
        res[0] = 0;
        
        for(int i = 1; i <= num; i++){

        	// even
            if((i & 1) == 0){
                res[i] = res[i >> 1];
            }

            else{
            	// odd
                res[i] = res[i - 1] + 1;
            }
        }
        
        return res;
    }
}


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



