60. Permutation Sequence - Medium

The set [1,2,3,...,n] contains a total of n! unique permutations.

By listing and labeling all of the permutations in order, we get the following sequence for n = 3:

"123"
"132"
"213"
"231"
"312"
"321"

Given n and k, return the kth permutation sequence.

Note:

Given n will be between 1 and 9 inclusive.
Given k will be between 1 and n! inclusive.

Example 1:
Input: n = 3, k = 3
Output: "213"

Example 2:
Input: n = 4, k = 9
Output: "2314"

******************************************************
key:
	- first sort array, then calculate, since # permutation = 3(first digit) * 2(2nd digit) * 1
		ex. for n = 3, k = 3 --> 3 - 2! = 1 -->
		ex. for n = 4, k = 0 --> 9 - 2! = 7, 7 - 3! = 1 

	- edge case:
		1) n = 0, return 0
		2) n = 1, return 1

******************************************************



=======================================================================================================
method 1:

method:

	- 以 n = 4 为例，因为是从小到大排列，那么最高位一定是从 1 到4。然后可以看成一组一组的，我们只需要
		求出组数，就知道最高位是多少了。而每组的个数就是 n - 1 的阶乘，也就是 3 的阶乘 6。
	- 算组数的时候， 1 到 5 除以 6 是 0，6 除以 6 是 1，而 6 是属于第 0 组的，所有要把 k 减去 1。这样做除法
		结果就都是 0 了。
			int perGroupNum = factorial(n - 1); 
			int groupNum = (k - 1) / perGroupNum;

	- 当然，还有一个问题下次 k 是多少了。求组数用的除法，余数就是下次的 k 了。因为 k 是从 1 计数的，所以如果 k 
		刚好等于了 perGroupNum 的倍数，此时得到的余数是 0 ，而其实由于我们求 groupNum 的时候减 1 了，所以此时 
		k 应该更新为 perGroupNum。

			k = k % perGroupNum; 
			k = k == 0 ? perGroupNum : k;

	- 举个例子，如果 k = 6，那么 groupNum = ( k - 1 ) / 6 = 0， k % perGroupNum = 6 % 6 = 0，而下次的 k ，
		可以结合上图，很明显是 perGroupNum ，依旧是 6。
	- 结合下图，确定了最高位属于第 0 组，下边就和上边的情况一样了。唯一不同的地方是最高位是 2 3 4，没有了 1。
		所有得到 groupNum 怎么得到最高位需要考虑下。
	- 我们可以用一个 list 从小到大保存 1 到 n，每次选到一个就去掉，这样就可以得到 groupNum 对应的数字了。


	-say n = 4, you have {1, 2, 3, 4}.If you were to list out all the permutations you have
		1 + (permutations of 2, 3, 4)
		2 + (permutations of 1, 3, 4)
		3 + (permutations of 1, 2, 4)
		4 + (permutations of 1, 2, 3)
	- We know how to calculate the number of permutations of n numbers... n! So each of
		those with permutations of 3 numbers means there are 6 possible permutations.
		Meaning there would be a total of 16 permutations in this particular one. So if you
		were to look for the (k = 14) 14th permutation, it would be in the
		3 + (permutations of 1, 2, 4) subset.
	- To programmatically get that, you take k = 13 (subtract 1 because of things always
		starting at 0) and divide that by the 6 we got from the factorial, which would give
		you the index of the number you want. In the array {1, 2, 3, 4}, k/(n-1)! = 13/(4-1)! =
		13/3! = 13/6 = 2. The array {1, 2, 3, 4} has a value of 3 at index 2. So the first
		number is a 3.
	- Then the problem repeats with less numbers.
		The permutations of {1, 2, 4} would be:
			1 + (permutations of 2, 4)
			2 + (permutations of 1, 4)
			4 + (permutations of 1, 2)
	- Update k, we subtract 12 from k, which gives you 1. Programmatically that would be...

			k = k - (index from previous) * (n-1)! = k - 2(n-1)! = 13 - 2(3)! = 1

		In this second step, permutations of 2 numbers has only 2 possibilities, meaning
		each of the three permutations listed above a has two possibilities, giving a total of
		6. 

	- We are looking for the first one, so that would be in the 1 + (permutations of 2, 4)
		subset, so 
			index to get number from is k / (n - 2)! = 1 / (4-2)! = 1 / 2! = 0.. from {1, 2,
		4}, index 0 is 1

		so the numbers we have so far is 3, 1... and then repeating without explanations.
	- {2, 4}
		k = k - (index from pervious) * (n-2)! = k - 0 * (n - 2)! = 1 - 0 = 1;
		third number index = k / (n - 3)! = 1 / (4-3)! = 1/ 1! = 1... from {2, 4}, index 1 has 4
		Thus, 3rd number is 4
	- {2}
		k = k - (index from pervious) * (n - 3)! = k - 1 * (4 - 3)! = 1 - 1 = 0;
		fourth 4th index = k / (n - 4)! = 0 / (4-4)! = 0/ 1 = 0... from {2}, index 0 has 2
		Thus, 4th number is 2

