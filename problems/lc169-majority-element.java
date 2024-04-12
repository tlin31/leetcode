169. Majority Element - Easy

Given an array of size n, find the majority element. The majority element is the element that appears 
more than ⌊ n/2 ⌋ times.

You may assume that the array is non-empty and the majority element always exist in the array.

Example 1:

Input: [3,2,3]
Output: 3
Example 2:

Input: [2,2,1,1,1,2,2]
Output: 2

******************************************************
key:
	- Moore voting, hashmap, bit, sorting
	- edge case:
		1) empty string, return empty
		2)

	- 变型：if 1/4， same elements are grouped together, 
		   则check 1/4th, 2/4th, 3/4th的点
		   use binary search to find count

******************************************************



=======================================================================================================
method 1:

method:

	- if we cancel out each occurrence of an element e with all the other elements that are different 
	  from e then e will exist till end if it is a majority element.
	- here assume there may or may not have a majority element

stats:

	-  O(n).
	- 

	/* Function to find the candidate for Majority */
    int findCandidate(int a[], int size)  { 
        int maj_index = 0, count = 1; 
        int i; 
        for (i = 1; i < size; i++)  
        { 
            if (a[maj_index] == a[i]) 
                count++; 
            else
                count--;

            if (count == 0) { 
                maj_index = i; 
                count = 1; 
            } 
        } 
        return a[maj_index]; 
    } 
  
    /* Function to check if the candidate occurs more 
       than n/2 times */
    boolean isMajority(int a[], int size, int cand)  
    { 
        int i, count = 0; 
        for (i = 0; i < size; i++)  
        { 
            if (a[i] == cand) 
                count++; 
        } 
        if (count > size / 2)  
            return true; 
        else
            return false; 
    } 

    // main function
	void printMajority(int a[], int size)  
    { 
        /* Find the candidate for Majority*/
        int cand = findCandidate(a, size); 
  
        /* Print the candidate if it is Majority*/
        if (isMajority(a, size, cand)) 
            System.out.println(" " + cand + " "); 
        else 
            System.out.println("No Majority Element"); 
    } 

=======================================================================================================

// Sorting
public int majorityElement1(int[] nums) {
    Arrays.sort(nums);
    return nums[nums.length/2];
}



=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

// Hashtable 
public int majorityElement2(int[] nums) {
    Map<Integer, Integer> myMap = new HashMap<Integer, Integer>();

    int ret=0;
    for (int num: nums) {
        if (!myMap.containsKey(num))
            myMap.put(num, 1);
        else
            myMap.put(num, myMap.get(num)+1);
        
        if (myMap.get(num) > nums.length/2) {
            ret = num;
            break;
        }
    }
    return ret;
}
=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 
// Bit manipulation 
public int majorityElement(int[] nums) {
    int[] bit = new int[32];
    for (int num: nums)
        for (int i=0; i<32; i++) 
            if ((num>>(31-i) & 1) == 1)
                bit[i]++;
    int ret=0;
    for (int i=0; i<32; i++) {
        bit[i]=bit[i]>nums.length/2?1:0;
        ret += bit[i]*(1<<(31-i));
    }
    return ret;
}

