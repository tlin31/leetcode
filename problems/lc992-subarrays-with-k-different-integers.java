992. Subarrays with K Different Integers - Hard

Given an array A of positive integers, call a (contiguous, not necessarily distinct) subarray of A 
good if the number of different integers in that subarray is exactly K.

(For example, [1,2,3,1,2] has 3 different integers: 1, 2, and 3.)

Return the number of good subarrays of A.

 

Example 1:

Input: A = [1,2,1,2,3], K = 2
Output: 7
Explanation: Subarrays formed with exactly 2 different integers: [1,2], [2,1], [1,2], [2,3], [1,2,1],
 [2,1,2], [1,2,1,2].


Example 2:
Input: A = [1,2,1,3,4], K = 3
Output: 3
Explanation: Subarrays formed with exactly 3 different integers: [1,2,1,3], [2,1,3], [1,3,4].
 

Note:

1 <= A.length <= 20000
1 <= A[i] <= A.length
1 <= K <= A.length

******************************************************
key:
	- sliding windows?
	- edge case:
		1) array length = 0
		2) K = 0

******************************************************



=======================================================================================================
method 1:

method:
	- harrrrrrd
	- 	sliding window
	- 	typical if it asks the number of subarrays with at most K distinct elements.
	- 	Just need one more step to reach the folloing equation:
			exactly(K) = atMost(K) - atMost(K-1)

	-	Write/copy a helper function of sliding window, to get the number of subarrays with at most K 
		distinct elements.
	- 

stats:

	- Time O(N) for two passes.
	- Space O(K) at most K elements in the counter


    public int subarraysWithKDistinct(int[] A, int K) {
        return atMostK(A, K) - atMostK(A, K - 1);
    }

    int atMostK(int[] A, int K) {
        int i = 0, res = 0;

        // store number & frequency
        Map<Integer, Integer> count = new HashMap<>();

        // j as right pointer
        for (int j = 0; j < A.length; ++j) {

        	// if we encounter a new digit (not stored in map), we reduce K,
        	// so now we need to see K-1个distinct number
            if (count.getOrDefault(A[j], 0) == 0) 
            	K--;

            // update frequency map
            count.put(A[j], count.getOrDefault(A[j], 0) + 1);

            // delete left most number
            while (K < 0) {
                count.put(A[i], count.get(A[i]) - 1);

                // 最后一次出现，并且不在window里面，所以可以再看一个新的值
                if (count.get(A[i]) == 0) 
                	K++;

                i++;
            }

            res += j - i + 1;
        }
        return res;
    }

ex. [1,2,1,2], k = 2 --> 在 i = 3 (num[i] =2 )结尾的一共有res = 3-0+1 = 4组 
					 --> { (1,2), (2), (2,1,2), (1,2,1,2)}



					 

// use array instead of hashmap
class Solution {
public:
  int subarraysWithKDistinct(vector<int>& A, int K) {
    // Returns the number of subarrays with k or less distinct numbers.
    auto subarrys = [&A](int k) {
      vector<int> count(A.size() + 1);
      int ans = 0;
      int i = 0;
      for (int j = 0; j < A.size(); ++j) {
        if (count[A[j]]++ == 0) 
          --k;
        while (k < 0)
          if (--count[A[i++]] == 0) ++k;
        ans += j - i + 1;
      }
      return ans;
    };
    return subarrys(K) - subarrys(K - 1);
  }
};

=======================================================================================================
method 2:

method:
	- https://leetcode.com/problems/subarrays-with-k-different-integers/discuss/262299/Single-pass-two-pointer-4ms-Java-solution-beats-100-O(N)-space
	- 

stats:

	- 
	- 

    public int subarraysWithKDistinct(int[] A, int K) {
        int nvals[] = new int[A.length + 1];
        int nsub = 1;
        int l = 0;
        int r = 0;
        int totalsub = 0;
        while (r < A.length) {
            if (nvals[A[r++]]++ == 0) {
                K--;
            }
            if (K < 0) {
                --nvals[A[l++]];
                K++;
                nsub = 1;
            }
            if (K == 0) {
                while (nvals[A[l]] > 1) {
                    --nvals[A[l++]];
                    nsub++;
                }
                totalsub += nsub;
            }
        }
        return totalsub;
    }


=======================================================================================================
method 3:

method:

	- The Idea is to iterate the second pointer until the size of the map is equal to k and then 
	  clear the map and move the first pointer.
	- 

stats:

	- O(n^2)
	- 

class Solution {
	public int subarraysWithKDistinct(int[] nums, int K) {
		Map<Integer,Integer> map = new HashMap<>();

		int first=0;
		int second = 0;
		int count = 0;

		while(second < nums.length){
			map.put(nums[second],map.getOrDefault(nums[second],0)+1);   

			// move left pointer by 1
			// let right ptr = left ptr
			if(map.size()>K){
				map.clear();
				first++;
				second = first;
				continue;
			}

			// when find one exact subarray: count++, 
			// second++ to see whether the next one is still k size map
			if(map.size() == K){
				count++;
				second++;
				if(second == nums.length){
					map.clear();
					first++;
					second = first;
				}

				continue;
			}

			second++;

		}

		return count;
    
	}
}




