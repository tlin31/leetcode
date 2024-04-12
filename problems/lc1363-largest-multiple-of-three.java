1363. Largest Multiple of Three - Hard

Given an integer array of digits, return the largest multiple of three that can be formed by
concatenating some of the given digits in any order.

Since the answer may not fit in an integer data type, return the answer as a string.

If there is no answer return an empty string.

 

Example 1:

Input: digits = [8,1,9]
Output: "981"
Example 2:

Input: digits = [8,6,7,1,0]
Output: "8760"
Example 3:

Input: digits = [1]
Output: ""
Example 4:

Input: digits = [0,0,0,0,0,0]
Output: "0"
 

Constraints:

1 <= digits.length <= 10^4
0 <= digits[i] <= 9
The returning answer must not contain unnecessary leading zeros.


******************************************************
key:
	- Math & mod
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time O(nlogn), where I use quick sort. We can also apply counting sort, so it will be O(n)
	- Space O(sort)



Method:
	999....999 % 3 == 0
	1000....000 % 3 == 1
	a000....000 % 3 == a % 3
	abcdefghijk % 3 == (a+b+c+..+i+j+k) % 3


Explanation
	Calculate the sum of digits total = sum(A)
	If total % 3 == 0, we got it directly
	If total % 3 == 1 and we have one of 1,4,7 in A:
	we try to remove one digit of 1,4,7
	If total % 3 == 2 and we have one of 2,5,8 in A:
	we try to remove one digit of 2,5,8
	
	public String largestMultipleOfThree(int[] digits) {
        int[] c = new int[10];
        int sum = 0;
        for (int i = 0; i < digits.length; i++) {
            sum += digits[i];
            c[digits[i]]++;
        }
        if (sum == 0) {
            return "0";
        }
        
        remove(c, sum % 3);
        
        return buildStr(c);
    }
    
    private void remove(int[] count, int mod) {
        if (mod == 0) {
            return;
        }
        for (int i = 0; i <= 2; i++) {
            int check = i * 3 + mod;
            if (count[check] != 0) {
                //remove the smallest element that make the mod equals 0
                count[check]--;
                return;
            }
        }
        //if not found, find two smallest elements that make the mod equals 0
        remove(count, 3 - mod);
        remove(count, 3 - mod);
    }
    
    private String buildStr(int[] count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 9; i >= 0; i--) {
            while (count[i]-- > 0) {
                sb.append(i);
            }
        }
        return sb.toString();
    }



~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


    def largestMultipleOfThree(self, A):
        total = sum(A)
        count = collections.Counter(A) 		# stores {num:count}

        A.sort(reverse=1)

        def f(i):
            if count[i]:
                A.remove(i)
                count[i] -= 1
            if not A: return ''
            if not any(A): return '0'
            if sum(A) % 3 == 0: return ''.join(map(str, A))

        if total % 3 == 0:
            return f(-1)
        if total % 3 == 1 and count[1] + count[4] + count[7]:
            return f(1) or f(4) or f(7)
        if total % 3 == 2 and count[2] + count[5] + count[8]:
            return f(2) or f(5) or f(8)
        if total % 3 == 2:
            return f(1) or f(1) or f(4) or f(4) or f(7) or f(7)

        return f(2) or f(2) or f(5) or f(5) or f(8) or f(8)


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


