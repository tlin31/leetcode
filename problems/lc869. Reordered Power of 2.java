869. Reordered Power of 2 - Medium

You are given an integer n. We reorder the digits in any order (including the original order) 
such that the leading digit is not zero.

Return true if and only if we can do this so that the resulting number is a power of two.

 

Example 1:

Input: n = 1
Output: true
Example 2:

Input: n = 10
Output: false
 

Constraints:

1 <= n <= 109

******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

例如：

128 → rearranged → 281 → 是 2 的幂 → true

10 → rearranged → 01 → 1 → false


任何排列的数字，本质是：
数字的频次（0~9 的计数）是否和某个 2 的幂相同。

1. 预生成所有 2 的幂（范围：1 到 10^9）因为 n <= 10^9，2 的幂最多有 30 个。

2. 将每个 2 的幂转成 digit count signature

	例如：

	128 → “1:1,2:1,8:1”

	256 → “2:1,5:1,6:1”

	1024 → “0:1,1:1,2:1,4:1”

	我们把它转成一个 字符串版本的频次 key：

	"0112" → 表示某种排序后的字符

3. 比较 n 是否与其中任意一个 key 相同

	这样就避免了排列（factorial 级别），时间足够快。


Stats:

	- 计算签名：O(d log d)，d ≤ 10. 总共 31 个 2 的幂 → O(31 × d log d) → 近乎常数 -> 整体 O(1)
	- 

class Solution {
    public boolean reorderedPowerOf2(int n) {
        String target = signature(n);
        
        for (int i = 0; i < 31; i++) {
            int pow = 1 << i;
            if (target.equals(signature(pow))) {
                return true;
            }
        }
        return false;
    }
    
    private String signature(int n) {
        char[] arr = Integer.toString(n).toCharArray();
        Arrays.sort(arr);
        return new String(arr);
    }
}

是否可以把 power-of-2 signatures 缓存？

可以，将 31 个 precompute 到 hash set 中。

