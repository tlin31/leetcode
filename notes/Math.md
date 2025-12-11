# Math

	- possible combinations: Cnk = n!/(n-k)!*k!

## bit manipulation
- try do XOR for your input data. 
- ex. Single Number II, Single Number III, Maximum XOR of Two Numbers in an Array, Repeated DNA Sequences, Maximum Product of Word Lengths, etc.

### 基础

| 是Bitwise OR

& 是Bitwise AND

^ 是 XOR

~ 是 NOT

<< 左移 (Left Shift)
功能： 将所有位向左移动指定的次数。右侧（低位）空出的位置补 0。
效果： 对于非负数，相当于将原数乘以 2 的 N 次方（* 2^n）。
```java
int a = 3;       // 二进制: 0011
int b = a << 2;  // 结果 b 是 12 (二进制: 1100, 相当于 3 * 2^2)
```

>> 带符号右移 (Signed Right Shift)
功能： 将所有位向右移动指定的次数。左侧（高位）空出的位置会填充原来的符号位（正数补 0，负数补 1），以保持原数的正负号不变。
效果： 相当于将原数除以 2 的 N 次方（/ 2^n），并向下取整（向负无穷方向取整）。
```java
int a = 8;       // 二进制: 1000
int b = a >> 1;  // 结果 b 是 4 (二进制: 0100)

int c = -8;      // 二进制补码: ...111000
int d = c >> 1;  // 结果 d 是 -4 (二进制补码: ...111100)
```

>>> 无符号右移 (Unsigned Right Shift)
功能： 将所有位向右移动指定的次数。左侧（高位）空出的位置一律补 0，无论原数的正负。
注意： 这个运算符将负数转换为正数。Java 中没有 <<<。

```java
int a = -8;      // 二进制补码: 11111111 ... 111000
int b = a >>> 1; // 结果 b 是一个非常大的正数 (01111111 ... 111100)
```

### 常用

1. 设置第k位为 1（set bit）
x |= (1 << k);

2. 设置某一位为 0（clear bit）
x &= ~(1 << k);

3. 翻转某一位（toggle）
x ^= (1 << k);

4. 判断某一位是不是 1
(x & (1 << k)) != 0

5. Get its last set bit
x &= -x;

-x 等于 (~x + 1)，它会 保留 x 的最低位 1，并把它右边（低位）的 0 和左边（高位）的所有位翻转。
当你做 x & -x 时，只有最低位的 1 会同时为 1，其他全部为 0，所以得到“最低位 set bit”。


Set： |=
Clear： &=~
Toggle： ^=
Check： &
Lowest bit： x & -x
Remove lowest bit： x&(x-1)



### 加法

二进制加法其实就是：
sum = a ^ b       // 不带进位的加法
carry = (a & b)   // 产生的进位
但 carry 要左移一位：

carry <<= 1


然后继续累加：

a = sum
b = carry

ex. 67
```java
class Solution {
    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1;
        int carry = 0;

        while (i >= 0 || j >= 0 || carry != 0) {
            int bitA = (i >= 0 ? a.charAt(i--) - '0' : 0);
            int bitB = (j >= 0 ? b.charAt(j--) - '0' : 0);

            // sum of current bit = XOR
            int sum = bitA ^ bitB ^ carry;

            // update carry = majority vote
            carry = (bitA & bitB) | (bitA & carry) | (bitB & carry);

            sb.append(sum);
        }

        return sb.reverse().toString();
    }
}
```


### bitmask 

### How to set the n-th bit?  n_th_bit = 1 << n.

How to compute bitmask for a word? Iterate over the word, letter by letter, compute bit number corresponding to that letter n = (int)ch - (int)'a', 
and add this n-th bit n_th_bit = 1 << n into bitmask bitmask |= n_th_bit.

                m |= 1 << (c - 'a');


## GCD


比如leetcode 149，计算斜率 dy/dx 是 double，容易出现：0.333333 vs 0.333332

所以不能用 double！必须用 最简分数 Greatest Common Divisor, GCD：

    dx = x2 - x1
    dy = y2 - y1
    g = gcd(dx, dy)
    dx /= g
    dy /= g

例子：使用 gcd(dx, dy) 化简：
    dx = 4, dy = 8  
    g = gcd(4, 8) = 4  
    dx = 4 / 4 = 1  
    dy = 8 / 4 = 2  
    最终斜率 = (1, 2)


```java
    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
```



