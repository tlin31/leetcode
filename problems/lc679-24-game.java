679. 24 Game - Hard

You have 4 cards each containing a number from 1 to 9. You need to judge whether they could 
operated through *, /, +, -, (, ) to get the value of 24.

Example 1:
Input: [4, 1, 8, 7]
Output: True
Explanation: (8-4) * (7-1) = 24

Example 2:
Input: [1, 2, 1, 2]
Output: False

Note:
The division operator / represents real division, not integer division. For example, 4 / (1 - 2/3) = 12.
Every operation done is between two numbers. In particular, we cannot use - as a unary operator. For example, with [1, 1, 1, 1] as input, the expression -1 - 1 - 1 - 1 is not allowed.
You cannot concatenate numbers together. For example, if the input is [1, 2, 1, 2], we cannot write this as 12 + 12.


******************************************************
key:
	- backtrack, store newly calculated result & mark in a bool visited array for (already calculated) 
	- edge case:
		1) only 1 number in input, test == 24
		2)

******************************************************


思路：
    把问题看成逐步合并数列：

    从当前列表中选出任意两个数 a 和 b；

    用四种运算（a+b, a-b, b-a, a*b, a/b (如果 b!=0), b/a (如果 a!=0)）生成结果 c；

    把 a 与 b 从列表中删掉，把 c 加入列表 -> 继续递归。

    终止条件：列表只剩 1 个数，判断其是否接近 24（允许浮点误差）。

    这是标准的 DFS（深度优先搜索）+ 回溯（backtracking）。

精度问题（Floating-point）

    浮点计算会产生误差，通常用 EPS = 1e-6 判断 Math.abs(x - 24) < EPS。

    可选更严谨做法：用**有理数（Fraction）**表示每个中间值（分子/分母整型）以避免浮点误差；或用 BigInteger 做分数约简（面试加分项）。

优化技巧（常考）

    对加法与乘法做交换性剪枝：对于 a+b 与 b+a、a*b 与 b*a，只需计算一次（避免重复）。
    实现时：在枚举两个数时，可以只对 i<j 并对 a+b，a*b 加一次；但 a-b、b-a 都要做。

    浮点短路：一旦找到满足 24 的表达式，立即返回，不必遍历剩余。

    排序或哈希记忆化（对更大 n）：把当前 multiset 编码成 key 做 visited 集合避免重复（状态压缩）。

    使用分数（Fraction）避免 eps 问题：把每个值表示为 (num, den) 并约简，最后比较 num/den == 24。

    提前剪枝：如果数值过大/过小（比如全为0且不是24），可剪枝（适用场景有限）。


class Solution {
    private static final double TARGET = 24.0;
    private static final double EPS = 1e-6;

    public boolean judgePoint24(int[] nums) {
        List<Double> list = new ArrayList<>();
        for (int n : nums) list.add((double) n);
        return dfs(list);
    }

    private boolean dfs(List<Double> nums) {
        int n = nums.size();
        if (n == 1) {
            return Math.abs(nums.get(0) - TARGET) < EPS;
        }

        // choose any two indices i < j
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double a = nums.get(i), b = nums.get(j);

                // 把除了 i 和 j 之外的所有数字加入 next
                List<Double> next = new ArrayList<>();
                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) continue;
                    next.add(nums.get(k));
                }

                // try all possible results from a and b
                List<Double> candidates = generateCandidates(a, b);

                for (double cand : candidates) {
                    next.add(cand);
                    if (dfs(next)) return true;
                    next.remove(next.size() - 1);
                }
            }
        }
        return false;
    }

    private List<Double> generateCandidates(double a, double b) {
        List<Double> res = new ArrayList<>();
        res.add(a + b);
        res.add(a - b);
        res.add(b - a);
        res.add(a * b);
        if (Math.abs(b) > 1e-9) res.add(a / b);
        if (Math.abs(a) > 1e-9) res.add(b / a);
        return res;
    }
}


返回表达式版本

class SolutionExpr {
    private static final double TARGET = 24.0;
    private static final double EPS = 1e-6;

    public String getExpression(int[] nums) {
        List<Pair> list = new ArrayList<>();
        for (int n : nums) list.add(new Pair((double)n, String.valueOf(n)));
        Pair ans = dfsExpr(list);
        return ans == null ? null : ans.expr;
    }

