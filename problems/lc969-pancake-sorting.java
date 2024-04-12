969. Pancake Sorting - Medium


Given an array A, we can perform a pancake flip: We choose some positive integer k <= A.length, 
then reverse the order of the first k elements of A.  We want to perform zero or more pancake 
flips (doing them one after another in succession) to sort the array A.

Return the k-values corresponding to a sequence of pancake flips that sort A.  Any valid answer 
that sorts the array within 10 * A.length flips will be judged as correct.

 

Example 1:

Input: [3,2,4,1]
Output: [4,2,4,3]

Explanation: 
We perform 4 pancake flips, with k values 4, 2, 4, and 3.
Starting state: A = [3, 2, 4, 1]
After 1st flip (k=4): A = [1, 4, 2, 3]
After 2nd flip (k=2): A = [4, 1, 2, 3]
After 3rd flip (k=4): A = [3, 2, 1, 4]
After 4th flip (k=3): A = [1, 2, 3, 4], which is sorted. 

Example 2:

Input: [1,2,3]
Output: []
Explanation: The input is already sorted, so there is no need to flip anything.
Note that other answers, such as [3, 3], would also be accepted.
 

Note:

1 <= A.length <= 100
A[i] is a permutation of [1, 2, ..., A.length]


******************************************************
key:
	- find max and reverse twice
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time: O(N^2), Flips: 2 * N
	- 


Method:

	-	
	-	Find the largest number & Flip twice to the tail


class Solution {
    public List<Integer> pancakeSort(int[] A) {
        List<Integer> result = new ArrayList<>();
        int n = A.length, 
            largest = n;
        for (int i = 0; i < n-1; i++,largest--) {
            int index = find(A, largest);
            if(index==largest-1) {
                continue;
            }

            // only do flip if max is not at index 0
            if(index > 0) {
                flip(A, index);
                result.add(index + 1);
            }
            flip(A, largest - 1);
            result.add(largest);
        }
        return result;
    }
    private int find(int[] A, int target) {
        for (int i = 0; i < A.length; i++) {
            if (A[i] == target) {
                return i;
            }
        }
        return -1;
    }
    private void flip(int[] A, int index) {
        int i = 0, j = index;
        while (i < j) {
            int temp = A[i];
            A[i++] = A[j];
            A[j--] = temp;
        }
    }
}

Input: [3,2,4,1]

flip 3: 4,2,3,1
flip 4: 1,3,2,4
flip 2: 3,1,2,4
flip 3: 2,1,3,4
flip 2: 1,2,3,4





~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

def pancakeSort(self, A: List[int]) -> List[int]:
	"""
	找到最大的数字，假设它的下标是i
	反转0到i之间的数字，使得A[i]变成第一个数
	反转整个数组，让最大的数到末尾
	"""
	n = len(A)
	res = []
	for i in range(n):
		cur_max = max(A[0:n-i])
		j = 0
		while A[j] != cur_max:
			j += 1

		# should reverse j+1 elements
		A[:j+1] = reversed(A[:j+1])
		res.append(j+1)

		# reverse all
		A[:n-i] = reversed(A[:n-i])
		res.append(n-i)
	return res

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

