3318. Find X-Sum of All K-Long Subarrays I - Easy

You are given an array nums of n integers and two integers k and x.

The x-sum of an array is calculated by the following procedure:

Count the occurrences of all elements in the array.
Keep only the occurrences of the top x most frequent elements. If two elements have the same number of occurrences, the element with the bigger value is considered more frequent.
Calculate the sum of the resulting array.
Note that if an array has less than x distinct elements, its x-sum is the sum of the array.

Return an integer array answer of length n - k + 1 where answer[i] is the x-sum of the subarray nums[i..i + k - 1].

 

Example 1:

Input: nums = [1,1,2,2,3,4,2,3], k = 6, x = 2

Output: [6,10,12]

Explanation:

For subarray [1, 1, 2, 2, 3, 4], only elements 1 and 2 will be kept in the resulting array. Hence, answer[0] = 1 + 1 + 2 + 2.
For subarray [1, 2, 2, 3, 4, 2], only elements 2 and 4 will be kept in the resulting array. Hence, answer[1] = 2 + 2 + 2 + 4. Note that 4 is kept in the array since it is bigger than 3 and 1 which occur the same number of times.
For subarray [2, 2, 3, 4, 2, 3], only elements 2 and 3 are kept in the resulting array. Hence, answer[2] = 2 + 2 + 2 + 3 + 3.
Example 2:

Input: nums = [3,8,7,8,7,5], k = 2, x = 2

Output: [11,15,15,15,12]

Explanation:

Since k == x, answer[i] is equal to the sum of the subarray nums[i..i + k - 1].

 

Constraints:

1 <= n == nums.length <= 50
1 <= nums[i] <= 50
1 <= x <= k <= nums.length


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

	-	



Stats:

窗口数：n - k + 1

每个窗口最多 k 个不同元素

时间复杂度 O(n⋅klogk)

👉 I 版 n / k 都比较小，完全 OK

空间复杂度 O(k)



class Solution {
    public int[] findXSum(int[] nums, int k, int x) {
        int n = nums.length;
        int[] res = new int[n - k + 1];

        Map<Integer, Integer> freq = new HashMap<>();

        // 初始化第一个窗口
        for (int i = 0; i < k; i++) {
            freq.put(nums[i], freq.getOrDefault(nums[i], 0) + 1);
        }

        res[0] = calcXSum(freq, x);

        // 滑动窗口
        for (int i = k; i < n; i++) {
            int add = nums[i];
            int remove = nums[i - k];

            freq.put(add, freq.getOrDefault(add, 0) + 1);

            freq.put(remove, freq.get(remove) - 1);
            
            if (freq.get(remove) == 0) {
                freq.remove(remove);
            }

            res[i - k + 1] = calcXSum(freq, x);
        }

        return res;
    }

    private int calcXSum(Map<Integer, Integer> freq, int x) {
        List<int[]> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            list.add(new int[]{e.getKey(), e.getValue()});
        }

        // 排序：freq 降序，num 降序
        list.sort((a, b) -> {
            if (a[1] != b[1]) return b[1] - a[1];
            return b[0] - a[0];
        });

        int sum = 0;
        for (int i = 0; i < Math.min(x, list.size()); i++) {
            sum += (long) list.get(i)[0] * list.get(i)[1];
        }
        return sum;
    }
}


📊 实时统计系统

	每 k 秒一个统计窗口

	统计 出现最频繁的前 x 个事件

	并计算加权得分

📈 热点分析 / 推荐系统

	窗口内最常出现的商品 / 搜索词

	频率 × 权重作为评分
