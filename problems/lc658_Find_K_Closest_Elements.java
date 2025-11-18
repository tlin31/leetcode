658. Find K Closest Elements - Medium


Given a sorted integer array arr, two integers k and x, return the k closest integers to x 
in the array. The result should also be sorted in ascending order.

An integer a is closer to x than an integer b if:

|a - x| < |b - x|, or
|a - x| == |b - x| and a < b
 

Example 1:

Input: arr = [1,2,3,4,5], k = 4, x = 3

Output: [1,2,3,4]

Example 2:

Input: arr = [1,1,2,3,4,5], k = 4, x = -1

Output: [1,1,2,3]

 

Constraints:

1 <= k <= arr.length
1 <= arr.length <= 104
arr is sorted in ascending order.
-104 <= arr[i], x <= 104


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

	Assume we are taking A[i] ~ A[i + k -1].
	We can binary research i
	We compare the distance between x - A[mid] and A[mid + k] - x

	@vincent_gui listed the following cases:
	Assume A[mid] ~ A[mid + k] is sliding window

	case 1: x - A[mid] < A[mid + k] - x, need to move window go left
	-------x----A[mid]-----------------A[mid + k]----------

	case 2: x - A[mid] < A[mid + k] - x, need to move window go left again
	-------A[mid]----x-----------------A[mid + k]----------

	case 3: x - A[mid] > A[mid + k] - x, need to move window go right
	-------A[mid]------------------x---A[mid + k]----------

	case 4: x - A[mid] > A[mid + k] - x, need to move window go right
	-------A[mid]---------------------A[mid + k]----x------




Stats:

	Time O(log(N - K)) to binary research and find result
	Space O(K) to create the returned list.




    public List<Integer> findClosestElements(int[] A, int k, int x) {
        int left = 0, right = A.length - k;
        while (left < right) {
            int mid = (left + right) / 2;
            if (x - A[mid] > A[mid + k] - x)
                left = mid + 1;
            else
                right = mid;
        }
        return Arrays.stream(A, left, left + k).boxed().collect(Collectors.toList());
    }






