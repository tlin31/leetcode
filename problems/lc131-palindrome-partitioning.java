131. Palindrome Partitioning - Medium

Given a string s, partition s such that every substring of the partition is a palindrome.

Return all possible palindrome partitioning of s.

Example:

Input: "aab"
Output:
[
  ["aa","b"],
  ["a","a","b"]
]

******************************************************
key:
   - backtrack
   - edge case:
      1) empty string - return empty
      2)

******************************************************



=======================================================================================================
method 1:

method:

   - backtrack
   - 

stats:
   - Runtime: 2 ms, faster than 97.26% of Java online submissions for Palindrome Partitioning.
   - Memory Usage: 38.4 MB, less than 100.00% 

   public List<List<String>> partition(String s) {
      List<List<String>> list = new ArrayList<>();
      backtrack(list, new ArrayList<>(), s, 0);
      return list;
   }

   public void backtrack(List<List<String>> list, List<String> tempList, String s, int start){
      
      // reach the end, return what's stored in temp list
      if(start == s.length())
         list.add(new ArrayList<>(tempList));

      else{
         for(int i = start; i < s.length(); i++){

            //condition, 从start到i这个substring is palindrome
            if(isPalindrome(s, start, i)){

               tempList.add(s.substring(start, i + 1));
               backtrack(list, tempList, s, i + 1);
               tempList.remove(tempList.size() - 1);
            }
         }
      }
   }

   public boolean isPalindrome(String s, int low, int high){
      while(low < high)
         if(s.charAt(low++) != s.charAt(high--)) 
            return false;
      return true;
   } 


=======================================================================================================
method 2:

method:

   - dp
   - Here the pair is to mark a range for the substring is a Palindrome. if pair[i][j] is true, that
     means sub string from i to j is palindrome
   - The result[i], is to store from beginng until current index i (Non inclusive), all possible 
     partitions. From the past result we can determine current result
   

stats:
   - Runtime: 2 ms, faster than 97.26% 
   - Memory Usage: 39 MB, less than 97.73% 

   public class Solution {
       public static List < List < String >> partition(String s) {
           int len = s.length();
           List < List < String >> [] result = new List[len + 1];

           result[0] = new ArrayList < List < String >> ();
           result[0].add(new ArrayList < String > ());
           boolean[][] pair = new boolean[len][len];
           
           for (int i = 0; i < s.length(); i++) {
               result[i + 1] = new ArrayList < List < String >> ();

               //check whether it's a palindrome --> from 2 end, it's equal
               for (int left = 0; left <= i; left++) {

                  if (s.charAt(left) == s.charAt(i) && (i - left <= 1 || pair[left + 1][i - 1])) {
                       pair[left][i] = true;
                       String str = s.substring(left, i + 1);
                       for (List < String > r: result[left]) {
                        
                           // add the newly found palindrome str to every previous list
                           List < String > ri = new ArrayList < String > (r);
                           ri.add(str);
                           result[i + 1].add(ri);
                       }
                   }
               }
           }
           return result[len];
       }
   }




=======================================================================================================
method 3:

method:

   - 
   - 

stats:

   - 
   - Runtime: 2 ms, faster than 97.26% of Java online submissions for Palindrome Partitioning.
Memory Usage: 38.9 MB, less than 97.73%

   public class Solution {
       List < List < String >> resultLst;
       ArrayList < String > currLst;

       public List < List < String >> partition(String s) {
           resultLst = new ArrayList < List < String >> ();
           currLst = new ArrayList < String > ();
           backTrack(s, 0);
           return resultLst;
       }
       
       public void backTrack(String s, int l) {
           if (currLst.size() > 0 //the initial str could be palindrome
               &&
               l >= s.length()) {
               List < String > r = (ArrayList < String > ) currLst.clone();
               resultLst.add(r);
           }
           for (int i = l; i < s.length(); i++) {
               if (isPalindrome(s, l, i)) {
                   if (l == i)
                       currLst.add(Character.toString(s.charAt(i)));
                   else
                       currLst.add(s.substring(l, i + 1));
                   backTrack(s, i + 1);
                   currLst.remove(currLst.size() - 1);
               }
           }
       }
       public boolean isPalindrome(String str, int l, int r) {
           if (l == r) return true;
           while (l < r) {
               if (str.charAt(l) != str.charAt(r)) return false;
               l++;
               r--;
           }
           return true;
       }
   }
