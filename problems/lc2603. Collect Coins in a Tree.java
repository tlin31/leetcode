2603. Collect Coins in a Tree- Hard

There exists an undirected and unrooted tree with n nodes indexed from 0 to n - 1. You are given an integer n and a 2D integer array edges of length n - 1, where edges[i] = [ai, bi] indicates that there is an edge between nodes ai and bi in the tree. You are also given an array coins of size n where coins[i] can be either 0 or 1, where 1 indicates the presence of a coin in the vertex i.

Initially, you choose to start at any vertex in the tree. Then, you can perform the following operations any number of times: 

Collect all the coins that are at a distance of at most 2 from the current vertex, or
Move to any adjacent vertex in the tree.
Find the minimum number of edges you need to go through to collect all the coins and go back to the initial vertex.

Note that if you pass an edge several times, you need to count it into the answer several times.

 

Example 1:


Input: coins = [1,0,0,0,0,1], edges = [[0,1],[1,2],[2,3],[3,4],[4,5]]
Output: 2
Explanation: Start at vertex 2, collect the coin at vertex 0, move to vertex 3, collect the coin at vertex 5 then move back to vertex 2.
Example 2:


Input: coins = [0,0,0,1,1,0,0,1], edges = [[0,1],[0,2],[1,3],[1,4],[2,5],[5,6],[5,7]]
Output: 2
Explanation: Start at vertex 0, collect the coins at vertices 4 and 3, move to vertex 2,  collect the coin at vertex 7, then move back to vertex 0.
 

******************************************************
key:
	- topological sorting + trim
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1: Tree Trimming

Step1：
	像拓扑排序一样，把没有 coin 的叶子剪掉,因为这些节点不贡献价值。

	我们一直剪：没 coin 的叶子都是没用的, 删到剩下所有 coin 连接的最小子树

Step2：
	现在我们只保留有 coin 的骨干树

	然后再进一步： 最多允许每个 leaf 再被剪两层。因为从叶子向内走 2 步之外就没有必要再回头。

	因为路径至少需要来回一次。访问叶子后要回到别的分支交汇点，至少 2 edges。
	 all the leafs actually contain coins; you can gather these coins from the grandparent 
	 (parent of parent of the leaf) since the coins from nodes upto a distance 2 can be 
	 directly achieved.

Now in this entire process you must have deleted several edges. so you'll be left with some edges. the answer is the number of edges we are left with!
NOTE-> since I considered a->b and b->a as 2 different edges hence I need to multiply it by 2 so as to imitate coming back to the origin node!


Step3：
	剩余节点形成一个核心 tree， 最终剩余边数就是答案。



Stats:

	- 时间复杂度 O(N)

	- 


class Solution {
    public int collectTheCoins(int[] coins, int[][] edges) {

        int n = edges.length;
        ArrayList<HashSet<Integer>> list = new ArrayList<>();
        Queue<Integer> q = new LinkedList<>();
        int totalEdges = 2*n;
        int deletedEdges = 0;

        for(int i=0;i<=n;i++){
            list.add(new HashSet<Integer>());
        }

        for(int i=0;i<n;i++){
            int a = edges[i][0];
            int b = edges[i][1];
            list.get(a).add(b);
            list.get(b).add(a);
        }

        // Round 1: trim all leafs without coins
        for(int i=0;i<=n;i++){
            if(list.get(i).size()==1 && coins[i]==0){
                q.add(i);
            }
        }
        while(!q.isEmpty()){
            int cur = q.remove();
            if(list.get(cur).size()==0) continue; // this node has no neighbor

            int p = list.get(cur).iterator().next();
            list.get(cur).remove(p);
            list.get(p).remove(cur);

            if(list.get(p).size()==1 && coins[p]==0){
                q.add(p); //要删除的leaf
            }
            deletedEdges += 2;
        }

        // Round 2: trim two more layers of leaf nodes
        for(int i=0;i<=n;i++){
            if(list.get(i).size()==1){
                q.add(i);
            }
        }

        int size=2;
        while(size>0){
            size--;
            int qsize = q.size();
            while(qsize>0){
                qsize--;
                int cur = q.remove();
                if(list.get(cur).size()==0) continue;
                int p = list.get(cur).iterator().next();
                list.get(cur).remove(p);
                list.get(p).remove(cur);
                if(list.get(p).size()==1){
                    q.add(p);
                }
                deletedEdges += 2;
            }
        }

        return totalEdges - deletedEdges;
    }
}


拓展：如何并行执行剪枝？

可以：

parallel BFS

lock-free queue

distributed prune

🧠核心思想：Parallel Multi-Source BFS

原题里面两轮剪枝本质都是：

    找到所有 leaf

    “并发地”删掉它们

    推出下一批 leaf

这就是一个经典的 topology layer by layer processing，非常适合并行化。



使用全局队列：

    ConcurrentLinkedQueue<Integer> queue;
    AtomicIntegerArray degree;
    AtomicInteger remaining;


并行线程池：

    ExecutorService pool = Executors.newFixedThreadPool(k);

    k 可设为：CPU 数 或 N / logN（工业级经验）

Step2 并行处理一层 leaf

关键：每一层用 barrier 或 latch 等待全部线程完成。

    while (!queue.isEmpty()) {

        List<Integer> batch = drain(queue);

        CountDownLatch latch = new CountDownLatch(batch.size());

        for (int node : batch) {
            pool.submit(() -> {
                prune(node);

                for (int adj : g[node]) {
                    if (degree.decrementAndGet(adj) == 1) {
                        queue.offer(adj);
                    }
                }

                latch.countDown();
            });
        }

        latch.await();
    }


加速效果

    瓶颈不在逻辑，而在同步：

    单层规模越大并行越好

    规模太小时偏向串行更快

    类似 MapReduce 的 map-phase。
