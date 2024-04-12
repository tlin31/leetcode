Python notes:

## 1. enumerate:

1.
``` python
	l1 = ["eat","sleep","repeat"] 
	  
	# printing the tuples in object directly 
	for ele in enumerate(l1): 
	    print ele 

	# changing index and printing separately 
	for count,ele in enumerate(l1,100): 
	    print count,ele 

 ```
Output:

	(0, 'eat')
	(1, 'sleep')
	(2, 'repeat')

	100 eat
	101 sleep
	102 repeat

2.         index = {x: i for i, x in enumerate(A)}

	output: {'repeat': 2, 'sleep': 1, 'eat': 0}


3. can also enumerate(string) --> return index & char

4. for idx, s in enumerate(L, start = 1) --> start looping from the first element in L, but will go back to index 0 at the end. 

```python
	L = ['apples', 'bananas', 'oranges']
	for idx, s in enumerate(L, start = 1):
	  print("index is %d and value is %s" \
	         % (idx, s))
```
Output:
index is 1 and value is apples
index is 2 and value is bananas
index is 3 and value is oranges



## 2. Dictionary (work as hashmap in java):

1. iterate through dict:

print key & value
```python
d = {'a': 1, 'b': 2, 'c': 3}
for k, v in d.items():
  # k is now the key
  # v is the value
  print(k, v)
```


Print all key names in the dictionary, one by one:

for x in thisdict:
  print(x)


Print all values in the dictionary, one by one:

for x in thisdict.values():
  print(x)


2. get value of a key:

x = thisdict["model"]
x = thisdict.get("model")

3. change values:

thisdict = {
  "brand": "Ford",
  "model": "Mustang",
  "year": 1964
}
thisdict["year"] = 2018

4. check if a key exists:

if "model" in thisdict:


5. remove item:The pop() method removes the item with the specified key name:

thisdict = {
  "brand": "Ford",
  "model": "Mustang",
  "year": 1964
}
thisdict.pop("model")

6. delete dict:
The clear() method empties the dictionary:

thisdict = {
  "brand": "Ford",
  "model": "Mustang",
  "year": 1964
}
thisdict.clear()

7. copy a dict

Make a copy of a dictionary with the copy() method:

thisdict = {
  "brand": "Ford",
  "model": "Mustang",
  "year": 1964
}
mydict = thisdict.copy()





## 3. Set:

1. iterate 
s = {'a', 'b', 'c'}
for v in s:
  print(v)

2. create set:
	new_set = set()




## 4. collections.Counter    

A counter is a container that stores elements as dictionary keys, and their counts are stored as dictionary values.

```python
from collections import Counter

myList = [1,1,2,3,4,5,3,2,3,4,2,1,2,3]
print Counter(myList)

Counter({2: 4, 3: 4, 1: 3, 4: 2, 5: 1})
----------
print Counter(myList).items()
[(1, 3), (2, 4), (3, 4), (4, 2), (5, 1)]
----------
print Counter(myList).keys()
[1, 2, 3, 4, 5]
----------
print Counter(myList).values()
[3, 4, 4, 2, 1]
```


## 5. heapq --> priority queue

- used to represent a priority queue
- each time the smallest of heap element is popped(min heap)
- Whenever elements are pushed or popped, heap structure in maintained. 
- The heap[0] element also returns the smallest element each time.

### Operations on heap :

1. heapify(iterable) : convert the iterable into a heap data structure. i.e. in heap order.

2. heappush(heap, ele) : insert the element mentioned in its arguments into heap. The order is adjusted, so as heap structure is maintained.

3. heappop(heap) : remove and return the smallest element from heap. The order is adjusted, so as heap structure is maintained.

```python
	import heapq 
	  
	# initializing list 
	li = [5, 7, 9, 1, 3] 
	  
	# using heapify to convert list into heap 
	heapq.heapify(li) 
	  
	# printing created heap 
	print ("The created heap is : ",end="") 
	print (list(li)) 
	  
	# using heappush() to push elements into heap 
	heapq.heappush(li,4) 
	  
	# printing modified heap 
	print ("The modified heap after push is : ",end="") 
	print (list(li)) 
	  
	# using heappop() to pop smallest element 
	print ("The popped and smallest element is : ",end="") 
	print (heapq.heappop(li)) 

```
Output :

The created heap is : [1, 3, 9, 7, 5]
The modified heap after push is : [1, 3, 4, 7, 5, 9]
The popped and smallest element is : 1


