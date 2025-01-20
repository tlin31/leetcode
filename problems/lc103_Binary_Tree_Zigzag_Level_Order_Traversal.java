103. Binary Tree Zigzag Level Order Traversal- Medium

Given the root of a binary tree, return the zigzag level order traversal of its nodes' values. 
(i.e., from left to right, then right to left for the next level and alternate between).

 

Example 1:


Input: root = [3,9,20,null,null,15,7]
Output: [[3],[20,9],[15,7]]
Example 2:

Input: root = [1]
Output: [[1]]
Example 3:

Input: root = []
Output: []
 

Constraints:

The number of nodes in the tree is in the range [0, 2000].
-100 <= Node.val <= 100


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


	public class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) 
    {
        List<List<Integer>> sol = new ArrayList<>();
        travel(root, sol, 0);
        return sol;
    }
    
    private void travel(TreeNode curr, List<List<Integer>> sol, int level)
    {
        if(curr == null) return;
        
        if(sol.size() <= level){
            List<Integer> newLevel = new LinkedList<>();
            sol.add(newLevel);
        }
        
        List<Integer> collection  = sol.get(level);
        if(level % 2 == 0) collection.add(curr.val);
        else collection.add(0, curr.val);
        
        travel(curr.left, sol, level + 1);
        travel(curr.right, sol, level + 1);
    }
}
===================================================================================================


    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        if(root == null)
            return list;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        boolean normalorder = false;
        while(!q.isEmpty()){
            int size = q.size();            
            LinkedList<Integer> levelValues = new LinkedList<>();  
            normalorder = !normalorder;
            while(size > 0){
                size--;       
                TreeNode cur = q.poll();  
                if(normalorder)
                    levelValues.add(cur.val); 
                else
                    levelValues.addFirst(cur.val);
                
                if(cur.left != null)
                    q.offer(cur.left);
                if(cur.right != null)
                    q.offer(cur.right);      
                                     
            }
            //System.out.println("Level values are "+levelValues);
            list.add(levelValues);
        }
        return list;
    }


