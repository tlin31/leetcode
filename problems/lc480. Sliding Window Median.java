480. Sliding Window Median - Hard

The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle values.

For examples, if arr = [2,3,4], the median is 3.
For examples, if arr = [1,2,3,4], the median is (2 + 3) / 2 = 2.5.
You are given an integer array nums and an integer k. There is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.

Return the median array for each window in the original array. Answers within 10-5 of the actual value will be accepted.

 

Example 1:

Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [1.00000,-1.00000,-1.00000,3.00000,5.00000,6.00000]
Explanation: 
Window position                Median
---------------                -----
[1  3  -1] -3  5  3  6  7        1
 1 [3  -1  -3] 5  3  6  7       -1
 1  3 [-1  -3  5] 3  6  7       -1
 1  3  -1 [-3  5  3] 6  7        3
 1  3  -1  -3 [5  3  6] 7        5
 1  3  -1  -3  5 [3  6  7]       6
Example 2:

Input: nums = [1,2,3,4,2,3,1,4,2], k = 3
Output: [2.00000,3.00000,3.00000,3.00000,2.00000,3.00000,2.00000]
 

Constraints:

1 <= k <= nums.length <= 105
-231 <= nums[i] <= 231 - 1


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

✅ 正确思路：双堆 + 延迟删除

维护两个堆：

	堆	含义
	small	大顶堆，存窗口中 较小的一半
	large	小顶堆，存窗口中 较大的一半
	堆不变量（必须记住）

small.size() == large.size() 或 small.size() == large.size() + 1

所有 small ≤ 所有 large

👉 中位数：

k 为奇数：small.peek()

k 为偶数：(small.peek() + large.peek()) / 2


为什么需要 Lazy Deletion？

	Java 的 PriorityQueue：

	❌ 不支持 O(log n) 删除任意元素

	remove(x) 是 O(n)

	解决方案：延迟删除（Lazy Deletion）

	用 HashMap<Integer, Integer> delayed

	标记“该删除的元素”

	堆顶访问时才真正删除

delayed[x] = k 表示：值为 x 的元素 已经被逻辑删除 k 次，但还没从堆里物理移除

	只处理 堆顶元素
	因为只有堆顶才能安全删除，堆中间的“脏元素”先不管	


Stats:

	- 
	- 


class Solution {
    private PriorityQueue<Integer> small = new PriorityQueue<>(Collections.reverseOrder());
    private PriorityQueue<Integer> large = new PriorityQueue<>();
    private Map<Integer, Integer> delayed = new HashMap<>();

    private int k;
    private int smallSize = 0, largeSize = 0;

    public double[] medianSlidingWindow(int[] nums, int k) {
        this.k = k;
        int n = nums.length;
        double[] res = new double[n - k + 1];

        for (int i = 0; i < n; i++) {
            add(nums[i]);
            if (i >= k) remove(nums[i - k]);
            if (i >= k - 1) res[i - k + 1] = getMedian();
        }
        return res;
    }

    private void add(int num) {
        if (small.isEmpty() || num <= small.peek()) {
            small.offer(num);
            smallSize++;
        } else {
            large.offer(num);
            largeSize++;
        }
        balance();
    }

    private void remove(int num) {
        delayed.put(num, delayed.getOrDefault(num, 0) + 1);

        if (num <= small.peek()) {
            smallSize--;
            if (num == small.peek()) prune(small);
        } else {
            largeSize--;
            if (num == large.peek()) prune(large);
        }
        balance();
    }

    private void balance() {
        if (smallSize > largeSize + 1) {
            large.offer(small.poll());
            smallSize--;
            largeSize++;
            prune(small);
        } 
        else if (smallSize < largeSize) {
            small.offer(large.poll());
            smallSize++;
            largeSize--;
            prune(large);
        }
    }

    private void prune(PriorityQueue<Integer> heap) {
        while (!heap.isEmpty()) {
            int num = heap.peek();
            if (delayed.containsKey(num)) {
                delayed.put(num, delayed.get(num) - 1);
                if (delayed.get(num) == 0) delayed.remove(num);
                heap.poll();
            } else break;
        }
    }

    private double getMedian() {
        if (k % 2 == 1) return small.peek();
        return ((double) small.peek() + large.peek()) / 2.0;
    }
}


