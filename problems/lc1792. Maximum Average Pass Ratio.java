1792. Maximum Average Pass Ratio - Medium

There is a school that has classes of students and each class will be having a final exam. You are given a 2D integer array classes, where classes[i] = [passi, totali]. You know beforehand that in the ith class, there are totali total students, but only passi number of students will pass the exam.

You are also given an integer extraStudents. There are another extraStudents brilliant students that are guaranteed to pass the exam of any class they are assigned to. You want to assign each of the extraStudents students to a class in a way that maximizes the average pass ratio across all the classes.

The pass ratio of a class is equal to the number of students of the class that will pass the exam divided by the total number of students of the class. The average pass ratio is the sum of pass ratios of all the classes divided by the number of the classes.

Return the maximum possible average pass ratio after assigning the extraStudents students. Answers within 10-5 of the actual answer will be accepted.

 

Example 1:

Input: classes = [[1,2],[3,5],[2,2]], extraStudents = 2
Output: 0.78333
Explanation: You can assign the two extra students to the first class. The average pass ratio will be equal to (3/4 + 3/5 + 2/2) / 3 = 0.78333.
Example 2:

Input: classes = [[2,4],[3,9],[4,5],[2,10]], extraStudents = 4
Output: 0.53485
 

Constraints:

1 <= classes.length <= 105
classes[i].length == 2
1 <= passi <= totali <= 105
1 <= extraStudents <= 105


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************

给定若干班级 (pass, total)，你可以额外分配 extraStudents 个 必过学生，
每个学生只能加入一个班级，目标是 最大化所有班级通过率的平均值。



===================================================================================================
Method 1:

Method:

核心贪心思想（最重要）
❗关键洞察

每次都应该把学生分配给「边际收益最大」的班级。

对一个班级 (p, t)：

当前通过率：p/t



加 1 个必过学生后：p+1/t+1


👉 边际收益（增量）：Δ= (p+1/t+1)-p/t

每一步都选择 Δ 最大的班级 加学生
👉 典型：最大堆 / 优先队列

Stats:

	- 
	- 


class Solution {

    public double maxAverageRatio(int[][] classes, int extraStudents) {

        // 最大堆：按“边际收益”排序
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Double.compare(gain(b), gain(a))
        );

        for (int[] c : classes) {
            pq.offer(c);
        }

        // 分配 extraStudents
        while (extraStudents-- > 0) {
            int[] cur = pq.poll();
            cur[0]++; // pass++
            cur[1]++; // total++
            pq.offer(cur);
        }

        // 计算最终平均值
        double sum = 0.0;
        while (!pq.isEmpty()) {
            int[] c = pq.poll();
            sum += (double) c[0] / c[1];
        }

        return sum / classes.length;
    }

    // 边际收益
    private double gain(int[] c) {
        double p = c[0], t = c[1];
        return (p + 1) / (t + 1) - p / t;
    }
}


