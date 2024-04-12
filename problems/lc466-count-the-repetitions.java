466. Count The Repetitions - Hard

Define S = [s,n] as the string S which consists of n connected strings s. For example, ["abc", 3] 
="abcabcabc".

On the other hand, we define that string s1 can be obtained from string s2 if we can remove some 
characters from s2 such that it becomes s1. For example, “abc” can be obtained from “abdbec” based 
on our definition, but it can not be obtained from “acbbe”.

You are given two non-empty strings s1 and s2 (each at most 100 characters long) and two integers 
0 ≤ n1 ≤ 106 and 1 ≤ n2 ≤ 106. Now consider the strings S1 and S2, where S1=[s1,n1] and S2=[s2,n2]. 
Find the maximum integer M such that [S2,M] can be obtained from S1.

Example:

Input:
s1="acb", n1=4
s2="ab", n2=2

Return:
2


******************************************************
key:
	- want longest s2 from s1
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	如果 s2 在 S1 中出现了N次，那么 S2 肯定在 S1 中出现了 N/n2次, (大写表示字符串加上重复次数组成的大字符串)
	 	所以可以得出结论，只要算出 s2 出现的次数，然后除以 n2，就可以得出 S2 出现的次数了。

	- 那么问题就是表示重复，遍历 s1 字符串 n1 次，表示每个 s1 字符串为一段，对于每段，可以得知：

		1. 出现在该段的 s2 字符串的累计出现次数

		2. 一个 nextIndex，其中 s2[nextIndex] 表示在下一段 s1 中你所要寻找的 s2 中的一个字符。
		  (比如说 s1="abc", s2="bac", 由于第一个 s1 中只能匹配上 s2 中的b，那么只有在下一段 s1 中才能继续匹配 
		  s2 中的a，所以 nextIndex=1，即 a 在 s2 中的位置为1；同理，比如  s1="abca", s2="bac"，第一个 s1 可以匹配上 s2 中的 ba，那么后面的c只能在下一段 s1 中匹配上，那么 nextIndex=2，即c在 s2 中的位置为2)
	- ex. 
		Input:
		s1="abacb", n1=6
		s2="bcaa", n2=1

		Return:
		3
 

		j --------------->  1 2    3 0 1      2    3 0 1      2    3 0   
		S1 --------------> abacb | abacb | abacb | abacb | abacb | abacb 

		repeatCount ----->    0  |   1   |   1   |   2   |   2   |   3

		nextIndex ------->    2  |   1   |   2   |   1   |   2   |   1
		 

	- nextIndex 的范围从 0 到 s2.size()-1，根据Pigeonhole theory，在遍历 s1 段 s2.size()+1 次之后, 
	  一定会找到相同的两个 nextIndex 。在上面的例子中，重复的 nextIndex 出现在第三段，和第一段一样都为2，那么重复
	  的 pattern 就找到了，是第二段和第三段中的 aabc，而且从第四段开始，每两段就有一个 aabc，现在的目标就是统计出整个
	  S1 中有多少个s2。

	  由于 pattern 占用了两段，所以 interval 为2，然后看整个 S1 中有多少个这样的两段，
	  		repeat = (n1 - start) / interval

	  start 表示 pattern 的起始段数，之前的不是 pattern，然后算在整个 S1 中有多少个 pattern 出现，
	  		patternCnt = (repeatCnt[k] - repeatCnt[start]) * repeat，

	  注意这里的 repeatCnt[k] - repeatCnt[start] 表示一个 pattern 中有多少个字符串 s2，个人感觉一般来说都是1个。

	  然后算出剩下的非 pattern 的字符串里能包含几个 s2，
	  		remainCnt = repeatCnt[start + (n1 - start) % interval]，

	  然后把 patternCnt + remainCnt 之和算出来除以 n2 就是需要的结果啦。

	  如果 pattern 未曾出现，那么我们直接用 repeatCnt[n1] / n2 也能得到正确的结果：
	-	

Stats:

	- 
	- 


class Solution {
    int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        int[] repeatCnt = new int[n1 + 1];
        int[] nextIdx = new int[n1 + 1];
        int j = 0, cnt = 0;
        for (int k = 1; k <= n1; ++k) {
            for (int i = 0; i < s1.length(); ++i) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    ++j;
                    if (j == s2.length()) {  
                        j = 0;
                        ++cnt;
                    }
                }
            }
            repeatCnt[k] = cnt;
            nextIdx[k] = j;
            for (int start = 0; start < k; ++start) {
                if (nextIdx[start] == j) {
                    int interval = k - start;
                    int repeat = (n1 - start) / interval;
                    int patternCnt = (repeatCnt[k] - repeatCnt[start]) * repeat;
                    int remainCnt = repeatCnt[start + (n1 - start) % interval];
                    return (patternCnt + remainCnt) / n2;
                }
            }
        }
        return repeatCnt[n1] / n2;
    }
}

=======================================================================================================
method 2:

Method:

	-	optimize
	-	


Stats:

	- Runtime: 1 ms, faster than 93.60% of Java online submissions for Count The Repetitions.
	- Memory Usage: 37.2 MB, less than 100.00%


	public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        if (s1 == null || s2 == null || n1 <= 0 || n2 <= 0) {
            return 0;
        }
        HashMap<Integer, Integer> posMap = new HashMap<Integer, Integer>(); // key: the rest position of s2  value:the number of s1
        int[] repTimes = new int[102]; // repTimes[i]: nummer of used s1 is i, repetitions times is repTimes[i]
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();
        int len1 = s1.length();
        int len2 = s2.length();
        int s1Num = 1;
        int posInS2 = 0;
        int times = 0;
        while (s1Num <= n1) {
            for (int j = 0; j < len1; j++) {
                if (chars1[j] == chars2[posInS2]) {
                    posInS2++;
                    if (posInS2 == len2) {
                        times++;
                        posInS2 = 0;
                    }
                }
            }
            repTimes[s1Num] = times;
            if (posMap.containsKey(posInS2)) {
                break;
            }
            posMap.put(posInS2, s1Num);
            s1Num++;
        }
        if (s1Num >= n1) {
            return times / n2;
        }
        int k = posMap.get(posInS2);
        int s1NumInLoop = s1Num - k; // s1 num in each loop
        int s2NumInLoop = repTimes[s1Num] - repTimes[k]; // s2 num in each loop
        int repeatCount = (n1 - k) / s1NumInLoop;
        int n = repeatCount * s2NumInLoop;
        n += repTimes[k + (n1 - k) % s1NumInLoop];
        return n / n2;
    }

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



