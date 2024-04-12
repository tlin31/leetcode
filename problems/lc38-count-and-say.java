38. Count and Say - easy

The count-and-say sequence is the sequence of integers beginning as follows:
1, 11, 21, 1211, 111221, ...
1 is read off as "one 1" or 11 .
11 is read off as "two 1s" or 21 .
21 is read off as "one 2 , then one 1" or 1211 .

Given an integer n, generate the n-th sequence.
Note: The sequence of integers will be represented as a string.



******************************************************
key:
	- 
	- edge case:
		1) empty string --> return 0
		2) 

******************************************************



=======================================================================================================
method 1:

method:

	- recursion
	- 只要知道了 n - 1 行，就可以写出第 n 行了，首先想到的就是递归。
	  第五行是 111221，求第六行的话，我们只需要知道每个字符重复的次数加上当前字符就行啦。
	  1 重复 3 次，就是 31，2 重复 2 次就是 22，1 重复 1 次 就是 11，所以最终结果就是 312211。

stats:

	- Runtime: 7 ms, faster than 25.49% 
	- Memory Usage: 37.7 MB, less than 5.26%
	- 空间复杂度：O（1）。


	public String countAndSay(int n) {
	    //第一行就直接输出
	    if (n == 1) {
	        return "1";
	    }
	    //得到上一行的字符串
	    String last = countAndSay(n - 1);
	    //输出当前行的字符串
	    return getNextString(last);

	}

	private String getNextString(String last) {
	    //长度为 0 就返回空字符串
	    if (last.length() == 0) {
	        return "";
	    }
	    //得到第 1 个字符重复的次数
	    int num = getRepeatNum(last);
	    // 次数 + 当前字符 + 其余的字符串的情况
	    return num + "" + last.charAt(0) + getNextString(last.substring(num));
	}

	//得到字符 string[0] 的重复个数，例如 "111221" 返回 3
	private int getRepeatNum(String string) {
	    int count = 1;
	    char same = string.charAt(0);
	    for (int i = 1; i < string.length(); i++) {
	        if (same == string.charAt(i)) {
	            count++;
	        } else {
	            break;
	        }
	    }
	    return count;
	}




=======================================================================================================
method 2:

method:

	- iterative
	- 

stats:

	- 
	- 空间复杂度：O（1）。


	public String countAndSay(int n) {
	    String res = "1";
	    //从第一行开始，一行一行产生
	    while (n > 1) {
	        String temp = "";
	        for (int i = 0; i < res.length(); i++) {
	            int num = getRepeatNum(res.substring(i));
	            temp = temp + num + "" + res.charAt(i);
	            //跳过重复的字符
	            i = i + num - 1;
	        }
	        n--;
	        //更新
	        res = temp;
	    }
	    return res;

	}
	//得到字符 string[0] 的重复个数，例如 "111221" 返回 3
	private int getRepeatNum(String string) {
	    int count = 1;
	    char same = string.charAt(0);
	    for (int i = 1; i < string.length(); i++) {
	        if (same == string.charAt(i)) {
	            count++;
	        } else {
	            break;
	        }
	    }
	    return count;
	}

=======================================================================================================
method 3:

method:

	- recursion
	- 

stats:

	- Runtime: 1 ms, faster than 99.29% of Java online submissions for Count and Say.
	- Memory Usage: 34.2 MB, less than 100.00% 

public String countAndSay(int n) {

		// why??
        if(n == 1) return "1"; 
        String prev = countAndSay(n - 1); 
        StringBuilder str = new StringBuilder();
        int i = 0;
        while(i < prev.length()) {
            char curr = prev.charAt(i);
            int j = 0;
            //for case of 111, j = 3 --> need to output three one --> 31
            while(i+j < prev.length() && prev.charAt(i+j) == curr) j++;
            str.append(j);
            str.append(curr);
            i += j;
        }
        return str.toString();
    }













