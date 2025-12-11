318. Maximum Product of Word Lengths - Medium

Example 1:

Input: words = ["abcw","baz","foo","bar","xtfn","abcdef"]
Output: 16
Explanation: The two words can be "abcw", "xtfn".
Example 2:

Input: words = ["a","ab","abc","d","cd","bcd","abcd"]
Output: 4
Explanation: The two words can be "ab", "cd".
Example 3:

Input: words = ["a","aa","aaa","aaaa"]
Output: 0
Explanation: No such pair of words.
 

Constraints:

2 <= words.length <= 1000
1 <= words[i].length <= 1000
words[i] consists only of lowercase English letters.


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:bitmask


判断两个单词有没有公共字母最简单的方法：用 bitmask 表示每个单词的字符组成（共 26 位）

例如：

"abc" = 000...0111
"bd" = 000...1010

两个单词无公共字母 等同于做 位运算：

	(mask[i] & mask[j]) == 0


比 O(N² * wordLength) 更快。

📌步骤

每个单词计算 mask：

mask |= 1 << (word.charAt(k) - 'a')


两层循环检查所有 pair：

if ((mask[i] & mask[j]) == 0)
    ans = max(ans, len[i] * len[j])


Stats:

	✔时间复杂度

	构造 mask：O(n * L)

	检查 pair：O(n²)	

class Solution {
    public int maxProduct(String[] words) {
        int n = words.length;
        int[] mask = new int[n];
        int[] len = new int[n];

        for (int i = 0; i < n; i++) {
            int m = 0;
            for (char c : words[i].toCharArray()) {
                m |= 1 << (c - 'a');
            }
            mask[i] = m;
            len[i] = words[i].length();
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if ((mask[i] & mask[j]) == 0) {
                    ans = Math.max(ans, len[i] * len[j]);
                }
            }
        }

        return ans;
    }
}

可以先每个单词内部字母排序，用map存起来，Map<mask, maxLen> 因为 abc acb cab 。。。。都是一样的

class Solution {
  public int bitNumber(char ch){
    return (int)ch - (int)'a';
  }

  public int maxProduct(String[] words) {
    Map<Integer, Integer> hashmap = new HashMap();

    int bitmask = 0, bitNum = 0;
    for (String word : words) {
      bitmask = 0;
      for (char ch : word.toCharArray()) {
        // add bit number bitNumber in bitmask
        bitmask |= 1 << bitNumber(ch);
      }
      // there could be different words with the same bitmask
      // ex. ab and aabb
      hashmap.put(bitmask, Math.max(hashmap.getOrDefault(bitmask, 0), word.length()));
    }

    int maxProd = 0;
    for (int x : hashmap.keySet())
      for (int y : hashmap.keySet())
        if ((x & y) == 0) maxProd = Math.max(maxProd, hashmap.get(x) * hashmap.get(y));

    return maxProd;
  }
}


🧱工业落地场景（很真实）

	搜索词去重

	推荐系统去噪音

	广告系统关键词排重

	分布式搜索索引加速

	NLP embedding hashing

尤其是 bitmask 技术是产业级套路，例如：

	bloom filter

	bitset filtering

	inverted index

