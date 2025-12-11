421. Maximum XOR of Two Numbers in an Array - Medium

!!! additional question added --> max xor of subarray (multiple numbers) at the end

Given a non-empty array of numbers, a0, a1, a2, … , an-1, where 0 ≤ ai < 231.

Find the maximum result of ai XOR aj, where 0 ≤ i, j < n.

Could you do this in O(n) runtime?

Example:

Input: [3, 10, 5, 25, 2, 8]

Output: 28

Explanation: The maximum result is 5 ^ 25 = 28.

******************************************************
key:
	- Trie
	- edge case:
		1) 
		2)

******************************************************



=======================================================================================================
Method 1: https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/discuss/130427/()-92

method:

	- 这个过程中对于5,10,8的二进制前28位异或结果是一样的，对于10,8而言前30位异或结果是一致的。很容易想到对数组中的数
		的二进制，构建前缀树。相同前缀的计算结果可复用，就能提升效率。
	- 因为二进制元素只有0,1。考虑使用二叉树来构建前缀树。
	- 根节点为符号位0, 从高位到低位依次构建, 左子树为1节点，又子树为0节点
		[3,10,5,25,2,8] 按照以上规则构建如下图（省略高位0）
		那么这颗树包含了数组中所有的数字，从根节点到叶子节点的一条路径表示数组中的一个十进制数的二进制编码。

	- 找最大异或值: 对于25 (0000 0000 0000 0000 0000 0000 0001 1001) 而言，从第31-6位（都是正数，不考虑符号位），
		都为0,，且数组中的数字31-6位都为0，因此最大异或结果31-6位只能为0。

		当前使得异或结果最大的数x为0000 0000 0000 0000 0000 0000 000? ????
		当前指针curNode指向第图中根节点。
			从第5位开始：
			5 1 x的第5位为0，则结果最大。应选择右分支，curNode = curNode->right
			4 1 x的第4位为0，则结果最大。应选择右分支，curNode = curNode->right
			3 0 x的第3位为1，则结果最大。应选择左分支，curNode = curNode->left
			2 0 x的第2位为1，则结果最大。应选择左分支，但树中左分支为null，只能选择右分支curNode = curNode->right 于是x的第2位为0。
			1 1 x的第1位为0，则结果最大。应选择右分支，但树中右分支为null，只能选择左分支curNode = curNode->left 于是x的第1位为1。
			找到的x为5（00101）。

stats:

	- 
	- 

class Solution {

	public int findMaximumXOR(int[] nums) {
		if (nums.length == 0) {
			return 0;
		}

		TrieNode root = new TrieNode();

		// 建树 --> 每一个数字有32位
		// 每一个数字循环32次，反正输入没有负数
		// 从最高位开始处理
		for (int i : nums) {
			TrieNode node = root;
			for (int j = 31; j >= 0; j--) {
				int bit = (i >> j) & 1;
				if (bit == 1) {
					if (node.one == null) {// one的位置是null，就新建
						node.one = new TrieNode();
					}
					node = node.one;
				} else {
					if (node.zero == null) {
						node.zero = new TrieNode();
					}
					node = node.zero;
				}
			}
		}

		int max = Integer.MIN_VALUE;

		//找每一个数字和别的数字异或出来的最大值！
		//所有最大值，取其中最大的
		for (int i : nums) {
			TrieNode node = root;
			int XOR = 0;// 异或结果,的累加。
			for (int j = 31; j >= 0; j--) {
				int bit = (i >> j) & 1;
				//如果这一位是1，为了异或最大，最好使用0，这样异或出来是1
				//如果0不存在就只能使用1，这样这一位异或出来就是0
				//如果这一位是0，为了异或最大，最好使用1，这样异或出来是1
				//如果1不存在就只能使用0，这样这一位异或出来就是0
				if (bit == 1) {
					if (node.zero != null) {
						node = node.zero;
						XOR += 1 << j;
					} else {
						node = node.one;
						XOR += 0 << j;// 0没用为了明确还是写上
					}
				} else {
					if (node.one != null) {
						node = node.one;
						XOR += 1 << j;
					} else {
						node = node.zero;
						XOR += 0 << j;
					}
				}
			}
			max = max > XOR ? max : XOR;
		}

		return max;
	}
}

