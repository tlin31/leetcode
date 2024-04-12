474. Ones and Zeroes - Medium


In the computer world, use restricted resource you have to generate maximum benefit is what we always 
want to pursue.

For now, suppose you are a dominator of m 0s and n 1s respectively. On the other hand, there is an 
array with strings consisting of only 0s and 1s.


Now your task is to find the maximum number of strings that you can form with given m 0s and n 1s. 
Each 0 and 1 can be used at most once.

Note:

The given numbers of 0s and 1s will both not exceed 100
The size of given string array wont exceed 600.
 

Example 1:

Input: Array = {"10", "0001", "111001", "1", "0"}, m = 5, n = 3
Output: 4

Explanation: This are totally 4 strings can be formed by the using of 5 0s and 3 1s, which are “10,”0001”,”1”,”0”
 

Example 2:

Input: Array = {"10", "0", "1"}, m = 1, n = 1
Output: 2

Explanation: You could form "10", but then youd have nothing left. Better form "0" and "1".


******************************************************
key:
	- DP, first sort by length
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: Recursive


Stats:

	- 
	- 


Method:

	-  for each string, we count the zeroes in it by countZeroesIn(String str) and see if there are
	   enough 0s and 1s for it. If so, we accumulate that string and proceed with the remaining strings,
	   0s and 1s by means of the following code:

	- So, the main idea is, for each string, we will decide whether
		1. use remaining 0s and 1s (if there are enough of them) and count that string or
		2. do not use any 0s and 1s and skip that string entirely

public class Solution {

  public int findMaxForm(String[] strs, int m, int n) {
    return findMaxFormStartingWith(strs, m, n, 0);
  }
	
  private int findMaxFormStartingWith(String[] strs, int m, int n, int begin) {
   
    if ((begin==strs.length) || (m+n==0)) {
      return 0;
    }

    int countByAddingString = 0;
    String current = strs[begin];
    int zeroes = countZeroesIn(current);
    int ones = current.length()-zeroes;

    if (m>=zeroes && n>=ones) {
      	countByAddingString = 1 + findMaxFormStartingWith(strs, m-zeroes, n-ones, begin+1);
    }

    int countBySkippingString = findMaxFormStartingWith(strs, m, n, begin+1);

    if (countByAddingString > countBySkippingString) {
      	return countByAddingString;
    }

    return countBySkippingString;
  }
	
  private int countZeroesIn(String str) {
    int count = 0;
    for (int i=0; i<str.length(); i++) {
      if (str.charAt(i) == '0') {
        count++;
      }
    }
    return count;
  }
}





=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-  findMaxFormStartingWith(strs, m, n, begin) is called by 4 parameters. The string array is 
	   provided for practical purposes. It can be simply left out by declaring a private field for 
	   the class. The actual parameters are the remaining 0s (m), 1s (n) and current string index begin. 

	   I preferred to create a 3D integer array to store and retrieve the results of subproblems. 
	   Each dimension represents the respective values of the parameters.

		  private int[][][] dpTable;

		  public int findMaxForm(String[] strs, int m, int n) {
		    dpTable = new int[m+1][n+1][strs.length];
		    return findMaxFormStartingWith(strs, m, n, 0);
		  }

	- 2 extra operations:

		1. return the result if we have solved for these parameters before
		2. store the result for further access



	private int[][][] dpTable;

	public int findMaxForm(String[] strs, int m, int n) {
		dpTable = new int[m+1][n+1][strs.length];
	    return findMaxFormStartingWith(strs, m, n, 0);
	}

  	private int findMaxFormStartingWith(String[] strs, int m, int n, int begin) {
	    if ((begin == strs.length) || (m+n==0)) {
	      return 0;
	    }

	    // return the result if we have solved for these parameters before
	    if (dpTable[m][n][begin] > 0) {
	      return dpTable[m][n][begin];
	    }

	    int countByAddingString = 0;
	    String current = strs[begin];
	    int zeroes = countZeroesIn(current);
	    int ones = current.length()-zeroes;
	    if (m>=zeroes && n>=ones) {
	      countByAddingString = findMaxFormStartingWith(strs, m-zeroes, n-ones, begin+1)+1;
	    }

	    int countBySkippingString = findMaxFormStartingWith(strs, m, n, begin+1);

	    // store the result for further access
	    if (countByAddingString > countBySkippingString) {
	      dpTable[m][n][begin] = countByAddingString;
	    } else {
	      dpTable[m][n][begin] = countBySkippingString;
	    }
	    return dpTable[m][n][begin];
  	}


  private int countZeroesIn(String str) {
    int count = 0;
    for (int i=0; i<str.length(); i++) {
      if (str.charAt(i) == '0') {
        count++;
      }
    }
    return count;
  }


