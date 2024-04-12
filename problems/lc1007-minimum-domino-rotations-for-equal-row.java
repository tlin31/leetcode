1007. Minimum Domino Rotations For Equal Row - Medium

In a row of dominoes, A[i] and B[i] represent the top and bottom halves of the i-th domino.  (A domino is 
a tile with two numbers from 1 to 6 - one on each half of the tile.)

We may rotate the i-th domino, so that A[i] and B[i] swap values.

Return the minimum number of rotations so that all the values in A are the same, or all the values in B 
are the same.

If it cannot be done, return -1.

 

Example 1:

Input: A = [2,1,2,4,2,2], B = [5,2,6,2,3,2]
Output: 2
Explanation: 
The first figure represents the dominoes as given by A and B: before we do any rotations.
If we rotate the second and fourth dominoes, we can make every value in the top row equal to 2, as 
indicated by the second figure.


Example 2:

Input: A = [3,5,1,2,3], B = [3,6,3,3,4]
Output: -1
Explanation: 
In this case, it is not possible to rotate the dominoes to make one row of values equal.
 

Note:

1 <= A[i], B[i] <= 6
2 <= A.length == B.length <= 20000

******************************************************
key:
	- Trick --> complement, use equation countA[i] + countB[i] - same[i] = n
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- 	Note: if A[0] works, no need to check B[0].
		Because if both A[0] and B[0] exist in all dominoes, when you swap A[0] in a whole row,
		you will swap B[0] in a whole at the same time.
		The result of trying A[0] and B[0] will be the same.
	- Solution:
		Count the occurrence of all numbers in A and B,
		and also the number of domino with two same numbers.

		Try all possibilities from 1 to 6.
		If we can make number i in a whole row,
		it should satisfy that countA[i] + countB[i] - same[i] = n

		Take example of
		A = [2,1,2,4,2,2]
		B = [5,2,6,2,3,2]

		countA[2] = 4, as A[0] = A[2] = A[4] = A[5] = 2
		countB[2] = 3, as B[1] = B[3] = B[5] = 2
		same[2] = 1, as A[5] = B[5] = 2

		We have countA[2] + countB[2] - same[2] = 6,
		so we can make 2 in a whole row.

stats:

	- Time O(N)
	- Space O(1)

    public int minDominoRotations(int[] A, int[] B) {
        int[] countA = new int[7], 
        	  countB = new int[7], 
        	  same = new int[7];

        int n = A.length;
        for (int i = 0; i < n; ++i) {
            countA[A[i]]++;
            countB[B[i]]++;
            if (A[i] == B[i])
                same[A[i]]++;
        }

        for (int i  = 1; i < 7; ++i)
            if (countA[i] + countB[i] - same[i] == n)
                return n - Math.max(countA[i], countB[i]);

        return -1;
    }


=======================================================================================================
method 2:

method:
	- observation, pick a random number in array (here namingly, A[0]), if this works then good,
	  but if this fails, no way trying A[1] or A[2] .....A[n] will work! 
	- 	Try make A[0] in a whole row, the condition is that A[i] == A[0] || B[i] == A[0]
	  	a and b are the number of swap to make a whole row A[0]

		Try B[0]
		the condition is that A[i] == B[0] || B[i] == B[0]
		a and b are the number of swap to make a whole row B[0]

		Return -1
	- 

stats:

	- 
	- 

    public int minDominoRotations(int[] A, int[] B) {
        int n = A.length;

        // try making all equals to a[0]
        for (int i = 0, a = 0, b = 0; i < n && (A[i] == A[0] || B[i] == A[0]); ++i) {
            if (A[i] != A[0]) a++;
            if (B[i] != A[0]) b++;
            if (i == n - 1) 
            	return Math.min(a, b);
        }

        // try making all equals to b[0]
        for (int i = 0, a = 0, b = 0; i < n && (A[i] == B[0] || B[i] == B[0]); ++i) {
            if (A[i] != B[0]) a++;
            if (B[i] != B[0]) b++;
            if (i == n - 1) 
            	return Math.min(a, b);
        }
        return -1;
    }

=======================================================================================================
method 3:

method:

	- Find intersection set s of all {A[i], B[i]}
		s.size = 0, no possible result.
		s.size = 1, one and only one result.
		s.size = 2, it means all dominoes are [a,b] or [b,a], try either one.
		s.size > 2, impossible.
	- 

stats:

	- 
	- 


Java:

    public int minDominoRotations(int[] A, int[] B) {
        HashSet<Integer> s = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        int[] countA = new int[7], countB = new int[7];
        for (int i = 0; i < A.length; ++i) {
            s.retainAll(new HashSet<>(Arrays.asList(A[i], B[i])));
            countA[A[i]]++;
            countB[B[i]]++;
        }
        for (int i : s)
            return A.length - Math.max(countA[i], countB[i]);
        return -1;
    }


