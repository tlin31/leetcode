666. Path Sum IV - Medium


If the depth of a tree is smaller than 5, then this tree can be represented by a list of three-digits 
integers.

For each integer in this list:

	1. The hundreds digit represents the depth D of this node, 1 <= D <= 4.
	2. The tens digit represents the position P of this node in the level it belongs to, 1 <= P <= 8. 
		The position is the same as that in a full binary tree.
	3. The units digit represents the value V of this node, 0 <= V <= 9.

Given a list of ascending three-digits integers representing a binary tree with the depth smaller 
than 5, you need to return the sum of all paths from the root towards the leaves.

Its guaranteed that the given list represents a valid connected binary tree.

Example 1:

Input: [113, 215, 221]
Output: 12
Explanation: 
The tree that the list represents is:
    3
   / \
  5   1

The path sum is (3 + 5) + (3 + 1) = 12.
 

Example 2:

Input: [113, 221]
Output: 4
Explanation: 
The tree that the list represents is: 
    3
     \
      1

The path sum is (3 + 1) = 4.


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: 

Stats:

	- O(N). The analysis is the same as in Approach #1.

	- 


Method:

	-	As in Approach #1, we will depth-first search on the tree. One time-saving idea is that we 
	  can use num / 10 = 10 * depth + pos as a unique identifier for that node. The left child of 
	  such a node would have identifier 10 * (depth + 1) + 2 * pos - 1, and the right child would 
	  be one greater.



class Solution {
    int ans = 0;
    Map<Integer, Integer> values;

    public int pathSum(int[] nums) {
        values = new HashMap();
        for (int num: nums)
            values.put(num / 10, num % 10);

        // pass in values
        dfs(nums[0] / 10, 0);
        return ans;
    }

    public void dfs(int node, int sum) {
        if (!values.containsKey(node)) 
        	return;
        	
        sum += values.get(node);

        int depth = node / 10, pos = node % 10;
        int left = (depth + 1) * 10 + 2 * pos - 1;
        int right = left + 1;

        if (!values.containsKey(left) && !values.containsKey(right)) {
            ans += sum;
        } else {
            dfs(left, sum);
            dfs(right, sum);
        }
    }
}



=======================================================================================================
method 2: convert to trees



Stats:

	- Time: O(N) where NN is the length of nums. We construct the graph and traverse it in this time.
	- Space Complexity: O(N), the size of the implicit call stack in our depth-first search.



Method:

	- Convert the given array into a tree using Node objects. Afterwards, for each path from root 
	  to leaf, we can add the sum of that path to our answer.

Algo:
	- There are two steps, the tree construction, and the traversal.

	- In the tree construction, we have some depth, position, and value, and we want to know where 
	  the new node goes. With some effort, we can see the relevant condition for whether a node 
	  should be left or right is pos - 1 < 2**(depth - 2). For example, when depth = 4, the positions 
	  are 1, 2, 3, 4, 5, 6, 7, 8, and its left when pos <= 4.

	- In the traversal, we perform a depth-first search from root to leaf, keeping track of the 
	  current sum along the path we have travelled. Every time we reach a leaf (node.left == null && 
	  node.right == null), we have to add that running sum to our answer.




	class Node {
	    Node left, right;
	    int val;
	    Node(int v) {
	    	val = v;
	    }
	}

	class Solution {
	    int ans = 0;
	    public int pathSum(int[] nums) {
	        Node root = new Node(nums[0] % 10);

	        for (int num: nums) {

	        	// skip the root node
	            if (num == nums[0]) 
	            	continue;

	            int depth = num / 100, 
	            	pos = num / 10 % 10, 
	            	val = num % 10;

	            pos--;
	            Node cur = root;
	            for (int d = depth - 2; d >= 0; --d) {
	                if (pos < 1<<d) {
	                    if (cur.left == null) 
	                    	cur.left = new Node(val);

	                    cur = cur.left;
	                } 

	                else {
	                    if (cur.right == null) 
	                    	cur.right = new Node(val);

	                    cur = cur.right;
	                }
	                pos %= 1<<d;
	            }
	        }

	        dfs(root, 0);
	        return ans;
	    }

	    public void dfs(Node node, int sum) {
	        if (node == null) 
	        	return;
	        sum += node.val;
	        if (node.left == null && node.right == null) {
	            ans += sum;
	        } else {
	            dfs(node.left, sum);
	            dfs(node.right, sum);
	        }
	    }
	}






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

classNode(object):
    def __init__(self, val):
        self.val = val
        self.left = self.right = None

classSolution(object):
    def pathSum(self, nums):
        self.ans = 0
        root = Node(nums[0] % 10)

        for x in nums[1:]:
            depth, pos, val = x/100, x/10 % 10, x % 10
            pos -= 1
            cur = root
            for d in xrange(depth - 2, -1, -1):
                if pos < 2**d:
                    cur.left = cur = cur.left or Node(val)
                else:
                    cur.right = cur = cur.right or Node(val)

                pos %= 2**d

        def dfs(node, running_sum = 0):
            if not node: return
            running_sum += node.val
            if not node.left and not node.right:
                self.ans += running_sum
            else:
                dfs(node.left, running_sum)
                dfs(node.right, running_sum)

        dfs(root)
        return self.ans

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	


class Solution {
    public int pathSum(int[] nums) {
        int[][] m = new int[5][8];
        for (int n : nums) {
            int i = n / 100; // i is 1 based index;
            int j = (n % 100) / 10 - 1; // j used 0 based index;
            int v = n % 10;
            m[i][j] = m[i - 1][j / 2] + v;
        }

        int sum = 0;
        for (int i = 1; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 4 || m[i][j] != 0 && m[i + 1][j * 2] == 0 && m[i + 1][j * 2 + 1] == 0){
                    sum += m[i][j];
                }
            }
        }
        return sum;        
    }
}









~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

