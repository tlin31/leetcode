1025. Divisor Game - Easy

Alice and Bob take turns playing a game, with Alice starting first.

Initially, there is a number N on the chalkboard. On each players turn, that player makes a move
consisting of:

	1. Choosing any x with 0 < x < N and N % x == 0.
	2. Replacing the number N on the chalkboard with N - x.

Also, if a player cannot make a move, they lose the game.

Return True if and only if Alice wins the game, assuming both players play optimally.

 

Example 1:

Input: 2
Output: true
Explanation: Alice chooses 1, and Bob has no more moves.
Example 2:

Input: 3
Output: false
Explanation: Alice chooses 1, Bob chooses 1, and Alice has no more moves.
 

Note:

1 <= N <= 1000

******************************************************
key:
	- Trick, brain teaser
	- return N %2 == 0
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	Conclusion
		If N is even, can win. If N is odd, will lose.

	Recursive Prove （Top-down)
	If N is even:
		We can choose x = 1.
		The opponent will get N - 1, which is a odd.
		Reduce to the case odd and he will lose.

	If N is odd,
		2.1 If N = 1, lose directly.
		2.2 We can ONLY choose an odd x.
		The opponent will get N - x, which is a even.
		Reduce to the case even and he will win.

	So the N will change odd and even alternatively until N = 1.


Stats:

	- 
	- 




=======================================================================================================
method 2: brute force/starting point

Method:

	-	
	-	


Stats:

	- 
	- 

public boolean divisorGame(int n) {
        for(int x=1; x < n; x++)
            if(n % x == 0 && !divisorGame(n-x)) //if Bob fails, Alice wins
                return true;
        return false;
    } 
=======================================================================================================
method 3: DP

Method:

	-	
	-	


Stats:

	- 
	- 


dp[i] indicates the result of the game for the given number i and for the player who started the game.
Alice will try all factors and choose the one which gives his opponent a losing value.

public boolean divisorGame(int N) {
        boolean[] dp = new boolean[N+1];
        dp[0] = false;
        dp[1] = false;
        for(int i=2; i <= N; i++){
            for(int j=1; j < i; j++){
                if(i % j == 0){
                    if(dp[i-j] == false){
                        dp[i] = true;
                        break;
                    }
                }
            }
        }
        return dp[N];
    }
