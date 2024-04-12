1096. Brace Expansion II - Hard



Under a grammar given below, strings can represent a set of lowercase words.  Lets use R(expr) 
to denote the set of words the expression represents.

Grammar can best be understood through simple examples:

Single letters represent a singleton set containing that word.
	R("a") = {"a"}
	R("w") = {"w"}

When we take a comma delimited list of 2 or more expressions, we take the union of possibilities.
	R("{a,b,c}") = {"a","b","c"}
	R("{{a,b},{b,c}}") = {"a","b","c"} (notice the final set only contains each word at most once)

When we concatenate two expressions, we take the set of possible concatenations between two words 
where the first word comes from the first expression and the second word comes from the second 
expression.

	R("{a,b}{c,d}") = {"ac","ad","bc","bd"}
	R("a{b,c}{d,e}f{g,h}") = {"abdfg", "abdfh", "abefg", "abefh", "acdfg", "acdfh", "acefg", "acefh"}

Formally, the 3 rules for our grammar:

	For every lowercase letter x, we have R(x) = {x}
	For expressions e_1, e_2, ... , e_k with k >= 2, we have R({e_1,e_2,...}) = R(e_1) ∪ R(e_2) ∪ ...
	For expressions e_1 and e_2, we have R(e_1 + e_2) = {a + b for (a, b) in R(e_1) × R(e_2)}, 
	    where + denotes concatenation, and × denotes the cartesian product.

Given an expression representing a set of words under the given grammar, return the sorted list of 
words that the expression represents.

 

Example 1:

Input: "{a,b}{c,{d,e}}"
Output: ["ac","ad","ae","bc","bd","be"]
Example 2:

Input: "{{a,z},a{b,c},{ab,z}}"
Output: ["a","ab","ac","z"]
Explanation: Each distinct word is written only once in the final answer.
 

Constraints:

1 <= expression.length <= 60
expression[i] consists of '{', '}', ','or lowercase English letters.
The given expression represents a set of words based on the grammar given in the description.


******************************************************
key:
	- case by case, redo
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

	-  Split expressions into groups separated by top level ','; for each top-level sub expression 
	   (substrings with braces), process it and add it to the corresponding group; finally combine 
	   the groups and sort.

		maintain a list of groups separated by top level ','
			when meets ',': create a new empty group as the current group
			when meets '{': check whether current level is 0, if so, record the starting index of 
			                the sub expression; always increase level by 1, no matter whether current 
			                level is 0
			when meets '}': decrease level by 1; then check whether current level is 0, if so, 
							recursively call braceExpansionII to get the set of words from expresion
							[start: end], where end is the current index (exclusive).
							& add the expanded word set to the current group

			when meets a letter: check whether the current level is 0, if so, add [letter] to the 
								  current group
			base case: when there is no brace in the expression.

		finally, after processing all sub expressions and collect all groups (seperated by ','), we 
			initialize an empty word_set, and then iterate through all groups
		
			for each group, find the product of the elements inside this group
			compute the union of all groups
		
		sort and return
		
		note that itertools.product(*group) returns an iterator of tuples of characters, so we 
			use''.join() to convert them to strings


class Solution {
    public List<String> braceExpansionII(String expression) {
        List<List<List<String>>> groups = new ArrayList<>();
        groups.add(new ArrayList<>());
        int level = 0;
        int start = -1, end = -1;
        
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '{') {
                if (level == 0) {
                    start = i + 1;
                }
                level++;
            } else if (c == '}') {
                level--;
                if (level == 0) {
                    String subExpression = expression.substring(start, i);
                    List<String> subRes = braceExpansionII(subExpression);
                    groups.get(groups.size() - 1).add(subRes);
                }
            } else if (c == ',' && level == 0) {
                groups.add(new ArrayList<>());
            } else if (level == 0) {
                groups.get(groups.size() - 1).add(Arrays.asList(String.valueOf(c)));
            }
        }

        Set<String> set = new HashSet<>();
        for (List<List<String>> group : groups) {
            List<String> pre = new ArrayList<>();
            pre.add("");
            for (List<String> g : group) {
                List<String> tmp = new ArrayList<>();
                for (String pStr : pre) {
                    for (String gStr : g) {
                        tmp.add(pStr + gStr);
                    }
                }
                pre = tmp;
            }
            set.addAll(pre);
        }
        
        List<String> res = new ArrayList<>(set);
        Collections.sort(res);
        
        return res;
    }
}










~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def braceExpansionII(self, expression: str) -> List[str]:
        groups = [[]]
        level = 0
        for i, c in enumerate(expression):
            if c == '{':
                if level == 0:
                    start = i+1
                level += 1
            elif c == '}':
                level -= 1
                if level == 0:
                    groups[-1].append(self.braceExpansionII(expression[start:i]))
            elif c == ',' and level == 0:
                groups.append([])
            elif level == 0:
                groups[-1].append([c])
        word_set = set()
        for group in groups:
            word_set |= set(map(''.join, itertools.product(*group)))
        return sorted(word_set)


=======================================================================================================
method 2: iterative dfs

Stats:

	- 
	- 


Method:

	-	


class Solution {
    public List<String> braceExpansionII(String expression) {
        Queue<String> queue = new LinkedList<>();
        queue.offer(expression);
        Set<String> set = new HashSet<>();
        
        while (!queue.isEmpty()) {
            String str = queue.poll();
            
            if (str.indexOf('{') == -1) {
                set.add(str);
                continue;
            }
            
            int i = 0, l = 0, r = 0;
            while (str.charAt(i) != '}') {
                if (str.charAt(i) == '{') 
                    l = i; 
                i++;
            }
            r = i;
            
            String before = str.substring(0, l);
            String after = str.substring(r+1);
            String[] strs = str.substring(l+1, r).split(",");
            
            StringBuilder sb = new StringBuilder();
            for (String ss : strs) {
                sb.setLength(0);
                queue.offer(sb.append(before).append(ss).append(after).toString());
            }
        }
        
        List<String> ans = new ArrayList<>(set);
        Collections.sort(ans);
        return ans;
    }
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

