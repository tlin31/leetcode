907. Sum of Subarray Minimums - Medium


Given an array of integers A, find the sum of min(B), where B ranges over every (contiguous) 
subarray of A.

Since the answer may be large, return the answer modulo 10^9 + 7.

 

Example 1:

Input: [3,1,2,4]
Output: 17
Explanation: Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4]. 
Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.  Sum is 17.
 

Note:

1 <= A.length <= 30000
1 <= A[i] <= 30000



******************************************************
key:
	- monotonous increase stack
	- edge case:
		1) empty string, return empty
		2)

******************************************************


=======================================================================================================
Method 1:



1. What is monotonous increase stack?
	- Roughly speaking, the elements in the an monotonous increase stack keeps an increasing order.

		for(int i = 0; i < A.size(); i++){
		  while(!in_stk.empty() && in_stk.top() > A[i]){
		    in_stk.pop();
		  }
		  in_stk.push(A[i]);
		}



2. What can monotonous increase stack do?
	(1) find the previous less element of each element in a vector with O(n) time:
		What is the previous less element of an element?
		For example:
		[3, 7, 8, 4]
		The previous less element of 7 is 3.
		The previous less element of 8 is 7.
		The previous less element of 4 is 3.
		There is no previous less element for 3.
		For simplicity of notation, we use abbreviation PLE to denote Previous Less Element.

		Instead of directly pushing the element itself, here for simplicity, we push the index.
		We do some record when the index is pushed into the stack.

		// previous_less[i] = j means A[j] is the previous less element of A[i].
		// previous_less[i] = -1 means there is no previous less element of A[i].
		vector<int> previous_less(A.size(), -1);
		for(int i = 0; i < A.size(); i++){
		  while(!in_stk.empty() && A[in_stk.top()] > A[i]){
		    in_stk.pop();
		  }
		  previous_less[i] = in_stk.empty()? -1: in_stk.top();
		  in_stk.push(i);
		}


	(2) find the next less element of each element in a vector with O(n) time:
		What is the next less element of an element?
		For example:
		[3, 7, 8, 4]
		The next less element of 8 is 4.
		The next less element of 7 is 4.
		There is no next less element for 3 and 4.
		For simplicity of notation, we use abbreviation NLE to denote Next Less Element.

		// next_less[i] = j means A[j] is the next less element of A[i].
		// next_less[i] = -1 means there is no next less element of A[i].
		vector<int> previous_less(A.size(), -1);
		for(int i = 0; i < A.size(); i++){
		  while(!in_stk.empty() && A[in_stk.top()] > A[i]){
		    auto x = in_stk.top(); in_stk.pop();
		    next_less[x] = i;
		  }
		  in_stk.push(i);
		}



3. How can the monotonous increase stack be applied to this problem?

	Consider the element 3 in the following vector:

	                [2, 9, 7, 8, 3, 4, 6, 1]
				     |                    |
		             the previous less       the next less 
		                element of 3          element of 3

	After finding both NLE and PLE of 3, we can determine the
	distance between 3 and 2(previous less) , and the distance between 3 and 1(next less).
	In this example, the distance is 4 and 3 respectively.

	How many subarrays with 3 being its minimum value?
	The answer is 4*3.

	9 7 8 3 
	9 7 8 3 4 
	9 7 8 3 4 6 
	7 8 3 
	7 8 3 4 
	7 8 3 4 6 
	8 3 
	8 3 4 
	8 3 4 6 
	3 
	3 4 
	3 4 6

	How much the element 3 contributes to the final answer?
	It is 3*(4*3).

	What is the final answer?
		Denote by left[i] the distance between element A[i] and its PLE.
		Denote by right[i] the distance between element A[i] and its NLE.

		The final answer is,
		sum(A[i]*left[i]*right[i] )

The solution (One pass)
class Solution {
public:
  int sumSubarrayMins(vector<int>& A) {
    stack<pair<int, int>> in_stk_p, in_stk_n;
    // left is for the distance to previous less element
    // right is for the distance to next less element
    vector<int> left(A.size()), right(A.size());
		
    //initialize
    for(int i = 0; i < A.size(); i++) left[i] =  i + 1;
    for(int i = 0; i < A.size(); i++) right[i] = A.size() - i;
		
    for(int i = 0; i < A.size(); i++){
      // for previous less
      while(!in_stk_p.empty() && in_stk_p.top().first > A[i]) 
      	in_stk_p.pop();

      left[i] = in_stk_p.empty()? i + 1: i - in_stk_p.top().second;
      in_stk_p.push({A[i],i});
			
      // for next less
      while(!in_stk_n.empty() && in_stk_n.top().first > A[i]){
        auto x = in_stk_n.top();in_stk_n.pop();
        right[x.second] = i - x.second;
      }
      in_stk_n.push({A[i], i});
    }

    int ans = 0, mod = 1e9 +7;
    for(int i = 0; i < A.size(); i++){
      ans = (ans + A[i]*left[i]*right[i])%mod;
    }
    return ans;
  }
};

