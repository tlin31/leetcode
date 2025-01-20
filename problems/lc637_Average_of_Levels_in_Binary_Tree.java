637. Average of Levels in Binary Tree - Easy

Given the root of a binary tree, return the average value of the nodes on each level 
in the form of an array. Answers within 10-5 of the actual answer will be accepted.
 

Example 1:


Input: root = [3,9,20,null,null,15,7]
Output: [3.00000,14.50000,11.00000]
Explanation: The average value of nodes on level 0 is 3, on level 1 is 14.5, and on level 2 is 11.
Hence return [3, 14.5, 11].
Example 2:


Input: root = [3,9,20,15,7]
Output: [3.00000,14.50000,11.00000]
 

Constraints:

The number of nodes in the tree is in the range [1, 104].
-231 <= Node.val <= 231 - 1


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

	-	



Stats:

	- 
	- 


class Solution {
    public List<Double> averageOfLevels(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        List<Double> res = new LinkedList<>();
        if (root == null)
            return res;
        queue.offer(root);
        while (!queue.isEmpty()) {
            int levelNum = queue.size(); // 当前层元素的个数
            double sum = 0;
            for (int i = 0; i < levelNum; i++) {
                TreeNode curNode = queue.poll();

                sum +=curNode.val;
                //只保存当前层的最后一个元素
                if (i == levelNum - 1) {
                    res.add(sum/levelNum);
                }
                if (curNode.left != null) {
                    queue.offer(curNode.left);
                }
                if (curNode.right != null) {
                    queue.offer(curNode.right);
                }

            }
        }
        return res;
    }
}