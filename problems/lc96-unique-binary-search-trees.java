96. Unique Binary Search Trees - Medium

Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?

Example:

Input: 3
Output: 5
Explanation:
Given n = 3, there are a total of 5 unique BST's:

   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3

******************************************************
key:
	- recursion/backtrack
	- edge case:
		1) n == 0, return 0
		2) n == 1, return 1 

******************************************************



=======================================================================================================
method 1:

method:

	- 我们可以利用一下查找二叉树的性质。左子树的所有值小于根节点，右子树的所有值大于根节点。
		所以如果求 1...n 的所有可能。
		我们只需要把 1 作为根节点，[ ] 空作为左子树，[ 2 ... n ] 的所有可能作为右子树。
		2 作为根节点，[ 1 ] 作为左子树，[ 3...n ] 的所有可能作为右子树。
		3 作为根节点，[ 1 2 ] 的所有可能作为左子树，[ 4 ... n ] 的所有可能作为右子树，然后左子树和右子树两两组合。
		4 作为根节点，[ 1 2 3 ] 的所有可能作为左子树，[ 5 ... n ] 的所有可能作为右子树，然后左子树和右子树两两组合。
		...
		n 作为根节点，[ 1... n ] 的所有可能作为左子树，[ ] 作为右子树。
		至于，[ 2 ... n ] 的所有可能以及 [ 4 ... n ] 
		以及其他情况的所有可能，可以利用上边的方法，把每个数字作为根节点，然后把所有可能的左子树和右子树组合起来即可。
		如果只有一个数字，那么所有可能就是一种情况，把该数字作为一棵树。而如果是 [ ]，那就返回 null。
		对于这道题，我们会更简单些，只需要返回树的数量即可。求当前根的数量，只需要左子树的数量乘上右子树。
	- 

	- 我们只关心数量，所以不需要具体的范围，而是传树的节点的数量即可。
	- memorization

stats:

	- 
	- 


	public int numTrees(int n) { 
	    if (n == 0) {
	        return 0;
	    }
	    HashMap<Integer,Integer> memoization = new HashMap<>();
	    return getAns(n,memoization);

	}

	private int getAns(int n, HashMap<Integer,Integer> memoization) { 
	    if(memoization.containsKey(n)){
	        return memoization.get(n);
	    }

	    int ans = 0;
	    //此时没有数字，只有一个数字,返回 1
	    if (n==0 ||n==1) { 
	        return 1;
	    } 

	    //尝试每个数字作为根节点
	    for (int i = 1; i <= n; i++) {
	        //得到所有可能的左子树
	        int leftTreesNum = getAns(i-1,memoization);
	        //得到所有可能的右子树
	        int rightTreesNum  = getAns(n-i,memoization);
	        //左子树右子树两两组合
	        ans+=leftTreesNum * rightTreesNum;
	    }
	    
	    memoization.put(n, ans);
	    return ans;
	}

=======================================================================================================
method 2:

method:

	- dp
	- 或者直接从这里的解法一的思路考虑，因为递归是从顶层往下走，压栈压栈压栈，到了长度是 0 或者是 1 就出栈出栈出栈。
		我们可以利用动态规划的思想，直接从底部往上走。求出长度是 0，长度是 1，长度是 2....长度是 n 的解。用一个数组 
		dp 把这些结果全部保存起来。


stats:
	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Unique Binary Search Trees.
	- Memory Usage: 32.8 MB, less than 5.55%

	public int numTrees(int n) {
	    int[] dp = new int[n + 1];
	    dp[0] = 1;
	    if (n == 0) {
	        return 0;
	    }
	    // 长度为 1 到 n
	    for (int len = 1; len <= n; len++) {
	        // 将不同的数字作为根节点，只需要考虑到 len
	        for (int root = 1; root <= len; root++) {
	            int left = root - 1; // 左子树的长度
	            int right = len - root; // 右子树的长度
	            dp[len] += dp[left] * dp[right];
	        }
	    }
	    return dp[n];
	}


