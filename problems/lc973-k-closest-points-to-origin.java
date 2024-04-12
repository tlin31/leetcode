973. K Closest Points to Origin - Medium

We have a list of points on the plane.  Find the K closest points to the origin (0, 0).

(Here, the distance between two points on a plane is the Euclidean distance.)

You may return the answer in any order.  The answer is guaranteed to be unique (except for the order 
that it is in.)

 

Example 1:

Input: points = [[1,3],[-2,2]], K = 1
Output: [[-2,2]]
Explanation: 
The distance between (1, 3) and the origin is sqrt(10).
The distance between (-2, 2) and the origin is sqrt(8).
Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
We only want the closest K = 1 points from the origin, so the answer is just [[-2,2]].


Example 2:

Input: points = [[3,3],[5,-1],[-2,4]], K = 2
Output: [[3,3],[-2,4]]
(The answer [[-2,4],[3,3]] would also be accepted.)
 

Note:

1 <= K <= points.length <= 10000
-10000 < points[i][0] < 10000
-10000 < points[i][1] < 10000



******************************************************
key:
	- min heap OR quick select
	- edge case:
		1) empty list
		2) if already at origin

******************************************************



=======================================================================================================
method 1:

method:

	-  max-heap with size K. 
	-  max heap is always maintain top K smallest elements from the first one to crruent one. 
	

stats:

	-  O(NlogK)
	- 


public int[][] kClosest(int[][] points, int K) {
    PriorityQueue<int[]> pq = new PriorityQueue<int[]>((p1, p2) -> p2[0] * p2[0] + p2[1] * p2[1] - p1[0] * p1[0] - p1[1] * p1[1]);

    for (int[] p : points) {
        pq.offer(p);
        if (pq.size() > K) {
            pq.poll();
        }
    }

    int[][] res = new int[K][2];
    while (K > 0) {
        res[--K] = pq.poll();
    }
    return res;
}

=======================================================================================================
method 2:

method:

	-  quick sort/quick select based on RANK!
	-  each iteration, we choose a pivot and then find the position p the pivot should be. Then we 
	   compare p with the K, if the p is smaller than the K, meaning the all element on the left of 
	   the pivot are all proper candidates but it is not adequate, we have to do the same thing on 
	   right side, and vice versa. 
	-  If the p is exactly equal to the K, meaning that we have found the K-th position. Therefore, 
	   we just return the first K elements, since they are not greater than the pivot.
	-  quick select helper function return rank of the element, since it now knows
	-  


stats:

	- avg is O(n), but worst time O(N^2)
	- fastest on leetcode

public int[][] kClosest(int[][] points, int K) {
    int len =  points.length, l = 0, r = len - 1;
    while (l <= r) {
        int mid = helper(points, l, r);
        if (mid == K) break;
        if (mid < K) {
            l = mid + 1;
        } else {
            r = mid - 1;
        }
    }
    return Arrays.copyOfRange(points, 0, K);
}

private int helper(int[][] A, int l, int r) {
    int[] pivot = A[l];
    while (l < r) {
    	// right hand side, if right >= pivot, then decrements r until a misplace element
        while (l < r && compare(A[r], pivot) >= 0) r--;
        A[l] = A[r];

        // left hand side, if left <= pivot, increment l;
        while (l < r && compare(A[l], pivot) <= 0) l++;
        A[r] = A[l];
    }
    // reset to original pivot
    A[l] = pivot;
    return l;
}

private int compare(int[] p1, int[] p2) {
    return p1[0] * p1[0] + p1[1] * p1[1] - p2[0] * p2[0] - p2[1] * p2[1];
}


=======================================================================================================
method 3:

method:

	- Sort all points and return K first

	- 

stats:

	- O(NlogN)
	- 

  public int[][] kClosest(int[][] points, int K) {
        Arrays.sort(points, Comparator.comparing(p -> p[0] * p[0] + p[1] * p[1]));
        return Arrays.copyOfRange(points, 0, K);
    }

