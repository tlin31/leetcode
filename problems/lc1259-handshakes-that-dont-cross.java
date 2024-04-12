1259. Handshakes That Dont Cross - Hard


You are given an even number of people num_people that stand around a circle and each person 
shakes hands with someone else, so that there are num_people / 2 handshakes total.

Return the number of ways these handshakes could occur such that none of the handshakes cross.

Since this number could be very big, return the answer mod 10^9 + 7

 

Example 1:

Input: num_people = 2
Output: 1


Example 2:

Input: num_people = 4
Output: 2
Explanation: There are two ways to do it, the first way is [(1,2),(3,4)] and the second one is [(2,3),(4,1)].

Example 3:
Input: num_people = 6
Output: 5

Example 4:
Input: num_people = 8
Output: 14
 

Constraints:

2 <= num_people <= 1000
num_people % 2 == 0


******************************************************
key:
	- math + dp
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time O(N^2)
	- Space O(N)



Method:

	- Consider there are n people (n is even)
		For those people to not cross hand, person 1 can shake 2, 4, 6, 8, ..., n:

		he/she can choose anyone, split i pairs on his left and n - 1 - i pairs on his right.

	- Shake 2: divide into 2 sets (an emtpy set and a set of people from 3 to n)
	  Shake 4: divide into 2 sets (a set of people 2 & 3 and a set of people from 5 to n)
	  Shake 6: divide into 2 sets (a set of people from 2 to 5 and a set of people from 7 to n)
      ...
	  Shake n: divide into 2 sets (a set of people from 2 to n-1 and an empty set)
	
	- For n people, there are n/2 way for perosn 1 to shake hand. If person 1 shake hand with 
	  person k, there are count(2 to k-1)*count(k+1 to n) scenarios.

	- So here comes the equation of dynamic program:
			dp[n + 1] = dp[0] * dp[n] + dp[1] * dp[n - 1] + ..... + dp[n] * dp[0]


    public int numberOfWays(int n) {
        long mod = (long)1e9 + 7;
        long[] dp = new long[n / 2 + 1];
        dp[0] = 1;
        for (int k = 1; k <= n / 2; ++k) {
            for (int i = 0; i < k; ++i) {
                dp[k] = (dp[k] + dp[i] * dp[k - 1 - i]) % mod;
            }
        }
        return (int)dp[n / 2];
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

