1188. Design Bounded Blocking Queue - Medium


Implement a thread-safe bounded blocking queue that has the following methods:

BoundedBlockingQueue(int capacity) The constructor initializes the queue with a maximum capacity.
void enqueue(int element) Adds an element to the front of the queue. If the queue is full, the calling thread is blocked until the queue is no longer full.
int dequeue() Returns the element at the rear of the queue and removes it. If the queue is empty, the calling thread is blocked until the queue is no longer empty.
int size() Returns the number of elements currently in the queue.
Your implementation will be tested using multiple threads at the same time. Each thread will either be a producer thread that only makes calls to the enqueue method or a consumer thread that only makes calls to the dequeue method. The size method will be called after every test case.

Please do not use built-in implementations of bounded blocking queue as this will not be accepted in an interview.

 

Example 1:

Input:
1
1
["BoundedBlockingQueue","enqueue","dequeue","dequeue","enqueue","enqueue","enqueue","enqueue","dequeue"]
[[2],[1],[],[],[0],[2],[3],[4],[]]

Output:
[1,0,2,2]

Explanation:
Number of producer threads = 1
Number of consumer threads = 1

BoundedBlockingQueue queue = new BoundedBlockingQueue(2);   // initialize the queue with capacity = 2.

queue.enqueue(1);   // The producer thread enqueues 1 to the queue.
queue.dequeue();    // The consumer thread calls dequeue and returns 1 from the queue.
queue.dequeue();    // Since the queue is empty, the consumer thread is blocked.
queue.enqueue(0);   // The producer thread enqueues 0 to the queue. The consumer thread is unblocked and returns 0 from the queue.
queue.enqueue(2);   // The producer thread enqueues 2 to the queue.
queue.enqueue(3);   // The producer thread enqueues 3 to the queue.
queue.enqueue(4);   // The producer thread is blocked because the queue's capacity (2) is reached.
queue.dequeue();    // The consumer thread returns 2 from the queue. The producer thread is unblocked and enqueues 4 to the queue.
queue.size();       // 2 elements remaining in the queue. size() is always called at the end of each test case.
Example 2:

Input:
3
4
["BoundedBlockingQueue","enqueue","enqueue","enqueue","dequeue","dequeue","dequeue","enqueue"]
[[3],[1],[0],[2],[],[],[],[3]]
Output:
[1,0,2,1]

Explanation:
Number of producer threads = 3
Number of consumer threads = 4

BoundedBlockingQueue queue = new BoundedBlockingQueue(3);   // initialize the queue with capacity = 3.

queue.enqueue(1);   // Producer thread P1 enqueues 1 to the queue.
queue.enqueue(0);   // Producer thread P2 enqueues 0 to the queue.
queue.enqueue(2);   // Producer thread P3 enqueues 2 to the queue.
queue.dequeue();    // Consumer thread C1 calls dequeue.
queue.dequeue();    // Consumer thread C2 calls dequeue.
queue.dequeue();    // Consumer thread C3 calls dequeue.
queue.enqueue(3);   // One of the producer threads enqueues 3 to the queue.
queue.size();       // 1 element remaining in the queue.

Since the number of threads for producer/consumer is greater than 1, we do not know how the threads will be scheduled in the operating system, even though the input seems to imply the ordering. Therefore, any of the output [1,0,2] or [1,2,0] or [0,1,2] or [0,2,1] or [2,0,1] or [2,1,0] will be accepted.
 

Constraints:

1 <= Number of Prdoucers <= 8
1 <= Number of Consumers <= 8
1 <= size <= 30
0 <= element <= 20
The number of calls to enqueue is greater than or equal to the number of calls to dequeue.
At most 40 calls will be made to enque, deque, and size.


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

这道题的核心要求是：

1. 阻塞性：队列满时 enqueue 阻塞，队列空时 dequeue 阻塞。
2. 线程安全：多个线程同时操作时，数据结构必须保持一致。


以下是基于 ReentrantLock 和 Condition 的标准工业级实现：



