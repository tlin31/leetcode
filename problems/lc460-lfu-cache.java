460. LFU Cache - Hard

Design and implement a data structure for Least Frequently Used (LFU) cache. It should support 
the following operations: get and put.

	1. get(key): Get the value (will always be positive) of the key if the key exists in the cache, 
			     otherwise return -1.

	2. put(key, value): Set or insert the value if the key is not already present. When the cache 
	                    reaches its capacity, it should invalidate the least frequently used item 
	                    before inserting a new item. 
	                    tie  --> the least recently used key would be evicted.

Note that the number of times an item is used is the number of calls to the get and put functions for that item since it was inserted. This number is set to zero when the item is removed.

 

Follow up:
Could you do both operations in O(1) time complexity?

 

Example:

LFUCache cache = new LFUCache( 2 /* capacity */ );

cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // returns 1
cache.put(3, 3);    // evicts key 2
cache.get(2);       // returns -1 (not found)
cache.get(3);       // returns 3.
cache.put(4, 4);    // evicts key 1.
cache.get(1);       // returns -1 (not found)
cache.get(3);       // returns 3
cache.get(4);       // returns 4


******************************************************
key:
	- similar problem: https://leetcode.com/problems/lru-cache/
	- need to store frequency!
	- Queue(doubly linked list) + hashmap + hashmap
	- edge case:
		1) empty string, return empty
		2)

******************************************************


核心数据结构（3 个）
	key → Node
	freq → DoublyLinkedList
	minFreq： 永远指向当前最小频率

Node 结构
	key, value, freq
	prev, next

总体结构图
	freq = 1  →  [k2, k5, k7]
	freq = 2  →  [k3, k1]
	freq = 3  →  [k4]

	每个 freq 对应一个双向链表，链表内：LRU


class LFUCache {

    class Node {
        int key, value, freq;
        Node prev, next;
        Node(int k, int v) {
            key = k;
            value = v;
            freq = 1;
        }
    }

    class DoublyLinkedList {
        Node head, tail;
        int size;

        DoublyLinkedList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        void addFirst(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            size++;
        }

        void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }

        Node removeLast() {
            if (size > 0) {
                Node node = tail.prev;
                remove(node);
                return node;
            }
            return null;
        }
    }

/** -------------------------------------------------
 * 
 * 			主逻辑
 * 
 * ---------------------------------------------------**/
    
    int capacity, minFreq;
    Map<Integer, Node> nodeMap;
    Map<Integer, DoublyLinkedList> freqMap;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
        nodeMap = new HashMap<>();
        freqMap = new HashMap<>();
    }

    public int get(int key) {
        if (!nodeMap.containsKey(key)) return -1;
        Node node = nodeMap.get(key);
        updateFreq(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        if (nodeMap.containsKey(key)) {
            Node node = nodeMap.get(key);
            node.value = value;
            updateFreq(node);
        } else {
            if (nodeMap.size() == capacity) {
                DoublyLinkedList minList = freqMap.get(minFreq);
                Node toRemove = minList.removeLast();
                nodeMap.remove(toRemove.key);
            }
            Node node = new Node(key, value);
            nodeMap.put(key, node);
            freqMap.computeIfAbsent(1, k -> new DoublyLinkedList()).addFirst(node);
            minFreq = 1;
        }
    }

    private void updateFreq(Node node) {
        int freq = node.freq;
        DoublyLinkedList list = freqMap.get(freq);
        list.remove(node);

        if (freq == minFreq && list.size == 0) {
            minFreq++;
        }

        node.freq++;
        freqMap.computeIfAbsent(node.freq, k -> new DoublyLinkedList()).addFirst(node);
    }
}



=======================================================================================================

method 2:

	- Java O(1) Solution Using Two HashMap and One DoubleLinkedList
	- cache: key to Node mapping, storing all nodes by their keys
	  frequencyMap: key to linked list mapping, storing all double linked list by their frequencies

stats:

	- Runtime: 39 ms, faster than 28.50% of Java online submissions for LFU Cache.
	- Memory Usage: 61.4 MB, less than 35.00% of Java online submissions for LFU Cache.

public class LFUCache {

	final int capacity;
	int curSize;
	int minFrequency;

	// cache: key to Node mapping, storing all nodes by their keys
	// frequencyMap: key to linked list mapping, storing all double linked list by their frequencies
	Map<Integer, DLLNode> cache;
	Map<Integer, DoubleLinkedList> frequencyMap;


	public LFUCache(int capacity) {
	    this.capacity = capacity;
	    this.curSize = 0;
	    this.minFrequency = 0;

	    this.cache = new HashMap<>();
	    this.frequencyMap = new HashMap<>();
	}

	// get node value by key,  then update node frequency as well as relocate that node
	public int get(int key) {
	    DLLNode curNode = cache.get(key);
	    if (curNode == null) {
	        return -1;
	    }
	    updateNode(curNode);
	    return curNode.val;
	}

