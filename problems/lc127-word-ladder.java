127. Word Ladder Medium

Given two words (beginWord and endWord), and a dictionary word list, find the length of shortest 
transformation sequence from beginWord to endWord, such that:

Only one letter can be changed at a time.
Each transformed word must exist in the word list. Note that beginWord is not a transformed word

Note: 
	1. Return 0 if there is no such transformation sequence.
	2. All words have the same length.
	3. All words contain only lowercase alphabetic characters.
	4. You may assume no duplicates in the word list.
	5. You may assume beginWord and endWord are non-empty and are not the same.

Example 1:

Input:
beginWord = "hit",
endWord = "cog",
wordList = ["hot","dot","dog","lot","log","cog"]

Output: 5

Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
return its length 5.


Example 2:
Input:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log"]

Output: 0

Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.


******************************************************
key:
	- BFS OR Bidirectional Breadth First Search (search from beginWord & endWord in parallel)
	- faster achieved by masking all combination and stores it
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- build a graph, then bfs
	- find intermediate word:
		use a mask and store all possibilities in a hashmap as the key.
		ex. hot --> map stores {<*ot, [hot, dot, lot]>, <h*t, [hot, hit]>, <ho*, {hot}>}

	- Push a tuple containing the beginWord and 1 in a queue. The 1 represents the level number of a 
	  node. 
	- return the level of the endNode as that would represent the shortest sequence/distance from the 
	  beginWord.

	- To prevent cycles, use a visited dictionary.
	- BFS:
		While the !queue.isEmpty(), get the front element of the queue = current_word.

		Find all the generic transformations of the current_word and find out if any of these 
		transformations is also a transformation of other words in the word list. This is achieved by 
		checking the all_combo_dict.

		The list of words we get from all_combo_dict are all the words which have a common intermediate 
		state with the current_word. These new set of words will be the adjacent nodes/words to 
		current_word and hence added to the queue.

		Hence, for each word in this list of intermediate words, append (word, level + 1) into the queue 
		where level is the level for the current_word.

stats:

	- Time Complexity: 43 ms, O(M×N), where M = length of words and N = total number of words in the input word 
	   list. Finding out all the transformations takes M iterations for each of the N words & BFS may go
	   to every N words
	- Space Complexity: O(M×N), to store all M transformations for each of the N words, in the 
	  all_combo_dict dictionary. Visited dictionary is of N size. Queue for BFS in worst case would need 
	  space for all N words.


class Solution {
  public int ladderLength(String beginWord, String endWord, List<String> wordList) {

    // Since all words are of same length.
    int L = beginWord.length();

    // Dictionary to hold combination of words that can be formed,
    // from any given word. By changing one letter at a time.
    Map<String, List<String>> allComboDict = new HashMap<>();

    wordList.forEach(
        word -> {
          for (int i = 0; i < L; i++) {
            // Key is the generic word
            // Value is a list of words which have the same intermediate generic word.
            String newWord = word.substring(0, i) + '*' + word.substring(i + 1, L);
            List<String> transformations = allComboDict.getOrDefault(newWord, new ArrayList<>());
            transformations.add(word);
            allComboDict.put(newWord, transformations);
          }
        });

    // Queue for BFS
    Queue<Pair<String, Integer>> Q = new LinkedList<>();
    Q.add(new Pair(beginWord, 1));

    // Visited to make sure we don't repeat processing same word.
    Map<String, Boolean> visited = new HashMap<>();
    visited.put(beginWord, true);

    while (!Q.isEmpty()) {
      Pair<String, Integer> node = Q.remove();
      String word = node.getKey();
      int level = node.getValue();

      // check for all *ot, h*t, ho* 
      for (int i = 0; i < L; i++) {

        // Intermediate words for current word
        String newWord = word.substring(0, i) + '*' + word.substring(i + 1, L);

        // Next states are all the words which share the same intermediate state.
        for (String adjacentWord : allComboDict.getOrDefault(newWord, new ArrayList<>())) {
          // If at any point if we find what we are looking for
          // i.e. the end word - we can return with the answer.
          if (adjacentWord.equals(endWord)) {
            return level + 1;
          }
          // Otherwise, add it to the BFS Queue. Also mark it visited
          if (!visited.containsKey(adjacentWord)) {
            visited.put(adjacentWord, true);
            Q.add(new Pair(adjacentWord, level + 1));
          }
        }
      }
    }

    return 0;
  }
}

