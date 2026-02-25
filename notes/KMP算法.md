KMP算法


**KMP 是一种在字符串匹配中，通过“失败指针（LPS / next 数组）避免重复比较”的算法，时间复杂度 O(n + m)**

https://cloud.tencent.com/developer/article/1511699

【最浅显易懂的 KMP 算法讲解】 https://www.bilibili.com/video/BV1AY4y157yL/?share_source=copy_web&vd_source=6b0c1acb5c1955061f4fe02cfa906ac4


# 一、KMP 解决什么问题？

在字符串 text 中，找 pattern 第一次出现的位置

暴力做法的问题
text:    ababcabcabababd
pattern:         ababd


匹配失败时，指针回退很多次 👉 最坏 _O(n*m)_


## KMP 的核心思想

🔑 关键：不回退 text 指针, 利用 pattern 自身的结构

当匹配失败时 pattern 跳到一个“已经匹配过的位置”


# 二、状态机 & DP 定义

我们可以认为pat的匹配就是状态的转移。要确定状态转移的行为，得明确两个变量，一个是当前的匹配状态，另一个是遇到的字符


```java
dp[j][c] = next
	0 <= j < M，代表当前的状态
	0 <= c < 256，代表遇到的字符（ASCII 码）
	0 <= next <= M，代表下一个状态

dp[4]['A'] = 3 表示：当前是状态 4，如果遇到字符 A，pat 应该转移到状态 3

dp[1]['B'] = 2 表示：当前是状态 1，如果遇到字符 B，pat 应该转移到状态 2

```

Search 函数： 根据状态机匹配就行

```java
	public int search(String txt) {
	    int M = pat.length();
	    int N = txt.length();
	    // pat 的初始态为 0
	    int j = 0;
	    for (int i = 0; i < N; i++) {
	        // 当前是状态 j，遇到字符 txt[i]，pat 应该转移到哪个状态？assign新状态to j
	        j = dp[j][txt.charAt(i)];

	        // 如果达到终止态，返回匹配开头的索引
	        if (j == M) return i - M + 1;
	    }
	    // 没到达终止态，匹配失败
	    return -1;
	}

```

# 三、构建状态转移图

```java
for 0 <= j < M: # 状态
    for 0 <= c < 256: # 字符
        dp[j][c] = next

```

这个 next 状态应该怎么求呢？

- 如果遇到的字符c和pat[j]匹配的话，状态就应该向前推进一个，也就是说next = j + 1，称这种情况为状态推进
- 如果遇到的字符c和pat[j]不匹配的话，状态就要回退（或者原地不动），称这种情况为状态重启


再定义一个名字：影子状态，用变量X表示。所谓影子状态，就是和当前状态具有相同的前缀

```java
   A     B     A    B      C
0 --> 1 --> 2 --> 3 --> 4 --> 5
            X           j
```

当前状态j = 4，其影子状态为X = 2，它们都有相同的前缀 "AB"。 

因为状态X和状态j存在相同的前缀，所以当状态j准备进行状态重启的时候（遇到的字符c和pat[j]不匹配），可以通过X的状态转移图来获得最近的重启位置。

比如说刚才的情况，如果状态j遇到一个字符 "A"，应该转移到哪里呢？首先状态 4 只有遇到 "C" 才能推进状态，遇到 "A" 显然只能进行状态重启。状态j会把这个字符委托给状态X处理，也就是dp[j]['A'] = dp[X]['A']：

```java
int X # 影子状态
for 0 <= j < M:
    for 0 <= c < 256:
        if c == pat[j]:
            # 状态推进
            dp[j][c] = j + 1
        else: 
            # 状态重启
            # 委托 X 计算重启位置
            dp[j][c] = dp[X][c] 
```

# 四、代码实现

