457. Circular Array Loop - Medium


You are given a circular array nums of positive and negative integers. 
If a number k at an index is positive, then move forward k steps. 
Conversely, if it is negative (-k), move backward k steps. 
Since the array is circular, you may assume that the last elements next element is the first 
element, and the first elements previous element is the last element.

Q: Determine if there is a loop (or a cycle) in nums. 

A cycle must start and end at the same index and the cycles length > 1. Furthermore, movements 
in a cycle must all follow a single direction. In other words, a cycle must not consist of both 
forward and backward movements.

 

Example 1:

Input: [2,-1,1,2,2]
Output: true
Explanation: There is a cycle, from index 0 -> 2 -> 3 -> 0. The cycle length is 3.
Example 2:

Input: [-1,2]
Output: false
Explanation: The movement from index 1 -> 1 -> 1 ... is not a cycle, because the cycle length is 1. 
By definition the cycle length must be greater than 1.

Example 3:

Input: [-2,1,-1,-2,-2]
Output: false
Explanation: The movement from index 1 -> 2 -> 1 -> ... is not a cycle, because movement from 
index 1 -> 2 is a forward movement, but movement from index 2 -> 1 is a backward movement. 
All movements in a cycle must follow a single direction.
 

Note:

-1000 ≤ nums[i] ≤ 1000
nums[i] ≠ 0
1 ≤ nums.length ≤ 5000
 

Follow up:

Could you solve it in O(n) time complexity and O(1) extra space complexity?


******************************************************
key:
	- slow & fast pointers
	- if positive, all positive (to ensure 1 direction)
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: slow and fast pointer


Stats:

	- 
	- 


Method:

	- The "next" function detects cases that we can abandon, while the main code focuses on finding 
	  a loop.

	- The main loop is a simple slow/fast pointer search to find loops. The next function uses 
	  Integer object, so that it can pass or return null as an end case. 
	  And null returns end the search.

	Null can be returned from next in the following cases:

		1. When input pos value is null (fast pointer that double-calls would cause this)
		2. When direction changes. Direction is captured on the first step in the "dir" value, and 
		    then passed to all follow-on calls to next. So the first time the product of dir and 
		    num[pos] is less than zero, we know we have changed direction, so return null.
		3. When a "self pointer" is found, meaning the next value from pos is the same index as pos.


public class Solution {
    public boolean circularArrayLoop(int[] nums) {
        boolean found = false;

        // ps = slow pointer, pf = fast pointer
        for ( int n=0; n<nums.length; n++ ) {
            Integer ps = n;
            Integer pf = next(nums, 0, n);
            int dir = nums[n];

            while ( ps != null && pf != null && ps != pf ) {
                ps = next(nums, dir, ps);
                pf = next(nums, dir, next(nums, dir, pf));
            }

            if ( ps != null && ps == pf ) {
                found = true;
                break;
            }
        }

        return found;
    }

    Integer next(int[] nums, int dir, Integer pos) {
        if ( pos == null ) 
        	return null; 

        // change in direction, return null
        if ( dir * nums[pos] < 0 ) 
        	return null; 

        Integer next = (pos + nums[pos]) % nums.length;

        // wrap negative for going back
        if ( next < 0 ) 
        	next += nums.length; 

		// self-pointer, return null
        if ( next == pos ) 
        	next = null; 
        return next;
    }
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

When we start a loop, as the problem description says, every item in the loop should have same sign, 
and when some loop have length more than len(nums) then it's indeed a circular loop, then we can 
avoid to have auxiliary storage

class Solution:
    def circularArrayLoop(self, nums: 'List[int]') -> 'bool':
        n = len(nums)
        for i, num in enumerate(nums):
            linkLength = 0
            j = i
            forward = nums[j] > 0
            while True:

            	# not same sign
                if (forward and nums[j] < 0) or (not forward and nums[j] > 0):
                    break

                nextj = (j + nums[j] + n) % n

                if nextj == j:
                    break

                j = nextj
                linkLength += 1
                if linkLength > n:
                    return True
        return False

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

