552. Student Attendance Record II - Hard

Given a positive integer n, return the number of all possible attendance records with length n, 
which will be regarded as rewardable. The answer may be very large, return it after mod 109 + 7.

A student attendance record is a string that only contains the following three characters:

'A' : Absent.
'L' : Late.
'P' : Present.

A record is regarded as rewardable if it does not contain more than one 'A' (absent) or more than 
two continuous 'L' (late).

Example 1:
Input: n = 2
Output: 8 
Explanation:
There are 8 records with length 2 will be regarded as rewardable:
"PP" , "AP", "PA", "LP", "PL", "AL", "LA", "LL"
Only "AA" won't be regarded as rewardable owing to more than one absent times. 
Note: The value of n won't exceed 100,000.


******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:


定义了三个 DP 数组 P, L, A：
	P[i] 表示数组 [0,i] 范围内以P结尾的所有排列方式，
	L[i] 表示数组 [0,i] 范围内以L结尾的所有排列方式，
	A[i] 表示数组 [0,i] 范围内以A结尾的所有排列方式。

	那么最终所求的就是 P[n-1] + L[n-1] + A[n-1]

1. P数组的，P字符没有任何限制条件，可以跟在任何一个字符后面，所以有 
					P[i] = A[i-1] + P[i-1] + L[i-1]

2. L数组的，L字符唯一的限制条件是不能有超过两个连续的L，那么在P和L字符后面可以加1一个L，如果前一个字符是L，要看再前面的
   一位是什么字符，
   	如果是P或着A的话，可以加L，
   	如果是L的话，就不能再加了，否则就连续3个了
   					L[i] = A[i-1] + P[i-1] + A[i-2] + P[i-2]

3. A数组的，这个比较麻烦，字符A的限制条件是整个字符串最多只能有1个A，那么当前一个字符是A的话，就不能再加A来，当前一个
   字符是P或者L的话，要确定之前从没有A出现过，才能加上A。

   定义两个数组 P1 & L1:
   	P1[i] 表示数组 [0,i] 范围内以P结尾的不包含A的所有排列方式，
   	L1[i] 表示数组 [0,i] 范围内以L结尾的不包含A的所有排列方式

				A[i] = P1[i-1] + L1[i-1]

				P1[i] = P1[i-1] + L1[i-1]

				L1[i] = P1[i-1] + P1[i-2]

	将第二第三个等式多次带入第一个等式，就可以将 P1 和 L1 消掉，可以化简为：

			A[i] = A[i-1] + A[i-2] + A[i-3]


stats:

	- Time complexity: O(n), since the algorithm is one-pass from 1 to n.
	- Space complexity: O(n), since 3 arrays are used to save P(n), L(n), A(n), the total size is 3n.

public int checkRecord(int n){      
        if(n == 1) return 3;
        if(n == 2) return 8;
        int m = 1000000007;
        int[] A = new int [n];
        int[] P = new int [n];
        int[] L = new int [n];
        
        P[0] = 1;

        L[0] = 1;	// L
        L[1] = 3;	// PL, LL, AL

        A[0] = 1;	// A
        A[1] = 2;	// PA, LA
        A[2] = 4;	// PPA, PLA, LPA, LLA
        
        // build dp
        for(int i = 1; i < n; i++) {
            A[i - 1] %= m;
            P[i - 1] %= m;
            L[i - 1] %= m;
            
            P[i] = ((A[i - 1] + P[i - 1]) % m + L[i - 1]) % m;
            
            if(i > 1) 
            	L[i] = ((A[i - 1] + P[i - 1]) % m + (A[i - 2] + P[i - 2]) % m) % m;
            
            if(i > 2) 
            	A[i] = ((A[i - 1] + A[i - 2]) % m + A[i - 3]) % m;
        }
        
        return ((A[n - 1] % m + P[n - 1] % m) % m + L[n - 1] % m) % m;
    }    
}



=======================================================================================================

method 2:

