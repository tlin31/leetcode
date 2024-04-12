1339. Maximum Product of Splitted Binary Tree - Medium


Given a binary tree root. Split the binary tree into two subtrees by removing 1 edge such that 
the product of the sums of the subtrees are maximized.

Since the answer may be too large, return it modulo 10^9 + 7.

 

Example 1:



Input: root = [1,2,3,4,5,6]
Output: 110
Explanation: Remove the red edge and get 2 binary trees with sum 11 and 10. Their product is 110 
(11*10)


Example 2:

Input: root = [1,null,2,3,4,null,null,5,6]
Output: 90
Explanation:  Remove the red edge and get 2 binary trees with sum 15 and 6.Their product is 90 (15*6)


Example 3:

Input: root = [2,3,9,10,7,8,6,5,4,11,1]
Output: 1025

Example 4:
Input: root = [1,1]
Output: 1
 

Constraints:

Each tree has at most 50000 nodes and at least 2 nodes.
Each nodes value is between [1, 10000].


******************************************************
key:
	- tree, DP, pre-order
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time O(N)
	- Space O(height)



Method:

	-	First pass, get the total sum.
		Now we have the right total sum of the whole tree.
		Second pass, find the biggest product.


Overflow:
	For those who are not using Python and are worried about overflow:
	You can use long int (which is 64-bit long)
	Each tree has at most 50000 nodes and at least 2 nodes.
	Each node's value is between [1, 10000].
	So the maximum possible sum 5000 * 100000 = 500000000 = 0x1DCD6500 won't overflow 32-bit int.
	500000000 * 500000000 won't overflow 64-bit long.

Mod:
	- Don't do MOD too early when calculate the sum, because that will give you wrong result when 
	  you look for the maximum value.




class Solution {
    long res = 0, total = 0, sub;
    public int maxProduct(TreeNode root) {
        total = s(root); 
        s(root);
        return (int)(res % (int)(1e9 + 7));
    }

    //get the sum of a sub tree
    private long s(TreeNode root) {
        if (root == null) 
        	return 0;
        sub = root.val + s(root.left) + s(root.right);
        res = Math.max(res, sub * (total - sub));
        return sub;
    }
}


-------------------------------------------

static int MOD = 1000000007;
public int maxProduct(TreeNode root) {
    List<Long> sums = new ArrayList<>();
    long total = helper(root, sums);
    long max = 0;
    for(long s : sums){
        long p = s * (total - s);
        max = Math.max(max, p);
    }
    return (int)(max % MOD);
}

public long helper(TreeNode node, List<Long> sums){
    if(node == null){
        return 0;
    }
    long left = helper(node.left, sums);
    long right = helper(node.right, sums);
    long sum = left + right + node.val;
    sums.add(sum); 
    return sum;
}

ex.input = [1,2,3,4,5,6]

	 1
  2    3
 4 5    6

sums = [4, 5, 11, 6, 9, 21]


~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: post order

Stats:

	- 
	- 


Method:

	-	
	-	


class Solution {
    static long sum;
    static long maxProd;
    public static void dfs(TreeNode root) {
        if(root == null) return;
        sum += (long)root.val;
        dfs(root.left);
        dfs(root.right);
    }
    public static long checkMax(TreeNode root) {
        if(root == null) return 0;
        long l = checkMax(root.left);
        long r = checkMax(root.right);
        maxProd = Math.max(maxProd, (l + r + root.val) * (sum - l - r - root.val));
        return l + r + root.val;
    }
    public int maxProduct(TreeNode root) {
        sum = 0;
        maxProd = 0;
        dfs(root);
        long rootProd = checkMax(root);
        return (int)(maxProd % ((int)Math.pow(10, 9) + 7));
    }
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
    def maxProduct(self, root: TreeNode) -> int:
        self.sum = self.getSum(root)
        return self.helper(root)[1] % (10**9 + 7)
    
    def getSum(self, root):
        if not root:
            return 0
        left_sum = self.getSum(root.left)
        right_sum = self.getSum(root.right)
        return left_sum + right_sum + root.val
        
    def helper(self, root):
        if not root:
            return 0, 0
        left_sum, left_max = self.helper(root.left)
        right_sum, right_max = self.helper(root.right)
        bottom_sum = left_sum + right_sum + root.val
        num_max = max(left_max, right_max, left_sum * (self.sum - left_sum), right_sum * (self.sum - right_sum))
        return bottom_sum, num_max

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

