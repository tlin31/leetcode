# Sorting & Merging

## Table of contents
1. Counting Sort
2. Bucket Sort
3. Selection Sort
4. Quick Sort: Java's sort 
5. Insertion Sort
6. Merge Sort：不依赖输入分布，是 外部排序 / 分布式排序 / MapReduce 的基础，比 QuickSort 更安全（无退化）
7. Heap Sort
8. Radix Sort
9. Bubble Sort

## Summary:

- 范围小 → Counting

- 稳定 + 大数据 → Merge

- 快 + 原地 → Quick

- 内存受限 → Heap

- 几乎有序 → Insertion

- 整数位排序 → Radix


| 排序算法        | 时间复杂度       | 空间复杂度 | 稳定   | 是否比较排序 | 典型用途   |
| -------------- | -------------- | -------- | ----  | ------      | ------ |
| Counting Sort  | O(n + k)       | O(k)     | ✅    | ❌          | 数据范围小  |
| Bucket Sort    | O(n + k) avg   | O(n + k) | 取决实现 | ❌        | 均匀分布   |
| Quick Sort     | O(n log n) avg | O(log n) | ❌    | ✅          | 工业级    |
| Merge Sort     | O(n log n)     | O(n)     | ✅    | ✅          | 稳定排序   |
| Heap Sort      | O(n log n)     | O(1)     | ❌    | ✅          | 内存受限   |
| Radix Sort     | O(d·(n+k))     | O(n+k)   | ✅    | ❌          | 整数/字符串 |
| Insertion Sort | O(n²)          | O(1)     | ✅    | ✅          | 小数据    |
| Bubble Sort    | O(n²)          | O(1)     | ✅    | ✅          | 教学     |
| Selection Sort | O(n²)          | O(1)     | ❌    | ✅          | 教学     |



问题: 比较O（n+k）的bucket sort和O（n * log n）的排序算法？

1. 理论性能：𝑂(𝑛+𝑘) 胜出 
- 桶排序 𝑂(𝑛+𝑘))：是一种非比较排序。它通过空间换取时间，跳出了比较排序算法 𝑂(𝑛log𝑛) 的理论下界

- 当数据分布非常均匀时，每个数据几乎直接“跳”进它所属的桶，这种线性效率在处理海量数据时（如 10 亿个分布均匀的浮点数）具有绝对优势。

- 快速/归并排序 𝑂(𝑛log𝑛)：是比较排序。无论数据分布如何，它都必须通过两两比较来确定顺序，其处理速度随数据量 𝑛 的增加呈对数级增长，效率上限低于线性算法。 

2. 实际运行速度：𝑂(𝑛log𝑛) 往往表现更稳 
在实际中，常数项（Constant Factor）和缓存命中率决定了真实感知的速度：

- 硬件缓存友好性：
  - 𝑂(𝑛log𝑛)（如快速排序）：具有极佳的局部性原理，它主要在连续的内存块上操作，能充分利用 CPU 的 L1/L2 缓存。
  - 桶排序：由于数据被分散到不同的桶（通常是链表或离散数组），会产生大量的随机内存访问，导致 CPU 缓存频繁失效（Cache Miss），在实际执行中可能会慢于优化良好的快排。

- 内存分配开销：
  - 桶排序需要频繁申请和管理多个桶的内存空间，这个“管理成本”有时会抵消掉算法复杂度带来的红利。 





## 1. Count sort（计数排序）

- 核心思想： 不比较元素，统计每个值出现的次数。

### 特点
1) 非比较排序
2) 要求数据是 整数 + 范围小
3) 非常快
4) In place
- 适合: 年龄、分数、ID 等


### 复杂度
- Time: O(n + k), size of inputArray[] and countArray[]
- worst case, avg case和best case都是O(n + k)
- Space: O(k)



https://www.youtube.com/watch?v=OKd534EWcdk&ab_channel=CSDojo

### 算法：
  - 统计：创建一个额外的计数数组 count[]，其长度取决于待排序数据的范围（最大值 - 最小值 + 1）。
  - 累加：记录每个数字出现的次数。
  - 填充：根据统计好的次数，按顺序将数字填回原数组。

### 例子
以数组 [4, 2, 2, 8, 3, 3, 1] 为例

1) 确定范围（Range）
- 遍历数组，找到最大值 max 和最小值 min。
- 例子中：min = 1, max = 8。
- 计算数值范围：range = 8 - 1 + 1 = 8。

