4. Median of Two Sorted Arrays

There are two sorted arrays nums1 and nums2 of size m and n respectively.

Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

You may assume nums1 and nums2 cannot be both empty.

Example 1:

nums1 = [1, 3]
nums2 = [2]

The median is 2.0
Example 2:

nums1 = [1, 2]
nums2 = [3, 4]

The median is (2 + 3)/2 = 2.5

===================================================================================================================
method 1:
key:
	Combine both arrays into a new single sorted array. If the length of the new array is odd, 
	then return the element in the middle. If the length of the array is even, then add both 
	number in the middle and divide by two.

Run-time: 
	O(n + m), use merge sort

class Solution 
{
    public double findMedianSortedArrays(int[] nums1, int[] nums2) 
    {
        int i = 0;
        int j = 0;
        int k = 0;
        
        int [] array = new int [nums1.length + nums2.length]; 
        
        while (i <= nums1.length - 1 ||  j <= nums2.length - 1)
        {
            if (i == nums1.length)
                array[k++] = nums2[j++];
            
            else if (j == nums2.length)
                array[k++] = nums1[i++];
            
            else if (nums1[i] < nums2[j])
                array[k++] = nums1[i++];
            
            else
                array[k++] = nums2[j++];       
        }
        
        int len = array.length;
        
        if (len % 2 == 1)
            return array[len / 2];
           
        else 
            return ((double)(array[len/2] + array[(len/2) - 1])) / 2; 
    }
}

===================================================================================================================
method 2: smaller time complexity, more Math involved
runtime: 
	O(log(m + n))

key:
	Since we have 2 seperately sorted array in this question, to find the middle value is somewhat 
	complicated. However, keep in mind that we do not care about the actual value of the numbers, 
	what we want is the middle point from the combination of 2 arrays. In other words, we are looking 
	for the middle index of the 2 arrays. Thus approach like binary search could be employed.

	Based on the fact that the 2 arrays are sorted seperatedly, we could try to get the submedian of 
	the 2 arrays in each round. Than compare them. 

	And the basic idea is that the left half of the array with a smaller submedian can never contains 
    the common median.

    if (aMid < bMid) 
        Keep [aRight + bLeft]    
    else 
        Keep [bRight + aLeft]


public class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Deal with invalid corner case. 
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) 
            return 0.0;
        
        int m = nums1.length, n = nums2.length;
        int l = (m + n + 1) / 2; //left half of the combined median
        int r = (m + n + 2) / 2; //right half of the combined median
        
        // If the nums1.length + nums2.length is odd, the 2 function will return the same number
        // Else if nums1.length + nums2.length is even, the 2 function will return the left number 
        //      and right number that make up a median
        return (getKth(nums1, 0, nums2, 0, l) + getKth(nums1, 0, nums2, 0, r)) / 2.0;
    }
    
    
    // This function finds the Kth element in nums1 + nums2
    private double getKth(int[] nums1, int start1, int[] nums2, int start2, int k) {
        System.out.println("astart =" + aStart + ", bStart = "+ bStart + ", k = " + k);

        // If nums1 is exhausted, return kth number in nums2
        if (start1 > nums1.length - 1) 
            return nums2[start2 + k - 1];
        
        // If nums2 is exhausted, return kth number in nums1
        if (start2 > nums2.length - 1) 
            return nums1[start1 + k - 1];
        
        // If k == 1, return the first number
        // Since nums1 and nums2 is sorted, the smaller one among the start point of 
        //       nums1 and nums2 is the first one
        if (k == 1) 
            return Math.min(nums1[start1], nums2[start2]);
        
        // get the mid of array 1 & array 2
        int mid1 = Integer.MAX_VALUE;
        int mid2 = Integer.MAX_VALUE;
        if (start1 + k / 2 - 1 < nums1.length) 
            mid1 = nums1[start1 + k / 2 - 1];

        if (start2 + k / 2 - 1 < nums2.length) 
            mid2 = nums2[start2 + k / 2 - 1];
        

        System.out.println("amid =" + aMid + ", bMid = "+ bMid);


        // Throw away half of the array from nums1 or nums2. And cut k in half
        if (mid1 < mid2) {
            return getKth(nums1, start1 + k / 2, nums2, start2,         k - k / 2); //nums1.right + nums2
        } else {
            return getKth(nums1, start1,        nums2,  start2 + k / 2, k - k / 2); //nums1 + nums2.right
        }
    }
}


