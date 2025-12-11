239. Sliding Window Maximum - Hard

Given an array nums, there is a sliding window of size k which is moving from the very left of 
the array to the very right. You can only see the k numbers in the window. Each time the sliding 
window moves right by one position. Return the max number of these sliding window.

Example:

Input: nums = [1,3,-1,-3,5,3,6,7], and k = 3
Output: [3,3,5,5,6,7] 
Explanation: 

Window position                Max
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
Note:
You may assume k is always valid, 1 ≤ k ≤ input arrays size for non-empty array.

Follow up:
Could you solve it in linear time?

******************************************************
key:
	- monotonic queue, 不是heap！
	- DP
	- edge case:
		1) k = 0 or k = 1
		2)

******************************************************


🎯 最优思路：单调队列 Monotonic Queue

时间复杂度 O(N)
因为每个元素最多进队一次、出队一次。

核心性质：

	使用一个 递减单调队列（Deque）

	队首 always 是当前窗口最大值

	当滑动窗口移动时：

	把不在窗口范围的值弹出队首

	把所有小于当前值的元素从队尾删掉，保持递减性


class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) return new int[0];

        int n = nums.length;
        int[] res = new int[n - k + 1];

        Deque<Integer> deque = new LinkedList<>();//存index

        for (int i = 0; i < n; i++) {

            // ① 移除不在窗口范围内的元素（i-k）
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }

            // ② 删除队列中所有小于当前 nums[i] 的元素
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // ③ 将当前元素下标加入队尾
            deque.offerLast(i);

            // ④ 当窗口大小达到 k 时，记录窗口最大值
            if (i >= k - 1) {
                res[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return res;
    }
}


ex. [1,3,-1,-3,5,3,6,7]

i=0: [1]
i=1: [3]       // 1 < 3, so 1 removed
i=2: [3, -1]
max = 3

i=3: [3, -1, -3]
max = 3

i=4: [5]       // 3,-1,-3 都比 5 小
max = 5

i=5: [5, 3]
max = 5

i=6: [6]       // 5,3 都比 6 小
max = 6

i=7: [7]
max = 7

输出：
[3,3,5,5,6,7]


为什么不能排序？

	窗口滑动一次要 O(k log k)，整体 O(n k log k)，太慢。

为什么不能用大顶堆？

	堆删除窗口外元素难，且每次最大值是 lazy deletion，平均 O(log k)，整体 O(n log k)，也不是最优。

单调队列为什么是 O(n)?

	每个元素最多入队 1 次, 最多出队 1 次, 总操作 ≤ 2n。




=======================================================================================================
method 1:

method:

	- Deque
	- in a maximum heap, heap[0] is always the largest element. Though to add an element in a heap of 
		size k costs log(k), that means O(n * logk) time complexity 
	- use a deque (double-ended queue), the structure which pops from / pushes to either side with the 
		same O(1) performance.

	- store the deque indexes instead of elements since both are used during an array parsing.

Algorithm:
	- Process the first k elements separately to initiate the deque.
		Iterate over the array, at each step :
    			Clean the deque :
        				1. Keep only the indexes of elements from the current sliding window.
        				2. Remove indexes of all elements smaller than the current one, since they 
        				   will not be the maximum ones.
    			Append the current element to the deque.
    			Append deque[0] to the output.
		Return the output array.
	- 

stats:

	- Runtime: 9 ms, faster than 83.13% of Java online submissions for Sliding Window Maximum.
	- Memory Usage: 40.6 MB, less than 93.75% 
	- O(n)


public int[] maxSlidingWindow(int[] inputNum, int k) {		
		if (inputNum == null || k <= 0) {
			return new int[0];
		}

		int[] res = new int[inputNum.length-k+1];
		int ri = 0;

		// store index
		Deque<Integer> q = new ArrayDeque<>();
		for (int i = 0; i < inputNum.length; i++) {

			// remove numbers out of range k
			while (!q.isEmpty() && q.peek() < i - k + 1) {
				q.poll();

			}

			// remove smaller numbers in k range as they are useless, remove from the end
			// to make the first one always be the greatest
			while (!q.isEmpty() && inputNum[q.peekLast()] < inputNum[i]) {
				q.pollLast();
			}
            
			// add current index to the end of deque
			q.offer(i);

			// start writing to result
			if (i >= k - 1) {
				res[ri++] = inputNum[q.peek()];
			}
		}

		return res;
	}

