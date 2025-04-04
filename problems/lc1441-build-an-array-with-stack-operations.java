1441. Build an Array With Stack Operations - Easy

Given an array target and an integer n. In each iteration, you will read a number from  
list = {1,2,3..., n}.

Build the target array using the following operations:

	Push: Read a new element from the beginning list, and push it in the array.
	Pop: delete the last element of the array.

If the target array is already built, stop reading more elements.
You are guaranteed that the target array is strictly increasing, only containing numbers between 1 
to n inclusive.

Return the operations to build the target array.

You are guaranteed that the answer is unique.

 

Example 1:

Input: target = [1,3], n = 3
Output: ["Push","Push","Pop","Push"]
Explanation: 
Read number 1 and automatically push in the array -> [1]
Read number 2 and automatically push in the array then Pop it -> [1]
Read number 3 and automatically push in the array -> [1,3]
Example 2:

Input: target = [1,2,3], n = 3
Output: ["Push","Push","Push"]
Example 3:

Input: target = [1,2], n = 4
Output: ["Push","Push"]
Explanation: You only need to read the first 2 numbers and stop.
Example 4:

Input: target = [2,3,4], n = 4
Output: ["Push","Pop","Push","Push","Push"]
 

Constraints:

1 <= target.length <= 100
1 <= target[i] <= 100
1 <= n <= 100
target is strictly increasing.


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: 2 pointers


Stats:

	- 
	- 


Method:

	-	
	-	


	public List<String> buildArray(int[] target, int n) {
        List<String> result = new ArrayList<>();
        int j=0;
        for (int i=1;i<=n && j<target.length;i++) {
            result.add("Push");
            if(target[j]==i) {
                j++;
            } else {
                result.add("Pop");
            }
        }
        return result;
    }







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

Time: O(N)
Space: O(N) for output

class Solution:
    def buildArray(self, target: List[int], n: int) -> List[str]:
        def build():
            curr = 1
            for num in target:
                yield 'Push'
                while curr < num:
                    yield from ('Pop', 'Push')
                    curr += 1
                curr += 1
        return list(build())

        
--
Update: only for version

class Solution:
    def buildArray(self, target: List[int], n: int) -> List[str]:
        curr, result = 1, []
        for num in target:
            result += ['Push'] + ['Pop', 'Push'] * (num - curr)
            curr = num + 1
        return result



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

