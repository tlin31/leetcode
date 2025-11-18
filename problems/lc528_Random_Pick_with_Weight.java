528. Random Pick with Weight - Medium

You are given a 0-indexed array of positive integers w where w[i] describes the weight 
of the ith index.

You need to implement the function pickIndex(), which randomly picks an index in the range 
[0, w.length - 1] (inclusive) and returns it. The probability of picking an index i is w[i] / sum(w).

For example, if w = [1, 3], the probability of picking index 0 is 1 / (1 + 3) = 0.25 (i.e., 25%), 
and the probability of picking index 1 is 3 / (1 + 3) = 0.75 (i.e., 75%).
 

Example 1:

Input
["Solution","pickIndex"]
[[[1]],[]]
Output
[null,0]

Explanation
Solution solution = new Solution([1]);
solution.pickIndex(); // return 0. The only option is to return 0 since there is only one element in w.

Example 2:

Input
["Solution","pickIndex","pickIndex","pickIndex","pickIndex","pickIndex"]
[[[1,3]],[],[],[],[],[]]
Output
[null,1,1,1,1,0]

Explanation
Solution solution = new Solution([1, 3]);
solution.pickIndex(); // return 1. It is returning the second element (index = 1) that has a probability of 3/4.
solution.pickIndex(); // return 1
solution.pickIndex(); // return 1
solution.pickIndex(); // return 1
solution.pickIndex(); // return 0. It is returning the first element (index = 0) that has a probability of 1/4.

Since this is a randomization problem, multiple answers are allowed.
All of the following outputs can be considered correct:
[null,1,1,1,1,0]
[null,1,1,1,1,1]
[null,1,1,1,0,0]
[null,1,1,1,0,1]
[null,1,0,1,0,0]
......
and so on.
 

Constraints:

1 <= w.length <= 104
1 <= w[i] <= 105
pickIndex will be called at most 104 times.


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

	-	Use accumulated freq array to get idx.
			w[] = {2,5,3,4} => wsum[] = {2,7,10,14}
			then get random val random.nextInt(14)+1, idx is in range [1,14]

			idx in [1,2] return 0
			idx in [3,7] return 1
			idx in [8,10] return 2
			idx in [11,14] return 3
			then become LeetCode 35. Search Insert Position
Example:
Let's assume the same weights are: [2, 7, 3, 3]
The prefix sum becomes: [2, 9, 12, 15]
The maximum in this range is 15.
Note: The binary search algorithm here finds the upper bound index. 
For random numbers [10, 11, 12], we would want index 2.


Random.nextInt(15) + 1
This generates random numbers in the range (1, 15).
So for random numbers in range [1, 2], index 0 will be picked.
And for random numbers in range [13, 15] index 3 will be picked.
Conclusion: This corrects the probability distribution for index 3 as well as index 0, and hence the correct solution.


	- In Java, random.nextInt(X) will return value from 0 to X-1

		As there is no 0 weight, we need to +1 to avoid getting 0 from the random function.

		+1 will make the range as 1 to X, which is exactly the real range we need.

Stats:

	- Time: O(n) to init, O(logn) for one pick
	Space: O(n)
	




class Solution {

    Random random;
    int[] wSums;
    
    public Solution(int[] w) {
        this.random = new Random();
        for(int i=1; i<w.length; ++i)
            w[i] += w[i-1];
        this.wSums = w;
    }
    
    public int pickIndex() {
        int len = wSums.length;
        int idx = random.nextInt(wSums[len-1]) + 1;
        int left = 0, right = len - 1;

        // search position 
        while(left <= right){
            int mid = left + (right-left)/2;
            if(wSums[mid] == idx)
                return mid;
            else if(wSums[mid] < idx)
                left = mid + 1;
            else
                right = mid-1;
        }
        return left;
    }
}







