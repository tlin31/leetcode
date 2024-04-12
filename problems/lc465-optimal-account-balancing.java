465. Optimal Account Balancing - Hard

A group of friends went on holiday and sometimes lent each other money. For example, Alice paid for
Bill lunch for $10. Then later Chris gave Alice $5 for a taxi ride. 

We can model each transaction as a tuple (x, y, z) which means person x gave person y $z. 
Assuming Alice, Bill, and Chris are person 0, 1, and 2 respectively (0, 1, 2 are the person ID), the
transactions can be represented as [[0, 1, 10], [2, 0, 5]].

Given a list of transactions between a group of people, return the minimum number of transactions required to settle the debt.

Note:

	1. A transaction will be given as a tuple (x, y, z). Note that x ≠ y and z > 0.
	2. Person IDs may not be linear, e.g. we could have the persons 0, 1, 2 or we could also have 
	   the persons 0, 2, 6.

Example 1:
Input:
[[0,1,10], [2,0,5]]

Output:
2

Explanation:
Person #0 gave person #1 $10.
Person #2 gave person #0 $5.

Two transactions are needed. One way to settle the debt is person #1 pays person #0 and #2 $5 each.
Example 2:

Input:
[[0,1,10], [1,0,1], [1,2,5], [2,0,5]]

Output:
1

Explanation:
Person #0 gave person #1 $10.
Person #1 gave person #0 $1.
Person #1 gave person #2 $5.
Person #2 gave person #0 $5.

Therefore, person #1 only need to give person #0 $4, and all debt is settled.


******************************************************
key:
	- dfs, greedy
	- edge case:
		1) The question can be transferred to a 3-partition problem, which is NP-hard. Ex. balance: 
		   [200, 100, 1, -50, -50, -50, -50, -101], Greedy will give us transaction of 7, while the
		   actual good transaction number is 6.

		   If we have m people with deficit and n people with surplus then the upper bound of the 
		   total necessary transactions is m+n-1. Whenever we find a surplus balance equal to the 
		   sum of several deficit balances, we can reduce the upper bound by 1. 
		   But it is impossible to find the maximum number of surplus balances that can be decomposed
		   into the sum of several disjoint sets of deficit balances. 
		   This is a NPC problem



		2)

******************************************************


=======================================================================================================
method 0:

method:
	- 这里使用一个 HashMap 来建立每个人和其账户的映射，其中账户若为正数，说明其他人欠你钱；如果账户为负数，
	  说明你欠别人钱。
	- 对于每份账单，前面的人就在 HashMap 中减去钱数，后面的人在哈希表中加上钱数。这样每个人就都有一个账户了，接下来
	  合并账户，看最少需要多少次汇款。先统计出账户值不为0的人数，因为如果为0了，表明你既不欠别人钱，别人也不欠你钱，
	  如果不为0，把钱数放入一个数组 accnt 中，然后调用递归函数。
	- 在递归函数中，首先跳过为0的账户，然后看若此时 start 已经是 accnt 数组的长度了，说明所有的账户已经检测完了，
	  用 cnt 来更新结果 res。否则就开始遍历之后的账户，如果当前账户和之前账户的钱数正负不同的话，将前一个账户的钱数
	  加到当前账户上，这很好理解，比如前一个账户钱数是 -5，表示张三欠了别人5块钱，当前账户钱数是5，表示某人欠了李四5
	  块钱，那么张三给李四5块，这两人的账户就都清零了。
	- 然后调用递归函数，此时从当前改变过的账户开始找，cnt 表示当前的转账数，需要加1，后面别忘了复原当前账户的值，
	  典型的递归



public int minTransfers(int[][] transactions) {
    // 1. build net
    Map<Integer, Integer> netMap = new HashMap<>();
    for(int[] item: transactions) {
        int from = item[0];
        int to = item[1];
        int amount = item[2];
        
        netMap.put(from, netMap.getOrDefault(from, 0) + amount);
        netMap.put(to, netMap.getOrDefault(to, 0) - amount);
    }
    
    int[] netlist = new int[netMap.size()];
    int i = 0;
    for(Map.Entry<Integer, Integer> entry: netMap.entrySet()) {
        netlist[i++] = entry.getValue();
    }
    
    return dfs(netlist, 0);
}

int dfs(int[] netlist, int from) {

	// skip already balanced accounts
    while(from < netlist.length && netlist[from] == 0) {
        from++;
    }

    // none unbalanced account left, done!
    if(from == netlist.length) {
        return 0;
    }
    
    int min = Integer.MAX_VALUE;

    for(int i = from+1; i < netlist.length; ++i) {
        if((netlist[from] * netlist[i]) < 0) {
            netlist[i] += netlist[from];
            min = Math.min(min, 1 + dfs(netlist, from+1));
            netlist[i] -= netlist[from];
        }
    }
    return min;
}

=======================================================================================================
method 1:

method:

	- 	Q: what does it mean to settle the debt?  
	  	A: nobody owes others

	  	Q: how do we represent how much money a person owes others?
	  	A: build current debt situation debt[], e.g. debt[i] = 10 means a person owes others 10

		Q: how do we settle ones debt
		A: assuming [0, curId - 1] has been settled, for debt[curId], any curId + 1 <= i <debt.length
		   such that debt[i] * debt[curId] < 0 can settle it

	- state: The next account to balance, curId, can uniquely identify a state
	  state function:
		state(debt[], curId) is the min transactions to balance debts[curId...debtLength - 1] such 
		that debts[0...curId-1] are balanced.
	  goal state
		state(initial debt[], 0)
	  state transition
		now: state(debt[], curId)
		next: state (debt[] after balance curId, curId + 1)

			state(debt[], curId) = 1 + min(state (debt[] after balance curId, curId + 1))

	Q: How do we decide who can balance the account of curId?
	A: There are many people who can balance curIds account -- person i behind curId with 
	   debt[i] * debt[curId] < 0.
	