class TrieNode {
	TrieNode zero;
	TrieNode one;
}


=======================================================================================================
method 2:

method:

	- 根据题目描述，我们需要找到最大的异或值，异或代表了两个数的二进制的不同程度，且越高位越不一样，异或值就越大，
		由于是按位比较，所以使用Trie树来当做基础数据结构。

因为整型的位数是固定的，排除第一位符号位，Trie树的高度是常数的，即最高32层（包括root）
由于只有0和1两个子节点，所以为了节省空间，可以使用二叉树的方式（或者数组和HashMap均可）
由于是异或，前缀位如果相同，异或值都是0，所以可以先找到第一个两个子节点都不为空的节点当做root
	- 

stats:

	- Runtime: 11 ms, faster than 97.61% of Java online submissions for Maximum XOR of Two Numbers in an Array.
Memory Usage: 49.5 MB, less than 11.76% 
	- 

class Solution {
    class TrieNode {
        int val;
        TrieNode zero, one;
        boolean isEnd;
    }
    class TrieTree {
        TrieNode root;
        public TrieTree() {
            root = new TrieNode();
        }
        public void insert(int num) {
            TrieNode cur = root;
            int j = 1 << 30;
            for (int i = 0; i < 31; i++) {
                // 根据num在j位置的数目判断应该向0还是向1
                int b = (j & num) == 0 ? 0 : 1;
                if (b == 0 && cur.zero == null) {
                    cur.zero = new TrieNode();
                }
                if (b == 1 && cur.one == null) {
                    cur.one = new TrieNode();
                }
                cur = b == 0 ? cur.zero : cur.one;
                j >>= 1;
            }
            cur.isEnd = true;
            cur.val = num;
        }
    }

    public int findMaximumXOR(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        TrieTree tree = new TrieTree();
        for (int n : nums) {
            tree.insert(n);
        }
        // 获取真正需要开始判断的root
        TrieNode cur = tree.root;
        while (cur.one == null || cur.zero == null) {
            cur = cur.zero != null ? cur.zero : cur.one;
        }
        return maxHelper(cur.one, cur.zero);

    }

    /**
     * 分治法，原则就是尽量使两个分支的高位不同
     * one是1分支，zero是0分支，可以看做图中的左区和右区
     * 1. 当1分支的下一位只有1时，找0分支的0，若没有，就找0分支的1
     * 2. 当1分支的下一位只有0时，找0分支的1，若没有，就找0分支的0
     * 3. 当1分支的下一位0，1均有时，看0分支：如果0分支只有1，则1分支走0，反之则走1
     * 4. 当0，1两个分支均有两个下一位时，尝试【1分支走1，0分支走0】和【1分支走0，0分支走1】两条路线并取最大值
     * */
    private int maxHelper(TrieNode one, TrieNode zero) {
        if (one.isEnd && zero.isEnd) {
            return one.val ^ zero.val;
        }
        if (one.zero == null) {
            return maxHelper(one.one, zero.zero == null ? zero.one : zero.zero);
        } else if (one.one == null) {
            return maxHelper(one.zero, zero.one == null ? zero.zero : zero.one);
        } else if (zero.zero == null) {
            return maxHelper(one.zero, zero.one);
        } else if (zero.one == null) {
            return maxHelper(one.one, zero.zero);
        } else {
            return Math.max(maxHelper(one.one, zero.zero), maxHelper(one.zero, zero.one));
        }
    }
}

=======================================================================================================
Questions:
Given an array of integers. find the maximum XOR subarray value in given array. Expected time 
complexity O(n).


method:


1) Create an empty Trie.  Every node of Trie is going to 
   contain two children, for 0 and 1 value of bit.
