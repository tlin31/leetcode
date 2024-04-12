435. Non-overlapping Intervals - Medium

Given a collection of intervals, find the minimum number of intervals you need to remove to 
make the rest of the intervals non-overlapping.

 
Example 1:
Input: [[1,2],[2,3],[3,4],[1,3]]
Output: 1
Explanation: [1,3] can be removed and the rest of intervals are non-overlapping.

Example 2:
Input: [[1,2],[1,2],[1,2]]

Output: 2
Explanation: You need to remove two [1,2] to make the rest of intervals non-overlapping.

Example 3:
Input: [[1,2],[2,3]]
Output: 0
Explanation: You don't need to remove any of the intervals since they're already non-overlapping.
 

Note:

You may assume the interval's end point is always bigger than its start point.
Intervals like [1,2] and [2,3] have borders "touching" but they don't overlap each other.

******************************************************
key:
	- sort
	- key: need to delete minimum amount
		-->{all intervals} - {max compatible intervals} = minimum deleted intervals
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:
	- Actually, the problem is the same as "Given a collection of intervals, find the maximum number 
		of intervals that are non-overlapping." (the classic Greedy problem: Interval Scheduling). 
	- 1. Why {all intervals} - {max compatible intervals} = minimum deleted intervals? 
		Suppose interval A in the latter max compatible set B and A causes two other intervals 
		be deleted. If we delete A instead and insert those two deleted intervals to B can obtain 
		a larger set, then it contradicts B is the max compatible intervals.

	- 2. Why sort by finish time can get max compatible intervals? 
		if earliest finished is not included, we can always replace the first interval in the set 
		with it.

 
stats:

	- Total complexity: O(nlogn)
	- Sorting Interval.end in ascending order is O(nlogn), then traverse intervals array to get the 
		maximum number of non-overlapping intervals is O(n). Total is O(nlogn).

    public int eraseOverlapIntervals(int[][] intervals) {
        if(intervals == null || intervals.length == 0) return 0; 

        //sort base on end date
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        
        int end = intervals[0][1]; 
        int count = 1; 
        
        for(int i = 1; i < intervals.length; i++){

        	//if next interval's start >= current latest, update end
            if(intervals[i][0] >= end){
                end = intervals[i][1]; 
                count++; 
            }
        }
        
        return intervals.length - count; 
    }


   
    public int eraseOverlapIntervals(Interval[] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(i -> i.end));
        int max = 0, lastend = Integer.MIN_VALUE;
        for (Interval in : intervals) {
            if (lastend <= in.start) {
                lastend = in.end;
                max++;
            }
        }
        return intervals.length - max;
    }


=======================================================================================================
method 2:

method:

	- The intution here comes into play when we choose which interval to delete. If 2 intervals 
		are overlapping always delete the interval with bigger end value. 
	- this way we ensure the upcoming intervals have lesser chance of overlapping with previous 
		intervals and thus can gurantee lesser deletions.
	- 

stats:

	- 
	- 

	
	public int eraseOverlapIntervals(Interval[] intervals) {
        if(intervals == null || intervals.length == 0) return 0;
        Arrays.sort(intervals, new IntervalComparator());
        Interval prev = intervals[0];
        Interval curr = null;
        int toRemove = 0;
        for(int i=1;i<intervals.length;i++) {
            curr = intervals[i];
            if(overlapping(prev, curr)) {
                toRemove++;
                if(prev.end >= curr.end) {
                    prev = curr;
                }
            }else{
                prev = curr;
            }
        }
        return toRemove;
    }
    
    private boolean overlapping(Interval i1, Interval i2) {
        if(i1.end <= i2.start) return false;
        return true;
    }
    
    static class IntervalComparator implements Comparator<Interval> {
        
        public int compare(Interval i1, Interval i2) {
            if(i1.start == i2.start) return Integer.compare(i1.end, i2.end);
            return Integer.compare(i1.start, i2.start);
        }
    }

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 




