1345. Jump Game IV - Hard

Given an array of integers arr, you are initially positioned at the first index of the array.

In one step you can jump from index i to index:

i + 1 where: i + 1 < arr.length.
i - 1 where: i - 1 >= 0.
j where: arr[i] == arr[j] and i != j.
Return the minimum number of steps to reach the last index of the array.

Notice that you can not jump outside of the array at any time.

 

Example 1:

Input: arr = [100,-23,-23,404,100,23,23,23,3,404]
Output: 3
Explanation: You need three jumps from index 0 --> 4 --> 3 --> 9. Note that index 9 is the last index of the array.
Example 2:

Input: arr = [7]
Output: 0
Explanation: Start index is the last index. You do not need to jump.
Example 3:

Input: arr = [7,6,9,6,9,6,9,7]
Output: 1
Explanation: You can jump directly from index 0 to index 7 which is last index of the array.
 

Constraints:

1 <= arr.length <= 5 * 104
-108 <= arr[i] <= 108


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method: BFS + HashMap + 剪枝

把数组下标当作图的节点，用 BFS 找最短路径；
用 value → indices 映射，并在访问后 立刻清空 来避免 TLE。

时间复杂度 O(n)，空间 O(n)。



访问一次后立刻清空（最关键）
map.get(arr[i]).clear();


👉 含义：

同一个值的“同值跳”只允许用一次

否则会反复把相同 index 加入队列，直接炸。


Stats:

	- 
	- 

class Solution {
    public int minJumps(int[] arr) {
        // bfs

        int n = arr.length;
        if (n==1) return 0;
        int res = 0;
        // value -> all indices
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
        }

        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[n];
        q.offer(0);
        visited[0] = true;

        int steps = 0;

        while(!q.isEmpty()){
            int size = q.size();

            // iterate through all nodes at this level
            for(int i = 0; i<size;i++){
                int index = q.poll();
                if(index==n-1) {
                    //update res, then continue to finish traversal
                    return steps;
                }

                if(index+1<n && !visited[index+1]){
                    q.offer(index+1);
                    visited[index+1] = true;
                }
                if(index-1>=0 && !visited[index - 1]){
                    q.offer(index-1);
                    visited[index-1] = true;

                }
                List<Integer> list = map.get(arr[index]);
                if(list!=null){
                    for(int next:list){
                        if (!visited[next]) {
                            visited[next] = true;
                            q.offer(next);
                        }
                    }
                    list.clear();
                }

            }
            steps++;
            
        }

        return res;
    }
}

