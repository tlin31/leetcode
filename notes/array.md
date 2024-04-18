## Array

### declare the arraylist

不用写里面的type

```java
    1.	List<List<Integer>> list = new ArrayList<>();
    	int[] intArray; 
    	intArray = new int[20]
    2.  List < String > r = (ArrayList < String > ) currLst.clone()
    3.  List < String > r = new ArrayList<>(tempList)					--> initialize with templist
    4. ArrayList<String> str = new ArrayList<String>();
    5. ArrayList<String> words = new ArrayList<String>(
            Arrays.asList("A",
                          "B",
                          "C"));
```

### comparator

- A.compareTo(B): if == 0, maintain the original order
                  if == 1, first is larger, put it to the second --> original A > B, now order is [B,A]
                  if == -1, first is smaller


### sort

```java

----Method 1:
        Arrays.sort(nums); //time: O(n*logn)

----Method 2:

        class Sortbyroll implements Comparator<Student> 
        { 
            // Used for sorting in ascending order of roll number 
            public int compare(Student a, Student b) 
            { 
                return a.rollno - b.rollno; 
            } 
        } 
      
        Arrays.sort(arr, new Sortbyroll()); 
  
----Method 3:
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]); //last element of interval
        Arrays.sort(intervals, (i1, i2) -> Integer.compare(i1[0], i2[0])); //first elem of interval

        //sort (interval data type -> using the standard of )

---Method 4:   

        // Sort intervals
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[0], b[0]);
            }
        });

--- Method 5
        private class IntervalComparator implements Comparator<Interval> {
            @Override
            public int compare(Interval a, Interval b) {
                return a.start < b.start ? -1 : a.start == b.start ? 0 : 1;
            }
        }
        Collections.sort(intervals, new IntervalComparator());

```


#### Arrays.sort() vs Collections.sort()
- Arrays.sort works for arrays which can be of primitive data type also. 
- Collections.sort() works for objects Collections like ArrayList, LinkedList, etc.


### length
```java
	a.length
```


### change from string to int
```java
 int m = str.charAt(i) - '0';
 ```


### change from list to array
```java
        List<int[]> res = new ArrayList<>();
        return res.toArray(new int[res.size()][]); // pass in number of elements in the list.

 ```

cast arraylist to list of list

        public List<List<Integer>> threeSum(int[] nums) {
        Set<List<Integer>> res  = new HashSet<>();
        if(nums.length==0) return new ArrayList<>(res);


to return 1 element of List

        Arrays.asList(nums[i],nums[j],nums[k]);


### static way to copy an array --> System.arraycopy(Object src,int srcPos,
									Object dest,int destPos,int length)

src:源数组；		srcPos:源数组要复制的起始位置；
dest:目的数组；	destPos:目的数组放置的起始位置；		length:复制的长度。

1. 注意：src and dest都必须是同类型或者可以进行转换类型的数组．
2. 有趣的是这个函数可以实现自己到自己复制，比如：
	int[] fun ={0,1,2,3,4,5,6}; 
	System.arraycopy(fun,0,fun,3,3);
	则结果为：{0,1,2,0,1,2,6};

3. 实现过程是这样的，先生成一个长度为length的临时数组,将fun数组中srcPos 
	到srcPos+length-1之间的数据拷贝到临时数组中，再执行System.arraycopy(临时数组,0,fun,3,3).

### Fill in array

        for (int[] memoRow : memo)
            Arrays.fill(memoRow, -1);

### 2d array 

### declare


    data_type[][] array_Name = new data_type[no_of_rows][no_of_columns];





#### length

1. row = foo.length
2. column = foo[0].length


    int[][] foo = new int[][] {
        new int[] { 1, 2, 3 },
        new int[] { 1, 2, 3, 4},
    };

    System.out.println(foo.length); //2
    System.out.println(foo[0].length); //3
    System.out.println(foo[1].length); //4


## Arraylist & linked list
1. ArrayList:

- Implemented with the concept of dynamic array.

    ArrayList<Type> arrL = new ArrayList<Type>();

- ArrayList needs to be update its index if you insert something anywhere except at the end of array.

2. LinkedList:
- Implemented with the concept of doubly linked list.

    LinkedList<Type> linkL = new LinkedList<Type>();


- Insertions are easy and fast in LinkedList as compared to ArrayList because there is no risk of resizing array and copying content to new array if array gets full which makes adding into ArrayList of O(n) in worst case, while adding is O(1) operation in LinkedList
 
- Removal is better in LinkedList than ArrayList due to same reasons as insertion.
- LinkedList has more memory overhead than ArrayList because in ArrayList each index only holds actual object (data) but in case of LinkedList each node holds both data and address of next and previous node.

- Both LinkedList and ArrayList require O(n) time to find if an element is present or not. However we can do Binary Search on ArrayList if it is sorted and therefore can search in O(Log n) time.

```java
import java.util.*;
  
public class ArrayListLinkedListExample 
{ 
    public static void main(String[] args) 
    { 
        ArrayList<String> arrlistobj = new ArrayList<String>(); 
        arrlistobj.add("0. Practice.GeeksforGeeks.org"); 
        arrlistobj.add("1. Quiz.GeeksforGeeks.org"); 
        arrlistobj.add("2. Code.GeeksforGeeks.org"); 
        arrlistobj.remove(1);  // Remove value at index 2 
        System.out.println("ArrayList object output :" +  arrlistobj); 
  
        // Checking if an element is present. 
        if (arrlistobj.contains("2. Code.GeeksforGeeks.org")) 
            System.out.println("Found"); 
        else
            System.out.println("Not found"); 
  
  
        LinkedList llobj = new LinkedList(); 
        llobj.add("0. Practice.GeeksforGeeks.org"); 
        llobj.add("1. Quiz.GeeksforGeeks.org"); 
        llobj.add("2. Code.GeeksforGeeks.org"); 
        llobj.remove("1. Quiz.GeeksforGeeks.org"); 
        System.out.println("LinkedList object output :" + llobj); 
  
        // Checking if an element is present. 
        if (llobj.contains("2. Code.GeeksforGeeks.org")) 
            System.out.println("Found"); 
        else
            System.out.println("Not found"); 
    } 
} 


Output:
ArrayList object output :[0. Practice.GeeksforGeeks.org, 2. Code.GeeksforGeeks.org]
Found

LinkedList object output :[0. Practice.GeeksforGeeks.org, 2. Code.GeeksforGeeks.org]
Found
```


