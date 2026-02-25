1242. Web Crawler Multithreaded
Medium
Topics
conpanies icon
Companies
Given a URL startUrl and an interface HtmlParser, implement a Multi-threaded web crawler to crawl all links that are under the same hostname as startUrl.

Return all URLs obtained by your web crawler in any order.

Your crawler should:

Start from the page: startUrl
Call HtmlParser.getUrls(url) to get all URLs from a webpage of a given URL.
Do not crawl the same link twice.
Explore only the links that are under the same hostname as startUrl.

As shown in the example URL above, the hostname is example.org. For simplicity's sake, you may assume all URLs use HTTP protocol without any port specified. For example, the URLs http://leetcode.com/problems and http://leetcode.com/contest are under the same hostname, while URLs http://example.org/test and http://example.com/abc are not under the same hostname.

The HtmlParser interface is defined as such:

interface HtmlParser {
  // Return a list of all urls from a webpage of given url.
  // This is a blocking call, that means it will do HTTP request and return when this request is finished.
  public List<String> getUrls(String url);
}
Note that getUrls(String url) simulates performing an HTTP request. You can treat it as a blocking function call that waits for an HTTP request to finish. It is guaranteed that getUrls(String url) will return the URLs within 15ms. Single-threaded solutions will exceed the time limit so, can your multi-threaded web crawler do better?

Below are two examples explaining the functionality of the problem. For custom testing purposes, you'll have three variables urls, edges and startUrl. Notice that you will only have access to startUrl in your code, while urls and edges are not directly accessible to you in code.

 

Example 1:



Input:
urls = [
  "http://news.yahoo.com",
  "http://news.yahoo.com/news",
  "http://news.yahoo.com/news/topics/",
  "http://news.google.com",
  "http://news.yahoo.com/us"
]
edges = [[2,0],[2,1],[3,2],[3,1],[0,4]]
startUrl = "http://news.yahoo.com/news/topics/"
Output: [
  "http://news.yahoo.com",
  "http://news.yahoo.com/news",
  "http://news.yahoo.com/news/topics/",
  "http://news.yahoo.com/us"
]
Example 2:



Input: 
urls = [
  "http://news.yahoo.com",
  "http://news.yahoo.com/news",
  "http://news.yahoo.com/news/topics/",
  "http://news.google.com"
]
edges = [[0,2],[2,1],[3,2],[3,1],[3,0]]
startUrl = "http://news.google.com"
Output: ["http://news.google.com"]
Explanation: The startUrl links to all other pages that do not share the same hostname.
 

Constraints:

1 <= urls.length <= 1000
1 <= urls[i].length <= 300
startUrl is one of the urls.
Hostname label must be from 1 to 63 characters long, including the dots, may contain only the ASCII letters from 'a' to 'z', digits from '0' to '9' and the hyphen-minus character ('-').
The hostname may not start or end with the hyphen-minus character ('-'). 
See:  https://en.wikipedia.org/wiki/Hostname#Restrictions_on_valid_hostnames
You may assume there're no duplicates in the URL library.
 

Follow up:

Assume we have 10,000 nodes and 1 billion URLs to crawl. We will deploy the same software onto each node. The software can know about all the nodes. We have to minimize communication between machines and make sure each node does equal amount of work. How would your web crawler design change?
What if one node fails or does not work?
How do you know when the crawler is done?




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

解题思路
  常用的高效方案是使用 BlockingQueue 结合 ThreadPoolExecutor（Java）或使用 ForkJoinPool。 
  AtomicInteger 维护活跃任务数 + ThreadPoolExecutor + 真正的线程安全去重

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class Solution {
    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        // 1. 获取初始 Hostname
        String hostName = getHostName(startUrl);
        
        // 2. 线程安全的去重集合
        Set<String> visited = ConcurrentHashMap.newKeySet();
        visited.add(startUrl);
        
        // 3. 待处理的 URL 队列
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer(startUrl);
        
        // 4. 活跃任务计数器：用于判断何时所有线程都已空闲且没有新链接产生
        AtomicInteger activeTasks = new AtomicInteger(0);
        
        // 5. 线程池：IO 密集型任务，线程数可以设得大一些
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores * 2);
        
        try {
            while (true) {
                // 尝试从队列取出一个 URL
                String curUrl = queue.poll(10, TimeUnit.MILLISECONDS);
                
                if (curUrl != null) {
                    // 提交新任务
                    activeTasks.incrementAndGet();
                    executor.execute(() -> {
                        try {
                            List<String> nextUrls = htmlParser.getUrls(curUrl);
                            for (String next : nextUrls) {
                                // 域名校验 + 原子性去重 (visited.add 为原子操作)
                                if (next.contains(hostName) && getHostName(next).equals(hostName)) {
                                    if (visited.add(next)) {
                                        queue.offer(next);
                                    }
                                }
                            }
                        } finally {
                            // 任务执行完毕，计数减一
                            activeTasks.decrementAndGet();
                        }
                    });
                } else {
                    // 如果队列为空，检查是否还有活跃任务在执行
                    if (activeTasks.get() == 0) {
                        break;
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }
        
        return new ArrayList<>(visited);
    }

    private String getHostName(String url) {
        // 更健壮的 Hostname 提取逻辑
        int start = 7; // skip "http://"
        int end = url.indexOf('/', start);
        return end == -1 ? url.substring(start) : url.substring(start, end);
    }
}


该版本的关键改进点：
1. 彻底消除阻塞 (Future.get)：
主线程不再通过 Future.get() 等待单个任务，而是使用 queue.poll(timeout) 进行非阻塞轮询。这允许主线程在任何子线程产出新 URL 时立即响应。

2. 原子性去重 (Atomic Check-then-Act)：
原代码：if(!visited.contains(cur)) visited.add(cur)（非原子，多线程下会漏判）。
新代码：if (visited.add(next))。Set.add() 如果元素已存在会返回 false，整个过程是线程安全的原子操作，确保每个 URL 仅被解析一次。

3. 活跃计数器 (AtomicInteger)：
引入 activeTasks。只有当“队列为空”且“没有任何线程正在解析网页”时，程序才会判定爬取彻底完成并退出循环。这比手动管理 taskQueue 更清晰且容错率更高。

4. Hostname 提取健壮性：
通过 indexOf('/') 动态寻找路径分隔符，能正确处理类似 http://leetcode.com（末尾无斜杠）和 http://leetcode.com 的情况，避免了 split 可能导致的数组越界风险。

5. 资源管理：
使用了 finally { executor.shutdown(); } 确保即便发生异常，线程池也能被正确关闭，防止资源泄露。


在优化后的爬虫代码中，选择 executor.execute 而不是 submit 主要基于以下原因：
1. 结果处理方式不同 (Fire and Forget)
execute (Runnable)：属于“发后即忘”。它执行一个没有返回值的任务。在爬虫场景中，我们的目标是让子线程获取链接并直接放入 queue，主线程不需要接收子线程传回的任何数据。
submit (Callable/Runnable)：会返回一个 Future 对象。你必须通过 future.get() 来检查任务是否成功。正如之前分析的，如果你在主循环里调用 get()，会导致主线程阻塞，把多线程变成了“串行等待”，极大地降低效率。





