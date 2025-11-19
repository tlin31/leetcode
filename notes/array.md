## Array

### declare the arraylist

不用写里面的type

```java
    1.	List<List<Integer>> list = new ArrayList<>();
    	int[] intArray; 
    	intArray = new int[20]
    2.  List < String > r = (ArrayList < String > ) currLst.clone()
    3.  List < String > r = new ArrayList<>(tempList)					--> initialize with templist
    4. ArrayList<String> str = new ArrayList<String>();
    5. ArrayList<String> words = new ArrayList<String>(
            Arrays.asList("A",
                          "B",
                          "C"));
```

### comparator

- A.compareTo(B): if == 0, maintain the original order
                  if == 1, first is larger, put it to the second --> original A > B, now order is [B,A]
                  if == -1, first is smaller


### sort

```java

----Method 1:
        Arrays.sort(nums); //time: O(n*logn)

----Method 2:

        class Sortbyroll implements Comparator<Student> 
        { 
            // Used for sorting in ascending order of roll number 
            public int compare(Student a, Student b) 
            { 
                return a.rollno - b.rollno; 
            } 
        } 
      
        Arrays.sort(arr, new Sortbyroll()); 
  
----Method 3:
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]); //last element of interval
        Arrays.sort(intervals, (i1, i2) -> Integer.compare(i1[0], i2[0])); //first elem of interval

        //sort (interval data type -> using the standard of )

---Method 4:   

        // Sort intervals
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[0], b[0]);
            }
        });

--- Method 5
        private class IntervalComparator implements Comparator<Interval> {
            @Override
            public int compare(Interval a, Interval b) {
                return a.start < b.start ? -1 : a.start == b.start ? 0 : 1;
            }
        }
        Collections.sort(intervals, new IntervalComparator());

```


#### Arrays.sort() vs Collections.sort()
- Arrays.sort works for arrays which can be of primitive data type also. 
- Collections.sort() works for objects Collections like ArrayList, LinkedList, etc.


### length
```java
	a.length
```


### change from string to int
```java
 int m = str.charAt(i) - '0';
 ```


### change from list to array
```java
        List<int[]> res = new ArrayList<>();
        return res.toArray(new int[res.size()][]); // pass in number of elements in the list.

 ```

cast arraylist to list of list

        public List<List<Integer>> threeSum(int[] nums) {
        Set<List<Integer>> res  = new HashSet<>();
        if(nums.length==0) return new ArrayList<>(res);


to return 1 element of List

        Arrays.asList(nums[i],nums[j],nums[k]);


### static way to copy an array --> System.arraycopy(Object src,int srcPos,
									Object dest,int destPos,int length)

src:源数组；		srcPos:源数组要复制的起始位置；
dest:目的数组；	destPos:目的数组放置的起始位置；		length:复制的长度。

1. 注意：src and dest都必须是同类型或者可以进行转换类型的数组．
2. 有趣的是这个函数可以实现自己到自己复制，比如：
	int[] fun ={0,1,2,3,4,5,6}; 
	System.arraycopy(fun,0,fun,3,3);
	则结果为：{0,1,2,0,1,2,6};

3. 实现过程是这样的，先生成一个长度为length的临时数组,将fun数组中srcPos 
	到srcPos+length-1之间的数据拷贝到临时数组中，再执行System.arraycopy(临时数组,0,fun,3,3).

### Fill in array

        for (int[] memoRow : memo)
            Arrays.fill(memoRow, -1);

### 2d array 

### declare


    data_type[][] array_Name = new data_type[no_of_rows][no_of_columns];





#### length

1. row = foo.length
2. column = foo[0].length


    int[][] foo = new int[][] {
        new int[] { 1, 2, 3 },
        new int[] { 1, 2, 3, 4},
    };

    System.out.println(foo.length); //2
    System.out.println(foo[0].length); //3
    System.out.println(foo[1].length); //4


## Arraylist & linked list
1. ArrayList:

- Implemented with the concept of dynamic array.

    ArrayList<Type> arrL = new ArrayList<Type>();

- ArrayList needs to be update its index if you insert something anywhere except at the end of array.

2. LinkedList:
- Implemented with the concept of doubly linked list.

    LinkedList<Type> linkL = new LinkedList<Type>();


- Insertions are easy and fast in LinkedList as compared to ArrayList because there is no risk of resizing array and copying content to new array if array gets full which makes adding into ArrayList of O(n) in worst case, while adding is O(1) operation in LinkedList
 
