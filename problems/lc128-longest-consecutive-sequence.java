128. Longest Consecutive Sequence - Hard--> medium

Given an unsorted array of integers, find the length of the longest consecutive elements sequence.

Your algorithm should run in O(n) complexity.

Example:

Input: [100, 4, 200, 1, 3, 2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.

******************************************************
key:
	- Hashmap 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:


当插入一个数字 num 时：

检查 left = map.get(num - 1)

检查 right = map.get(num + 1)

新序列长度：total = left + 1 + right

更新新的左右边界长度：

map.put(num - left, total)（左边界）

map.put(num + right, total)（右边界）

类似动态合并区间（interval merge）
每个数字最多触发一次操作 → O(n)


class Solution {
    public int longestConsecutive(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int longest = 0;

        for (int num : nums) {
            if (map.containsKey(num)) continue;  // 避免重复计算

            int left = map.getOrDefault(num - 1, 0);
            int right = map.getOrDefault(num + 1, 0);
            int total = left + 1 + right;

            // 记录新的总长度
            map.put(num, total);

            // 更新整个连续序列的边界
            map.put(num - left, total);
            map.put(num + right, total);

            longest = Math.max(longest, total);
        }

        return longest;
    }
}


=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

class Solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> num_set = new HashSet<Integer>();
        for (int num : nums) {
            num_set.add(num);
        }

        int longestStreak = 0;

        for (int num : num_set) {

        	// find the very first one
            if (!num_set.contains(num-1)) {
                int currentNum = num;
                int currentStreak = 1;

                // loop to find all consequtive sequence
                while (num_set.contains(currentNum+1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }
}


classSolution:
    def longestConsecutive(self, nums):
        longest_streak = 0
        num_set = set(nums)

        for num in num_set:
            if num - 1 not in num_set:
                current_num = num
                current_streak = 1

                while current_num + 1 in num_set:
                    current_num += 1
                    current_streak += 1

                longest_streak = max(longest_streak, current_streak)

        return longest_streak

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



