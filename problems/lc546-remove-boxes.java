546. Remove Boxes - Hard

Given several boxes with different colors represented by different positive numbers.
You may experience several rounds to remove boxes until there is no box left. 
Each time you can choose some continuous boxes with the same color (composed of k boxes, k >= 1), 
remove them and get k*k points.

Find the maximum points you can get.

Example 1:
Input:

[1, 3, 2, 2, 2, 3, 4, 3, 1]
Output:
23
Explanation:
[1, 3, 2, 2, 2, 3, 4, 3, 1] 
[1, 3,          3, 4, 3, 1] (3*3=9 points) 
[1, 3, 			3,    3, 1] (1*1=1 points) 
[1, 					 1] (3*3=9 points) 
[						  ] (2*2=4 points)
Note: The number of boxes n would not exceed 100.


******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP

Method:

	- dp[i][j][k] mean the max points from box[i] to box[j] with k boxes of value box[i] merged.
	- dp = :

		1) merge k boxes:
			score = dp(i+1, j, 1) + k * k ...............(1)

		2) dont merge k boxes
			we can continue to find box which is the same
			when we find box m equal to box i, we can have one more box, so k become k + 1
			
			first term is the other boxes, second term contain information of the same boxes
			(box[i] or box[m]) we have found till now

			score = dp(i+1, m-1, 1) + dp (m, j, k + 1) ...............(2)
		
		dp[i][j][k] = max ((1), (2))

Stats:

	- O(n^3)
	- O(n^3)

 public int removeBoxes(int[] boxes) {
        if (boxes == null || boxes.length == 0) {
            return 0;
        }

        int size = boxes.length;
        int[][][] dp = new int[size][size][size];

        return get(dp, boxes, 0, size-1, 1);
    }

    private int get(int[][][] dp, int[] boxes, int i, int j, int k) {
         if (i > j) {
            return 0;
        } 
        if (i == j) {
            return k * k;
        } 
        if (dp[i][j][k] != 0) {
            return dp[i][j][k];
        } 
        else {
        	// case 1: merge
            int temp = get(dp, boxes, i + 1, j, 1) + k * k;

            // case 2: not merge
            for (int m = i + 1; m <= j; m++) {
                if (boxes[i] == boxes[m]) {
                    temp = Math.max(temp, get(dp, boxes, i + 1, m - 1, 1) + get(dp, boxes, m, j, k + 1));
                }
            }

            dp[i][j][k] = temp;
            return temp;
        }


    }


=======================================================================================================
method 2:

Method:

	-	
	-	


Stats:

	- 
	- 

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



