679. 24 Game - Hard

You have 4 cards each containing a number from 1 to 9. You need to judge whether they could 
operated through *, /, +, -, (, ) to get the value of 24.

Example 1:
Input: [4, 1, 8, 7]
Output: True
Explanation: (8-4) * (7-1) = 24

Example 2:
Input: [1, 2, 1, 2]
Output: False

Note:
The division operator / represents real division, not integer division. For example, 4 / (1 - 2/3) = 12.
Every operation done is between two numbers. In particular, we cannot use - as a unary operator. For example, with [1, 1, 1, 1] as input, the expression -1 - 1 - 1 - 1 is not allowed.
You cannot concatenate numbers together. For example, if the input is [1, 2, 1, 2], we cannot write this as 12 + 12.


******************************************************
key:
	- backtrack, store newly calculated result & mark in a bool visited array for (already calculated) 
	- edge case:
		1) only 1 number in input, test == 24
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- backtrack
	- There are only 4 cards and only 4 operations that can be performed. 
	  Even when all operations do not commute, that gives us an upper bound of 12 * 6 * 2 * 4 * 4 * 4 = 
	  9216 possibilities, which makes it feasible to just try them all. 
	- Specifically, we choose 2 numbers (with order) in 12 ways and perform one of 4 operations 
	  (12 * 4). Then, with 3 remaining numbers, we choose 2 of them and perform one of 4 operations 
	  (6 * 4). Finally we have two numbers left and make a final choice of 2 * 4 possibilities.



stats:
	- Time Complexity: O(1). There is a hard limit of 9216 possibilities, 
	   				   we do O(1) work for each of them.

	- Space Complexity: O(1). Our intermediate arrays are at most 4 elements, and the number 
						made is bounded by an O(1) factor.

	- Runtime: 3 ms, faster than 84.83% of Java online submissions for 24 Game.
	- Memory Usage: 41.4 MB, less than 6.25% of Java online submissions for 24 Game.



class Solution {
    public boolean judgePoint24(int[] nums) {
        ArrayList A = new ArrayList<Double>();
        for (int v: nums) A.add((double) v);
        return solve(A);
    }
    private boolean solve(ArrayList<Double> nums) {
        if (nums.size() == 0) return false;
        if (nums.size() == 1) return Math.abs(nums.get(0) - 24) < 1e-6;

        for (int i = 0; i < nums.size(); i++) {
            for (int j = 0; j < nums.size(); j++) {
                if (i != j) {
                    ArrayList<Double> nums2 = new ArrayList<Double>();
                    for (int k = 0; k < nums.size(); k++) if (k != i && k != j) {
                        nums2.add(nums.get(k));
                    }
                    for (int k = 0; k < 4; k++) {
                        if (k < 2 && j > i) continue;
                        if (k == 0) nums2.add(nums.get(i) + nums.get(j));
                        if (k == 1) nums2.add(nums.get(i) * nums.get(j));
                        if (k == 2) nums2.add(nums.get(i) - nums.get(j));
                        if (k == 3) {
                            if (nums.get(j) != 0) {
                                nums2.add(nums.get(i) / nums.get(j));
                            } else {
                                continue;
                            }
                        }
                        if (solve(nums2)) return true;
                        nums2.remove(nums2.size() - 1);
                    }
                }
            }
        }
        return false;
    }
}

=======================================================================================================
method 2:

