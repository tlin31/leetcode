48. Rotate Image - Medium

You are given an n x n 2D matrix representing an image.

Rotate the image by 90 degrees (clockwise).

Note:

You have to rotate the image in-place, which means you have to modify the input 2D matrix directly. 
DO NOT allocate another 2D matrix and do the rotation.

Example 1:
Given input matrix = 
[
  [1,2,3],
  [4,5,6],
  [7,8,9]
],

rotate the input matrix in-place such that it becomes:
[
  [7,4,1],
  [8,5,2],
  [9,6,3]
]

Example 2:
Given input matrix =
[
  [ 5, 1, 9,11],
  [ 2, 4, 8,10],
  [13, 3, 6, 7],
  [15,14,12,16]
], 

rotate the input matrix in-place such that it becomes:
[
  [15,13, 2, 5],
  [14, 3, 4, 1],
  [12, 6, 8, 9],
  [16, 7,10,11]
]



******************************************************
key:
  - trick, rotate clockwise 90 degree --> revert upside & down, then revert based on diagnal
  - anticlockwise rotate --> first reverse left to right, then swap the symmetry
  - edge case:
    1) empty string, return empty
    2)

******************************************************



=======================================================================================================
method 1:

method:

  - use trick
  - /*
     * clockwise rotate
     * first reverse up to down, then swap the symmetry 
     * 1 2 3     7 8 9     7 4 1
     * 4 5 6  => 4 5 6  => 8 5 2
     * 7 8 9     1 2 3     9 6 3
    */

stats:
  - Runtime: 0 ms, faster than 100.00% of Java online submissions for Rotate Image.
  - Memory Usage: 36.4 MB, less than 100.00% 
  - 时间复杂度：O（n²）
  - 空间复杂度：O（1）

  public void rotate(int[][] matrix) {
    int s = 0, e = matrix.length - 1;
    
    // reverse up & down
    while(s < e){
        int[] temp = matrix[s];
        matrix[s] = matrix[e];
        matrix[e] = temp;
        s++; 
        e--;
    }

    // swap matrix[0][1] & matrix[1][0]
    // here changes (0,1), (0,2), (1,2)
    for(int i = 0; i < matrix.length; i++){
        for(int j = i+1; j < matrix[i].length; j++){
            int temp = matrix[i][j];
            matrix[i][j] = matrix[j][i];
            matrix[j][i] = temp;
        }
    }
}

ex.
after revert:
[7, 8, 9]
[4, 5, 6]
[1, 2, 3]

swap diagnal:
0, 1, num = 8
0, 2, num = 9
1, 2, num = 6

/*
 * anticlockwise rotate
 * first reverse left to right, then swap the symmetry
 * 1 2 3     3 2 1     3 6 9
 * 4 5 6  => 6 5 4  => 2 5 8
 * 7 8 9     9 8 7     1 4 7
*/
void anti_rotate(vector<vector<int> > &matrix) {
    for (auto vi : matrix) reverse(vi.begin(), vi.end());
    for (int i = 0; i < matrix.size(); ++i) {
        for (int j = i + 1; j < matrix[i].size(); ++j)
            swap(matrix[i][j], matrix[j][i]);
    }
}

=======================================================================================================
method 2:

method:

  - ?????????
  - 

stats:
  - Runtime: 0 ms, faster than 100.00% of Java online submissions for Rotate Image.
  - Memory Usage: 36 MB, less than 100.00% 
  - 时间复杂度：O（n²）。
  - 空间复杂度：O（1）。


  public void rotate(int[][] matrix) {
    int n=matrix.length;
    for (int i=0; i<n/2; i++) 
        for (int j=i; j<n-i-1; j++) {
            int tmp=matrix[i][j];
            matrix[i][j]=matrix[n-j-1][i];
            matrix[n-j-1][i]=matrix[n-i-1][n-j-1];
            matrix[n-i-1][n-j-1]=matrix[j][n-i-1];
            matrix[j][n-i-1]=tmp;
        }
  }






