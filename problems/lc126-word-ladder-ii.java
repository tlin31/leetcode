126. Word Ladder II - Hard

Given two words (beginWord and endWord), and a dictionary word list, find all shortest 
transformation sequence(s) from beginWord to endWord, such that:

	1. Only one letter can be changed at a time
	2. Each transformed word must exist in the word list. Note that beginWord is not a transformed word.

Note:
	Return an empty list if there is no such transformation sequence.
	All words have the same length.
	All words contain only lowercase alphabetic characters.
	You may assume no duplicates in the word list.
	You may assume beginWord and endWord are non-empty and are not the same.


Example 1:

Input:
beginWord = "hit",
endWord = "cog",
wordList = ["hot","dot","dog","lot","log","cog"]

Output:
[
  ["hit","hot","dot","dog","cog"],
  ["hit","hot","lot","log","cog"]
]
Example 2:

Input:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log"]

Output: []

Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.


******************************************************
key:
	- construct graph, then dfs/backtrack 
	- edge case:
		1) empty string, return empty
		2)

******************************************************

Note:
1. Layer-wise Removal: In BFS, do not remove words from the dictionary immediately when they are found. Wait until you've processed the entire current level. This ensures you capture all parallel shortest paths to the same word.

2. Pruning: Stop the BFS immediately once you reach the level containing endWord. Any paths found deeper than this cannot be "shortest."

3. Backward Search: For very large dictionaries, the backtracking phase is often faster if you search from the smaller "side" of the graph if using Bidirectional BFS.

====================================================================================================
method 1:

method:

	- 1). Use BFS to find the shortest distance between start and end, tracing the distance of 
	      crossing nodes from start node to end node, and store node next level neighbors to HashMap;
	- 2). Use DFS to output paths with the same distance as the shortest distance from distance 
          HashMap: compare if the distance of the next level node equals the distance of the 
          current node + 1.


stats:

	- Runtime: 82 ms, faster than 69.64% of Java online submissions for Word Ladder II.
	- Memory Usage: 46 MB, less than 82.69% 






import java.util.*;

class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> result = new ArrayList<>();
        Set<String> wordSet = new HashSet<>(wordList);

        // Quick exit: if endWord not in dictionary, no path exists
        if (!wordSet.contains(endWord)) return result;

        // BFS: build a parent map — for each word, which words led to it?
        Map<String, List<String>> parents = new HashMap<>();
        Map<String, Integer> dist = new HashMap<>();
        Queue<String> queue = new LinkedList<>();

        dist.put(beginWord, 0);
        queue.offer(beginWord);

        boolean found = false;
        int shortestLen = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            String word = queue.poll();
            int d = dist.get(word);
            if (d >= shortestLen) break; // no need to go deeper

            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char original = chars[i];
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == original) continue;
                    
                    chars[i] = c;
                    String next = new String(chars);
                    chars[i] = original;

                    if (!wordSet.contains(next)) continue; //already visited/used

                    if (!dist.containsKey(next)) {
                        dist.put(next, d + 1);
                        queue.offer(next);
                        parents.computeIfAbsent(next, k -> new ArrayList<>()).add(word);

                        // find end word with shortest distance! update!
                        if (next.equals(endWord)) { 
                            found = true; 
                            shortestLen = d + 1; 
                        }
                    } 

                    else if (dist.get(next) == d + 1) {
                        // Another word at same level can also reach next
                        parents.get(next).add(word);
                    }
                }
            }
        }

        if (!found) return result;

        // DFS backward from endWord to beginWord using the parent map
        Deque<String> path = new ArrayDeque<>();
        path.addFirst(endWord);
        dfs(beginWord, endWord, parents, path, result);
        return result;
    }

    private void dfs(String beginWord, String word,
                     Map<String, List<String>> parents,
                     Deque<String> path, List<List<String>> result) {
        if (word.equals(beginWord)) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (String parent : parents.getOrDefault(word, List.of())) {
            path.addFirst(parent);
            dfs(beginWord, parent, parents, path, result);
            path.removeFirst();
        }
    }
}



这个solution在更新了test case之后可以通过！因为 it builds the graph backward using a parents map instead of a forward-facing children map.



1. The "Narrowing" Effect (Pruning)
In your original version, the DFS starts at the beginWord and explores forward. Even with a level graph, a beginWord might have many neighbors that eventually lead to dead ends or paths that don't reach the endWord.

