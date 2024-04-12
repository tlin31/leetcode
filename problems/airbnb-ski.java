
背景是一个滑雪选手从高山上往下滑，会遇到不同的checkpoint，每一个checkpoint有自己的point，然后每个edge有distance。
经过每一个checkpoint所得到的score是通过一个包含point和distance的式子算出来的（比如2 * point +distance之类的）。

最终求从最高点往下滑能得到的最大score是多少，用BFS就可以。

类似下图，从A出发，最终可以到 I 或者 J，求到I 或者 J 能得到的最大score是多少。

https://www.1point3acres.com/bbs/interview/airbnb-engineering-470093.html

=======================================================================================================

Method:
    - bfs + topological sort

 
import java.util.*;
import org.junit.*; 
import static org.junit.Assert.*;
 
public class Ski {
     
    class Node {
        String name ; 
        int point ; 
        Map<String, Integer> nexts ;
        int score ; 
        Node(String name, int point) {
            this.name = name ; 
            this.point = point ; 
            this.nexts = new HashMap<>() ;
            this.score = Integer.MIN_VALUE ; 
        }
    }
     
    private int getScore(int point, int dist) {
        return 2 * point + dist ; 
    }
     
    public int maxPath(String[] nodes, String[] edges, String src, List<String> path) {
        Map<String, Node> graph = new HashMap<>() ;
        Map<String, Integer> indegrees = new HashMap<>() ; 

        // add node and its reward point
        for ( String s: nodes ) {
            String[] t = s.split(",") ; 
            String name = t[0] ; 
            int point = Integer.parseInt(t[1]) ; 
            if (! graph.containsKey(name) ) 
                graph.put(name, new Node(name, point)) ; 

            graph.get(name).point = point ; 
            indegrees.put(name, 0) ; 
            if ( name.equals(src) ) 
                graph.get(name).score = 0 ; // Set src score to 0
        }

        // go through edges and update indegree
        for ( String s: edges ) {
            String[] t = s.split(",") ; 
            String[] fromto = t[0].split("->") ; 
            String from = fromto[0], to = fromto[1] ; 
            int dist = Integer.parseInt(t[1]) ; 

            if ( !graph.get(from).nexts.containsKey(to) ) {
                indegrees.put(to, indegrees.getOrDefault(to, 0) + 1) ; 
            }
            
            graph.get(from).nexts.put(to, dist) ; 
        }
         
        // topological order
        Map<String, String> links = new HashMap<>() ; 
        Queue<Node> q = new LinkedList<>() ; 
        for ( String name: indegrees.keySet() ) {
            if ( indegrees.get(name) == 0 ) q.offer(graph.get(name)) ; 
        }
        String finalName = "" ; 
        int ans = Integer.MIN_VALUE ; 
        while ( ! q.isEmpty() ) {
            Node curr = q.poll() ;
            if ( curr.nexts.isEmpty() && curr.score != Integer.MIN_VALUE && ans < curr.score ) {
                ans = curr.score ; 
                finalName = curr.name ; 
            }
            for ( String next: curr.nexts.keySet() ) {
                if ( curr.score != Integer.MIN_VALUE ) {
                    int score = getScore(curr.point, curr.nexts.get(next)) ; 
                    if ( curr.score + score > graph.get(next).score ) {
                        graph.get(next).score = curr.score + score ;
                        links.put(next, curr.name) ; 
                    }
                }
                indegrees.put(next, indegrees.get(next) - 1) ; 
                if ( indegrees.get(next) == 0 ) {
                    q.offer(graph.get(next)) ; 
                }
            }
        }
        // Build path
        path.clear(); 
        while ( !finalName.isEmpty() ) {
            path.add(finalName) ; 
            finalName = links.containsKey(finalName) ? links.get(finalName) : "" ; 
        }
        Collections.reverse(path);
        return ans ; 
    }



    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Ski sol = new Ski() ; 
        String[] nodes = {"A,5", "B,7", "C,6", "D,2", "E,4", "F,7", "H,7", "I,3", "J,2"} ;
        String[] edges = {
                "A->B,2",
                "A->C,3",
                "B->D,5",
                "B->E,6",
                "C->E,4",
                "C->F,4",
                "D->H,7",
                "E->H,6",
                "H->I,1",
                "H->J,2",
                "F->J,3"
        } ; 
        List<String> path = new ArrayList<>() ; 
        int ans = sol.maxPath(nodes, edges, "A", path) ;        
        System.out.println(ans); 
        System.out.println(path);
    }
 
}



=======================================================================================================

Assumption
1. The input is a list of list, each list store ["A", "B", "10"] represents A to B and path weight 10
and the second input is a list of list ["A", "15"] represents the point of A is 15
given a list of end, and a start, find the max score
2. Is the end point given or not
3. Does the graph contains cycle
(negative cycle is ok, but positive cycle there is no answer)
to detect positive cycle, in the dfs, we use a set for current path, if visited && current score
greater than the score in the map=> positive cycle. We should just stop.

