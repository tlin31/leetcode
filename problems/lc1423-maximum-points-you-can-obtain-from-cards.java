1423. Maximum Points You Can Obtain from Cards - Medium


There are several cards arranged in a row, and each card has an associated number of points 
The points are given in the integer array cardPoints.

In one step, you can take one card from the beginning or from the end of the row. You have to 
take exactly k cards.

Your score is the sum of the points of the cards you have taken.

Given the integer array cardPoints and the integer k, return the maximum score you can obtain.

 

Example 1:

Input: cardPoints = [1,2,3,4,5,6,1], k = 3
Output: 12
Explanation: After the first step, your score will always be 1. However, choosing the rightmost card 
first will maximize your total score. The optimal strategy is to take the three cards on the right, 
giving a final score of 1 + 6 + 5 = 12.


Example 2:

Input: cardPoints = [2,2,2], k = 2
Output: 4
Explanation: Regardless of which two cards you take, your score will always be 4.

Example 3:

Input: cardPoints = [9,7,7,9,7,7,9], k = 7
Output: 55
Explanation: You have to take all the cards. Your score is the sum of points of all cards.

Example 4:

Input: cardPoints = [1,1000,1], k = 1
Output: 1
Explanation: You cannot take the card in the middle. Your best score is 1. 


Example 5:

Input: cardPoints = [1,79,80,1,1,1,200,1], k = 3
Output: 202
 

Constraints:

1 <= cardPoints.length <= 10^5
1 <= cardPoints[i] <= 10^4
1 <= k <= cardPoints.length


******************************************************
key:
	- DP or sliding window
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: sliding window


Stats:

	- 
	- 


Method:

	- In extreme case, we can choose last k or first k elements;
	  In general case, we choose some from the left end and the other from the right end;
	
	- From the above 2 steps, why not connecting the both ends and then forming a curcular array?
		Traverse the 2k elements to find the maximum: from the kth from the last one-> ... -> the 
		last -> first -> ...->the kth element, this is a typical sliding window problem.
		Last k and first k elements form a window of 2k size.
	-	



    public int maxScore(int[] cardPoints, int k) {
        int res = 0, len = cardPoints.length;
        int win = 0;

        // if choose last k elements
        int start = len-k;

        for (int i = start; i < len + k; ++i) {
        	// accumulate the value of the sliding window.
            win += cardPoints[i % len]; 

            // Is the sliding window wider than k?
            if (i - start >= k) { 

            	// deduct the element from the left of the window.
                win -= cardPoints[(i - k) % len]; 
            }

            res = Math.max(win, res); // update the maximum.
        }
        return res;
    }



~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def maxScore(self, cardPoints: List[int], k: int) -> int:
        ans = win = 0
        for i in range(-k, k):
            win += cardPoints[i]
            if i >= 0:
                win -= cardPoints[i - k]
            ans = max(win, ans)    
        return ans


=======================================================================================================
method 2: DP

Stats:

	- 
	- 


Method:

	-	
	- if we want to take k cards, we could :

	take k cards from left.
	take k cards from right.
	take n(n >=1) cards from left, and take (k-n) cards from right.
	
	total points(i) = sum of last k cards 		if i = 0
					= total points[i-1] + points[i-1] - point[arrayLen - k + i - 1] when i >= 1

class Solution {
    public int maxScore(int[] cardPoints, int k) {
        int[] dp = new int[k + 1];
        //   this dp array stores the total points when taking i cards from left.
        //   since we could take 0 - k cards from left ,the length of the dp array would be k+1.

        for (int i = cardPoints.length - 1; i >= cardPoints.length - k; i--) {
            dp[0] += cardPoints[i];
        }

        int max_points = dp[0];

        for (int i = 1; i < k + 1; i++) {
            dp[i] = dp[i - 1] + cardPoints[i - 1] - cardPoints[cardPoints.length - k + i - 1];
            max_points = Math.max(max_points, dp[i]);
        }

        return max_points;
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

