135. Candy -Hard

There are n children standing in a line. Each child is assigned a rating value given in the 
integer array ratings.

You are giving candies to these children subjected to the following requirements:

Each child must have at least one candy.
Children with a higher rating get more candies than their neighbors.
Return the minimum number of candies you need to have to distribute the candies to the children.

 

Example 1:

Input: ratings = [1,0,2]
Output: 5
Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
Example 2:

Input: ratings = [1,2,2]
Output: 4
Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
The third child gets 1 candy because it satisfies the above two conditions.
 

Constraints:

n == ratings.length
1 <= n <= 2 * 104
0 <= ratings[i] <= 2 * 104

******************************************************
key:
	- peak and valley
	- when see equal ratings, set the second to 1
	- edge case:
		1) 
		2)

******************************************************

peak & valley
1. 解题思路：左右双取向
这道题的核心难点在于，一个孩子的糖果数同时受左邻居和右邻居的影响。如果我们只走一遍，很难同时满足两边的条件。
最佳实践：
    左规则 (Left-to-Right)：从左向右遍历。如果右边比左边评分高，右边的糖果 = 左边 + 1。
    右规则 (Right-to-Left)：从右向左遍历。如果左边比右边评分高，左边的糖果取 (当前值) 和 (右边 + 1) 中的最大值。

Time & space O(n)

class Solution {
    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        
        // 每人至少分 1 个
        Arrays.fill(candies, 1);

        // 1. 从左往右扫：处理比左邻居分高的情况
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }

        // 2. 从右往左扫：处理比右邻居分高的情况
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                // 取最大值是为了同时满足左规则和右规则
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }

        // 统计总和
        int totalCandies = 0;
        for (int c : candies) {
            totalCandies += c;
        }
        return totalCandies;
    }
}



=======================================================================================================
Method 1: 坡度理论 (The Slope Theory)

想象一下评分数组是一个山脉，由“上坡”、“下坡”和“平原”组成：
上坡 (Ascent)：糖果数必须递增（1, 2, 3...）。
下坡 (Descent)：糖果数必须递减（...3, 2, 1）。
平原 (Flat)：评分相同。根据题目，评分相同不需要更多糖果，所以直接只给 1 个。

2. 算法三大变量 (Three Keys)
我们要维护三个状态：
    pre: 前一个孩子分到的糖果数。
    inc: 当前连续上坡的长度。
    dec: 当前连续下坡的长度。

3. 逻辑规则 (The Rules)
如果评分增加 (Up)：
pre++, inc = pre, dec = 0。
ans += pre。

如果评分相等 (Flat)：(重置为最低限度)
pre = 1, inc = 1, dec = 0。
ans += 1。

如果评分降低 (Down)：
为了保持糖果数最少，下坡的终点必须是 1 个糖果。但我们是从左往右走的，我们不知道下坡有多长。
解决办法：每多一步下坡，我们就给“之前的下坡路径”上的每个人都多补 1 个糖果。

dec++。
    假设从山顶 (5) 开始下坡：
    评分:    5  ->  3  ->  2  ->  1
    步数:          [1]    [2]    [3]  (dec 增加)

    第1步下坡 (到3): 给 3 分 1 个。           累加 +1 (dec=1)
    第2步下坡 (到2): 给 3 分 2 个, 2 分 1 个。 累加 +2 (dec=2)
    第3步下坡 (到1): 给 3 分 3 个, 2 分 2 个, 1 分 1 个。 累加 +3 (dec=3)

如果下坡长度达到了上坡长度（dec == inc），说明山峰那个点也需要补一个糖果（为了让下坡能接得住）。
ans += dec。
pre = 1（下坡到底部，下一个起跳点设为 1）。



Stats:

	- 
	- 


Method:

	-	

class Solution {
    public int candy(int[] ratings) {
        int n = ratings.length;
        if (n <= 1) return n;

        int ans = 1; // 第一个孩子起码 1 个
        int inc = 1; // 连续上升长度
        int dec = 0; // 连续下降长度
        int pre = 1; // 前一个人分的糖果

        for (int i = 1; i < n; i++) {
            //上坡
            if (ratings[i] >= ratings[i - 1]) {
                dec = 0; // reset dec， 以防前面一个是下坡的
                pre = (ratings[i] == ratings[i - 1]) ? 1 : pre + 1;
                ans += pre;
                inc = pre;
            } 
            else {
                dec++;
                // 关键点：如果下坡长度等于上坡高度，山峰需要额外加 1
                if (dec == inc) {
                    dec++;
                }
                ans += dec;
                pre = 1;
            }
        }
        return ans;
    }
}


例子：

数组：[1, 3, 5, 3, 2, 1]
步骤  评分  坡度  动作  糖果分布示意 (仅逻辑)    总数 ans
1   1   起点  ans=1, inc=1, pre=1 [1] 1
2   3   上坡  pre=2, ans+=2, inc=2    [1, 2]  3
3   5   上坡  pre=3, ans+=3, inc=3    [1, 2, 3]   6
4   3   下坡  dec=1, ans+=1   [1, 2, 3, 1]    7
5   2   下坡  dec=2, ans+=2   [1, 2, 3, 2, 1] 9
6   1   下坡  dec=3. 发现 dec == inc!   [1, 2, 4, 3, 2, 1]  9 + 4 = 13
注意步骤 6：
原本 dec 应该是 3，但因为 dec 追平了 inc (3)，说明山顶的 3 不够用了。
我们将 dec 变成 4（相当于给山顶补了 1），总数增加 4。最终山顶变成了 4。


=======================================================================================================

        public int Candy(int[] ratings) {
            if (ratings.Length == 0) return 0;
            int candy = 1;
            int up = 0, down = 0, peak = 0;
            for (int i = 1; i < ratings.Length; i++) {
                if (ratings[i - 1] < ratings[i]) {
                    peak = ++up;
                    down = 0;
                    candy += 1 + up;
                } 
                //two same ratings, just start form 1 again, thus +=1
                else if (ratings[i - 1] == ratings[i])  {
                    peak = up = down = 0;
                    candy += 1;
                } 
                else {
                	// if decreasing, set up to 0, find down/valley
                    up = 0;
                    down++;
					candy += down + (peak >= down ? 0 : 1);                
				}
            }

            return candy;
        }


=======================================================================================================


    // Initialize 1 candy for each child
    int[] candies = new int[ratings.length];
    for (int i = 0; i < candies.length; i++) {
        candies[i] = 1;
    }
    
    // from left to right, increase 1 candy if see an up slope
    for (int i= 1; i < ratings.length; i++) {
        if (ratings[i] > ratings[i - 1]) {
            candies[i] = candies[i - 1] + 1;
        }
    }
    
    // from right to left, increase 1 candy if see an up slope
    for (int i = ratings.length - 2; i >= 0; i--) {
        if (ratings[i] > ratings[i + 1]) {
            // i may be a peak
            candies[i] = Math.max(candies[i], candies[i + 1] + 1);
        }
    }
    
    int total = 0;
    for (int i = 0; i < candies.length; i++) {
        total += candies[i];
    }
    
    return total;



















