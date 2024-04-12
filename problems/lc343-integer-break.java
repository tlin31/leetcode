343. Integer Break - Medium

Given a positive integer n, break it into the sum of at least two positive integers and maximize 
the product of those integers. 

Return the maximum product you can get.

Example 1:
Input: 2
Output: 1
Explanation: 2 = 1 + 1, 1 × 1 = 1.


Example 2:
Input: 10
Output: 36
Explanation: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36.
Note: You may assume that n is not less than 2 and not larger than 58.


******************************************************
key:
	- DP -> dp[i] means output when input = i, e.g. dp[4] = 4 (2*2),dp[8] = 18 (2*2*3)...
		 -> = Math.max(dp[i],Math.max(j, dp[j]) * Math.max(i - j, dp[i - j]))
	- Prove that for all integers n > 4, ( ( n-3 ) * 3 ) > n
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	- What is the max product if we break a number N into two factors?
		I use a function to express this product: f=x(N-x)
		When x = N/2, we get the maximum of this function.
		Max is (N/2)*(N/2) when N is even or (N-1)/2 *(N+1)/2 when N is odd.
		When the maximum of f is larger than N, we should do the break:
			(N/2)*(N/2)>=N, then N >= 4
			(N-1)/2 *(N+1)/2>=N, then N >= 5

	- These two expressions mean that factors should be less than 4, otherwise we can do the 
	  break and get a better product. The factors in last result should be 1, 2 or 3. Obviously, 1 should be abandoned. Thus, the factors of the perfect product should be 2 or 3.

		The reason why we should use 3 as many as possible is
		For 6, 3 * 3 > 2 * 2 * 2. Thus, the optimal product should contain no more than three 2.

	- "Prove that for all integers n > 4, ( ( n-3 ) * 3 ) > n".



Stats:

	- O(N) 
	- 



public class Solution {
    public int integerBreak(int n) {
        if(n==2) return 1;
        if(n==3) return 2;
        int product = 1;
        while(n > 4){
            product *= 3;
            n -= 3;
        }

        // finally multiplies whatever is left in n
        product *= n;
        
        return product;
    }
}


=======================================================================================================
method 2: DP

Method:

	-	
	-	


Stats:

	- 
	- 

class Solution {
    public int integerBreak(int n) {

        //dp[i] means output when input = i, e.g. dp[4] = 4 (2*2),dp[8] = 18 (2*2*3)...
        int[] dp = new int[n + 1];
        dp[1] = 1;

       // fill the entire dp array
        for (int i = 2; i <= n; i++) {

            //ex. i = 8, then fill dp[8]: if 8 can only be broken into 2 parts, the answer could 
            // be among 1 * 7, 2 * 6, 3 * 5, 4 * 4... but these numbers can be further broken. 
            // so we have to compare 1 with dp[1], 7 with dp[7], 2 with dp[2], 6 with dp[6]...
            for (int j = 1; j <= i / 2; j++) {
                dp[i] = Math.max(dp[i],Math.max(j, dp[j]) * Math.max(i - j, dp[i - j]));
            }
        }
        return dp[n];
    }
}

=======================================================================================================
method 3:

Method:

	-	


Stats:

	- 
	- 



public class Solution {
    public int integerBreak(int n) {
        if(n == 2)
            return 1;
        else if(n == 3)
            return 2;
        else if(n%3 == 0)
            return (int)Math.pow(3, n/3);
        else if(n%3 == 1)
            return 2 * 2 * (int) Math.pow(3, (n - 4) / 3);
        else 
            return 2 * (int) Math.pow(3, n/3);
    }
            
}