2) 统计频率（Counting）
- 创建一个长度为 range 的计数数组 count[]，初始化为 0。再次遍历原数组，每遇到一个值，就在对应的索引处加 1。
- count 数组状态：索引0代表值1，索引1代表值2...
- 统计结果：count = [1, 2, 2, 1, 0, 0, 0, 1] （表示1出现1次，2出现2次，3出现2次...）

3) 累加计数（Prefix Sum）—— 稳定性的关键
- 对 count[] 数组进行顺序累加。count[i] = count[i] + count[i-1]。
- 累加后：count = [1, 3, 5, 6, 6, 6, 6, 7]。
- 数学意义：此时 count[i] 代表小于等于该数值的元素一共有多少个。例如 count[1]=3，意味着值“2”及其以下的数共有 3 个，那么最后一个“2”应该排在第 3 位（索引 2）。

4) 反向填充（Backwards Filling）
- 这是保证稳定性（相同元素相对顺序不变）的关键。

- 从原数组的末尾开始向前遍历：
  - 取出一个数（如原数组最后一个数 1）。
  - 查看 count 数组中该数对应的位置：count[1-1] 是 1。
  - 将其放入结果数组的第 1-1 = 0 个位置。
  - 将 count 数组中对应的数值减 1（count[0] 变为 0）。
  - 重复直至遍历完原数组。


```java
public class CountingSort {
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        // 1. 寻找范围
        int max = arr[0], min = arr[0];
        for (int num : arr) {
            max = Math.max(max, num);
            min = Math.min(min, num);
        }
        int range = max - min + 1;

        // 2. 统计出现次数
        int[] count = new int[range];
        for (int num : arr) {
            count[num - min]++;
        }

        // 3. 累加次数（为了实现稳定性）
        // 累加后的 count[i] 表示该元素在结果数组中的最后一个位置
        for (int i = 1; i < range; i++) {
            count[i] += count[i - 1];
        }

        // 4. 倒序遍历原数组，构建结果数组
        int[] output = new int[arr.length];

        for (int i = arr.length - 1; i >= 0; i--) {
            int val = arr[i];
            int position = count[val - min] - 1; // 获取在输出数组中的索引
            output[position] = val;
            count[val - min]--; // 减少次数，供下一个相同数字使用
        }

        // 5. 复制回原数组
        System.arraycopy(output, 0, arr, 0, arr.length);
    }
}

```


## 2. Bucket Sort/ Bin Sort 桶排序

- 核心思想: 把数据分到多个桶里，每个桶内部排序。

### 特点
- 非比较排序（桶内可用比较）
- 依赖数据分布是否均匀
- use linked list for multiple elements in an bucket
- 与计数排序只能处理整数不同，桶排序可以处理浮点数，并且在数据分布均匀时，时间复杂度接近线性 O(n)
- 适合：浮点数、均匀分布数据

### 复杂度
- Avg: O(n)， If insertion sort is used to sort bucket elements, the overall complexity will be linear, i.e. O(n+k).
- Worst: O(n²)
- Space Complexity: O(n+k).


### Algorithm: 
1. 分桶：根据数据的范围，将区间划分为 k 个连续的子区间（桶）。例如：数据在 [0,1)之间，分 10 个桶，则 [0,0.1)是第一个桶，[0.1,0.2)是第二个。
2. 入桶：遍历原数组，将每个元素映射到对应的桶中。
3. 内排序：对每个非空的桶进行单独排序（内部通常使用 O(n * log n) 级别的算法）。
4. 合并：按照桶的顺序，依次取出桶中的元素，拼成最终的有序序列


### In problems

1. lc 274 H-index: 
	- the h-index is defined as the number of papers with reference greater than the number. 
	- So assume n is the total number of papers, if we have n+1 buckets, number from 0 to n, then for any paper with reference corresponding to the index of the bucket, we increment the count for that bucket. 
	- The only exception is that for any paper with larger number of reference than n, we put in the n-th bucket.



