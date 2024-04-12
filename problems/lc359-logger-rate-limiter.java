359. Logger Rate Limiter - Easy

Design a logger system that receive stream of messages along with its timestamps, each 
message should be printed if and only if it is not printed in the last 10 seconds.

Given a message and a timestamp (in seconds granularity), return true if the message should be 
printed in the given timestamp, otherwise returns false.

It is possible that several messages arrive roughly at the same time.

Example:

Logger logger = new Logger();

// logging string "foo" at timestamp 1
logger.shouldPrintMessage(1, "foo"); returns true; 

// logging string "bar" at timestamp 2
logger.shouldPrintMessage(2,"bar"); returns true;

// logging string "foo" at timestamp 3
logger.shouldPrintMessage(3,"foo"); returns false;

// logging string "bar" at timestamp 8
logger.shouldPrintMessage(8,"bar"); returns false;

// logging string "foo" at timestamp 10
logger.shouldPrintMessage(10,"foo"); returns false;

// logging string "foo" at timestamp 11
logger.shouldPrintMessage(11,"foo"); returns true;


******************************************************
key:
	- question to ask: timestamp comes in order?
	- hashmap with last printed timestamp
	- edge case:
		1) empty string, return empty
		2) invalid timestamp

******************************************************



=======================================================================================================
method 1:

method:

	- Instead of logging print times, I store when it's ok for a message to be printed again. 
	- 

stats:

	- Time Complexity: O(1). The lookup and update of the hashtable takes a constant time.
	- Space Complexity: O(M)

class Logger {
  private HashMap<String, Integer> msgDict;

  /** Initialize your data structure here. */
  public Logger() {
    msgDict = new HashMap<String, Integer>();
  }

  /**
   * Returns true if the message should be printed in the given timestamp, otherwise returns false.
   */
  public boolean shouldPrintMessage(int timestamp, String message) {

    if (!this.msgDict.containsKey(message)) {
      this.msgDict.put(message, timestamp);
      return true;
    }

    Integer oldTimestamp = this.msgDict.get(message);
    if (timestamp - oldTimestamp >= 10) {
      this.msgDict.put(message, timestamp);
      return true;
    } else
      return false;
  }
}


=======================================================================================================
method 2:

method:

	- It is not given in this question that shouldPrintMessages will be called in increasing 
	  order of timestamps. Without this assumption, the algorithm may not work
	- Time complexity of sets[idx].clear() is not constant. Could be replaced by set[idx] = new HashSet()
	- bucket sort
	- And every time Logger receive a new message, it will remove all messages that's contained on 
	  the bucket at timestamp%10. 
	  Update bucket[id] keep current timestamp and make room for new messages. 
	  Loops thru the buckets and check bucket with only bucket[id] (means last timestamp) on the 
	  last 10 second



stats:

	- 
	- 

public class Logger {
    private int[] buckets;
    private Set[] sets;

    /** Initialize your data structure here. */
    public Logger() {
        buckets = new int[10];
        sets = new Set[10];
        for (int i = 0; i < sets.length; ++i) {
            sets[i] = new HashSet<String>();
        }
    }
    
    
    public boolean shouldPrintMessage(int timestamp, String message) {
        int idx = timestamp % 10;
        if (timestamp != buckets[idx]) {
            sets[idx].clear();
            buckets[idx] = timestamp;
        }
        for (int i = 0; i < buckets.length; ++i) {
            if (timestamp - buckets[i] < 10) {
                if (sets[i].contains(message)) {
                    return false;
                }
            }
        } 
        sets[idx].add(message);
        return true;
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



