202. Happy Number - Easy

Write an algorithm to determine if a number n is happy.

A happy number is a number defined by the following process:

Starting with any positive integer, replace the number by the sum of the squares of its digits.
Repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.
Those numbers for which this process ends in 1 are happy.
Return true if n is a happy number, and false if not.

 

Example 1:

Input: n = 19
Output: true
Explanation:
1^2 + 9^2 = 82
8^2 + 2^2 = 68
6^2 + 8^2 = 100
1^2 + 0^2 + 0^2 = 1


******************************************************
key:
  - use hashset to find if it's looping
  - use Floyd's algo with fast and slow nodes to detect circle in a linked list storage

  - edge case:
    1) single digitis besides 1
    2)

******************************************************



=======================================================================================================
Method 1:

public boolean isHappy(int n) {
    Set<Integer> inLoop = new HashSet<Integer>();
    int squareSum,remain;
	while (inLoop.add(n)) {
		squareSum = 0;
		while (n > 0) {
		    remain = n%10;
			squareSum += remain*remain;
			n /= 10; 
		}
		if (squareSum == 1)
			return true;
		else
			n = squareSum;

	}
	return false;

}

=======================================================================================================

import java.util.LinkedList;

class Solution {
    public boolean isHappy(int n) {
        
        int slow = n;
        int fast = n;
		//while loop is not used here because initially slow and 
		//fast pointer will be equal only, so the loop won't run.
        do {
			//slow moving one step ahead and fast moving two steps ahead

            slow = square(slow);
            fast = square(square(fast));
        } while (slow != fast);

//if a cycle exists, then the number is not a happy number
//and slow will have a value other than 1

        return slow == 1;
    }
    
//Finding the square of the digits of a number

    public int square(int num) {
        
        int ans = 0;
        
        while(num > 0) {
            int remainder = num % 10;
            ans += remainder * remainder;
            num /= 10;
        }
        
        return ans;
    }
}