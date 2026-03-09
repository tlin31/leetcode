# Dynamic Programming 

## When to use DP
1. when recursion goes beyond O(n^2)
2. Counting ways using n different resources
3. optimization 

## 一些注意的点

### dp初始化为n+1还是n？

凡是这种问题：
- 是否能拆分
- 是否能到达
- 是否能匹配前缀
- 👉 dp[i] 通常表示“前 i 个元素是否可行”

  - 所以：dp 大小 = n + 1， dp[0] = true

ex. leetcode 139 word break： 给一个string s 和一个字典wordDict，判断s是否可以被空格拆分为一个或多个在字典中出现的单词。

- 因为 dp[i] 表示的是：前 i 个字符(不包括i)是否可以被成功拆分。
- 当 i = 0 时表示“空字符串”，当 i = s.length() 时表示“整个字符串”，所以需要 length + 1 个状态来标记包括s的最后一个char的所有字符

```java

public class Solution {
    public boolean wordBreak(String s, Set<String> dict) {
        
        boolean[] dp = new boolean[s.length() + 1];        
        dp[0] = true;
 
        for(int i=1; i <= s.length(); i++){
            for(int j=0; j < i; j++){
                if(dp[j] && dict.contains(s.substring(j, i))){
                    dp[i] = true;
                    break;
                }
            }
        }
        
        return dp[s.length()];
    }
}

```


## 要确定的条件

1. 确定 base case
- ex. 目标⾦额 amount 为 0 时算法返回 0，因为不需要任何硬币就已经凑出目标金额了了。

2. 确定「状态」，也就是原问题和⼦问题中会变化的变量。
- ex. 由于硬币数量量⽆无限，硬币的⾯面额也是题⽬目 给定的，只有⽬目标⾦金金额会不不断地向 base case 靠近，所以唯⼀一的「状态」就是⽬目标⾦金金额 amount 。

3. 确定「选择」，也就是导致「状态」产⽣变化的行为。
- ex. ⽬标金额为什么变化，因为你在选择硬币，你每选择⼀枚硬币，就相当于减少了目标⾦额。所以说所有硬币的面值，就是你的「选择」。

4. 明确 dp 函数/数组的定义。
- ex. 我们这⾥讲的是⾃自顶向下的解法，所以会有⼀个递归的 dp 函数，⼀般来说函数的参数就是状态转移中会变化的量量，也就是上⾯面说到的「状态」;函数的返回值就是题⽬目要 求我们计算的量量。就本题来说，状态只有⼀一个，即「⽬目标⾦金金额」，题⽬目要求我们计算凑出⽬目标⾦金金额所需 的最少硬币数量量。所以我们可以这样定义 dp 函数:



## Impementations
    
    1. Find recursive relation
    2. Recursive (top-down)
    3. Recursive + memo (top-down)
    4. Iterative + memo (bottom-up)
    5. Iterative + N variables (bottom-up)

### 

```java
    # 初始化 base case dp[0][0][...] = base
    # 进⾏行行状态转移
    for 状态1 in 状态1的所有取值:
        for 状态2 in 状态2的所有取值: 
            for ...
                dp[状态1][状态2][...] = 求最值(选择1，选择2...)
```
## Typical problems

### 1. Min(max) path to reach a target

    routes[i]=min(routes[i-1],routes[i-2],routes[i-3],...,routes[i-k])+cost[i]

Leetcode 322. Coin change


Leetcode 55 Jump Game - Medium *Reachable*/Greedy

- Each element in the array represents your maximum jump length at that position. Determine if you are able to reach the last index.
- The basic idea is this: at each step, we keep track of the furthest reachable index. The nature of the problem (eg. maximal jumps where you can hit a range of targets instead of singular jumps where you can only hit one target) is that for an index to be reachable, each of the previous indices have to be reachable.

```java
       for(int i = 0; i < nums.length; i ++) {
           if(i > reachable) 
                return false;
           reachable = Math.max(reachable, i + nums[i]);
       } 
```


### 2. Distinct ways

    routes[i]=routes[i-1]+routes[i-2]+routes[i-3]+...routes[i-k]

Leetcode 70. climb stiars


### 3. Merging intervals
    //from i to j
    dp[i][j]=dp[i][k]+result[k]+dp[k+1][j]

Leetcode 96. Unique Binary Search Tree

### 4. DP on Strings

斜着遍历： 按着 i + j 或者 j - i 相同的对角线方向遍历 dp。

