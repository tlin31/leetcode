2141. Maximum Running Time of N Computers - Hard

You have n computers. You are given the integer n and a 0-indexed integer array batteries where the ith battery can run a computer for batteries[i] minutes. You are interested in running all n computers simultaneously using the given batteries.

Initially, you can insert at most one battery into each computer. After that and at any integer time moment, you can remove a battery from a computer and insert another battery any number of times. The inserted battery can be a totally new battery or a battery from another computer. You may assume that the removing and inserting processes take no time.

Note that the batteries cannot be recharged.

Return the maximum number of minutes you can run all the n computers simultaneously.

 

Example 1:


Input: n = 2, batteries = [3,3,3]
Output: 4
Explanation: 
Initially, insert battery 0 into the first computer and battery 1 into the second computer.
After two minutes, remove battery 1 from the second computer and insert battery 2 instead. Note that battery 1 can still run for one minute.
At the end of the third minute, battery 0 is drained, and you need to remove it from the first computer and insert battery 1 instead.
By the end of the fourth minute, battery 1 is also drained, and the first computer is no longer running.
We can run the two computers simultaneously for at most 4 minutes, so we return 4.

Example 2:


Input: n = 2, batteries = [1,1,1,1]
Output: 2
Explanation: 
Initially, insert battery 0 into the first computer and battery 2 into the second computer. 
After one minute, battery 0 and battery 2 are drained so you need to remove them and insert battery 1 into the first computer and battery 3 into the second computer. 
After another minute, battery 1 and battery 3 are also drained so the first and second computers are no longer running.
We can run the two computers simultaneously for at most 2 minutes, so we return 2.
 

Constraints:

1 <= n <= batteries.length <= 105
1 <= batteries[i] <= 109


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

二分查找 (Binary Search on Answer)
我们直接寻找答案：“能否让 n 台电脑同时运行 T  分钟？”
	下界 (Low): 1 分钟。
	上界 (High): 所有电池能量总和除以 n。


贪心判定 (Greedy Check)
	对于一个目标时间 T，每块电池能提供的最大贡献是多少？
	如果电池电量 batteries[i]>T，它最多也只能提供 T 分钟（因为它不能同时给两台电脑供电，
	多出的电量在 T 分钟内无法被其他电脑使用）。

	如果电池电量 batteries[i]<=T，它可以全部贡献出来。

	判定条件：如果所有电池贡献的电量之和 >=n x T，则目标时间 T 是可行的。



Stats:

	- 
	- 


class Solution {
    public long maxRunTime(int n, int[] batteries) {
        long sum = 0;
        for (int b : batteries) sum += b;
        
        long left = 1;
        long right = sum / n;
        long ans = 0;
        
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (canRun(n, batteries, mid)) {
                ans = mid; //保留当前找到的最佳可行解。
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }
    
    private boolean canRun(int n, int[] batteries, long target) {
        long totalPower = 0;
        for (int b : batteries) {
            // 贪心：每块电池对目标时间的贡献上限是 target
            totalPower += Math.min((long)b, target);
        }
        return totalPower >= (long)n * target;
    }
}


class Solution:
    def maxRunTime(self, n: int, batteries: List[int]) -> int:
    
        def can_run(target: int) -> bool:
            # 贪心：单个电池贡献不能超过目标运行时间 target
            total_power = sum(min(b, target) for b in batteries)
            return total_power >= n * target
        
        left = 1
        right = sum(batteries) // n
        ans = 0
        
        while left <= right:
            mid = (left + right) // 2
            if can_run(mid):
                ans = mid
                left = mid + 1
            else:
                right = mid - 1
                
        return ans

3. 业界最佳实践 (Best Practices)
1） 二分答案法的适用性：
当你发现“求最大值”很困难，但“给定一个值，判断是否可行”很容易时，优先考虑 二分答案 (Binary Search on Answer)。
这在双 11 压测确定系统最大 QPS 阈值时非常常用。

2） 数值溢出 (Overflow)：
在 Java 中处理此类问题必须使用 long。在微服务中处理大额订单金额或高频计数时，也应使用 Long 或 BigInteger。

3） 排序优化：
虽然二分法不需要排序，但如果先对电池排序，可以进一步优化 canRun 函数（利用前缀和或二分定位），但在 N
 较小时不明显。




