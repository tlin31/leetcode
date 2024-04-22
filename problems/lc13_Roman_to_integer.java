13. Roman to integer---Easy

Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.

Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
For example, two is written as II in Roman numeral, just two one's added together. Twelve is written as, 
XII, which is simply X + II. The number twenty seven is written as XXVII, which is XX + V + II.

Roman numerals are usually written largest to smallest from left to right. However, the numeral for four 
is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it 
making four. The same principle applies to the number nine, which is written as IX. There are six instances 
where subtraction is used:

I can be placed before V (5) and X (10) to make 4 and 9. 
X can be placed before L (50) and C (100) to make 40 and 90. 
C can be placed before D (500) and M (1000) to make 400 and 900.
Given a roman numeral, convert it to an integer. Input is guaranteed to be within the range from 1 to 3999.

Input: "LVIII"
Output: 58
Explanation: L = 50, V= 5, III = 3.

Example 5:
Input: "MCMXCIV"
Output: 1994
Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.

=========================================================================================================================================================
method 1:

key:
	- finding the pattern for IV & VI，
	- go from the back

Runtime: 5 ms, faster than 59.37% of Java
Memory Usage: 36.1 MB, less than 100.00% of Java

public int romanToInt(String s) {
    //：Ⅰ（1）Ⅴ（5）Ⅹ（10）L（50）C（100）D（500）M（1000）
    // rules:位于⼤数的后⾯时就作为加数；位于⼤数的前⾯就作为减数
    //eg：Ⅲ=3,Ⅳ=4,Ⅵ=6,ⅩⅨ=19,ⅩⅩ=20,ⅩLⅤ=45,MCMⅩⅩC=1980

    if (s == null || s.length() == 0) return 0;
    int len = s.length();
    HashMap < Character, Integer > map = new HashMap < Character, Integer > ();
    map.put('I', 1);
    map.put('V', 5);
    map.put('X', 10);
    map.put('L', 50);
    map.put('C', 100);
    map.put('D', 500);
    map.put('M', 1000);
    
    //the last char
    int result = map.get(s.charAt(len - 1));
    int pivot = result;
    for (int i = len - 2; i >= 0; i--) {
    	// second to the last char
        int curr = map.get(s.charAt(i));


        if (curr >= pivot) {
            result += curr;
        } else {
            result -= curr;
        }
        pivot = curr;
    }
    return result;
}




=========================================================================================================================================================
method 2:
	- instead of char by char, now goes to search the chars
	- minus 2 for IV, because without knowing this, IV = I+V = 6

Runtime: 4 ms, faster than 76.61% of Java online submissions for Roman to Integer.
Memory Usage: 35.9 MB, less than 100.00% of Java

public int romanToInt(String s) {
    int sum = 0;

    if (s.indexOf("IV") != -1) {
        sum -= 2;
    }
    if (s.indexOf("IX") != -1) {
        sum -= 2;
    }
    if (s.indexOf("XL") != -1) {
        sum -= 20;
    }
    if (s.indexOf("XC") != -1) {
        sum -= 20;
    }
    if (s.indexOf("CD") != -1) {
        sum -= 200;
    }
    if (s.indexOf("CM") != -1) {
        sum -= 200;
    }
    char c[] = s.toCharArray();
    int count = 0;
    for (; count <= s.length() - 1; count++) {
        if (c[count] == 'M') sum += 1000;
        if (c[count] == 'D') sum += 500;
        if (c[count] == 'C') sum += 100;
        if (c[count] == 'L') sum += 50;
        if (c[count] == 'X') sum += 10;
        if (c[count] == 'V') sum += 5;
        if (c[count] == 'I') sum += 1;
    }
    return sum;
}

The logic here is that, if a current character value is greater than that of the previous, 
we have to subtract it. We subtract twice, because previously iteration had blindly added it. 


=========================================================================================================================================================
method 3:
	- similar to method 1, but don't use a map

Runtime: 3 ms, faster than 100.00% of Java 
Memory Usage: 36.4 MB, less than 100.00% 

public int romanToInt(String str) {
    int[] a = new int[26];
    a['I' - 'A'] = 1;
    a['V' - 'A'] = 5;
    a['X' - 'A'] = 10;
    a['L' - 'A'] = 50;
    a['C' - 'A'] = 100;
    a['D' - 'A'] = 500;
    a['M' - 'A'] = 1000;
    char prev = 'A';
    int sum = 0;
    
    //key!!!
    for(char s : str.toCharArray()) {
        if(a[s - 'A'] > a[prev - 'A']) {
            sum = sum - 2 * a[prev - 'A'];
        }
        sum = sum + a[s - 'A'];
        prev = s;
    }
    return sum;
}























