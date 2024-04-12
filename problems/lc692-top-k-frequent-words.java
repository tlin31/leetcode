692. Top K Frequent Words - Medium

Given a non-empty list of words, return the k most frequent elements.

Your answer should be sorted by frequency from highest to lowest. If two words have the same 
frequency, then the word with the lower alphabetical order comes first.

Example 1:
Input: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
Output: ["i", "love"]
Explanation: "i" and "love" are the two most frequent words.
    Note that "i" comes before "love" due to a lower alphabetical order.


Example 2:
Input: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
Output: ["the", "is", "sunny", "day"]
Explanation: "the", "is", "sunny" and "day" are the four most frequent words,
    with the number of occurrence being 4, 3, 2 and 1 respectively.



Note:
You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
Input words contain only lowercase letters.
Follow up:
Try to solve it in O(n log k) time and O(n) extra space.

******************************************************
key:
	- sort
	- Trie
	- edge case:
		1) empty input list, return []
		2) k = 0, return []
		3) 

******************************************************



=======================================================================================================
method 1:

method:
	- Count the frequency of each word, and sort the words with a custom ordering relation that uses 
		these frequencies. Then take the best k of them.
	- 

stats:

	- Time:O(NlogN), where N is the length of words. 
		count the frequency of each word in O(N) time, sort the given words in O(NlogN) time.

	- Space Complexity: O(N), the space used to store our candidates.
	- Runtime: 37 ms, faster than 40.97% of Java online submissions for Top K Frequent Words.
	- Memory Usage: 38.7 MB, less than 67.86% o


class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> count = new HashMap();

        for (String word: words) {
            count.put(word, count.getOrDefault(word, 0) + 1);
        }

        List<String> candidates = new ArrayList(count.keySet());

        Collections.sort(candidates, (w1, w2) -> 
        	// if count w1 and count w2 are the same, then compare the words by lexi-order
        	// if count is difference, sort by list.
        	count.get(w1).equals(count.get(w2)) ?
                w1.compareTo(w2) : count.get(w2) - count.get(w1));


        // get the sublist
        return candidates.subList(0, k);
}




=======================================================================================================
method 2:

method:

	- Count the frequency of each word, then add it to heap that stores the best k candidates. 
		Here, "best" is defined with our custom ordering relation, which puts the worst candidates 
		at the top of the heap. At the end, we pop off the heap up to k times and reverse the result 
		so that the best candidates are first.
	- 

stats:

	- Time Complexity: O(Nlogk),  N = length of words. 
		count the frequency of each word in O(N) time, then add N words to the heap, each in 
		O(logk) time. Finally, we pop from the heap up to k times. As k≤N, this is O(Nlogk)
	- Space Complexity: O(N), the space used to store our count.
	- Runtime: 38 ms, faster than 17.94% of Java online submissions for Top K Frequent Words.
	- Memory Usage: 39.1 MB, less than 67.86%

	class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> count = new HashMap();

        for (String word: words) {
            count.put(word, count.getOrDefault(word, 0) + 1);
        }

        // order by count
        PriorityQueue<String> heap = new PriorityQueue<String>(
                (w1, w2) -> count.get(w1).equals(count.get(w2)) ?
                w2.compareTo(w1) : count.get(w1) - count.get(w2) );

        for (String word: count.keySet()) {
            heap.offer(word);
            if (heap.size() > k) 
            	heap.poll();
        }

        List<String> ans = new ArrayList();

        while (!heap.isEmpty()) 
        	ans.add(heap.poll());

        Collections.reverse(ans);

        return ans;
    }
}


-----------------------
Method 2.5: 
	- The idea is to keep a count of each word in a HashMap and then insert in a Priority Queue.
	- While inserting in pq, if the count of two words is same then insert based on string compare 
		of the keys.

Stats:
	- Runtime: 6 ms, faster than 96.14% of Java online submissions for Top K Frequent Words.
	- Memory Usage: 38.2 MB, less than 73.21%

class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        HashMap<String, Integer> map = new HashMap<>();

        // max for all words
        int max = 0;
        for (String w: words) {
            map.put(w, map.getOrDefault(w, 0) + 1);
            max = Math.max(max, map.get(w));
        }

        List<String>[] bucket = new ArrayList[max + 1];

        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            int numCount = entry.getValue();

            // get list of number of counts == numCount
            if (bucket[numCount] == null) {
                bucket[numCount] = new ArrayList<>();
            }
            bucket[numCount].add(entry.getKey());
        }

        List<String> res = new ArrayList<>();

        // go from back of the count, and add it to the list
        // can't ask for exactly k个, because may exist same count
        for (int i = max; i >= 0 && res.size() < k; i--) {
            if (bucket[i] != null) {
                Collections.sort(bucket[i]);
                res.addAll(bucket[i]);
            }
        }
        return res.subList(0, k);
    }
}



