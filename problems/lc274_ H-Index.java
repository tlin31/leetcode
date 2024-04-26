274. H-Index
Medium

Given an array of integers citations where citations[i] is the number of citations a researcher 
received for their ith paper, return the researcher's h-index.

According to the definition of h-index on Wikipedia: The h-index is defined as the maximum value 
of h such that the given researcher has published at least h papers that have each been cited at least h times.

 

Example 1:
Input: citations = [3,0,6,1,5]
Output: 3
Explanation: [3,0,6,1,5] means the researcher has 5 papers in total and each of them had 
received 3, 0, 6, 1, 5 citations respectively.

Since the researcher has 3 papers with at least 3 citations each and the remaining two with 
no more than 3 citations each, their h-index is 3.

Example 2:
Input: citations = [1,3,1]
Output: 1
 

Constraints:

n == citations.length
1 <= n <= 5000
0 <= citations[i] <= 1000

******************************************************
key:
	- bucket sort
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

	-the h-index is defined as the number of papers with reference greater than the number. 
	So assume n is the total number of papers, if we have n+1 buckets, number from 0 to n, then 
	for any paper with reference corresponding to the index of the bucket, we increment the count 
	for that bucket. The only exception is that for any paper with larger number of reference than n,
	 we put in the n-th bucket.

	- Then we iterate from the back to the front of the buckets, whenever the total count exceeds 
	the index of the bucket, meaning that we have the index number of papers that have reference 
	greater than or equal to the index. Which will be our h-index result. The reason to scan from 
	the end of the array is that we are looking for the greatest h-index. 



public int hIndex(int[] citations) {
    int n = citations.length;
    int[] buckets = new int[n+1];
    for(int c : citations) {
        if(c >= n) {
            buckets[n]++;
        } else {
            buckets[c]++;
        }
    }

    int count = 0; // use this instead of the output count array
    for(int i = n; i >= 0; i--) {
        count += buckets[i];
        if(count >= i) {
            return i;
        }
    }
    return 0;
}


