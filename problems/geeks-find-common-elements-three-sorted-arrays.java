Find common elements in three sorted arrays


https://www.geeksforgeeks.org/find-common-elements-three-sorted-arrays/

method:
	- If x, y and z are same, print any of them as common element and move ahead in all three arrays.
	  Else If x < y, we can move ahead in ar1[] as x cannot be a common element.
      Else If x > z and y > z, we can simply move ahead in ar3[] as z cannot be a common element.


Runtime:
	- O(n1 + n2 + n3)

 void findCommon(int ar1[], int ar2[], int ar3[]) 
    { 
        // Initialize starting indexes for ar1[], ar2[] and ar3[] 
        int i = 0, j = 0, k = 0; 
  
        // Iterate through three arrays while all arrays have elements 
        while (i < ar1.length && j < ar2.length && k < ar3.length) 
        { 
             // If x = y and y = z, print any of them and move ahead  in all arrays 
             if (ar1[i] == ar2[j] && ar2[j] == ar3[k]) {
 		   		System.out.print(ar1[i]+" ");   
         		i++; 
         		j++; 
         		k++; 
         	} 
  
             // x < y 
             else if (ar1[i] < ar2[j]) 
                 i++; 
  
             // y < z 
             else if (ar2[j] < ar3[k]) 
                 j++; 
  
             // We reach here when x > y and y > z, i.e., z is smallest 
             else
                 k++; 
        } 
    } 