    private Pair dfsExpr(List<Pair> nums) {
        int n = nums.size();
        if (n == 0) return null;
        if (n == 1) {
            if (Math.abs(nums.get(0).val - TARGET) < EPS) return nums.get(0);
            return null;
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Pair A = nums.get(i), B = nums.get(j);
                List<Pair> next = new ArrayList<>();
                for (int k = 0; k < n; k++) if (k != i && k != j) next.add(nums.get(k));

                List<Pair> candidates = generatePairs(A, B);

                for (Pair cand : candidates) {
                    next.add(cand);
                    Pair res = dfsExpr(next);
                    if (res != null) return res;
                    next.remove(next.size() - 1);
                }
            }
        }
        return null;
    }

    private List<Pair> generatePairs(Pair A, Pair B) {
        List<Pair> out = new ArrayList<>();
        double a = A.val, b = B.val;
        out.add(new Pair(a + b, "(" + A.expr + "+" + B.expr + ")"));
        out.add(new Pair(a - b, "(" + A.expr + "-" + B.expr + ")"));
        out.add(new Pair(b - a, "(" + B.expr + "-" + A.expr + ")"));
        out.add(new Pair(a * b, "(" + A.expr + "*" + B.expr + ")"));
        if (Math.abs(b) > 1e-9) out.add(new Pair(a / b, "(" + A.expr + "/" + B.expr + ")"));
        if (Math.abs(a) > 1e-9) out.add(new Pair(b / a, "(" + B.expr + "/" + A.expr + ")"));
        return out;
    }

    static class Pair {
        double val;
        String expr;
        Pair(double v, String e) { val = v; expr = e; }
    }
}

===================================================================================================

method: 用boolean【】 visited加速

	- there are limited number of combinations for cards and operators (+-*/()). 
	- One idea is to search among all the possible combinations. This is what backtracking does.
	- Note that the brackets () play no role in this question. Say, parentheses give some operators 
	  a higher priority to be computed. However, the following algorithm has already considered 
	  priorities, thus it is of no use to take parentheses into account anymore.

	- arr contains value for cards， vis[i] indicates whether arr[i] has been used or not. 
	  Every time select 2 un-used cards arr[i] and arr[j]. Calculate the answer for arr[i] and arr[j] 
	  with some operator, update arr[i] with this new value and mark arr[j] as visited. 

	  Now we have 1 less card available. Note that we should use that new value (new arr[i]) in the
	  future, thus we should NOT mark arr[i] as visited. 

	  When there is no card available, check whether the answer is 24 or not.

	- Since each time after we select 2 cards arr[i] and arr[j], we just do the calculation without
	  considering the priorities for operators we use, we could think that we have already added a 
	  pair of () for arr[i] OPERATOR arr[j]. 
	  This contains all the possible considerations, thus we do not need to consider parentheses anymore.


stats:

	- Runtime: 0 ms, faster than 100.00% of Java online submissions for 24 Game.
	- Memory Usage: 37.1 MB, less than 100.00% of Java online submissions for 24 Game.


public boolean judgePoint24(int[] nums) {
        boolean[] vis = new boolean[4];
        double[] arr = new double[4];
        for (int i=0; i<4; i++) 
        	arr[i] = 1.0 * nums[i];
        return backtrack(arr, vis, 4);
    }
    
    boolean backtrack(double[] arr, boolean[] vis, int available) {
        double prev = 0;

        // exit condition
        if (available == 1) {
            for (int i=0; i<arr.length; i++) 
                if (!vis[i]) 
                	return Math.abs(arr[i]-24) < 0.000001 ? true : false;
        } 

        for (int i=0; i < arr.length; i++) {
            if (vis[i]) 
            	continue;

            // prev is for backtracking
            prev = arr[i];

            // check all combinations
            for (int j=i+1; j<arr.length; j++) {
                // calculate i & j
                if (vis[j]) 
                	continue;

                vis[j] = true;
                
                // add
                arr[i] = prev + arr[j];
                if (backtrack(arr, vis, available-1)) return true;
                
                // minus
                arr[i] = prev - arr[j];
                if (backtrack(arr, vis, available-1)) return true;

                arr[i] = -prev + arr[j];
                if (backtrack(arr, vis, available-1)) return true;
                
                // multiply
                arr[i] = prev * arr[j];
                if (backtrack(arr, vis, available-1)) return true;
                
                // division
                arr[i] = prev / arr[j];
                if (backtrack(arr, vis, available-1)) return true;

                arr[i] = arr[j] / prev;
                if (backtrack(arr, vis, available-1)) return true;
                
                vis[j] = false;
            }

            // recover arr[i]
            arr[i] = prev;
        }
        return false;
    }

=======================================================================================================
method 3:

method:

	- dfs
	- 

stats:

	- Runtime: 6 ms, faster than 54.42% of Java online submissions for 24 Game.
	- Memory Usage: 41.2 MB, less than 6.25% 


