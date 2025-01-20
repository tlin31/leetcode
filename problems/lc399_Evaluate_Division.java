399. Evaluate Division - Medium


You are given an array of variable pairs equations and an array of real numbers values, where equations[i] = [Ai, Bi] and values[i] represent the equation Ai / Bi = values[i]. Each Ai or Bi is a string that represents a single variable.

You are also given some queries, where queries[j] = [Cj, Dj] represents the jth query where you must find the answer for Cj / Dj = ?.
a --2--> b --3--> c
We simply find a path using DFS from node a to node c and multiply the weights of edges passed, i.e. 2 * 3 = 6.

Please note that during DFS,

Rejection case should be checked before accepting case.
Accepting case is (graph.get(u).containsKey(v)) rather than (u.equals(v)) for it takes O(1) but (u.equals(v)) takes O(n) for n is the length of the longer one between u and v.

Return the answers to all queries. If a single answer cannot be determined, return -1.0.

Note: The input is always valid. You may assume that evaluating the queries will not result in division by zero and that there is no contradiction.

Note: The variables that do not occur in the list of equations are undefined, so the answer cannot be determined for them.

 

Example 1:

Input: equations = [["a","b"],["b","c"]], values = [2.0,3.0], queries = [["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]
Output: [6.00000,0.50000,-1.00000,1.00000,-1.00000]
Explanation: 
Given: a / b = 2.0, b / c = 3.0
queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? 
return: [6.0, 0.5, -1.0, 1.0, -1.0 ]
note: x is undefined => -1.0
Example 2:

Input: equations = [["a","b"],["b","c"],["bc","cd"]], values = [1.5,2.5,5.0], queries = [["a","c"],["c","b"],["bc","cd"],["cd","bc"]]
Output: [3.75000,0.40000,5.00000,0.20000]
Example 3:

Input: equations = [["a","b"]], values = [0.5], queries = [["a","b"],["b","a"],["a","c"],["x","y"]]
Output: [0.50000,2.00000,-1.00000,-1.00000]
 

Constraints:

1 <= equations.length <= 20
equations[i].length == 2
1 <= Ai.length, Bi.length <= 5
values.length == equations.length
0.0 < values[i] <= 20.0
1 <= queries.length <= 20
queries[i].length == 2
1 <= Cj.length, Dj.length <= 5
Ai, Bi, Cj, Dj consist of lower case English letters and digits.



******************************************************
key:
	- a --2--> b --3--> c
We simply find a path using DFS from node a to node c and multiply the weights of edges passed, i.e. 2 * 3 = 6.

Please note that during DFS,
	Rejection case should be checked before accepting case.
	Accepting case is (graph.get(u).containsKey(v)) rather than (u.equals(v)) for it takes O(1) but (u.equals(v)) takes O(n) for n is the length of the longer one between u and v.

	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

	-	



Stats:

	- 
	- 

	public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        // build big double direction map
        HashMap<String, HashMap<String, Double>> map = new HashMap<>();
        for (int i = 0; i < equations.size(); i++) {
            String start = equations.get(i).get(0);
            String end = equations.get(i).get(1);
            double ratio = values[i];
            map.putIfAbsent(start, new HashMap<>());
            map.get(start).put(end, ratio);
            map.putIfAbsent(end, new HashMap<>());
            map.get(end).put(start, 1.0 / ratio);
        }
        // deal with every query
        double[] res = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String start = queries.get(i).get(0);
            String end = queries.get(i).get(1);
            // first check if start or end exist in big map or not
            if (!map.containsKey(start) || !map.containsKey(end)) {
                res[i] = -1.0;
                continue;
            }
            // enter dfs loop, for each query, there is a new visited set
            res[i] = helper(map, start, end, new HashSet<>());
        }
        return res;
    }
    public double helper(HashMap<String, HashMap<String, Double>> map, String start, String end, HashSet<String> visited) {
        // actually no need to check quitting condition, because if one can enter this dfs, one must iterate all children
        // if (!map.containsKey(start)) return -1;

        if (map.get(start).containsKey(end)) {
            return map.get(start).get(end);
        }

        // mark visited
        visited.add(start);
        for (Map.Entry<String, Double> entry : map.get(start).entrySet()) {
            if (visited.contains(entry.getKey())) continue;
            double res = helper(map, entry.getKey(), end, visited);
            if (res == -1.0) continue;
            return res * entry.getValue();
        }
        return -1.0;
    }



