437. Path Sum III - Easy

You are given a binary tree in which each node contains an integer value.

Find the number of paths that sum to a given value.

The path does not need to start or end at the root or a leaf, but it must go downwards (traveling only 
from parent nodes to child nodes).

The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000.

Example:

root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8

      10
     /  \
    5   -3
   / \    \
  3   2   11
 / \   \
3  -2   1

Return 3. The paths that sum to 8 are:

1.  5 -> 3
2.  5 -> 2 -> 1
3. -3 -> 11

{0: 1, 7: 0, 10: 0, 15: 0, 16: 0, 17: 0, 18: 0, 21: 0}


******************************************************
key:
	- brute force, use 2 sum & hashmap
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- brute force dfs
	- 

stats:

	- Time: O(n^2) in worst case (no branching); O(nlogn) in best case (balanced tree).
	- Space: O(n) due to recursion.




    public class Solution {
        public int pathSum(TreeNode root, int sum) {
            if (root == null) return 0;
            return pathSumFrom(root, sum) + pathSum(root.left, sum) + pathSum(root.right, sum);
        }

        private int pathSumFrom(TreeNode node, int sum) {
            if (node == null) 
                return 0;
            
            int res = 0;
            
            if (node.val == sum)
                res = 1;
            
            res += pathSumFrom(node.left, sum - node.val);
            res += pathSumFrom(node.right, sum - node.val);
            return res;
        }
    }


=======================================================================================================
method 2:

method:
    - 2 sum + backtrack
    
	- similar as Two sum, using HashMap to store (key= the prefix sum, value=how many ways get to this
	  prefix sum) , and whenever reach a node, we check if prefix sum - target exists in hashmap or not, 
	  if it does, we added up the ways of prefix (sum - target) into res.
	- ex: in one path we have 1,2,-1,-1,2, then the prefix sum will be: 1, 3, 2, 1, 3, let say we want to 
      find target sum is 2, then we will have{2}, {1,2,-1}, {2,-1,-1,2} and {2}ways.

	- I used global variable count, but obviously we can avoid global variable by passing the count from 
	  bottom up. 

	- The map stores <prefix sum, frequency> pairs before getting to the current node. 
	  so the sum from any node in the middle of the path to the current node = the difference between 
	  the sum from the root to the current node & the prefix sum of the node in the middle.

	- If the difference between the current sum and the target value exists in the map, there must exist 
	  a node in the middle of the path, such that from this node to the current node, the sum is equal 
	  to the target value.
	- 多个node： The frequency in the map is used to help with this.
	  To get the total number of path count, we add up the number of valid paths ended by EACH node in 
	  the tree.
	- Each recursion returns the total count of valid paths in the subtree rooted at the current node. 
	  And this sum can be divided into three parts:
		- the total number of valid paths in the subtree rooted at the current node's left child
		- the total number of valid paths in the subtree rooted at the current node's right child
		- the number of valid paths ended by the current node
		
stats:

	- time complexity is O(n)
	- 

	public int pathSum(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        return findPathSum(root, 0, sum, map);  
    }

    private int findPathSum(TreeNode curr, int sum, int target, Map<Integer, Integer> map) {
        if (curr == null) {
            return 0;
        }

        // update the prefix sum by adding the current val
        sum += curr.val;

        // get the number of valid path, ended by the current node
        // sum - target because we use prefix array 
        int numPathToCurr = map.getOrDefault(sum-target, 0); 


        // update the map with the current sum, so the map is good to be passed to the next recursion
        map.put(sum, map.getOrDefault(sum, 0) + 1);

        // add the 3 parts discussed in (8) together
        int res = numPathToCurr + findPathSum(curr.left, sum, target, map)
                                + findPathSum(curr.right, sum, target, map);

       // restore the map, as the recursion goes from the bottom to the top
        map.put(sum, map.get(sum) - 1);
        return res;
    }

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



