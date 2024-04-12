95. Unique Binary Search Trees II - Medium

Given an integer n, generate all structurally unique BST's (binary search trees) that store 
values 1 ... n.

Example:
Input: 3
Output:
[
  [1,null,3,2],
  [3,2,null,1],
  [3,1,null,null,2],
  [2,1,3],
  [1,null,2,null,3]
]
Explanation:
The above output corresponds to the 5 unique BST's shown below:

   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3

******************************************************
key:
  - dp
  - edge case:
    1) n = 0, return null / empty
    2) n = 1, return itself

******************************************************



=======================================================================================================
method 1:

method:

  - 二叉树的性质。左子树的所有值小于根节点，右子树的所有值大于根节点。

    所以如果求 1...n 的所有可能。

    我们只需要把 1 作为根节点，[ ] 空作为左子树，[ 2 ... n ] 的所有可能作为右子树。

    2 作为根节点，[ 1 ] 作为左子树，[ 3...n ] 的所有可能作为右子树。

    3 作为根节点，[ 1 2 ] 的所有可能作为左子树，[ 4 ... n ] 的所有可能作为右子树，然后左子树和右子树两两组合。

    4 作为根节点，[ 1 2 3 ] 的所有可能作为左子树，[ 5 ... n ] 的所有可能作为右子树，然后左子树和右子树两两组合。

    ...

    n 作为根节点，[ 1... n ] 的所有可能作为左子树，[ ] 作为右子树。

    至于，[ 2 ... n ] 的所有可能以及 [ 4 ... n ] 以及其他情况的所有可能，可以利用上边的方法，把每个数字作为根节点，
      然后把所有可能的左子树和右子树组合起来即可。

    如果只有一个数字，那么所有可能就是一种情况，把该数字作为一棵树。而如果是 [ ]，那就返回 null。

  - 

stats:

  - Runtime: 1 ms, faster than 100.00% of Java online submissions for Unique Binary Search Trees II.
  - Memory Usage: 39.5 MB, less than 57.14%

  public List<TreeNode> generateTrees(int n) {
      List<TreeNode> ans = new ArrayList<TreeNode>();
      if (n == 0) {
          return ans;
      }
      return getAns(1, n);

  }

  private List<TreeNode> getAns(int start, int end) { 
      List<TreeNode> ans = new ArrayList<TreeNode>();

      //此时没有数字，将 null 加入结果中
      if (start > end) {
          ans.add(null);
          return ans;
      }

      //只有一个数字，当前数字作为一棵树加入结果中
      if (start == end) {
          TreeNode tree = new TreeNode(start);
          ans.add(tree);
          return ans;
      }

      //尝试每个数字作为根节点
      for (int i = start; i <= end; i++) {
          //得到所有可能的左子树
          List<TreeNode> leftTrees = getAns(start, i - 1);

           //得到所有可能的右子树
          List<TreeNode> rightTrees = getAns(i + 1, end);

          //combine left and right tree
          for (TreeNode leftTree : leftTrees) {
              for (TreeNode rightTree : rightTrees) {
                  TreeNode root = new TreeNode(i);
                  root.left = leftTree;
                  root.right = rightTree;

                  //加入到最终结果中
                  ans.add(root);
              }
          }
      }
      return ans;
  } 




=======================================================================================================
method 2:

method:

  - dp， 发现结构一样
  - 就是求出长度为 1 的所有可能，长度为 2 的所有可能 ... 直到 n。
    但是我们注意到，求长度为 2 的所有可能的时候，我们需要求 [ 1 2 ] 的所有可能，[ 2 3 ] 的所有可能，这只是 n = 3 
      的情况。如果 n 等于 100，我们需要求的更多了 [ 1 2 ] ， [ 2 3 ] ， [ 3 4 ] ... [ 99 100 ] 太多了。
    仔细观察，我们可以发现长度是为 2 的所有可能其实只有两种结构。
  - 所以我们 n = 100 的时候，求长度是 2 的所有情况的时候，我们没必要把 [ 1 2 ] ， [ 2 3 ] ， [ 3 4 ] ... 
      [ 99 100 ] 所有的情况都求出来，只需要求出 [ 1 2 ] 的所有情况即可。
    推广到任意长度 len，我们其实只需要求 [ 1 2 ... len ] 的所有情况就可以了 [ 99 100 ] 怎么办呢？我们只求了 
      [ 1 2 ] 的所有情况。答案很明显了，在 [ 1 2 ] 的所有情况每个数字加一个偏差 98，即加上根节点的值就可以了。

        private TreeNode clone(TreeNode n, int offset) {
            if (n == null) {
                return null;
            }
            TreeNode node = new TreeNode(n.val + offset);
            node.left = clone(n.left, offset);
            node.right = clone(n.right, offset);
            return node;
        }

  - 而求长度为 len 的所有情况，我们只需要求出一个代表 [ 1 2 ... len ] 的所有情况，其他的话加上一个偏差，加上当前根
      节点即可。

