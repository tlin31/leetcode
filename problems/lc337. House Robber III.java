337. House Robber III - Medium

The thief has found himself a new place for his thievery again. There is only one entrance to this area, called root.

Besides the root, each house has one and only one parent house. After a tour, the smart thief realized that all houses in this place form a binary tree. It will automatically contact the police if two directly-linked houses were broken into on the same night.

Given the root of the binary tree, return the maximum amount of money the thief can rob without alerting the police.

 

Example 1:


Input: root = [3,2,3,null,3,null,1]
Output: 7
Explanation: Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
Example 2:


Input: root = [3,4,5,1,3,null,1]
Output: 9
Explanation: Maximum amount of money the thief can rob = 4 + 5 = 9.
 

Constraints:

The number of nodes in the tree is in the range [1, 104].
0 <= Node.val <= 104

******************************************************
key:
	- DP + memo
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

	-	



Stats:

	- 
	- 


	int rob(TreeNode root) {
	    int[] res = dp(root);
	    return Math.max(res[0], res[1]);
	}

	/* 返回⼀个⼤小为 2 的数组 arr
	arr[0] 表示不抢 root 的话，得到的最大钱数 arr[1] 表示抢 root 的话，得到的最大钱数 */ 
	int[] dp(TreeNode root) {
	    if (root == null)
	        return new int[]{0, 0};

		int[] left = dp(root.left);
		int[] right = dp(root.right);

		// 抢，下家就不能抢了
		int rob = root.val + left[0] + right[0]; 

		//不抢，下家的选择取决于max是哪个
		int not_rob = Math.max(left[0], left[1])
		                + Math.max(right[0], right[1]);
		
		return new int[]{not_rob, rob};
	}




如果用正常bfs&memo：

class Solution {

	Map<TreeNode, Integer> memo = new HashMap<>();

	public int rob(TreeNode root) {
		if (root == null) return 0; // 利用备忘录消除重叠子问题
		if (memo.containsKey(root))
			return memo.get(root); // 抢，然后去下下家
		
		int do_it = root.val
		        + (root.left == null ?
		            0 : rob(root.left.left) + rob(root.left.right))
		        + (root.right == null ?
		            0 : rob(root.right.left) + rob(root.right.right));

		int not_do = rob(root.left) + rob(root.right);
		int res = Math.max(do_it, not_do);
		memo.put(root, res);
		return res;
	}
}




