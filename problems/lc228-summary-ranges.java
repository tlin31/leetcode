228. Summary Ranges - Medium-->easy


Given a sorted integer array without duplicates, return the summary of its ranges.

Example 1:

Input:  [0,1,2,4,5,7]
Output: ["0->2","4->5","7"]
Explanation: 0,1,2 form a continuous range; 4,5 form a continuous range.
Example 2:

Input:  [0,2,3,4,6,8,9]
Output: ["0","2->4","6","8->9"]
Explanation: 2,3,4 form a continuous range; 8,9 form a continuous range.


******************************************************
key:
	- 2 pointers
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	-	
	-	


public class Solution {
    public List<String> summaryRanges(int[] nums) {
        List<String> summary = new ArrayList<>();
        for (int i, j = 0; j < nums.length; ++j){
            i = j;
            // try to extend the end
            while (j + 1 < nums.length && nums[j + 1] == nums[j] + 1)
                ++j;

            // put the range into the list
            if (i == j)
                summary.add(nums[i] + "");
            else
                summary.add(nums[i] + "->" + nums[j]);
        }
        return summary;
    }
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


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

