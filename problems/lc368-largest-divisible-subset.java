368. Largest Divisible Subset - Medium

Given a set of distinct positive integers, find the largest subset such that every pair (Si, Sj) 
of elements in this subset satisfies:

Si % Sj = 0 or Sj % Si = 0.

If there are multiple solutions, return any subset is fine.

Example 1:

Input: [1,2,3]
Output: [1,2] (of course, [1,3] will also be ok)

Example 2:

Input: [1,2,4,8]
Output: [1,2,4,8]



******************************************************
key:
	- DP, DFS
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP

Method:

	-	Use DP to track max Set and pre index.


Stats:

	- O(n^2)
	- 

public static List<Integer> largestDivisibleSubset(int[] nums) {
	if (nums.length == 0) return new ArrayList<Integer>();

	//sort the array first
	Arrays.sort(nums);
	ArrayList<Integer>[] dp = (ArrayList<Integer>[]) new ArrayList[nums.length];	

	int maxindex = 0;
	int maxsize = -1;
	int finalindex = 0;
	int finalsize = -1;

	for (int i = 0; i < nums.length; i++) {
		dp[i] = new ArrayList<Integer>();

		// add all possible divisors from the list
		for (int j = i-1; j >= 0; j--) {

			// if nums[i] can be divided by nums[j], it can also be divided by every element in dp[j].
			// Find a previous nums[j] that has most element in
			if (nums[i] % nums[j] == 0) {
				if (dp[j].size() > maxsize) {
					maxsize = dp[j].size();
					maxindex = j;
				}
			}
		}

		// if maxsize not equal to -1, which means divisor for nums[i] is found, 
		// add the one with most element in.
		if (maxsize != -1) 
			dp[i].addAll(dp[maxindex]);

		// add nums[i] itself to dp
		dp[i].add(nums[i]);

		if (dp[i].size() > finalsize) {
			finalsize = dp[i].size();
			finalindex = i;
		}

		maxindex = 0;
		maxsize = -1;

	}
	return dp[finalindex];
}


at i = 0, dp[i] = [1]
at i = 1, dp[i] = [1, 2]
at i = 2, dp[i] = [1, 3]
at i = 3, dp[i] = [1, 2, 4]
at i = 4, dp[i] = [1, 2, 4, 8]


=======================================================================================================
method 2: DFS

Method:

	-  look for the multiply of current number.
	-  e.g. for 2,4,7,8,16,24, first check multiply of 2, found 4 is candidate, then search further 
	        for multiply of 4, and so on. 
	-  There are duplicated calculations, so use memoization to cache the result.
	-  The tricky part is initially (i==0) I set div to be 1 so that every number can start.



Stats:

	- Cause every number is searched n times, so the time complexity is O(n^2)
	- 


public class Solution {
    
    Map<Integer,List<Integer>> mem = new HashMap<>();
    

    // main function
    public List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);
        return helper(nums, 0);
    }


    private List<Integer> helper(int[] nums, int i) {
        if(mem.containsKey(i))
            return mem.get(i);

        List<Integer> maxLenLst = new ArrayList<>();

        if (i == 0)
        	div = 1;
        else
        	div = nums[i-1];

        // start from begining, check for multiples in the rest of the list
        for(int k=i; k<nums.length; k++) {
            if(nums[k] % div == 0) {

                // make a copy is crucial here, so that we won't modify the returned List reference
                List<Integer> lst = new ArrayList<>(helper(nums, k+1)); 
                lst.add(nums[k]);

                if(lst.size() > maxLenLst.size())
                    maxLenLst = lst;
            }
        }
        mem.put(i, maxLenLst);
        return maxLenLst;
    }
    
}


=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



