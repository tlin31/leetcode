# backtracking questions in Java (Subsets, Permutations, Combination Sum, Palindrome Partitioning)


https://leetcode.com/problems/combination-sum/discuss/16502/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)

## Problem list
- list all combination
22    51    93    89
17
10
46
39
37
78
79

## Template:
- 用memorization to reduce runtime

- 很多时候，会有一个start variable，然后进入递归时start+1，那么for loop就是
  
    for (int i = start; i ....)

  

```java
    递归{
        递归出口；
        update result; (add sth.)

        for(int i = 1;i<=n;i++){
            add(i);
            进入递归；
            remove(i);
        }
    }
```


## Recover techniques:

### ArrayList:

```java
        //first add the current one
        tempList.add(nums[i]);

        backtracking.......
        
        //reset
        tempList.remove(tempList.size() - 1);


```

### String Builder:

```java
    StringBuilder sb = new StringBuilder();
    int len = sb.length();  
    BACKTRACKING.......

    // recover 
    sb.setLength(len); 

```

### add array

因为 board 是一个共享的可变对象，回溯还会继续修改它。如果直接 add(board)，最终 result 中存放的全是同一个 board 的引用，后续回溯会把它改坏。

result.add(new ArrayList<>(board));
这是 深复制（至少一层深复制）：每次找到一个解就复制当前 board 内容，保证后续回溯不会影响已经保存的结果。

Note: 如果 board 是不可变结构还需要深拷贝吗？不需要，例如 Strings 本身不可变。

ex. N queens
```java
        if (row == n) {
            result.add(new ArrayList<String>(board));
        }
```
原本的全部是：
```java

private void backtack(List<List<String>> result, List<String> board, int row, 
        boolean[] cols, boolean[] d1, boolean[] d2, int n){

        if (row == n) {
            result.add(new ArrayList<String>(board));
        }
        
        for (int col=0; col<n; col++){
            int id1 = col - row + n;
            int id2 = col + row;

            // if valid to place it here
            if (!cols[col] && !d1[id1] && !d2[id2]){
                char[] r = new char[n];
                Arrays.fill(r, '.');
                r[col] = 'Q';
                board.add(new String(r));
                //take up the space accordingly
                cols[col] = true;
                d1[id1] = true;
                d2[id2] = true;

                //back track --> condition is row+1 --> move to next row
                backtack(result, board, row+1, cols, d1, d2, n);

                //reset
                board.remove(board.size()-1);
                cols[col] = false;
                d1[id1] = false;
                d2[id2] = false;
            }
        }


```



## pattern
```java

public List<List<Integer>> subsets(int[] nums) {
	  //create answer/return list
    List<List<Integer>> list = new ArrayList<>();

    //sort if needed (combination sum don't need it)
    Arrays.sort(nums);

    //call backtrack
    backtrack(list, new ArrayList<>(), nums, 0);
    return list;
}

private void backtrack(List<List<Integer>> list , List<Integer> tempList, int [] nums, int start){
    
    // condition about when to add 
    list.add(new ArrayList<>(tempList));

    // could add return statements here to return something
    // in order to exit backtrack

    for(int i = start; i < nums.length; i++){
        // !!!! if we want no duplicates, then here check whether element already exists
        if(tempList.contains(nums[i])) 
            continue; 

    	  //first add the current one
        tempList.add(nums[i]);

        //see with the current one added to the list, whether the rest/others will still work
        backtrack(list, tempList, nums, i + 1);

        //reset
        tempList.remove(tempList.size() - 1);
    }
}
```
some of the conditions:

  1. if subset/power set:         tempList.add(s.substring(start, i + 1));


## Examples

### Subsets : 
https://leetcode.com/problems/subsets/

```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> list = new ArrayList<>();
    Arrays.sort(nums);
    backtrack(list, new ArrayList<>(), nums, 0);
    return list;
}

private void backtrack(List<List<Integer>> list , List<Integer> tempList, int [] nums, int start){
    list.add(new ArrayList<>(tempList));
    for(int i = start; i < nums.length; i++){
        tempList.add(nums[i]);
        backtrack(list, tempList, nums, i + 1);
        tempList.remove(tempList.size() - 1);
    }
}

```

### Subsets II (contains duplicates) : 
https://leetcode.com/problems/subsets-ii/

