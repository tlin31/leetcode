31. Next Permutation --- Medium

Implement next permutation, which rearranges numbers into the lexicographically next greater 
permutation of numbers.

If such arrangement is not possible, it must rearrange it as the lowest possible order 
(ie, sorted in ascending order).

The replacement must be in-place and use only constant extra memory.

Here are some examples. Inputs are in the left-hand column and its corresponding outputs are 
in the right-hand column.

1,2,3 → 1,3,2
3,2,1 → 1,2,3
1,1,5 → 1,5,1

=======================================================================================================
method 1:

method: 
	- for an array:
		1. Find the largest index k such that nums[k] < nums[k + 1] . 
            If no such index exists, the permutation is sorted in descending order, just reverse it to
			ascending order and we are done.  (traverse backward )
		2. Find the largest index l greater than k such that nums[k] < nums[l]. Notice that 
			index larger than or equal to i is not possible as num[i,n-1] is reversely sorted.
		3. Swap the value of nums[k] with that of nums[l] .
		4. Reverse the sequence from nums[k + 1] up to and including the final element
			nums[nums.size() - 1] .

Stats:
    时间复杂度：最坏的情况就是遍历完所有位，O（n），倒置也是 O（n），所以总体依旧是 O（n）。
    空间复杂度：O（1）。

    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;

        //Find the largest index i such that nums[i] < nums[i + 1]
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        //如果到了最左边，就直接倒置输出
        if (i < 0) {
            reverse(nums, 0);
            return;
        }

        //Find the largest index j greater than i such that nums[i] < nums[j].
        int j = nums.length - 1;
        while (j >= 0 && nums[j] <= nums[i]) {
            j--;
        }

        swap(nums, i, j);

        // reverse from i+1 to end of array
        reverse(nums, i + 1);

    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[j];
        nums[j] = nums[i];
        nums[i] = temp;
    }

    private void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }









