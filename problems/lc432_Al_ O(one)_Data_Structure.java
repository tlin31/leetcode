432. All O`one Data Structure
Hard

Design a data structure to store the strings' count with the ability to return the strings with 
minimum and maximum counts.

Implement the AllOne class:

AllOne() Initializes the object of the data structure.
inc(String key) Increments the count of the string key by 1. 
	If key does not exist in the data structure, insert it with count 1.

dec(String key) Decrements the count of the string key by 1. 
	If the count of key is 0 after the decrement, remove it from the data structure. 
	It is guaranteed that key exists in the data structure before the decrement.

getMaxKey() Returns one of the keys with the maximal count. 
	If no element exists, return an empty string "".

getMinKey() Returns one of the keys with the minimum count. 
	If no element exists, return an empty string "".

Note that each function must run in O(1) average time complexity.

 

Example 1:

Input
["AllOne", "inc", "inc", "getMaxKey", "getMinKey", "inc", "getMaxKey", "getMinKey"]
[[], ["hello"], ["hello"], [], [], ["leet"], [], []]

Output
[null, null, null, "hello", "hello", null, "hello", "leet"]

Explanation
AllOne allOne = new AllOne();
allOne.inc("hello");
allOne.inc("hello");
allOne.getMaxKey(); // return "hello"
allOne.getMinKey(); // return "hello"
allOne.inc("leet");
allOne.getMaxKey(); // return "hello"
allOne.getMinKey(); // return "leet"
 

Constraints:

1 <= key.length <= 10
key consists of lowercase English letters.
It is guaranteed that for each call to dec, key is existing in the data structure.
At most 5 * 104 calls will be made to inc, dec, getMaxKey, and getMinKey.


******************************************************
key:
	- 
	- edge case:
		1) no actions? 
		2) invalid options
		3) 

******************************************************



=======================================================================================================
Method 1: 

solution with pics
https://leetcode.com/problems/all-oone-data-structure/solutions/731468/hashmap-doublylinkedlist-strategy/


Stats:

	- 
	- 


Method:

	-	

ex. For a node n, it has a key of input 'a', 'b','c', etc,， each has frequency of 1 

class Node {
    int freq;
    Node prev;
    Node next;
    Set<String> keys;

    public Node(int freq) {
        this.freq = freq;
        keys = new HashSet<>();
    }
}


public class AllOne {

    private Node head;
    private Node tail;
    Map<String, Node> map;

    /** Initialize your data structure here. */
    public AllOne() {
        head = null;
        tail = null;
        // maps puts string as key, node as value
        map = new HashMap<>();
    }

    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
    public void inc(String key) {

        if (map.containsKey(key)) {
            Node node = map.get(key);
            int freq = node.freq;
            // remove word/string from current node's frequency set
            node.keys.remove(key);

            if (node.next == null) {
                Node newNode = new Node(freq + 1);
                node.next = newNode;
                newNode.prev = node;
                newNode.keys.add(key);
                map.put(key, newNode);
                tail = newNode;
            } 

            else {
                Node next = node.next;

                // this node and next node's frequency has a gap
                if (next.freq - freq > 1) {
                    Node newNode = new Node(freq + 1);
                    newNode.keys.add(key);
                    node.next = newNode;
                    newNode.prev = node;
                    newNode.next = next;
                    next.prev = newNode;
                    map.put(key, newNode);
                } else {
                	// there's a node with exact frequency of freq+1
                    next.keys.add(key);
                    map.put(key, next);
                }
            }

            if (node.keys.size() == 0) {
                removeNode(node);
            }

        } else { 
        	// map does not contains the key
            if (head == null) {
                head = new Node(1);
                head.keys.add(key);
                tail = head;
            } 

            else {
                if (head.freq == 1) {
                    head.keys.add(key);
                } else {
                    Node newNode = new Node(1);
                    newNode.keys.add(key);
                    newNode.next = head;
                    head.prev = newNode;
                    head = newNode;
                }
            }
            map.put(key, head);
        }
    }

    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
    public void dec(String key) {
        if (!map.containsKey(key)) {
            return;
        }

        Node node = map.get(key);


        if (node.keys.size() == 0) {
            removeNode(node);
        }

        node.keys.remove(key);
        int freq = node.freq;
        if (freq == 1) {
            map.remove(key);

        } else if (node == head) {
            Node newNode = new Node(freq - 1);
            newNode.keys.add(key);
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            map.put(key, head);
        } else {
            Node prev = node.prev;
            if (freq - prev.freq == 1) {
                prev.keys.add(key);
                map.put(key, prev);
            } else {
                Node newNode = new Node(freq - 1);
                prev.next = newNode;
                newNode.prev = prev;
                newNode.next = node;
                node.prev = newNode;
                newNode.keys.add(key);
                map.put(key, newNode);
            }
        }

    }


    /** Returns one of the keys with maximal value. */
    public String getMaxKey() {
        if (head == null) {
            return "";
        }
        return tail.keys.iterator().next();
    }

    /** Returns one of the keys with Minimal value. */
    public String getMinKey() {
        if (head == null) {
            return "";
        }
        return head.keys.iterator().next();
    }


    private void removeNode(Node node) {
        Node prevNode = node.prev;
        Node nextNode = node.next;
        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
        	//prevNode is null, which means this node is head.
            this.head = this.head.next;
        }
        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            this.tail = this.tail.prev;
        }
    }

    

}