#### 方法1： 按“对角线编号” (d = i + j) 遍历

假设 n × n 的矩阵， 从左上角往右下角的「斜线方向」：

```java
for (int d = 0; d <= 2 * (n - 1); d++) {
    for (int i = 0; i <= d; i++) {
        int j = d - i;
        if (i < n && j < n) {
            // 访问 dp[i][j]
        }
    }
}
```
| 对角线编号 `d` | 遍历的格子 (i, j)               |
| --------- | -------------------------- |
| 0         | (0,0)                      |
| 1         | (0,1), (1,0)               |
| 2         | (0,2), (1,1), (2,0)        |
| 3         | (0,3), (1,2), (2,1), (3,0) |
| 4         | (1,3), (2,2), (3,1)        |
| 5         | (2,3), (3,2)               |
| 6         | (3,3)                      |


#### 方法2： 按“子串长度”遍历（常用于区间 DP）

它遵循一个特定的模板：先解决所有长度为 2 的子串，然后是长度为 3、4，直到 N 这种方法在处理回文（Palindromes）或最优切分（Optimal Partitioning）问题时非常强大。

例如在回文子串或矩阵链乘法中：dp[i][j] 依赖于 dp[i+1][j-1]，即左下角的状态。

所以要从 短区间到长区间 遍历：

```java

    // 初始化：长度为 1 的单个字符（基本情况）通常默认为 true
    for (int i = 0; i < n; i++) dp[i][i] = true; 

    // 按长度 L 进行迭代
    for (int L = 2; L <= n; L++) { 
        for (int i = 0; i <= n - L; i++) {
            int j = i + L - 1; // 结束索引
            
            // 核心逻辑：从较小的区间 [i+1, j-1] 转移到 [i, j]
            if (s.charAt(i) == s.charAt(j)) {
                // 如果长度为 2，只要首尾相等就是回文；否则取决于内部子串
                dp[i][j] = (L == 2) ? true : dp[i + 1][j - 1];
            }
        }
    }

```

A. 回文相关（“三大核心题”）
|题目       | 目标DP        |状态/逻辑      |
| --------- | ------------|-------------- |
|LC 5: 最长回文子串  |  找到最长的子串。 | dp[i][j] 为布尔值。每当 dp[i][j] 为 true 时更新 maxLen。|

|LC 647: 回文子串数量 | 计算回文子串的总数。|每当 dp[i][j] 为 true 时，计数器 count++。|

|LC 516: 最长回文子序列 | 最长子序列（不要求连续）|
 若 s[i]==s[j]：dp[i][j] = dp[i+1][j-1] + 2。否则：max(dp[i+1][j], dp[i][j-1])。|


B. 结构修改/区间划分
题目      目标DP            状态/逻辑
LC 1312: 让字符串成为回文的最少插入次数最少添加次数。结果 = n - 最长回文子序列长度。

LC 1039: 多边形三角剖分的最低得分最优划分（区间 DP）。dp[i][j] = min(dp[i][k] + dp[k][j] + cost(i,k,j))，其中 \(k\) 在 \((i,j)\) 之间。

面试中的权衡 (Trade-offs)
  - 复杂度：几乎所有基于 L 的字符串 DP 都是 O(N^2) 时间 和 O(N^2) 空间。
  - “空间优化”陷阱：虽然基于 L 的 DP 逻辑清晰，但将其压缩到 O(N) 空间比使用“逆向 i 正向 j”的方法要难。如果面试官要求 O(N) 空间，你可能需要切换模式。
  - 与双指针的对比：对于“最长回文子串”，中心扩散法（双指针）是 O(N^2) 时间但 O(1)空间。在系统设计或高性能要求的面试中，由于 O(1) 空间表现，双指针法通常优于 DP


#### 方法3： 反向 i 遍历

```java

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (s[i] == s[j])
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                else
                    dp[i][j] = max(dp[i + 1][j], dp[i][j - 1]);
            }
        }
 ```
               

#### 解法分类：

涉及两个字符串/数组的⼦列，⽐如前文讲的「最长公共子序列」。本思路中 dp 数组含义又分为「只涉及一个字符串」和「涉及两个字符串」两种情况

```java
    int n = arr.length;
    int[][] dp = new dp[n][n];
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (arr[i] == arr[j])
                dp[i][j] = dp[i][j] + ...
            else
                dp[i][j] = 最值(...)

        } 
    }
```

