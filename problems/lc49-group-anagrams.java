49. Group Anagrams - Medium

Given an array of strings, group anagrams together.

Example:

Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
Output:
[
  ["ate","eat","tea"],
  ["nat","tan"],
  ["bat"]
]
Note:

All inputs will be in lowercase.
The order of your output does not matter.

******************************************************
key:
	- hash, put each char in a map, if the value equals, put it into the set
	- ????? ArrayList<List<String>>(hash.values())

	- edge case:
		1) empty array, then return empty array
		2)

******************************************************

=======================================================================================================
method 1:

method:

	- use hashmap
	- String.valueOf(char array)!!! 
	- 

stats:

	- Runtime: 17 ms, faster than 22.72% of Java online submissions for Group Anagrams.
	- Memory Usage: 41.5 MB, less than 94.74%
	- 时间复杂度：排序的话算作 O（K log（K））,最外层的 for 循环，所以就是 O（n K log（K））。
	- 空间复杂度：O（NK），用来存储结果。

	public List<List<String>> groupAnagrams(String[] strs) {
		if (strs == null || strs.length == 0) 
			return new ArrayList<List<String>>();

		Map<String, List<String>> map = new HashMap<String, List<String>>();

		Arrays.sort(strs);

		for (String s : strs) {
			char[] ca = s.toCharArray();
			Arrays.sort(ca);

			//!!! string.valueof
			String keyStr = String.valueOf(ca);

			if (!map.containsKey(keyStr)) 
				map.put(keyStr, new ArrayList<String>());
			
			map.get(keyStr).add(s);
		}
		return new ArrayList<List<String>>(map.values());
	}


=======================================================================================================
method 2:

method:
	- 记录字符串的每个字符出现的次数从而完成映射。因为有 26 个字母，不好解释，我们假设只有 5 个字母，来看一下怎么完成映射。
	- 首先初始化 key = "0#0#0#0#0#"，数字分别代表 abcde 出现的次数，# 用来分割。
		这样的话，"abb" 就映射到了  "1#2#0#0#0"。
		"cdc" 就映射到了 "0#0#2#1#0"。
		"dcc" 就映射到了 "0#0#2#1#0"。

stats:

	- 时间复杂度： O（nK）。
	- 空间复杂度：O（NK），用来存储结果。
	- Runtime: 37 ms, faster than 13.76% of Java online submissions for Group Anagrams.
	- Memory Usage: 44.6 MB, less than 42.10%

	public List<List<String>> groupAnagrams(String[] strs) {
	    HashMap<String, List<String>> hash = new HashMap<>();

	    for (int i = 0; i < strs.length; i++) {
	    	//create array to store counts of each character
	        int[] num = new int[26];

	        //记录每个字符的次数
	        for (int j = 0; j < strs[i].length(); j++) {
	            num[strs[i].charAt(j) - 'a']++;
	        }

	        //转成 0#2#2# 类似的形式
	        String key = "";
	        for (int j = 0; j < num.length; j++) {
	            key = key + num[j] + '#';
	        }

	        if (hash.containsKey(key)) {
	            hash.get(key).add(strs[i]);
	        } else {
	            List<String> temp = new ArrayList<String>();
	            temp.add(strs[i]);
	            hash.put(key, temp);
	        }

	    }
	    return new ArrayList<List<String>>(hash.values());
	}


=======================================================================================================
method 3:

method:
	- use prime number & theorem: every number consists of muptiplication of prime numbers
		所有自然数，要不本来是prime， 要么可以写为2个以上的质数的[积], each factors group is unique
	- https://leetcode.com/problems/group-anagrams/discuss/19183/Java-beat-100!!!-use-prime-number)
	- 利用这个，我们把每个字符串都映射到一个正数上。
	- 用一个数组存储质数 prime = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 41, 43, 47, 53, 
		59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103}。
	- 然后每个字符串的字符减去 ' a ' ，然后取到 prime 中对应的质数。把它们累乘。
	- 例如 abc ，就对应 'a' - 'a'， 'b' - 'a'， 'c' - 'a'，即 0, 1, 2，也就是对应素数 2 3 5，
		然后相乘 2 * 3 * 5 = 30，就把 "abc" 映射到了 30。

stats:

	- 时间复杂度：O（n * K），K 是字符串的最长长度。
	- 空间复杂度：O（NK），用来存储结果。
	- Runtime: 10 ms, faster than 47.90% of Java online submissions for Group Anagrams.
	- Memory Usage: 40.4 MB, less than 96.49% 
	- downside!! --> 因为求 key 的时候用的是累乘，可能会造成溢出，超出 int 所能表示的数字。



	public List<List<String>> groupAnagrams(String[] strs) {
	    HashMap<Integer, List<String>> hash = new HashMap<>();
	    //每个字母对应一个质数
	    int[] prime = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 41, 43, 47, 53, 59, 61, 67, 71, 
	    	73, 79, 83, 89, 97, 101, 103 };

	    for (int i = 0; i < strs.length; i++) {
	        int key = 1;
	        //累乘得到 key
	        for (int j = 0; j < strs[i].length(); j++) {
	            key *= prime[strs[i].charAt(j) - 'a'];
	        } 
	        if (hash.containsKey(key)) {
	            hash.get(key).add(strs[i]);
	        } else {
	            List<String> temp = new ArrayList<String>();
	            temp.add(strs[i]);
	            hash.put(key, temp);
	        }

	    }
	    return new ArrayList<List<String>>(hash.values());
	}



