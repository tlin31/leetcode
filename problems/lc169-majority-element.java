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


Moore Voting Algorithm
Intuition:
The intuition behind the Moore's Voting Algorithm is based on the fact that if there is a majority element in an array, it will always remain in the lead, even after encountering other elements.

Explanation:


Initialize two variables: count and candidate. Set count to 0 and candidate to an arbitrary value.
Iterate through the array nums:
a. If count is 0, assign the current element as the new candidate and increment count by 1.
b. If the current element is the same as the candidate, increment count by 1.
c. If the current element is different from the candidate, decrement count by 1.
After the iteration, the candidate variable will hold the majority element.
Explanation:

The algorithm starts by assuming the first element as the majority candidate and sets the count to 1.
As it iterates through the array, it compares each element with the candidate:
a. If the current element matches the candidate, it suggests that it reinforces the majority element because it appears again. Therefore, the count is incremented by 1.
b. If the current element is different from the candidate, it suggests that there might be an equal number of occurrences of the majority element and other elements. Therefore, the count is decremented by 1.
Note that decrementing the count doesn't change the fact that the majority element occurs more than n/2 times.
If the count becomes 0, it means that the current candidate is no longer a potential majority element. In this case, a new candidate is chosen from the remaining elements.
The algorithm continues this process until it has traversed the entire array.
The final value of the candidate variable will hold the majority element.
Explanation of Correctness:
The algorithm works on the basis of the assumption that the majority element occurs more than n/2 times in the array. This assumption guarantees that even if the count is reset to 0 by other elements, the majority element will eventually regain the lead.

Let's consider two cases:

If the majority element has more than n/2 occurrences:

The algorithm will ensure that the count remains positive for the majority element throughout the traversal, guaranteeing that it will be selected as the final candidate.
If the majority element has exactly n/2 occurrences:

In this case, there will be an equal number of occurrences for the majority element and the remaining elements combined.
However, the majority element will still be selected as the final candidate because it will always have a lead over any other element.
In both cases, the algorithm will correctly identify the majority element.

The time complexity of the Moore's Voting Algorithm is O(n) since it traverses the array once.

This approach is efficient compared to sorting as it requires only a single pass through the array and does not change the original order of the elements.


class Solution {
    public int majorityElement(int[] nums) {
        int count = 0;
        int candidate = 0;
        
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            
            if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }
        
        return candidate;
    }
}

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


