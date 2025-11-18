2034. Stock Price Fluctuation - Medium

You are given a stream of records about a particular stock. Each record contains a timestamp 
and the corresponding price of the stock at that timestamp.

Unfortunately due to the volatile nature of the stock market, the records do not come in order. 
Even worse, some records may be incorrect. Another record with the same timestamp may appear 
later in the stream correcting the price of the previous wrong record.

Design an algorithm that:

Updates the price of the stock at a particular timestamp, correcting the price from any previous records at the timestamp.
Finds the latest price of the stock based on the current records. The latest price is the price at the latest timestamp recorded.
Finds the maximum price the stock has been based on the current records.
Finds the minimum price the stock has been based on the current records.
Implement the StockPrice class:

StockPrice() Initializes the object with no price records.
void update(int timestamp, int price) Updates the price of the stock at the given timestamp.
int current() Returns the latest price of the stock.
int maximum() Returns the maximum price of the stock.
int minimum() Returns the minimum price of the stock.
 

Example 1:

Input
["StockPrice", "update", "update", "current", "maximum", "update", "maximum", "update", "minimum"]
[[], [1, 10], [2, 5], [], [], [1, 3], [], [4, 2], []]
Output
[null, null, null, 5, 10, null, 5, null, 2]

Explanation
StockPrice stockPrice = new StockPrice();
stockPrice.update(1, 10); // Timestamps are [1] with corresponding prices [10].
stockPrice.update(2, 5);  // Timestamps are [1,2] with corresponding prices [10,5].
stockPrice.current();     // return 5, the latest timestamp is 2 with the price being 5.
stockPrice.maximum();     // return 10, the maximum price is 10 at timestamp 1.
stockPrice.update(1, 3);  // The previous timestamp 1 had the wrong price, so it is updated to 3.
                          // Timestamps are [1,2] with corresponding prices [3,5].
stockPrice.maximum();     // return 5, the maximum price is 5 after the correction.
stockPrice.update(4, 2);  // Timestamps are [1,2,4] with corresponding prices [3,5,2].
stockPrice.minimum();     // return 2, the minimum price is 2 at timestamp 4.
 

Constraints:

1 <= timestamp, price <= 109
At most 105 calls will be made in total to update, current, maximum, and minimum.
current, maximum, and minimum will be called only after update has been called at least once.

******************************************************
key:
    - 
    - edge case:
        1) 
        2)

******************************************************



===================================================================================================
Method 1:

Method: 用 HashMap + TreeMap （推荐）

用 HashMap<Integer,Integer> timePrice 存每个 timestamp 对应的当前价格；
用 TreeMap<Integer,Integer> priceCount（价格 -> 出现次数）来维护价格的多重计数并支持 O(log n) 的取 min/max；

还记录当前 latestTimestamp。

更新逻辑：

    如果 timestamp 已存在，取旧价 old = timePrice.get(ts)：在 priceCount 中将 old 的计数减 1（必要时移除键）；

    将新价加入 timePrice 与 priceCount（计数加 1）；

    如果 ts > latestTimestamp，更新 latestTimestamp。

查询：

    current()：返回 timePrice.get(latestTimestamp)；

    maximum()：priceCount.lastKey()；

    minimum()：priceCount.firstKey()。



Stats: update / current / max / min 均为 O(log n)（TreeMap 操作）。 

import java.util.*;

public class StockPrice {
    private Map<Integer, Integer> timePrice;
    private TreeMap<Integer, Integer> priceCount;
    private int latest;

    public StockPrice() {
        timePrice = new HashMap<>();
        priceCount = new TreeMap<>();
        latest = 0;
    }

    public void update(int timestamp, int price) {
        // 如果已有该时刻，减少旧价格计数
        if (timePrice.containsKey(timestamp)) {
            int old = timePrice.get(timestamp);
            int cnt = priceCount.get(old);
            if (cnt == 1) priceCount.remove(old);
            else priceCount.put(old, cnt - 1);
        }
        // 更新 timestamp -> price
        timePrice.put(timestamp, price);
        // 增加新价格计数
        priceCount.put(price, priceCount.getOrDefault(price, 0) + 1);
        // 更新最新时间
        if (timestamp > latest) latest = timestamp;
    }

    public int current() {
        return timePrice.get(latest);
    }

    public int maximum() {
        return priceCount.lastKey();
    }

    public int minimum() {
        return priceCount.firstKey();
    }
}







方法 B — 用 HashMap + 双堆（最大堆 + 最小堆）懒删除

用 HashMap<Integer,Integer> timePrice 存 timestamp->price；

使用两个堆 maxHeap（price, timestamp）和 minHeap（price, timestamp）存每次 update 的记录；

当历史 timestamp 被更新，堆中旧记录还存在 —— 在查询 max/min 时采用“懒删除”策略：取堆顶，若堆顶
记录的 price != 当前 timePrice.get(ts)（已被覆盖），则将堆顶弹出并继续检查，直到堆顶有效。

优点：堆的 push O(log n)，查询 amortized 较快（弹无效项）。缺点：堆大小随更新累积，可能更高的内存占用；
在频繁覆盖同 timestamp 时要清理很多无效项。

复杂度：update O(log n)，查询 amortized O(log n)（最坏情况下弹很多无效项）。


class StockPrice {

    private Map<Integer, Integer> map;
    private PriorityQueue<int[]> minimum;
    private PriorityQueue<int[]> maximum;
    private int latestTimestamp;

    public StockPrice() {
        map = new HashMap<>();
        minimum = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        maximum = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0]));
        latestTimestamp = -1;
    }
    
    public void update(int timestamp, int price) {
        map.put(timestamp, price);
        minimum.offer(new int[] {price, timestamp});
        maximum.offer(new int[] {price, timestamp});
        latestTimestamp = Math.max(latestTimestamp, timestamp);
    }
    
    public int current() {
        return map.get(latestTimestamp);
    }
    
    public int maximum() {
        while(!maximum.isEmpty()){
            int[] top = maximum.peek();
            int price = top[0];
            int timestamp = top[1];
            if(map.getOrDefault(timestamp, Integer.MIN_VALUE) == price){
                return price;
            }
            else {
                maximum.poll();
            }
        }
        return -1;
    }
    
    public int minimum() {
        while(!minimum.isEmpty()){
            int[] top = minimum.peek();
            int price = top[0];
            int timestamp = top[1];
            if(map.getOrDefault(timestamp, Integer.MAX_VALUE) == price){
                return price;
            }
            else {
                minimum.poll();
            }
        }
        return -1;
    }
}

/**
 * Your StockPrice object will be instantiated and called as such:
 * StockPrice obj = new StockPrice();
 * obj.update(timestamp,price);
 * int param_2 = obj.current();
 * int param_3 = obj.maximum();
 * int param_4 = obj.minimum();
 */
