130. Surrounded Regions- Medium

You are given an m x n matrix board containing letters 'X' and 'O', capture regions that 
are surrounded:

Connect: A cell is connected to adjacent cells horizontally or vertically.
Region: To form a region connect every 'O' cell.
Surround: The region is surrounded with 'X' cells if you can connect the region with 'X' cells 
and none of the region cells are on the edge of the board.
A surrounded region is captured by replacing all 'O's with 'X's in the input matrix board.

 

Example 1:

Input: board = [
	["X","X","X","X"],
	["X","O","O","X"],
	["X","X","O","X"],
	["X","O","X","X"]]

Output: [
	["X","X","X","X"],
	["X","X","X","X"],
	["X","X","X","X"],
	["X","O","X","X"]]

Explanation:


In the above diagram, the bottom region is not captured because it is on the edge of the 
board and cannot be surrounded.

Example 2:

Input: board = [["X"]]

Output: [["X"]]

Constraints:

m == board.length
n == board[i].length
1 <= m, n <= 200
board[i][j] is 'X' or 'O'.
******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:dfs

Method:

	-从边界的 O 做 DFS，然后把遇到的 O 都标记一下，这些 O 就是可以连通到边界的。然后把边界的所有的 O 都做一次 DFS
	把 DFS 过程的中的 O 做一下标记。最后我们只需要遍历节点，把没有标记过的 O 改成 X 就可以了。

	标记的话，我们可以用一个 visited 二维数组，把访问过的置为 true 



Stats:

	- 
	- 
 

class Solution {
    public void solve(char[][] board) {
        if (board.length == 0 || board[0].length == 0) return;
        if (board.length < 3 || board[0].length < 3) return;

        int m = board.length;
        int n = board[0].length;

		//Any 'O' connected to a boundary can't be turned to 'X'

		//Start from first and last column, turn 'O' to '*'.
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') helper(board, i, 0);
            if (board[i][n - 1] == 'O') helper(board, i, n - 1);
        }

		//Start from first and last row, turn '0' to '*'
        for (int j = 1; j < n - 1; j++) {
            if (board[0][j] == 'O') helper(board, 0, j);
            if (board[m - 1][j] == 'O') helper(board, m - 1, j);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X';
                if (board[i][j] == '*') board[i][j] = 'O';
            }
        }
    }
    
    //Use DFS algo to turn internal however boundary-connected 'O' to '*';
    private void helper(char[][] board, int r, int c) {
        if (r < 0 || c < 0 || r > board.length - 1 || c > board[0].length - 1 || board[r][c] != 'O') 
        	return;
        board[r][c] = '*';
        helper(board, r + 1, c);
        helper(board, r - 1, c);
        helper(board, r, c + 1);
        helper(board, r, c - 1);
    }
}


===================================================================================================
Method 2: union find

public class Solution {
    int rows, cols;

    public void solve(char[][] board) {
        if(board == null || board.length == 0) return;

        rows = board.length;
        cols = board[0].length;

        //多申请一个空间
        UnionFind uf = new UnionFind(rows * cols + 1);
        //所有边界的 O 节点都和 dummy 节点合并
        int dummyNode = rows * cols;

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(board[i][j] == 'O') {
                    //当前节点在边界就和 dummy 合并
                    if(i == 0 || i == rows-1 || j == 0 || j == cols-1) {
                        uf.union( dummyNode,node(i,j));
                    }
                    else {
                        //将上下左右的 O 节点和当前节点合并
                        if(board[i-1][j] == 'O')  uf.union(node(i,j), node(i-1,j));
                        if(board[i+1][j] == 'O')  uf.union(node(i,j), node(i+1,j));
                        if(board[i][j-1] == 'O')  uf.union(node(i,j), node(i, j-1));
                        if(board[i][j+1] == 'O')  uf.union(node(i,j), node(i, j+1));
                    }
                }
            }
        }

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                //判断是否和 dummy 节点是一类
                if(uf.isConnected(node(i,j), dummyNode)) {
                    board[i][j] = 'O';
                }
                else {
                    board[i][j] = 'X';
                }
            }
        }
    }

    int node(int i, int j) {
        return i * cols + j;
    }
}

class UnionFind {
    int [] parents;
    public UnionFind(int totalNodes) {
        parents = new int[totalNodes];
        for(int i = 0; i < totalNodes; i++) {
            parents[i] = i;
        }
    }

    void union(int node1, int node2) {
        int root1 = find(node1);
        int root2 = find(node2);
        if(root1 != root2) {
            parents[root2] = root1;
        }
    }

    int find(int node) {
        while(parents[node] != node) {
            parents[node] = parents[parents[node]];
            node = parents[node];
        }
        return node;
    }

    boolean isConnected(int node1, int node2) {
        return find(node1) == find(node2);
    }
}

