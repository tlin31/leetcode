939. Minimum Area Rectangle - Medium


Given a set of points in the xy-plane, determine the minimum area of a rectangle formed from 
these points, with sides parallel to the x and y axes.

If there is not any rectangle, return 0.

 

Example 1:

Input: [[1,1],[1,3],[3,1],[3,3],[2,2]]
Output: 4

Example 2:
Input: [[1,1],[1,3],[3,1],[3,3],[4,1],[4,3]]
Output: 2
 

Note:

1 <= points.length <= 500
0 <= points[i][0] <= 40000
0 <= points[i][1] <= 40000
All points are distinct.



******************************************************
key:
	- Hashmap
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:
	-	Count each rectangle by right-most edge.
	-	Group the points by x coordinates, so that we have columns of points. 
		Then, for every pair of points in a column (with coordinates (x,y1) and (x,y2)), check 
		for the smallest rectangle with this pair of points as the rightmost edge. 

	-	!! Key: use a coded value 40001*y1+y2 to make the height of rectangle be the key of the hashmap,
		     only if there contains another pair which results in the same code, then it forms a parallel
		     rectangle!
	- 	In addition, since x is sorted and we go from left to right, for the same height, the area
		of possible rectangle will only be smaller and smaller. Ex. example 2, column 1 & column 3 & c4


Note:
	-	Treemap: computeIfAbsent(Key, Function) to compute value for a given key using the given 
	    mapping function, if key is not already associated with a value (or is mapped to null) 
	    and enter that computed value in Hashmap else null.

	-	computeIfAbsent(K key,Function<? super K, ? extends V> remappingFunction)

Stats:

	- Time: O(n^2)
	- Space: O(n)

class Solution {
    public int minAreaRect(int[][] points) {
        Map<Integer, List<Integer>> rows = new TreeMap();
        for (int[] point: points) {
            int x = point[0], y = point[1];
            rows.computeIfAbsent(x, z-> new ArrayList()).add(y);
        }

        int ans = Integer.MAX_VALUE;
        Map<Integer, Integer> lastx = new HashMap();
        for (int x: rows.keySet()) {
            List<Integer> row = rows.get(x);
            Collections.sort(row);

            // every possible pair combinations of y coordinates
            for (int i = 0; i < row.size(); ++i)
                for (int j = i+1; j < row.size(); ++j) {
                    int y1 = row.get(i), y2 = row.get(j);
                    int code = 40001 * y1 + y2;
                    if (lastx.containsKey(code))
                        ans = Math.min(ans, (x - lastx.get(code)) * (y2-y1));
                    lastx.put(code, x);
                }
        }

        return ans < Integer.MAX_VALUE ? ans : 0;
    }
}


=======================================================================================================
method 2:

Method:
	-	For each pair of points in the array, consider them to be the long diagonal of a 
	    potential rectangle. We can check if all 4 points are there using a Set.

		For example, if the points are (1, 1) and (5, 5), we check if we also have (1, 5) and (5, 1). 
		If we do, we have a candidate rectangle.

	-	Put all the points in a set. For each pair of points, if the associated rectangle are 4 distinct
		points all in the set, then take the area of this rectangle as a candidate answer.


Stats:

	- Runtime: 166 ms, faster than 79.00% of Java online submissions for Minimum Area Rectangle.
	- Memory Usage: 41.8 MB, less than 100.00% of Java online submissions for Minimum Area Rectangle.

class Solution {
    public int minAreaRect(int[][] points) {
        int max = 40000;
        int res = Integer.MAX_VALUE;
        Set<Integer> set = new HashSet<>();
        int n = points.length;

        // only check the diagnals
        for (int i = 0; i < n; i++){
            for (int j = 0; j < i; j++){
            	// if same point
                if (points[i][0] == points[j][0] && points[i][1] == points[j][1]){
                    continue;
                }

                // if the other 2 points are also in the set already
                if (set.contains(points[i][0] * max + points[j][1]) && set.contains(points[j][0] * max + points[i][1])){
                    int area = Math.abs(points[i][0] - points[j][0]) * Math.abs(points[i][1] - points[j][1]);
                    res = Math.min(res, area);
                }
            }

            // stores the diagnal
            set.add(points[i][0] * max + points[i][1]);
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }
}
=======================================================================================================
method 3:

Method:



Stats:

	- 
	- 



