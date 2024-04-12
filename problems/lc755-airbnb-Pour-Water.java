755. Pour Water - Medium

We are given an elevation map, heights[i] representing the height of the terrain at that index. 
The width at each index is 1. After V units of water fall at index K, how much water is at each index?

Water first drops at index K and rests on top of the highest terrain or water at that index. Then, 
it flows according to the following rules:

    If the droplet would eventually fall by moving left, then move left.
    Otherwise, if the droplet would eventually fall by moving right, then move right.
    Otherwise, rise at its current position.

"eventually fall" means that the droplet will eventually be at a lower level if it moves in that 
direction. Also, "level" means the height of the terrain plus any water in that column.

We can assume there is infinitely high terrain on the two sides out of bounds of the array. 
Also, there could not be partial water being spread out evenly on more than 1 grid block - 
each unit of water has to be in exactly one block.


Example 1:
Input: heights = [2,1,1,2,1,2,2], V = 4, K = 3
Output: [2,2,2,3,2,2,2]

Explanation:
#       #
#       #
##  # ###
#########
 0123456    <- index

The first drop of water lands at index K = 3:

#       #
#   w   #
##  # ###
#########
 0123456    

When moving left or right, the water can only move to the same level or a lower level.
(By level, we mean the total height of the terrain plus any water in that column.)
Since moving left will eventually make it fall, it moves left.
(A droplet "made to fall" means go to a lower height than it was at previously.)

#       #
#       #
## w# ###
#########
 0123456    

Since moving left will not make it fall, it stays in place.  The next droplet falls:

#       #
#   w   #
## w# ###
#########
 0123456  

Since the new droplet moving left will eventually make it fall, it moves left.
Notice that the droplet still preferred to move left,
even though it could move right (and moving right makes it fall quicker.)

#       #
#  w    #
## w# ###
#########
 0123456  

#       #
#       #
##ww# ###
#########
 0123456  

After those steps, the third droplet falls.
Since moving left would not eventually make it fall, it tries to move right.
Since moving right would eventually make it fall, it moves right.

#       #
#   w   #
##ww# ###
#########
 0123456  

#       #
#       #
##ww#w###
#########
 0123456  

Finally, the fourth droplet falls.
Since moving left would not eventually make it fall, it tries to move right.
Since moving right would not eventually make it fall, it stays in place:

#       #
#   w   #
##ww#w###
#########
 0123456  

The final answer is [2,2,2,3,2,2,2]:

    #    
 ####### 
 ####### 
 0123456 
Example 2:
Input: heights = [1,2,3,4], V = 2, K = 2
Output: [2,3,3,4]
Explanation:
The last droplet settles at index 1, since moving further left would not cause it to eventually fall to a lower height.
Example 3:
Input: heights = [3,1,3], V = 5, K = 1
Output: [4,4,4]
Note:

heights will have length in [1, 100] and contain integers in [0, 99].
V will be in range [0, 2000].
K will be in range [0, heights.length - 1].

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



=======================================================================================================
method 1: simulation

method:

	-   （0）handle the water drop by drop。 there are infinitely high walls on the left and right
		（1）水先向左走，走到不能走为止。call it leftMost  
		（2）if leftmost < start，leftMost水+1，done
		（3）if leftmost >= start，水向右走，走到不能走为止。call it rightMost
		（4）if rightmost < start，rightMost水+1，done
		（5）if rightmost >= start，leftMost水+1，done
	- 

stats:

	- Given a flat starting terrain where heights are all 0, each drop takes a average of N iterations. 
	  (And first drop even takes 2N steps). If you have V drops, this is O(VN)
	- O(V * 2L) = O(V*L)

import java.util.*;


class Solution {
	public static void main(String[] args) {
        Solution sol = new Solution();
        int[] heights = {2,1,1,2,1,2,2};
        sol.pourWater(heights, 3, 4);
    }

