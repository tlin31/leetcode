2452. Words Within Two Edits of Dictionary - Medium

You are given two string arrays, queries and dictionary. All words in each array comprise of lowercase English letters and have the same length.

In one edit you can take a word from queries, and change any letter in it to any other letter. Find all words from queries that, after a maximum of two edits, equal some word from dictionary.

Return a list of all words from queries, that match with some word from dictionary after a maximum of two edits. Return the words in the same order they appear in queries.

 

Example 1:

Input: queries = ["word","note","ants","wood"], dictionary = ["wood","joke","moat"]
Output: ["word","note","wood"]
Explanation:
- Changing the 'r' in "word" to 'o' allows it to equal the dictionary word "wood".
- Changing the 'n' to 'j' and the 't' to 'k' in "note" changes it to "joke".
- It would take more than 2 edits for "ants" to equal a dictionary word.
- "wood" can remain unchanged (0 edits) and match the corresponding dictionary word.
Thus, we return ["word","note","wood"].

Example 2:

Input: queries = ["yes"], dictionary = ["not"]
Output: []
Explanation:
Applying any two edits to "yes" cannot make it equal to "not". Thus, we return an empty array.
 

Constraints:

1 <= queries.length, dictionary.length <= 100
n == queries[i].length == dictionary[j].length
1 <= n <= 100
All queries[i] and dictionary[j] are composed of lowercase English letters.



******************************************************
key:
	- Trie, hamming distance
	- edge case:
		1) 
		2)

******************************************************

===================================================================================================
Method 1:

Method:

	-	
Using Trie Data Structure Solution



Stats:
	Time Complexity: O(n * k + m * k)
	Space Complexity : O(n * k * 26)
	m = length queries array
	n = length dictionary array
	m = length String



class Solution {
    private static class Node {
		private char data;
		private boolean isEnd;
		private Node[] children;

		public Node(char data) {
			this.data = data;
			this.isEnd = false;
			this.children = new Node[26];
		}
	}

	private Node root = new Node('/');

	private void insertWord(String word) {
		Node curr = root;
		for (int i = 0; i < word.length(); i++) {
			int childIdx = word.charAt(i) - 'a';
			if (curr.children[childIdx] == null) {
				curr.children[childIdx] = new Node(word.charAt(i));
			}
			curr = curr.children[childIdx];
		}
		curr.isEnd = true;
	}

	private boolean searchWord(Node root, String word, int count, int index) {
		if (index == word.length()) {
			return count <= 2;
		}
		boolean ans = false;
		for (int i = 0; i < root.children.length; i++) {
			if (root.children[i] != null) {
				ans |= searchWord(root.children[i], word, count + (((word.charAt(index) - 'a') == i) ? 0 : 1),
						index + 1);
			}
		}
		return ans;
	}

	public List<String> twoEditWords(String[] queries, String[] dictionary) {
		List<String> ans = new ArrayList<>();
		for (String word : dictionary) {
			insertWord(word);
		}
		for (String query : queries) {
			Node curr = root;
			if (searchWord(curr, query, 0, 0)) {
				ans.add(query);
			}
		}
		return ans;
	}
}


brute force:
class Solution {
    public List<String> twoEditWords(String[] queries, String[] dictionary) {
        List<String> ans = new ArrayList<>();
        for(int i = 0; i < queries.length; i++){
            String str = queries[i];
            
            for(int j = 0; j < dictionary.length; j++){
                String s = dictionary[j];
                int count = 0;
                for(int k = 0; k < s.length(); k++){
                    if(str.charAt(k) != s.charAt(k)){
                        count++;
                    }
                    if(count > 2){
                        break;
                    }
                }
                if(count <= 2){
                    ans.add(str);
                    break;
                }
                count = 0;
            }
        }
        return ans;
    }
}

===================================================================================================
Method 2: hamming-distance function.

Method:

	-	Hamming distance is a metric used to measure the difference between two strings of equal length. It counts the number of positions at which the corresponding symbols are different. The strings are typically binary (composed of 0s and 1s), but it can also apply to other sequences (like DNA sequences, for example).


Steps:
	Initialize a counter to 0.
	Loop through each position in the strings.
	If the characters at the current position in both strings are different, increment the counter.
	Once the loop finishes, return the counter.


Stats:

	- Time complexity is quadratic:O(N*N)
	- Space complexity is linear:O(N).


class Solution 
{
    int hamming(String s1, String s2)
    {
        int diff = 0;
        for (int i = 0; i < s1.length(); ++i)
            { diff += s1.charAt(i) != s2.charAt(i) ? 1 : 0; }
        return diff;
    };

    public List<String> twoEditWords(String[] queries, String[] dictionary) 
    {
        ArrayList<String> words = new ArrayList<>();
        
        for (String q : queries)
            for (String d : dictionary)
                if (hamming(q,d) <= 2){
                    words.add(q);
                    break;
                }
        
        return words;
    }
}








