733. Flood Fill - Easy

An image is represented by a 2-D array of integers, each integer representing the pixel value of 
the image (from 0 to 65535).

Given a coordinate (sr, sc) representing the starting pixel (row and column) of the flood fill, 
and a pixel value newColor, "flood fill" the image.

To perform a "flood fill", consider the starting pixel, plus any pixels connected 4-directionally to 
the starting pixel of the same color as the starting pixel, plus any pixels connected 4-directionally 
to those pixels (also with the same color as the starting pixel), and so on. Replace the color of all 
of the aforementioned pixels with the newColor.

At the end, return the modified image.

Example 1:
Input: 
image = [[1,1,1],[1,1,0],[1,0,1]]
sr = 1, sc = 1, newColor = 2
Output: [[2,2,2],[2,2,0],[2,0,1]]
Explanation: 
From the center of the image (with position (sr, sc) = (1, 1)), all pixels connected 
by a path of the same color as the starting pixel are colored with the new color.
Note the bottom corner is not colored 2, because it is not 4-directionally connected
to the starting pixel.


Note:

The length of image and image[0] will be in the range [1, 50].
The given starting pixel will satisfy 0 <= sr < image.length and 0 <= sc < image[0].length.
The value of each color in image[i][j] and newColor will be an integer in [0, 65535].


******************************************************
key:
	- BFS
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time complexity: O(m*n), space complexity: O(1). m is number of rows, n is number of columns.
	- 


Method:

	-	
	-  Say color is the color of the starting pixel. Let's floodfill the starting pixel: we change 
	  the color of that pixel to the new color, then check the 4 neighboring pixels to make sure they are valid pixels of the same color, and of the valid ones, we floodfill those, and so on.



class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if (image[sr][sc] == newColor) 
        	return image;

        fill(image, sr, sc, image[sr][sc], newColor);
        return image;
    }
    
    private void fill(int[][] image, int sr, int sc, int color, int newColor) {

    	// the coordinates 
        if (sr < 0 || sr >= image.length || sc < 0 || sc >= image[0].length || image[sr][sc] != color) 	
        	return;

        image[sr][sc] = newColor;
        fill(image, sr + 1, sc, color, newColor);
        fill(image, sr - 1, sc, color, newColor);
        fill(image, sr, sc + 1, color, newColor);
        fill(image, sr, sc - 1, color, newColor);
    }
}

=======================================================================================================
method 2: BFS

Stats:

	- Time Complexity: O(mn). We are visiting every cell only once.
	- Space Complexity: O(mn) as we are maintaining a visited array.



Method:

	1. Add the starting cell to the queue.
	2. Poll a cell out of queue. Keep putting all the valid neighbors in the queue.
	3. We can maintain a visited matrix to optimize the code to O(mn) as we might end up pushing 
	   few nodes in the queue that are already present in the queue. Although it would be a valid
	   solution but the code will not be optimized.


class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if(image[sr][sc] == newColor) 
        	return image;

        int color = image[sr][sc];

        Queue <int[]> q = new LinkedList <>();
        // put your starting cell in the queue
        q.add(new int [] {sr, sc});

        boolean [][] visited = new boolean [image.length][image[0].length];
        visited[sr][sc] = true;

        // Good practice to have dirs array to avoid
        int [][] dirs = new int [][]{{0,1}, {1,0}, {-1,0}, {0,-1}};

        while(!q.isEmpty()){
            int [] curr = q.poll();
            image[curr[0]][curr[1]] = newColor;

            // iterate on all posiible directions
            for(int [] dir : dirs){

                int i = dir[0] + curr[0];
                int j = dir[1] + curr[1];

                if(i>= 0 && i < image.length 
                   && j >=0 && j < image[0].length
                   && image[i][j] == color && !visited[i][j]) {
                        q.add(new int[] {i,j});
                        
                        // Mark Visited
                        visited[i][j] =  true;
                    }  
            }
        }
        return image;
    }
}

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



