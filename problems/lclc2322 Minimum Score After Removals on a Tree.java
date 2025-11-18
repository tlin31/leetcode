2322. Minimum Score After Removals on a Tree - Hard

There is an undirected connected tree with n nodes labeled from 0 to n - 1 and n - 1 edges.

You are given a 0-indexed integer array nums of length n where nums[i] represents the value of 
the ith node. You are also given a 2D integer array edges of length n - 1 where edges[i] = [ai, bi] 
indicates that there is an edge between nodes ai and bi in the tree.

Remove two distinct edges of the tree to form three connected components. For a pair of removed 
edges, the following steps are defined:

Get the XOR of all the values of the nodes for each of the three components respectively.
The difference between the largest XOR value and the smallest XOR value is the score of the pair.
For example, say the three components have the node values: [4,5,7], [1,9], and [3,3,3]. 
The three XOR values are 4 ^ 5 ^ 7 = 6, 1 ^ 9 = 8, and 3 ^ 3 ^ 3 = 3. The largest XOR value is 8 and the smallest XOR value is 3. The score is then 8 - 3 = 5.
Return the minimum score of any possible pair of edge removals on the given tree.

 

Example 1:


Input: nums = [1,5,5,4,11], edges = [[0,1],[1,2],[1,3],[3,4]]
Output: 9
Explanation: The diagram above shows a way to make a pair of removals.
- The 1st component has nodes [1,3,4] with values [5,4,11]. Its XOR value is 5 ^ 4 ^ 11 = 10.
- The 2nd component has node [0] with value [1]. Its XOR value is 1 = 1.
- The 3rd component has node [2] with value [5]. Its XOR value is 5 = 5.
The score is the difference between the largest and smallest XOR value which is 10 - 1 = 9.
It can be shown that no other pair of removals will obtain a smaller score than 9.
Example 2:


Input: nums = [5,5,2,4,4,2], edges = [[0,1],[1,2],[5,2],[4,3],[1,3]]
Output: 0
Explanation: The diagram above shows a way to make a pair of removals.
- The 1st component has nodes [3,4] with values [4,4]. Its XOR value is 4 ^ 4 = 0.
- The 2nd component has nodes [1,0] with values [5,5]. Its XOR value is 5 ^ 5 = 0.
- The 3rd component has nodes [2,5] with values [2,2]. Its XOR value is 2 ^ 2 = 0.
The score is the difference between the largest and smallest XOR value which is 0 - 0 = 0.
We cannot obtain a smaller score than 0.
 

Constraints:

n == nums.length
3 <= n <= 1000
1 <= nums[i] <= 108
edges.length == n - 1
edges[i].length == 2
0 <= ai, bi < n
ai != bi
edges represents a valid tree.


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

1. 先算整个树的 XOR 总和

	令 total = XOR of all nums[i]。
	当你删除第一条边后，假设一边子树的 XOR 为 xor1，另一边就是 total ^ xor1。

2. 枚举第一条边，拆出一个子树

	在树上，任选一条边 (u, v)，我们把它视作第一条删除边。
	然后 DFS 计算当这一边被切断后，其中一边子树（假设包含 u 的那边）的 XOR 值 xor1。

	这一步得到两个部分：

	部分 A 的 XOR = xor1

	部分 B 的 XOR = total ^ xor1

3. 在子树 A（或 B）内部再枚举第二条边

	对于被拆分出来的那部分子树（比如 A），我们在它里面再次枚举第二条删除边，把它进一步拆为两个子部分。

	假设第二条删除边把 A 拆成子树 B1 和 B2，那么：

	B1 的 XOR = xor2

	B2 的 XOR = xor1 ^ xor2

	于是最终三个组件的 XOR 值分别是：

	Component1 = total ^ xor1（第一条边未切断那侧的部分）

	Component2 = xor2

	Component3 = xor1 ^ xor2

	我们计算这三者的最大值和最小值之差，更新答案的最小值。


剪枝 / 优化

	使用 DFS 时避免重复路径 / 父节点回退；

	对第一条边的枚举可以通过节点与其父节点关系来枚举；

	内部第二次枚举限制在第一条边切出来的子树范围内；

	因为 n ~ 1000，最坏是 O(n²) 的算法可以勉强过（在较优常数下）。

Stats:

	时间复杂度：O(n²)
	因为我们可能枚举行第一条边，再在子树中做第二条边的枚举，每次枚举都可能触发 DFS 遍历子树。

	空间复杂度：O(n)
	主要是图结构 + 递归调用栈。

