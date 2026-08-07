759. Employee Free Time - Hard

We are given a list schedule of employees, which represents the working time for each employee.

Each employee has a list of non-overlapping Intervals, and these intervals are in sorted order.

Return the list of finite intervals representing common, positive-length free time for all employees, also in sorted order.

(Even though we are representing Intervals in the form [x, y], the objects inside are Intervals, 
not lists or arrays. For example, schedule[0][0].start = 1, schedule[0][0].end = 2, and 
schedule[0][0][0] is not defined).  

Also, we wouldn't include intervals like [5, 5] in our answer, as they have zero length.

 

Example 1:

Input: schedule = [[[1,2],[5,6]],[[1,3]],[[4,10]]]
Output: [[3,4]]
Explanation: There are a total of three employees, and all common
free time intervals would be [-inf, 1], [3, 4], [10, inf].
We discard any intervals that contain inf as they aren't finite.
Example 2:

Input: schedule = [[[1,3],[6,7]],[[2,4]],[[2,5],[9,12]]]
Output: [[5,6],[7,9]]
 

Constraints:

1 <= schedule.length , schedule[i].length <= 50
0 <= schedule[i].start < schedule[i].end <= 10^8


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************


Instead of sorting all intervals (which could be many), we use a Min-Heap to efficiently merge k sorted lists (one per employee).

1. Flatten and Sort: 
    - Collect every "busy" interval from all employees into a single list and sort them by start time.

2. Merge Intervals: 
    - Iterate through the sorted busy intervals. If the current interval overlaps with the previous "merged" interval, merge them by updating the end time to max(prev_end, curr_end).

3. Identify Gaps: 
    - Any space between two merged busy intervals represents a time when all employees are free.
    - If merged_prev_end < curr_start, the gap is [merged_prev_end, curr_start]

Time Complexity:
• Flatten/Sort: O(Nlog N) where N is the total number of intervals.
• Priority Queue: 0(Nlog K) where K is the number of employees. This is better if K
< N.
• Space Complexity: O(N) to store flattened intervals (or O(K) for the heap).

Note: 
其实可以不用k-way merge，也就是不管这个interval是哪个employee的，直接全部flatten & sort
因为一个人busy就代表这个时间段不是free的



/*
// Definition for an Interval.
class Interval {
    public int start;
    public int end;

    public Interval() {}

    public Interval(int _start, int _end) {
        start = _start;
        end = _end;
    }
};
*/

class Solution {
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        List<Interval> result = new ArrayList<>();
        // PriorityQueue stores: {start, employee_index, interval_index}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        
        // add first interval of each employee
        for (int i = 0; i < schedule.size(); i++) {
            pq.add(new int[]{schedule.get(i).get(0).start, i, 0});
        }
        
        int prevEnd = pq.peek()[0]; // Initialize with the earliest start
        
        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int empIdx = top[1];
            int intervalIdx = top[2];
            Interval curr = schedule.get(empIdx).get(intervalIdx);
            
            // If there's a gap between the previous busy end and current start
            if (curr.start > prevEnd) {
                result.add(new Interval(prevEnd, curr.start));
            }
            
            prevEnd = Math.max(prevEnd, curr.end);
            
            // Add the next interval from the same employee to the heap
            if (intervalIdx + 1 < schedule.get(empIdx).size()) {
                pq.add(new int[]{schedule.get(empIdx).get(intervalIdx + 1).start, empIdx, intervalIdx + 1});
            }
        }
        
        return result;
    }
}





=======================================================================================================
method 1:

method:
	- line sweep
	-  Free time is a period of time such that no employees scheduled to work, i.e. the gap 
	   between every two non-overlapping intervals. 

	   If we meet a start of interval, score++; or else(if we meet an end of interval), score--. Non-overlapping intervals exist when score equals to 0.
	- We add current gap interval to the result list when score is not 0 any more.

stats:

	- Time Complexity: O(ClogC), where C is the number of intervals across all employees.

	- Space Complexity: O(C).


Solution
        List<Interval> result = new ArrayList<>();

        // Key: time point, value: score.
        Map<Integer, Integer> map = new TreeMap<>(); 

        for (List<Interval> list : schedule) {
            for (Interval interval : list) {
                map.put(interval.start, map.getOrDefault(interval.start, 0) + 1);
                map.put(interval.end, map.getOrDefault(interval.end, 0) - 1);
            }
        }

        int start = -1, score = 0;
        for (int point : map.keySet()) {
            score += map.get(point);
            if (score == 0 && start == -1) {
                start = point;
            } 
            // reach the end of the interval!
            else if (start != -1 && score != 0) {
                result.add(new Interval(start, point));
                start = -1;
            }
        }

        return result;


=======================================================================================================
method 2:

method:
	- priority queue
	- similar logic as 1
	- PQ is sorted by start time, and for the same start time, sort by either largest end time 
	  or smallest
	- Everytime you poll from priority queue, just make sure it doesn't intersect with 
	  previous interval. 


stats:

	- Time Complexity: O(C*logN), where N is the number of employees, and C is the number of 
	                   jobs across all employees. 
	                   The maximum size of the heap is N, so each push and pop operation is O(logN),
	                   and there are O(C) such operations.

	- Space Complexity: O(N) in additional space complexity.


    public List<Interval> employeeFreeTime(List<List<Interval>> avails) {

        List<Interval> result = new ArrayList<>();

        PriorityQueue<Interval> pq = new PriorityQueue<>((a, b) -> a.start - b.start);
        avails.forEach(e -> pq.addAll(e));

        Interval temp = pq.poll();
        while(!pq.isEmpty()) {
        	// current end time < next start time, add to result
            if(temp.end < pq.peek().start) { 
                result.add(new Interval(temp.end, pq.peek().start));
                temp = pq.poll(); 
            }

			// if this.end < next.end, directly move to the next interval
			// as current interval is contained in the next one.
            else { 
                temp = temp.end < pq.peek().end ? pq.peek() : temp;
                pq.poll();
            }
        }
        return result;
    }

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



