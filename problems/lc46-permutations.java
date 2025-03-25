46. Permutations - Medium

Given a collection of distinct integers, return all possible permutations.

Example:

Input: [1,2,3]
Output:
[
  [1,2,3],
  [1,3,2],
  [2,1,3],
  [2,3,1],
  [3,1,2],
  [3,2,1]
]


******************************************************
key:
	- back track
	- main condition --> when there are 3 elems in the sub-array, 
	- edge case:
		1) empty string, return empty
		2)

******************************************************

class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        backtrack(new ArrayList<>(), ans, nums);
        return ans;
    }

    public void backtrack(
        List<Integer> curr,
        List<List<Integer>> ans,
        int[] nums
    ) {
        if (curr.size() == nums.length) {
            ans.add(new ArrayList<>(curr));
            return;
        }

        for (int num : nums) {
            if (!curr.contains(num)) {
                curr.add(num);
                backtrack(curr, ans, nums);
                curr.remove(curr.size() - 1);
            }
        }
    }
}




=======================================================================================================
method 1:

method:

	- back tracking 

stats:

	- Runtime: 1 ms, faster than 98.08% of Java online submissions for Permutations.
	- Memory Usage: 38.3 MB, less than 70.92%

	public List<List<Integer>> permute(int[] nums) {
	   List<List<Integer>> list = new ArrayList<>();
	   // Arrays.sort(nums); // not necessary
	   backtrack(list, new ArrayList<>(), nums);
	   return list;
	}

	private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums){
	   // condition: when get same size 
	   if(tempList.size() == nums.length){
	      list.add(new ArrayList<>(tempList));
	   } else{
	      for(int i = 0; i < nums.length; i++){ 
	      	 
	      	 // !!!! element already exists, skip
	         if(tempList.contains(nums[i])) 
	         	continue; 

	         tempList.add(nums[i]);
	         backtrack(list, tempList, nums);
	         tempList.remove(tempList.size() - 1);
	      }
	   }
	} 

ex. Input: [1,2,3]

i = 0 --> templist = [1] --> backtrack(list, [1], nums)
	backtrack(list, [1], nums) --> i = 0; continue;
							   --> i = 1; templist = [1,2]; --> backtrack(list, [1,2], nums)
		backtrack(list, [1,2], nums) --> i = 0; continue;
									 --> i = 1; continue;
									 --> i = 2; --> backtrack(list, [1,2,3], nums)
			backtrack(list, [1,2,3], nums) --> templist.size == nums.length, list.add([1,2,3])
									 --> templist.remove(3)
								--> i = 2; templist.add(3) --> backtrack(list, [1,3], nums)


=======================================================================================================
method 2:

method:

	- 在数字的缝隙插入新的数字
	- 如果只有 1 个数字 [ 1 ]，那么很简单，直接返回 [ [ 1 ] ] 就 OK 了。
	- 如果加了 1 个数字 2， [ 1 2 ] 该怎么办呢？我们只需要在上边的情况里，在 1 的空隙，也就是左边右边插入 2 就够了。
	  变成 [ [ 2 1 ], [ 1 2 ] ]。
	- 如果再加 1 个数字 3，[ 1 2 3 ] 我们只需要在上边的所有情况里的空隙里插入数字 3 就行啦。1 2，1 3 2，1 2 3，所以最后的结果就是 [ [ 3 2 1]，[ 2 3 1]，[ 2 1 3 ], [ 3 1 2 ]，[ 1 3 2 ]，[ 1 2 3 ] ]。


stats:
	- Runtime: 1 ms, faster than 98.08% 
	- Memory Usage: 37.5 MB, less than 95.74%

	public List<List<Integer>> permute(int[] nums) {
	    return permute_end(nums,nums.length-1);
	}

	// end 表示当前新增的数字的位置
	private List<List<Integer>> permute_end(int[] nums, int end) {
	    // 只有一个数字的时候
	    if(end == 0){
	        List<List<Integer>> all = new ArrayList<>();
	        List<Integer> temp = new ArrayList<>();
	        temp.add(nums[0]);
	        all.add(temp);
	        return all;
	    }
	    //得到上次所有的结果
	    List<List<Integer>> all_end = permute_end(nums,end-1);
	    int current_size = all_end.size();

	    //遍历每一种情况
	    for (int j = 0; j < current_size; j++) { 

	        //在数字的缝隙插入新的数字
	        for (int k = 0; k <= end; k++) {
	            List<Integer> temp = new ArrayList<>(all_end.get(j));
	            temp.add(k, nums[end]);

	            //添加到结果中
	            all_end.add(temp);
	        };

	    }
	    //由于 all_end 此时既保存了之前的结果，和添加完的结果，所以把之前的结果要删除
	    for (int j = 0; j < current_size; j++) {
	        all_end.remove(0);
	    }
	    return all_end;
	}



=======================================================================================================
method 3:

method:

	- iterative
	- 

stats:

	- 如果从最后的结果来说，应该是 n! 个结果，所以时间复杂度应该是 O（n！)。
	- 空间复杂度：O（1)

	public List<List<Integer>> permute(int[] nums) {
	    List<List<Integer>> all = new ArrayList<>();
	    all.add(new ArrayList<>());
	    //在上边的基础上只加上最外层的 for 循环就够了，代表每次新添加的数字
	    for (int i = 0; i < nums.length; i++) {
	        int current_size = all.size();
	        for (int j = 0; j < current_size; j++) {
	            for (int k = 0; k <= i; k++) {
	                List<Integer> temp = new ArrayList<>(all.get(j));
	                temp.add(k, nums[i]);
	                all.add(temp);
	            }
	        }
	        for (int j = 0; j < current_size; j++) {
	            all.remove(0);
	        }
	    }
	    return all;
	}

=======================================================================================================
method 4:

method:

	- recursion
	- 这个想法就很 cool 了，之前第一个解法的递归，有点儿动态规划的意思，把 1 个数字的解，2 个数字的解，
	  3 个数字的解，一环套一环的求了出来。
	- 假设有一个函数，可以实现题目的要求，即产生 nums 的所有的组合，并且加入到 all 数组中。不过它多了一个参数，
	  begin，即只指定从 nums [ begin ] 开始的数字，前边的数字固定不变。

stats:

	- 如果从最后的结果来说，应该是 n! 个结果，所以时间复杂度应该是 O（n！)。
	- 空间复

public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> all = new ArrayList<>();
    //从下标 0 开始的所有组合
    upset(nums, 0, all);
    return all;
}

private void upset(int[] nums, int begin, List<List<Integer>> all) {
    if (begin == nums.length) {
        ArrayList<Integer> temp = new ArrayList<Integer>(); 
        for (int i = 0; i < nums.length; i++) {
            temp.add(nums[i]);
        }
        all.add(new ArrayList<Integer>(temp));
        return;
    }
    for (int i = begin; i < nums.length; i++) {
        swap(nums, i, begin);
        upset(nums, begin + 1, all);
        swap(nums, i, begin);
    }

}

private void swap(int[] nums, int i, int begin) {
    int temp = nums[i];
    nums[i] = nums[begin];
    nums[begin] = temp;
}

