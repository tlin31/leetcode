253. Meeting Rooms II - Medium

Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] 
(si < ei), find the minimum number of conference rooms required.

Example 1:
Input: [[0, 30],[5, 10],[15, 20]]
Output: 2

Example 2:
Input: [[7,10],[2,4]]
Output: 1

******************************************************
key:
	- check number of overlapped time intervals
	- edge case:
		1) empty list, return 0
		2) 1 interval, return 1

******************************************************



=======================================================================================================
method 1:

method:

	- Key idea is to sort all start/end points and then iterate increasing counter by 1 when open 
		is encountered and decreasing by 1 when we see an end.
	- a math way to "calculate" the conflicts

stats:

	- 
	- Runtime: 39 ms, faster than 16.42% of Java online submissions for Meeting Rooms II.
	- Memory Usage: 37.3 MB, less than 71.79% of

  	static class Point {
        int t, val;
        Point(int t, int openOrEnd) {
            this.t = t;
            this.openOrEnd = openOrEnd;
        }
    }

    public int minMeetingRooms(int[][] intervals) {
        List<Point> points = new ArrayList<>();

        //differentiate start & end by the val.
        for (int i = 0; i < intervals.length; i++) {
            points.add(new Point(intervals[i][0], 1));
            points.add(new Point(intervals[i][1], -1));
        }

        Collections.sort(points, (p1, p2) -> {
            // Sort by time and if time is equal, place interval ends before interval starts
            // so we can reuse freed up rooms right away.
            return p1.t - p2.t != 0 ? p1.t - p2.t : p1.openOrEnd - p2.openOrEnd;
        });

        int max = 0, 
            cur = 0;
        for (Point point : points) {
            cur += point.openOrEnd;
            if (cur > max) 
                max = cur;
        }
        return max;
    }
	


=======================================================================================================
method 2:

method:

	- Put all the interval numbers in a list with a indicator whether it is start or end.
	- sort the array with the numbers. You can think of this as the timeline.
	- Every time there is a meeting starting, put that number into the stack. When the meeting 
		ends, pop it from the stack. The stack represents the number of meetings currently held.
	- The minimum number of meeting rooms needed => max of the height of the stack it has every been.

stats:

	- Runtime: 92 ms, faster than 69.42% of Python3 online submissions for Meeting Rooms II.
	- Memory Usage: 17.3 MB, less than 5.41% of Python3 online submissions for Meeting Rooms II.


    def minMeetingRooms(self, intervals: List[List[int]]) -> int:
        if len(intervals) == 0:
            return 0
        
        timeline = []
        for start, end in intervals:
            timeline.append((start, True))
            timeline.append((end, False))
        
        timeline = sorted(timeline)
        
        stack = []
        max_height = 0

        for time, is_start in timeline:
            if is_start:
                stack.append(time)
            else:
                stack.pop()
            height = len(stack)
            if max_height < height:
                max_height = height

        return max_height

=======================================================================================================
method 3:

method:

	- use tree map
	- similar to method 1
    - The TreeMap in Java is used to implement Map interface and NavigableMap along with the 
      Abstract Class. The map is sorted according to the natural ordering of its keys, or by a 
      Comparator provided at map creation time, depending on which constructor is used. 
      This proves to be an efficient way of sorting and storing the key-value pairs. 
      The storing order maintained by the treemap must be consistent with equals just like any other sorted map, irrespective of the explicit comparators. The treemap implementation is not synchronized in the sense that if a map is accessed by multiple threads, concurrently and at least one of the threads modifies the map structurally, it must be synchronized externally. Some important features of the treemap are:



stats:

	- 
	- Runtime: 38 ms, faster than 25.86% of Java online submissions for Meeting Rooms II.
	- Memory Usage: 37.8 MB, less than 58.97% 

    public int minMeetingRooms(Interval[] intervals) {

        // Sort Key based on nature order
        Map<Integer, Integer> map = new TreeMap<Integer, Integer>(); 

        for (Interval i : intervals) {
            if (map.containsKey(i.start)) {
                map.put(i.start, map.get(i.start)+1);
            } else {
                map.put(i.start, 1);
            }
            if (map.containsKey(i.end)) {
                map.put(i.end, map.get(i.end)-1);
            } else {
                map.put(i.end, -1);
            }
        }

        int maxRoom = 0,
            curRoom = 0;

        for (int i : map.keySet()) {
            maxRoom = Math.max(maxRoom, curRoom += map.get(i));
        }

        return maxRoom;
    }




=======================================================================================================
method 4:

method:

	- we first create two events: Meeting Starts & Meeting Ends
	- Next, we acknowledge three facts:
		1. The numbers of the intervals give chronological orders
		2. When an ending event occurs, there must be a starting event has happened before that, 
			where “happen before” is defined by the chronological orders given by the intervals
		3. Meetings that started which have not ended yet have to be put into different meeting 
			rooms, and the number of rooms needed is the number of such meetings

	- for example, we have meetings that span along time as follows:

		|_____|
		      |______|
		|________|
		        |_______|

		Then, the start time array and end time array after sorting appear like follows:

		||    ||
		     |   |   |  |

	- Initially, endsItr points to the first end event, and we move i which is the start event 
		pointer. As we examine the start events, we’ll find the first two start events happen 
		before the end event that endsItr points to, so we need two rooms (we magically created 
		two rooms), as shown by the variable rooms. 
	- Then, as i points to the third start event, we’ll find that this event happens after the 
		end event pointed by endsItr, then we increment endsItr so that it points to the next 
		end event. What happens here can be thought of as one of the two previous meetings ended, 
		and we moved the newly started meeting into that vacant room, thus we don’t need to 
		increment rooms at this time and move both of the pointers forward.
	- Next, because endsItr moves to the next end event, we’ll find that the start event pointed by i 
		happens before the end event pointed by endsItr. Thus, now we have 4 meetings started but only 
		one ended, so we need one more room. And it goes on as this.


stats:

	- 

    public int minMeetingRooms(Interval[] intervals) {
        int[] starts = new int[intervals.length];
        int[] ends = new int[intervals.length];
        for(int i=0; i<intervals.length; i++) {
            starts[i] = intervals[i].start;
            ends[i] = intervals[i].end;
        }
        Arrays.sort(starts);
        Arrays.sort(ends);
        int rooms = 0;
        int endsItr = 0;
        for(int i=0; i<starts.length; i++) {
            if(starts[i]<ends[endsItr])
                rooms++;
            else
                endsItr++;
        }
        return rooms;
    }


