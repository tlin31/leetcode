526. Beautiful Arrangement - Medium

Suppose you have N integers from 1 to N. We define a beautiful arrangement as an array that 
is constructed by these N numbers successfully if one of the following is true for the 
ith position (1 <= i <= N) in this array:

	1. The number at the ith position is divisible by i.
	2. i is divisible by the number at the ith position.
	 

Now given N, how many beautiful arrangements can you construct?

Example 1:

Input: 2
Output: 2

Explanation: 

The first beautiful arrangement is [1, 2]:

Number at the 1st position (i=1) is 1, and 1 is divisible by i (i=1).

Number at the 2nd position (i=2) is 2, and 2 is divisible by i (i=2).

The second beautiful arrangement is [2, 1]:

Number at the 1st position (i=1) is 2, and 2 is divisible by i (i=1).

Number at the 2nd position (i=2) is 1, and i (i=2) is divisible by 1.
 

Note:

N is a positive integer and will not exceed 15.



******************************************************
key:
	- backtrack
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	Backtrack
	-	

Stats:

	- Time complexity : O(k). k refers to the number of valid permutations.

	- Space complexity : O(n). visitedvisited array of size nn is used. The depth of recursion 
	   					 tree will also go upto n. Here, nn refers to the given integer n.

public class Solution {
    int count = 0;
    
    public int countArrangement(int N) {
        if (N == 0) return 0;
        helper(N, 1, new int[N + 1]);
        return count;
    }
    
    private void helper(int N, int pos, int[] used) {
        if (pos > N) {
            count++;
            return;
        }
        
        for (int i = 1; i <= N; i++) {
            if (used[i] == 0 && (i % pos == 0 || pos % i == 0)) {
                used[i] = 1;
                helper(N, pos + 1, used);
                used[i] = 0;
            }
        }
    }
}



=======================================================================================================
method 2:

Method:

	-	I think we could use memorization to improve the backtracking since there are many 
	    overlap subproblems. For example, N = 10, index 1 choose 4 and 2 choose 8 vs 
	    1 choose 8 and 2 choose 4, the left subproblems are overlaped.

	-	


Stats:

	- 
	- 


    public int countArrangement(int N) {
        char[] currState = new char[N + 1];
        Arrays.fill(currState, 'f');  // f means not selected, t means selected
        return helper(new HashMap<String, Integer>(), currState, 1);
    }
    
    public int helper(Map<String, Integer> map, char[] currState, int index) {
        if(index == currState.length) return 1;
        String key = String.valueOf(currState);
        if(map.containsKey(key)) return map.get(key);
        int count = 0;
        for(int i = 1; i < currState.length; i++) {
            if(currState[i] == 'f' && (i % index == 0 || index % i == 0)) {
                currState[i] = 't';
                count += helper(map, currState, index + 1);
                currState[i] = 'f';
            }
        }
        map.put(key, count);
        return count;
    }

=======================================================================================================
method 3:

Method:

	-	The back tracking start from the back so that each search won't go too deep before it 
	    fails because smaller numbers have higher chance to be divisible among themselves.
	-	Also I don't use "visited" boolean array but use swap of an array of 1~N to avoid duplication.



Stats:

	- 
	- 



    private int count = 0;
    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    private void helper(int[] nums, int start) {
        if (start == 0) {
            count++;
            return;
        }
        for (int i = start; i > 0; i--) {
            swap(nums, start, i);
            if (nums[start] % start == 0 || start % nums[start] == 0) 
            	helper(nums, start-1);
            swap(nums,i, start);
        }
    }
    public int countArrangement(int N) {
        if (N == 0) return 0;
        int[] nums = new int[N+1];
        for (int i = 0; i <= N; i++) nums[i] = i;
        helper(nums, N);
        return count;
    }
