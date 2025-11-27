KMP算法 （重复字符串匹配）

labuladong+的算法小抄（零一二）.pdf @Page 159

时间复杂度：O(n + m)
空间复杂度：O(m)

KMP 本质是一个 自动状态机（FSM finite state machine）：字母流不断输入。如果失败，就跳转到已知的安全状态继续匹配


1. 暴力解法
	比如匹配：

	text:    ABCDABCDABEE
	pattern: ABCDABE

	暴力匹配每次不匹配，都要从头重新开始对齐：

	ABCDABCDABEE
	ABCDABE
	      ^ mismatch: back to pattern[0] again

	所以最坏 O(n * m)

	KMP 就是为了解决：遇到不匹配时，如何不回退 text 指针？

	最终它把复杂度做到：O(n + m)

2. KMP 的思想（最重要）

	一句话概括：

	当 pattern[ i ] != text[ j ] 时，我们通过 prefix table 找到 pattern 中可以直接继续匹配的位置，而不用从头来。

	也就是说不用每次都回头匹配！

3. 核心结构：前缀表（Prefix Table / LPS 数组）

	LPS = longest proper prefix that is also suffix

	举例 pattern:

	A B C D A B E

	LPS 数组:

	A B C D A B E
	0 0 0 0 1 2 0

	意思是：

	到 pattern[4] = 'A' 时，最长前后缀长度为 1 → "A"

	到 pattern[5] 时最长是 2 → "AB"

4. 匹配流程（最精华）

	匹配过程中只做两件事：

	case1: 匹配成功 i++, j++
	case2: 不匹配：

	我们不回退 i，而是：j = LPS[j-1]


	只移动 pattern 的指针！

	这就是 KMP 的魔力所在。


	为了描述状态转移图，我们定义一个二维 dp 数组，它的含义如下:
	dp[j][c] = next
	0 <= j < M，代表当前的状态
	0 <= c < 256，代表遇到的字符(ASCII 码) 
	0 <= next <= M，代表下⼀个状态
	dp[4]['A'] = 3 表示: 当前是状态 4，如果遇到字符 A， pat 应该转移到状态 3
	dp[1]['B'] = 2 表示: 当前是状态 1，如果遇到字符 B， pat 应该转移到状态 2



	如果字符 c 和 pat[j] 不不匹配的话，状态就要回退(或者原地不动)，我们不妨称这种情况为状态重启:
	我们再定义⼀个名字:影子状态，⽤变量 X 表示。所谓影子状态，就是和当前状态具有相同的前缀。
	它永远比当前状态 j 落后⼀个状态，拥有和 j 最⻓的相同前缀
	也就是 dp[j] ['A'] = dp[X]['A'] :

	int X //影子状态 
	for 0 <= j < M:
	    for 0 <= c < 256:
	        if c == pat[j]:
				//状态推进
	            dp[j][c] = j + 1
	        else:
				// 状态重启
				// 委托 X 计算重启位置 
				dp[j][c] = dp[X][c]


5. KMP 在工程中的真实应用

	日志搜索

	模式匹配

	网络协议报文解析

	正则表达式基础实现

	字符分词

	搜索引擎分词

	做百万级字符串匹配时 KMP 优于暴力太多。


public class KMP {
    private int[][] dp;
    private String pat;

 	// 通过 pat 构建 FSM, 也就是dp 数组, 需要 O(M) 时间    
    public KMP(String pat) {
        this.pat = pat;
		int M = pat.length();

		// dp[状态][字符] = 下个状态
		dp = new int[M][256];

		// base case, 只有遇到 pat[0] 这个字符才能使状态从 0 转移到 1，其它字符都还是停留在状态 0
		// (Java 默认初始化数组全为 0
		dp[0][pat.charAt(0)] = 1;

		// 影⼦状态 X 初始为 0
		int X = 0;

		// 当前状态 j 从 1 开始
		for (int j = 1; j < M; j++) {
            for (int c = 0; c < 256; c++) {
                if (pat.charAt(j) == c)
                    dp[j][c] = j + 1;    //状态推进
                else
                    dp[j][c] = dp[X][c]; //状态重启， 委托 X 计算重启位置 
			}		
			// 更新影子状态,因为x永远在j/current前面被算出，所以可以得到这个值
            X = dp[X][pat.charAt(j)];
        }
	}

	//// 需要 O(N) 时间,借助 dp 数组去匹配 txt
	public int search(String txt) { 
	
		int M = pat.length();
		int N = txt.length();
		// pat 的初始态为 0
		int j = 0;
	    for (int i = 0; i < N; i++) {
			// 当前是状态 j，遇到字符 txt[i]， 问问dp：pat 应该转移到哪个状态?
			j = dp[j][txt.charAt(i)];

			// 如果达到终⽌止态，返回匹配开头的索引 
			if (j == M) return i - M + 1;
		}
		// 没到达终⽌止态，匹配失败
		return -1; 
	}
 
}


例子：pattern = ’ababc‘
✔ 第 1 轮 j = 1：字符是 'b'

	遍历所有字符 c：

	如果 c == 'b' → dp[1]['b'] = 2

	其他字符 → dp[1][c] = dp[X=0][c]

	而 dp[0][c] 只有 dp[0]['a']=1，其它都是0。

	所以：

	更新前后 dp 状态变化：
	dp[1]['b'] = 2
	dp[1]['a'] = dp[0]['a'] = 1
	其他 dp[1][*] = 0

	更新 shadow X：
	X = dp[X][pat[1]]
	  = dp[0]['b']
	  = 0


	(因为 dp[0]['b'] 默认是 0)

✔ 第 2 轮 j = 2：字符是 'a'

	当 c='a'：

	dp[2]['a'] = j+1 = 3


	否则：

	dp[2][c] = dp[X=0][c]


	dp[0]['a']=1，其它为0，所以：

	dp[2]['a'] = 3
	dp[2]['b'] = dp[0]['b'] = 0
	dp[2][other] = 0


	更新 X：

	X = dp[0]['a'] = 1

✔ 第 3 轮 j = 3：字符 'b'

	当 c='b'：

	dp[3]['b'] = 4


	否则：

	dp[3][c] = dp[X=1][c]


	那我们要看 dp[1]:

	dp[1]['a'] = 1
	dp[1]['b'] = 2


	所以：

	dp[3]['b'] = 4
	dp[3]['a'] = dp[1]['a'] = 1
	dp[3][others] = 0


	更新 X：

	X = dp[X=1]['b'] = 2

✔ 第 4 轮 j = 4：字符 'c'

	当 c='c':

	dp[4]['c'] = 5


	否则：

	dp[4][c] = dp[X=2][c]


	dp[2] 我们之前算过：

	dp[2]['a'] = 3
	dp[2]['b'] = 0


	所以：

	dp[4]['c'] = 5
	dp[4]['a'] = dp[2]['a'] = 3
	dp[4]['b'] = dp[2]['b'] = 0
	其他=0


	更新 X：

	X = dp[2]['c'] = 0


⭐ 最终 shadow state 的变化过程
	j	pat[j]	X更新后值
	0	'a'		X=0（初始化）
	1	'b'		X=0
	2	'a'		X=1
	3	'b'		X=2
	4	'c'		X=0

⭐ 最终的关键 dp 转移表（只看重要字符）

我们仅列出 a、b、c 对应列：

state	on 'a'	on 'b'	on 'c'
dp[0]	1		0		0
dp[1]	1		2		0
dp[2]	3		0		0
dp[3]	1		4		0
dp[4]	3		0		5

