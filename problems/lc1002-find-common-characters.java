1002. Find Common Characters - Easy


Given an array A of strings made only from lowercase letters, return a list of all characters that 
show up in all strings within the list (including duplicates).  For example, if a character occurs 
3 times in all strings but not 4 times, you need to include that character three times in the 
final answer.

You may return the answer in any order.

 

Example 1:

Input: ["bella","label","roller"]
Output: ["e","l","l"]

Example 2:

Input: ["cool","lock","cook"]
Output: ["c","o"]
 

Note:

1 <= A.length <= 100
1 <= A[i].length <= 100
A[i][j] is a lowercase letter


******************************************************
key:
	- count map
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: count map


Stats:

	- 
	- 


Method:

	-	the common dict stores the minimum # of char

	public List<String> commonChars(String[] A) {
        List<String> ans = new ArrayList<>();
        // Common characters dictionary
        int[] dict = new int[26];

        // first string
        for (int j = 0; j < A[0].length(); j++) {
            dict[A[0].charAt(j) - 'a']++;
        }

        for (int i = 1; i < A.length; i++) {
            
            // Dictionary of the current word
            int[] curr = new int[26];
            for (int j = 0; j < A[i].length(); j++) {
                curr[A[i].charAt(j) - 'a']++;
            }

            // Update the common dictionary
            for (int j = 0; j < 26; j++) {
                if (curr[j] < dict[j]) 
                	dict[j] = curr[j];
            }
        }

        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < dict[i]; j++) {
                ans.add(Character.toString((char) ('a' + i)));
            }
        }
        return ans;
    }




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def commonChars(self, A: List[str]) -> List[str]:
        cnt = collections.Counter(A[0])
        for s in A:
            cnt2 = collections.Counter(s)
            for k in cnt.keys():
                cnt[k] = min(cnt[k], cnt2[k])
        return cnt.elements()


 def commonChars(self, A):
        res = collections.Counter(A[0])
        for a in A:
            res &= collections.Counter(a)
        return list(res.elements())


 def commonChars(self, A):
        return list(reduce(collections.Counter.__and__, map(collections.Counter, A)).elements())


=======================================================================================================
method 2: hashmap 

Stats:

	- 
	- 


Method:

	-	


public static String[] commonChars (String[] A){
	// ["cool","loock","cook"]
	if ((A.length)<1){
	throw new IllegalArgumentException("not enough strings to compare");
	}

    List<Character> result = new ArrayList<>();
    HashMap<Character, Integer> commonHM = new HashMap<>();
    for (Character c : A[0].toCharArray()){
        commonHM.put(c, commonHM.getOrDefault(c,0)+1);
    }

    for (int i=1; i<A.length; i++) {
        HashMap<Character, Integer> currentHM = new HashMap<>();
        for (Character c : A[i].toCharArray()) {
            currentHM.put(c, currentHM.getOrDefault(c, 0) + 1);
        }
        for (Character c : A[i].toCharArray()) {
            if ((commonHM.containsKey(c)) && ((currentHM).get(c) < commonHM.get(c))) {
                commonHM.put(c, currentHM.get(c));
            }
        }
    }

    System.out.println(" the common hashmap is " +commonHM);
    for (Character x : commonHM.keySet()){
        while (commonHM.get(x)!=0){
            result.add(x);
            commonHM.put(x,commonHM.get(x)-1);
        }
    }
    return new String[]{result.toString()};
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

