276. Paint Fence - Easy

There is a fence with n posts, each post can be painted with one of the k colors.

You have to paint all the posts such that no more than two adjacent fence posts have the same color.

Return the total number of ways you can paint the fence.

Note:
n and k are non-negative integers.

Example:

Input: n = 3, k = 2
Output: 6
Explanation: Take c1 as color 1, c2 as color 2. All possible ways are:

            post1  post2  post3      
 -----      -----  -----  -----       
   1         c1     c1     c2 
   2         c1     c2     c1 
   3         c1     c2     c2 
   4         c2     c1     c1  
   5         c2     c1     c2
   6         c2     c2     c1



******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************


=======================================================================================================
method 1:

Stats:

	- 
	- 


Method:
	- have 2 options each step:
		1. make it different color as i-1th post
			- If you are painting the ith post, how many ways can you paint it to make it different 
			  from the i-1 th post?  --> k-1

			  	num_ways_diff(i) = num_ways(i-1) * (k-1)


		2. make it same color as i-1 th post 
			- If you are painting the ith post, how many ways can you paint it to make it the same as 
			  the i-1th post? At first, we should think the answer is 1, it must be whatever color 
			  the last one was.
			- But no! This will fail in the cases where painting the last post the same results in 
			  three adjacent posts of the same color! 

			- We need to consider ONLY the cases where the last two colors were different. But we can 
			  do that!

				num_ways_diff(i-1) <- all the cases where the i-1th and i-2th are different.

			THESE are the cases where can just plop the same color to the end, and no longer worry about 
			causing three in a row to be the same.

				num_ways_same(i) = num_ways_diff(i-1) * 1

	Thus, num_ways(i) = num_ways_diff(i) + num_ways_same(i) 
					  = num_ways(i-1) * (k-1) + num_ways_diff(i-1)
	      num_ways(i) = num_ways(i-1) * (k-1) + num_ways(i-2) * (k-1)
		  num_ways(i) = (num_ways(i-1) + num_ways(i-2)) * (k-1)



class Solution {
    public int numWays(int n, int k) {
        // if there are no posts, there are no ways to paint them
        if (n == 0) return 0;
        
        // if there is only one post, there are k ways to paint it
        if (n == 1) return k;
        
        // if there are only two posts, you can't make a triplet, so you 
        // are free to paint however you want.
        if (n == 2) return k*k;
        
        int table[] = new int[n+1];
        table[0] = 0;
        table[1] = k;
        table[2] = k*k;
        for (int i = 3; i <= n; i++) {
            // the recursive formula that we derived
            table[i] = (table[i-1] + table[i-2]) * (k-1);
        }
        return table[n];
    }
}

=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	-	
	-	

	public int numWays(int n, int k) {
        if (n <= 0 || k <= 0) {
            return 0;
        }
        if (n == 1) {
            return k;
        }
        // Case 1: First 2 posts have same color.
        int sameCase = k;

        // Case 2: First 2 posts have different colors.
        int diffCase = k * (k - 1);

        for (int i = 3; i <= n; i++) {
            int temp = diffCase;
            
            // To every sameCase and diffCase we can add a new post with different color as the last one. 
            // We have k-1 color options for the last one.
            diffCase = (sameCase + diffCase) * (k - 1);
            
            // To every diffCase we can add a new post with the same color as the last one to not 
            // generate violation - no more than 2 adjacent fence posts have the same color.
            sameCase = temp;
        }
        return sameCase + diffCase;
    }



=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



