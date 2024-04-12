## Compare

### implements Comparator

- !! initially, sort by ascending order, smaller in front.
- java compare(x,y) method:
    0: if (x==y)
    -1: if (x < y)
    1: if (x > y)


- 1. if want increasing order: compare(int x, int y) and return (x - y);
```java
        public int compare(Student a, Student b) { cc
            return a.rollno - b.rollno; 
        } 

        the output will be: smaller rollno, bigger rollno
```


- 2. if comparining two intervals:
```java
        Arrays.sort(intervals, (i1, i2) -> Integer.compare(i1[0], i2[0]));
```

- 3. locate Pair<key, value> objects for specific key = timestamp using binary search
```java
        int i = Collections.binarySearch(A, new Pair<Integer, String>(timestamp, "{"),
                (a, b) -> Integer.compare(a.getKey(), b.getKey()));

```


- 4. In java 8, compare 2 objects with multiple fields: .thenComparing()

```java

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
 
public class MultipleFieldSorter
{
    public static void main(String[] args)
    {
        List<Employee> list = Arrays.asList(new Employee(1, "A", "B", 34),
                                        new Employee(4, "C", "D", 30),
                                        new Employee(3, "B", "A", 31),
                                        new Employee(2, "D", "C", 25));
 
        Collections.sort(list, new FirstNameSorter()
                                .thenComparing(new LastNameSorter())
                                .thenComparing(new AgeSorter()));
 
        System.out.println(list);
    }
}
```

- 5. Add comparator for priority queue:

    小的在前:
        Queue<int[]> pq = new PriorityQueue<>((a,b) -> (Integer.compare(a[0], b[0])));

    same as:
        Queue<int[]> pq = new PriorityQueue<>((a,b) -> (a[0] - b[0]));

    compare Map.Entry
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
             (a,b) -> a.getValue()==b.getValue() ? b.getKey().compareTo(a.getKey()) : a.getValue()-b.getValue()
        );

    compare heights[]
        PriorityQueue<Integer> left = new PriorityQueue<>((a,b)->(heights[a]==heights[b])?b-a:heights[a]-heights[b]);


- (old) if comparining a new object:
```java
   private class IntervalComparator implements Comparator<Interval> {
        @Override
        public int compare(Interval a, Interval b) {
            return a.start < b.start ? -1 : a.start == b.start ? 0 : 1;
        }
    } 

    --------- use it
    Collections.sort(intervals, new IntervalComparator());

    ---------
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[0], b[0]);
            }
        });

```

### Collections.sort:

- java.util.Collections.sort() method is present in java.util.Collections class. 
- sort the elements present in the specified list of Collection in ascending order.
- can sort the elements of Array as well as linked list, queue and many more present in it.
- Example:
    Let us suppose that our list contains
    {"Geeks For Geeks", "Friends", "Dear", "Is", "Superb"}

    After using Collection.sort(), we obtain a sorted list as
    {"Dear", "Friends", "Geeks For Geeks", "Is", "Superb"}

```java
import java.util.*; 
  
public class Collectionsorting 
{ 
    public static void main(String[] args) 
    { 
        ArrayList<String> al = new ArrayList<String>(); 
        al.add("Geeks For Geeks"); 
        al.add("Friends"); 
        al.add("Dear"); 
        al.add("Is"); 
        al.add("Superb"); 
  
        //Collections.sort method is sorting the elements of ArrayList in ascending order.
        Collections.sort(al); 
  
        // Let us print the sorted list 
        System.out.println("List after the use of" + " Collection.sort() :\n" + al); 
    } 
} 

```

- Difference: 
Arrays.sort works for arrays which can be of primitive data type also. 
Collections.sort() works for objects Collections like ArrayList, LinkedList, etc.
We can use Collections.sort() to sort an array after creating a ArrayList of given array items.






## Iterator

### Iterator：
- java.util package has public interface Iterator and contains three methods:

  1. boolean hasNext(): It returns true if Iterator has more element to iterate.
  2. Object next(): It returns the next element in the collection until the hasNext()method return true. This method throws ‘NoSuchElementException’ if there is no next element.
  3. void remove(): It removes the current element in the collection. This method throws ‘IllegalStateException’ if this function is called before next( ) is invoked.

