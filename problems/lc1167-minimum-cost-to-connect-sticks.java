1167. Minimum Cost to Connect Sticks - Medium

You have some sticks with positive integer lengths.

You can connect any two sticks of lengths X and Y into one stick by paying a cost of X + Y.  
You perform this action until there is one stick remaining.

Return the minimum cost of connecting all the given sticks into one stick in this way.

 

Example 1:

Input: sticks = [2,4,3]
Output: 14

Example 2:
Input: sticks = [1,8,3,5]
Output: 30
 

Constraints:

1 <= sticks.length <= 10^4
1 <= sticks[i] <= 10^4


******************************************************
key:
	- greedy
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- Earlier sticks will be counted again. Therefore, alwasy use current shortest two sticks 
	  till only one remains.
	- We can solve this using min heap. On every step we need to take two most cheapest sticks 
	  and join them. 
	  We poll two top (most cheap) elements, add them, add to the result and put the sum 
	  back to the min heap cause this is our new stick.



stats:

	- Time: O(nlogn), where n = sticks.length.
	- space: O(n)


    public int connectSticks(int[] sticks) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int s : sticks) {
            pq.offer(s);
        }
        int sum = 0;
        while (pq.size() > 1) {
            int two = pq.poll() + pq.poll();
            sum += two;
            pq.offer(two);
        }
        return sum;
    }

=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



