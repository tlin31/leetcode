968. Binary Tree Cameras - Hard

Given a binary tree, we install cameras on the nodes of the tree. 

Each camera at a node can monitor its parent, itself, and its immediate children.

Calculate the minimum number of cameras needed to monitor all nodes of the tree.

 

Example 1:


Input: [0,0,null,0,0]
Output: 1
Explanation: One camera is enough to monitor all nodes if placed as shown.
Example 2:


Input: [0,0,null,0,null,0,null,null,0]
Output: 2
Explanation: At least two cameras are needed to monitor all nodes of the tree. The above image shows one of the valid configurations of camera placement.

Note:

The number of nodes in the given tree will be in the range [1, 1000].
Every node has value 0.


******************************************************
key:
	- greedy
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: Greedy


Stats:

	- Time Complexity: O(N), where N is the number of nodes in the given tree.
	- Space Complexity: O(H), where H is the height of the given tree.


Intuition:
	Consider a node in the tree.
	It can be covered by its parent, itself, its two children.
	Four options.

	Consider the root of the tree.
	It can be covered by left child, or right child, or itself.
	Three options.

	Consider one leaf of the tree.
	It can be covered by its parent or by itself.
	Two options.

	If we set a camera at the leaf, the camera can cover the leaf and its parent.
	If we set a camera at its parent, the camera can cover the leaf, its parent and its sibling.

	We can see that the second plan is always better than the first.
	Now we have only one option, set up camera to all leaves parent.

Algo:

	If a node has children that are not covered by a camera, then we must place a camera here. 
	if a node has no parent and it is not covered, we must place a camera here.

class Solution {

  	public class TreeNode {
      	int val;
      	TreeNode left;
      	TreeNode right;
      	TreeNode(int x) { val = x; }
  	}

    int ans;
    Set<TreeNode> covered;

    public int minCameraCover(TreeNode root) {
        ans = 0;
        covered = new HashSet();

        // since it now contains null, make sure the leaf is not added with camera
        covered.add(null);

        dfs(root, null);
        return ans;
    }

    public void dfs(TreeNode node, TreeNode par) {
        if (node != null) {

        	// traverse to the leaf nodes
            dfs(node.left, node);
            dfs(node.right, node);

            if ((par == null && !covered.contains(node)) ||
                    !covered.contains(node.left) ||
                    !covered.contains(node.right)) {
            	// set camera at the leave's parent
                ans++;
                covered.add(node);
                covered.add(par);
                covered.add(node.left);
                covered.add(node.right);
            }
        }
    }
}
=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	

class Solution {
    enum State { CAMERA, MONITORED, NOT_MONITORED }
    
    public int minCameraCover(TreeNode root) {
        Status status = getStatus(root);
        if (status.state == State.NOT_MONITORED) {
            status.cameras++;
        }
        return status.cameras;
    }
    
    private Status getStatus(TreeNode node) {
        if (node == null) 
            return new Status(0, State.MONITORED);
        
        Status left = getStatus(node.left);
        Status right = getStatus(node.right);
        
        Status curr = new Status(left.cameras + right.cameras, State.NOT_MONITORED);
        
        if (left.state == State.NOT_MONITORED || right.state == State.NOT_MONITORED) {
            curr.cameras++;
            curr.state = State.CAMERA;
        } else if (left.state == State.CAMERA || right.state == State.CAMERA) {
            curr.state = State.MONITORED;
        }
        
        return curr;
    }
    
    private static class Status {
        int cameras;
        State state;
        Status(int cameras, State state) {
            this.cameras = cameras;
            this.state = state;
        }
    }
}
=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



