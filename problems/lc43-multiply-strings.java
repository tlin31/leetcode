43. Multiply Strings - Medium

Given two non-negative integers num1 and num2 represented as strings, return the 
product of num1 and num2, also represented as a string.

Example 1:

Input: num1 = "2", num2 = "3"
Output: "6"
Example 2:

Input: num1 = "123", num2 = "456"
Output: "56088"
Note:

The length of both num1 and num2 is < 110.
Both num1 and num2 contain only digits 0-9.
Both num1 and num2 do not contain any leading zero, except the number 0 itself.
You must not use any built-in BigInteger library or convert the inputs to integer directly.

******************************************************
key:
	- offset count/trick
    - when do regular add 2 numbers, can use string to store answer, and add carry 
        using carry + "" + ans_orig, to append carry at the begining
    - check: if the bits = 0, need to consider that
	- edge case:
		1) if num1 == 0 or num2 == 0, return 0
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- 个位乘个位，得出一个数，然后个位乘十位，全部乘完以后，就再用十位乘以各个位。然后百位乘以各个位，
		最后将每次得出的数相加。十位的结果要补 1 个 0 ，百位的结果要补两个 0 。相加的话我们可以直接
		用之前的大数相加。

stats:

	- Runtime: 38 ms, faster than 6.36% of Java online submissions for Multiply Strings.
	- Memory Usage: 37.3 MB, less than 90.00%
	- 时间复杂度：O（m * n）, m，n 是两个字符串的长度。
	- 空间复杂度：O（1）


public String multiply(String num1, String num2) {
    if (num1.equals("0") || num2.equals("0")) {
        return "0";
    }  
    String ans = "0";
    int index = 0; //记录当前是哪一位，便于后边补 0 
    for (int i = num2.length() - 1; i >= 0; i--) {
        int carry = 0; //保存进位
        String ans_part = ""; //直接用字符串保存每位乘出来的数
        int m = num2.charAt(i) - '0';
        
        //乘上每一位
        for (int j = num1.length() - 1; j >= 0; j--) {
            int n = num1.charAt(j) - '0';
            int mul = m * n + carry; 
            ans_part = mul % 10 + "" + ans_part;
            carry = mul / 10;
        }
        if (carry > 0) {
            ans_part = carry + "" + ans_part;
        }

        //补 0 
        for (int k = 0; k < index; k++) {
            ans_part = ans_part + "0";
        }
        
        index++;
        //和之前的结果相加
        ans = sumString(ans, ans_part);
    }
    return ans;
}

//大数相加
private String sumString(String num1, String num2) {
    int carry = 0;

    //go from back to front
    int num1_index = num1.length() - 1;
    int num2_index = num2.length() - 1;
    String ans = "";

    while (num1_index >= 0 || num2_index >= 0) {
        //if one nunber has no more digits, simply make it 0, b/c won't change the addition's result
        int n1 = num1_index >= 0 ? num1.charAt(num1_index) - '0' : 0;
        int n2 = num2_index >= 0 ? num2.charAt(num2_index) - '0' : 0;
        int sum = n1 + n2 + carry;
        carry = sum / 10;
        ans = sum % 10 + "" + ans;
        num1_index--;
        num2_index--;
    }
    //if there are still carry left, then simply add it adthe begining of the answer
    if (carry > 0) {
        ans = carry + "" + ans;
    }
    return ans;
}


=======================================================================================================
method 2:

method:

	- 竖式计算。
    - Start from right to left, perform multiplication on every pair of digits, and add them
        together.
    - !!!! formula --> num1[i] * num2[j] will be placed at indices [i + j`, `i + j + 1]

stats:

	- 时间复杂度：O（m * n）m，n 是两个字符串的长度。
    - 空间复杂度：O（m + n）m，n 是两个字符串的长度。
	- Runtime: 4 ms, faster than 87.26% of Java online submissions for Multiply Strings.
    - Memory Usage: 35.9 MB, less than 100.00% 

    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int n1 = num1.length();
        int n2 = num2.length();

        // store result
        int[] pos = new int[n1 + n2]; 

        // start from the back
        for (int i = n1 - 1; i >= 0; i--) {
            for (int j = n2 - 1; j >= 0; j--) {
                //相乘的结果
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                //加上 pos[i+j+1] 之前已经累加的结果
                int sum = mul + pos[i + j + 1];

                //更新 pos[i + j]
                pos[i + j] += sum / 10;

                //更新 pos[i + j + 1]
                pos[i + j + 1] = sum % 10;
            }
        }

        StringBuilder sb = new StringBuilder();

        //careful cause will have the case if sum <10, then pos[i + j] = 0
        for (int i = 0; i < pos.length; i++) {
            //判断最高位是不是 0 
            if (i == 0 && pos[i] == 0) {
                continue;
            }
            sb.append(pos[i]);
        }
        return sb.toString();
    }

=======================================================================================================
method 3:

method:

    - similar method to method 2, only difference in that here it stores multiple 
        digits in products[]
    - 

stats:
    - Runtime: 3 ms, faster than 97.39% of Java online submissions for Multiply Strings.
    - Memory Usage: 36.4 MB, less than 100.00% of Java online submissions for Multiply Strings.

public class Solution {
    public String multiply(String num1, String num2) {
        int n1 = num1.length(), 
            n2 = num2.length();
        int[] products = new int[n1 + n2];
        for (int i = n1 - 1; i >= 0; i--) {
            for (int j = n2 - 1; j >= 0; j--) {
                int d1 = num1.charAt(i) - '0';
                int d2 = num2.charAt(j) - '0';
                products[i + j + 1] += d1 * d2;
            }
        }
        int carry = 0;
        for (int i = products.length - 1; i >= 0; i--) {
            int tmp = (products[i] + carry) % 10;
            carry = (products[i] + carry) / 10;
            products[i] = tmp;
        }
        StringBuilder sb = new StringBuilder();
        for (int num: products) sb.append(num);
        while (sb.length() != 0 && sb.charAt(0) == '0') sb.deleteCharAt(0);
        return sb.length() == 0 ? "0" : sb.toString();
    }
