Google largest subarray length k

https://leetcode.com/discuss/interview-question/352459/


test case: {1,1,0,2,4} k = 3 --> 0,2,4


int[] A = new int[]{1,4,2,3,5};
int k = 3; --> expect ans = {4,2,3}


private static int[] largestSubArray(int k, int[] a) {
	int maxno = Integer.MIN_VALUE;
	int maxStartIndex = 0;
	int[] largestSubArr = new int[k];

	// find the Maximum number within the range,
	// as we don't need to check sub-arrays starting with smaller numbers
	for (int x = 0; x <= a.length - k; x++) {
		if (a[x] > maxno)
			maxno = a[x];
	}

	for (int x = 0; x <= a.length - k; x++) {
		if (a[x] == maxno) // check only for maximum number..reducing the number of comparisons
			maxStartIndex = largestArray(maxStartIndex, a, x, k);
	}
	
	for (int i = maxStartIndex; i < maxStartIndex + k; i++) {   // fill the final largest array
		largestSubArr[i - maxStartIndex] = a[i];
	}

	return largestSubArr;
}

private static int largestArray(int maxIndex, int[] a, int x, int k) {

	for (int i = 0; i <= k - 1; i++) {
		if (a[maxIndex + i] < a[x + i]) {
			return x;
		}
		if (a[maxIndex + i] > a[x + i]) {
			return maxIndex;
		}
	}

	return maxIndex;
}

=================

// for distinct integers
	private static int[] largestSubArray(int k, int[] a) {
        int max = Integer.MIN_VALUE;
        int index = -1;

        if(a.length == k) {
            return a;
        }

        for(int i = 0; i <= a.length-k;i++) {
            if(a[i]>max) {
                index = i;
                max = a[i];
            }
        }

        int[] subarray = new int[k];
		System.arraycopy(a, index, subarray, 0, index+k);
		return subarray;
        //return Arrays.copyOfRange(a, index, index+k);
    }