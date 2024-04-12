659. Split Array into Consecutive Subsequences - Medium

Given an array nums sorted in ascending order, return true if and only if you can split it into 1 or 
more subsequences such that each subsequence consists of consecutive integers and has length AT LEAST 3.

 

Example 1:
Input: [1,2,3,3,4,5]
Output: True
Explanation:
You can split them into two consecutive subsequences : 
1, 2, 3
3, 4, 5

Example 2:
Input: [1,2,3,3,4,4,5,5]
Output: True
Explanation:
You can split them into two consecutive subsequences : 
1, 2, 3, 4, 5
3, 4, 5

Example 3:
Input: [1,2,3,4,4,5]
Output: False
 

Constraints:

1 <= nums.length <= 10000


******************************************************
key:
	- if want to print the series, see the comment code
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:
	- Greedy:
		Considering numbers x from left to right, if x can be added to a current chain, it is at least 
		as good to add x to that chain first, rather than to start a new chain.

		Because if we started with numbers x and greater from the beginning, the shorter chains starting 
		from x could be concatenated with the chains ending before x, possibly helping us if there 
		was a "chain" from x that was only length 1 or 2.
	

	- We iterate through the array once to get the frequency of all the elements in the array
	- We iterate through the array once more and for each element we either:
		1. see if it can be appended to a previously constructed consecutive sequence  
		2. if it can be the start of a new consecutive sequence. 
		3. If neither are true, then we return false.

	- eg: [1,2,3,4,5]
		// i =1
		we fall in case 3 "start of a new subsequence"
		we decrease frequence of  2, 3 to 0
		and put <4, 1> in appendfreq, this mean I have 1 subsequence can continue from 4

		//i =2, 3
		we continue

		//i = 4
		we fall in 2 case since <4, 1> is in appendfreq
		now this subsequence should end in 5
		so we decreace <4, 1> to <4, 0> since we no longer have subsequence can continue from 4
		and we put <5, 1> in appendfreq since now we have a subsequence can continue from 5
stats:

	- O(n)
	- O(n)


public boolean isPossible(int[] nums) {
    Map<Integer, Integer> freq = new HashMap<>(), 
    				      appendfreq = new HashMap<>();

    ArrayList<List<Integer>> result = new ArrayList<>();

    // create frequency map
    for (int i : nums) 
    	freq.put(i, freq.getOrDefault(i,0) + 1);

    // go through input array
    for (int i : nums) {

    	// already exausted from previous 3连
        if (freq.get(i) == 0) 
        	continue;

        // if there is some subsequence that is ended with i,
        // then we can put the number i in the subsequence
        else if (appendfreq.getOrDefault(i,0) > 0) {
            appendfreq.put(i, appendfreq.get(i) - 1);
            appendfreq.put(i+1, appendfreq.getOrDefault(i+1,0) + 1);
        }   

        // can be a start of the new sequence, check its following 2 numbers
        else if (freq.getOrDefault(i+1,0) > 0 && freq.getOrDefault(i+2,0) > 0) {
        	// change count of i+1 and i+2
            freq.put(i+1, freq.get(i+1) - 1);
            freq.put(i+2, freq.get(i+2) - 1);

            // mark a new sequence can start at index i + 3
            appendfreq.put(i+3, appendfreq.getOrDefault(i+3,0) + 1);
        }
        else 
        	return false;

        freq.put(i, freq.get(i) - 1);
    }
    // print result
    return true;
}


-----
Change it to at least length k

class Solution {

	Map<Integer, Integer> freq = new HashMap<>(), appendfreq = new HashMap<>();

	public boolean isPossible(int[] nums, int k) {
	    for (int i : nums) 
	    	freq.put(i, freq.getOrDefault(i,0) + 1);

	    for (int i : nums) {
	        if (freq.get(i) == 0) 
	        	continue;

	        else if (appendfreq.getOrDefault(i,0) > 0) {
	            appendfreq.put(i, appendfreq.get(i) - 1);
	            appendfreq.put(i+1, appendfreq.getOrDefault(i+1,0) + 1);
	        }   

	        else if (canStart(i,k)) {
	            update(i,k);
	            appendfreq.put(i+k, appendfreq.getOrDefault(i+k,0) + 1);
	        }
	        else 
	            return false;

	        freq.put(i, freq.get(i) - 1);
	    }
	    return true;
	}
    
    private boolean canStart(int index, int k) {
        for (int i = 1; i < k; i++) {
            if (freq.getOrDefault(index+1,0) == 0) {
                return false;
            }
            index++;
                
        }
        return true;
    }
    
    private void update(int index, int k)  {
        for (int i = 1; i < k; i++) {
        	freq.put(index+1, freq.get(index+1) - 1);
        	index++;                
        }
    }
}

--------------

Print the sequence: hashmap store (end index, start index)

Hashmap <1,3>, if append 4 --> <1,4>

=======================================================================================================
method 2:

method:
	-
	- 

stats:

	- 
	- 


=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



