1094. Car Pooling - Medium

There is a car with capacity empty seats. The vehicle only drives east (i.e., it cannot turn around and drive west).

You are given the integer capacity and an array trips where trips[i] = [numPassengersi, fromi, toi] indicates that the ith trip has numPassengersi passengers and the locations to pick them up and drop them off are fromi and toi respectively. The locations are given as the number of kilometers due east from the car's initial location.

Return true if it is possible to pick up and drop off all passengers for all the given trips, or false otherwise.

 

Example 1:

Input: trips = [[2,1,5],[3,3,7]], capacity = 4
Output: false
Example 2:

Input: trips = [[2,1,5],[3,3,7]], capacity = 5
Output: true
 

Constraints:

1 <= trips.length <= 1000
trips[i].length == 3
1 <= numPassengersi <= 100
0 <= fromi < toi <= 1000
1 <= capacity <= 105


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

这可以类比为：一个微服务节点的 CPU/内存容量 是固定的，多个异步任务在不同的时间段进入和退出，你需要判断该节点是否
		   会在某个时刻因资源耗尽而 OOM (Out of Memory) 或崩溃。



Stats:

	- 
	- 

核心算法方案：差分数组 (Difference Array) / 扫描线 (Sweep Line)

虽然可以用最小堆 (Min-Heap) 解决，但差分数组是处理此类“固定区间增减”问题的最优解 (O(n) 时间复杂度)。

逻辑步骤 (Logic Steps)：
1. 建立路段模型：由于车站范围固定 (0 到 1000)，我们可以创建一个数组 stops 来记录每个车站的乘客净变化量。

2. 记录变化 (Events)：
	在 from 站：stops[from] += numPassengers（乘客上车，资源占用增加）。
	在 to 站：stops[to] -= numPassengers（乘客下车，资源释放）。

3. 累加校验 (Prefix Sum)：遍历 stops 数组，累加每个车站的净变化量，得到每个时刻车上的总人数。
						如果任何时刻总人数超过 capacity，返回 false。



class Solution {
    public boolean carPooling(int[][] trips, int capacity) {
        // 1. 定义差分数组 (Stop range: 0 to 1000)
        int[] stops = new int[1001];
        
        // 2. 标记上车与下车的变化量 (Mark net changes)
        for (int[] trip : trips) {
            int num = trip[0];
            int from = trip[1];
            int to = trip[2];
            
            stops[from] += num;
            stops[to] -= num;
        }
        
        // 3. 计算前缀和，检查是否超载 (Calculate prefix sum)
        int currentPassengers = 0;
        for (int count : stops) {
            currentPassengers += count;
            if (currentPassengers > capacity) {
                return false;
            }
        }
        
        return true;
    }
}

class Solution:
    def carPooling(self, trips: List[List[int]], capacity: int) -> bool:
        # 1. 差分数组或使用字典处理离散车站
        stops = [0] * 1001
        
        # 2. 记录每个站点的净人数变化
        for num, start, end in trips:
            stops[start] += num
            stops[end] -= num
            
        # 3. 遍历路线，累加当前车内总人数 (Simulate the trip)
        current_passengers = 0
        for change in stops:
            current_passengers += change
            if current_passengers > capacity:
                return False
                
        return True




