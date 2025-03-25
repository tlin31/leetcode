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
    - 

stats:

    - 
    - 


public int[] findOrder(int numCourses, int[][] prerequisites) { 
    if (numCourses == 0) return null;

    // Convert graph presentation from edges to indegree of adjacent list.
    int indegree[] = new int[numCourses], 
        order[] = new int[numCourses], 
        index = 0;

    for (int i = 0; i < prerequisites.length; i++) // Indegree - how many prerequisites are needed.
        indegree[prerequisites[i][0]]++;    

    Queue<Integer> queue = new LinkedList<Integer>();
    for (int i = 0; i < numCourses; i++) 
        if (indegree[i] == 0) {
            // Add the course to the order because it has no prerequisites.
            order[index++] = i;
            queue.offer(i);
        }

    // How many courses don't need prerequisites. 
    while (!queue.isEmpty()) {
        int prerequisite = queue.poll(); // Already finished this prerequisite course.
        for (int i = 0; i < prerequisites.length; i++)  {
            if (prerequisites[i][1] == prerequisite) {
                indegree[prerequisites[i][0]]--; 
                if (indegree[prerequisites[i][0]] == 0) {
                    // If indegree is zero, then add the course to the order.
                    order[index++] = prerequisites[i][0];
                    queue.offer(prerequisites[i][0]);
                }
            } 
        }
    }

    return (index == numCourses) ? order : new int[0];
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

