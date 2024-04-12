1329. Sort the Matrix Diagonally - Medium


Given a m * n matrix mat of integers, sort it diagonally in ascending order from the top-left to 
the bottom-right then return the sorted array.

 

Example 1:


Input: mat = [[3,3,1,1],[2,2,1,2],[1,1,1,2]]
Output: [[1,1,1,1],[1,2,2,2],[1,2,3,3]]
 

Constraints:

m == mat.length
n == mat[i].length
1 <= m, n <= 100
1 <= mat[i][j] <= 100


******************************************************
key:
	- priority queue or bubble sort
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time O(MNlogD), where D is the length of diagonal with D = min(M,N).
	- Space O(MN)



Method:

	- A[i][j] on the same diagonal have same value of i - j
	  For each diagonal,put its elements together, sort, and set them back.
	  

    public int[][] diagonalSort(int[][] A) {
        int m = A.length, n = A[0].length;
        HashMap<Integer, PriorityQueue<Integer>> d = new HashMap<>();

        // sort
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                d.putIfAbsent(i - j, new PriorityQueue<>());
                d.get(i - j).add(A[i][j]);
            }
        }

        // form the output
        for (int i = 0; i < m; ++i)
            for (int j = 0; j < n; ++j)
                A[i][j] = d.get(i - j).poll();

        return A;
    }






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def diagonalSort(self, A):
        n, m = len(A), len(A[0])
        d = collections.defaultdict(list)
        for i in xrange(n):
            for j in xrange(m):
                d[i - j].append(A[i][j])
        for k in d:
            d[k].sort(reverse=1)
        for i in xrange(n):
            for j in xrange(m):
                A[i][j] = d[i - j].pop()
        return A



=======================================================================================================
method 2: bubble sort

Stats:

	- Time: O(M^2*N); Space: O(M*N)
	- 


Method:

	-	

class Solution { 
public: 
    vector<vector<int>> diagonalSort(vector<vector<int>>& mat){
        bool isSwapped;
        for(int k = mat.size(); k--; ){
            for(int i = 0; i < k; i++)
                for(int j = 0; j + 1 < mat[i].size(); j++)
                    if(mat[i][j] > mat[i + 1][j + 1]) {
                        swap(mat[i][j], mat[i + 1][j + 1]);
                        isSwapped = true;   
                    }

            if(isSwapped==false) 
            	break;
        }
        return mat;
    }
};








~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

