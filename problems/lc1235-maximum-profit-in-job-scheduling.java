1235. Maximum Profit in Job Scheduling - Hard


We have n jobs, where every job is scheduled to be done from startTime[i] to endTime[i], obtaining 
a profit of profit[i].

You are given the startTime , endTime and profit arrays, you need to output the maximum profit you can
take such that there are no 2 jobs in the subset with overlapping time range.

If you choose a job that ends at time X you will be able to start another job that starts at time X.

 

Example 1:

Input: startTime = [1,2,3,3], endTime = [3,4,5,6], profit = [50,10,40,70]
Output: 120
Explanation: The subset chosen is the first and fourth job. 
Time range [1-3]+[3-6] , we get profit of 120 = 50 + 70.


Example 2:

Input: startTime = [1,2,3,4,6], endTime = [3,5,10,6,9], profit = [20,20,100,70,60]
Output: 150
Explanation: The subset chosen is the first, fourth and fifth job. 
Profit obtained 150 = 20 + 70 + 60.


Example 3:

Input: startTime = [1,1,1], endTime = [2,3,4], profit = [5,6,4]
Output: 6
 

Constraints:

1 <= startTime.length == endTime.length == profit.length <= 5 * 10^4
1 <= startTime[i] < endTime[i] <= 10^9
1 <= profit[i] <= 10^4


******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: knapsack DP


Stats:

		Time O(NlogN) for sorting
		Time O(NlogN) for binary search for each job
		Space O(N)


Method:

	-	Sort the jobs by endTime.
	-	dp[time] = profit means that within the first time duration, we cam make at most profit money.
		
		Intial dp[0] = 0, as we make profit = 0 at time = 0.

		For each job = [s, e, p], where s,e,p are its start time, end time and profit,
			If we do not do this job, nothing will be changed.
			If we do this job, binary search in the dp to find the largest profit before start time s.

	-	Compare with last element in the dp, if we make more money --> it worth doing this job,
		then we add the pair of [e, cur] to the back of dp.
		Otherwise, we would like not to do this job.


=== TreeMap =======
	public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][3];

        for (int i = 0; i < n; i++) {
            jobs[i] = new int[] {startTime[i], endTime[i], profit[i]};
        }

        Arrays.sort(jobs, (a, b)->a[1] - b[1]);

        // dp stores <end time, profit>
        TreeMap<Integer, Integer> dp = new TreeMap<>();
        dp.put(0, 0);

        for (int[] job : jobs) {
            // floorEntry()gets the max key&value pair that's smaller than job[0]
            // It returns a key-value mapping associated with the greatest key less than or equal 
            // to the given key, or null if there is no such key.

            int curProfit = dp.floorEntry(job[0]).getValue() + job[2];

            if (curProfit > dp.lastEntry().getValue())
                dp.put(job[1], curProfit);
        }

        return dp.lastEntry().getValue();
    }


==== object ======

public class JobScheduling {

    private class Job {
        int start, finish, profit;
        Job(int start, int finish, int profit) {
            this.start = start;
            this.finish = finish;
            this.profit = profit;
        }
    }

    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        Job[] jobs = new Job[n];
        for(int i=0;i<n;i++) {
            jobs[i] = new Job(startTime[i],endTime[i],profit[i]);
        }
        return scheduleApt(jobs);
    }

    private int scheduleApt(Job[] jobs) {
        // Sort jobs according to finish time
        Arrays.sort(jobs, Comparator.comparingInt(a -> a.finish));

        // dp[i] stores the profit for jobs till jobs[i] (including jobs[i])
        int n = jobs.length;
        int[] dp = new int[n];
        dp[0] = jobs[0].profit;
        for (int i=1; i<n; i++) {
            // Profit including the current job
            int profit = jobs[i].profit;
            int l = search(jobs, i);
            if (l != -1)
                profit += dp[l];

            // Store maximum of including and excluding
            dp[i] = Math.max(profit, dp[i-1]);
        }

        return dp[n-1];
    }

    //binary search
    private int search(Job[] jobs, int index) {
        int start = 0, end = index - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (jobs[mid].finish <= jobs[index].start) {
                if (jobs[mid + 1].finish <= jobs[index].start)
                    start = mid + 1;
                else
                    return mid;
            }
            else
                end = mid - 1;
        }
        return -1;
    }

}





~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

 def jobScheduling(self, startTime, endTime, profit):
        jobs = sorted(zip(startTime, endTime, profit), key=lambda v: v[1])
        dp = [[0, 0]]
        for s, e, p in jobs:
            i = bisect.bisect(dp, [s + 1]) - 1
            if dp[i][1] + p > dp[-1][1]:
                dp.append([e, dp[i][1] + p])
        return dp[-1][1]



