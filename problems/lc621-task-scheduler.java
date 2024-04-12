621. Task Scheduler - Medium


Given a char array representing tasks CPU need to do. It contains capital letters A to Z where 
different letters represent different tasks. Tasks could be done without original order. Each task 
could be done in one interval. For each interval, CPU could finish one task or just be idle.

However, there is a non-negative cooling interval n that means between two SAME tasks, there must 
be at least n intervals that CPU are doing different tasks or just be idle.

You need to return the least number of intervals the CPU will take to finish all the given tasks.

 

Example:

Input: tasks = ["A","A","A","B","B","B"], n = 2
Output: 8
Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
 

Constraints:

The number of tasks is in the range [1, 10000].
The integer n is in the range [0, 100].


******************************************************
key:
	- Priority queue + greedy
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:  Greedy & priority queue


Stats:

	- Time complexity : O(time) Number of iterations will be equal to resultant time.
	- Space complexity : O(1). Constant size array mapmap is used.



Method:

	-  Greedy --> we should always process the task which has largest amount left.
	-  1. Put tasks (only their counts are enough, we don't care they are 'A' or 'B') in a PriorityQueue 
	         in descending order.
	   2. Start to process tasks from front of the queue. If amount left > 0, put it into a coolDown 
	         HashMap
	   3. If there's task which cool-down expired, put it back into the Q and wait to be processed.
	   		cooldown map = <ready time, left count of this task>
	   		ex. for A x 3, left = 3-1 = 2, put <0,2> in map so that when time = 0 comes,
	   		    we take out Ax2 out from map and put it back into the queue
	   4. Repeat step 2,3 till there is no task left.

public class Solution {
    public int leastInterval(char[] tasks, int n) {
        if (n == 0) 
        	return tasks.length;
        
        Map<Character, Integer> taskToCount = new HashMap<>();

        for (char c : tasks) {
            taskToCount.put(c, taskToCount.getOrDefault(c, 0) + 1);
        }
        
        Queue<Integer> queue = new PriorityQueue<>((i1, i2) -> i2 - i1);
        for (char c : taskToCount.keySet()) 
        	queue.offer(taskToCount.get(c));
        
        // map <ready time, left count of this task>
        Map<Integer, Integer> coolDown = new HashMap<>();
        int currTime = 0;

        while (!queue.isEmpty() || !coolDown.isEmpty()) {

            if (coolDown.containsKey(currTime - n - 1)) {
                queue.offer(coolDown.remove(currTime - n - 1));
            }

            if (!queue.isEmpty()) {
                int left = queue.poll() - 1;
        		if (left != 0) 
        			coolDown.put(currTime, left);
            }
            currTime++;
        }
        
        return currTime;
    }
}

ex. ["A","A","A","C","C","B","B","B"], n = 3

Queue = [3, 3, 2]
---currTime = 0
schedule a new task, now map = {0=2}
---currTime = 1
schedule a new task, now map = {0=2, 1=2}
---currTime = 2
schedule a new task, now map = {0=2, 1=2, 2=1}
---currTime = 3 --> idle, don't have new task to fill into place

---currTime = 4
Have a new task after cooldown ready = 2
schedule a new task, now map = {1=2, 2=1, 4=1}
---currTime = 5
Have a new task after cooldown ready = 2
schedule a new task, now map = {2=1, 4=1, 5=1}
---currTime = 6
Have a new task after cooldown ready = 1
schedule a new task, now map = {4=1, 5=1}
---currTime = 7

---currTime = 8
Have a new task after cooldown ready = 1
schedule a new task, now map = {5=1}
---currTime = 9
Have a new task after cooldown ready = 1
schedule a new task, now map = {}


~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: Sort + Math

Stats:

	- 
	- 


Method:
	- ex. ["A","A","A","C","C","B","B","B"], n = 3
	 	After sort c, it will have [0, 0, ......., 2, 3, 3]
		

// (c[25] - 1) * (n + 1) + 25 - i  is frame size
// when inserting chars, the frame might be "burst", then tasks.length takes precedence
// when 25 - i > n, the frame is already full at construction, the following is still valid.

public class Solution {
    public int leastInterval(char[] tasks, int n) {

        int[] c = new int[26];
        for(char t : tasks){
            c[t - 'A']++;
        }

        Arrays.sort(c);
        int i = 25;
        while(i >= 0 && c[i] == c[25]) 
        	i--;

        return Math.max(tasks.length, (c[25] - 1) * (n + 1) + (25 - i));
    }
}




ex.
AAAABBBEEFFGG 3

here X represents a space gap:

Frame: "AXXXAXXXAXXXA"
insert 'B': "ABXXABXXABXXA" <--- 'B' has higher frequency than the other characters, insert it first.
insert 'E': "ABEXABEXABXXA"
insert 'F': "ABEFABEXABFXA" <--- each time try to fill the k-1 gaps as full or evenly as possible.
insert 'G': "ABEFABEGABFGA"


AACCCBEEE 2

3 identical chunks "CE", "CE CE CE" <-- this is a frame
insert 'A' among the gaps of chunks since it has higher frequency than 'B' ---> "CEACEACE"
insert 'B' ---> "CEABCEACE" <----- result is tasks.length;


AACCCDDEEE 3

3 identical chunks "CE", "CE CE CE" <--- this is a frame.
Begin to insert 'A'->"CEA CEA CE"
Begin to insert 'B'->"CEABCEABCE" <---- result is tasks.length;


ACCCEEE 2

3 identical chunks "CE", "CE CE CE" <-- this is a frame
Begin to insert 'A' --> "CEACE CE" <-- result is (c[25] - 1) * (n + 1) + 25 -i = 2 * 3 + 2 = 8


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

