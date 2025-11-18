310. Minimum Height Trees - Medium


A tree is an undirected graph in which any two vertices are connected by exactly one path. 
In other words, any connected graph without simple cycles is a tree.

Given a tree of n nodes labelled from 0 to n - 1, and an array of n - 1 edges where edges[i] 
= [ai, bi] indicates that there is an undirected edge between the two nodes ai and bi in the 
tree, you can choose any node of the tree as the root. When you select a node x as the root, 
the result tree has height h. Among all possible rooted trees, those with minimum height 
(i.e. min(h))  are called minimum height trees (MHTs).

Return a list of all MHTs' root labels. You can return the answer in any order.

The height of a rooted tree is the number of edges on the longest downward path between the 
root and a leaf.

 

Example 1:


Input: n = 4, edges = [[1,0],[1,2],[1,3]]
Output: [1]
Explanation: As shown, the height of the tree is 1 when the root is the node with label 1 
which is the only MHT.
Example 2:


Input: n = 6, edges = [[3,0],[3,1],[3,2],[3,4],[5,4]]
Output: [3,4]
 

Constraints:

1 <= n <= 2 * 104
edges.length == n - 1
0 <= ai, bi < n
ai != bi
All the pairs (ai, bi) are distinct.
The given input is guaranteed to be a tree and there will be no repeated edges.


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

🔹 核心原理：

一棵树的 最小高度根 一定是：

	🌳「离所有叶子最远的中心节点（或两个中点）」。

换句话说：

	树的“高度”是从根到叶的最长路径；

	要让这个最长路径最短，根应当位于“直径的中心”。

📘 所以我们可以：

	从叶子节点开始，不断“剪掉”叶子（BFS 层层剥离），直到只剩下 1 或 2 个节点。

	剩下的节点就是树的“中心点”，也就是 Minimum Height Tree 的根。

	唯一邻居 指的是：
	“对于当前叶子节点 leaf 来说，它唯一连接的另一个节点（degree = 1 的对端节点）”。


算法步骤（拓扑剪枝法）

	1️ 构建图的邻接表；
	2️ 初始化所有度数为 1 的节点（叶子节点）；
	3️ 不断删除叶子节点（每一轮 BFS）；
	4️ 当剩下的节点 ≤ 2 时，停止；
	5️ 剩下的节点即为答案。

Stats:

操作			复杂度
建图			O(N)
BFS 剪枝		O(N)
总时间复杂度	O(N)
空间复杂度	O(N)

import java.util.*;

public class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {

        List<Integer> result = new ArrayList<>();
        if (n <= 0) return result;
        if (n == 1) {
            result.add(0);
            return result;
        }

        // 1. 构建邻接表
        List<Set<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new HashSet<>());
        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }

        // 2. 找出所有叶子节点（度数为1）
        List<Integer> leaves = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (graph.get(i).size() == 1) {
                leaves.add(i);
            }
        }

        // 3. BFS逐层剪枝
        int remainingNodes = n;
        while (remainingNodes > 2) {
            remainingNodes -= leaves.size();
            List<Integer> newLeaves = new ArrayList<>();

            for (int leaf : leaves) {
                int neighbor = graph.get(leaf).iterator().next(); // 因为是leaf node所以只有一个邻居
                graph.get(neighbor).remove(leaf); // 删除唯一邻居和叶子的连接
                if (graph.get(neighbor).size() == 1) { //如果此时这个点变成新叶子了，加入下一轮循环
                    newLeaves.add(neighbor);
                }
            }

            leaves = newLeaves; // 更新下一层叶子
        }

        return leaves; // 剩下的1或2个中心节点
    }
}



class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {

        // edge cases
        if (n < 2) {
            ArrayList<Integer> centroids = new ArrayList<>();
            for (int i = 0; i < n; i++)
                centroids.add(i);
            return centroids;
        }

        // Build the graph with the adjacency list
        ArrayList<Set<Integer>> neighbors = new ArrayList<>();
        for (int i = 0; i < n; i++)
            neighbors.add(new HashSet<Integer>());

        for (int[] edge : edges) {
            Integer start = edge[0], end = edge[1];
            neighbors.get(start).add(end);
            neighbors.get(end).add(start);
        }

        // Initialize the first layer of leaves
        ArrayList<Integer> leaves = new ArrayList<>();
        for (int i = 0; i < n; i++)
            if (neighbors.get(i).size() == 1)
                leaves.add(i);

        // Trim the leaves until reaching the centroids
        int remainingNodes = n;
        while (remainingNodes > 2) {
            remainingNodes -= leaves.size();
            ArrayList<Integer> newLeaves = new ArrayList<>();

            // remove the current leaves along with the edges
            for (Integer leaf : leaves) {
                // the only neighbor left for the leaf node
                Integer neighbor = neighbors.get(leaf).iterator().next();
                // remove the edge along with the leaf node
                neighbors.get(neighbor).remove(leaf);
                if (neighbors.get(neighbor).size() == 1)
                    newLeaves.add(neighbor);
            }

            // prepare for the next round
            leaves = newLeaves;
        }

        // The remaining nodes are the centroids of the graph
        return leaves;
    }
}