import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBlockingQueue {
    // 1. 使用 LinkedList 作为底层存储
    private final Queue<Integer> queue;
    private final int capacity;
    
    // 2. 核心锁机制
    private final Lock lock = new ReentrantLock();
    // 队列不满的信号，由消费者发出，通知生产者
    private final Condition notFull = lock.newCondition();
    // 队列不空的信号，由生产者发出，通知消费者
    private final Condition notEmpty = lock.newCondition();

    public BoundedBlockingQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
    }

    public void enqueue(int element) throws InterruptedException {
        lock.lock(); // 必须先获取锁
        try {
            // 3. 循环检查条件（防止虚假唤醒）
            while (queue.size() == capacity) {
                notFull.await(); // 队列满了，生产者在此处睡觉并释放锁
            }
            queue.offer(element);
            // 4. 生产了一个，通知正在等待的消费者
            notEmpty.signal(); 
        } finally {
            lock.unlock(); // 永远在 finally 中释放锁
        }
    }

    public int dequeue() throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == 0) {
                notEmpty.await(); // 队列空了，消费者在此处睡觉并释放锁
            }
            int element = queue.poll();
            // 5. 消费了一个，通知正在等待的生产者
            notFull.signal();
            return element;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }
}

1. 为什么使用 while 而不是 if？

原因：为了防止虚假唤醒（Spurious Wakeup）。线程被唤醒后，可能由于其他线程抢先一步取走了元素，导致条件再次不满足。
使用 while 可以在唤醒后再次确认条件。


2. Condition 相比 wait/notify 的优势

精准唤醒：我们可以区分 notFull 和 notEmpty。如果只用 notifyAll()，会把所有的生产者和消费者全部叫醒，造成无效的竞争
使用 Condition 可以实现“点对点”通知。


3. 2026 年的技术细节：虚拟线程兼容性

ReentrantLock 和 Condition 已经过优化，能够非常平滑地解耦虚拟线程与物理线程。
如果你在面试中提到这一点，说明你了解现代 JVM 内部的**调度器（Scheduler）**改进。


4. 关于 size() 方法的锁定

很多初学者认为 size() 只是读一个数，不需要加锁。
真相：在 Java 内存模型中，为了保证 size 的最新可见性（读取到的是其他线程修改后的最新值），必须加锁。
除非你将 size 定义为 AtomicInteger。


5. 为什么通常优先推荐 Lock/Condition 而不是 Semaphore？

在实现“有界队列”时，两者各有千秋，但 Lock + Condition 更常见的核心原因是：
原子性保护：Semaphore 只能管理“数量（Permits）”，但它不能保护对 LinkedList（非线程安全）本身的并发访问。
因此，即使用了信号量，你仍然需要一个额外的 synchronized 或 Lock 来保证 queue.offer() 或 queue.poll() 操作的
原子性。

逻辑一致性：Lock + Condition 将“互斥”和“条件通知”统一在一套机制下，代码逻辑更符合传统的生产者-消费者模型。

如果使用 Semaphore，我们需要 3 个信号量：
	notFull：管理剩余空间（初始值为 capacity）。
	notEmpty：管理现有元素（初始值为 0）。
	mutex：管理对队列本身的互斥访问（初始值为 1，充当锁）。



import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

class BoundedBlockingQueue {
    private final Queue<Integer> queue;
    
    // 1. 信号量：控制空位数量
    private final Semaphore notFull;
    // 2. 信号量：控制元素数量
    private final Semaphore notEmpty;
    // 3. 信号量：互斥锁（充当 synchronized/Lock）
    private final Semaphore mutex;

    public BoundedBlockingQueue(int capacity) {
        this.queue = new LinkedList<>();
        this.notFull = new Semaphore(capacity);
        this.notEmpty = new Semaphore(0);
        this.mutex = new Semaphore(1);
    }

    public void enqueue(int element) throws InterruptedException {
        // 先等待空位（如果不先拿空位先拿锁，会死锁）
        notFull.acquire(); 
        
        // 锁定队列进行写操作
        mutex.acquire();
        try {
            queue.offer(element);
        } finally {
            mutex.release();
        }
        
        // 增加一个元素信号，通知消费者
        notEmpty.release();
    }

    public int dequeue() throws InterruptedException {
        // 先等待元素
        notEmpty.acquire();
        
        // 锁定队列进行读操作
        mutex.acquire();
        int element;
        try {
            element = queue.poll();
        } finally {
            mutex.release();
        }
        
        // 增加一个空位信号，通知生产者
        notFull.release();
        return element;
    }

    public int size() {
        // size 的读取也需要互斥
        return queue.size(); 
    }
}



如果你的队列逻辑涉及复杂的判断条件（比如“当元素总和超过 100 且队列不空时才出队”），请务必使用 Condition，
因为它支持灵活的逻辑判断。

如果你的逻辑只是简单的数量配额控制，Semaphore 是非常优雅的选择。

特别注意：在 enqueue 中，必须先调用 notFull.acquire() 再调用 mutex.acquire()。如果顺序调换，会出现
“拿到锁后发现没位子去睡觉，但占着锁不放，导致消费者进不来放位子”的经典死锁。




