1231. Divide Chocolate - Hard


You have one chocolate bar that consists of some chunks. Each chunk has its own sweetness given 
by the array sweetness.

You want to share the chocolate with your K friends so you start cutting the chocolate bar into K+1 
pieces using K cuts, each piece consists of some consecutive chunks.

Being generous, you will eat the piece with the minimum total sweetness and give the other pieces 
to your friends.

Find the maximum total sweetness of the piece you can get by cutting the chocolate bar optimally.

 

Example 1:

Input: sweetness = [1,2,3,4,5,6,7,8,9], K = 5
Output: 6
Explanation: You can divide the chocolate to [1,2,3], [4,5], [6], [7], [8], [9]

Example 2:
Input: sweetness = [5,6,7,8,9,1,2,3,4], K = 8
Output: 1
Explanation: There is only one way to cut the bar into 9 pieces.

Example 3:
Input: sweetness = [1,2,2,1,2,2,1,2,2], K = 2
Output: 5
Explanation: You can divide the chocolate to [1,2,2], [1,2,2], [1,2,2]
 

Constraints:

0 <= K < sweetness.length <= 10^4
1 <= sweetness[i] <= 10^5


******************************************************
key:
	- Binary search~ 神奇
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- Binary search: we want to maximize the minimum sweetness, BS the result between 1 and 10^9.


stats:

	- Time O(Nlog(10^9))
	- Space O(1)



class Solution {
    public int maximizeSweetness(int[] sweetness, int K) {
        int sum = 0;
        int min = Integer.MAX_VALUE;
        for (int num : sweetness) {
            sum += num;
            min = Math.min(num, min);
        }

        //注意是切K刀，变成K+1块儿
        if (K + 1 == 1) return sum;
        
        //binary search between l and r
        int l = min, r = sum;
        
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (valid(mid, sweetness, K + 1)) {
                //2 situations: 
                // 1) can divide the array into k+1 parts & the sum of each part < mid
                // 2) we used up all numbers before we reach k+1. 
                // result: need to lower mid because we want to find the minimum one
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }
    public boolean valid(long target, int[] nums, int K) {
        int count = 1;
        long total = 0;
        for(int num : nums) {
            total += num;
            if (total > target) {
                total = 0;//here what is different from 410
                count++;
                if (count > K) 
                	return false;
            }
        }
        // means count <= k, can decrease sweetness per person
        return true;
    }
}


=======================================================================================================
method 2:

method:

	- compact ver.
	- 

stats:

	- 
	- 

    public int maximizeSweetness(int[] A, int K) {
        int left = 1, right = (int)1e9 / (K + 1);
        while (left < right) {
            int mid = (left + right + 1) / 2;
            int cur = 0, cuts = 0;
            for (int a : A) {
                if ((cur += a) >= mid) {
                    cur = 0;
                    if (++cuts > K) break;
                }
            }
            if (cuts > K)
                left = mid;
            else
                right = mid - 1;
        }
        return left;
    }
=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 





