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



=======================================================================================================
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


class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<String> path = new ArrayList<>();
        List<List<String>> result = new ArrayList<List<String>>();
        HashMap<String, List<String>> graph = new HashMap<String, List<String>>();
        HashSet<String> dict = new HashSet<>(wordList);
        buildGraph(beginWord, endWord, graph, dict);
        dfs(beginWord, endWord, graph, path, result);
        return result;
    }
    
    private void buildGraph(String beginWord, String endWord, HashMap<String, List<String>> graph, 
      HashSet<String> dict) {
        HashSet<String> visited = new HashSet<>();
        HashSet<String> toVisit = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        toVisit.add(beginWord);
        boolean foundEnd = false;
        
        while(!queue.isEmpty()) {
            visited.addAll(toVisit);
            toVisit.clear();
            int count = queue.size();
            
            for (int i = 0; i < count; i++) {
                String word = queue.poll();
                List<String> children = getNextLevel(word, dict);
                for (String child : children) {
                    if (child.equals(endWord)) 
                      foundEnd = true;
                    if (!visited.contains(child)) {
                        if (!graph.containsKey(word)) {
                            graph.put(word, new ArrayList<String>());
                        }
                        graph.get(word).add(child);
                    }
                    if (!visited.contains(child) && !toVisit.contains(child)) {
                        queue.offer(child);
                        toVisit.add(child);
                    }
                }
            }
            
            if (foundEnd) break;
        }
    }
    
    // find neighbor nodes, ex. hot --> try to find aot, bot, ...zot in dict,
    // then find hat, hbt, hct,... hzt & hoa, hob,.... hoz in dict
    private List<String> getNextLevel(String word, HashSet<String> dict) {
        List<String> result = new ArrayList<>();
        char[] chs = word.toCharArray();
        
        for (int i = 0; i < chs.length; i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (chs[i] == c) 
                   continue;
                char t = chs[i];
                chs[i] = c;
                String target = String.valueOf(chs);
                if (dict.contains(target)) 
                	result.add(target);
                chs[i] = t;
            }
        }
        
        return result;
    }
    
    private void dfs(String curWord, String endWord, HashMap<String, List<String>> graph, 
                     List<String> path, List<List<String>> result) {
        path.add(curWord);
        
        if (curWord.equals(endWord)) 
        	result.add(new ArrayList<String>(path));
        
        else if (graph.containsKey(curWord)) {
            for (String nextWord : graph.get(curWord)) {
                dfs(nextWord, endWord, graph, path, result);
            }
        }

        path.remove(path.size()-1);
    }
}


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



