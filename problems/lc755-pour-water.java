755. Pour Water - Medium

We are given an elevation map, heights[i] representing the height of the terrain at that index. The width at each index is 1. After V units of water fall at index K, how much water is at each index?

Water first drops at index K and rests on top of the highest terrain or water at that index. Then, it flows according to the following rules:

If the droplet would eventually fall by moving left, then move left.
Otherwise, if the droplet would eventually fall by moving right, then move right.
Otherwise, rise at it's current position.
Here, "eventually fall" means that the droplet will eventually be at a lower level if it moves in that direction. Also, "level" means the height of the terrain plus any water in that column.
We can assume there's infinitely high terrain on the two sides out of bounds of the array. Also, there could not be partial water being spread out evenly on more than 1 grid block - each unit of water has to be in exactly one block.


往一个int array 代表海拔的格子里倒水，打印出倒水后的图， 输入：int[] 海拔， int 水数量， int 倒得位置。


我的assumption：
（0）handle the water drop by drop。 there are infinitely high walls on the left and right
（1）水先向左走，走到不能走为止。call it leftMost  
（2）if leftmost < start，leftMost水+1，done
（3）if leftmost >= start，水向右走，走到不能走为止。call it rightMost
（4）if rightmost < start，rightMost水+1，done
（5）if rightmost >= start，leftMost水+1，done

******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************


/*
Assumption:
1. input is array of integer, represents the height of ground
2. print the graph after water drop
3. water will go out from left side and right side
4. water first go left to drop, if can not find a place, go right
5. otherwise, drop at original place

Simulate the drop one by one

##wwwww###
###w##w###
##########
##########

Time: O(nk) + O(nh)
k is the number of drop, n is the length of the array.
drop water is O(nk), print graph takes O(nh) where h is the max number of the array
Space: O(n) to store the water
 */


=======================================================================================================
method 1:

method:

	- （0）handle the water drop by drop。 there are infinitely high walls on the left and right
		（1）水先向左走，走到不能走为止。call it leftMost  
		（2）if leftmost < start，leftMost水+1，done
		（3）if leftmost >= start，水向右走，走到不能走为止。call it rightMost
		（4）if rightmost < start，rightMost水+1，done
		（5）if rightmost >= start，leftMost水+1，done
	- 

stats:

	- Given a flat starting terrain where heights are all 0, each drop takes a average of N iterations. 
	  (And first drop even takes 2N steps). If you have n drops, this is O(n^2)
	- O(V * 2L) = O(V*L)

import java.util.*;


class Solution {
	public static void main(String[] args) {
        Solution sol = new Solution();
        int[] heights = {2,1,1,2,1,2,2};
        sol.pourWater(heights, 3, 4);
    }

    public void pourWater(int[] heights, int K, int V) {

        int[] water = new int[heights.length];

    	print(heights,water);

    	// each waterdrop
        for(int i = 0; i < V; i++) {
            int curPosition = K;

            // Move left if cur >= left, then there's a fall to the left
            while(curPosition > 0 && heights[curPosition] + water[curPosition] >= heights[curPosition - 1]+water[curPosition-1]) {
                curPosition--;
                // System.out.println("left -- at drop " + i + " the curPosition is : " + curPosition);
            }

            // Move right
            while(curPosition < heights.length - 1 && heights[curPosition]+ water[curPosition] >= heights[curPosition + 1]+ water[curPosition+1]) {
                curPosition++;

            }

            // Move left to K, if you cannot move right and eventually fall, you'll need to stay at K
			while(curPosition > K && heights[curPosition] + water[curPosition] == heights[curPosition - 1]+ water[curPosition-1]){
                curPosition--;
            }

            water[curPosition]++;
            // System.out.println("------------- new round ------------");

        }
        print(heights,water);

    }