stats:

	- 
	- Runtime: 1 ms, faster than 99.29% of Java online submissions for Permutation Sequence.
	- Memory Usage: 34.3 MB, less than 100.00%

	public String getPermutation(int n, int k) {

		// use num to store the factors from 1,.... n
	    List<Integer> nums = new ArrayList<Integer>();
	    for (int i = 0; i < n; i++) {
	        nums.add(i + 1);
	    }
	    return getAns(nums, n, k);
	}

	private String getAns(List<Integer> nums, int n, int k) {
	    if (n == 1) {
	        //把剩下的最后一个数字返回就可以了
	        return nums.get(0) + "";
	    }

	    int perGroupNum = factorial(n - 1); //每组的个数
	    int groupNum = (k - 1) / perGroupNum;
	    int num = nums.get(groupNum);
	    nums.remove(groupNum);
	    k = k % perGroupNum; //更新下次的 k 
	    k = k == 0 ? perGroupNum : k;
	    return num + getAns(nums, n - 1, k);
	}
	public int factorial(int number) {
	    if (number <= 1)
	        return 1;
	    else
	        return number * factorial(number - 1);
	}

----------------------------------------------------------------------------------------

	public String getPermutation(int n, int k) {
	    List < Integer > num = new LinkedList < Integer > ();

	    for (int i = 1; i <= n; i++) num.add(i);

	    int[] fact = new int[n]; // factorial
	    fact[0] = 1;
	    for (int i = 1; i < n; i++) fact[i] = i * fact[i - 1];

	    k = k - 1;

	    StringBuilder sb = new StringBuilder();
	    for (int i = n; i > 0; i--) {
	        int groupNum = k / fact[i - 1];
	        k = k % fact[i - 1];
	        sb.append(num.get(groupNum));
	        num.remove(groupNum);
	    }
	    return sb.toString();
	}

=======================================================================================================
method 2:

method:

	- optimize of method 1
	- 1. 更新 k 的时候，不用
		k = k % perGroupNum; //更新下次的 k 
		k = k == 0 ? perGroupNum : k;
	我们只要把 k - 1 % perGroupNum，这样得到的结果就是 k 从 0 编码的了。
	然后求 groupNum = (k - 1) / perGroupNum; 这里 k 也不用减 1 了。

	- 2. 这个算法很容易改成改成迭代的写法，只需要把递归的函数参数， 在每次迭代更新就够了。
	- 3. 我们求 perGroupNum 的时候，每次都调用了求迭代的函数，其实没有必要的，我们只需要一次循环求出 n 的阶乘。
		然后在每次迭代中除以 nums 的剩余个数就够了。


stats:

	- 时间复杂度：O（n），当然如果 remove 函数的时间是复杂度是 O（n），那么整体上就是 O（n²）。
	- 空间复杂度：O（1）。

	public String getPermutation(int n, int k) {
	    List<Integer> nums = new ArrayList<Integer>();
	    int factorial = 1;
	    for (int i = 0; i < n; i++) {
	        nums.add(i + 1);
	        if (i != 0) {
	            factorial *= i;		        // factorial[] = {1, 1, 2, 6, 24, ... (n-1)!}
	        }
	    }
	    factorial *= n; //先求出 n 的阶乘
	    StringBuilder ans = new StringBuilder();
	    k = k - 1; // k 变为 k - 1

	    for (int i = n; i > 0; i--) { 
	    	
	        factorial /= (nums.size()); //更新为 n - 1 的阶乘
	        int groupNum = k / factorial;
	        int num = nums.get(groupNum);
	        nums.remove(groupNum);
	        k = k % factorial;
	        ans.append(num);  

	    }
	    return ans.toString();
	}