stats:

  - 
  - 

  public List<TreeNode> generateTrees(int n) {
      ArrayList<TreeNode>[] dp = new ArrayList[n + 1];
      dp[0] = new ArrayList<TreeNode>();
      if (n == 0) {
          return dp[0];
      }
      dp[0].add(null);
      
      //长度为 1 到 n, build dp
      for (int len = 1; len <= n; len++) {
          dp[len] = new ArrayList<TreeNode>();

          //将不同的数字作为根节点，只需要考虑到 len
          for (int root = 1; root <= len; root++) {
              int left = root - 1;  //左子树的长度
              int right = len - root; //右子树的长度
              for (TreeNode leftTree : dp[left]) {
                  for (TreeNode rightTree : dp[right]) {
                      TreeNode treeRoot = new TreeNode(root);
                      treeRoot.left = leftTree;
                      //克隆右子树并且加上偏差
                      treeRoot.right = clone(rightTree, root);
                      dp[len].add(treeRoot);
                  }
              }
          }
      }
      return dp[n];
  }

  private TreeNode clone(TreeNode n, int offset) {
      if (n == null) {
          return null;
      }
      TreeNode node = new TreeNode(n.val + offset);
      node.left = clone(n.left, offset);
      node.right = clone(n.right, offset);
      return node;
  }


=======================================================================================================
method 3:

method:

  - dp
  - 首先我们每次新增加的数字大于之前的所有数字，所以新增加的数字出现的位置只可能是根节点或者是根节点的右孩子，右孩子的
      右孩子，右孩子的右孩子的右孩子等等，总之一定是右边。
  - 其次，新数字所在位置原来的子树，改为当前插入数字的左孩子即可，因为插入数字是最大的。
  - 由于求当前的所有解只需要上一次的解，所有我们只需要两个 list，pre 保存上一次的所有解， cur 计算当前的所有解。



stats:

  - Runtime: 1 ms, faster than 100.00% of Java online submissions for Unique Binary Search Trees II.
  - Memory Usage: 37.9 MB, less than 97.62%

  public List<TreeNode> generateTrees(int n) {
      List<TreeNode> pre = new ArrayList<TreeNode>();
      if (n == 0) {
          return pre;
      }
      pre.add(null);

      //每次增加一个数字
      for (int i = 1; i <= n; i++) {
          List<TreeNode> cur = new ArrayList<TreeNode>();

          //遍历之前的所有解
          for (TreeNode root : pre) {
              //情况1：插入到根节点
              TreeNode insert = new TreeNode(i);
              insert.left = root;
              cur.add(insert);


              //情况2： 插入到右孩子，右孩子的右孩子...最多找 n 次孩子
              for (int j = 0; j <= n; j++) {
                  TreeNode root_copy = treeCopy(root); //复制当前的树
                  TreeNode right = root_copy; //找到要插入右孩子的位置
                  int k = 0;
                  //遍历 j 次找右孩子
                  for (; k < j; k++) {
                      if (right == null)
                          break;
                      right = right.right;
                  }
                  //到达 null 提前结束
                  if (right == null)
                      break;
                  //保存当前右孩子的位置的子树作为插入节点的左孩子
                  TreeNode rightTree = right.right;
                  insert = new TreeNode(i);
                  right.right = insert; //右孩子是插入的节点
                  insert.left = rightTree; //插入节点的左孩子更新为插入位置之前的子树
                  //加入结果中
                  cur.add(root_copy);
              }
          }
          pre = cur;

      }
      return pre;
  }


  private TreeNode treeCopy(TreeNode root) {
      if (root == null) {
          return root;
      }
      TreeNode newRoot = new TreeNode(root.val);
      newRoot.left = treeCopy(root.left);
      newRoot.right = treeCopy(root.right);
      return newRoot;
  }




