135. Candy -Hard

There are n children standing in a line. Each child is assigned a rating value given in the 
integer array ratings.

You are giving candies to these children subjected to the following requirements:

Each child must have at least one candy.
Children with a higher rating get more candies than their neighbors.
Return the minimum number of candies you need to have to distribute the candies to the children.

 

Example 1:

Input: ratings = [1,0,2]
Output: 5
Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
Example 2:

Input: ratings = [1,2,2]
Output: 4
Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
The third child gets 1 candy because it satisfies the above two conditions.
 

Constraints:

n == ratings.length
1 <= n <= 2 * 104
0 <= ratings[i] <= 2 * 104

******************************************************
key:
	- peak and valley
	- when see equal ratings, set the second to 1
	- edge case:
		1) 
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	-	

We can easily calculate the number of candy needed by using an array to store the candy of each child. 
And after that traverse the rating array twice. The first loop makes sure children with a higher rating 
get more candy than its left neighbor, the second loop makes sure children with a higher rating get 
more candy than its right neighbor. At last add the total number of candies. This approach is O(n) 
time and O(n) space. 




Space O(1) approach
We can consider this problem like valley and peak problem. In each valley there should be 1 candy 
and for each increasing slope in either side we need to increse candy by 1. Peaks have highest candy. 
If any equal rating is found then candy resets to 1 as two equal neighbours may have any number of 
candies. The peak should contain the higher number of candy between which is calculated from the 
incresing slope and which is calculated from decreasing slope. Because this will satisfy the condition 
that peak element is having more candies than its neighbours.




Each child represented as rating(candy he is given)
Peak = max(peak, valley)
--> find the number that satisfy both left and right, which is take max
--> max of the peak calculated from left and valley calculated from right.
--> if equal element it gets reset to 1 candy or if it is peak we take max(0, right valley)
/**
1. initially n candy for n children. Then we start traversing the rating array from the second element. 
If we find equal elements we continue to next element as they already have 1 candy.

2. If an increasing slop is found (ratings[i] > ratings [i-1]) we increase value of peak and and 
add the peak value to candy. After each iteration new peak is found and the value is added to candy. 
In this way we also get the value of minimum height of the peak at the end.

3. If a decreasing slope is found (ratings[i] < ratings [i-1]) we calculate the depth of the valley 
which is in turn the minimum height of the previous peak. In each iteration we increse the valley 
by 1 and add it to the candy. You can visualise it as 1 candy is added to each of the previous 
members untill peak or the new peak value is interted at the previous peak and other values are 
shifted to right by 1 place. We also need to check if it is going out of array if so then return 
the number of candy.

4. After this we can see we have added the peak value twice in candy once as peak and once as valley. 
But we need only the max value out of these two so we substract the min(peak, valley) from candy.
**/

class Solution {
    public int candy(int[] ratings) {
        int n = ratings.length;
        int candy = n;//each kid has 1 candy at the begining
        int i=1;

        while(i<n){
            //since all candy is initialized as 1
            if(ratings[i]==ratings[i-1]){
                i++;
                continue;
            }

            //for increasing slope
            int peak=0;
            while(ratings[i]>ratings[i-1]){
            	//peak is the number of candy index i-1 gets
                peak++;
                candy+=peak;
                i++;
                //if it's a monotonic increasing array, return the accumulated candy
                if(i==n){
                    return candy;
                }
            }

            //for decreasing
            int valley=0;
            while(i<n && ratings[i]<ratings[i-1]){
                valley++;
                candy+=valley;
                i++;
            }
            //keep only te higher peak
            candy-=Math.min(peak,valley);
        }
        return candy;
    }
}
=======================================================================================================

        public int Candy(int[] ratings) {
            if (ratings.Length == 0) return 0;
            int candy = 1;
            int up = 0, down = 0, peak = 0;
            for (int i = 1; i < ratings.Length; i++) {
                if (ratings[i - 1] < ratings[i]) {
                    peak = ++up;
                    down = 0;
                    candy += 1 + up;
                } 
                //two same ratings, just start form 1 again, thus +=1
                else if (ratings[i - 1] == ratings[i])  {
                    peak = up = down = 0;
                    candy += 1;
                } 
                else {
                	// if decreasing, set up to 0, find down/valley
                    up = 0;
                    down++;
					candy += down + (peak >= down ? 0 : 1);                
				}
            }

            return candy;
        }


=======================================================================================================


    // Initialize 1 candy for each child
    int[] candies = new int[ratings.length];
    for (int i = 0; i < candies.length; i++) {
        candies[i] = 1;
    }
    
    // from left to right, increase 1 candy if see an up slope
    for (int i= 1; i < ratings.length; i++) {
        if (ratings[i] > ratings[i - 1]) {
            candies[i] = candies[i - 1] + 1;
        }
    }
    
    // from right to left, increase 1 candy if see an up slope
    for (int i = ratings.length - 2; i >= 0; i--) {
        if (ratings[i] > ratings[i + 1]) {
            // i may be a peak
            candies[i] = Math.max(candies[i], candies[i + 1] + 1);
        }
    }
    
    int total = 0;
    for (int i = 0; i < candies.length; i++) {
        total += candies[i];
    }
    
    return total;



















