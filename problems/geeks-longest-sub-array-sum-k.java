Geeks - Longest sub-array having sum k

Given an array arr[] of size n containing integers. The problem is to find the length of the longest 
sub-array having sum equal to the given value k.



problem 2:

max length of subarray with sum at most k


Examples:

Input : arr[] = { 10, 5, 2, 7, 1, 9 }, 
            k = 15
Output : 4
The sub-array is {5, 2, 7, 1}.

Input : arr[] = {-5, 8, -14, 2, 4, 12},
            k = -5
Output : 5

******************************************************
key:
	- hashmap + prefix
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

		Initialize sum = 0 and maxLen = 0.
		Create a hash table having (sum, index) tuples.
		For i = 0 to n-1, perform the following steps:
			Accumulate arr[i] to sum.
			If sum == k, update maxLen = i+1.
			Check whether sum is present in the hash table or not. 
				If not present, then add it to the hash table as (sum, i) pair.
			Check if (sum-k) is present in the hash table or not. 
				If present, then obtain index of (sum-k) from the hash table as index.

			Now check if maxLen < (i-index), then update maxLen = (i-index).
		
		Return maxLen.

stats:

	- Time Complexity: O(n).
	- Auxiliary Space: O(n).



	static int lenOfLongSubarr(int[] arr, int n, int k) 
      { 
             // HashMap to store (sum, index) tuples 
             HashMap<Integer, Integer> map = new HashMap<>(); 
             int sum = 0, maxLen = 0; 
  
             // traverse the given array 
             for (int i = 0; i < n; i++) { 
                  
                  // accumulate sum 
                  sum += arr[i]; 
                  
                  // when subarray starts from index '0' 
                  if (sum == k) 
                      maxLen = i + 1; 
  
                  // make an entry for 'sum' if it is 
                  // not present in 'map' 
                  if (!map.containsKey(sum)) { 
                      map.put(sum, i); 
                  } 
  
                  // check if 'sum-k' is present in 'map' 
                  // or not 
                  if (map.containsKey(sum - k)) { 
                        
                      // update maxLength 
                      if (maxLen < (i - map.get(sum - k))) 
                          maxLen = i - map.get(sum - k); 
                  } 
             } 
               
             return maxLen;              
      } 


=======================================================================================================
problem-related: 

max length of subarray with sum at most = money


1. Traverse the array and check if on adding the current element its sum is less than or equal to k.
2. If it’s less than k  
		add it to sum and increase the count.

   Else
		Remove the first element of subarray and decrease the count.
		Again check if on adding the current element its sum is less than or equal to k.
		If it’s less than k then add it to sum and increase the count.
3. Keep track of Maximum count.




  public static int getMaximumOutfits(List<Integer> outfits, int money) {
        int curSum = 0; 
        int length = 0, 
            maxlength = 0;
      
        for (int i = 0; i < outfits.size(); i++) { 

            if ((curSum + outfits.get(i)) <= money) { 
                curSum += outfits.get(i);  
                length++; 
            }  

            else if (curSum!=0) {
                //sliding window
                
                firstNum = outfits.get(i - length);
                curSum = curSum - firstNum + outfits.get(i); 
            }
      
            maxlength = Math.max(length, maxlength);  
        } 

        return maxlength; 
        

    }









