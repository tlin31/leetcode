136. Single Number - Easy

Given a non-empty array of integers, every element appears twice except for one. Find that single one.

Note:

Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

Example 1:

Input: [2,2,1]
Output: 1
Example 2:

Input: [4,1,2,1,2]
Output: 4


******************************************************
key:
	- XOR
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: XOR

Method:

	-	If we take XOR of zero and some bit, it will return that bit
			a⊕0=a
		If we take XOR of two same bits, it will return 0
			a⊕a=0
		
		a⊕b⊕a = (a⊕a)⊕b = 0⊕b = b
		
		So we can XOR all bits together to find the unique number.
	-	

Stats:

	- 
	- 


class Solution {
  public int singleNumber(int[] nums) {
    int a = 0;
    for (int i : nums) {
      a ^= i;
    }
    return a;
  }
}

=======================================================================================================
method 2: Math

Method:

	-	2∗(a+b+c)−(a+a+b+b+c)=c


	-	


Stats:

	- Time complexity : O(n + n) = O(n). 

	- Space complexity : O(n + n) = O(n). set needs space for the elements in nums


class Solution {
  public int singleNumber(int[] nums) {
    int sumOfSet = 0, sumOfNums = 0;
    Set<Integer> set = new HashSet();

    for (int num : nums) {
      if (!set.contains(num)) {
        set.add(num);
        sumOfSet += num;
      }
      sumOfNums += num;
    }
    return 2 * sumOfSet - sumOfNums;
  }
}

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



