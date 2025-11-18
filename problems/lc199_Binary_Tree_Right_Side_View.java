199. Binary Tree Right Side View- Medium

Given the root of a binary tree, imagine yourself standing on the right side of it, return the 
values of the nodes you can see ordered from top to bottom.

 

Example 1:


Input: root = [1,2,3,null,5,null,4]
Output: [1,3,4]
Example 2:

Input: root = [1,null,3]
Output: [1,3]
Example 3:

Input: root = []
Output: []
 

Constraints:

The number of nodes in the tree is in the range [0, 100].
-100 <= Node.val <= 100


******************************************************
key:
	- 	1.Each depth of the tree only select one node.
		2. View depth is current size of result list.
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
    public List<Integer> rightSideView(TreeNode root) {
        var list = new ArrayList<Integer>();
        rec(root,list,0);
        return list;
    }
    void rec(TreeNode root,ArrayList<Integer> list, int depth){
        if (root == null) 
        	return;

        if(list.size()==depth) 
        	list.add(root.val);

        if(root.right!=null) rec(root.right,list,depth+1);
        
        if(root.left!=null) rec(root.left,list,depth+1);
    }
}

===================================================================================================
iterative


public List<Integer> rightSideView(TreeNode root) {
    Queue<TreeNode> queue = new LinkedList<TreeNode>();
    List<Integer> res = new LinkedList<>();

    if (root == null)
        return res;

    queue.offer(root);
    while (!queue.isEmpty()) {
        int levelNum = queue.size(); // 当前层元素的个数
        for (int i = 0; i < levelNum; i++) {
            TreeNode curNode = queue.poll();

            //只保存当前层的最后/最右一个元素
            if (i == levelNum - 1) {
                res.add(curNode.val);
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
