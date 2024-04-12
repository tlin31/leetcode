269. Alien Dictionary - Hard

There is a new alien language which uses the latin alphabet. However, the order among letters are
unknown to you. You receive a list of non-empty words from the dictionary, where words are sorted
lexicographically by the rules of this new language. Derive the order of letters in this language.

Example 1:

Input:
[
  "wrt",
  "wrf",
  "er",
  "ett",
  "rftt"
]

Output: "wertf"

Example 2:
Input:
[
  "z",
  "x"
]

Output: "zx"

Example 3:
Input:
[
  "z",
  "x",
  "z"
] 

Output: "" 

Explanation: The order is invalid, so return "".
Note:

You may assume all letters are in lowercase.
You may assume that if a is a prefix of b, then a must appear before b in the given dictionary.
If the order is invalid, return an empty string.
There may be multiple valid order of letters, return any one of them is fine.


******************************************************
key:
  - topological sort
	- first note the first letter
  - edge case:
	1) 
	2)

******************************************************



=======================================================================================================
method 1:

method:

  - dfs, no topological sort!
  - build the graph for the character from consecutive word. But we do not calculate the indegree. 
    Start from any unvisited node, do a post-order traversal, and append the character.
		For example:
		A -> B -> C
		B -> D
	start from B, visit C, "C"
	visit D, append D: "CD"
	then we can append B: "CDB" , we should visit A, A can not go further, append A: "CDBA"
	Then reverse the string to get the right order.

stats:

  - Runtime: 1 ms, faster than 100.00% of Java online submissions for Alien Dictionary.
  - Memory Usage: 34.8 MB, less than 100.00%


  private final int N = 26;
  public String alienOrder(String[] words) {

	  //create array with length of 26 letters
	  boolean[][] adj = new boolean[N][N];
	  int[] visited = new int[N];
	  buildGraph(words, adj, visited);

	  StringBuilder sb = new StringBuilder();

	  for(int i = 0; i < N; i++) {
	  	  // unvisited
		  if(visited[i] == 0) {                
			  if(!dfs(adj, visited, sb, i)) 
			  	return "";
		  }
	  }
	  return sb.reverse().toString();
  }

  public boolean dfs(boolean[][] adj, int[] visited, StringBuilder sb, int i) {
  	  // 1 = visiting
	  visited[i] = 1;   

	  // go through entire matrix                         
	  for(int j = 0; j < N; j++) {
		  if(adj[i][j]) {                        
		  	  // if we already visit j, cycle 
			  if(visited[j] == 1) 
			  	return false;

			  // 0 = unvisited    
			  if(visited[j] == 0) {              
				  if(!dfs(adj, visited, sb, j)) 
				  	return false;
			  }
		  }
	  }

	  // 2 = visited
	  visited[i] = 2;                          
	  sb.append((char) (i + 'a'));
	  return true;
  }

  public void buildGraph(String[] words, boolean[][] adj, int[] visited) {
	  // -1 = not even existed
	  Arrays.fill(visited, -1);   

	  for(int i = 0; i < words.length; i++) {
		  // initialize all char in that word to 0
		  for(char c : words[i].toCharArray()) 
			  visited[c - 'a'] = 0;

		  // compare current word with previous word
		  if(i > 0) {
			  String w1 = words[i - 1], 
					 w2 = words[i];
			  int len = Math.min(w1.length(), w2.length());

			  // loop thru all char in shorter string
			  for(int j = 0; j < len; j++) {
				  char c1 = w1.charAt(j), 
					   c2 = w2.charAt(j);

				  // find the first diff char
				  if(c1 != c2) {
					  // means there's an edge between these 2 char.
					  // ex. ['ab','ac'] -> adj[b][c] = true
					  adj[c1 - 'a'][c2 - 'a'] = true;
					  break;
				  }
			  }
		  }
	  }
  }


=======================================================================================================
method 2:

method:

  - bfs and then topological sorting
  - First, build a degree map for each character in all the words:
	w:0
	r:0
	t:0
	f:0
	e:0
	Then build the hashmap by comparing the adjacent words, the first character that is different 
	between two adjacent words reflect the lexicographical order. For example:
	 "wrt",
	 "wrf",
    first different character is 3rd letter, so t comes before f

	 "wrf",
	 "er",
     first different character is 1rd letter, so w comes before e

	The characters in set come after the key. x->y means letter x comes before letter y. 
	x -> set: y,z,t,w means x comes before all the letters in the set. 

	ex. HashMap:
	t -> set: f    
	w -> set: e
	r -> set: t
	e -> set: r

	and final HashMap "degree" looks like, the number means "how many letters come before the key":

	w:0
	r:1
	t:1
	f:1
	e:1
	Then use Kahn s aglorithm to do topological sort. This is essentially BFS.

stats:

  - space complexity: O(max(V, E)) while degree use O(V) and map uses O(E).
  - time complexity: O(max(V, E)). V is the number of nodes and E is edge number in the graph.


public class AlienDict {

    public static void main(String[] args) {
        AlienDict sol = new AlienDict();
        List<String> words = Arrays.asList("aa", "ab", "ac", "bb");
        System.out.println(sol.findOrder(words));
    }

    public String findOrder(List<String> words) {

    	// exit case
        if (words == null || words.size() == 0) 
        	return "";

        // store degree
        Map<Character, Integer> degree = new HashMap<>();

        // store edges from point A to all its neighbors {B, C, D, ...}
        Map<Character, Set<Character>> graph = new HashMap<>();

        // init graph and degree map
        for (String word : words) {
            for (char c : word.toCharArray()) {
                degree.putIfAbsent(c, 0);
                graph.putIfAbsent(c, new HashSet<>());
            }
        }

        // build the graph and degree map use curr word & next word
        for (int i = 0; i < words.size() - 1; i++) {
            String word1 = words.get(i);
            String word2 = words.get(i + 1);
            int len = Math.min(word1.length(), word2.length());
            for (int j = 0; j < len; j++) {
                char c1 = word1.charAt(j);
                char c2 = word2.charAt(j);
                if (c1 != c2) {
                    if (!graph.get(c1).contains(c2)) {
                        graph.get(c1).add(c2);

                        // if a ->b, means a come before b, we add in degree for b.
                        degree.put(c2, degree.get(c2) + 1);
                    }
                    break;
                }
            }
        }

        // topological sort, start with in degree == 0
        Queue<Character> queue = new LinkedList<>();
        for (char key : degree.keySet()) {
            if (degree.get(key) == 0) {
                queue.offer(key);
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            char c = queue.poll();
            sb.append(c);
            // generate neighbors
            for (char neighbor : graph.get(c)) {
                degree.put(neighbor, degree.get(neighbor) - 1);
                if (degree.get(neighbor) == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        
        // if there is a cycle, means the sb.length() != degree.size();
        if (degree.size() != sb.length()) {
            return "";
        }
        return sb.toString();
    }
}






















