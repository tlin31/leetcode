210. Course Schedule II - Medium

There are a total of n courses you have to take, labeled from 0 to n-1.

Some courses may have prerequisites, for example to take course 0 you have to first take course 1, 
which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs, return the ordering of courses 
you should take to finish all courses.

There may be multiple correct orders, you just need to return one of them. If it is impossible to 
finish all courses, return an empty array.

Example 1:

Input: 2, [[1,0]] 
Output: [0,1]
Explanation: There are a total of 2 courses to take. To take course 1 you should have finished   
             course 0. So the correct course order is [0,1] .
Example 2:

Input: 4, [[1,0],[2,0],[3,1],[3,2]]
Output: [0,1,2,3] or [0,2,1,3]
Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both     
             courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0. 
             So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3] .
Note:
The input prerequisites is a graph represented by a list of edges, not adjacency matrices.
You may assume that there are no duplicate edges in the input prerequisites.


******************************************************
key:
    - similar to lc 207, but here return the order
    - edge case:
        1) empty string, return empty
        2) 

******************************************************



=======================================================================================================
method 1:

method:

    - bfs

统计每个节点（课程）的 入度（in-degree）；

先把所有 入度为 0 的节点加入队列；

不断弹出队列中的节点，并“移除”它的出边（即让后继节点入度 -1）；

每当某节点入度变为 0，就入队；

最终输出的节点顺序即为拓扑排序。

如果最终输出的节点数量 小于课程总数，说明存在环（无法完成所有课程）。

stats:

操作  时间复杂度
构建图 O(E)
BFS遍历   O(V + E)
总复杂度    O(V + E) （线性）
空间复杂度   O(V + E)



import java.util.*;

class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // 构建图和入度表
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[numCourses];
        
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        // 建图：edge = [a, b] 表示 b -> a
        for (int[] edge : prerequisites) {
            int course = edge[0];
            int pre = edge[1];
            graph.get(pre).add(course);
            indegree[course]++;
        }
        
        // BFS队列，存储所有入度为0的课程
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        int[] order = new int[numCourses];
        int index = 0;
        
        // BFS 拓扑排序
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            order[index++] = cur;
            
            // 移除当前节点的出边
            for (int next : graph.get(cur)) {
                indegree[next]--;
                if (indegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }
        
        // 如果排序结果中课程数量不足，说明有环
        return index == numCourses ? order : new int[0];
    }
}

=======================================================================================================
method 2:

method:

    - We define in degree as the number of edges into a node in the graph.
    - we remove the nodes that has in degree equals to 0, decrease the in degree of the nodes that 
        require the current node, and repeat, until we have removed all the nodes (the successful 
        case), or there is no node with in degree equals to 0 (the failed case).
    - using a priority queue, and make the in degree as the priority. Every time we poll a node from 
        the queue, and decrease the priorities of the children of the node. If the polled node has 
        in degree larger than 1, it means we failed. But since Java's priority queue doesn't support 
        convenient decrease key operation, we have to remove one node and add it back, which causes 
        bad performance.
    - Or we can use two pointers. We put the removed node in an array, and use a left pointer to 
        iterate through the array and decrease the in degrees of the nodes than require the current 
        node. 
        And use a right pointer to add those nodes which have 0 in degree after the decreasing 
            operation. Repeat this until all nodes are added.
    

stats:

    - Runtime: 3 ms, faster than 96.42% of Java online submissions for Course Schedule II.
    - Memory Usage: 45.5 MB, less than 90.24%

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        
        int[] inDeg = new int[numCourses];
        List<Integer>[] chl = new ArrayList[numCourses];
        
        for (int i = 0; i < numCourses; i++) {
            chl[i] = new ArrayList<Integer>();
        }
        
        int pre;
        int cour;
        
        for (int[] pair : prerequisites) {
            pre = pair[1];
            cour = pair[0];
            
            chl[pre].add(cour);
            inDeg[cour]++;
        }
        
        int[] res = new int[numCourses];
        int k = 0;
        
        for (int i = 0; i < numCourses; i++) {
            if (inDeg[i] == 0) {
                res[k++] = i;
            }
        }
        
        if (k == 0) {
            return new int[0];
        }
        
        int j = 0;
        List<Integer> tmp;
        
        while (k < numCourses) {
            tmp = chl[res[j++]];
            
            for (int id : tmp) {
                if (--inDeg[id] == 0) {
                    res[k++] = id;
                }
            }
            
            if (j == k) {
                return new int[0];
            }
        }
        
        return res;
    }

