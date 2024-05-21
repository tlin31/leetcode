# Concurrency

## Summary:
- Concurrency is the ability to run several programs or several parts of a program in parallel. If a time consuming task can be performed asynchronously or in parallel, this improves the throughput and the interactivity of the program.

- A modern computer has several CPU’s or several cores within one CPU. The ability to leverage these multi-cores can be the key for a successful high-volume application.

- Goal: enable multitasking

- Problems caused by concurrency -- multiple processes/threads share some resources:
	1. race conditions: a race condition is a condition of a program where its behavior depends on relative timing or interleaving of multiple threads or processes. occurs when two or more threads can access shared data and they try to change it at the same time.
	2. deadlocks: the concurrent processes wait for some necessary resources from each other. As a result, none of them can make progress.
	3. resource starvation: a process is perpetually denied necessary resources to progress its works.
    4. visibility problem occurs if thread A reads shared data which is later changed by thread B and thread A is unaware of this change. Leads to Liveness failure: The program does not react anymore due to problems in the concurrent access of data, e.g. deadlocks.
    5. access problem can occur if several threads access and change the same shared data at the same time. Safety failure: The program creates incorrect data.

## Reference

1. Java concurrency (multi-threading) - Tutorial
https://www.vogella.com/tutorials/JavaConcurrency/article.html#fork-join-in-java-7


2. Geeks for geeks Different Approaches to Concurrent Programming in Java
https://www.geeksforgeeks.org/different-approaches-to-concurrent-programming-in-java/


## Process vs. threads

**process:** 
- Processes are programs that are dispatched from the ready state and are scheduled in the CPU for execution. 
- PCB(Process Control Block) holds the concept of process. 
- A process can create other processes which are known as Child Processes. 
- The process takes more time to terminate and it is isolated means it does not share the memory with any other process. 
- runs independently and isolated of other processes. 
- It cannot directly access shared data in other processes. 
- The resources of the process, e.g. memory and CPU time, are allocated to it via the operating system.
- The process can have the following states new, ready, running, waiting, terminated, and suspended. 

**thread: achieve parallel processing or asynchronous behavior.** 
- lightweight process
- is the segment of a process which means a process can have multiple threads and these multiple threads are contained within a process. 
- A thread has three states: Running, Ready, and Blocked. 
- It has its own **call stack**, but can access shared data of other threads in the same process.
- Every thread has its own memory cache. 
- If a thread reads shared data, it stores this data in its own memory cache.
- A thread can re-read the shared data.
- A thread in Java is the direction or path that is taken while a program is being executed. Generally, all the programs have at least one thread, known as the main thread, that is provided by the JVM or Java Virtual Machine at the starting of the program’s execution. At this point, when the main thread is provided, the main() method is invoked by the main thread.
- Thread is critical in the program because it enables multiple operations to take place within a single method. Each thread in the program often has its own **program counter, stack, and local variable**
- Thread in Java enables concurrent execution, dividing tasks for improved performance. It's essential for handling operations like I/O and network communication efficiently. Understanding threads is crucial for responsive Java applications. 
- Extending java.lang.Thread class
- Implementing Runnable interface


| Process | Thread |
| --- | --- |
| any program is in execution| a segment of a process |
| takes more time for creation| takes less time |
| more time for context switching | less time for context switching |
|The process is less efficient in terms of communication. |Thread is more efficient in terms of communication.|
|Multiprogramming holds the concepts of multi-process. |We don’t need multi programs in action for multiple threads because a single process consists of multiple threads.|
|The process is isolated|Threads share memory.|
|heavyweight process|Thread is lightweight as each thread in a process shares code, data, and resources.|
|Process switching uses an interface in an operating system.|Thread switching does not require calling an operating system and causes an interrupt to the kernel.|
|If one process is blocked then it will not affect the execution of other processes |If a user-level thread is blocked, then all other user-level threads are blocked. |
|The process has its own Process Control Block, Stack, and Address Space. |Thread has Parents’ PCB, its own Thread Control Block, and Stack and common Address space.|
| Changes to the parent process do not affect child processes |changes to the main thread may affect the behavior of the other threads of the process|
|system call is involved|No system call， it's created using APIs.|



## Concurrent Programming on Single Processor Machine:

Sceanrio: user need to download files from 2 different servers, each server has 5 images and each image takes 5s. It is faster to download a little bit of image one, then a little bit of image two, three, four, five and then come back and a little bit of image one and so forth. 

