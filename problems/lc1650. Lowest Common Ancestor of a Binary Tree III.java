1650. Lowest Common Ancestor of a Binary Tree III - Medium

Given two nodes of a binary tree p and q, return their lowest common ancestor (LCA).

Each node will have a reference to its parent node. The definition for Node is below:

class Node {
    public int val;
    public Node left;
    public Node right;
    public Node parent;
}
According to the definition of LCA on Wikipedia: "The lowest common ancestor of two nodes p and q in a tree T is the lowest node that has both p and q as descendants (where we allow a node to be a descendant of itself)."

 

Example 1:


Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.
Example 2:


Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5 since a node can be a descendant of itself according to the LCA definition.
Example 3:

Input: root = [1,2], p = 1, q = 2
Output: 1
 

Constraints:

The number of nodes in the tree is in the range [2, 105].
-109 <= Node.val <= 109
All Node.val are unique.
p != q
p and q exist in the tree.


******************************************************
key:
    - 
    - edge case:
        1) 
        2)

******************************************************



===================================================================================================
Method 1:

Method: 解法：双指针

两个指针都一定会走同样长度的路径

最后肯定会在公共祖先处相遇

这个技巧叫：

linked list intersection trick
时间复杂度 O(h)
空间 O(1)

为什么无需 visited set？ 因为像链表相交问题一样，多走一次对方路径可以对齐。


Stats:

    - 
    - 

public Node lowestCommonAncestor(Node p, Node q) {
    Node a = p, b = q;
    while (a != b) {
        a = a == null? q : a.parent;
        b = b == null? p : b.parent;    
    }
    return a;
}



❗另外一个方法：祖先标记
    visited = set()
    while p:
        visited.add(p)
        p = p.parent

    while q:
        if q in visited:
            return q
        q = q.parent


时间 O(h)，空间 O(h)


🏭 工程(真实工业场景)应用


1. 文件系统中查共同目录

    Linux PATH：

    /usr/local/bin/tool
    /usr/local/share/doc


    LCA = /usr/local

    使用 parent pointers 很自然。

2. 知识图谱 / 推荐系统 分类树

    商品类目：

    手机 → 智能手机 → iPhone
    家电 → 手机


    查询两个商品的最小公共分类节点。

    阿里、京东正式使用这种。


3. Git merge base 查找

    Git 的一个 commit graph 并不严格是树，但 merge-base 逻辑一样：

    找两个分支的最近共同祖先 commit

    这是 LCA 的工业应用经典案例。

4.路由优化 / 网络拓扑

    比如 CDN backbone：

    找两台服务器的最近汇聚路由节点

    这是典型路由规划算法的底层逻辑。