The last thing that needs to be mentioned for handling duplicate elements:
	- Method: Set strict less and non-strict less(less than or equal to) for finding NLE and 
	        PLE respectively. The order doesnt matter.

	- For example, the above code for finding NLE is strict less, while PLE is actually non-strict less.
	- Remark: Although in both loop conditions the signs are set as >, for NLE, we make records 
	          inside the loop, while for PLE, records are done outside the loop.

=======================================================================================================
Method 2:

Stats:

	- 
	- 


Method:

	- Lets try to count the number of subarrays #(j) for which A[j] is the right-most minimum. 
	  Then, the answer will be sum #(j) * A[j]. 
	  (We must say right-most so that we form disjoint sets of subarrays and do not double count any, 
	  as the minimum of an array may not be unique.)

	- This in turn brings us the question of knowing the smallest index i <= j for which A[i], A[i+1], 
	  ..., A[j] are all >= A[j]; and the largest index k >= j for which A[j+1], A[j+2], ..., A[k] are 
	  all > A[j].

Algorithm

	i = the previous smaller element
	k = the next smaller element

	For example, if A = [10, 3, 4, 5, [3], 2, 3, 10] and we would like to know #(j = 4) [the count 
	of the second 3, which is marked], we would find i = 1 and k = 5.

	From there, the actual count is #(j) = (j - i + 1) * (k - j + 1), as there are j - i + 1 choices i, 
	i+1, ..., j for the left index of the subarray, and k - j + 1 choices j, j+1, ..., k for the right 
	index of the subarray.

	Answering these queries (ie. determining (i, k) given j) is a classic problem that can be answered 
	with a stack. We will focus on the problem of finding i: the problem of finding k is similar.

Making a Prev Array

	The idea is to maintain stack, a monotone decreasing subsequence of A (actually, indices of A in 
	implementation). These represent candidate boundaries i* - 1 for the next query, stored in 
	increasing order of A[i*].

	Now considering j in increasing order, we can remove candidates for which A[i*] <= A[j] in 
	decreasing order of i*.

	For example, if A = [10, 5, 3, 7, 0, 4, 5, 2, 1, [8] ], then when considering j = 9 (A[j] = 8), we 
	have a stack of boundaries like [-1, 0, 3, 6] (representing A[i*] = -inf, 10, 7, 5). We pop 6 and 3 
	from the stack, as 5 <= 8 and 7 <= 8, and we get the answer boundary i* - 1 = 0.




class Solution {
    public int sumSubarrayMins(int[] A) {
        int MOD = 1_000_000_007;
        int N = A.length;

        // prev has i* - 1 in increasing order of A[i* - 1]
        // where i* is the answer to query j
        Stack<Integer> stack = new Stack();
        int[] prev = new int[N];
        for (int i = 0; i < N; ++i) {
            while (!stack.isEmpty() && A[i] <= A[stack.peek()])
                stack.pop();
            prev[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        // next has k* + 1 in increasing order of A[k* + 1]
        // where k* is the answer to query j
        stack = new Stack();
        int[] next = new int[N];
        for (int k = N-1; k >= 0; --k) {
            while (!stack.isEmpty() && A[k] < A[stack.peek()])
                stack.pop();
            next[k] = stack.isEmpty() ? N : stack.peek();
            stack.push(k);
        }

        // Use prev/next array to count answer
        long ans = 0;
        for (int i = 0; i < N; ++i) {
            ans += (i - prev[i]) * (next[i] - i) % MOD * A[i] % MOD;
            ans %= MOD;
        }
        return (int) ans;

    }
}





~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

classSolution(object):
    def sumSubarrayMins(self, A):
        MOD = 10**9 + 7
        N = len(A)

        # prev has i* - 1 in increasing order of A[i* - 1]
        # where i* is the answer to query j
        stack = []
        prev = [None] * N
        for i in xrange(N):
            while stack and A[i] <= A[stack[-1]]:
                stack.pop()
            prev[i] = stack[-1] if stack else -1
            stack.append(i)

        # next has k* + 1 in increasing order of A[k* + 1]
        # where k* is the answer to query j
        stack = []
        next_ = [None] * N
        for k in xrange(N-1, -1, -1):
            while stack and A[k] < A[stack[-1]]:
                stack.pop()
            next_[k] = stack[-1] if stack else N
            stack.append(k)

        # Use prev/next array to count answer
        return sum((i - prev[i]) * (next_[i] - i) * A[i]
                   for i in xrange(N)) % MOD

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

