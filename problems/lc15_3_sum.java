15. 3Sum	--- Medium

Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

Note:

The solution set must not contain duplicate triplets.

Example:

Given array nums = [-1, 0, 1, 2, -1, -4],

A solution set is:
[
  [-1, 0, 1],
  [-1, -1, 2]
]

=========================================================================================================================================================
method 1:

key:
	- The idea is to sort an input array and then run thru all indices of a possible 1st element 
      of a triplet. 
	- For each possible first element we make a standard bidirectional 2Sum sweep of the remaining 
      part of the array. 
	- !!! we want to skip equal elements to avoid duplicates in the answer without making a set or 
    smth like that.

public List < List < Integer >> threeSum(int[] num) {
    
    Arrays.sort(num);
    List < List < Integer >> res = new LinkedList < > ();

    // i is the first element, lo is second element, hi is the third
    for (int i = 0; i < num.length - 2; i++) {

    	// corner case!!! if number = 0!!
        if (i == 0 || (i > 0 && num[i] != num[i - 1])) {
            int lo = i + 1, 
            	hi = num.length - 1, 
            	sum = 0 - num[i];

            while (lo < hi) {
                if (num[lo] + num[hi] == sum) {
                    res.add(Arrays.asList(num[i], num[lo], num[hi]));

                    //skip duplicates
                    while (lo < hi && num[lo] == num[lo + 1]) lo++;
                    while (lo < hi && num[hi] == num[hi - 1]) hi--;
                    
                    // updates, continue to find next pairs
                    lo++;
                    hi--;
                } else if (num[lo] + num[hi] < sum) 
                    lo++;
                else 
                    hi--;
            }
        }
    }
    return res;
}



=========================================================================================================================================================


如果不让sort整个array：
步骤 (Steps)：
1. 外层循环：固定第一个数 a = nums[i]。
2. 内层逻辑：将问题转化为 两数之和 (Two Sum)。对于当前的 a，我们需要在剩余的元素中找到 b + c = -a。
3. 哈希表去重：
    使用一个 Set<Integer> 来查找是否存在匹配的 c。
    使用另一个 Set<List<Integer>> 来存储已发现的三元组。为了确保 [1, -1, 0] 和 [0, 1, -1] 被视为同一个组合，
    存入前需要对这三个数进行局部排序 (Local Sort)。





public List<List<Integer>> threeSum(int[] nums) {
    Set<List<Integer>> res = new HashSet<>(); // 用于结果去重 (Result de-duplication)
    Set<Integer> dups = new HashSet<>();      // 用于固定第一个数去重
    Map<Integer, Integer> seen = new HashMap<>(); // 两数之和的哈希表

    for (int i = 0; i < nums.length; i++) {
        if (!dups.add(nums[i])) continue; // 如果当前 a 处理过，跳过

        Set<Integer> currentSet = new HashSet<>();
        for (int j = i + 1; j < nums.length; j++) {
            int complement = -nums[i] - nums[j];
            if (currentSet.contains(complement)) {
                List<Integer> triplet = Arrays.asList(nums[i], nums[j], complement);
                Collections.sort(triplet); // 局部排序确保去重一致性
                res.add(triplet);
            }
            currentSet.add(nums[j]);
        }
    }
    return new ArrayList<>(res);
}







