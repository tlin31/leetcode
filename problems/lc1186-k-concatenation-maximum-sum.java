1186. Maximum Subarray Sum with One Deletion - Medium


Given an array of integers, return the maximum sum for a non-empty subarray (contiguous elements) 
with at most one element deletion. In other words, you want to choose a subarray and optionally 
delete one element from it so that there is still at least one element left and the sum of the 
remaining elements is maximum possible.

Note that the subarray needs to be non-empty after deleting one element.

 

Example 1:

Input: arr = [1,-2,0,3]
Output: 4
Explanation: Because we can choose [1, -2, 0, 3] and drop -2, thus the subarray [1, 0, 3] becomes 
the maximum value.


Example 2:

Input: arr = [1,-2,-2,3]
Output: 3
Explanation: We just choose [3] and its the maximum sum.


Example 3:

Input: arr = [-1,-1,-1,-1]
Output: -1
Explanation: The final subarray needs to be non-empty. You cant choose [-1] and delete -1 from it,
then get an empty subarray to make the sum equals to 0.
 

Constraints:

1 <= arr.length <= 10^5
-10^4 <= arr[i] <= 10^4



******************************************************
key:
	- prefix & suffix
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	-  Compute maxEndHere and maxStartHere arrays and also find overall max along with them.
	-  evaluate the case where 1-element can be eliminated, that is at each index, we can make 
	   use of maxEndHere[i-1] + maxStartHere[i+1]

	-  Basically, the difference here is we can eliminate 1 number and still can continue with 
	   expanding our subarray.
	-  imagine a subarray where you removed 1 element, then it forms two subarrays ending at prev 
	   index and starting at next index. We know how to get maxEndHere from the max sum subarray 
	   problem for each index. 

	   If we reverse our thinking to follow the same logic to solve for subarray at next index, 
	   we should be able to see computing maxStartHere is just backwards of maxEndHere. 
	   So now at each index, it is just about looking at prev and next numbers from the respective 
	   arrays to get overall max.


	public int maximumSum(int[] a) {
        int n = a.length;
        int[] prefix = new int[n], 
              suffix = new int[n];
        int max = a[0];
        prefix[0] = a[0];

        for(int i=1; i < n; i++){
            prefix[i] = Math.max(a[i], prefix[i-1] + a[i]);
            max = Math.max(max, prefix[i]);
        }

        suffix[n-1] = a[n-1];
        for(int i=n-2; i >= 0; i--)
            suffix[i] = Math.max(a[i], suffix[i+1] + a[i]);

        for(int i=1; i < n-1; i++)
            max = Math.max(max, prefix[i-1] + suffix[i+1]);

        return max;
    }




input: [1,-2,0,3]

prefix: [1, -1, 0, 3]
suffix: [2, 1, 3, 3]




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

2 cases:
	- ignored variable stores the contiguous cursum by ignoring any one element
	- notignored stores the contiguous sum by not ignoring any value so far

case 1)
	Whenever we have a positive value, we simply add the value to the ingored sum and notignored sum

case 2)
	If we encounter a negative value, adding negative value to notignored
	The ignored sum can be two things.
		1) We choose to ignore the current value, in which case, the ignored sum = notignored sum 
		   from before
		2) We choose to include the current value, in which case, we choose the previous ignored 
		   sum and add cur value to it.
	Which of these two options, to do, is simply the max of both options.

case 3)
	At the end of case 2) , if either ignored sum or notignored sum becomes negative, we can reset 
	them to 0.

def maximumSum(self, a: List[int]) -> int:
        ignored, notignored, res = 0, 0, a[0]
        for num in a:
            if num >= 0:
                ignored += num
				notignored += num
            else:
                ignored = max(ignored + num, notignored)
                notignored += num
                
            res = max([res, ignored if ignored != 0 else -math.inf, notignored if notignored != 0 else -math.inf])
            if ignored < 0: ignored = 0
            if notignored < 0: notignored = 0
        return max(res, max(a))    

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

