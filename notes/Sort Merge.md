# Sorting & Merging

## Summary:



### 1. Count sort

https://www.youtube.com/watch?v=OKd534EWcdk&ab_channel=CSDojo

Tips: 
	- non-comparison-based, 
	- integer values, 
	- Non negative values
	- Limited range of values
	- In place

1. Complexity Analysis of Counting Sort:
	Time : O(N+M), where N and M are the size of inputArray[] and countArray[] respectively.
	Worst-case: O(N+M).
	Average-case: O(N+M).
	Best-case: O(N+M).

	Auxiliary Space: O(N+M), where N and M are the space taken by outputArray[] and countArray[] respectively.

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


### 2. Bucket Sort/ Bin Sort

- divides an array's elements into several buckets. The buckets are then sorted one at a time, either using a different sorting algorithm or by recursively applying the bucket sorting algorithm. 

- Methods: 
	1. Create an array of "buckets" that are initially empty
	2. Scatter: Go through the original array, placing each object in its appropriate bucket
	3. Each non-empty bucket should be sorted
	4. Gather: Return all elements to the original array after visiting the buckets in order