```java
import java.util.*;

public class BucketSort {
    public static void sort(float[] arr) {
        int n = arr.length;
        if (n <= 1) return;

        // 1. 创建桶（使用 ArrayList 数组）
        List<Float>[] buckets = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            buckets[i] = new ArrayList<>();
        }

        // 2. 将元素映射到桶中
        for (float val : arr) {
            int bucketIdx = (int) (val * n); // 映射公式：val * 桶数量
            buckets[bucketIdx].add(val);
        }

        // 3. 对每个桶内部排序
        for (int i = 0; i < n; i++) {
            Collections.sort(buckets[i]); 
        }

        // 4. 合并结果
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (float val : buckets[i]) {
                arr[index++] = val;
            }
        }
    }
}

```


## 3. Selection Sort
- 每轮选最小的放到前面。

- 简单但慢

- 不稳定

- 复杂度：Time: O(n²)，Space: O(1)

- 适合： 教学 / 理解排序思想

Tips: 

	- repeatedly selects the smallest (or largest) and swaps it with the first element of the unsorted part. 
	- in place
	- not stable by default, depends on implementation

### Algo

	Find the smallest card. Swap it with the first card.
	Find the second-smallest card. Swap it with the second card.
	Find the third-smallest card. Swap it with the third card.
	Repeat finding the next-smallest card, and swapping it into the correct position until the array is sorted.

```java
       int n = arr.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (arr[j] < arr[min_idx])
                    min_idx = j;

            // Swap the found minimum element with the first
            // element
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }

```


## 4. Quick Sort（快速排序，Java 常用）

- 分治Divide and Conquer + pivot，把小的放左，大的放右。

- 平均最快的比较排序

- 不稳定

- 最坏情况要避免（随机 pivot）

- 复杂度: Avg: O(n log n), Worst: O(n²)

- Java中：Arrays.sort(int[]) → Dual-Pivot QuickSort， Arrays.sort(Object[]) → TimSort


**Advantages of Quick Sort:**
- It is a divide-and-conquer algorithm that makes it easier to solve problems.
- It is efficient on large data sets.
- It has a low overhead, as it only requires a small amount of memory to function.

**Disadvantage**
- It has a worst-case time complexity of O(N^2), which occurs when the pivot is chosen poorly.
- It is not a good choice for small data sets.
- It is not a stable sort

### 1. Compexity

Average Case & best case: O ( N log (N))性能极佳，常数项小。

Worst Case: O(N^2), 当数组已经有序且每次都选第一个元素作为基准时触发。

Auxiliary Space: O ( log (N))主要是递归调用产生的栈空间。

- O(1), if we don’t consider the recursive stack space. If we consider the recursive stack space then, in the worst case quicksort could make O(N).


### 2. Algorithm
快速排序通过一个“基准值”（Pivot），将数组分为两个子数组，递归地进行排序：
1. 挑选基准 (Pick)：从数组中选出一个元素作为 pivot。
2. 分区 (Partition)：重新排列数组，所有比基准小的元素摆放在基准前面，所有比基准大的元素摆在基准后面。 此时，基准元素就处于其最终确定的位置。
3. 递归 (Recursive)：递归地对基准前后的两个子数组进行相同操作。

```java
public class QuickSort {
    public void sort(int[] nums) {
        if (nums == null || nums.length < 2) return;
        quickSort(nums, 0, nums.length - 1);
    }

    private void quickSort(int[] nums, int left, int right) {
        if (left >= right) return;

        // 1. 分区操作，获取基准值的最终位置
        int pivotIndex = partition(nums, left, right);

        // 2. 递归排序左子数组和右子数组
        quickSort(nums, left, pivotIndex - 1);
        quickSort(nums, pivotIndex + 1, right);
    }

    private int partition(int[] nums, int left, int right) {
        // 选择最右侧元素作为基准 (Pivot)
        int pivot = nums[right];
        int i = left; // i 记录小于 pivot 的元素的边界

        for (int j = left; j < right; j++) {
            if (nums[j] < pivot) {
                swap(nums, i, j);
                i++;
            }
        }
        // 将基准值交换到中间正确的位置
        swap(nums, i, right);
        return i;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}


```

### 优化实践：如何让它更快？
1. 三数取中法 (Median-of-three)：不简单取第一个数，而是取左、中、右三个数的中间值作为基准。这能极大地避免在近乎有序的数组上退化到 O(n^2)。

2. 混合排序策略 (Hybrid Sort)：当子数组的大小降低到一定阈值（通常为 10-20 个元素）时，快速排序会切换为插入排序 (Insertion Sort)，因为在小规模数据上插入排序的常数开销更小。

