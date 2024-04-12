561. Array Partition I - Easy

Given an array of 2n integers, your task is to group these integers into n pairs of integer, 
say (a1, b1), (a2, b2), ..., (an, bn) which makes sum of min(ai, bi) for all i from 1 to n as large 
as possible.

Example 1:
Input: [1,4,3,2]

Output: 4
Explanation: n is 2, and the maximum sum of pairs is 4 = min(1, 2) + min(3, 4).
Note:
n is a positive integer, which is in the range of [1, 10000].
All the integers in the array will be in the range of [-10000, 10000].




******************************************************
key:
	- sort & group by 2
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- O(n * logn)
	- O(1)


Method:

	-	
	-	
class Solution {
    public int arrayPairSum(int[] nums) {
        Arrays.sort(nums);
        int result = 0;
        for (int i = 0; i < nums.length;){
            result +=nums[i];
            i +=2;
        }
        return result;
    }
}

=======================================================================================================
method 2:

Stats:

	- O(n)
	- O(n)


Method:

	-	
	-	


public class Solution {

	public int arrayPairSum(int[] nums) {
		int[] exist = new int[20001];

		for (int i = 0; i < nums.length; i++) {
			exist[nums[i] + 10000]++;
		}
		
		int sum = 0;
		boolean odd = true;

		for (int i = 0; i < exist.length; i++) {
			while (exist[i] > 0) {
				if (odd) {
					sum += i - 10000;
				}
				// add 1 element in each pair
				odd = !odd;
				exist[i]--;
			}
		}
		return sum;
	}
	
}

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