[-5,    3,  6, 12, 15 ]         length1 = 5
[-12, -10, -6, -3,  4, 10]      length2 = 6

output:
getKth(nums1, 0, nums2, 0, 6) 
astart =0, bStart = 0, k = 6
amid =6, bMid = -6

astart =0, bStart = 3, k = 3
amid =-5, bMid = -3

astart =1, bStart = 3, k = 2
amid =3, bMid = -3

astart =1, bStart = 4, k = 1

== getKth(nums1, 0, nums2, 0, 6) ===
astart =0, bStart = 0, k = 6
amid =6, bMid = -6

astart =0, bStart = 3, k = 3
amid =-5, bMid = -3

astart =1, bStart = 3, k = 2
amid =3, bMid = -3

astart =1, bStart = 4, k = 1


output = 3

===================================================================================================================

method 3 (geeksforgeeks):
https://www.geeksforgeeks.org/median-of-two-sorted-arrays-of-different-sizes/
The basic idea is same, we find the median of two arrays and compare the medians to discard almost 
half of the elements in both arrays. Since the number of elements may differ here, there are many 
base cases that need to be handled separately. Before we proceed to complete solution, let us first 
talk about all base cases.

Let the two arrays be A[N] and B[M]. In the following explanation, it is assumed that size N <= M.

Base cases:
The smaller array has only one element
Case 0: N = 0, M = 2
Case 1: N = 1, M = 1.
Case 2: N = 1, M is odd
Case 3: N = 1, M is even
The smaller array has only two elements
Case 4: N = 2, M = 2
Case 5: N = 2, M is odd
Case 6: N = 2, M is even

Case 0: There are no elements in first array, return median of second array. If second array is also empty, return -1.

Case 1: There is only one element in both arrays, so output the average of A[0] and B[0].

Case 2: N = 1, M is odd
	Let B[5] = {5, 10, 12, 15, 20}
	First find the middle element of B[], which is 12 for above array. There are following 4 sub-cases.
	…2.1 If A[0] is smaller than 10, the median is average of 10 and 12.
	…2.2 If A[0] lies between 10 and 12, the median is average of A[0] and 12.
	…2.3 If A[0] lies between 12 and 15, the median is average of 12 and A[0].
	…2.4 If A[0] is greater than 15, the median is average of 12 and 15.
	In all the sub-cases, we find that 12 is fixed. So, we need to find the median of B[ M / 2 – 1 ], B[ M / 2 + 1], A[ 0 ] and take its average with B[ M / 2 ].

Case 3: N = 1, M is even
	Let B[4] = {5, 10, 12, 15}
	First find the middle items in B[], which are 10 and 12 in above example. There are following 3 sub-cases.
	…3.1 If A[0] is smaller than 10, the median is 10.
	…3.2 If A[0] lies between 10 and 12, the median is A[0].
	…3.3 If A[0] is greater than 12, the median is 12.
	So, in this case, find the median of three elements B[ M / 2 – 1 ], B[ M / 2] and A[ 0 ].

Case 4: N = 2, M = 2
	There are four elements in total. So we find the median of 4 elements.

Case 5: N = 2, M is odd
	Let B[5] = {5, 10, 12, 15, 20}
	The median is given by median of following three elements: B[M/2], max(A[0], B[M/2 – 1]), min(A[1], B[M/2 + 1]).

Case 6: N = 2, M is even
	Let B[4] = {5, 10, 12, 15}
	The median is given by median of following four elements: B[M/2], B[M/2 – 1], max(A[0], B[M/2 – 2]), min(A[1], B[M/2 + 1])

