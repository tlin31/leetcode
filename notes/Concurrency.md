# Concurrency

## Summary:
- Concurrency is the ability to run several programs or several parts of a program in parallel. If a time consuming task can be performed asynchronously or in parallel, this improves the throughput and the interactivity of the program.

- A modern computer has several CPU’s or several cores within one CPU. The ability to leverage these multi-cores can be the key for a successful high-volume application.

- Goal: enable multitasking

- Problems caused by concurrency -- multiple processes/threads share some resources:
	1. race conditions: the program ends with an undesired output, resulting from the sequence of execution among the processes.
	2. deadlocks: the concurrent processes wait for some necessary resources from each other. As a result, none of them can make progress.
	3. resource starvation: a process is perpetually denied necessary resources to progress its works.

## Process vs. threads

A process runs independently and isolated of other processes. It cannot directly access shared data in other processes. The resources of the process, e.g. memory and CPU time, are allocated to it via the operating system.

A thread is a so called lightweight process. It has its own call stack, but can access shared data of other threads in the same process. Every thread has its own memory cache. If a thread reads shared data, it stores this data in its own memory cache.

A thread can re-read the shared data.

Process: Processes are basically the programs that are dispatched from the ready state and are scheduled in the CPU for execution. PCB(Process Control Block) holds the concept of process. A process can create other processes which are known as Child Processes. The process takes more time to terminate and it is isolated means it does not share the memory with any other process. 

The process can have the following states new, ready, running, waiting, terminated, and suspended. 

Thread: Thread is the segment of a process which means a process can have multiple threads and these multiple threads are contained within a process. A thread has three states: Running, Ready, and Blocked. 

The thread takes less time to terminate as compared to the process but unlike the process, threads do not isolate. 


![fig 2](/Users/lintianyi/Desktop/leetcode/pics/diff_ofProcessNThread.png)


Thread: 
A Java application runs by default in one process. Within a Java application you work with several threads to achieve parallel processing or asynchronous behavior.

A thread in Java is the direction or path that is taken while a program is being executed. Generally, all the programs have at least one thread, known as the main thread, that is provided by the JVM or Java Virtual Machine at the starting of the program’s execution. At this point, when the main thread is provided, the main() method is invoked by the main thread.

A thread is an execution thread in a program. Multiple threads of execution can be run concurrently by an application running on the Java Virtual Machine. The priority of each thread varies. Higher priority threads are executed before lower priority threads.

Thread is critical in the program because it enables multiple operations to take place within a single method. Each thread in the program often has its own program counter, stack, and local variable.

Thread in Java enables concurrent execution, dividing tasks for improved performance. It's essential for handling operations like I/O and network communication efficiently. Understanding threads is crucial for responsive Java applications. Enroll in a Java Course to master threading and create efficient multithreaded programs.

- Extending java.lang.Thread class
- Implementing Runnable interface
There are basically 4 stages in the lifecycle of a thread, as given below:

New
Runnable
Running
Blocked (Non-runnable state)
Dead


## Concurrent Programming on Single Processor Machine:

Sceanrio: user need to download files from 2 different servers, each server has 5 images and each image takes 5s. It is faster to download a little bit of image one, then a little bit of image two, three, four, five and then come back and a little bit of image one and so forth. 

- because when the image from the first server is called and it takes 5 seconds, not because incoming bandwidth is maxed out, but because it takes a while for the server to send it to the user. Basically, the user sits around waiting most of the time. So, while the user is waiting for the first image, he might as well be starting to download the second image. If the server is slow, by doing it in multiple threads concurrently, one can download additional images without much extra time.
- Pros: faster speed & decreased latency(Doing a little bit at a time decreases latency, so the user can see some feedback as things go along)


### Need of Concurrent Programming

<em>Threads</em> are useful when:
- the task is relatively large and self contained. When the user needs to perform only a small amount of combination after a large amount of separate processing, there’s some overhead to starting and using threads. 
- when the users are waiting. For instance, while one is waiting for one server, the other can be reading from another server.


## Steps for Concurrent Programming

1. queue a task. The Executors.newFixedThreadPool and gives it a size. This size indicates the maximum number of simultaneous tasks. For instance, if one add a thousand things to the queue but the pool size is 50, then only 50 of them will be running at any one time. Only when one of the first fifty finishes executing will 51st be taken up for execution. A number like 100 as pool size won’t overload the system.

    ExecutorService taskList = Executors.newFixedThreadPool(poolSize);
    
2. put some tasks of a runnable type to the tasks queue. Runnable is just a single interface that has one method called the run. System calls the run method at the appropriate time when it switches back and forth among the tasks by starting a separate thread.

    taskList.execute(someRunnable)

