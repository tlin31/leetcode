444. Sequence Reconstruction - Medium

You are given an integer array nums of length n where nums is a permutation of the integers in the range [1, n]. You are also given a 2D integer array sequences where sequences[i] is a subsequence of nums.

Check if nums is the shortest possible and the only supersequence. The shortest supersequence is a sequence with the shortest length and has all sequences[i] as subsequences. There could be multiple valid supersequences for the given array sequences.

For example, for sequences = [[1,2],[1,3]], there are two shortest supersequences, [1,2,3] and [1,3,2].
While for sequences = [[1,2],[1,3],[1,2,3]], the only shortest supersequence possible is [1,2,3]. [1,2,3,4] is a possible supersequence but not the shortest.
Return true if nums is the only shortest supersequence for sequences, or false otherwise.

A subsequence is a sequence that can be derived from another sequence by deleting some or no elements without changing the order of the remaining elements.

 

Example 1:

Input: nums = [1,2,3], sequences = [[1,2],[1,3]]
Output: false
Explanation: There are two possible supersequences: [1,2,3] and [1,3,2].
The sequence [1,2] is a subsequence of both: [1,2,3] and [1,3,2].
The sequence [1,3] is a subsequence of both: [1,2,3] and [1,3,2].
Since nums is not the only shortest supersequence, we return false.
Example 2:

Input: nums = [1,2,3], sequences = [[1,2]]
Output: false
Explanation: The shortest possible supersequence is [1,2].
The sequence [1,2] is a subsequence of it: [1,2].
Since nums is not the shortest supersequence, we return false.
Example 3:

Input: nums = [1,2,3], sequences = [[1,2],[1,3],[2,3]]
Output: true
Explanation: The shortest possible supersequence is [1,2,3].
The sequence [1,2] is a subsequence of it: [1,2,3].
The sequence [1,3] is a subsequence of it: [1,2,3].
The sequence [2,3] is a subsequence of it: [1,2,3].
Since nums is the only shortest supersequence, we return true.
 

Constraints:

n == nums.length
1 <= n <= 104
nums is a permutation of all the integers in the range [1, n].
1 <= sequences.length <= 104
1 <= sequences[i].length <= 104
1 <= sum(sequences[i].length) <= 105
1 <= sequences[i][j] <= n
All the arrays of sequences are unique.
sequences[i] is a subsequence of nums.


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************


给定一个原始序列 org 和若干子序列 seqs，判断是否 存在且仅存在一个 拓扑序列，并且这个唯一的序列 恰好等于 org。

关键词：
唯一拓扑序（Unique Topological Order）

关键判断条件（非常重要）

要返回 true，必须同时满足 三点：

✅ 1️：图能覆盖 org 中的所有节点

不能缺节点，也不能有多余节点

✅ 2️：拓扑排序过程中 每一步入度为 0 的节点只能有一个

否则说明：存在多种拓扑序& 顺序不唯一

✅ 3️： 拓扑排序生成的序列 == org

class Solution {
    public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
        // Do a topological sort. It should only have one node with indegree = 0; 
        // If at any point, more than one option for topological ==> false
        if (seqs.size() == 0) {
            return org.length == 0;
        }
        int n = org.length;
        int[] inDegree = new int[n + 1];
        
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < n + 1; i++) {
            adjList.add(new ArrayList<>());
        }
        
        for (List<Integer> seq: seqs) {
            for (int i = 0; i + 1 < seq.size(); i++) {
                int src = seq.get(i);
                int dst = seq.get(i + 1);
                
                if (src <= 0 || src > n) {
                    return false;
                }
                
                if (dst <= 0 || dst > n) {
                    return false;
                }
                
                adjList.get(src).add(dst);
                inDegree[dst] += 1;
            }
        }
        
        Set<Integer> seqNodes = new HashSet<>();
        for (List<Integer> seq: seqs) {
            for (int i = 0; i < seq.size(); i++) {
                int src = seq.get(i);
                seqNodes.add(src);
            }
        }
        
        for (int i = 1; i <= n; i++) {
            if (!seqNodes.contains(i)) {
                return false;
            }
        }
        
        if (seqNodes.size() != org.length) {
            return false;
        }
        
        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                q.offer(i);
            }
        }
        
        int nodesVisited = 0;
        while (q.size() > 0) {
            int size = q.size();
            if (q.size() != 1) {
                return false;
            }
            
            int node = q.poll();
            if (org[nodesVisited] != node) {
                return false;
            }
            nodesVisited++;
            for (int nextNode: adjList.get(node)) {
                inDegree[nextNode] -= 1;
                if (inDegree[nextNode] == 0) {
                    q.offer(nextNode);
                }
            }
            
        }
        
        return nodesVisited == n;
    }
}
===================================================================================================
Method 1:

Method:

这个解法不显式建图，而是利用 org 中每个数字的位置索引，
直接验证 seqs 中的所有相对顺序是否 不违反 org，
并且是否 强制约束了 org 中的每一对相邻元素，
从而判断 org 是否是唯一可重建序列。


Stats:

	- 
	- 


class Solution {
    public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
        if (seqs == null || seqs.size() == 0) return false;

        int[] order = new int[org.length + 1];
        for (int i = 0; i < org.length; i++) {
            if (org[i] <= 0 || org[i] > org.length) return false;
            order[org[i]] = i;
        }

        boolean[] pairs = new boolean[org.length];
        for (List<Integer> seq : seqs) {
            for (int i = 0; i < seq.size(); i++) {
                if (seq.get(i) <= 0 || seq.get(i) > org.length) return false;
                if (i == 0 && seq.get(i) == org[0]) pairs[0] = true;
                if (i > 0 && order[seq.get(i - 1)] >= order[seq.get(i)]) return false;
                if (i > 0 && order[seq.get(i - 1)] + 1 == order[seq.get(i)]) pairs[order[seq.get(i)]] = true;
            }
        }
        for (int i = 0; i < pairs.length; i++) {
            if (!pairs[i]) return false;
        }
        return true;
    }
}


