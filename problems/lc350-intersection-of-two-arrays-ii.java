350. Intersection of Two Arrays II - Easy

Given two arrays, write a function to compute their intersection.

Example 1:

Input: nums1 = [1,2,2,1], nums2 = [2,2]
Output: [2,2]
Example 2:

Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
Output: [4,9]
Note:

Each element in the result should appear as many times as it shows in both arrays.
The result can be in any order.


Follow up:

Q1: What if the given array is already sorted? How would you optimize your algorithm?

A: use two pointers to iterate, which somehow resembles the merge process in merge sort.


Q2: What if nums1 size is small compared to nums2 size? Which algorithm is better?

A: Suppose lengths of two arrays are N and M, the time complexity of my solution is O(N+M) and the 
   space complexity if O(N) considering the hash. So it is better to use the smaller array to construct 
   the counter hash.



Q3: What if elements of nums2 are stored on disk, and the memory is limited such that you cannot 
    load all elements into the memory at once?

A: If only nums2 cannot fit in memory, put all elements of nums1 into a HashMap, read chunks of array 
   that fit into the memory, and record the intersections.

   If both nums1 and nums2 are so huge that neither fit into the memory, sort them individually (external 
   sort), then read 2 elements from each array at a time in memory, record intersections.

   An improvement can be sort them using external sort, read ex. 2G of each into memory and then using 
   the 2 pointer technique, then read 2G more from the array that has been exhausted. Repeat this until 
   no more data to read from disk.

   Divide and conquer. Repeat the process frequently: Slice nums2 to fit into memory, process (calculate 
   intersections), and write partial results to memory.



******************************************************
key:
	- hashmap or sort
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- use hashmap
	- 

stats:

	- Runtime: 2 ms, faster than 93.45% of Java online submissions for Intersection of Two Arrays II.
	- Memory Usage: 37.3 MB, less than 74.19%


 public int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        ArrayList<Integer> result = new ArrayList<Integer>();

        // put nums 1 into hashmap
        for(int i = 0; i < nums1.length; i++)
        {
        	//map.put(n1, map.getOrDefault(n1, 0) + 1);

            if(map.containsKey(nums1[i])) 
            	map.put(nums1[i], map.get(nums1[i])+1);
            else 
            	map.put(nums1[i], 1);
        }
    
        // put  nums 2 into hashmap, only add element to result if
        for(int i = 0; i < nums2.length; i++)
        {
            if(map.containsKey(nums2[i]) && map.get(nums2[i]) > 0)
            {
                result.add(nums2[i]);
                map.put(nums2[i], map.get(nums2[i])-1);
            }
        }
    
       int[] r = new int[result.size()];
       for(int i = 0; i < result.size(); i++)
       {
           r[i] = result.get(i);
       }
    
       return r;
    }


=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

public int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int pnt1 = 0;
        int pnt2 = 0;
        ArrayList<Integer> myList = new ArrayList<Integer>();
        while((pnt1 < nums1.length) &&(pnt2< nums2.length)){
            if(nums1[pnt1]<nums2[pnt2]){
                pnt1++;
            }
            else{
                if(nums1[pnt1]>nums2[pnt2]){
                    pnt2++;
                }
                else{
                    myList.add(nums1[pnt1]);
                    pnt1++;
                    pnt2++;
                }
            }
        }
        int[] res = new int[myList.size()];
        for(int i = 0; i<res.length; i++){
            res[i] = (Integer)myList.get(i);
        }
        return res;
    }
=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



