611. Valid Triangle Number - Medium

Given an integer array nums, return the number of triplets chosen from the array that can make triangles if we take them as side lengths of a triangle.

 

Example 1:

Input: nums = [2,2,3,4]
Output: 3
Explanation: Valid combinations are: 
2,3,4 (using the first 2)
2,3,4 (using the second 2)
2,2,3
Example 2:

Input: nums = [4,2,3,4]
Output: 4
 

Constraints:

1 <= nums.length <= 1000
0 <= nums[i] <= 1000


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1: sort + 2 pointers (~ 3 sum)

Method:

Let sort nums in increasing order.
Let nums[i] be the smallest element, nums[j] be the middle element, nums[k] is the largest element 
(i < j < k). Then nums[i], nums[j], nums[k] can form a valid Triangle if and only if 
nums[i] + nums[j] > nums[k].


Stats:

	- 
	- 


class Solution {
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length, ans = 0;
        for (int k = 2; k < n; ++k) {
            int i = 0, 
                j = k - 1;

            while (i < j) {
                if (nums[i] + nums[j] > nums[k]) {
                    ans += j - i; // (i+1, j ,k),(i+2, j ,k)....(j-1,j,k)都是valid的ans
                    j--;
                } else {
                    i++;
                }
            }
        }
        return ans;
    }
}

class Solution:
    def triangleNumber(self, nums: List[int]) -> int:
        nums.sort()
        n = len(nums)
        ans = 0
        for k in range(2, n):
            i = 0
            j = k - 1
            while i < j:
                if nums[i] + nums[j] > nums[k]:
                    ans += j - i
                    j -= 1
                else:
                    i += 1
        return ans


binary search：

public class Solution {
    int binarySearch(int nums[], int l, int r, int x) {
        while (r >= l && r < nums.length) {
            int mid = (l + r) / 2;
            if (nums[mid] >= x)
                r = mid - 1;
            else
                l = mid + 1;
        }
        return l;
    }
    public int triangleNumber(int[] nums) {
        int count = 0;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            int k = i + 2;
            for (int j = i + 1; j < nums.length - 1 && nums[i] != 0; j++) {
                k = binarySearch(nums, k, nums.length - 1, nums[i] + nums[j]);
                count += k - j - 1;
            }
        }
        return count;
    }
}