public boolean judgePoint24(int[] nums) {
        List<Double> list = new ArrayList<>();
        for (int i : nums) {
            list.add((double) i);
        }
        return dfs(list);
    }

    private boolean dfs(List<Double> list) {
        if (list.size() == 1) {
            if (Math.abs(list.get(0)- 24.0) < 0.001) {
                return true;
            }
            return false;
        }

        for(int i = 0; i < list.size(); i++) {
            for(int j = i + 1; j < list.size(); j++) {
                for (double c : generatePossibleResults(list.get(i), list.get(j))) {
                    List<Double> nextRound = new ArrayList<>();
                    nextRound.add(c);
                    for(int k = 0; k < list.size(); k++) {
                        if(k == j || k == i) continue;
                        nextRound.add(list.get(k));
                    }
                    if(dfs(nextRound)) return true;
                }
            }
        }
        return false;

    }

    private List<Double> generatePossibleResults(double a, double b) {
        List<Double> res = new ArrayList<>();
        res.add(a + b);
        res.add(a - b);
        res.add(b - a);
        res.add(a * b);
        res.add(a / b);
        res.add(b / a);
        return res;
    }



Fraction 版本

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class JudgePoint24Fraction {
    private static final BigInteger TARGET = BigInteger.valueOf(24);

    // 分数类（不可变），自动约简并保证分母为正
    static class Fraction {
        final BigInteger num;
        final BigInteger den;

        Fraction(BigInteger n, BigInteger d) {
            if (d.equals(BigInteger.ZERO)) throw new ArithmeticException("denominator zero");
            // 规范化：让分母为正
            if (d.signum() < 0) {
                n = n.negate();
                d = d.negate();
            }
            BigInteger g = n.gcd(d);
            if (!g.equals(BigInteger.ONE)) {
                n = n.divide(g);
                d = d.divide(g);
            }
            this.num = n;
            this.den = d;
        }

        Fraction(long n) {
            this(BigInteger.valueOf(n), BigInteger.ONE);
        }

        Fraction add(Fraction o) {
            BigInteger n = this.num.multiply(o.den).add(o.num.multiply(this.den));
            BigInteger d = this.den.multiply(o.den);
            return new Fraction(n, d);
        }

        Fraction sub(Fraction o) {
            BigInteger n = this.num.multiply(o.den).subtract(o.num.multiply(this.den));
            BigInteger d = this.den.multiply(o.den);
            return new Fraction(n, d);
        }

        Fraction mul(Fraction o) {
            BigInteger n = this.num.multiply(o.num);
            BigInteger d = this.den.multiply(o.den);
            return new Fraction(n, d);
        }

        Fraction div(Fraction o) {
            if (o.num.equals(BigInteger.ZERO)) throw new ArithmeticException("divide by zero");
            BigInteger n = this.num.multiply(o.den);
            BigInteger d = this.den.multiply(o.num);
            return new Fraction(n, d);
        }

        boolean equalsTarget24() {
            // 判断 num / den == 24  <=> num == den * 24
            return this.num.equals(this.den.multiply(TARGET));
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Fraction)) return false;
            Fraction o = (Fraction) obj;
            return this.num.equals(o.num) && this.den.equals(o.den);
        }

        @Override
        public int hashCode() {
            return num.hashCode() * 31 + den.hashCode();
        }

        @Override
        public String toString() {
            return num + "/" + den;
        }
    }

    // Public API：输入整型数组（例如长度为4），判断能否得到 24
    public boolean judgePoint24(int[] nums) {
        List<Fraction> list = new ArrayList<>();
        for (int x : nums) list.add(new Fraction(BigInteger.valueOf(x), BigInteger.ONE));
        return dfs(list);
    }

    private boolean dfs(List<Fraction> nums) {
        int n = nums.size();
        if (n == 0) return false;
        if (n == 1) {
            return nums.get(0).equalsTarget24();
        }

        // 任选两个数进行合并
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Fraction a = nums.get(i);
                Fraction b = nums.get(j);

                List<Fraction> next = new ArrayList<>();
                for (int k = 0; k < n; k++) if (k != i && k != j) next.add(nums.get(k));

                // 枚举 a 与 b 的六种运算结果（加、减、反减、乘、除、反除）
                List<Fraction> cands = new ArrayList<>();
                cands.add(a.add(b));
                cands.add(a.sub(b));
                cands.add(b.sub(a));
                cands.add(a.mul(b));
                if (!b.num.equals(BigInteger.ZERO)) cands.add(a.div(b));
                if (!a.num.equals(BigInteger.ZERO)) cands.add(b.div(a));

                for (Fraction cand : cands) {
                    next.add(cand);
                    if (dfs(next)) return true;
                    next.remove(next.size() - 1);
                }
            }
        }
        return false;
    }

    // 测试
    public static void main(String[] args) {
        JudgePoint24Fraction solver = new JudgePoint24Fraction();
        int[] a1 = {4,1,8,7};
        int[] a2 = {1,2,1,2};
        System.out.println("4 1 8 7 => " + solver.judgePoint24(a1)); // true
        System.out.println("1 2 1 2 => " + solver.judgePoint24(a2)); // false
    }
}