=======================================================================================================
method 2:

method:

	- treemap
	- 

stats:

	- Runtime: 22 ms, faster than 32.25% of Java online submissions for Sliding Window Maximum.
	- Memory Usage: 48.5 MB, less than 6.25% of Java online submissions for Sliding Window Maximum.

public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) return new int[] {};
        // implement a treemap to save the entry <nums[i], i>
        TreeMap<Integer, Integer> tree = new TreeMap<Integer, Integer>();
        	
        // the maxWindow to save the largest in sliding window with width k
        int[] maxWindow = new int[nums.length - k + 1];
        
        
        for (int i = 0; i < nums.length; i++) {
        	// for i < k, just put the entries to the tree because there are not enough entries
        	tree.put(nums[i], i);

        	// if i >= k - 1, meaning that there are k elements in the window
        	if (i >= k - 1) {
        		// get the last (largest) key of the tree and put it into the max array
        		maxWindow[i - k + 1] = tree.lastKey();
        		// since there are enough entries, we need to remove the oldest one
        		// however, there may exist duplicates
        		// thus we need to check whether the index matches
        		// if matches, just remove it safely
        		// if not match, meaning that the number is automatically covered by some later numbers and we don't need to remove it
           		if (tree.get(nums[i - k + 1]) == i - k + 1) { tree.remove(nums[i - k + 1]); }
                }
         }

        return maxWindow;
    }
=======================================================================================================
method 3:

method: https://leetcode.com/problems/sliding-window-maximum/solution/

https://leetcode.com/problems/sliding-window-maximum/Figures/239/one_two.png

	- DP, like stock buy and sell
	- The idea is to split an input array into blocks of k elements. The last block could contain 
		less elements if n % k != 0.
	- The current sliding window with the first element i and the last element j could be placed 
		inside one block, or in two different blocks.
	- situation 1:
		we use an array left, where left[j] is a maximum element from the 
		beginning of the block to index j, direction left->right.
	- situation 2:
		we introduce array right, where right[j] is a maximum element from the end of the 
		block to index j, direction right->left. right is basically the same as left, but in the 
		other direction.
	- These two arrays together give all the information about window elements in both blocks. we 
		consider a sliding window from index i to index j. By definition, element right[i] is a 
		maximum element for window elements in the leftside block, and element left[j] is a maximum 
		element for window elements in the rightside block. 

	- Hence the maximum element in the sliding window is max(right[i], left[j]).


Algorithm:
	1. Iterate along the array in the direction left->right and build an array left.

	2. Iterate along the array in the direction right->left and build an array right.

	3. Build an output array as max(right[i], left[i + k - 1]) for i in range (0, n - k + 1).




stats:

	- time: O(N) since all we do is 3 passes along the array of length N.
	- Space: O(N) to keep left and right arrays of length N, and output array of length N - k + 1.

Runtime: 3 ms, faster than 95.25% of Java online submissions for Sliding Window Maximum.
Memory Usage: 49.4 MB, less than 6.25% of Java online submissions for Sliding Window Maximum.


class Solution {
  public int[] maxSlidingWindow(int[] nums, int k) {
    int n = nums.length;
    if (n * k == 0) return new int[0];
    if (k == 1) return nums;

    int [] left = new int[n];
    left[0] = nums[0];
    int [] right = new int[n];
    right[n - 1] = nums[n - 1];
    for (int i = 1; i < n; i++) {
      // from left to right
      if (i % k == 0) 
      	left[i] = nums[i];  // block_start
      else 
      	left[i] = Math.max(left[i - 1], nums[i]);

      // from right to left
      int j = n - i - 1;
      if ((j + 1) % k == 0) 
      	right[j] = nums[j];  // block_end
      else 
      	right[j] = Math.max(right[j + 1], nums[j]);
    }

    int [] output = new int[n - k + 1];
    for (int i = 0; i < n - k + 1; i++)
      output[i] = Math.max(left[i + k - 1], right[i]);

    return output;
  }
}


ex. Input: nums = [1,3,-1,-3,5,3,6,7], and k = 3


left = [1, 3, 3, -3, 5, 5, 6, 7]

right = [3, 3, -1, 5, 5, 3, 7, 7]

Output: [3,3,5,5,6,7] 
