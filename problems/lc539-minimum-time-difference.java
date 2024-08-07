539. Minimum Time Difference - Medium


Given a list of 24-hour clock time points in "Hour:Minutes" format, find the minimum minutes 
difference between any two time points in the list.


Example 1:
Input: ["23:59","00:00"]
Output: 1


Note:
The number of time points in the given list is at least 2 and wont exceed 20000.
The input time is legal and ranges from 00:00 to 23:59.


******************************************************
key:
	- Sort + bucket sort
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: use sort


Stats:

	- 
	- 


Method:

	-	
	-	


class Solution {
    public int findMinDifference(List<String> timePoints) {
        int n = timePoints.size();
        int[] minPoints = new int[timePoints.size()];
        for(int i = 0; i < timePoints.size();i++){
            String cur = timePoints.get(i);
            int min = Integer.parseInt(cur.substring(0,2))*60 + Integer.parseInt(cur.substring(3,5));
            minPoints[i] = min;
        }
        
        Arrays.sort(minPoints);
        int min = Integer.MAX_VALUE;
        for (int i = 1; i<minPoints.length; i++) {
            min = Math.min(min,minPoints[i]-minPoints[i-1]);
        }
        
        // corner case!
        min = Math.min(min, minPoints[0] + 24*60 - minPoints[n-1]);
        return min;
    }
}






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: bucket sort

Stats:

	- O(n)
	- 


Method:

	-	
	-	



There is only 24 * 60 = 1440 possible time points. Just create a boolean array, each element stands 
for if we see that time point or not. 

public class Solution {
    public int findMinDifference(List<String> timePoints) {
        boolean[] mark = new boolean[24 * 60];
        for (String time : timePoints) {
            String[] t = time.split(":");
            int h = Integer.parseInt(t[0]);
            int m = Integer.parseInt(t[1]);
            if (mark[h * 60 + m]) return 0;
            mark[h * 60 + m] = true;
        }
        
        int prev = 0, min = Integer.MAX_VALUE;
        int first = Integer.MAX_VALUE, last = Integer.MIN_VALUE;
        for (int i = 0; i < 24 * 60; i++) {
            if (mark[i]) {
                if (first != Integer.MAX_VALUE) {
                    min = Math.min(min, i - prev);
                }
                first = Math.min(first, i);
                last = Math.max(last, i);
                prev = i;
            }
        }
        
        min = Math.min(min, (24 * 60 - last + first));
        
        return min;
    }
}






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

