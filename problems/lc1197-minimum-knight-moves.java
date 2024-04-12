1197. Minimum Knight Moves - Medium

In an infinite chess board with coordinates from -infinity to +infinity, you have a knight at 
square [0, 0].

A knight has 8 possible moves it can make, as illustrated below. 
Each move is two squares in a cardinal direction, then one square in an orthogonal direction.



Return the minimum number of steps needed to move the knight to the square [x, y].  
It is guaranteed the answer exists.

 

Example 1:

Input: x = 2, y = 1
Output: 1
Explanation: [0, 0] → [2, 1]
Example 2:

Input: x = 5, y = 5
Output: 4
Explanation: [0, 0] → [2, 1] → [4, 2] → [3, 4] → [5, 5]
 

Constraints:

|x| + |y| <= 300

******************************************************
key:
	- BFS, or math
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:
	- https://coordinate.wang/index.php/archives/2628/
	- 离目标位置最近的点，只可能是上面0的几种情况（其他对称位置没画）。对于绿色的位置，只需要一步即可。对于紫色位置需要两步，
	  对于蓝色位置需要三步。那么可以这么思考，如果步数最少，无非三种情况

		使用[2,1] --> floor((x+1)/2)
		使用[1,2] --> floor((y+1)/2)
		同时使用[1,2]和[2,1] --> (2a+b)+(a+2b)≥x+y  --> a+b = floor((x + y + 2)/3) (假设a个[2,1]和b个[1,2])

		最少步数不可能少于max(max((x + 1)//2, (y + 1)//2), (x + y + 2)//3)

	- 最后需要思考的就是无法整除的情况，也就是无法通过[2,1]和[1,2]组合得到的情况，实际上这就是紫色点和蓝色点。而我们通过
	  观察发现，蓝色点可以得到紫色点，所以最后只需要思考紫色点。
	  我们通过[1,2]和[2,1]组合后得到紫色点坐标是(xt,yt)的话，那么只存在三种情况：

		xt=yt=1
		abs(xt)=2     yt=0
		abs(yt)=2     xt=0

	- 我们可以通过判断x、y和x+y能否整除确定紫色点。最后还有一个边界情况就是初始点就是蓝色点，那么直接返回3

	- 

stats:

	- 
	- 


class Solution:
    def minKnightMoves(self, x: int, y: int) -> int:
        x, y = abs(x), abs(y)
        if x + y == 1:
            return 3
        res = max(max(floor((x + 1)/2), floor((y + 1)/2)), floor((x + y + 2)/3))
        res += (res ^ x ^ y) & 1
        return res


=======================================================================================================
method 2:

method:

	- BFS, encode the position to 601. 
	- 
stats:

	- O(x*y)
	- 

	private static final int[] d = {2, 1, 2, -1, 2, -1, -2, 1, 2}; 
    private static final int p = 601;

    public int minKnightMoves(int x, int y) {
        x = Math.abs(x); y = Math.abs(y); // Use the symmetric property.
        Queue<Integer> q = new LinkedList<>();
        q.offer(0);
        Set<Integer> seen = new HashSet<>(q);

        // while queue is not empty, increment step (keep a counter in the for loop!)
        for (int steps = 0; !q.isEmpty(); ++steps) {

            for (int sz = q.size(); sz > 0; --sz) {
                int i = q.peek() / p, 
                    j = q.poll() % p;

                // reach the destination
                if (i == x && j == y) 
                    return steps;

                // move
                for (int k = 0; k < 8; ++k) {
                    int new_i = i + d[k], 
                        new_j = j + d[k + 1];

                    // add to the queue if it's not seen and it's within ?
                    if (new_i >= -2 && new_j >= -2 && seen.add(new_i * p + new_j)) {
                        q.offer(new_i * p + new_j);
                    }
                }
            }
        }
        return -1;
    }

Followup & notes:
A: can you please tell why did u choose 601 in the encoding part?

Q: You can imagine how you assign the numbers for the cells in N * N grid:
row 0: 0 * N + 0, 0 * N + 1, 0 * N + 2. ..., 0 * N + (N - 1)
row 1: 1 * N + 0, 1 * N + 1, 1 * N + 2. ..., 1 * N + (N - 1)
...
row N - 1: (N - 1) * N + 0, (N - 1) * N + 1, (N - 1) * N + 2. ..., (N - 1) * N + (N - 1)

The number must be greater than 300 to avoid Hash Collision, and 301 is enough actually.

Q: You might still get collision since the board is infinite. Though the final position must be on a 300 * 300 area, it does not restrict the intermediate positions. Position (1, 302) and (2, 1) have the same hashing value 603.
A: String certainly is more generic than int, but the latter a space efficient lightweight tool, given |x| + |y| <= 300.

--------------------------------------------------------------------------------------------
Method 2.2: use matrix to store visited nodes.

Algorithm:
	- Because x and y are constrained to be in range[-300, 300], we can use BFS to find the minimum steps needed to reach target(x, y). Furthermore, we can only consider the case that x >=0 && y >=0 since the chess board is symmetric.  The bfs implementation is pretty straightforward. There are two important points you need to be careful with.

1.  Pruning. We can limit the search dimension within 310 * 310. Any moves that lead to a position that is outside this box will not yield an optimal result.

2. Initially, you used a Set of type int[] to track visited positions. This caused TLE because you didn't overwrite the hashCode and equals methods for int[]. As a result, Set uses the default hashCode and equals method when checking if an element is already in the set. For equals(), The default implementation provided by the JDK is based on memory location — two objects are equal if and only if they are stored in the same memory address. For a comprehensive reading, refer to https://dzone.com/articles/working-with-hashcode-and-equals-in-java


class Solution {
    public int minKnightMoves(int x, int y) {
        x = Math.abs(x);
        y = Math.abs(y);
        int MAXN = 310, 
            steps = 0;
        int[] dx = {-2,-1,1,2,2,1,-1,-2};
        int[] dy = {1,2,2,1,-1,-2,-2,-1};
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[MAXN][MAXN];
        q.add(new int[]{0,0});        
        visited[0][0] = true;
        
        while(q.size() > 0) {
            int sz = q.size();
            for(int i = 0; i < sz; i++) {
                int[] curr = q.poll();
                if(curr[0] == x && curr[1] == y) {
                    return steps;
                }
                for(int j = 0; j < 8; j++) {
                    int x1 = curr[0] + dx[j];
                    int y1 = curr[1] + dy[j];
                    if(x1 < 0 || y1 < 0 || x1 >= MAXN || y1 >= MAXN) {
                        continue;
                    }
                    if(!visited[x1][y1]) {
                        visited[x1][y1] = true;
                        q.add(new int[]{x1, y1});
                    }
                }
            }
            steps++;
        }
        return -1;
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



