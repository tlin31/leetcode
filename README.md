# leetcode-java
leetcode and key points 

link to leetcode company repo: https://github.com/youhusky/Leetcode_Company
ref to detail solution in chinese: https://leetcode.wang/leetCode-30-Substring-with-Concatenation-of-All-Words.html !!!with time complexity for each problem

## Two pointers & sliding windows

note:
1. always start with:
		
		ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        dummy.next = head;
        use curr to go through list and perform actions
        return dummy.next;


2. to delete a node: 不要跳过所求的node，停在那个node之前，才能skip desired node
3. when change order, always set x.next = y, then change the node before x
4. always check, head != null

5. Two pointers: 

        while(leftPointer< RightPointer) 

6. Peak and valley:
- when there are a simultaneously increasing or decreasing array
- ex. lc 135-Candy
- can use Stack to 

7. reverse a certain part in linked list
```java
    dummy.next = head;
    ListNode pre = dummy; // pre来traverse
    for(int i = 0; i<m-1; i++) pre = pre.next;
    
    ListNode start = pre.next; // a pointer to the beginning of a sub-list that will be reversed
    ListNode then = start.next; // a pointer to a node that will be reversed
    
    // 1 - 2 -3 - 4 - 5 ; m=2; n =4 ---> pre = 1, start = 2, then = 3
    // dummy-> 1 -> 2 -> 3 -> 4 -> 5
    
    for(int i=0; i<n-m; i++)
    {
        start.next = then.next;
        then.next = pre.next;
        pre.next = then;
        then = start.next;
    }
    
    // first reversing : dummy->1 - 3 - 2 - 4 - 5; pre = 1, start = 2, then = 4
    // second reversing: dummy->1 - 4 - 3 - 2 - 5; pre = 1, start = 2, then = 5 (finish)
    
    return dummy.next;
```

Sliding Windows:
	
	problem: [lc3 - Longest Substring Without Repeating Characters](problems/lc3_longest_substring_without_repeating_char.java)
			 [lc 76 - Minimum Window Substring](problems/lc76-min-window-substr.java)
			 lc 239
			 lc 1040
			 lc 424
			 lc 567 !!! interesting sliding window
             lc 713 - subarray product less than k
             lc 992 - subarrays with k different integers
             google - block-with-min-distance
             lc 1074 - # of submatricies that sum to target
             lc 727 - interesting 2points
             lc 1423 - take first k or mid k or last k --> make it a circular array and do sliding window
             lc 1031 - max sum of 2 non-overlapping subarrays, prefix sum + sliding window


