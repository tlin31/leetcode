
题目是给你公式，比如偶数的话除2，奇数的话就变成3*n+1，对于任何一个正数，数学猜想是最终他会变成1。每变一步步数加1，
给一个上限，让找出范围内最长步数。

从7变到1，一共17步：

7->22->11->34->17->52->26->13->40->20->10->5->16->8->4->2->1

Assumption:
1. The input is a integer n
2. return the max steps we can find for value from 1 to n
3. Assume The number will become 1 in the end

Approach:
    - The idea is straightforward, for each number, in a while loop, we keep apply change to the number 
      until it become 1. 
    - If the step is greater than a global max, we update global max.
    - to reduce the number of unnecessay computation, we use a hash table to memorize the steps for 
        number we have found.

Time: O(n*k) k is the average steps to become 1
Space: O(n)



import java.util.HashMap;
import java.util.Map;

public class CollatzConjecture {

    public static void main(String[] args) {
        CollatzConjecture sol = new CollatzConjecture();
        System.out.println(sol.findMax(1000000));
    }

    public int findMax(int n) {
        int max = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            int curr = i;
            int step = 0;

            // didn't reach to the end
            while (curr != 1) {
                // there's a loop, we can't reach 1
                if (map.containsKey(curr)) {
                    step += map.get(curr);
                    break;
                }
                step++;
                if (curr % 2 == 0) {
                    curr = curr / 2;
                } else {
                    curr = curr * 3 + 1;
                }
            }
            map.put(i, step);
            max = Math.max(max, step);
        }
        return max;
    }
}
// Recursion
//     public int findMax(int n) {
//         int max = 0;
//         Map<Integer, Integer> map = new HashMap<>();
//         for (int i = 1; i <= n; i++) {
//             int step = findStep(i, map);
//             map.put(i, step);
//             max = Math.max(max, step);
//         }
//         return max;
//     }

//     private int findStep(int num, Map<Integer, Integer> map) {
//         if (num == 1) return 0;
//         if (map.containsKey(num)) return map.get(num);
//         if (num % 2 == 1) {
//             return 1 + findStep(3 * num + 1, map);
//         } else {
//             return 1 + findStep(num / 2, map);
//         }
//     }


no hashmap:
    private int findSteps(int num) {
            if (num <= 1) return 1;
            if (num % 2 == 0) return 1 + findSteps(num / 2);
            return 1 + findSteps(3 * num + 1);
        }

        public int findLongestSteps(int num) {
            if (num < 1) return 0;

            int res = 0;
            for (int i = 1; i <= num; i++) {
                int t = findSteps(i);
                res = Math.max(res, t);
            }

            return res;
        }