Remaining Cases:
	Once we have handled the above base cases, following is the remaining process.
	1) Find the middle item of A[] and middle item of B[].
	…..1.1) If the middle item of A[] is greater than middle item of B[], ignore the last half of A[], let length of ignored part is idx. 
			Also, cut down B[] by idx from the start.
	…..1.2) else, ignore the first half of A[], let length of ignored part is idx. Also, cut down B[] by idx from the last.

in c++:

// Utility function to find median of single array 
float medianSingle(int arr[], int n) 
{ 
   if (n == 0) 
      return -1; 
   if (n%2 == 0) 
        return (double)(arr[n/2] + arr[n/2-1])/2; 
   return arr[n/2]; 
} 
  
// This function assumes that N is smaller than or equal to M 
// This function returns -1 if both arrays are empty 
float findMedianUtil( int A[], int N, int B[], int M ) 
{ 
    // If smaller array is empty, return median from second array 
    if (N == 0) 
      return medianSingle(B, M); 
  
    // If the smaller array has only one element 
    if (N == 1) 
    { 
        // Case 1: If the larger array also has one element, 
        // simply call MO2() 
        if (M == 1) 
            return MO2(A[0], B[0]); 
  
        // Case 2: If the larger array has odd number of elements, 
        // then consider the middle 3 elements of larger array and 
        // the only element of smaller array. Take few examples 
        // like following 
        // A = {9}, B[] = {5, 8, 10, 20, 30} and 
        // A[] = {1}, B[] = {5, 8, 10, 20, 30} 
        if (M & 1) 
            return MO2( B[M/2], MO3(A[0], B[M/2 - 1], B[M/2 + 1]) ); 
  
        // Case 3: If the larger array has even number of element, 
        // then median will be one of the following 3 elements 
        // ... The middle two elements of larger array 
        // ... The only element of smaller array 
        return MO3( B[M/2], B[M/2 - 1], A[0] ); 
    } 
  
    // If the smaller array has two elements 
    else if (N == 2) 
    { 
        // Case 4: If the larger array also has two elements, 
        // simply call MO4() 
        if (M == 2) 
            return MO4(A[0], A[1], B[0], B[1]); 
  
        // Case 5: If the larger array has odd number of elements, 
        // then median will be one of the following 3 elements 
        // 1. Middle element of larger array 
        // 2. Max of first element of smaller array and element 
        //    just before the middle in bigger array 
        // 3. Min of second element of smaller array and element 
        //    just after the middle in bigger array 
        if (M & 1) 
            return MO3 ( B[M/2], 
                         max(A[0], B[M/2 - 1]), 
                         min(A[1], B[M/2 + 1]) 
                       ); 
  
        // Case 6: If the larger array has even number of elements, 
        // then median will be one of the following 4 elements 
        // 1) & 2) The middle two elements of larger array 
        // 3) Max of first element of smaller array and element 
        //    just before the first middle element in bigger array 
        // 4. Min of second element of smaller array and element 
        //    just after the second middle in bigger array 
        return MO4 ( B[M/2], 
                     B[M/2 - 1], 
                     max( A[0], B[M/2 - 2] ), 
                     min( A[1], B[M/2 + 1] ) 
                   ); 
    } 
  
    int idxA = ( N - 1 ) / 2; 
    int idxB = ( M - 1 ) / 2; 
  
     /* if A[idxA] <= B[idxB], then median must exist in 
        A[idxA....] and B[....idxB] */
    if (A[idxA] <= B[idxB] ) 
      return findMedianUtil(A + idxA, N/2 + 1, B, M - idxA ); 
  
    /* if A[idxA] > B[idxB], then median must exist in 
       A[...idxA] and B[idxB....] */
    return findMedianUtil(A, N/2 + 1, B + idxA, M - idxA ); 
} 
  
// A wrapper function around findMedianUtil(). This function 
// makes sure that smaller array is passed as first argument 
// to findMedianUtil 
float findMedian( int A[], int N, int B[], int M ) 
{ 
    if (N > M) 
       return findMedianUtil( B, M, A, N ); 
  
    return findMedianUtil( A, N, B, M ); 
} 


