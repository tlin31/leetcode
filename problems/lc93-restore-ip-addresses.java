93. Restore IP Addresses - Medium

A valid IP address consists of exactly four integers separated by single dots. Each integer is 
between 0 and 255 (inclusive) and cannot have leading zeros.

For example, "0.1.2.201" and "192.168.1.1" are valid IP addresses, but "0.011.255.245", 
"192.168.1.312" and "192.168@1.1" are invalid IP addresses.

Given a string s containing only digits, return all possible valid IP addresses that can 
be formed by inserting dots into s. You are not allowed to reorder or remove any digits in s. 
You may return the valid IP addresses in any order.

 

Example 1:

Input: s = "25525511135"
Output: ["255.255.11.135","255.255.111.35"]
Example 2:

Input: s = "0000"
Output: ["0.0.0.0"]
Example 3:

Input: s = "101023"
Output: ["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
 

Constraints:

1 <= s.length <= 20
s consists of digits only.
******************************************************
key:
	- backtrack
	- edge case:
		1) empty string, return empty
		2)

******************************************************


    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        doRestore(result, "", s, 0);
        return result;
    }
    
    // k = num of parts, part is the num between two .
    private void doRestore(List<String> result, String path, String s, int k) {
        if (s.isEmpty() || k == 4) {
            if (s.isEmpty() && k == 4)
                result.add(path.substring(1));
            return;
        }

        // Avoid leading 0
        for (int i = 1; i <= (s.charAt(0) == '0' ? 1 : 3) && i <= s.length(); i++) { 
            String part = s.substring(0, i);
            if (Integer.valueOf(part) <= 255)
                doRestore(result, path + "." + part, s.substring(i), k + 1);
        }
    }



=======================================================================================================
method 1:

method:

	- Here is an algorithm for the backtrack function backtrack(prev_pos = -1, dots = 3) which 
	  takes pos of the previously placed dot prev_pos and number of dots to place dots as arguments :

	Iterate over 3 available slots curr_pos to place a dot.
		Check if the segment from the previous dot to the current one is valid :
			Yes :
				Place the dot.
				Check if all 3 dots are placed :
					Yes :
						Add the solution into the output list.
					No :
						Proceed to place next dots backtrack(curr_pos, dots - 1).
		Remove the last dot to backtrack.
	- 

stats:

	- 
	- 

class Solution {
	  int n;
	  String s;
	  LinkedList<String> segments = new LinkedList<String>();
	  ArrayList<String> output = new ArrayList<String>();


	  // main function
	  public List<String> restoreIpAddresses(String s) {
		    n = s.length();
		    this.s = s;
		    backtrack(-1, 3);
		    return output;
	  }

	  public void backtrack(int prev_pos, int dots) {
		    // prev_pos : the position of the previously placed dot
		    // dots : number of dots to place

		    // The current dot curr_pos could be placed in a range from prev_pos + 1 to prev_pos + 4.
		    // The dot couldn't be placed after the last character in the string.
		    int max_pos = Math.min(n - 1, prev_pos + 4);
		    for (int curr_pos = prev_pos + 1; curr_pos < max_pos; curr_pos++) {
		      String segment = s.substring(prev_pos + 1, curr_pos + 1);
		      if (valid(segment)) {
		        segments.add(segment);  // place dot
		        if (dots - 1 == 0)      // if all 3 dots are placed
		          update_output(curr_pos);  // add the solution to output
		        else
		          backtrack(curr_pos, dots - 1);  // continue to place dots
		        segments.removeLast();  // remove the last placed dot 
		      }
		    }
	  }

	  public boolean valid(String segment) {
		    /*
		    Check if the current segment is valid :
		    1. less or equal to 255      
		    2. the first character could be '0' 
		    only if the segment is equal to '0'
		    */
		    int m = segment.length();
		    if (m > 3)
		      return false;
		    return (segment.charAt(0) != '0') ? (Integer.valueOf(segment) <= 255) : (m == 1);
	  }

	  public void update_output(int curr_pos) {
		    /*
		    Append the current list of segments 
		    to the list of solutions
		    */
		    String segment = s.substring(curr_pos + 1, n);
		    if (valid(segment)) {
		      segments.add(segment);
		      output.add(String.join(".", segments));
		      segments.removeLast();
		    }
	  }




}


=======================================================================================================
method 2:

method:

	- backtrack
	- 

stats:

	- 
	- 


    

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- Runtime: 2 ms, faster than 87.78% of Java online submissions for Restore IP Addresses.
	- Memory Usage: 38.2 MB, less than 30.23% of Java online submissions for Restore IP Addresses.


public List<String> restoreIpAddresses(String s) {
		List<String> ret = new ArrayList<>();
		
		StringBuffer ip = new StringBuffer();
		for(int a = 1 ; a < 4 ; ++ a)
		for(int b = 1 ; b < 4 ; ++ b)
	        for(int c = 1 ; c < 4 ; ++ c)
		for(int d = 1 ; d < 4 ; ++ d)
		{
			if(a + b + c + d == s.length() )
			{
				int n1 = Integer.parseInt(s.substring(0, a));
				int n2 = Integer.parseInt(s.substring(a, a+b));
				int n3 = Integer.parseInt(s.substring(a+b, a+b+c));
				int n4 = Integer.parseInt(s.substring(a+b+c));
				if(n1 <= 255 && n2 <= 255 && n3 <= 255 && n4 <= 255)
				{
					ip.append(n1).append('.').append(n2)
						.append('.').append(n3).append('.').append(n4);
					if(ip.length() == s.length() + 3) ret.add(ip.toString());
					ip.delete(0, ip.length());
				}
			}
		}
		return ret;
    }

