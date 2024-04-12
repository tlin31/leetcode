413. Arithmetic Slices - Medium

A sequence of number is called arithmetic if it consists of at least three elements and if 
the difference between any two consecutive elements is the same.

For example, these are arithmetic sequence:

1, 3, 5, 7, 9
7, 7, 7, 7
3, -1, -5, -9

The following sequence is not arithmetic.

1, 1, 2, 5, 7

A zero-indexed array A consisting of N numbers is given. A slice of that array is any pair of 
integers (P, Q) such that 0 <= P < Q < N.

A slice (P, Q) of array A is called arithmetic if the sequence:
A[P], A[p + 1], ..., A[Q - 1], A[Q] is arithmetic. In particular, this means that P + 1 < Q.

The function should return the number of arithmetic slices in the array A.


Example:

A = [1, 2, 3, 4]

return: 3, for 3 arithmetic slices in A: [1, 2, 3], [2, 3, 4] and [1, 2, 3, 4] itself.


******************************************************
key:
	- dp, recursion
	- sum += curr
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: Brute force

Method:

	-	if we are currently considering the range bound by the elements, lets say, A[s](start) 
		and A[e](end), we have checked the consecutive elements in this range to have the same
		difference. Now, when we move on to the next range between the indices s and e+1, we 
		just check the last pair to have the same difference as the one used for the previous 
		range(same s, incremented e).
	-	Note that if the last range didnt constitute an arithmetic slice, the same elements will 
	    be a part of the updated range as well. Thus, we can omit the rest of the ranges consisting 
	    of the same starting index. 

	    The rest of the process remains the same as in the last approach.


Stats:

	- Time complexity : O(n^2). Two for loops are used.
	- Space complexity : O(1). Constant extra space is used.



public class Solution {
    public int numberOfArithmeticSlices(int[] A) {
        int count = 0;
        for (int s = 0; s < A.length - 2; s++) {
            int d = A[s + 1] - A[s];
            for (int e = s + 2; e < A.length; e++) {
                if (A[e] - A[e - 1] == d)
                    count++;
                else
                    break;
            }
        }
        return count;
    }
}

=======================================================================================================
method 2: DP


1) DP Solution with time O(n^2), space: O(n^2)

    - Assume A[i:j] (both include A[i] and A[j]) is an arithmetic slice, then we have:

    	if A[i]-A[i-1] == A[i+1]-A[i], then A[i-1:j] is an arithmetic slice;
    	if A[j+1]-A[j] == A[j]-A[j-1], then A[i:j+1] is an arithmetic slice.

    - use dp[i][j] to memorize whether A[i:j] is an arithmetic slice, and count the num of 
      arithmetic slices:

    public int numberOfArithmeticSlices(int[] A) {
        int n=A.length;
        if(n<3)
        	return 0;

        //initial value is false
        boolean[][] dp=new boolean[n][n]; 
        int count=0;

        // update only the 3-ones
        for(int i=0; i<n-3+1; i++){
            if((A[i+1]-A[i])==(A[i+2]-A[i+1])){
                dp[i][i+3-1]=true;
                count++;
            }
        }

        // start checking for sequence longer than 3
        // k = length of the sequence
        for(int k=4;k<=n;k++){

            for (int i=0; i<n-k+1; i++){
            	// j = ending index of the sequence that start at i and has length = 4
                int j=i+k-1;

                // can extend to its previous one
                if(dp[i+1][j]==true && (A[i+1]-A[i]==A[i+2]-A[i+1])){
                    dp[i][j]=true;
                    count++;
                } else if(dp[i][j-1]==true && (A[j]-A[j-1]==A[j-1]-A[j-2])){
                    dp[i][j]=true;
                    count++;
                }
            }
        }
        return count;
    }



2) DP Solution: time O(n), space O(n)

sub problem: assume dp[i] is the num of arithmetic slices which are end with A[i]
	
    if A[i]-A[i-1] == A[i-1]-A[i-2]:
        dp[i] = dp[i-1] + 1         // find another slice, ex. A[i] & A[i-1]
    else:
        dp[i] = 0                   // 断掉了


public int numberOfArithmeticSlices(int[] A) {
    int n=A.length;
    
    if(n<3)
    	return 0;

    int[] dp=new int[n];
    dp[0]=0;
    dp[1]=0;
    int sum=0;
    for(int i=2;i<n;i++){
        if((A[i]-A[i-1])==(A[i-1]-A[i-2])){
            dp[i]=dp[i-1]+1;
        }else{
            dp[i]=0;
        }
        sum+=dp[i];
    }
    return sum;
}

3) time O(n), space O(1)
	i)   We need minimum 3 indices to make arithmetic progression,
	ii)  So start at index 2, see if we got two diffs same, so we get a current 1 arith sequence
	iii) At any index i, if we see it forms arith seq with former two, that means running (curr) 
		 sequence gets extended upto this index, at the same time we get one more sequence (the 
		 three numbers ending at i), so curr++. 
		 Any time this happens, add the curr value to total sum.

	iv)  Any time we find ith index does not form arith seq, make currently running num of seqs = zero.
	


Stats:

	- Time complexity : O(n). The recursive function is called at most n−2 times.
	- Space complexity : O(1). The depth of the recursion tree goes upto n−2.


public int numberOfArithmeticSlices(int[] A) {
    int curr = 0, sum = 0;
    for (int i=2; i<A.length; i++)
        if (A[i]-A[i-1] == A[i-1]-A[i-2]) {
            curr += 1;
            sum += curr;
        } else {
            curr = 0;
        }
    return sum;
}

