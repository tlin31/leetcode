706. Design HashMap - Easy

Design a HashMap without using any built-in hash table libraries.

To be specific, your design should include these functions:
	
	1. put(key, value) : Insert a (key, value) pair into the HashMap. If the value already exists in 
	                     the HashMap, update the value.
	2. get(key): Returns the value to which the specified key is mapped, or -1 if this map contains 
	             no mapping for the key.
	3. remove(key) : Remove the mapping for the value key if this map contains the mapping for the key.

Example:

MyHashMap hashMap = new MyHashMap();
hashMap.put(1, 1);          
hashMap.put(2, 2);         
hashMap.get(1);            // returns 1
hashMap.get(3);            // returns -1 (not found)
hashMap.put(2, 1);          // update the existing value
hashMap.get(2);            // returns 1 
hashMap.remove(2);          // remove the mapping for 2
hashMap.get(2);            // returns -1 (not found) 

Note:

All keys and values will be in the range of [0, 1000000].
The number of operations will be in the range of [1, 10000].
Please do not use the built-in HashMap library.



******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



2 main issues 

	1). hash function design: 
	    the purpose of hash function is to map a key value to an address in the storage space, 
	    similarly to the system that we assign a postcode to each mail address. As one can image, 
	    for a good hash function, it should map different keys evenly across the storage space, 
	    so that we dont end up with the case that the majority of the keys are concentrated in a few 
	    spaces.

	2). collision handling: 
		2 different keys are mapped to the same address, which is what we call 'collision'. Since the 
		collision is inevitable, it is important that we have a strategy to handle the collision.


=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	- use modulo operator as the hash function, since the key value is of integer type.
	  in order to minimize the potential collisions, it is advisable to use a prime number as the 
	  base of modulo, e.g. 2069.

	- We organize the storage space as an array where each element is indexed with the output value 
	  of the hash function.

	- In case of collision, where two different keys are mapped to the same address, we use a bucket 
	  to hold all the values. The bucket is a container that hold all the values that are assigned 
	  by the hash function. 

	  We could use either a LinkedList or an Array to implement the bucket data structure.

Algorithm
	- For a given key value, first we apply the hash function to generate a hash key, which corresponds 
	  to the address in our main storage. With this hash key, we would find the bucket where the value 
	  should be stored.

	-  Now that we found the bucket, we simply iterate through the bucket to check if the desired 
	   <key, value> pair does exist.
	
	-  用array来实现random access，保证平均情况下hashmap的put和get是O（1）的，如果出现了collision，那么可以选择
	   使用linkedlist来处理collision的情况，所以在put get 和remove的时候，需要一个while loop来查找所有
	   collision的元素，导致worst case是O（n）

	   因此我在这里把listnode改变了一下结构，使其能够同时存储key和value，便于查找，同时需要保证key是final无法被改变。
	   而根据题意这个size最大是10000所以array的size是10001来保证array[10000]可以被取值。


class MyHashMap {
	class Node{
		final int key;
		int value;
		Node next;
		public Node(int key, int value) {
			this.key = key;
			this.value = value;
		}
	}
	final int SIZE = 10001;
	Node[] array;
	public MyHashMap() {
		 array = new Node[SIZE];
	}

public void put(int key, int value) {
    int index = hash(key); 
    Node head = array[index];

    Node node = head;

    // update the value if key already existed
    while (node != null) {
        if (node.key == key) {
            node.value = value;
            return;
        }
        node = node.next;
    }

    // a new node with new key, add at the begining of the collision chain
    Node newNode = new Node(key, value);
    newNode.next = head;
    array[index] = newNode;
}


public int get(int key) {
    int index = hash(key);
    Node node = array[index];
    while (node != null) {
        if (node.key == key) return node.value;
        node = node.next;
    }
    return -1;
}

// need to modify this remove
public void remove(int key) {
    int index = hash(key);
    Node node = array[index];
    while (node != null) {
        if (node.key == key) {
            node.value = -1;
            return;
        }
        node = node.next;
    }

}

private int hash(int key) {
    return Integer.hashCode(key) % SIZE;
}

----------
class MyHashMap {
    private static final double LOAD_FACTOR = 0.75;
    private Node[] nodes;
    private int size; // number of keys

