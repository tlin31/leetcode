739. Daily Temperatures-Medium

Given an array of integers temperatures represents the daily temperatures, return an 
array answer such that answer[i] is the number of days you have to wait after the ith 
day to get a warmer temperature. If there is no future day for which this is possible, 
keep answer[i] == 0 instead.

 

Example 1:

Input: temperatures = [73,74,75,71,69,72,76,73]
Output: [1,1,4,2,1,1,0,0]
Example 2:

Input: temperatures = [30,40,50,60]
Output: [1,1,1,0]
Example 3:

Input: temperatures = [30,60,90]
Output: [1,1,0]
 

Constraints:

1 <= temperatures.length <= 105
30 <= temperatures[i] <= 100

******************************************************
key:
	- Keep monotonic decreasing stack!
	- edge case:
		1) empty array? return 0
		2) 

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time: O(N)
		Space: O(N)
	- 


Method:

	-	

//Stack:
public int[] dailyTemperatures(int[] temperatures) {
    Stack<Integer> stack = new Stack<>();
    int[] ret = new int[temperatures.length];
    for(int i = 0; i < temperatures.length; i++) {
        while(!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
            int idx = stack.pop();
            ret[idx] = i - idx;
        }
        stack.push(i);
    }
    return ret;
}








//Array:
public int[] dailyTemperatures(int[] temperatures) {
    int[] stack = new int[temperatures.length];
    //top is the height of the stack
    int top = -1;
    int[] ret = new int[temperatures.length];
    for(int i = 0; i < temperatures.length; i++) {
        while(top > -1 && temperatures[i] > temperatures[stack[top]]) {
        	//top-- is popping the stack
            int idx = stack[top--];
            ret[idx] = i - idx;
        }
        stack[++top] = i;
    }
    return ret;
}


temps = [73,74,75,71,69,72,76,73]
stack = []  (stores indices)
result = [0,0,0,0,0,0,0,0]

i=0: stack=[] → push 0 → stack=[0]
i=1: temps[1]=74 > temps[0]=73 → pop 0, result[0]=1-0=1
     stack=[] → push 1 → stack=[1]
i=2: temps[2]=75 > temps[1]=74 → pop 1, result[1]=2-1=1
     stack=[] → push 2 → stack=[2]
i=3: temps[3]=71 < temps[2]=75 → push 3 → stack=[2,3]
i=4: temps[4]=69 < temps[3]=71 → push 4 → stack=[2,3,4]
i=5: temps[5]=72 > temps[4]=69 → pop 4, result[4]=5-4=1
               72 > temps[3]=71 → pop 3, result[3]=5-3=2
               72 < temps[2]=75 → stop → push 5 → stack=[2,5]
i=6: temps[6]=76 > temps[5]=72 → pop 5, result[5]=6-5=1
               76 > temps[2]=75 → pop 2, result[2]=6-2=4
     stack=[] → push 6 → stack=[6]
i=7: temps[7]=73 < temps[6]=76 → push 7 → stack=[6,7]

Remaining in stack: [6,7] → result stays 0 (no warmer day)

result = [1,1,4,2,1,1,0,0] ✅
