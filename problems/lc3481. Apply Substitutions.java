3481. Apply Substitutions - Medium

You are given a replacements mapping and a text string that may contain placeholders formatted as %var%, where each var corresponds to a key in the replacements mapping. Each replacement value may itself contain one or more such placeholders. Each placeholder is replaced by the value associated with its corresponding replacement key.

Return the fully substituted text string which does not contain any placeholders.



Example 1:

Input: replacements = [["A","abc"],["B","def"]], text = "%A%_%B%"

Output: "abc_def"

Explanation:

The mapping associates "A" with "abc" and "B" with "def".
Replace %A% with "abc" and %B% with "def" in the text.
The final text becomes "abc_def".
Example 2:

Input: replacements = [["A","bce"],["B","ace"],["C","abc%B%"]], text = "%A%_%B%_%C%"

Output: "bce_ace_abcace"

Explanation:

The mapping associates "A" with "bce", "B" with "ace", and "C" with "abc%B%".
Replace %A% with "bce" and %B% with "ace" in the text.
Then, for %C%, substitute %B% in "abc%B%" with "ace" to obtain "abcace".
The final text becomes "bce_ace_abcace".
 

Constraints:

1 <= replacements.length <= 10
Each element of replacements is a two-element list [key, value], where:
key is a single uppercase English letter.
value is a non-empty string of at most 8 characters that may contain zero or more placeholders formatted as %<key>%.
All replacement keys are unique.
The text string is formed by concatenating all key placeholders (formatted as %<key>%) randomly from the replacements mapping, separated by underscores.
text.length == 4 * replacements.length - 1
Every placeholder in the text or in any replacement value corresponds to a key in the replacements mapping.
There are no cyclic dependencies between replacement keys.


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Hashmap


class Solution {
    public String applySubstitutions(List<List<String>> replacements, String text) {
        Map<String, String> reps = new HashMap<>();
        for(int i=0; i<replacements.size(); i++) {
            reps.put(replacements.get(i).get(0), replacements.get(i).get(1));
        }

        while(text.contains("%")) {
            StringBuilder output = new StringBuilder();
            String[] split = text.split("%");
            for(int i=0; i<split.length; i++) {
                if(i%2==0) {
                    output.append(split[i]);
                } else {
                    output.append(reps.get(split[i]));
                }
            }
            text = output.toString();
        };

        return text;
    }
}


===================================================================================================

Method 1:

Build a graph keeping in mind the dependencies.
For example, here ["C","abc%B%"] C has a dependency on B.
So B will have an outward edge to C.
Do the topological sort. One the indegree of a key becomes 0, it means now the existing keys can be used to evaluate that key.


Stats:

	- 
	- 

class Solution {
    public String applySubstitutions(List<List<String>> replacements, String text) {
        
        HashMap<String, String> replacementMap = new HashMap<>();

        Map<String, List<String>> graph = new HashMap<>();

        Map<String, Integer> indegreeMap = new HashMap<>();

        for (List<String>replacement : replacements){
            String key = replacement.get(0);
            String value = replacement.get(1);

            int i = 0;
            graph.putIfAbsent(key, new ArrayList<>());
            indegreeMap.put(key, 0);
            while (i< value.length()){
                if (value.charAt(i) != '%'){
                    i += 1;
                }
                else{
                    // collecting all the dependecies. for eg. abc%B%de%A%
                    i += 1;
                    String neighborKey = String.valueOf(value.charAt(i));
                    graph.putIfAbsent(neighborKey, new ArrayList<>());
                    graph.get(neighborKey).add(key);
                    indegreeMap.put(key, indegreeMap.get(key)+1);
                    i += 2;
                    
                }
            }
            replacementMap.put(key, value);
        }

        Queue<String> q = new LinkedList<>();

        for (String key : indegreeMap.keySet()){
            if (indegreeMap.get(key) == 0){
                q.add(key);
            }
        }

        while (q.size() > 0){
            String curr = q.poll();

            StringBuilder sb = new StringBuilder();
            String currRes = replacementMap.get(curr);

            int i = 0;
            while (i < currRes.length()){
                if (currRes.charAt(i) != '%'){
                    sb.append(currRes.charAt(i));
                    i += 1;
                }else{
                    // getting the values of all the dependencies for the curr key.
                    i += 1;
                    sb.append(replacementMap.get(String.valueOf(currRes.charAt(i))));
                    i += 2;
                }
            }
            replacementMap.put(curr, sb.toString());

            for (String neighbor : graph.get(curr)){
                indegreeMap.put(neighbor, indegreeMap.get(neighbor)-1);
                if (indegreeMap.get(neighbor) == 0){
                    q.add(neighbor);
                }
            }
        }

        StringBuilder ans = new StringBuilder();

        int i = 0;
        while (i< text.length()){
            if (text.charAt(i) != '%'){
                ans.append(text.charAt(i));
                i += 1;
            }else{
                i += 1;
                String key = String.valueOf(text.charAt(i));
                ans.append(replacementMap.get(key));
                i += 2;
            }
        }

        return ans.toString();
    }
}