---------------------------------------------------------------------------------------------------------------------------

👉 TreeSet + 索引比较器（Index-based BST），

用 两个 TreeSet 存索引而不是值，
通过自定义 comparator 保证：

    按 nums[index] 排序！

值相同按 index 排序，从而 支持 O(log k) 删除窗口左端元素。

如果直接存值：
	❌ 有重复值 → TreeSet 会认为是同一个元素

	❌ 删除不确定（删哪个）


left  = maxHeap（TreeSet reversed）
        👉 存「较小的一半」
        👉 left.first() = 较小一半里的最大值

right = minHeap
        👉 存「较大的一半」
        👉 right.first() = 较大一半里的最小值
中位数规则
    k 是奇数 → median = right.first()

    k 是偶数 → (left.first() + right.first()) / 2

    ⚠️ 注意：
    中位数永远在 right 里（或 right 的最小值）




⚠️ TreeSet 支持 O(log k) 删除，这是最大优势


class Solution {
    public double[] medianSlidingWindow(int[] nums, int k) {
        Comparator<Integer> comparator = new Comparator<>(){
             public int compare(Integer a, Integer b) {
                if (nums[a] != nums[b]) {
                    return Integer.compare(nums[a], nums[b]);
                }
                else {
                    return a - b;
                }
            } 
        };
        TreeSet<Integer> left = new TreeSet<>(comparator.reversed());
        TreeSet<Integer> right = new TreeSet<>(comparator);
        double[] res = new double[nums.length - k + 1];

        // initialize, add all element in window to left, then rebalance
        for (int i = 0; i < k; i++) {
            left.add(i);
        }

        balance(left, right);

        res[0] = getMedian(k, nums, left, right);

        int r = 1;
        for (int i = k; i < nums.length; i++) {
            if(!left.remove(i - k)) {
                right.remove(i - k);
            }

            //先把新元素扔进“右边（大的一半）”，
            //再把右边最小的那个，挪到左边
            right.add(i); 
            left.add(right.pollFirst());
            balance(left, right); 

            res[r] = getMedian(k, nums, left, right);
            r++;
        }

        return res;
    }
    
    private void balance(TreeSet<Integer> left, TreeSet<Integer> right) {
        while (left.size() > right.size()) {
            right.add(left.pollFirst());
        }
    }
    
    private double getMedian(int k, int[] nums, TreeSet<Integer> left, TreeSet<Integer> right) {
        if (k % 2 == 0) {
            return ((double) nums[left.first()] + nums[right.first()]) / 2;
        }
        else {
            return (double) nums[right.first()];
        }
    }
}



python:
import heapq

class Solution:
    def medianSlidingWindow(self, nums: list[int], k: int) -> list[float]:
        small, large = [], [] # Max-heap (small), Min-heap (large)
        delayed = {} # Hash map for lazy removal
        
        def prune(heap, is_max_heap=False):
            while heap:
                val = -heap[0] if is_max_heap else heap[0]
                if val in delayed and delayed[val] > 0:
                    delayed[val] -= 1
                    heapq.heappop(heap)
                else:
                    break

        # Initial window
        for i in range(k):
            heapq.heappush(small, -nums[i])
        for _ in range(k // 2):
            heapq.heappush(large, -heapq.heappop(small))
            
        res = []
        # Python's "/" handles float division
        res.append(-small[0] if k % 2 else (-small[0] + large[0]) / 2.0)
        
        for i in range(k, len(nums)):
            in_val, out_val = nums[i], nums[i-k]
            balance = 0
            
            # Lazy remove out_val
            delayed[out_val] = delayed.get(out_val, 0) + 1
            balance += -1 if out_val <= -small[0] else 1
            
            # Add in_val
            if small and in_val <= -small[0]:
                balance += 1
                heapq.heappush(small, -in_val)
            else:
                balance -= 1
                heapq.heappush(large, in_val)
                
            # Rebalance
            if balance < 0:
                heapq.heappush(small, -heapq.heappop(large))
            elif balance > 0:
                heapq.heappush(large, -heapq.heappop(small))
            
            prune(small, True)
            prune(large, False)
            res.append(-small[0] if k % 2 else (-small[0] + large[0]) / 2.0)
            
        return res



