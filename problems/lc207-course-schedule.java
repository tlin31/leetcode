207. Course Schedule - Medium

There are a total of n courses you have to take, labeled from 0 to n-1.

Some courses may have prerequisites, for example to take course 0 you have to first take course 1,
which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish
all courses?

Example 1:

Input: 2, [[1,0]] 
Output: true
Explanation: There are a total of 2 courses to take. 
             To take course 1 you should have finished course 0. So it is possible.
Example 2:

Input: 2, [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take. 
             To take course 1 you should have finished course 0, and to take course 0 you should
             also have finished course 1. So it is impossible.
Note:

The input prerequisites is a graph represented by a list of edges, not adjacency matrices. 
Read more about how a graph is represented.
You may assume that there are no duplicate edges in the input prerequisites.


******************************************************
key:
	- topological sort, check whether there is a cycle
	- edge case:
		1) 2 numbers in the [] are the same
		2) empty list --> true

******************************************************



=======================================================================================================
method 1:

method:
	- bfs + topological sorting
	- bfs的基本思路是维护每个节点的indegree, 就是有多少个节点指向这个节点， 然后从indegree=0的节点开始，就是不
		依赖于其他任何节点的开始。
	- 然后在遍历的过程中， 取出一个节点，就把他指向的节点的入度更新。
	- 循环这个过程直到不能再找到入度为0的节点。
	- 如果最后可以访问到所有的节点，那么就返回true，否则就返回false

stats:
	- Finding cycles O(n^2) -> Topological sort O(n)
	- Runtime: 6 ms, faster than 62.08% of Java online submissions for Course Schedule.
	- Memory Usage: 44.8 MB, less than 93.85%


	public boolean canFinish(int numCourses, int[][] prerequisites) {
		Map<Integer, List<Integer>> map = new HashMap<>();

		// store in degree for each class
		int[] indegree = new int[numCourses];

		// build map
		for (int i = 0; i < prerequisites.length; i++) {
			int first = prerequisites[i][0];
			int second = prerequisites[i][1];
			if (!map.containsKey(first))
				map.put(first, new ArrayList<>());

			//if already exists first, then add second to the list
			map.get(first).add(second);

			// 入度加一，从first->second
			indegree[second]++;
		}

		// 存储所有入度为0的结点
		Queue<Integer> q = new LinkedList<>();
		for (int i = 0; i < numCourses; i++) {
			if (indegree[i] == 0)
				q.offer(i);
		}

		
		int count = 0;
		while (!q.isEmpty()) {

			// get the head of the queue
			int val = q.poll();

			count++;

			// if doesn't contain, skip this round, move to next loop
			if (!map.containsKey(val))
				continue;

			// 获取val的临结点
			List<Integer> tmp = map.get(val);
			for (int i = 0; i < tmp.size(); i++) {
				
				// 把所有的以val为开头的入度减一
				int idx = tmp.get(i);
				indegree[idx]--;
				
				// 如果入度为0，则把该结点加入队列中
				if (indegree[idx] == 0)
					q.offer(idx);
			}
		}
		return count == numCourses;
	}

=======================================================================================================
method 2:

method:

	- dfs
	- 也需要建立有向图，还是用二维数组来建立，和 BFS 不同的是，我们像现在需要一个一维数组 visit 来记录访问状态
	- 三种状态:
		0表示还未访问过，
		1表示已经访问了
		-1 表示有冲突
	- 大体思路是，先建立好有向图，然后从第一门课开始，找其可构成哪门课，暂时将当前课程标记为已访问，然后对新得到的课程
		调用 DFS 递归，直到出现新的课程已经访问过了，则返回 false，没有冲突的话返回 true，然后把标记为已访问的课程
		改为未访问

stats:

	- Runtime: 4 ms, faster than 75.85% of Java online submissions for Course Schedule.
	- Memory Usage: 44.3 MB, less than 96.15%


	public boolean canFinish(int numCourses, int[][] prerequisites) {
	    if(prerequisites == null){
	        throw new IllegalArgumentException("illegal prerequisites array");
	    }
	 
	    int len = prerequisites.length;
	 
	    if(numCourses == 0 || len == 0){
	        return true;
	    }
	 
	    //track visited courses
	    int[] visit = new int[numCourses];
	 
	    // use the map to store what courses depend on a course 
	    HashMap<Integer,ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();
	    for(int[] a: prerequisites){
	        if(map.containsKey(a[1])){
	            map.get(a[1]).add(a[0]);
	        }else{
	        	// new class never seen before
	            ArrayList<Integer> l = new ArrayList<Integer>();
	            l.add(a[0]);
	            map.put(a[1], l);
	        }
	    }
	 
	    for(int i=0; i<numCourses; i++){
	        if(!canFinishDFS(map, visit, i))
	            return false;
	    }
	 
	    return true;
	}
	 
	private boolean canFinishDFS(HashMap<Integer,ArrayList<Integer>> map, int[] visit, int i){
	    if(visit[i]==-1) 
	        return false;
	    if(visit[i]==1) 
	        return true;
	 
	    visit[i]=-1;
	    if(map.containsKey(i)){

	    	// check if there's clash
	        for(int j: map.get(i)){
	            if(!canFinishDFS(map, visit, j)) 
	                return false;
	        }
	    }
	 
	    visit[i]=1;
	 
	    return true;
	}


=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 


















