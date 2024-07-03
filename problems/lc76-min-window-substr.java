76. Minimum Window Substring - Hard

Given a string S and a string T, find the minimum window in S which will contain all the 
characters in T in complexity O(n).

Example:

Input: S = "ADOBECODEBANC", T = "ABC"
Output: "BANC"
Note:

If there is no such window in S that covers all characters in T, return the empty string "".
If there is such window, you are guaranteed that there will always be only one unique minimum 
window in S.

******************************************************
key:
	- 2 pointers + hashmap
	- edge case:
		1) if not found, return ""
		2)

******************************************************


S = "ADOBECODEBANC", T = "ABC"
A D O B E C O D E B A N C //l 和 r 初始化为 0
^
l
r

A D O B E C O D E B A N C //向后移动 r，扩大窗口
^ ^
l r

A D O B E C O D E B A N C //向后移动 r，扩大窗口
^   ^
l   r

A D O B E C O D E B A N C //向后移动 r，扩大窗口
^     ^
l     r

A D O B E C O D E B A N C //向后移动 r，扩大窗口
^       ^
l       r


//此时窗口中包含了所有字母（ABC），停止移动 r，记录此时的 l 和 r，然后开始移动 l
A D O B E C O D E B A N C 
^         ^
l         r

//向后移动 l，减小窗口，此时窗口中没有包含所有字母（ABC），重新开始移动 r，扩大窗口
A D O B E C O D E B A N C 
  ^       ^
  l       r

//移动 r 直到窗口包含了所有字母（ABC），
//和之前的长度进行比较，如果窗口更小，则更新 l 和 r
//然后开始移动 l，开始缩小窗口
A D O B E C O D E B A N C 
  ^                 ^
  l                 r

//此时窗口内依旧包含所有字母
//和之前的长度进行比较，如果窗口更小，则更新 l 和 r
//继续移动 l，继续缩小窗口
A D O B E C O D E B A N C 
    ^               ^
    l               r

//此时窗口内依旧包含所有字母
//和之前的长度进行比较，如果窗口更小，则更新 l 和 r
//继续移动 l，继续缩小窗口
A D O B E C O D E B A N C 
      ^             ^
      l             r

//继续减小 l，直到窗口中不再包含所有字母，然后开始移动 r，不停的重复上边的过程，直到全部遍历完
class Solution {
    public String minWindow(String s, String t) {
        int[] map= new int[128];
        for (char c: t.toCharArray()){
            map[c]++;
        }

        int start=0, end=0, minStart=0, minLen=Integer.MAX_VALUE, counter=t.length();
        while(end<s.length()){
            char c1= s.charAt(end);
            if (map[c1]>0) counter--;
            map[c1]--;
            end++;
            while(counter==0){ //all char in t should be mapped to 0
                if (minLen>end-start){
                    minLen=end-start;
                    minStart=start;
                }
                // move start pointer, shrink the window
                char c2= s.charAt(start);
                map[c2]++;

                // When map[c2]>0, then a char exists in t was deleted
                // increase counter, break out of the loop, searching for that c2 
                if (map[c2]>0) counter++;
                start++;
            }
        }
        return minLen==Integer.MAX_VALUE?"":s.substring(minStart,minStart+minLen);
    }
}

=======================================================================================================
method 1:

method:
    - hashmap stores <character, count>
	- 用双指针 left 和 right 表示一个窗口, right 向右移增大窗口，直到窗口包含了所有要求的字母。进行第二步。
	- 记录此时的长度，left 向右移动，开始减少长度，每减少一次，就更新最小长度。直到当前窗口不包含所有字母，
		回到第 1 步。
	- 判断字符串相等，并且不要求顺序，之前已经用过很多次了，利用 HashMap，对于两个字符串 S = "ADOBECODEBANC", 
		T = "ABCB"，用 map 统计 T 的每个字母的出现次数，然后遍历 S，遇到相应的字母，就将相应字母的次数减 1，
		如果此时 map 中所有字母的次数都小于等于 0，那么此时的窗口一定包含了所有字母。



stats:

	- 时间复杂度：O（nm），n 是 S 的长度，match 函数消耗 O（m）。
	- 空间复杂度：O（m），m 是 T 的长度。
	Runtime: 31 ms, faster than 15.02% of Java online submissions for Minimum Window Substring.
Memory Usage: 36.9 MB, less than 98.94%



public String minWindow(String s, String t) { 
    Map<Character, Integer> map = new HashMap<>();

    //遍历字符串 t，初始化每个字母的次数
    for (int i = 0; i < t.length(); i++) {
        char char_i = t.charAt(i);
        map.put(char_i, map.getOrDefault(char_i, 0) + 1);
    }

    int left = 0; //左指针
    int right = 0; //右指针
    int ans_left = 0; //保存最小窗口的左边界
    int ans_right = -1; //保存最小窗口的右边界
    int ans_len = Integer.MAX_VALUE; //当前最小窗口的长度

    //遍历字符串 s
    while (right < s.length()) {
        char char_right = s.charAt(right);

        //判断 map 中是否含有当前字母
        if (map.containsKey(char_right)) {

            //当前的字母次数减一
            map.put(char_right, map.get(char_right) - 1);

            //开始移动左指针，减小窗口
            //call match function, 如果当前窗口包含所有字母，就进入循环
            while (match(map)) { 
                
                //当前窗口大小
                int temp_len = right - left + 1;

                //如果当前窗口更小，则更新相应变量
                if (temp_len < ans_len) {
                    ans_left = left;
                    ans_right = right;
                    ans_len = temp_len;
                }

                //得到左指针的字母
                char key = s.charAt(left);

                //判断 map 中是否有当前字母
                if (map.containsKey(key)) {

                    //因为要把当前字母移除，所有相应次数要加 1
                    map.put(key, map.get(key) + 1);
                }

                left++; 
            }

        }

        // 当不包含全部字母的时候，继续扩大右指针
        //右指针右移扩大窗口
        right++;
    }
    return s.substring(ans_left, ans_right+1);
}

