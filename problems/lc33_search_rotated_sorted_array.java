33. Search in Rotated Sorted Array(binary search)

https://leetcode.com/problems/search-in-rotated-sorted-array/submissions/

Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.(i.e., [
0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
You are given a target value to search. 

If found in the array return its index, otherwise return -1.

You may assume no duplicate exists in the array.

Your algorithms runtime complexity must be in the order of O(log n).



similar question: 
    1) restore a sorted & rotated array :https://www.geeksforgeeks.org/sort-rotated-sorted-array/ use reverse 
    functions, From zero index to split index. --> From split index to end index. --> From zero index to end index.
    2) Check if an array is sorted and rotated clockwise ：https://www.geeksforgeeks.org/check-if-an-array-is-sorted-and-rotated-clockwise/ find the minimum number in the array, check all the elements
     before minimum element will be in increasing order and all elements after the minimum element will also be in 
     increasing order. also, !!!!Check if the last element of the array is smaller than the element just before the 
     minimum element.
    3) find element in a sorted matrix: compare with top right corner.
    4) find minimum in the rotated & sorted array (LeeTCODE 153) : https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/

        public int findMin(int[] nums) {
                int l = 0, r = nums.length-1, m = 0;
                while(l <= r){
                    m = (l+r)/2;
                    if(nums[m] >= nums[r]) l = m + 1;
                    else r = m;     // end loop condition
                }
                return nums[m];
            }

    Key: need to know where is the "real start" of the array

=======================================================================================================
method 1:

method:
    - 1) If nums[mid] and target are "on the same side" of nums[0], we just take nums[mid]. 
         Otherwise we use -infinity or +infinity as needed.
    - 2) If target is lets say 14, then we adjust nums to this, where "inf" means infinity: 
        [12, 13, 14, 15, 16, 17, 18, 19, inf, inf, inf, inf, inf, inf, inf, inf, inf, inf, inf, inf]
    - 3) And then we can simply do ordinary binary search.

    * 怎么判断 nums [ mid ] 和 target 在同一段？
        - 把 nums [ mid ] 和 target 同时与 nums [ 0 ] 比较，如果它俩都大于 nums [ 0 ] 或者都小于 nums [ 0 ]，
          那么就代表它俩在同一段。例如 [ 4 5 6 7 1 2 3]，如果 target = 5，此时数组看做 [ 4 5 6 7 inf inf inf ]
          nums [ mid ] = 7，target > nums [ 0 ]，nums [ mid ] > nums [ 0 ]，所以它们在同一段  
          nums [ mid ] = 7，不用变化。

    * 怎么判断 nums [ mid ] 和 target 不在同一段？
        - 把 nums [ mid ] 和 target 同时与 nums [ 0 ] 比较，如果它俩一个大于 nums [ 0 ] 一个小于 nums [ 0 ]，
          那么就代表它俩不在同一段。例如[ 4 5 6 7 1 2 3]，如果 target = 2，此时数组看做 
          [ - inf - inf - inf - inf 1 2 3]。nums [ mid ] = 7，target < nums [ 0 ]，
          nums [ mid ] > nums [ 0 ]，一个大于，一个小于，所以它们不在同一段  nums [ mid ] = - inf，变成了负无穷大。
        - if nums[0] <= target, we want to search in the range [start ~ mid], thus we want the pivot
          to be always be greater than target, thus we get to move right to mid, and search in the left
          half 

stats:
    - Runtime: 0 ms, faster than 100.00%
    - Memory Usage: 39.1 MB, less than 27.67% 
    - 时间复杂度：O（log（n））。
    - 空间复杂度：O（1）。

public int search(int[] nums, int target) {
        double inf = Double.POSITIVE_INFINITY;
        int l=0;
        int r=nums.length;
        double pivot=0;
        while (l < r) {

            int mid = (l+r)/2;

            // target and the array's mid is on the same side
            if ((nums[0] <= nums[mid]) == (nums[0] <= target))		
                pivot = nums[mid];
            else 
                pivot = nums[0] <= target ? inf : -inf;

            // regular binary search
            if (pivot < target)
                l = mid + 1;
            else if (pivot > target)
                r = mid;
            else 
                return mid;
        }
        return -1;
    }




=======================================================================================================
method 2:

method:
    - 算法基于一个事实，数组从任意位置劈开后，至少有一半是有序的，什么意思呢？
      比如 [ 4 5 6 7 1 2 3] ，从 7 劈开，左边是 [ 4 5 6 7] 右边是 [ 7 1 2 3]，左边是有序的。
    - 我们可以先找到哪一段是有序的 (只要判断端点即可)，然后看 target 在不在这一段里，如果在，那么就把另一半丢弃。
      如果不在，那么就把这一段丢弃。

stats:
    - Runtime: 0 ms, faster than 100.00% of Java online submissions for Search in Rotated Sorted Array.
    - Memory Usage: 38.9 MB, less than 32.08%
    - 时间复杂度：O（log（n））。
    - 空间复杂度：O（1）。

public int search(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {

            int mid = (start + end) / 2;

            if (target == nums[mid]) {
                return mid;
            }

            //左半段是有序的
            if (nums[start] <= nums[mid]) {
                //target 在这段里
                if (target >= nums[start] && target < nums[mid]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }

            //右半段是有序的
            } else {
                if (target > nums[mid] && target <= nums[end]) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }

        }
        return -1;
    }