3. Execute method is a little bit of misnomer because when a task is added to the task in the queue that is created above with Executors.newFixedThreadPool, it doesn’t necessarily start executing it right away. It starts executing when one of those executing simultaneously(pool size) finishes execution.

## Implement concurrent programming

### Method 1. Separate Class that implements Runnable

- Pro:
    - Loose Coupling: Since a separate class can be reused, it promotes loose coupling.
    - Constructors: Arguments can be passed to constructors for different cases. For example, describing different loop limits for threads.
    - Race Conditions: If the data has been shared, it is unlikely that a separate class would be used as an approach and if it does not have a shared data, then no need to worry about the race conditions.

- Cons:
    - bit inconvenient to call back to the main application. A reference had to be passed along the constructor, and even if there is access to reference, only public methods(pause method in the given example) in the main application can be called.

Steps: 
1. create an entirely separate class that implements the runnable interface.

    public class MyRunnable implements Runnable {
             public void run() { ... }  
    }

2. make some instances of the main class and pass them to execute. Let’s apply this first approach to making threads that just count. So, each thread will print the thread name, task number and counter value.

3. use the pause method to sit around waiting so that system switches back and forth. Print statements will thus be interleaved.

4. Pass the constructor arguments to the constructor of the Runnable, so that different instances will count for a different number of times.

5. Calling the shutdown method means shutting down the thread that’s watching to see if any new tasks have been added or not.

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



### Method 2: Main App Implements Runnable

- Pros:
    - run method can call methods in the main application including the private ones.

- Cons: 
    - race conditions. 
    - The reason we put the run method in the main application is so it can handle data in the main application. If the user starts more than one thread and they are simultaneously modifying the same shared data, then there are race conditions to worry about. 
    - Secondly, there is no constructor which makes it very hard to pass constructor arguments, thus each class starts off the same way.

Steps:
1. The user has the main class that implements runnable which is a promise to the compiler that the class will have a run method.

    public class MyClass implements Runnable{
        public void run(){
        }
    }

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

### Method 3: Inner Class And Anonymous Inner Class that Implements Runnable 

- Pro:
    - easy to access the main application because methods in inner classes can access any public or private methods or instance variables of the outer class.
    - As with separate classes, the user can pass arguments to the constructor that stores them in instance variables that run uses.

- Con:
    - tight coupling as the run method is closely tied to this application. The run method cannot be reused elsewhere.
    - severe danger of race conditions. Inner classes are specifically used when the user wants to access the shared data (data in the main application).

Steps:

1. the user physically puts the class definition of the class that implements Runnable inside the class definition of the main class.

    public class OuterClass{
        private class InnerClass implements Runnable{
            public void run(){
            }
        }
    }

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



### Method 3.2: Anonymous Inner Class that implements Runnable

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



### Method 4: Lambda Expressions

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

    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("Printed inside Anonymous Inner Class!");
      }
    });

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

As we can see, in the normal case, we expect that the balance would never become negative after the execution of the function, which is also the desired behavior of the function.

However, unfortunately we could run into a race condition where the balance becomes negative. Here is how it could happen:
	Imagine we have 2 threads invoking the function at the same time with different input parameters, e.g. for thread #1, withdraw(amount=400) and for thread #2, withdraw(amount=200). The execution of the two threads is scheduled as the graph below, where at each time instance, we run exclusively only a statement from either threads.

![fig 1](https://leetcode.com/problems/print-in-order/Figures/1114/1114_race_condition.png)

As one can see, at the end of the above execution flow, we would end up with a negative balance, which is not a desired output.


### Race-free Concurrency

- Solution: coordinate resource sharing by <strong> access control on critical section + notification to the blocking threads.</strong>

	- if we could ensure the exclusivity of certain critical code section (e.g. the statements to check and deduce the balance), we could prevent the program from running into certain inconsistent states.

	- ex. <strong> LOCK </strong> that restricts the access of the critical section. Following the previous example, we apply the lock on the critical section, i.e. the statements of balance check and balance deduction. We then rerun the two threads, which could lead to the following flow:

![fig 12](https://leetcode.com/problems/print-in-order/Figures/1114/1114_lock.png)
	- Now, once a thread enters the critical section, it would prevent other threads from entering the same critical section. 
	- at the timestamp #3, the thread #2 enters the critical section. Then at the next timestamp #4, the thread #1 could have sneaked into the dangerous critical section if the statement was not protected by the lock. At the end, the two threads run concurrently, while the consistency of the system is maintained, i.e. the balance remains positive.

- If the thread is not granted with the access of the critical section --> thread is blocked or put into sleep, e.g. the thread #1 is blocked at the timestamp #4.

- Need to notify the waiting threads once the critical section is released. For instance, as soon as the thread #2 releases the critical section at the timestamp #5, the thread #1 got notified to take over the critical section.


## Problems
- ex. leetcode 1114. Print in order 

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




### Method 3: volatile

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