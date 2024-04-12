517. Super Washing Machines - Hard


You have n super washing machines on a line. Initially, each washing machine has some dresses or is empty.

For each move, you could choose any m (1 ≤ m ≤ n) washing machines, and pass one dress of each washing
machine to one of its adjacent washing machines at the same time .

Given an integer array representing the number of dresses in each washing machine from left to right 
on the line, you should find the minimum number of moves to make all the washing machines have the 
same number of dresses. If it is not possible to do it, return -1.

Example1

Input: [1,0,5]

Output: 3

Explanation: 
1st move:    1     0 <-- 5    =>    1     1     4
2nd move:    1 <-- 1 <-- 4    =>    2     1     3    
3rd move:    2     1 <-- 3    =>    2     2     2   

Example2
Input: [0,3,0]

Output: 2

Explanation: 
1st move:    0 <-- 3     0    =>    1     2     0    
2nd move:    1     2 --> 0    =>    1     1     1     
Example3

Input: [0,2,0]

Output: -1

Explanation: 
It's impossible to make all the three washing machines have the same number of dresses. 


Note:
The range of n is [1, 10000].
The range of dresses number in a super washing machine is [0, 1e5].


******************************************************
key:
	- Greedy
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time O(N) for two passes
	- Space O(1) for no extra space


Method:

	-  moveCount means the clothes that we need to pass to the right number.
	-  ex. machines[] is [0,0,11,5], total = 16 and target value for each machine = 4. Convert the
	   machines array to a kind of gain/lose array, we get: [-4,-4,7,1]. Now what we want to do is 
	   go from the first one and try to make all of them 0.
	-  To make the 1st machines 0, you need to give all its "load" to the 2nd machines.
		So we get: [0,-8,7,1]
		then: [0,0,-1,1]
		lastly: [0,0,0,0], done.
	-  You don't have to worry about the details about how these machines give load to each other. 
	   In this process, the least steps we need to eventually finish this process is determined by the
	   peak of abs(moveCount) and the max of "gain/lose" array. 
	   In this case, the peak of abs(moveCount) is 8 and the max of gain/lose array is 7. 
	   So the result is 8.

	-  ex. machines: [0,3,0]; gain/lose array: [-1,2,-1]; max = 2, moveCount = 0, -1, 1, 0, 
	       its abs peak is 1. So result is 2.
	-  ex. machines: [1,0,5]; gain/lose array: [-1,-2,3]; max = 3, moveCount = 0, -1, -3, 0, 
	       its abs peak is 3. So result is 3.

    public int findMinMoves(int[] machines) {
        int total = 0, n = machines.length;
        for (int load : machines)
            total += load;
        if (total % n > 0) 
        	return -1;
        int target = total / n, 
        	res = 0, 
        	moveCount = 0;

        for (int load : machines) {
            moveCount = moveCount + load - target;
            res = Math.max(res, Math.max(Math.abs(moveCount), load - target));
        }
        return res;
    }



=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



