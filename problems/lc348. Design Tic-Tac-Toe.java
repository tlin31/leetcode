348. Design Tic-Tac-Toe - Medium

Assume the following rules are for the tic-tac-toe game on an n x n board between two players:

A move is guaranteed to be valid and is placed on an empty block.
Once a winning condition is reached, no more moves are allowed.
A player who succeeds in placing n of their marks in a horizontal, vertical, or diagonal row wins the game.
Implement the TicTacToe class:

TicTacToe(int n) Initializes the object the size of the board n.
int move(int row, int col, int player) Indicates that the player with id player plays at the cell (row, col) of the board. The move is guaranteed to be a valid move, and the two players alternate in making moves. Return
0 if there is no winner after the move,
1 if player 1 is the winner after the move, or
2 if player 2 is the winner after the move.
 

Example 1:

Input
["TicTacToe", "move", "move", "move", "move", "move", "move", "move"]
[[3], [0, 0, 1], [0, 2, 2], [2, 2, 1], [1, 1, 2], [2, 0, 1], [1, 0, 2], [2, 1, 1]]
Output
[null, 0, 0, 0, 0, 0, 0, 1]

Explanation
TicTacToe ticTacToe = new TicTacToe(3);
Assume that player 1 is "X" and player 2 is "O" in the board.
ticTacToe.move(0, 0, 1); // return 0 (no one wins)
|X| | |
| | | |    // Player 1 makes a move at (0, 0).
| | | |

ticTacToe.move(0, 2, 2); // return 0 (no one wins)
|X| |O|
| | | |    // Player 2 makes a move at (0, 2).
| | | |

ticTacToe.move(2, 2, 1); // return 0 (no one wins)
|X| |O|
| | | |    // Player 1 makes a move at (2, 2).
| | |X|

ticTacToe.move(1, 1, 2); // return 0 (no one wins)
|X| |O|
| |O| |    // Player 2 makes a move at (1, 1).
| | |X|

ticTacToe.move(2, 0, 1); // return 0 (no one wins)
|X| |O|
| |O| |    // Player 1 makes a move at (2, 0).
|X| |X|

ticTacToe.move(1, 0, 2); // return 0 (no one wins)
|X| |O|
|O|O| |    // Player 2 makes a move at (1, 0).
|X| |X|

ticTacToe.move(2, 1, 1); // return 1 (player 1 wins)
|X| |O|
|O|O| |    // Player 1 makes a move at (2, 1).
|X|X|X|
 

Constraints:

2 <= n <= 100
player is 1 or 2.
0 <= row, col < n
(row, col) are unique for each different call to move.
At most n^2 calls will be made to move.


Follow-up: Could you do better than O(n^2) per move() operation?


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

使用计数：

	rows[n]

	cols[n]

	diag

	antiDiag

	玩家 1 记为 +1
	玩家 2 记为 -1

	如果某行或某列的绝对值达到 n → 该玩家获胜。

为什么有效？
	因为连成一条线意味着该行（或列、对角线）被同一个玩家填满。
	用 +1/-1 可以直接判断总和是否为 n 或 -n。

状态变化示例（直观）

	例如 n = 3：

	玩家 1 在 (0,1) 落子 → rows[0] += 1 → 等等
	玩家 2 在 (0,2) 落子 → rows[0] -= 1

	如果 rows[0] == 3 → 玩家 1 赢
	如果 rows[0] == -3 → 玩家 2 赢


时间 O(1)，空间 O(n)

class TicTacToe {
    private int[] rows;
    private int[] cols;
    private int diag;
    private int antiDiag;
    private int n;

    public TicTacToe(int n) {
        this.n = n;
        rows = new int[n];
        cols = new int[n];
    }

    public int move(int row, int col, int player) {
        int val = (player == 1) ? 1 : -1;

        rows[row] += val;
        cols[col] += val;

        if (row == col) {
            diag += val;
        }
        if (row + col == n - 1) {
            antiDiag += val;
        }

        if (Math.abs(rows[row]) == n ||
            Math.abs(cols[col]) == n ||
            Math.abs(diag) == n ||
            Math.abs(antiDiag) == n) {
            return player;
        }

        return 0;
    }
}

1. 为什么用 +1/-1？

用整数累计，可以快速判断是否被一人占满：
abs(sum) == n → win。

2. 如果要支持撤销 move？

你需要反向更新 rows/cols/diag/antiDiag 的值即可。

1. 如何支持 redo？（CTRL+Z → CTRL+Y）

	用两个栈：undo 栈 + redo 栈

	undo 时从 undo 栈 pop → push 到 redo 栈

	redo 时反过来

2. 如何避免重复判断胜负？

	撤销时需要记录 winner 状态，可扩展一个变量 winner。

3. 如何支持多玩家 (>2 players)？

	不能用 +1/-1
	需要改成计数法：
	rows[row][player]++


