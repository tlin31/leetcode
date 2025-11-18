295. Find Median from Data Stream - Hard

The median is the middle value in an ordered integer list. If the size of the list is even, 
there is no middle value, and the median is the mean of the two middle values.

For example, for arr = [2,3,4], the median is 3.
For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
Implement the MedianFinder class:

MedianFinder() initializes the MedianFinder object.
void addNum(int num) adds the integer num from the data stream to the data structure.
double findMedian() returns the median of all elements so far. Answers within 10-5 of the 
actual answer will be accepted.
 

Example 1:

Input
["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
[[], [1], [2], [], [3], []]
Output
[null, null, null, 1.5, null, 2.0]

Explanation
MedianFinder medianFinder = new MedianFinder();
medianFinder.addNum(1);    // arr = [1]
medianFinder.addNum(2);    // arr = [1, 2]
medianFinder.findMedian(); // return 1.5 (i.e., (1 + 2) / 2)
medianFinder.addNum(3);    // arr[1, 2, 3]
medianFinder.findMedian(); // return 2.0
 

Constraints:

-105 <= num <= 105
There will be at least one element in the data structure before calling findMedian.
At most 5 * 104 calls will be made to addNum and findMedian.
 

Follow up:

If all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?
If 99% of all integer numbers from the stream are in the range [0, 100], how would you optimize 
your solution?


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

我们维护两个堆：

堆类型			含义					特点
最大堆 (maxHeap)	存储“较小的一半”数据	堆顶是左半部分的最大值
最小堆 (minHeap)	存储“较大的一半”数据	堆顶是右半部分的最小值


要求保持：

maxHeap.size() == minHeap.size() 或 maxHeap.size() == minHeap.size() + 1

所有 maxHeap 元素 ≤ 所有 minHeap 元素


如果总数是奇数，中位数 = maxHeap.peek(),如果总数是偶数，中位数 = (maxHeap.peek() + minHeap.peek()) / 2.0

Stats:

	操作				时间复杂度	说明
	addNum()		O(log n)	堆插入操作
	findMedian()	O(1)		直接读取堆顶元素


import java.util.PriorityQueue;
import java.util.Collections;

class MedianFinder {
    private PriorityQueue<Integer> maxHeap; // 左边（较小一半）
    private PriorityQueue<Integer> minHeap; // 右边（较大一半）

    public MedianFinder() {
        maxHeap = new PriorityQueue<>((a, b) -> b - a); // 最大堆
        minHeap = new PriorityQueue<>(); // 最小堆
    }

    public void addNum(int num) {
        // Step 1: 先放入 maxHeap
        maxHeap.offer(num);
        // Step 2: 平衡两堆，使 maxHeap 的所有元素 <= minHeap 的所有元素
        minHeap.offer(maxHeap.poll());
        
        // Step 3: 如果右堆比左堆大了，调整回来
        if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    public double findMedian() {
        if (maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        } else {
            return maxHeap.peek();
        }
    }
}



多线程版本（线程安全的 MedianFinder）

在高并发环境中（例如实时交易价格流），多个线程可能同时：调用 addNum() 或 findMedian()

为避免竞态条件（race condition），我们可以用 ReentrantReadWriteLock：

	允许多个线程同时读取（findMedian）

	写操作（addNum）时会独占锁

import java.util.*;
import java.util.concurrent.locks.*;

class ConcurrentMedianFinder {
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void addNum(int num) {
        lock.writeLock().lock();
        try {
            maxHeap.offer(num);
            minHeap.offer(maxHeap.poll());
            if (minHeap.size() > maxHeap.size()) {
                maxHeap.offer(minHeap.poll());
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public double findMedian() {
        lock.readLock().lock();
        try {
            if (maxHeap.size() == minHeap.size()) {
                return (maxHeap.peek() + minHeap.peek()) / 2.0;
            } else {
                return maxHeap.peek();
            }
        } finally {
            lock.readLock().unlock();
        }
    }
}

一、Follow-up 1：所有数都在 [0, 100]

👉 优化目标：
因为数值范围固定且非常小（只有 101 种可能），我们可以放弃堆，使用 计数数组。

class MedianFinder {
    private int[] count = new int[101];
    private int totalCount = 0;

    public void addNum(int num) {
        count[num]++;
        totalCount++;
    }

    public double findMedian() {
        int mid1 = (totalCount + 1) / 2;
        int mid2 = (totalCount % 2 == 0) ? (totalCount / 2 + 1) : mid1;

        int cumulative = 0;
        int m1 = -1, m2 = -1;

        for (int i = 0; i <= 100; i++) {
            cumulative += count[i];
            if (m1 == -1 && cumulative >= mid1) m1 = i;
            if (m2 == -1 && cumulative >= mid2) m2 = i;
        }

        return (m1 + m2) / 2.0;
    }
}


🧩 二、Follow-up 2：99% 的数在 [0, 100]

这个更有挑战性，因为有少量异常值（outliers）。

✅ 优化思路：混合模型（Hybrid Approach）

我们分两部分处理：

部分		范围			存储结构					理由
主体数据	[0, 100]	计数数组（count[101]）		占 99%，O(1) 插入
异常数据	<0 或 >100	两个堆（maxHeap、minHeap）	数量少但需要动态中位数

✅ 操作逻辑

插入时：

如果 num ∈ [0, 100]，更新 count[num]；否则插入到堆结构（例如小于 0 → maxHeap，大于 100 → minHeap）。

计算中位数：

如果绝大多数数据在 [0, 100] 内，直接用计数数组找到中位位置；

如果堆内有少量数据（比如两边有溢出部分），需根据数量调整中位索引：

“左堆的大小”代表比 0 小的数的数量；“右堆的大小”代表比 100 大的数的数量；因此真实的中位位置要偏移。