=======================================================================================================
method 2:

method:

	- Bidirectional Breadth First Search
	- The graph formed from the nodes in the dictionary might be too big. The search space considered 
	  by the bfs depends upon the branching factor of the nodes at each level. If the branching factor 
	  remains the same for all the nodes, the search space increases exponentially with # of levels. 

	- cut down the search space of the standard breadth first search algorithm if we launch two 
	  simultaneous BFS. One from the beginWord and one from the endWord. We progress one node at a 
	  time from both sides and at any point in time if we find a common node in both the searches, 
	  we stop the search. 


stats:

	- Runtime: 24 ms, faster than 90.88% of Java online submissions for Word Ladder.
	- Memory Usage: 45.2 MB, less than 14.60% of Java online submissions for Word Ladder.
	- same complexity as method 1, but in practise will end a lot faster


class Solution {

  private int L;
  private Map<String, List<String>> allComboDict;

  Solution() {
    this.L = 0;

    // Dictionary to hold combination of words that can be formed,
    // from any given word. By changing one letter at a time.
    this.allComboDict = new HashMap<>();
  }

  private int visitWordNode( Queue<Pair<String, Integer>> Q, Map<String, Integer> visited,
    							Map<String, Integer> othersVisited) {

    Pair<String, Integer> node = Q.remove();
    String word = node.getKey();
    int level = node.getValue();

    for (int i = 0; i < this.L; i++) {

      // Intermediate words for current word
      String newWord = word.substring(0, i) + '*' + word.substring(i + 1, L);

      // Next states are all the words which share the same intermediate state.
      for (String adjacentWord : this.allComboDict.getOrDefault(newWord, new ArrayList<>())) {

      	// begin bfs & end bfs meet in the middle!!! yayyyy
        if (othersVisited.containsKey(adjacentWord)) {
          return level + othersVisited.get(adjacentWord);
        }

        if (!visited.containsKey(adjacentWord)) {

          // Save the level as the value of the dictionary, to save number of hops.
          visited.put(adjacentWord, level + 1);
          Q.add(new Pair(adjacentWord, level + 1));
        }
      }
    }
    return -1;
  }

  //required to write function
  public int ladderLength(String beginWord, String endWord, List<String> wordList) {

    if (!wordList.contains(endWord)) {
      return 0;
    }

    // Since all words are of same length.
    this.L = beginWord.length();

    // add to the dictionary
    wordList.forEach(
        word -> {
          for (int i = 0; i < L; i++) {
            // Key is the generic word
            // Value is a list of words which have the same intermediate generic word.
            // ex. word --> {*ord, w*rd, wo*d,wor*}
            String newWord = word.substring(0, i) + '*' + word.substring(i + 1, L);

            //in dict: [*ord,{word}]
            List<String> transformations =
                this.allComboDict.getOrDefault(newWord, new ArrayList<>());
            transformations.add(word);
            this.allComboDict.put(newWord, transformations);
          }
        });

    // Queues for birdirectional BFS

    // BFS starting from beginWord
    Queue<Pair<String, Integer>> Q_begin = new LinkedList<>();
    Q_begin.add(new Pair(beginWord, 1));

    // BFS starting from endWord
    Queue<Pair<String, Integer>> Q_end = new LinkedList<>();
    Q_end.add(new Pair(endWord, 1));

    // Visited to make sure we don't repeat processing same word.
    Map<String, Integer> visitedBegin = new HashMap<>();
    Map<String, Integer> visitedEnd = new HashMap<>();
    visitedBegin.put(beginWord, 1);
    visitedEnd.put(endWord, 1);

    //手动bi-directional
    while (!Q_begin.isEmpty() && !Q_end.isEmpty()) {

      // One hop from begin word
      int ans = visitWordNode(Q_begin, visitedBegin, visitedEnd);
      if (ans > -1) {
        return ans;
      }

      // One hop from end word
      // visitedEnd and visitedBegin的位置互换了！
      ans = visitWordNode(Q_end, visitedEnd, visitedBegin);
      if (ans > -1) {
        return ans;
      }
    }

    return 0;
  }
}
=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



