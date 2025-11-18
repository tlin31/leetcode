https://www.geeksforgeeks.org/object-oriented-programming-oops-concept-in-java/

## OOP in java


✅ 一、总结：什么是 Java 的面向对象编程（OOP）？

Java 是一种 纯面向对象（Object Oriented Programming, OOP） 的语言，它通过对象和类来组织代码。
OOP 的核心思想是：

**用对象（具备属性 + 行为）来模拟现实世界，并通过封装、继承、多态实现灵活、可扩展、可维护的软件结构。**

Java 中 OOP 的四大特性是：

1. 封装（Encapsulation）：隐藏内部实现，只暴露必要接口

2. 继承（Inheritance）：复用父类代码，支持层次结构

3. 多态（Polymorphism）：运行时不同对象表现出不同行为

4. 抽象（Abstraction）：只保留本质特征，屏蔽复杂细节


✅ 二、Java 面向对象的四大特性（详细 + 示例）

1. 封装（Encapsulation）
- 封装是隐藏内部状态，只通过方法访问对象的属性。

实现方式：
- 使用 private 修饰成员变量
- 提供 getter/setter 方法

```java
class Person {
    private String name;      // 封装字段
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
```

📌 优势：
- 隐藏实现细节，防止误操作
- 更安全、灵活
- 易于维护



2. 继承（Inheritance）

Java 使用关键字 extends 实现类继承。

```java
class Animal {
    void eat() { System.out.println("eating"); }
}

class Dog extends Animal {
    void bark() { System.out.println("barking"); }
}

    Animal animal = new Dog();

```

📌 优势：
- 代码复用
- 建立类的层次结构（例如：Animal → Dog、Cat）

📌 注意：
Java 是单继承（一个类只能继承一个父类），但可以实现多个接口。



3. 多态（Polymorphism）

多态主要有两种：

① 编译时多态（方法重载 Overloading）
```java
    void print(int a) {}
    void print(String s) {}
```


② 运行时多态（重写 Override + 向上转型）
```java
    Animal animal = new Dog();
    animal.eat(); // 调用 Dog 的 eat()
```

📌 原理：Java 根据实际对象类型而非引用类型决定方法调用。

📌 多态是面试重点，优势包括：
- 更高扩展性
- 灵活的代码（替换实现无须改调用方）





4. 抽象（Abstraction）

通过抽象类或接口定义抽象行为，让子类实现具体功能。

```java
    抽象类（abstract）：
    abstract class Animal {
        abstract void makeSound();
    }

    接口（interface）：
    interface Flyable { 
        void fly();
    }
```

📌 Java 接口用于定义能力，如 Serializable、Comparable。


✅ 三、Java OOP 的核心概念

1. 类（Class）与对象（Object）

    类：对象的模板

    对象：类的实例

    class Car { String color; }

    Car c = new Car();  // 创建对象


2. 方法（Method）与字段（Field）

- 字段代表对象属性，方法代表对象行为。



3. 构造方法 Constructor

    用于初始化对象：
```java
    public Car(String color) {
        this.color = color;
    }
```

4. this 与 super 关键字

- this：访问当前对象
- super：访问父类对象


✅ 四、Java 为什么适合 OOP？


1. 一切非基本类型都是对象

2. 支持单继承 + 多接口的稳定模型

3. 运行时多态是语言核心设计

4. JVM 促进 OOP 的安全性（字节码验证）

5. 强类型系统使对象模型更稳定



✅ 五、面试常问：Java OOP 的优点？

1. 模块化强（对象结构清晰）

2. 可复用性强（继承 + 组合）

3. 可维护性强（封装特性）

4. 高扩展性（多态 + 接口）

5. 可读性好，符合现实世界模型

🎯 六、面试 30 秒标准回答（你可以直接背）：

Java 的面向对象编程基于类与对象，通过封装、继承、多态和抽象四大特性组织代码。

封装隐藏实现细节；继承实现代码复用；多态让对象在运行时表现不同行为；抽象屏蔽复杂细节。

Java 的强类型系统、接口机制和运行时多态使其非常适合用于构建可维护、可扩展的企业级系统。

Java's object-oriented programming is based on classes and objects, organizing code through four major characteristics: encapsulation, inheritance, polymorphism, and abstraction. 

Encapsulation hides implementation details; inheritance enables code reuse; polymorphism allows objects to exhibit different behaviors at runtime; and abstraction masks complex details. 

Java's strong typing system, interface mechanism, and runtime polymorphism make it very suitable for building maintainable and scalable enterprise-level systems.




Object-oriented programming: aims to implement real-world entities like inheritance, hiding, polymorphism etc in programming. The main aim of OOP is to bind together the data and the functions that operate on them so that no other part of the code can access this data except that function.

OOPs Concepts:

Polymorphism
Inheritence
Encapsulation
Abstraction
Class
Object
Method
Message Passing



Polymorphism: Polymorphism refers to the ability of OOPs programming languages to differentiate between entities with the same name efficiently. This is done by Java with the help of the signature and declaration of these entities.

overload: When a method is invoked (§15.12), the number of actual arguments (and any explicit type arguments) and the compile-time types of the arguments are used, at compile time, to determine the signature of the method that will be invoked (§15.12.2). If the method that is to be invoked is an instance method, the actual method to be invoked will be determined at run time, using dynamic method lookup (§15.12.4).


For example:

public class Sum { 
  
    // Overloaded sum(). 
    // This sum takes two int parameters 
    public int sum(int x, int y) 
    { 
        return (x + y); 
    } 
  
