230. Kth Smallest Element in a BST - Medium


Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.

Note:
You may assume k is always valid, 1 ≤ k ≤ BST total elements.

Example 1:

Input: root = [3,1,4,null,2], k = 1
   3
  / \
 1   4
  \
   2
Output: 1


Example 2:

Input: root = [5,3,6,2,4,null,null,1], k = 3
       5
      / \
     3   6
    / \
   2   4
  /
 1
Output: 3

Follow up:
What if the BST is modified (insert/delete operations) often and you need to find the kth smallest 
frequently? How would you optimize the kthSmallest routine?

Ans:
Let us use here the same logic as for LRU cache design, and combine an indexing structure (we could 
keep BST here) with a double linked list.

Such a structure would provide:

O(H) time for the insert and delete.

O(k) for the search of kth smallest.



******************************************************
key:
  - use inorder
  - edge case:
    1) empty string, return empty
    2)

******************************************************



=======================================================================================================
Method 1:


Stats:

  - 
  - 


Method:

  - 
  - 


 public int kthSmallest(TreeNode root, int k) {
     Stack<TreeNode> stack = new Stack<>();
     while(root != null || !stack.isEmpty()) {
         while(root != null) {
             stack.push(root);    
             root = root.left;   
         } 
         root = stack.pop();
         if(--k == 0) break;
         root = root.right;
     }
     return root.val;
 }







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: recursion


class Solution {

  public int kthSmallest(TreeNode root, int k) {
    ArrayList<Integer> nums = inorder(root, new ArrayList<Integer>());
    return nums.get(k - 1);
  }

  public ArrayList<Integer> inorder(TreeNode root, ArrayList<Integer> arr) {
    if (root == null) return arr;
    
    inorder(root.left, arr);
    arr.add(root.val);
    inorder(root.right, arr);
    return arr;
  }

}

dfs time complexity: O(N)


  // better keep these two variables in a wrapper class
  private static int number = 0;
  private static int count = 0;

  public int kthSmallest(TreeNode root, int k) {
      count = k;
      helper(root);
      return number;
  }
  
  public void helper(TreeNode n) {
      if (n.left != null) helper(n.left);
      count--;
      if (count == 0) {
          number = n.val;
          return;
      }
      if (n.right != null) helper(n.right);
  }

~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

class Solution:
    def kthSmallest(self, root, k):
        """
        :type root: TreeNode
        :type k: int
        :rtype: int
        """
        def inorder(r):
            return inorder(r.left) + [r.val] + inorder(r.right) if r else []
    
        return inorder(root)[k - 1]

=======================================================================================================
method 3:for follow up
https://leetcode.com/problems/kth-smallest-element-in-a-bst/solutions/1407567/follow-up-solution-with-o-log-n-time-complexity-orderstatisticstree/?envType=study-plan-v2&envId=top-interview-150

Stats:

  - 
  - 


Method:

  - 
  - 




We can find kthSmallest in O(log(n)) if we build a tree in a certain way.
The only addition we need - is to have "size" property for each TreeNode. Size can be found as:

node.size = 1 + size(node.left) + size(node.right);
If we know size for each node, we can implement FindByRank operation in O(log(n)) time:
https://www.cs.yale.edu/homes/aspnes/pinewiki/OrderStatisticsTree.html

Solution below will demonstrate how it works, however it is slower that regular O(n) solutions, because we are building the tree in custom way, calculating size for each node and only then calculating "Rank".
Solution below is O(nlog(n)): build tree (O(nlog(n)) + findRank (O(log(n))
If we had "size" property initially in the tree, we would have found kthSmallest (findByRank) in O(log(n)) time.

class Solution {
//region precoditions
    class TreeNodeWithSize {
        int val;
        int size;
        TreeNodeWithSize left;
        TreeNodeWithSize right;
        TreeNodeWithSize(int val) { this.val = val; }
    }

    class OrderStatisticsTree {
        TreeNodeWithSize root;

       public int size(TreeNodeWithSize node){
            if(node == null){
                return 0;
            }
            return node.size;
        }

        public void put(TreeNodeWithSize node){
            root = put(root, node);
        }

        private TreeNodeWithSize put(TreeNodeWithSize x, TreeNodeWithSize node){
            if(x == null) {
                node.size = 1;
                return node;
            }

            if(node.val < x.val){
                x.left = put(x.left, node);
            }else if(node.val > x.val){
                x.right = put(x.right, node);
            }else{
                x.val = node.val; //redundant, but leaving it here for display purposes
            }
            x.size = 1 + size(x.left) + size(x.right);
            return x;
        }

        public int findByRank(int rank){
            return findByRank(root, rank);
        }

        private int findByRank(TreeNodeWithSize root, int rank){
            if(root == null) throw new IllegalArgumentException("empty tree");

            int leftSize = 0;
            if(root.left != null){
                leftSize = root.left.size;
            }

            if(rank <= leftSize){
                return findByRank(root.left, rank);
            }else if(rank > leftSize + 1){
                return findByRank(root.right, rank - leftSize - 1);
            }
            return root.val;
        }
    }
//endregion 

//region problem solving
    OrderStatisticsTree bst = new OrderStatisticsTree();
    public int kthSmallest(TreeNode root, int k) {
        buildOrderStatisticsTree(root);
        return bst.findByRank(k);
    }
//endregion 

    public void buildOrderStatisticsTree(TreeNode node) {
        if (node == null){
            return;
        }

        bst.put(new TreeNodeWithSize(node.val));
        buildOrderStatisticsTree(node.left);
        buildOrderStatisticsTree(node.right);
    }
}


