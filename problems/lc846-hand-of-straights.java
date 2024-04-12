846. Hand of Straights - Medium

Alice has a hand of cards, given as an array of integers.

Now she wants to rearrange the cards into groups so that each group is EXACTLY size W, and consists of 
W consecutive cards.

Return true if and only if she can.

 

Example 1:
Input: hand = [1,2,3,6,2,3,4,7,8], W = 3
Output: true
Explanation: Alices hand can be rearranged as [1,2,3],[2,3,4],[6,7,8].


Example 2:
Input: hand = [1,2,3,4,5], W = 4
Output: false
Explanation: Alice's hand can't be rearranged into groups of 4.
 

Note:

1 <= hand.length <= 10000
0 <= hand[i] <= 10^9
1 <= W <= hand.length

******************************************************
key:
	- treemap, remove one by one
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:


	- Count number of different cards to a map c
		Loop from the smallest card number.
		Everytime we meet a new card i, we cut off i - i + W - 1 from the counter.
	- early fail --> check at the end of the hand

stats:

	- O(MlogM + MW), where M is the number of different cards.
	- 


  public boolean isNStraightHand(int[] hand, int W) {
        Map<Integer, Integer> c = new TreeMap<>();
        for (int i : hand) 
        	c.put(i, c.getOrDefault(i, 0)+1);

        for (int it : c.keySet())
            if (c.get(it) > 0) {

                // check from the last one, earlier fail
                for (int i = W - 1; i >= 0; --i) {
                    if (c.getOrDefault(it + i, 0) < c.get(it)) 
                    	return false;
                    c.put(it + i, c.get(it + i) - c.get(it));
                }
            }
        return true;
    }


ex. hand = [1,2,3,6,2,3,4,7,8], W = 3 --> {(1,1), (2,2), (3,2) (4,1) (6,1)(7,1)(8,1)}

1st: (1,1) --> change to (3,1) & (2,1)
2nd: (2,1) --> change to (4,0) & (3,0)
3rd: (3,0) skip
4th: (4,0) skip
5th: (6,1)

=======================================================================================================
method 2:

method:

	- Brute force
	- We will repeatedly try to form a group (of size W) starting with the lowest card. 
	  This works because the lowest card still in our hand must be the bottom end of a size W straight.
	- 1. keep a count {card: number of copies of card} as a TreeMap (or dict).
	- 2. repeatedly do the following steps: 
			find the lowest value card that has 1 or more copies (say has value x), 
			and try to remove x, x+1, x+2, ..., x+W-1 from our count. 
			If we can not, then the task is impossible.

stats:

	- Time Complexity: O(N * (N/W)) = O(N*log N), where N is the length of hand. The (N/W) factor comes 
	  from min(count). 
	  In Java, the (N/W) factor becomes logN due to the complexity of TreeMap.

	- Space Complexity: O(N)



class Solution {
    public boolean isNStraightHand(int[] hand, int W) {
        TreeMap<Integer, Integer> count = new TreeMap();
        for (int card: hand) {
            count.put(card, count.getOrDefault(card,0) + 1);
        }

        while (count.size() > 0) {
            int first = count.firstKey();
            for (int card = first; card < first + W; ++card) {
                if (!count.containsKey(card)) 
                	return false;
                int c = count.get(card);
                if (c == 1) 
                	count.remove(card);
                else 
                	count.put(card, c - 1);
            }
        }

        return true;
    }
}
=======================================================================================================
method 3 --> follow up: What if W is huge, should we cut off card on by one?

method:

	Count number of different cards to a map c
	Cur represent current open straight groups.
	In a deque 'start', we record the number of opened a straight group.
	
	Loop from the smallest card number.
	For example, hand = [1,2,3,2,3,4], W = 3
	We meet one 1:
		opened = 0, we open a new straight groups starting at 1, push (1,1) to start.
	We meet two 2:
		opened = 1, we need open another straight groups starting at 1, push (2,1) to start.
	We meet two 3:
		opened = 2, it match current opened groups.
		We open one group at 1, now we close it. opened = opened - 1 = 1
	We meet one 4:
		opened = 1, it match current opened groups.
		We open one group at 2, now we close it. opened = opened - 1 = 0

	return if no more open groups.
	

stats:

	- Time Complexity:O(MlogM), where M is the number of different cards, because I count and sort cards.
	- In Cpp and Java it is O(NlogM), which can also be improved.


public boolean isNStraightHand(int[] hand, int W) {
        Map<Integer, Integer> c = new TreeMap<>();
        for (int i : hand) 
        	c.put(i, c.getOrDefault(i, 0)+1);

        Queue<Integer> start = new LinkedList<>();

        int last_checked = -1, opened = 0;

        for (int i : c.keySet()) {
        	// 1. if still has open hands & missing a middle value ex. last_checked = 2, i = 4 
        	// 	  can't make up (2,3,4), there's a gap
        	// 2.opened = need add consecutive, but not enough
            if (opened > 0 && i > last_checked + 1 || opened > c.get(i)) 
            	return false;

            start.add(c.get(i) - opened);
            last_checked = i; 
            opened = c.get(i);

            // remove the first one
            if (start.size() == W) 
            	opened -= start.remove();
        }
        return opened == 0;
    }


