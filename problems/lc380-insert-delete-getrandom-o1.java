380. Insert Delete GetRandom O(1) - Medium

Design a data structure that supports all following operations in average O(1) time.

	1. insert(val): Inserts an item val to the set if not already present.
	2. remove(val): Removes an item val from the set if present.
	3. getRandom: Returns a random element from current set of elements. Each element must have 
	              the same probability of being returned.

Example:

// Init an empty set.
RandomizedSet randomSet = new RandomizedSet();

// Inserts 1 to the set. Returns true as 1 was inserted successfully.
randomSet.insert(1);

// Returns false as 2 does not exist in the set.
randomSet.remove(2);

// Inserts 2 to the set, returns true. Set now contains [1,2].
randomSet.insert(2);

// getRandom should return either 1 or 2 randomly.
randomSet.getRandom();

// Removes 1 from the set, returns true. Set now contains [2].
randomSet.remove(1);

// 2 was already in the set, so return false.
randomSet.insert(2);

// Since 2 is the only number in the set, getRandom always return 2.
randomSet.getRandom();


******************************************************
key:
	- use hashmap (stroe value & its index in array) + arraylist
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:
		Hashmap (element, its index)
		Array List of elements.

	- GetRandom: There is no indexes in hashmap, and hence to get true random value, one has first to convert 
	  hashmap keys in a list, that would take linear time. 

	  The solution here is to build a list of keys aside and to use this list to compute GetRandom 
	  in constant time.

	- Delete: The solution here is to always delete the last value:

		Swap the element to delete with the last one & Pop the last element out.

		For that, one has to compute an index of each element in constant time, and hence needs a 
		hashmap which stores element -> its index dictionary.


	- Insert:
		1. Add value -> its index into dictionary, average O(1) time.

		2. Append value to array list, average O(1) time as well.

	- Delete

		1. Retrieve an index of element to delete from the hashmap.

		2. Move the last element to the place of the element to delete, O(1) time.

		3. Pop the last element out, O(1) time.


stats:

	- 
	- 


class RandomizedSet {
  Map<Integer, Integer> dict;
  List<Integer> list;
  Random rand = new Random();

  /** Initialize your data structure here. */
  public RandomizedSet() {
    dict = new HashMap();
    list = new ArrayList();
  }

  /** Inserts a value to the set. */
  public boolean insert(int val) {
    if (dict.containsKey(val)) 
        return false;

    dict.put(val, list.size());
    list.add(list.size(), val);
    return true;
  }

  /** Removes a value from the set. Returns true if the set contained the specified element. */
  public boolean remove(int val) {
    if (! dict.containsKey(val)) 
    	return false;

    // move the last element to the place idx of the element to delete
    int lastElement = list.get(list.size() - 1);
    int idx = dict.get(val);

    // swap last element & target val
    list.set(idx, lastElement);
    dict.put(lastElement, idx);
    
    // delete the last element
    list.remove(list.size() - 1);
    dict.remove(val);
    return true;
  }

  /** Get a random element from the set. */
  public int getRandom() {
    return list.get(rand.nextInt(list.size()));
  }
}

=======================================================================================================
method 2:

method:

	- if allow duplicates --> hashmap stores <value, set of index that has this value>
	- 

stats:

	- 
	- 

public class RandomizedSet {
	    ArrayList<Integer> nums;
	    HashMap<Integer, Set<Integer>> locs;
	    java.util.Random rand = new java.util.Random();
	    /** Initialize your data structure here. */
	    public RandomizedSet() {
	        nums = new ArrayList<Integer>();
	        locs = new HashMap<Integer, Set<Integer>>();
	    }
	    
	    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
	    public boolean insert(int val) {
	        boolean contain = locs.containsKey(val);
	        if ( ! contain ) locs.put( val, new HashSet<Integer>() ); 
	        locs.get(val).add(nums.size());        
	        nums.add(val);
	        return ! contain ;
	    }
	    
	    /** Removes a value from the set. Returns true if the set contained the specified element. */
	    public boolean remove(int val) {
	        boolean contain = locs.containsKey(val);
	        if ( ! contain ) return false;

	        int loc = locs.get(val).iterator().next();
            locs.get(val).remove(loc);

	        if (loc < nums.size() - 1 ) {
	            int lastone = nums.get(nums.size() - 1 );
	            nums.set( loc , lastone );
	            locs.get(lastone).remove(nums.size() - 1);
	            locs.get(lastone).add(loc);
	        }
	        nums.remove(nums.size() - 1);
	        if (locs.get(val).isEmpty()) locs.remove(val);
	        return true;
	    }
	    
	    /** Get a random element from the set. */
	    public int getRandom() {
	        return nums.get( rand.nextInt(nums.size()) );
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



