295. Find Median from Data Stream - Hard

Median is the middle value in an ordered integer list. If the size of the list is even, there is no
middle value. So the median is the mean of the two middle value.

For example,
[2,3,4], the median is 3

[2,3], the median is (2 + 3) / 2 = 2.5

Design a data structure that supports the following two operations:

	1. void addNum(int num) - Add a integer number from the data stream to the data structure.
	2. double findMedian() - Return the median of all elements so far.
 

Example:

addNum(1)
addNum(2)
findMedian() -> 1.5
addNum(3) 
findMedian() -> 2
 

Follow up:

1. If all integer numbers from the stream are between 0 and 100, how would you optimize it?

Ans: We can maintain an integer array of length 100 to store the count of each number along with a 
     total count. Then, we can iterate over the array to find the middle value to get our median.

	 Time and space complexity would be O(100) = O(1).

2. If 99% of all integer numbers from the stream are between 0 and 100, how would you optimize it?

Ans: Divide problem into 3 subproblems:
		1.Numbers < 0: You have 2 options:
			Use 2-heap solution (that we coded in original solution), or
			Use 1 array, which represents 1 bucket
		2. 0 <= Numbers <= 100: Use 100 buckets using an array of size 100
		3. 100 < Numbers: You have 2 options:
			Use 2-heap solution (that we coded in original solution), or
			Use 1 array, which represents 1 bucket

	For each number we get in the stream, insert it into 1 of the 3 groupings, keeping track of 
	the count of numbers in each of these 3 groupings

	* For Numbers < 0 and 100 < Numbers, using 2 arrays/buckets is the more practical solution since 
	it is very unlikely the median will fall into either bucket/array. 

	This makes findMedian() O(1) in average case. In the worst case, all numbers fall in 1 array, and we would either have to use Quickselect (O(n) average case, O(n2) worst case), or sorting (O(n log n)) to find the median.

	If you use 2 heaps instead, you will get findMedian() of O(1) average case, O(log n) worst case.


******************************************************
key:
	- 2 heap OR insertion sort
	- edge case:
		1) first command is get, return error or '#'
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- 2 heap
	- A max-heap lo to store the smaller half of the numbers
	  A min-heap hi to store the larger half of the numbers

	- Adding a number num:
		1. Add num to max-heap lo. 
		   Then do a balancing step for hi. So remove the largest element from lo and offer it to hi.
		2. The min-heap hi might end holding more elements than the max-heap lo, after the previous 
		   operation. We fix that by removing the smallest element from hi and offering it to lo.

stats:

	- Time complexity: O(5⋅logn)+O(1)≈O(logn).
					   Worst case: 3 heap insertions and 2 heap deletions from the top. Each of these 
					   takes about O(logn) time.
		               Finding the mean takes constant O(1) time since the tops of heaps are directly 
		               accessible.
	- Space complexity: O(n) linear space to hold input in containers.

	- Runtime: 51 ms, faster than 77.20% of Java online submissions for Find Median from Data Stream.
	  Memory Usage: 55.6 MB, less than 100.00% 


class MedianFinder {
   private Queue<Integer> small = new PriorityQueue(1, new Comparator<Integer>() {
		   public int compare(Integer o1, Integer o2) {
			   return o2 - o1; 
		   };
   });
	   
   private Queue<Integer> large = new PriorityQueue();

    // Adds a number into the data structure.
    public void addNum(int num) {
        large.add(num);
        small.add(large.poll());
        if (large.size() < small.size())
            large.add(small.poll());
    }

    // Returns the median of current data stream
    public double findMedian() {
        return large.size() > small.size()
               ? large.peek()
               : (large.peek() + small.peek()) / 2.0;
    }
}

------------------
private PriorityQueue<Integer> small = new PriorityQueue<>(Collections.reverseOrder());
private PriorityQueue<Integer> large = new PriorityQueue<>();
private boolean even = true;

public double findMedian() {
    if (even)
        return (small.peek() + large.peek()) / 2.0;
    else
        return small.peek();
}

