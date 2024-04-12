88. Merge Sorted Array - Easy

Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.

Note:

The number of elements initialized in nums1 and nums2 are m and n respectively.
You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional 
elements from nums2.

Example:
Input:
nums1 = [1,2,3,0,0,0], m = 3
nums2 = [2,5,6],       n = 3

Output: [1,2,2,3,5,6]



******************************************************
key:
	- in place! 
	- edge case:
		1) if duplicates? not ignored!
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- put from back!
	- pointer at A does not need to go through entirely. can just finish where it lefts off 

stats:

	- 100 %
	- 


void merge(int A[], int m, int B[], int n) {
        int i = m-1;
		int j = n-1;
		int k = m+n-1;
		while(i >=0 && j>=0)
		{
			if(A[i] > B[j])
				A[k--] = A[i--];
			else
				A[k--] = B[j--];
		}
		while(j>=0)
			A[k--] = B[j--];
    }



