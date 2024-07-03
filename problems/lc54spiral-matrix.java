54. Spiral Matrix - Medium

Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in 
spiral order.

Example 1:
Input:
[
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
]
Output: [1,2,3,6,9,8,7,4,5]

Example 2:
Input:
[
  [1, 2, 3, 4],
  [5, 6, 7, 8],
  [9,10,11,12]
]
Output: [1,2,3,4,8,12,11,10,9,5,6,7]



******************************************************
key:
	- use 4 indicators
	- loop --> end condition: new array length == # of elements in the matrix
	- edge case:
		1) empty original matrix, return emptuy list
		2)

******************************************************

class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new LinkedList<>(); 
        if (matrix == null || matrix.length == 0) return res;

        int n = matrix.length, m = matrix[0].length;
        int up = 0,  down = n - 1;
        int left = 0, right = m - 1;
        
        while (res.size() < n * m) {
            for (int j = left; j <= right && res.size() < n * m; j++)
                res.add(matrix[up][j]);
            
            for (int i = up + 1; i <= down - 1 && res.size() < n * m; i++)
                res.add(matrix[i][right]);
                     
            for (int j = right; j >= left && res.size() < n * m; j--)
                res.add(matrix[down][j]);
                        
            for (int i = down - 1; i >= up + 1 && res.size() < n * m; i--) 
                res.add(matrix[i][left]);
                
            left++; right--; up++; down--; 
        }
        return res;
    }
}



=======================================================================================================
method 1:

method:

	- 
	- 

stats:

	- 时间复杂度：O（m * n），m 和 n 是数组的长宽。
	- 空间复杂度：O（1）。

	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Spiral Matrix.
	- Memory Usage: 34.3 MB, less than 100.00% 

	/*
	 * direction 0 代表向右, 1 代表向下, 2 代表向左, 3 代表向上
	*/
	public List<Integer> spiralOrder(int[][] matrix) {
	    List<Integer> ans = new ArrayList<>();
	    if(matrix.length == 0){
	        return ans;
	    }
	    int start_x = 0, 
	    start_y = 0,
	    direction = 0, 
	    top_border = -1,  //上边界
	    right_border = matrix[0].length,  //右边界
	    bottom_border = matrix.length, //下边界
	    left_border = -1; //左边界

	    while(true){

	        //全部遍历完结束
	        if (ans.size() == matrix.length * matrix[0].length) {
	            return ans;
	        }

	        //注意 y 方向写在前边，x 方向写在后边 --> matrix[row][column]
	        // always add the current one
	        ans.add(matrix[start_y][start_x]);

	        switch (direction) {
	            //当前向左
	            case 0:
	                //继续向左是否到达边界
	                //到达边界就改变方向，并且更新上边界
	                if (start_x + 1 == right_border) {
	                    direction = 1;
	                    start_y += 1;
	                    top_border += 1;
	                } else {
	                    start_x += 1;
	                }
	                break;

	            //当前向下
	            case 1:
	                //继续向下是否到达边界
	                //到达边界就改变方向，并且更新右边界
	                if (start_y + 1 == bottom_border) {
	                    direction = 2;
	                    start_x -= 1;
	                    right_border -= 1;
	                } else {
	                    start_y += 1;
	                }
	                break;
	            case 2:
	                if (start_x - 1 == left_border) {
	                    direction = 3;
	                    start_y -= 1;
	                    bottom_border -= 1;
	                } else {
	                    start_x -= 1;
	                }
	                break;
	            case 3:
	                if (start_y - 1 == top_border) {
	                    direction = 0;
	                    start_x += 1;
	                    left_border += 1;
	                } else {
	                    start_y -= 1;
	                }
	                break;
	        }
	    }

	}


=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Spiral Matrix.
	- Memory Usage: 34.4 MB, less than 100.00%


	public List < Integer > spiralOrder(int[][] matrix) {
	    List < Integer > spiralList = new ArrayList < > ();
	    if (matrix == null || matrix.length == 0) return spiralList;
	    // declare indices
	    int top = 0;
	    int bottom = matrix.length - 1;
	    int left = 0;
	    int right = matrix[0].length - 1;
	    while (true) {
	        // 1. print top row
	        for (int j = left; j <= right; j++) {
	            spiralList.add(matrix[top][j]);
	        }
	        top++;

	        if (boundriesCrossed(left, right, bottom, top))
	            break;


	        // 2. print rightmost column
	        for (int i = top; i <= bottom; i++) {
	            spiralList.add(matrix[i][right]);
	        }
	        right--;

	        if (boundriesCrossed(left, right, bottom, top))
	            break;


	        // 3. print bottom row
	        for (int j = right; j >= left; j--) {
	            spiralList.add(matrix[bottom][j]);
	        }
	        bottom--;

	        if (boundriesCrossed(left, right, bottom, top))
	            break;


	        // 4. print leftmost column
	        for (int i = bottom; i >= top; i--) {
	            spiralList.add(matrix[i][left]);
	        }
	        left++;
	        if (boundriesCrossed(left, right, bottom, top))
	            break;

	    } // end while true

	    return spiralList;
	}
	private boolean boundriesCrossed(int left, int right, int bottom, int top) {
	    if (left > right || bottom < top)
	        return true;
	    else
	        return false;
	}
	