```java
	public class KMP {
	    private int[][] dp;
	    private String pat;

	    public KMP(String pat) {
	        this.pat = pat;
	        int M = pat.length();

	        // dp[状态][字符] = 下个状态
	        dp = new int[M][256];

	        // base case 只有遇到 pat[0] 这个字符才能使状态从 0 转移到 1，遇到其它字符的话还是停留在状态 0（Java 默认初始化数组全为 0）。

	        dp[0][pat.charAt(0)] = 1;

	        // 影子状态 X 初始为 0
	        int X = 0;

	        // 当前状态 j 从 1 开始
	        for (int j = 1; j < M; j++) {
	            for (int c = 0; c < 256; c++) {
	                if (pat.charAt(j) == c) 
	                    dp[j][c] = j + 1;
	                else 
	                    dp[j][c] = dp[X][c];
	            }

	            // 更新影子状态， 当前是状态 X，遇到字符 pat[j]，pat 应该转移到哪个状态？
	            X = dp[X][pat.charAt(j)];
	        }
	    }

	    public int search(String txt) {
	        int M = pat.length();
	        int N = txt.length();

	        // pat 的初始态为 0
	        int j = 0;

	        for (int i = 0; i < N; i++) {
	            // 计算 pat 的下一个状态
	            j = dp[j][txt.charAt(i)];

	            // 到达终止态，返回结果
	            if (j == M) return i - M + 1;
	        }
	        // 没到达终止态，匹配失败
	        return -1;
	    }

}

```


# 五、从状态机实现进化到LPS数组实现

LPS 本质上是 DP 自动机里“影子状态 X 的一维压缩版”

- DP 写法是 “显式构造状态机”
- LPS 写法是 “只保留失败跳转的信息”

## LPS的定义： 寻找“最长公共前后缀”

LPS [i] 代表 **pattern中可以‘跳过匹配’的字符个数**，也就是子串 pattern[0..i] 的「最长相等前后缀长度」

- 在模式串 P 的前 i 个字符构成的子串中，最长的相等的前缀和后缀的长度。

  - 前缀：包含第一个字符，但不包含最后一个字符的连续子串。
  - 后缀：包含最后一个字符，但不包含第一个字符的连续子串。

例子：模式串 ABAB
- A：   无前后缀，长度 0。
- AB：  前缀A，后缀B。不相等，长度 0。
- ABA： 前缀A, AB，后缀A, BA。相等的只有 A，长度 1。当匹配ABAC的时候，可以跳到
- ABAB：前缀A, AB, ABA，后缀B, AB, BAB。相等的只有 AB，长度 2。
- LPS 数组就是 [0, 0, 1, 2]。


KMP 构建的思想：
- 既然后缀和前缀相等，那么后缀我已经匹配过了，就相当于前缀我也已经匹配过了。我不必从头开始，直接跳到前缀的下一个位置继续比就行。


## Build 过程的代码逻辑：动态规划的思想

- 构建 next/LPS 数组本身也是一个类似“自己匹配自己”的过程

- i（当前后缀末尾）和 j（当前前缀末尾，也代表长度）。

```java
public int[] buildNext(String pattern) {
    int n = pattern.length();
    int[] next = new int[n];
    int j = 0; // j 代表当前最长公共前后缀的长度，也就是状态机DP中的 影子状态

    for (int i = 1; i < n; i++) {
        // 【关键点 1】如果不匹配，j 就要回退（回退到之前匹配过的位置）
        while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
            j = next[j - 1];  //等同于 状态机DP中的 X 回退
        }

        // 【关键点 2】如果匹配，长度加 1
        if (pattern.charAt(i) == pattern.charAt(j)) {
            j++;
        }

        // 【关键点 3】记录当前位置的最长公共前后缀长度
        next[i] = j;
    }
    return next;
}
```


为什么不匹配时 j = next[j-1]？
- 如果 pattern[i] 和 pattern[j] 不配了，我们不需要把 j 直接归零，因为 j 之前的那些字符已经匹配过了。
- 我们去看看“在那一段已经匹配好的前缀里，还有没有更短的一点前后缀是可以复用的？”
- 这个动作就像是在问：“我已经确认了 ABAB 匹配，现在第 5 位坏了，我能不能直接退而求其次，利用 AB 这一段重新开始？”