```java

public List<List<Integer>> subsetsWithDup(int[] nums) {
    List<List<Integer>> list = new ArrayList<>();
    Arrays.sort(nums);
    backtrack(list, new ArrayList<>(), nums, 0);
    return list;
}

private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums, int start){
    list.add(new ArrayList<>(tempList));
    for(int i = start; i < nums.length; i++){
        // skip duplicates
        if(i > start && nums[i] == nums[i-1]) 
          continue; 
        tempList.add(nums[i]);

        backtrack(list, tempList, nums, i + 1);
        tempList.remove(tempList.size() - 1);
    }
} 
```

### Permutations : 
https://leetcode.com/problems/permutations/
```java

  public List<List<Integer>> permute(int[] nums) {
     List<List<Integer>> list = new ArrayList<>();
     // not necessary
     // Arrays.sort(nums); 
     backtrack(list, new ArrayList<>(), nums);
     return list;
  }

  private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums){
     if(tempList.size() == nums.length){
        list.add(new ArrayList<>(tempList));
     } else{
        for(int i = 0; i < nums.length; i++){ 
           if(tempList.contains(nums[i])) continue; // element already exists, skip
           tempList.add(nums[i]);
           backtrack(list, tempList, nums);
           tempList.remove(tempList.size() - 1);
        }
     }
  } 
```

### Permutations II (contains duplicates) : 
https://leetcode.com/problems/permutations-ii/
```java
public List<List<Integer>> permuteUnique(int[] nums) {
    List<List<Integer>> list = new ArrayList<>();
    Arrays.sort(nums);
    backtrack(list, new ArrayList<>(), nums, new boolean[nums.length]);
    return list;
}

private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums, boolean [] used){
    if(tempList.size() == nums.length){
        list.add(new ArrayList<>(tempList));
    } else{
        for(int i = 0; i < nums.length; i++){
            if(used[i] || i > 0 && nums[i] == nums[i-1] && !used[i - 1]) continue;
            used[i] = true; 
            tempList.add(nums[i]);
            backtrack(list, tempList, nums, used);
            used[i] = false; 
            tempList.remove(tempList.size() - 1);
        }
    }
}
```

### Combination Sum : 
https://leetcode.com/problems/combination-sum/
```java
  public List<List<Integer>> combinationSum(int[] nums, int target) {
      List<List<Integer>> list = new ArrayList<>();
      Arrays.sort(nums);
      backtrack(list, new ArrayList<>(), nums, target, 0);
      return list;
  }

  private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums, int remain, int start){
      if(remain < 0) 
        return;
      else if(remain == 0) 
        list.add(new ArrayList<>(tempList));
      else{ 
          for(int i = start; i < nums.length; i++){
              tempList.add(nums[i]);

              // not i + 1 because we can reuse same elements
              backtrack(list, tempList, nums, remain - nums[i], i); 
              tempList.remove(tempList.size() - 1);
          }
      }
  }
```

### Combination Sum II (can't reuse same element) : 
https://leetcode.com/problems/combination-sum-ii/
```java

  public List<List<Integer>> combinationSum2(int[] nums, int target) {
      List<List<Integer>> list = new ArrayList<>();
      Arrays.sort(nums);
      backtrack(list, new ArrayList<>(), nums, target, 0);
      return list;
      
  }

  private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums, int remain, int start){
      if(remain < 0) 
        return;
      else if(remain == 0) 
        list.add(new ArrayList<>(tempList));
      else{
          for(int i = start; i < nums.length; i++){
              // skip duplicates
              if(i > start && nums[i] == nums[i-1]) 
                continue; 
              tempList.add(nums[i]);
              backtrack(list, tempList, nums, remain - nums[i], i + 1);
              tempList.remove(tempList.size() - 1); 
          }
      }
  } 
```

### Combination Sum III
```java
public List<List<Integer>> combinationSum3(int k, int n) {
    List<List<Integer>> list = new ArrayList<>();
    backtrack(list, new ArrayList<Integer>(), k, n, 1);
    return list;
}

private void backtrack(List<List<Integer>> list, List<Integer> tempList, int k, int remain, int start) {
    if(tempList.size() > k) return; /** no solution */
    else if(tempList.size() == k && remain == 0) list.add(new ArrayList<>(tempList));
    else{
        for (int i = start; i <= 9; i++) {
            tempList.add(i);
            backtrack(list, tempList, k, remain-i, i+1);
            tempList.remove(tempList.size() - 1);
        }
    }
}

```



