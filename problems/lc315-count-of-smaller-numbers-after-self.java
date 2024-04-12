315. Count of Smaller Numbers After Self - Hard

You are given an integer array nums and you have to return a new counts array. The counts array has
the property where counts[i] is the number of smaller elements to the right of nums[i].

Example:

Input: [5,2,6,1]
Output: [2,1,1,0] 
Explanation:
To the right of 5 there are 2 smaller elements (2 and 1).
To the right of 2 there is only 1 smaller element (1).
To the right of 6 there is 1 smaller element (1).
To the right of 1 there is 0 smaller element.

******************************************************
key:
	- merge sort, BST, 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

https://www.youtube.com/watch?v=AeyUmjk4HGQ


method:

	- merge sort
	- The basic idea is to do merge sort to nums[]. To record the result, we need to keep the index 
	  of each number in the original array. So instead of sort the number in nums, we sort the indexes 
	  of each number.
		Example: nums = [5,2,6,1], indexes = [0,1,2,3]
		After sort: indexes = [3,1,0,2]

	- While doing the merge part, say that we are merging left[] and right[], left[] and right[] are 
	  already sorted.

	  We keep a rightcount to record how many numbers from right[] we have added and keep an array 
	  count[] to record the result.

	  When we move a number from right[] into the new sorted array, we increase rightcount by 1.

	  When we move a number from left[] into the new sorted array, we increase count[index of the 
	  number] by rightcount.

    - arraycopy(Object source_arr, int sourcePos,Object dest_arr, int destPos, int len)

stats:
    - Time: O(n * log n)
	- Runtime: 5 ms, faster than 77.13% 
    - Memory Usage: 44.1 MB, less than 5.55% 


class Solution {
    class Item {
        int val;
        int index;
        public Item(int v, int i) {
            val = v;
            index = i;
        }
    }

    public List<Integer> countSmaller(int[] nums) {
        int n = nums.length;
        Item[] items = new Item[n];
        for (int i = 0; i < n; i++) {
            items[i] = new Item(nums[i], i);
        }

        int[] count = new int[n];
        mergeSort(items, 0, n - 1, count);
        List<Integer> res = new ArrayList<>();
        for (int i : count) {
            res.add(i);
        }
        return res;
    }

    private void mergeSort(Item[] items, int lo, int hi, int[] count) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        mergeSort(items, lo, mid, count);
        mergeSort(items, mid + 1, hi, count);
        merge(items, lo, mid, mid + 1, hi, count);
    }

    // note, here the lo part is already sorted and high part is already sorted!
    private void merge(Item[] items, int lo, int loEnd, int hi, int hiEnd, int[] count) {
        // number of items to merge
        int m = hiEnd - lo + 1;

        // store result after merge
        Item[] sorted = new Item[m];

        int rightCounter = 0;
        int loPtr = lo, 
            hiPtr = hi;

        // index going from left to right to store sorted elements in sorted[]
        int index = 0;

        while (loPtr <= loEnd && hiPtr <= hiEnd) {

            // the order is reversed, means at loPtr, there's at least 1
            // element that is smaller than loPtr.val, so increment rightCounter
            // pull the 'head' of higher array
            if (items[loPtr].val > items[hiPtr].val) {
                rightCounter++;
                // because the lower part is already sorted!
                sorted[index++] = items[hiPtr++];
            } else {
                // pull the 'head' of lower array
                // since the lower array are to the left of higher array, so don't increment rightCounter
                count[items[loPtr].index] += rightCounter;
                sorted[index++] = items[loPtr++];
            }
        }

        // if there are left over low array, means all already sorted higher part
        // are ???????
        while (loPtr <= loEnd) {
            count[items[loPtr].index] += rightCounter;
            sorted[index++] = items[loPtr++];
        }

        while (hiPtr <= hiEnd) {
            sorted[index++] = items[hiPtr++];
        }

        // replace original unsorted section in items with the now sorted ones.
        System.arraycopy(sorted, 0, items, lo, m);
    }
}

=======================================================================================================
method 2:

method:

	- binary search tree
	- Traverse from nums[len - 1] to nums[0], and build a binary search tree, which stores:

		val: value of nums[i]
		count: if val == root.val, there will be count number of smaller numbers on the right


	  Every node will maintain a val sum recording the total of number on it left bottom side, dup 
	  counts the duplication. For example, [3, 2, 2, 6, 1], from back to beginning, we would have:

                1(0, 1)
                     \
                     6(3, 1)
                     /
                   2(0, 2)
                       \
                        3(0, 1)

	  When we try to insert a number, the total number of smaller number would be adding dup and sum 
	  of the nodes where we turn right. for example, if we insert 5, it should be inserted on the 
	  way down to the right of 3, the nodes where we turn right is 1(0,1), 2,(0,2), 3(0,1), so the 
	  answer should be (0 + 1)+(0 + 2)+ (0 + 1) = 4

      if we insert 7, the right-turning nodes are 1(0,1), 6(3,1), so answer should be (0 + 1) + 
      (3 + 1) = 5