class Solution {
    int n;
    int[] nums;
    List<Integer>[] graph;
    int totalXor;
    int ans;

    public int minimumScore(int[] nums, int[][] edges) {
        this.n = nums.length;
        this.nums = nums;
        this.graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            graph[u].add(v);
            graph[v].add(u);
        }

        // 1. 计算 totalXor
        totalXor = 0;
        for (int x : nums) {
            totalXor ^= x;
        }

        ans = Integer.MAX_VALUE;

        // 2. 枚举第一条边 (u, v) — 使用 DFS 计算子树 XOR
        for (int u = 0; u < n; u++) {
            for (int v : graph[u]) {
                // 为避免双向重复边，可以 enforce u < v 或标记方向
                // 切断边 (u, v)，假设我们计算子树 XOR（包含 u 那边）
                int xor1 = dfsXor(u, v, -1);
                // 在这部分子树内部再枚举第二条边
                dfsSecond(u, v, -1, xor1);
            }
        }
        return ans;
    }

    // DFS 计算子树 XOR：从 node 出发，不经过 blockEdge
    private int dfsXor(int node, int blockNode, int parent) {
        int res = nums[node];
        for (int nei : graph[node]) {
            if (nei == parent || nei == blockNode) continue;
            res ^= dfsXor(nei, blockNode, node);
        }
        return res;
    }

    // 在子树内部枚举第二条边
    private int dfsSecond(int node, int blockNode, int parent, int xor1) {
        int subXor = nums[node];
        for (int nei : graph[node]) {
            if (nei == parent || nei == blockNode) continue;

            // 此处 xor2 是子树 nei 的 XOR 值
            int xor2 = dfsSecond(nei, blockNode, node, xor1);
            
            // 第二条边切断的是 (node, nei)，把子树 nei 那边作为一个组件
            int part2 = xor2;
            int part3 = xor1 ^ xor2;
            int part1 = totalXor ^ xor1;

            int maxScore = Math.max(Math.max(part1, part2), part3);
            int minScore = Math.min(Math.min(part1, part2), part3);
            ans = Math.min(ans, maxScore - minScore);


		    // 我们在这一步，拿到 xor2 后，进行逻辑判断，尝试切断 (node, nei)
		    // 每次遍历一个子节点后，用 XOR 把该子树的结果合并到当前节点上。
			// 最终 subXor 就是当前节点整个子树的 XOR。

			//当递归地处理完一个子节点（即一个子树）之后，把该子树的 XOR 结果合并回当前节点的 XOR 值。
            subXor ^= xor2;
        }
        return subXor;
    }
}

🔍 举个简单例子：

假设我们有这样的树结构：

   A(5)
  / \
 B(2) C(7)


假设当前递归在 A 节点，A 的 nums[A] = 5。

我们进入第一个子节点 B：

dfsSecond(B) 返回它子树的 XOR 值 xor2 = 2

然后我们执行：

subXor ^= xor2; // 5 ^= 2 → subXor = 7


继续处理第二个子节点 C：

dfsSecond(C) 返回 xor2 = 7

再执行：

subXor ^= xor2; // 7 ^= 7 → subXor = 0


最后 dfsSecond(A) 返回 0，
也就是整棵树 A 的 XOR = 5 ^ 2 ^ 7 = 0。 ✅

===================================================================================================


import java.util.*;
import java.util.concurrent.*;

public class MinimumScoreMultiThread {

    private final ExecutorService executor;
    private final Map<Integer, List<Integer>> graph = new HashMap<>();
    private final int[] nums;
    private int n;
    private final int[] xor;

    public MinimumScoreMultiThread(int[] nums, int[][] edges) {
        this.nums = nums;
        this.n = nums.length;
        this.xor = new int[n];
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < n; i++) {
            graph.put(i, new ArrayList<>());
        }
        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }
    }

    // 并行计算所有子树 XOR
    private int dfsXor(int node, int parent) throws ExecutionException, InterruptedException {
        int val = nums[node];
        List<Future<Integer>> futures = new ArrayList<>();

        for (int nei : graph.get(node)) {
            if (nei == parent) continue;
            // 每个子树异步执行
            Future<Integer> f = executor.submit(() -> dfsXor(nei, node));
            futures.add(f);
        }

        // 等待子任务结果并合并
        for (Future<Integer> f : futures) {
            val ^= f.get();
        }

        xor[node] = val;
        return val;
    }

    // 主函数（简化版，仅展示计算 XOR 的并行逻辑）
    public int computeTotalXor() throws Exception {
        int total = dfsXor(0, -1);
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        return total;
    }

    public static void main(String[] args) throws Exception {
        int[] nums = {1, 5, 5, 4, 11};
        int[][] edges = {
                {0, 1}, {1, 2}, {1, 3}, {3, 4}
        };
        MinimumScoreMultiThread solver = new MinimumScoreMultiThread(nums, edges);
        System.out.println("Total XOR = " + solver.computeTotalXor());
    }
}

