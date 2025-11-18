632. Smallest Range Covering Elements from K Lists - Hard

You have k lists of sorted integers in non-decreasing order. Find the smallest range that 
includes at least one number from each of the k lists.

We define the range [a, b] is smaller than range [c, d] if b - a < d - c or a < c if b - a == d - c.

 

Example 1:

Input: nums = [[4,10,15,24,26],[0,9,12,20],[5,18,22,30]]
Output: [20,24]
Explanation: 
List 1: [4, 10, 15, 24,26], 24 is in range [20,24].
List 2: [0, 9, 12, 20], 20 is in range [20,24].
List 3: [5, 18, 22, 30], 22 is in range [20,24].
Example 2:


Input: nums = [[1,2,3],[1,2,3],[1,2,3]]
Output: [1,1]
 

Constraints:

nums.length == k
1 <= k <= 3500
1 <= nums[i].length <= 50
-105 <= nums[i][j] <= 105
nums[i] is sorted in non-decreasing order.


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

	-	The key takeaway is to utilize a min-heap to track the minimum element from each 
	    list and a sliding window to track the range of numbers.


1. Initialize a min-heap (smallest element from each list, list index, index of element 
	within the list)

2. Track current_max across all the k lists (for range)

3. Iterate through the lists by always popping the minimum element from the heap (by heap's 
	nature) and checking the range between the minimum and the current maximum.

4. Update the smallest range whenever a smaller one is found.

5. Push the next element from the same list (the list from which the popped element came) 
   into the heap. Update the current maximum as necessary.

6. Stop the iteration once any list runs out of elements.

Example
nums = [[4,10,15,24,26],[0,9,12,20],[5,18,22,30]]
# Heap initialization (element, list_idx, idx)
Heap: [(0, 1, 0), (4, 0, 0), (5, 2, 0)]

# Popping from this heap will result in returning (0, 1, 0)
Heap: [(4, 0, 0), (5, 2, 0)]

# After range updates, push the next element in 1st list to heap
Heap:  [(4, 0, 0), (5, 2, 0), (9, 1, 1)] 
# since it was 9 (bigger than 4 & 5), it goes to the end

# Keep doing this until it reaches one of the lists' end (break)


Stats:
	Time complexity: O(Nlogk)
		N: Total number of elements across all lists
		k: Number of lists
		O(logk): Heap Operations(add & remove)
	
	Space complexity: O(k), k: Number of lists




class Solution {
    public int[] smallestRange(List<List<Integer>> nums) {
        // Min-Heap: stores (value, list index, element index)
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        int curMax = Integer.MIN_VALUE;

        // Initialize the heap with the first element of each list
        for (int i = 0; i < nums.size(); i++) {
            minHeap.offer(new int[]{nums.get(i).get(0), i, 0});
            curMax = Math.max(curMax, nums.get(i).get(0));
        }

        // Track the smallest range
        int[] resRange = new int[]{0, Integer.MAX_VALUE};

        while (true) {
            // Get the minimum element from the heap
            int[] curr = minHeap.poll();
            int curMin = curr[0], listIdx = curr[1], elemIdx = curr[2];

            // Update the smallest range if a better one is found
            if (curMax - curMin < resRange[1] - resRange[0]) {
                resRange[0] = curMin;
                resRange[1] = curMax;
            }

            // Move to the next element in the same list
            if (elemIdx + 1 < nums.get(listIdx).size()) {
                int nextVal = nums.get(listIdx).get(elemIdx + 1);
                minHeap.offer(new int[]{nextVal, listIdx, elemIdx + 1});
                curMax = Math.max(curMax, nextVal);
            } else {
                // If any list is exhausted, stop
                break;
            }
        }
        return resRange;
    }
}






