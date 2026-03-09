347. Top K Frequent Elements - Medium

Given a non-empty array of integers, return the k most frequent elements.

Example 1:

Input: nums = [1,1,1,2,2,3], k = 2
Output: [1,2]
Example 2:

Input: nums = [1], k = 1
Output: [1]
Note:

You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
Your algorithm's time complexity must be better than O(n log n), where n is the array's size.


******************************************************
key:
	- TreeMap, bucket-sort, Heap
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

传统的桶排序是按数值大小分桶，而这里的核心思想是：将“出现频次 (Frequency)”作为桶的下标 (Index)。

逻辑步骤 (Logic Steps)：

1. 计数 (Counting)：使用哈希表（HashMap）统计每个元素出现的次数。
2. 分桶 (Bucketing)：创建一个数组 buckets，其中 buckets[i] 存储所有出现次数恰好为 i 的元素。
                   桶的最大下标是数组长度 N (即某个元素出现了 N 次）。
3. 倒序收集 (Gathering)：从最大的桶（频率最高）向最小的桶遍历，收集前 K 个元素。



stats:

	- O(n)
	- 
import java.util.*;

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        // 1. 统计频率 (Counting)
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int n : nums) {
            countMap.put(n, countMap.getOrDefault(n, 0) + 1);
        }

        // 2. 建立桶 (Bucketing)
        // 桶的下标表示频率，所以长度需要 nums.length + 1
        List<Integer>[] buckets = new List[nums.length + 1];
        for (int key : countMap.keySet()) {
            int frequency = countMap.get(key);
            if (buckets[frequency] == null) {
                buckets[frequency] = new ArrayList<>();
            }
            buckets[frequency].add(key);
        }

        // 3. 倒序提取前 K 个 (Gathering)
        int[] result = new int[k];
        int index = 0;
        for (int f = buckets.length - 1; f >= 0 && index < k; f--) {
            if (buckets[f] != null) {
                for (int val : buckets[f]) {
                    result[index++] = val;
                    if (index == k) 
                        return result;
                }
            }
        }
        return result;
    }
}

from collections import Counter

class Solution:
    def topKFrequent(self, nums: List[int], k: int) -> List[int]:
        # 1. 统计频率 (Counting)
        counts = Counter(nums)
        
        # 2. 建立桶 (Bucketing)
        # 频率最大为 len(nums)，创建一个 len(nums) + 1 长度的列表
        buckets = [[] for _ in range(len(nums) + 1)]
        for val, freq in counts.items():
            buckets[freq].append(val)
            
        # 3. 倒序提取前 K 个 (Gathering)
        res = []
        for i in range(len(buckets) - 1, 0, -1):
            for val in buckets[i]:
                res.append(val)
                if len(res) == k:
                    return res
        return res



=======================================================================================================
method 2:

method:

	- use min heap. Put entry into it so we can always poll a number with largest frequency

	- 

stats:

	- Time complexity : O(Nlog(k)). The complexity of Counter method is O(N). To build a heap and 
	                    output list takes O(Nlog(k)). 

	- Space complexity : O(N) to store the hash map.


class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        // O(1) time
        if (k == nums.length) {
            return nums;
        }
        
        // 1. Build hash map: character and how often it appears
        // O(N) time
        Map<Integer, Integer> count = new HashMap();
        for (int n: nums) {
          count.put(n, count.getOrDefault(n, 0) + 1);
        }

        // init heap 'the less frequent element first'
        Queue<Integer> heap = new PriorityQueue<>(
            (n1, n2) -> count.get(n1) - count.get(n2));

        // 2. Keep k top frequent elements in the heap
        // O(N log k) < O(N log N) time
        for (int n: count.keySet()) {
          heap.add(n);
          if (heap.size() > k) heap.poll();     // remove 头部元素，即最小的，least freq
        }

        // 3. Build an output array
        // O(k log k) time
        int[] top = new int[k];
        for(int i = k - 1; i >= 0; --i) {
            top[i] = heap.poll();
        }
        return top;
    }
}


from collections import Counter
class Solution:
    def topKFrequent(self, nums: List[int], k: int) -> List[int]: 
        # O(1) time 
        if k == len(nums):
            return nums
        
        # 1. Build hash map: character and how often it appears
        # O(N) time
        count = Counter(nums)   

        # 2-3. Build heap of top k frequent elements and
        # convert it into an output array
        # O(N log k) time
        return heapq.nlargest(k, count.keys(), key=count.get) 



=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