2) Initialize pre_xor = 0 and insert into the Trie.
3) Initialize result =  -infinite
4) Traverse the given array and do following for every array element arr[i].
       a) pre_xor  = pre_xor  ^ arr[i]
          pre_xor now contains xor of elements from arr[0] to arr[i].

       b) Query the maximum xor value ending with arr[i] from Trie.
			- We can observe from above algorithm that we build a Trie that contains XOR of all 
			  prefixes of given array. To find the maximum XOR subarray ending with arr[i], there may 
			  be two cases.
			i) The prefix itself has the maximum XOR value ending with arr[i]. For example if i=2 
			   in {8, 2, 1, 12}, then the maximum subarray xor ending with arr[2] is the whole prefix.
			   - If there is no prefix to be removed, we return 0 (that’s why we inserted 0 in Trie).

			ii) We need to remove some prefix (ending at index from 0 to i-1). For example if i=3 in 
			   {8, 2, 1, 12}, then the maximum subarray xor ending with arr[3] starts with arr[1] and 
			   we need to remove arr[0].

				- To find the prefix to be removed, we find the entry in Trie that has maximum XOR value 
				with current prefix. If we do XOR of such previous prefix with current prefix, we get 
				the maximum XOR value ending with arr[i].

       c) Update result if the value obtained in step 4.b is more than current value of result.


// Java program for a Trie based O(n) solution to 
// find max subarray XOR 
class GFG 
{ 
	// Assumed int size 
	static final int INT_SIZE = 32; 
	
	// A Trie Node 
	static class TrieNode 
	{ 
		int value; // Only used in leaf nodes 
		TrieNode[] arr = new TrieNode[2]; 
		public TrieNode() { 
			value = 0; 
			arr[0] = null; 
			arr[1] = null; 
		} 
	} 
	static TrieNode root; 
	
	// Inserts pre_xor to trie with given root 
	static void insert(int pre_xor) 
	{ 
		TrieNode temp = root; 
	
		// Start from the msb, insert all bits of 
		// pre_xor into Trie 
		for (int i=INT_SIZE-1; i>=0; i--) 
		{ 
			// Find current bit in given prefix 
			int val = (pre_xor & (1<<i)) >=1 ? 1 : 0; 
	
			// Create a new node if needed 
			if (temp.arr[val] == null) 
				temp.arr[val] = new TrieNode(); 
	
			temp = temp.arr[val]; 
		} 
	
		// Store value at leaf node 
		temp.value = pre_xor; 
	} 
	
	// Finds the maximum XOR ending with last number in 
	// prefix XOR 'pre_xor' and returns the XOR of this 
	// maximum with pre_xor which is maximum XOR ending 
	// with last element of pre_xor. 
	static int query(int pre_xor) 
	{ 
		TrieNode temp = root; 
		for (int i=INT_SIZE-1; i>=0; i--) 
		{ 
			// Find current bit in given prefix 
			int val = (pre_xor & (1<<i)) >= 1 ? 1 : 0; 
	
			// Traverse Trie, first look for a 
			// prefix that has opposite bit 
			if (temp.arr[1-val] != null) 
				temp = temp.arr[1-val]; 
	
			// If there is no prefix with opposite 
			// bit, then look for same bit. 
			else if (temp.arr[val] != null) 
				temp = temp.arr[val]; 
		} 
		return pre_xor^(temp.value); 
	} 
	
	// Returns maximum XOR value of a subarray in 
		// arr[0..n-1] 
	static int maxSubarrayXOR(int arr[], int n) 
	{ 
		// Create a Trie and insert 0 into it 
		root = new TrieNode(); 
		insert(0); 
	
		// Initialize answer and xor of current prefix 
		int result = Integer.MIN_VALUE; 
		int pre_xor =0; 
	
		// Traverse all input array element 
		for (int i=0; i<n; i++) 
		{ 
			// update current prefix xor and insert it 
				// into Trie 
			pre_xor = pre_xor^arr[i]; 
			insert(pre_xor); 
	
			// Query for current prefix xor in Trie and 
			// update result if required 
			result = Math.max(result, query(pre_xor)); 

		} 
		return result; 
	} 
	
	// Driver program to test above functions 
	public static void main(String args[]) 
	{ 
		int arr[] = {8, 1, 2, 12}; 
		int n = arr.length; 
		System.out.println("Max subarray XOR is " + 
								maxSubarrayXOR(arr, n)); 
	} 
} 
// This code is contributed by Sumit Ghosh 













