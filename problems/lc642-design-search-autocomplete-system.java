642. Design Search Autocomplete System - Hard

Design a search autocomplete system for a search engine. Users may input a sentence (at least one word
and end with a special character '#'). For each character they type except '#', you need to return the
top 3 historical hot sentences that have prefix the same as the part of sentence already typed. 

Here are the specific rules:

	1. The hot degree for a sentence is defined as the number of times a user typed the exactly 
		 same sentence before.
	2. The returned top 3 hot sentences should be sorted by hot degree (The first is the hottest one). 
		 If several sentences have the same degree of hot, you need to use ASCII-code order (smaller 
		 one appears first).
	3. If less than 3 hot sentences exist, then just return as many as you can.


Your job is to implement the following functions:

	1. The constructor function --> AutocompleteSystem(String[] sentences, int[] times): 
		 The input is historical data. Sentences is a string array consists of previously typed 
		 sentences. Times is the corresponding times a sentence has been typed. Your system should 
		 record these historical data.

	2. List<String> input(char c): 
		 The input c is the next character typed by the user. The character will only be lower-case letters 
		 ('a' to 'z'), blank space (' ') or a special character ('#'). Also, the previously typed sentence
		 should be recorded in your system. The output will be the top 3 historical hot sentences that have 
		 prefix the same as the part of sentence already typed.

 
Example:

Operation: AutocompleteSystem(["i love you", "island","ironman", "i love leetcode"], [5,3,2,2])
The system have already tracked down the following sentences and their corresponding times:
"i love you" : 5 times
"island" : 3 times
"ironman" : 2 times
"i love leetcode" : 2 times

Now, the user begins another search:

Operation: input('i')
Output: ["i love you", "island","i love leetcode"]
Explanation:

There are four sentences that have prefix "i". Among them, "ironman" and "i love leetcode" have same hot degree. Since ' ' has ASCII code 32 and 'r' has ASCII code 114, "i love leetcode" should be in front of "ironman". Also we only need to output top 3 hot sentences, so "ironman" will be ignored.

Operation: input(' ')
Output: ["i love you","i love leetcode"]
Explanation:
There are only two sentences that have prefix "i ".

Operation: input('a')
Output: []
Explanation:
There are no sentences that have prefix "i a".

Operation: input('#')
Output: []
Explanation:
The user finished the input, the sentence "i a" should be saved as a historical sentence in system. And the following input will be counted as a new search.

 
Note:

	1. The input sentence will always start with a letter and end with '#', and only one blank space 
		 will exist between two words.
	2. The number of complete sentences that to be searched won't exceed 100. The length of each 
		 sentence including those in the historical data won't exceed 100.
	3. use double-quote instead of single-quote when you write test cases even for a character input.
	4. Please remember to RESET your c_lass variables declared in c_lass AutocompleteSystem, as s_tatic/
		 c_lass variables are persisted across multiple test cases. Please see here for more details.
 


******************************************************
key:
	- Trie
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- Trie
	- Note: need to store current state in the trie

stats:

操作			时间复杂度		说明
初始化		O(N * L)		N 句子，每句长度 L
插入			O(L²)			每层更新 countMap
查询			O(L + K log K)	L 前缀长度，K 为候选数目
空间复杂度	O(N * L)		Trie 存储所有前缀路径


🧩 1. 数据结构选择

	要能快速根据 前缀 找到对应句子 ⇒ 用 Trie（字典树）。

	Trie 每个节点存：

	children: 字符 -> TrieNode

	countMap: 记录该前缀下所有句子及其出现次数

	或者 hotList: 当前节点下 top 3 热门句子（优化内存）

🧩 2. Trie + 优先队列的核心逻辑

	当我们输入字符时，不断沿 Trie 向下查找。

	进入到某个 Trie 节点（对应当前前缀），获取它的所有句子。

	按「出现次数高 → 字典序」排序，返回前三个。

🧩 3. 句子统计

	用一个全局 HashMap<String, Integer> 来记录每个句子的出现次数（frequency）。

	每次遇到 '#'，将当前输入的句子：

	加入 HashMap（计数 +1）

	同时更新 Trie（路径上的节点都记录这个句子）


class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    Map<String, Integer> countMap = new HashMap<>(); //记录该前缀下所有句子及其出现次数
}

class AutocompleteSystem {
    private TrieNode root;
    private StringBuilder curInput = new StringBuilder();
    private Map<String, Integer> countMap;

    public AutocompleteSystem(String[] sentences, int[] times) {
        root = new TrieNode();
        countMap = new HashMap<>();
        for (int i = 0; i < sentences.length; i++) {
            countMap.put(sentences[i], times[i]);
            insert(sentences[i], times[i]);
        }
    }

