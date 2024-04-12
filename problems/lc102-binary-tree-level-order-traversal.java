102. Binary Tree Level Order Traversal - Medium

Given a binary tree, return the level order traversal of its nodes values. (ie, from left to right, 
level by level).

For example:
Given binary tree [3,9,20,null,null,15,7],
    3
   / \
  9  20
    /  \
   15   7
return its level order traversal as:
[
  [3],
  [9,20],
  [15,7]
]

******************************************************
key:
	- 
	- edge case:
		1) empty tree, return empty ArrayList
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- 
	- 

stats:

	- 
	- 
public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        levelHelper(res, root, 0);
        return res;
    }
    
    public void levelHelper(List<List<Integer>> res, TreeNode root, int height) {
        if (root == null) return;

        if (height >= res.size()) {
            res.add(new LinkedList<Integer>());
        }
        
        res.get(height).add(root.val);
        levelHelper(res, root.left, height+1);
        levelHelper(res, root.right, height+1);
    }



=======================================================================================================

    public List<List<Integer>> levelOrder(TreeNode root) {
		List<List<Integer>> res = new ArrayList<>();
		if (root == null)
			return res;
		levelOrderHelper(res, root, 0);
		return res;
	}
	
	public void levelOrderHelper(List<List<Integer>> res, TreeNode root, int level) {
		if (root == null)
			return;

		List<Integer> curr;

		if (level >= res.size()) {
			curr = new ArrayList<>();
			curr.add(root.val);
			res.add(curr);
		} else {
			curr = res.get(level); 
			curr.add(root.val); 
			//res.add(curr); // No need to add the curr into the res, because the res.get(index) method does not remove the index element
		}
		levelOrderHelper(res, root.left, level + 1);
		levelOrderHelper(res, root.right, level + 1);
	}