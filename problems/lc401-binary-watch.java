401. Binary Watch - Easy

A binary watch has 4 LEDs on the top which represent the hours (0-11), and the 6 LEDs on the bottom
represent the minutes (0-59).

Each LED represents a zero or one, with the least significant bit on the right.


For example, the above binary watch reads "3:25".

Given a non-negative integer n which represents the number of LEDs that are currently on, return all
possible times the watch could represent.

Example:

Input: n = 1
Return: ["1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04", "0:08", "0:16", "0:32"]
Note:
The order of output does not matter.
The hour must not contain a leading zero, for example "01:00" is not valid, it should be "1:00".
The minute must be consist of two digits and may contain a leading zero, for example "10:2" is not valid, it should be "10:02".


******************************************************
key:
	- recursively, build up on n-1, n-2, .... 2, 1
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: enumeration

Method:

	-	%d means an integer
		: is a :
		%02d means an integer, left padded with zeros up to 2 digits.
	-	counts the bits of the hours and minutes combined together for all the combination of 
	    hours and minutes in a day, if the number of bits matches the number of LED lights, 
	    then he added it to the solution list to be returned.


Stats:

	- Runtime: 12 ms, faster than 39.87% of Java online submissions for Binary Watch.
	- Memory Usage: 39.1 MB, less than 16.67% 


public List<String> readBinaryWatch(int num) {
    List<String> times = new ArrayList<>();
    for (int h=0; h<12; h++)
        for (int m=0; m<60; m++)
            if (Integer.bitCount(h) + Integer.bitCount(m) == num)
                times.add(String.format("%d:%02d", h, m));
    return times;        
}

=======================================================================================================
method 2:

Method:

	-	backtrack
	-	


Stats:

	- 
	- 

class Solution {
    
    // possible hours & minuters light
    int[] weight = {8, 4, 2, 1, 32, 16, 8, 4, 2, 1};
    
    public List<String> readBinaryWatch(int num) {
        List<String> res = new ArrayList<>();
        helper(0, 0, 0, num, res);
        return res;
    }
    
    private void helper(int hour, int min, int index, int num, List<String> res) {
        // 0 lights, don't have any meaning to add more digits
        if (hour > 12 || min >= 60 || hour == 12 && min == 0) {
            return;
        }

        if (num == 0) {
            String strMin = String.valueOf(min);

            // add learning 0's to minute
            if (strMin.length() == 1) {
                strMin = "0" + strMin;
            }

            res.add(String.valueOf(hour) + ":" + strMin);

        } else if (index < 10 && num <= 10 - index && num > 0) {
            helper(hour, min, index + 1, num, res);

            // update the hours
            if (index < 4) {
                helper(hour + weight[index], min, index + 1, num - 1, res);
            } else {
            // update minute light
                helper(hour, min + weight[index], index + 1, num - 1, res);
            }
        }
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



