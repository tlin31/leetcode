56. Merge Intervals - Medium

Given a collection of intervals, merge all overlapping intervals.

Example 1:

Input: [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].


Example 2:
Input: [[1,4],[4,5]]
Output: [[1,5]]
Explanation: Intervals [1,4] and [4,5] are considered overlapping.
NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to 
get new method signature.

******************************************************
key:
    - are arrays sorted? if not, sort based on first element

	- method 1: two pointers, keep start and end
	- method 2: use graph
    - method 3: use StackTraceElement

	- edge case:
	1) empty array
	2) negative or zero --> doesnt matter
	3) 

******************************************************



=======================================================================================================
method 1:

method:

	- sort the intervals by their starting points, then take the first interval and compare the end 
		with the next intervals starts. As long as they overlap, we update the end to be the max 
		end of the overlapping intervals. Once we find a non overlapping interval, we can add the 
		previous "extended" interval and start over.
	- use of comparator! 
	- !! if use the new object <interval>, need to know how to write its comparator

stats:

	- 时间复杂度：O（n log（n））--> Sorting takes O(n log(n)) and merging the intervals takes O(n)
	- 空间复杂度：O（n），存储结果。另外排序算法也可能需要。
	- Runtime: 7 ms, faster than 75.97% of Java online submissions for Merge Intervals.
	- Memory Usage: 36.9 MB, less than 100.00% 


	private class IntervalComparator implements Comparator<Interval> {
	    @Override
	    public int compare(Interval a, Interval b) {
	        return a.start < b.start ? -1 : a.start == b.start ? 0 : 1;
	    }
	}

	public List<Interval> merge(List<Interval> intervals) {
	    Collections.sort(intervals, new IntervalComparator());

	    LinkedList<Interval> merged = new LinkedList<Interval>();
	    for (Interval interval : intervals) {

	        //最开始是空的，直接加入
	        //然后对应情况 1，新加入的节点的左端点大于最后一个节点的右端点
	        if (merged.isEmpty() || merged.getLast().end < interval.start) {
	            merged.add(interval);
	        }

	        //对于情况 2 ，set the next end
	        else {

	            merged.getLast().end = Math.max(merged.getLast().end, interval.end);
	        }
	    }

	    return merged;
	}


=======================================================================================================
method 1.5:
	- use int[][] instead of list<intervals>

    public int[][] merge(int[][] intervals) {
        
        // Sort intervals
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[0], b[0]);
            }
        });

        // Arrays.sort(intervals, (i1, i2) -> Integer.compare(i1[0], i2[0]));

        
        // Iterate through intervals
        List<int[]> res = new ArrayList<>();
        int[] prev = {Integer.MIN_VALUE, Integer.MIN_VALUE};

        for (int[] cur : intervals) {
            
            // Overlapping -> Merge
            if (cur[0] <= prev[1]) {
                if (prev[1] < cur[1]) {
                    prev[1] = cur[1];
                }
                
                if (res.size()>0){
                    res.remove(res.size() - 1);
                    res.add(prev);
                }
                
            // Non overlapping -> Add to res
            } else {
                res.add(cur);
                prev = cur;
            }
        }
        
        // Return as an array
        return res.toArray(new int[res.size()][]);
    }


=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- Runtime: 36 ms, faster than 55.04% of Java online submissions for Merge Intervals.
	- Memory Usage: 43.3 MB, less than 44.21%


class Solution {
    public int[][] merge(int[][] intervals) {
        if(intervals.length <= 1)
            return intervals;
        
        Arrays.sort(intervals, (i1, i2) -> Integer.compare(i1[0], i2[0]));
        
        List<int[]> result = new ArrayList<>();
        int[] newInterval = intervals[0];
        result.add(newInterval);
        for(int[] interval : intervals){

        	// this interval's end is greater than next interval's start
            if(newInterval[1] >= interval[0])
                newInterval[1] = Math.max(newInterval[1], interval[1]);

            else{
            	//update interval
                newInterval = interval;
                result.add(newInterval);
            }
        }
        return result.toArray(new int[result.size()][]);
    }
}
=======================================================================================================
method 3:

method:

	- use graph + adjacency list
	- If we draw a graph (with intervals as nodes) that contains undirected edges between all pairs 
		of intervals that overlap, then all intervals in each connected component of the graph can 
		be merged into a single interval.
	- With the above intuition in mind, we can represent the graph as an adjacency list, inserting 
		directed edges in both directions to simulate undirected edges. 
	- Then, to determine which connected component each node is it, we perform graph traversals from 
		arbitrary unvisited nodes until all nodes have been visited. To do this efficiently, we store 
		visited nodes in a Set, allowing for constant time to checks and insertion. 
	- Finally, we consider each connected component, merging all of its intervals by constructing a 
		new Interval with start equal to the minimum start among them and end equal to the maximum 
		end.

