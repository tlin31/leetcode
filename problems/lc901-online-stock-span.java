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


Stats:

	- 
	- 


Method:

	- In a typical case, we get a new element like 7, and there are some previous elements like 11, 
	  3, 9, 5, 6, 4. Lets try to create some relationship between this query and the next query.

	- If (after getting 7) we get an element like 2, then the answer is 1. So in general, whenever 
	  we get a smaller element, the answer is 1.

	  If we get an element like 8, the answer is 1 plus the previous answer (for 7), as the 8 "stops" 
	  on the same value that 7 does (namely, 9).

	- If we get an element like 10, the answer is 1 plus the previous answer, plus the answer for 9.

	- Notice throughout this evaluation, we only care about elements that occur in increasing order - 
	  we "shortcut" to them. That is, from adding an element like 10, we cut to 7 [with "weight" 4], 
	  then to 9 [with weight 2], then cut to 11 [with weight 1].


Algorithm

	- Lets maintain a weighted stack of decreasing elements. The size of the weight will be the 
	  total number of elements skipped. For example, 11, 3, 9, 5, 6, 4, 7 will be (11, weight=1), 
	  (9, weight=2), (7, weight=4).

	- When we get a new element like 10, this helps us count the previous values faster by popping 
	  weighted elements off the stack. The new stack at the end will look like (11, weight=1), 
	  (10, weight=7).



class StockSpanner {
    Stack<Integer> prices, weights;

    public StockSpanner() {
        prices = new Stack();
        weights = new Stack();
    }

    public int next(int price) {
        int w = 1;
        while (!prices.isEmpty() && prices.peek() <= price) {
            prices.pop();
            w += weights.pop();
        }

        prices.push(price);
        weights.push(w);
        return w;
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

