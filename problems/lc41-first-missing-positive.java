41. First Missing Positive - Hard

Given an unsorted integer array, find the smallest missing positive integer.

Example 1:

Input: [1,2,0]
Output: 3
Example 2:

Input: [3,4,-1,1]
Output: 2
Example 3:

Input: [7,8,9,11,12]
Output: 1

!!! Your algorithm should run in O(n) time and uses constant extra space.


******************************************************
key:
	- use constant extra space --> swap
	- how to label missing one in constant space? --> use 正负数 as label
	- edge case:
		1) 
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- swap to make space is o(n)
	- The basic idea is for any k positive numbers (duplicates allowed), the first
		missing positive number must be within [1,k+1]. The reason is like you put
		k balls into k+1 bins, there must be a bin empty, the empty bin can be viewed as the
		missing number. (pigeon hole)
	- 如果没限制空间复杂度 --> 用一个等大的数组去顺序保存这些数字。
		比如说，数组 nums [ 3 4 -1 1 8]，它的大小是 5。然后再创建一个等大的数组 a，初始化为 [ - 1，- 1，- 1，- 1，-1] 
		然后我们遍历 nums，把数字分别存到对应的位置。1 就存到数组 a 的第 1 个位置（a [ 0 ]），2 就存到数组 a 的第 2 个位
		置（a [ 1 ]），3 就存到数组 a 的第 3 个位置（a [ 2 ]）...
		nums [ 0 ] 等于 3，更新 a [ - 1，- 1，3，- 1，-1] 。
		nums [ 1 ] 等于 4，更新 a [ - 1，- 1，3，4，-1 ] 。
		nums [ 2 ] 等于 - 1，不是正数，忽略。
		nums [ 3 ] 等于 1，更新 a [ 1，- 1，3，4，-1 ] 。
		nums [ 4 ] 等于 8，我们的 a 数组只能存 1 到 5，所以同样忽略。
		最后，我们只需要遍历 a 数组，遇到第一次 a [ i ] ！= i + 1，就说明缺失了 i + 1。因为我们的 a 数组每个位置都
		存着比下标大 1 的数。

    - ！！！没有额外空间 我们直接把原数组当成 a 数组去用。 会出现的问题就是之前的数就会被覆盖掉。覆盖之前我们把它放回
      到当前数字的位置， 换句话说就是交换一下位置。然后把交换回来的数字放到应该在的位置，又交换回来的新数字继续判断，直
      到交换回来的数字小于 0，或者大于了数组的大小，或者它就是当前位置放的数字了。接着遍历 nums 的下一个数。

		nums = [ 3 4 -1 1 8 ]

		nums [ 0 ] 等于 3，把 3 放到第 3 个位置，并且把之前第 3 个位置的 -1 放回来，更新 nums [ -1， 4， 3， 1， 8 ]。

		!!! --> 然后继续判断交换回来的数字，nums [ 0 ] 等于 -1，不是正数，忽略。

		nums [ 1 ] 等于 4，把 4 放到第 4 个位置，并且把之前第 4个位置的 1 放回来，更新 nums [ -1， 1， 3， 4， 8 ]。

		然后继续判断交换回来的数字，nums [ 1 ] 等于 1，把 1 放到第 1 个位置，并且把之前第 1 个位置的 -1 放回来，
		更新 nums [ 1， -1， 3， 4， 8 ]。

		然后继续判断交换回来的数字，nums [ 1 ] 等于 -1，不是正数，忽略。

		nums [ 2 ] 等于 3，刚好在第 3 个位置，不用管。

		nums [ 3 ] 等于 4，刚好在第 4 个位置，不用管。

		nums [ 4 ] 等于 8，我们的 nums 数组只能存 1 到 5，所以同样忽略。

		最后，我们只需要遍历 nums 数组，遇到第一次 nums [ i ] ！= i + 1，就说明缺失了 i + 1。因为我们的 nums 数组
		每个位置都存着比下标大 1 的数。


stats:
	- 时间复杂度：for 循环里边套了个 while 循环，如果粗略的讲，那时间复杂度就是 O（n²），但是因为每交换一次，就有一个数字放到了
	  应该在的位置，只有 n 个数字，所以 while 里边的交换函数，最多执行 n 次。所以时间复杂度更精确的说，应该是 O（n）。
	- 空间复杂度：O（1）。
	- Runtime: 0 ms, faster than 100.00% of Java online submissions for First Missing Positive.
	- Memory Usage: 34.6 MB, less than 100.00% 

	public int firstMissingPositive(int[] nums) {
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
	            return i + 1;
	        }
	    }

	    return n + 1;
	}

	private void swap(int[] nums, int i, int j) {
	    int temp = nums[i];
	    nums[i] = nums[j];
	    nums[j] = temp;
	}



=======================================================================================================
method 2:

method:

	- 标记法
	- 我们先考虑如果可以有额外的空间该怎么做。对于 nums = [ 3 4 -1 1 8] ，我们创建一个等大的数组 a，初始化为 
		[ false，false，false，false，false ]。然后如果 nums 里有 1 就把，第一个位置 a [ 0 ] 改为 true。
		如果 nums 里有 m ，就把 a [ m - 1 ] 改为 true。看下具体的例子。
			nums = [ 3 4 -1 1 8]
			nums [ 0 ] 等于 3，更新 a [ false，false，true，false，false ]。
			nums [ 1 ] 等于 4，更新 a [ false，false，true，true，false ] 。
			nums [ 2 ] 等于 - 1，不是正数，忽略。
			nums [ 3 ] 等于 1，更新 a [ true，false，true，true，false ] 。
			nums [ 4 ] 等于 8，我们的 a 数组只能存 1 到 5，所以同样忽略。
		然后遍历数组 a ，如果 a [ i ] != true。那么，我们就返回 i + 1。因为 a [ i ] 等于 true 就意味着 i + 1 存在。

	- !!!没有额外空间，我们只能利用原来的数组 nums。同样我们直接把 nums 用作数组 a。
		但当我们更新的时候，如果直接把数组的数赋值成 true，那么原来的数字就没了。这里有个很巧妙的技巧。
		考虑到我们真正关心的只有正数。开始 a 数组的初始化是 false，所以我们把正数当做 false，负数当成 true。
		如果我们想要把 nums [ i ] 赋值成 true，如果 nums [ i ] 是正数，我们直接取相反数作为标记就行，如果是
		负数就不用管了。这样做的好处就是，遍历数字的时候，我们只需要取绝对值，就是原来的数了。
	- 当然这样又带来一个问题，我们取绝对值的话，之前的负数该怎么办？一取绝对值的话，就会造成干扰。简单粗暴些，我们把
		正数都放在前边，我们只考虑正数。负数和 0 就丢到最后，遍历的时候不去遍历就可以了。

		nums = [ 3 4 -1 1 8]

		先把所有正数放前边，并且只考虑正数。
		nums = [ 3 4 1 8 ]，正数当作 false，负数当做 true。所以 nums 就可以看成 [ false，false，false，false ]。

		nums [ 0 ] 等于 3，把第 3 个位置的数字变为负数， 更新 nums [ 3， 4， - 1， 8 ]，可以看做 [ false，false，true，false]。

		nums [ 1 ] 等于 4，把第 4 个位置的数字变为负数，更新 nums [ 3， 4， - 1， - 8 ]，可以看做 [ false，false，true，true] 。

		nums [ 2 ] 等于 - 1，取绝对值为 1，把第 1 个位置的数字变为负数，更新 nums [ - 3， 4， - 1， - 8 ]，可以看做 [ true，false，true，true] 。

		nums [ 3 ] 等于 - 8，取绝对值为 8，我们的 nums 数组只考虑 1 到 4，所以忽略。

		最后再遍历 nums，如果 nums [ i ] 大于 0，就代表缺失了 i + 1。因为正数代表 false。

		把正数移到最前边，写了两种算法，代码里注释了，大家可以参考下。



stats:
	- Runtime: 1 ms, faster than 38.69% of Java online submissions for First Missing Positive.
	- Memory Usage: 34.9 MB, less than 100.00% o

public int firstMissingPositive(int[] nums) {
    int n = nums.length;
    //将正数移到前边，并且得到正数的个数
    int k = positiveNumber(nums);
    for (int i = 0; i < k; i++) {
        //得到要标记的下标
        int index = Math.abs(nums[i]) - 1;
        if (index < k) {
            //判断要标记的位置的数是不是小于 0，不是小于 0 就取相反数
            int temp = Math.abs(nums[index]);
            nums[index] = temp < 0 ? temp : -temp;
        }
    }
    //找到第一个大于 0 的位置
    for (int i = 0; i < k; i++) {
        if (nums[i] > 0) {
            return i + 1;
        }
    }
    return k + 1;
}

private int positiveNumber(int[] nums) {
    //解法一 把负数和 0 全部交换到最后
    /*    int n = nums.length;
    for (int i = 0; i < n; i++) {
        while (nums[i] <= 0) {
            swap(nums, i, n - 1);
            n--;
            if (i == n) {
                break;
            }
        }
    }
    return n;*/

    //解法二 用一个指针 p ，保证 p 之前的都是正数。遍历 nums，每遇到一个正数就把它交换到 p 指针的位置，并且 p 指针后移
    int n = nums.length;
    int p = 0;
    for (int i = 0; i < n; i++) {
        if (nums[i] > 0) {
            swap(nums, i, p);
            p++;
        }
    }
    return p;

}

private void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}




