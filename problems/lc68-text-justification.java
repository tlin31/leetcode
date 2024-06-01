68. Text Justification - Hard

Given an array of words and a width maxWidth, format the text such that each line has exactly 
maxWidth characters and is fully (left and right) justified.

You should pack your words in a greedy approach; that is, pack as many words as you can in each 
line. Pad extra spaces ' ' when necessary so that each line has exactly maxWidth characters.

Extra spaces between words should be distributed as evenly as possible. If the number of spaces 
on a line do not divide evenly between words, the empty slots on the left will be assigned more 
spaces than the slots on the right.

For the last line of text, it should be left justified and no extra space is inserted between words.

Note:

A word is defined as a character sequence consisting of non-space characters only.
Each word s length is guaranteed to be greater than 0 and not exceed maxWidth.
The input array words contains at least one word.
Example 1:

Input:
words = ["This", "is", "an", "example", "of", "text", "justification."]
maxWidth = 16
Output:
[
   "This    is    an",
   "example  of text",
   "justification.  "
]
Example 2:

Input:
words = ["What","must","be","acknowledgment","shall","be"]
maxWidth = 16
Output:
[
  "What   must   be",
  "acknowledgment  ",
  "shall be        "
]
Explanation: Note that the last line is "shall be    " instead of "shall     be",
             because the last line must be left-justified instead of fully-justified.
             Note that the second line is also left-justified becase it contains only one word.
Example 3:

Input:
words = ["Science","is","what","we","understand","well","enough","to","explain",
         "to","a","computer.","Art","is","everything","else","we","do"]
maxWidth = 20
Output:
[
  "Science  is  what we",
  "understand      well",
  "enough to explain to",
  "a  computer.  Art is",
  "everything  else  we",
  "do                  "
]





******************************************************
key:
  - rules!!!
  - 

  - edge case:
    1) empty string
    2) line only has one word

******************************************************


public class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>();
        List<String> curWords = new ArrayList<>();
        int curLen = 0;

        for (String word : words) {

            // curWrods.size is the number of current spaces
            if (curLen + word.length() + curWords.size() > maxWidth) {
                int totalSpaces = maxWidth - curLen;
                int gaps = curWords.size() - 1;

                //1 word firs this whole line
                if (gaps == 0) {
                    res.add(curWords.get(0) + " ".repeat(totalSpaces));
                } 
                else {
                    int spacePerGap = totalSpaces / gaps;
                    int extraSpaces = totalSpaces % gaps;
                    StringBuilder line = new StringBuilder();
                    for (int i = 0; i < curWords.size(); i++) {
                        line.append(curWords.get(i));
                        if (i < gaps) {
                            line.append(" ".repeat(spacePerGap));
                            if (i < extraSpaces) {
                                line.append(' ');
                            }
                        }
                    }
                    res.add(line.toString());
                }
                curWords.clear();
                curLen = 0;
            }

            // add new words will still stay in this cur line
            curWords.add(word);
            curLen += word.length();
        }

        StringBuilder lastLine = new StringBuilder(String.join(" ", curWords));
        while (lastLine.length() < maxWidth) {
            lastLine.append(' ');
        }
        res.add(lastLine.toString());

        return res;
    }
}

=======================================================================================================
method 1:

method:

  - 一个句子，和一个长度表示一行最长的长度，然后对齐文本，有下边几个规则。
      1. 同一个单词只能出现在一行中，不能拆分
      2. 一行如果只能放下一个单词，该单词放在最左边，然后空格补齐，例如 
        "acknowledgement#####"，这里只是我为了直观，# 表示空格，题目并没有要求。
      3. 一行如果有多个单词，最左边和最右边不能有空格，每个单词间隙尽量平均，如果无法平均，把剩余的空隙从左边开始分配。例
        如，"enough###to###explain##to"，3 个间隙，每个 2 个空格的话，剩下 2 个空格，从左边依次添加一个空格。
      4. 最后一行执行左对齐，单词间一个空格，末尾用空格补齐。
  - 一行一行计算该行可以放多少个单词，然后计算单词间的空隙是多少，然后把它添加到结果中。


