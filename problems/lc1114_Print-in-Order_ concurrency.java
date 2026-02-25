1114. Print in Order - Easy (concurrency)

Suppose we have a class:

public class Foo {
  public void first() { print("first"); }
  public void second() { print("second"); }
  public void third() { print("third"); }
}

The same instance of Foo will be passed to three different threads. Thread A will call first(), 
thread B will call second(), and thread C will call third(). 

Design a mechanism and modify the program to ensure that second() is executed after first(), 
and third() is executed after second().

first --> second --> third

Example 1:

Input: [1,2,3]
Output: "firstsecondthird"
Explanation: There are three threads being fired asynchronously. The input [1,2,3] means thread A calls first(), thread B calls second(), and thread C calls third(). "firstsecondthird" is the correct output.
Example 2:

Input: [1,3,2]
Output: "firstsecondthird"
Explanation: The input [1,3,2] means thread A calls first(), thread B calls third(), and thread C calls second(). "firstsecondthird" is the correct output.
 

Note:

We do not know how the threads will be scheduled in the operating system, even though the numbers in the input seems to imply the ordering. The input format you see is mainly to ensure our tests' comprehensiveness.


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

由于这道题不需要处理复杂的原子性计数，只需处理执行顺序，我们有几种标准的实现方案：


方案一：使用 Semaphore（信号量 - 最推荐）
这是最简洁且最直观的方案。我们利用信号量的“初始值为 0”特性来实现阻塞。


import java.util.concurrent.Semaphore;

class Foo {
    // 两个信号量，初始许可都为 0
    private Semaphore spa = new Semaphore(0);
    private Semaphore spb = new Semaphore(0);

    public Foo() {}

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        // 释放一个许可，通知 second 可以开始了
        spa.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        // 尝试获取许可，如果 first 没完，这里会阻塞
        spa.acquire();
        printSecond.run();
        // 释放一个许可，通知 third 可以开始了
        spb.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
        // 等待 second 完成
        spb.acquire();
        printThird.run();
    }
}





方案二：使用 CountDownLatch（闭锁）
闭锁非常适合这种“一等一”的场景。



import java.util.concurrent.CountDownLatch;

class Foo {
    private CountDownLatch latch1 = new CountDownLatch(1);
    private CountDownLatch latch2 = new CountDownLatch(1);

    public Foo() {}

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        latch1.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        latch1.await();
        printSecond.run();
        latch2.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
        latch2.await();
        printThird.run();
    }
}

方案三：使用 Volatile + 自旋（轻量级但消耗 CPU）
如果对延迟极其敏感且任务执行非常快，可以使用 volatile 变量进行轮询。



class Foo {
    // 使用 volatile 保证可见性
    private volatile int stage = 1;

    public Foo() {}

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        stage = 2;
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while (stage != 2) { 
            // 自旋等待，直到 stage 变为 2
        }
        printSecond.run();
        stage = 3;
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (stage != 3) {
            // 自旋等待
        }
        printThird.run();
    }
}

2026 年面试点评：
Semaphore 是工业界处理此类“顺序流”最规范的工具，体现了你对 JUC 工具包的熟悉程度。
CountDownLatch 同样优秀，逻辑清晰。
注意点：在 Java 21+ 虚拟线程环境下，Semaphore 和 CountDownLatch 的阻塞代价极低，是处理此类同步问题的首选方案。

总结：这道题考察的核心是 Happens-before 关系。无论是信号量的释放还是闭锁的减数，都保证了前一个线程的操作
对后一个线程是可见的并先于其发生