===================================================================================================
Method 2: union find

	-	



Stats:

	- Time complexity is 0(e + q) where e is the number of equations and q is the number of queries since Union and find operations could be considered constant time if using rank and path compression.


	- 
	a / b = 2.0
	==> b is the parent of a and map.put(a, 2.0)
	==> a = root.get(a) * map.get(a);
	"root" restores the parent of the node; "map" restores factor. The formula is "node = parent * factor"
	For example, "x / y = 2.0". Here, y is the parent of x; and the factor is 2.0.
	If we also have "y / z = 3.0", which means that z is the final parent of x due to path compression; and the factor turns out to be 6.0.

	When we find the parent of the string, we also accumulately multiply the factors

/**
    1. Thoughts
        - check if we have enough info to get the result
        - if yes, calculate; if not, return -1.0
        - Method: union find
            - a/b = 2.0 --> b is the root of a; the distance from a to b is 1/2.0
            - if two nums have the same root, we can get the result; a/b=2.0, b/c=3.0
            index   a   b   c
            root    b   c   c 
            dist    2   3   1
            - if we want to know a/c = ?: a = 2 * b = 2 * 3 * c => a/c = 6.0
    2. Corner case
        - if any input is null, return null
        - no enough info, return -1.0
    3. Steps
        - go through equations to union elements with the same root and update root map and distance map
        - go through each query: check if has the same root; find relative dist
*/
class Solution {
    Map<String, String> parents = new HashMap();
    Map<String, Double> valueMap = new HashMap();
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Set<String> zeros = new HashSet<>();
        for( int i = 0; i < equations.size(); i++ ){
            if( values[i] == 0 ){
                zeros.add(equations.get(i).get(0));  
                continue;
            }
            union(equations.get(i).get(0), equations.get(i).get(1), values[i]);
        }
        
        List<Double> res = new LinkedList<>();
        for(List<String> q : queries){
            if(!parents.containsKey(q.get(0))||!parents.containsKey(q.get(1))) 
            	res.add( -1.0 );
            else if(zeros.contains(q.get(0))){
                res.add(0.0);
            }else if(zeros.contains(q.get(1))){
                res.add(-1.0);
            }else if(!find(q.get(0)).equals(find(q.get(1)))) 
                res.add(-1.0);
            else{
                res.add(valueMap.get(q.get(0))/valueMap.get(q.get(1)));
            }
        }
        double[] resArray = new double[res.size()];
        for( int i=0; i<res.size(); i++ ) resArray[i] = res.get(i);
        return resArray;
        //return res.toArray(new Double[res.size()]);
    }
    
    private String find(String s){
        if(parents.containsKey(s) && !parents.get(s).equals(s)){
            String p = parents.get(s);
            parents.put(s, find(p));
            valueMap.put(s, valueMap.getOrDefault(s, 1.0) * valueMap.get(p));
        }else{
            parents.put(s, s);
            valueMap.put(s, 1.0);
        }
        return parents.get(s);
    }
    
    private void union(String s1, String s2, double val){ // val is not 0
        String p1 = find(s1), p2 = find(s2);
        if(p1.compareTo(p2) > 0){
            parents.put(p1,p2);
            valueMap.put(p1, valueMap.get(s2) / valueMap.get(s1) * val);
        }else{
            parents.put(p2,p1);
            valueMap.put(p2, valueMap.get(s1) / valueMap.get(s2) / val);
        }
    }
}