    private void insert(String sentence, int count) {
        TrieNode node = root;
        for (char c : sentence.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
            node.countMap.put(sentence, node.countMap.getOrDefault(sentence, 0) + count);
        }
    }

    public List<String> input(char c) {
    	// 保存至今输入的char
    	// 存入countmap， 然后存到trie
    	// reset curInput
        if (c == '#') {
            String sentence = curInput.toString();
            countMap.put(sentence, countMap.getOrDefault(sentence, 0) + 1);
            insert(sentence, 1);
            curInput = new StringBuilder();
            return new ArrayList<>();
        }

        // 如果当前已存储的没有
        curInput.append(c);
        TrieNode node = root;
        for (char ch : curInput.toString().toCharArray()) {
            if (!node.children.containsKey(ch)) 
            	return new ArrayList<>();
            node = node.children.get(ch);
        }

        //根据存的value/count，如果一样比较string的大小
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
            (a, b) -> a.getValue().equals(b.getValue()) ?
                a.getKey().compareTo(b.getKey()) : b.getValue() - a.getValue()
        );

//         PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
//     Comparator.comparing(Map.Entry<String, Integer>::getValue, Comparator.reverseOrder())
//               .thenComparing(Map.Entry<String, Integer>::getKey)
// );

/**
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>((a, b) -> {
		    // 1. If counts are the same, compare Strings alphabetically
		    if (a.getValue().equals(b.getValue())) {
		        return a.getKey().compareTo(b.getKey());
		    }
		    // 2. Otherwise, sort by Count (Highest first)
		    return b.getValue() - a.getValue(); 
			});
*/

        pq.addAll(node.countMap.entrySet());
        List<String> res = new ArrayList<>();
        int k = 3;
        while (!pq.isEmpty() && k-- > 0) {
            res.add(pq.poll().getKey());
        }
        return res;
    }
}


如果要支持删除句子怎么办？
→ 在 countMap 里减次数或删除，并在 Trie 路径上更新对应计数。

如何优化内存？
→ 用 hotList 而不是 countMap；或限制 Trie 深度。

支持多线程查询？
→ 可用读写锁 ReadWriteLock 或使用 ConcurrentHashMap 改造。

==========================================================================================





class Node {
	String sentence;
	int times;

	Node(String inputSentence, int hotness) {
		sentence = inputSentence;
		times = hotness;
	}
}

class Trie {
	int times;
	Trie[] branches = new Trie[27];
}

class AutocompleteSystem {
	private Trie root;
	private String cur_sent = "";

	public AutocompleteSystem(String[] sentences, int[] times) {
		root = new Trie();
		for (int i = 0; i < sentences.length; i++) {
			insert(root, sentences[i], times[i]);
		}
	}

	private void insert(Trie t, String s, int times) {
		for (int i = 0; i < s.length(); i++) {
			if (t.branches[toInt(s.charAt(i))] == null) {
				t.branches[toInt(s.charAt(i))] = new Trie();
			}

			// move 1 level down
			t = t.branches[toInt(s.charAt(i))];
		}

		// increment hotness
		t.times += times;
	}

	//help converts a character to its corresponding index in an int array
	private int toInt(char c) {
		return c == ' ' ? 26 : c - 'a';
	}



	public List<String> input(char c) {
		List<String> res = new ArrayList<>();
		if (c == '#') {
			insert(root, cur_sent, 1);
			cur_sent = "";
		} else {
			cur_sent += c;
			List<Node> list = lookup(root, cur_sent);
			Collections.sort(list,
					(a, b) -> a.times == b.times ? a.sentence.compareTo(b.sentence) : b.times - a.times);

			// get the first 3
			for (int i = 0; i < Math.min(3, list.size()); i++) 
				res.add(list.get(i).sentence);
		}
		return res;
	}

	// find the current sentence's position in the tree
	private List<Node> lookup(Trie t, String s) {

		// store all possibile sentence after exausting chars in s
		List<Node> list = new ArrayList<>();

		// get to the last char in s
		for (int i = 0; i < s.length(); i++) {
			if (t.branches[toInt(s.charAt(i))] == null) {
				return new ArrayList<>();
			}
			t = t.branches[toInt(s.charAt(i))];
		}

		// fill up list
		traverse(s, t, list);
		return list;
	}

	// store all children after s in list
	private void traverse(String s, Trie t, List<Node> list) {
		
		// reach a leaf node
		if (t.times > 0) 
			list.add(new Node(s, t.times));

		// go through 26 chars in each level
		for (char i = 'a'; i <= 'z'; i++) {
			if (t.branches[i - 'a'] != null) {
				traverse(s + i, t.branches[i - 'a'], list);
			}
		}
		if (t.branches[26] != null) {
			traverse(s + ' ', t.branches[26], list);
		}
	}


}   

=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



