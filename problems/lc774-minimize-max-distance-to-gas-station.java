774. Minimize Max Distance to Gas Station Hard

On a horizontal number line, we have gas stations at positions stations[0], stations[1], ..., 
stations[N-1], where N = stations.length.

Now, we add K more gas stations so that D, the maximum distance between adjacent gas stations, 
is minimized.

Return the smallest possible value of D.

Example:

Input: stations = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], K = 9
Output: 0.500000


Note:
stations.length will be an integer in range [10, 2000].
stations[i] will be an integer in range [0, 10^8].
K will be an integer in range [1, 10^6].
Answers within 10^-6 of the true value will be accepted as correct.

******************************************************
key:
	- binary search
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- binary search
	  hint from "Answers within 10^-6 of the true value will be accepted as correct." Because binary 
	  search may not find exact value but it can approach the true answer.

	- initilze left = 0 and right = the distance between the first and the last station
	  count is the number of gas station we need to make it possible.
	  if count > K, it means mid is too small to realize using only K more stations.
	  if count <= K, it means mid is possible and we can continue to find a bigger one.
	  
	  When left + 1e-6 >= right, it means the answer within 10^-6 of the true value and it will be 
		accepted.

stats:

	- O(NlogM), where N is station length and M is st[N - 1] - st[0]
	- 


    public double minmaxGasDist(int[] st, int K) {
        int count, N = st.length;
        double left = 0, right = st[N - 1] - st[0], mid;

        while (left +1e-6 < right) {
            mid = (left + right) / 2;
            count = 0;
            for (int i = 0; i < N - 1; ++i)
                count += Math.ceil((st[i + 1] - st[i]) / mid) - 1;

            // check
            if (count > K) 
                left = mid;
            else 
                right = mid;
        }
        return right;
    }


=======================================================================================================
method 2:

method:

	- At first I thought this was a sequential station add problem, not concurrent station 
      add problem. Quickly you see that it is the concurrent station add problem when you try the former and it fails. The idea now is that we view each inter-station interval as having a fraction of the total ground covered. That is, we assign the extra stations to the intervals according to how much of the total ground they cover. Then, we have up to N leftover because we are always taking the floor (integer) when calculating these assignments. So we take care of the remainder greedily using priorityqueue (O(remainingmax*log (intervals)) == O(n log n)). Also I assume the stations are originally given potentially out of order, so the only way I can consider them as adjacency intervals it to sort. This also costs O(n log n).


	- 

stats:

	- 
	- 
class Solution {
    public double minmaxGasDist(int[] stations, int K) {

        Arrays.sort(stations);
        PriorityQueue<Interval> que = new PriorityQueue<Interval>(new Comparator<Interval>() {
            public int compare(Interval a, Interval b) {

                double diff = a.distance() - b.distance();
                if (diff < 0) { return +1; }
                else if (diff > 0) { return -1; }
                else { return 0; }
            }
        });

        double leftToRight = stations[stations.length-1] - stations[0];
        int remaining = K;

        for (int i = 0; i < stations.length-1; i++) {
            int numInsertions = (int)(K*(((double)(stations[i+1]-stations[i]))/leftToRight));
            que.add(new Interval(stations[i], stations[i+1], numInsertions));
            remaining -= numInsertions;
        }

        while (remaining > 0) {
            Interval interval = que.poll();
            interval.numInsertions++;
            que.add(interval);
            remaining--;
        }

        Interval last = que.poll();
        return last.distance();

    }

    class Interval {
        double left;
        double right;
        int numInsertions;
        double distance() { return (right - left)/  ((double)(numInsertions+1)) ; }
        Interval(double left, double right, int numInsertions) { this.left = left; this.right = right; this.numInsertions = numInsertions; }
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