- Removal is better in LinkedList than ArrayList due to same reasons as insertion.
- LinkedList has more memory overhead than ArrayList because in ArrayList each index only holds actual object (data) but in case of LinkedList each node holds both data and address of next and previous node.

- Both LinkedList and ArrayList require O(n) time to find if an element is present or not. However we can do Binary Search on ArrayList if it is sorted and therefore can search in O(Log n) time.

```java
import java.util.*;
  
public class ArrayListLinkedListExample 
{ 
    public static void main(String[] args) 
    { 
        ArrayList<String> arrlistobj = new ArrayList<String>(); 
        arrlistobj.add("0. Practice.GeeksforGeeks.org"); 
        arrlistobj.add("1. Quiz.GeeksforGeeks.org"); 
        arrlistobj.add("2. Code.GeeksforGeeks.org"); 
        arrlistobj.remove(1);  // Remove value at index 2 
        System.out.println("ArrayList object output :" +  arrlistobj); 
  
        // Checking if an element is present. 
        if (arrlistobj.contains("2. Code.GeeksforGeeks.org")) 
            System.out.println("Found"); 
        else
            System.out.println("Not found"); 
  
  
        LinkedList llobj = new LinkedList(); 
        llobj.add("0. Practice.GeeksforGeeks.org"); 
        llobj.add("1. Quiz.GeeksforGeeks.org"); 
        llobj.add("2. Code.GeeksforGeeks.org"); 
        llobj.remove("1. Quiz.GeeksforGeeks.org"); 
        System.out.println("LinkedList object output :" + llobj); 
  
        // Checking if an element is present. 
        if (llobj.contains("2. Code.GeeksforGeeks.org")) 
            System.out.println("Found"); 
        else
            System.out.println("Not found"); 
    } 
} 


Output:
ArrayList object output :[0. Practice.GeeksforGeeks.org, 2. Code.GeeksforGeeks.org]
Found

LinkedList object output :[0. Practice.GeeksforGeeks.org, 2. Code.GeeksforGeeks.org]
Found
```

## PreSum（前缀和）和 Suffix sum（后缀和） 

**PreSum（前缀和）**适用题型：

    1. 区间求和（最经典）

    2. 子数组和是否满足某条件（=K、%K=0 等）

    3. 子数组的计数题（多少个满足条件的 subarray）

    4. 需要 O(1) 查询区间信息的题

    5. 2D 前缀和用于矩阵区域求和

**Suffix sum（后缀和）**适用题型：

    1. 从右往左计算区间信息: 需要在数组中寻找一个右侧元素 j，使得它尽可能“更优”（比如更小或者更大等）并且位置尽可能靠右。

    2. 需要快速求 “右侧区间和”

    3. 需要配合前缀和定位分割点（例如最大前后缀和差值最小）

区别：

| 项目   | PreSum（前缀和）                  | Suffix Sum（后缀和）                       |
| ---- | ---------------------------- | ------------------------------------- |
| 方向   | 从左往右累加                       | 从右往左累加                                |
| 常用任务 | 快速求区间 `[l, r]` 和             | 快速求区间 `[l, r]` 右侧或左侧的和                |
| 常见题目 | 子数组求和、统计题                    | 分割数组、从右侧约束的题                          |
| 应用   | subarray sums / counting     | right side constraints / partitioning |
| 常用公式 | sum(l,r) = pre[r] - pre[l-1] | sum(l,r) = suf[l] - suf[r+1]          |

### 前缀和 vs 后缀和：数学层面的区别

本质上：

PreSum 是 ∑ a[0..i], Suffix Sum 是 ∑ a[i..n-1]

两者可以相互转换：

    suf[i] = total_sum - pre[i-1]


### 1. ✦ PreSum 用在哪些题？

1. 普通区间求和题

- 例如：频繁查询数组一个区间的和

前缀和直接 O(1) 查询：

    pre[i] = a[0] + ... + a[i]
    sum(l, r) = pre[r] - pre[l-1]


2. 找满足条件的子数组（等于 K）

- 例如：有多少个子数组和 = K？

用哈希表统计前缀和频次：

    if pre[i] - pre[j] = K  → subarray j+1..i 满足要求


- 经典题：LeetCode 560 Subarray Sum Equals K



3. 找子数组和 % K == 0

- 例如：求有多少个 subarray，使得 sum % K == 0

    (pre[i] - pre[j]) % K == 0  → pre[i] % K == pre[j] % K


4. 子数组最大/最小问题（需要前缀技巧）