stats:
  - Runtime: 1 ms, faster than 42.13% of Java online submissions for Text Justification.
  - Memory Usage: 36.3 MB, less than 97.22%

public List<String> fullJustify(String[] words, int maxWidth) {
    List<String> ans = new ArrayList<>();
    //当前行单词已经占用的长度
    int currentLen = 0;

    //保存当前行的单词
    List<String> row = new ArrayList<>();

    //iterate all words
    for (int i = 0; i < words.length;) {

        //case 1: adding current word will not exceed max width
        //  case 1.1: current word is the first in this row
        //  case 1.2: if already has words, need to add 1 blank space before curr & prev words
        if (currentLen == 0 && currentLen + words[i].length() <= maxWidth
            || currentLen > 0 && currentLen + 1 + words[i].length() <= maxWidth) {
            row.add(words[i]);
            if (currentLen == 0) {
                currentLen = currentLen + words[i].length();
            } 

            else {
                currentLen = currentLen + 1 + words[i].length();
            }
            i++;
        
        } 

        //case 2: add curr word exceeds max-width
        else {

            //计算有多少剩余，也就是总共的空格数，因为之前计算 currentLen 多算了一个空格，这里加回来
            int sub = maxWidth - currentLen + row.size() - 1;

            // case 2.1: if only has 1 word in row, fill up the rest of the space with blanks
            if (row.size() == 1) {
                String blank = getStringBlank(sub);
                ans.add(row.get(0) + blank);
            } 

            // case 2.2: allocate rest space into prev blank ranges
            else {
                StringBuilder temp = new StringBuilder();
                temp.append(row.get(0));
                int averageBlank = sub / (row.size() - 1);
                
                //如果除不尽，计算剩余空格数
                int missing = sub - averageBlank * (row.size() - 1);
                
                //前 missing 的空格数比平均空格数多 1
                String blank = getStringBlank(averageBlank + 1);
                int k = 1;
                for (int j = 0; j < missing; j++) {
                    temp.append(blank + row.get(k));
                    k++;
                }
                //剩下的空格数就是求得的平均空格数
                blank = getStringBlank(averageBlank);
                for (; k < row.size(); k++) {
                    temp.append(blank + row.get(k));
                }
                //将当前结果加入 
                ans.add(temp.toString());

            }

            //清空以及置零
            row = new ArrayList<>();
            currentLen = 0;

        }
    }
    //单独考虑最后一行，左对齐
    StringBuilder temp = new StringBuilder();
    temp.append(row.get(0));
    for (int i = 1; i < row.size(); i++) {
        temp.append(" " + row.get(i));
    }
    //剩余部分用空格补齐
    temp.append(getStringBlank(maxWidth - currentLen));
    //最后一行加入到结果中
    ans.add(temp.toString());
    return ans;
}

//得到 n 个空白
private String getStringBlank(int n) {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < n; i++) {
        str.append(" ");
    }
    return str.toString();
}



=======================================================================================================
method 2:

method:

  - 
  - 