1. 涉及两个字符串/数组时(⽐如最长公共⼦序列)，dp 数组的含义如下:
在⼦数组arr1[0..i]和⼦子数组 arr2[0..j] 中，我们要求的⼦序列(最长公共子序列)⻓度为 dp[i][j]
这种情况可以参考这两篇旧文:「编辑距离」「公共⼦序列」

2. 只涉及⼀个字符串/数组时(⽐如本⽂要讲的最⻓回⽂⼦序列)，dp 数组的含义如下: 

    在子数组 array[i..j] 中，我们要求的子序列(最⻓回⽂⼦序列)的⻓度为 dp[i][j] 。 

ex. 

```c++
        int longestPalindromeSubseq(string s) {
            int n = s.size();
            // dp 数组全部初始化为 0
            vector<vector<int>> dp(n, vector<int>(n, 0)); // base case
                for (int i = 0; i < n; i++)
                    dp[i][i] = 1;
            // 反着遍历保证正确的状态转移
            for (int i = n - 1; i >= 0; i--) {
                for (int j = i + 1; j < n; j++) { // 状态转移⽅方程
                        if (s[i] == s[j])
                            dp[i][j] = dp[i + 1][j - 1] + 2;
                else
                } 
            }
            // 整个 s 的最⻓长回⽂文⼦子串串⻓长度
            return dp[0][n - 1];
        }

```


Leetcode 1143. Longest Common Subseequence


### 5. Decision Making


### 6. Binary tree construction
95. Unique Binary Search Trees II - Medium
Given an integer n, generate all structurally unique BST's (binary search trees) that store 
values 1 ... n.

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

```java
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
  ```

## 2. Examples (house robber lc 198)

Step 1. Figure out recursive relation.

A robber has 2 options: a) rob current house i; b) don't rob current house.

- If an option "a" is selected it means she can't rob previous i-1 house but can safely proceed to the one before previous i-2 and gets all cumulative loot that follows.
- If an option "b" is selected the robber gets all the possible loot from robbery of i-1 and all the following buildings.

Recursive formula: 

robbery of current house + loot from houses before the previous loot from the previous house robbery and any loot captured before that

rob(i) = Math.max( rob(i - 2) + currentHouseValue, rob(i - 1) )






Step 2. Recursive (top-down)
Converting the recurrent relation from Step 1 shound't be very hard.

```java
public int rob(int[] nums) {
    return rob(nums, nums.length - 1);
}

private int rob(int[] nums, int i) {
    if (i < 0) {
        return 0;
    }

    return Math.max(rob(nums, i - 2) + nums[i], rob(nums, i - 1));
}
```

This algorithm will process the same i multiple times and it needs improvement. 
Time complexity: Recursion --> O(n^2) ?





Step 3. Recursive + memo (top-down).

```java
int[] memo;
public int rob(int[] nums) {
    memo = new int[nums.length + 1];
    Arrays.fill(memo, -1);
    return rob(nums, nums.length - 1);
}

private int rob(int[] nums, int i) {
    if (i < 0) {
        return 0;
    }
    if (memo[i] >= 0) {
        return memo[i];
    }
    int result = Math.max(rob(nums, i - 2) + nums[i], rob(nums, i - 1));
    memo[i] = result;
    return result;
}
```

O(n) time. 
Space complexity is O(n) because of the recursion stack






Step 4. Iterative + memo (bottom-up)

```java
public int rob(int[] nums) {
    if (nums.length == 0) return 0;
    int[] memo = new int[nums.length + 1];
    memo[0] = 0;
    memo[1] = nums[0];
    for (int i = 1; i < nums.length; i++) {
        int val = nums[i];
        memo[i+1] = Math.max(memo[i], memo[i-1] + val);
    }
    return memo[nums.length];
}
```


Step 5. Iterative + 2 variables (bottom-up)


We can notice that in the previous step we use only memo[i] and memo[i-1], so going just 2 steps back. We can hold them in 2 variables instead. This optimization is met in Fibonacci sequence creation and some other problems [to paste links].

```java
/* the order is: prev2, prev1, num  */
public int rob(int[] nums) {
    if (nums.length == 0) return 0;
    int prev1 = 0;
    int prev2 = 0;
    for (int num : nums) {
        int tmp = prev1;
        prev1 = Math.max(prev2 + num, prev1);
        prev2 = tmp;
    }
    return prev1;
}
```