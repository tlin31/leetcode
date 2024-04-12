448. Find All Numbers Disappeared in an Array - Easy


Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and 
others appear once.

Find all the elements of [1, n] inclusive that do not appear in this array.

Could you do it without extra space and in O(n) runtime? You may assume the returned list does not 
count as extra space.

Example:

Input:
[4,3,2,7,8,2,3,1]

Output:
[5,6]


******************************************************
key:
	- same as lc41
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



	class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
	    List<Integer> res = new ArrayList<>();
        
        int n = nums.length;

	    for (int i = 0; i < n; i++) {

	        //判断交换回来的数字 --> 大于0 + 不超过array size + 不在自己本来就该在的地方
	        while (nums[i] > 0 && nums[i] <= n && nums[i] != nums[nums[i] - 1]) {

	            //第 nums[i] 个位置的下标是 nums[i] - 1
	            swap(nums, i, nums[i] - 1);
	        }
	    }

	    //找出第一个 nums[i] != i + 1 的位置
	    for (int i = 0; i < n; i++) {
	        if (nums[i] != i + 1) {
	            res.add(i+1);
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







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	Because the elements in the Array are from 1 to n, so subtracting 1 will be 0 to n-1 which 
	    are the index of the array.
	Take input [4.3.2.7.8.2.3.1] as an example, by subtracting 1 it becomes [3.2.1.6.7.1.2.0] 
	For the first iteration
	when i = 0 , it marks the nums[3] as negative, the array become [4.3.2.-7.8.2.3.1].
	when i = 1, it marks the nums[2] as negative, the array become [4.3.-2.-7.8.2.3.1].
	when i = 2, it marks the nums[1] as negative, the array become [4.-3.-2.-7.8.2.3.1].
	when i = 3, it marks the nums[6] as negative, the array become [4.-3.-2.-7.8.2.-3.1].
	...
	...
	when i = 6, it marks the nums[0] as negative, the array become [-4.-3.-2.-7.8.2.-3.-1].

	For the second iteration
	find nums[4] = 8 and nums[5] = 2 which > 0;
		--> 4 and 5 are not in the index array[3.2.1.6.7.1.2.0].
	by adding 1, 5 and 6 are not in the input[4.3.2.7.8.2.3.1]
	return[5.6]



 public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> ret = new ArrayList<Integer>();
        
        for(int i = 0; i < nums.length; i++) {
            int val = Math.abs(nums[i]) - 1;
            if(nums[val] > 0) {
                nums[val] = -nums[val];
            }
        }
        
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] > 0) {
                ret.add(i+1);
            }
        }
        return ret;
    }






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

