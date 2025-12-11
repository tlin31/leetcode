149. Max Points on a Line - Hard

Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane, return the maximum number of points that lie on the same straight line.

 

Example 1:


Input: points = [[1,1],[2,2],[3,3]]
Output: 3
Example 2:


Input: points = [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
Output: 4
 

Constraints:

1 <= points.length <= 300
points[i].length == 2
-104 <= xi, yi <= 104
All the points are unique.


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

	✔ 对每个点 p，计算它与所有其他点的斜率

	同一条直线 = 斜率相同

	用 HashMap 统计某个斜率出现次数

注意处理特殊情况：

- 斜率无穷大（垂直线）

- 重合点（duplicate）

- 精度问题 → 斜率用分数 gcd(dx, dy) 表示（避免 double 精度）


斜率 dy/dx 是 double，容易出现：0.333333 vs 0.333332

所以不能用 double！必须用 最简分数 Greatest Common Divisor, GCD：

	dx = x2 - x1
	dy = y2 - y1
	g = gcd(dx, dy)
	dx /= g
	dy /= g

例子：使用 gcd(dx, dy) 化简：
	dx = 4, dy = 8  
	g = gcd(4, 8) = 4  
	dx = 4 / 4 = 1  
	dy = 8 / 4 = 2  
	最终斜率 = (1, 2)


这样斜率变成一个整数 pair (dx, dy)，可以安全放入 HashMap。

📌 算法流程（按点枚举）

	对于每个点 i：

	map = new HashMap<斜率, count>

	duplicate = 1（计上自己）

	对所有 j > i：

		如果重复 → duplicate++

		否则计算 gcd 化简斜率 → map 增加计数

	当前点的最大共线点数 = max(map.values) + duplicate

	全局最大值更新


Stats:

时间复杂度：O(n²)	


class Solution {
    public int maxPoints(int[][] points) {
        int n = points.length;
        if (n <= 2) return n;

        int maxPoints = 1;

        for (int i = 0; i < n; i++) {
            Map<String, Integer> slopeMap = new HashMap<>();
            int duplicate = 1;

            for (int j = i + 1; j < n; j++) {
                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];

                if (dx == 0 && dy == 0) {
                    // duplicate point
                    duplicate++;
                    continue;
                }

                int g = gcd(dx, dy);
                dx /= g;
                dy /= g;

                // normalize dx, dy (to avoid -1/1 and 1/-1 inconsistency)
                if (dx < 0) {
                    dx = -dx;
                    dy = -dy;
                }

                String key = dx + "," + dy;
                slopeMap.put(key, slopeMap.getOrDefault(key, 0) + 1);
            }

            int localMax = 0;
            for (int count : slopeMap.values()) {
                localMax = Math.max(localMax, count);
            }

            maxPoints = Math.max(maxPoints, localMax + duplicate);
        }

        return maxPoints;
    }

    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}



🌍 场景 2：分布式系统中——请求热点聚类（bRPC、gRPC）

请求的 Latency 分布点有方向趋势：

	某类请求随负载增加按线性增长

	某类请求完全不相关

在性能日志中找“直线分布”能识别：

	网络瓶颈趋势

	增长模式一致的请求类型

	找出需要优化的一簇请求

本质与找最大共线点一样：方向一致 → 属于同一族。

🌍 场景 3：图像识别（Edge Detection）

Hough Transform 找直线的原理：

将图像像素点转成“斜率 + 截距”统计

出现次数最多的那个斜率 → 图像里的直线

LeetCode 149 就是一个“迷你版 Hough Transform”。