Optimize:
	- 1. the 0 balance account will be removed first
	- 2. Any two accounts have opposite balance will be removed and will add one more transaction


stats:
	- O(n!)
	- 


class Solution {
     public int minTransfers(int[][] transactions) {
        int[] debt = buildDebtArray(transactions); 
        Arrays.sort(debt);
        int l=0, 
        	r=debt.length-1, 
        	n=0,
        	ans=0;

        Set<Integer> set = new HashSet<>();
        while(l<r){
            if(debt[l]+debt[r]==0){
                ans++;
                set.add(l); 
                set.add(r);
                l++;
                r--;
            } else if(debt[l]+debt[r]<0) {
            	l++;
            } else 
            	r--;
        }

        int[] target = new int[debt.length - set.size()];
        int index = 0;
        for(int i=0;i < debt.length; i++){
            if(!set.contains(i)) 
            	target[index++] = debt[i];
        }

        return ans + dfs(0, target);
    }
    
	private int dfs(int curId, int[] debt) {
		// skip the already balanced account.
        while (curId < debt.length && debt[curId] == 0) 
        	curId++;      
        
        // Base case, already finished all the accounts
        if (curId == debt.length)   
            return 0;

        // case 3, Recursive case. 
        // try to find a account whose balance is opposite from current account.
        // then it transfers all the money in current account to the account which has opposite balance. 
		// to balance current account & decrease total unbalanced account
		// ex. current balance is 10, you find a account has -5, then you transfer this 10 to the 
		//     other account, current is 0 and the other will have 5, current balance is -5, and you 
		//     find a account has 10, then you transfer -5 to the other account, current is 0 and the 
		// 	   other will have 5, in both cases, the un-balanced accounts will decreased by one. and 
		//     eventually, all accounts will be balanced.
		// Thus, we need to find unbalance accound with x * y < 0 --> if (debt[i] * debt[curId] < 0) 
        int min = Integer.MAX_VALUE;
        for (int i = curId + 1; i < debt.length; i++) {
            if (debt[i] * debt[curId] < 0) {
                debt[i] += debt[curId];
                min = Math.min(min, 1+dfs(curId + 1, debt));
                debt[i] -= debt[curId];
            }
        }
        
        return min;
    }
    
    // return amount of unsettled amount
    private int[] buildDebtArray(int[][] transactions) {
    	// Map person ID to debt amount.
        Map<Integer, Integer> debtMap = new HashMap<>(); 

        for (int[] transaction : transactions) {
            int giver = transaction[0];
            int taker = transaction[1];
            int amount = transaction[2];
            debtMap.put(giver, debtMap.getOrDefault(giver, 0) + amount);
            debtMap.put(taker, debtMap.getOrDefault(taker, 0) - amount);
        }
        
        // contain only un-settled users
        Map<Integer, Integer> ans = new HashMap<>();
        for(Map.Entry<Integer,Integer> e: debtMap.entrySet()){
            if(e.getValue()==0) 
            	continue;
            ans.put(e.getKey(), e.getValue());
        }
        
        int[] debt = new int[ans.size()];
        int i = 0;
        for (int amount : ans.values()) {
            debt[i++] = amount;
        }
        
        return debt;
    }
}
=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- O(n!)
	- 

public int minTransfers(int[][] transactions) {
    if (transactions == null || transactions.length == 0 || transactions[0].length == 0)
        return 0;
    //calculate delta for each account
    Map<Integer, Integer> accountToDelta = new HashMap<Integer, Integer>();
    for (int[] transaction : transactions) {
        int from = transaction[0];
        int to = transaction[1];
        int val = transaction[2];
        if (!accountToDelta.containsKey(from)) {
            accountToDelta.put(from, 0);
        }
        if (!accountToDelta.containsKey(to)) {
            accountToDelta.put(to, 0);
        }
        accountToDelta.put(from, accountToDelta.get(from) - val);
        accountToDelta.put(to, accountToDelta.get(to) + val);
    }
    List<Integer> deltas = new ArrayList<Integer>();
    for (int delta : accountToDelta.values()) {
        if (delta != 0)
            deltas.add(delta);
    }
    //since minTransStartsFrom is slow, we can remove matched deltas to optimize it
    //for example, if account A has balance 5 and account B has balance -5, we know
    //that one transaction from B to A is optimal.
    int matchCount = removeMatchDeltas(deltas);
    //try out all possibilities to get minimum number of transactions
    return matchCount + minTransStartsFrom(deltas, 0);
}

private int removeMatchDeltas(List<Integer> deltas) {
    Collections.sort(deltas);
    int left = 0;
    int right = deltas.size() - 1;
    int matchCount = 0;
    while (left < right) {
        if (-1 * deltas.get(left) == deltas.get(right)) {
            deltas.remove(left);
            deltas.remove(right - 1);
            right -= 2;
            matchCount++;
        } else if (-1 * deltas.get(left) > deltas.get(right)) {
            left++;
        } else {
            right--;
        }
    }
    return matchCount;
}

private int minTransStartsFrom(List<Integer> deltas, int start) {
    int result = Integer.MAX_VALUE;
    int n = deltas.size();
    while (start < n && deltas.get(start) == 0)
        start++;
    if (start == n)
        return 0;
    for (int i = start + 1; i < n; i++) {
        if ((long) deltas.get(i) * deltas.get(start) < 0) {
            deltas.set(i, deltas.get(i) + deltas.get(start));
            result = Math.min(result, 1 + minTransStartsFrom(deltas, start + 1));
            deltas.set(i, deltas.get(i) - deltas.get(start));
    }
    }
    return result;
}

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