### Leetcode
|# |    Title   |Solution|Complexity|Note|Difficulty|
|---| ----------| ------ |----------|----|----------|
|1|[633. Sum of Square Numbers](https://leetcode.com/problems/sum-of-square-numbers/submissions/)|[Java](problems/lc-633_sum_of_square_num.java)| | binary search | easy
|2|[69. Sqrt(x)](https://leetcode.com/problems/sqrtx/)|[Java](problems/lc69_sqrt_x.java)| | | easy
|3|[3. Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/)|[Java](problems/lc3_longest_substring_without_repeating_char.java)|O(n) |key: when to make the left pointer move & fastest way to check whether a char is seen before | easy
|4|[5.Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/)|[Java](problems/lc5_longest_palindrome.java)|O(n), O(1) |!!! start from middle, 2 pointers / Manchester's algo --> linear time |Medium
|5|[11. Container With Most water](https://leetcode.com/problems/container-with-most-water/)|[Java](problems/lc11_container_most_water.java)| | practise the reasoning | Medium
|6|[15. 3 Sum](https://leetcode.com/problems/3sum/)|[Java](problems/lc15_3_sum.java)|O(N^2) | | Medium
|7|[17. 3 Sum Closest](https://leetcode.com/problems/3sum-closest/)|[Java](problems/lc15_3_sum.java)|O(N^2) | | Medium
|8|[18. 4 Sum ](https://leetcode.com/problems/4sum/)|[Java](problems/lc18_4_sum.java)| | increase speed --> put checks! | Medium
|9|[19. Remove Nth Node From End of List ](https://leetcode.com/problems/remove-nth-node-from-end-of-list/)|[Java](problems/lc19_remove_n_node_from_back_list.java)| | | Medium
|10|[22. Generate Parentheses ](https://leetcode.com/problems/generate-parentheses/)|[Java](problems/lc22_generate_parentheses.java)| | generalization!! redo | Medium
|11|[42. Trapping Rain Water](https://leetcode.com/problems/trapping-rain-water/)|[Java](problems/lc42-trapping-rain-water.java)| time:O（n), space:O（1）|2 pointers, dp, stack | Hard
|12|[76. Minimum Window Substring](https://leetcode.com/problems/minimum-window-substring/)|[Java](problems/lc76-min-window-substr.java)| |sliding window| Hard
|13|[239. Sliding Window Maximum](https://leetcode.com/problems/sliding-window-maximum/)|[Java](problems/lc239-sliding-window-maximum.java)| |sliding window, DP| Hard
|14|[1040. Moving Stones Until Consecutive II](https://leetcode.com/problems/moving-stones-until-consecutive-ii/)|[Java](problems/lc1040-moving-stones-until-consecutive-ii.java)| |sliding window| Medium
|15|[424. Longest Repeating Character Replacement](https://leetcode.com/problems/longest-repeating-character-replacement/)|[Java](problems/lc424-longest-repeating-character-replacement.java)| |sliding window| Medium
|16|[567. Permutation in String](https://leetcode.com/problems/permutation-in-string/)|[Java](problems/lc567-permutation-in-string.java)| |sliding window| Medium
|17|[1004. Max Consecutive Ones III](https://leetcode.com/problems/max-consecutive-ones-iii/)|[Java](problems/lc1004-max-consecutive-ones-iii.java)| |sliding window| Medium
|18|[Count substrings with each character occurring at most k times](https://www.geeksforgeeks.org/count-substrings-character-occurring-k-times/)|[Java](problems/geeks-count-substr-with-occurance-k.java)||sliding window| Medium
|19|[Count substrings with each character occurring at most k times](https://www.geeksforgeeks.org/number-substrings-count-character-k/)|[Java](problems/geeks-num-substring-count-char-k.java)| |sliding window| Medium
|20|[755. Pour Water](https://leetcode.com/problems/pour-water/)|[Java](problems/lc755-airbnb-Pour-Water.java)| |sliding window| Medium
|21|[Airbnb - Implement Queue with Limited Size of Arrays](https://leetcode.com/discuss/interview-question/156969/Implement-Queue-using-fixed-size-array/)|[Java](problems/airbnb-queue-with-limited-size-of-array.java)| |sliding window| Medium
|22|[88. Merge Sorted Array](https://leetcode.com/problems/merge-sorted-array/)|[Java](problems/lc-88-merge-sorted-array.java)| || Easy
|23|[Geeks-Find common elements in three sorted arrays](https://www.geeksforgeeks.org/find-common-elements-three-sorted-arrays/)|[Java](problems/geeks-find-common-elements-three-sorted-arrays.java)| || Easy
|24|[349. Intersection of Two Arrays](https://leetcode.com/problems/intersection-of-two-arrays/)|[Java](problems/lc349-intersection-of-two-arrays.java)| |hashset, sort + 2 pointers, binary search| Easy
|25|[350. Intersection of Two Arrays II](https://leetcode.com/problems/intersection-of-two-arrays-ii/)|[Java](problems/lc350-intersection-of-two-arrays-ii.java)| | count-sort (similar), or hashmap| Easy
|26|[Geeks-Reverse a string without affecting special characters](https://www.geeksforgeeks.org/reverse-a-string-without-affecting-special-characters/)|[Java](problems/geeks-reverse-str-withno-special-char.java)| | count-sort (similar), or hashmap| Easy
|27|[674. Longest Continuous Increasing Subsequence ](https://leetcode.com/problems/longest-continuous-increasing-subsequence/)|[Java](problems/lc674-longest-continuous-increasing-subsequence.java)| | count-sort (similar), or hashmap| Easy
|28|[713. Subarray Product Less Than K ](https://leetcode.com/problems/subarray-product-less-than-k/)|[Java](problems/lc713-subarray-product-less-than-k.java)| | sliding window, or binary search| Medium
|29|[992. Subarrays with K Different Integers](https://leetcode.com/problems/subarrays-with-k-different-integers/)|[Java](problems/lc992-subarrays-with-k-different-integers.java)| O(n)| sliding window, hashmap --> use At Most K diff| Hard
|30|[809. Expressive Words](https://leetcode.com/problems/expressive-words/)|[Java](problems/lc809-expressive-words.java)| O(K*n) = k words, O(n) for checking every word | hashmap, 2 pointers | Medium
|31|[209. Minimum Size Subarray Sum](https://leetcode.com/problems/minimum-size-subarray-sum/)|[Java](problems/lc209-minimum-size-subarray-sum.java)| O(n), |  2 pointers | Medium
|33|[google: block with min distance to all amenities](https://leetcode.com/discuss/interview-question/algorithms/285144/google-minimize-the-distance-to-the-farthest-point)|[Java](problems/google-block-with-min-distance-to-all-amenities.java)| O(n) | sliding window | Medium
|34|[Google - 2 string, make cut to make palindrome a1+b2 or a2+b1](https://leetcode.com/discuss/interview-question/306859/Google-phone-screen-Split-strings-to-form-a-palindrome/287491)|[Java](problems/google-cut-str-to-palindrome.java)| O(n) | sliding window, follow up: cut can be in differnet position for str1 and str2 | Medium
|35|[1055. Shortest Way to Form String](https://leetcode.com/problems/shortest-way-to-form-string/)|[Java](problems/lc1055-shortest-way-to-form-string.java)| O(MN) -> O(N * logM) -> O(N) |  REDO, 2 pointers, hashmap, flatten map | Medium
|36|[1074. Number of Submatrices That Sum to Target](https://leetcode.com/problems/number-of-submatrices-that-sum-to-target/)|[Java](problems/lc1074-number-of-submatrices-that-sum-to-target.java)| O(MN^2) |   | Hard
|37|[727. Minimum Window Subsequence](https://leetcode.com/problems/minimum-window-subsequence/)|[Java](problems/lc727-minimum-window-subsequence.java)| | sliding window/two pointers  | Hard
|38|[283. Move Zeroes](https://leetcode.com/problems/move-zeroes/)|[Java](problems/lc283-move-zeroes.java)| | two pointers  | Easy
|39|[443. String Compression](https://leetcode.com/problems/string-compression/)|[Java](problems/lc443-string-compression.java)| | two pointers  | Easy
|40|[680. Valid Palindrome II](https://leetcode.com/problems/valid-palindrome-ii/)|[Java](problems/lc680-valid-palindrome-ii.java)| | two pointers  | Easy
|41|[392. Is Subsequence](https://leetcode.com/problems/is-subsequence/)|[Java](problems/lc392-is-subsequence.java)| | two pointers  | Easy
|42|[344. Reverse String](https://leetcode.com/problems/reverse-string/)|[Java](problems/lc344-reverse-string.java)| | two pointers  | Easy
|43|[837. New 21 Game](https://leetcode.com/problems/new-21-game/)|[Java](problems/lc837-new-21-game.java)| | sliding windows  | Medium
|44|[16. 3Sum Closest](https://leetcode.com/problems/3sum-closest/)|[Java](problems/lc16_3_sum_closest.java)| | 3 pointers  | Medium
|45|[345. Reverse Vowels of a String](https://leetcode.com/problems/reverse-vowels-of-a-string/)|[Java](problems/lc345-reverse-vowels-of-a-string.java)| | 2 pointers  | Medium
|46|[917. Reverse Only Letters](https://leetcode.com/problems/reverse-only-letters/)|[Java](problems/lc917-reverse-only-letters.java)| | two pointers  | Easy
|47|[287. Find the Duplicate Number](https://leetcode.com/problems/find-the-duplicate-number/)|[Java](problems/lc287-find-the-duplicate-number.java)| | 2 pointers  | Medium
|48|[228. Summary Ranges](https://leetcode.com/problems/summary-ranges/)|[Java](problems/lc228-summary-ranges.java)| | 2 pointers  | Medium
|49|[905. Sort Array By Parity](https://leetcode.com/problems/sort-array-by-parity/)|[Java](problems/lc905-sort-array-by-parity.java)| | two pointers  | Easy
|50|[457. Circular Array Loop](https://leetcode.com/problems/circular-array-loop/)|[Java](problems/lc457-circular-array-loop.java)| | two pointers  | Easy
|51|[1423. Maximum Points You Can Obtain from Cards](https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/)|[Java](problems/lc1423-maximum-points-you-can-obtain-from-cards.java)| | sliding window  | Medium
|52|[1031. Maximum Sum of Two Non-Overlapping Subarrays](https://leetcode.com/problems/maximum-sum-of-two-non-overlapping-subarrays/)|[Java](problems/lc1031-maximum-sum-of-two-non-overlapping-subarrays.java)| | sliding window  | Medium

 








#Array & Linked list
[array-problem summary](https://note.youdao.com/web/#/file/WEBbf5b9176e1667a7087aeca29fcdcb766/note/WEB22425e6d258587db413adefda8979ca4/)
<br> <br />
[array-key points](https://note.youdao.com/web/#/file/WEBbf5b9176e1667a7087aeca29fcdcb766/note/WEB040b60a3c8ed07e20ab6b7535d94cd49/)

### Array

1. Arrays.toString(another_array[]);

2. Sort: Arrays.sort(nums);
    
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);


3. if have result = List<List<Integer>>, then add to the result by:
        result.add(Arrays.asList(x,y, z......));

4. check array is empty:
        if (array.length == 0 || array == null)

5. Array length: 
    int[] a --> a.length
    ArrayList<Integer> b --> b.size()  // because arraylist is a collection element


6. if need to return int[][] result, but don't know the size of result yet:
        
        List<int[]> res = new ArrayList<>();
        return res.toArray(new int[res.size()][]);

// length can be used for int[], double[], String[] 
// to know the length of the arrays.

// length() can be used for String, StringBuilder, etc 
// String class related Objects to know the length of the String


7. type --> Given an unsorted integer array, find the smallest missing positive integer in this range

    lc41, 442, 136

8. change from Arraylist to Array

        arrayListVar.stream().mapToInt(i -> i).toArray();






### Linked List

#### Reverse list

1. iterative method:
- same as swapping A and B

```java
public ListNode reverseList(ListNode head) {
    
    //  1. Initialize 3 pointers: prev as NULL, curr as head, next as NULL.
    ListNode prev = null;
    ListNode next = null;
    ListNode curr = head;

    while (curr != null) {
        next = curr.next;               Before changing next of current, store next node
        curr.next = prev;               change next of current, this is where actual reversing happens
        prev = curr;                    Move prev and curr one step forward
        curr = next;
    }
    return prev;                        In last step, cur = null, and prev stores the last real node
}

```

2.Recursive Method:

   1) Divide the list in two parts - first node and rest of the linked list.
   2) Call reverse for the rest of the linked list.
   3) Link rest to first.
   4) Fix head pointer

```java
  
public ListNode reverseList(ListNode head) {
    return reverseListInt(head, null);
}

private ListNode reverseListInt(ListNode head, ListNode prev) {
    if (head == null)
        return prev;
    ListNode next = head.next;
    head.next = prev;
    return reverseListInt(next, head);          !!! be careful with the order
}


---------
Node reverseUtil(Node curr, Node prev) 
    { 
  
        /* If last node mark it head*/
        if (curr.next == null) { 
            head = curr; 
  
            /* Update next to prev node */
            curr.next = prev; 
  
            return head; 
        } 
  
        /* Save curr->next node for recursive call */
        Node next1 = curr.next; 
  
        /* and update next ..*/
        curr.next = prev; 
  
        reverseUtil(next1, curr); 
        return head; 
    } 
```

Redo:
    
    lc 430: flatten multilevel double linked list
    lc 234: palindrome linked list


#### skip next node

    prev.next = current.next;

#### Tips

1. Always check if node == null
2. use 1 at the begining for any recursive/traversal problems



### Leetcode
|# |    Title   |Solution|Complexity|Note|Difficulty|
|---| ----------| ------ |----------|----|----------|
|1|[2. add two sums linked list](https://leetcode.com/problems/add-two-numbers/)|[Java](problems/2_add_two_sums_linked_list.java)| | create sum node, if one list is shorter than the other | Easy
|2|[61. Rotate List ](https://leetcode.com/problems/rotate-list/)|[Java](problems/61-rotate-list.java)| | find pattern, use mod | Medium
|3|[445. Add Two Numbers II ](https://leetcode.com/problems/add-two-numbers-ii/)|[Java](problems/445-add-two-numbers-2.java)| | linked list | Medium
|4|[Maximum difference between two elements such that larger element appears after the smaller number](https://www.geeksforgeeks.org/maximum-difference-between-two-elements/)|[Java](problems/1_max_diff_two_elem.java)|Time: O(n), Space: O(1)| take the difference with the min element so far. So we remember 1) Maximum difference found so far (max_diff). 2) Minimum number visited so far (min_element).
|5|[Geeks- Remove duplicates from a linked list (both sorted and not sorted)]()|[Java](problems/geeks-remove-dup-linked-list.java)|Time: O(n), Space: O(1)| reverse = swap nearby nodes |Medium|
|6|[138. Copy List with Random Pointer](https://leetcode.com/problems/copy-list-with-random-pointer/)|[Java](problems/lc138-copy-list-with-random-pointer.java)| | linked list | Medium
|7|[937. Reorder Data in Log Files](https://leetcode.com/problems/reorder-data-in-log-files/)|[Java](problems/lc937-reorder-data-in-log-files.java)| | override compare method | Easy
|8|[380. Insert Delete GetRandom O(1)](https://leetcode.com/problems/insert-delete-getrandom-o1/)|[Java](problems/lc380-insert-delete-getrandom-o1.java)| | hashmap (store (value, index in the array)) + arraylist | Medium
|9|[430. Flatten a Multilevel Doubly Linked List](https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/)|[Java](problems/lc430-flatten-a-multilevel-doubly-linked-list.java)| | recursion OR stack | Medium
|10|[206. Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/)|[Java](problems/lc206-reverse-linked-list.java)| | resursion or iterative | Easy
|11|[234. Palindrome Linked List](https://leetcode.com/problems/palindrome-linked-list/)|[Java](problems/lc234-palindrome-linked-list.java)| | resursion | Easy
|12|[716. Max Stack](https://leetcode.com/problems/max-stack/)|[Java](problems/lc716-max-stack.java)| | treemap + double linked list | Easy
|13|[238. Product of Array Except Self](https://leetcode.com/problems/product-of-array-except-self/)|[Java](problems/lc238-product-of-array-except-self.java)| | create left product array and right product array | Medium
|14|[219. Contains Duplicate II](https://leetcode.com/problems/contains-duplicate-ii/)|[Java](problems/lc219-contains-duplicate-ii.java)| | set + sliding window, | Easy
|15|[989. Add to Array-Form of Integer](https://leetcode.com/problems/add-to-array-form-of-integer/)|[Java](problems/lc989-add-to-array-form-of-integer.java)| | start from end of input 1 array | Easy
|16|[950. Reveal Cards In Increasing Order](https://leetcode.com/problems/reveal-cards-in-increasing-order/)|[Java](problems/lc950-reveal-cards-in-increasing-order.java)| | sorting, stimulation | Medium
|17|[697. Degree of an Array](https://leetcode.com/problems/degree-of-an-array/)|[Java](problems/lc697-degree-of-an-array.java)|  | hashmap | Easy
|18|[280. Wiggle Sort](https://leetcode.com/problems/wiggle-sort/)|[Java](problems/lc280-wiggle-sort.java)|  | hashmap | Medium
|19|[767. Reorganize String](https://leetcode.com/problems/reorganize-string/)|[Java](problems/lc767-reorganize-string.java)|  | hashmap | Medium
|20|[969. Pancake Sorting](https://leetcode.com/problems/pancake-sorting/)|[Java](problems/lc969-pancake-sorting.java)|  | array, find max & reverse twice | Medium
|21|[84. Largest Rectangle in Histogram](https://leetcode.com/problems/largest-rectangle-in-histogram/)|[Java](problems/lc84-largest-rectangle-in-histogram.java)|  | divfide & conquer, stack | Hard
|22|[859. Buddy Strings](https://leetcode.com/problems/buddy-strings/)|[Java](problems/lc859-buddy-strings.java)|  |  | Easy
|23|[83. Remove Duplicates from Sorted List](https://leetcode.com/problems/remove-duplicates-from-sorted-list/)|[Java](problems/lc83-remove-duplicates-from-sorted-list.java)|  | linked list | Easy
|24|[1441. Build an Array With Stack Operations](https://leetcode.com/problems/build-an-array-with-stack-operations/)|[Java](problems/lc1441-build-an-array-with-stack-operations.java)|  | stack,  | Easy
|25|[667. Beautiful Arrangement II](https://leetcode.com/problems/beautiful-arrangement-ii/)|[Java](problems/lc667-beautiful-arrangement-ii.java)|  | array  | Medium
|26|[723. Candy Crush](https://leetcode.com/problems/candy-crush/)|[Java](problems/lc723-candy-crush.java)|  | array  | Medium
|27|[496. Next Greater Element I](https://leetcode.com/problems/next-greater-element-i/)|[Java](problems/lc496-next-greater-element-i.java)|  | stack  | Easy
|28|[907. Sum of Subarray Minimums](https://leetcode.com/problems/sum-of-subarray-minimums/)|[Java](problems/lc907-sum-of-subarray-minimums.java)|  | stack, with detailed monotonous stack note  | Medium
|29|[901. Online Stock Span](https://leetcode.com/problems/online-stock-span/)|[Java](problems/lc901-online-stock-span.java)|  | stack, with detailed monotonous stack note  | Medium
|30|[503. Next Greater Element II](https://leetcode.com/problems/next-greater-element-ii/)|[Java](problems/lc503-next-greater-element-ii.java)|  | stack | Medium
|31|[1329. Sort the Matrix Diagonally](https://leetcode.com/problems/sort-the-matrix-diagonally/)|[Java](problems/lc1329-sort-the-matrix-diagonally.java)|  | priority queue or bubble sort | Medium
|32|[119. Pascal's Triangle II](https://leetcode.com/problems/pascals-triangle-ii/)|[Java](problems/lc119-pascals-triangle-ii.java)|  |  | Easy
|33|[665. Non-decreasing Array](https://leetcode.com/problems/non-decreasing-array/)|[Java](problems/lc665-non-decreasing-array.java)|  |  | Easy
|34|[1096. Brace Expansion II](https://leetcode.com/problems/brace-expansion-ii/)|[Java](problems/lc1096-brace-expansion-ii.java)|  | REDO, case by case, python-cartisen product | Hard
|35|[158. Read N Characters Given Read4 II - Call multiple times](https://leetcode.com/problems/read-n-characters-given-read4-ii-call-multiple-times/)|[Java](problems/lc158-read-n-characters-given-read4-ii-call-multiple-times.java)|  |  | Hard
|36|[1313. Decompress Run-Length Encoded List](https://leetcode.com/problems/decompress-run-length-encoded-list/)|[Java](problems/lc1313-decompress-run-length-encoded-list.java)|  | java- ans.stream().mapToInt(i -> i).toArray()  | Hard
|37|[80. Remove Duplicates from Sorted Array II](https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/)|[Java](problems/lc80-remove-duplicates-from-sorted-array-ii.java)|  | 2 pointers  | Medium
|38|[974. Subarray Sums Divisible by K ](https://leetcode.com/problems/subarray-sums-divisible-by-k/)|[Java](problems/lc974-subarray-sums-divisible-by-k.java)|  | prefix sum, mod array, ~lc523  | Medium





 
(刷到 835)

 




## Dynamic programming / Greedy
[dp-problem summary](https://note.youdao.com/web/#/file/WEB1ab106e98e0921f5cb8ffd57eb18a6f9/note/WEB022fd5285d2ecd51ad98b002e0102c23/)


Notes:
1. when want to optimize space --> from m * n reduce to O(m), declare 2 rows only, prev & current, 
   then use i % 2 to determine which row it is current

		boolean[][] dp = new boolean[2][pattern.length() + 1];
	    dp[text.length() % 2][pattern.length()] = true;
	    dp[i % 2][j] = dp[i % 2][j + 1] || first_match && dp[(i + 1) % 2][j];

2. good practise/optimize question


3. whenever we do sum, subtract, multiply or divide of integers, check overflow!

4. How to find loop:
    1) if dp[i][j] depends on dp[i-1][j-1] --> 需要知道比他小的dp
        for(i = 0, ....)
            for (j = 0, ....)

    2) 

5. use bit mask to represent state --> more see Math section


### Leetcode
|# |    Title   |Solution|Complexity|Note|Difficulty|
|---| ----------| ------ |----------|----|----------|
|1|[121. Best Time to Buy and Sell Stock (1 tranx)](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/submissions/)|[Java](problems/lc121_best_time_buy_sell_stock.java)| | key: find a contiguous subarray giving maximum profit == 找到后面比前面某天最多的difference | easy
|2|[122. Best Time to Buy and Sell Stock II(multiple tranx)](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/)|[Java](problems/lc122_best_time_buy_sell_stock_2.java)| |profit += Math.max(0, prices[i] - prices[i - 1]) | easy
|3|[123. Best Time to Buy and Sell Stock III (2 tranx)](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/)|[Java](problems/lc123_best_time_buy_sell_stock_3.java)|O(n), O(1) |!!! 1)use temp variables  2)use dp with one array storing left most value |Hard
|4|[188. Best Time to Buy and Sell Stock IV (k tranx)](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/)|[Java](problems/lc188_best_time_buy_sell_stock_4.java)|O(kn), O(1) |!!！ need to rework this problem |Hard
|5|[221. Maximal Square](https://leetcode.com/problems/maximal-square/)|[Java](problems/lc123_best_time_buy_sell_stock_3.java)|O(n), O(1) |!!! 1)use temp variables  2)use dp with one array storing left most value |Hard
|6|[5.Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/)|[Java](problems/lc5_longest_palindrome.java)|O(n), O(1) |!!! start from middle, 2 pointers / Manchester's algo --> linear time |Medium
|7|[873.Length of Longest Fibonacci Subsequence](https://leetcode.com/problems/length-of-longest-fibonacci-subsequence/)|[Java](problems/lc873_longest_fibonacci.java)|O(n), O(1) |!!! |Medium
|8|[10. Regular Expression Matching](https://leetcode.com/problems/regular-expression-matching/)|[Java](problems/lc10_regular_expression_match.java)|O(p.length() * s.length()) both time & space |!!! consider all cases, practise both recursive and dp |Hard
|9|[22. Generate Parentheses ](https://leetcode.com/problems/generate-parentheses/)|[Java](problems/lc22_generate_parentheses.java)| | !! generalization!! redo | Medium
|10|[32. Longest Valid Parentheses ](https://leetcode.com/problems/longest-valid-parentheses/)|[Java](problems/lc32-longest-valid-parentheses.java)| | or use stack | Hard
|11|[42. Trapping Rain Water](https://leetcode.com/problems/trapping-rain-water/)|[Java](problems/lc42-trapping-rain-water.java)| time:O（n), space:O（1）|2 pointers, dp (maxleft & maxright), stack | Hard
|12|[44. Wildcard Matching](https://leetcode.com/problems/wildcard-matching/)|[Java](problems/lc44-wildcard-matching.java)| time:O（TP) --> str.len=T，pattern.len=P, space:O（1）|dp, iterative, need to check whether one string go out of bound | Hard
|13|[53. Maximum Subarray](https://leetcode.com/problems/maximum-subarray/)|[Java](problems/lc53-maximum-subarray.java)||dp & trick! (折半) | Easy
|14|[62. Unique Paths](https://leetcode.com/problems/unique-paths/)|[Java](problems/lc62-unique-paths.java)||dp & back track | Medium
|15|[63. Unique Paths II ](https://leetcode.com/problems/unique-paths-ii/)|[Java](problems/lc63-unique-paths-ii.java)||similar to #62 | Medium
|16|[64. Minimum Path Sum ](https://leetcode.com/problems/minimum-path-sum/)|[Java](problems/lc64-minimum-path-sum.java)||similar to #62 | Medium
|17|[70. Climbing Stairs](https://leetcode.com/problems/climbing-stairs/)|[Java](problems/lc70-climbing-stairs.java)||  | Easy
|18|[239. Sliding Window Maximum](https://leetcode.com/problems/sliding-window-maximum/)|[Java](problems/lc239-sliding-window-maximum.java)| |sliding window, DP| Hard
|19|[264. Ugly Number II](https://leetcode.com/problems/ugly-number-ii/)|[Java](problems/lc264-ugly-number-ii.java)| | dp or heap, find pattern | Medium
|20|[1027. Longest Arithmetic Sequence](https://leetcode.com/problems/longest-arithmetic-sequence/)|[Java](problems/lc1027-longest-arithmetic-sequence.java)| | dp or hashmap | Medium
|21|[1130. Minimum Cost Tree From Leaf Values](https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/)|[Java](problems/lc1130-minimum-cost-tree-from-leaf-values.java)| | dp or greedy, or dfs, stack | Medium
|22|[Google-interview: num of ways to go back to origin](https://leetcode.com/discuss/interview-question/416381/Google-Phone-Interview-Question-DP)|[Java](problems/interview-num-ways-back-to-origin.java)| | dp or greedy, or dfs | Medium
|23|[131. Palindrome Partitioning](https://leetcode.com/problems/palindrome-partitioning/)|[Java](problems/lc131-palindrome-partitioning.java)| | back-tracking, or dp | Medium
|24|[Geeks - Perfect Sum Problem (Print all subsets with given sum)](https://www.geeksforgeeks.org/perfect-sum-problem-print-subsets-given-sum/)|[Java](problems/geeks-perfect-sum-problem-print-subsets-given-sum.java)| | lc40, back-tracking, or dp | Medium
|24|[518. Coin Change 2 --> number of ways ](https://leetcode.com/problems/coin-change-2/)|[Java](problems/lc518-coin-change-2.java)| | dp | Medium
|25|[322. Coin Change - min num of coins](https://leetcode.com/problems/coin-change/)|[Java](problems/lc322-coin-change.java)| | dp | Medium
|26|[338. Counting Bits](https://leetcode.com/problems/counting-bits/)|[Java](problems/lc338-counting-bits.java)| | dp, even: countBits(num >> 1), odd: countBits(num - 1) + 1 | Medium
|27|[312. Burst Balloons](https://leetcode.com/problems/burst-balloons/)|[Java](problems/lc312-burst-balloons.java)| | matrix chain multiplication | Hard
|28|[300. Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/)|[Java](problems/lc300-longest-increasing-subsequence.java)| | matrix chain multiplication | Medium
|29|[516. Longest Palindromic Subsequence](https://leetcode.com/problems/longest-palindromic-subsequence/)|[Java](problems/lc516-longest-palindromic-subsequence.java)| | start from end of the string! | Medium
|30|[740. Delete and Earn](https://leetcode.com/problems/delete-and-earn/)|[Java](problems/lc740-delete-and-earn.java)| | bucket sort, DP, Greedy | Medium
|31|[198. House Robber](https://leetcode.com/problems/house-robber/)|[Java](problems/lc198-house-robber.java)| | DP| Easy
|32|[279. Perfect Squares](https://leetcode.com/problems/perfect-squares/)|[Java](problems/lc279-perfect-squares.java)| | DP| Medium
|33|[689. Maximum Sum of 3 Non-Overlapping Subarrays](https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/)|[Java](problems/lc689-maximum-sum-of-3-non-overlapping-subarrays.java)| | DP, similar to buy&sell stock 3, | Hard
|34|[140. Word Break II](https://leetcode.com/problems/word-break-ii/)|[Java](problems/lc140-word-break-ii.java)| | dp(very neat!) or dfs+memorization| Hard
|35|[472. Concatenated Words](https://leetcode.com/problems/concatenated-words/)|[Java](problems/lc472-concatenated-words.java)| | dp(very neat!) or dfs + Trie| Hard
|36|[1167. Minimum Cost to Connect Sticks](https://leetcode.com/problems/minimum-cost-to-connect-sticks/)|[Java](problems/lc1167-minimum-cost-to-connect-sticks.java)| | greedy | Medium
|37|[801. Minimum Swaps To Make Sequences Increasing](https://leetcode.com/problems/minimum-swaps-to-make-sequences-increasing/)|[Java](problems/lc801-minimum-swaps-to-make-sequences-increasing.java)| | DP | Medium
|38|[552. Student Attendance Record II](https://leetcode.com/problems/student-attendance-record-ii)|[Java](problems/lc552-student-attendance-record-ii.java)| | redo!! DP, hard to think transition func | Hard
|39|[85. Maximal Rectangle](https://leetcode.com/problems/maximal-rectangle/)|[Java](problems/lc85-maximal-rectangle.java)||  | Hard
|40|[72. Edit Distance](https://leetcode.com/problems/edit-distance/)|[Java](problems/lc72-edit-distance.java)||DP  | Hard
|41|[139. Word Break](https://leetcode.com/problems/word-break/)|[Java](problems/lc139-word-break.java)||DP  | Medium
|42|[152. Maximum Product Subarray](https://leetcode.com/problems/maximum-product-subarray/)|[Java](problems/lc152-maximum-product-subarray.java)||DP  | Medium
|43|[120.Triangle](https://leetcode.com/problems/triangle/)|[Java](problems/lc120-triangle.java)||DP  | Medium
|44|[309. Best Time to Buy and Sell Stock with Cooldown](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/)|[Java](problems/lc309-best-time-to-buy-and-sell-stock-with-cooldown.java)||DP  | Medium
|45|[714. Best Time to Buy and Sell Stock with Transaction Fee](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/)|[Java](problems/lc714-best-time-to-buy-and-sell-stock-with-transaction-fee.java)||DP  | Medium
|46|[97. Interleaving String](https://leetcode.com/problems/interleaving-string/)|[Java](problems/lc97-interleaving-string.java)||DP, DFS, BFS  | Hard
|47|[413. Arithmetic Slices](https://leetcode.com/problems/arithmetic-slices/)|[Java](problems/lc413-arithmetic-slices.java)||DP | Medium
|48|[91. Decode Ways](https://leetcode.com/problems/decode-ways/)|[Java](problems/lc91-decode-ways.java)||DP & recursion | Medium
|49|[115. Distinct Subsequences](https://leetcode.com/problems/distinct-subsequences/)|[Java](problems/lc115-distinct-subsequences.java)|| DP | Hard
|50|[647. Palindromic Substrings](https://leetcode.com/problems/palindromic-substrings/)|[Java](problems/lc647-palindromic-substrings.java)|| DP | Medium
|51|[1140. Stone Game II](https://leetcode.com/problems/stone-game-ii/)|[Java](problems/lc1140-stone-game-ii.java)|| DP | Medium
|52|[741. Cherry Pickup](https://leetcode.com/problems/cherry-pickup/)|[Java](problems/lc741-cherry-pickup.java)|| DP | Hard
|53|[746. Min Cost Climbing Stairs](https://leetcode.com/problems/min-cost-climbing-stairs/)|[Java](problems/lc746-min-cost-climbing-stairs.java)|| DP | Easy
|54|[343. Integer Break](https://leetcode.com/problems/integer-break/)|[Java](problems/lc343-integer-break.java)|| DP | Medium
|55|[256. Paint House](https://leetcode.com/problems/paint-house/)|[Java](problems/lc256-paint-house.java)|| DP | Easy
|56|[718. Maximum Length of Repeated Subarray](https://leetcode.com/problems/maximum-length-of-repeated-subarray/)|[Java](problems/lc718-maximum-length-of-repeated-subarray.java)|| DP | Medium
|57|[174. Dungeon Game](https://leetcode.com/problems/dungeon-game/)|[Java](problems/lc174-dungeon-game.java)|| DP | Hard
|58|[730. Count Different Palindromic Subsequences ](https://leetcode.com/problems/count-different-palindromic-subsequences/)|[Java](problems/lc730-count-different-palindromic-subsequences.java)|| Redo, DP | Hard
|59|[546. Remove Boxes](https://leetcode.com/problems/remove-boxes/)|[Java](problems/lc546-remove-boxes.java)|| DP | Hard
|60|[304. Range Sum Query 2D - Immutable](https://leetcode.com/problems/range-sum-query-2d-immutable/)|[Java](problems/lc304-range-sum-query-2d-immutable.java)|| prefix sum in row or ranctangles | Medium
|61|[975. Odd Even Jump](https://leetcode.com/problems/odd-even-jump/)|[Java](problems/lc975-odd-even-jump.java)|| prefix sum in row or ranctangles | Hard
|62|[321. Create Maximum Number](https://leetcode.com/problems/create-maximum-number/)|[Java](problems/lc321-create-maximum-number.java)|| greedy,get largest k in each array, then merge | Hard
|63|[983. Minimum Cost For Tickets](https://leetcode.com/problems/minimum-cost-for-tickets/)|[Java](problems/lc983-minimum-cost-for-tickets.java)|| DP | Medium
|64|[403. Frog Jump](https://leetcode.com/problems/frog-jump/)|[Java](problems/lc403-frog-jump.java)|| DP | Hard
|65|[887. Super Egg Drop](https://leetcode.com/problems/super-egg-drop/)|[Java](problems/lc887-super-egg-drop.java)|| DP | Hard
|66|[368. Largest Divisible Subset](https://leetcode.com/problems/largest-divisible-subset/)|[Java](problems/lc368-largest-divisible-subset.java)|| DP or DFS | Medium
|67|[956. Tallest Billboard](https://leetcode.com/problems/tallest-billboard/)|[Java](problems/lc956-tallest-billboard.java)|| DP or DFS | Hard
|68|[712. Minimum ASCII Delete Sum for Two Strings](https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/)|[Java](problems/lc712-minimum-ascii-delete-sum-for-two-strings.java)|| DP | Medium
|69|[1143. Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/)|[Java](problems/lc1143-longest-common-subsequence.java)|| DP | Medium
|70|[494. Target Sum](https://leetcode.com/problems/target-sum/)|[Java](problems/lc494-target-sum.java)|| DP | Medium
|71|[377. Combination Sum IV](https://leetcode.com/problems/combination-sum-iv/)|[Java](problems/lc377-combination-sum-iv.java)|| DP | Medium
|72|[646. Maximum Length of Pair Chain](https://leetcode.com/problems/maximum-length-of-pair-chain/)|[Java](problems/lc646-maximum-length-of-pair-chain.java)|| Greedy | Medium
|73|[213. House Robber II](https://leetcode.com/problems/house-robber-ii/)|[Java](problems/lc213-house-robber-ii.java)|| Greedy | Medium
|74|[903. Valid Permutations for DI Sequence](https://leetcode.com/problems/valid-permutations-for-di-sequence/)|[Java](problems/lc903-valid-permutations-for-di-sequence.java)|| DP| Hard
|75|[920. Number of Music Playlists](https://leetcode.com/problems/number-of-music-playlists/)|[Java](problems/lc920-number-of-music-playlists.java)|| DP| Hard
|76|[416. Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/)|[Java](problems/lc416-partition-equal-subset-sum.java)|| DP, knapsack| Medium
|77|[964. Least Operators to Express Number](https://leetcode.com/problems/least-operators-to-express-number/)|[Java](problems/lc964-least-operators-to-express-number.java)|| DFS + memo| Hard
|78|[1000. Minimum Cost to Merge Stones](https://leetcode.com/problems/minimum-cost-to-merge-stones/)|[Java](problems/lc1000-minimum-cost-to-merge-stones.java)|| DFS + memo| Hard
|79|[813. Largest Sum of Averages](https://leetcode.com/problems/largest-sum-of-averages/)|[Java](problems/lc813-largest-sum-of-averages.java)|| DP | Medium
|80|[664. Strange Printer](https://leetcode.com/problems/strange-printer/)|[Java](problems/lc664-strange-printer.java)|| DP | Hard
|81|[474. Ones and Zeroes](https://leetcode.com/problems/ones-and-zeroes/)|[Java](problems/lc474-ones-and-zeroes.java)|| DP | Medium
|82|[931. Minimum Falling Path Sum](https://leetcode.com/problems/minimum-falling-path-sum/)|[Java](problems/lc931-minimum-falling-path-sum.java)|| DP | Medium
|83|[276. Paint Fence](https://leetcode.com/problems/paint-fence/)|[Java](problems/lc276-paint-fence.java)|| DP | Easy
|84|[376. Wiggle Subsequence](https://leetcode.com/problems/wiggle-subsequence/)|[Java](problems/lc376-wiggle-subsequence.java)|| Greedy | Medium
|85|[688. Knight Probability in Chessboard](https://leetcode.com/problems/knight-probability-in-chessboard/)|[Java](problems/lc688-knight-probability-in-chessboard.java)|| DP | Medium
|86|[673. Number of Longest Increasing Subsequence](https://leetcode.com/problems/number-of-longest-increasing-subsequence/)|[Java](problems/lc673-number-of-longest-increasing-subsequence.java)|| Greedy | Medium
|87|[1105. Filling Bookcase Shelves](https://leetcode.com/problems/filling-bookcase-shelves/)|[Java](problems/lc1105-filling-bookcase-shelves.java)|| DP | Medium
|88|[650. 2 Keys Keyboard](https://leetcode.com/problems/2-keys-keyboard/)|[Java](problems/lc650-2-keys-keyboard.java)|| DP or prime factorization | Medium
|89|[361. Bomb Enemy](https://leetcode.com/problems/bomb-enemy/)|[Java](problems/lc361-bomb-enemy.java)|| DP | Medium
|90|[1039. Minimum Score Triangulation of Polygon](https://leetcode.com/problems/minimum-score-triangulation-of-polygon/)|[Java](problems/lc1039-minimum-score-triangulation-of-polygon.java)|| DP | Medium
|91|[600. Non-negative Integers without Consecutive Ones](https://leetcode.com/problems/non-negative-integers-without-consecutive-ones/)|[Java](problems/lc600-non-negative-integers-without-consecutive-ones.java)|| DP | Hard
|92|[968. Binary Tree Cameras](https://leetcode.com/problems/binary-tree-cameras/)|[Java](problems/lc968-binary-tree-cameras.java)|| Greedy | Hard
|93|[517. Super Washing Machines](https://leetcode.com/problems/binary-tree-cameras/)|[Java](problems/lc517-binary-tree-cameras.java)|| Greedy | Hard
|94|[788. Rotated Digits](https://leetcode.com/problems/rotated-digits/)|[Java](problems/lc788-rotated-digits.java)|| DP, brute force + pruning | Easy
|95|[818. Race Car](https://leetcode.com/problems/race-car/)|[Java](problems/lc818-race-car.java)|| DP, brute force + pruning | Hard
|96|[ 838. Push Dominoes](https://leetcode.com/problems/push-dominoes/)|[Java](problems/lc838-push-dominoes.java)|| simulation, use 2 array to store the force | Medium
|97|[1024. Video Stitching](https://leetcode.com/problems/video-stitching/)|[Java](problems/lc1024-video-stitching.java)|| greedy, sort | Medium
|98|[446. Arithmetic Slices II - Subsequence](https://leetcode.com/problems/arithmetic-slices-ii-subsequence/)|[Java](problems/lc446-arithmetic-slices-ii-subsequence.java)|| DP, hashmap stores the position, dp[i][j] = dp[j][k]+1 for sequence(k,j,i) | Hard
|99|[467. Unique Substrings in Wraparound String](https://leetcode.com/problems/unique-substrings-in-wraparound-string/)|[Java](problems/lc467-unique-substrings-in-wraparound-string.java)|| DP, need to consider overlap cases (ex. abcbc, bc is repeated and can't have {'bc','bc','b','b'} in the answer set) | Medium
|100|[1223. Dice Roll Simulation](https://leetcode.com/problems/dice-roll-simulation/)|[Java](problems/lc1223-dice-roll-simulation.java)|| DP, compress states & find pattern | Medium
|101|[1092. Shortest Common Supersequence](https://leetcode.com/problems/shortest-common-supersequence/)|[Java](problems/lc1092-shortest-common-supersequence.java)|| DP | Hard
|102|[982. Triples with Bitwise AND Equal To Zero]( https://leetcode.com/problems/triples-with-bitwise-and-equal-to-zero/)|[Java](problems/lc982-triples-with-bitwise-and-equal-to-zero.java)|| DP or Hashmap | Hard
|103|[960. Delete Columns to Make Sorted III](https://leetcode.com/problems/delete-columns-to-make-sorted-iii/)|[Java](problems/lc960-delete-columns-to-make-sorted-iii.java)|| DP | Hard
|104|[871. Minimum Number of Refueling Stops](https://leetcode.com/problems/minimum-number-of-refueling-stops/)|[Java](problems/lc871-minimum-number-of-refueling-stops.java)|| DP | Hard
|105|[691. Stickers to Spell Word](https://leetcode.com/problems/stickers-to-spell-word/)|[Java](problems/lc691-stickers-to-spell-word.java)|| DP | Hard
|106|[1235. Maximum Profit in Job Scheduling](https://leetcode.com/problems/maximum-profit-in-job-scheduling/)|[Java](problems/lc1235-maximum-profit-in-job-scheduling.java)|| DP | Hard
|107|[1187. Make Array Strictly Increasing](https://leetcode.com/problems/make-array-strictly-increasing/)|[Java](problems/lc1187-make-array-strictly-increasing.java)|| DP | Hard
|108|[1155. Number of Dice Rolls With Target Sum](https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/)|[Java](problems/lc1155-number-of-dice-rolls-with-target-sum.java)|| DP | Medium
|109|[808. Soup Servings](https://leetcode.com/problems/soup-servings/)|[Java](problems/lc808-soup-servings.java)|| DP | Medium
|110|[790. Domino and Tromino Tiling](https://leetcode.com/problems/domino-and-tromino-tiling/)|[Java](problems/lc790-domino-and-tromino-tiling.java)|| DP/ pattern finding | Medium
|111|[978. Longest Turbulent Subarray](https://leetcode.com/problems/longest-turbulent-subarray/)|[Java](problems/lc978-longest-turbulent-subarray.java)|| DP | Medium
|112|[764. Largest Plus Sign](https://leetcode.com/problems/largest-plus-sign/)|[Java](problems/lc764-largest-plus-sign.java)|| DP | Medium
|113|[940. Distinct Subsequences II](https://leetcode.com/problems/distinct-subsequences-ii/)|[Java](problems/lc940-distinct-subsequences-ii.java)|| DP,seq that resulted from putting "b" the last time (ie."b", "ab") will get double counted. | Hard
|114|[1125. Smallest Sufficient Team ](https://leetcode.com/problems/smallest-sufficient-team/)|[Java](problems/lc1125-smallest-sufficient-team.java)|| DP, set cover problem | Hard
|115|[621. Task Scheduler](https://leetcode.com/problems/task-scheduler/)|[Java](problems/lc621-task-scheduler.java)|| Greedy & priority queue | Medium
|116|[1139. Largest 1-Bordered Square](https://leetcode.com/problems/largest-1-bordered-square/)|[Java](problems/lc1139-largest-1-bordered-square.java)|| prefix sum calculate, pruning / early fail| Medium
|117|[1320. Minimum Distance to Type a Word Using Two Fingers](https://leetcode.com/problems/minimum-distance-to-type-a-word-using-two-fingers/)|[Java](problems/lc1320-minimum-distance-to-type-a-word-using-two-fingers.java)|| recursion OR DP| Hard
|118|[265. Paint House II](https://leetcode.com/problems/paint-house-ii/)|[Java](problems/lc265-paint-house-ii.java)|| DP, set cover problem | Hard
|119|[471. Encode String with Shortest Length](https://leetcode.com/problems/encode-string-with-shortest-length/)|[Java](problems/lc471-encode-string-with-shortest-length.java)|| DP, set cover problem | Hard
|120|[576. Out of Boundary Paths](https://leetcode.com/problems/out-of-boundary-paths/)|[Java](problems/lc576-out-of-boundary-paths.java)|| recursion OR dp | Medium
|121|[879. Profitable Schemes](https://leetcode.com/problems/profitable-schemes/)|[Java](problems/lc879-profitable-schemes.java)|| DP, dfs | Hard
|122|[514. Freedom Trail](https://leetcode.com/problems/freedom-trail/)|[Java](problems/lc514-freedom-trail.java)|| DP, dfs | Hard
|123|[1278. Palindrome Partitioning III](https://leetcode.com/problems/palindrome-partitioning-iii/)|[Java](problems/lc1278-palindrome-partitioning-iii.java)|| DP, good problem | Hard
|124|[639. Decode Ways II](https://leetcode.com/problems/decode-ways-ii/)|[Java](problems/lc639-decode-ways-ii.java)|| DP, good problem | Hard
|125|[1220. Count Vowels Permutation](https://leetcode.com/problems/count-vowels-permutation/)|[Java](problems/lc1220-count-vowels-permutation.java)|| DP or tree problem~ | Hard
|126|[1191. K-Concatenation Maximum Sum](https://leetcode.com/problems/k-concatenation-maximum-sum/)|[Java](problems/lc1191-k-concatenation-maximum-sum.java)|| prefix & suffix array | Medium
|127|[1186. Maximum Subarray Sum with One Deletion](https://leetcode.com/problems/k-concatenation-maximum-sum/)|[Java](problems/lc1186-k-concatenation-maximum-sum.java)|| prefix & suffix array | Medium
|128|[1326. Minimum Number of Taps to Open to Water a Garden](https://leetcode.com/problems/minimum-number-of-taps-to-open-to-water-a-garden/)|[Java](problems/lc1326-minimum-number-of-taps-to-open-to-water-a-garden.java)|| Greedy, always check the furthest | Hard
|129|[1246. Palindrome Removal](https://leetcode.com/problems/palindrome-removal/)|[Java](problems/lc1246-palindrome-removal.java)|| DP, 3 cases | Hard
|130|[1312. Minimum Insertion Steps to Make a String Palindrome](https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/)|[Java](problems/lc1312-minimum-insertion-steps-to-make-a-string-palindrome.java)|| DP | Hard
|131|[1335. Minimum Difficulty of a Job Schedule](https://leetcode.com/problems/minimum-difficulty-of-a-job-schedule/)|[Java](problems/lc1335-minimum-difficulty-of-a-job-schedule.java)|| DP | Hard
|132|[568. Maximum Vacation Days](https://leetcode.com/problems/maximum-vacation-days/)|[Java](problems/lc568-maximum-vacation-days.java)|| DP | Hard
|133|[1349. Maximum Students Taking Exam](https://leetcode.com/problems/maximum-students-taking-exam/)|[Java](problems/lc1349-maximum-students-taking-exam.java)|| DP + bitmask | Hard
|134|[967. Numbers With Same Consecutive Differences](https://leetcode.com/problems/numbers-with-same-consecutive-differences/)|[Java](problems/lc967-numbers-with-same-consecutive-differences.java)|| DP + bitmask | Medium
|135|[1262. Greatest Sum Divisible by Three](https://leetcode.com/problems/greatest-sum-divisible-by-three/)|[Java](problems/lc1262-greatest-sum-divisible-by-three.java)|| DP | Medium
|136|[1340. Jump Game V](https://leetcode.com/problems/jump-game-v/)|[Java](problems/lc1340-jump-game-v.java)|| DFS + memo | Hard
|137|[1269. Number of Ways to Stay in the Same Place After Some Steps](https://leetcode.com/problems/number-of-ways-to-stay-in-the-same-place-after-some-steps/)|[Java](problems/lc1269-number-of-ways-to-stay-in-the-same-place-after-some-steps.java)|| DP | Hard
|138|[651. 4 Keys Keyboard](https://leetcode.com/problems/4-keys-keyboard/)|[Java](problems/lc651-4-keys-keyboard.java)|| DP + bitmask | Medium
|139|[1388. Pizza With 3n Slices](https://leetcode.com/problems/pizza-with-3n-slices/)|[Java](problems/lc1388-pizza-with-3n-slices.java)|| DP + circular array (dfs(arr[0]~arr[last-1]) or dfs(arr[1] ~ arr[last])) | Hard
|140|[1289. Minimum Falling Path Sum II ](https://leetcode.com/problems/minimum-falling-path-sum-ii/)|[Java](problems/lc1289-minimum-falling-path-sum-ii.java)|| DP, similar to 265 paint house 2, use min & 2nd min | Hard
|141|[1406. Stone Game III](https://leetcode.com/problems/stone-game-iii/)|[Java](problems/lc1406-stone-game-iii.java)|| DP | Hard
|142|[1444. Number of Ways of Cutting a Pizza](https://leetcode.com/problems/number-of-ways-of-cutting-a-pizza/)|[Java](problems/lc1444-number-of-ways-of-cutting-a-pizza.java)|| DP | Hard
|143|[1301. Number of Paths with Max Score](https://leetcode.com/problems/number-of-paths-with-max-score/)|[Java](problems/lc1301-number-of-paths-with-max-score.java)|| DP | Hard
|144|[1216. Valid Palindrome III](https://leetcode.com/problems/valid-palindrome-iii/)|[Java](problems/lc1216-valid-palindrome-iii.java)|| DP | Hard
|145|[1411.Number of Ways to Paint N × 3 Grid](https://leetcode.com/problems/number-of-ways-to-paint-n-3-grid/)|[Java](problems/lc1411-number-of-ways-to-paint-n-3-grid.java)|| DP+pattern finding OR DFS | Hard
|146|[1449. Form Largest Integer With Digits That Add up to Target](https://leetcode.com/problems/form-largest-integer-with-digits-that-add-up-to-target/)|[Java](problems/lc1449-form-largest-integer-with-digits-that-add-up-to-target.java)|| DP-knapsack | Hard
|147|[1416. Restore The Array](https://leetcode.com/problems/restore-the-array/)|[Java](problems/lc1416-restore-the-array.java)|| DP | Hard
|148|[1259. Handshakes That Don't Cross](https://leetcode.com/problems/handshakes-that-dont-cross/)|[Java](problems/lc1259-handshakes-that-dont-cross.java)|| DP | Hard
|149|[1067. Digit Count in Range 233.](https://leetcode.com/problems/digit-count-in-range/)|[Java](problems/lc1067-digit-count-in-range.java)|| DP, math, find pattern | Hard
|150|[656. Coin Path](https://leetcode.com/problems/coin-path/)|[Java](problems/lc656-coin-path.java)|| DP | Hard
|151|[1230. Toss Strange Coins](https://leetcode.com/problems/toss-strange-coins/)|[Java](problems/lc1230-toss-strange-coins.java)|| DP | Medium
|152|[118. Pascal's Triangle](https://leetcode.com/problems/pascals-triangle/)|[Java](problems/lc118-pascals-triangle.java)|| DP | Easy



 ## Backtrack

我们只要在递归之前做出选择，在递归之后撤销刚才的选择

框架：

    result = []
    def backtrack(路路径, 选择列列表):
        if 满⾜足结束条件: 
            result.add(路路径)
        return
    
        for 选择 in 选择列列表: 
            做选择
            backtrack(路路径, 选择列列表) 
            撤销选择

如果有list all permutations的情况，比如 17. Letter Combinations of a Phone Number， 则不撤销选择，一直往前走

```java
class Solution {
  Map<String, String> phone = new HashMap<String, String>() {{
    put("2", "abc");
    put("3", "def");
    put("4", "ghi");
    put("5", "jkl");
    put("6", "mno");
    put("7", "pqrs");
    put("8", "tuv");
    put("9", "wxyz");
  }};

  List<String> output = new ArrayList<String>();

  // main method
  public List<String> letterCombinations(String digits) {
    if (digits.length() != 0)
        backtrack("", digits);

    return output;
  }

  public void backtrack(String combination, String next_digits) {
    // if there is no more digits to check
    if (next_digits.length() == 0) {
      output.add(combination);
    }
    else {
      // iterate over all letters which map the next available digit
      String digit = next_digits.substring(0, 1);
      String letters = phone.get(digit);
      
      for (int i = 0; i < letters.length(); i++) {
        String letter = phone.get(digit).substring(i, i + 1);

        // append the current letter to the combination
        // and proceed to the next digits, substirng(1) means delete the first char in string
        backtrack(combination + letter, next_digits.substring(1));
      }
    }
  }
```

#### 不想得到所有合法的答案，只想要一个答案

其实特别简单，只要稍微修改⼀一下回溯算法的代码即可:

```java
// 函数找到⼀一个答案后就返回 true
bool backtrack(vector<string>& board, int row) {
    // 触发结束条件
    if (row == board.size()) {
            res.push_back(board);
            return true;
        }
        ...
        for (int col = 0; col < n; col++) {
            ...
            board[row][col] = 'Q';
            if (backtrack(board, row + 1))
                return true;
            board[row][col] = '.';
        }
    return false;
}
```


## Tree

!! try RECURSION first
[tree-problem summary](https://note.youdao.com/web/#/file/WEBf38b05fdf3f279c3f09fa0dfa493ec84/note/WEB1963e1b6a9d3c6e0466fff21d7ee3977/)

[tree-key points](https://note.youdao.com/web/#/file/WEBf38b05fdf3f279c3f09fa0dfa493ec84/note/WEB552ce61678e473af8af109e49936082b/)

[Tree problems and tricks](notes/data_struct.md)

- Trie： 
	- please see tree problems note pad.
	- can also be solved by hashmap??
	- problem list:
		lc 421: use search to do XOR
		lc 208: implement trie
		lc 336: palindrome
		lc 1065: 
		lc 720:
		lc 211:
		lc 648
		lc 677:
        lc 642: auto-complete system
        472. Concatenated Words
        1268. Search Suggestions System
        1032. stream of characters: store the search words in reverse per question's request


- Binary search tree:
    257:        Binary Tree Paths
    450:        Delete Node in a BST
    1373        Maximum Sum BST in Binary Tree
    98:         BST, in order traversal !!!!!!!!


- Huffman algorithm --> greedy:
    1199        min time to build blocks

### Leetcode
|#|    Title   |Solution|Complexity|Note|Difficulty|
|---|----------| ------ |----------|----| --------|
|1|[257-Binary Tree Paths (print all path in a binary tree)](https://leetcode.com/problems/binary-tree-paths/)|[Java](problems/lc257_binary_tree_paths.java)|Time: O(n), Space: O(n)| do level order traversal of tree. While doing traversal, process nodes of different level separately. | easy
|2|[450-Delete Node in a BST](https://leetcode.com/problems/delete-node-in-a-bst/)|[Java](problems/450_delete_node_in_bst.java)|Time: O(height)| 3 cases | medium
|3|[329. Longest Increasing Path in a Matrix](https://leetcode.com/problems/longest-increasing-path-in-a-matrix/)|[Java](problems/329-longest-increasing-path-in-a-matrix.java)|Time: O(mn)| dfs + memorization, high frequence | Hard
|4|[104. Maximum Depth of Binary Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/)|[Java](problems/lc104-maximum-depth-of-binary-tree.java)|Time: O(mn)| dfs + memorization, high frequence | Easy
|5|[226. Invert Binary Tree](https://leetcode.com/problems/invert-binary-tree/)|[Java](problems/lc226-invert-binary-tree.java)|| dfs or stack or queue | Easy
|6|[617. Merge Two Binary Trees](https://leetcode.com/problems/merge-two-binary-trees/)|[Java](problems/lc617-merge-two-binary-trees.java)|| recursion or bfs or dfs | Easy
|7|[96. Unique Binary Search Trees](https://leetcode.com/problems/unique-binary-search-trees/)|[Java](problems/lc96-unique-binary-search-trees.java)|| recursion or dp | Medium
|8|[95. Unique Binary Search Trees II](https://leetcode.com/problems/unique-binary-search-trees-ii/)|[Java](problems/lc95-unique-binary-search-trees-ii.java)|| !!!!redo, recursion or dp | Medium
|9|[173. Binary Search Tree Iterator](https://leetcode.com/problems/binary-search-tree-iterator/)|[Java](problems/lc173-binary-search-tree-iterator.java)|| use stack | Medium
|10|[421. Maximum XOR of Two Numbers in an Array](https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/)|[Java](problems/lc421-maximum-xor-of-two-numbers-in-an-array.java)|| Trie | Medium
|11|[208. Implement Trie (Prefix Tree)](https://leetcode.com/problems/implement-trie-prefix-tree/)|[Java](problems/lc208-implement-trie-prefix-tree.java)|| Trie | Medium
|12|[336. Palindrome Pairs](https://leetcode.com/problems/palindrome-pairs/)|[Java](problems/lc336-palindrome-pairs.java)|| Trie, not finished for method 2 and 3 | Hard
|13|[1065. Index Pairs of a String](https://leetcode.com/problems/index-pairs-of-a-string/)|[Java](problems/lc1065-index-pairs-of-a-string.java)|| Trie or startsWith() | Easy
|14|[720. Longest Word in Dictionary](https://leetcode.com/problems/longest-word-in-dictionary/)|[Java](problems/lc720-longest-word-in-dictionary.java)|| Trie or startsWith() | Easy
|15|[692. Top K Frequent Words](https://leetcode.com/problems/top-k-frequent-words/)|[Java](problems/lc692-top-k-frequent-words.java)|| Trie, bucket sort, sort | Medium
|16|[211. Add and Search Word - Data structure design](https://leetcode.com/problems/add-and-search-word-data-structure-design/)|[Java](problems/lc211-add-and-search-word.java)|| Trie | Medium
|17|[648. Replace Words](https://leetcode.com/problems/replace-words/)|[Java](problems/lc648-replace-words.java)|| Trie, use hashmap+trie, hashset | Medium
|18|[677. Map Sum Pairs Medium](https://leetcode.com/problems/map-sum-pairs/)|[Java](problems/lc677-map-sum-pairs.java)|| Trie | Medium
|19|[236. Lowest Common Ancestor of a Binary Tree](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/)|[Java](problems/lc236-lowest-common-ancestor-of-a-binary-tree.java)|| binary tree | Medium
|20|[235. Lowest Common Ancestor of a Binary Search Tree](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/)|[Java](problems/lc235-lowest-common-ancestor-of-a-binary-search-tree.java)|| binary tree | Easy
|21|[102. Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/)|[Java](problems/lc102-binary-tree-level-order-traversal.java)|| binary tree | Medium
|22|[297. Serialize and Deserialize Binary Tree](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/)|[Java](problems/lc297-serialize-and-deserialize-binary-tree.java)|| binary tree | Hard
|23|[113. Path Sum II](https://leetcode.com/problems/path-sum-ii/)|[Java](problems/lc113-path-sum-ii.java)|| binary tree, dfs, start from root & end at leaf | Medium
|24|[437. Path Sum III](https://leetcode.com/problems/path-sum-iii/)|[Java](problems/lc437-path-sum-iii.java)|| ！！！prefix sum hashmap, start & end at any node | Easy
|25|[117. Populating Next Right Pointers in Each Node II](https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/)|[Java](problems/lc117-populating-next-right-pointers-in-each-node-ii.java)|| binary tree, dfs, start from root & end at leaf | Medium
|26|[1145. Binary Tree Coloring Game](https://leetcode.com/problems/binary-tree-coloring-game/)|[Java](problems/lc1145-binary-tree-coloring-game.java)|| find pattern, compare max(left, right, parent) and n / 2 | Medium
|27|[Geeks-Find maximum level sum in Binary Tree](https://www.geeksforgeeks.org/find-level-maximum-sum-binary-tree/)|[Java](problems/1_max_level_sum.java)|Time: O(n), Space: O(n)| do level order traversal of tree. While doing traversal, process nodes of different level separately.|
|28|[Geeks-Maximum width of a binary tree](https://www.geeksforgeeks.org/maximum-width-of-a-binary-tree/)| [Java](problems/2_max_width.java)| | 1) Level Order Traversal with Queue 2) Using Preorder Traversal|
|29|[Geeks-Print path from root to a given node in a binary tree](https://www.geeksforgeeks.org/print-path-root-given-node-binary-tree/)| [Java](problems/3_path_to_given_node.java)|O(n) | use arraylist to store path, remember to delete elem in array if not found! |
|30|[Geeks-Sorted order printing of a given array that represents a BST](https://www.geeksforgeeks.org/sorted-order-printing-of-an-array-that-represents-a-bst/)| [Java](problems/4_sorted_order_print_bst.java)|O(n) | 1) use arraylist to store path, remember to delete elem in array if not found! 2)in array, the node's at position x, then its left child is at 2x+1, its right child is at 2x+2 |
|31|Geeks-Prime in a subtree| [Java](problems/tree/5_prime_in_tree.java)| |twitter OA|
|32|[Geeks-Construct treee with inorder & level order](https://www.geeksforgeeks.org/construct-tree-inorder-level-order-traversals/)| [Java](problems/6_construct_tree_inorder_level_order.java)|O(n^2) | [Leetcode 106.](https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/) |
|33|[Geeks-Construct Tree from given Inorder and Preorder traversals]( https://www.geeksforgeeks.org/construct-tree-from-given-inorder-and-preorder-traversal/)| [Java](problems/7_construct_tree_inorder_preorder.java)| | !!! hard [Leetcode 105.](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/) |
|33|[105.Construct Tree from given Inorder and Preorder traversals](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/)| [Java](problems/lc105-construct_tree_inorder_preorder.java)| | 1) use hashmap 2)stack + hashmap 3) use stack + set:  |Medium
|34|[Geeks - Find the maximum sum leaf to root path in a Binary Tree](https://www.geeksforgeeks.org/find-the-maximum-sum-path-in-a-binary-tree/)| [Java](problems/geeks-find-the-maximum-sum-path-in-a-binary-tree.java)| | recursion |
|35|[124. Binary Tree Maximum Path Sum (don't need to pass root)](https://leetcode.com/problems/binary-tree-maximum-path-sum/)| [Java](problems/lc124-binary-tree-maximum-path-sum.java)| | recursion, keep global variable maxValue and keep updating it |Hard
|36|[Geeks - Maximum sum of nodes in Binary tree such that no two are adjacent](https://www.geeksforgeeks.org/maximum-sum-nodes-binary-tree-no-two-adjacent/)| [Java](problems/geeks-maximum-sum-nodes-binary-tree-no-two-adjacent.java)| | recursion or pair |
|37|[642. Design Search Autocomplete System](https://leetcode.com/problems/design-search-autocomplete-system/)| [Java](problems/lc642-design-search-autocomplete-system.java)| | Trie |Hard
|38|[212. Word Search II](https://leetcode.com/problems/word-search-ii/)| [Java](problems/lc212-word-search-ii.java)| | Trie |Hard
|39|[572. Subtree of Another Tree](https://leetcode.com/problems/subtree-of-another-tree/)| [Java](problems/lc572-subtree-of-another-tree.java)| | String builder, or recursively check each node, need to check isSubtree(s.left, t) OR isSubtree(s.right, t) |Easy
|40|[472. Concatenated Words](https://leetcode.com/problems/concatenated-words/)|[Java](problems/lc472-concatenated-words.java)| | dp(very neat!) or dfs + Trie| Hard
|41|[99. Recover Binary Search Tree](https://leetcode.com/problems/recover-binary-search-tree/)|[Java](problems/lc99-recover-binary-search-tree.java)| | inorder traversal + find misplaced pair| Hard
|42|[545. Boundary of Binary Tree](https://leetcode.com/problems/boundary-of-binary-tree/)|[Java](problems/lc545-boundary-of-binary-tree.java)| | REDO! preorder, (weird problem)| Medium
|43|[1268. Search Suggestions System](https://leetcode.com/problems/search-suggestions-system/)|[Java](problems/lc1268-search-suggestions-system.java)| | Trie or binary search with treemap~ | Medium
|44|[1110. Delete Nodes And Return Forest](https://leetcode.com/problems/delete-nodes-and-return-forest/)|[Java](problems/lc1110-delete-nodes-and-return-forest.java)| |recursion | Medium
|45|[222. Count Complete Tree Nodes](https://leetcode.com/problems/count-complete-tree-nodes/)|[Java](problems/lc222-count-complete-tree-nodes.java)| |recursion | Medium
|46|[951. Flip Equivalent Binary Trees ](https://leetcode.com/problems/flip-equivalent-binary-trees/)|[Java](problems/lc951-flip-equivalent-binary-trees.java)| |recursion, canonical representaion (left child is smaller than the right) | Medium
|47|[1032. Stream of Characters](https://leetcode.com/problems/stream-of-characters/)|[Java](problems/lc1032-stream-of-characters.java)| |Trie | Hard
|48|[543. Diameter of Binary Tree](https://leetcode.com/problems/diameter-of-binary-tree/)|[Java](problems/lc543-diameter-of-binary-tree.java)| |recursion | Easy
|49|[979. Distribute Coins in Binary Tree ](https://leetcode.com/problems/distribute-coins-in-binary-tree/)|[Java](problems/lc979-distribute-coins-in-binary-tree.java)| |recursion | Medium
|50|[671. Second Minimum Node In a Binary Tree ](https://leetcode.com/problems/second-minimum-node-in-a-binary-tree/)|[Java](problems/lc671-second-minimum-node-in-a-binary-tree.java)| |recursion or bfs | Easy
|51|[110. Balanced Binary Tree](https://leetcode.com/problems/balanced-binary-tree/)|[Java](problems/lc110-balanced-binary-tree.java)| |recursion or iterative, subtrees diff by 1 | Easy
|52|[1373. Maximum Sum BST in Binary Tree](https://leetcode.com/problems/maximum-sum-bst-in-binary-tree/)|[Java](problems/lc1373-maximum-sum-bst-in-binary-tree.java)| |need to store: is_BST, sum, min, max | Hard
|53|[98. Validate Binary Search Tree](https://leetcode.com/problems/validate-binary-search-tree/)|[Java](problems/lc98-validate-binary-search-tree.java)| |recursion or use stack| Medium
|55|[94. Binary Tree Inorder Traversal](https://leetcode.com/problems/binary-tree-inorder-traversal/)|[Java](problems/lc94-binary-tree-inorder-traversal.java)| |recursion or use stack| Medium
|56|[230. Kth Smallest Element in a BST](https://leetcode.com/problems/kth-smallest-element-in-a-bst/)|[Java](problems/lc230-kth-smallest-element-in-a-bst.java)| |recursion or use stack| Medium
|57|[1339. Maximum Product of Splitted Binary Tree](https://leetcode.com/problems/maximum-product-of-splitted-binary-tree/)|[Java](problems/lc1339-maximum-product-of-splitted-binary-tree.java)| |recursion | Medium
|58|[1367. Linked List in Binary Tree](https://leetcode.com/problems/linked-list-in-binary-tree/)|[Java](problems/lc1367-linked-list-in-binary-tree.java)| |recursion or KPM| Medium
|59|[107. Binary Tree Level Order Traversal II](https://leetcode.com/problems/binary-tree-level-order-traversal-ii/)|[Java](problems/lc107-binary-tree-level-order-traversal-ii.java)| |dfs or bfs| Easy
|60|[563. Binary Tree Tilt](https://leetcode.com/problems/binary-tree-tilt/)|[Java](problems/lc563-binary-tree-tilt.java)| |recursion | !! Easy
|61|[1372. Longest ZigZag Path in a Binary Tree](https://leetcode.com/problems/longest-zigzag-path-in-a-binary-tree/)|[Java](problems/lc1372-longest-zigzag-path-in-a-binary-tree.java)| |dfs | Medium
|62|[1199. Minimum Time to Build Blocks](https://leetcode.com/problems/minimum-time-to-build-blocks/)|[Java](problems/lc1199-minimum-time-to-build-blocks.java)| | Huffman algorithm | Hard
|63|[108. Convert Sorted Array to Binary Search Tree](https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/)|[Java](problems/lc108-convert-sorted-array-to-binary-search-tree.java)| | recursion or BFS | Easy
|64|[101. Symmetric Tree](https://leetcode.com/problems/symmetric-tree/)|[Java](problems/lc101-symmetric-tree.java)| |recursion OR queue (iterative) | Easy
|65|[111. Minimum Depth of Binary Tree](https://leetcode.com/problems/minimum-depth-of-binary-tree/)|[Java](problems/lc111-minimum-depth-of-binary-tree.java)| | dfs or bfs | Easy
|66|[112. Path Sum](https://leetcode.com/problems/path-sum/)|[Java](problems/lc112-path-sum.java)| | dfs or bfs | Easy
|67|[129. Sum Root to Leaf Numbers](https://leetcode.com/problems/sum-root-to-leaf-numbers/)|[Java](problems/lc129-sum-root-to-leaf-numbers.java)| | recursion or use stack, preorder | Medium
|68|[666. Path Sum IV](https://leetcode.com/problems/path-sum-iv/)|[Java](problems/lc666-path-sum-iv.java)| | recursion or use stack, preorder | Medium

 

(刷到1130)




## Graph

1) Island problems:
    - use dfs /  bfs / union find
    - when reach a '1', go to dfs function 
        - in dfs: 1) check for validility, 2) mark current position to a 0, 3) call dfs on neigbors
    - water flood fill
    lc 1254: num of closed islands
    lc 695:  max area of island

2) Double end bfs:
    lc 752: open the lock

    have 2 hashSet: begin, end
    The whole process is like: 
            Running BFS in 'begin' set -----> Create a new set 'temp' to store the value -----> 
            begin = temp -----> 'begin'(is 'temp' now) and 'end' exchange value ------> 
            (next loop) Running BFS in 'end'(is now 'begin') 
    
    Caution: for 2 end bfs, need to maintain a global set visited in order to not let 2
               ends into looping

3) represent visited/unvisited node state: use int mask
        
        for (int next : graph[node.id])
            Node nextNode = new Node(next, node.mask | (1 << next));



### Leetcode
|#|    Title   |Solution|Complexity|Note|Difficulty|
|---|----------| ------ |----------|----| --------|
|1|[269. Alien Dictionary](https://leetcode.com/problems/alien-dictionary/)| [Java](problems/lc269-alien-dictionary.java)|  O(max(V, E)) | topological sort, or triex| Hard
|2|[207. Course Schedule](https://leetcode.com/problems/course-schedule/)| [Java](problems/lc207-course-schedule.java)| | | Medium
|3|[210. Course Schedule II](https://leetcode.com/problems/course-schedule-ii/)| [Java](problems/lc210-course-schedule-ii.java)| | | Medium
|4|[547. Friend Circles](https://leetcode.com/problems/friend-circles/)| [Java](problems/lc547-friend-circles.java)| | Transitive property! Union find or DFS or BFS| Medium
|5|[773. Sliding Puzzle](https://leetcode.com/problems/sliding-puzzle/)| [Java](problems/lc773-sliding-puzzle.java)| | Transitive property! Union find or DFS or BFS| Hard
|6|[Airbnb - sliding puzzle](https://leetcode.com/problems/sliding-puzzle/)| [Java](problems/airbnb-sliding-puzzle.java)| | | Hard
|7|[787-Airbnb - Minimum Cost with At Most K Stops](https://leetcode.com/problems/cheapest-flights-within-k-stops/)| [Java](problems/lc787-cheap-flight-within-k.java)| | | Hard
|8|[Airbnb - ski need to get max score]()| [Java](problems/lc787-cheap-flight-within-k.java)| | | Hard
|9|[489. Robot Room Cleaner](https://leetcode.com/problems/robot-room-cleaner/)| [Java](problems/lc489-robot-room-cleaner.java)| | | Hard
|10|[994. Rotting Oranges](https://leetcode.com/problems/rotting-oranges/)| [Java](problems/lc994-rotting-oranges.java)| | game of life similar, bfs| easy
|11|[200. Number of Islands](https://leetcode.com/problems/number-of-islands/)| [Java](problems/lc200-number-of-islands.java)| | bfs, dfs, union find| Medium
|12|[684. Redundant Connection](https://leetcode.com/problems/redundant-connection/)| [Java](problems/lc684-redundant-connection.java)| | bfs, dfs, union find| Medium
|13|[Geeks- Unique paths covering every non-obstacle block exactly once in a grid](https://www.geeksforgeeks.org/unique-paths-covering-every-non-obstacle-block-exactly-once-in-a-grid/)| [Java](problems/geeks-unique-paths-covering-every-non-obstacle-block-exactly-once-in-a-grid.java)| | dfs+backtrack, count blocks encountered| Medium
|14|[Geeks- Number of palindromic paths in a matrix](https://www.geeksforgeeks.org/number-of-palindromic-paths-in-a-matrix/)| [Java](problems/geeks-number-of-palindromic-paths-in-a-matrix.java)| | dfs, memo| Medium
|15|[Geeks - Find safe cells in a matrix](https://www.geeksforgeeks.org/find-safe-cells-in-a-matrix/)| [Java](problems/geeks-find-safe-cells-in-a-matrix.java)| | dfs| Medium
|16|[743. Network Delay Time](https://leetcode.com/problems/network-delay-time/)| [Java](problems/lc743-network-delay-time.java)| | dijakstra | Medium
|17|[1254. Number of Closed Islands](https://leetcode.com/problems/number-of-closed-islands/)| [Java](problems/lc1254-number-of-closed-islands.java)| | flood fill + dfs, bfs, union find | Medium
|18|[Geeks - Number of Triangles in Directed and Undirected Graphs](https://www.geeksforgeeks.org/number-of-triangles-in-directed-and-undirected-graphs/)| [Java](problems/geeks-number-of-triangles-in-directed-and-undirected-graphs.java)| O(V^3)| remember to /3 when count| Medium
|19|[1197. Minimum Knight Moves](https://leetcode.com/problems/minimum-knight-moves/)| [Java](problems/lc1197-minimum-knight-moves.java)| | bfs, math | Medium
|20|[1192. Critical Connections in a Network](https://leetcode.com/problems/critical-connections-in-a-network/)| [Java](problems/lc1192-Critical-Connections.java)| | time stamp + dfs (detect cycle) | Hard
|21|[127. Word Ladder](https://leetcode.com/problems/word-ladder/)| [Java](problems/lc127-word-ladder.java)| | bfs, build graph with hashmap | Medium
|22|[126. Word Ladder II](https://leetcode.com/problems/word-ladder-ii/)| [Java](problems/lc126-word-ladder-ii.java)| | find transformation of changing 1 char at a time, build graph, double end bfs| Hard-1
|23|[694. Number of Distinct Islands](https://leetcode.com/problems/number-of-distinct-islands/)| [Java](problems/lc694-number-of-distinct-islands.java)| | dfs with hashmap storing shapes | Medium
|24|[1102. Path With Maximum Minimum Value](https://leetcode.com/problems/path-with-maximum-minimum-value/)| [Java](problems/lc1102-path-with-maximum-minimum-value.java)| | bfs | Medium
|25|[465. Optimal Account Balancing](https://leetcode.com/problems/optimal-account-balancing/)| [Java](problems/lc465-optimal-account-balancing.java)| | dfs, greedy | Hard
|26|[752. Open the Lock](https://leetcode.com/problems/open-the-lock/)| [Java](problems/lc752-open-the-lock.java)| | 2 end bfs | Medium
|27|[980. Unique Paths III](https://leetcode.com/problems/unique-paths-iii/)| [Java](problems/lc980-unique-paths-iii.java)| | DFS/backtrack| Hard
|28|[847. Shortest Path Visiting All Nodes](https://leetcode.com/problems/shortest-path-visiting-all-nodes/)| [Java](problems/lc847-shortest-path-visiting-all-nodes.java)| | BFS, need to remember visited node| Hard
|29|[943. Find the Shortest Superstring](https://leetcode.com/problems/find-the-shortest-superstring/)| [Java](problems/lc943-find-the-shortest-superstring.java)| | traveling salesman, very very hard| Hard
|30|[733. Flood Fill](https://leetcode.com/problems/flood-fill/)| [Java](problems/lc733-flood-fill.java)| | BFS & DFS | Easy
|31|[1066. Campus Bikes II](https://leetcode.com/problems/campus-bikes-ii/)| [Java](problems/lc1066-campus-bikes-ii.java)| | BFS & DFS | Medium
|32|[695. Max Area of Island ](https://leetcode.com/problems/max-area-of-island/)| [Java](problems/lc695-max-area-of-island.java)| | DFS | Medium

 





## Sorting & # Searching
[question summary](https://note.youdao.com/web/#/file/WEBe805023933e71d5b51281681ac1b2ba7/note/WEB9c6245902e12aea67e02d7d6fe109b17/)

Interesting binary search problems:

	lc 1011
	lc 875
	lc 774
	lc 410 --> value 
    lc 74
    lc 1231
    lc 162 - Find Peak Element - Medium



Tricks:

1. Sorting Algorithm:
    
    Arrays.sort() used to sort elements of a array.
    Collections.sort() used to sort elements of a collection.

For primitives, Arrays.sort() uses dual pivot quicksort algorithms.

2. Searching Algorithm:
    
    Arrays.binarySearch(array,key)) used to apply binary search on an sorted array.
    Collections.binarySearch() used to apply binary search on a collection based on comparators.

            int i = Arrays.binarySearch(dp, 0, len, num);   // search in entire array
            int i = Arrays.binarySearch(int[] arr, int fromIndex, int toIndex, int key) // in subarray

            if (i < 0) {
                i = -(i + 1);
            }


3. Binary search:

            int mid = i + Math.floor((j-i)/2);





### Leetcode
|#|    Title   |Solution|Complexity|Note|Difficulty|
|---|----------| ------ |----------|----| --------|
|1|[33-Search in Rotated Sorted Array(binary search)](https://leetcode.com/problems/search-in-rotated-sorted-array/submissions/)| [Java](problems/lc33_search_rotated_sorted_array.java)|O(log n) | need to know where is the "real start" of the array | medium
|2|[34-Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)| [Java](problems/lc34_find_dirst_last_element_sorted_array.java)|O(log n) | !!! do 2 binary search | medium
|3|[21. Merge Two Sorted Lists](https://leetcode.com/problems/merge-two-sorted-lists/submissions/)| [Java](problems/lc21_merge_2_sorted_list.java)| |  | Easy
|4|[23. Merge k Sorted Lists](https://leetcode.com/problems/merge-k-sorted-lists/)| [Java](problems/lc23_merge_k_sorted_list.java)| | redo! node is hard | Hard
|5|[35. Search Insert Position](https://leetcode.com/problems/search-insert-position/)| [Java](problems/lc35-search-insert-position.java)| | edge case | Easy
|6|[378. Kth Smallest Element in a Sorted Matrix ](https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/)| [Java](problems/lc378-kth-smallest-element-in-a-sorted-matrix.java)| | Heap or Binary search, also #240| Medium
|7|[373. Find K Pairs with Smallest Sums](https://leetcode.com/problems/find-k-pairs-with-smallest-sums/)| [Java](problems/lc373-find-k-pairs-with-smallest-sums.java)| | Heap !! method 3 doesn't use priority queue| Medium
|8|[1011. Capacity To Ship Packages Within D Days](https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/)| [Java](problems/lc1011-capacity-to-ship-packages-within-d-days.java)| |Binary search, airbnb-oa4| Medium
|9|[875. Koko Eating Bananas ](https://leetcode.com/problems/koko-eating-bananas/)| [Java](problems/lc875-koko-eating-bananas.java)| |Binary search, airbnb-oa4| Medium
|10|[774. Minimize Max Distance to Gas Station](https://leetcode.com/problems/minimize-max-distance-to-gas-station/)| [Java](problems/lc774-minimize-max-distance-to-gas-station.java)| |Binary search| Hard
|11|[410. Split Array Largest Sum](https://leetcode.com/problems/split-array-largest-sum/)| [Java](problems/lc410-split-array-largest-sum.java)| |!! redo. Binary search on sum of subarry & push, DFS+ memorization, backtrack| Hard
|12|[geeks-K’th Smallest Element in Unsorted Array](https://www.geeksforgeeks.org/kth-smallestlargest-element-unsorted-array-set-3-worst-case-linear-time/)| [Java](problems/geeks-k-smallest-unsorted.java)|worst case: linear | divide to 5 list| Medium
|13|[315. Count of Smaller Numbers After Self](https://leetcode.com/problems/count-of-smaller-numbers-after-self/)|[Java](problems/lc315-count-of-smaller-numbers-after-self.java)| |merge sort, BST, binary indexed tree| Hard
|14|[74. Search a 2D Matrix](https://leetcode.com/problems/search-a-2d-matrix/)|[Java](problems/lc74-search-a-2d-matrix.java)| | binary search on value| Medium
|15|[363. Max Sum of Rectangle No Larger Than K](https://leetcode.com/problems/max-sum-of-rectangle-no-larger-than-k/)|[Java](problems/lc363-max-sum-of-rectangle-no-larger-than-k.java)|O(n * n * (m) * lgm) n = col size, m = row size | treeset, 3 loops, prefix-sum| Hard
|16|[973. K Closest Points to Origin](https://leetcode.com/problems/k-closest-points-to-origin/)|[Java](problems/lc973-k-closest-points-to-origin.java)| O(n), O(N*log k)| quick sort & min-heap| Medium
|17|[240. Search a 2D Matrix II](https://leetcode.com/problems/search-a-2d-matrix-ii/)|[Java](problems/lc240-search-a-2d-matrix-ii.java)| O(m+n) | start from bottom left, or divide & conquer| Medium
|18|[295. Find Median from Data Stream](https://leetcode.com/problems/find-median-from-data-stream/)|[Java](problems/lc295-find-median-from-data-stream.java)| O(log n) | 2 heap(one large,one small)| Hard
|19|[ 347. Top K Frequent Elements ](https://leetcode.com/problems/top-k-frequent-elements/)|[Java](problems/lc347-top-k-frequent-elements.java)| O(n) | bucket sort, tree map, min heap| Medium
|20|[140. Word Break II](https://leetcode.com/problems/word-break-ii/)|[Java](problems/lc140-word-break-ii.java)| | dp or dfs+memorization| Hard
|21|[759. Employee Free Time](https://leetcode.com/problems/employee-free-time/)|[Java](problems/lc759-employee-free-time.java)| O(c*log n)| sweep line or PQ stored| Hard
|22|[1268. Search Suggestions System](https://leetcode.com/problems/search-suggestions-system/)|[Java](problems/lc1268-search-suggestions-system.java)| | Trie or binary search with treemap~ | Medium
|23|[1231. Divide Chocolate](https://leetcode.com/problems/divide-chocolate/)|[Java](problems/lc1231-divide-chocolate.java)| | 神奇的binary search | Hard
|24|[75. Sort Colors](https://leetcode.com/problems/sort-colors/)|[Java](problems/lc75-sort-colors.java)| | quick sort | Medium
|25|[354. Russian Doll Envelopes](https://leetcode.com/problems/russian-doll-envelopes/)|[Java](problems/lc354-russian-doll-envelopes.java)| | quick sort | Hard
|26|[464. Can I Win](https://leetcode.com/problems/can-i-win/)|[Java](problems/lc464-can-i-win.java)| | DFS + memo, key is that opponent always lose | Medium
|27|[162. Find Peak Element](https://leetcode.com/problems/find-peak-element/)|[Java](problems/lc162-find-peak-element.java)| | binary search, math always find a peak on left if a[mid] > a[mid+1] | Medium
|28|[704. Binary Search](https://leetcode.com/problems/binary-search/)|[Java](problems/lc704-binary-search.java)| | binary search | Easy

 

 





## Math
[math summary](https://note.youdao.com/web/#/file/WEBdaa819bb7d914ad6a9e5fbfd891c9791/note/WEBd73094f450a8165863235f4e8763238c/)

Tricks:
1. Calculating the most significant digit of number N:

    double K = Math.log10(N);
    K = K - Math.floor(K);
    int X = (int)Math.pow(10, K);
    X will be the most significant digit.


2. Calculating the # of digits directly: 

    No. of digits in N = Math.floor(Math.log10(N)) + 1;


3. checking for a prime number: returns true if this BigInteger is probably prime(with some certainty), false if it’s definitely composite.

    BigInteger.valueOf(1235).isProbablePrime(1)


4. XOR:

        If we take XOR of zero and some bit, it will return that bit
            a⊕0=a
        If we take XOR of two same bits, it will return 0
            a⊕a=0
        
        a⊕b⊕a = (a⊕a)⊕b = 0⊕b = b

     public int singleNumber(int[] nums) {
        int a = 0;
        for (int i : nums) {
          a ^= i;
        }
        return a;
      }

5. bit mask (lc 1349)

- 0. use (1 << x) to set the x-th bit to one, ex. 1<< 2 = 2^2 = 4, 1<<3 = 2^3 = 8, ...
       
       Thus, we can use an int variable int mask to show the status of x workers
       1) check if the j-th worker is already being processed:
            if (mask & (1<<j)) == 0) 

       2）set the j-th worker as 'already processed':
            mask | (1 << j)

       3）unset j-th bit                
            used &= ~(1 << j)   // (1<<3) = 100, ~(1 << 3) = 011, used = used & 011
                                // ex. used = 1100 now, 1100 & 011 = 1000, now we unset the 3rd bit to 0

- 1. We can use (x >> i) & 1 to get i-th bit in state x, where >> is the right shift operation. 
     If we are doing this in an if statement (i.e. to check whether the i-th bit is 1), we can also use x & (1 << i), where the << is the left shift operation.

- 2. We can use (x & y) == x to check if x is a subset of y. The subset means every state in x could be 1 only if the corresponding state in y is 1.

- 3. We can use (x & (x >> 1)) == 0 to check if there are no adjancent valid states in x.

- 4. store pre-compute count in an array

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                validRows[i] = (validRows[i] << 1) + (seats[i][j] == '.' ? 1 : 0);


6. bit problem (lots of time only care about last digit, don't store entire number to avoid overflow):
    lc 190 --> reverse unsigned bits
    lc 1349 --> use bit and mask to check for upper left position, upper right position
    lc 868 --> binary gap

7. Bitwise operator:

Bitwise operator works on bits and performs bit-by-bit operation. 

Assume if a = 60 and b = 13; now in binary format they will be as follows −

a = 0011 1100

b = 0000 1101

-----------------

a&b = 0000 1100

a|b = 0011 1101

a^b = 0011 0001

~a  = 1100 0011

- & (bitwise and)
    Binary AND Operator copies a bit to the result if it exists in both operands.   
    (A & B) will give 12 which is 0000 1100
- | (bitwise or)  
    Binary OR Operator copies a bit if it exists in either operand. 
    (A | B) will give 61 which is 0011 1101

- ^ (bitwise XOR) 
    Binary XOR Operator copies the bit if it is set in one operand but not both.    
    (A ^ B) will give 49 which is 0011 0001

- ~ (bitwise compliment)  
    Binary Ones Complement Operator is unary and has the effect of 'flipping' bits. 
    (~A ) will give -61 which is 1100 0011 in 2's complement form due to a signed binary number.

- << (left shift) 
    Binary Left Shift Operator. The left operands value is moved left by the number of bits specified by the right operand. 
    A << 2 will give 240 which is 1111 0000

- >> (right shift)    
    Binary Right Shift Operator. The left operands value is moved right by the number of bits specified by the right operand.   
    A >> 2 will give 15 which is 1111

- >>> (zero fill right shift) 
    Shift right zero fill operator. The left operands value is moved right by the number of bits specified by the right operand and shifted values are filled up with zeros.    
    A >>>2 will give 15 which is 0000 1111

- ex. flip 0 to 1, and 1 to 0 
    int x ^ 1
    a = 0011 1100 --> a^1 = 1100 0011


### Leetcode
|#|    Title   |Solution|Complexity|Note|Difficulty|
|---|----------| ------ |----------|----| --------|
|1|[593-Valid Square](https://leetcode.com/problems/valid-square/)|[Java](problems/lc593_valid_square.java)| | do level order traversal of tree. While doing traversal, process nodes of different level separately. | easy
|2|[400-Nth Digit](https://leetcode.com/problems/nth-digit/)|[Java](problems/lc400_nth_digit.java)| | minus single digits, double digits, etc.| easy
|3|[4-Median of Two Sorted Arrays](https://leetcode.com/problems/median-of-two-sorted-arrays/)|[Java](problems/lc4_median_of_two_sorted_array.java)| | lots of math involved, redo when have time| hard
|4|[6. ZigZag Conversion](https://leetcode.com/problems/zigzag-conversion/)|[Java](problems/lc6_zigzag_conversion.java)| | find pattern, use increment & decrement variables, string buffers| medium
|5|[7. Reverse Integer](https://leetcode.com/problems/reverse-integer/)|[Java](problems/lc7_reverse_int.java)| | use %10 | Easy
|6|[9. Palindrome Number](https://leetcode.com/problems/palindrome-number/)|[Java](problems/lc9_palindrome_num.java)| | tricky special case, consider # end with 0, consider loop condition & comparison | Easy
|7|[31. Next Permutation](https://leetcode.com/problems/next-permutation/)|[Java](problems/lc31-next-permutation.java)| | find pattern for next greater num | Medium
|8|[60. Permutation Sequence](https://leetcode.com/problems/permutation-sequence/)|[Java](problems/lc60-permutation-sequence.java)| | redo! permiutation, calculate group | Medium
|9|[264. Ugly Number II](https://leetcode.com/problems/ugly-number-ii/)|[Java](problems/lc264-ugly-number-ii.java)| | dp or heap, find pattern | Medium
|10|[166. Fraction to Recurring Decimal ](https://leetcode.com/problems/fraction-to-recurring-decimal/)|[Java](problems/lc166-fraction-to-recurring-decimal.java)| | dp or heap, find pattern | Medium
|11|[Geeks - Find position of the only set bit](https://www.geeksforgeeks.org/find-position-of-the-only-set-bit/)|[Java](problems/geeks-find-position-of-the-only-set-bit.java)| | dp or heap, find pattern | Medium
|12|[Geeks - Binary representation of a given number](https://www.geeksforgeeks.org/binary-representation-of-a-given-number/)|[Java](problems/geeks-binary-representation.java)| | bit  | Medium
|13|[Geeks - find set bits](https://www.geeksforgeeks.org/count-set-bits-in-an-integer/)|[Java](problems/geeks-count-set-bits.java)| | bit  | Medium
|14|[852. Peak Index in a Mountain Array](https://leetcode.com/problems/peak-index-in-a-mountain-array/)|[Java](problems/lc852-peak-index-in-a-mountain-array.java)| | dp or heap, find pattern | Easy
|15|[Geeks - Sum of all Subarrays ](https://www.geeksforgeeks.org/sum-of-all-subarrays/)|[Java](problems/geeks-sum-all-subarrays.java)| | math, count occurance of each element  | Medium
|16|[1007. Minimum Domino Rotations For Equal Row](https://leetcode.com/problems/minimum-domino-rotations-for-equal-row/)|[Java](problems/lc1007-minimum-domino-rotations-for-equal-row.java)| | observe, countA[i] + countB[i] - same[i] = n| Medium
|17|[279. Perfect Squares](https://leetcode.com/problems/minimum-domino-rotations-for-equal-row/)|[Java](problems/lc1007-minimum-domino-rotations-for-equal-row.java)| | observe, countA[i] + countB[i] - same[i] = n| Medium
|18|[Geeks - Convex Hull - find convex polygon to contain all points](https://www.geeksforgeeks.org/convex-hull-set-1-jarviss-algorithm-or-wrapping/)|[Java](problems/geeks-convex-hull-set-1-jarviss-algorithm-or-wrapping.java)| | greedy, pick the most "counter-clockwise" point  | Medium
|19|[Geeks - Sum of absolute differences of all pairs in a given array](https://www.geeksforgeeks.org/sum-absolute-differences-pairs-given-array/)|[Java](problems/geeks-sum-absolute-differences-pairs-given-array.java)| | Math, sum = sum + (i*a[i]) – (n-1-i)*a[i]  | Medium
|20|[1277. Count Square Submatrices with All Ones](https://leetcode.com/problems/count-square-submatrices-with-all-ones/)|[Java](problems/lc1277-count-square-submatrices-with-all-ones.java)| | observe, countA[i] + countB[i] - same[i] = n| Medium
|21|[793. Preimage Size of Factorial Zeroes Function](https://leetcode.com/problems/preimage-size-of-factorial-zeroes-function/)|[Java](problems/lc793-preimage-size-of-factorial-zeroes-function.java)| | exactly K = (trailing zero < K) - (trailing zero < K-1) | Hard
|22|[273. Integer to English Words](https://leetcode.com/problems/integer-to-english-words/)|[Java](problems/lc273-integer-to-english-words.java)| | array with constants English words | Hard
|23|[957. Prison Cells After N Days](https://leetcode.com/problems/prison-cells-after-n-days/)|[Java](problems/lc957-prison-cells-after-n-days.java)| | stimulation --> store seen ones --> find pattern, 直接mod| Medium
|24|[456. 132 Pattern](https://leetcode.com/problems/132-pattern/)|[Java](problems/lc456-132-pattern.java)| | stimulation --> store seen ones --> find pattern, 直接mod| Medium
|25|[866. Prime Palindrome](https://leetcode.com/problems/prime-palindrome/)|[Java](problems/lc866-prime-palindrome.java)| | check odd length palindrome (even length is a multiple of 11)| Medium
|26|[1041. Robot Bounded In Circle](https://leetcode.com/problems/robot-bounded-in-circle/)|[Java](problems/lc1041-robot-bounded-in-circle.java)| | check odd length palindrome (even length is a multiple of 11)| Medium
|27|[939. Minimum Area Rectangle](https://leetcode.com/problems/minimum-area-rectangle/)|[Java](problems/lc939-minimum-area-rectangle.java)| | check for rectangles, use encoded, sort by row & hashmap| Medium
|28|[1025. Divisor Game](https://leetcode.com/problems/divisor-game/)|[Java](problems/lc1025-divisor-game.java)| | math, done by deduction, return n%2 == 0| Easy
|29|[136. Single Number](https://leetcode.com/problems/single-number/)|[Java](problems/lc136-single-number.java)| | use XOR to achieve linear time & no extra memory --> XOR all bits| Easy
|30|[1227. Airplane Seat Assignment Probability](https://leetcode.com/problems/airplane-seat-assignment-probability/)|[Java](problems/lc1227-airplane-seat-assignment-probability.java)| | Math | Medium
|31|[415. Add Strings](https://leetcode.com/problems/add-strings/)|[Java](problems/lc415-add-strings.java)| | | Easy
|32|[466. Count The Repetitions](https://leetcode.com/problems/count-the-repetitions/)|[Java](problems/lc466-count-the-repetitions.java)| | need to find the pattern | Hard
|33|[1012. Numbers With Repeated Digits](https://leetcode.com/problems/numbers-with-repeated-digits/)|[Java](problems/lc1012-numbers-with-repeated-digits.java)| | dp + math | Hard
|34|[902. Numbers At Most N Given Digit Set](https://leetcode.com/problems/numbers-at-most-n-given-digit-set/)|[Java](problems/lc902-numbers-at-most-n-given-digit-set.java)| | math | Hard
|35|[750. Number Of Corner Rectangles](https://leetcode.com/problems/number-of-corner-rectangles/)|[Java](problems/lc750-number-of-corner-rectangles.java)| | | Medium
|36|[190. Reverse Bits](https://leetcode.com/problems/reverse-bits/)|[Java](problems/lc190-reverse-bits.java)| |bits | Easy
|37|[229. Majority Element II](https://leetcode.com/problems/majority-element-ii/)|[Java](problems/lc229-majority-element-ii.java)| | | Medium
|38|[1058. Minimize Rounding Error to Meet Target](https://leetcode.com/problems/minimize-rounding-error-to-meet-target/)|[Java](problems/lc1058-minimize-rounding-error-to-meet-target.java)| | Very nice greedy solution! DP --> generate all combinations | Medium
|39|[902. Numbers At Most N Given Digit Set](https://leetcode.com/problems/numbers-at-most-n-given-digit-set/)|[Java](problems/lc902-numbers-at-most-n-given-digit-set.java)| | math | Hard
|40|[564. Find the Closest Palindrome]https://leetcode.com/problems/find-the-closest-palindrome/)|[Java](problems/lc564-find-the-closest-palindrome.java)| |  math, corner case| Hard
|41|[868. Binary Gap](https://leetcode.com/problems/binary-gap/)|[Java](problems/lc868-binary-gap.java)| |bits | Easy
|42|[693. Binary Number with Alternating Bits](https://leetcode.com/problems/binary-number-with-alternating-bits/)|[Java](problems/lc693-binary-number-with-alternating-bits.java)| |bits | Easy
|43|[1018. Binary Prefix Divisible By 5](https://leetcode.com/problems/binary-prefix-divisible-by-5/)|[Java](problems/lc1018-binary-prefix-divisible-by-5.java)| |bits | Easy
|44|[1363. Largest Multiple of Three](https://leetcode.com/problems/largest-multiple-of-three/)|[Java](problems/lc1363-largest-multiple-of-three.java)| |mod | Hard

 
 








### Geeks for geeks 
|#|    Title   |Solution|Complexity|Note|
|---|-------------| ----- |----|---------|
|1|[Print ‘K’th least significant bit of a number](https://www.geeksforgeeks.org/print-kth-least-significant-bit-number/)|[Java](problems/k_least_sig_bit.java)| | use shift and AND | easy




## Hashmap

Notes:
1.different ways of checking whether an element exists: 

1)

	if (hashMap.getOrDefault(c, 0) != 0) {
	                return false;
	            } else {
	                hashMap.put(c, 1);
	            }

	!!getOrDefault(key, defaultValue): 当Map集合中有这个key时，就使用这个key值，如果没有就使用默认值defaultValue

2)

	if (!singleSet.add(board[i][j])) return false;

3) 

	void putIfAbsence(path, value)


2.use hashmap to store anagrams

	Map<String, List<String>> map = new HashMap<String, List<String>>();
	Arrays.sort(strs);
	for (String s : strs) {
		char[] ca = s.toCharArray();
		Arrays.sort(ca);
		String keyStr = String.valueOf(ca);
		if (!map.containsKey(keyStr)) map.put(keyStr, new ArrayList<String>());
			map.get(keyStr).add(s);
	}
	
3.create array with each hash map's value:

	new ArrayList<List<String>>(hashmap.values())

4.store letter in hashmap:

	1. Map<Character, Integer> dictT = new HashMap<Character, Integer>();
        for (int i = 0; i < t.length(); i++) {
            int count = dictT.getOrDefault(t.charAt(i), 0);
            dictT.put(t.charAt(i), count + 1);
        }

!! another possibility --> if only characters or integers, use array as the hashmap !!

    2. int[] map = new int[256];
       for(char c: t.toCharArray()){
         map[c - 'A']++;
       }

5.Tricks:
1) computeIfAbsent(Key, Function)

     HashMap<String, Integer> map = new HashMap<>(); 
     map.put("key5", 10000); 
     map.computeIfAbsent("key5", k -> 2000 + 3000); 

     不会放入(key5, 5000)因为 key1 已经有了

2) Treemap.floorEntry(int key): return a key-value mapping associated with the greatest key less than or equal to the given key, or null if there is no such key.

Treemap.ceilingEntry(int key):return a key-value mapping associated with the least key greater than or equal to the given key, or null if there is no such key.

Complexity: treemap guaranteed log(n) time cost for the containsKey, get, put and remove operations, and the implementation of floorEntry() is similar to the implementation of get() in terms of complexity.



[Hashmap-problem summary](https://note.youdao.com/web/#/file/WEBe01409872a4a7218fe6724b5d15fc22f/note/WEB0f69e074804c18ecc25dcfd5ea8e81ab/)
https://note.youdao.com/web/#/file/WEB8268b1d897cef35a3e813ddfb5809e69/note/WEBe30238f51ab83c604d162dfa8a53492b/


### Leetcode
|#|    Title   |Solution|Complexity|Note|Difficulty|
|---|----------| ------ |----------|----| --------|
|1|[593-Valid Square](https://leetcode.com/problems/valid-square/)|[Java](problems/lc593_valid_square.java)| | do level order traversal of tree. While doing traversal, process nodes of different level separately. | easy
|2|[833-Find And Replace in String](https://leetcode.com/problems/valid-square/)|[Java](problems/lc833_find_replace_in_string.java)| | piece table, record all the "to do" changes, then put togetger the string (method 2!!! haven't finished) | medium
|3|[1-two sums](https://leetcode.com/problems/two-sum/submissions/)|[Java](problems/lc1-two-sums.java)| O(n) | use hashmap, put what we are looking for (target sum - current) into the map | easy (hashmap!!!)
|4|[12. Integer to roman](https://leetcode.com/problems/integer-to-roman/)|[Java](problems/lc12_int_to_roman.java)| O(n) | way of creating the list| Medium
|4|[13. Roman to integer](https://leetcode.com/problems/integer-to-roman/)|[Java](problems/llc13_Roman_to_integer.java)| O(n) |consider the case of IV! | Easy
|5|[30. Substring with Concatenation of All Words ](https://leetcode.com/problems/substring-with-concatenation-of-all-words/)|[Java](problems/lc30-substring-with-concatenation-of-all-words.java)| | redo! optimize | Hard
|6|[1166. Design File System](https://leetcode.com/problems/design-file-system)|[Java](problems/lc1166-design-file-system.java)| | Hashmap, trie tree or graph | Medium
|7|[Geek-Equilibrium index of an array](https://www.geeksforgeeks.org/equilibrium-index-of-an-array/)|[Java](problems/geek-equi-index-array.java)| | Hashmap, use sum | Medium
|8|[Twitter-19-OA- partitioning array](https://leetcode.com/discuss/interview-question/375262/Twitter-or-OA-2019-or-Partitioning-array)|[Java](problems/oa-partition-array.java)| | Hashmap| Medium
|9|[Geek-min number of manipulations to make 2 strings anagram](https://www.geeksforgeeks.org/minimum-number-of-manipulations-required-to-make-two-strings-anagram-without-deletion-of-character/)|[Java](problems/geeks-min-change-num-make-anagram.java)| | count-sort (similar), or hashmap| Medium
|10|[299. Bulls and Cows](https://leetcode.com/problems/bulls-and-cows/)|[Java](problems/lc299-bulls-and-cows.java)| | count-sort (similar), or hashmap| Easy
|11|[350. Intersection of Two Arrays II](https://leetcode.com/problems/intersection-of-two-arrays-ii/)|[Java](problems/lc350-intersection-of-two-arrays-ii.java)| | count-sort (similar), or hashmap| Easy
|12|[169. Majority Element](https://leetcode.com/problems/majority-element/)|[Java](problems/lc169-majority-element.java)| | Moore voting, hashmap, bit, sorting| Easy
|13|[interview-check variadic function type matching]()|[Java](problems/confluent-interview.java)| | non-variadic function| Easy
|14|[frequency Of Max Value to its right]()|[Java](problems/frequencyOfMaxValue.java)| | memo, store| Easy
|15|[825. Friends Of Appropriate Ages](https://leetcode.com/problems/friends-of-appropriate-ages/)|[Java](problems/lc825-friends-of-appropriate-ages.java)| | memo, store| Medium
|16|[560. number of continuous Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/)|[Java](problems/lc560-subarray-sum-equals-k.java)| | similar to 2 sum, use pre-sum array in hashmap| Medium
|17|[Geeks - Longest sub-array having sum k](https://www.geeksforgeeks.org/longest-sub-array-sum-k/)|[Java](problems/geeks-longest-sub-array-sum-k.java)| | similar to 2 sum, use pre-sum array in hashmap| Medium
|18|[846. Hand of Straights --> EXACTLY length = w](https://leetcode.com/problems/hand-of-straights/)|[Java](problems/lc846-hand-of-straights.java)| | Treemap, exactly sequence of length w| Medium
|19|[659. Split Array into Consecutive Subsequences --> AT LEAST length = 3](https://leetcode.com/problems/split-array-into-consecutive-subsequences/)|[Java](problems/lc659-split-array-into-consecutive-subsequences.java)| | 2 hashmap, AT LEAST sequence of length w| Medium
|20|[128. Longest Consecutive Sequence](https://leetcode.com/problems/longest-consecutive-sequence/)|[Java](problems/lc128-longest-consecutive-sequence.java)| O(n) | hashmap | Hard
|21|[809. Expressive Words](https://leetcode.com/problems/expressive-words/)|[Java](problems/lc809-expressive-words.java)| O(K*n) = k words, O(n) for checking every word | hashmap, 2 pointers | Medium
|22|[340. Longest Substring with At Most K Distinct Characters](https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/)|[Java](problems/lc340-longest-substring-with-at-most-k-distinct-characters.java)| O(n) | hashmap, 2 pointers/sliding window | Medium
|23|[981. Time Based Key-Value Store](https://leetcode.com/problems/time-based-key-value-store/)|[Java](problems/lc981-time-based-key-value-store.java)| O(1) for set, O(log N) for get | hashmap + binary search, TreeMap | Medium
|24|[1152. Analyze User Website Visit Pattern](https://leetcode.com/problems/analyze-user-website-visit-pattern/)|[Java](problems/lc1152-analyze-user-website-visit-pattern.java)| O(1) for set, O(log N) for get | hashmap + hashset | Medium
|25|[359. Logger Rate Limiter](https://leetcode.com/problems/logger-rate-limiter/)|[Java](problems/lc359-logger-rate-limiter.java)| O(n) | hashmap | Easy
|26|[460. LFU Cache](https://leetcode.com/problems/lfu-cache/)|[Java](problems/lc460-lfu-cache.java)| O(1) | 2 * hashmap + linked list | Hard
|27|[835. Image Overlap](https://leetcode.com/problems/image-overlap/)|[Java](problems/lc835-image-overlap.java)|  | reduce matrix to array by offsets, OR use padding and loop through | Medium
|28|[ 1146. Snapshot Array](https://leetcode.com/problems/snapshot-array/)|[Java](problems/lc1146-snapshot-array.java)|  | only store changed keys, binary search on TreeMap | Medium
|29|[953. Verifying an Alien Dictionary](https://leetcode.com/problems/verifying-an-alien-dictionary/)|[Java](problems/lc953-verifying-an-alien-dictionary.java)|  | hashmap | Easy
|30|[1048. Longest String Chain](https://leetcode.com/problems/longest-string-chain/)|[Java](problems/lc1048-longest-string-chain.java)|  | hashmap | Medium
|31|[706. Design HashMap](https://leetcode.com/problems/design-hashmap/)|[Java](problems/lc706-design-hashmap.java)|  | Design hashmap | Easy
|32|[811. Subdomain Visit Count](https://leetcode.com/problems/subdomain-visit-count/)|[Java](problems/lc811-subdomain-visit-count.java)|  | use substring & hashmap | Easy
|33|[819. Most Common Word](https://leetcode.com/problems/most-common-word/)|[Java](problems/lc819-most-common-word.java)|  |  | Easy
|34|[387. First Unique Character in a String](https://leetcode.com/problems/first-unique-character-in-a-string/)|[Java](problems/lc387-first-unique-character-in-a-string.java)|  |  | Easy
|35|[898. Bitwise ORs of Subarrays](https://leetcode.com/problems/bitwise-ors-of-subarrays/)|[Java](problems/lc898-bitwise-ors-of-subarrays.java)|  | use 3 hashmaps, result & current & prev | Medium
|36|[929. Unique Email Addresses](https://leetcode.com/problems/unique-email-addresses/)|[Java](problems/lc929-unique-email-addresses.java)|  |  | Easy
|37|[804. Unique Morse Code Words](https://leetcode.com/problems/unique-morse-code-words/)|[Java](problems/lc804-unique-morse-code-words.java)|  |  | Easy
|38|[982. Triples with Bitwise AND Equal To Zero]( https://leetcode.com/problems/triples-with-bitwise-and-equal-to-zero/)|[Java](problems/lc982-triples-with-bitwise-and-equal-to-zero.java)|| DP or Hashmap | Hard
|39|[609. Find Duplicate File in System](https://leetcode.com/problems/find-duplicate-file-in-system/)|[Java](problems/lc609-find-duplicate-file-in-system.java)|  | hashmap | Medium
|40|[523. Continuous Subarray Sum](https://leetcode.com/problems/continuous-subarray-sum/)|[Java](problems/lc523-continuous-subarray-sum.java)|  | hashmap | Medium
|41|[697. Degree of an Array](https://leetcode.com/problems/degree-of-an-array/)|[Java](problems/lc697-degree-of-an-array.java)|  | hashmap | Easy
|42|[1218. Longest Arithmetic Subsequence of Given Difference](https://leetcode.com/problems/longest-arithmetic-subsequence-of-given-difference/)|[Java](problems/lc1218-longest-arithmetic-subsequence-of-given-difference.java)|  | hashmap | Medium
|43|[1002. Find Common Characters](https://leetcode.com/problems/find-common-characters/)|[Java](problems/lc1002-find-common-characters.java)|  | hashmap | Easy
|44|[635. Design Log Storage System](https://leetcode.com/problems/design-log-storage-system/)|[Java](problems/lc635-design-log-storage-system.java)|  | tree map | Medium
|45|[890. Find and Replace Pattern](https://leetcode.com/problems/find-and-replace-pattern/)|[Java](problems/lc890-find-and-replace-pattern.java)|  | 2 hashmap to create bijection | Medium

 

  
 






## corner case/tricks

### Leetcode
|#|    Title   |Solution|Complexity|Note|Difficulty|
|---|----------| ------ |----------|----| --------|
|1|[8. String to Integer (atoi)](https://leetcode.com/problems/string-to-integer-atoi/)|[Java](problems/lc8_str_to_int.java)| | str = "", " ", and "  " | Medium
|2|[11. Container With Most water](https://leetcode.com/problems/container-with-most-water/)|[Java](problems/lc11_container_most_water.java)| | reduce calculations, 2 pointer | Medium
|3|[14. Longest Common prefix --- Easy](https://leetcode.com/problems/longest-common-prefix/)|[Java](problems/lc14_longest_common_prefix.java)| | sort or trick | Easy
|4|[18. 4 Sum ](https://leetcode.com/problems/4sum/)|[Java](problems/lc18_4_sum.java)| | increase speed --> put checks! | Medium
|5|[26. Remove Duplicates from Sorted Array ](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)|[Java](problems/lc26_remove-duplicates-from-sorted-array.java)| | method 2 -- use loop variant | Easy
|6|[29. Divide Two Integers](https://leetcode.com/problems/divide-two-integers/)|[Java](problems/lc29-divide-two-integers.java)| |redo!! bit manipulation boost the speed | Medium
|7|[41. First Missing Positive](https://leetcode.com/problems/first-missing-positive/)|[Java](problems/lc41-first-missing-positive.java)| |space need to be o(1) | Hard
|8|[42. Trapping Rain Water](https://leetcode.com/problems/trapping-rain-water/)|[Java](problems/lc42-trapping-rain-water.java)| time:O（n), space:O（1）|2 pointers, dp, stack | Hard
|9|[43. Multiply Strings](https://leetcode.com/problems/multiply-strings/)|[Java](problems/lc43-multiply-strings.java)| | use array to count offsets | Medium
|10|[45. Jump Game II](https://leetcode.com/problems/jump-game-ii/)|[Java](problems/lc45-jump-game-ii.java)|time-O（n), space-O（1） | greedy algo & how to count steps, use boundry | Hard
|11|[48. Rotate Image](https://leetcode.com/problems/rotate-image/)|[Java](problems/lc48-rotate-image.java)|time-O（n), space-O（1） | trick, remember how to rotate | Medium
|12|[50. Pow(x, n)](https://leetcode.com/problems/powx-n/)|[Java](problems/lc50-powx-n.java)|time-log（n）, space-O（1） | many edge case, recursion | Medium
|13|[58. Length of Last Word](https://leetcode.com/problems/length-of-last-word/)|[Java](problems/lc58-length-of-last-word.java)|| check end case | Easy
|14|[65. Valid Number](https://leetcode.com/problems/valid-number/)|[Java](problems/lc65-valid-number.java)|| check all conditions, or use FSM | Hard
|15|[153. Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)|[Java](problems/lc153-find-minimum-in-rotated-sorted-array.java)|| binary search | Medium
|16|[348. Design Tic-Tac-Toe](https://leetcode.com/problems/design-tic-tac-toe/)|[Java](problems/lc348-design-tic-tac-toe.java)|| simply check all winner condition | Medium


  - 



## Design

### Leetcode
|#|    Title   |Solution|Complexity|Note|Difficulty|
|---|----------| ------ |----------|----| --------|
|1|[146. LRU Cache](https://leetcode.com/problems/lru-cache/)|[Java](problems/lc146-lru-cache.java)| |use hashmap + double linked list | Medium
|2|[Trip advisor-design blackjack](https://leetcode.com/discuss/interview-question/object-oriented-design/159168/design-blackjack-game)|[Java](problems/des-blackjack.java)| |several classes, use enum | Medium
|3|[1114. Print in Order](https://leetcode.com/problems/print-in-order/)|[Java](problems/lc1114_Print-in-Order_concurrency.java)| | concurrency | Easy
|4|[588. Design In-Memory File System](https://leetcode.com/problems/design-in-memory-file-system/)|[Java](problems/lc588-design-in-memory-file-system.java)| | REDO! hashmap, very nice OOD structure | Hard

 



## String

1. length: string.length();

2. substring:
    string.substring(int startIndex, int endIndex) --> 不包含endindex，只到endIndex - 1
    string.substring(int index): 保留这个index及其之后的所有letter
        ex. "abcde".substring(2) = "cde"

3. find the index within this string of the first occurrence of the specified character or -1, 
   if the character does not occur.

    string.indexOf(String sub) 

4. str.indexOf(char ch, int strt ): returns the index within this string of the first occurrence of the specified character, starting the search at the specified index or -1, if the character does not occur.

str.indexOf(String str, int strt) : This method returns the index within this string of the first occurrence of the specified substring, starting at the specified index. If it does not occur, -1 is returned.

str.indexOf(String str)

5. instead of using +:
    s.concat(String b)

6. split:
    String[] arrOfStr = str.split(":"); 

7. change from char[] to string

      // Method 1: Using String object
      char[] ch = {'g', 'o', 'o', 'd', ' ', 'm', 'o', 'r', 'n', 'i', 'n', 'g'};
      String str = new String(ch);
 
      // Method 2: Using valueOf method
      String str2 = String.valueOf(ch);

8. regex:

    string.split("\\(") --> \\ means 'abc((((d' --> [abc,d]
    string.split("\\.") 


9. remove all special character:

    String text = "This - text ! has \\ /allot # of % special % characters"; 
    text = text.replaceAll("[^a-zA-Z0-9]", ""); 
    System.out.println(text); 

    String html = "This is bold"; 
    html = html.replaceAll("[^a-zA-Z0-9\\s+]", ""); 
    System.out.println(html);

Output 
    Thistexthasallotofspecialcharacters 
    b This is bold b

10. **Palindrome**

- 1. check if palindrome:
        
        String rev = new StringBuffer(actual).reverse().toString();
        return actual.equals(rev);

- 2. create a palindrome given the first half (lc 564)
    ex. orig = 12345, wants its closest palindrome --> left = 123 (depend on length of orig is even or odd), then call getPalindrome(123, len(12345)%2 == 0)

```java
    private long getPalindrome(long left, boolean even) {
        long res = left;

        // 123 --> 12, so only add '21' to left = 12321
        if (!even) 
            left = left / 10;
        while (left > 0) {
            res = res * 10 + left % 10;
            left /= 10;
        }
        return res;
    }
```


11. KMP algo:
    leetcode 214


12. check if the char is a letter:
    if (Character.isLetter(S.charAt(i)))


13. reverse a string:

1) reverse & string builder
        
        String input = "Geeks for Geeks"; 
        StringBuilder input1 = new StringBuilder(); 
        input1.append(input); 
        output = input1.reverse(); 

2) swap in string array

        String input = "Geeks For Geeks"; 
        char[] arr = input.toCharArray(); 
        int left = 0, 
            right= arr.length-1; 
        for (left=0; left < right ; left++ ,right--) { 
            // Swap values of left and right 
            char temp = arr[left]; 
            arr[left] = arr[right]; 
            arr[right]=temp; 
        } 

14. From string to integer:

    Integer.parseInt(str.substring(0,2) --> ex. str = 11:25, and get the integer 11

### Palindrome questinos:
|#|    Title   |Solution|Complexity|Note|Difficulty|
|---|----------| ------ |----------|----| --------|
|1|[geeksforgeeks-longest non palindrome string](https://www.geeksforgeeks.org/longest-non-palindromic-substring/)|[Java](problems/geeks-longest-non-palindrome.java)|  O(4^n)| redo! tricky| Medium
|2|[214. Shortest Palindrome](https://leetcode.com/problems/shortest-palindrome/)|[Java](problems/lc214-shortest-palindrome.java)| | KMP algo, redo | Hard
|3|[131. Palindrome Partitioning](https://leetcode.com/problems/palindrome-partitioning/)|[Java](problems/lc131-palindrome-partitioning.java)| | back-tracking, or dp | Medium
|4|[1147. Longest Chunked Palindrome Decomposition](https://leetcode.com/problems/longest-chunked-palindrome-decomposition/)|[Java](problems/lc1147-longest-chunked-palindrome-decomposition.java)||  | Hard
|5|[5.Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/)|[Java](problems/lc5_longest_palindrome.java)|O(n), O(1) |!!! start from middle, 2 pointers / Manchester's algo --> linear time |Medium
|6|[Google - 2 string, make cut to make palindrome a1+b2 or a2+b1](https://leetcode.com/discuss/interview-question/306859/Google-phone-screen-Split-strings-to-form-a-palindrome/287491)|[Java](problems/google-cut-str-to-palindrome.java)| O(n) | sliding window, follow up: cut can be in differnet position for str1 and str2 | Medium
|7|[680. Valid Palindrome II](https://leetcode.com/problems/valid-palindrome-ii/)|[Java](problems/lc680-valid-palindrome-ii.java)| | two pointers  | Easy
|8|[234. Palindrome Linked List](https://leetcode.com/problems/palindrome-linked-list/)|[Java](problems/lc234-palindrome-linked-list.java)| | resursion | Easy
|9|[131. Palindrome Partitioning](https://leetcode.com/problems/palindrome-partitioning/)|[Java](problems/lc131-palindrome-partitioning.java)| | back-tracking, or dp | Medium
|10|[516. Longest Palindromic Subsequence](https://leetcode.com/problems/longest-palindromic-subsequence/)|[Java](problems/lc516-longest-palindromic-subsequence.java)| | start from end of the string! | Medium
|11|[647. Palindromic Substrings](https://leetcode.com/problems/palindromic-substrings/)|[Java](problems/lc647-palindromic-substrings.java)|| DP | Medium
|12|[730. Count Different Palindromic Subsequences ](https://leetcode.com/problems/count-different-palindromic-subsequences/)|[Java](problems/lc730-count-different-palindromic-subsequences.java)|| Redo, DP | Hard
|13|[1278. Palindrome Partitioning III](https://leetcode.com/problems/palindrome-partitioning-iii/)|[Java](problems/lc1278-palindrome-partitioning-iii.java)|| DP, good problem | Hard
|14|[1246. Palindrome Removal](https://leetcode.com/problems/palindrome-removal/)|[Java](problems/lc1246-palindrome-removal.java)|| DP, 3 cases | Hard
|15|[336. Palindrome Pairs](https://leetcode.com/problems/palindrome-pairs/)|[Java](problems/lc336-palindrome-pairs.java)|| Trie, not finished for method 2 and 3 | Hard
|16|[Geeks- Number of palindromic paths in a matrix](https://www.geeksforgeeks.org/number-of-palindromic-paths-in-a-matrix/)| [Java](problems/geeks-number-of-palindromic-paths-in-a-matrix.java)| | dfs, memo| Medium
|17|[9. Palindrome Number](https://leetcode.com/problems/palindrome-number/)|[Java](problems/lc9_palindrome_num.java)| | tricky special case, consider # end with 0, consider loop condition & comparison | Easy
|18|[866. Prime Palindrome](https://leetcode.com/problems/prime-palindrome/)|[Java](problems/lc866-prime-palindrome.java)| | check odd length palindrome (even length is a multiple of 11)| Medium
|19|[564. Find the Closest Palindrome]https://leetcode.com/problems/find-the-closest-palindrome/)|[Java](problems/lc564-find-the-closest-palindrome.java)| |  math, corner case| Hard








### Leetcode
|#|    Title   |Solution|Complexity|Note|Difficulty|
|---|----------| ------ |----------|----| --------|
|1|[geeksforgeeks-longest non palindrome string](https://www.geeksforgeeks.org/longest-non-palindromic-substring/)|[Java](problems/geeks-longest-non-palindrome.java)|  O(4^n)| redo! tricky| Medium
|2|[482. License Key Formatting](https://leetcode.com/problems/license-key-formatting/)|[Java](problems/lc482-license-key-formatting.java)| | string builder | Easy
|3|[248. Strobogrammatic Number III](https://leetcode.com/problems/strobogrammatic-number-iii/)|[Java](problems/lc248-strobogrammatic-number-iii.java)| | string builder | Hard
|4|[557. Reverse Words in a String III](https://leetcode.com/problems/reverse-words-in-a-string-iii/)|[Java](problems/lc557-reverse-words-in-a-string-iii.java)| | string builder | Easy
|5|[524. Longest Word in Dictionary through Deleting](https://leetcode.com/problems/longest-word-in-dictionary-through-deleting/)|[Java](problems/lc524-longest-word-in-dictionary-through-deleting.java)| | string, pointer | Medium
|6|[151. Reverse Words in a String](https://leetcode.com/problems/reverse-words-in-a-string/)|[Java](problems/lc151-reverse-words-in-a-string.java)| | string built in method| Medium
|7|[1108. Defanging an IP Address](https://leetcode.com/problems/defanging-an-ip-address/)|[Java](problems/lc1108-defanging-an-ip-address.java)| | replace method or string builder | Easy
|8|[709. To Lower Case](https://leetcode.com/problems/to-lower-case/)|[Java](problems/lc709-to-lower-case.java)| |  | Easy
|9|[383. Ransom Note](https://leetcode.com/problems/ransom-note/)|[Java](problems/lc383-ransom-note.java)| |  | Easy
|10|[1397. Find All Good Strings](https://leetcode.com/problems/find-all-good-strings/)|[Java](problems/lc1397-find-all-good-strings.java)| | KMP algorithm for pattern matching | Hard
|11|[418. Sentence Screen Fitting](https://leetcode.com/problems/sentence-screen-fitting/)|[Java](problems/lc418-sentence-screen-fitting.java)| | | Medium
|12|[761. Special Binary String](https://leetcode.com/problems/special-binary-string/)|[Java](problems/lc761-special-binary-string.java)| | ??????? | Hard
|13|[686. Repeated String Match](https://leetcode.com/problems/repeated-string-match/)|[Java](problems/lc686-repeated-string-match.java)| |  | Easy
|14|[459. Repeated Substring Pattern](https://leetcode.com/problems/repeated-substring-pattern/)|[Java](problems/lc459-repeated-substring-pattern.java)| |  | Easy
|15|[214. Shortest Palindrome](https://leetcode.com/problems/shortest-palindrome/)|[Java](problems/lc214-shortest-palindrome.java)| | KMP algo, redo | Hard
|16|[521. Longest Uncommon Subsequence I](https://leetcode.com/problems/longest-uncommon-subsequence-i/)|[Java](problems/lc521-longest-uncommon-subsequence-i.java)| | brain teaser | Easy
|17|[71. Simplify Path](https://leetcode.com/problems/simplify-path/)|[Java](problems/lc71-simplify-path.java)| | stack| Medium
|18|[681. Next Closest Time](https://leetcode.com/problems/next-closest-time/)|[Java](problems/lc681-next-closest-time.java)| | simulation | Medium
|19|[539. Minimum Time Difference](https://leetcode.com/problems/minimum-time-difference/)|[Java](problems/lc539-minimum-time-difference.java)| | sort or bucket sort | Medium
|20|[165. Compare Version Numbers](https://leetcode.com/problems/compare-version-numbers/)|[Java](problems/lc165-compare-version-numbers.java)| | split or 2 pointers | Medium
|21|[564. Find the Closest Palindrome]https://leetcode.com/problems/find-the-closest-palindrome/)|[Java](problems/lc564-find-the-closest-palindrome.java)| |  math, corner case| Hard
|22|[856. Score of Parentheses](https://leetcode.com/problems/score-of-parentheses/)|[Java](problems/lc856-score-of-parentheses.java)| | stack, array, layers | Medium
|23|[696. Count Binary Substrings](https://leetcode.com/problems/count-binary-substrings/)|[Java](problems/lc696-count-binary-substrings.java)| | stack, array, layers | Easy
|24|[1221. Split a String in Balanced Strings](https://leetcode.com/problems/split-a-string-in-balanced-strings/)|[Java](problems/lc1221-split-a-string-in-balanced-strings.java)| | stack, array, layers | Easy
 
 (刷tag 到 1221)


## minimax

notes on minimax: notes/minimax.md

### Leetcode
|#|    Title   |Solution|Complexity|Note|Difficulty|
|---|----------| ------ |----------|----| --------|
|1|[292. Nim Game](https://leetcode.com/problems/nim-game/)|[Java](problems/lc292-nim-game.java)| | minimax, brain teaser | Easy
|2|[375. Guess Number Higher or Lower II](https://leetcode.com/problems/guess-number-higher-or-lower-ii/)|[Java](problems/lc375-guess-number-higher-or-lower-ii.java)|| minimax + binary search | Medium
|3|[375. Guess Number Higher or Lower II](https://leetcode.com/problems/guess-number-higher-or-lower-ii/)|[Java](problems/lc375-guess-number-higher-or-lower-ii.java)|| minimax + binary search | Medium
|4|[877. Stone Game](https://leetcode.com/problems/stone-game/)|[Java](problems/lc877-stone-game.java)|| minimax, dp | Medium
|5|[486. Predict the Winner](https://leetcode.com/problems/predict-the-winner/)|[Java](problems/lc486-predict-the-winner.java)|| ！！！ redo, minimax, dp | Medium
|6|[843. Guess the Word](https://leetcode.com/problems/guess-the-word/)|[Java](problems/lc843-guess-the-word.java)|| minimax, filter | Hard



## Other
### Leetcode
|#|    Title   |Solution|Complexity|Note|Difficulty|
|---|----------| ------ |----------|----| --------|
|1|[17. Letter Combinations of a Phone Number)](https://leetcode.com/problems/letter-combinations-of-a-phone-number/)|[Java](problems/lc17_letter_combo_phone_num.java)|  O(4^n)| redo! tricky| Medium
|2|[20. Valid Parentheses](https://leetcode.com/problems/valid-parentheses/)|[Java](problems/lc20_valid_parenthese.java)|  O(4^n)| great solution 2, use array as a stack! | Easy
|3|[24. Swap Nodes in Pairs](https://leetcode.com/problems/swap-nodes-in-pairs/)|[Java](problems/lc24_swap_node_in_pairs.java)| | redo! node swapping | Medium
|4|[25. Reverse Nodes in k-Group](https://leetcode.com/problems/reverse-nodes-in-k-group/)|[Java](problems/lc25_reverse_node_in_k_group.java)| | redo! iterative method redo | Hard
|5|[27. Remove Element](https://leetcode.com/problems/remove-element/)|[Java](problems/lc27_remove-element.java)| | redo! iterative method redo | Easy
|6|[28. Implement strStr()](https://leetcode.com/problems/implement-strstr/)|[Java](problems/lc28_implement_strstr.java)| | | Easy
|7|[36. Valid Sudoku](https://leetcode.com/problems/valid-sudoku/)|[Java](problems/lc36-valid-sudoku.java)| | | Medium
|8|[37. Sudoku Solver](https://leetcode.com/problems/sudoku-solver/)|[Java](problems/lc37-sudoku-solver.java)| | | Hard
|9|[38. Count and Say](https://leetcode.com/problems/count-and-say/)|[Java](problems/lc38-count-and-say.java)| | | Easy
|10|[39. Combination Sum](https://leetcode.com/problems/combination-sum/)|[Java](problems/lc39-combination-sum.java)| | back-tracking/dp | Medium
|11|[78. Subsets](https://leetcode.com/problems/subsets/)|[Java](problems/lc78-subsets.java)| | back-tracking/bit | Medium
|12|[90. Subsets II](https://leetcode.com/problems/subsets-ii/)|[Java](problems/lc90-subsets-ii.java)| | back-tracking/bit | Medium
|13|[46. Permutations](https://leetcode.com/problems/permutations/)|[Java](problems/lc46-permutations.java)| | !! back-tracking | Medium
|14|[47. Permutations II](https://leetcode.com/problems/permutations-ii/)|[Java](problems/lc47-permutations-ii.java)| | back-tracking | Medium
|15|[40. Combination Sum II](https://leetcode.com/problems/combination-sum-ii/)|[Java](problems/lc40-combination-sum-ii.java)| | back-tracking | Medium
|16|[131. Palindrome Partitioning](https://leetcode.com/problems/palindrome-partitioning/)|[Java](problems/lc131-palindrome-partitioning.java)| | back-tracking, or dp | Medium
|17|[292. Nim Game](https://leetcode.com/problems/nim-game/)|[Java](problems/lc292-nim-game.java)| | minimax, brain teaser | Easy
|18|[45. Jump Game II](https://leetcode.com/problems/jump-game-ii/)|[Java](problems/lc45-jump-game-ii.java)|time-O（n), space-O（1） | greedy algo | Hard
|19|[49. Group Anagrams](https://leetcode.com/problems/group-anagrams/)|[Java](problems/lc49-group-anagrams.java)|time-O（n), space-O（1） | | Medium
|20|[51. N-Queens](https://leetcode.com/problems/n-queens/)|[Java](problems/lc51-n-queens.java)||backtrack! | Hard
|21|[53. Maximum Subarray](https://leetcode.com/problems/maximum-subarray/)|[Java](problems/lc53-maximum-subarray.java)||dp & trick! (折半) | Easy
|22|[52. N-Queens II](https://leetcode.com/problems/n-queens-ii/)|[Java](problems/lc52-n-queens-ii.java)||back track, set| Hard
|23|[54. Spiral Matrix](https://leetcode.com/problems/spiral-matrix/)|[Java](problems/lc54spiral-matrix.java)||find pattern| Medium
|24|[55. Jump Game](https://leetcode.com/problems/jump-game/)|[Java](problems/lc55-jump-game.java)||similar to #45| Medium
|25|[56. Merge Intervals](https://leetcode.com/problems/merge-intervals/)|[Java](problems/lc56-merge-intervals.java)||similar to 57,252, 253, 435| Medium
|26|[57. Insert Interval](https://leetcode.com/problems/insert-interval/)|[Java](problems/lc57-insert-interval.java)||similar to 56, 252, 253, 435| Hard
|27|[252. Meeting Rooms](https://leetcode.com/problems/meeting-rooms/)|[Java](problems/lc252-meeting-rooms.java)||similar to 56, 252, 253, 435| Easy
|28|[253. Meeting Rooms II](https://leetcode.com/problems/meeting-rooms-ii/)|[Java](problems/lc253-meeting-rooms-ii.java)||similar to 56, 252, 253, 435. !!! redo | Medium
|29|[435. Non-overlapping Intervals](https://leetcode.com/problems/non-overlapping-intervals/)|[Java](problems/lc435-non-overlapping-intervals.java)||similar to 56, 252, 253, 435.  | Medium
|30|[59. Spiral Matrix II](https://leetcode.com/problems/spiral-matrix-ii/)|[Java](problems/lc59-spiral-matrix-ii.java)|| follow pattern, be careful with boundries| Medium
|31|[66. Plus One](https://leetcode.com/problems/plus-one/)|[Java](problems/lc66-plus-one.java)|| follow pattern, be careful with boundries| Medium
|32|[67. Add Binary](https://leetcode.com/problems/add-binary/)|[Java](problems/lc67-add-binary.java)|| follow pattern, be careful with boundries| Medium
|33|[68. Text Justification](https://leetcode.com/problems/text-justification/)|[Java](problems/lc68-text-justification.java)|| redo! | Hard
|34|[591. Tag Validator](https://leetcode.com/problems/tag-validator/)|[Java](problems/lc591-tag-validator.java)|| stack or regex| Hard
|35|[Airbnb-oa4](https://1o24bbs.com/t/topic/12548)|[Java](problems/lcairbnb-deplomacy game.java)|| use hashmap| Hard
|36|[242. Valid Anagram](https://leetcode.com/problems/valid-anagram/)|[Java](problems/lc242-valid-anagram.java)|| use count or hashmap| Easy
|37|[Airbnb-guess-num (cows and bulls)](https://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=290126)|[Java](problems/airbnb-guess-num.java)|| connect to server| Easy
|38|[GS-OA-rotate-strings]()|[Java](problems/GS-OA-rotate-strings.java)|| rotate a string to right or left| Easy
|39|[796. Rotate String](https://leetcode.com/problems/rotate-string/)|[Java](problems/lc796-rotate-string.java)|| use count or hashmap| Easy
|40|[463. Island Perimeter](https://leetcode.com/problems/island-perimeter/)| [Java](problems/lc463-island-perimeter.java)| | pattern of adding neighbors, math| easy
|41|[163. Missing Ranges](https://leetcode.com/problems/missing-ranges/)| [Java](problems/lc163-missing-ranges.java)| | | Medium
|42|[855. Exam Room ](https://leetcode.com/problems/exam-room/submissions/)| [Java](problems/lc855-exam-room.java)| | priority queue, list, or treemap| Medium
|43|[Google - compare strings (intern oa)](https://leetcode.com/discuss/interview-question/352458/)|[Java](problems/google-compare-str.java)|| | Easy
|44|[Google-largest subarray length k](https://leetcode.com/discuss/interview-question/352459/)|[Java](problems/google-largest-subarray-len-k.java)|| | Easy
|45|[Google-max time](https://leetcode.com/discuss/interview-question/396769/)|[Java](problems/google-max-time.java)|| | Easy
|46|[Google - Most Booked Hotel Room](https://leetcode.com/discuss/interview-question/421787/)|[Java](problems/google-most-booked-hotel.java)|| | Easy
|47|[227. Basic Calculator II](https://leetcode.com/problems/basic-calculator-ii/)| [Java](problems/lc227-basic-calculator-2.java)| | stack, or use while| Medium
|48|[224. Basic Calculator](https://leetcode.com/problems/basic-calculator/)| [Java](problems/lc224-basic-calculator.java)| | stack, or use while| Hard
|49|[Geeks - Next Greater Element (in the array)](https://www.geeksforgeeks.org/next-greater-element/)| [Java](problems/geeks-next-greater-element.java)| |stack: pop if smaller than current, assign value | medium
|50|[155. Min Stack](https://leetcode.com/problems/min-stack/)| [Java](problems/lc155-min-stack.java)| | stack store [cur_value, min_value] or 2 stack| Easy
|51|[843. Guess the Word](https://leetcode.com/problems/guess-the-word/)|[Java](problems/lc843-guess-the-word.java)|| minimax, filter | Hard
|52|[1170. Compare Strings by Frequency of the Smallest Character](https://leetcode.com/problems/compare-strings-by-frequency-of-the-smallest-character/)|[Java](problems/lc1170-compare-strings-by-frequency-of-the-smallest-character.java)|| count array or binary search | Easy
|53|[679. 24 Game](https://leetcode.com/problems/24-game/)|[Java](problems/lc679-24-game.java)|| backtrack | Hard
|54|[79. Word Search](https://leetcode.com/problems/word-search/)|[Java](problems/lc79-word-search.java)|| 5acktrack | Medium
|55|[93. Restore IP Addresses ](https://leetcode.com/problems/restore-ip-addresses/)|[Java](problems/lc93-restore-ip-addresses.java)|| backtrack | Medium
|56|[946. Validate Stack Sequences](https://leetcode.com/problems/validate-stack-sequences/)|[Java](problems/lc946-validate-stack-sequences.java)|O(n), space O(n) --> O(1)| stimulation | Medium
|57|[89. Gray Code](https://leetcode.com/problems/gray-code/)|[Java](problems/lc89-gray-code.java)|| bits + find pattern + backtrack | Medium
|58|[357. Count Numbers with Unique Digits](https://leetcode.com/problems/count-numbers-with-unique-digits/submissions/)|[Java](problems/lc357-count-numbers-with-unique-digits.java)|| backtrack OR combinatory| Medium
|59|[243. Shortest Word Distance](https://leetcode.com/problems/shortest-word-distance/)|[Java](problems/lc243-shortest-word-distance.java)||  | Easy
|60|[73. Set Matrix Zeroes](https://leetcode.com/problems/set-matrix-zeroes/)|[Java](problems/lc73-set-matrix-zeroes.java)||  | Medium
|61|[ 306. Additive Number](https://leetcode.com/problems/additive-number/)|[Java](problems/lc306-additive-number.java)|| interesting & tricky, find length of first & 2nd number before checking for validity --> early fail  | Medium
|62|[401. Binary Watch](https://leetcode.com/problems/binary-watch/)|[Java](problems/lc401-binary-watch.java)|| backtrack | Easy
|63|[216. Combination Sum III](https://leetcode.com/problems/combination-sum-iii/)|[Java](problems/lc216-combination-sum-iii.java)|| backtrack | Medium
|64|[351. Android Unlock Patterns](https://leetcode.com/problems/android-unlock-patterns/)|[Java](problems/lc351-android-unlock-patterns.java)|| backtrack OR DFS | Medium
|65|[320. Generalized Abbreviation](https://leetcode.com/problems/generalized-abbreviation/)|[Java](problems/lc320-generalized-abbreviation.java)|| backtrack | Medium
|66|[1079. Letter Tile Possibilities](https://leetcode.com/problems/letter-tile-possibilities/)|[Java](problems/lc1079-letter-tile-possibilities.java)|| backtrack | Medium
|67|[526. Beautiful Arrangement](https://leetcode.com/problems/beautiful-arrangement/)|[Java](problems/lc526-beautiful-arrangement.java)|| backtrack | Medium
|68|[784. Letter Case Permutation](https://leetcode.com/problems/letter-case-permutation/)|[Java](problems/lc784-letter-case-permutation.java)|| backtrack/BFS/ DFS | Easy
|69|[1240. Tiling a Rectangle with the Fewest Squares](https://leetcode.com/problems/tiling-a-rectangle-with-the-fewest-squares/)|[Java](problems/lc1240-tiling-a-rectangle-with-the-fewest-squares.java)|| backtrack/BFS/ DFS | Hard
|70|[303. Range Sum Query - Immutable](https://leetcode.com/problems/range-sum-query-immutable/)|[Java](problems/lc303-range-sum-query-immutable.java)|| prefix sum array | Easy
|71|[87. Scramble String](https://leetcode.com/problems/scramble-string/)|[Java](problems/lc87-scramble-string.java)|| recursion | Hard
|72|[698. Partition to K Equal Sum Subsets](https://leetcode.com/problems/partition-to-k-equal-sum-subsets/)|[Java](problems/lc698-partition-to-k-equal-sum-subsets.java)|| backtrack | Medium
|73|[202. Happy Number](https://leetcode.com/problems/happy-number/)|[Java](problems/lc202-happy-number.java)|| detect cycle with slow & fast rabbit | Easy
|74|[638. Shopping Offers ](https://leetcode.com/problems/shopping-offers/)|[Java](problems/lc638-shopping-offers.java)|| backtrack | Medium
|75|[412. Fizz Buzz](https://leetcode.com/problems/fizz-buzz/)|[Java](problems/lc412-fizz-buzz.java)|| bruteforce or hashmap | Easy
|76|[935. Knight Dialer](https://leetcode.com/problems/knight-dialer/)|[Java](problems/lc935-knight-dialer.java)|| backtrack / dfs + memo | Medium
|77|[204. Count Primes](https://leetcode.com/problems/count-primes/)|[Java](problems/lc204-count-primes.java)||  | Easy
|78|[561. Array Partition I](https://leetcode.com/problems/array-partition-i/)|[Java](problems/lc561-array-partition-i.java)||  | Easy
|79|[944. Delete Columns to Make Sorted](https://leetcode.com/problems/delete-columns-to-make-sorted/)|[Java](problems/lc944-delete-columns-to-make-sorted.java)||  | Easy
|80|[289. Game of Life](https://leetcode.com/problems/game-of-life/)|[Java](problems/lc289-game-of-life.java)|| use 4 variables to denote this state & next state, then update entire board by %2 | Medium
|81|[442. Find All Duplicates in an Array](https://leetcode.com/problems/find-all-duplicates-in-an-array/)|[Java](problems/lc442-find-all-duplicates-in-an-array.java)|| similar to lc 41. , keep swapping to its correct position | Medium
|82|[448. Find All Numbers Disappeared in an Array](https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/)|[Java](problems/lc448-find-all-numbers-disappeared-in-an-array.java)|| similar to lc 41. , keep swapping to its correct position | Easy
|83|[657. Robot Return to Origin](https://leetcode.com/problems/robot-return-to-origin/)|[Java](problems/lc657-robot-return-to-origin.java)||  | Easy
|84|[1147. Longest Chunked Palindrome Decomposition](https://leetcode.com/problems/longest-chunked-palindrome-decomposition/)|[Java](problems/lc1147-longest-chunked-palindrome-decomposition.java)||  | Hard
|84|[999. Available Captures for Rook](https://leetcode.com/problems/available-captures-for-rook/)|[Java](problems/lc999-available-captures-for-rook.java)||  | Easy





  




## Redo/memorize problems
315     count of smaller numbers after self -- merge sort, binary indexed tree
552     student attendance record 2 -- DP, 重新推导
42      trapping rain water
44      the dp solution
121     Best Time to Buy and Sell Stock
85      Max rectangle -- DP, how to 'compress' the 2D matrix
140     word break 2 -- dfs & backtrack, find pattern
1240    tiling a rectangle with fewest squares -- backtrack
1130    min cost tree from leaf values -- understand the problem.... + stack
309     All the stock buy & sell problems -- https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/discuss/108870/Most-consistent-ways-of-dealing-with-the-series-of-stock-problems
403     Frog jump -- very cool DP optimization: use array to store reachable jumps
706     Design Hashmap
494     target sum -- weird DP
903     valid permutations of DI seqeunce -- hard to think of dp
1000    Min cost to merge stones -- similar to burst ballons/matrix multiplication, but a little twist
                                    good dp practise problem
733     flood fill -- good practise problem, DFS, BFS
361     bomb-enemy -- interesting way to store & DP
292     Nin Game
960     delete to make column sorted 3
41      first missing positive --> template use for many other find xxx in array[1~n]
691     stickers to spell word --> good string / decompose / backtrack problem
1235    Maximum Profit in Job Scheduling --> classic problem, use TreeMap or sort + binary search for floor entry
609     find duplicate file in system --> good follow up problems！
1125    smallest sufficient team --> set cover DP problem
214     shortest palindrome --> KMP algorithm
514     Freedom Trail --> good DP problem
1349    maximum students taking exam --> DP + bit mask




## Concurrency
### Leetcode
|#|    Title   |Solution|Complexity|Note|Difficulty|
|---|----------| ------ |----------|----| --------|
|1|[182. Duplicate Emails)](https://leetcode.com/problems/duplicate-emails/)|[Java](problems/lc182-duplicate-emails.java)| | having, self-join | Easy



  

## SQL
### Leetcode
|#|    Title   |Solution|Complexity|Note|Difficulty|
|---|----------| ------ |----------|----| --------|
|1|[182. Duplicate Emails)](https://leetcode.com/problems/duplicate-emails/)|[Java](problems/lc182-duplicate-emails.java)| | having, self-join | Easy







## general notes:

1. always start with empty or null pointer
2. check corner cases
3. with int, consider have leading 0's
4. string.charAt(position)
5. faster run time --> puts more checks before heading to the for loop + break quick if sth. went wrong
6. trick: //decrease list size to half --> len = (len+1)/2;
7. find unique xxx --> if given a list/array, first sort the array, then use if (nums[i] == nums[i-1]) continue; 	in a for loop gto avoid
8. sometimes let the false condition to return false maybe easier



## Stack problem --> notes in #907.

1130    Minimum Cost Tree From Leaf Values
907     Sum of Subarray Minimums
901     Online Stock Span
856     Score of Parentheses
503     Next Greater Element II
496     Next Greater Element I
84      Largest Rectangle in Histogram
42      Trapping Rain Water



## notes

|#|    Title   |Match Question|Note|
|---|----------| -------------|-----|
|1|[backtracking)](notes/backtracking.md)|[78. Subsets](https://leetcode.com/problems/subsets/), 46.permutation, 47. permutation II,  51. N-Queens| recursion
