Flatten array of array

Question: implement :

我被考的remove是remove上一个getNext() get到的数。就是说不remove 还没getNext的数的意思。。
举个例子：
如果函数被call的顺序是这样的：hasnext() getnext() hasnext() get‍‌‌‍‍‌‌‌‍‌‌‌‍‍‌‌‍‌‍next() remove()
假设data是 [［1，2］［3］］
那么返回--> true， 1，true, 2, void


Assumption:
1. input is a list of list, implement the iterator for the list of list
2. outer list may contain null, for example [[1,2], null, [3,4]] --> 1,2,3,4
3. inner list may contain null, for example [[1,2,null,3],[4,5]] -->  1,2,null,3,4,5

Approach:
we can just use the list iterator to implement hasNext and next

if inner list iterator is null || does not has next (reach end) && outer list iterator has next, 
we move forward outer list iterator to get the list and generate a new iterator to use.
 

import java.util.*;

class Test {
    public static void main(String[] args) {
        List<List<Integer>> list = new ArrayList<>();
        list.add(new ArrayList<>(Arrays.asList(1,2,3)));
        list.add(new ArrayList<>());
        list.add(new ArrayList<>());
        list.add(new ArrayList<>(Arrays.asList(1,null,2)));

        BListIterator iter = new BListIterator(list);
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }

        // check remoce
        iter = new BListIterator(list);
        while (iter.hasNext()) {
            iter.next();
            iter.remove();
        }
        System.out.println(list);
    }
    // Output:
    //     1
    //     2
    //     3
    //     1
    //     null
    //     2
    //     [[], [], [], []]
}


public class BListIterator implements Iterator<Integer>  {
    // go through the list
    private Iterator<List<Integer>> listIter;  
    private Iterator<Integer> arrayIter;

    public BListIterator(List<List<Integer>> list)  {
        listIter = list.iterator();
        arrayIter = null;
    }

    @Override
    public boolean hasNext() {
        // first element or last element in a sublist, and more sublist next to it
        while ((arrayIter == null || !arrayIter.hasNext()) && listIter.hasNext()) {
            // goes to next list
            List<Integer> next = listIter.next();
            if (next != null) {
                arrayIter = next.iterator();
            }
        }
        return arrayIter != null && arrayIter.hasNext();
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return arrayIter.next();
    }

    @Override
    public void remove() {
        if (arrayIter == null) {
            return;
        }
        arrayIter.remove();
        System.out.println("remove it!");
    }
}