- because when the image from the first server is called and it takes 5 seconds, not because incoming bandwidth is maxed out, but because it takes a while for the server to send it to the user. Basically, the user sits around waiting most of the time. So, while the user is waiting for the first image, he might as well be starting to download the second image. If the server is slow, by doing it in multiple threads concurrently, one can download additional images without much extra time.

- Pros: faster speed & decreased latency(Doing a little bit at a time decreases latency, so the user can see some feedback as things go along)


### Need of Concurrent Programming

<em>Implement through</em>:
- Multithreading, multiprocessing, asynchronous programming, or event-driven programming.

<em>Threads</em> are useful when:
- the task is relatively large and self contained. When the user needs to perform only a small amount of combination after a large amount of separate processing, there’s some overhead to starting and using threads. 
- if the task is really small, one never get paid back for the overhead.
- when the users are waiting. For instance, while one is waiting for one server, the other can be reading from another server.

<em>Pros</em>
1. Improved Performance
2. Better Resource Utilization
3. Improved Responsiveness
4. Simplified Modeling
5. Robust Concurrency API: includes thread pools, concurrent collections, and atomic variables to ensure robustness. These concurrency tools streamline the development of concurrent code and mitigate prevalent concurrency problems.

## Steps for Concurrent Programming

1. queue a task. The **Executors.newFixedThreadPool** and gives it a size. This size indicates the maximum number of simultaneous tasks. For instance, if one add a thousand things to the queue but the pool size is 50, then only 50 of them will be running at any one time. Only when one of the first fifty finishes executing will 51st be taken up for execution. A number like 100 as pool size won’t overload the system.
```
    ExecutorService taskList = Executors.newFixedThreadPool(poolSize);
```    

2. put some tasks of a runnable type to the tasks queue. Runnable is just a single interface that has one method called the run. System calls the run method at the appropriate time when it switches back and forth among the tasks by starting a separate thread.
```
    taskList.execute(someRunnable)
```

3. Execute method is a little bit of misnomer because when a task is added to the task in the queue that is created above with Executors.newFixedThreadPool, it doesn’t necessarily start executing it right away. It starts executing when one of those executing simultaneously(pool size) finishes execution.

## Implement concurrent programming

### Method 1. Method extends Thread

--> just override the thread object’s run() method. The run() method will be invoked when the thread is started.

```java
public class ExampleThread extends Thread {
    @Override
    public void run() {
        // contains all the code you want to execute when the thread starts

        // prints out the name of the thread which is running the process
        System.out.println(Thread.currentThread().getName());
    }
}
```
To start a new thread, we create an instance of the above class and call the start() method on it.

```java
public class ThreadExamples {
    public static void main(String[] args) {
        ExampleThread thread = new ExampleThread();
        thread.start();
    }
}
```
Note:

- calling the run() method ***does not*** start a new thread. Instead, it executes the code of the thread inside the parent thread. We use the start() method to execute a new thread.



### Method 2. Separate Class that implements Runnable

- Pro:
    - Loose Coupling: Since a separate class can be reused
    - Constructors: Arguments can be passed to constructors for different cases. For example, describing different loop limits for threads.
    - **Race Conditions**: If the data has been shared, it is unlikely that a separate class would be used as an approach and if it does not have a shared data, then no need to worry about the race conditions.

- Cons:
    - bit inconvenient to call back to the main application. A reference had to be passed along the constructor, and even if there is access to reference, only public methods(pause method in the given example) in the main application can be called.

Steps: 
1. create an entirely separate class that implements the runnable interface.
```
    public class MyRunnable implements Runnable {
        public void run() { ... }  
    }
```

2. make some instances of the main class and pass them to execute. Let’s apply this first approach to making threads that just count. So, each thread will print the thread name, task number and counter value.

3. use the pause method to sit around waiting so that system switches back and forth. Print statements will thus be interleaved.

4. Pass the constructor arguments to the constructor of the Runnable, so that different instances will count for a different number of times.

5. Calling the shutdown method means shutting down the thread that’s watching to see if any new tasks have been added or not.

Executors and Thread Pools

