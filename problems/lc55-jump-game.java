55. Jump Game - Medium

Given an array of non-negative integers, you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Determine if you are able to reach the last index.

Example 1:
Input: [2,3,1,1,4]
Output: true
Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.

Example 2:
Input: [3,2,1,0,4]
Output: false
Explanation: You will always arrive at index 3 no matter what. Its maximum
             jump length is 0, which makes it impossible to reach the last index.

******************************************************
key:
	- greedy
	- dfs
	- similar to #45
	- edge case:
		1) if = 0; just ignores it
		2) empty array, return true

******************************************************



=======================================================================================================

Simplest O(N) solution with constant space

Idea is to work backwards from the last index. Keep track of the smallest index that can 
"jump" to the last index. Check whether the current index can jump to this smallest index.

For every index, I'm checking the max reach I can have till that element, if that reach is less 
than the value of my index, that means I can never reach this particular index and my answer should 
be false.


bool canJump(int A[], int n) {
    int last=n-1,i,j;
    for(i=n-2;i>=0;i--){
        if(i+A[i]>=last)
        	last=i;
    }
    return last<=0;
}

=======================================================================================================
method 1:

method:

	- 顺藤摸瓜
	- 

stats:

	- 时间复杂度：O（n）
	- 空间复杂度：O（1）


class Solution {
    public boolean canJump(int[] nums) {
       int reachable = 0;
       for(int i = 0; i < nums.length; i ++) {
           if(i > reachable) 
           		return false;
           reachable = Math.max(reachable, i + nums[i]);
       } 
       return true;
    }
}


	public boolean canJump(int[] nums) { 
	    int end = 0;
	    int maxPosition = 0;  
	    for(int i = 0; i < nums.length - 1; i++){

	    	//提前end for loop
	        //当前更新超过了边界，那么意味着出现了 0 ，直接返回 false
	        if(end < i){
	            return false;
	        }
	        //找能跳的最远的 
	        maxPosition = Math.max(maxPosition, nums[i] + i); 

	        if( i == end){ //遇到边界，就更新边界，并且步数加一
	            end = maxPosition; 
	        }
	    }
	    //最远的距离是否到答末尾
	    return maxPosition>=nums.length-1;
	}




=======================================================================================================
method 2:

method:

	- 这里修改的话，只需要判断最后回没回到 0 ，并且如果 while 里的 for 循环没有进入 if ，就意味着一个位置都没找到，
		就要返回 false。
	- 从后面往前

stats:
	- 时间复杂度：O（n²）。
	- 空间复杂度：O（1）。

	public boolean canJump(int[] nums) { 
	    int position = nums.length - 1; //要找的位置
	    boolean isUpdate = false; 
	    while (position != 0) { //是否到了第 0 个位置
	        isUpdate = false;
	        for (int i = 0; i < position; i++) {
	            if (nums[i] >= position - i) {
	                position = i; //更新要找的位置 
	                isUpdate = true;
	                break;
	            }
	        }
	        //如果没有进入 for 循环中的 if 语句，就返回 false
	        if(!isUpdate){
	            return false;
	        }
	    }
	    return true;
	}

OR :
bool canJump(int A[], int n) {
    int last = n - 1, i, j;
    for (i = n - 2; i >= 0; i--) {
        if (i + A[i] >= last) last = i;
    }
    return last <= 0;
}

=======================================================================================================
method 3:

method:

	- 让我们直击问题的本质，与 45 题不同，我们并不需要知道最小的步数，所以我们对跳的过程并不感兴趣。并且如果数组
		里边没有 0，那么无论怎么跳，一定可以从第 0 个跳到最后一个位置。
	- 所以我们只需要看 0 的位置，如果有 0 的话，我们只需要看 0 前边的位置，能不能跳过当前的 0 ，如果 0 前边的
		位置都不能跳过当前 0，那么直接返回 false。如果能的话，就看后边的 0 的情况。


stats:

	- 时间复杂度：O（n）。
	- 空间复杂度：O（1）。
	- Runtime: 1 ms, faster than 99.26% of Java online submissions for Jump Game.
	- Memory Usage: 37.5 MB, less than 100.00% 

public boolean canJump(int[] nums) {
    for (int i = 0; i < nums.length - 1; i++) {
        //for loop只用来找到 0 的位置
        if (nums[i] == 0) {
            int j = i - 1;
            boolean isCanSkipZero = false;
            while (j >= 0) {
                //判断 0 前边的元素能否跳过 0 
                if (j + nums[j] > i) {
                    isCanSkipZero = true;
                    break;
                }
                j--;
            }
            if (!isCanSkipZero) {
                return false;
            }
        }
    }
    return true;
}

但这样时间复杂度没有提高， 在 @Zhengwen 的提醒下，可以用下边的方法。
我们判断 0 前边的元素能否跳过 0 ，不需要每次都向前查找，我们只需要用一个变量保存当前能跳的最远的距离，
然后判断最远距离和当前 0 的位置就可以了。


public boolean canJump(int[] nums) {
    int max = 0;
    for (int i = 0; i < nums.length - 1; i++) {

    	// see if can pass the 0's
        if (nums[i] == 0 && i >= max) {
            return false;
        }
        max = Math.max(max, nums[i] + i);
    }
    return true;
}


参考这里，我们甚至不需要考虑 0 的位置，只需要判断最大距离有没有超过当前的 i 。


public boolean canJump(int[] nums) {
    int max = 0; 
    for (int i = 0; i < nums.length; i++) {
        if (i > max) {
            return false;
        }
        max = Math.max(max, nums[i] + i);
    }
    return true;
}



