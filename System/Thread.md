## Defining and Starting a Thread

An application that creates an instance of Thread must provide the code that will run in that thread.

### Method 1: Provide a Runnable object. 
- The Runnable interface defines a single method, run, meant to contain the code executed in the thread. 
- The Runnable object is passed to the Thread constructor, as in the HelloRunnable example:

```java
public class HelloRunnable implements Runnable {

    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String args[]) {
        (new Thread(new HelloRunnable())).start();
    }

}
```


### Method 2: Subclass Thread. 
- The Thread class itself implements Runnable, though its run method does nothing. 
- An application can subclass Thread, providing its own implementation of run, as in the HelloThread example:

```java
public class HelloThread extends Thread {

    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String args[]) {
        (new HelloThread()).start();
    }

}
```

as a user who shops on amazon, 

### Difference btw 2 methods

- Method 1:
    - more general, because the Runnable object can subclass a class other than Thread. 
    - flexible, but it is applicable to the high-level thread management APIs covered later.


- Method 2: 
    - easier to use in simple applications
    - but is limited by the fact that your task class must be a descendant of Thread. 



The Thread class defines a number of methods useful for thread management. These include static methods, which provide information about, or affect the status of, the thread invoking the method. The other methods are invoked from other threads involved in managing the thread and Thread object. 