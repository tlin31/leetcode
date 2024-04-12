264. Ugly Number II - Medium

Write a program to find the n-th ugly number.

Ugly numbers are positive numbers whose prime factors only include 2, 3, 5. 

Example:

Input: n = 10
Output: 12
Explanation: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 is the sequence of the first 10 ugly numbers.
Note:  

1 is typically treated as an ugly number.
n does not exceed 1690.

******************************************************
key:
	- stack
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- 假设我们现在已经有了一个丑数的有序数组，如果要找到下一个丑数，则可以将数组中的每一个数乘以2，并将其中第一个大于
		当前丑数的的结果记为M2，同样将当前有序数组每一个数都乘以3，第一个大于当前丑数的的结果记为M3，同样方式得到乘以
		5的第一个大于当前丑数的结果记为M5。可以下一个丑数必然是min(M2, M3, M5)。
	- 事实上我们并不必要将数组中的每个数都乘以2,3,5。对于乘以2来说，我们只要找到第一个乘以2大于当前丑数的数在数组中的
		位置，同样找到第一个乘以3,5大于当前丑数的数的位置。如果当前丑数记为M，然后就可以使用min(M*2, M*3, M*5)来产
		生下一个丑数。
	- dp


stats:

	- 
	- 


public int nthUglyNumber(int n) {
    if(n<=0)
        return 0;
 
    ArrayList<Integer> list = new ArrayList<Integer>();
    list.add(1);
 
    int i=0;
    int j=0;
    int k=0;
 
    while(list.size()<n){
        int m2 = list.get(i)*2;
        int m3 = list.get(j)*3;
        int m5 = list.get(k)*5;
 
        int min = Math.min(m2, Math.min(m3, m5));
        list.add(min);
 
        if(min==m2)
            i++;
 
        if(min==m3)
            j++;
 
        if(min==m5)
            k++;
    }
 
    return list.get(list.size()-1);
}

=======================================================================================================
method 2:

method:

	- TreeSet, so do not need to worry about duplicates. 
	- 

stats:

	- 
	- 


public class Solution {
    public int nthUglyNumber(int n) {
        TreeSet<Long> ans = new TreeSet<>();
        ans.add(1L);
        for (int i = 0; i < n - 1; ++i) {
            long first = ans.pollFirst();
            ans.add(first * 2);
            ans.add(first * 3);
            ans.add(first * 5);
        }
        return ans.first().intValue();
    }
}


=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