```java

public static void main(String[] args) 
    { 
        ArrayList<String> list = new ArrayList<String>(); 
  
        list.add("A"); 
        list.add("B"); 
        list.add("C"); 
        list.add("D"); 
        list.add("E"); 
  
        // Iterator to traverse the list 
        Iterator iterator = list.iterator(); 
  
        System.out.println("List elements : "); 
  
        while (iterator.hasNext()) 
            System.out.print(iterator.next() + " "); 
  
        System.out.println(); 
    } 

```
### ListIterator --> 2 directions iterate

‘ListIterator’ in Java is an Iterator which allows users to traverse Collection in both direction. It contains the following methods:

  1. void add(Object object): It inserts object immediately before the element that is returned by the next( ) function.
  2. boolean hasNext( ): It returns true if the list has a next element.
  3. boolean hasPrevious( ): It returns true if the list has a previous element.
  4. Object next( ): It returns the next element of the list. It throws ‘NoSuchElementException’ if there is no next element in the list.
  5. Object previous( ): It returns the previous element of the list. It throws ‘NoSuchElementException’ if there is no previous element.
  6. void remove( ): It removes the current element from the list. It throws ‘IllegalStateException’ if this function is called before next( ) or previous( ) is invoked.

```java
    public static void main(String[] args) 
    { 
        ArrayList<String> list = new ArrayList<String>(); 
  
        list.add("A"); 
        list.add("B"); 
        list.add("C"); 
        list.add("D"); 
        list.add("E"); 
  
        // ListIterator to traverse the list 
        ListIterator iterator = list.listIterator(); 
  
        // Traversing the list in forward direction   
        while (iterator.hasNext()) 
            System.out.print(iterator.next() + " "); 
    
        // Traversing the list in backward direction 
        System.out.println("Displaying list elements in backward direction : "); 
  
        while (iterator.hasPrevious()) 
            System.out.print(iterator.previous() + " "); 
  
        System.out.println(); 
    } 
```


### Method implement iterator

```java

public class Solution implements Iterator<Integer> {
 private Iterator<List<Integer>> rowIter;
 private Iterator<Integer> colIter;
 public Solution(List<List<Integer>> vec2d) {
 rowIter = vec2d.iterator();
 colIter = Collections.emptyIterator();
 }
 
```






## String

### get char in a string

1. str.charAt(index)

2. char inChar = str.toCharArray()

3. getChars(int srcBegin, int srcEnd, char dst[], int dstBegin) : convert part of string to character array.

### get length

    int len = s.length()

### Split String

- sentence.split("\\s+"): s+ means the 空格

### Change a char in string
- use string builder

```java
private String swap(String str, int i, int j) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i, str.charAt(j));
        sb.setCharAt(j, str.charAt(i));
        return sb.toString();
    }
```







## continue、break和return的区别：

continue：跳出本次循环继续下一次循环

break：   跳出循环体，继续执行循环外的函数体

return:   跳出整个函数体，函数体后面的部分不再执行


### Continue 可以用于在找到null之前结束
```java
        if (tree.rightNode == null)
           break;
```





### Long number checking equality
-  for long number, need to check it equals to "0L"




## Random number generator

### Math.random()

- Math.random() 方法生成[0, 1)范围内的double类型随机数
- Math.random() 线程安全，多线程环境能被调用；
- 如无特殊需求，则使用(int)(Math.random()*n)的方式生成随机数即可。

- 如果想求 0 ~ 6的随机数，用：
    Integer res = (int)(Math.random() * n);

## Construct solution class and write tests

```java
        public class MinimumCostwithAtMostKStops {

            public class Solution {
                public int minCost(List<String> lines, String source, String target, int k) {
            }

            public static class UnitTest {
                @Test
                public void test1() {
                    Solution sol = new MinimumCostwithAtMostKStops().new Solution();
                    List<String> lines = new ArrayList<>();
                    lines.add("A->B,100");
                    lines.add("A->C,400");
                    lines.add("B->C,100");
                    lines.add("C->D,100");
                    lines.add("C->A,10");
                    assertEquals(-1, sol.minCost(lines, "A", "D", 0));
                    assertEquals(500, sol.minCost(lines, "A", "D", 1));
                    assertEquals(300, sol.minCost(lines, "A", "D", 2));
                }
            }
        }
```

