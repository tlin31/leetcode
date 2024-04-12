773. Sliding Puzzle - Hard

On a 2x3 board, there are 5 tiles represented by the integers 1 through 5, and an empty square 
represented by 0.

A move consists of choosing 0 and a 4-directionally adjacent number and swapping it.

The state of the board is solved if and only if the board is [[1,2,3],[4,5,0]].

Given a puzzle board, return the least number of moves required so that the state of the board is 
solved. If it is impossible for the state of the board to be solved, return -1.

Examples:

Input: board = [[1,2,3],[4,0,5]]
Output: 1
Explanation: Swap the 0 and the 5 in one move.

Input: board = [[1,2,3],[5,4,0]]
Output: -1
Explanation: No number of moves will make the board solved.

Input: board = [[4,1,2],[5,0,3]]
Output: 5
Explanation: 5 is the smallest number of moves that solves the board.
An example path:
After move 0: [[4,1,2],[5,0,3]]
After move 1: [[4,1,2],[0,5,3]]
After move 2: [[0,1,2],[4,5,3]]
After move 3: [[1,0,2],[4,5,3]]
After move 4: [[1,2,0],[4,5,3]]
After move 5: [[1,2,3],[4,5,0]]

Input: board = [[3,2,4],[1,5,0]]
Output: 14



Note:
board will be a 2 x 3 array as described above.
board[i][j] will be a permutation of [0, 1, 2, 3, 4, 5].
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

	- BFS
	- Consider each state in the board as a graph node, we just need to find out the min distance 
	  between start node and final target node "123450". Since it is a single point to single 
	  point questions, Dijkstra is not needed here. 

	  We use BFS, and count the level we passed. Every time we swap 0 position in the String to 
	  find the next state. Use a hashTable to store the visited states.
	- because the properties of HashSet, every step is unrepeatable and optimization,


stats:

	- Runtime: 5 ms, faster than 84.86% of Java online submissions for Sliding Puzzle.
	- Memory Usage: 35.7 MB, less than 100.00%


public int slidingPuzzle(int[][] board) {
        String target = "123450";
        String start = "";

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                start += board[i][j];
            }
        }

        HashSet<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        // all the positions 0 can be swapped to, index = position of 0 in [0, 1,2,3,4,5]
        // 
        int[][] dirs = new int[][] { { 1, 3 }, { 0, 2, 4 },
                { 1, 5 }, { 0, 4 }, { 1, 3, 5 }, { 2, 4 } };

        queue.offer(start);
        visited.add(start);
        int res = 0;
        while (!queue.isEmpty()) {

            // level count, has to use size control here, otherwise not needed
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String cur = queue.poll();
                if (cur.equals(target)) {
                    return res;
                }

                int zero = cur.indexOf('0');
                // swap if possible
                for (int dir : dirs[zero]) {
                    String next = swap(cur, zero, dir);
                    if (visited.contains(next)) {
                        continue;
                    }
                    visited.add(next);
                    queue.offer(next);

                }
            }
            res++;
        }
        return -1;
    }

    private String swap(String str, int i, int j) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i, str.charAt(j));
        sb.setCharAt(j, str.charAt(i));
        return sb.toString();
    }

-------------------------------------------------
We can easily conclude the swap displacement are -1, 1, -column, and column correspondingly.


	//  relative displacements of neighbors in board.
 	private static final int[] d = {0, 1, 0, -1, 0}; 

    public int slidingPuzzle(int[][] board) {
        // convert board to string - initial state.
        // e.g., [[1,2,3],[4,0,5]] -> "123405"
        String s = "";

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                s += board[i][j];
            }
        }

        Queue<String> q = new LinkedList<>(Arrays.asList(s)); // add initial state to queue.
        Set<String> seen = new HashSet<>(q); // used to avoid duplicates

        for (int steps = 0; !q.isEmpty(); ++steps) { 
            // loop used to control search breadth.
            for (int sz = q.size(); sz > 0; --sz) {
                String str = q.poll();
                if (str.equals("123450")) { return steps; } // found target.
				
				// board[x][y] is '0'.
                int i = str.indexOf("0"), 
                	x = i / board[0].length, 
                	y = i % board[0].length; 

                // traverse all actions, 
                for (int k = 0; k < 4; ++k) { 

                	// board[r][c] is the neighbor of '0'.
                    int row = x + d[k], 
                    	column = y + d[k + 1]; 

                    if (row >= 0 && row < board.length && column >= 0 && column < board[0].length) {
                        char[] ch = str.toCharArray();

                        // swap current 0 to the new action
                        // r * board[0].length + c is the string index of board[r][c].
                        ch[i] = ch[row * board[0].length + column]; 

                        // assign '0' to the neighbor of board[x][y].
                        ch[row * board[0].length + column] = '0'; 

                        // Returns the string representation of the char array argument.
                        s = String.valueOf(ch);

                        if (seen.add(s)) 
                        	q.offer(s); // if not duplicate, add s to the queue.
                    }
                }
            }
        }
        return -1;
    }

=======================================================================================================
method 2:

method:

	- DFS + backtrack
	- use encode --> a int number instead of string

stats:

	- 
	- 

class Solution {

	//global variable
    int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    Map<Integer, Integer> map = new HashMap<>();
    int min_move = Integer.MAX_VALUE;

    public int slidingPuzzle(int[][] board) {
        map.put(123450, 0);

        //use a pair to store the index of the 0.
        int[] zero = new int[2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    zero[0] = i;
                    zero[1] = j;
                    break;
                }
            }
        }

        helper(board, zero[0], zero[1], 0);

        return min_move == Integer.MAX_VALUE ? -1 : min_move;
    }

    private void helper(int[][] board, int x, int y, int move) {
        if (move > min_move) 
        	return;

        int code = encode(board);

        if (code == 123450) {
            min_move = move;
            return;
        }

        if (map.containsKey(code)) {
            if (move > map.get(code)) 
            	return;
        }

        map.put(code, move);
        for (int[] dir : dirs) {
            int nx = x + dir[0], 
            	ny = y + dir[1];

            // backtrack
            if (nx >= 0 && nx < 2 && ny >= 0 && ny < 3) {
                swap(board, x, y, nx, ny);
                helper(board, nx, ny, move + 1);

                // swap back
                swap(board, nx, ny, x, y);
            }
        }
    }

    private void swap (int[][] board, int i, int j, int ii, int jj) {
        int temp = board[i][j];
        board[i][j] = board[ii][jj];
        board[ii][jj] = temp;
    }

    private int encode(int[][] board) {
        int code = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                code *= 10;
                code += board[i][j];
            }
        }
        return code;
    }
}

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 
