===================================================================================================================

method 4:
https://www.geeksforgeeks.org/median-two-sorted-arrays-different-sizes-ologminn-m/
runtime:
	O(log(min(n, m)))

key:
	Start partitioning the two arrays into two groups of halves (not two parts, but both partitioned 
	should have same number of elements). The first half contains some first elements from the first and 
	the second arrays, and the second half contains the rest (or the last) elements form the first and 
	the second arrays. Because the arrays can be of different sizes, it does not mean to take every half 
	from each array. The below example clarifies the explanation. Reach a condition such that, every element 
	n the first half is less than or equal to every element in the second half.

How to reach this condition ?
	Example in the case of even numbers. Suppose, partition is found. Because A[] and B[] are two sorted 
	arrays, a1 is less than or equal to a2, and b2 is less than or equal to b3. Now, to check if a1 is less 
	than or equal to b3, and if b2 is less than or equal to a2. If that’s the case, it means that every 
	element in the first half is less than or equal to every element in the second half, because, a1 is 
	greater than or equal to every element before it (a0) in A[], and b2 is greater than or equal to every 
	element before it (b1 and b0) in B[]. In case of even numbers in total the median will be the average 
	between max of a1, b2 and the min of a2, b3, but in case of odd numbers in total the median will be the 
	max of a2, b2. But if it is not these two cases, there are two options (in referring to the even numbers 
		example) :
		b2 > a2 or a1 > b3
		if, b2 > a2 it means that, search on the right side of the array, and 
		if a1 > b3 it means that, search on the left side of the array, until desired condition is found.



Why the above condition leads to the median ?
	The median is the (n + 1) / 2 smallest element of the array, and here, the median is the 
	(n + m + 1) / 2 smallest element among the two arrays. If, all the elements in the first half are 
	less than (or equal) to all elements in the second half, in case of odd numbers in total, just calculate 
	the maximum between the last two elements in the first half (a2 and b2 in our example),and this will 
	lead us to the (n + m + 1) / 2 smallest element among the two arrays, which is the median. 

	But in case of even numbers in total, calculate the average between the maximum of the last two elements in 
	the first half (a1 and b2 in our example) with its successive number among the arrays which is the minimum 
	of first two elements in the second half (a2 and b3 in our example).

The process of the partition :
	To make two halves, make the partition such that the index that partitioning array A[] + the index that 
	partitioning array B[] are equal to the total number of elements plus one divided by 2, 
	i.e. (n + m + 1) / 2 (+1 is, if the total number of elements is odd).

First, define two variables : min_index and max_index, and initialize min_index to 0, and max_index to the length of the smaller array. In these below examples A[] is the smaller array.
To partition A[], use the formula (min_index + max_index) / 2 and insert it to a variable i. To partition B[], use the formula (n + m + 1) / 2 – i and insert it to a variable j.
the variable i means the number of elements to be inserted from A[] into the first half, and j means the number of elements to be inserted from B[] into the first half, the rest of the elements will be inserted into the second half.
Take a look at the below examples :


class GFG 
{ 
    static int []a = new int[]{900}; 
    static int []b = new int[]{10, 13, 14}; 
  
    // Function to find max 
    static int maximum(int a, int b)  
    { 
        return a > b ? a : b; 
    } 
      
    // Function to find minimum 
    static int minimum(int a, int b)  
    { 
        return a < b ? a : b;  
    } 
      