    // Overloaded sum(). 
    // This sum takes three int parameters 
    public int sum(int x, int y, int z) 
    { 
        return (x + y + z); 
    } 
  
    // Overloaded sum(). 
    // This sum takes two double parameters 
    public double sum(double x, double y) 
    { 
        return (x + y); 
    } 
  
    // Driver code 
    public static void main(String args[]) 
    { 
        Sum s = new Sum(); 
        System.out.println(s.sum(10, 20)); 
        System.out.println(s.sum(10, 20, 30)); 
        System.out.println(s.sum(10.5, 20.5)); 
    } 
} 
Output:
30
60
31.0

Polymorphism in Java are mainly of 2 types:

Overloading in Java
Overriding in Java



Inheritence: Inheritance is an important pillar of OOP(Object Oriented Programming). It is the mechanism in java by which one class is allow to inherit the features(fields and methods) of another class.
Important terminology:
Super Class: The class whose features are inherited is known as superclass(or a base class or a parent class).
Sub Class: The class that inherits the other class is known as subclass(or a derived class, extended class, or child class). The subclass can add its own fields and methods in addition to the superclass fields and methods.
Reusability: Inheritance supports the concept of “reusability”, i.e. when we want to create a new class and there is already a class that includes some of the code that we want, we can derive our new class from the existing class. By doing this, we are reusing the fields and methods of the existing class.
The keyword used for inheritance is extends.
Syntax:

class derived-class extends base-class  
{  
   //methods and fields  
}  




Encapsulation: 
    Encapsulation is defined as the wrapping up of data under a single unit. It is the mechanism that binds together code and the data it manipulates. Another way to think about encapsulation is, it is a protective shield that prevents the data from being accessed by the code outside this shield.
Technically in encapsulation, the variables or data of a class is hidden from any other class and can be accessed only through any member function of own class in which they are declared.
As in encapsulation, the data in a class is hidden from other classes, so it is also known as data-hiding.
Encapsulation can be achieved by Declaring all the variables in the class as private and writing public methods in the class to set and get the values of variables.
Encapsulation




Abstraction: 
    Data Abstraction is the property by virtue of which only the essential details are displayed to the user.The trivial or the non-essentials units are not displayed to the user. Ex: A car is viewed as a car rather than its individual components.
Data Abstraction may also be defined as the process of identifying only the required characteristics of an object ignoring the irrelevant details. The properties and behaviours of an object differentiate it from other objects of similar type and also help in classifying/grouping the objects.

Consider a real-life example of a man driving a car. The man only knows that pressing the accelerators will increase the speed of car or applying brakes will stop the car but he does not know about how on pressing the accelerator the speed is actually increasing, he does not know about the inner mechanism of the car or the implementation of accelerator, brakes etc in the car. This is what abstraction is.

In java, abstraction is achieved by interfaces and abstract classes. We can achieve 100% abstraction using interfaces.



Class: A class is a user defined blueprint or prototype from which objects are created. It represents the set of properties or methods that are common to all objects of one type. In general, class declarations can include these components, in order:
Modifiers: A class can be public or has default access (Refer this for details).
Class name: The name should begin with a initial letter (capitalized by convention).
Superclass(if any): The name of the class’s parent (superclass), if any, preceded by the keyword extends. A class can only extend (subclass) one parent.
Interfaces(if any): A comma-separated list of interfaces implemented by the class, if any, preceded by the keyword implements. A class can implement more than one interface.
Body: The class body surrounded by braces, { }.
Object: It is a basic unit of Object Oriented Programming and represents the real life entities. A typical Java program creates many objects, which as you know, interact by invoking methods. An object consists of:
State : It is represented by attributes of an object. It also reflects the properties of an object.
Behavior : It is represented by methods of an object. It also reflects the response of an object with other objects.
Identity : It gives a unique name to an object and enables one object to interact with other objects.
Example of an object: dog

Blank Diagram - Page 1 (5)

Method: A method is a collection of statements that perform some specific task and return result to the caller. A method can perform some specific task without returning anything. Methods allow us to reuse the code without retyping the code. In Java, every method must be part of some class which is different from languages like C, C++ and Python.
Methods are time savers and help us to reuse the code without retyping the code.
Method Declaration

In general, method declarations has six components:

Access Modifier: Defines access type of the method i.e. from where it can be accessed in your application. In Java, there 4 type of the access specifiers.
public: accessible in all class in your application.
protected: accessible within the package in which it is defined and in its subclass(es)(including subclasses declared outside the package)
private: accessible only within the class in which it is defined.
default (declared/defined without using any modifier): accessible within same class and package within which its class is defined.
The return type: The data type of the value returned by the method or void if does not return a value.
Method Name: the rules for field names apply to method names as well, but the convention is a little different.
Parameter list: Comma separated list of the input parameters are defined, preceded with their data type, within the enclosed parenthesis. If there are no parameters, you must use empty parentheses ().
Exception list: The exceptions you expect by the method can throw, you can specify these exception(s).
Method body: it is enclosed between braces. The code you need to be executed to perform your intended operations.
methods in java

Message Passing: Objects communicate with one another by sending and receiving information to each other. A message for an object is a request for execution of a procedure and therefore will invoke a function in the receiving object that generates the desired results. Message passing involves specifying the name of the object, the name of the function and the information to be sent.



## OOP design:
What Are Class & Object?
It is the basic concept of OOP; an extended concept of the structure used in C. It is an abstract and user-defined data type. It consists of several variables and functions. The primary purpose of the class is to store data and information. The members of a class define the behaviour of the class. A class is the blueprint of the object, but also, we can say the implementation of the class is the object. The class is not visible to the world, but the object is.