===================================================================================================


Multi-thread version 

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class Solution {
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final int MINIMUM_PAIRS_FOR_MULTITHREADING = 1000;

    public int minimumScore(int[] nodeValues, int[][] edges) {
        int nodeCount = nodeValues.length;
        List<List<Integer>> adjacencyList = buildAdjacencyList(edges, nodeCount);

        int[] subtreeXorSum = new int[nodeCount];
        int[] dfsEnterTime = new int[nodeCount];
        int[] dfsExitTime = new int[nodeCount];
        int[] dfsTimer = { 0 };

        computeSubtreeData(0, -1, nodeValues, adjacencyList, subtreeXorSum,
                dfsEnterTime, dfsExitTime, dfsTimer);

        long totalNodePairs = (long) (nodeCount - 1) * (nodeCount - 2) / 2;

        if (totalNodePairs < MINIMUM_PAIRS_FOR_MULTITHREADING) {
            return singleThreadedMinimumScore(nodeCount, subtreeXorSum, dfsEnterTime, dfsExitTime);
        }

        return parallelMinimumScore(nodeCount, subtreeXorSum, dfsEnterTime, dfsExitTime);
    }

    private List<List<Integer>> buildAdjacencyList(int[][] edges, int nodeCount) {
        List<List<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            adjacencyList.get(edge[0]).add(edge[1]);
            adjacencyList.get(edge[1]).add(edge[0]);
        }
        return adjacencyList;
    }

    private int singleThreadedMinimumScore(int nodeCount, int[] subtreeXorSum,
            int[] dfsEnterTime, int[] dfsExitTime) {
        int minimumScore = Integer.MAX_VALUE;
        for (int firstNode = 1; firstNode < nodeCount; firstNode++) {
            for (int secondNode = firstNode + 1; secondNode < nodeCount; secondNode++) {
                int currentScore = calculatePartitionScore(firstNode, secondNode,
                        subtreeXorSum, dfsEnterTime, dfsExitTime);
                minimumScore = Math.min(minimumScore, currentScore);
            }
        }
        return minimumScore;
    }

    private int parallelMinimumScore(int nodeCount, int[] subtreeXorSum,
            int[] dfsEnterTime, int[] dfsExitTime) {
        ExecutorService threadPool = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS);
        AtomicInteger globalMinimumScore = new AtomicInteger(Integer.MAX_VALUE);

        try {
            List<Future<Void>> threadTasks = new ArrayList<>();
            int nodesPerThread = Math.max(1, (nodeCount - 1) / AVAILABLE_PROCESSORS);

            for (int threadIndex = 0; threadIndex < AVAILABLE_PROCESSORS; threadIndex++) {
                final int threadStartNode = 1 + threadIndex * nodesPerThread;
                final int threadEndNode = (threadIndex == AVAILABLE_PROCESSORS - 1) ? nodeCount
                        : Math.min(nodeCount, 1 + (threadIndex + 1) * nodesPerThread);

                if (threadStartNode >= nodeCount)
                    break;

                threadTasks.add(threadPool.submit(() -> {
                    processNodeRange(threadStartNode, threadEndNode, nodeCount,
                            subtreeXorSum, dfsEnterTime, dfsExitTime, globalMinimumScore);
                    return null;
                }));
            }

            for (Future<Void> task : threadTasks) {
                task.get();
            }

        } catch (InterruptedException | ExecutionException exception) {
            return singleThreadedMinimumScore(nodeCount, subtreeXorSum, dfsEnterTime, dfsExitTime);
        } finally {
            shutdownThreadPool(threadPool);
        }

        return globalMinimumScore.get();
    }

    private void processNodeRange(int startNode, int endNode, int totalNodes,
            int[] subtreeXorSum, int[] dfsEnterTime, int[] dfsExitTime,
            AtomicInteger globalMinimumScore) {
        int threadLocalMinimum = Integer.MAX_VALUE;

        for (int firstNode = startNode; firstNode < endNode; firstNode++) {
            for (int secondNode = firstNode + 1; secondNode < totalNodes; secondNode++) {
                int partitionScore = calculatePartitionScore(firstNode, secondNode,
                        subtreeXorSum, dfsEnterTime, dfsExitTime);
                threadLocalMinimum = Math.min(threadLocalMinimum, partitionScore);

                if (threadLocalMinimum == 0) {
                    atomicUpdateMinimum(globalMinimumScore, threadLocalMinimum);
                    return;
                }
            }

            if (firstNode % 10 == 0) {
                atomicUpdateMinimum(globalMinimumScore, threadLocalMinimum);
                if (globalMinimumScore.get() == 0)
                    return;
            }
        }

        atomicUpdateMinimum(globalMinimumScore, threadLocalMinimum);
    }

    private void atomicUpdateMinimum(AtomicInteger globalMinimum, int candidateValue) {
        int currentGlobalValue;
        do {
            currentGlobalValue = globalMinimum.get();
            if (candidateValue >= currentGlobalValue)
                break;
        } while (!globalMinimum.compareAndSet(currentGlobalValue, candidateValue));
    }

    private int calculatePartitionScore(int firstNode, int secondNode, int[] subtreeXorSum,
            int[] dfsEnterTime, int[] dfsExitTime) {
        int firstEnterTime = dfsEnterTime[firstNode];
        int firstExitTime = dfsExitTime[firstNode];
        int secondEnterTime = dfsEnterTime[secondNode];
        int secondExitTime = dfsExitTime[secondNode];

        if (secondEnterTime > firstEnterTime && secondEnterTime < firstExitTime) {
            int remainingTreeXor = subtreeXorSum[0] ^ subtreeXorSum[firstNode];
            int firstSubtreeWithoutSecond = subtreeXorSum[firstNode] ^ subtreeXorSum[secondNode];
            int secondSubtreeXor = subtreeXorSum[secondNode];
            return calculateScoreRange(remainingTreeXor, firstSubtreeWithoutSecond, secondSubtreeXor);
        } else if (firstEnterTime > secondEnterTime && firstEnterTime < secondExitTime) {
            int remainingTreeXor = subtreeXorSum[0] ^ subtreeXorSum[secondNode];
            int secondSubtreeWithoutFirst = subtreeXorSum[secondNode] ^ subtreeXorSum[firstNode];
            int firstSubtreeXor = subtreeXorSum[firstNode];
            return calculateScoreRange(remainingTreeXor, secondSubtreeWithoutFirst, firstSubtreeXor);
        } else {
            int remainingTreeXor = subtreeXorSum[0] ^ subtreeXorSum[firstNode] ^ subtreeXorSum[secondNode];
            int firstSubtreeXor = subtreeXorSum[firstNode];
            int secondSubtreeXor = subtreeXorSum[secondNode];
            return calculateScoreRange(remainingTreeXor, firstSubtreeXor, secondSubtreeXor);
        }
    }

    private int calculateScoreRange(int partitionOne, int partitionTwo, int partitionThree) {
        int maximumValue = Math.max(partitionOne, Math.max(partitionTwo, partitionThree));
        int minimumValue = Math.min(partitionOne, Math.min(partitionTwo, partitionThree));
        return maximumValue - minimumValue;
    }

    private void computeSubtreeData(int currentNode, int parentNode, int[] nodeValues,
            List<List<Integer>> adjacencyList, int[] subtreeXorSum,
            int[] dfsEnterTime, int[] dfsExitTime, int[] dfsTimer) {
        dfsEnterTime[currentNode] = dfsTimer[0]++;
        subtreeXorSum[currentNode] = nodeValues[currentNode];

        for (int neighborNode : adjacencyList.get(currentNode)) {
            if (neighborNode == parentNode)
                continue;

            computeSubtreeData(neighborNode, currentNode, nodeValues, adjacencyList,
                    subtreeXorSum, dfsEnterTime, dfsExitTime, dfsTimer);
            subtreeXorSum[currentNode] ^= subtreeXorSum[neighborNode];
        }

        dfsExitTime[currentNode] = dfsTimer[0];
    }

    private void shutdownThreadPool(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException exception) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}




