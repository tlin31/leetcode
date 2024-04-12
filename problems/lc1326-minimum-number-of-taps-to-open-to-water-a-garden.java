1326. Minimum Number of Taps to Open to Water a Garden - Hard


There is a one-dimensional garden on the x-axis. The garden starts at the point 0 and ends at 
the point n. (i.e The length of the garden is n).

There are n + 1 taps located at points [0, 1, ..., n] in the garden.

Given an integer n and an integer array ranges of length n + 1 where ranges[i] (0-indexed) means 
the i-th tap can water the area [i - ranges[i], i + ranges[i]] if it was open.


Return the minimum number of taps that should be open to water the whole garden, If the garden 
cannot be watered return -1.

 

Example 1:


Input: n = 5, ranges = [3,4,1,1,0,0]
Output: 1
Explanation: The tap at point 0 can cover the interval [-3,3]
The tap at point 1 can cover the interval [-3,5]
The tap at point 2 can cover the interval [1,3]
The tap at point 3 can cover the interval [2,4]
The tap at point 4 can cover the interval [4,4]
The tap at point 5 can cover the interval [5,5]
Opening Only the second tap will water the whole garden [0,5]


Example 2:

Input: n = 3, ranges = [0,0,0,0]
Output: -1
Explanation: Even if you activate all the four taps you cannot water the whole garden.


Example 3:

Input: n = 7, ranges = [1,2,1,0,2,1,0,1]
Output: 3

Example 4:

Input: n = 8, ranges = [4,0,0,0,0,0,0,0,4]
Output: 2


Example 5:

Input: n = 8, ranges = [4,0,0,0,4,0,0,0,4]
Output: 1
 

Constraints:

1 <= n <= 10^4
ranges.length == n + 1
0 <= ranges[i] <= 100



******************************************************
key:
	- Greedy or Sort
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: Sort


Stats:
	- Time: O(nlogn), space: O(n).




Method:

	- For this problem, we just need construct a new array to move the value into the leftmost 
	  point we can water, so the problem becomes Jump Game II. 
	  For example, at index i we could water (i - arr[i]) ~ (i + arr[i]). 
	  So we store the farthest point we can water at "i - arr[i]". 
	  Then "left" in range [left, right] is index and "right" is the value in arr[index].

	- Iterate through all the intervals and store the longest position value that can be reached
	- Every time choose to go to the farthest point it can reach
	
		1. sort the array and fill in the max end value of each starting point, 
		   for example, if there is [0, 1], [0, 2], [0, 8] then we ill fill 0 with value 8
		2. It shall always start from 0, so perform early exit if it doesn't exist
		3. Then based on the end of previous interval, we need to find the next interval which 
		   can be merged with previous to maximum and proceed. For example, currently we have [0, 4], options can be [2, 8], [4, 6], [5, 7], among them we will begin from 4 as starting point, and go to left to find if there is greater interval choice and finally we choose [2, 8] as new beginning
		If in the process we cannot proceed anymore then simply exit

	- intervals[i] = at position i, the farest garden the water tap at i can reach

class Solution {
    public int minTaps(int n, int[] ranges) {
        int[] intervals = new int[n];
        Arrays.fill(intervals, -1);

        for (int i = 0; i < ranges.length; i++) {
            if (ranges[i] == 0) continue;
            int left = i - ranges[i] >= 0 ? i - ranges[i] : 0;
            int right = i + ranges[i];
            intervals[left] = Math.max(intervals[left], right);
        }

        if (intervals[0] == -1) return -1;
        int longest = intervals[0];
        int count = 1;
        int i = 0;
        while (longest < n) {
            int temp = Integer.MIN_VALUE;

            // check within the intervals, whether it can extend the farest reach/longest
            for (; i <= longest; i++) {
                int val = intervals[i];
                if (val == -1) 
                	continue;
                temp = Math.max(temp, val);
            }
            if (temp <= longest) 
            	return -1;
            longest = temp;
            count++;
        }
        return count;
    }
}


input: 5, [3,4,1,1,0,0]

output:
intervals: [5, 3, 4, -1, -1]

~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


    def minTaps(self, n: int, ranges: List[int]) -> int:
        rg = sorted([[i - r, i + r] for i, r in enumerate(ranges)])
        ans = start = end = i = 0
        while start < n:
            while i <= n and rg[i][0] <= start:
                end = max(end, rg[i][1])
                i += 1   
            if start == end:
                return -1
            start = end
            ans += 1
        return ans

=======================================================================================================
method 2: Greedy

Stats:

	- 
	- 


Method:

	-	
	-	







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
We build a list max_range to store the max range it can be watered from each index.

Then it becomes Jump Game II, where we want to find the minimum steps to jump from 0 to n.

The only difference is Jump Game II guarantees we can jump to the last index but this one not.

We need to additionally identify the unreachable cases.

class Solution:
    def minTaps(self, n: int, ranges: List[int]) -> int:
        max_range = [0] * (n + 1)
        for i, r in enumerate(ranges):
            left, right = max(0, i - r), min(n, i + r)
            max_range[left] = max(max_range[left], right)
        start = end = step = 0
        while end < n:
            step += 1
            start, end = end, max(max_range[i] for i in range(start, end + 1))
            if start == end: return -1
        return step

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

