1218. Longest Arithmetic Subsequence of Given Difference - Medium


Given an integer array arr and an integer difference, return the length of the longest subsequence 
in arr which is an arithmetic sequence such that the difference between adjacent elements in the 
subsequence equals difference.

 

Example 1:

Input: arr = [1,2,3,4], difference = 1
Output: 4
Explanation: The longest arithmetic subsequence is [1,2,3,4].

Example 2:

Input: arr = [1,3,5,7], difference = 1
Output: 1
Explanation: The longest arithmetic subsequence is any single element.


Example 3:

Input: arr = [1,5,7,8,5,3,4,2,1], difference = -2
Output: 4
Explanation: The longest arithmetic subsequence is [7,5,3,1].
 

Constraints:

1 <= arr.length <= 10^5
-10^4 <= arr[i], difference <= 10^4



******************************************************
key:
	- DP
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

	-	map stores: (num, count that can reach to this number)
	-	check if the next num in the sequence is already in map,
			if not: 
				add current number
			if exists:
				put the count of this number as prev num + 1





    public int longestSubsequence(int[] arr, int difference) {
        int res=1;
        Map<Integer, Integer> map=new HashMap<>();
        for(int i=0;i<arr.length;i++) { 
            if(!map.containsKey(arr[i]-difference)) 
            	map.put(arr[i], 1);
            else 
            	map.put(arr[i], map.get(arr[i]-difference)+1);

            res=Math.max(res, map.get(arr[i]));
        }
        return res;
    }




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

def longestSubsequence(self, arr: List[int], diff: int) -> int:
        res = {}
        for num in arr:
            res[num] = res[num - diff] + 1 if (num - diff) in res else 1
        return max(res.values())
        
=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	










~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