    /** Initialize your data structure here. */
    public MyHashMap() {
        nodes = new Node[5];
        size = 0;
    }
    
    /** value will always be non-negative. */
    public void put(int key, int value) {
        int idx = hash(key);
        for (Node x = nodes[idx]; x != null; x = x.next) {
            if (x.key == key) {
                x.value = value;
                return;
            }
        }
        nodes[idx] = new Node(key, value, nodes[idx]);
        size++;
        
        double loadFactor = (double) size / nodes.length;
        if (loadFactor > LOAD_FACTOR) {
            rehash();
        }
    }
    
    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int key) {
        int idx = hash(key);
        for (Node x = nodes[idx]; x != null; x = x.next) {
            if (x.key == key) {
                return x.value;
            }
        }
        return -1;
    }
    
    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {
        int idx = hash(key);
        Node pre = new Node(-1, -1, nodes[idx]); // sentinal node before list head
        for (Node prev = pre; prev.next != null; prev = prev.next) {
            if (prev.next.key == key) {
                prev.next = prev.next.next;
                break;
            }
        }
        nodes[idx] = pre.next;
		size--;
    }
    
    private int hash(int key) {
        return key % nodes.length;
    }
    
    private void rehash() {
        Node[] tmp = nodes;
        nodes = new Node[tmp.length * 2];
        size = 0;
        for (Node head: tmp) {
            for (Node x = head; x != null; x = x.next) {
                put(x.key, x.value);
            }
        }
    }
    
    class Node {
        int key;
        int value;
        Node next;

        public Node(int key, int value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}


=======================================================================================================
method 2:

Stats:

	- The general implementation of HashMap uses bucket which is basically a chain of linked lists and each node containing <key, value> pair.
So if we have duplicate nodes, that doesn't matter - it will still replicate each key with it's value in linked list node.
When we insert the pair (10, 20) and then (10, 30), there is technically no collision involved. We are just replacing the old value with the new value for a given key 10, since in both cases, 10 is equal to 10 and also the hash code for 10 is always 10.
Collision happens when multiple keys hash to the same bucket. In that case, we need to make sure that we can distinguish between those keys. Chaining collision resolution is one of those techniques which is used for this.
Just for the information. In JDK 8, HashMap has been tweaked so that if keys can be compared for ordering, then any densely-populated bucket is implemented as a tree, so that even if there are lots of entries with the same hash-code, the complexity isO(log n).
	- 


Method:

	-	
	-	
class MyHashMap
{
	ListNode[] nodes = new ListNode[10000];

	public int get(int key)
	{
		int index = getIndex(key);
		ListNode prev = findElement(index, key);
		return prev.next == null ? -1 : prev.next.val;
	}
	
	public void put(int key, int value)
	{
		int index = getIndex(key);
		ListNode prev = findElement(index, key);
		
		if (prev.next == null)
			prev.next = new ListNode(key, value);
		else 
			prev.next.val = value;
	}

	public void remove(int key)
	{
		int index = getIndex(key);
        ListNode prev = findElement(index, key);
			
        if(prev.next != null)
		    prev.next = prev.next.next;
	}

	private int getIndex(int key)
	{	
		return Integer.hashCode(key) % nodes.length;
	}

	private ListNode findElement(int index, int key)
	{
		if(nodes[index] == null)
			return nodes[index] = new ListNode(-1, -1);
        
        ListNode prev = nodes[index];
		
		while(prev.next != null && prev.next.key != key)
		{
			prev = prev.next;
		}
		return prev;
	}

	private static class ListNode
	{
		int key, val;
		ListNode next;

		ListNode(int key, int val)
		{
			this.key = key;
			this.val = val;
		}
	}
}

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



