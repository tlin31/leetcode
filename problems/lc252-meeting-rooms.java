252. Meeting Rooms - Easy

Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] 
(si < ei), determine if a person could attend all meetings.

Example 1:
Input: [[0,30],[5,10],[15,20]]
Output: false

Example 2:
Input: [[7,10],[2,4]]
Output: true

******************************************************
key:
	- sort, then check one by one
	- 
	- edge case:
		1) empty list or length == 1, return true
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- 
	- 

stats:

	- 
	- Runtime: 35 ms, faster than 52.09% of Java online submissions for Meeting Rooms.
	- Memory Usage: 37.2 MB, less than 64.10% 

class Solution {
    public boolean canAttendMeetings(int[][] intervals) {
        int length = intervals.length;
        if (length == 0) return true;
        Arrays.sort(intervals, (i1, i2) -> Integer.compare(i1[0], i2[0]));
        int prev[] = intervals[0];
        for (int i = 1; i< length; i++) {
            if (prev[1] > intervals[i][0]) return false;
            prev = intervals[i];
            
        }
        return true;
    }
}




=======================================================================================================
method 2:

method:

	- optimize, do not need to store previous 
	- 

stats:

	- 
	- Runtime: 6 ms, faster than 75.71% of Java online submissions for Meeting Rooms.
	- Memory Usage: 37 MB, less than 64.10%

    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return true;
        }
        
        Arrays.sort(intervals, new Comparator<int[]>(){
            public int compare(int[] a, int[] b)
            {
                return a[0] - b[0];
            }
        });
        
        for (int i = 0; i < intervals.length - 1; ++i){
            if (intervals[i][1] > intervals[i + 1][0])
            {
                return false;
            }
        }
        
        return true;
    }


