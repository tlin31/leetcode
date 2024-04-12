852. Peak Index in a Mountain Array - Easy

Let us call an array A a mountain if the following properties hold:

A.length >= 3
There exists some 0 < i < A.length - 1 such that A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > 
A[A.length - 1]

Given an array that is definitely a mountain, return any i such that 
A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1].

Example 1:

Input: [0,1,0]
Output: 1


Example 2:
Input: [0,2,1,0]
Output: 1

******************************************************
key:
	- binary search
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- For loop to find the first A[i] > A[i + i], 

	- 

stats:

	- O(N)
	- 


    public int peakIndexInMountainArray(int[] A) {
        for (int i = 1; i + 1 < A.length; ++i) 
        	if (A[i] > A[i + 1]) 
        		return i;
        	
        return 0;
    }


=======================================================================================================
method 2:

method:

	- binary search
	- 

stats:

	- 
	- 

public int peakIndexInMountainArray(int[] A) {
        int l = 0, 
            r = A.length - 1, 
            m;

        while (l < r) {
            m = (l + r) / 2;

            // 正常情况
            if (A[m] < A[m + 1])
                l = m + 1;
            else
                r = m;
        }
        return l;
    }


=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



