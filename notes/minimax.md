## Minimax Algorithm in Game Theory

### Basics
- backtracking algorithm used in decision making and game theory to find the optimal move for a player, assuming that your opponent also plays optimally. 

- widely used in two player turn-based games such as Tic-Tac-Toe, Backgammon, Mancala, Chess, etc.

- two players are called maximizer and minimizer. The maximizer tries to get the highest score possible while the minimizer tries to do the opposite and get the lowest score possible.

- The values of the board are calculated by some heuristics which are unique for every type of game.


### Code

```java
import java.io.*; 
  
class GFG { 
    
// Returns the optimal value a maximizer can obtain. 
// depth is current depth in game tree. 
// nodeIndex is index of current node in scores[]. 
// isMax is true if current move is of maximizer, else false 
// scores[] stores leaves of Game tree. 
// h is maximum height of Game tree 

 static int minimax(int depth, int nodeIndex, boolean  isMax, int scores[], int h) 
{ 
    // Terminating condition. i.e leaf node is reached 
    if (depth == h) 
        return scores[nodeIndex]; 
  
    // If current move is maximizer, find the maximum attainable value 
    if (isMax) 
    	return Math.max(minimax(depth+1, nodeIndex*2, false, scores, h), 
            minimax(depth+1, nodeIndex*2 + 1, false, scores, h)); 
  
    // Else (If current move is Minimizer), find the minimum attainable value 
    else
        return Math.min(minimax(depth+1, nodeIndex*2, true, scores, h), 
            minimax(depth+1, nodeIndex*2 + 1, true, scores, h)); 
} 
  
// A utility function to find Log n in base 2 
 static int log2(int n) 
{ 
return (n==1)? 0 : 1 + log2(n/2); 
} 
  
// Driver code 
  
    public static void main (String[] args) { 
    // The number of elements in scores must be a power of 2. 
    int scores[] = {3, 5, 2, 9, 12, 5, 23, 23}; 
    int n = scores.length; 
    int h = log2(n); 
    int res = minimax(0, 0, true, scores, h); 
    System.out.println( "The optimal value is : "  +res);  
          
    } 
} 
```


### more

https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-2-evaluation-function/

https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-3-tic-tac-toe-ai-finding-optimal-move/


https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-4-alpha-beta-pruning/


https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-5-zobrist-hashing/



### Problems


 #	Title	Acceptance	Difficulty	Frequency
292	 Nim Game	55.8%	Easy	

294	 Flip Game II 	48.8%	Medium	

375	 Guess Number Higher or Lower II	38.5%	Medium	

464	 Can I Win	27.8%	Medium	

486	 Predict the Winner	47.0%	Medium	

843	 Guess the Word	44.4%	Hard	

877	 Stone Game	62.1%	Medium	

913	 Cat and Mouse	29.1%	Hard	






