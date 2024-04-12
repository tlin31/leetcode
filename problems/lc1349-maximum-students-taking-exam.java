1349. Maximum Students Taking Exam - Hard


Given a m * n matrix seats  that represent seats distributions in a classroom. If a seat is broken, 
it is denoted by '#' character otherwise it is denoted by a '.' character.

Students can see the answers of those sitting next to the left, right, upper left and upper right, 
but he cannot see the answers of the student sitting directly in front or behind him. 

Return the maximum number of students that can take the exam together without any cheating 
being possible.

Students must be placed in seats in good condition.

 

Example 1:


Input: seats = [["#",".","#","#",".","#"],
                [".","#","#","#","#","."],
                ["#",".","#","#",".","#"]]
Output: 4
Explanation: Teacher can place 4 students in available seats so they dont cheat on the exam. 

Example 2:

Input: seats = [[".","#"],
                ["#","#"],
                ["#","."],
                ["#","#"],
                [".","#"]]
Output: 3
Explanation: Place all students in available seats. 

Example 3:

Input: seats = [["#",".",".",".","#"],
                [".","#",".","#","."],
                [".",".","#",".","."],
                [".","#",".","#","."],
                ["#",".",".",".","#"]]
Output: 10
Explanation: Place students in available seats in column 1, 3 and 5.
 

Constraints:

seats contains only characters '.' and'#'.
m == seats.length
n == seats[i].length
1 <= m <= 8
1 <= n <= 8


******************************************************
key:
        - 
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

        -       
        -  flat out the graph into a string, position[i][j] = i*n+j


class Solution {
    int m, n;
    Map<String, Integer> memo;

    public int maxStudents(char[][] seats) {
        m = seats.length;
        if(m==0) return 0;
        n = seats[0].length;
        
        memo = new HashMap<String, Integer>();

        // forms sb = '#.##.#.####.#.##.#'
        StringBuilder sb = new StringBuilder();
        for(char[] row: seats){
            sb.append(row);
        }
        
        return dfs(sb.toString());
    }
        
    /* dfs returns the max student we can place if start with the given state */
    public int dfs(String state){
        if(memo.containsKey(state)) 
            return memo.get(state);
       
        int max = 0;
        char[] C = state.toCharArray();

        // i is row, j is column
        for(int i = 0; i<m; i++){
            for(int j = 0; j<n; j++){

                //we see an empty seat, there are two choices, place a student here or leave it empty.
                if(C[i*n+j]== '.'){

                    //case 1: we choose not to place a student, but we place a x to mark this seat 
                    // as unanvailable, so we don't repeatedly search this state again. 
                    C[i*n+j] = 'x';
                    max = Math.max(max, dfs(new String(C)));
     
                    //case 2: we place a student, but this makes left, right, bottom left, 
                    // bottom right seat unavailable. 

                    // mark bottom right and right seat unavaliable
                    if(j+1<n){
                        if(i<m-1 && C[(i+1)*n+j+1] == '.') 
                            C[(i+1)*n+j+1] = 'x';
                        if(C[i*n+j+1] == '.') 
                            C[i*n+j+1] = 'x';
                    }

                    // mark bottom left and left seat unavaliable
                    if(j-1>=0){
                        if(i<m-1 && C[(i+1)*n+j-1] == '.') 
                            C[(i+1)*n+j-1]= 'x';
                        if(C[i*n+j-1] == '.') 
                            C[i*n+j-1] = 'x';
                    }

                    max = Math.max(max, 1+dfs(new String(C)));
                }
            }
        }
        memo.put(state, max);
        return max; 
    }
}






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: DP + bitmask

Stats:

        - Time: O(m * 4^n), m is the number of rows, n is the number of columns of matrix (m, n <= 8).
        - Space: O(m * 2^n)