public void addNum(int num) {
    if (even) {
        large.offer(num);
        small.offer(large.poll());
    } else {
        small.offer(num);
        large.offer(small.poll());
    }
    even = !even;
}


=======================================================================================================
method 2:

method:

	- bucket sort
	- 

stats:

	- 
	- 

public class MedianFinder {
    private LinkedList<LinkedList<Integer>> buckets; // store all ranges
    private int total_size;

    MedianFinder() {
        total_size = 0;
        buckets = new LinkedList<>();
        buckets.add(new LinkedList<>());
    }

    void addNum(int num) {
        List<Integer> correctRange = new LinkedList<>();
        int targetIndex = 0;

        // find the correct range to insert given num
        for (int i = 0; i < buckets.size(); i++) {
            if (buckets.size() == 1 ||
                    (i == 0 && num <= buckets.get(i).getLast()) ||
                    (i == buckets.size() - 1 && num >= buckets.get(i).getFirst()) ||
                    (buckets.get(i).getFirst() <= num && num <= buckets.get(i).getLast()) ||
                    (num > buckets.get(i).getLast() && num < buckets.get(i+1).getFirst())) {
                        correctRange = buckets.get(i);
                        targetIndex = i;
                        break;
            }
        }

        // put num at back of correct range, and sort it to keep increasing sequence
        total_size++;
        correctRange.add(num);
        Collections.sort(correctRange);

        // if currentRange's size > threshold, split it into two halves and add them back to buckets
        int len = correctRange.size();
        if (len * len > total_size) {
            LinkedList<Integer> half1 = new LinkedList<>(correctRange.subList(0, (len) / 2));
            LinkedList<Integer> half2 = new LinkedList<>(correctRange.subList((len) / 2, len));

            buckets.set(targetIndex, half1); //replaces
            buckets.add(targetIndex + 1, half2); //inserts
        }

    }

    // iterate thru all ranges in buckets to find median value
    double findMedian() {
        if (total_size==0)
            return 0;

        int mid1 = total_size/2;
        int mid2 = mid1 + 1;

        int leftCount=0;
        double first = 0.0, second = 0.0;
        for (List<Integer> bucket : buckets) {
            if (leftCount<mid1 && mid1<=leftCount+bucket.size())
                first = bucket.get(mid1 - leftCount - 1);

            if (leftCount<mid2 && mid2<=leftCount+bucket.size()) {
                second = bucket.get(mid2 - leftCount - 1);
                break;
            }
            leftCount += bucket.size();
        }

        if (total_size % 2 != 0)
            return second;
        else
            return (first + second)/2;
    }
}


=======================================================================================================
method 3:

method:

	- insertion sort
	- Intuition

Keeping our input container always sorted (i.e. maintaining the sorted nature of the container as an invariant).

Algorithm

Which algorithm allows a number to be added to a sorted list of numbers and yet keeps the entire list sorted? Well, for one, insertion sort!

We assume that the current list is already sorted. When a new number comes, we have to add it to the list while maintaining the sorted nature of the list. This is achieved easily by finding the correct place to insert the incoming number, using a binary search (remember, the list is always sorted). Once the position is found, we need to shift all higher elements by one space to make room for the incoming number.

This method would work well when the amount of insertion queries is lesser or about the same as the amount of median finding queries.

stats:

	- Time complexity: O(n) + O(\log n) \approx O(n)O(n)+O(logn)≈O(n).

Binary Search takes O(\log n)O(logn) time to find correct insertion position.
Insertion can take up to O(n)O(n) time since elements have to be shifted inside the container to make room for the new element.


	- Space complexity: O(n)O(n) linear space to hold input in a container.


class MedianFinder {
    vector<int> store; // resize-able container

public:
    // Adds a number into the data structure.
    void addNum(int num)
    {
        if (store.empty())
            store.push_back(num);
        else
            store.insert(lower_bound(store.begin(), store.end(), num), num);     // binary search and insertion combined
    }

    // Returns the median of current data stream
    double findMedian()
    {
        int n = store.size();
        return n & 1 ? store[n / 2] : (store[n / 2 - 1] + store[n / 2]) * 0.5;
    }
};


