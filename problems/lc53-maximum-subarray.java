53. Maximum Subarray - Easy

Given an integer array nums, find the contiguous subarray (containing at least 
one number) which has the largest sum and return its sum.

Example:
Input: [-2,1,-3,4,-1,2,1,-5,4],
Output: 6
Explanation: [4,-1,2,1] has the largest sum = 6.

Follow up:
If you have figured out the O(n) solution, try coding another solution using the 
divide and conquer approach, which is more subtle.

******************************************************
key:
	- greedy/dp， divide & conquer
	- edge case:
		1) empty string, return empty
		2)

******************************************************



Kadane：在每个位置 i，只关心“以 i 结尾的最大子数组和”，
		如果之前的和是负数，就果断丢弃，从当前元素重新开始。


dp[i] = 以 i 结尾的最大子数组和
dp[i] = max(nums[i], dp[i-1] + nums[i])
最终的答案： max(dp[i])  (i 从 0 到 n-1)


空间优化（Kadane 的精华）

	我们发现：dp[i] 只依赖 dp[i-1]

	所以可以压缩成两个变量：

	curSum：当前以 i 结尾的最大和

	maxSum：全局最大子数组和

class Solution {
    public int maxSubArray(int[] nums) {
        int curSum = nums[0];
        int maxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            curSum = Math.max(nums[i], curSum + nums[i]);
            maxSum = Math.max(maxSum, curSum);
        }

        return maxSum;
    }
}


=======================================================================================================

method:

	- divide and conquer approach
	- 假设我们有了一个函数 int getSubMax(int start, int end, int[] nums) ，可以得到 num[ start, end ) 
		(左包右不包) 中子数组最大值。
	- 如果， start == end，那么 getSubMax 直接返回 nums [ start ] 就可以了。
	- 先找一个 mid ， mid = ( start + end ) / 2。
	- 然后，对于我们要找的和最大的子数组有两种情况。
		1) mid 不在我们要找的子数组中
			--> 子数组的最大值要么是 mid 左半部分数组的子数组产生，要么是右边的产生，最大值可以利用 getSubMax 求出来。
			int leftMax = getSubMax(start, mid, nums);
			int rightMax = getSubMax(mid + 1, end, nums);

		2) mid 在我们要找的子数组中
			--> 我们可以分别从 mid 左边扩展，和右边扩展，找出两边和最大的时候，然后加起来就可以了。
				当然如果，左边或者右边最大的都小于 0 ，我们就不加了。

	- 最后，我们只需要返回这三个中最大的值就可以了。

stats:

	- 
	- 时间复杂度：O（n log ( n )）。由于 getContainMidMax 这个函数耗费了 O（n）。所以时间复杂度反而相比之前的算法变大了。
	- Runtime: 53 ms, faster than 5.16% of Java online submissions for Maximum Subarray.
	- Memory Usage: 46.6 MB, less than 5.16%


class Solution {

    int[] nums;
    
    public int maxSubArray(int[] nums) {
        //divide and conquer

        if(nums==null||nums.length==0) return 0;
        int n = nums.length;
        this.nums=nums;
        return divideAndConquer(0,n-1);
    }

    private int divideAndConquer(int start, int end){
        if(start==end) return nums[start];

        int mid = start+(end-start)/2;
        //if max sum doesn't conatin mid
        int leftMax = divideAndConquer(start,mid);
        int rightMax = divideAndConquer(mid+1,end);

        // if contains max
        int midLeftMax = 0;
        int sum = 0;
        for(int i = mid-1;i>=start;i--){
            sum+=nums[i];
            midLeftMax=Math.max(midLeftMax,sum);
        }


        int midRightMax=0;
        sum=0;
        for(int i = mid+1;i<=end;i++){
            sum+=nums[i];
            midRightMax=Math.max(midRightMax,sum);         
            
        }

        int containMid = midLeftMax+nums[mid]+midRightMax;
	    return Math.max(containMid, Math.max(leftMax, rightMax));
    }
}




=======================================================================================================
method 1:

method:

	- dp optimize
	- 用一个一维数组 dp [ i ] 表示以下标 i 结尾的子数组的元素的最大的和，也就是这个子数组最后一个元素
		是下边为 i 的元素，并且这个子数组是所有以 i 结尾的子数组中，和最大的。
	- 如果 dp [ i - 1 ] < 0，那么 dp [ i ] = nums [ i ]。
	- 如果 dp [ i - 1 ] >= 0，那么 dp [ i ] = dp [ i - 1 ] + nums [ i ]。

stats:

	- 时间复杂度： O（n）。
	- 空间复杂度：O（n）。
	- Runtime: 1 ms, faster than 87.23% of Java online submissions for Maximum Subarray.
	- Memory Usage: 38.1 MB, less than 98.12%

	public int maxSubArray(int[] nums) {
	    int n = nums.length;
	    int[] dp = new int[n];
	    int max = nums[0];
	    dp[0] = nums[0];

	    for (int i = 1; i < n; i++) {
	        //两种情况更新 dp[i]

	        // if previous sum < 0, then add that to current will only be smaller.
	        // set this to its current value
	        if (dp[i - 1] < 0) {
	            dp[i] = nums[i];
	        } else {
	            dp[i] = dp[i - 1] + nums[i];
	        }
	        //更新 max
	        max = Math.max(max, dp[i]);
	    }
	    return max;
	}


=======================================================================================================
method 1.2:

method:

	- 
	- 当然，和以前一样，我们注意到更新 i 的情况的时候只用到 i - 1 的时候，所以我们不需要数组，只需要两个变量。


stats:

	- 时间复杂度： O（n）。
	- 空间复杂度：O（1）。
	- Runtime: 1 ms, faster than 87.23% of Java online submissions for Maximum Subarray.
	- Memory Usage: 38.1 MB, less than 98.12%


public int maxSubArray(int[] nums) {
    int n = nums.length;
    //两个变量即可
    int[] dp = new int[2];
    int max = nums[0];
    dp[0] = nums[0];
    for (int i = 1; i < n; i++) {
        //利用求余，轮换两个变量
        if (dp[(i - 1) % 2] < 0) {
            dp[i % 2] = nums[i];
        } else {
            dp[i % 2] = dp[(i - 1) % 2] + nums[i];
        }
        max = Math.max(max, dp[i % 2]);
    }
    return max;
}


=======================================================================================================
method 1.3:

method:

	- 直接用一个变量就可以了
	- 

stats:

	- 
	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Maximum Subarray.
	- Memory Usage: 36.9 MB, less than 99.53%

public int maxSubArray(int[] nums) {
    int n = nums.length;
    int dp = nums[0];
    int max = nums[0]; 
    for (int i = 1; i < n; i++) {
    	// 下面的if else也可以用 ： dp= Math.max(dp + nums[i],nums[i]);

        if (dp < 0) {
            dp = nums[i];
        } else {
            dp= dp + nums[i];
        }
        max = Math.max(max, dp);
    }
    return max;
}



=======================================================================================================
method 2:


