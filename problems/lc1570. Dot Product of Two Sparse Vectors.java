1570. Dot Product of Two Sparse Vectors - Medium

Given two sparse vectors, compute their dot product.

Implement class SparseVector:

SparseVector(nums) Initializes the object with the vector nums
dotProduct(vec) Compute the dot product between the instance of SparseVector and vec
A sparse vector is a vector that has mostly zero values, you should store the sparse vector efficiently and compute the dot product between two SparseVector.

 

Example 1:

Input: nums1 = [1,0,0,2,3], nums2 = [0,3,0,4,0]
Output: 8
Explanation: v1 = SparseVector(nums1) , v2 = SparseVector(nums2)
v1.dotProduct(v2) = 1*0 + 0*3 + 0*0 + 2*4 + 3*0 = 8
Example 2:

Input: nums1 = [0,1,0,0,0], nums2 = [0,0,0,0,2]
Output: 0
Explanation: v1 = SparseVector(nums1) , v2 = SparseVector(nums2)
v1.dotProduct(v2) = 0*0 + 1*0 + 0*0 + 0*0 + 0*2 = 0
Example 3:

Input: nums1 = [0,1,0,0,2,0,0], nums2 = [1,0,0,0,3,0,4]
Output: 6
 

Constraints:

n == nums1.length == nums2.length
1 <= n <= 10^5
0 <= nums1[i], nums2[i] <= 100


Follow up: What if only one of the vectors is sparse?

pick upon the indices of the sparse vector and then binary search the indices in the second vector. 
In this case complexity would be (l log N) where l is the length of sparse vector and N is the 
length of non-sparse vector.

******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

只需要存：(index → value), 换句话说，你只记录 值不为 0 的位置。
比如 vector [0,1,0,0,2] 变成：[(1, 1), (4, 2)]

Stats:

	- 
	- 

class SparseVector {
    List<int[]> list = new ArrayList<>();
    
    SparseVector(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                list.add(new int[]{i, nums[i]}); // 存 index, value
            }
        }
    }
    
    // Return the dotProduct of two sparse vectors
    public int dotProduct(SparseVector vec) {
        int i = 0, j = 0, res = 0;
        
        while (i < this.list.size() && j < vec.list.size()) {
            int index1 = this.list.get(i)[0];
            int index2 = vec.list.get(j)[0];
            
            if (index1 == index2) {
                res += this.list.get(i)[1] * vec.list.get(j)[1];
                i++;
                j++;
            } else if (index1 < index2) {
                i++;
            } else {
                j++;
            }
        }
        
        return res;
    }
}


🆚 是否可以用 HashMap？

可以，不过不如双指针快（迭代 map 成本高）。但如果 index 非常大（比如 1e9），Map 会更自然。

双指针仍然是 最优解。

Technically Hashmap uses an array of nodes internally, but to get an element we hash the key, 
and then access this array based on the hashcode. It's highly probable that the chunk of 
array where our destination lies is not in the cache, hence a cache miss.





