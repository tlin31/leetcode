# Sorting & Merging

## Table of contents
1. Counting Sort
2. Bucket Sort
3. Selection Sort
4. Quick Sort: Java's sort 
5. Insertion Sort
6. Merge Sort


Bubble Sort
Heap Sort
Radix Sort

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

```
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




## Bubble Sort



