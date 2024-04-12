638. Shopping Offers - Medium

In LeetCode Store, there are some kinds of items to sell. Each item has a price.

However, there are some special offers, and a special offer consists of one or more different kinds 
of items with a sale price.


You are given the each items price, a set of special offers, and the number we need to buy for each
item. The job is to output the lowest price you have to pay for exactly certain items as given, where
you could make optimal use of the special offers.


Each special offer is represented in the form of an array, the last number represents the price you 
need to pay for this special offer, other numbers represents how many specific items you could get 
if you buy this offer.

You could use any of special offers as many times as you want.

Example 1:
Input: [2,5], [[3,0,5],[1,2,10]], [3,2]
Output: 14
Explanation: 
There are two kinds of items, A and B. Their prices are $2 and $5 respectively. 
In special offer 1, you can pay $5 for 3A and 0B
In special offer 2, you can pay $10 for 1A and 2B. 
You need to buy 3A and 2B, so you may pay $10 for 1A and 2B (special offer #2), and $4 for 2A.

Example 2:
Input: [2,3,4], [[1,1,0,4],[2,2,1,9]], [1,2,1]
Output: 11
Explanation: 
The price of A is $2, and $3 for B, $4 for C. 
You may pay $4 for 1A and 1B, and $9 for 2A ,2B and 1C. 
You need to buy 1A ,2B and 1C, so you may pay $4 for 1A and 1B (special offer #1), and $3 for 1B, $4 for 1C. 
You cannot add more items, though only $9 for 2A ,2B and 1C.

Note:
There are at most 6 kinds of items, 100 special offers.
For each item, you need to buy at most 6 of them.
You are not allowed to buy more items than you want, even if that would lower the overall price.


******************************************************
key:
	- backtrack / recursion
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:back tracking

Method:

	-  similar to combination sum. In combination sum where each element can be repeated, check each
	   element to see if it can be used (in this case, if the sum doesnt exceed the target). 
	   If so, use it. Repeat this until we get the result.

	-  For this question, we check each promotion offers, if the offer can be used, use it. Repeat 
	   the process and find the minimum result. 
	   In this question, the condition whether one offer can be used is the number of items in the
	   offer doesnt exceed the needed number. Find the minimum among all combinations.

	-	remember to compare if buying directly is cheaper.
	-  Optimization:

		1. 防止backtracking中重复计算，use memo Map<needs, minimum value for this needs>Solution里面的第二个
		2. index, special offer 1, specifal offer 2，防止special offer 2, specifal offer 1的情况再次计算


Stats:

	- 
	- 



class Solution {
    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        HashMap<List<Integer>, Integer> map = new HashMap<>();
        return helper(price, special, needs, 0, map);
    }

    // int cur indicates the index of special offer
    private int helper(List<Integer> price, List<List<Integer>> special, List<Integer> needs, int cur, 
    					HashMap<List<Integer>, Integer> map) 
    {
        if (price == null || price.size() == 0 || needs == null || needs.size() == 0) {
            return 0;
        }
        if (map.containsKey(needs)) {
            return map.get(needs);
        }
        int ans = 0;

        // compute direct purchase price (without using special offers)
        for (int i = 0; i < price.size(); i++) {
            ans += price.get(i) * needs.get(i);
        }

        // check each special size
        for (int j = cur; j < special.size(); j++) {
            List<Integer> item = special.get(j);
            List<Integer> copy = new LinkedList<Integer>(needs);
            int i = 0;
            for (i = 0; i < copy.size(); i++) {

            	// num of special offer > our need, don't use it
                if (copy.get(i) < item.get(i)) {
                    break;
                }

                // use the offer
                copy.set(i, copy.get(i) - item.get(i));
            }

            // if this offer will work for all items (no item has num < special offer)
            if (i == copy.size()) {
                ans = Math.min(ans, item.get(i) + helper(price, special, copy, j, map)); 
            }
        }
        map.put(needs, ans);
        return ans;
    }
}


=======================================================================================================
method 2:

Method:

	-	
	-	


Stats:

	- 
	- 

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