stats:

	- Time complexity : O(n^2)
		Building the graph costs O(V + E) = O(V) + O(E) = O(n) + O(n^2) = O(n^2)O(V+E)=O(V)+O(E)=
		O(n)+O(n^2)=O(n^2) time, as in the worst case all intervals are mutually overlapping. 
		Traversing the graph has the same cost (although it might appear higher at first) because 
		our visited set guarantees that each node will be visited exactly once. Finally, because 
		each node is part of exactly one component, the merge step costs O(V) = O(n)O(V)=O(n) time. 

	- Space complexity : O(n^2)
		worst case, all intervals are mutually overlapping, so there will be an edge for every pair 
		of intervals. Therefore, the memory footprint is quadratic in the input size.


	class Solution {
    private Map<Interval, List<Interval> > graph;
    private Map<Integer, List<Interval> > nodesInComp;
    private Set<Interval> visited;

    // return whether two intervals overlap (inclusive)
    private boolean overlap(Interval a, Interval b) {
        return a.start <= b.end && b.start <= a.end;
    }

    // build a graph where an undirected edge between intervals u and v exists
    // iff u and v overlap.
    private void buildGraph(List<Interval> intervals) {
        graph = new HashMap<>();
        for (Interval interval : intervals) {
            graph.put(interval, new LinkedList<>());
        }

        //O(n^2)
        for (Interval interval1 : intervals) {
            for (Interval interval2 : intervals) {
                if (overlap(interval1, interval2)) {
                	// add in both intervalc b/c is undirected graph
                    graph.get(interval1).add(interval2);
                    graph.get(interval2).add(interval1);
                }
            }
        }
    }

    // merges all of the nodes in this connected component into one interval.
    private Interval mergeNodes(List<Interval> nodes) {
        int minStart = nodes.get(0).start;
        for (Interval node : nodes) {
            minStart = Math.min(minStart, node.start);
        }

        int maxEnd = nodes.get(0).end;
        for (Interval node : nodes) {
            maxEnd= Math.max(maxEnd, node.end);
        }

        return new Interval(minStart, maxEnd);
    }

    // use depth-first search to mark all nodes in the same connected component
    // with the same integer.
    private void markComponentDFS(Interval start, int compNumber) {
        Stack<Interval> stack = new Stack<>();
        stack.add(start);

        while (!stack.isEmpty()) {
            Interval node = stack.pop();
            if (!visited.contains(node)) {
                visited.add(node);

                if (nodesInComp.get(compNumber) == null) {
                    nodesInComp.put(compNumber, new LinkedList<>());
                }
                nodesInComp.get(compNumber).add(node);

                for (Interval child : graph.get(node)) {
                    stack.add(child);
                }
            }
        }
    }

    // gets the connected components of the interval overlap graph.
    private void buildComponents(List<Interval> intervals) {
        nodesInComp = new HashMap();
        visited = new HashSet();
        int compNumber = 0;

        for (Interval interval : intervals) {
            if (!visited.contains(interval)) {
                markComponentDFS(interval, compNumber);
                compNumber++;
            }
        }
    }

    public List<Interval> merge(List<Interval> intervals) {
        buildGraph(intervals);
        buildComponents(intervals);

        // for each component, merge all intervals into one interval.
        List<Interval> merged = new LinkedList<>();
        for (int comp = 0; comp < nodesInComp.size(); comp++) {
            merged.add(mergeNodes(nodesInComp.get(comp)));
        }

        return merged;
    }
}


========================================================================================================================
stack

class Solution {
    // 8 Jaynary 2023
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a1,a2)-> a1[0]-a2[0]); // O(NlogN)

        Stack<int[]> stack = new Stack<>(); //O(N)
        stack.push(intervals[0]);
        int i=1;
        while(!stack.isEmpty() && i < intervals.length){ //O(N)
            int[] current = stack.peek();
            int[] next = intervals[i++];
            if(current[1]>=next[0]){
                stack.pop();
                current[1] = Math.max(current[1], next[1]);
                stack.push(current);
            }else{
                stack.push(next);
            }
        }

        return stack.toArray(new int[stack.size()][2]);
    }
}