4. heappushpop(heap, ele) : combines the functioning of both push and pop operations in one statement, increasing efficiency. Heap order is maintained after this operation.

5. heapreplace(heap, ele) : element is first popped, then the element is pushed.i.e, the value larger than the pushed value can be returned. heapreplace() returns the smallest value originally in heap regardless of the pushed element as opposed to heappushpop().

```python
	import heapq 
	  
	# initializing list 1 
	li1 = [5, 7, 9, 4, 3] 
	  
	# initializing list 2 
	li2 = [5, 7, 9, 4, 3] 
	  
	# using heapify() to convert list into heap 
	heapq.heapify(li1) 
	heapq.heapify(li2) 
	  
	# using heappushpop() to push and pop items simultaneously 
	print ("The popped item using heappushpop() is : ",end="") 
	print (heapq.heappushpop(li1, 2)) 
	  
	# using heapreplace() to push and pop items simultaneously 
	print ("The popped item using heapreplace() is : ",end="") 
	print (heapq.heapreplace(li2, 2)) 
```

Output :

The popped item using heappushpop() is : 2
The popped item using heapreplace() is : 3


6. nlargest(k, iterable, key = fun) : return the k largest elements from the iterable specified and satisfying the key if mentioned.

7. nsmallest(k, iterable, key = fun) :- This function is used to return the k smallest elements from the iterable specified and satisfying the key if mentioned.

```python
	import heapq 
	  
	# initializing list  
	li1 = [6, 7, 9, 4, 3, 5, 8, 10, 1] 
	  
	# using heapify() to convert list into heap 
	heapq.heapify(li1) 
	  
	# using nlargest to print 3 largest numbers 
	# prints 10, 9 and 8 
	print("The 3 largest numbers in list are : ",end="") 
	print(heapq.nlargest(3, li1)) 
	  
	# using nsmallest to print 3 smallest numbers 
	# prints 1, 3 and 4 
	print("The 3 smallest numbers in list are : ",end="") 
	print(heapq.nsmallest(3, li1)) 
```

Output :

The 3 largest numbers in list are : [10, 9, 8]
The 3 smallest numbers in list are : [1, 3, 4]


## 6. itertools

1. zip_longest()

	p, q, …

	(p[0], q[0]), (p[1], q[1]), …

	zip_longest('ABCD', 'xy', fillvalue='-') --> Ax By C- D-


## 7. String or list

1. for str & list: 
	[::-1] 顺序相反操作		[-1] 读取倒数第一个元素		[3::-1] 从下标为3（从0开始）的元素开始翻转读取

eg：
    a=[1,2,3,4,5]
    b=a[::-1]
	--> output: b = [5, 4, 3, 2, 1]
	b=a[3::-1]
	--> output: b = [4, 3, 2, 1]

2. bisect

- 1. bisect(list, num, beg, end) :
	- returns the position in the sorted list, where the number passed in argument can be placed so as to maintain the resultant list in sorted order. 
	- If the element is already present in the list, the right most position where element has to be inserted is returned. 
	- This function takes 4 arguments, list which has to be worked with, number to insert, starting position in list to consider, ending position which has to be considered.

- 2. bisect_left(list, num, beg, end) :
	- returns the position in the sorted list, where the number passed in argument can be placed so as to maintain the resultant list in sorted order. 
	- If the element is already present in the list, the left most position where element has to be inserted is returned. 
	- This function takes 4 arguments, list which has to be worked with, number to insert, starting position in list to consider, ending position which has to be considered.

- 3. bisect_right(list, num, beg, end) :
	- This function works similar to the “bisect()” and mentioned above.

```python

import bisect 
  
# initializing list 
li = [1, 3, 4, 4, 4, 6, 7] 
  
# using bisect() to find index to insert new element --> returns 5 ( right most possible index ) 
print ("The rightmost index to insert, so list remains sorted is  : ", end="") 
print (bisect.bisect(li, 4)) 
  
# using bisect_left() to find index to insert new element --> returns 2 ( left most possible index ) 
print ("The leftmost index to insert, so list remains sorted is  : ", end="") 
print (bisect.bisect_left(li, 4)) 
  
# using bisect_right() to find index to insert new element 
# returns 4 ( right most possible index ) 
print ("The rightmost index to insert, so list remains sorted is  : ", end="") 
print (bisect.bisect_right(li, 4, 0, 4)) 

```


