67. Add Binary - Easy

Given two binary strings, return their sum (also a binary string).

The input strings are both non-empty and contains only characters 1 or 0.

Example 1:
Input: a = "11", b = "1"
Output: "100"

Example 2:
Input: a = "1010", b = "1011"
Output: "10101"

******************************************************
key:
	- carry
	- edge case:
		1) a or b = 0, then return the other one
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- use a string builder, then do sb.reverse
	- 

stats:

	- 
	- Runtime: 1 ms, faster than 100.00% of Java online submissions for Add Binary.
	- Memory Usage: 36 MB, less than 100.00%

	public String addBinary(String a, String b) {
		StringBuilder sb = new StringBuilder();

		// start from the end
		int i = a.length() - 1, 
			j = b.length() - 1, 
			carry = 0;

		while (i >= 0 || j >= 0) {

			//directly let it equal to carry
			int sum = carry;

			if (i >= 0) sum += a.charAt(i--) - '0';
			if (j >= 0) sum += b.charAt(j--) - '0';

			sb.append(sum % 2); 

			carry = sum / 2;
		}
		
		if (carry != 0) 
			sb.append(carry);

		return sb.reverse().toString();
	}


---------------	no reverse

	string addBinary(string a, string b){
		string s = "";
		int c = 0, i = a.size() - 1, j = b.size() - 1;

		while(i >= 0 || j >= 0 || c == 1){
			c += i >= 0 ? a[i --] - '0' : 0;
			c += j >= 0 ? b[j --] - '0' : 0;

			s = char(c % 2 + '0') + s;

			c /= 2;
		}
		return s;
	}



=======================================================================================================
method 2:

method:

	- use bits
	- result = aByte ^ bByte ^ carry --> as long as there is a one, then =1

stats:

	- 
	- 

	public String addBinary(String a, String b) {
		if(a == null || a.isEmpty()) {
			return b;
		}
		if(b == null || b.isEmpty()) {
			return a;
		}
		char[] aArray = a.toCharArray();
		char[] bArray = b.toCharArray();
		StringBuilder stb = new StringBuilder();
		int i = aArray.length - 1;
		int j = bArray.length - 1;
		int aByte;
		int bByte;
		int carry = 0;
		int result;
		while(i > -1 || j > -1 || carry == 1) {
			aByte = (i > -1) ? Character.getNumericValue(aArray[i--]) : 0;
			bByte = (j > -1) ? Character.getNumericValue(bArray[j--]) : 0;
			result = aByte ^ bByte ^ carry;
			carry = ((aByte + bByte + carry) >= 2) ? 1 : 0;
			stb.append(result);
		}
		return stb.reverse().toString();
	}

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



