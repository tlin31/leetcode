835. Image Overlap - Medium

Two images A and B are given, represented as binary, square matrices of the same size.  
(A binary matrix has only 0s and 1s as values.)

We translate one image however we choose (sliding it left, right, up, or down any number of units), 
and place it on top of the other image.  After, the overlap of this translation is the number of 
positions that have a 1 in both images.

(Note also that a translation does not include any kind of rotation.)

What is the largest possible overlap?

Example 1:

Input: A = [[1,1,0],
            [0,1,0],
            [0,1,0]]

       B = [[0,0,0],
            [0,1,1],
            [0,0,1]]

Output: 3
Explanation: We slide A to right by 1 unit and down by 1 unit.
Notes: 

1 <= A.length = A[0].length = B.length = B[0].length <= 30
0 <= A[i][j], B[i][j] <= 1



******************************************************
key:
	- Hashmap --> key = difference between two coordinates
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	Assume index in A and B is [0, N * N -1].

	Loop on A, if value == 1, save a coordinates i / N * 100 + i % N to LA.
	Loop on B, if value == 1, save a coordinates i / N * 100 + i % N to LB.

	Loop on combination (i, j) of LA and LB, increase count[i - j] by 1.
	If we slide to make A[i] orverlap B[j], we can get 1 point.
	Loop on count and return max values.
	
	Note:	
		Q: How big is enough?
		   Bigger than 2N - 1.

Example:
	Assume A and B are 3 * 3 matrix.:
	A:
	1,0,1
	1,0,0
	1,1,1
	B:
	0,0,1
	0,1,1
	1,1,1
	Flatten each of them to 1-D array:
	flattened idx: 0,1,2,3,4,5,6,7,8
	flattened A: 1,0,1,1,0,0,1,1,1 -> 0,2,3,6,7,8 : LA
	flattened B: 0,0,1,0,1,1,1,1,1 -> 2,4,5,6,7,8 : LB
	Each '1' in A can be overlapped with each '1' in B for different offset.
	Iterate through every overlap situation for '1' (at i) in LA and '1' (at j) in LB, group by 
		offset (i - j).
	Final step is to find the largest number of overlapped '1's among all offsets.

Stats:

	- Time:O(AB + N^2), Assume A the number of points in the image A, B the number of points in 
						the image B, N = A.length = B.length. O(N^2) time for preparing,and O(AB) 
						time for loop.

	- Space: O(A + B)
	- Runtime: 60 ms, faster than 58.02% of Java online submissions for Image Overlap.
	- Memory Usage: 41.9 MB, less than 44.44%



    public int largestOverlap(int[][] A, int[][] B) {
        int N = A.length;
        List<Integer> LA = new ArrayList<>(),  
        			  LB = new ArrayList<>();
        HashMap<Integer, Integer> count = new HashMap<>();

        for (int i = 0; i < N * N; ++i)
            if (A[i / N][i % N] == 1)
                LA.add(i / N * 100 + i % N);

        for (int i = 0; i < N * N; ++i)
            if (B[i / N][i % N] == 1)
                LB.add(i / N * 100 + i % N);

        for (int i : LA) 
        	for (int j : LB)
                count.put(i - j, count.getOrDefault(i - j, 0) + 1);


        int res = 0;
        for (int i : count.values())
            res = Math.max(res, i);

        return res;
    }


=======================================================================================================
method 2:

Method:
	-Brute force, padding

	Expand A from a 3x3 to a 9x9 graph and fill the blank with 0, then try every possible postion 
	using B. Considering n<=30, it is not the best but still acceptable.


Stats:

	- If we do brute force, we have 2N horizontal possible sliding, 2N vertical sliding and N^2 to count overlap area.
	- We get O(N^4) solution and it may get accepted.
	- waste time on sparse matrix

class Solution {
    public int largestOverlap(int[][] A, int[][] B) {
        int n=A.length, res=0;
        int[][] Ap= new int[3*n][3*n];

        // padding 
        for (int i=n; i<2*n; i++){
            for (int j=n; j<2*n; j++){
                Ap[i][j]=A[i-n][j-n];
            }
        }
        for (int i=0; i<=2*n; i++){
            for (int j=0; j<=2*n; j++){
                res=Math.max(res, helper(Ap, B, i, j));
            }
        }
        return res;
    }
    public int helper(int[][] A, int[][] B, int x, int y){
        int n=B.length, sum=0;

        // loop through all possibilities
        // sum == 1 only if B and A both are 1.
        for (int i=0; i<n; i++){
            for (int j=0; j<n; j++){
                sum+=B[i][j]*A[x+i][y+j];
            }
        }
        return sum;
    }
}
=======================================================================================================
method 3:

Method:



Stats:

	- 
	- 