	// condition 1: if LFU cache has input key, update node value and node position in list
	// condition 2: if LFU cache does NOT have input key
	// 	-- 1) if LFU cache does NOT have enough space, remove the LRU node in min frequency list, 
	//		then add new node
	//  -- 2): if LFU cache has enough space, add new node directly
	public void put(int key, int value) {
	    // corner case: check cache capacity initialization
	    if (capacity == 0) {
	        return;
	    }

	    if (cache.containsKey(key)) {
	        DLLNode curNode = cache.get(key);
	        curNode.val = value;
	        updateNode(curNode);
	    }
	    else {
	        curSize++;
	        if (curSize > capacity) {
	            // get minimum frequency list
	            DoubleLinkedList minFreqList = frequencyMap.get(minFrequency);
	            DLLNode deleteNode = minFreqList.removeTail();
	            cache.remove(deleteNode.key);
	            curSize--;
	        }

	        // reset min frequency to 1 because of adding new node
	        minFrequency = 1;
	        DLLNode newNode = new DLLNode(key, value);

	        // get the list with frequency 1, and then add new node into the list, 
	        // as well as into LFU cache
	        DoubleLinkedList curList = frequencyMap.getOrDefault(1, new DoubleLinkedList());
	        curList.addNode(newNode);
	        frequencyMap.put(1, curList);
	        cache.put(key, newNode);
	    }
	}

	// helper function
	private void updateNode(DLLNode curNode) {
	    int curFreq = curNode.frequency;
	    DoubleLinkedList curList = frequencyMap.get(curFreq);
	    curList.removeNode(curNode);

	    // if current list contains the lowest frequency and current node is the only node in that list
	    //  need to remove the entire list & increase min frequency value by 1
	    if (curFreq == minFrequency && curList.listSize == 0) {
	        minFrequency++;
	    }

	    curNode.frequency++;

	    // move node to its new correspondy freq map.
	    DoubleLinkedList newList = frequencyMap.getOrDefault(curNode.frequency, new DoubleLinkedList());
	    newList.addNode(curNode);
	    frequencyMap.put(curNode.frequency, newList);
	}


	class DLLNode {
	    int key;
	    int val;
	    int frequency;
	    DLLNode prev;
	    DLLNode next;

	    public DLLNode(int key, int val) {
	        this.key = key;
	        this.val = val;
	        this.frequency = 1;
	    }
	}

	class DoubleLinkedList {
	    int listSize;
	    DLLNode head;
	    DLLNode tail;
	    public DoubleLinkedList() {
	        this.listSize = 0;
	        this.head = new DLLNode(0, 0);
	        this.tail = new DLLNode(0, 0);
	        head.next = tail;
	        tail.prev = head;
	    }

	    /** add new node into head of list and increase list size by 1 **/
	    public void addNode(DLLNode curNode) {
	        DLLNode nextNode = head.next;
	        curNode.next = nextNode;
	        curNode.prev = head;
	        head.next = curNode;
	        nextNode.prev = curNode;
	        listSize++;
	    }

	    /** remove input node and decrease list size by 1**/
	    public void removeNode(DLLNode curNode) {
	        DLLNode prevNode = curNode.prev;
	        DLLNode nextNode = curNode.next;
	        prevNode.next = nextNode;
	        nextNode.prev = prevNode;
	        listSize--;
	    }

	    /** remove tail node **/
	    public DLLNode removeTail() {
	        // DO NOT FORGET to check list size
	        if (listSize > 0) {
	            DLLNode tailNode = tail.prev;
	            removeNode(tailNode);
	            return tailNode;
	        }
	        return null;
	    }
	}
}


=======================================================================================================
method 1:

method:
	- use 3 hashmaps
	- the least recently+frequently used value to be removed is the first element in LinkedHashSet 
	  with the lowest count/frequency.
	- min is used to track the group of elements with least frequency
	- lists maps frequency to groups, each element in same group has the same count.
	- countToLRUKeys = <count, {set of keys with this count}>

stats:

	- Runtime: 59 ms, faster than 18.29% of Java online submissions for LFU Cache.
	- Memory Usage: 59.2 MB, less than 60.00% of Java online submissions for LFU Cache.



public class LFUCache {
    
    private int min;

    private final int capacity;
    private final HashMap<Integer, Integer> keyToVal;
    private final HashMap<Integer, Integer> keyToCount;
    private final HashMap<Integer, LinkedHashSet<Integer>> countToLRUKeys;
    
    public LFUCache(int capacity) {
        this.min = -1;
        this.capacity = capacity;
        this.keyToVal = new HashMap<>();
        this.keyToCount = new HashMap<>();
        this.countToLRUKeys = new HashMap<>();
    }
    
    public int get(int key) {
        if (!keyToVal.containsKey(key)) return -1;
        
        int count = keyToCount.get(key);

        // remove key from current count (since we will inc count)
        countToLRUKeys.get(count).remove(key); 

        if (count == min && countToLRUKeys.get(count).size() == 0) 
        	min++; 
        
        putCount(key, count + 1);
        return keyToVal.get(key);
    }
    
    public void put(int key, int value) {

    	// can't put any more
        if (capacity <= 0) return;
        
        // update key's value & update key's count
        if (keyToVal.containsKey(key)) {
            keyToVal.put(key, value); 
            get(key); 
            return;
        } 
        
        // evict LRU from this min count bucket
        if (keyToVal.size() >= capacity){
            evict(countToLRUKeys.get(min).iterator().next()); 
        }
        
        // if key doesn't exist before
        min = 1;
        putCount(key, min); // adding new key and count
        keyToVal.put(key, value); // adding new key and value
    }
    
    private void evict(int key) {
        countToLRUKeys.get(min).remove(key);
        keyToVal.remove(key);
    }
    
    private void putCount(int key, int count) {
        keyToCount.put(key, count);
        countToLRUKeys.computeIfAbsent(count, ignore -> new LinkedHashSet<>());
        countToLRUKeys.get(count).add(key);
    }
}


=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



