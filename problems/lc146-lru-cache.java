146. LRU Cache - Medium

Design and implement a data structure for Least Recently Used (LRU) cache. It should support 
the following operations: get and put.

	1.get(key) - Get the value (will always be positive) of the key if the key exists in the 
	  cache, otherwise return -1.
	2.put(key, value) - Set or insert the value if the key is not already present. When the 
	  cache reached its capacity, it should invalidate the least recently used item before
	  inserting a new item.

The cache is initialized with a positive capacity.

Follow up:
Could you do both operations in O(1) time complexity?

Example:

LRUCache cache = new LRUCache( 2 /* capacity */ );

cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // returns 1
cache.put(3, 3);    // evicts key 2
cache.get(2);       // returns -1 (not found)
cache.put(4, 4);    // evicts key 1
cache.get(1);       // returns -1 (not found)
cache.get(3);       // returns 3
cache.get(4);       // returns 4


******************************************************
key:
	- what kind of data structure makes first in first out? --> queue
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************


1. hash map holds iterators to linked list
2. linked list holds key and value, key to access hash map items
3. when item is accessed, it is promoted - moved to the tail of the list - O(1) operation
4. when item should be removed, we remove head of the list - O(1) operation
5. when item is not promoted long time, it is moved to the head of the list automatically
6. get() - O(1) performance, set() - O(1) performance


=======================================================================================================
method 1:

method:

	- implement the queue by using a doubly linked list --> head is old, tail is new
	- a hashtable that keeps track of the keys and its values in the double linked list. 
	- One interesting property about double linked list is that the node can remove itself 
	  	without other reference &  constant time to add and remove nodes from the head or tail.
	-  + create a pseudo head and tail to mark the boundary, so that we dont need to check the NULL
		node during the update. --> code concise and clean + good performance

	- actual implementation:
		-> get: after get the value, move this element to the head

stats:

	- 
	- Runtime: 58 ms, faster than 83.90% of Java online submissions for LRU Cache.
	- Memory Usage: 57.6 MB, less than 60.74% 



	import java.util.Hashtable;
	public class LRUCache {

		class DLinkedNode {
		  int key;
		  int value;
		  DLinkedNode prev;
		  DLinkedNode post;
		}

		private HashMap<Integer, DLinkedNode>  cache = new HashMap<Integer, DLinkedNode>();
		private int count;
		private int capacity;
		private DLinkedNode head, tail;

		public LRUCache(int capacity) {
		    this.count = 0;
		    this.capacity = capacity;

		    head = new DLinkedNode();
		    head.prev = null;

		    tail = new DLinkedNode();
		    tail.post = null;

		    head.post = tail;
		    tail.prev = head;
		}

		
		// add the new node right after head
		private void addNode(DLinkedNode node) {
		    
			// add node's front & back link
		  	node.prev = head;
		  	node.post = head.post;

		  	// change head & next element's front & back link
		  	DLinkedNode next = head.post;
		  	next.prev = node;
		  	head.post = node;
		}

		// Remove an existing node from the linked list.
		private void removeNode(DLinkedNode node){
		  DLinkedNode before = node.prev;
		  DLinkedNode next = node.post;

		  before.post = next;
		  next.prev = before;
		}
		
		//Move certain node in between to the head.
		private void moveToHead(DLinkedNode node){
		  this.removeNode(node);
		  this.addNode(node);
		}

		// pop the current tail. 
		private DLinkedNode popTail(){
		  DLinkedNode res = tail.prev;
		  this.removeNode(res);
		  return res;
		}


		public int get(int key) {

		  DLinkedNode node = cache.get(key);
		  if(node == null){
		    return -1; // should raise exception here.
		  }

		  // move the accessed node to the head;
		  this.moveToHead(node);

		  return node.value;
		}


		public void put(int key, int value) {
		  DLinkedNode node = cache.get(key);

		  if(node == null){

		  	// insert new node
		    DLinkedNode newNode = new DLinkedNode();
		    newNode.key = key;
		    newNode.value = value;

		    this.cache.put(key, newNode);
		    this.addNode(newNode);

		    ++count;

		    if(count > capacity){
		      // pop the tail
		      DLinkedNode tail = this.popTail();
		      this.cache.remove(tail.key);
		      --count;
		    }
		  }

		  else{
		    // update the value.
		    node.value = value;
		    this.moveToHead(node);
		  }
		}

	}



=======================================================================================================
method 2:

method:

	- manually implemented a double linked list
	- 

stats:

	- 
	- Runtime: 61 ms, faster than 66.28% of Java online submissions for LRU Cache.
Memory Usage: 58.3 MB, less than 42.94% 

	class LRUCache {
    public LRUCache(int capacity) {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
        map = new HashMap<>();
        this.capacity = capacity;
    }

    private Node add(int key, int val) {
        Node node = new Node(key, val);
        node.prev = head;
        node.next = head.next;
        head.next = node;
        node.next.prev = node;
        return node;
    }

    private Node update(Node node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        node.prev = head;
        return node;
    }

    private Node remove() {
        Node node = tail.prev;
        tail.prev = tail.prev.prev;
        tail.prev.next = tail;
        return node;
    }

    public int get(int key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            update(node);
            return node.val;
        } else {
            return -1;
        }
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = update(map.get(key));
            node.val = value;
        } else {
            if (capacity > 0) {
                map.put(key, add(key, value));
                capacity--;
            } else {
                map.remove(remove().key);
                map.put(key, add(key, value));
            }
        }
    }

    class Node {
        Node prev;
        Node next;
        int val;
        int key;

        public Node() {
        }

        Node (int key, int val) {
            this.val = val;
            this.key = key;
        }
    }

    Node head;
    Node tail;
    Map<Integer, Node> map;
    int capacity;
}

=======================================================================================================
method 3:

method:

	- LinkedHashSet
	- 

stats:

	- 
	- 


class LRUCache {
    LinkedHashSet<Integer> listSet;
    Map<Integer, Integer> map;
    
    int max_size = 0;
    int cur_size = 0;
    public LRUCache(int capacity) {
        map = new HashMap<>();
        listSet = new LinkedHashSet<>();
        this.max_size = capacity;
    }
    
    public int get(int key) {
        if(map.containsKey(key)==false){
            return -1;
        }
        
        int val = map.get(key);
        listSet.remove(key);
        listSet.add(key);
        return val;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            map.put(key, value);
            listSet.remove(key);
            listSet.add(key);
        }else{
            map.put(key, value);
            cur_size++;
            listSet.add(key);
            if(cur_size>max_size){
                int first_key = listSet.iterator().next();
                listSet.remove(first_key);
                map.remove(first_key);
                cur_size--;
            }
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */


