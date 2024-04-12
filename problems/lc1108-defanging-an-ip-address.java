1108. Defanging an IP Address - Easy


Given a valid (IPv4) IP address, return a defanged version of that IP address.

A defanged IP address replaces every period "." with "[.]".

 

Example 1:

Input: address = "1.1.1.1"
Output: "1[.]1[.]1[.]1"
Example 2:

Input: address = "255.100.50.0"
Output: "255[.]100[.]50[.]0"
 

Constraints:

The given address is a valid IPv4 address.


******************************************************
key:
	- string
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: string builder


Stats:

	- 
	- 


Method:

	-	
	-	

class Solution {
    public String defangIPaddr(String address) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < address.length(); i++) {
            if (address.charAt(i) != '.')
                result.append(address.charAt(i) );
            else
                result.append("[.]");
        }
        return result.toString();
    }
}
=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	

class Solution {
    public String defangIPaddr(String address) {
        return address.replace(".","[.]");
    }
}

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