This solution uses a parents map (child -> [list of parents]). By doing this:
    The BFS identifies every word that could be on a shortest path.
    The DFS starts at the endWord and moves backward.

The Logic: You only ever visit nodes that are guaranteed to have come from the beginWord. You aren't "searching" for the end; you are "retracing" a path that you already know exists.


2. Strict Level Control (dist.get(next) == d + 1)

Your original code used visited and toVisit sets. While correct, it was slightly loose. 

This solution uses an explicit dist map to enforce a strict layer requirement:

    If we find next and it’s not in the dist map, it’s a brand-new discovery.
    
    If next is in the dist map, we check: “Is the word I’m currently holding at the level exactly before next?”
        
        If yes, we add the current word as a parent. 

This captures all parallel shortest paths without ever accidentally looking at a longer detour.


3. Early Exit (d >= shortestLen)

The moment it discovers the endWord, it sets shortestLen. 

Any node pulled from the queue after that which is already at that distance is immediately ignored. 

This prevents the BFS from processing useless nodes that are too far away to be part of the shortest path.



4. Memory/Object Efficiency

Deque for DFS: 
    Using path.addFirst() and path.removeFirst() on a Deque (Double Ended Queue) is highly efficient for backtracking when building paths in reverse.

String creation: 
    It generates the next string inside the character loop only when necessary, minimizing garbage collection overhead.

=======================================================================================================
method 2:

method:

	- two end bfs
	- 

stats:

	- Runtime: 19 ms, faster than 93.50% of Java online submissions for Word Ladder II.
	- Memory Usage: 42.8 MB, less than 86.54% of Java online submissions for Word Ladder II.


class Solution {
 public List<List<String>> findLadders(String start, String end, List<String> dict) {
    Set<String> dict1 = new HashSet<>();
    List<List<String>> res = new ArrayList<List<String>>();

    dict1.addAll(dict);
    if (!dict1.contains(end)) return res;

    // hash set for both ends
    Set<String> set1 = new HashSet<String>();
    Set<String> set2 = new HashSet<String>();
    
    // initial words in both ends
    set1.add(start);
    set2.add(end);
    
    // we use a map to help construct the final result
    Map<String, List<String>> map = new HashMap<String, List<String>>();
    
    // build the map
    helper(dict1, set1, set2, map, false);
    
    List<String> sol = new ArrayList<String>(Arrays.asList(start));
    
    // recursively build the final result
    generateList(start, end, map, sol, res);
    
    return res;
  }
  
  boolean helper(Set<String> dict, Set<String> set1, Set<String> set2, Map<String,
                List<String>> map, boolean flip) {
    if (set1.isEmpty()) {
      return false;
    }
    
    if (set1.size() > set2.size()) {
      return helper(dict, set2, set1, map, !flip);
    }
    
    // remove words on current both ends from the dict
    dict.removeAll(set1);
    dict.removeAll(set2);
    
    // as we only need the shortest paths
    // we use a boolean value help early termination
    boolean done = false;
    
    // set for the next level
    Set<String> set = new HashSet<String>();
    
    // for each string in end 1
    for (String str : set1) {
      for (int i = 0; i < str.length(); i++) {
        char[] chars = str.toCharArray();
        
        // change one character for every position
        for (char ch = 'a'; ch <= 'z'; ch++) {
          chars[i] = ch;
          
          String word = new String(chars);
          
          // make sure we construct the tree in the correct direction
          String key = flip ? word : str;
          String val = flip ? str : word;
              
          List<String> list = map.containsKey(key) ? map.get(key) : new ArrayList<String>();
              
          if (set2.contains(word)) {
            done = true;
            
            list.add(val);
            map.put(key, list);
          } 
          
          if (!done && dict.contains(word)) {
            set.add(word);
            
            list.add(val);
            map.put(key, list);
          }
        }
      }
    }
    
    // early terminate if done is true
    return done || helper(dict, set2, set, map, !flip);
  }
  
  void generateList(String start, String end, Map<String, List<String>> map, 
                   List<String> sol, List<List<String>> res) {
    if (start.equals(end)) {
      res.add(new ArrayList<String>(sol));
      return;
    }
    
    // need this check in case the diff between start and end happens to be one
    // e.g "a", "c", {"a", "b", "c"}
    if (!map.containsKey(start)) {
      return;
    }
    
    for (String word : map.get(start)) {
      sol.add(word);
      generateList(word, end, map, sol, res);
      sol.remove(sol.size() - 1);
    }
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



