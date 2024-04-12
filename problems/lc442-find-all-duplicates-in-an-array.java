442. Find All Duplicates in an Array - Medium


Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and 
others appear once.

Find all the elements that appear twice in this array.

Could you do it without extra space and in O(n) runtime?

Example:
Input:
[4,3,2,7,8,2,3,1]

Output:
[2,3]



******************************************************
key:
	- swap to correct position, and if already exists, return
	- similar to leetcode 41
	- edge case:
		1) empty string, return empty
		2)

******************************************************

=======================================================================================================
method 1:

Stats:

	- 
	- 


Method:

	-	
	-	


class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        int n = nums.length;
        List<Integer> res = new ArrayList<>();

	    for (int i = 0; i < n; i++) {

	        //判断交换回来的数字 --> 不在自己本来就该在的地方
	        while (nums[i] != nums[nums[i] - 1]) {

	            //第 nums[i] 个位置的下标是 nums[i] - 1
	            swap(nums, i, nums[i] - 1);
	        }
	    }
        
	    //找出 nums[i] != i + 1 的位置 --> store the duplicates
	    for (int i = 0; i < n; i++) {
	        if (nums[i] != i + 1) {
	            res.add(nums[i]);
	        }
	    }

	    return res;
    }
    


	private void swap(int[] nums, int i, int j) {
	    int temp = nums[i];
	    nums[i] = nums[j];
	    nums[j] = temp;
	}
}

=======================================================================================================
Method 2:


Stats:

	- 
	- 


Method:

	-	when find a number i, flip the number at position i-1 to negative. 
	-	if the number at position i-1 is already negative, i is the number that occurs twice.



public class Solution {
    
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; ++i) {
            int index = Math.abs(nums[i])-1;
            if (nums[index] < 0)
                res.add(Math.abs(index+1));

            nums[index] = -nums[index];
        }
        return res;
    }
}






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
The idea is we do a linear pass using the input array itself as a hash to store which numbers have 
been seen before. We do this by making elements at certain indexes negative. 


classSolution(object):
    def findDuplicates(self, nums):
        """
        :type nums: List[int]
        :rtype: List[int]
        """
        res = []
        for x in nums:
            if nums[abs(x)-1] < 0:
                res.append(abs(x))
            else:
                nums[abs(x)-1] *= -1
        return res







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

