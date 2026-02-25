1169. Invalid Transactions - Medium


A transaction is possibly invalid if:

the amount exceeds $1000, or;
if it occurs within (and including) 60 minutes of another transaction with the same name in a different city.
You are given an array of strings transaction where transactions[i] consists of comma-separated values representing the name, time (in minutes), amount, and city of the transaction.

Return a list of transactions that are possibly invalid. You may return the answer in any order.

 

Example 1:

Input: transactions = ["alice,20,800,mtv","alice,50,100,beijing"]
Output: ["alice,20,800,mtv","alice,50,100,beijing"]
Explanation: The first transaction is invalid because the second transaction occurs within a difference of 60 minutes, have the same name and is in a different city. Similarly the second one is invalid too.
Example 2:

Input: transactions = ["alice,20,800,mtv","alice,50,1200,mtv"]
Output: ["alice,50,1200,mtv"]
Example 3:

Input: transactions = ["alice,20,800,mtv","bob,50,1200,mtv"]
Output: ["bob,50,1200,mtv"]
 

Constraints:

transactions.length <= 1000
Each transactions[i] takes the form "{name},{time},{amount},{city}"
Each {name} and {city} consist of lowercase English letters, and have lengths between 1 and 10.
Each {time} consist of digits, and represent an integer between 0 and 1000.
Each {amount} consist of digits, and represent an integer between 0 and 2000.


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

	-	



Stats:

	- 
	- 
// 步骤 (Steps)：
// 对象化 (Object Mapping)：将原始字符串解析为 Transaction 对象，包含 name, time, amount, city 和原始索引 id。
// 分类存储 (Grouping)：使用 Map<String, List<Transaction>> 按用户名 (Name) 对交易进行分组。这能将对比范围从全量 O（n^2）缩小到同名用户的  O（k^2）
// 判定逻辑 (Judgment Logic)：
// 遍历每个交易，如果金额 >1000 ，标记为无效。
// 在同名用户的交易列表中，两两比对。如果 Math.abs(t1.time - t2.time) <= 60 且 !t1.city.equals(t2.city)，则两笔交易均标记为无效。
// 去重输出 (Deduplication)：由于一笔交易可能因为多个原因无效，使用 boolean[] 数组通过索引记录无效状态，最后统一输出。
class Solution {
class Transaction {
    String name, city, raw;
    int time, amount, id;

    Transaction(String csv, int id) {
        String[] parts = csv.split(",");
        this.name = parts[0];
        this.time = Integer.parseInt(parts[1]);
        this.amount = Integer.parseInt(parts[2]);
        this.city = parts[3];
        this.raw = csv;
        this.id = id;
    }
}

public List<String> invalidTransactions(String[] transactions) {
    int n = transactions.length;
    Transaction[] trans = new Transaction[n];
    Map<String, List<Transaction>> map = new HashMap<>();
    boolean[] isInvalid = new boolean[n];

    for (int i = 0; i < n; i++) {
        trans[i] = new Transaction(transactions[i], i);
        map.computeIfAbsent(trans[i].name, k -> new ArrayList<>()).add(trans[i]);
    }

    for (Transaction curr : trans) {
        // 条件 1: 金额过大 (Amount > 1000)
        if (curr.amount > 1000) isInvalid[curr.id] = true;
        
        // 条件 2: 城市冲突 (City conflict within 60 mins)
        List<Transaction> others = map.get(curr.name);
        for (Transaction other : others) {
            if (!curr.city.equals(other.city) && Math.abs(curr.time - other.time) <= 60) {
                isInvalid[curr.id] = true;
                break; // 只要找到一个冲突即可
            }
        }
    }

    List<String> res = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        if (isInvalid[i]) res.add(transactions[i]);
    }
    return res;
}

}


3. 业界最佳实践 (Best Practices)
解耦与解析 (Decoupling & Parsing)：
	不要直接处理 CSV 字符串。在微服务中，应先通过 DTO (Data Transfer Object) 映射数据，
	确保代码的可读性 (Readability)。

防御性编程 (Defensive Programming)：
	在解析字符串时，务必处理 NumberFormatException。在实际电商后端，非法输入（如金额处填了字母）是
	导致系统崩溃的常见原因。

分布式追踪 (Distributed Tracing)：
	如果这笔交易跨越了多个微服务，应在 Header 中带上 traceId，以便在检测
	到无效交易时，能回溯整个调用链路。

滑动窗口优化 (Sliding Window)：
	如果单用户的交易量极大，可以对该用户的 List 按时间排序，使用双指针 (Two Pointers) 维护一个 60 分钟的
	窗口，将比对效率从 O(k^2)提升至 O(k logk)







