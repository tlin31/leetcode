40. Combination Sum II - Medium

Given a collection of candidate numbers (candidates) and a target number (target), find all 
unique combinations in candidates where the candidate numbers sums to target.

Each number in candidates may only be used once in the combination.

Note:
All numbers (including target) will be positive integers.
The solution set must not contain duplicate combinations.

Example 1:
Input: candidates = [10,1,2,7,6,1,5], target = 8,
A solution set is:
[
  [1, 7],
  [1, 2, 5],
  [2, 6],
  [1, 1, 6]
]

Example 2:
Input: candidates = [2,5,2,1,2], target = 5,
A solution set is:
[
  [1,2,2],
  [5]
]


******************************************************
key:
	- back track, can only use once, 
  - dp
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1: backtrack

method:

	- 
	- 

stats:
	- Runtime: 3 ms, faster than 81.39% of Java online submissions for Combination Sum II.
	- Memory Usage: 36.9 MB, less than 100.00%



public List<List<Integer>> combinationSum2(int[] nums, int target) {
    List<List<Integer>> list = new ArrayList<>();
    Arrays.sort(nums);
    backtrack(list, new ArrayList<>(), nums, target, 0);
    return list;
    
}

private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums, 
                      int remain, int start){
    
    if(remain < 0) 
      return;
    else if(remain == 0) 
      list.add(new ArrayList<>(tempList));
    
    else{
        for(int i = start; i < nums.length; i++){

            // skip duplicates
            if(i > start && nums[i] == nums[i-1]) 
              continue; 

            //Runtime: 2 ms, faster than 99.93% of Java --> trick: early break!
            if(remain-nums[i] < 0) 
              break; 

            tempList.add(nums[i]);
            backtrack(list, tempList, nums, remain - nums[i], i + 1);
            tempList.remove(tempList.size() - 1); 
        }
    }
} 


=======================================================================================================
method 2:

method:

    - dp[i][j] = true if sum j is possible with array elements from 0 to i.

    - After filling dp[][], we recursively traverse it from dp[n-1][sum]. For cell being traversed, we   
      store path before reaching it and consider two possibilities for the element.
            1) Element is included in current path.
            2) Element is not included in current path.

    - Whenever sum becomes 0, we stop the recursive calls and print current path.

stats:

    - 
    - 


import java.util.ArrayList; 
public class SubSet_sum_problem 
{ 

    static boolean[][] dp; 
       
    static void display(ArrayList<Integer> v) 
    { 
       System.out.println(v); 
    } 
       
    // helper function
    // Vector p[] stores current subset. 
    static void printSubsetsRec(int arr[], int i, int sum,  
                                         ArrayList<Integer> p) 
    { 
        // If we reached end and sum is non-zero. We print p[] 
        // only if arr[0] is equal to sum OR dp[0][sum] is true. 
        if (i == 0 && sum != 0 && dp[0][sum]) 
        { 
            p.add(arr[i]); 
            display(p); 
            p.clear(); 
            return; 
        } 
       
        // If sum becomes 0 
        if (i == 0 && sum == 0) 
        { 
            display(p); 
            p.clear(); 
            return; 
        } 
       
        // If given sum can be achieved after ignoring current element. 
        if (dp[i-1][sum]) 
        { 
            // Create a new vector to store path 
            ArrayList<Integer> b = new ArrayList<>(); 
            b.addAll(p); 
            printSubsetsRec(arr, i-1, sum, b); 
        } 
       
        // If given sum can be achieved after considering current element. 
        if (sum >= arr[i] && dp[i-1][sum-arr[i]]) 
        { 
            p.add(arr[i]); 
            printSubsetsRec(arr, i-1, sum-arr[i], p); 
        } 
    } 
       
    // Main function!!!!
    static void printAllSubsets(int arr[], int n, int sum) 
    { 
        if (n == 0 || sum < 0) 
           return; 
       
        // Sum 0 can always be achieved with 0 elements 
        dp = new boolean[n][sum + 1]; 

        //base case:
        for (int i=0; i<n; ++i) 
        { 
            dp[i][0] = true;   
        } 
       
        // Sum arr[0] can be achieved with single element 
        if (arr[0] <= sum) 
           dp[0][arr[0]] = true; 
       
        // Fill rest of the entries in dp[][] 
        for (int i = 1; i < n; ++i) {
            for (int j = 0; j < sum + 1; ++j) {

                //can add to sum
                if (arr[i] <= j) {
                    // if previous index already can add to sum, or the exact diff = arr[i]
                    dp[i][j] = (dp[i-1][j] || dp[i-1][j-arr[i]]);
                } 
                // can't
                else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }


        if (dp[n-1][sum] == false) 
        { 
            System.out.println("There are no subsets with" + " sum "+ sum); 
            return; 
        } 
       
        // Now recursively traverse dp[][] to find all paths from dp[n-1][sum] 
        ArrayList<Integer> p = new ArrayList<>(); 
        printSubsetsRec(arr, n-1, sum, p); 
    } 
      
    //Driver Program to test above functions 
    public static void main(String args[]) 
    { 
        int arr[] = {1, 2, 3, 4, 5}; 
        int n = arr.length; 
        int sum = 10; 
        printAllSubsets(arr, n, sum); 
    } 
} 
