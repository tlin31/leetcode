# Dynamic Programming 

## When to use DP
1. when recursion goes beyond O(n^2)
2. Counting ways using n different resources
3. optimization 

## Impementations
    
    1. Find recursive relation
    2. Recursive (top-down)
    3. Recursive + memo (top-down)
    4. Iterative + memo (bottom-up)
    5. Iterative + N variables (bottom-up)

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

Leetcode 1143. Longest Common Subseequence


### 5. Decision Making





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

```
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

```
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

```
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

```
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