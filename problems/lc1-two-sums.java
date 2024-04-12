1. Two Sum
Easy

Given an array of integers, return indices of the two numbers such that they add up to a specific target.

You may assume that each input would have exactly one solution, and you may not use the same element twice.

Example:

Given nums = [2, 7, 11, 15], target = 9,

Because nums[0] + nums[1] = 2 + 7 = 9, return [0, 1].

******************************************************
key:
    - HashMap
    - edge case:
        1) empty string, return empty
        2)

******************************************************

=======================================================================================================
Method 1:


Stats:

    - 
    - 


Method:

    -   
    -   这样只需判断 sub 在不在 hash 的 key 里就可以了，而此时的时间复杂度仅为 O（1）！

需要注意的地方是，还需判断找到的元素不是当前元素，因为题目里讲一个元素只能用一次。

public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<nums.length;i++){
            map.put(nums[i],i);
        }
        for(int i=0;i<nums.length;i++){
            int sub=target-nums[i];
            if(map.containsKey(sub)&&map.get(sub)!=i){
                return new int[]{i,map.get(sub)};
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

时间复杂度：比解法一少了一个 for 循环，降为 O（n）

空间复杂度：所谓的空间换时间，这里就能体现出来， 开辟了一个 hash table ，空间复杂度变为 O（n）

=======================================================================================================
Method 2:


Stats:

    - 
    - 


Method:

    -   
    -   看解法二中，两个 for 循环，他们长的一样，我们当然可以把它合起来。复杂度上不会带来什么变化，变化仅仅是不需要判断是
        不是当前元素了，因为当前元素还没有添加进 hash 里。

public int[] twoSum3(int[] nums, int target) {
        Map<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<nums.length;i++){
            int sub = target-nums[i];
            if(map.containsKey(sub)){
                return new int[]{i,map.get(sub)};
        }
        map.put(nums[i], i);
    }
    throw new IllegalArgumentException("No two sum solution");
}