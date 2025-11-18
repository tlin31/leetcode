987. Vertical Order Traversal of a Binary Tree - hard

Given the root of a binary tree, calculate the vertical order traversal of the binary tree.

For each node at position (row, col), its left and right children will be at positions 
(row + 1, col - 1) and (row + 1, col + 1) respectively. The root of the tree is at (0, 0).

The vertical order traversal of a binary tree is a list of top-to-bottom orderings for each 
column index starting from the leftmost column and ending on the rightmost column. There may 
be multiple nodes in the same row and same column. In such a case, sort these nodes by their values.

Return the vertical order traversal of the binary tree.

 

Example 1:


Input: root = [3,9,20,null,null,15,7]
Output: [[9],[3,15],[20],[7]]
Explanation:
Column -1: Only node 9 is in this column.
Column 0: Nodes 3 and 15 are in this column in that order from top to bottom.
Column 1: Only node 20 is in this column.
Column 2: Only node 7 is in this column.
Example 2:


Input: root = [1,2,3,4,5,6,7]
Output: [[4],[2],[1,5,6],[3],[7]]
Explanation:
Column -2: Only node 4 is in this column.
Column -1: Only node 2 is in this column.
Column 0: Nodes 1, 5, and 6 are in this column.
          1 is at the top, so it comes first.
          5 and 6 are at the same position (2, 0), so we order them by their value, 5 before 6.
Column 1: Only node 3 is in this column.
Column 2: Only node 7 is in this column.
Example 3:


Input: root = [1,2,3,4,6,5,7]
Output: [[4],[2],[1,5,6],[3],[7]]
Explanation:
This case is the exact same as example 2, but with nodes 5 and 6 swapped.
Note that the solution remains the same since 5 and 6 are in the same location and should be ordered by their values.
 

Constraints:

The number of nodes in the tree is in the range [1, 1000].
0 <= Node.val <= 1000

******************************************************
key:
    - 二叉树遍历 + 坐标排序
    - edge case:
        1) 
        2)

******************************************************



===================================================================================================
Method 1:

Method:

✅ 核心思想：

给每个节点一个坐标 (x, y)：

    x 表示列号（左子树 -1，右子树 +1）

    y 表示行号（根为 0，往下 +1）

然后用 DFS 或 BFS 遍历整棵树；记录每个节点的 (x, y, val)；按排序规则排序：

第一关键字：x（列，从小到大）

第二关键字：y（行，从小到大）

第三关键字：val（节点值，从小到大）

把相同 x 的节点放入同一个列表。  



Stats:

DFS 遍历      O(N)
排序          O(N log N)
分组          O(N)
总时间复杂度   O(N log N)
空间复杂度     O(N)


import java.util.*;

class Solution {
    static class Node {
        int x, y, val;
        Node(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }
    }

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<Node> nodes = new ArrayList<>();
        dfs(root, 0, 0, nodes);

        // 多级排序
        Collections.sort(nodes, (a, b) -> {
            if (a.x != b.x) return a.x - b.x;
            if (a.y != b.y) return a.y - b.y;
            return a.val - b.val;
        });

        // 分组
        List<List<Integer>> result = new ArrayList<>();
        int prevX = Integer.MIN_VALUE;
        for (Node node : nodes) {
            if (node.x != prevX) {
                result.add(new ArrayList<>());
                prevX = node.x;
            }
            result.get(result.size() - 1).add(node.val);
        }
        return result;
    }

    private void dfs(TreeNode root, int x, int y, List<Node> nodes) {
        if (root == null) return;
        nodes.add(new Node(x, y, root.val));
        dfs(root.left, x - 1, y + 1, nodes);
        dfs(root.right, x + 1, y + 1, nodes);
    }
}



bfs：

Queue<Pair<TreeNode, int[]>> q = new LinkedList<>();
q.add(new Pair<>(root, new int[]{0, 0}));

while (!q.isEmpty()) {
    Pair<TreeNode, int[]> p = q.poll();
    TreeNode node = p.getKey();
    int x = p.getValue()[0];
    int y = p.getValue()[1];
    nodes.add(new Node(x, y, node.val));

    if (node.left != null) q.add(new Pair<>(node.left, new int[]{x - 1, y + 1}));
    if (node.right != null) q.add(new Pair<>(node.right, new int[]{x + 1, y + 1}));
}