stats:

  - 
  - Runtime: 1 ms, faster than 42.13% of Java online submissions for Text Justification.
  - Memory Usage: 35.4 MB, less than 97.22%


    // main function
    public List<String> fullJustify(String[] words, int maxWidth) {
        int left = 0; List<String> result = new ArrayList<>();

        while (left < words.length) {
            int right = findRight(left, words, maxWidth);
            result.add(justify(left, right, words, maxWidth));
            left = right + 1;
        }

        return result;
    }

    //找到当前行最右边的单词下标
    private int findRight(int left, String[] words, int maxWidth) {
        int right = left;
        int sum = words[right++].length();

        while (right < words.length && (sum + 1 + words[right].length()) <= maxWidth)
            sum += 1 + words[right++].length();

        return right - 1;
    }

    //根据不同的情况添加不同的空格
    private String justify(int left, int right, String[] words, int maxWidth) {
        if (right - left == 0) return padResult(words[left], maxWidth);

        boolean isLastLine = right == words.length - 1;
        int numSpaces = right - left;
        int totalSpace = maxWidth - wordsLength(left, right, words);

        String space = isLastLine ? " " : blank(totalSpace / numSpaces);
        int remainder = isLastLine ? 0 : totalSpace % numSpaces;

        StringBuilder result = new StringBuilder();
        for (int i = left; i <= right; i++)
            result.append(words[i])
            .append(space)
            .append(remainder-- > 0 ? " " : "");

        return padResult(result.toString().trim(), maxWidth);
    }

    //当前单词的长度
    private int wordsLength(int left, int right, String[] words) {
        int wordsLength = 0;
        for (int i = left; i <= right; i++) wordsLength += words[i].length();
        return wordsLength;
    }

    private String padResult(String result, int maxWidth) {
        return result + blank(maxWidth - result.length());
    }

    private String blank(int length) {
        return new String(new char[length]).replace('\0', ' ');
    }


=======================================================================================================
method 3:

method:

  - 
  - 看了下，发现思想和自己也是一样的。但是这个速度却打败了 100% ，0 ms。考虑了下，差别应该在我的算法里使用了一个叫做 
    row 的 list 用来保存当前行的单词，用了很多 row.get ( index )，而上边的算法只记录了 left 和 right 下标，
    取单词直接用的 words 数组。

    然后尝试着在我之前的算法上改了一下，去掉 row，用两个变量 start 和 end 保存当前行的单词范围。主要是 ( end - start ) 
    代替了之前的 row.size ( )， words [ start + k ] 代替了之前的 row.get ( k )

stats:

  - Runtime: 0 ms, faster than 100.00% of Java online submissions for Text Justification.
  - Memory Usage: 35.8 MB, less than 97.22% 

public List<String> fullJustify2(String[] words, int maxWidth) {
    List<String> ans = new ArrayList<>();
    int currentLen = 0;
    int start = 0;
    int end = 0;
    for (int i = 0; i < words.length;) {
        //判断加入该单词是否超过最长长度
        //分了两种情况，一种情况是加入第一个单词，不需要多加 1
        //已经有单词的话，再加入单词，需要多加个空格，所以多加了 1
        if (currentLen == 0 && currentLen + words[i].length() <= maxWidth
            || currentLen > 0 && currentLen + 1 + words[i].length() <= maxWidth) {
            end++;
            if (currentLen == 0) {
                currentLen = currentLen + words[i].length();
            } else {
                currentLen = currentLen + 1 + words[i].length();
            }
            i++;
        } else {
            int sub = maxWidth - currentLen + (end - start) - 1;
            if (end - start == 1) {
                String blank = getStringBlank(sub);
                ans.add(words[start] + blank);
            } else {
                StringBuilder temp = new StringBuilder();
                temp.append(words[start]);
                int averageBlank = sub / ((end - start) - 1);
                 //如果除不尽，计算剩余空格数
                int missing = sub - averageBlank * ((end - start) - 1);
                String blank = getStringBlank(averageBlank + 1);
                int k = 1;
                for (int j = 0; j < missing; j++) {
                    temp.append(blank + words[start+k]);
                    k++;
                }
                blank = getStringBlank(averageBlank);
                for (; k <(end - start); k++) {
                    temp.append(blank + words[start+k]);
                }
                ans.add(temp.toString());

            }
            start = end;
            currentLen = 0;

        }
    }
    StringBuilder temp = new StringBuilder();
    temp.append(words[start]);
    for (int i = 1; i < (end - start); i++) {
        temp.append(" " + words[start+i]);
    }
    temp.append(getStringBlank(maxWidth - currentLen));
    ans.add(temp.toString());
    return ans;
}
//得到 n 个空白
private String getStringBlank(int n) {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < n; i++) {
        str.append(" ");
    }
    return str.toString();
}