## Get position of a letter in 26 letter or a num in 0~9

### int Index of a char in 26 letters
        char letter = 'x'
        int index = letter - 'a';


### int Index of a num
        char num = '9';
        int index = num - '0';

### Get char if know its index 

      sb.append((char) (i + 'a'));


## Fast I/O in Java
https://www.geeksforgeeks.org/fast-io-in-java-in-competitive-programming/

### 1. Scanner Class 
- easy, less typing, but not recommended very slow
- In most of the cases we get TLE while using scanner class. It uses built-in nextInt(), nextLong(), nextDouble methods to read the desired object after initiating scanner object with input stream.(eg System.in). The following program many a times gets time limit exceeded verdict and therefore not of much use.

```java
// Working program using Scanner 
import java.io.BufferedReader; 
import java.io.InputStreamReader; 
import java.util.Scanner; 
public class Main 
{ 
    public static void main(String[] args) 
    { 
        Scanner s = new Scanner(System.in); 
        int n = s.nextInt(); 
        int k = s.nextInt(); 
        int count = 0; 
        while (n-- > 0) 
        { 
            int x = s.nextInt(); 
            if (x%k == 0) 
               count++; 
        }  
        System.out.println(count); 
    } 
} 
 ```

## 2.BufferedReader
- fast, but not recommended as it requires lot of typing
- The Java.io.BufferedReader class reads text from a character-input stream, buffering characters so as to provide for the efficient reading of characters, arrays, and lines. With this method we will have to parse the value every time for desired type. Reading multiple words from single line adds to its complexity because of the use of Stringtokenizer and hence this is not recommended. This gets accepted with a running time of approx 0.89 s. but still as you can see it requires a lot of typing all together and therefore method 3 is recommended.

``` java
// Working program using BufferedReader 
import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.util.StringTokenizer; 
  
public class Main 
{ 
    public static void main(String[] args) throws IOException 
    { 
  
        BufferedReader br = new BufferedReader( 
                              new InputStreamReader(System.in)); 
  
        StringTokenizer st = new StringTokenizer(br.readLine()); 
        int n = Integer.parseInt(st.nextToken()); 
        int k = Integer.parseInt(st.nextToken()); 
        int count = 0; 
        while (n-- > 0) 
        { 
            int x = Integer.parseInt(br.readLine()); 
            if (x%k == 0) 
               count++; 
        } 
        System.out.println(count); 
    } 
} 
```


## 3.Userdefined FastReader Class
- uses bufferedReader and StringTokenizer
- This method uses the time advantage of BufferedReader and StringTokenizer and the advantage of user defined methods for less typing and therefore a faster input altogether. This gets accepted with a time of 1.23 s and this method is very much recommended as it is easy to remember and is fast enough to meet the needs of most of the question in competitive coding.

```java
// Working program with FastReader 
import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.util.Scanner; 
import java.util.StringTokenizer; 
  
public class Main 
{ 
    static class FastReader 
    { 
        BufferedReader br; 
        StringTokenizer st; 
  
        public FastReader() 
        { 
            br = new BufferedReader(new
                     InputStreamReader(System.in)); 
        } 
  
        String next() 
        { 
            while (st == null || !st.hasMoreElements()) 
            { 
                try
                { 
                    st = new StringTokenizer(br.readLine()); 
                } 
                catch (IOException  e) 
                { 
                    e.printStackTrace(); 
                } 
            } 
            return st.nextToken(); 
        } 
  
        int nextInt() 
        { 
            return Integer.parseInt(next()); 
        } 
  
        long nextLong() 
        { 
            return Long.parseLong(next()); 
        } 
  
        double nextDouble() 
        { 
            return Double.parseDouble(next()); 
        } 
  
        String nextLine() 
        { 
            String str = ""; 
            try
            { 
                str = br.readLine(); 
            } 
            catch (IOException e) 
            { 
                e.printStackTrace(); 
            } 
            return str; 
        } 
    } 
  
    public static void main(String[] args) 
    { 
        FastReader s=new FastReader(); 
        int n = s.nextInt(); 
        int k = s.nextInt(); 
        int count = 0; 
        while (n-- > 0) 
        { 
            int x = s.nextInt(); 
            if (x%k == 0) 
               count++; 
        } 
        System.out.println(count); 
    } 
} 

```