1. reuse existing threads while also limiting the number of threads you can create 
2. The ExecutorService class allows us to create a certain number of threads and distribute tasks among the threads. Since you are creating a fixed number of threads, you have a lot of control over the performance of your application.

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 20; i++) {
            int finalI = i;
            executor.submit(() -> System.out.println(Thread.currentThread().getName() + " is executing task " + finalI));
        }
        executor.shutdown();
    }
}
```

More example:
```java
    import java.util.concurrent.ExecutorService; 
    import java.util.concurrent.Executors; 
      
      
    // Runnable Class that defines the logic of run method of runnable interface 
    public class Counter implements Runnable  
    { 
        private final MainApp mainApp; 
        private final int loopLimit; 
        private final String task; 
      
        // Constructor to get a reference to the main class 
        public Counter (MainApp mainApp, int loopLimit, String task) 
        { 
            this.mainApp = mainApp; 
            this.loopLimit = loopLimit; 
            this.task = task; 
        } 
      
        // Prints the thread name, task number and the value of counter 
        // Calls pause method to allow multithreading to occur 
        @Override
        public void run() 
        { 
            for (int i = 0; i < loopLimit; i++)  
            { 
                System.out.println("Thread: " + 
                    Thread.currentThread().getName() + 
                    " Counter: " + (i + 1) + " Task: " + task); 

                mainApp.pause(Math.random()); 
            } 
        } 
    }

    class MainApp  
    { 
        // Driver method 
        public static void main(String[] args) { 
            new MainApp().startThread(); 
        } 
      
        // Starts the threads. Pool size 2 means at any time there'll only be 2 simultaneous threads 
        public void startThread() { 
            ExecutorService taskList =  Executors.newFixedThreadPool(2); 

            for (int i = 0; i < 5; i++)  
            { 
                // At the appropriate time, calls run method of runnable interface 
                taskList.execute(new Counter(this, i + 1, "task " + (i + 1))); 
            } 
      
            // Shuts the thread that's watching to see if you have added new tasks. 
            taskList.shutdown(); 
        } 
      
        // Pauses execution for a moment so that system switches back and forth 
        public void pause(double seconds) { 
            try { 
                Thread.sleep(Math.round(1000.0 * seconds)); 
            } 
            catch (InterruptedException e) { 
                e.printStackTrace(); 
            } 
        } 
      

    } 

```
Output:

    Thread: pool-1-thread-1 Counter: 1 Task: task 1
    Thread: pool-1-thread-2 Counter: 1 Task: task 2
    Thread: pool-1-thread-2 Counter: 2 Task: task 2
    Thread: pool-1-thread-1 Counter: 1 Task: task 3
    Thread: pool-1-thread-2 Counter: 1 Task: task 4
    Thread: pool-1-thread-1 Counter: 2 Task: task 3
    Thread: pool-1-thread-1 Counter: 3 Task: task 3
    Thread: pool-1-thread-1 Counter: 1 Task: task 5
    Thread: pool-1-thread-2 Counter: 2 Task: task 4
    Thread: pool-1-thread-2 Counter: 3 Task: task 4
    Thread: pool-1-thread-1 Counter: 2 Task: task 5
    Thread: pool-1-thread-2 Counter: 4 Task: task 4
    Thread: pool-1-thread-1 Counter: 3 Task: task 5
    Thread: pool-1-thread-1 Counter: 4 Task: task 5
    Thread: pool-1-thread-1 Counter: 5 Task: task 5


<details>

<summary>Other methods that'll get race conditions</summary>

### Method 3: Main App Implements Runnable

- Pros:
    - run method can call methods in the main application including the private ones.

- Cons: 
    - race conditions. 
    - The reason we put the run method in the main application is so it can handle data in the main application. If the user starts more than one thread and they are simultaneously modifying the same shared data, then there are race conditions to worry about. 
    - Secondly, there is no constructor which makes it very hard to pass constructor arguments, thus each class starts off the same way.

Steps:
1. The user has the main class that implements runnable which is a promise to the compiler that the class will have a run method.
```java
    public class MyClass implements Runnable{
        public void run(){
        }
    }
```

2. The user then passes a reference to the main application to the execute method using the this keyword.

    taskList.execute(this)

This is the way to convey to the compiler that when it gets around to running a particular task, call it’s the respective run method.

3. The user implements runnable in the main class and the same class also has a bunch of other methods for making the task queue and calling the execute method.


```java
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors; 
  
public class MainAppRunnable implements Runnable { 
  
    private final int loopLimit; 
  
