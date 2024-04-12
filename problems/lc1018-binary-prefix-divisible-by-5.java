1018. Binary Prefix Divisible By 5 - Easy


Given an array A of 0s and 1s, consider N_i: the i-th subarray from A[0] to A[i] interpreted as 
a binary number (from most-significant-bit to least-significant-bit.)

Return a list of booleans answer, where answer[i] is true if and only if N_i is divisible by 5.

Example 1:

Input: [0,1,1]
Output: [true,false,false]
Explanation: 
The input numbers in binary are 0, 01, 011; which are 0, 1, and 3 in base-10.  Only the first number 
is divisible by 5, so answer[0] is true.


Example 2:

Input: [1,1,1]
Output: [false,false,false]

Example 3:

Input: [0,1,1,1,1,1]
Output: [true,false,false,false,true,false]

Example 4:

Input: [1,1,1,0,1]
Output: [false,false,false,false,false]



******************************************************
key:
	- bit -->  use mod definition to avoid storing the actual number
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

	-	
	-	Observe that we only care about the remainder, not the actual number.
	-  (ab + c)%d = ((a%d)(b%d) + c%d)%d.
		new_number%5 = ((old_number%5)*2 + d)%5;
	- This would prevent overflows, since new_number is the equivalent representation of the original 
	  number in the modular arithemtic of 5.

Start with num=0.
For each valid i update num as num = (num*2 + a[i])%5
At each stage, if num is zero, the substring ending at i is divisible by 5.



class Solution {
    public List<Boolean> prefixesDivBy5(int[] A) {
        List<Boolean> b = new ArrayList<>();
        int start = A[0];
        if(start % 5 == 0) {b.add(true);}
        if(start % 5 != 0) {b.add(false);}

        for(int i = 1; i < A.length; i++){
            if(A[i] == 1){
                start = start * 2 + 1;
            }else{
                start = start * 2;
            }
            if(start % 5 == 0) {b.add(true);}
            if(start % 5 != 0) {b.add(false);}
            start = start % 5;
        }
        return b;

        
    }
}




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	
class Solution {
    public List<Boolean> prefixesDivBy5(int[] A) {
        List<Boolean> res = new ArrayList<>();
        int num = 0;
        for(int cur : A){
            num = ((num << 1) + cur) % 5;
            if(num == 0) res.add(true);
            else res.add(false);
        }
        
        return res;
    }
}





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