    // Function to find median  
    // of two sorted arrays 
    static double findMedianSortedArrays(int n,  
                                         int m) 
    { 
          
        int min_index = 0,  
            max_index = n, i = 0, 
            j = 0, median = 0; 
          
        while (min_index <= max_index) 
        { 
            i = (min_index + max_index) / 2; 
            j = ((n + m + 1) / 2) - i; 
          
            // if i = n, it means that Elements  
            // from a[] in the second half is an  
            // empty set. and if j = 0, it means  
            // that Elements from b[] in the first 
            // half is an empty set. so it is  
            // necessary to check that, because we 
            // compare elements from these two  
            // groups. Searching on right 
            if (i < n && j > 0 && b[j - 1] > a[i])      
                min_index = i + 1; 
                      
            // if i = 0, it means that Elements 
            // from a[] in the first half is an  
            // empty set and if j = m, it means  
            // that Elements from b[] in the second 
            // half is an empty set. so it is  
            // necessary to check that, because  
            // we compare elements from these two 
            // groups. searching on left 
            else if (i > 0 && j < m && b[j] < a[i - 1])      
                max_index = i - 1; 
      
            // we have found the desired halves. 
            else
            { 
                // this condition happens when we  
                // don't have any elements in the  
                // first half from a[] so we 
                // returning the last element in  
                // b[] from the first half. 
                if (i == 0)          
                    median = b[j - 1];          
                  
                // and this condition happens when  
                // we don't have any elements in the 
                // first half from b[] so we  
                // returning the last element in  
                // a[] from the first half. 
                else if (j == 0)          
                    median = a[i - 1];          
                else    
                    median = maximum(a[i - 1],  
                                     b[j - 1]);          
                break; 
            } 
        } 
          
        // calculating the median. 
        // If number of elements is odd  
        // there is one middle element. 
        if ((n + m) % 2 == 1) 
            return (double)median; 
              
        // Elements from a[] in the  
        // second half is an empty set.  
        if (i == n) 
            return (median + b[j]) / 2.0; 
              
        // Elements from b[] in the 
        // second half is an empty set. 
        if (j == m) 
            return (median + a[i]) / 2.0; 
          
        return (median + minimum(a[i],  
                                 b[j])) / 2.0; 
    } 
      
    // Driver code 
    public static void main(String args[]) 
    { 
        int n = a.length; 
        int m = b.length; 
          
        // we need to define the  
        // smaller array as the  
        // first parameter to  
        // make sure that the 
        // time complexity will 
        // be O(log(min(n,m))) 
        if (n < m) 
            System.out.print("The median is : " +  
                   findMedianSortedArrays(n, m)); 
        else
            System.out.print("The median is : " +  
                   findMedianSortedArrays(m, n)); 
    } 
}  
  
Another Approach : Same program, but returns the median that exist in the merged array
((n + m) / 2 – 1 position):

int maximum(int a, int b); 
  
// Function to find median of given two sorted arrays 
int findMedianSortedArrays(int *a, int n,  
                           int *b, int m) 
{ 
      
    int min_index = 0, max_index = n, i, j; 
      
    while (min_index <= max_index) 
    { 
        i = (min_index + max_index) / 2; 
        j = ((n + m + 1) / 2) - i; 
      
        // if i = n, it means that Elements from a[] in 
        // the second half is an empty set. If j = 0, it 
        // means that Elements from b[] in the first half 
        // is an empty set. so it is necessary to check that, 
        // because we compare elements from these two groups. 
        // searching on right 
        if (i < n && j > 0 && b[j - 1] > a[i])         
            min_index = i + 1;         
          
        // if i = 0, it means that Elements from a[] in the 
        // first half is an empty set and if j = m, it means 
        // that Elements from b[] in the second half is an 
        // empty set. so it is necessary to check that,  
        // because we compare elements from these two groups. 
        // searching on left 
        else if (i > 0 && j < m && b[j] < a[i - 1])         
            max_index = i - 1;         
          
        // we have found the desired halves. 
        else
        { 
            // this condition happens when we don't have 
            // any elements in the first half from a[] so 
            // we returning the last element in b[] from 
            // the first half. 
            if (i == 0)             
                return b[j - 1];             
              
            // and this condition happens when we don't have any  
            // elements in the first half from b[] so we  
            // returning the last element in a[] from the first half. 
            if (j == 0)             
                return a[i - 1];             
            else            
                return maximum(a[i - 1], b[j - 1]);            
        } 
    } 
      
    cout << "ERROR!!! " << "returning\n";     
    return 0; 
} 
  
// Function to find maximum 
int maximum(int a, int b)  
{ 
    return a > b ? a : b;  
} 
  









