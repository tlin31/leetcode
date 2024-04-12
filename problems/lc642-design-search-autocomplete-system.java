642. Design Search Autocomplete System Hard

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
	- 精华在 traverse function!
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

	- AutocompleteSystem() takes O(k*l)time. We need to iterate over l sentences each of average length
		k, to create the trie for the given set of sentencessentences.

	- input() takes O(p+q+ mlogm) time. Here, pp refers to the length of the sentence formed till now, 
		cur_sent. q refers to the number of nodes in the trie considering the sentence formed till now as 
		the root node. Again, we need to sort the listlist of length m indicating the options available 
		for the hot sentences, which takes O(mlogm) time.



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



