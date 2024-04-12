801. Minimum Swaps To Make Sequences Increasing - Medium


We have two integer sequences A and B of the same non-zero length.

We are allowed to swap elements A[i] and B[i].  Note that both elements are in the same index position 
in their respective sequences.

At the end of some number of swaps, A and B are both strictly increasing.  (A sequence is strictly 
increasing if and only if A[0] < A[1] < A[2] < ... < A[A.length - 1].)

Given A and B, return the minimum number of swaps to make both sequences strictly increasing.  It is
guaranteed that the given input always makes it possible.

Example:
Input: A = [1,3,5,4], B = [1,2,3,7]
Output: 1
Explanation: 
Swap A[3] and B[3].  Then the sequences are:
A = [1, 3, 5, 7] and B = [1, 2, 3, 4]
which are both strictly increasing.

Note:

A, B are arrays with the same length, and that length will be in the range [1, 1000].
A[i], B[i] are integer values in the range [0, 2000].



******************************************************
key:
    - 
    - edge case:
        1) empty string, return empty
        2)

******************************************************



=======================================================================================================
Method 1:


Stats:

    - O（n）
    - O(n)


Method:
    -  Since 'the given input always makes it possible', at least one of the two conditions above 
       should be satisfied.
        1. areBothSelfIncreasing: A[i - 1] < A[i] && B[i - 1] < B[i], 
        2. areInterchangeIncreasing: A[i - 1] < B[i] && B[i - 1] < A[i].
    -  swapRecord means for the ith element in A and B, the minimum swaps if we swap A[i] and B[i]
    -  fixRecord means for the ith element in A and B, the minimum swaps if we DO NOT swap A[i] and B[i]

class Solution {
    public int minSwap(int[] A, int[] B) {
        int swapRecord = 1, 
            fixRecord = 0;

        for (int i = 1; i < A.length; i++) {

            // means if we swap current i, then we have to swap (i-1) as well, so new swapRecord
            // equals to last swapRecord + 1
            if (A[i - 1] >= B[i] || B[i - 1] >= A[i]) {
                // fixRecord = fixRecord;
                swapRecord++;
            } 

            // need to swap, 1) swap current index & don't change prev index-->swapRecord = fixRecord + 1
            // 2) fix current index, and swap the previous index
            else if (A[i - 1] >= A[i] || B[i - 1] >= B[i]) {
                int temp = swapRecord;
                swapRecord = fixRecord + 1;
                fixRecord = temp;
            } 

            // Either swap or fix is OK. Let's keep the minimum one
            else {
                int min = Math.min(swapRecord, fixRecord);
                swapRecord = min + 1;
                fixRecord = min;
            }
        }
        return Math.min(swapRecord, fixRecord);
    }
}


ex.

index               0    1    2    3    4

A                   1    3    5    4    9
B                   1    2    3    7    10   

swapRecord          1    1    2    1    2
fixRecord           0    0    0    2    1


Obviously, swapRecord[0] = 1 and fixRecord[0] = 0.

-  For i = 1, either swap or fix is OK. So we take the minimum previous result, min = min(swapRecord[0], 
   fixRecord[0]) = 0. swapRecord[1] = min + 1 = 1, fixRecord[1] = min = 0

-  For i = 2, notice that A[1] >= B[2], which means the manipulation of A[2] and B[2] should be same 
   as A[1] and B[1], if A[1] and B[1] swap, A[2] and B[2] should swap, vice versa. 
   So swapRecord[2] = swapRecord[1]++ 
      fixRecord[2] = fixRecord[1]

-  For i = 3, notice that A[2] >= A[3], which mean the manipulation of A[3] and B[3] and A[2] and B[2] 
   should be opposite. In this case, swapRecord[3] = fixRecord[2] + 1 and fixRecord[3] = swapRecord[2]

-  For the last elements, its similiar as the elements when i = 1. Either swap or fix is OK. 



=======================================================================================================
method 2: DP

Stats:

    - 
    - 


Method:
    -  Since 'the given input always makes it possible', at least one of the two conditions above 
       should be satisfied.
            1. areBothSelfIncreasing: A[i - 1] < A[i] && B[i - 1] < B[i], 
            2. areInterchangeIncreasing: A[i - 1] < B[i] && B[i - 1] < A[i].
    -  state function
            state(i, 0) = min swaps to make A[0..i] and B[0..i] both increasing if we do not swap 
                          A[i] with B[i]
            
            state(i, 1) = min swaps to make A[0..i] and B[0..i] both increasing if we do swap A[i] 
                          with B[i]

    -  goal state: min{state(n - 1, 0), state(n - 1, 1)} where n = A.length

    - Base case:
        if i == 0, 
            state(0, 0) = 0; 
            state(0, 1) = 1;
            
Generally speaking,
    if areBothSelfIncreasing && areInterchangeIncreasing
            // Doesn't matter whether the previous is swapped or not.
            state(i, 0) = Math.min(state(i - 1, 0), state(i - 1, 1));
            state(i, 1) = Math.min(state(i - 1, 0), state(i - 1, 1)) + 1;

    else if areBothSelfIncreasing
            // Following the previous action.
            state(i, 0) =  state(i - 1, 0);
            state(i, 1) =  state(i - 1, 1) + 1;

    else if areInterchangeIncreasing
            // Opposite to the previous action.
            state(i, 0) = state(i - 1, 1);      // if we swap the previous one and this one unchanged
            state(i, 1) = state(i - 1, 0) + 1;  // we swap this one, then we don't need to swap
                                                    // previous ones



    public int minSwap(int[] A, int[] B) {
        int n = A.length;
        
        /* 
         state[i][0] is min swaps too make A[0..i] and B[0..i] increasing if we do not swap A[i] and B[i]; 
         state[i][1] is min swaps too make A[0..i] and B[0..i] increasing if we swap A[i] and B[i].
         */
        int[][] state = new int[n][2];
        state[0][1] = 1;
        
        for (int i = 1; i < n; i++) {
            boolean areBothSelfIncreasing = A[i - 1] < A[i] && B[i - 1] < B[i];
            boolean areInterchangeIncreasing = A[i - 1] < B[i] && B[i - 1] < A[i];
            
            if (areBothSelfIncreasing && areInterchangeIncreasing) {
                state[i][0] = Math.min(state[i - 1][0], state[i - 1][1]);
                state[i][1] = Math.min(state[i - 1][0], state[i - 1][1]) + 1;
            } else if (areBothSelfIncreasing) {
                state[i][0] = state[i - 1][0];
                state[i][1] = state[i - 1][1] + 1;
            } else { // if (areInterchangeIncreasing)
                state[i][0] = state[i - 1][1];
                state[i][1] = state[i - 1][0] + 1;
            }
        }
        
        return Math.min(state[n - 1][0], state[n - 1][1]);
    }




=======================================================================================================
method 3:

Stats:

    - 
    - 


Method:

    -   
    -   



