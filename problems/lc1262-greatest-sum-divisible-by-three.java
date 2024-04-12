1262. Greatest Sum Divisible by Three - Medium


Given an array nums of integers, we need to find the maximum possible sum of elements of the 
array such that it is divisible by three.

 

Example 1:

Input: nums = [3,6,5,1,8]
Output: 18
Explanation: Pick numbers 3, 6, 1 and 8 their sum is 18 (maximum sum divisible by 3).
Example 2:

Input: nums = [4]
Output: 0
Explanation: Since 4 is not divisible by 3, do not pick any number.
Example 3:

Input: nums = [1,2,3,4,4]
Output: 12
Explanation: Pick numbers 1, 3, 4 and 4 their sum is 12 (maximum sum divisible by 3).
 

Constraints:

1 <= nums.length <= 4 * 10^4
1 <= nums[i] <= 10^4


******************************************************
key:
	- DP, use math to get dp[]
	- edge case:
		1) empty array, return 0
		2) if nums == 0, return 0

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time O(N)
	- Space O(1)



Method:

	-	get sum devisible by 3
	
    1.The last maximum possible sum that it is divisible by three could only depends
      on 3 kinds of "subroutines/subproblems":
            1. previous maximum possible sum that it is divisible by three
               preSum % 3 == 0       (example: preSum=12 if lastNum=3)

            2. preSum % 3 == 1       (example: preSum=13 if lastNum=2)

            3. preSum % 3 == 2       (example: preSum=14 if lastNum=1)
        
    2. dp state: 
        dp[i] = accumulated max sum s.t. reminder == i when sum / 3 --> only has 3 elements

    init:
    	dp[0]= 0: max sum such that the reminder == 0 when 0 / 3 is 0
    	dp[1]=-Inf: max sum such that the reminder == 1 when 0 / 3 does not exist
    	dp[2]=-Inf: max sum such that the reminder == 2 when 0 / 3 does not exist


    Transition: 
        dp_cur[(rem + num) % 3] 
            = max(dp_prev[(rem + num) % 3], dp_prev[rem]+num)
            where "rem" stands for reminder for shorter naming

        meaning: 
            "Current max sum with reminder 0 or 1 or 2" could be from
	            1) prevSum with reminder 0 or 1 or 2 consecutively
	            2) prevSum with some reminder "rem" + current number "num"
            
            Since (dp_prev[rem]+num) % 3 = (rem+num) % 3 = i, we are able to correctly 
            update dp[i] for i = 1,2,3 each time


	public int maxSumDivThree(int[] nums) {

	    int[] dp = new int[]{0, Integer.MIN_VALUE, Integer.MIN_VALUE};
	    
	    for(int num : nums){
	        int[] temp = new int[3];

	        for(int reminder=0; reminder<3; reminder++){
	            temp[(num+reminder)%3] = Math.max(dp[ (num+reminder)%3 ], dp[reminder] + num);
	        }
	        dp = temp; 
	    }
	    return dp[0];
	}


ex. input: [3,6,5,1,8]


for num = 3, temp[] = [3, -2147483645, -2147483645]
for num = 6, temp[] = [9, -2147483639, -2147483639]
for num = 5, temp[] = [9, -2147483634, 14]  --> 3rd = 9+5 = 14
for num = 1, temp[] = [15, 10, 14]
for num = 8, temp[] = [18, 22, 23]


~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
 def maxSumDivThree(self, A):
        seen = [0, 0, 0]
        for a in A:
            for i in seen[:]:
                seen[(i + a) % 3] = max(seen[(i + a) % 3], i + a)

        return seen[0]


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

