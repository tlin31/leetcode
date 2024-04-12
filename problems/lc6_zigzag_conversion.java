6. ZigZag Conversion -- Medium

The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this:
(you may want to display this pattern in a fixed font for better legibility)

P   A   H   N
A P L S I I G
Y   I   R
And then read line by line: "PAHNAPLSIIGYIR"

Write the code that will take a string and make this conversion given a number of rows:

string convert(string s, int numRows);
Example 1:

Input: s = "PAYPALISHIRING", numRows = 3
Output: "PAHNAPLSIIGYIR"
Example 2:

Input: s = "PAYPALISHIRING", numRows = 4
Output: "PINALSIGYAHRPI"
Explanation:

P     I    N
A   L S  I G
Y A   H R
P     I

===================================================================================================================
method 1:

key:
	create list of string buffers
	add the character to its corresbonding string buffer one by one

    public String convert(String s, int nRows) {
        char[] c = s.toCharArray();
        int len = c.length;
        StringBuffer[] sb = new StringBuffer[nRows];

        // create string buffer with in string buffers
        for (int i = 0; i < sb.length; i++) 
        	sb[i] = new StringBuffer();

        int i = 0;
        while (i < len) {

        	//key points!!! i < len
            for (int idx = 0; idx < nRows && i < len; idx++) // vertically down
                sb[idx].append(c[i++]);

            for (int idx = nRows - 2; idx >= 1 && i < len; idx--) // obliquely up
                sb[idx].append(c[i++]);
        }

        for (int idx = 1; idx < nRows; idx++)
            sb[0].append(sb[idx]);

        return sb[0].toString();
    }


===================================================================================================================
method 2:

class Solution {
    public:
        string convert(string s, int numRows) {
            string result = "";
            if (numRows == 1)
                return s;
            int step1, step2;
            int len = s.size();

            for (int i = 0; i < numRows; ++i) {
                step1 = (numRows - i - 1) * 2;
                step2 = (i) * 2;
                int pos = i;
                if (pos < len)
                    result += s.at(pos);
                while (1) {
                    pos += step1;
                    if (pos >= len)
                        break;
                    if (step1)
                        result += s.at(pos);
                    pos += step2;
                    if (pos >= len)
                        break;
                    if (step2)
                        result += s.at(pos);
                }
            }
            return result;
        }
};




