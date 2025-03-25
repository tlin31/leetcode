1718. Construct the Lexicographically Largest Valid Sequence-Medium

Given an integer n, find a sequence with elements in the range [1, n] that satisfies all of 
the following:

The integer 1 occurs once in the sequence.
Each integer between 2 and n occurs twice in the sequence.
For every integer i between 2 and n, the distance between the two occurrences of i is exactly i.
The distance between two numbers on the sequence, a[i] and a[j], is the absolute 
difference of their indices, |j - i|.

Return the lexicographically largest sequence. It is guaranteed that under the given 
constraints, there is always a solution.

A sequence a is lexicographically larger than a sequence b (of the same length) if in the 
first position where a and b differ, sequence a has a number greater than the corresponding 
number in b. For example, [0,1,9,0] is lexicographically larger than [0,1,5,6] because the 
first position they differ is at the third number, and 9 is greater than 5.

 

Example 1:

Input: n = 3
Output: [3,1,2,3,2]
Explanation: [2,3,2,1,3] is also a valid sequence, but [3,1,2,3,2] is the lexicographically 
largest valid sequence.
Example 2:

Input: n = 5
Output: [5,3,1,4,3,5,2,4,2]
 

Constraints:

1 <= n <= 20

******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

	-	Since 'n' ranges between 1 to 20 , we can afford a solution of exponential time complexity.

We try to construct our result array by taking the largest number possible at each point.

Except 1 (since its count is 1) whenever we place an integer in a particular position, we also place the second occurence of that integer in our temporary array. This is because in the question it is mentioned that : For every integer i between 2 and n, the distance between the two occurrences of i is exactly i.

As soon as we reach a valid solution that follows all the constraints we return true This indicates we do not need to continu e our search (This is an important step as otherwise we will get TLE).
Since we start from largest possible number at each step once we reach a valid solution that is our answer.





Stats:

Time Complexity : Ideally it should be (n!) since we are using backtracking and trying to find out all possible solutions.

But because of this constraint : For every integer i between 2 and n, the distance between the two occurrences of i is exactly i, the number of permutations under consideration is reducing greatly.

Also since we are trying to find out the lexicographically largest sequence we stop as soon as we find a valid solution (early stop).




class Solution {

        public int[] constructDistancedSequence(int n) {

        	//1个1和两倍的剩下的数字
            int[] ans = new int[n * 2 - 1];
            boolean[] visited = new boolean[n + 1];
            calc(0, ans, visited, n);
            return ans;
        }

        private boolean calc(int index, int[] ans, boolean[] visited, int n) {
            if (index == ans.length) {
                return true;
            }
            if (ans[index] != 0) 
            	// value already assigned in this position. So go ahead with the next index.
            	return calc(index + 1, ans, visited, n); 
            
            else {
				// we start from n to 1 since we need to find out the lexicographically largest sequence.
                for (int i = n; i >= 1; i--) {

                    if (visited[i]) continue;

                    //start the backtrack
                    visited[i] = true;
                    ans[index] = i;
                    if (i == 1) {
                        if (calc(index + 1, ans, visited, n)) 
                        	return true;
                    } 

                    else if (index + i < ans.length && ans[index + i] == 0) {
                        // assigning the second occurence of i in the desired position i.e, (current index + i )
                        ans[i + index] = i; 

						// largest possible sequence satisfying the given conditions found.
                        if (calc(index + 1, ans, visited, n)) 
                        	return true; 

                        ans[index + i] = 0;
                    }
                    ans[index] = 0;
                    visited[i] = false;
                }

            }
            return false;
        }
    }
	









