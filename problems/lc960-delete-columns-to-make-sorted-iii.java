960. Delete Columns to Make Sorted III - hard


We are given an array A of N lowercase letter strings, all of the same length.

Now, we may choose any set of deletion indices, and for each string, we delete all the characters 
in those indices.

For example, if we have an array A = ["babca","bbazb"] and deletion indices {0, 1, 4}, then the final
array after deletions is ["bc","az"].

Suppose we chose a set of deletion indices D such that after deletions, the final array has every 
element (row) in lexicographic order.


For clarity, A[0] is in lexicographic order (ie. A[0][0] <= A[0][1] <= ... <= A[0][A[0].length - 1]), 
A[1] is in lexicographic order (ie. A[1][0] <= A[1][1] <= ... <= A[1][A[1].length - 1]), and so on.

Return the minimum possible value of D.length.

Example 1:

Input: ["babca","bbazb"]
Output: 3
Explanation: After deleting columns 0, 1, and 4, the final array is A = ["bc", "az"].
Both these rows are individually in lexicographic order (ie. A[0][0] <= A[0][1] and A[1][0] <= A[1][1]).
Note that A[0] > A[1] - the array A isnt necessarily in lexicographic order.


Example 2:

Input: ["edcba"]
Output: 4
Explanation: If we delete less than 4 columns, the only row wont be lexicographically sorted.


Example 3:

Input: ["ghi","def","abc"]
Output: 0
Explanation: All rows are already lexicographically sorted.
Note:

1 <= A.length <= 100
1 <= A[i].length <= 100



******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP


Stats:

	- Time Complexity:
		O(N^2) to find increasing subsequence
		O(M) for comparing elements.
		So Overall O(MN^2) time.
	- Space:
		O(N) for dp.


Method:

	- Take n cols as n elements, so we have an array of n elements.
		=> The final array has every row in lexicographic order.
		=> The elements in final state is in increasing order.
		=> The final elements is a sub sequence of initial array.
		=> Transform the problem to find the maximum increasing sub sequence.

	- dp[i] := max length of increasing sub-sequence (of all strings) ends with i-th letter.

		For all j < i, if A[][j] < A[][i], then dp[j] = max(dp[j], dp[i] + 1)




 public int minDeletionSize(String[] A) {
        int numOfString = A.length, 
        	strLength = A[0].length(), 
        	res = strLength - 1, 
        	k;

        int[] dp = new int[strLength];
        Arrays.fill(dp, 1);
 		for (int end = 0; end < strLength; end++) {
            for (int start = 0; start < end; start++) {

            	// check the pos in each string in array
                for (k = 0; k < numOfString; k++) {
                    if (A[k].charAt(start) > A[k].charAt(end)) 
                    	break;
                }

                // if all strings are already in lexi sorted
                if (k == numOfString && dp[start] + 1 > dp[end])
                    dp[end] = dp[start] + 1;

                System.out.println("start = " + start + ", end = " + end + ", k = " + k);
                System.out.println("dp = " + Arrays.toString(dp));
            }

            // update res
            res = Math.min(res, strLength - dp[end]);
        }
        return res;
    }


ex.
start = 0, end = 1, k = 0 	--> break at first string
dp = [1, 1, 1, 1, 1]
start = 0, end = 2, k = 1
dp = [1, 1, 1, 1, 1]
start = 1, end = 2, k = 1
dp = [1, 1, 1, 1, 1]

found!
start = 0, end = 3, k = 2
dp = [1, 1, 1, 2, 1]

start = 1, end = 3, k = 2
dp = [1, 1, 1, 2, 1]
start = 2, end = 3, k = 2
dp = [1, 1, 1, 2, 1]
start = 0, end = 4, k = 0
dp = [1, 1, 1, 2, 1]

found!
start = 1, end = 4, k = 2
dp = [1, 1, 1, 2, 2]

start = 2, end = 4, k = 0
dp = [1, 1, 1, 2, 2]
start = 3, end = 4, k = 0
dp = [1, 1, 1, 2, 2]


~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

classSolution:
    def minDeletionSize(self, A):
        """
        :type A: List[str]
        :rtype: int
        """
        r, c = len(A), len(A[0])
        mem = [1] * c
        for i in range(1, c):
            for j in range(i):
                for k in range(r+1):  
                    if k == r:
                        mem[i] = max(mem[i], mem[j] + 1)
                    elif A[k][j] > A[k][i]:
                        break
                        
        return c - max(mem)



=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	

~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	


~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