//判断所有的 value 是否为 0
private boolean match(Map<Character, Integer> map) {
    for (Integer value : map.values()) {
        if (value > 0) {
            return false;
        }
    }
    return true;
}


=======================================================================================================
method 2:

method:

    - Using length (coint) to replace checking all map values
        题意是找到str中最短的substring，它里面与t的所有字母对应的数量更多。
        比如t里面有3个A，那么substring里面至少有3个A。
        第一步，数一下t里面每个字母出现了多少次。
        第二步，move end point，找到str中满足条件的字符串。就是刚好减掉了n个，n是t的长度。
        第三步，move start point，去夹逼最小的substring，意思就是move start到不能往右移为止，多移一位
            substring就不满足条件。
        第四步，比较长度。
        第五步，把start右移一位，让substring不满足条件。
        回到第二步。

    - use int array instead of hashmap
    

stats:

    - Runtime: 2 ms, faster than 98.82% of Java online submissions for Minimum Window Substring.
    - Memory Usage: 36.7 MB, less than 98.94%
    - Each element will be checked at most twice. So it is O(2N) -> O(N)

public String minWindow(String str, String t) {
      int[] map = new int[256];

      for(char c: t.toCharArray()){
        map[c - 'A']++;
      }
      
      int minLen = Integer.MAX_VALUE, 
        minStart = 0;
  
      int length = t.length();
      char[] strArr = str.toCharArray();

      //start & end
      int start = 0, end = 0;

      while(end < strArr.length){

        //get end character
        int endChar = strArr[end] - 'A';
        map[endChar]--;

        if(map[endChar] >= 0){
          length--; 
        }
        
        // if length of t is zero, now move the start char
        if(length == 0){
          int startChar = strArr[start] - 'A';
          while(map[startChar] < 0){
            map[startChar]++;
            start++;
            startChar = strArr[start] - 'A';  
          }
          
          int len = end - start + 1;
          if(len < minLen){
            minLen = len;
            minStart = start;
          }
          
          // recover one by one
          map[startChar]++;
          start++;
          length++;
        }
        
        end++;
      }
  
      return minLen == Integer.MAX_VALUE ? "" : str.substring(minStart, minStart + minLen);
    }





=======================================================================================================
method 2:

method:

    - reduce the time complexity of the algorithm to O(2*|filtered\_S| + |S| + |T|), where 
        filtered\_S is the string formed from S by removing all the elements not present in T.
    - This complexity reduction is evident when |filtered\_S| <<< |S|∣filtered_S∣<<<∣S∣.
    - This kind of scenario might happen when length of string TT is way too small than the length 
      of string SS and string SS consists of numerous characters which are not present in TT.
    - We create a list called filtered\_S which has all the characters from string SS along with 
        their indices in SS, but these characters should be present in TT.
    - 

stats:

    - ∣filtered_S∣ <<< |S|, the complexity would reduce because the number of iterations would be
        2*|filtered\_S| + |S| + |T|
    - Space Complexity : O(|S| + |T|)

import javafx.util.Pair;

class Solution {
    public String minWindow(String s, String t) {

        if (s.length() == 0 || t.length() == 0) {
            return "";
        }

        Map<Character, Integer> dictT = new HashMap<Character, Integer>();

        for (int i = 0; i < t.length(); i++) {
            int count = dictT.getOrDefault(t.charAt(i), 0);
            dictT.put(t.charAt(i), count + 1);
        }

        int required = dictT.size();

        // Filter all the characters from s into a new list along with their index.
        // The filtering criteria is that the character should be present in t.
        List<Pair<Integer, Character>> filteredS = new ArrayList<Pair<Integer, Character>>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (dictT.containsKey(c)) {
                filteredS.add(new Pair<Integer, Character>(i, c));
            }
        }

        int l = 0, r = 0, formed = 0;
        Map<Character, Integer> windowCounts = new HashMap<Character, Integer>();  
        int[] ans = {-1, 0, 0};

        // Look for the characters only in the filtered list instead of entire s.
        // This helps to reduce our search.
        // Hence, we follow the sliding window approach on as small list.
        while (r < filteredS.size()) {
            char c = filteredS.get(r).getValue();
            int count = windowCounts.getOrDefault(c, 0);
            windowCounts.put(c, count + 1);

            if (dictT.containsKey(c) && windowCounts.get(c).intValue() == dictT.get(c).intValue()) {
                formed++;
            }

            // Try and contract the window till the point where it ceases to be 'desirable'.
            while (l <= r && formed == required) {
                c = filteredS.get(l).getValue();

                // Save the smallest window until now.
                int end = filteredS.get(r).getKey();
                int start = filteredS.get(l).getKey();
                if (ans[0] == -1 || end - start + 1 < ans[0]) {
                    ans[0] = end - start + 1;
                    ans[1] = start;
                    ans[2] = end;
                }

                windowCounts.put(c, windowCounts.get(c) - 1);
                if (dictT.containsKey(c) && windowCounts.get(c).intValue() < dictT.get(c).intValue()) {
                    formed--;
                }
                l++;
            }
            r++;   
        }
        return ans[0] == -1 ? "" : s.substring(ans[1], ans[2] + 1);
    }
}
