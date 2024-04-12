Airbnb - sliding puzzle

Given a 3*3 grid contains 8 tiles, with one empty square. Each tile has a number from 1 to 8. 
You are allowed to move the adjacent tiles horizontally or vertically into the empty square, 
our goal is to rearrange the tiles so the number on each of the tile match the final order.



/*
Assumption:
1. We are going to define a class Puzzle
we can either shuffle the board to get a random board or take an 2D array to create the board
 - provide api decide that if current puzzle can be solved or not
 - provide api to solve the puzzle if puzzle can be solved

2. Assume the board is 3 * 3
Approach: We can first model the Puzzle as a 2D array, This problem can be considered as a graph 
search problem.

Each state of array is a node on the graph, We want to find if we can start from one state and 
reach the end state.

For example, start with [[1,2,3][4,5,6][7,0,8]], we can represents the board as "123456708", and 
we want to know if we can finally become "123456780" by moving the 0. 

We can do a BFS start with the init state string "123456708", each time we move 0 to 4 direction 
by swap the position of 0 on the string. 

Time: O(n*n!)
    - in the worst case, there are n! possible permutation of the number, where n is the number 
      of element in the array. We tried all of them. Therefore, transfer from one state to 
      another takes O(n). 
Space: O(n!)
    - In the worst case, queue poll one permutation out, offer 4 new permutation, there are n! 
      permutation. the queue need to store n! words.

========================================
Follow up:
1. N * N puzzle: use a delimeter, store state as "1,2,3,4,5,6,7...", then parse it to array to 
   find the index of 0

 */

import java.util.*;

public class BSlidingPuzzle {
    public static void main(String[] args) {
        int[][] matrix = {{2,0,3},
                {1,4,5},
                {7,8,6}};
        Puzzle puzzle = new Puzzle(matrix);
        System.out.println(puzzle.canSolve());
        for (int i = 0; i < 20; i++) {
            puzzle = new Puzzle();
            System.out.println(puzzle.canSolve());
        }
    }
}

class Puzzle {

    private String state;

    private static final String target = "123456780";
    private static final int BOARD_SIZE = 3;
    private static final int[] DIR_X = {-1, 1, 0, 0};
    private static final int[] DIR_Y = {0, 0, -1, 1};
    private static final String[] DIR = {"UP", "DOWN", "LEFT", "RIGHT"};

    public Puzzle() {
        shuffle();
    }

    public Puzzle(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                sb.append(matrix[i][j]);
            }
        }
        this.state = sb.toString();
    }

    private void shuffle() {
        char[] arr = new char[BOARD_SIZE * BOARD_SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (char)(i + '0');
        }
        for (int i = 0; i < arr.length; i++) {
            int rand = (int)(Math.random() * (arr.length - i));
            char temp = arr[i];
            arr[i] = arr[i + rand];
            arr[i + rand] = temp;
        }
        this.state = new String(arr);
    }

    public boolean canSolve() {
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, List<String>> prev = new HashMap<>();
        queue.offer(state);
        visited.add(state);
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            if (curr.equals(target)) {
                getPath(prev);
                return true;
            }
            int zero = curr.indexOf("0");
            int row = zero / BOARD_SIZE;
            int col = zero % BOARD_SIZE;

            // only 4 actions
            for (int i = 0; i < 4; i++) {
                int nextRow = row + DIR_X[i];
                int nextCol = col + DIR_Y[i];
                if (inBound(nextRow, nextCol)) {
                    // index of zero in string
                    int nextZero = nextRow * BOARD_SIZE + nextCol;

                    // swap cur zero and new zero after action
                    String next = moveBoard(curr, zero, nextZero);
                    if (!visited.contains(next)) {
                        visited.add(next);
                        queue.offer(next);
                        prev.put(next, Arrays.asList(curr, DIR[i]));
                    }
                } // end if

            } // end for
        }
        return false;
    }

    private void getPath(Map<String, List<String>> prev) {
        List<String> path = new ArrayList<>();
        String curr = target;

        while (prev.containsKey(curr)) {
            path.add(prev.get(curr).get(1));
            curr = prev.get(curr).get(0);
        }
        Collections.reverse(path);
        System.out.println(path);
    }

    private String moveBoard(String str, int x, int y) {
        char[] arr = str.toCharArray();
        char temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
        return new String(arr);
    }

    private boolean inBound(int r, int c) {
        return r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE;
    }

    public void slide(String input) {
        int index = -1;
        for (int i = 0; i < DIR.length; i++) {
            if (DIR[i].equalsIgnoreCase(input)) {
                index = i;
                break;
            }
        }
        if (index == -1) return;
        int zero = state.indexOf("0");
        int nextRow = zero / BOARD_SIZE + DIR_X[index];
        int nextCol = zero % BOARD_SIZE + DIR_Y[index];
        if (!inBound(nextCol, nextRow)) return;
        int nextZero = nextRow * BOARD_SIZE + nextCol;
        this.state = moveBoard(this.state, zero, nextZero);
    }

    public void printBoard() {
        for (int i = 0; i < state.length(); i++) {
            System.out.print(state.charAt(i) + " ");
            if ((i + 1) % 3 == 0) System.out.println();
        }
    }
}