3. 双基准快排 (Dual-Pivot Quicksort)：这是 Java 核心库采用的方案。它使用两个基准将数组分为三部分，比单基准快排能更好地处理大量重复元素的情况。

4. 随机化基准：通过随机选择基准，使算法在面对任何输入时都有极大概率维持 O(n * log n)

### 面试追问
Q1: 为什么 Java 的 Arrays.sort() 对基本类型用快排，而对对象类型用归并排序 (TimSort)？

- 稳定性 (Stability)：对象排序通常需要稳定性（即相等元素的相对顺序不变），快排是不稳定排序 (Unstable)，而 TimSort 是稳定的。
- 基本类型无需稳定性：对于 int 这种基本类型，两个 5 交换位置没有任何影响，因此选择平均速度最快的快排。

Q2: 快速排序和堆排序 (Heap Sort) 相比，哪个更好？

- 虽然两者平均都是 O（n log n），但在实际运行中快排通常更快。因为快排具有良好的 局部性原理 (Locality of Reference)，对 CPU 缓存 (CPU Cache) 更加友好，而堆排序的**跳跃式访问**会导致频繁的缓存缺失。



### Java用的排序？



1️⃣ 对 基本类型（int / long / double）
Arrays.sort(int[])


👉 Dual-Pivot QuickSort（双轴快排）

平均：O(n log n)

实际速度极快

cache locality 极佳

Java 7+ 使用

2️⃣ 对 对象类型（Integer / String / 自定义对象）
Arrays.sort(Object[])


👉 TimSort（归并排序 + 插入排序）

稳定排序

O(n log n)

对「部分有序」数组非常快

Python / Java 都用它


## 4.1 Tim Sort
TimSort 是一种结合了 合并排序 (Merge Sort) 和 插入排序 (Insertion Sort) 的混合稳定排序算法


### 1. 核心原理
TimSort 的核心思想是：现实世界中的数据通常包含许多**已经部分排好序**的子序列。

A. 分区 (Runs)
- TimSort 将数组拆分为一个个小的、已经排好序的片段，称为 Run。
- 如果片段是升序的，直接保留。
- 如果片段是严格降序的，将其反转为升序。

B. 二分插入排序 (Binary Insertion Sort)
- 如果一个 Run 的长度太短（小于 minRun，通常是 32 或 64），TimSort 会使用插入排序将后续元素挤进这个 Run 中。由于数据量小且在连续内存，插入排序在小范围内非常快。

C. 合并 (Merging)
- 当产生多个 Run 后，TimSort 使用合并排序将它们合并。
- 合并规则：它维持一个栈来记录这些 Run，并遵循特定的合并策略（如 A>B+C 且 B>C），确保合并过程的平衡性。
- Galloping Mode (飞跃模式)：
  - 当合并两个已排序序列 A 和 B 时，如果发现 A 中的元素连续多次（默认 7 次）都比 B 中的当前元素小，算法会认为 A 中可能有一大块数据都更小。此时不再逐一对比，而是通过二分查找直接定位 B[0]在 A 中的插入位置，批量拷贝数据。




### 2. 为什么 Java 对象排序必须用 TimSort？

1) 稳定性 (Stability)：
- 在电商项目中，如果用户先按“价格”排序，再按“销量”排序。稳定排序能保证销量相同时，价格的相对顺序不变。快排 (QuickSort) 不稳定，而 TimSort 是稳定的 (Stable)。

2) 适应性 (Adaptability)：
- 对于已经部分有序的数据（这在数据库查询结果中很常见），TimSort 的时间复杂度趋近于 O(N)，而快排依然是 O(N log N)
- 最坏情况保障： TimSort 的最坏时间复杂度是固定的 O(N log N)，不像快排可能退化到 O(N^2)


### 3. 业界最佳实践 (Best Practices)
1) 利用局部有序性：
- 在微服务中，如果从多个分片获取已排序的数据流，使用 Java 的 sort 会非常快，因为它能迅速识别这些 Runs。

2) 重写 compareTo 或 Comparator：
- TimSort 严格依赖比较逻辑。注意：Java 的 TimSort 要求比较器必须满足 传递性 (Transitivity)。如果你的比较逻辑（如 a > b 返回 1，b > a 返回 1）违反逻辑，Java 会抛出 IllegalArgumentException: Comparison method violates its general contract!。

