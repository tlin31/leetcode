78. Subsets - Medium

Given a set of distinct integers, nums, return all possible subsets (the power set).

Note: The solution set must not contain duplicate subsets.

Example:

Input: nums = [1,2,3]
Output:
[
  [3],
  [1],
  [2],
  [1,2,3],
  [1,3],
  [2,3],
  [1,2],
  []
]

******************************************************
key:
    - back tracking & bit
    - edge case:
        1) empty array, return empty
        2)

******************************************************



=======================================================================================================
method 1:

method:

    - back tracking
    - 

stats:

    - Memory Usage: 37.2 MB, less than 99.18%     
    - Runtime: 1 ms, faster than 43.56% of Java online submissions for Subsets.



    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(list, new ArrayList<>(), nums, 0);
        return list;
    }

    private void backtrack(List<List<Integer>> list , List<Integer> tempList, int [] nums, int start){

        //initialize a array list with temp list
        list.add(new ArrayList<>(tempList));
        for(int i = start; i < nums.length; i++){
            tempList.add(nums[i]);
            backtrack(list, tempList, nums, i + 1);
            tempList.remove(tempList.size() - 1);
        }
}




=======================================================================================================
method 2:

method:

    - iterative
    - 和 77 题解法三一个思想，想找出数组长度 1 的所有解，然后再在长度为 1 的所有解上加 1 个数字变成长度为 2 的所有解，
      同样的直到 n
    - 

stats:

    - Runtime: 1 ms, faster than 43.56% of Java online submissions for Subsets.
    - Memory Usage: 37.3 MB, less than 99.18%

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        ans.add(new ArrayList<Integer>());
        res.add(new ArrayList<Integer>());
        int n = nums.length;

        // 第一层循环，子数组长度从 1 到 n
        for (int i = 1; i <= n; i++) {

            // 第二层循环，遍历上次的所有结果
            List<List<Integer>> tmp = new ArrayList<List<Integer>>();
            for (List<Integer> list : res) {

                // 第三次循环，对每个结果进行扩展
                for (int m = 0; m < n; m++) {
                    //只添加比末尾数字大的数字，防止重复
                    if (list.size() > 0 && list.get(list.size() - 1) >= nums[m])
                        continue;
                    List<Integer> newList = new ArrayList<Integer>(list);
                    newList.add(nums[m]);
                    tmp.add(newList);
                    ans.add(newList);
                }
            }
            res = tmp;
        }
        return ans;
    }

=======================================================================================================
method 3:

method:

    - iterative
    - 解法一的迭代法，是直接从结果上进行分类，将子数组的长度分为长度是 1 的，2 的 .... n 的。我们还可以从条件上入手，
      先只考虑给定数组的 1 个元素的所有子数组，然后再考虑数组的 2 个元素的所有子数组 ... 最后再考虑数组的 n 个元素
      的所有子数组。求 k 个元素的所有子数组，只需要在 k - 1 个元素的所有子数组里边加上 nums [ k ] 即可。

stats:

    - 
    - Runtime: 0 ms, faster than 100.00% of Java online submissions for Subsets.
    - Memory Usage: 37.2 MB, less than 99.18% of Java online submissions for Subsets.


    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        ans.add(new ArrayList<>());//初始化空数组

        for(int i = 0;i<nums.length;i++){
            List<List<Integer>> ans_tmp = new ArrayList<>();
            
            //遍历之前的所有结果
            for(List<Integer> list : ans){
                List<Integer> tmp = new ArrayList<>(list);
                tmp.add(nums[i]); //加入新增数字
                ans_tmp.add(tmp);
            }
            ans.addAll(ans_tmp);
        }
        return ans;
    }

=======================================================================================================
method 4:

method:

    - !!! bit
    - 数组的每个元素，可以有两个状态，在子数组中和不在子数组中，所有状态的组合就是所有子数组了。
    - 例如，nums = [ 1, 2 , 3 ]。1 代表在，0 代表不在。

stats:

    - possible solution: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
    - Runtime: 1 ms, faster than 43.56% of Java online submissions for Subsets.
    - Memory Usage: 37.3 MB, less than 99.18%

public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> ans = new ArrayList<>();
    int bit_nums = nums.length;

    //# of power sets = 2^n
    int ans_nums = 1 << bit_nums; 

    // need to fill all 8 slots of power set
    for (int i = 0; i < ans_nums; i++) {
        List<Integer> tmp = new ArrayList<>();
        int count = 0; //记录当前对应数组的哪一位
        int i_copy = i; //用来移位

        //add
        while (i_copy != 0) { 
            if ((i_copy & 1) == 1) { //判断当前位是否是 1
                tmp.add(nums[count]);
            }
            count++;
            i_copy = i_copy >> 1;//右移一位
        }
        
        //add empty list at first
        ans.add(tmp);

    }
    return ans;
}







