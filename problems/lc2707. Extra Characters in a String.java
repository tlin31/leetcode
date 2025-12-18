2707. Extra Characters in a String - Medium

You are given a 0-indexed string s and a dictionary of words dictionary. You have to break s into one or more non-overlapping substrings such that each substring is present in dictionary. There may be some extra characters in s which are not present in any of the substrings.

Return the minimum number of extra characters left over if you break up s optimally.

 

Example 1:

Input: s = "leetscode", dictionary = ["leet","code","leetcode"]
Output: 1
Explanation: We can break s in two substrings: "leet" from index 0 to 3 and "code" from index 5 to 8. There is only 1 unused character (at index 4), so we return 1.

Example 2:

Input: s = "sayhelloworld", dictionary = ["hello","world"]
Output: 3
Explanation: We can break s in two substrings: "hello" from index 3 to 7 and "world" from index 8 to 12. The characters at indices 0, 1, 2 are not used in any substring and thus are considered as extra characters. Hence, we return 3.
 

Constraints:

1 <= s.length <= 50
1 <= dictionary.length <= 50
1 <= dictionary[i].length <= 50
dictionary[i] and s consists of only lowercase English letters
dictionary contains distinct words


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

DP 定义（不变）
	dp[i] = s[0..i-1] 最少 extra 字符数

转移方式（关键变化）

在位置 i：

1️、 默认：把 s[i] 当作 extra

	dp[i + 1] = min(dp[i + 1], dp[i] + 1)


2️、Trie 向后匹配：

	从 i 开始

	沿 Trie 匹配 s[i..j]

	若是一个单词结尾：

	dp[j + 1] = min(dp[j + 1], dp[i])


原始 DP 的问题

	dict.contains(s.substring(j, i))

	substring 本身是 O(len)

	Hash 计算 + 创建新字符串

	在 n² 次循环里非常慢

	👉 Trie 可以边走边匹配，不创建字符串


Stats:

时间复杂度

	Trie 构建：O(totalDictChars)

	DP 匹配：最坏 O(n²)
	但：

	不创建 substring

	常数因子小很多

	👉 实践中明显快于 HashSet 版

空间复杂度

	Trie：O(totalDictChars)

	DP：O(n)


class Solution {

    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord;
    }

    TrieNode root = new TrieNode();

    public int minExtraChar(String s, String[] dictionary) {
        // build trie
        for (String word : dictionary) {
            insert(word);
        }

        int n = s.length();
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 0; i < n; i++) {
            if (dp[i] == Integer.MAX_VALUE) continue;

            // case 1: treat s[i] as extra
            dp[i + 1] = Math.min(dp[i + 1], dp[i] + 1);

            // case 2: trie match
            TrieNode cur = root;
            for (int j = i; j < n; j++) {
                int idx = s.charAt(j) - 'a';
                if (cur.children[idx] == null) break;

                cur = cur.children[idx];
                if (cur.isWord) {
                    dp[j + 1] = Math.min(dp[j + 1], dp[i]);
                }
            }
        }

        return dp[n];
    }

    private void insert(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (cur.children[idx] == null)
                cur.children[idx] = new TrieNode();
            cur = cur.children[idx];
        }
        cur.isWord = true;
    }
}







class Solution {
    
    TrieNode root;

    class TrieNode {
        Map<Character, TrieNode> children = new HashMap();
        boolean isWord = false;

    }

    private TrieNode buildTree(String[] dict){
        TrieNode root = new TrieNode();
        for(String word:dict){
            for(char c: word.toCharArray()){
                root.children.putIfAbsent(c, new TrieNode());
                root = root.children.get(c);
            }
            root.isWord = true;

        }
        return root;
    }

    public int minExtraChar(String s, String[] dictionary) {
        int n = s.length();
        int[] dp = new int[n+1]; 
        root = buildTree(dictionary);
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for(int i = 0; i<n;i++){
            if (dp[i] == Integer.MAX_VALUE) continue;
            dp[i+1] = Math.min(dp[i+1],1+dp[i]);
            TrieNode cur = root;

            for(int j = i; j<n;j++){
                // search in trie
                if (!cur.children.containsKey(s.charAt(j))) {
                    break;
                }
                cur = cur.children.get(s.charAt(j));
                if(cur.isWord){
                    dp[j+1] = Math.min(dp[j+1], dp[i]);
                }
            }
        }
        return dp[n];
    }
}