    public void print(int[] heights, int[] water) {
        int max = 0;

        // get the max of entire sink
        for (int i = 0; i < heights.length; i++) {
            max = Math.max(max, heights[i] + water[i]);
        }

        for (int i = max; i > 0; i--) {

        	// print from left, 一层一层print
            for (int j = 0; j < heights.length; j++) {
                if (heights[j] + water[j] < i) {
                    System.out.print(" ");
                } 
                // total height >= max, and without water it's smaller than i
                else if (heights[j] + water[j] >= i && heights[j] < i){
                    System.out.print("w");
                } else {
                    System.out.print("#");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}



Example output:
#  # ##
#######

at drop 0 curPosition is: 2 water is 0
left -- at drop 0 the curPosition is : 2
left -- at drop 0 the curPosition is : 1
right -- at drop 0 the curPosition is : 2
------------- new round ------------
at drop 1 curPosition is: 2 water is 0
left -- at drop 1 the curPosition is : 2
left -- at drop 1 the curPosition is : 1
------------- new round ------------
at drop 2 curPosition is: 2 water is 0
left -- at drop 2 the curPosition is : 2
left -- at drop 2 the curPosition is : 1
left -- at drop 2 the curPosition is : 0
right -- at drop 2 the curPosition is : 1
right -- at drop 2 the curPosition is : 2
right -- at drop 2 the curPosition is : 3
right -- at drop 2 the curPosition is : 4
------------- new round ------------
at drop 3 curPosition is: 2 water is 0
left -- at drop 3 the curPosition is : 2
left -- at drop 3 the curPosition is : 1
left -- at drop 3 the curPosition is : 0
right -- at drop 3 the curPosition is : 1
right -- at drop 3 the curPosition is : 2
right -- at drop 3 the curPosition is : 3
right -- at drop 3 the curPosition is : 4
right -- at drop 3 the curPosition is : 5
right -- at drop 3 the curPosition is : 6
stay in mid -- at drop 3 the curPosition is : 5
stay in mid -- at drop 3 the curPosition is : 4
stay in mid -- at drop 3 the curPosition is : 3
------------- new round ------------
   w   
#ww#w##
#######

the water level at 0 is: 0
the water level at 1 is: 1
the water level at 2 is: 1
the water level at 3 is: 1
the water level at 4 is: 1
the water level at 5 is: 0
the water level at 6 is: 0


=======================================================================================================
method 2:

method:

	- store in stacks (one for each direction) all the indices where a drop in height occurs 
	  (until there is an strict increase in height). 
	- Note that the inner while loop never re-runs from K; they always continue from i, j. 
	  Therefore, the inner 2 loops should not run more than O(n). All other updates are constant time 
	  per iteration. => O(n) total

	- The idea here is we use 2 stacks to keep track of the first drop off points going left and right, 
	  respectively. The water being poured will fill these drop off points first. As these points are 
	  filled up, we pop them if they are no longer the first drop off point (some point before is now 
	  the new drop off point).
	 - We might also need to add the new dropoff point (the point after current dropoff).


stats:

	- This is O(VN) since you have to scan left or right each time. You can get this to O(V*LogN) 
	  by using a priority queue instead of stack.


public void scanLeft(Stack<Integer> left, int[] heights, int start) {
        for (int i = start - 1; i >= 0 && heights[i] <= heights[i + 1]; --i)
            if (heights[i] < heights[i + 1])
                left.push(i);
    }
    
    public void scanRight(Stack<Integer> right, int[] heights, int start) {
        for (int i = start + 1; i < heights.length && heights[i] <= heights[i - 1]; ++i)
            if (heights[i] < heights[i - 1])
                right.push(i);
    }
    
    public int[] pourWater(int[] heights, int V, int K) {
        Stack<Integer> left = new Stack<>(), right = new Stack<>();
        
        scanLeft(left, heights, K);
        scanRight(right, heights, K);
        
        for (int v = 0; v < V; ++v) {
            if (!left.empty()) {
                int current = left.pop();  // it must be that current < K and heights[current] < heights[K]
                heights[current] += 1;
                
                scanLeft(left, heights, current + 1);
            }
            else if (!right.empty()) {
                int current = right.pop();  // it must be that current > K and heights[current] < heights[K]
                heights[current] += 1;
                
                scanRight(right, heights, current - 1);
            }
            else {
                heights[K] += 1;
        
                scanLeft(left, heights, K);
                scanRight(right, heights, K);
            }
        }
        
        return heights;
    }

=======================================================================================================

Method 3:
	- use 2 PriorityQueue

stats:
	- O(N lg N + V lg N) = O((N+V) * lg N). The "add" operation in pq will execute at most N times. Hence, 
	  the total runtime inside the while loops is going to be O(N log N). We also "remove" and "add" 
	  from priority queue V times, hence O(V log N). 
	- in java, priority queue is binary heap and heapify procedure will take O(lg n).



class Solution {
	// V = volumn of water, K = position
    public int[] pourWater(int[] heights, int V, int K) {
        PriorityQueue<Integer> pq1 = new PriorityQueue<Integer>(10, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
            	// returns an originally Integer value as int
                int n1 = i1.intValue();
                int n2 = i2.intValue();

                // smaller heights at front
                if(heights[n1] - heights[n2] != 0) 
                	return heights[n1] - heights[n2];

                // if same height, bigger index at front
                return n2 - n1;
            }
        });
        
        PriorityQueue<Integer> pq2 = new PriorityQueue(10, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                int n1 = i1.intValue();
                int n2 = i2.intValue();
                if (heights[n1] - heights[n2] != 0) 
                	return heights[n1] - heights[n2];
                return n1 - n2;
            }
        });
        
        int leftPtr = K - 1, 
            rightPtr = K + 1;

        for (int v = 0; v < V; v++) {

        	// move left, add all potential falls, in pq, organize by heights
            while (leftPtr >= 0 && heights[leftPtr] <= heights[leftPtr + 1]) 
            	pq1.add(leftPtr--);

            // move right, there's a fall in right
            while (rightPtr < heights.length && heights[rightPtr] <= heights[rightPtr - 1]) 
            	pq2.add(rightPtr++);

            // check left first
            if (pq1.size() != 0 && heights[pq1.peek()] < heights[K]) {
                int index = pq1.poll();

                heights[index] += 1;
                pq1.add(index);
            } else if (pq2.size() != 0 && heights[pq2.peek()] < heights[K]) {
                int index = pq2.poll();
                heights[index] += 1;
                pq2.add(index);
            } else {
                heights[K] += 1;
            }
        }
        
        return heights;
    }
}


=======================================================================================================

class Solution {
    public int[] pourWater(int[] heights, int V, int K) {
        while (V-- > 0)
            drop(heights, K, heights[K] + 1, true, true);
        return heights;
    }
   private boolean drop(int[] h, int i, int j, boolean l, boolean r) {

   		// to left
        if (l && i > 0 && h[i - 1] <= h[i] 
        	&& drop(h, i - 1, h[i], l, false)) 
        	return true;

        // to right
        if (r && i < h.length - 1 && h[i + 1] <= h[i] 
        	&& drop(h, i + 1, h[i], false, r)) 
        	return true;

        if (h[i] == j) 
        	return false;

        h[i]++;
        return true;
    }
}