method:
	Let AnLn denote number of strings containing n A's and ending with n L's
	For example:

	When n = 1 we have:

		 A0L0: P
		 A0L1: L
		 A0L2:
		 A1L0: A
		 A1L1:
		 A1L2:
		 Done:

	When n = 2 we have:

		 A0L0: LP, PP
		 A0L1: PL
		 A0L2: LL
		 A1L0: AP, LA, PA
		 A1L1: AL
		 A1L2:
		 Done: AA
		
	In general we have this transition table:

		 -----+---------------
		 INIT | A -- L -- P --
		 -----+---------------
		 A0L0 | A1L0 A0L1 A0L0
		 A0L1 | A1L0 A0L2 A0L0
		 A0L2 | A1L0 Done A0L0
		 A1L0 | Done A1L1 A1L0
		 A1L1 | Done A1L2 A1L0
		 A1L2 | Done Done A1L0

	From the transition table we see that:
	A0L0 of n can result from A0L0 + A0L1 + A0L2 of n - 1 by appending P
	A0L1 of n can only result from A0L0 of n - 1 by appending L
	and so on...

	That is why in each iteration we update:
	dp[0] = dp[0] + dp[1] + dp[2]
	dp[1] = dp[0]
	and so on...


stats:

	- 
	- 



public int checkRecord(int n) {
    int[] dp = { 1, 1, 0, 1, 0, 0 }; // init table for n = 1
    for (int i = 2; i <= n; i++) // updating table for n = i
        dp = new int[] { sum(dp, 0, 2), dp[0], dp[1], sum(dp, 0, 5), dp[3], dp[4] };
    return sum(dp, 0, 5);       
}                                   

static int sum(int[] arr, int l, int h) {
    int s = 0;  
    for (int i = l; i <= h; i++) 
        s = (s + arr[i]) % 1000000007;  
    return s;                   
} 
=======================================================================================================
method 3:

method:

	这里面定义了两个数组 P 和 PorL，其中 P[i] 表示数组前i个数字中以P结尾的排列个数，PorL[i] 表示数组前i个数字中已P或者
	L结尾的排列个数。

	这个解法的精髓是先不考虑字符A的情况，而是先把定义的这个数组先求出来，由于P字符可以再任意字符后面加上，所以 
			
			P[i] = PorL[i-1]；

	而 PorL[i] 由两部分组成，P[i] + L[i]，其中 P[i] 已经更新了，L[i] 只能当前一个字符是P，或者前一个字符是L且再
	前一个字符是P的时候加上，即为 P[i-1] + P[i-2]，所以 

			PorL[i] = P[i] + P[i-1] + P[i-2]。

	那么这里就已经把不包含A的情况求出来了，存在了 PorL[n] 中，下面就是要求包含一个A的情况，那么就得去除一个字符，
	从而给A留出位置。

	就相当于在数组的任意一个位置上加上A，数组就被分成左右两个部分了，而这两个部分当然就不能再有A了，实际上所有不包含A的
	情况都已经在数组 PorL 中计算过了，而分成的子数组的长度又不会大于原数组的长度，所以直接在 PorL 中取值就行了，两个
	子数组的排列个数相乘，然后再把所有分割的情况累加起来就是最终结果啦，

stats:

	- 
	- 
static final int M = 1000000007;

public int checkRecord(int n) {
    long[] PorL = new long[n + 1]; // ending with P or L, no A
    long[] P = new long[n + 1]; // ending with P, no A
    PorL[0] = P[0] = 1; PorL[1] = 2; P[1] = 1;

    for (int i = 2; i <= n; i++) {
        P[i] = PorL[i - 1];
        PorL[i] = (P[i] + P[i - 1] + P[i - 2]) % M;
    }
    
    long res = PorL[n];
    for (int i = 0; i < n; i++) { // inserting A into (n-1)-length strings
    	long s = (PorL[i] * PorL[n - i - 1]) % M;
        res = (res + s) % M;
    }
    
    return (int) res;
}


