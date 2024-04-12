Geeks - Convex Hull | Set 1 (Jarvis’s Algorithm or Wrapping)

https://www.geeksforgeeks.org/convex-hull-set-1-jarviss-algorithm-or-wrapping/


Question:

Given a set of points in the plane. the convex hull of the set is the smallest convex polygon that 
contains all the points of it.

Method:
	- The idea of Jarvis’s Algorithm is simple, we start from the leftmost point (or point with minimum 
	  x coordinate value) and we keep wrapping points in counterclockwise direction. 

	- The big question is, given a point p as current point, how to find the next point in output? 
	  The idea is to use orientation() here.
	  Next point is selected as the point that beats all other points at counterclockwise orientation, 
	  i.e., next point is q if for any other point r, we have “orientation(p, q, r) = counterclockwise”. 

	- Following is the detailed algorithm.

		1) Initialize p as leftmost point.
		2) Do following while we don’t come back to the first (or leftmost) point.
		…..a) The next point q is the point such that the triplet (p, q, r) is counterclockwise for any 
     		  other point r.
		…..b) next[p] = q (Store q as next of p in the output convex hull).
		…..c) p = q (Set p as q for next iteration).

Complexity:
	- Time Complexity: O(m * n) where n = |input points|, m = |output or hull points| (m <= n). 

	  For every point on the hull we examine all the other points to determine the next point. 

	  In worst case, time complexity is O(n^2). The worst case occurs when all the points are on the hull (m = n)


import java.util.*; 

class Point 
{ 
	int x, y; 
	Point(int x, int y){ 
		this.x=x; 
		this.y=y; 
	} 
} 

class GFG { 
	
	// To find orientation of ordered triplet (p, q, r). 
	// The function returns following values 
	// 0 --> p, q and r are colinear 
	// 1 --> Clockwise 
	// 2 --> Counterclockwise 
	public static int orientation(Point p, Point q, Point r) 
	{ 
		int val = (q.y - p.y) * (r.x - q.x) - 
				(q.x - p.x) * (r.y - q.y); 
	
		if (val == 0) return 0; // collinear 
		return (val > 0)? 1: 2; // clock or counterclock wise 
	} 
	
	// Prints convex hull of a set of n points. 
	public static void convexHull(Point points[], int n) 
	{ 
		// There must be at least 3 points 
		if (n < 3) return; 
	
		// Initialize Result 
		Vector<Point> hull = new Vector<Point>(); 
	
		// Find the leftmost point 
		int l = 0; 
		for (int i = 1; i < n; i++) 
			if (points[i].x < points[l].x) 
				l = i; 
	
		// Start from leftmost point, keep moving 
		// counterclockwise until reach the start point 
		// again. This loop runs O(h) times where h is 
		// number of points in result or output. 
		int p = l, q; 
		do
		{ 
			// Add current point to result 
			hull.add(points[p]); 
	
			// Search for a point 'q' such that 
			// orientation(p, x, q) is counterclockwise 
			// for all points 'x'. The idea is to keep 
			// track of last visited most counterclock- 
			// wise point in q. If any point 'i' is more 
			// counterclock-wise than q, then update q. 
			q = (p + 1) % n; 
			
			for (int i = 0; i < n; i++) 
			{ 
			// If i is more counterclockwise than 
			// current q, then update q 
			if (orientation(points[p], points[i], points[q]) 
												== 2) 
				q = i; 
			} 
	
			// Now q is the most counterclockwise with 
			// respect to p. Set p as q for next iteration, 
			// so that q is added to result 'hull' 
			p = q; 
	
		} while (p != l); // While we don't come to first 
						// point 
	
		// Print Result 
		for (Point temp : hull) 
			System.out.println("(" + temp.x + ", " + 
								temp.y + ")"); 
	} 
	
	/* Driver program to test above function */
	public static void main(String[] args) 
	{ 

		Point points[] = new Point[7]; 
		points[0]=new Point(0, 3); 
		points[1]=new Point(2, 3); 
		points[2]=new Point(1, 1); 
		points[3]=new Point(2, 1); 
		points[4]=new Point(3, 0); 
		points[5]=new Point(0, 0); 
		points[6]=new Point(3, 3); 
		
		int n = points.length; 
		convexHull(points, n); 
		
	} 
} 
	
Output: The output is points of the convex hull.
(0, 3)
(0, 0)
(3, 0)
(3, 3)

================================================================================================
Method 2 --> O(n * logn)

https://www.geeksforgeeks.org/convex-hull-set-2-graham-scan/


Graham’s algorithm

Method:

Let points[0..n-1] be the input array.

1) Find the bottom-most point by comparing y coordinate of all points. If there are two points with the same y value, then the point with smaller x coordinate value is considered. Let the bottom-most point be P0. Put P0 at first position in output hull.

2) Consider the remaining n-1 points and sort them by polar angle in counterclockwise order around points[0]. If the polar angle of two points is the same, then put the nearest point first.

3 After sorting, check if two or more points have the same angle. If two more points have the same angle, then remove all same angle points except the point farthest from P0. Let the size of the new array be m.

4) If m is less than 3, return (Convex Hull not possible)

5) Create an empty stack ‘S’ and push points[0], points[1] and points[2] to S.

6) Process remaining m-3 points one by one. Do following for every point ‘points[i]’
        4.1) Keep removing points from stack while orientation of following 3 points is not counterclockwise (or they don’t make a left turn).
            a) Point next to top in stack
            b) Point at the top of stack
            c) points[i]
         4.2) Push points[i] to S

