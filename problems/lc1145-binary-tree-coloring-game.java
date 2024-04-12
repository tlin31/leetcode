1145. Binary Tree Coloring Game - Medium

https://leetcode.com/problems/binary-tree-coloring-game/submissions/


Two players play a turn based game on a binary tree.  We are given the root of this binary tree, and the 
number of nodes n in the tree.  n is odd, and each node has a distinct value from 1 to n.

Initially, the first player names a value x with 1 <= x <= n, and the second player names a value y with 1 
<= y <= n and y != x.  The first player colors the node with value x red, and the second player colors the 
node with value y blue.

Then, the players take turns starting with the first player.  In each turn, that player chooses a node of 
their color (red if player 1, blue if player 2) and colors an uncolored neighbor of the chosen node (either
the left child, right child, or parent of the chosen node.)

If (and only if) a player cannot choose such a node in this way, they must pass their turn.  If both 
players pass their turn, the game ends, and the winner is the player that colored more nodes.

You are the second player.  If it is possible to choose such a y to ensure you win the game, return true. 
If it is not possible, return false.

 

Example 1:


Input: root = [1,2,3,4,5,6,7,8,9,10,11], n = 11, x = 3
Output: true
Explanation: The second player can choose the node with value 2.
 

Constraints:

root is the root of a binary tree with n nodes and distinct node values from 1 to n.
n is odd.
1 <= x <= n <= 100

******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:
	- Greedy
	- The first player colors a node, there are at most 3 nodes connected to this node. 
	  Its left, its right and its parent. Take this 3 nodes as the root of 3 subtrees.

	- 对于二号玩家，最多有三个备选的位置，一个是 x 的左结点，一个是右结点，或者是父结点。
	  我们通过一次深度优先遍历，求出 x 左右子树的大小，以及除了 x 为根的子树之外的大小，就分别可以得到三个备选所代表的被蓝色
	  染色结点个数。找到最大结点数的备选位置即可。

	- The second player just color any one root, and the whole subtree will be his. And this is also all 
	  he can take, since he cannot cross the node of the first player.

	- count: recursively count the number of nodes, in the left and in the right.
		     (n - left - right) will be the number of nodes in the "subtree" of parent.
	- Now we just need to compare max(left, right, parent) and n / 2


stats:

	- Time O(N)
	- Space O(height) for recursion


Solution {
    int left, right, val;
    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        val = x;
        count(root);
        return Math.max(Math.max(left, right), n - left - right - 1) > n / 2;
    }

    private int count(TreeNode node) {
        if (node == null) return 0;

        int l = count(node.left), 
        	r = count(node.right);

        if (node.val == val) {
            left = l;
            right = r;
        }
        return l + r + 1;
    }

}

==============
public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
	if(root == null) return false;

	if(root.val == x){
		int left = count(root.left);
		int right = count(root.right);
		int parent = n - (left+right+1);

		return parent > (left + right) || left > (parent + right) || right > (left + parent);
	}

	return btreeGameWinningMove(root.left, n, x) || btreeGameWinningMove(root.right, n, x);
}

private int count(TreeNode node){
	if(node == null) return 0;
	return count(node.left) + count(node.right) + 1;
}

=======================================================================================================
method 2:

Fun Moment of Follow-up:
	- Alex and Lee are going to play this turned based game. Alex draw the whole tree. root and n 
	  will be given.
	- Note the n will be odd, so no tie in the end.

	- Now Lee says that, this time he wants to color the node first. Return true if Lee can ensure his 
	  win, otherwise return false


Ans:
	- Yes, similar to the solution 877. Stone Game Just return true. But this time, it is Lee s turn to 
	ride shotgun today! Bravo.



=======================================================================================================
method 3:

Follow up:
	- Could you find the set all the nodes, that Lee can ensure he wins the game?
	- Return the size of this set.

Ans: 
	- There will one and only one node in the tree, that can Lee s win.
	- It can be solve in O(N).
