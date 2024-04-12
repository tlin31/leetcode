464. Can I Win - Medium


In the "100 game," two players take turns adding, to a running total, any integer from 1..10. 
The player who first causes the running total to reach or exceed 100 wins.

What if we change the game so that players cannot re-use integers?

For example, two players might take turns drawing from a common pool of numbers of 1..15 without replacement until they reach a total >= 100.

Given an integer maxChoosableInteger and another integer desiredTotal, determine if the first player 
to move can force a win, assuming both players play optimally.

You can always assume that maxChoosableInteger will not be larger than 20 and desiredTotal will not 
be larger than 300.


Example

Input:
maxChoosableInteger = 10
desiredTotal = 11

Output:
false

Explanation:
No matter which integer the first player choose, the first player will lose.
The first player can choose an integer from 1 up to 10.
If the first player choose 1, the second player can only choose integers from 2 up to 10.
The second player will win by choosing 10 and get a total = 11, which is >= desiredTotal.
Same with other integers chosen by the first player, the second player will always win.


******************************************************
key:
	- check opponent should return false, DFS
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- O(2^n)
	- 


Method:

	-  We want to see if a path exists where all the branches for the opponent results in a false.
	   The opponent tries every single branch before he gives up and returns a false.

	-  Game state:	The unchosen numbers & The remaining desiredTotal to reach

	-  we can get the 2) by deducting the sum of chosen numbers from original desiredTotal.
	-  use a boolean array to denote which numbers have been chosen
		problem: if we want to use a Hashmap to remember the outcome of sub-problems, can we just use 
		         Map<boolean[], Boolean> ? 
		         can not the reference to boolean[] wont reveal the actual content in boolean[].

	-  Since in the problem statement, it says maxChoosableInteger will not be larger than 20, use 
	   Integer to represent this boolean[] array.

	ex. boolean[] is {false, false, true, true, false}, then we can transfer it to an Integer with 
	    binary representation as 00110. Since Integer is a perfect choice to be the key of HashMap, 
	    then we now can "memorize" the sub-problems using Map<Integer, Boolean>.


public class Solution {
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal <= 0) 
        	return true;

       	// can not add up desired total
        if (maxChoosableInteger*(maxChoosableInteger+1)/2 < desiredTotal) 
        	return false;

        return canIWin(desiredTotal, new int[maxChoosableInteger], new HashMap<>());
    }


    private boolean canIWin(int total, int[] state, HashMap<String, Boolean> hashMap) {
        String curr = Arrays.toString(state);

        if (hashMap.containsKey(curr)) 
        	return hashMap.get(curr);

        for (int i=0;i<state.length;i++) {

        	// pick an unchosen number
            if (state[i]==0) {
                state[i] = 1;

                // when total <= i +1, then no matter what the opponent choose, the opponent will lose
                if (total <= i+1 || !canIWin(total-(i+1), state, hashMap)) {
                    hashMap.put(curr, true);
                    state[i]=0;
                    return true;
                }
                
                state[i]=0;
            }
        }
        hashMap.put(curr, false);
        return false;
    }
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