5) Print contents of S

The above algorithm can be divided into two phases.


Phase 1 (Sort points): We first find the bottom-most point. The idea is to pre-process points be sorting them with respect to the bottom-most point. Once the points are sorted, they form a simple closed path (See the following diagram).

What should be the sorting criteria? computation of actual angles would be inefficient since trigonometric functions are not simple to evaluate. The idea is to use the orientation to compare angles without actually computing them (See the compare() function below)

Phase 2 (Accept or Reject Points): Once we have the closed path, the next step is to traverse the path and remove concave points on this path. How to decide which point to remove and which to keep? Again, orientation helps here. The first two points in sorted array are always part of Convex Hull. For remaining points, we keep track of recent three points, and find the angle formed by them. Let the three points be prev(p), curr(c) and next(n). If orientation of these points (considering them in same order) is not counterclockwise, we discard c, otherwise we keep it. Following diagram shows step by step process of this phase


// A C++ program to find convex hull of a set of points. Refer 
// https://www.geeksforgeeks.org/orientation-3-ordered-points/ 
// for explanation of orientation() 
#include <iostream> 
#include <stack> 
#include <stdlib.h> 
using namespace std; 

struct Point 
{ 
	int x, y; 
}; 

// A global point needed for sorting points with reference 
// to the first point Used in compare function of qsort() 
Point p0; 

// A utility function to find next to top in a stack 
Point nextToTop(stack<Point> &S) 
{ 
	Point p = S.top(); 
	S.pop(); 
	Point res = S.top(); 
	S.push(p); 
	return res; 
} 

// A utility function to swap two points 
int swap(Point &p1, Point &p2) 
{ 
	Point temp = p1; 
	p1 = p2; 
	p2 = temp; 
} 

// A utility function to return square of distance 
// between p1 and p2 
int distSq(Point p1, Point p2) 
{ 
	return (p1.x - p2.x)*(p1.x - p2.x) + 
		(p1.y - p2.y)*(p1.y - p2.y); 
} 

// To find orientation of ordered triplet (p, q, r). 
// The function returns following values 
// 0 --> p, q and r are colinear 
// 1 --> Clockwise 
// 2 --> Counterclockwise 
int orientation(Point p, Point q, Point r) 
{ 
	int val = (q.y - p.y) * (r.x - q.x) - 
			(q.x - p.x) * (r.y - q.y); 

	if (val == 0) return 0; // colinear 
	return (val > 0)? 1: 2; // clock or counterclock wise 
} 

// A function used by library function qsort() to sort an array of 
// points with respect to the first point 
int compare(const void *vp1, const void *vp2) 
{ 
Point *p1 = (Point *)vp1; 
Point *p2 = (Point *)vp2; 

// Find orientation 
int o = orientation(p0, *p1, *p2); 
if (o == 0) 
	return (distSq(p0, *p2) >= distSq(p0, *p1))? -1 : 1; 

return (o == 2)? -1: 1; 
} 

// Prints convex hull of a set of n points. 
void convexHull(Point points[], int n) 
{ 
// Find the bottommost point 
int ymin = points[0].y, min = 0; 
for (int i = 1; i < n; i++) 
{ 
	int y = points[i].y; 

	// Pick the bottom-most or chose the left 
	// most point in case of tie 
	if ((y < ymin) || (ymin == y && 
		points[i].x < points[min].x)) 
		ymin = points[i].y, min = i; 
} 

// Place the bottom-most point at first position 
swap(points[0], points[min]); 

// Sort n-1 points with respect to the first point. 
// A point p1 comes before p2 in sorted output if p2 
// has larger polar angle (in counterclockwise 
// direction) than p1 
p0 = points[0]; 
qsort(&points[1], n-1, sizeof(Point), compare); 

// If two or more points make same angle with p0, 
// Remove all but the one that is farthest from p0 
// Remember that, in above sorting, our criteria was 
// to keep the farthest point at the end when more than 
// one points have same angle. 
int m = 1; // Initialize size of modified array 
for (int i=1; i<n; i++) 
{ 
	// Keep removing i while angle of i and i+1 is same 
	// with respect to p0 
	while (i < n-1 && orientation(p0, points[i], 
									points[i+1]) == 0) 
		i++; 


	points[m] = points[i]; 
	m++; // Update size of modified array 
} 

// If modified array of points has less than 3 points, 
// convex hull is not possible 
if (m < 3) return; 

// Create an empty stack and push first three points 
// to it. 
stack<Point> S; 
S.push(points[0]); 
S.push(points[1]); 
S.push(points[2]); 

// Process remaining n-3 points 
for (int i = 3; i < m; i++) 
{ 
	// Keep removing top while the angle formed by 
	// points next-to-top, top, and points[i] makes 
	// a non-left turn 
	while (orientation(nextToTop(S), S.top(), points[i]) != 2) 
		S.pop(); 
	S.push(points[i]); 
} 

// Now stack has the output points, print contents of stack 
while (!S.empty()) 
{ 
	Point p = S.top(); 
	cout << "(" << p.x << ", " << p.y <<")" << endl; 
	S.pop(); 
} 
} 

// Driver program to test above functions 
int main() 
{ 
	Point points[] = {{0, 3}, {1, 1}, {2, 2}, {4, 4}, 
					{0, 0}, {1, 2}, {3, 1}, {3, 3}}; 
	int n = sizeof(points)/sizeof(points[0]); 
	convexHull(points, n); 
	return 0; 
} 









