163. Missing Ranges - Medium

Given a sorted integer array nums, where the range of elements are in the inclusive range [lower, upper], 
return its missing ranges.

Example:

Input: nums = [0, 1, 3, 50, 75], lower = 0 and upper = 99,
Output: ["2", "4->49", "51->74", "76->99"]

******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- Find the range between lower and first element in the array.
	- Find ranges between adjacent elements in the array.
	- Find the range between upper and last element in the array.
	       1) 两个数可以连起来 --> [xxx, 3,4, xxx]
              Then do not create missing range
           2)  

stats:

	- 
	- 


 public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> result = new ArrayList<>();

        //edge case
        if (nums == null || nums.length == 0){
            result.add(formRange(lower,upper));
            return result;
        }

        // 1st step
        if (nums[0] > lower){
            result.add(formRange(lower,nums[0]-1));
        }

        // 2nd step
        for (int i = 0; i < nums.length-1; i++){

            if (nums[i+1] != nums[i] && nums[i+1] > nums[i] +1) {
                result.add(formRange(nums[i]+1, nums[i+1]-1));
            }
        }

       // 3rd step
        if (nums[nums.length-1] < upper){
            result.add(formRange(nums[nums.length-1]+1, upper));
        }
        return result;
    }
    
    public String formRange(int low, int high){
    	String result = "";
    	if (low == high) {
    		result = String.valueOf(low);
    	} else {
    		result = low + "->" + high;
    	}
        return result;
    }
}


=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

public class Solution {
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> list = new ArrayList<String>();
        for(int n : nums){
            int justBelow = n - 1;
            if(lower == justBelow) list.add(lower+"");
            else if(lower < justBelow) list.add(lower + "->" + justBelow);
            lower = n+1;
        }
        if(lower == upper) list.add(lower+"");
        else if(lower < upper) list.add(lower + "->" + upper);
        return list;
    }
}
=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 


public class Solution {
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> res = new ArrayList<>();
        for(int i : nums) {
            if(i > lower) res.add(lower+((i-1 > lower)?"->"+(i-1):""));
            if(i == upper) return res; // Avoid overflow
            lower = i+1;
        }
        if(lower <= upper) res.add(lower + ((upper > lower)?"->"+(upper):""));
        return res;
    }
}
