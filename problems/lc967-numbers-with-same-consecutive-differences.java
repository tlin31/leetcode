967. Numbers With Same Consecutive Differences - Medium


Return all non-negative integers of length N such that the absolute difference between every 
two consecutive digits is K, ex. abc --> |a-b| = |b-c| = 7

Note that every number in the answer must not have leading zeros except for the number 0 itself. 
For example, 01 has one leading zero and is invalid, but 0 is valid.

You may return the answer in any order.

 

Example 1:

Input: N = 3, K = 7
Output: [181,292,707,818,929] --> diff of 1 & 8 = 7, diff of 8 & 1 = 7, ...
Explanation: Note that 070 is not a valid number, because it has leading zeroes.

Example 2:

Input: N = 2, K = 1
Output: [10,12,21,23,32,34,43,45,54,56,65,67,76,78,87,89,98]
 

Note:

1 <= N <= 9
0 <= K <= 9


******************************************************
key:
	- DFS or BFS
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- O(10 * 2 ^ N)
	- 


Method:

	-	
	- Keep adding digits from 0 to 9 to a temporary integer at units place. 
	  Before adding we check if the digit being added is satisfying the condition of differing 
	  with previous digit by K. 
	  We add untill the temporary integer has N digits in it.

Dfs:

	1. fill the base cases first.
	2. if the Number has enough digits, add it to the final list.
	3. When the temporary integer is empty add all the digits except 0.
	4. recurse
	
	Think about handling edge cases - What should be returned if N = 0, N = 1 (where 0 should 
	appear in end result) or K = 1 ?


	- Since on each level, we only need to consider two cases: 1) lastDigit + K; 2) lastDigit - K.

public int[] numsSameConsecDiff(int N, int K) {
        if (N == 1) 
        	return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        List<Integer> list = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            dfs(list, N - 1, K, i);
        }
        
        return list.stream().mapToInt(i -> i).toArray();
    }
    
    private void dfs(List<Integer> list, int N, int K, int num) {
        if (N <= 0) {
            list.add(num);
            return;
        }

        int lastDigit = num % 10;
        if (lastDigit + K <= 9) {
            dfs(list, N - 1, K, num * 10 + lastDigit + K);
        }
        
        if (lastDigit - K >= 0 && K != 0) {
            dfs(list, N - 1, K, num * 10 + lastDigit - K);
        }
    } 


~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: BFS

Stats:

	- 
	- 


Method:

	-	
	-	


We initial the current result with all 1-digit numbers,
like cur = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9].

Each turn, for each x in cur,
we get its last digit lastDigit = x % 10.
If lastDigit + K < 10, we add x * 10 + lastDigit + K to the new list.
If lastDigit - K >= 0, we add x * 10 + lastDigit - K to the new list.

We repeat this step N - 1 times and return the final result.


    public int[] numsSameConsecDiff(int N, int K) {
        List<Integer> cur = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        
        for (int i = 2; i <= N; ++i) {
            List<Integer> cur2 = new ArrayList<>();
            for (int x : cur) {
                int lastDigit = x % 10;
                if (x > 0 && lastDigit + K < 10)
                    cur2.add(x * 10 + lastDigit + K);
                
                if (x > 0 && K > 0 && lastDigit - K >= 0)
                    cur2.add(x * 10 + lastDigit - K);
            }
            cur = cur2;
        }
        return cur.stream().mapToInt(j->j).toArray();
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

