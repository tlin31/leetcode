1024. Video Stitching - Medium


You are given a series of video clips from a sporting event that lasted T seconds.  These video clips 
can be overlapping with each other and have varied lengths.

Each video clip clips[i] is an interval: it starts at time clips[i][0] and ends at time clips[i][1].  
We can cut these clips into segments freely: for example, a clip [0, 7] can be cut into segments 
[0, 1] + [1, 3] + [3, 7].

Return the minimum number of clips needed so that we can cut the clips into segments that cover the 
entire sporting event ([0, T]).  If the task is impossible, return -1.

 

Example 1:

Input: clips = [[0,2],[4,6],[8,10],[1,9],[1,5],[5,9]], T = 10
Output: 3

Explanation: 
We take the clips [0,2], [8,10], [1,9]; a total of 3 clips.
Then, we can reconstruct the sporting event as follows:
We cut [1,9] into segments [1,2] + [2,8] + [8,9].
Now we have segments [0,2] + [2,8] + [8,10] which cover the sporting event [0, 10].


Example 2:

Input: clips = [[0,1],[1,2]], T = 5
Output: -1
Explanation: 
We can not cover [0,5] with only [0,1] and [0,2].


Example 3:

Input: clips = [[0,1],[6,8],[0,2],[5,6],[0,4],
				[0,3],[6,7],[1,3],[4,7],[1,4],[2,5],[2,6],[3,4],[4,5],[5,7],[6,9]], T = 9
Output: 3
Explanation: 
We can take clips [0,4], [4,7], and [6,9].


Example 4:

Input: clips = [[0,4],[2,8]], T = 5
Output: 2
Explanation: 
Notice you can have extra video after the event ends.
 

Note:

1 <= clips.length <= 100
0 <= clips[i][0], clips[i][1] <= 100
0 <= T <= 100




******************************************************
key:
	- greedy
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:Sort + greedy


Stats:

	- Time O(NlogN), Space O(1)
	- 


Method:

	1. sort the array  and fill in the max end value of each starting point
		ex.if there is [0, 1], [0, 2], [0, 8] then we will fill 0 with value 8

	2. early exit if it doesnt start from 0

	3. based on the end of previous interval, find the next interval which can be merged with previous
		ex. currently we have [0, 4], options can be [2, 8], [4, 6], [5, 7], 4 as starting point, and 
			go to left to find if there is greater interval choice and finally we choose [2, 8] as 
			new beginning
	
	4. If in the process we cannot proceed anymore then simply exit

	

   int videoStitching(int[][] clips, int T) {
        Arrays.sort(clips, (c1, c2) -> c1[0] != c2[0] ? c1[0] - c2[0] : c1[1] - c2[1]);

        // store the intervals
        int[] maxes = new int[100];
        for (int[] clip : clips) {
            maxes[clip[0]] = clip[1];
        }

        // early exit
        if (maxes[0] == 0) 
        	return -1;

        int count = 1;
        int start = 0, end = maxes[0];
        while (start < T && end < T) {
            int max = 0, 
            	maxIndex = -1;

            // go to left to find the longest covered interval
            for (int j = end; j > start; j--) {
                if (maxes[j] > max) {
                    max = maxes[j];
                    maxIndex = j;
                }
            }

            // can't find an interval overlap with the first [0, x]
            if (maxIndex == -1 || max <= end) 
            	return -1;

            count++;
            start = maxIndex;
            end = maxes[start];
        }

        return count;
    }

=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	- DP
		Loop on i form 0 to T,
		loop on all clips,
		If clip[0] <= i <= clip[1], we update dp[i]

Time O(NT), Space O(T)

Java

    public int videoStitching(int[][] clips, int T) {
        int[] dp = new int[T + 1];
        Arrays.fill(dp, T + 1);
        dp[0] = 0;
        for (int i = 1; i <= T && dp[i - 1] < T; i++) {
            for (int[] c : clips) {
                if (c[0] <= i && i <= c[1])
                    dp[i] = Math.min(dp[i], dp[c[0]] + 1);
            }
        }
        return dp[T] == T + 1 ? -1 : dp[T];
    }


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



