412. Fizz Buzz - Easy

Write a program that outputs the string representation of numbers from 1 to n.

But for multiples of three it should output “Fizz” instead of the number and for the multiples of 
five output “Buzz”. For numbers which are multiples of both three and five output “FizzBuzz”.

Example:

n = 15,

Return:
[
    "1",
    "2",
    "Fizz",
    "4",
    "Buzz",
    "Fizz",
    "7",
    "8",
    "Fizz",
    "Buzz",
    "11",
    "Fizz",
    "13",
    "14",
    "FizzBuzz"
]


******************************************************
key:
    - 
    - edge case:
        1) empty string, return empty
        2)

******************************************************



=======================================================================================================
Method 1:


Stats:

    - 
    - 


Method:
    
    -  If the number is divisible, concatenate the corresponding string mapping Fizz or Buzz to 
       the current answer string.

        For eg. If we are checking for the number 15, the steps would be:

            Condition 1: 15 % 3 == 0 , num_ans_str = "Fizz"
            Condition 2: 15 % 5 == 0 , num_ans_str += "Buzz"
            => num_ans_str = "FizzBuzz"

        Similarly, for FizzBuzzJazz now we would just have three conditions to check for divisibility.
    -   

class Solution {
  public List<String> fizzBuzz(int n) {
    // ans list
    List<String> ans = new ArrayList<String>();

    for (int num = 1; num <= n; num++) {

      boolean divisibleBy3 = (num % 3 == 0);
      boolean divisibleBy5 = (num % 5 == 0);

      String numAnsStr = "";

      if (divisibleBy3) {
        // Divides by 3, add Fizz
        numAnsStr += "Fizz";
      }

      if (divisibleBy5) {
        // Divides by 5, add Buzz
        numAnsStr += "Buzz";
      }

      if (numAnsStr.equals("")) {
        // Not divisible by 3 or 5, add the number
        numAnsStr += Integer.toString(num);
      }

      // Append the current answer str to the ans list
      ans.add(numAnsStr);
    }

    return ans;
  }
}

=======================================================================================================
method 2: Hashmsp

Stats:

    - 
    - 


Method:
    - when there's too many mappings, want to put it in an iterable way
    - Put all the mappings in a hash table. The hash table fizzBuzzHash would look something 
        like { 3: 'Fizz', 5: 'Buzz' }
      Iterate on the numbers from 1 ... N
      For every number, iterate over the fizzBuzzHash keys and check for divisibility.
      If the number is divisible by the key, concatenate the corresponding hash value to the 
         answer string for current number. We do this for every entry in the hash table.
      Add the answer string to the answer list.
    -   

class Solution {
  public List<String> fizzBuzz(int n) {

    // ans list
    List<String> ans = new ArrayList<String>();

    // Hash map to store all fizzbuzz mappings.
    HashMap<Integer, String> fizzBizzDict =
        new HashMap<Integer, String>() {
          {
            put(3, "Fizz");
            put(5, "Buzz");
          }
        };

    for (int num = 1; num <= n; num++) {

      String numAnsStr = "";

      for (Integer key : fizzBizzDict.keySet()) {

        // If the num is divisible by key, then add the corresponding string mapping to 
        // current numAnsStr
        if (num % key == 0) {
          numAnsStr += fizzBizzDict.get(key);
        }
      }

      if (numAnsStr.equals("")) {
        // Not divisible by 3 or 5, add the number
        numAnsStr += Integer.toString(num);
      }

      // Append the current answer str to the ans list
      ans.add(numAnsStr);
    }

    return ans;
  }
}

=======================================================================================================
method 3:

Stats:

    - 
    - 


Method:

    -   
    -   



