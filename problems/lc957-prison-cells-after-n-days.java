957. Prison Cells After N Days - Medium

There are 8 prison cells in a row, and each cell is either occupied or vacant.

Each day, whether the cell is occupied or vacant changes according to the following rules:

	1. If a cell has two adjacent neighbors that are both occupied or both vacant, then the cell 
	   becomes occupied.
	2. Otherwise, it becomes vacant.
	   (Note that because the prison is a row, the first and the last cells in the row can not have 
	   two adjacent neighbors.)

We describe the current state of the prison in the following way: 
	cells[i] == 1 if the i-th cell is occupied, else cells[i] == 0.

Given the initial state of the prison, return the state of the prison after N days (and N such changes described above.)

 

Example 1:

Input: cells = [0,1,0,1,1,0,0,1], N = 7
Output: [0,0,1,1,0,0,0,0]
Explanation: 
The following table summarizes the state of the prison on each day:
Day 0: [0, 1, 0, 1, 1, 0, 0, 1]
Day 1: [0, 1, 1, 0, 0, 0, 0, 0]
Day 2: [0, 0, 0, 0, 1, 1, 1, 0]
Day 3: [0, 1, 1, 0, 0, 1, 0, 0]
Day 4: [0, 0, 0, 0, 0, 1, 0, 0]
Day 5: [0, 1, 1, 1, 0, 1, 0, 0]
Day 6: [0, 0, 1, 0, 1, 1, 0, 0]
Day 7: [0, 0, 1, 1, 0, 0, 0, 0]

Example 2:

Input: cells = [1,0,0,1,0,0,1,0], N = 1000000000
Output: [0,0,1,1,1,1,1,0]
 

Note:

cells.length == 8
cells[i] is in {0, 1}
1 <= N <= 10^9


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	-   1.Have a sub function nextDay() that finds the next day cell states
        2.Iterate and store the cell states that occurred previously
        3.If there is no cycle, return. If there is a cycle, break the loop and rerun N%cycle 
          times to find the target cell states

stats:

	- Runtime: 3 ms, faster than 61.99% of Java online submissions for Prison Cells After N Days.
	- Memory Usage: 39.4 MB, less than 7.14% of Java online submissions for Prison Cells After N Days.


class Solution {
    public int[] prisonAfterNDays(int[] cells, int N) {
        if(cells==null || cells.length==0 || N<=0) 
            return cells;
        boolean hasCycle = false;
        int cycle = 0;
        HashSet<String> set = new HashSet<>(); 

        for(int i=0;i<N;i++){
            int[] next = nextDay(cells);
            String key = Arrays.toString(next);

            //store cell state
            if(!set.contains(key)){ 
                set.add(key);
                cycle++;
            }
            else{ //hit a cycle
                hasCycle = true;
                break;
            }
            cells = next;
        }

        if(hasCycle){
            N %= cycle;
            for(int i=0;i<N;i++){
                cells = nextDay(cells);
            }   
        }

        return cells;
    }
    
    private int[] nextDay(int[] cells){
        int[] tmp = new int[cells.length];
        for(int i=1;i<cells.length-1;i++){
            tmp[i]=cells[i-1]==cells[i+1]?1:0;
        }
        return tmp;
    }
}


=======================================================================================================
method 2:

method:

	- We record all seen states.
	- transform array to string as the key, o.w. it use the reference.
	- start from largest N, then comes back

stats:

	- Runtime: 5 ms, faster than 27.31% of Java online submissions for Prison Cells After N Days.
	- Memory Usage: 41.2 MB, less than 7.14% 


    public int[] prisonAfterNDays(int[] cells, int N) {
        Map<String, Integer> seen = new HashMap<>();
        while (N > 0) {
            int[] cells2 = new int[8];
            seen.put(Arrays.toString(cells), N--);
            for (int i = 1; i < 7; ++i)
                cells2[i] = cells[i - 1] == cells[i + 1] ? 1 : 0;
            cells = cells2;

            if (seen.containsKey(Arrays.toString(cells))) {
                N %= seen.get(Arrays.toString(cells)) - N;
            }
        }
        return cells;
    }

=======================================================================================================
method 3:

method:

	- loop pattern: loop can be 1, 7, or 14.

So once we enter the loop, every 14 steps must be the same state.

The length of cells is even,
so for any state, we can find a previous state.
So all states are in a loop.

It means that, after a single step from the initial state, we enter the loop.

	- 

stats:

	- Runtime: 1 ms, faster than 98.49% of Java online submissions for Prison Cells After N Days.
	- Memory Usage: 38.6 MB, less than 9.52% 


I tried to find the pattern of the loop.



    public int[] prisonAfterNDays(int[] cells, int N) {
        for (N = (N - 1) % 14 + 1; N > 0; --N) {
            int[] cells2 = new int[8];
            for (int i = 1; i < 7; ++i)
                cells2[i] = cells[i - 1] == cells[i + 1] ? 1 : 0;
            cells = cells2;
        }
        return cells;
    }

