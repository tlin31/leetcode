1367. Linked List in Binary Tree - Medium


Given a binary tree root and a linked list with head as the first node. 

Return True if all the elements in the linked list starting from the head correspond to some 
downward path connected in the binary tree otherwise return False.

In this context downward path means a path that starts at some node and goes downwards.

 

Example 1:



Input: head = [4,2,8], root = [1,4,4,null,2,2,null,1,null,6,8,null,null,null,null,1,3]
Output: true
Explanation: Nodes in blue form a subpath in the binary Tree.  

Example 2:

Input: head = [1,4,2,6], root = [1,4,4,null,2,2,null,1,null,6,8,null,null,null,null,1,3]
Output: true

Example 3:

Input: head = [1,4,2,6,8], root = [1,4,4,null,2,2,null,1,null,6,8,null,null,null,null,1,3]
Output: false
Explanation: There is no path in the binary tree that contains all the elements of the linked list 
from head.
 

Constraints:

1 <= node.val <= 100 for each node in the linked list and binary tree.
The given linked list will contain between 1 and 100 nodes.
The given binary tree will contain between 1 and 2500 nodes.


******************************************************
key:
	- recursion or DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DFS


Stats:

	- Time O(N * min(L,H)), Space O(H), where N = tree size, H = tree height, L = list length.

	- 


Method:

	-	
	-	


    public boolean isSubPath(ListNode head, TreeNode root) {
        if (head == null) return true;
        if (root == null) return false;
        return dfs(head, root) || isSubPath(head, root.left) || isSubPath(head, root.right);
    }

    private boolean dfs(ListNode head, TreeNode root) {
        if (head == null) return true;
        if (root == null) return false;
        return head.val == root.val && (dfs(head.next, root.left) || dfs(head.next, root.right));
    }







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

 def isSubPath(self, head, root):
        def dfs(head, root):
            if not head: return True
            if not root: return False
            return root.val == head.val and (dfs(head.next, root.left) or dfs(head.next, root.right))
        if not head: return True
        if not root: return False
        return dfs(head, root) or self.isSubPath(head, root.left) or self.isSubPath(head, root.right)


=======================================================================================================
method 2: 	KMP algorithm

Stats:

	- Time O(N), Space O(L + H), where N = tree size, H = tree height, L = list length.
	- 


Method:
	- Iterate the whole link, find the maximum matched length of prefix.
	- Iterate the whole tree, find the maximum matched length of prefix.
	- This problem is the same with problem: find needle in haystack where haystack is the binary tree, 
	   	needle is the linked list, & Convert needle linked list to array, so we can jump to any 
	   	position of the needle.
	- dp[] store all possible remaining linkedlist avoid searching repeatedly. For every node, we 
	  need to check if it could be the real head of the list (so we add the head to our list every 
	  time). Keep searching and passing all possible lists until we reach the end of one of lists.



    public boolean isSubPath(ListNode head, TreeNode root) {
        List<Integer> A = new ArrayList(), 
                     dp = new ArrayList();
        A.add(head.val);
        dp.add(0);
        int i = 0;
        head = head.next;
        while (head != null) {
            while (i > 0 && head.val != A.get(i))
                i = dp.get(i - 1);

            if (head.val == A.get(i)) 
            	++i;
            A.add(head.val);
            dp.add(i);
            head = head.next;
        }
        return dfs(root, 0, A, dp);
    }

    // i indicates the matched position in A
    private boolean dfs(TreeNode root, int i, List<Integer> A, List<Integer> dp) {
        if (root == null) 
        	return false;

        // break the sequence,
        while (i > 0 && root.val != A.get(i))
            i = dp.get(i - 1);

        if (root.val == A.get(i)) 
        	++i;

        return i == dp.size() || dfs(root.left, i, A, dp) || dfs(root.right, i, A, dp);
    }


~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

 def isSubPath(self, head, root):
        A, dp = [head.val], [0]
        i = 0
        node = head.next
        while node:
            while i and node.val != A[i]:
                i = dp[i - 1]
            i += node.val == A[i]
            A.append(node.val)
            dp.append(i)
            node = node.next

        def dfs(root, i):
            if not root: return False
            while i and root.val != A[i]:
                i = dp[i - 1]
            i += root.val == A[i]
            return i == len(dp) or dfs(root.left, i) or dfs(root.right, i)
        return dfs(root, 0)

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

