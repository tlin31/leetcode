15. 3Sum	--- Medium

Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

Note:

The solution set must not contain duplicate triplets.

Example:

Given array nums = [-1, 0, 1, 2, -1, -4],

A solution set is:
[
  [-1, 0, 1],
  [-1, -1, 2]
]

=========================================================================================================================================================
method 1:

key:
	- The idea is to sort an input array and then run thru all indices of a possible 1st element 
      of a triplet. 
	- For each possible first element we make a standard bidirectional 2Sum sweep of the remaining 
      part of the array. 
	- !!! we want to skip equal elements to avoid duplicates in the answer without making a set or 
    smth like that.

public List < List < Integer >> threeSum(int[] num) {
    
    Arrays.sort(num);
    List < List < Integer >> res = new LinkedList < > ();

    // i is the first element, lo is second element, hi is the third
    for (int i = 0; i < num.length - 2; i++) {

    	// corner case!!! if number = 0!!
        if (i == 0 || (i > 0 && num[i] != num[i - 1])) {
            int lo = i + 1, 
            	hi = num.length - 1, 
            	sum = 0 - num[i];

            while (lo < hi) {
                if (num[lo] + num[hi] == sum) {
                    res.add(Arrays.asList(num[i], num[lo], num[hi]));

                    //skip duplicates
                    while (lo < hi && num[lo] == num[lo + 1]) lo++;
                    while (lo < hi && num[hi] == num[hi - 1]) hi--;
                    
                    // updates, continue to find next pairs
                    lo++;
                    hi--;
                } else if (num[lo] + num[hi] < sum) 
                    lo++;
                else 
                    hi--;
            }
        }
    }
    return res;
}