901. Online Stock Span - Medium


Write a class_StockSpanner which collects daily price quotes for some stock, and returns the span 
of that stocks price for the current day.

The span of the stocks price today is defined as the maximum number of consecutive days (starting 
from today and going backwards) for which the price of the stock was less than or equal to today
price.

For example, if the price of a stock over the next 7 days were [100, 80, 60, 70, 60, 75, 85], then the stock spans would be [1, 1, 1, 2, 1, 4, 6].

 

Example 1:

Input: ["StockSpanner","next","next","next","next","next","next","next"], [[],[100],[80],[60],[70],[60],[75],[85]]
Output: [null,1,1,1,2,1,4,6]
Explanation: 
First, S = StockSpanner() is initialized.  Then:
S.next(100) is called and returns 1,
S.next(80) is called and returns 1,
S.next(60) is called and returns 1,
S.next(70) is called and returns 2,
S.next(60) is called and returns 1,
S.next(75) is called and returns 4,
S.next(85) is called and returns 6.

Note that (for example) S.next(75) returned 4, because the last 4 prices
(including today's price of 75) were less than or equal to today's price.
 

Note:

Calls to StockSpanner.next(int price) will have 1 <= price <= 10^5.
There will be at most 10000 calls to StockSpanner.next per test case.
There will be at most 150000 calls to StockSpanner.next across all test cases.
The total time limit for this problem has been reduced by 75% for C++, and 50% for all other languages.


******************************************************
key:
	- stack
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


单调栈（Monotonic Stack）

我们维护一个 单调递减栈（从栈顶到底部：高 → 低）

栈中每个元素保存：(price, span)


新加入一个价格时：

	当栈顶价格 ≤ 当前价格，就弹栈

	弹出的所有元素的 span 累加到当前价格上

	再把当前 (price, span) 入栈

	因为每个元素只会被 push 一次、pop 一次，所以总复杂度：

均摊 O(1) / 每次调用

ex. 栈顶 -> (70, 1) (80, 1) (100, 1)
当前输入 price = 75：
	75 > 70 → pop

	75 < 80 → stop

	最后新 push (75,2)，span = 自己 1 + 被弹的 1


复杂度分析

每个元素最多 push 一次、pop 一次, 均摊 O(1)（好面试官会问这个，必须提）空间复杂度：O(n)


因为每条记录弹出后永远不会再入栈：每个数据只会被处理两次（入栈、出栈），因此均摊 O(1)。


class StockSpanner {
    // 每个元素是 {price, span}
    Stack<int[]> stack;

    public StockSpanner() {
        stack = new Stack<>();
    }

    public int next(int price) {
        int span = 1;

        // 弹出所有 <= 当前价格的记录
        while (!stack.isEmpty() && stack.peek()[0] <= price) {
            span += stack.pop()[1];
        }

        // 入栈
        stack.push(new int[]{price, span});
        return span;
    }
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

class StockSpanner(object):
    def __init__(self):
        self.stack = []

    def next(self, price):
        weight = 1
        while self.stack and self.stack[-1][0] <= price:
            weight += self.stack.pop()[1]
        self.stack.append((price, weight))
        return weight
=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	










~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