### Palindrome Partitioning : 
https://leetcode.com/problems/palindrome-partitioning/
```java

public List<List<String>> partition(String s) {
   List<List<String>> list = new ArrayList<>();
   backtrack(list, new ArrayList<>(), s, 0);
   return list;
}

public void backtrack(List<List<String>> list, List<String> tempList, String s, int start){
   if(start == s.length())
      list.add(new ArrayList<>(tempList));
   else{
      for(int i = start; i < s.length(); i++){
         if(isPalindrome(s, start, i)){
            tempList.add(s.substring(start, i + 1));
            backtrack(list, tempList, s, i + 1);
            tempList.remove(tempList.size() - 1);
         }
      }
   }
}

public boolean isPalindrome(String s, int low, int high){
   while(low < high)
      if(s.charAt(low++) != s.charAt(high--)) return false;
   return true;
} 
```

### 679. 24 games

```java
  public boolean judgePoint24(int[] nums) {
        boolean[] vis = new boolean[4];
        double[] arr = new double[4];
        for (int i=0; i<4; i++) 
          arr[i] = 1.0 * nums[i];
        return backtrack(arr, vis, 4);
    }
    
    boolean backtrack(double[] arr, boolean[] vis, int available) {
        double prev = 0;

        // exit condition
        if (available == 1) {
            for (int i=0; i<arr.length; i++) 
                if (!vis[i]) 
                  return Math.abs(arr[i]-24) < 0.000001 ? true : false;
        } 

        for (int i=0; i < arr.length; i++) {
            if (vis[i]) 
              continue;

            // prev is for backtracking
            prev = arr[i];

            // check all combinations
            for (int j=i+1; j<arr.length; j++) {
                // calculate i & j
                if (vis[j]) 
                  continue;

                vis[j] = true;
                
                // add
                arr[i] = prev + arr[j];
                if (backtrack(arr, vis, available-1)) return true;
                
                // minus
                arr[i] = prev - arr[j];
                if (backtrack(arr, vis, available-1)) return true;

                arr[i] = -prev + arr[j];
                if (backtrack(arr, vis, available-1)) return true;
                
                // multiply
                arr[i] = prev * arr[j];
                if (backtrack(arr, vis, available-1)) return true;
                
                // division
                arr[i] = prev / arr[j];
                if (backtrack(arr, vis, available-1)) return true;

                arr[i] = arr[j] / prev;
                if (backtrack(arr, vis, available-1)) return true;
                
                vis[j] = false;
            }

            // recover arr[i]
            arr[i] = prev;
        }
        return false;
    }


```


### backtrack & dfs

#### 64. find min path sum in a grid

```java
  public int minPathSum(int[][] grid) {
      int m = grid.length;
      int n = grid[0].length;
      HashMap<String, Integer> visited = new HashMap<>();
      return getAns(0, 0, m - 1, n - 1, 0, visited, grid);
  }

  // x & y is current index, m &n is target coordinate
  private int getAns(int x, int y, int m, int n, int num, HashMap<String, Integer> visited, 
            int[][] grid) {
      // 到了终点，返回终点的权值
      if (x == m && y == n) {
          return grid[m][n];
      }

      int n1 = Integer.MAX_VALUE;
      int n2 = Integer.MAX_VALUE;

      // 往右走
      String key = x + 1 + "@" + y;
      if (!visited.containsKey(key)) {
          if (x + 1 <= m) {
              n1 = getAns(x + 1, y, m, n, num, visited, grid);
          }
      } else {
          n1 = visited.get(key);
      }

      // 往下走
      key = x + "@" + (y + 1);
      if (!visited.containsKey(key)) {
          if (y + 1 <= n) {
              n2 = getAns(x, y + 1, m, n, num, visited, grid);
          }
      } else {
          n2 = visited.get(key);
      }

      // 将当前点加入 visited 中
      key = x + "@" + y;
      visited.put(key, Math.min(n1, n2) + grid[x][y]);

      //返回两个之间较小的，并且加上当前权值
      return Math.min(n1, n2) + grid[x][y];
  }
```

### Minimax
[Minimax](notes/minimax.md)
