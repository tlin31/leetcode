362. Design Hit Counter - Medium

Design a hit counter which counts the number of hits received in the past 5 minutes 
(i.e., the past 300 seconds).

Your system should accept a timestamp parameter (in seconds granularity), and you may
assume that calls are being made to the system in chronological order (i.e., timestamp is 
monotonically increasing). Several hits may arrive roughly at the same time.

Implement the HitCounter class:

- HitCounter() Initializes the object of the hit counter system.
- void hit(int timestamp) Records a hit that happened at timestamp (in seconds). Several hits 
may happen at the same timestamp.
- int getHits(int timestamp) Returns the number of hits in the past 5 minutes from timestamp 
(i.e., the past 300 seconds).
 

Example 1:

Input
["HitCounter", "hit", "hit", "hit", "getHits", "hit", "getHits", "getHits"]
[[],   [1],  [2], [3],  [4], [300], [300], [301]]
Output
[null, null, null, null, 3, null, 4, 3]

Explanation
HitCounter hitCounter = new HitCounter();
hitCounter.hit(1);       // hit at timestamp 1.
hitCounter.hit(2);       // hit at timestamp 2.
hitCounter.hit(3);       // hit at timestamp 3.
hitCounter.getHits(4);   // get hits at timestamp 4, return 3.
hitCounter.hit(300);     // hit at timestamp 300.
hitCounter.getHits(300); // get hits at timestamp 300, return 4.
hitCounter.getHits(301); // get hits at timestamp 301, return 3.
 

Constraints:

1 <= timestamp <= 2 * 109
At most 300 calls will be made to hit and getHits.
 

Follow up: What if the number of hits per second could be huge? Does your design scale?


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

	-	O(s) s is total seconds in given time interval, in this case 300.
basic ideal is using buckets. 
1 bucket for every second because we only need to keep the recent hits info for 300 seconds. 
hit[] array is wrapped around by mod operation. Each hit bucket is associated with times[] 
bucket which record current time. If it is not current time, it means it is 300s or 600s... 
ago and need to reset to 1.



Stats:

	- 
	- 


int array store hit timestamp ex[1秒，10秒，13秒。。。]
map （1秒，count1 & 10秒， count 2 & 13秒 count 3）
for getHits, 从array中binary search找到离 timestamp-300最近的左边，然后从map中找到当时已有的count，
用现在的减去当时的


public class HitCounter {
    private int[] times;
    private int[] hits;
    /** Initialize your data structure here. */
    public HitCounter() {
        times = new int[300];
        hits = new int[300];
    }
    
    /** Record a hit.
        @param timestamp - The current timestamp (in seconds granularity). */

    public void hit(int timestamp) {
        int index = timestamp % 300;
        //新的一轮300s了，reset
        if (times[index] != timestamp) {
            times[index] = timestamp;
            hits[index] = 1;
        } else {
            hits[index]++;
        }
    }
    
    /** Return the number of hits in the past 5 minutes.
        @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        int total = 0;
        for (int i = 0; i < 300; i++) {
            if (timestamp - times[i] < 300) {
                total += hits[i];
            }
        }
        return total;
    }
}

===================================================================================================
follow up : large scale

class HitCounter {
    LinkedList<Integer> queueTimestamp = new LinkedList<>();
    HashMap<Integer, Integer> freq = new HashMap<>();
    int hitCount = 0;

    /** Initialize your data structure here. */
    public HitCounter() {

    }

    /** Record a hit.
     @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        if (!queueTimestamp.isEmpty() && queueTimestamp.peekLast() == timestamp) {
            freq.put(timestamp, freq.get(timestamp) + 1);
        } else {
            freq.put(timestamp, 1);
            queueTimestamp.addLast(timestamp);
        }
        hitCount++;
        rollOutOldData(timestamp);
    }

    /** Return the number of hits in the past 5 minutes.
     @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        rollOutOldData(timestamp);
        return hitCount;
    }
    
    void rollOutOldData(int timestamp) {
        while (!queueTimestamp.isEmpty() && timestamp - queueTimestamp.peek() + 1 > 300) {
            int victim = queueTimestamp.poll();
            hitCount -= freq.get(victim);
            freq.remove(victim);
        }
    }
}

===================================================================================================
follow up : large scale + multi thread

🚀 多线程高性能版本

对于高并发、大输入场景（比如高频日志系统、点击流统计），我们希望：

1. 多个线程可以同时 hit()；

2. 只有在清理过期数据时才有少量竞争；

3. getHits() 不会阻塞 hit() 操作。

为此，我们可以使用：

ConcurrentLinkedQueue：线程安全的无锁队列；

AtomicInteger：记录当前命中次数；

无需 synchronized，实现更细粒度的并发控制。


方面          说明
线程安全性     ConcurrentLinkedQueue 是无锁队列，内部用 CAS (Compare-And-Swap) 实现并发入队/出队操作。
计数一致性     AtomicInteger 通过 CAS 保证原子递增、递减，不会出现计数错误。
性能优势       hit() 操作只入队，不阻塞；cleanUp() 只会在窗口外命中较多时运行，性能稳定。
无锁设计       全程无 synchronized 锁，所有操作几乎都是 lock-free 的。


import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentHitCounter {
    private final ConcurrentLinkedQueue<Integer> hits = new ConcurrentLinkedQueue<>();
    private final AtomicInteger count = new AtomicInteger(0);
    private static final int WINDOW = 300; // 5分钟窗口

    // 记录一次命中
    public void hit(int timestamp) {
        hits.offer(timestamp);
        count.incrementAndGet();
        cleanUp(timestamp);
    }

    // 获取过去5分钟的命中次数
    public int getHits(int timestamp) {
        cleanUp(timestamp);
        return count.get();
    }

    // 清理过期的命中
    private void cleanUp(int timestamp) {
        while (!hits.isEmpty()) {
            Integer t = hits.peek();
            if (timestamp - t >= WINDOW) {
                hits.poll();
                count.decrementAndGet();
            } else {
                break;
            }
        }
    }
}

对于超大规模系统（例如上百万 QPS）：

    可以使用 分片锁（Striped Lock） 或 分桶计数法（Bucket Counting）：

将 300 秒分成 300 个桶；

    每个桶记录当前秒的命中次数；

    命中时只更新当前桶；

    查询时只遍历 300 个桶求和（常数时间）。

可以用 ScheduledExecutorService 周期性清理过期数据，而不是每次 hit 都清理。



