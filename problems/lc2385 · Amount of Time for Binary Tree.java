LC 2385 · Amount of Time for Binary Tree to Be Infected 🟡 Bonus Medium — Topic 7: Trees


📋 Problem

You are given the root of a binary tree with unique values, and an integer start. At minute 0, an infection starts at the node with value start. Each minute, a node becomes infected if:

Its left child is infected
Its right child is infected
Its parent is infected

Return the number of minutes needed for the entire tree to be infected.

Input:  root = [1,5,3,null,4,10,6,9,2], start = 3
Output: 4

Input:  root = [1], start = 1
Output: 0
Tree:         1
             / \
            5   3
           / \ / \
          4  2 10  6
         /
        9
        
Start at 3. Infection spreads:
  t=0: {3}
  t=1: {3,1,10,6}      (parent 1, children 10,6)
  t=2: {3,1,10,6,5}    (1's other child 5)
  t=3: {3,1,10,6,5,4,2} (5's children)
  t=4: {all, including 9} (4's child 9)
→ return 4
1. Pattern ID

Convert tree to graph + BFS from start node: since infection spreads to parents (upward) as well as children (downward), convert the binary tree to an undirected graph using DFS, then run multi-source BFS from start to find the maximum distance to any node.

2. Algorithm Discussion

The key challenge: in a binary tree, you can only traverse downward (parent → children). But infection spreads upward too (child → parent). So pure tree DFS won't work.

Two-phase approach:

Phase 1 — Convert tree to undirected graph
DFS through the tree, building an adjacency list where each node connects to both its children AND its parent. This gives us bidirectional traversal.

Phase 2 — BFS from start node
Standard BFS from start, tracking visited nodes. The number of BFS levels needed to visit all nodes = the answer (maximum distance from start to any node).

Tree adjacency list:
1: [5, 3]
5: [1, 4, 2]      ← parent 1 added
3: [1, 10, 6]     ← parent 1 added
4: [5, 9]         ← parent 5 added
2: [5]            ← parent 5 added
10: [3]           ← parent 3 added
6: [3]            ← parent 3 added
9: [4]            ← parent 4 added

BFS from 3:
  t=0: visit {3}
  t=1: visit {1, 10, 6}    (3's neighbors)
  t=2: visit {5}            (1's unvisited neighbor)
  t=3: visit {4, 2}         (5's unvisited neighbors)
  t=4: visit {9}            (4's unvisited neighbor)
→ return 4 ✅

Alternative — Single DFS approach
Find the start node during DFS, then compute distances in all directions simultaneously. Cleaner but harder to derive under pressure.

🧠 Pop quiz! — Why can't we just do DFS from the start node directly on the tree? What information is missing?

Answer: a tree node has no pointer to its parent — once you're at a node you can only go to its children, not back up. Infection spreading upward to parents is impossible to model with pure tree traversal. Converting to an undirected graph adds the missing parent→child reverse edges, enabling BFS to spread in all four directions (up, down, left, right). 🎯

3. Edge Cases
Single node tree, start = root — already all infected at t=0, return 0 ✅
Start node is a leaf — infection spreads only upward initially ✅
Start node is the root — infection spreads only downward ✅
Linear tree (skewed) — maximum distance = n-1 ✅
Start node deep in tree — BFS correctly handles spreading both up and down ✅
null root — return 0 ✅