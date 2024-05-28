# Sorting & Merging

## Table of contents
1. Counting Sort
2. Bucket Sort
3. Selection Sort
4. Quick Sort: Java's sort 
5. Insertion Sort
6. Merge Sort
7. Heap Sort
8. Radix Sort
9. Bubble Sort

## Summary:



## 1. Count sort

https://www.youtube.com/watch?v=OKd534EWcdk&ab_channel=CSDojo

Tips: 

	- non-comparison-based, 
	- integer values, 
	- Non negative values
	- Limited range of values
	- In place

1. Complexity Analysis of Counting Sort:
- Time : O(N+M), where N and M are the size of inputArray[] and countArray[] respectively.
- Worst-case: O(N+M).
- Average-case: O(N+M).
- Best-case: O(N+M).

- Auxiliary Space: O(N+M), where N and M are the space taken by outputArray[] and countArray[] respectively.

2. Counting Sort Algorithm:
	1) Declare an auxiliary array countArray[] of size max(inputArray[])+1 and initialize it with 0.
	2) Traverse array inputArray[] and map each element of inputArray[] as an index of countArray[] array, i.e., execute countArray[inputArray[i]]++ for 0 <= i < N.
	3) Calculate the prefix sum at every index of array inputArray[].
	4) Create an array outputArray[] of size N.
	5) Traverse array inputArray[] from end and update outputArray[ countArray[ inputArray[i] ] – 1] = inputArray[i]. Also, update countArray[ inputArray[i] ] = countArray[ inputArray[i] ]- – .

```java
import java.util.Arrays;

public class CountSort {
	public static int[] countSort(int[] inputArray) {
		int N = inputArray.length;
		int M = 0;
		for (int i = 0; i < N; i++) {
			M = Math.max(M, inputArray[i]);
		}
		int[] countArray = new int[M + 1];
		for (int i = 0; i < N; i++) {
			countArray[inputArray[i]]++;
		}
		for (int i = 1; i <= M; i++) {
			countArray[i] += countArray[i - 1];
		}
		int[] outputArray = new int[N];
		for (int i = N - 1; i >= 0; i--) {
			outputArray[countArray[inputArray[i]] - 1] = inputArray[i];
			countArray[inputArray[i]]--;
		}
		return outputArray;
	}

```


## 2. Bucket Sort/ Bin Sort

Tips: 

	- divides an array's elements into several buckets. The buckets are then sorted one at a time, either using a different sorting algorithm or by recursively applying the bucket sorting algorithm. 
	- good for double/float
	- use linked list for multiple elements in an bucket
	- when restored sorted array: use input[i]'s value to get to buckets, see how many elements are in front of it, then it corresponds to the output array's location-1

### 1. Complexity

Best Case & Average case: Complexity O(n)
- It occurs when the elements are distributed uniformly in the buckets, with nearly identical elements in each bucket. 

- When the elements within the buckets are already sorted, the complexity increases. 

- If insertion sort is used to sort bucket elements, the overall complexity will be linear, i.e. O(n+k).
O(n) is the complexity of creating buckets, and O(k) is the complexity of sorting bucket elements using algorithms with linear time complexity in the best case.

- It happens when the array's elements are distributed at random. Bucket sorting takes linear time, even if the elements are not distributed uniformly. 

Worst Case: O(n*n)
- When elements in the array are close in proximity, they are likely to be placed in the same bucket. As a result, some buckets may contain more elements than others. 

- It makes the complexity dependent on the sorting algorithm used to sort the bucket's elements.

Space Complexity: O(n+k).


### 2. Algorithm: 
	1) Create n empty buckets (Or lists) and do the following for every array element arr[i].
	2) Insert arr[i] into bucket[n*array[i]]
		- Take each element from the input array.
		- Multiply the element by the size of the bucket array (10 in this case). For example, for element 0.23, we get 0.23 * 10 = 2.3.
		- Convert the result to an integer, which gives us the bucket index. In this case, 2.3 is converted to the integer 2.
		- Insert the element into the bucket corresponding to the calculated index.
		- Repeat these steps for all elements in the input array.
	3) Sort individual buckets using insertion sort.
		- Apply a stable sorting algorithm (e.g., Bubble Sort, Merge Sort) to sort the elements within each bucket.
	4) Concatenate all sorted buckets.


### 3. In problems

1. lc 274 H-index: 
	- the h-index is defined as the number of papers with reference greater than the number. 
	- So assume n is the total number of papers, if we have n+1 buckets, number from 0 to n, then for any paper with reference corresponding to the index of the bucket, we increment the count for that bucket. 
	- The only exception is that for any paper with larger number of reference than n, we put in the n-th bucket.