    // Limit till which the counter will run 
    private MainAppRunnable(int loopLimit) 
    { 
        this.loopLimit = loopLimit; 
    } 
  
    private void startThreads() 
    { 
  
        // Made the task queue 
        ExecutorService taskList = Executors.newFixedThreadPool(2); 
  
        // Added these to the task queue and made available for execution 
        // call its respective run method here!
        taskList.execute(this); 
        taskList.execute(this); 
        taskList.execute(this); 
        taskList.execute(this); 
        taskList.execute(this); 
  
        // Stopped new tasks from being added to the task queue 
        taskList.shutdown(); 
    } 
  
    @Override
    public void run() 
    { 
        for (int i = 0; i < loopLimit; i++) { 
            System.out.println( Thread.currentThread().getName() 
                + " Counter: " + i); 
        } 
  
        // Called private method that is part of the same application 
        pause(Math.random()); 
    } 
  
    // Methods that run uses can be private in this approach 
    private void pause(double seconds) 
    { 
        try { 
            Thread .sleep(Math.round(seconds * 1000.0)); 
        } 
        catch (InterruptedException e) { 
            e.printStackTrace(); 
        } 
    } 
  
    // Driver method 
    public static void main(String[] args) 
    { 
        new MainAppRunnable(3).startThreads(); 
    } 
} 
```

Output:

    pool-1-thread-1 Counter: 0
    pool-1-thread-2 Counter: 0
    pool-1-thread-1 Counter: 1
    pool-1-thread-1 Counter: 2   

### Method 4.1: Inner Class And Anonymous Inner Class that Implements Runnable 

- Pro:
    - easy to access the main application because methods in inner classes can access any public or private methods or instance variables of the outer class.
    - As with separate classes, the user can pass arguments to the constructor that stores them in instance variables that run uses.

- Con:
    - tight coupling as the run method is closely tied to this application. The run method cannot be reused elsewhere.
    - severe danger of **race conditions**. Inner classes are specifically used when the user wants to access the shared data (data in the main application).

Steps:

1. the user physically puts the class definition of the class that implements Runnable inside the class definition of the main class.
```
    public class OuterClass{
        private class InnerClass implements Runnable{
            public void run(){
            }
        }
    }
```
2. The run() method is then put inside the inner class and passed to execute method. Execute does not really mean execute. It means available for execution. 

    taskList.execute(new InnerClass());

```java
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors; 

public class OuterClass { 
  
    public static void main(String[] args) { 
        new OuterClass().startThreads(); 
    } 
  
    private void startThreads() { 
        ExecutorService taskList = Executors.newFixedThreadPool(2); 
  
        taskList.execute(new InnerClass(1)); 
        taskList.execute(new InnerClass(2)); 
        taskList.execute(new InnerClass(3)); 
        taskList.execute(new InnerClass(4)); 
        taskList.execute(new InnerClass(5)); 
        taskList.shutdown(); 
    } 
  

    private void pause(double seconds) 
    { 
        try { 
            Thread.sleep(Math.round(1000.0 * seconds)); 
        } 
        catch (InterruptedException e) { 
            e.printStackTrace(); 
        } 
    } 
  
    // Inner Class 
    public class InnerClass implements Runnable { 
  
        private int loopLimit; 
  
        // Constructor to define different limits 
        InnerClass(int loopLimit) { 
            this.loopLimit = loopLimit; 
        } 
  
        // Prints thread name and value of the counter variable 
        @Override
        public void run() 
        { 
            for (int i = 0; i < loopLimit; i++) { 
                System.out.println( 
                    Thread.currentThread().getName() 
                    + " Counter: " + i); 
                pause(Math.random()); 
            } 
        } 
    } 
} 
```
Output:


    pool-1-thread-1 Counter: 0
    pool-1-thread-2 Counter: 0
    pool-1-thread-1 Counter: 0
    pool-1-thread-2 Counter: 1
    pool-1-thread-1 Counter: 1
    pool-1-thread-2 Counter: 0
    pool-1-thread-2 Counter: 1
    pool-1-thread-1 Counter: 2
    pool-1-thread-1 Counter: 0
    pool-1-thread-2 Counter: 2
    pool-1-thread-1 Counter: 1
    pool-1-thread-2 Counter: 3
    pool-1-thread-1 Counter: 2
    pool-1-thread-1 Counter: 3
    pool-1-thread-1 Counter: 4



### Method 4.2: Anonymous Inner Class that implements Runnable

- shorten the syntax by using Anonymous Inner Classes where the user gives the class definition and instantiate the class all in one fell swoop. 
- Pro:
    - same as inner classes 
    - shorter and more succinct. 
- Con:
    - more confusing to beginners, there are no constructors 
    - cannot be reused elsewhere.

```java
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors; 
  

