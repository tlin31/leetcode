2276. Count Integers in Intervals - Hard

Given an empty set of intervals, implement a data structure that can:

Add an interval to the set of intervals.
Count the number of integers that are present in at least one interval.
Implement the CountIntervals class:

CountIntervals() Initializes the object with an empty set of intervals.
void add(int left, int right) Adds the interval [left, right] to the set of intervals.
int count() Returns the number of integers that are present in at least one interval.
Note that an interval [left, right] denotes all the integers x where left <= x <= right.

 

Example 1:

Input
["CountIntervals", "add", "add", "count", "add", "count"]
[[], [2, 3], [7, 10], [], [5, 8], []]
Output
[null, null, null, 6, null, 8]

Explanation
CountIntervals countIntervals = new CountIntervals(); // initialize the object with an empty set of intervals. 
countIntervals.add(2, 3);  // add [2, 3] to the set of intervals.
countIntervals.add(7, 10); // add [7, 10] to the set of intervals.
countIntervals.count();    // return 6
                           // the integers 2 and 3 are present in the interval [2, 3].
                           // the integers 7, 8, 9, and 10 are present in the interval [7, 10].
countIntervals.add(5, 8);  // add [5, 8] to the set of intervals.
countIntervals.count();    // return 8
                           // the integers 2 and 3 are present in the interval [2, 3].
                           // the integers 5 and 6 are present in the interval [5, 8].
                           // the integers 7 and 8 are present in the intervals [5, 8] and [7, 10].
                           // the integers 9 and 10 are present in the interval [7, 10].
 

Constraints:

1 <= left <= right <= 109
At most 105 calls in total will be made to add and count.
At least one call will be made to count.


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

A standard List of intervals would take O(N) for every addition, leading to O(n^2) overall (Time Limit Exceeded). 
To pass, we use a Balanced BST or a TreeMap to keep intervals sorted by their start points


Algorithm: Segment Merging with Ordered Map
Maintain Disjoint Intervals: 
    Use a TreeMap (Java) or SortedDict (Python) to store intervals as [start, end].

Overlap Detection: When adding [left, right]:
    Find the interval starting just before or at left.
    Find all intervals that overlap with the new range.

Merge & Update:
    1. Combine all overlapping intervals into one single range.
    2. The new start is min(left, existing_starts).
    3. The new end is max(right, existing_ends).

Count Management: 
    Maintain a global count variable. Subtract the size of intervals you remove and add the size of the final merged interval. 





Stats:

    - 
    -

import java.util.TreeMap;

class CountIntervals {
    TreeMap<Integer, Integer> map;
    int count;

    public CountIntervals() {
        map = new TreeMap<>();
        count = 0;
    }

    public void add(int left, int right) {
        // Find existing intervals that could overlap
        // floorKey(left) gives the interval starting at or before 'left'
        Integer cur = map.floorKey(right);
        
        while (cur != null && map.get(cur) >= left) {
            int l = cur;
            int r = map.get(cur);
            
            // Merge coordinates and update global count
            left = Math.min(left, l);
            right = Math.max(right, r);
            count -= (r - l + 1);
            
            map.remove(cur);
            cur = map.floorKey(right);
        }
        
        map.put(left, right);
        count += (right - left + 1);
    }

    public int count() {
        return count;
    }
}





