1306. Jump Game III - Medium

Given an array of non-negative integers arr, you are initially positioned at start index of the array. When you are at index i, you can jump to i + arr[i] or i - arr[i], check if you can reach any index with value 0.

Notice that you can not jump outside of the array at any time.

 

Example 1:

Input: arr = [4,2,3,0,3,1,2], start = 5
Output: true
Explanation: 
All possible ways to reach at index 3 with value 0 are: 
index 5 -> index 4 -> index 1 -> index 3 
index 5 -> index 6 -> index 4 -> index 1 -> index 3 
Example 2:

Input: arr = [4,2,3,0,3,1,2], start = 0
Output: true 
Explanation: 
One possible way to reach at index 3 with value 0 is: 
index 0 -> index 4 -> index 1 -> index 3
Example 3:

Input: arr = [3,0,2,1,2], start = 2
Output: false
Explanation: There is no way to reach at index 1 with value 0.
 

Constraints:

1 <= arr.length <= 5 * 104
0 <= arr[i] < arr.length
0 <= start < arr.length


******************************************************
key:
	- bfs、dfs、 recursion
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

	-	bfs



Stats:

	- O(N) & O(n)
	- 

class Solution {
    public boolean canReach(int[] arr, int start) {
        if(arr==null || arr.length==0) return false;

        int n = arr.length;


        Queue<Integer> q = new LinkedList<>();
        
        q.offer(start);

        while(!q.isEmpty()){
            if(arr[q.peek()]==0) return true;

            int cur = q.poll();
            if(arr[cur]<0) continue;

            if(cur+arr[cur]<n) {
                q.offer(cur+arr[cur]);
            }
            if(cur-arr[cur]>=0) {
                q.offer(cur-arr[cur]);

            }
            arr[cur] = -arr[cur];
        }

        return false;


    }
}



recursion:

	public boolean canReach(int[] arr, int start) {
        int[] visited = new int[arr.length];
        return jumpGame(arr, start,visited); 
    }
     
   public boolean jumpGame(int[] arr, int start,int[] visited)
   {
       if(start >= arr.length)
            return false;
        
        if(start < 0)
            return false;
        
        if(arr[start] == 0)
            return true;
        
        if(visited[start] == 1)
            return false;
        
        visited[start] = 1;       
        return jumpGame(arr,start+arr[start],visited) || jumpGame(arr,start-arr[start],visited);
   }