public class MyClass { 
  
    public static void main(String[] args) 
    { 
        new MyClass().startThreads(); 
    } 
  
    private void startThreads() 
    { 
        ExecutorService taskList 
            = Executors.newFixedThreadPool(2); 
  
        for (int i = 0; i < 5; i++) { 
            int finalI = i + 1; 
  
            // Giving the class definition and instantiating it all at once 
            taskList.execute(new Runnable() { 
  
                @Override
                public void run() 
                { 
                    for (int j = 0; j < finalI; j++) { 
                        System.out.println( 
                            Thread .currentThread().getName() 
                            + " Counter:" + j); 
                        pause(Math.random()); 
                    } 
                } 
            }); 
        } 
        taskList.shutdown(); 
    } 
  
    // Pauses execution allowing time for system to switch back and forth 
    private void pause(double seconds) { 
        try { 
            Thread.sleep( 
                Math.round(1000.0 * seconds)); 
        } 
        catch (InterruptedException e) { 
            e.printStackTrace(); 
        } 
    } 
} 

```

Output:

    pool-1-thread-1 Counter:0
    pool-1-thread-2 Counter:0
    pool-1-thread-2 Counter:1
    pool-1-thread-1 Counter:0
    pool-1-thread-2 Counter:0
    pool-1-thread-1 Counter:1
    pool-1-thread-2 Counter:1
    pool-1-thread-1 Counter:2
    pool-1-thread-2 Counter:2
    pool-1-thread-2 Counter:3
    pool-1-thread-1 Counter:0
    pool-1-thread-1 Counter:1
    pool-1-thread-1 Counter:2
    pool-1-thread-1 Counter:3
    pool-1-thread-1 Counter:4



### Method 5: Lambda Expressions

Lambda expressions are very similar in behaviour to anonymous inner classes. 
They have complete access to code from surrounding classes including the private data. 

!!! lambda expressions cannot have instance variables, so they do not maintain a state.

- Pro:
    - The user can easily access data in the main class including the private data. 
    - Lambdas are concise, succinct and readable.

- Con:
    - Lambdas are not allowed to have instance variables, thus we cannot pass arguments to run method. 
    - we typically use lambdas when we have some shared data which invites the danger of race conditions.


We can replace Anonymous Inner Class with Lambda Expression as follows:



Anonymous Inner Class:

```java
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("Printed inside Anonymous Inner Class!");
      }
    });
```
Lambda Expression:

    Thread anotherThread = new Thread(() -> System.out.println(“Printed inside Lambda!”));

```java
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors; 
  
// Code for Concurrent programming using 
// Lambda Expression 
public class MyClass { 
  
    // Driver method 
    public static void main(String[] args) 
    { 
        new MyClass().startThreads(); 
    } 
  
    // Starting threads with pool size as 2 
    private void startThreads() 
    { 
        ExecutorService taskList 
            = Executors.newFixedThreadPool(2); 
  
        for (int i = 0; i < 5; i++) { 
  
            int finalI = i + 1; 
  
            // Prints thread name and value 
            // of the counter variable 
            taskList.execute(() -> { 
  
                for (int j = 0; j < finalI; j++) { 
  
                    System.out.println( 
                        Thread 
                            .currentThread() 
                            .getName() 
                        + " Counter:" + j); 
                    pause(Math.random()); 
                } 
            }); 
        } 
        taskList.shutdown(); 
    } 
  
    // Pauses execution allowing time for 
    // system to switch back and forth 
    private void pause(double seconds) 
    { 
        try { 
            Thread.sleep( 
                Math.round(1000.0 * seconds)); 
        } 
        catch (InterruptedException e) { 
            e.printStackTrace(); 
        } 
    } 
} 