Method:

    - For the bit part, everything is encoded as a single bit, so the whole state can be encoded 
      as a group of bits, i.e. a binary number. 
      For the mask part, we use 0/1 to represent the state of something. In most cases, 1 stands 
      for the valid state while 0 stands for the invalid state.

        ex. There are 4 cards on the table and I am going to choose several of them. We can encode 
            the 4 cards as 4 bits. Say, if we choose cards 0, 1 and 3, we will use the binary 
            number "1011" to represent the state. If we choose card 2, we will use the binary number
            "0100" then. The bits on the right represent cards with smaller id.

    - When doing Bitmasking DP, we are always handling problems like "what is the i-th bit in the
     state" or "what is the number of valid bits in a state". These problems can be very complicated 
    if we do not handle them properly. 
    
    1. We can use (x >> i) & 1 to get i-th bit in state x, where >> is the right shift operation. 
       If we are doing this in an if statement (i.e. to check whether the i-th bit is 1), we can 
       also use x & (1 << i), where the << is the left shift operation.

    2. We can use (x & y) == x to check if x is a subset of y. The subset means every state in x 
       could be 1 only if the corresponding state in y is 1.

    3. We can use (x & (x >> 1)) == 0 to check if there are no adjancent valid states in x.

    - This problem: use a bitmask of n bits to represent the validity of each row in the classroom. 
      The i-th bit is 1 if and only if the i-th seat is not broken. 

      For the first example in this problem, the bitmasks will be "010010", "100001" and "010010". 
      When we arrange the students to seat in this row, we can also use n bits to represent the 
      students. The i-th bit is 1 if and only if the i-th seat is occupied by a student. We should 
      notice that n bits representing students must be a subset of n bits representing seats.

    - dp[i][mask] = the maximum number of students for the first i rows while the students in 
                    the i-th row follow the masking mask (the states of i-th row). 

    There should be no adjancent valid states in mask. The transition function is:

        dp[i][mask] = max(dp[i - 1][mask of (i-1)th row]) + number of valid bits(mask)
    
    Conditions:
        (mask & (mask of (i-1)th row >> 1)) == 0, 
            - there should be no students in the upper left position for every student.

        ((mask >> 1) & (mask of (i-1)th row)) == 0, 
            - there should be no students in the upper right position for every student.

    If these two equation holds and dp[i - 1][mask''] itself is valid, we could then transit from 
       dp[i - 1][(mask of (i-1)th row)] to dp[i][mask] according to the transition function.

    And the last question is, how can we compute the number of valid bits in a masking efficiently? 
        - pre-compute by using count[i] = count[i/2] + (i % 2 == 1) and store them in an array.


class Solution {
    public int maxStudents(char[][] seats) {
        int m = seats.length, 
            n = seats[0].length;

        // ex. validRows[0] = 18 = 010010 for ["#",".","#","#",".","#"]
        int[] validRows = new int[m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                validRows[i] = (validRows[i] << 1) + (seats[i][j] == '.' ? 1 : 0);

        // There are 2^n states for n columns in binary format       
        int stateSize = 1 << n; 
        int[][] dp = new int[m][stateSize];
        for (int i = 0; i < m; i++) 
            Arrays.fill(dp[i], -1);

        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < stateSize; j++) {
                // (j & valid) == j: check if j is a subset of valid
                // !(j & (j >> 1)): check if there is no adjancent students in the row
                if (((j & validRows[i]) == j) && ((j & (j >> 1)) == 0)) {
                    if (i == 0) {
                        dp[i][j] = Integer.bitCount(j);
                    } else {
                        for (int k = 0; k < stateSize; k++) {
                            // !(j & (k >> 1)): no students in the upper left positions
                            // !((j >> 1) & k): no students in the upper right positions
                            // dp[i-1][k] != -1: the previous state is valid
                            if ((j & (k >> 1)) == 0 && ((j >> 1) & k) == 0 && dp[i-1][k] != -1)  {
                                
                                dp[i][j] = Math.max(dp[i][j], dp[i-1][k] + Integer.bitCount(j));
                            }
                        }
                    }
                    ans = Math.max(ans, dp[i][j]);
                }
            }
        }
        return ans;
    }
}




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

