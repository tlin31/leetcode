class Result {

    /*
     * Complete the 'primeQuery' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER_ARRAY first
     *  3. INTEGER_ARRAY second
     *  4. INTEGER_ARRAY values
     *  5. INTEGER_ARRAY queries
     */

    public static List<Integer> primeQuery(int n, List<Integer> first, List<Integer> second, List<Integer> values, List<Integer> queries) {
    // Write your code here
        Result res = new Result();
        List<Integer> result = new ArrayList<>();
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < first.size(); i++) {
            if (graph.get(first.get(i)) == null) {
                List<Integer> list = new ArrayList<>();
                list.add(second.get(i));
                graph.put(first.get(i), list);
            } else {
                List<Integer> list = graph.get(first.get(i));
                list.add(second.get(i));
            }

            if (graph.get(second.get(i)) == null) {
                List<Integer> list = new ArrayList<>();
                list.add(first.get(i));
                graph.put(second.get(i), list);
            } else {
                List<Integer> list = graph.get(second.get(i));
                list.add(first.get(i));
            }
        }

        res.dfs0(graph, -1, 1);
        int[] visited = new int[n + 1];
        Arrays.fill(visited, -1);
        for (int query : queries) {
            if (visited[query] == -1) {
                result.add(res.dfs(graph, query, visited, values));
            } else {
                result.add(visited[query]);
            }
        }
        return result;
    } 

    private int dfs(Map<Integer, List<Integer>> graph, int cur, int[] visited, List<Integer> values) {
        if (visited[cur] == -1) {
            visited[cur] = 0;
            if (isPrime(values.get(cur - 1))) {
                visited[cur] += 1;
            }

            for (int next : graph.get(cur)) {
                visited[cur] += dfs(graph, next, visited, values);
            }
        }

        return visited[cur];
    }

    private void dfs0(Map<Integer, List<Integer>> graph, int parent, int cur) {
        for (int next : graph.get(cur)) {
            if (parent != next) {
                dfs0(graph, cur, next);
            }
        }
        graph.get(cur).remove(new Integer(parent));
    }
    

    private boolean isPrime(int val) {
        if (val == 1) {
            return false;
        }
        for (int i = 2; i < val; i++) {
            if (val % i == 0) {
                return false;
            }
        }
        return true;
    }
}