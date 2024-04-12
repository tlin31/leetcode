113. Path Sum II - Medium

Given a binary tree and a sum, find all root-to-leaf paths where each path sum equals the given sum.

Note: A leaf is a node with no children.

Example:

Given the below binary tree and sum = 22,

      5
     / \
    4   8
   /   / \
  11  13  4
 /  \    / \
7    2  5   1


Return:

[
   [5,4,11,2],
   [5,8,4,5]
]

******************************************************
key:
	- dfs
	- edge case:
		1) empty string, return empty
		2)

******************************************************


=======================================================================================================
method 1: backtrack

method:

	- dfs - recursion
	- backtrack

stats:

	- Time: we visit each node exactly once, thus the time complexity is O(N), N  = # of nodes.

	- Space: in the worst case, the tree is completely unbalanced, e.g. each node has only one child 
	  node, the recursion call would occur N times (the height of the tree), therefore the storage to 
	  keep the call stack would be O(N). 

	  But in the best case (the tree is completely balanced), the height of the tree would be log(N). 
	  Therefore, the space complexity in this case would be O(log(N)).


class Solution {
    List<List<Integer>> res;
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        res = new LinkedList();
        dfs(root, sum, new LinkedList());
        return res;
    }
    
    public void dfs(TreeNode root, int sum, LinkedList<Integer> path){
        
        if(root == null) return;
        
        path.add(root.val);

        // need to make sure it reaches to the leaf
        if(root.left == null && root.right == null && sum == root.val) 
        	res.add(new LinkedList(path));

        dfs(root.left, sum - root.val, path);
        dfs(root.right, sum - root.val, path);
        path.removeLast();
    }
}


=======================================================================================================
method 2:

method:

	- dfs-stack as backtrack
	- 

stats:

	- 
	- 

public class Solution {
    private List<List<Integer>> resultList = new ArrayList<List<Integer>>();

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        if(root==null) 
        	return resultList;
        Stack<Integer> path = new Stack<Integer>();
        pathSumInner(root, sum, path);
        return resultList;
    }   

    //helper function
    public void pathSumInner(TreeNode root, int sum, Stack<Integer>path) {
        path.push(root.val);

        if(root.left == null && root.right == null)
            if(sum == root.val) 
            	resultList.add(new ArrayList<Integer>(path));

        if(root.left!=null) pathSumInner(root.left, sum-root.val, path);
        if(root.right!=null)pathSumInner(root.right, sum-root.val, path);

        path.pop();
    }
    

}

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



