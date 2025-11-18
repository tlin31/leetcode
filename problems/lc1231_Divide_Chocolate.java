1231. Divide Chocolate - Hard

You have one chocolate bar that consists of some chunks. Each chunk has its own sweetness given by the array sweetness.

You want to share the chocolate with your k friends so you start cutting the chocolate bar into k + 1 pieces using k cuts, each piece consists of some consecutive chunks.

Being generous, you will eat the piece with the minimum total sweetness and give the other pieces to your friends.

Find the maximum total sweetness of the piece you can get by cutting the chocolate bar optimally.

 

Example 1:

Input: sweetness = [1,2,3,4,5,6,7,8,9], k = 5
Output: 6
Explanation: You can divide the chocolate to [1,2,3], [4,5], [6], [7], [8], [9]
Example 2:

Input: sweetness = [5,6,7,8,9,1,2,3,4], k = 8
Output: 1
Explanation: There is only one way to cut the bar into 9 pieces.
Example 3:

Input: sweetness = [1,2,2,1,2,2,1,2,2], k = 2
Output: 5
Explanation: You can divide the chocolate to [1,2,2], [1,2,2], [1,2,2]
 

Constraints:

0 <= k < sweetness.length <= 104
1 <= sweetness[i] <= 105


******************************************************
key:
	- similar to 401 split subarray max
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

	-	In this problem we want to find: Maximum of minimum total sweetness
In "Split Array Largest sum" we want to find: Minimum of maximum largest sum

In both places we do binary search on target answer, the difference is subtle but the key:

In this when we overshoot the target, we will include that number in previous sum, as that is how we will maintain the target as the minimum number and binary search will find this optimal minimum
In "Split Array Largest sum" when we overshoot the target, we will include the number in the next sum, so we can ensure all numbers are less than target - binary search does the rest of the magic



Stats:

	- 
	- 


//Same as lc410, n*log(range) time, constant space
class Solution {
    public int maximizeSweetness(int[] sweetness, int K) {
        int sum = 0;
        int min = Integer.MAX_VALUE;
        for (int num : sweetness) {
            sum += num;
            min = Math.min(num, min);
        }

        if (K + 1 == 1) return sum;//注意是切K刀，变成K+1块儿
        
        //binary search between l and r
        int l = min, r = sum;
        
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (valid(mid, sweetness, K + 1)) {
                //lower mid     
                //2 situations: either we successfully divide the array into m parts and the sum 
                // of each part is less than mid, or we used up all numbers before we reach m. 
                // Both of them mean that we should lower mid because we need to find the minimum one.
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
                if (count > K) return false;
            }
        }
        return true;
    }

}









