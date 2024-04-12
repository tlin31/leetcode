752. Open the Lock - Medium

You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots: '0', '1', '2', 
'3', '4', '5', '6', '7', '8', '9'. The wheels can rotate freely and wrap around: for example we 
can turn '9' to be '0', or '0' to be '9'. Each move consists of turning one wheel one slot.

The lock initially starts at '0000', a string representing the state of the 4 wheels.

You are given a list of 'deadends' dead ends, meaning if the lock displays any of these codes, 
the wheels of the lock will stop turning and you will be unable to open it.

Q: Given a target representing the value of the wheels that will unlock the lock, return the minimum 
total number of turns required to open the lock, or -1 if it is impossible.

Example 1:
Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
Output: 6
Explanation:
A sequence of valid moves would be "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202".
Note that a sequence like "0000" -> "0001" -> "0002" -> "0102" -> "0202" would be invalid,
because the wheels of the lock become stuck after the display becomes the dead end "0102".

Example 2:
Input: deadends = ["8888"], target = "0009"
Output: 1
Explanation:
We can turn the last wheel in reverse to move from "0000" -> "0009".


Example 3:
Input: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target = "8888"
Output: -1
Explanation:
We can not reach the target without getting stuck.


Example 4:
Input: deadends = ["0000"], target = "8888"
Output: -1


Note:
The length of deadends will be in the range [1, 500].
target will not be in the list deadends.
Every string in deadends and the string target will be a string of 4 digits from the 10,000 
possibilities '0000' to '9999'.




******************************************************
key:
	- bfs.  go from the target, try to avoid the deadends
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- bfs 
	- 

stats:

	- Time: O(N^2 * A^N +D) 
			A is the number of digits in our alphabet, 
			N is the number of digits in the lock, 
			D is the size of deadends. 
			We might visit every lock combination, plus we need to instantiate our set dead. 
			When we visit every lock combination, we spend O(N^2)

	- Space: O(A^N +D), for the queue and the set dead.




class Solution {
    public int openLock(String[] deadends, String target) {
        Queue<String> q = new LinkedList<>();
        Set<String> deads = new HashSet<>(Arrays.asList(deadends));
        Set<String> visited = new HashSet<>();
        q.offer("0000");
        visited.add("0000");
        int countOfSteps = 0;
        while(!q.isEmpty()) {
            int size = q.size();

            while(size > 0) {
                String s = q.poll();
                if(deads.contains(s)) {
                    size --;
                    continue;
                }

                if(s.equals(target)) 
                	return countOfSteps;

                StringBuilder sb = new StringBuilder(s);

                for(int i = 0; i < 4; i ++) {
                    char c = sb.charAt(i);

                    // only has 2 variations for next state, either +1 or -1
                    String s1 = sb.substring(0, i) + (c == '9' ? 0 : c - '0' + 1) + sb.substring(i + 1);
                    String s2 = sb.substring(0, i) + (c == '0' ? 9 : c - '0' - 1) + sb.substring(i + 1);
                    
                    if(!visited.contains(s1) && !deads.contains(s1)) {
                        q.offer(s1);
                        visited.add(s1);
                    }

                    if(!visited.contains(s2) && !deads.contains(s2)) {
                        q.offer(s2);
                        visited.add(s2);
                    }
                }
                // done enumerating all possibilities for current s
                size--;
            }
            countOfSteps ++;
        }
        return -1;
    }
}

=======================================================================================================
method 2:

method:

	- 2 end bfs
	- The purpose for exchanging 'begin' and 'end': 
		we will be processing BFS for the elements from 'end' set in the next loop.
		The whole process is like: 
			Running BFS in 'begin' set -----> Create a new set 'temp' to store the value -----> 
			begin = temp -----> 'begin'(is 'temp' now) and 'end' exchange value ------> 
			(next loop) Running BFS in 'end'(is now 'begin') 
	- Caution: for 2 end bfs, need to maintain a global set visited in order to not let 2
			   ends into looping

stats:

	- Runtime: 58 ms, faster than 89.30% of Java online submissions for Open the Lock.
	- Memory Usage: 42.3 MB, less than 84.21% of Java online submissions for Open the Lock.


class Solution {
    public int openLock(String[] deadends, String target) {
        Set<String> begin = new HashSet<>();
        Set<String> end = new HashSet<>();

        Set<String> deads = new HashSet<>(Arrays.asList(deadends));
        begin.add("0000");
        end.add(target);
        int countOfSteps = 0;
        Set<String> temp;

        while(!begin.isEmpty() && !end.isEmpty()) {

        	// always has smaller size in begin
        	// naturally, will swap begin and end / alternate
            if (begin.size() > end.size()) {
                temp = begin;
                begin = end;
                end = temp;
            }

            temp = new HashSet<>();
            for(String s : begin) {
                if(end.contains(s)) 
                	return countOfSteps;

                if(deads.contains(s)) 
                	continue;

                // don't have visited set now, just add it to deads to avoid looping!
                deads.add(s);
                StringBuilder sb = new StringBuilder(s);
                for(int i = 0; i < 4; i ++) {
                    char c = sb.charAt(i);

                    // 2 cases: go up AND go down, generate 2 possible strings
                    String s1 = sb.substring(0, i) + (c == '9' ? 0 : c - '0' + 1) + sb.substring(i + 1);
                    String s2 = sb.substring(0, i) + (c == '0' ? 9 : c - '0' - 1) + sb.substring(i + 1);
                    
                    if(!deads.contains(s1))
                        temp.add(s1);
                    if(!deads.contains(s2))
                        temp.add(s2);
                }
            }

            // finish processing all strings in this level, increment step
            countOfSteps ++;

            // store the to-be-processed next level in begin 
            begin = temp;
        }
        return -1;
    }
}