3) 内存占用：
- TimSort 需要额外的临时空间来合并 Run（最坏 O(N)），如果你的微服务容器内存极度紧张且处理的是海量数据，需监控 JVM 堆内存 (Heap Memory)。



## 5. Insertion sort （插入排序）

- 像整理扑克牌一样插入。

- 稳定

- 对“几乎有序”的数组极快

- 复杂度：Worst: O(n²)，Best: O(n)

- 用途： 小数组， QuickSort 的子排序，Java 的 Arrays.sort的底层优化手段。



### Complexity

**Time Complexity**
- Best case: O(n), 当数组已经完全有序时，只需比较 n-1 次，无需移动。
- Worst case: O(n^2)当数组是逆序时
- 平均情况：O(n^2)

**Space** Complexity of Insertion Sort**
- Auxiliary Space: O(1)。它是原地排序 (In-place)，不需要额外空间。

稳定性：稳定。相同元素的相对顺序在排序后保持不变。


### Algo
插入排序的工作方式非常像我们在打牌时整理手中的扑克牌：

1. 分两部分：将数组逻辑上分为“已排序”和“未排序”两个部分。初始时，第一个元素被认为是已排序的。

2. 取牌：从未排序部分取出一个元素（称为 key）。

3. 找位置：在已排序部分中从右向左比较，如果已排序元素大于 key，则将其向右移动一位。

4. 插入：找到合适的位置后，将 key 插入。

5. 循环：重复上述过程，直到所有元素都已排序。


```java
    public class InsertionSort {
        public static void sort(int[] arr) {
            if (arr == null || arr.length < 2) return;

            for (int i = 1; i < arr.length; i++) {
                int key = arr[i]; // 当前待插入的“牌”
                int j = i - 1;

                // 将大于 key 的元素向后移动
                while (j >= 0 && arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j--;
                }
                // 插入到正确位置
                arr[j + 1] = key;
            }
        }
    }


```



## 6. Merge sort

- 分而治之：先递归排序左右两半，再线性合并两个有序数组。
- 👉 Merge Sort 非常适合 大数据 & 分布式

- 时间复杂度稳定：O(n log n)
- Space: O(n)

- 不依赖输入分布
- 是 外部排序 / 分布式排序 / MapReduce 的基础
- 比 QuickSort 更安全（无退化）

```
示例：
nums = [5,2,3,1]

拆分
[5,2] [3,1]
[5] [2] [3] [1]

合并
[2,5] [1,3]
→ [1,2,3,5]
```




```java
class Solution {
    int[] temp;

    public int[] sortArray(int[] nums) {
        temp = new int[nums.length];
        mergeSort(nums, 0, nums.length - 1);
        return nums;
    }

    private void mergeSort(int[] nums, int left, int right) {
        if (left >= right) return;

        int mid = left + (right - left) / 2;
        mergeSort(nums, left, mid);
        mergeSort(nums, mid + 1, right);
        merge(nums, left, mid, right);
    }

    /* 把 nums[left..mid] 和 nums[mid+1..right] 这两个「已经有序」的子数组，合并成一个有序区间 nums[left..right]
        1. 双指针比较，放入临时数组 temp

        2. 把剩余元素直接拷贝

        3. 把结果从 temp 写回 nums

    */
    private void merge(int[] nums, int left, int mid, int right) {
        int i = left;       // 指向左半部分的起点
        int j = mid + 1;    // 指向右半部分的起点
        int k = left;       // 指向 temp 中当前要写入的位置

        //用 <= → 保证稳定性, stable sort
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }

        //处理左边和右边的剩余元素，因为已经排序了，直接照搬
        while (i <= mid) temp[k++] = nums[i++]; 
        while (j <= right) temp[k++] = nums[j++];

        // 把结果从 temp 写回 nums
        for (int p = left; p <= right; p++) {
            nums[p] = temp[p];
        }
    }

}
```


### 复杂度分析
| 项目    | 复杂度        |
| ----   | ---------- |
| 时间    | O(n log n) |
| 空间    | O(n)       |
| 稳定性   | ✅ 稳定       |
| 原地排序 | ❌          |


### 现实工程类比 🌍

- Kafka / Hadoop 中的排序阶段

- 外部排序（磁盘数据）

- 多机 merge 结果

- 日志合并

- 数据库排序（ORDER BY）