=======================================================================================================
method 3:

Stats:

	- Time complexity : O(l*m*n) Three nested loops are their, where ll is the length of strs, m 
	 					and n are the number of zeroes and ones respectively.

	- Space complexity : O(m*n) dp array of size m*n is used.



Method:

	-	
	-  The idea is to build up the solution for 0..m zeros and 0..n ones, from only knowing 1 string, 
	   2 strings, ..., up to n strings.


ex. {"10", "0", "1"}, m = 1, n = 1.

	for first string "10":
	zero = 0, one = 0
	zero = 1, one = 0
	zero = 0, one = 1
	zero = 1, one = 1, can form "10" [+1]

	continue on the second string "0", with previous knowledge of string "10":
	zero = 0, one = 0
	zero = 1, one = 0, can form "0" [+1]
	zero = 0, one = 1
	zero = 1, one = 1, can form "0" [+1] or 1 string ("10"), known from previous string

	continue on the last string "1", with previous knowledge of strings "10" and "0":
	zero = 0, one = 0
	zero = 1, one = 0, cant form "1", but we know it can form 1 string ("0") from previous set of strings
	zero = 0, one = 1, can form "1" (+1)
	zero = 1, one = 1, (can form "1" and 1 more string ("0") with zero = 1, one = 0, known from previous 
						set of strings) or (1 string ("10"), known from previous set of strings)


	Hence, at the end, we know that with zero = 1, one = 1, with string "10", "0", and "1", the maximum 
	number of strings we can form is 2.

----------
	dp[i][j] denotes the maximum number of strings that can be included in the subset given only i 0s 
	and j 1s are available.

	We traverse the given list of strings one by one. Suppose, at some point, we pick up any string 
	s_k, consisting of x zeroes and y ones. Now, choosing to put this string in any of the subset 
	possible by using the previous strings traversed so far will impact the element denoted by 
	dp[i][j] for i and j satisfying x ≤ i ≤ m, y ≤ j ≤ n. This is because for entries dp[i][j] with 
	i < x or j < y, there wont be sufficient number of 1s and 0s available to accomodate the current 
	string in any subset.

	Thus, for every string encountered, we need to appropriately update the dpdp entries within the 
	correct range.

	Further, while updating the dpdp values, if we consider choosing the current string to be a part 
	of the subset, the updated result will depend on whether it is profitable to include the current 
	string in any subset or not. If included in the subset, the dp[i][j] entry needs to be updated 
	as 
			dp[i][j] = 1+dp[i−zeroes_current_string][j−ones_current_string], 

	where the factor of +1 takes into account the number of elements in the current subset being 
	increased due to a new entry.

	But, it could be possible that the current string could be so long that it could be profitable 
	not to include it in any of the subsets. Thus, the correct equation for dpdp updation becomes:

			dp[i][j] = Math.max(1 + max[zeroLeft][oneLeft], dp[i][j])


class Solution {
	public int findMaxForm(String[] strs, int m, int n) {
		int[][] max = new int[m + 1][n + 1];
		for (int i = 0; i < strs.length; i++) {
			String str = strs[i];

	        int neededZero = 0;
	        int neededOne = 0;
	        for (int j = 0; j < str.length(); j++) {
	            if (str.charAt(j) == '0') {
	                neededZero++;
	            } else {
	                neededOne++;
	            }
	        }
	        
	        for (int zero = m; zero >= 0; zero--) {
	            for (int one = n; one >= 0; one--) {
	                if (zero >= neededZero && one >= neededOne) {
	                    int zeroLeft = zero - neededZero;
	                    int oneLeft = one - neededOne;
	                    max[zero][one] = Math.max(1 + max[zeroLeft][oneLeft], max[zero][one]);
	                } else {
	                    max[zero][one] = max[zero][one];
	                }
	            }
	        }
	    }
	    return max[m][n];
	}
}