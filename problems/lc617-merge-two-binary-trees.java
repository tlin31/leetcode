617. Merge Two Binary Trees - Easy

Given two binary trees and imagine that when you put one of them to cover the other, some nodes 
of the two trees are overlapped while the others are not.

You need to merge them into a new binary tree. The merge rule is that if two nodes overlap, then sum
node values up as the new value of the merged node. Otherwise, the NOT null node will be used as the
node of new tree.

Example 1:

Input: 
	Tree 1                     Tree 2                  
          1                         2                             
         / \                       / \                            
        3   2                     1   3                        
       /                           \   \                      
      5                             4   7                  
Output: 
Merged tree:
	     3
	    / \
	   4   5
	  / \   \ 
	 5   4   7
 

Note: The merging process must start from the root nodes of both trees.

******************************************************
key:
	- 
	- edge case:
		1) if any one of the head == null, return the other tree
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- 合并两个二叉树当然就是同步遍历了，对于相同位置上的一对结点n1和n2，处理策略是：
		- 若n1和n2都存在，则只需要保留其中一个结点（如n1），将另一结点的值加到此结点上即可（如n1.val += n2.val）
		- 若n1或n2任一不存在，则合并后的二叉树对应位置上的结点就是存在的那个了。
		- 若n1和n2都不存在，则合并后仍不存在。
	- 所以我们可以假定第一颗树为“主树”，第二颗树为“从树”：碰到对应位置上“主树”结点存在且“从树”结点存在，则将“从树”结点
		值加到“主树”；碰到对应位置上“主树”结点存在而“从树”结点不存在，则不作任何处理；碰到对应位置上“主树”结点不存在
		而“从树”结点存在，则将“从树”结点移到“主树”对应位置下。
	- recursion

stats:
	- Time: O(n)
	- Space: O(height)
	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Merge Two Binary Trees.
	- Memory Usage: 40.6 MB, less than 100.00%

    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
		if (t1 == null) {
		   return t2;
		}

		if (t2 != null) {
		  t1.val += t2.val;
		  t1.left = mergeTrees(t1.left, t2.left);
		  t1.right = mergeTrees(t1.right, t2.right);
		}

		return t1;
	}


=======================================================================================================
method 2:

method:

	- iterative dfs
	- 

stats:

	- 
	- Time: O(n)
	- Space: O(height)

	public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
		  if (t1 == null) {
		    return t2;
		  }

		  // Use stack to help DFS
		  Deque<TreeNode[]> stack = new LinkedList<>();

		  //use brackets to add both nodes 
		  stack.offerLast(new TreeNode[] {t1, t2});

		  while (!stack.isEmpty()) {
		    TreeNode[] cur = stack.pollLast();
		    // no need to merge t2 into t1
		    if (cur[1] == null) {
		      continue;
		    }
		    // merge t1 and t2
		    cur[0].val += cur[1].val;
		    // if node in t1 == null, use node in t2 instead
		    // else put both nodes in stack to merge
		    if (cur[0].left == null) {
		      cur[0].left = cur[1].left;
		    } else {
		      stack.offerLast(new TreeNode[] {cur[0].left, cur[1].left});
		    }

		    if (cur[0].right == null) {
		      cur[0].right = cur[1].right;
		    } else {
		      stack.offerLast(new TreeNode[] {cur[0].right, cur[1].right});
		    }
	  }
	  return t1;
	}

=======================================================================================================
method 3:

method:

	- Iterative BFS
	- 

stats:

	- Time: O(n)
	- Space: O(n)


	public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
		  if (t1 == null) {
		    return t2;
		  }
		  // Use stack to help DFS
		  Queue<TreeNode[]> queue = new LinkedList<>();
		  queue.offer(new TreeNode[] {t1, t2});
		  while (!queue.isEmpty()) {
		    TreeNode[] cur = queue.poll();
		    // no need to merge t2 into t1
		    if (cur[1] == null) {
		      continue;
		    }
		    // merge t1 and t2
		    cur[0].val += cur[1].val;
		    // if node in t1 == null, use node in t2 instead
		    // else put both nodes in stack to merge
		    if (cur[0].left == null) {
		      cur[0].left = cur[1].left;
		    } else {
		      queue.offer(new TreeNode[] {cur[0].left, cur[1].left});
		    }
		    if (cur[0].right == null) {
		      cur[0].right = cur[1].right;
		    } else {
		      queue.offer(new TreeNode[] {cur[0].right, cur[1].right});
		    }
		  }
		  return t1;
	}