```java
import java.util.ArrayList;
import java.util.List;

public class Main {
    // Insertion sort function to sort individual buckets
    public static void insertionSort(List<Float> bucket) {
        for (int i = 1; i < bucket.size(); ++i) {
            float key = bucket.get(i);
            int j = i - 1;
            while (j >= 0 && bucket.get(j) > key) {
                bucket.set(j + 1, bucket.get(j));
                j--;
            }
            bucket.set(j + 1, key);
        }
    }

    // Function to sort arr[] of size n using bucket sort
    public static void bucketSort(float[] arr) {
        int n = arr.length;

        // 1) Create n empty buckets
        List<Float>[] buckets = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            buckets[i] = new ArrayList<>();
        }

        // 2) Put array elements in different buckets
        for (int i = 0; i < n; i++) {
            int bi = (int) (n * arr[i]);
            buckets[bi].add(arr[i]);
        }

        // 3) Sort individual buckets using insertion sort
        for (int i = 0; i < n; i++) {
            insertionSort(buckets[i]);
        }

        // 4) Concatenate all buckets into arr[]
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < buckets[i].size(); j++) {
                arr[index++] = buckets[i].get(j);
            }
        }
    }

    // Driver program to test above function
    public static void main(String[] args) {
        float[] arr = {0.897f, 0.565f, 0.656f, 0.1234f, 0.665f, 0.3434f};
        bucketSort(arr);

        System.out.println("Sorted array is:");
        for (float num : arr) {
            System.out.print(num + " ");
        }
    }
}
```


## Selection Sort

Tips: 

	- repeatedly selects the smallest (or largest) and swaps it with the first element of the unsorted part. 
	- in place
	- not stable by default, depends on implementatino



### Complexity
Time Complexity: O(N^2) as there are two nested loops:

Auxiliary Space: O(1) as the only extra memory used is for temporary variables while swapping two values in Array. The selection sort never makes more than O(N) swaps and can be useful when memory writing is costly. 


### Algo

	Find the smallest card. Swap it with the first card.
	Find the second-smallest card. Swap it with the second card.
	Find the third-smallest card. Swap it with the third card.
	Repeat finding the next-smallest card, and swapping it into the correct position until the array is sorted.

```
       int n = arr.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (arr[j] < arr[min_idx])
                    min_idx = j;

            // Swap the found minimum element with the first
            // element
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }

```
## Quick Sort
Tips:
- partition(). The target of partitions is to place the pivot (any element can be chosen to be a pivot) at its correct position in the sorted array and put all smaller elements to the left of the pivot, and all greater elements to the right of the pivot.

- Partition is done recursively on each side of the pivot after the pivot is placed in its correct position and this finally sorts the array.


