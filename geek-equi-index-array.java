Equilibrium index of an array

https://www.geeksforgeeks.org/equilibrium-index-of-an-array/


Equilibrium index of an array is an index such that the sum of elements at lower indexes is equal 
to the sum of elements at higher indexes. For example, in an array A:




******************************************************
key:
    - 
    - edge case:
        1) empty string, return empty
        2)

******************************************************



=======================================================================================================
method 1:

method:

    - The idea is to get the total sum of the array first. Then Iterate through the array and keep 
      updating the left sum which is initialized as zero. In the loop, we can get the right sum by 
      subtracting the elements one by one. 

        1) Initialize leftsum  as 0
        2) Get the total sum of the array as sum
        3) Iterate through the array and for each index i, do following.
            a)  Update sum to get the right sum.  
                   sum = sum - arr[i] 
               // sum is now right sum

            b) If leftsum is equal to sum, then return current index. 
               // update leftsum for next iteration.

            c) leftsum = leftsum + arr[i]

        4) return -1 


stats:

    - O(n)
    - 


// Java program to find equilibrium 
// index of an array 

class EquilibriumIndex { 
    int equilibrium(int arr[], int n) 
    { 
        int sum = 0; // initialize sum of whole array 
        int leftsum = 0; // initialize leftsum 

        /* Find sum of the whole array */
        for (int i = 0; i < n; ++i) 
            sum += arr[i]; 

        for (int i = 0; i < n; ++i) { 
            sum -= arr[i]; // sum is now right sum for index i 

            if (leftsum == sum) 
                return i; 

            leftsum += arr[i]; 
        } 

        /* If no equilibrium index found, then return 0 */
        return -1; 
    } 

// Driver code 
    public static void main(String[] args) 
    { 
        EquilibriumIndex equi = new EquilibriumIndex(); 
        int arr[] = { -7, 1, 5, 2, -4, 3, 0 }; 
        int arr_size = arr.length; 
        System.out.println("First equilibrium index is " + 
                        equi.equilibrium(arr, arr_size)); 
    } 
} 

// This code has been contributed by Mayank Jaiswal 


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