    // k is index, v is volumn of water
    public void pourWater(int[] heights, int K, int V) {

        int[] water = new int[heights.length];

    	print(heights,water);

    	// each waterdrop
        for(int i = 0; i < V; i++) {
            int curPos = K;

            // Move left if cur >= left, then there's a fall to the left, find the lowest left index
            while(curPos > 0 
                    && heights[curPos] + water[curPos] >= heights[curPos-1]+water[curPos-1]) {
                
                curPos--;
            }

            // Move right
            while(curPos < heights.length - 1 
                    && heights[curPos]+ water[curPos] >= heights[curPos + 1]+ water[curPos+1]) {
                
                curPos++;

            }

            // Move left to K, if you cannot move right and eventually fall, you'll need to stay at K
			while(curPos > K 
                    && heights[curPos] + water[curPos] == heights[curPos - 1]+ water[curPos-1]){
                
                curPos--;
            }

            water[curPos]++;
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

        // print from top level
        for (int i = max; i > 0; i--) {

        	// print from left, 一层一层print
            for (int j = 0; j < heights.length; j++) {
                if (heights[j] + water[j] < i) {
                    System.out.print(" ");
                } 

                // total height >= max, and without water it's smaller than i
                else if (heights[j] + water[j] >= i && heights[j] < i){
                    System.out.print("w");
                } 

                else {
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

at drop 0 curPos is: 2 water is 0
left -- at drop 0 the curPos is : 2
left -- at drop 0 the curPos is : 1
right -- at drop 0 the curPos is : 2
------------- new round ------------
at drop 1 curPos is: 2 water is 0
left -- at drop 1 the curPos is : 2
left -- at drop 1 the curPos is : 1
------------- new round ------------
at drop 2 curPos is: 2 water is 0
left -- at drop 2 the curPos is : 2
left -- at drop 2 the curPos is : 1
left -- at drop 2 the curPos is : 0
right -- at drop 2 the curPos is : 1
right -- at drop 2 the curPos is : 2
right -- at drop 2 the curPos is : 3
right -- at drop 2 the curPos is : 4
------------- new round ------------
at drop 3 curPos is: 2 water is 0
left -- at drop 3 the curPos is : 2
left -- at drop 3 the curPos is : 1
left -- at drop 3 the curPos is : 0
right -- at drop 3 the curPos is : 1
right -- at drop 3 the curPos is : 2
right -- at drop 3 the curPos is : 3
right -- at drop 3 the curPos is : 4
right -- at drop 3 the curPos is : 5
right -- at drop 3 the curPos is : 6
stay in mid -- at drop 3 the curPos is : 5
stay in mid -- at drop 3 the curPos is : 4
stay in mid -- at drop 3 the curPos is : 3
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
	  until there is an strict increase in height 
	- Note that the inner while loop never re-runs from K; they always continue from i, j. 
	  Therefore, the inner 2 loops should not run more than O(n). All other updates are constant time 
	  per iteration. => O(n) total

	- The idea here is we use 2 stacks to keep track of the first drop off points going left and right, 
	  respectively. The water being poured will fill these drop off points first. As these points are 
	  filled up, we pop them if they are no longer the first drop off point (some point before is now 
	  the new drop off point).

      stack has the lowest index at top, ex. level 0 is on top of level 1.
      ex. {2,0,1,2,1} --> index = 3, left stack from top to bottom: 1, 2

	 - We might also need to add the new dropoff point (the point after current dropoff).


stats:

	- This is O(VN) since you have to scan left or right each time. You can get this to O(V*LogN) 
	  by using a priority queue instead of stack.

Class Solution{

    // left & right stack stores the next index
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
    
    // main function
    public int[] pourWater(int[] heights, int V, int K) {
        Stack<Integer> left = new Stack<>(), 
                      right = new Stack<>();
        
        scanLeft(left, heights, K);
        scanRight(right, heights, K);
        System.out.println("left stack" + left.toString());
        System.out.println("right stack" + right.toString());

        for (int v = 0; v < V; ++v) {

            // can go left
            if (!left.empty()) {
                int current = left.pop();  
                heights[current] += 1;
                
                scanLeft(left, heights, current + 1);
            }
            else if (!right.empty()) {
                int current = right.pop();  
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
}

=======================================================================================================

Method 3:
	- use 2 PriorityQueue

stats:
	- O(N lg N + V lg N) = O((N+V) * lg N). The "add" operation in pq will execute at most N times.
	  the total runtime inside the while loops is going to be O(N log N). We also "remove" and "add" 
	  from priority queue V times, hence O(V log N). 
	- in java, priority queue is binary heap and heapify procedure will take O(lg n).



class Solution {
	// V = volumn of water, K = position
    public int[] pourWater(int[] heights, int V, int K) {
        PriorityQueue<Integer> left = new PriorityQueue<>(
                (a,b)->(heights[a]==heights[b]) ? b-a : heights[a]-heights[b]);

        PriorityQueue<Integer> right = new PriorityQueue<>(
                (a,b)->(heights[a]==heights[b]) ? a-b : heights[a]-heights[b]);
        
        
        int leftPtr = K - 1, 
            rightPtr = K + 1;

        for (int v = 0; v < V; v++) {

        	// move left, add all potential falls, in pq, organize by heights
            while (leftPtr >= 0 && heights[leftPtr] <= heights[leftPtr + 1]) 
            	left.add(leftPtr--);

            // move right, there's a fall in right
            while (rightPtr < heights.length && heights[rightPtr] <= heights[rightPtr - 1]) 
            	right.add(rightPtr++);

            // check left first
            if (left.size() != 0 && heights[left.peek()] < heights[K]) {
                int index = left.poll();

                heights[index] += 1;
                left.add(index);
            } else if (right.size() != 0 && heights[right.peek()] < heights[K]) {
                int index = right.poll();
                heights[index] += 1;
                right.add(index);
            } else {
                heights[K] += 1;
            }
        }
        
        return heights;
    }
}



