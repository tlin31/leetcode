216. Combination Sum III - Medium

Find all possible combinations of k numbers that add up to a number n, given that only numbers 
from 1 to 9 can be used and each combination should be a unique set of numbers.

Note:

All numbers will be positive integers.
The solution set must not contain duplicate combinations.

Example 1:
Input: k = 3, n = 7
Output: [[1,2,4]]


Example 2:
Input: k = 3, n = 9
Output: [[1,2,6], [1,3,5], [2,3,4]]



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

	-	can only be numbers < n, can start from the back
	-	

Stats:

	- 
	- 

 public List<List<Integer>> combinationSum3(int k, int target) {
    List<List<Integer>> ans = new ArrayList<>();
    combination(ans, new ArrayList<Integer>(), k, 1, target);
    return ans;
}

private void combination(List<List<Integer>> ans, List<Integer> comb, int k,  int start, int target) {
	if(comb.size() > k) 
		return; //no solution

	else if (comb.size() == k && target == 0) {
		List<Integer> newResult = new ArrayList<Integer>(comb);
		ans.add(newResult);
		return;
	}
	
	else {
		for (int i = start; i <= 9; i++) {
			comb.add(i);
			combination(ans, comb, k, i+1, target-i);
			comb.remove(comb.size() - 1);
		}
	}
}

=======================================================================================================
method 2:

Method:

	-	
	-	


Stats:

	- 
	- 

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



