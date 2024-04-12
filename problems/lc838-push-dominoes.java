838. Push Dominoes - Medium


There are N dominoes in a line, and we place each domino vertically upright.

In the beginning, we simultaneously push some of the dominoes either to the left or to the right.



After each second, each domino that is falling to the left pushes the adjacent domino on the left.

Similarly, the dominoes falling to the right push their adjacent dominoes standing on the right.

When a vertical domino has dominoes falling on it from both sides, it stays still due to the balance 
of the forces.

For the purposes of this question, we will consider that a falling domino expends no additional force 
to a falling or already fallen domino.


Given a string "S" representing the initial state. S[i] = 'L', if the i-th domino has been pushed to 
the left; S[i] = 'R', if the i-th domino has been pushed to the right; S[i] = '.', if the i-th domino 
has not been pushed.

Return a string representing the final state. 

Example 1:

Input: ".L.R...LR..L.."
Output: "LL.RR.LLRRLL.."


Example 2:

Input: "RR.L"
Output: "RR.L"
Explanation: The first domino expends no additional force on the second domino.


Note:
0 <= N <= 10^5
String dominoes contains only 'L', 'R' and '.'


******************************************************
key:
	- simulation
	- edge case:
		1) empty string, return empty
		2)

******************************************************


=======================================================================================================
Method 1: Simulation

Stats:
	- Time complexity: O(n)
	- Space complexity: O(n)

Methods:
	Simulate the push process, record the steps from L and R for each domino.
	steps(L) == steps(R) => “.”
	steps(L) < steps(R) => “L”
	steps(L) > steps(R) => “R”



class Solution {
    public String pushDominoes(String S) { 
    	int n = S.length();
    	int[] L = new int[n];
        Arrays.fill(L, n+1); 

    	int[] R = new int[n];
        Arrays.fill(R, n+1); 

    	char[] result = S.toCharArray();

    	for (int i = 0; i < n; ++i) {
    		if (result[i] == 'L') {
    			L[i] = 0;

    			// go to all its left elements, and add 1
    			// stop if reach another L or R
				for (int j = i - 1; j >= 0 && result[j] == '.'; j--)
          			L[j] = L[j + 1] + 1;    		
          	} 

          	else if (result[i] == 'R') {        
		        R[i] = 0;
		        for (int j = i + 1; j < n && result[j] == '.'; j++)
		          	R[j] = R[j - 1] + 1;
		    }

    	}


    	// compare forces
    	for (int i = 0; i < n; ++i)
	      if (L[i] < R[i]) 
	      	result[i] = 'L';
	      else if (L[i] > R[i]) 
	      	result[i] = 'R';
        
	    String strResult = new String(result);
    	return strResult;
    }
}


------ use only 1 array

-  Scanning from left to right, our force decays by 1 every iteration, and resets to N if we meet an 'R',
   so that force[i] is higher (than force[j]) if and only if dominoes[i] is closer (looking leftward) 
   to 'R' (than dominoes[j]).
-  Similarly, scanning from right to left, we can find the force going rightward (closeness to 'L').

ex.
	S = 'R.R...L': 
		force going from left to right is [7, 6, 7, 6, 5, 4, 0]. 
		force going from right to left is [0, 0, 0, -4, -5, -6, -7]. 
		Combining them (taking their vector addition), the combined force is [7, 6, 7, 2, 0, -2, -7], 
		for a final answer of RRRR.LL.

class Solution {
    public String pushDominoes(String S) {
        char[] A = S.toCharArray();
        int N = A.length;
        int[] forces = new int[N];

        // Populate forces going from left to right
        int force = 0;
        for (int i = 0; i < N; ++i) {
            if (A[i] == 'R') 
            	force = N;
            else if (A[i] == 'L') 
            	force = 0;
            else if (A[i] == '.')
            	force = Math.max(force - 1, 0);

            forces[i] += force;
        }

        // Populate forces going from right to left
        force = 0;
        for (int i = N-1; i >= 0; --i) {
            if (A[i] == 'L') 
            	force = N;
            else if (A[i] == 'R') 
            	force = 0;
            else 
            	force = Math.max(force - 1, 0);
            forces[i] -= force;
        }

        StringBuilder ans = new StringBuilder();
        for (int f: forces)
            ans.append(f > 0 ? 'R' : f < 0 ? 'L' : '.');
        return ans.toString();
    }
}
=======================================================================================================
Method 1:


Stats:

	- O(n)
	- 


Method:

	-	
	-  Between every group of vertical dominoes ('.'), we have up to two non-vertical dominoes bordering
	   this group. Since additional dominoes outside this group do not affect the outcome, we can 
	   analyze these situations individually: there are 9 of them (as the border could be empty). 

	   Actually, if we border the dominoes by 'L' and 'R', there are only 4 cases. We'll write new 
	   letters between these symbols depending on each case.

Algorithm

	1. "A....B", where A = B, then we should write "AAAAAA".

	2. "R....L", then we will write "RRRLLL", or "RRR.LLL" if we have an odd number of dots. 

	3. If the initial symbols are at positions i and j, we can check our distance k-i and j-k to decide 
	   at position k whether to write 'L', 'R', or '.'.

	4. If we have "L....R" we don't do anything. We can skip this case.



    public String pushDominoes(String d) {
        d = 'L' + d + 'R';
        StringBuilder res = new StringBuilder();

        for (int i = 0, j = 1; j < d.length(); ++j) {
            if (d.charAt(j) == '.') 
            	continue;

            int middle = j - i - 1;

            if (i > 0) 
            	res.append(d.charAt(i));

            // L....L or R....R or ......
            //
            if (d.charAt(i) == d.charAt(j))
                for (int k = 0; k < middle; k++) 
                	res.append(d.charAt(i));

            //
            else if (d.charAt(i) == 'L' && d.charAt(j) == 'R')
                for (int k = 0; k < middle; k++) 
                	res.append('.');

            // 	
            else {
                for (int k = 0; k < middle / 2; k++) 
                	res.append('R');
                if (middle % 2 == 1) 
                	res.append('.');
                for (int k = 0; k < middle / 2; k++) 
                	res.append('L');
            }

            i = j;
        }
        return res.toString();
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