stats:


	- The worst case can be O(N^2) since you cannot ensure that the tree is balanced



public class Solution {
	class TreeNode {
		TreeNode left; 
		TreeNode right;
		int val;
		int count = 1;
		public TreeNode(int val) {
			this.val = val;
		}

	public List<Integer> countSmaller(int[] nums) {
		List<Integer> res = new ArrayList<>();
		if(nums == null || nums.length == 0) return res;
		TreeNode root = new TreeNode(nums[nums.length - 1]);
		res.add(0);

		// add from the back
		for(int i = nums.length - 2; i >= 0; i--) {
			int count = insertNode(root, nums[i]);
			res.add(count);
		}
		Collections.reverse(res);
		return res;
	}

	public int insertNode(TreeNode root, int val) {
		int thisCount = 0;
		while(true) {
			if(val <= root.val) {

				// add on the left, let root know a previous elem is < root.
				root.count++;
				if(root.left == null) {
					root.left = new TreeNode(val); 
					break;
				} else {
					root = root.left;
				}
			} else {

				// turn right, increment count. 
				thisCount += root.count;
				if(root.right == null) {
					root.right = new TreeNode(val); 
					break;
				} else {
					root = root.right;
				}
			}
		}
		return thisCount;
	}
}



=======================================================================================================
method 3:

https://zxi.mytechroad.com/blog/algorithms/array/leetcode-315-count-of-smaller-numbers-after-self/

method:

	- Binary Index Tree
    - Step 1: reverse array & get rank for each item.

    - prefix sums of the frequencies, convert number to its rank in sorted array

stats:

	-      Time: O(n* log n)
	-       Space: O(n)


class Solution {
  private static int lowbit(int x) { return x & (-x); }
  
  class FenwickTree {    
    private int[] sums;    
    
    public FenwickTree(int n) {
      sums = new int[n + 1];
    }
 
    // 
    public void update(int i, int delta) {    
      while (i < sums.length) {
          sums[i] += delta;
          i += lowbit(i);
      }
    }
 
    // 求 0 到 i 元素的总和, count # of 1 in BIT, same as 
    public int query(int i) {       
      int sum = 0;
      while (i > 0) {
          sum += sums[i];
          i -= lowbit(i);
      }
      return sum;
    }    
  };
  
  public List<Integer> countSmaller(int[] nums) {

    // sort & rank the original array, ranks contain: {(1,0), (2,1), (5,2), (6,5)}
    // use hashmap to find # of unique elements.
    int[] sorted = Arrays.copyOf(nums, nums.length);
    Arrays.sort(sorted);
    Map<Integer, Integer> ranks = new HashMap<>();
    int rank = 0;
    for (int i = 0; i < sorted.length; ++i)
      if (i == 0 || sorted[i] != sorted[i - 1])
        ranks.put(sorted[i], ++rank);
    

    FenwickTree tree = new FenwickTree(ranks.size());
    List<Integer> ans = new ArrayList<Integer>();

    // go from the back, sum gets the 
    for (int i = nums.length - 1; i >= 0; --i) {

        int sum = tree.query(ranks.get(nums[i]) - 1);      
        ans.add(sum);
        tree.update(ranks.get(nums[i]), 1);
    }
    
    Collections.reverse(ans);
    return ans;
  }
}

------- optimize/faster

public class Solution {
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new LinkedList<Integer>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        // find min value and minus min by each elements, plus 1 to avoid 0 element
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            min = (nums[i] < min) ? nums[i]:min;
        }
        int[] nums2 = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            nums2[i] = nums[i] - min + 1;
            max = Math.max(nums2[i],max);
        }
        int[] tree = new int[max+1];
        for (int i = nums2.length-1; i >= 0; i--) {
            res.add(0,get(nums2[i]-1,tree));
            update(nums2[i],tree);
        }
        return res;
    }
    private int get(int i, int[] tree) {
        int num = 0;
        while (i > 0) {
            num +=tree[i];
            i -= i&(-i);
        }
        return num;
    }
    private void update(int i, int[] tree) {
        while (i < tree.length) {
            tree[i] ++;
            i += i & (-i);
        }
    }
}