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

	- bucket sort (a reversed version)
	- store the frequencies as index, then go from largest to smallest in bucket array

stats:

	- O(n)
	- 

class Solution {
    public List<Integer> topKFrequent(int[] nums, int k) {
        // freq map
        Map<Integer, Integer> freq = new HashMap<Integer, Integer>();

        for (int n : nums) {
            freq.put(n, freq.getOrDefault(n, 0) + 1);
        }

        // bucket sort on freq
        List<Integer>[] bucket = new List[nums.length + 1];
        for (int i = 0; i < bucket.length; i++) 
        	bucket[i] = new ArrayList();

        for (int key : freq.keySet()) {
            bucket[freq.get(key)].add(key);
        }

        // gather result
        List<Integer> res = new ArrayList();
        for (int i = bucket.length - 1; i >= 0; i--) {
            res.addAll(bucket[i]);
            if (res.size() >= k) break;
        }
        return res;
    }
}


=======================================================================================================
method 2:

method:

	- use min heap. Put entry into it so we can always poll a number with largest frequency

	- 

stats:

	- Time complexity : O(Nlog(k)). The complexity of Counter method is O(N). To build a heap and 
	                    output list takes O(Nlog(k)). 

	- Space complexity : O(N) to store the hash map.


public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int n: nums){
            map.put(n, map.getOrDefault(n,0)+1);
        }

        PriorityQueue<Map.Entry<Integer, Integer>> minHeap =
            new PriorityQueue<>((a, b) -> Integer.compare(a.getValue(), b.getValue()));

        for(Map.Entry<Integer,Integer> entry: map.entrySet()){
            minHeap.add(entry);
            if (minHeap.size() > k) minHeap.poll(); 
        }

        List<Integer> res = new ArrayList<>();
        while(res.size()<k){
            Map.Entry<Integer, Integer> entry = minHeap.poll();
            res.add(entry.getKey());
        }
        return res;
    }

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