4. What if we can not reach any of the end, return -1

Approach:
DFS: The idea is DFS to traverse the map starting from start point, maintain the score along the path,
if we can reach one of the end, we update the global max. Also, to speed up the process, we can do some
memorization, we can keep track of the max score so far for each node, if we reach a node with smaller
score, we do not keep going.

Time: O(E*V)
    - If there is no cycle, In the worst case, every time we pass a edge, we update a score, we might need 
      re-visited all the node, that is O(E*V)
Space: O(h) + O(V+E) = O(V + E)
    - where h is the length of longest path from start to end
      O(V+E) to store the path information, the rewards, and a map to keep track of max score
=================================
Follow up:
Topo + BFS: Since the graph is a DAG, we can just traverse the map with topological ordering. 
Compared with BFS or DFS to traverse the map and find the max, we do not have to do duplicated 
computation. 
Once a node in-degree become 0, that is the final score, means there is no more incoming edges. 
Therefore, we can get the max score for each point, then, find the max one in the end point
 


import java.util.*;

public class ASki {

    public static void main(String[] args) {
        ASki sol = new ASki();
        List<List<String>> paths = new ArrayList<>();
        paths.add(Arrays.asList("A", "B", "2"));
        paths.add(Arrays.asList("A", "C", "3"));
        paths.add(Arrays.asList("B", "D", "5"));
        paths.add(Arrays.asList("B", "E", "6"));
        paths.add(Arrays.asList("C", "E", "4"));
        paths.add(Arrays.asList("C", "F", "4"));
        paths.add(Arrays.asList("D", "H", "7"));
        paths.add(Arrays.asList("E", "H", "6"));
        paths.add(Arrays.asList("H", "I", "1"));
        paths.add(Arrays.asList("H", "J", "2"));
        paths.add(Arrays.asList("F", "J", "3"));
        List<List<String>> points = new ArrayList<>();
        points.add(Arrays.asList("A", "5"));
        points.add(Arrays.asList("B", "7"));
        points.add(Arrays.asList("C", "6"));
        points.add(Arrays.asList("D", "2"));
        points.add(Arrays.asList("E", "4"));
        points.add(Arrays.asList("F", "7"));
        points.add(Arrays.asList("H", "7"));
        points.add(Arrays.asList("I", "3"));
        points.add(Arrays.asList("J", "2"));
        List<String> ends = Arrays.asList("I", "J");
        System.out.println(sol.findMaxScore(paths, points, "A"));
        System.out.println(sol.maxPath);
        // assume score = 2 * points + distance

    }

    private int maxScore = Integer.MIN_VALUE;

    // looks like (key = A, value = map (B, cost 2))
    private Map<String, Map<String, Integer>> pathMap;  // graph info
    private Map<String, Integer> rewardMap;             //   graph info
    private Map<String, Integer> scoreMap;              //   dfs pruning
    private List<String> maxPath;

    public int findMaxScore(List<List<String>> paths, List<List<String>> rewards, String start) {
        
        //initialize:
        this.pathMap = new HashMap<>();
        this.rewardMap = new HashMap<>();
        this.scoreMap = new HashMap<>();

        // path is like ("A", "B", "2")
        for (List<String> path : paths) {
            pathMap.putIfAbsent(path.get(0), new HashMap<>());
            pathMap.putIfAbsent(path.get(1), new HashMap<>());
            pathMap.get(path.get(0)).put(path.get(1), Integer.parseInt(path.get(2)));
        }

        // rewards like ("A", "5")
        for (List<String> point : rewards) {
            rewardMap.put(point.get(0), Integer.parseInt(point.get(1)));
        }

        Set<String> ends = new HashSet<>();
        for (String key : pathMap.keySet()) {
            scoreMap.put(key, 0);
            // if can't find key in all "starts", then it must be the end
            if (pathMap.get(key).isEmpty()) {
                ends.add(key);
            }
        }

        List<String> curPath = new ArrayList<>();
        dfs(curPath, ends, start, 2 * rewardMap.get(start));
        return maxScore == Integer.MIN_VALUE ? -1 : maxScore;
    }

    private void dfs(List<String> curPath, Set<String> ends, String curr, int score) {
        curPath.add(curr);

        // if reach the end
        if (ends.contains(curr)) {

            // update score
            if (score > this.maxScore) {
                this.maxPath = new ArrayList<>(curPath);
                this.maxScore = score;
            }

            // clear curPath
            curPath.remove(curPath.size() - 1);
            return;
        }


        for (Map.Entry<String, Integer> entry : pathMap.get(curr).entrySet()) {
            String next = entry.getKey();
            int newScore = score + 2 * rewardMap.get(next) + entry.getValue();;
            
            // pruning, if new score is less than previous score, just ignore
            if (newScore <= scoreMap.get(next)) 
                continue;

            scoreMap.put(next, newScore);
            dfs(curPath, ends, next, newScore);
        }

        // clear curPath
        curPath.remove(curPath.size() - 1);
    }
}