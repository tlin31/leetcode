17. Letter Combinations of a Phone Number --- Medium

Given a string containing digits from 2-9 inclusive, return all possible letter combinations 
that the number could represent.

A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 
1 does not map to any letters.



Example:

Input: "23"
Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
Note:

Although the above answer is in lexicographical order, your answer could be in any order you want.
=========================================================================================================================================================
method 1:

key:
    - backtrack

Statistics:
    - Time complexity : O(3^N * 4^M) where N is the number of digits in the input that
                        maps to 3 letters (e.g. 2, 3, 4, 5, 6, 8),
                        M is the number of digits in the input that maps to 4 letters 
                        (e.g. 7, 9), and N+M is the total number digits in the input.

Space complexity : O(3^N * 4^M)


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


}


=========================================================================================================================================================
method 2:

key:
	- use of ans.peek().length --> don't need another var
	- use of str a + str b --> dot product!!!

Runtime: 1 ms, faster than 65.50% 
Memory Usage: 36 MB, less than 98.63%

  public List < String > letterCombinations(String digits) {
    LinkedList < String > ans = new LinkedList < String > ();

      //edge case
    if (digits.length()==0){
  		return ans;
  	}

      String[] mapping = new String[] {
          "0",
          "1",
          "abc",
          "def",
          "ghi",
          "jkl",
          "mno",
          "pqrs",
          "tuv",
          "wxyz"
      };
      ans.add("");

      for (int i = 0; i < digits.length(); i++) {
          int x = Character.getNumericValue(digits.charAt(i));

          while (ans.peek().length() == i) {
              String t = ans.remove();
              for (char s: mapping[x].toCharArray())
                  ans.add(t + s);
          }
      }
      return ans;
  }