```
Output:


    pool-1-thread-1 Counter:0
    pool-1-thread-2 Counter:0
    pool-1-thread-2 Counter:1
    pool-1-thread-2 Counter:0
    pool-1-thread-1 Counter:0
    pool-1-thread-2 Counter:1
    pool-1-thread-1 Counter:1
    pool-1-thread-2 Counter:2
    pool-1-thread-1 Counter:2
    pool-1-thread-2 Counter:0
    pool-1-thread-1 Counter:3
    pool-1-thread-2 Counter:1
    pool-1-thread-2 Counter:2
    pool-1-thread-2 Counter:3
    pool-1-thread-2 Counter:4

</details>

## Race Condition

- Suppose we have a function called withdraw(amount) which deduces certain amount of money from the balance, if the demanding amount is less than the current balance. At the end, the function returns the remaining balance. 

```java

int balance = 500;
int withdraw(int amount) {
  if (amount < balance) {
    balance -= amount;
  }
  return balance;
}

```

However, unfortunately we could run into a race condition where the balance becomes negative. Here is how it could happen:

Imagine we have 2 threads invoking the function at the same time with different input parameters, e.g. for thread #1, withdraw(amount=400) and for thread #2, withdraw(amount=200). The execution of the two threads is scheduled as the graph below, where at each time instance, we run exclusively only a statement from either threads.

![fig 1](https://leetcode.com/problems/print-in-order/Figures/1114/1114_race_condition.png)



### Race-free Concurrency

- Solution: coordinate resource sharing by <strong> access control on critical section + notification to the blocking threads.</strong>

	- if we could ensure the exclusivity of certain critical code section (e.g. the statements to check and deduce the balance), we could prevent the program from running into certain inconsistent states.

	- ex. <strong> LOCK </strong> that restricts the access of the critical section. Following the previous example, we apply the lock on the critical section, i.e. the statements of balance check and balance deduction. We then rerun the two threads, which could lead to the following flow:

![fig 12](https://leetcode.com/problems/print-in-order/Figures/1114/1114_lock.png)

- Now, once a thread enters the critical section, it would prevent other threads from entering the same critical section. 

- at the timestamp #3, the thread #2 enters the critical section. Then at the next timestamp #4, the thread #1 could have sneaked into the dangerous critical section if the statement was not protected by the lock. At the end, the two threads run concurrently, while the consistency of the system is maintained, i.e. the balance remains positive.

- If the thread is not granted with the access of the critical section --> thread is blocked or put into sleep, e.g. the thread #1 is blocked at the timestamp #4.

- Need to notify the waiting threads once the critical section is released. For instance, as soon as the thread #2 releases the critical section at the timestamp #5, the thread #1 got notified to take over the critical section.


## Locks and thread synchronization

### Locks
Java provides **locks** to protect certain parts of the code to be executed by several threads at the same time: 
    - only a single thread can execute a block of code at the same time
    - each thread entering a synchronized block of code sees the effects of all previous modifications that were guarded by the same lock

```java
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
    private final ReentrantLock lock = new ReentrantLock();
    private int count = 0;

    public int increment() {
        lock.lock();
        try {
            return this.count++;
        } finally {
            lock.unlock();
        }
    }
}
```

When we call the lock() method in a thread, it tries to acquire the lock. If it’s successful, it executes the task. However, if it’s unsuccessful, the thread is blocked until the lock is released.

The isLocked() returns a boolean value depending on whether lock can be acquired or not.

The tryLock() method tries to acquire the lock in a nonblocking way. It returns true if it’s successful and false otherwise.

The unlock() method releases the lock.

### ReadWriteLock
When working with shared data and resources, usually, you’d want two things:
1) Multiple threads should be able to *read* the resource at a time if it’s not being written.
2) Only one thread can *write* the shared resource at a time if no other thread is reading or writing it.

**ReadWriteLock Interface** achieves this by using two locks instead of one. The read lock can be acquired by multiple threads at a time if no thread has acquired the write lock. The write lock can be acquired only if both read and write lock have not been acquired.


```java
public class SharedCache {
    // map stores key value pair
    private Map<String, String> cache = new HashMap<>();

    public String readData(String key) {
        return cache.get(key);
    }

