/**
https://www.geeksforgeeks.org/maximum-difference-between-two-elements/

Given an array arr[] of integers, find out the maximum difference between any two elements such that larger 
element appears after the smaller number.

Method 1 (Tricky and Efficient)
In this method, instead of taking difference of the picked element with every other element, we take the 
difference with the minimum element found so far. So we need to keep track of 2 things:
1) Maximum difference found so far (max_diff).
2) Minimum number visited so far (min_element).


Input : arr = {2, 3, 10, 6, 4, 8, 1}
	Output : 8
	Explanation : The maximum difference is between 10 and 2.

	Input : arr = {7, 9, 5, 6, 3, 2}
	Output : 2
	Explanation : The maximum difference is between 9 and 7.

Time Complexity : O(n)
Auxiliary Space : O(1)
**/

/* The function assumes that there are at least two elements in array. 
   The function returns a negative value if the array is sorted in decreasing order. 
   Returns 0 if elements are equal  */
    int maxDiff(int arr[], int arr_size)  
    { 
        int max_diff = arr[1] - arr[0]; 
        int min_element = arr[0]; 
        int i; 
        for (i = 1; i < arr_size; i++)  
        { 
            if (arr[i] - min_element > max_diff) 
                max_diff = arr[i] - min_element; 
            if (arr[i] < min_element) 
                min_element = arr[i]; 
        } 
        return max_diff; 
    } 


Method 2
First find the difference between the adjacent elements of the array and store all differences in an auxiliary 
array diff[] of size n-1. Now this problems turns into finding the maximum sum subarray of this difference 
array instead of creating an auxiliary array, we can calculate diff and max sum in same loop.

Time Complexity : O(n)
Auxiliary Space : O(1)

static int maxDiff (int arr[], int n)  
{  
    // Initialize diff, current sum and max sum  
    int diff = arr[1] - arr[0];  
    int curr_sum = diff;  
    int max_sum = curr_sum;  
  
    for(int i = 1; i < n - 1; i++)  
    {  
        // Calculate current diff  
        diff = arr[i + 1] - arr[i];  
  
        // Calculate current sum  
        if (curr_sum > 0)   curr_sum += diff;  
        else  curr_sum = diff;  
  
        // Update max sum, if needed  
        if (curr_sum > max_sum)  
        max_sum = curr_sum;  
    }  
  
    return max_sum;  
}  

