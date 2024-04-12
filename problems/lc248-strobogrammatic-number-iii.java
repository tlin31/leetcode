248. Strobogrammatic Number III - Hard

A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).

Write a function to count the total strobogrammatic numbers that exist in the range of low <= num <= high.

Example:

Input: low = "50", high = "100"
Output: 3 
Explanation: 69, 88, and 96 are three strobogrammatic numbers.
Note:
Because the range might be a large number, the low and high numbers are represented as string.


******************************************************
key:
	- String, find pattern, go from two ends to the middle
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:


	Construct char arrays from low.length() to high.length()

	Add good pairs from 左右两端 to middle

	When left > right, add eligible count

Stats:

	- 
	- 

public class Solution {
    private static final char[][] PAIRS = new char[][] {
        {'0', '0'}, {'1', '1'}, {'6', '9'}, {'8', '8'}, {'9', '6'}};

    public int strobogrammaticInRange(String low, String high) {
        if (low == null || high == null || low.length() > high.length()
            || (low.length() == high.length() && low.compareTo(high) > 0)) {
            return 0;
        }

        int count = 0;

        for (int len = low.length(); len <= high.length(); len++) {
            count += dfs(low, high, new char[len], 0, len - 1);
        }

        return count;
    }

    private int dfs(String low, String high, char[] ch, int left, int right) {
        if (left > right) {
            String s = new String(ch);

            // if in char is smaller than low or greater than high, skip, doesn't count.
            if ((ch.length == low.length() && s.compareTo(low) < 0)
                || (ch.length == high.length() && s.compareTo(high) > 0)) {
                return 0;
            } else {
                return 1;
            }
        }

        int count = 0;

        for (char[] p : PAIRS) {
            ch[left] = p[0];
            ch[right] = p[1];

            //don't start with 0
            if (ch.length != 1 && ch[0] == '0') {
                continue; 
            }

             //don't put 6/9 at the middle of string.
            if (left == right && (p[0] == '6' || p[0] == '9')) {
                continue;
            }

            count += dfs(low, high, ch, left + 1, right - 1);
        }
        return count;
    }
}


=======================================================================================================
method 2:

Method:



Stats:

	- 
	- 

public class Solution{


	public int strobogrammaticInRange(String low, String high){
		int count = 0;
		List<String> rst = new ArrayList<String>();
		for(int n = low.length(); n <= high.length(); n++){
			rst.addAll(helper(n, n));
		}
		for(String num : rst){
		    
			if((num.length() == low.length()&&num.compareTo(low) < 0 ) ||(num.length() == high.length() && num.compareTo(high) > 0)) continue;
				count++;
		}
		return count;
	}

	private List<String> helper(int cur, int max){
		if(cur == 0) return new ArrayList<String>(Arrays.asList(""));
		if(cur == 1) return new ArrayList<String>(Arrays.asList("1", "8", "0"));

		List<String> rst = new ArrayList<String>();
		List<String> center = helper(cur - 2, max);

		for(int i = 0; i < center.size(); i++){
			String tmp = center.get(i);
			if(cur != max) rst.add("0" + tmp + "0");
			rst.add("1" + tmp + "1");
			rst.add("6" + tmp + "9");
			rst.add("8" + tmp + "8");
			rst.add("9" + tmp + "6");
		}
		return rst;
	}
}

=======================================================================================================
method 3:

Method:
	Generating the numbers in ascending order so it can be teminated when the number is greater than high.

	Only need to actually generate the numbers having the same length of low and high for range checking. The total nubmers of other lengths can be counted with a dp function.

	Using char arrays and placing char on both ends is faster than using adding String operation. In addtion, comparing char arrays directly should be faster than calling String.compareTo() function.



Stats:

	- 
	- 



 public class Solution {
 char[] singles = new char[]{'0','1','8'};
 // Sorted by the first char in ascending order
 char[][] pairs = new char[][]{ {'0','0'},{'1','1'},{'6','9'},{'8','8'},{'9','6'} };
 char[] ans; // buffer of storing the number
 int count = 0; // total count
 char[] l; // char array of low
 char[] h; // char array of high
 
 // Compare two numbers' char array. Longer one is the larger one.
 // If have same length then compare each char from left to right
 // return positive when s2 > s1, 0 when s2==s1, nagetive when s2 < s1
 int comp(char[] s1, char[] s2) {
     int len1 = s1.length;
     int len2 = s2.length;
     if (len1 != len2) return len2-len1;
     else {
         for (int i=0; i < len1; i++) {
             if (s1[i] != s2[i]) return s2[i]-s1[i];
         }
         return 0;
     }
 }
 // Recursion for generating Strobogrammatic numbers of length n 
 // starting from both ends. As a result, the numbers are 
 // generate in ascending order.
 // Therefore, when when a number is greater than high it returns false 
 // and then terminate the loop. 
 boolean helper(int n, int s, int e, int len) {
     if (n==0) return false;
     if (len==n) { // a resulting number 
         // checking the range
         if ( comp(l, ans) >=0  && comp(ans, h) >=0 ) count++;
         if ( comp(ans, h) < 0) return false; // the nubmer is greater than high
         return true;
     } else if (s==e) { // odd length at the middle position, apply single digit
         for (int i =0 ; i< singles.length; i++) {
             if ( ! ( s == 0 && i == 0 && n > 1)  ) { // first digit can't be 0
                 ans[s] = singles[i];
                 if ( ! helper( n, s+1, e-1, len+1) ) return false;
             }
         }
     } else { // placing two digits at both ends
         for (int i =0 ; i< pairs.length; i++) {
             char[] pair = pairs[i];
             if ( ! ( s == 0 && i == 0)  ) { // first digit can't be 0
                 ans[s] = pair[0];
                 ans[e] = pair[1];
                 if ( ! helper( n, s+1, e-1, len+2 ) ) return false;
             }
         }
     }
     return true;
 }
 // counting the total Strobogrammatic numbers of lengh n 
 // without considering the range
 int counts(int n, int next) {
     if (next<=0) return 0;
     if (next==1) return 3;
     if (next==2) return n==next? 4 :5; // first digit can't be 0
     return n==next? 4 * counts(n, next-2) : 5 * counts(n, next-2);
 }
 
 public int strobogrammaticInRange(String low, String high) {
     int low_len = low.length();
     int high_len = high.length();
     l = low.toCharArray();
     h = high.toCharArray();
     for (int i=low_len; i <= high_len; i++) {
         // generating the numbers only when the length is equal to low or high
         if (i== low_len || i== high_len) {
             ans = new char[i];
             helper(i, 0, i-1, 0);
         } else { 
             // counting the total numbers without acctualy generating them
             count+=counts(i,i);
         }
     }
     return count;
 }
}
