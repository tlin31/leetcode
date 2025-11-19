42. Trapping Rain Water - Hard

Given n non-negative integers representing an elevation map where the width of each bar is 1, 
compute how much water it is able to trap after raining.


The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units 
of rain water (blue section) are being trapped. Thanks Marcos for contributing this image!

Example:

Input: [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6

******************************************************
key:
	- reduce calculation, think about when it will update max, instead of recording all max
	- edge case:
		1) empty string, return empty
		2)

	- ref: https://leetcode.wang/leetCode-42-Trapping-Rain-Water.html
******************************************************

=======================================================================================================
method 0:

method:
	- slow, but help to get started
	- 求每一列的水，我们只需要关注当前列，以及左边最高的墙，右边最高的墙就够了。
		根据木桶效应，我们只需要看左边最高的墙和右边最高的墙中较矮的一个就够了。
		所以，根据较矮的那个墙和当前列的墙的高度可以分为三种情况。
		1. shorter wall > current wall --> water amount = shorter - current
		   也就是 2 - 1 = 1，可以存一个单位的水。
		2. shorter wall < current wall --> current wall will not have water 
		3. shorter wall == current wall --> current wall will not have water 

	- ONLY case 1 will save water

stats:
	- 时间复杂度：O（n²），遍历每一列需要 n，找出左边最高和右边最高的墙加起来刚好又是一个 n，所以是 n²。
	- 空间复杂度：O（1）

public int trap(int[] height) {
    int sum = 0;

    //最两端的列不用考虑，因为一定不会有水。所以下标从 1 到 length - 2
    for (int i = 1; i < height.length - 1; i++) {
        
        //找出左边最高
        int max_left = 0;
        for (int j = i - 1; j >= 0; j--) {
            if (height[j] > max_left) {
                max_left = height[j];
            }
        }


        //找出右边最高
        int max_right = 0;
        for (int j = i + 1; j < height.length; j++) {
            if (height[j] > max_right) {
                max_right = height[j];
            }
        }


        //找出两端较小的
        int min = Math.min(max_left, max_right);

        //只有较小的一段大于当前列的高度才会有水，其他情况不会有水
        if (min > height[i]) {
            sum = sum + (min - height[i]);
        }
    }
    return sum;
}


=======================================================================================================
method 1:

method:

	- dp
	- 对于每一列，我们求它左边最高的墙和右边最高的墙，都是重新遍历一遍所有高度，这里我们可以优化一下。
		首先用两个数组:
            max_left [ i ] 代表第 i 列左边最高的墙的高度，
            max_right [ i ] 代表第 i 列右边最高的墙的高度

            !!第 i 列左（右）边最高的墙，是不包括自身的

	- 对于 max_left: max_left [ i ] = Max ( max_left [ i - 1] , height [ i - 1]) 
		它前边的墙的左边的最高高度和它前边的墙的高度选一个较大的，就是当前列左边最高的墙了。
	- 对于 max_right:max_right[ i ] = Max ( max_right[ i + 1] , height [ i + 1]) 
		它后边的墙的右边的最高高度和它后边的墙的高度选一个较大的，就是当前列右边最高的墙了。

stats:

	- 时间复杂度：O（n）。
	- 空间复杂度：O（n），用来保存每一列左边最高的墙和右边最高的墙。
	- Runtime: 1 ms, faster than 98.31% of Java online submissions for Trapping Rain Water.
	- Memory Usage: 37.7 MB, less than 95.21%

    public int trap(int[] height) {
        int sum = 0;
        int[] max_left = new int[height.length];
        int[] max_right = new int[height.length];

        // boundries are -1 and -2 because 2 edge can't store water
        for (int i = 1; i < height.length - 1; i++) {
            max_left[i] = Math.max(max_left[i - 1], height[i - 1]);
        }

        for (int i = height.length - 2; i >= 0; i--) {
            max_right[i] = Math.max(max_right[i + 1], height[i + 1]);
        }

        for (int i = 1; i < height.length - 1; i++) {
            int min = Math.min(max_left[i], max_right[i]);
            if (min > height[i]) {
                sum += min - height[i];
            }
        }
        return sum;
    }

=======================================================================================================
method 2:

method:

	- two pointers,optimize space
	- instead of calculating area by height * width, we can think it in a
		cumulative way. In other words, sum water amount of each bin(width=1). Search
		from left to right and maintain a max height of left and right separately, which is like
		a one-side wall of partial container. Fix the higher one and flow water from the lower
		part. 

        For example, if current height of left is lower, we fill water in the left bin. Until
		left meets right, we filled the whole container.

    - similar to buy & sell stock 2 --> just want to find the different in each small piece, then add.
stats:

	- 时间复杂度： O（n）。
	- 空间复杂度： O（1）。
	- Runtime: 1 ms, faster than 98.31% of Java online submissions for Trapping Rain Water.
	- Memory Usage: 37.5 MB, less than 98.63% 

    public int trap(int[] height) {
            int n = height.length;
            int left = 0;
            int right = n - 1;
            int res = 0;
            int maxleft = 0, maxright = 0;

            while (left <= right) {

                if (height[left] <= height[right]) { //左边存水
                    
                    // update maxleft
                    if (height[left] >= maxleft) 
                        maxleft = height[left];
                    else 
                        // maxLeft高，可以再max left & current left中间存水
                        res += maxleft - height[left];

                    left++;
                } 

                else {
                    if (height[right] >= maxright) 
                        maxright = height[right];
                    else 
                        res += maxright - height[right];
                    right--;
                }
            }
            return res;
    }







=======================================================================================================
method 3:

method:

	- monotonic decreasing stack
	- 每一层算一次

思路：

    用一个单调递减栈（存索引）。
    当我们遇到一个比栈顶大的元素时，就说明可能形成了“水洼”的右边界，
    从栈中弹出“凹槽”的底部，然后计算可以积多少水。

即：

    栈保持 height 递减（相邻越压越低）

    当出现“更高的墙”，说明中间的低点可以被填水

    每次弹出的元素代表“水洼的底”


举个例子：height = [0,1,0,2,1,0,1,3,2,1,2,1]

高度 3 |               X
高度 2 |       X       X X   X
高度 1 |   X   X X   X X X X X X
高度 0 | 0 1 0 2 1 0 1 3 2 1 2 1
         
         0 1 2 3 4 5 6 7 8 9 10 11 （索引）


初始：
index: 0
value: 0
stack = [0]

i = 1, height=1（比 0 大）

能形成水洼了吗？栈顶 = 0（高度 0）,当前高度 = 1 > 0 → 可以接水, 弹出 0 作为“底部”：

left boundary = stack new top = empty → 不足以蓄水

压入 1：stack = [1]



i = 2, height=0

0 < stack.top(1)，递减 → 压栈

stack = [1,2]



i = 3, height=2（大于 0 和 1）

当前高度 = 2， 栈顶 = 2（底部，height=0）

步骤 1：弹出 2（底部）

左边界：栈弹出之后的栈顶 = 1（高度=1）
右边界：当前高度=2

可以接水！

计算水量
height = min(leftBoundary, rightBoundary) - bottom
       = min(1, 2) - 0
       = 1

width = rightIndex - leftIndex - 1
      = 3 - 1 - 1
      = 1

water = 1 * 1 = 1


累计水量 += 1

步骤 2 —— 当前 2 还是比 leftBoundary (height=1) 大

继续弹出 1？

不行，因为 1 是左边界，需要保留
→ 停止

最后将 3 入栈：

stack = [1,3]
totalWater = 1

后面继续类似操作，核心思想一致：

当 height[i] > height[stack.top()] 时：

弹出一个底部，计算：
min(左边界高度, 右边界高度) - 底部高度


乘以：

右边界索引 - 左边界索引 - 1


最终总水量 = 6


class Solution {
    public int trap(int[] height) {
        int n = height.length;
        Deque<Integer> stack = new ArrayDeque<>(); // 单调递减栈
        int water = 0;

        for (int i = 0; i < n; i++) {

            // 若当前高度 > 栈顶 → 可以接水
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {

                int bottom = stack.pop(); // 水洼的底部高度

                if (stack.isEmpty()) break; // 没左边界，不构成水洼

                int left = stack.peek();  // 左边界索引

                int h = Math.min(height[left], height[i]) - height[bottom]; // 高度
                int w = i - left - 1; // 宽度

                water += h * w;
            }

            stack.push(i);
        }

        return water;
    }
}