- 例如：找某个条件下的最短 subarray
- 找 subarray sum >= K
- 用单调队列 + 前缀和


5. 2D 前缀和（矩阵）

- 求：任意子矩阵的和 & 任意子矩阵的平均值

公式：sum = pre[x2][y2] - pre[x1-1][y2] - pre[x2][y1-1] + pre[x1-1][y1-1]


### 2. ✦ Suffix sum 用在哪些题？

1. 需要知道右边区间和的题

- 例：数组里的某位置 i 左边和右边和比较, 那就要从右往左看哪些元素满足条件

    > 构造：suf[i] = a[i] + suf[i+1]
\

2. 分割数组、找平衡点

- 例如：找一个 i，使得左侧和 == 右侧和或差值最小

    > 左侧：pre[i]
    > 右侧：suf[i+1]


3. 有右侧限制的 DP

例如：

需要知道后缀区间的某种累加值

比如区间 DP 里更新右端点的状态

④ 字符串、数组右端开始处理

例：

从右往左处理字符串时，需要累加信息

例如构造：

后缀 hash

后缀最大值、最小值

后缀频率统计


### 一、典型适用场景总结

1. 找最大区间差距 j − i 的题

- 要求 i < j 且满足某个单调条件。

- 例如：LeetCode 962 Maximum Width Ramp，构建后缀最小值（A[j] 最小 → 更容易满足 A[i] ≤ A[j]）

- 其他 Ramp 类题：给定数组，找最宽区间满足 A[i] ≤ A[j]
- 原因：若右侧 j 值越小越好 → 后缀最小值数组自然满足单调性。



2. 股票类题：在右边找更低价格 / 更高价格

- 例如：股票利润最大化（简单版不需要，但有些变种会用）

- 寻找：对每个 i 找右侧比 nums[i] 更有利的 j

    **若右边寻找 最小值 → 后缀最小值，若右边寻找 最大值 → 后缀最大值**


3. 从右找可比较值用于决策（DP+Greedy）

比如：给你一个数组，每个元素要根据右侧的某个最优值决策。

- 如果“右侧的代价” 越小越优 → suffix min

- 如果“右侧的收益” 越大越优 → suffix max

常见于：动态规划从右往左计算需要右侧最优值、数组/区间贪心优化



4. 寻找右侧最近满足更弱条件的元素

- 例如：不是找第一个满足条件的元素（那是 monotonic stack），而是找一个“最优”右端，且允许越往右越好。这样就自然使用 decreasing suffix array（根据值降序）。


### 二、出现下列信号时，你就应该想到它
| 信号                                 | 含义                      |
| -------------------------------     | ----------------------- |
| 1. 你要找 j > i 且 A[j] 尽可能“小/大”  | suffix min / max        |
| 2. j 是“右侧最优位置”，越靠右越好       | suffix array            |
| 3. j 的候选可以用一个单调结构维护        | decreasing suffix array |
| 4. 不需要 “最近” ，而是 “最优”          | suffix array（不是单调栈） |
| 5. 想要 O(n) two pointer + greedy    | 必然 suffix array         |


和monotonic stack的区别：

    如果是找第一个满足条件的 j 或者 Next Greater/Smaller Element，用单调stack


### 三、例子

📘 1. LeetCode 962 - Maximum Width Ramp

- 最经典使用 decreasing suffix array。


📘 2. LeetCode 1856 - Maximum Subarray Min-Product

- 虽然大部分答案用 monotonic stack，但也可以用：suffix min array + binary search 解（第二种方法中用到）


📘 3. LeetCode 121 / 122 (股票买卖) 的变体题

- 你要知道“从右侧开始的最低价格” 或 “从右侧开始的最高价格”， 并在动态规划中使用后缀最小值/最大值


📘 4. LeetCode 769/768 Partition Labels 型题的变体

- 有些数组分段必须根据：某段右边的最小值/最大值 来判断是否可以“切一刀”。这样的题很适合 suffix min/max。


📘 5. 两端夹逼类 Two-pointer + 单调右边候选

与 962 本质相同，例如：

求 j > i 且 nums[j] ≥ nums[i]*k 的最远 j
求 j > i 且 nums[j] ≤ nums[i] 的最大间隔

需要右边最小/最大候选的题

都可以利用后缀最优值数组（suffix optimal array）。

📘 6. 一些 Hard 级 DP

如：

DP[i] 依赖 “右侧的最小代价”

而右侧代价可以预处理为 suffix min

再用 two pointer/binary search 筛选右侧可行区间