## 7.Heap Sort

核心思想

- 用最大堆 / 最小堆选最大值。

- 堆排序利用了完全二叉树 (Complete Binary Tree) 的特性：
  - 最大堆 (Max Heap)：任意节点的值都 >= 其子节点。
  - 堆顶（根节点）必然是最大值。
  - 索引规律：对于索引为 i 的节点，左孩子是 2i + 1，右孩子是 2i + 2，父节点是 (i - 1) / 2。

- 原地排序

- 不稳定， 但是可以implement成stable的

- 复杂度 
  - 最好、最坏、平均Time: O(n log n)
  - Space: O(1)

- 用途： 内存受限， Top K 问题

### Algorithm

1. 建堆：将无序数组构建成一个大顶堆。

2. 交换与下沉：将堆顶元素（最大值）与数组末尾元素交换，此时最大值已归位。

3. 重新调整：缩小堆的范围，对新的堆顶执行“下沉”（Sift Down）操作，使其重新满足大顶堆特性。

4. 循环：重复上述过程，直到整个数组有序。

```java

public class HeapSort {
    public void sort(int[] arr) {
        int n = arr.length;

        // 1. 构建大顶堆（从最后一个非叶子节点开始向上调整）
        // 👉 从 n/2 开始的节点，全是叶子节点, 叶子节点天然满足堆性质, 最后一个非叶子节点 = n/2 - 1
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        /* 2. 依次取出堆顶元素并调整
            arr[0] 是当前最大值， 把它放到最终位置 i = n-1
            堆大小缩小为 i
            修复堆顶即可（只破坏了 root）
        */
        for (int i = n - 1; i > 0; i--) {
            // 将当前最大的堆顶换到末尾 swap(0, i)
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // 重新调整root @ indx 0的堆，边界是i，因为[i+1 ... n-1]已经排好序（最大在右）
            heapify(arr, i, 0);
        }
    }

    // 堆化逻辑：使以 i 为根的子树满足大顶堆， 左右子树已经是堆，只修当前节点
    void heapify(int[] arr, int n, int i) {
        int largest = i;  // 假设根节点最大
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // If left child is larger than root
        if (left < n && arr[left] > arr[largest]) 
            largest = left;

        if (right < n && arr[right] > arr[largest]) 
            largest = right;

        // 如果最大值不是根节点，则交换并递归
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // 递归调整受影响的子树
            heapify(arr, n, largest);
        }
    }
}


```

### 代码使用例子

215. Kth Largest Element in an Array

```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int num: nums) {
            heap.add(num);
            if (heap.size() > k) {
                heap.remove();
            }
        }
        
        return heap.peek();
    }
}
```
### 例子

数组：[4, 10, 3, 5, 1]

索引： 0  1   2  3  4


#### 第一阶段：建堆 (Build Max Heap)

这个数组对应的完全二叉树结构是：
```
        4 (0)
       /     \
    10 (1)    3 (2)
   /    \
 5 (3)   1 (4)
```

步骤：从最后一个非叶子节点开始 heapify。
公式：最后一个非叶子节点索引 = n/2 - 1 = 5/2 - 1 = 索引 1 (值为 10)。

1) 处理索引 1 (值为 10)：
- 孩子是 5 (索引3) 和 1 (索引4)。
- 10 比 5 和 1 都大，不需要交换。
- 树保持不变。


2) 处理索引 0 (值为 4)：
- 左孩子是 10 (索引1)，右孩子是 3 (索引2)。
- 10 比 4 大，交换 4 和 10。
- 数组变为：[10, 4, 3, 5, 1]
  - 递归调整：交换后，原本的 4 到了索引 1，需要检查它的孩子（5 和 1）。
  - 5 比 4 大，交换 4 和 5。
  - 数组变为：[10, 5, 3, 4, 1]（建堆完成！堆顶是 10）。


#### 第二阶段：排序 (Sorting)
现在我们有一个最大堆：[10, 5, 3, 4, 1]。

1) Round 1:
- 交换：堆顶 10 和末尾 1 交换 --> 数组变为：[1, 5, 3, 4, | 10] （10 已固定）。
- 调整：对剩余的 [1, 5, 3, 4] 进行 heapify(0)。
  - 1 与孩子 5, 3 比较，5 最大，交换 1 和 5 -> [5, 1, 3, 4]。
  - 1 再与孩子 4 比较，4 最大，交换 1 和 4 -> [5, 4, 3, 1]。
