837. New 21 Game - Medium


Alice plays the following game, loosely based on the card game "21".

Alice starts with 0 points, and draws numbers while she has less than K points.  During each draw, 
she gains an integer number of points randomly from the range [1, W], where W is an integer.  
Each draw is independent and the outcomes have equal probabilities.

Alice stops drawing numbers when she gets K or more points.  

What is the probability that she has N or less points?

Example 1:

Input: N = 10, K = 1, W = 10
Output: 1.00000
Explanation:  Alice gets a single card, then stops.


Example 2:

Input: N = 6, K = 1, W = 10
Output: 0.60000
Explanation:  Alice gets a single card, then stops.
In 6 out of W = 10 possibilities, she is at or below N = 6 points.


Example 3:

Input: N = 21, K = 17, W = 10
Output: 0.73278


Note:

0 <= K <= N <= 10000
1 <= W <= 10000
Answers will be accepted as correct if they are within 10^-5 of the correct answer.
The judging time limit has been reduced for this question.


******************************************************
key:
	- Math, prob
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:
	Time O(N)
	Space O(N), can be improve to O(W)


Method:

	-	point 0 initially
		while (point < K) {
		    draw w  from [1, W] randomly 
		    point += w
		}
		probability(point <= N) ?

	-  	point total range [0, K + W - 1]
		we define probability[i] as probability to get i points
		Before we reach point `i`, we draw `w`, i.e., our last point is `i - w` 

		probability[i] = sum(probability to get [i - w] points * 1 / W) for w can be any of [1, W]
		where 0 <= i - w < K
		target probability = sum of prabability to get points [K, N]


	-	

dp[i] is the probability that we get points i at some moment.

In another word:
1 - dp[i] is the probability that we skip the points i.

dp[i] = sum(last W dp values) / W

To get Wsum = sum(last W dp values),
we can maintain a sliding window with size at most W.





 	public double new21Game(int N, int K, int W) {
        if (K == 0 || N >= K + W) 
        	return 1;

        double dp[] = new double[N + 1];
        double Wsum = 1, 
        	   res = 0;

        // base case 
        dp[0] = 1;

        for (int i = 1; i <= N; ++i) {
            dp[i] = Wsum / W;
            if (i < K) 
            	Wsum += dp[i]; 

            else 
            	res += dp[i];

            if (i - W >= 0) 
            	Wsum -= dp[i - W];
        }
        return res;
    }


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	


dp[k] = 1.0 when K <= k <= N, else 0.0

# dp[x] = prob Alice has x points
for k from K-1 to 0:
    dp[k] = (dp[k+1] + ... + dp[k+W]) / W
return dp[0]



class Solution {
    public double new21Game(int N, int K, int W) {

    	// max range is N + W + 1: already at N, take the next element which is W
        double[] dp = new double[N + W + 1];

        for (int k = K; k <= N; ++k)
            dp[k] = 1.0;

        double S = Math.min(N - K + 1, W);
        // S = dp[k+1] + dp[k+2] + ... + dp[k+W]

        for (int k = K - 1; k >= 0; --k) {
            dp[k] = S / W;
            S += dp[k] - dp[k + W];
        }
        return dp[0];
    }
}


ex.
N = 2
K = 2
W = 4
Answer: 0.3125


First Pick:
Alice can pick 1 to 4 points (with equal probability). So after first pick:
P[1] = P[2] = P[3] = P[4] = 0.25

Second Pick:
This can only occur if the first pick resulted in 1. For other cases the game stops.
If the first pick was 1, the probability of landing on 2 points is:
P[2] = 0.25 [first pick] + 0.25 * 0.25 [probability of selecting 1, and then 1 again]
Similarly for P[3] and P[4]

For second pick we will also have a probability of reaching 5, P[5] = 0.25 * 0.25

There is no possibility of a third pick as K = 2.

Now the answer is:
sum(P[i]) for i = K to N

Note that, if we add i = K to i = K+W-1, p = 1.0

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



