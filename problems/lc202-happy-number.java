202. Happy Number - Easy

Write an algorithm to determine if a number is "happy".

A happy number is a number defined by the following process: 
	Starting with any positive integer, replace the number by the sum of the squares of its 
	digits, and repeat the process until the number equals 1 (where it will stay), or it 
	loops endlessly in a cycle which does not include 1. Those numbers for which this process 
	ends in 1 are happy numbers.

Example: 

Input: 19
Output: true
Explanation: 
1^2 + 9^2 = 82
8^2 + 2^2 = 68
6^2 + 8^2 = 100
1^2 + 0^2 + 0^2 = 1


******************************************************
key:
	- slow & fast rabbit to detect cycles
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	
	-	

Stats:

	- Time: O(log n) --> Finding the next value for a given number has a cost of O(logn) because 
	        we are processing each digit in the number, and the number of digits in a number is 
	        given by logn.

			To work out the total time complexity, we'll need to think carefully about how many 
			numbers are in the chain, and how big they are.

			We determined above that once a number is below 243, it is impossible for it to go back 
			up above 243. Therefore, based on our very shallow analysis we know for sure that once 
			a number is below 243, it is impossible for it to take more than another 243 steps to
			terminate. Each of these numbers has at most 3 digits. With a little more analysis, we
			could replace the 243 with the length of the longest number chain below 243, however
			because the constant doesn't matter anyway, we won't worry about it.

			For an n above 243, we need to consider the cost of each number in the chain that is 
			above 243. With a little math, we can show that in the worst case, these costs will 
			be O(logn)+O(loglogn)+O(logloglogn).... Luckily for us, the O(logn) is the dominating 
			part, and the others are all tiny in comparison (collectively, they add up to less 
			than logn), so we can ignore them.

	- Space: O(log n)


class Solution {

    private int getNext(int n) {
        int totalSum = 0;
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            totalSum += d * d;
        }
        return totalSum;
    }

    public boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();
        while (n != 1 && !seen.contains(n)) {
            seen.add(n);
            n = getNext(n);
        }
        return n == 1;
    }
}

=======================================================================================================
method 2: iterative

Method:

	-	
	-	


Stats:

	- 
	- 

    public boolean isHappy(int n) {
        int cur = n;
        HashSet<Integer> set = new HashSet<>();
        while (true){
            n = cur;
            cur = 0;
            while (n!=0) {
                cur += Math.pow(n%10,2);
                n = n/10;
            }
            if (cur == 1) return true;
            if (set.contains(cur)) return false;
            set.add(cur);
            
        }
    }
=======================================================================================================
method 3: not using hashset to detect cycles

Method:

	-	
	-	


Stats:

	- 
	- 

class Solution {

     public int getNext(int n) {
        int totalSum = 0;
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            totalSum += d * d;
        }
        return totalSum;
    }

    public boolean isHappy(int n) {
        int slowRunner = n;
        int fastRunner = getNext(n);
        while (fastRunner != 1 && slowRunner != fastRunner) {
            slowRunner = getNext(slowRunner);				// one hop
            fastRunner = getNext(getNext(fastRunner));		// two hops
        }
        return fastRunner == 1;
    }
}

