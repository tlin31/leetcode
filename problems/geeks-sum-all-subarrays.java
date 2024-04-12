Sum of all Subarrays | Set 1

Given an integer array ‘arr[]’ of size n, find sum of all sub-arrays of given array.

Examples :

Input   : arr[] = {1, 2, 3}
Output  : 20
Explanation : {1} + {2} + {3} + {2 + 3} + 
              {1 + 2} + {1 + 2 + 3} = 20

Input  : arr[] = {1, 2, 3, 4}
Output : 50

******************************************************
key:
	- math, num of appearance = (n-i) + (n-i)*i  loop over i
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:
	arr[] = [1, 2, 3], n = 3
	All subarrays :  [1], [1, 2], [1, 2, 3], 
	                 [2], [2, 3], [3]
	here first element 'arr[0]' appears 3 times    
	     second element 'arr[1]' appears 4 times  
	     third element 'arr[2]' appears 3 times

	Every element arr[i] appears in two types of subsets:
	i)  In subarrays beginning with arr[i]. There are  (n-i) such subsets. For example [2] appears
	    in [2] and [2, 3].
	ii) In (n-i)*i subarrays where this element is not first element. For example [2] appears in 
	    [1, 2] and [1, 2, 3].

	Total of above (i) and (ii) = (n-i) + (n-i)*i 
	                            = (n-i)(i+1)
	                                  
	For arr[] = {1, 2, 3}, sum of subarrays is:
	  arr[0] * ( 0 + 1 ) * ( 3 - 0 ) + 
	  arr[1] * ( 1 + 1 ) * ( 3 - 1 ) +
	  arr[2] * ( 2 + 1 ) * ( 3 - 2 ) 

	= 1*3 + 2*4 + 3*3 
	= 20

stats:

	- O(n)
	- 


    public static long SubArraySum( int arr[] , int n ) 
    { 
        long result = 0; 
       
        for (int i=0; i<n; i++) 
            result += (arr[i] * (i+1) * (n-i)); 
       
        return result ; 
    } 

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



