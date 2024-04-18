18. 4Sum  --- Medium

Given an array nums of n integers and an integer target, are there elements a, b, c, and d 
in nums such that a + b + c + d = target? Find all unique quadruplets in the array which 
gives the sum of target.

Note:

The solution set must not contain duplicate quadruplets.

Example:

Given array nums = [1, 0, -1, 0, -2, 2], and target = 0.

A solution set is:
[
  [-1,  0, 0, 1],
  [-2, -1, 1, 2],
  [-2,  0, 0, 2]
]

Key：
- edge case: less than 4 numbers
- early fail: if smallest one *4>target or largest *4<target, return empty 







=========================================================================================================================================================
method 1:

key:

Runtime: 24 ms, faster than 52.43% of Java online submissions for 4Sum.
Memory Usage: 36.8 MB, less than 100.00%

public class Solution {
    public List < List < Integer >> fourSum(int[] num, int target) {

        ArrayList < List < Integer >> ans = new ArrayList < > ();
        if (num.length < 4) return ans;

        Arrays.sort(num);

        for (int i = 0; i < num.length - 3; i++) {

        	//ignore duplicates
            if (i > 0 && num[i] == num[i - 1]) 
            	continue;

            for (int j = i + 1; j < num.length - 2; j++) {
                if (j > i + 1 && num[j] == num[j - 1]) 
                	continue;

                int low = j + 1, high = num.length - 1;

                while (low < high) {
                    int sum = num[i] + num[j] + num[low] + num[high];
                    if (sum == target) {
                        ans.add(Arrays.asList(num[i], num[j], num[low], num[high]));

                        //ignore duplicates
                        while (low < high && num[low] == num[low + 1]) low++;
                        while (low < high && num[high] == num[high - 1]) high--;

                        low++;
                        high--;
                    } else if (sum < target) low++;
                    else high--;
                }
            }
        }
        return ans;
    }
}

=========================================================================================================================================================
method 2:

key:
	- use subfunctions for 3sum and 2sum, and keeping throwing all impossible cases. O(n^3) time 
	  complexity, O(1) extra space complexity.
	- question: how do we know before hand whether a number has the chance to be among the 4 nums?
	- important!!! put more checks on the number


// Runtime: 3 ms, faster than 100.00% of Java online submissions for 4Sum.
// Memory Usage: 36.7 MB, less than 100.00% of Java online submissions for 4Sum.

public List<List<Integer>> fourSum(int[] nums, int target) {
		ArrayList<List<Integer>> res = new ArrayList<List<Integer>>();
		int len = nums.length;
		if (nums == null || len < 4)
			return res;

		Arrays.sort(nums);

		// declare a max of the array, save time of going to thru the entire array!
		int max = nums[len - 1];

		if (4 * nums[0] > target || 4 * max < target)
			return res;

		int i, z;
		for (i = 0; i < len; i++) {
			z = nums[i];

			if (i > 0 && z == nums[i - 1])// avoid duplicate
				continue;

			if (z + 3 * max < target) // z is too small
				continue;

			if (4 * z > target) // z is too large
				break;

			if (4 * z == target) { // z is the boundary

				// if all all 4 positions = z, and 4*z = target
				if (i + 3 < len && nums[i + 3] == z)
					res.add(Arrays.asList(z, z, z, z));
				break;
			}

			// after clearing all impossible cases
			threeSumForFourSum(nums, target - z, i + 1, len - 1, res, z);
		}

		return res;
	}

	/*
	 * Find all possible distinguished three numbers adding up to the target
	 * in sorted array nums[] between indices low and high. If there are,
	 * add all of them into the ArrayList fourSumList, using
	 * fourSumList.add(Arrays.asList(z1, the three numbers))
	 */
	public void threeSumForFourSum(int[] nums, int target, int low, int high, ArrayList<List<Integer>> fourSumList,
			int z1) {
		if (low + 1 >= high)
			return;

		int max = nums[high];
		if (3 * nums[low] > target || 3 * max < target)
			return;

		int i, z;
		for (i = low; i < high - 1; i++) {
			z = nums[i];
			if (i > low && z == nums[i - 1]) // avoid duplicate
				continue;
			if (z + 2 * max < target) // z is too small
				continue;

			if (3 * z > target) // z is too large
				break;

			if (3 * z == target) { // z is the boundary
				if (i + 1 < high && nums[i + 2] == z)
					fourSumList.add(Arrays.asList(z1, z, z, z));
				break;
			}

			twoSumForFourSum(nums, target - z, i + 1, high, fourSumList, z1, z);
		}

	}

	/*
	 * Find all possible distinguished two numbers adding up to the target
	 * in sorted array nums[] between indices low and high. If there are,
	 * add all of them into the ArrayList fourSumList, using
	 * fourSumList.add(Arrays.asList(z1, z2, the two numbers))
	 */
	public void twoSumForFourSum(int[] nums, int target, int low, int high, ArrayList<List<Integer>> fourSumList,
			int z1, int z2) {

		if (low >= high)
			return;

		if (2 * nums[low] > target || 2 * nums[high] < target)
			return;

		int i = low, j = high, sum, x;
		while (i < j) {
			sum = nums[i] + nums[j];
			if (sum == target) {
				fourSumList.add(Arrays.asList(z1, z2, nums[i], nums[j]));

				x = nums[i];
				while (++i < j && x == nums[i]) // avoid duplicate
					;
				x = nums[j];
				while (i < --j && x == nums[j]) // avoid duplicate
					;
			}
			if (sum < target)
				i++;
			if (sum > target)
				j--;
		}
		return;
	}


=========================================================================================================================================================
general solution for k sum:


List<List<Integer>> kSum_Trim(int[] a, int target, int k) {
    List<List<Integer>> result = new ArrayList<>();
    if (a == null || a.length < k || k < 2) return result;
    Arrays.sort(a);
    kSum_Trim(a, target, k, 0, result, new ArrayList<>());
    return result;
}


void kSum_Trim(int[] a, int target, int k, int start, List<List<Integer>> result, List<Integer> path) {
    int max = a[a.length - 1];

    if (a[start] * k > target || max * k < target) return;
    
    if (k == 2) {                        // 2 Sum
        int left = start;
        int right = a.length - 1;
        while (left < right) {
        	
            if      (a[left] + a[right] < target) left++;
            else if (a[left] + a[right] > target) right--;
            else {
                result.add(new ArrayList<>(path));
                result.get(result.size() - 1).addAll(Arrays.asList(a[left], a[right]));
                left++; right--;
                while (left < right && a[left] == a[left - 1]) left++;
                while (left < right && a[right] == a[right + 1]) right--;
            }
        }
    }
    else {                        // k Sum
        for (int i = start; i < a.length - k + 1; i++) {
            if (i > start && a[i] == a[i - 1]) continue;
            if (a[i] + max * (k - 1) < target) continue;

            if (a[i] * k > target) break;

            if (a[i] * k == target) {
                if (a[i + k - 1] == a[i]) {
                    result.add(new ArrayList<>(path));
                    List<Integer> temp = new ArrayList<>();
                    for (int x = 0; x < k; x++) 
                    	temp.add(a[i]);

                    result.get(result.size() - 1).addAll(temp);    // Add result immediately.
                }
                break;
            }
            path.add(a[i]);
            kSum_Trim(a, target - a[i], k - 1, i + 1, result, path);
            path.remove(path.size() - 1);        // Backtracking
        }
    }
}


