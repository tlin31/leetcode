39. Combination Sum - Medium

Given a set of candidate numbers (candidates) (without duplicates) and a target number (target), 
find all unique combinations in candidates where the candidate numbers sums to target.

The same repeated number may be chosen from candidates unlimited number of times.

Note:

All numbers (including target) will be positive integers.
The solution set must not contain duplicate combinations.
Example 1:

Input: candidates = [2,3,6,7], target = 7,
A solution set is:
[
  [7],
  [2,2,3]
]
Example 2:

Input: candidates = [2,3,5], target = 8,
A solution set is:
[
  [2,2,2,2],
  [2,3,3],
  [3,5]
]

******************************************************
key:
	- back tracking!!
	- use search, allow duplicates
	- edge case:
		1) empty candidates --> return []
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- 就是先向前列举所有情况，得到一个解或者走不通的时候就回溯。和37题有异曲同工之处，也算是回溯法很典型的应用
	- 一般的架构就是一个大的 for 循环，然后先 add，接着利用递归进行向前遍历，然后再 remove ，继续循环。

stats:
	- Runtime: 4 ms, faster than 66.43% of Java online submissions for Combination Sum.
	- Memory Usage: 37.5 MB, less than 100.00% o

	public class Solution {
	    public List<List<Integer>> combinationSum(int[] candidates, int target) {
	        List<List<Integer>> ans = new ArrayList<>();
	        backtrack(ans, new ArrayList<Integer>(), candidates, target, 0);
	        return ans;
	    }
	    
	    private void backtrack(List<List<Integer>> ans, List<Integer> list, int[] cand, int remain, int from) {
	        // not a solution, by adding the last element, new > target
	        if (remain < 0) { 
	        	return; 
	        }

	        //working solution
	        if (remain == 0) { 
	        	ans.add(new ArrayList<Integer>(list)); 
	        	return; 
	        }

	        for (int i=from; i<cand.length; ++i) {  
	            list.add(cand[i]);
	            backtrack(ans, list, cand, remain-cand[i], i);
	            list.remove(list.size()-1);
	        }
	    }
	}



=======================================================================================================
method 2:

method:

	- dp
	- similar to coins/knapsack problem - to store the result for all i < target and create the 
		solution from them. For that for each t from 1 to our target we try every candidate which 
		is less or equal to t in ascending order. 

		For each candidate "c" we run through all combinations for target t-c starting with the 
		value greater or equal than c to avoid duplicates and store only ordered combinations.

stats:

	- Runtime: 7 ms, faster than 19.90% of Java online submissions for Combination Sum.
	- Memory Usage: 36.8 MB, less than 100.00%


 public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // sort candidates to try them in asc order
        Arrays.sort(candidates); 

        // dp[t] stores all combinations that add up to t
        List<List<Integer>>[] dp = new ArrayList[target+1];
        
        
        // build up dp
        for(int t=0; t<=target; t++) {
            // initialize
            dp[t] = new ArrayList();
            // initialize
            List<List<Integer>> combList = new ArrayList();
            
            // for each t, find possible combinations
            for(int j=0; j<candidates.length && candidates[j] <= t; j++) {

            	//刚开始 ans_sub 的大小是 0，所以单独考虑一下这种情况
                if(candidates[j] == t) {
                    combList.add(Arrays.asList(candidates[j])); // itself can form a list
                } 
                else {
                    for(List<Integer> prevlist: dp[t-candidates[j]]) { 

                        // only add to list when the candidates[j] >= the last element
                        // so the list remains ascending order, can prevent duplicate 
                        // (ex. has [2 3 3], no [3 2 3])
                        // equal is needed since we can choose the same element many times   
                        if(candidates[j] >= prevlist.get(prevlist.size()-1)){
                            List temp = new ArrayList(prevlist); // temp is needed since 
                            temp.add(candidates[j]); // cannot edit prevlist inside 4 each looop
                            combList.add(temp);
                        }
                    }
                }
            }
            // update
            dp[t] = combList;
        }
        return dp[target];
    }    




