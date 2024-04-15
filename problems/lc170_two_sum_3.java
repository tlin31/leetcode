170. Two Sum III - Data structure design
Design and implement a TwoSum class. It should support the following operations: add and find.

add - Add the number to an internal data structure.
find - Find if there exists any pair of numbers which sum is equal to the value.

Example 1:

add(1); add(3); add(5);
find(4) -> true
find(7) -> false
Example 2:

add(3); add(1); add(2);
find(3) -> true
find(6) -> false

Note:
// Your TwoSum object will be instantiated and called as such:
// TwoSum twoSum = new TwoSum();
// twoSum.add(number);
// twoSum.find(value);

******************************************************
key:
- ask if there are duplicate numbers
- when there's nothing added, find() return false
- call add method more or call find function more?
1、add优先：在这种策略中，我们假定add调用的次数要远远多于find调用的次数。所以设计add函数的时间复杂度为O(1)，
          find函数的时间复杂度为O(n)。此时我们hash nums的值。

2、find优先：在这种策略中，我们假定find调用的次数要远远多于add调用的次数。所以我们设计add函数的时间复杂度是O(n)，
            find函数的时间复杂度是O(1)。此时哈希的是sums的值。需要注意的是，我写的这段代码没有通过所有的测试用例
            (因为里面的add操作太多了），但是这确实提供了一种在软件设计中trade-off的思考方式。


******************************************************
class TwoSum {
    // A HashMap to store the numbers and their frequencies.
    private Map<Integer, Integer> countMap = new HashMap<>();

    // Constructor 
    public TwoSum() {
    }

    // Adds the input number to the data structure.
    public void add(int number) {
        // Merges the current number into the map, incrementing its count if it already exists.
        countMap.merge(number, 1, Integer::sum);
    }

    // Finds if there exists any pair of numbers which sum is equal to the value.
    public boolean find(int value) {
        // Iterates through the entries in our map
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            int currentKey = entry.getKey(); // The number in the pair we are currently considering.
            int currentValue = entry.getValue(); // The count of how many times currentKey has been added.
            int complement = value - currentKey; // The number that would complete the pair to equal 'value'.
          
            // Check if the complement exists in our map.
            if (countMap.containsKey(complement)) {
                // If currentKey and complement are the same number, we need at least two instances.
                if (currentKey != complement || currentValue > 1) {
                    return true; // Found a valid pair that sums up to the given value.
                }
            }
        }
        // If we haven't found any valid pair, return false.
        return false;
    }
}




public class TwoSum {
    Map<Integer, Integer> map = new HashMap<>();
    // Add the number to an internal data structure.
    public void add(int number) {
        if(map.containsKey(number)){
            map.put(number, map.get(number) + 1);
        }else{
            map.put(number, 1);
        }
    }

    // Find if there exists any pair of numbers which sum is equal to the value.
    public boolean find(int value) {
        for(Integer key : map.keySet()){
            int target = value -key;
            if(map.containsKey(target)){
                if(target == key && map.get(key) <2) continue;
                return true;
            }
        }
        return false;
    }
}