method:

	- there are limited number of combinations for cards and operators (+-*/()). 
	- One idea is to search among all the possible combinations. This is what backtracking does.
	- Note that the brackets () play no role in this question. Say, parentheses give some operators 
	  a higher priority to be computed. However, the following algorithm has already considered 
	  priorities, thus it is of no use to take parentheses into account anymore.

	- arr contains value for cards， vis[i] indicates whether arr[i] has been used or not. 
	  Every time select 2 un-used cards arr[i] and arr[j]. Calculate the answer for arr[i] and arr[j] 
	  with some operator, update arr[i] with this new value and mark arr[j] as visited. 

	  Now we have 1 less card available. Note that we should use that new value (new arr[i]) in the
	  future, thus we should NOT mark arr[i] as visited. 

	  When there is no card available, check whether the answer is 24 or not.

	- Since each time after we select 2 cards arr[i] and arr[j], we just do the calculation without
	  considering the priorities for operators we use, we could think that we have already added a 
	  pair of () for arr[i] OPERATOR arr[j]. 
	  This contains all the possible considerations, thus we do not need to consider parentheses anymore.


stats:

	- Runtime: 0 ms, faster than 100.00% of Java online submissions for 24 Game.
	- Memory Usage: 37.1 MB, less than 100.00% of Java online submissions for 24 Game.


public boolean judgePoint24(int[] nums) {
        boolean[] vis = new boolean[4];
        double[] arr = new double[4];
        for (int i=0; i<4; i++) 
        	arr[i] = 1.0 * nums[i];
        return backtrack(arr, vis, 4);
    }
    
    boolean backtrack(double[] arr, boolean[] vis, int available) {
        double prev = 0;

        // exit condition
        if (available == 1) {
            for (int i=0; i<arr.length; i++) 
                if (!vis[i]) 
                	return Math.abs(arr[i]-24) < 0.000001 ? true : false;
        } 

        for (int i=0; i < arr.length; i++) {
            if (vis[i]) 
            	continue;

            // prev is for backtracking
            prev = arr[i];

            // check all combinations
            for (int j=i+1; j<arr.length; j++) {
                // calculate i & j
                if (vis[j]) 
                	continue;

                vis[j] = true;
                
                // add
                arr[i] = prev + arr[j];
                if (backtrack(arr, vis, available-1)) return true;
                
                // minus
                arr[i] = prev - arr[j];
                if (backtrack(arr, vis, available-1)) return true;

                arr[i] = -prev + arr[j];
                if (backtrack(arr, vis, available-1)) return true;
                
                // multiply
                arr[i] = prev * arr[j];
                if (backtrack(arr, vis, available-1)) return true;
                
                // division
                arr[i] = prev / arr[j];
                if (backtrack(arr, vis, available-1)) return true;

                arr[i] = arr[j] / prev;
                if (backtrack(arr, vis, available-1)) return true;
                
                vis[j] = false;
            }

            // recover arr[i]
            arr[i] = prev;
        }
        return false;
    }

=======================================================================================================
method 3:

method:

	- dfs
	- 

stats:

	- Runtime: 6 ms, faster than 54.42% of Java online submissions for 24 Game.
	- Memory Usage: 41.2 MB, less than 6.25% 


public boolean judgePoint24(int[] nums) {
        List<Double> list = new ArrayList<>();
        for (int i : nums) {
            list.add((double) i);
        }
        return dfs(list);
    }

    private boolean dfs(List<Double> list) {
        if (list.size() == 1) {
            if (Math.abs(list.get(0)- 24.0) < 0.001) {
                return true;
            }
            return false;
        }

        for(int i = 0; i < list.size(); i++) {
            for(int j = i + 1; j < list.size(); j++) {
                for (double c : generatePossibleResults(list.get(i), list.get(j))) {
                    List<Double> nextRound = new ArrayList<>();
                    nextRound.add(c);
                    for(int k = 0; k < list.size(); k++) {
                        if(k == j || k == i) continue;
                        nextRound.add(list.get(k));
                    }
                    if(dfs(nextRound)) return true;
                }
            }
        }
        return false;

    }

    private List<Double> generatePossibleResults(double a, double b) {
        List<Double> res = new ArrayList<>();
        res.add(a + b);
        res.add(a - b);
        res.add(b - a);
        res.add(a * b);
        res.add(a / b);
        res.add(b / a);
        return res;
    }