    public void writeData(String key, String value) {
        cache.put(key, value);
    }
}
```

We want multiple threads to read our cache at the same time (while it’s not being written). But only one thread can write our cache at a time. To achieve this, we will use the ReentrantReadWriteLock which is an implementation of the ReadWriteLock interface.

```java
public class SharedCache {
    private Map<String, String> cache = new HashMap<>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    
    public String readData(String key) {
        lock.readLock().lock();
        try {
            return cache.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }
    

    public void writeData(String key, String value) {
        lock.writeLock().lock();
        try {
            cache.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
```

### Synchronized Blocks And Methods
Synchronized blocks are pieces of Java code that can be executed by only one thread at a time. 

    // SYNTAX
    synchronized (Object reference_object) {
     // code you want to be synchronized
    }

When you create a synchronized block, you need to pass a **reference object**. In the above example ”this” or the current object is the reference object, which means if multiple instances of the are created, they won’t be synchronized.

You can also make a method synchronized by using the synchronized keyword.

    public synchronized int increment();


Synchronization： 
    - necessary for mutually exclusive access to blocks of and for reliable communication between threads.This would ensure that only one thread can enter this method at the same time. 
    - Another thread which is calling this method would wait until the first thread leaves this method.

```java
public synchronized void critial() {
    // some thread critical stuff
}
```

All code which is protected by the same lock can only be executed by one thread at the same time.

For example the following data structure will ensure that only one thread can access the inner block of the add() and next() methods.


```java
package de.vogella.pagerank.crawler;

import java.util.ArrayList;
import java.util.List;

/**
 * Data structure for a web crawler. Keeps track of the visited sites and keeps
 * a list of sites which needs still to be crawled.
 *
 * @author Lars Vogel
 *
 */
public class CrawledSites {
    private List<String> crawledSites = new ArrayList<String>();
    private List<String> linkedSites = new ArrayList<String>();

    public void add(String site) {
        synchronized (this) {
            if (!crawledSites.contains(site)) {
                linkedSites.add(site);
            }
        }
    }

    /**
     * Get next site to crawl. Can return null (if nothing to crawl)
     */
    public String next() {
        if (linkedSites.size() == 0) {
            return null;
        }
        synchronized (this) {
            // Need to check again if size has changed
            if (linkedSites.size() > 0) {
                String s = linkedSites.get(0);
                linkedSites.remove(0);
                crawledSites.add(s);
                return s;
            }
            return null;
        }
    }

}
```
<em>Volatile</em>

if declared with the volatile keyword then it is guaranteed that any thread that reads the field will see the most recently written value. The volatile keyword will not perform any mutual exclusive lock on the variable.

## Deadlocks

Deadlock occurs when two or more threads are unable to proceed because each of them is waiting for the other to release a resource or take a specific action. 

Consider this, you have two threads and two locks (let’s call them threadA, threadB, lockA and lockB). ThreadA will try to acquire lockA first and if it’s successful, it will try to acquire lockB. ThreadB, on the other hand, tries to acquire lockB first and then lockA.

```java
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        ReentrantLock lockA = new ReentrantLock();
        ReentrantLock lockB = new ReentrantLock();

        Thread threadA = new Thread(() -> {
            lockA.lock();
            try {
                System.out.println("Thread-A has acquired Lock-A");
                lockB.lock();
                try {
                    System.out.println("Thread-A has acquired Lock-B");
                } finally {
                    lockB.unlock();
                }
            } finally {
                lockA.unlock();
            }
        });

        Thread threadB = new Thread(() -> {
            lockB.lock();
            try {
                System.out.println("Thread-B has acquired Lock-B");
                lockA.lock();
                try {
                    System.out.println("Thread-B has acquired Lock-A");
                } finally {
                    lockA.unlock();
                }
            } finally {
                lockB.unlock();
            }
        });
        
        threadA.start();
        threadB.start();
    }
}
```
Here, Thread A acquires lockA and is waiting to lockB. Thread B has acquired lockB and is waiting to acquire lockA.

Here, threadA will never acquire lockB as it is held by threadB. Similarly, threadB can never acquire lockA as it is held by threadA. This kind of situation is called a deadlock.

### How to avoid deadlocks

1. Define a strict order in which resources should be acquired. All threads must follow the same order when requesting resources.
2. Avoid nesting of locks or synchronized blocks. The cause for the deadlock in the previous example was that the threads were not able to release one lock without acquiring the other lock.
3. Ensure that threads do not acquire multiple resources simultaneously. If a thread holds one resource and needs another, it should release the first resource before attempting to acquire the second. This prevents circular dependencies and reduces the likelihood of deadlocks.
4. Set timeouts when acquiring locks or resources. If a thread fails to acquire a lock within a specified time, it releases all acquired locks and tries again later. This prevents a situation where a thread holds a lock indefinitely, potentially causing a deadlock.


## Problems
- ex. leetcode 1114. Print in order 

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

### Method 1:  Pair Synchronization

- key: create some dependencies between pairs of jobs
	- The dependency between pairs of jobs construct a partial order on the execution sequence of all jobs, e.g. with A < B, B < C, we could obtain the sequence of A < B < C.

- Use shared variable named firstJobDone to coordinate the execution order between the first job and the second job. Similarly, we could use another variable secondJobDone to enforce the order of execution between the second and the third jobs.

<strong><em> Algorithm </em></strong>

1.  initialize the coordination variables firstJobDone and secondJobDone, to indicate that the jobs are not done yet.
2. In the first() function, we have no dependency so that we could get straight down to the job. At the end of the function, we then update the variable firstJobDone to indicate that the first job is done.
3. In the second() function, we check the status of firstJobDone. If it is not updated, we then wait, otherwise we proceed to the task of the second job. And at the end of function, we update the variable secondJobDone to mark the completion of the second job.
4. In the third() function, similar to step 3.



```java
class Foo {

  private AtomicInteger firstJobDone = new AtomicInteger(0);
  private AtomicInteger secondJobDone = new AtomicInteger(0);

  public Foo() {}

  public void first(Runnable printFirst) throws InterruptedException {
    printFirst.run();

    // mark the first job as done, by increasing its count.
    firstJobDone.incrementAndGet();
  }

  public void second(Runnable printSecond) throws InterruptedException {
    while (firstJobDone.get() != 1) {
      // waiting for the first job to be done.
    }

    printSecond.run();

    // mark the second as done, by increasing its count.
    secondJobDone.incrementAndGet();
  }

  public void third(Runnable printThird) throws InterruptedException {
    while (secondJobDone.get() != 1) {
      // waiting for the second job to be done.
    }

    // printThird.run() outputs "third".
    printThird.run();
  }
}
```


### Method 2: Semaphore

- Semaphore is a bowl of marbles (or locks in this case). If you need a marble, and there are none, you wait. You wait until there is one marble and then you take it. If you release(), you will add one marble to the bowl (from thin air). If you release(100), you will add 100 marbles to the bowl (from thin air).
- The thread calling third() will wait until the end of second() when it releases a '3' marble. The second() will wait until the end of first() when it releases a '2' marble. Since first() never acquires anything, it will never wait. 
- With semaphores, you can start out with 1 marble or 0 marbles or 100 marbles. A thread can take marbles (up until it's empty) or put many marbles (out of thin air) at a time.

```java
class Foo {
    private Semaphore run2 = new Semaphore(0);
    private Semaphore run3 = new Semaphore(0);

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        run2.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        run2.acquire();
        printSecond.run();
        run3.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
        run3.acquire();
        printThird.run();
    }
}
```

- Time Complexity: O(1), Space Complexity: O(1)


### Method 3: CountDownLatch

```java
class Foo {
    private CountDownLatch latch2 = new CountDownLatch(1);
    private CountDownLatch latch3 = new CountDownLatch(1);

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        latch2.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        latch2.await();
        printSecond.run();
        latch3.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
        latch3.await();
        printThird.run();
    }
}
```
- Time Complexity: O(1), Space Complexity: O(1)




### Method 4: volatile

```java
class Foo {
    
    private volatile boolean onePrinted;
    private volatile boolean twoPrinted;

    public Foo() {
        onePrinted = false;
        twoPrinted = false;        
    }

    public synchronized void first(Runnable printFirst) throws InterruptedException {
        
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        onePrinted = true;
        notifyAll();
    }

    public synchronized void second(Runnable printSecond) throws InterruptedException {
        while(!onePrinted) {
            wait();
        }
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        twoPrinted = true;
        notifyAll();
    }

    public synchronized void third(Runnable printThird) throws InterruptedException {
        while(!twoPrinted) {
            wait();
        }
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}
```

1242. Web Crawler Multithreaded: Medium
1279. Traffic Light Controlled Intersection: easy
1188. Design Bounded Blocking Queue: Medium
2370. Longest Ideal Subsequence
Medium
1114. Print in Order
Easy
1115. Print FooBar Alternately
65.6%
Medium
1116. Print Zero Even Odd
61.0%
Medium
1117. Building H2O
56.1%
Medium


