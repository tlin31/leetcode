45. Jump Game II - Hard

Given an array of non-negative integers, you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Your goal is to reach the last index in the minimum number of jumps.

Example:

Input: [2,3,1,1,4]
Output: 2
Explanation: The minimum number of jumps to reach the last index is 2.
    Jump 1 step from index 0 to 1, then 3 steps to the last index.

Note:
You can assume that you can always reach the last index.

******************************************************
key:
	- bfs search --> use a queue 
		ex. layer 1 = 2, layer 2 = 3,1, layer 3 = 1,1,4
	- greedy search
		maxPosition = Math.max(maxPosition, nums[i] + i);
	- go from back to front
	- edge case:
		1) if there is a zero, then ignore it, proceed with next num
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- greedy algo
	- 这里要注意一个细节，就是 for 循环中，i < nums.length - 1，少了末尾。因为开始的时候边界是
	  	第 0 个位置，steps 已经加 1 了。如下图，如果最后一步刚好跳到了末尾，此时 steps 其实不用加 1 了。
	  	如果是 i < nums.length，i 遍历到最后的时候，会进入 if 语句中，steps 会多加 1 。
	- 写代码的话，我们用 end 表示当前能跳的边界，对于上边第一个图的橙色 1，第二个图中就是橙色的 4，
		遍历数组的时候，到了边界，我们就重新更新新的边界。

stats:

	- Runtime: 1 ms, faster than 100.00% of Java online submissions for Jump Game II.
	- Memory Usage: 38.7 MB, less than 100.00% 
	- 时间复杂度：O（n）
	- 空间复杂度：O（1）


	public int jump(int[] nums) {
	    int end = 0;
	    int maxPosition = 0; 
	    int steps = 0;
	    for(int i = 0; i < nums.length - 1; i++){
	        //找能跳的最远的
	        maxPosition = Math.max(maxPosition, nums[i] + i); 

	        //遇到边界，就更新边界，并且步数加一 --> 因为loop thru all elem in nums
	        if( i == end){ 
	            end = maxPosition;
	            steps++;
	        }
	    }
	    return steps;
	}





=======================================================================================================
method 2:

method:

	- go from back to front, now the max reach = farthest elem, not the max anymore.
	- end when reach position 0
	- why this is easier --> take [2,3,1,1,4]
		从左到右跳的话，2 -> 3 -> 4 -> 1。
		从右到左的话，我们找能跳到 1 的最左边的位置，我们找的只能是 4 或者是 4 左边的。
		找到 4 的话，不用说，刚好完美。
		如果是中间范围 3 和 4 之间的第 2 个  1 变成了 3，那么这个位置也可以跳到末尾的 1，
			按我们的算法我们就找到了这个 3，也就是 4 左边的位置。但其实并不影响我们的 steps，
			因为这个数字是 3 到 4 中间范围的数，左边界 3 也可以到这个数，所以下次找的话，会找到边界3,
			或者边界 3 左边的数。 会不会直接找到 上个边界 2 呢？不会的，如果找到了上一个边界 2，那么
			意味着从 2 直接跳到 3 和 4 之间的那个数，再从这个数跳到末尾就只需 2 步了，但是其实是需要 3 步的。


stats:
	- Runtime: 108 ms, faster than 28.24% of Java online submissions for Jump Game II.
	- Memory Usage: 36.6 MB, less than 100.00%
	- 时间复杂度：O（n²），因为最坏的情况比如 1 1 1 1 1 1，position 会从 5 更新到 0 ，并且每次更新都会 for 循环。
	- 空间复杂度：O（1）。

	public int jump(int[] nums) {
	    int position = nums.length - 1; //要找的位置
	    int steps = 0;
	    while (position != 0) { //是否到了第 0 个位置
	        for (int i = 0; i < position; i++) {

	        	//!!! 
	            if (nums[i] >= position - i) {
	                position = i; //更新要找的位置
	                steps++;
	                break;
	            }
	        }
	    }
	    return steps;
	}


Input: [2,3,1,1,4]
Output: 2
position = 4, steps = 0

i = 0 --> skip if loop, i++
i = 1 --> (nums[1] = 3 >= 4-1), go into for loop, positino = 1, steps ++ = 1, break ?????

=======================================================================================================
method 3:

method:

	- use bfs/queue
	- 

stats:

	- 
	- 

	queue.add(0)
	int count = 0
	while (q != empty) {
		int reach = input[queue.head]
		for (int i = 0; i < reach; i++) {
			queue.add(queue.head + i);
			if (queue.last = input.length-1) return count;
		}
		count++;

	}

