50. Pow(x, n) - Medium

Implement pow(x, n), which calculates x raised to the power n (xn).

Example 1:
Input: 2.00000, 10
Output: 1024.00000

Example 2:
Input: 2.10000, 3
Output: 9.26100

Example 3:
Input: 2.00000, -2
Output: 0.25000
Explanation: 2-2 = 1/22 = 1/4 = 0.25
Note:

-100.0 < x < 100.0
n is a 32-bit signed integer, within the range [−231, 231 − 1]

******************************************************
key:
	- use recursion, with everytime power/2, base case will be num * num
	- edge case:
		1) if power < 0, then let original num = 1/num
		2) if power = 0, return 1
		3) if power = 1, return num
		3.5) if power = -1, return num
		4) if num = 1, regardless of power, always return 1
		5) if num = -1, always return -1 or 1 based on power
		6) 问题出在 n = - n  上，因为最小负数 	     
	     	$$-2^{31}$$取相反数的话，按照计算机的规则，依旧是$$-2^{31}$$，所以这种情况需要单独讨论一下。

******************************************************



=======================================================================================================
method 1:

method:

	- 暴力，每次都乘 it self
	- 

stats:

	- 时间复杂度：O（n）。
	- 空间复杂度：O（1）。
	- Runtime: 1 ms, faster than 38.41% of Java online submissions for Pow(x, n).
	- Memory Usage: 33.4 MB, less than 5.88% of Java online submissions for Pow(x, n).


	public double myPow(double x, int n) {

		//-1 和 1 也需要单独讨论下，因为他们的任意次方都是 1 或者 -1 。
	    if (x == -1) {
	        if ((n & 1) != 0) {
	            return -1;
	        } else {
	            return 1;
	        }
	    }
	    if (x == 1.0)
	        return 1;

	     // 问题出在 n = - n  上，因为最小负数 	     
	     // $$-2^{31}$$取相反数的话，按照计算机的规则，依旧是$$-2^{31}$$，所以这种情况需要单独讨论一下。
	    if (n == -2147483648) {
	        return 0;
	    }

	    double mul = 1;
	    if (n > 0) {
	        for (int i = 0; i < n; i++) {
	            mul *= x;
	        }
	    } else {
	        n = -n;
	        for (int i = 0; i < n; i++) {
	            mul *= x;
	        }
	        mul = 1 / mul;
	    }
	    return mul;
	}



=======================================================================================================
method 2:

method:

	- recursion
	- improve from method 1, since we use previous results.
	- 乘法的话，我们不用一次一次的相乘，得到 2 次方后，我们可以直接把 2 次方的结果相乘，就可以得到 4 次方，
		得到 4 次方的结果再相乘，就是 8 次方了，这样的话就会快很多了。
	- if power = odd, x^n=x^{n/2} * x^{n/2} * x
	  if power = even, x^n=x^{n/2} * x^{n/2}

stats:

	- 时间复杂度：O（log（n）)。
	- 

	public double powRecursion(double x, int n) {
	    if (n == 0) {
	        return 1;
	    }
	    //偶数的情况
	    if ((n & 1) == 0) { 
	        double temp = powRecursion(x, n / 2);
	        return temp * temp;
	    } else { //奇数的情况
	        double temp = powRecursion(x, n / 2);
	        return temp * temp * x;
	    }
	}

	public double myPow(double x, int n) {
	    if (x == -1) {
	        if ((n & 1) != 0) {
	            return -1;
	        } else {
	            return 1;
	        }
	    }
	    if (x == 1.0f)
	        return 1;

	    if (n == -2147483648) {
	        return 0;
	    }
	    double mul = 1;
	    if (n > 0) {
	        mul = powRecursion(x, n);
	    } else {
	        n = -n;
	        mul = powRecursion(x, n);
	        mul = 1 / mul;
	    }
	    return mul;
	}

method:

	- recursion -> another type
	- if power = odd, x^n=(x*x)^{n/2}*x
	  if power = even, x^n=(x*x)^{n/2}

stats:

	- 时间复杂度：O（log（n）)。
	- 

public double powRecursion(double x, int n) {
    if (n == 0) {
        return 1;
    }
    //偶数的情况
    if ((n & 1) == 0) { 
        return powRecursion(x * x, n / 2);
    } else { //奇数的情况 
        return powRecursion(x * x, n / 2) * x;
    }
}

=======================================================================================================
method 3:

method:

	- use bitwise operators to check for an odd number (m & 1) and to floor-divide by 2 (m >>= 1).

	- Shifting all the bits 1 to the right (m >>= 1) has the effect of cutting an integer in half 
		and dropping the remainder. The bitwise and (m & 1) basically compares the last bit in m 
		with the single bit in 1, and if they r both 1 then it evaluates to 1 (otherwise it evaluates 
		to 0). So odd m will evaluate to 1 (or True), and even m will evaluate to 0 (or False).

stats:

	- O(log n)
	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Pow(x, n).
	- Memory Usage: 33.5 MB, less than 5.88% of Java

public double myPow(double x, int n) {
	// m = power
    long m = n > 0 ? n : -(long)n;
    double ans = 1.0;
    while (m != 0) {
    	//if m is odd, let it multiply x again
        if ((m & 1) == 1)
            ans *= x;

        x *= x;
        
        //m/2
        m >>= 1;
    }
    return n >= 0 ? ans : 1 / ans;
}


