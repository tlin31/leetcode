1105. Filling Bookcase Shelves - Medium


We have a sequence of books: the i-th book has thickness books[i][0] and height books[i][1].

We want to place these books in order onto bookcase shelves that have total width shelf_width.

We choose some of the books to place on this shelf (such that the sum of their thickness is <= 
shelf_width), then build another level of shelf of the bookcase so that the total height of the bookcase
has increased by the maximum height of the books we just put down.  We repeat this process until there 
are no more books to place.

Note again that at each step of the above process, the order of the books we place is the same order as
the given sequence of books.  For example, if we have an ordered list of 5 books, we might place the first
and second book onto the first shelf, the third book on the second shelf, and the fourth and fifth book on the last shelf.

Return the minimum possible height that the total bookshelf can be after placing shelves in this manner.

 

Example 1:


Input: books = [[1,1],[2,3],[2,3],[1,1],[1,1],[1,1],[1,2]], shelf_width = 4
Output: 6
Explanation:
The sum of the heights of the 3 shelves are 1 + 3 + 2 = 6.
Notice that book number 2 does not have to be on the first shelf.
 

Constraints:

1 <= books.length <= 1000
1 <= books[i][0] <= shelf_width <= 1000
1 <= books[i][1] <= 1000




******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	-  dp[i]: the min height for placing first books i - 1 on shelves
	-  For dp[i+1], 2 cases
	 	1) place book i on a new shelve => dp[i] + height[i],
		2) grab previous books together with book i and move to next level together, utlitzing the 
		   sub problem dp[j] => min(dp[j] + max(height[j+1] .. height[i])), where 
		   sum(width[j+1] + ... + sum(width[i]) <= shelve_width

	-  	Start placing books one by one, always use a new shelve to begin with
	-  	After you stored the new height value at this position in your dp array, start looking back 
	    at previous books one by one, and see while the width permits, how many books you can fit on 
	    this new level.
	-   Check to see if bringing said books down reduced the overall height, if it did, update the new
	    loest height value at your dp array.
	-   return the last element of your dp array

class Solution {
    public int minHeightShelves(int[][] books, int shelf_width) {
        int[] dp = new int[books.length + 1];
        
        dp[0] = 0;
        
        for (int i = 1; i <= books.length; ++i) {
            int width = books[i-1][0];
            int height = books[i-1][1];

            // if putting on a new shelf
            dp[i] = dp[i-1] + height;

            // j points to previous books before books[i - 1] until reach to shelf-width
            // this loop grab each of the previous book onto the same level of current book
            // books[i - 1]. This is same as trying to put books[i - 1] to previous books. 
            for (int j = i - 1; j > 0 && width + books[j-1][0] <= shelf_width; --j) {
                height = Math.max(height, books[j-1][1]);
                width += books[j-1][0];

                // compare height of current book with height of previous book
                dp[i] = Math.min(dp[i], dp[j-1] + height);
            }
        }
        return dp[books.length];
    }
}


i.e. dp[3] in books = [[1,1],[2,3],[2,3],[1,1],[1,1],[1,1],[1,2]], shelf_width = 4.
dp[i] = dp[3] = dp[2] + books[2][1] = 3 + 3 = 6;
In second for loop: height = max(3, books[1][1] which is 3 also) = 3;
so, dp[3] = min(6, 3 + dp[1]) = 4.

dp = [0, 1, 3, 4, 5, 5, 5, 6]

=======================================================================================================
method 2: recursion + memo

Stats:

	- 
	- 


Method:

	-	
	-	

    int[][] memo;
    public int minHeightShelves(int[][] books, int shelf_width) {
        memo = new int[books.length+1][shelf_width+1];
        return minHeight(books, 0, shelf_width, 0, shelf_width);
    }
    
    public int minHeight(int[][] books, int cur, int widthLeft, int curHeight, int shelf_width){
        if(cur==books.length){
            return curHeight;
        }
        if(memo[cur][widthLeft]!=0) 
        	return memo[cur][widthLeft];
        memo[cur][widthLeft] = curHeight + minHeight(books,cur+1,shelf_width-books[cur][0],books[cur][1],shelf_width);  
        
        if(widthLeft>=books[cur][0]){
            memo[cur][widthLeft] = Math.min(minHeight(books,cur+1,widthLeft-books[cur][0],Math.max(curHeight,books[cur][1]),shelf_width),memo[cur][widthLeft]);
        }
        return memo[cur][widthLeft];
    }

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



