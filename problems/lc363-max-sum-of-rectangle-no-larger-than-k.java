363. Max Sum of Rectangle No Larger Than K - Hard

Given a non-empty 2D matrix matrix and an integer k, find the max sum of a rectangle in the matrix 
such that its sum is no larger than k.

Example:

Input: matrix = [[1,0,1],[0,-2,3]], k = 2
Output: 2 
Explanation: Because the sum of rectangle [[0, 1], [-2, 3]] is 2,
             and 2 is the max number no larger than k (k = 2).

Note:
The rectangle inside the matrix must have an area > 0.

Follow up:
What if the number of rows is much larger than the number of columns?


******************************************************
key:
	- Treeset to achive logn search for prefix, prefix sum array
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:
	- Same idea: use the same rule to get the all the submatrix prefixsum. The lgn is not because the 
	  binary search. The reason lgN is here we use balanced bst here. (In java, TreeSet or TreeMap)

	- ceiling​(E e): This method returns the least element in this set greater than or equal to the 
	  given element, or null if there is no such element.

	- check for including column j
		ex. J = 1, check for column (1) (1,2) (1,2,3)...(1,2,3,...col.size) 

	- sum[] stores the prefix-sum of this row.
		1	2	3
		4	5	6
		7	8	9

		for j = 0, current = 0, sum = [1  4  7]
				   current = 1, sum = [1+2	4+5	7+8] --> consider 

	- current = 不断扩大的rectangle，current - k = (1,2,4,5,7,8) - 24 --> 超过了的recatange和
	  target = set中存在的能被减去的rectangle （ex. (1,2)）
	  此时，最接近与k （并且<k）的rectange为（4，5，7，8） --> 通过prefix sum 求出了2x2的desired rectangle
	- 

stats:

	-  TIME：O(n * n * (m) * lgm) n means the col size and m row size
	- 


public int maxSumSubmatrix(int[][] matrix, int k) {
    int row = matrix.length;
    int col = matrix[0].length;
    int result = Integer.MIN_VALUE;
    
    // for each column, find sum of the row
    for(int j = 0; j < col; j++) {
        int[] sum = new int[row];
        
        // loop through various range/combo of columns.
        for(int current = j; current < col; current++) {
            for(int i = 0; i < row; i++) {
                sum[i] += matrix[i][current];
            }
            
            int temp = find(sum, k);
            
            result = Math.max(temp, result);

            // early stop
            if (result == k)
            	return result;
            
        }
        
    }
    return result;
}

//O(lgM) find the maxium gap
private int find(int[] sum, int k) {
    int result = Integer.MIN_VALUE;

    // add possible rectangle areas
    TreeSet<Integer> set = new TreeSet<>();

    // use 0 as a thresh hold 
    set.add(0);
    int current = 0;

    // loop over rows
    for(int i = 0; i < sum.length; i++) {
        current += sum[i];
        
        // if current exceeds k, then target indicate the area of rectangle we need to delete from
        // the set, in order to have the new cropped rectangle has area < k
        Integer target = set.ceiling(current - k); 
            
        if(target != null) {
            result = Math.max(result, current - target);
        }

        set.add(current);
    }
    
    return result;
}

====================

ex. matrix = [[1,0,1],[0,-2,3]], k = 2

From j = 0:

current = 0:
	sum = [1 0]
	temp = find (sum, k)
	in find function:
		set = {0}
		i = 0: current = 1, target = set.ceiling (1-2) = set.ceiling(-1) = 0, 
			   result = max(Integer.MIN_VALUE, 1-0) = 1, set = {0,1} 
		i = 1: current = 1+0 = 1, target = set.ceiling (1-2) = 0, result = max(1, 1-0) = 1,
			   set = {0,1}
		return result (= 1)

	temp = Integer.MIN_VALUE
	result = Math.max(temp, result) = max(Integer.MIN_VALUE , 1) = 1

current = 1: consider the column 0 & 1
	sum = [1+0	0-2] = [1	-2]
	in find function:
		set = {0}
		i = 0: current = 1, target = set.ceiling(1-2) = 0, result = max(Integer.MIN_VALUE, 1-0) = 1,
			   set = {0,1}
		i = 1: current = 1-2 = -1. 
		       Here current represents the sum of 4 elements in the left 2 columns;
			   target = set.ceiling(-1-2) = 0, result = max(1, -1-2) = 1,
			   set = {-1, 0,1}
		return result (= 1)

	temp = 1
	result = max(1,1) = 1

current = 2: consider the columns 0 & 1 & 2
	sum = [1+0+1	0-2+3] = [2	1]
	in find function:
		set = {0}
		i = 0: current = 2, target = set.ceiling(2 -2) = 0, result = max(Integer.MIN_VALUE, 2-0) = 2,
			   set = {0,2}
		i = 1: current = 2+1 = 3, target = set.ceiling(3 -2) = 2, result = max(2, 3-2) = 2,
			   set = {0,2}

    temp = 2
    result = max(1,2) = 2

From j = 1:

current = 1 : consider the second column 

current = 2 : consider the rectangle combines of column 2 & 3


From j = 2:

current = 2
=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