![Screenshot of an example](https://www.geeksforgeeks.org/wp-content/uploads/gq/2014/01/QuickSort2.png)

**Advantages of Quick Sort:**
- It is a divide-and-conquer algorithm that makes it easier to solve problems.
- It is efficient on large data sets.
- It has a low overhead, as it only requires a small amount of memory to function.

**Disadvantage**
- It has a worst-case time complexity of O(N^2), which occurs when the pivot is chosen poorly.
- It is not a good choice for small data sets.
- It is not a stable sort

### 1. Compexity

Best Case: Ω (N log (N))
- The best-case scenario for quicksort occur when the pivot chosen at the each step divides the array into roughly equal halves.
- In this case, the algorithm will make balanced partitions, leading to efficient Sorting.

Average Case: θ ( N log (N))
- Quicksort’s average-case performance is usually very good in practice, making it one of the fastest sorting Algorithm.

Worst Case: O(N2)
- The worst-case Scenario for Quicksort occur when the pivot at each step consistently results in highly unbalanced partitions. When the array is already sorted and the pivot is always chosen as the smallest or largest element. To mitigate the worst-case Scenario, various techniques are used such as choosing a good pivot (e.g., median of three) and using Randomized algorithm (Randomized Quicksort ) to shuffle the element before sorting.

Auxiliary Space: 
- O(1), if we don’t consider the recursive stack space. If we consider the recursive stack space then, in the worst case quicksort could make O(N).


### 2. Algorithm

1. Choose a value in the array to be the pivot element.
2. Order the rest of the array so that lower values than the pivot element are on the left, and higher values are on the right.
3. Swap the pivot element with the first element of the higher values so that the pivot element lands in between the lower and higher values.
4. Do the same operations (recursively) for the sub-arrays on the left and right side of the pivot element.

```java
import java.io.*;

class GFG {

    // A utility function to swap two elements
    static void swap(int[] arr, int i, int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // This function takes last element as pivot,
    // places the pivot element at its correct position
    // in sorted array, and places all smaller to left
    // of pivot and all greater elements to right of pivot
    static int partition(int[] arr, int low, int high)
    {
        // last element as pivot
        int pivot = arr[high];

        // Index of smaller element and indicates
        // the right position of pivot found so far
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {

            // If current element is smaller than the pivot
            if (arr[j] < pivot) {

                // Increment index of smaller element
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    // The main function that implements QuickSort
    // arr[] --> Array to be sorted,
    // low --> Starting index,
    // high --> Ending index
    static void quickSort(int[] arr, int low, int high)
    {
        if (low < high) {

            // pi is partitioning index, arr[p]
            // is now at right place
            int pi = partition(arr, low, high);

            // Separately sort elements before
            // partition and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    // Driver Code
    public static void main(String[] args)
    {
        int[] arr = { 10, 7, 8, 9, 1, 5 };
        int N = arr.length;
        quickSort(arr, 0, N - 1);

    }
}
```


### 3. In Problem





## Insertion sort

Tips:
- Insertion sort is a simple sorting algorithm that works by iteratively inserting each element of an unsorted list into its correct position in a sorted portion of the list
- Continue this process, comparing each element with the ones before it and swapping as needed to place it in the correct position among the sorted elements.

- stable sorting algorithm
- Used for:
	- The list is small or nearly sorted.
	- Simplicity and stability are important.

### Complexity

**Time Complexity**
- Best case: O(n), If the list is already sorted, where n is the number of elements in the list.
- Average case: O(n^2), If the list is randomly ordered
- Worst case: O(n^2), If the list is in reverse order

**Space** Complexity of Insertion Sort**
- Auxiliary Space: O(1), Insertion sort requires O(1) additional space, making it a space-efficient sorting algorithm.

### Algo

	insertionSort(array)
	  mark first element as sorted
	  for each unsorted element X
	    'extract' the element X
	    for j <- lastSortedIndex down to 0
	      if current element j > X
	        move sorted element to the right by 1
	    break loop and insert X here
	end insertionSort


```java
import java.util.Arrays;

class InsertionSort {

  void insertionSort(int array[]) {
    int size = array.length;

    for (int step = 1; step < size; step++) {
      int key = array[step];
      int j = step - 1;

      // Compare key with each element on the left of it until an element smaller than
      // it is found.
      // For descending order, change key<array[j] to key>array[j].
      while (j >= 0 && key < array[j]) {
        array[j + 1] = array[j];
        --j;
      }

      // Place key at after the element just smaller than it.
      array[j + 1] = key;
    }
  }

  // Driver code
  public static void main(String args[]) {
    int[] data = { 9, 5, 1, 4, 3 };
    InsertionSort is = new InsertionSort();
    is.insertionSort(data);
    System.out.println("Sorted Array in Ascending Order: ");
    System.out.println(Arrays.toString(data));
  }
}
```




### Problems



## 7.Heap Sort

Heap sort is an in-place algorithm. 

Its typical implementation is not stable but can be made stable 

Typically 2-3 times slower than well-implemented QuickSort.  The reason for slowness is a lack of locality of reference.

### Algorithm
First convert the array into heap data structure using heapify, then one by one delete the root node of the Max-heap and replace it with the last node in the heap and then heapify the root of the heap. Repeat this process until size of heap is greater than 1.

1. Build a heap from the given input array.
2. Repeat the following steps until the heap contains only one element:
3. Swap the root element of the heap (which is the largest element) with the last element of the heap.
4. Remove the last element of the heap (which is now in the correct position).
5. Heapify the remaining elements of the heap.
6. The sorted array is obtained by reversing the order of the elements in the input array.

```java
 public void sort(int arr[])
    {
        int N = arr.length;

        // Build heap (rearrange array)
        for (int i = N / 2 - 1; i >= 0; i--)
            heapify(arr, N, i);

        // One by one extract an element from heap
        for (int i = N - 1; i > 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    void heapify(int arr[], int N, int i)
    {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < N && arr[l] > arr[largest])
            largest = l;

        // If right child is larger than largest so far
        if (r < N && arr[r] > arr[largest])
            largest = r;

        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, N, largest);
        }
    }
```
### Complexity & Analysis
Time Complexity: O(N log N)
Auxiliary Space: O(log n), due to the recursive call stack. However, auxiliary space can be O(1) for iterative implementation.

Advantages of Heap Sort:

1. Efficient Time Complexity: Heap Sort has a time complexity of O(n log n) in all cases. This makes it efficient for sorting large datasets. The log n factor comes from the height of the binary heap, and it ensures that the algorithm maintains good performance even with a large number of elements.

2. Memory Usage – Memory usage can be minimal (by writing an iterative heapify() instead of a recursive one). So apart from what is necessary to hold the initial list of items to be sorted, it needs no additional memory space to work

3. Simplicity –  It is simpler to understand than other equally efficient sorting algorithms because it does not use advanced computer science concepts such as recursion.

Disadvantages of Heap Sort:
1. Costly: Heap sort is costly as the constants are higher compared to merge sort even if the time complexity is O(n Log n) for both.

2. Unstable: Heap sort is unstable. It might rearrange the relative order.

3. Efficient: Heap Sort is not very efficient when working with highly complex data. 


## 8. Radix Sort
Radix Sort is a linear sorting algorithm that sorts elements by processing them digit by digit. It is an efficient sorting algorithm for integers or strings with fixed-size keys. 

distributes the elements into buckets based on each digit’s value. By repeatedly sorting the elements by their significant digits, from the least significant to the most significant

non-comparative sorting

### Complexity

Time complexity： O(d * (n + b)), where d is the number of digits, n is the number of elements, and b is the base of the number system being used.

In practical implementations, radix sort is often faster than **other comparison-based sorting** algorithms, such as quicksort or merge sort, for large datasets, especially when the keys have many digits. 


Auxiliary Space: 

O(n + b), where n is the number of elements and b is the base of the number system. This space complexity comes from the need to create buckets for each digit value and to copy the elements back to the original array after each digit has been sorted.


### Algorithm
1. Start with the least significant digit (rightmost digit).
2. Sort the values based on the digit in focus by first putting the values in the correct bucket based on the digit in focus, and then put them back into array in the correct order.
3. Move to the next digit, and sort again, like in the step above, until there are no digits left.

```java
class Radix {

    // A utility function to get maximum value in arr[]
    static int getMax(int arr[], int n)
    {
        int mx = arr[0];
        for (int i = 1; i < n; i++)
            if (arr[i] > mx)
                mx = arr[i];
        return mx;
    }

    // A function to do counting sort of arr[] according to
    // the digit represented by exp.
    static void countSort(int arr[], int n, int exp)
    {
        int output[] = new int[n]; // output array
        int i;
        int count[] = new int[10];
        Arrays.fill(count, 0);

        // Store count of occurrences in count[]
        for (i = 0; i < n; i++)
            count[(arr[i] / exp) % 10]++;

        // Change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];

        // Build the output array
        for (i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        // Copy the output array to arr[], so that arr[] now
        // contains sorted numbers according to current
        // digit
        for (i = 0; i < n; i++)
            arr[i] = output[i];
    }

    // The main function to that sorts arr[] of
    // size n using Radix Sort
    static void radixsort(int arr[], int n)
    {
        // Find the maximum number to know number of digits
        int m = getMax(arr, n);

        // Do counting sort for every digit. Note that
        // instead of passing digit number, exp is passed.
        // exp is 10^i where i is current digit number
        for (int exp = 1; m / exp > 0; exp *= 10)
            countSort(arr, n, exp);
    }

    // A utility function to print an array
    static void print(int arr[], int n)
    {
        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
    }

    // Main driver method
    public static void main(String[] args)
    {
        int arr[] = { 170, 45, 75, 90, 802, 24, 2, 66 };
        int n = arr.length;

        // Function Call
        radixsort(arr, n);
        print(arr, n);
    }
}
```



## Bubble Sort
repeatedly swapping the adjacent elements if they are in the wrong order.

### Complexity
Time Complexity: O(N^2)

Auxiliary Space: O(1)



### Algorithm
- traverse from left and compare adjacent elements and the higher one is placed at right side. 
- In this way, the largest element is moved to the rightmost end at first. 
- This process is then continued to find the second largest and place it and so on until the data is sorted.

```java
 static void bubbleSort(int arr[], int n)
    {
        int i, j, temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    
                    // Swap arr[j] and arr[j+1]
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            // If no two elements were
            // swapped by inner loop, then break
            if (swapped == false)
                break;
        }
    }

```






