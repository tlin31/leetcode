2611. Mice and Cheese - Medium

There are two mice and n different types of cheese, each type of cheese should be eaten by exactly one mouse.

A point of the cheese with index i (0-indexed) is:

reward1[i] if the first mouse eats it.
reward2[i] if the second mouse eats it.
You are given a positive integer array reward1, a positive integer array reward2, and a non-negative integer k.

Return the maximum points the mice can achieve if the first mouse eats exactly k types of cheese.

 

Example 1:

Input: reward1 = [1,1,3,4], reward2 = [4,4,1,1], k = 2
Output: 15
Explanation: In this example, the first mouse eats the 2nd (0-indexed) and the 3rd types of cheese, and the second mouse eats the 0th and the 1st types of cheese.
The total points are 4 + 4 + 3 + 4 = 15.
It can be proven that 15 is the maximum total points that the mice can achieve.


Example 2:

Input: reward1 = [1,1], reward2 = [1,1], k = 2
Output: 2
Explanation: In this example, the first mouse eats the 0th (0-indexed) and 1st types of cheese, and the second mouse does not eat any cheese.
The total points are 1 + 1 = 2.
It can be proven that 2 is the maximum total points that the mice can achieve.
 

Constraints:

1 <= n == reward1.length == reward2.length <= 105
1 <= reward1[i], reward2[i] <= 1000
0 <= k <= n


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************

reward1[i]：老鼠 1 吃第 i 个食物的得分

reward2[i]：老鼠 2 吃第 i 个食物的得分

要求：

恰好 k 个食物给老鼠 1
剩下的都给老鼠 2
使总分最大


===================================================================================================
Method 1:

Method: 差值排序（贪心）

对于每个食物，我们计算：

diff[i] = reward1[i] - reward2[i]


含义：diff 大 → 老鼠 1 吃更赚， diff 小甚至负 → 老鼠 2 吃更赚

因此策略非常简单：

计算所有 diff

按 diff 从大到小排序

前 k 个分给老鼠 1

剩下的分给老鼠 2	


Stats:

	- 时间：O(n log n)（排序）

	- 空间：O(1) 或 O(n)（看你是否原地计算 diff）



    public int miceAndCheese(int[] A, int[] B, int k) {
        int res = 0, n = A.length, diff[] = new int[n];
        for (int i = 0; i < n; i++) {
            diff[i] = A[i] - B[i];
            res += B[i];
        }
        Arrays.sort(diff);
        for (int i = 0; i < k; i++)
            res += diff[n - 1 - i];
        return res;
    }


    
class Solution {
    public int miceAndCheese(int[] reward1, int[] reward2, int k) {
        int n = reward1.length;
        int[][] arr = new int[n][3];

        for (int i = 0; i < n; i++) {
            arr[i][0] = reward1[i];
            arr[i][1] = reward2[i];
            arr[i][2] = reward1[i] - reward2[i]; // diff
        }

        Arrays.sort(arr, (a, b) -> b[2] - a[2]); // 按 diff 降序

        int sum = 0;
        for (int i = 0; i < n; i++) {
            if (i < k) 
            	sum += arr[i][0]; // 老鼠 1
            else 
            	sum += arr[i][1];       // 老鼠 2
        }

        return sum;
    }
}




