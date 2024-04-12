47. Permutations II - Medium

Given a collection of numbers that might contain duplicates, return all possible unique permutations.

Example:

Input: [1,1,2]
Output:
[
  [1,1,2],
  [1,2,1],
  [2,1,1]
]


******************************************************
key:
	- need to differentiate same elements,
	- method 3????? 
	- edge case:
		1)

******************************************************



=======================================================================================================
method 1:

method:

	- backtrack
	- use a boolean array to store whether has use an element

stats:
	- Runtime: 1 ms, faster than 100.00% of Java online submissions for Permutations II.
	- Memory Usage: 39.1 MB, less than 76.12%

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

	        	//if this number is already used or if this number is a duplicate && prev num is used
	            if(used[i] || i > 0 && nums[i] == nums[i-1] && !used[i - 1]) 
	            	continue;

	            used[i] = true; 
	            tempList.add(nums[i]);
	            backtrack(list, tempList, nums, used);

	            //reset
	            used[i] = false; 
	            tempList.remove(tempList.size() - 1);
	        }
	    }
	}




=======================================================================================================
method 2:

method:

	- iterative, then delete duplicates 
	- !! use hashmap to delete duplicates

stats:

	- 
	- Runtime: 42 ms, faster than 13.27% of Java online submissions for Permutations II.
	- Memory Usage: 43 MB, less than 7.46% 

	public List<List<Integer>> permuteUnique(int[] nums) {

		//result
	    List<List<Integer>> all = new ArrayList<>(); 
	    List<Integer> temp = new ArrayList<>();
	    temp.add(nums[0]);
	    all.add(temp); 

	    //go through all elements in nums
	    for (int i = 1; i < nums.length; i++) {
	        int current_size = all.size();

	        for (int j = 0; j < current_size; j++) {
	            List<Integer> last = all.get(j);

	            for (int k = 0; k <= i; k++) {
	                if (k < i && nums[i] == last.get(k)) {
	                    continue;
	                }
	                temp = new ArrayList<>(last);
	                temp.add(k, nums[i]);
	                all.add(temp);
	            }
	        }
	        for (int j = 0; j < current_size; j++) {
	            all.remove(0);
	        }
	    }
	    return removeDuplicate(all);
	}

	private List<List<Integer>> removeDuplicate(List<List<Integer>> list) {
	    Map<String, String> ans = new HashMap<String, String>();
	    for (int i = 0; i < list.size(); i++) {
	        List<Integer> l = list.get(i);
	        String key = "";
	        // [ 2 3 4 ] 转为 "2,3,4"
	        for (int j = 0; j < l.size() - 1; j++) {
	            key = key + l.get(j) + ",";
	        }
	        key = key + l.get(l.size() - 1);

	        //just add the key, maybe can also use set?
	        ans.put(key, "");
	    }
	    // 根据逗号还原 List
	    List<List<Integer>> ans_list = new ArrayList<List<Integer>>();
	    for (String k : ans.keySet()) {
	        String[] l = k.split(",");
	        List<Integer> temp = new ArrayList<Integer>();
	        for (int i = 0; i < l.length; i++) {
	            int c = Integer.parseInt(l[i]);
	            temp.add(c);
	        }
	        ans_list.add(temp);
	    }
	    return ans_list;
	}



=======================================================================================================
method 3:

method:

	- 这个改起来相对容易些，之前的想法就是在每一个位置，让每个数字轮流交换过去一下。这里的话，我们其实只要把当前位置
	  已经有哪些数字来过保存起来，如果有重复的话，我们不让他交换，直接换下一个数字就可以了。
	- 

stats:
	- Runtime: 3 ms, faster than 33.15% of Java online submissions for Permutations II.
	- Memory Usage: 39.4 MB, less than 65.67% 


public List<List<Integer>> permuteUnique(int[] nums) {
    List<List<Integer>> all = new ArrayList<>();
    Arrays.sort(nums);
    backtrack(nums, 0, all);
    return all;
}

private void backtrack(int[] nums, int begin, List<List<Integer>> all) {
    if (begin == nums.length) {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (int i = 0; i < nums.length; i++) {
            temp.add(nums[i]);
        }
        all.add(new ArrayList<Integer>(temp));
        return;
    }
    
    HashSet<Integer> set = new HashSet<>(); //保存当前要交换的位置已经有过哪些数字了
    for (int i = begin; i < nums.length; i++) {
        if (set.contains(nums[i])) { //如果存在了就跳过，不去交换
            continue;
        }
        set.add(nums[i]);
        swap(nums, i, begin);
        backtrack(nums, begin + 1, all);
        swap(nums, i, begin);
    }

}

private void swap(int[] nums, int i, int begin) {
    int temp = nums[i];
    nums[i] = nums[begin];
    nums[begin] = temp;
}