## 理解 X ≈ LPS[j-1]

dp[状态][字符] = 下一个状态, 初始化：dp = new int[M][256];

状态是什么意思？
- 状态 j = 当前已经匹配了 pat[0..j-1]


X 是什么？
- “如果当前状态 j 失败了，我该跳到哪个状态？”

- 也就是：X = 当前前缀的「最长可复用前缀」对应的状态 = 当前 j 之前子串的 LPS 值


LPS 的定义
- lps[j] = pat[0..j] 的最长相等前后缀长度
- X == lps[j - 1]



## 完整代码

next[i] 代表 pattern中可以‘跳过匹配’的字符个数，也就是子串 pattern[0..i] 的「最长相等前后缀长度」

- 比如： ABABC，对应的next = 【0， 0， 1， 2， 0】
- next【3】=2 代表可以跳过前两个字符，也就是最开头的AB，直接从第二个A开始比较

变量 j 始终表示：当前已经匹配好的前缀长度， 


```java
	/**
     * KMP 主查询函数
     */
    public static int kmpSearch(String text, String pattern) {
        if (pattern.isEmpty()) return 0;

        // 第一步：构建 next 数组（预处理模式串）
        int[] next = buildNext(pattern);

        int j = 0; // 模式串的指针

        // i是text的指针，i绝对不后退
        for (int i = 0; i < text.length(); i++) {
            
            // 第二步：匹配失败时，通过 next 数组跳过不必要的比较
            // 如果不匹配，j 回退到上一个公共前后缀的末尾
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }

            // 如果当前字符匹配成功，模式串指针后移
            if (text.charAt(i) == pattern.charAt(j)) {
                j++;
            }

            // 检查是否完全匹配
            if (j == pattern.length()) {
                return i - pattern.length() + 1; // 返回匹配的起始索引
            }
        }
        return -1;
    }

    /**
     * 构建 next 数组的核心逻辑
     * 理解点：寻找每个子串 [0...i] 的“最长相等前后缀长度”
     */
    private static int[] buildNext(String pattern) {
        int n = pattern.length();
        int[] next = new int[n];
        int j = 0; // j 代表当前最长公共前后缀的长度 

        // 下标 i 从 1 开始，因为单个字符没有前后缀
        for (int i = 1; i < n; i++) {
            // 如果不匹配，j 回退到之前的对称位置
            // 这是 KMP 最精妙的地方，利用已知的 next 结果递归回退
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }

            // 如果字符相等，说明最长前后缀长度增加了
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }

            // 记录当前位置的长度
            next[i] = j;
        }
        return next;
    }

```
示例

```java
pattern = "a b a b a c"
index      0 1 2 3 4 5
next:      0 0 1 2 3 0
```

🟢i = 0（跳过）
- 单字符没有前后缀，next[0] = 0（默认）


🟢 i = 1
- 子串: "ab"
- pattern[i] = 'b', pattern[j] = 'a'
- ❌ 不相等, 此时j == 0，不能回退
- next[1] = 0; j = 0


🟢 i = 2
- 子串: "aba"
- pattern[i] = 'a', pattern[j] = 'a'
- ✅ 相等！代表 前缀 "a" == 后缀 "a"
- j++ --> j = 1
- next[2] = 1;


🟢 i = 3
- 子串: "abab"
- pattern[i] = 'b', pattern[j] = 'b'
- ✅ 相等！前缀 "ab" == 后缀 "ab"
- j++;  --> j = 2
- next[3] = 2;


🟢 i = 4
- 子串: "ababa"
- pattern[i] = 'a', pattern[j] = 'a'
- ✅ 相等！前缀 "aba" == 后缀 "aba"
- j++;  // j = 3
- next[4] = 3;

🔴 i = 5（KMP 精华）
- 子串: "ababac"
- pattern[i] = 'c', pattern[j] = 'b'
- ❌ 不相等，进入 while：

```java
	while (j > 0 && pattern[i] != pattern[j]) {
	    j = next[j - 1];
	}
```
  - 第一次回退：此时j = 3 → next[2] = 1， 后退之后j=1

