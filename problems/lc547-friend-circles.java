547. Friend Circles - Medium

There are N students in a classroom. Some of them are friends, while some are not. Their friendship is 
transitive in nature. For example, if A is a direct friend of B, and B is a direct friend of C, 
then A is an indirect friend of C. 

And we defined a friend circle is a group of students who are direct or indirect friends.

Given a N*N matrix M representing the friend relationship between students in the classroom. 
If M[i][j] = 1, then the ith and jth students are direct friends with each other, otherwise not. 
And you have to output the total number of friend circles among all the students.

Example 1:
Input: 
[[1,1,0],
 [1,1,0],
 [0,0,1]]
Output: 2
Explanation:The 0th and 1st students are direct friends, so they are in a friend circle. 
The 2nd student himself is in a friend circle. So return 2.

Example 2:
Input: 
[[1,1,0],
 [1,1,1],
 [0,1,1]]
Output: 1
Explanation:The 0th and 1st students are direct friends, the 1st and 2nd students are direct friends, 
so the 0th and 2nd students are indirect friends. All of them are in the same friend circle, so return 1.

Note:
N is in range [1,200].
M[i][i] = 1 for all students.
If M[i][j] = 1, then M[j][i] = 1.

******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	-  Union Find
	-  path compression and union by rank/size.
	   We can implement path compression in UF:find(), which makes the height of the subset tree 
	   <= 2.
	   We can implement union by rank/size in UF:union(), which speeds up path compression 
	   operation.
	   

stats:
	- Those optimations ensure the union() operation takes O(1)!
	- Runtime: 1 ms, faster than 100.00% of Java online submissions for Friend Circles.
	- Memory Usage: 44.5 MB, less than 48.00% 



class Solution {
    class UF {
        private int[] parent, rank;
        private int count;
        
        public UF(int n) {
            parent = new int[n];
            rank = new int[n];
            count = n;
            for (int i = 0; i < n; i++) {

            	// each has its own set.
                parent[i] = i;
                rank[i] = 1;
            }

        }
        
		public int find(int i) {
            // path compression
             if (parent[i] == i) {
                return i;
            } else { 
                // Recursively find the representative.
                int result = find(parent[i]);

                // We cache the result by moving i’s node directly under the representative of 
                // this set
                parent[i] = result;

                return result;
            }
            
        }
        
        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) 
                return;

            // union by rank, always attach to bigger/deeper tree
            if (rank[rootP] > rank[rootQ]) {
                parent[rootQ] = rootP;
                rank[rootP] += rank[rootQ];
            } else {
                parent[rootP] = rootQ;
                rank[rootQ] += rank[rootP];
            }
            count--;
        }
        
        public int count() { 
        	return count; 
        }
    }
    
    
    public int findCircleNum(int[][] M) {
        int n = M.length;
        UF uf = new UF(n);
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                if (M[i][j] == 1)
                    uf.union(i, j);
        return uf.count();
    }
}

=======================================================================================================
method 2:

method:

	- dfs
	- 

stats:

	- 
	- 

public class Solution {
    public int findCircleNum(int[][] M) {
        boolean[] visited = new boolean[M.length]; 
        int count = 0;
        for(int i = 0; i < M.length; i++) {
            if(!visited[i]) {
                dfs(M, visited, i);
                count++;
            }
        }
        return count;
    }
    private void dfs(int[][] M, boolean[] visited, int person) {
        for(int other = 0; other < M.length; other++) {
            if(M[person][other] == 1 && !visited[other]) {
                //We found an unvisited person in the current friend cycle 
                visited[other] = true;
                dfs(M, visited, other); //Start DFS on this new found person
            }
        }
    }
}

=======================================================================================================
method 3:

method:

	- bfs
	- 

stats:

	- Runtime: 9 ms, faster than 12.56% of Java online submissions for Friend Circles.
	- Memory Usage: 45.5 MB, less than 40.00% 


public int findCircleNum(int[][] M) {
    int count = 0;
    for (int i=0; i<M.length; i++)
        if (M[i][i] == 1) { 
        	count++; 
        	BFS(i, M); 
        }
    return count;
}

public void BFS(int student, int[][] M) {
    Queue<Integer> queue = new LinkedList<>();
    queue.add(student);
    while (queue.size() > 0) {
        int queueSize = queue.size();
        for (int i=0;i<queueSize;i++) {
            int j = queue.poll();
            M[j][j] = 2; // marks as visited
            for (int k=0;k<M[0].length;k++) 
                if (M[j][k] == 1 && M[k][k] == 1) queue.add(k);
        }
    }
}