Output: 
old begin:  [0000]
new begin:  [0090, 0100, 0001, 1000, 0010, 9000, 0900, 0009]
swap begin and end at: 1
old begin:  [0202]
new begin:  [9202, 0302, 0203, 1202, 0212, 0292]
old begin:  [9202, 0302, 0203, 1202, 0212, 0292]
new begin:  [9102, 9201, 9302, 9203, 0392, 0293, 0192, 0291, 0402, 0303, 0204, 0301, 0103, 0222, 2202, 9212, 8202, 9292, 0282, 1292, 0312, 0213, 1302, 1203, 0112, 0211, 1102, 1201]
swap begin and end at: 3
old begin:  [0090, 0100, 0001, 1000, 0010, 9000, 0900, 0009]
new begin:  [9100, 9001, 0909, 0190, 0091, 0200, 0002, 0109, 0901, 0800, 0008, 1090, 8000, 0020, 2000, 1010, 9010, 0080, 9090, 0990, 0099, 0110, 0011, 1100, 1001, 9900, 9009, 0910, 0019, 1900, 1009]
swap begin and end at: 4
old begin:  [9102, 9201, 9302, 9203, 0392, 0293, 0192, 0291, 0402, 0303, 0204, 0301, 0103, 0222, 2202, 9212, 8202, 9292, 0282, 1292, 0312, 0213, 1302, 1203, 0112, 0211, 1102, 1201]
new begin:  [9103, 9301, 8212, 9101, 9222, 7202, 9303, 8292, 0492, 0294, 0092, 0290, 0403, 0205, 0401, 2302, 1213, 0003, 0322, 2102, 1211, 0122, 8102, 9312, 9112, 8302, 9392, 9192, 0382, 1392, 0182, 1192, 1303, 0412, 0214, 1301, 1103, 1222, 3202, 1101, 0012, 0210, 9002, 9200, 9402, 9204, 9282, 0393, 0272, 1282, 0391, 0193, 2292, 0191, 0502, 0304, 0104, 2203, 1312, 0300, 0223, 2201, 1112, 0221, 8201, 9213, 9211, 8203, 9293, 9291, 0283, 1293, 0281, 1291, 1402, 1204, 0313, 0311, 0113, 2212, 1002, 1200, 0232, 0111]
swap begin and end at: 5
old begin:  [9100, 9001, 0909, 0190, 0091, 0200, 0002, 0109, 0901, 0800, 0008, 1090, 8000, 0020, 2000, 1010, 9010, 0080, 9090, 0990, 0099, 0110, 0011, 1100, 1001, 9900, 9009, 0910, 0019, 1900, 1009]
new begin:  [7000, 9101, 0809, 0890, 0098, 1020, 3000, 8010, 0290, 0092, 9020, 2900, 1019, 0007, 0920, 0003, 8090, 0089, 8009, 0209, 0801, 1190, 8100, 9910, 1909, 1990, 2001, 1110, 0021, 9110, 9990, 0180, 0810, 0018, 9909, 9190, 1101, 0210, 0012, 9109, 9901, 1109, 1901, 1080, 2090, 9200, 9002, 9800, 9008, 0908, 2010, 0030, 0191, 0070, 2009, 1910, 0700, 0029, 0300, 9080, 0980, 8900, 0902, 0108, 1091, 8001, 9019, 0919, 1099, 2100, 1011, 0120, 9011, 9099, 0081, 1800, 1008, 9091, 1200, 1002, 0199, 0991, 0111, 0999, 0119, 0911]
swap begin and end at: 6
old begin:  [9103, 9301, 8212, 9101, 9222, 7202, 9303, 8292, 0492, 0294, 0092, 0290, 0403, 0205, 0401, 2302, 1213, 0003, 0322, 2102, 1211, 0122, 8102, 9312, 9112, 8302, 9392, 9192, 0382, 1392, 0182, 1192, 1303, 0412, 0214, 1301, 1103, 1222, 3202, 1101, 0012, 0210, 9002, 9200, 9402, 9204, 9282, 0393, 0272, 1282, 0391, 0193, 2292, 0191, 0502, 0304, 0104, 2203, 1312, 0300, 0223, 2201, 1112, 0221, 8201, 9213, 9211, 8203, 9293, 9291, 0283, 1293, 0281, 1291, 1402, 1204, 0313, 0311, 0113, 2212, 1002, 1200, 0232, 0111]

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