- 再比一次：pattern[i] = 'c'， pattern[j] = 'b' ❌

  - 第二次回退：j = 1 → next[0] = 0


- 现在：j == 0， 不能再回退
- next[5] = 0;



# 字符串的特点
在 KMP 算法的预处理阶段（构建 next 数组/前缀函数），我们关注的是 “最长相等真前后缀”。这种具有相同前后缀的字符串

以下是这类字符串的 5 大核心特点：

## 1. 周期性（Periodicity）
- 字符串的前后缀重叠情况直接决定了它的 周期（Period）
- 规律：如果字符串 S 的长度为 n，它最长的相等前后缀长度为 L，那么 n-L 就是 S 的最小周期长度。
- 例子：字符串 ABCABCABC，长度 n=9。
  - 最长相等前后缀是 ABCABC，长度 L=6
  - 最小周期长度为 9-6=3。即 ABC 是它的循环节。

- 推论：如果 n 能被 n-L 整除，说明该字符串是由某个子串完全循环构成的（如 ABABAB）
- ex. leetcode 459

## 2. 结构的递归性（Recursive Structure）
- KMP 构建 next 数组时利用的最核心特点是：Border 的 Border 还是 Border
- 规律：如果 S 有一个长度为 k 的相等前后缀，而这个前缀里本身又有一个长度为 m 的相等前后缀，那么 m 也是原字符串 S 的一个相等前后缀长度。

- 应用：这就是为什么在 next[i] 匹配失败时，我们可以通过 j = next[j-1] 不断回退寻找更短的匹配。这说明字符串的内部嵌套了多层对称结构

## 3. “不相交”与“重叠”的临界点
- 根据 L 与 n 的关系，字符串表现出不同的物理形态：
  1) L < n/2（不相交）：前后缀之间有间隔。例如 AB...AB。
  2) L = n/2（恰好相连）：例如 ABCABC
  3) L > n/2（重叠）：前后缀之间存在交叉重叠部分。

- 规律：重叠的部分本身也是该字符串的一个 Border。重叠程度越高，字符串的重复性越强，覆盖（Cover）整个字符串所需的子串就越短。

## 4. 覆盖性（Covering）
- 在字符串研究中，如果一个子串 T 既是 S 的前缀又是后缀，且 S 可以由多个 T 互相重叠地覆盖而成，那么 T 被称为 S 的一个 Cover
- 特点：相等的前后缀是寻找“最小覆盖子串”的唯一线索。所有的 Cover 必然是 S 的 Border，但并不是所有的 Border 都能成为 Cover

## 5. 在 KMP 匹配中的“跳跃”动力
- 这种相同的前后缀在物理上提供了一种“平移不变性”：
  - 规律：当你匹配到 L 长度的后缀时，本质上你已经完成了 L 长度前缀的匹配。
  - 特点：它允许模式串在失配时，利用已知的信息（后缀即前缀）直接向后跳跃 n-L 位，而不需要回溯主串指针

## 总结：在 2026 年算法面试中的“一眼看穿”技巧
当你看到一个字符串符合 next[n-1] > 0 时，你应该立刻联想到：

1) 压缩性：这个字符串可能有更短的表达形式（周期）
2) 对称性：头尾逻辑是一致的
3) 自相似性：可以通过 next 链条不断拆解出更小的相似子结构





# 四、KMP 两个阶段

1️⃣ 构建 LPS 数组（预处理 pattern）

时间：O(m)

2️⃣ 利用 LPS 做匹配

时间：O(n)


# 例题

| 题号   | 题名                      | 用法     |
| ---- | -------------------------- | ------ |
| 28   | Find the Index             | 直接     |
| 459  | Repeated Substring Pattern | 利用 LPS |
| 214  | Shortest Palindrome        | KMP 拼接 |
| 686  | Repeated String Match      | KMP    |
| 1392 | Longest Happy Prefix       | 本质 LPS |
| 1668 | Max Repeating Substring    | KMP 优化 |