- 4. append() and extend()
	- 1) Append: Adds its argument as a single element to the end of a list. 
	             The length of the list increases by one.
	             If you append another list onto a list, the parameter list will be a single object at the end of the list.


	syntax: 
		my_list.append(object)

		my_list = ['geeks', 'for', 'geeks'] 
		another_list = [6, 0, 4, 1] 

	- 2) extend(): Iterates over its argument and adding each element to the list and extending the list. The length of the list increases by number of elements in it’s argument.

	syntax: 
	my_list.extend(iterable) 

	my_list = ['geeks', 'for'] 
	another_list = [6, 0, 4, 1] 
	my_list.extend(another_list) 
	
Note: A string is an iterable, so if you extend a list with a string, you’ll append each character as you iterate over the string.

	my_list = ['geeks', 'for', 6, 0, 4, 1] 
	my_list.extend('geeks') 
	print my_list 
	Output:

	['geeks', 'for', 6, 0, 4, 1, 'g', 'e', 'e', 'k', 's']

Time Complexity:
Append has constant time complexity i.e.,O(1).
Extend has time complexity of O(k). Where k is the length of list which need to be added.

- 5. length: len(list)




## 8. Set

- 1. create a set:
	set1 = set()
	set1 = set(a, b, c)

- 2. delete an element:
	set1.discard(x)


## 9. Math
- 1. // : floor division
	ex. 5 //2 = 2




## 10. 2-D matrix 

- 1. Creates a list containing 5 lists, each of 8 items, all set to 0

```python
	w, h = 8, 5;
	Matrix = [[0 for x in range(w)] for y in range(h)] 

You can now add items to the list:
	Matrix[0][0] = 1
	Matrix[6][0] = 3 # error! range... 
	Matrix[0][6] = 3 # valid

Note that the matrix is "y" address major, in other words, the "y index" comes before the "x index".

	print Matrix[0][0] # prints 1
	x, y = 0, 6 
	print Matrix[x][y] # prints 3; be careful with indexing! 

```


- 2. Another way

```python
matrix = [[0] * 5 for i in range(5)] or  dp = [[0] * (n) for _ in range(n)] 


```


## 11. deque


- Deque (Doubly Ended Queue) in python is implemented using the module “collections“. Deque is preferred over list in the cases where we need quicker append and pop operations from both the ends of container, as deque provides an O(1) time complexity for append and pop operations as compared to list which provides O(n) time complexity.

Operations on deque :

1. append() :- This function is used to insert the value in its argument to the right end of deque.

2. appendleft() :- This function is used to insert the value in its argument to the left end of deque.

3. pop() :- This function is used to delete an argument from the right end of deque.

4. popleft() :- This function is used to delete an argument from the left end of deque.

5. index(ele, beg, end) :- This function returns the first index of the value mentioned in arguments, starting searching from beg till end index.

6. insert(index, element) :- This function inserts the value mentioned in arguments(element) at index(i) specified in arguments.

7. remove() :- This function removes the first occurrence of value mentioned in arguments.

8. count() :- This function counts the number of occurrences of value mentioned in arguments.

9. extend(iterable) :- This function is used to add multiple values at the right end of deque. The argument passed is an iterable.


10. extendleft(iterable) :- This function is used to add multiple values at the left end of deque. The argument passed is an iterable. Order is reversed as a result of left appends.

11. reverse() :- This function is used to reverse order of deque elements.

12. rotate() :- This function rotates the deque by the number specified in arguments. If the number specified is negative, rotation occurs to left. Else rotation is to right.



```python

# Python code to demonstrate working of 
# append(), appendleft(), pop(), and popleft() 

# importing "collections" for deque operations 
import collections 

# initializing deque 
de = collections.deque([1,2,3]) 

# using append() to insert element at right end 
# inserts 4 at the end of deque 
de.append(4) 

# printing modified deque 
print ("The deque after appending at right is : ") 
print (de) 

# using appendleft() to insert element at right end 
# inserts 6 at the beginning of deque 
de.appendleft(6) 

# printing modified deque 
print ("The deque after appending at left is : ") 
print (de) 

# using pop() to delete element from right end 
# deletes 4 from the right end of deque 
de.pop() 

# printing modified deque 
print ("The deque after deleting from right is : ") 
print (de) 

# using popleft() to delete element from left end 
# deletes 6 from the left end of deque 
de.popleft() 

# printing modified deque 
print ("The deque after deleting from left is : ") 
print (de) 
```