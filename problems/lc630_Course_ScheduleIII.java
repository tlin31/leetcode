630. Course Schedule III - Hard

There are n different online courses numbered from 1 to n. You are given an array courses 
where courses[i] = [durationi, lastDayi] indicate that the ith course should be taken continuously 
for durationi days and must be finished before or on lastDayi.

You will start on the 1st day and you cannot take two or more courses simultaneously.

Return the maximum number of courses that you can take.

 

Example 1:

Input: courses = [[100,200],[200,1300],[1000,1250],[2000,3200]]
Output: 3
Explanation: 
There are totally 4 courses, but you can take 3 courses at most:
First, take the 1st course, it costs 100 days so you will finish it on the 100th day, and ready to take the next course on the 101st day.
Second, take the 3rd course, it costs 1000 days so you will finish it on the 1100th day, and ready to take the next course on the 1101st day. 
Third, take the 2nd course, it costs 200 days so you will finish it on the 1300th day. 
The 4th course cannot be taken now, since you will finish it on the 3300th day, which exceeds the closed date.

Example 2:

Input: courses = [[1,2]]
Output: 1
Example 3:

Input: courses = [[3,2],[4,3]]
Output: 0
 

Constraints:

1 <= courses.length <= 104
1 <= durationi, lastDayi <= 104


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

贪心策略：

1.按课程截止时间（lastDay）升序排序, 因为越早截止的课越紧急。

2.遍历课程：

每看到一门课，尝试选它（累计所需时间）。如果超出当前课程的截止时间-->把当前所有已选课程中耗时最长的课删掉。

最后保留的课程数就是最多能修的数量。

⚙️ 数据结构：

用一个 最大堆（PriorityQueue<Integer>） 存储已选课程的 duration。

每次添加课程时，累计总时间 totalTime += duration。

若 totalTime > lastDay，说明超时了：
→ 移除堆中最大 duration 的课程。


Stats:

项目	复杂度
排序			O(N log N)
堆操作		O(N log N)
总时间复杂度	O(N log N)
空间复杂度	O(N)



import java.util.*;

public class Solution {
    public int scheduleCourse(int[][] courses) {
        // 1. 按课程的截止时间升序排列
        Arrays.sort(courses, (a, b) -> a[1] - b[1]);

        // 2. 最大堆（存放已选课程的耗时）
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        int totalTime = 0;

        for (int[] c : courses) {
            int duration = c[0];
            int lastDay = c[1];

            // 先选这门课
            totalTime += duration;
            maxHeap.offer(duration);

            // 如果超过了截止时间，就移除耗时最长的课
            if (totalTime > lastDay) {
                totalTime -= maxHeap.poll();
            }
        }

        // 最终堆中剩下的课程数量
        return maxHeap.size();
    }
}




