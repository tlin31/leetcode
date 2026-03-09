274. H-Index
Medium

Given an array of integers citations where citations[i] is the number of citations a researcher 
received for their ith paper, return the researcher's h-index.

According to the definition of h-index on Wikipedia: The h-index is defined as the maximum value 
of h such that the given researcher has published at least h papers that have each been cited at least h times.

 

Example 1:
Input: citations = [3,0,6,1,5]
Output: 3
Explanation: [3,0,6,1,5] means the researcher has 5 papers in total and each of them had 
received 3, 0, 6, 1, 5 citations respectively.

Since the researcher has 3 papers with at least 3 citations each and the remaining two with 
no more than 3 citations each, their h-index is 3.

Example 2:
Input: citations = [1,3,1]
Output: 1
 

Constraints:

n == citations.length
1 <= n <= 5000
0 <= citations[i] <= 1000

******************************************************
key:
	- bucket sort
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	-the h-index is defined as the number of papers with reference greater than the number. 
	So assume n is the total number of papers, if we have n+1 buckets, number from 0 to n, then 
	for any paper with reference corresponding to the index of the bucket, we increment the count 
	for that bucket. The only exception is that for any paper with larger number of reference than n,
	 we put in the n-th bucket.

	- Then we iterate from the back to the front of the buckets, whenever the total count exceeds 
	the index of the bucket, meaning that we have the index number of papers that have reference 
	greater than or equal to the index. Which will be our h-index result. The reason to scan from 
	the end of the array is that we are looking for the greatest h-index. 


规律：如果一个学者的发表文章总数为 n，那么他的 H 指数最高也不可能超过 n 。
策略：
创建一个长度为 n 的数组 buckets。
遍历原数组，记录每种引用次数出现的频率。
重点：如果某篇文章的引用次数超过了 n，我们直接把它记在 buckets[n] 上（因为 h 指数最大就是 n）。
逆向累加：从后往前遍历 buckets，累加文章数量。当我们累加的数量第一次 >= 当前下标 i 时，
        i 就是我们要找的 H 指数。

class Solution {
    public int hIndex(int[] citations) {
        int n = citations.length;
        int[] buckets = new int[n + 1];

        // 1. 填充桶 (Bucket Filling)
        for (int c : citations) {
            if (c >= n) {
                buckets[n]++;
            } else {
                buckets[c]++;
            }
        }

        // 2. 逆向统计 (Reverse Accumulation)
        int count = 0;
        for (int i = n; i >= 0; i--) {
            count += buckets[i]; // 引用次数至少为 i 的文章总数
            if (count >= i) {
                return i;
            }
        }

        return 0;
    }
}


class Solution:
    def hIndex(self, citations: List[int]) -> int:
        n = len(citations)
        temp = [0 for _ in range(n + 1)]

        for i,v in enumerate(citations):
            if v > n :
                temp[n] += 1
            else:
                temp[v] += 1
        
        total = 0
        for i in range(n, -1, -1):
            total += temp[i]
            if total >= i:
                return i

                
