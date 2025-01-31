57. Insert Interval - Hard

Given a set of non-overlapping intervals, insert a new interval into the intervals(merge if necessary).

You may assume that the intervals were initially sorted according to their start times.

Example 1:
Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
Output: [[1,5],[6,9]]

Example 2:
Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
Output: [[1,2],[3,10],[12,16]]
Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].

******************************************************
key:
	- similar to #56
	- tricky part: when copy old array, need to count the poisition
	- edge case:
		1) no interval, return none
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- find where the new interval belongs, then merge it
	- !!! need to copy front and end parts

stats:

	
	public List<Interval> insert(List<Interval> intervals, Interval newInterval) {    
		List<Interval> result = new ArrayList<>();
		int i = 0;
		int start = newInterval.start;
		int end = newInterval.end;
		    
        // add first half: add all the non-overlap intervals smaller than new interval, just add it to the result
		while (i < intervals.size() && intervals.get(i).end < start) {
		    result.add(intervals.get(i++));
		}

        // there's an overlap, [[1,2],[3,5],[6,7],[8,10],[12,16]], new= [4,8] -->[[1,2],[3,10],[12,16]]
		while (i < intervals.size() && intervals.get(i).start <= end) {
		    start = Math.min(start, intervals.get(i).start);
		    end = Math.max(end, intervals.get(i).end);
		    i++;
		}

		result.add(new Interval(start,end)); 

        // add the later half
		while (i < intervals.size()) 
            result.add(intervals.get(i++)); 

		return result;
	}

--------


 public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> ans = new ArrayList<>();
        int[] toAdd = newInterval;
        
        for (int i = 0; i < intervals.length; i ++) {
            /*1. No overlap and toAdd appears before current interval, add toAdd to result.*/
            if (intervals[i][0] > toAdd[1]) {
                ans.add(toAdd);
                toAdd = intervals[i];
            }
            /*2. Has overlap, update the toAdd to the merged interval.*/
            else if (intervals[i][1] >= toAdd[0])  
                toAdd = new int[] {Math.min(intervals[i][0], toAdd[0]),
                                   Math.max(intervals[i][1], toAdd[1]) };
            /*3. No overlap and toAdd appears after current interval, add current interval to result.*/
            else ans.add(intervals[i]); 
        }
        ans.add(toAdd);
        return ans.toArray(new int[ans.size()][2]);
    }
    
=======================================================================================================
method 2:

method:

	- binary search!
	- For me, I want to get the position of the interval that is the first to have start equal to 
		or larger than the inserted interval s start. 
	- Similarly, I get the position of the interval that is the first to have end equal to or larger 
		than the new interval s end. Then I can get the range of intervals that the new interval 
		connects. 
	- corner cases that need to be considered, i.e. when the new interval has no overlap with any of 
		the existing intervals (smaller, larger, or the original list is empty). In these corner cases,
		we just do not need to update the new interval. 


stats:

	- O(n) -->  adding all intervals before iStart and then after iEnd to the result, one by one. 
		The binary search part of the code is just used for finding the initial values of iStart 
		and iEnd.

	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Insert Interval.
	- Memory Usage: 40.3 MB, less than 90.63%


class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {

    	//edge case, of none 
        if (intervals.length == 0) {
            return new int[][]{newInterval};
        }

        // idx of the earlies interval which intersects with a new one
        int min = findMin(intervals, newInterval); 

         // idx of the latest interval which intersects with a new one
        int max = findMax(intervals, newInterval); 

        // when one entirely within another one. ex.{[1,3],[5,7]} & new = [4,8]
        if (min == max) {

        	// update intervals[mimn]
            intervals[min] = new int[]{Math.min(intervals[min][0], newInterval[0]), 
            	                       Math.max(intervals[min][1], newInterval[1])};
            return intervals;
        }
        
        int[][] newIntervals = new int[intervals.length - (max - min)][2];
        
        // copy all intervals < newInterval
        for (int i = 0; i < min; i++) {
            newIntervals[i] = intervals[i];
        }
        
        // copy all intervals > newInterval
        for (int i = intervals.length - 1; i > max; i--) {
            newIntervals[i - (max - min)] = intervals[i];
        }
        
        // insert interval
        if (max < min) { // couldn't find intersecting intervals, just append
            newIntervals[min] = newInterval;
        } else {
            newIntervals[min] = new int[]{Math.min(intervals[min][0], newInterval[0]), Math.max(intervals[max][1], newInterval[1])};
        }
        
        return newIntervals;
    }
    
    private static int findMin(int[][] intervals, int[] interval) {
        int idx = -1;
        int lo = 0;
        int hi = intervals.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int[] midInt = intervals[mid];
            if (intersects(midInt, interval)) {
                idx = mid;
                hi = mid - 1;
            } else if (interval[0] > midInt[1]) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return idx == -1 ? lo : idx;
    }
    
    private static int findMax(int[][] intervals, int[] interval) {
        int idx = -1;
        int lo = 0;
        int hi = intervals.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int[] midInt = intervals[mid];
            if (intersects(midInt, interval)) {
                idx = mid;
                lo = mid + 1;
            } else if (interval[0] > midInt[1]) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return idx == -1 ? hi : idx;
    }
    
    left:   [   ]
    right:    [    ]
    private static boolean intersects(int[] left, int[] right) {
        return left[0] <= right[1] && left[1] >= right[0];
    }
}



