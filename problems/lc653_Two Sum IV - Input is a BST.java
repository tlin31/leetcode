653. Two Sum IV - Input is a BST

Given the root of a binary search tree and an integer k, return true if there exist two elements in 
the BST such that their sum is equal to k, or false otherwise.


ex.
Input: root = [5,3,6,2,4,null,7], k = 9
Output: true

Input: root = [5,3,6,2,4,null,7], k = 28
Output: false


Constraints:

The number of nodes in the tree is in the range [1, 104].
-104 <= Node.val <= 104
root is guaranteed to be a valid binary search tree.
-105 <= k <= 105


******************************************************
key:
	- try to do space complexity of O(H)
	- edge case:
		1) empty tree / empty root node?
		2)

******************************************************



=======================================================================================================
Method 1:

Stats:

	- Time Complexity: O(nh)
	- Space Complexity: O(h). h is the height of the tree, which is logn at best case, and n at worst case.


Method:
	- binary search method. For each node, we check if k - node.val exists in this BST.
	- recursive



    public boolean findTarget(TreeNode root, int k) {
        return dfs(root, root,  k);
    }
    
    public boolean dfs(TreeNode root,  TreeNode cur, int k){
        if(cur == null)return false;
        return search(root, cur, k - cur.val) || dfs(root, cur.left, k) || dfs(root, cur.right, k);
    }
    
    public boolean search(TreeNode root, TreeNode cur, int value){
        if(root == null)
        	return false;
        
        return (root.val == value) && (root != cur) //not repeating the root
            || (root.val < value) && search(root.right, cur, value) 
                || (root.val > value) && search(root.left, cur, value);
    }





Method 2: hashset

This method also works for those who are not BSTs. The idea is to use a hashtable to save the values of the nodes in the BST. Each time when we insert the value of a new node into the hashtable, we check if the hashtable contains k - node.val .

Time Complexity: O(n)
Space Complexity: O(n)

public boolean findTarget(TreeNode root, int k) {
        HashSet<Integer> set = new HashSet<>();
        return dfs(root, set, k);
    }
    
    public boolean dfs(TreeNode root, HashSet<Integer> set, int k){
        if(root == null)return false;
        if(set.contains(k - root.val))return true;
        set.add(root.val);
        return dfs(root.left, set, k) || dfs(root.right, set, k);
    }



Method 3
use a sorted array to save the values of the nodes in the BST by using an inorder traversal. 
Then, we use two pointers which begins from the start and end of the array to find if there is a sum k.

Time Complexity: O(n)
Space Complexity: O(n).


    public boolean findTarget(TreeNode root, int k) {
        List<Integer> nums = new ArrayList<>();
        inorder(root, nums);
        for(int i = 0, j = nums.size()-1; i<j;){
            if(nums.get(i) + nums.get(j) == k)return true;
            if(nums.get(i) + nums.get(j) < k)i++;
            else j--;
        }
        return false;
    }
    
    public void inorder(TreeNode root, List<Integer> nums){
        if(root == null)return;
        inorder(root.left, nums);
        nums.add(root.val);
        inorder(root.right, nums);
    }