------------
class NPuzzle {

    private int[][] board;
    private int m;
    private int n;
    private int[] dirX = {-1, 1, 0, 0};
    private int[] dirY = {0, 0, -1, 1};
    private static final String[] dir = {"UP", "DOWN", "LEFT", "RIGHT"};
    private List<String> pathRes;

    public NPuzzle(int[][] matrix) {
        this.m = matrix.length;
        this.n = matrix[0].length;
        this.board = matrix;
    }

    public NPuzzle(int m, int n) {
        int[] rand = new int[m * n];
        this.board = new int[m][n];
        for (int i = 0; i < m * n; i++) {
            rand[i] = i;
        }
        shuffle(rand);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                this.board[i][j] = rand[n * i + j];
            }
        }
    }

    private void shuffle(int[] array) {
        for (int i = 0; i <= array.length - 1 ; i++) {
            int randomIndex = (int)(Math.random() * (array.length - i));
            int temp = array[i];
            array[i] = array[i + randomIndex];
            array[i + randomIndex] = temp;
        }
    }

    public boolean canSolve() {
        String target = "";
        for (int i = 1; i < m * n; i++) {
            target += i + ",";
        }
        target += "0";
        String start = "";
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                start += board[i][j] + ",";
            }
        }
        start = start.substring(0, start.length() - 1);
        Set<String> visited = new HashSet<>();
        Map<String, List<String>> prev = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            if (curr.equals(target)) {
                this.pathRes = getPath(prev, start, target);
                return true;
            }
            String[] str = curr.split(",");
            int zero = 0;
            for (int i = 0; i < str.length; i++) {
                if (str[i].equals("0")) {
                    zero = i;
                }
            }
            int row = zero / n;
            int col = zero % n;
            for (int i = 0; i < 4; i++) {
                int nextRow = row + dirX[i];
                int nextCol = col + dirY[i];
                if (nextRow >= 0 && nextRow < m && nextCol >= 0 && nextCol < n) {
                    int nextZero = nextRow * n + nextCol;
                    move(str, zero, nextZero);
                    String next = "";
                    for (String val : str) {
                        next += val + ",";
                    }
                    next = next.substring(0, next.length() - 1);
                    move(str, zero, nextZero); // move back for next direction
                    if (!visited.contains(next)) {
                        visited.add(next);
                        queue.offer(next);
                        prev.put(next, Arrays.asList(curr, dir[i]));
                    }
                }
            }
        }
        return false;
    }

    public List<String> solve() {
        if (!canSolve()) {
            return null;
        }
        return this.pathRes;
    }

    private List<String> getPath(Map<String, List<String>> prev, String start, String end) {
        List<String> path = new ArrayList<>();
        while (prev.containsKey(end)) {
            path.add(prev.get(end).get(1));
            end = prev.get(end).get(0);
        }
        Collections.reverse(path);
        return path;
    }

    private void move(String[] str, int x, int y) {
        String temp = str[x];
        str[x] = str[y];
        str[y] = temp;
    }
}