- 结果：[5, 4, 3, 1, | 10]


2) Round 2:
- 交换：堆顶 5 和当前末尾 1 交换。 --> 数组变为：[1, 4, 3, | 5, 10] （5 已固定）。
- 调整：对 [1, 4, 3] 进行 heapify(0)。
- 1 与孩子 4, 3 比较，4 最大，交换 1 和 4 -> [4, 1, 3]。
- 结果：[4, 1, 3, | 5, 10]


3) Round 3:
- 交换：堆顶 4 和当前末尾 3 交换。 --> 数组变为：[3, 1, | 4, 5, 10] （4 已固定）。
- 调整：对 [3, 1] 进行 heapify(0)。3 比 1 大，不需要交换。
- 结果：[3, 1, | 4, 5, 10]


4) Round 4:
- 交换：堆顶 3 和当前末尾 1 交换, 数组变为：[1, | 3, 4, 5, 10] （3 已固定）。
- 完成：只剩一个元素，排序结束。

最终数组：[1, 3, 4, 5, 10]


### Advantages of Heap Sort:

1. Efficient Time Complexity: 
- Heap Sort has a time complexity of O(n log n) in all cases. This makes it efficient for sorting large datasets. The log n factor comes from the height of the binary heap, and it ensures that the algorithm maintains good performance even with a large number of elements.

2. Memory Usage 
- Memory usage can be minimal (by writing an iterative heapify() instead of a recursive one). So apart from what is necessary to hold the initial list of items to be sorted, it needs no additional memory space to work

3. Simplicity 
- It is simpler to understand than other equally efficient sorting algorithms because it does not use advanced computer science concepts such as recursion.

### Disadvantages of Heap Sort:
1. Costly: Heap sort is costly as the constants are higher compared to merge sort even if the time complexity is O(n Log n) for both.

2. Unstable: Heap sort is unstable. It might rearrange the relative order.

3. Efficient: Heap Sort is not very efficient when working with highly complex data. 


## 8. Radix Sort

- 按位（个位、十位…）排序。

- 非比较排序

- 稳定

- 依赖位数

- 复杂度： time： O(d·(n+k)) d是number of digits, n 是 number of elements, and b is the base of the number system being used.

- Auxiliary Space: O(n + b)，因为要创建每一个digit的bucket

- 用途：整数、字符串排序


### Algorithm
基数排序通常采用 LSD (Least Significant Digit) 方式，即从最低有效位（个位）开始，一直排序到最高有效位。

1. 找最大值：确定数组中最大数的位数（决定需要进行几轮排序）。

2. 按位排序：从个位开始，使用一种稳定的排序算法（通常是计数排序 Counting Sort）对所有数字进行排序。

3. 迭代：移动到十位、百位……重复上述过程。

4. 稳定性要求：每一轮排序必须是稳定的，否则高位排序时会破坏低位已经排好的相对顺序。

```java
import java.util.Arrays;

public class RadixSort {
    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) return;

        // 1. 获取最大值，确定位数
        int max = arr[0];
        for (int val : arr) max = Math.max(max, val);

        // 2. 从个位开始，对每一位进行计数排序
        // exp 是 1, 10, 100...
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp);
        }
    }

    private static void countingSortByDigit(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10]; // 十进制只有 0-9

        // 统计当前位（(val/exp)%10）出现的次数
        for (int val : arr) {
            count[(val / exp) % 10]++;
        }

        // 累加计数，确定位置
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // 反向遍历保证稳定性
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }

        System.arraycopy(output, 0, arr, 0, n);
    }
}

```



## Bubble Sort

核心思想:
- 不断交换相邻逆序元素。

特点
- 最简单
- 最慢
- 稳定


### 复杂度
- Time: O(n²)
- Space: O(1)



### Algorithm
- traverse from left and compare adjacent elements and the higher one is placed at right side. 
- In this way, the largest element is moved to the rightmost end at first. 
- This process is then continued to find the second largest and place it and so on until the data is sorted.

```java
 static void bubbleSort(int arr[], int n)
    {
        int i, j, temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    
                    // Swap arr[j] and arr[j+1]
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            // If no two elements were
            // swapped by inner loop, then break
            if (swapped == false)
                break;
        }
    }

```






