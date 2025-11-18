875. Koko Eating Bananas - Medium

Koko loves to eat bananas.  There are N piles of bananas, the i-th pile has piles[i] bananas.  
The guards have gone and will come back in H hours.

Koko can decide her bananas-per-hour eating speed of K.  Each hour, she chooses some pile of bananas, 
and eats K bananas from that pile.  If the pile has less than K bananas, she eats all of them instead, 
and will not eat any more bananas during this hour.

Koko likes to eat slowly, but still wants to finish eating all the bananas before the guards come back.

Return the minimum integer K such that she can eat all the bananas within H hours.

 

Example 1:

Input: piles = [3,6,7,11], H = 8
Output: 4

Example 2:
Input: piles = [30,11,23,4,20], H = 5
Output: 30


Example 3:
Input: piles = [30,11,23,4,20], H = 6
Output: 23
 

Note:

1 <= piles.length <= 10^4
piles.length <= H <= 10^9
1 <= piles[i] <= 10^9

******************************************************
key:
	- binary search， set a close bound for left and right
	- edge case:
		1) empty list, return 0
		2) H = 0, return ????

******************************************************


class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int n = piles.length;
        long total = 0;
        for(int p:piles){
            total += p;
        }

        //最少一小时吃了1个香蕉，最后+1是以防前面的得出零点几，至少是1
        int low = (int)((total-1)/h) + 1;

        //如果length中每一个pile用一个小时吃，也要用这么多
        int high = (int)((total-n)/(h-n+1)) + 1;

        while(low<high){
            int mid = low+(high-low)/2;
            int time = 0;
            for(int p: piles){
                time += (p-1)/mid +1;
            }

            if(time>h)  
                low = mid+1;
            else high = mid; 
        }
        return low;
    }
}


=======================================================================================================
method 1:

    - Runtime: 8 ms, faster than 83.97% of Java online submissions for Koko Eating Bananas.
    - Memory Usage: 38.8 MB, less than 100.00% of Java online submissions for Koko Eating Bananas.

   public int minEatingSpeed(int[] piles, int H) {
        int lo = 1, hi = getMaxPile(piles);
        
        // Binary search to find the smallest valid K.
        while (lo <= hi) {
            int K = lo + ((hi - lo)/2);
            if (canEatAll(piles, K, H)) {
                hi = K - 1;
            } else {
                lo = K + 1;
            }
        }
        
        return lo;
    }
    
    private boolean canEatAll(int[] piles, int K, int H) {
        int countHour = 0; // Hours take to eat all bananas at speed K.
        
        for (int pile : piles) {
            countHour += pile / K;
            if (pile % K != 0)
                countHour++;
        }
        return countHour <= H;
    }
    
    private int getMaxPile(int[] piles) {
        int maxPile = Integer.MIN_VALUE;
        for (int pile : piles) {
            maxPile = Math.max(pile, maxPile);
        }
        return maxPile;
    }



=======================================================================================================
method 2:

method:

	- 

stats:

method:

    - Runtime: 13 ms, faster than 41.21% of Java online submissions for Koko Eating Bananas.
    - Memory Usage: 39.1 MB, less than 100.00%


stats:

    - Time Complexity: O(NlogW), where N is the number of piles, and W is the maximum size of a pile.

    - Space Complexity: O(1).


    public int minEatingSpeed(int[] piles, int H) {
        int l = 1, r = 1000000000;
        while (l < r) {
            int m = (l + r) / 2, total = 0;
            for (int p : piles) total += (p + m - 1) / m;
            if (total > H) l = m + 1; else r = m;
        }
        return l;
    }


=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 




