373. Find K Pairs with Smallest Sums - Medium

You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.

Define a pair (u,v) which consists of one element from the first array and one element from the 
second array.

Find the k pairs (u1,v1),(u2,v2) ...(uk,vk) with the smallest sums.

Example 1:
Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
Output: [[1,2],[1,4],[1,6]] 
Explanation: The first 3 pairs are returned from the sequence: 
             [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]

Example 2:
Input: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
Output: [1,1],[1,1]
Explanation: The first 2 pairs are returned from the sequence: 
             [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]

Example 3:
Input: nums1 = [1,2], nums2 = [3], k = 3
Output: [1,3],[2,3]
Explanation: All possible pairs are returned from the sequence: [1,3],[2,3]

******************************************************
key:
	- Heap
	- edge case:
		1) empty array, return []
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- Use min_heap to keep track on next minimum pair sum, and we only need to maintain K possible 
		candidates in the data structure.

	- For every numbers in nums1, its best partner(yields min sum) always strats from nums2[0] 
		since arrays are all sorted; And for a specific number in nums1, its next candidate should be 
		[this specific number] + nums2[current_associated_index + 1], unless out of boundary

	- https://leetcode.com/problems/find-k-pairs-with-smallest-sums/discuss/84551/simple-Java-O(KlogK)-solution-with-explanation

stats:

	- The run time complexity is O(kLogk) since que.size <= k and we do at most k loop.
	- 

public class Solution {
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
    	// sort the heap
        PriorityQueue<int[]> que = new PriorityQueue<>((a,b)->a[0]+a[1]-b[0]-b[1]);


        List<int[]> res = new ArrayList<>();

        if(nums1.length==0 || nums2.length==0 || k==0) 
        	return res;

        // add num1, num2, position of num2 in the num2[]
        for(int i=0; i<nums1.length && i<k; i++) 
        	que.offer(new int[]{nums1[i], nums2[0], 0});

        while(k-- > 0 && !que.isEmpty()){
            int[] cur = que.poll();
            res.add(new int[]{cur[0], cur[1]});

            if(cur[2] == nums2.length-1) 
            	continue;
            
            //adds (same num1, next element in num2, count of num2's position++)
            que.offer(new int[]{cur[0],nums2[cur[2]+1], cur[2]+1});
        }
        return res;
    }
}



=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 
public class Solution {
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<Tuple> pq = new PriorityQueue<Tuple>();
        int m = nums1.length, n = nums2.length;
        List<int[]> res = new ArrayList<int[]>();

        if(nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0 || k <= 0) 
        	return res;

        // go through array 2
        for(int j = 0; j <= n-1; j++) 
        		pq.offer(new Tuple(0, j, nums1[0]+nums2[j]));

        for(int i = 0; i < Math.min(k, m *n); i++) {
            Tuple t = pq.poll();
            res.add(new int[]{nums1[t.x], nums2[t.y]});
            if(t.x == m - 1) continue;
            pq.offer(new Tuple (t.x + 1, t.y, nums1[t.x + 1] + nums2[t.y]));
        }
        return res;
    }
}

class Tuple implements Comparable<Tuple> {
    int x, y, val;
    public Tuple (int x, int y, int val) {
        this.x = x;
        this.y = y;
        this.val = val;
    }
    
    @Override
    public int compareTo (Tuple that) {
        return this.val - that.val;
    }
}
=======================================================================================================
method 3:

method:

	- no priority queue/heap!
	- Different from classical two pointer problems, we should use an array to record positions, 
		since it does not necessarily always make sense that index2 will keep moving forward from 
		previous position.

	- If index1 move forward, then index2 should start from the first place in nums2
		ex. nums1 = {1,3,4,5} and nums2 = {2,3,4,5}, when index1->1 && index2->1, sum = 1 + 2 = 3; 
		then we keep moving, index1->1 && nums2->2 and sum = 1 + 3 = 4; 
		keep going then index1->1 && index2->3 and sum = 1 + 4 = 5; 
		if we keep moving index2 forward, then index1->1 && index2->4, it does not make sense since 
			index1->2 && index2->1 should get smaller sum. 

		You can see here, we move the index1 forward instead of index2 and reset index2 to 0 and 
			start over again. Since we do not want to reset index2 every time when necessary, we can 
			create an array since an array will initialize every position to value of 0.

	- You can simple understand that you have a pair of index(index1, index2) in which 
		case index2 = index2[index1], which means you can use only one variable index1 to record two 
		variables(two positions in nums1 and nums2).

	- Because both array are sorted, so we can keep track of the paired index. Therefore, we do not 
		need to go through all combinations when k < nums1.length + num2.length. 

stats:

	- Time complexity is O(k*m) where m is the length of the shorter array.
	- 


public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<int[]> ret = new ArrayList<int[]>();
        if(nums1.length == 0 || nums2.length == 0 || k == 0) return ret;
        
        //index2 is used for recording position in nums2 corresponding to given position in nums1
        int[] index2 = new int[nums1.length];
        while(k-- > 0){
            int min = Integer.MAX_VALUE;
            //every time we should start from the first place in nums2 to find proper position
            int index = -1;
            for(int index1 = 0; index1 < nums1.length; index1 ++){
                if(index2[index1] >= nums2.length) continue;
                
                if(nums1[index1] + nums2[index2[index1]] < min){
                    min = nums1[index1] + nums2[index2[index1]];
                    //keep record the index in nums1
                    index = index1;
                }
            }
            if(index == -1) break;
            
            int[] temp = {nums1[index], nums2[index2[index]]};
            ret.add(temp);
            index2[index] ++;
        }
        return ret;
    }
