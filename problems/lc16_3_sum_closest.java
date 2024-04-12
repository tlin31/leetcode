16. 3Sum Closest --- Medium

Given an array nums of n integers and an integer target, find three integers in nums such that the sum is 
closest to target. Return the sum of the three integers. You may assume that each input would have exactly 
one solution.

Example:

Given array nums = [-1, 2, 1, -4], and target = 1.

The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
=========================================================================================================================================================
method 1: 3 pointers

key:
    !! remember to use high+1 when update result!

    use 3 pointers to point current element, next element and the last element. 
    If the sum is less than target, it means we have to add a larger element so next element move 
        to the next. 
    If the sum is greater, it means we have to add a smaller element so last element move to 
    the second last element. Keep doing this until the end. Each time compare the difference 
    between sum and target, if it is
    less than minimum difference so far, then replace result with it, otherwise keep
    iterating.
    
public class Solution {
    public int threeSumClosest(int[] num, int target) {
        int result = num[0] + num[1] + num[num.length - 1];
        Arrays.sort(num);
        for (int i = 0; i < num.length - 2; i++) {
            int start = i + 1, end = num.length - 1;
            while (start < end) {
                int sum = num[i] + num[start] + num[end];
                if (sum > target) {
                    end--;
                } 
                else {
                    start++;
                }

                if (Math.abs(sum - target) < Math.abs(result - target)) {
                    result = sum;
                }
            }
        }
        return result;
    }
}


public int threeSumClosest(int[] nums, int target) {
    Arrays.sort(nums);
    int closest = nums[0]+nums[1]+nums[2];
    int low,high;
    
    for(int i=0; i<nums.length-1; i++){
        low = i+1;
        high = nums.length-1;
        while(low < high){
            if(nums[low]+nums[high] == target-nums[i]) 
                return target;

            else if(nums[low]+nums[high]>target-nums[i]){
                // need a smaller value
                while(low<high && nums[low]+nums[high]>target-nums[i]) 
                    high--;

                // may went over
                if(Math.abs(nums[i]+nums[low]+nums[high+1]-target)< Math.abs(closest-target))
                    closest=nums[i]+nums[low]+nums[high+1];
            }
            else{
                while(low<high&&nums[low]+nums[high]<target-nums[i]) low++;
                if(Math.abs(nums[i]+nums[low-1]+nums[high]-target)<Math.abs(closest-target))
                    closest=nums[i]+nums[low-1]+nums[high];
            }
        }
    }
    return closest;
}






