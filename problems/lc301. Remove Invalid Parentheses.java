301. Remove Invalid Parentheses - Hard

Given a string s that contains parentheses and letters, remove the minimum number of invalid parentheses to make the input string valid.

Return a list of unique strings that are valid with the minimum number of removals. You may return the answer in any order.

 

Example 1:

Input: s = "()())()"
Output: ["(())()","()()()"]
Example 2:

Input: s = "(a)())()"
Output: ["(a())()","(a)()()"]
Example 3:

Input: s = ")("
Output: [""]
 

Constraints:

1 <= s.length <= 25
s consists of lowercase English letters and parentheses '(' and ')'.
There will be at most 20 parentheses in s.


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************


BFS


def removeInvalidParentheses(s: str) -> list[str]:
    def is_valid(string):
        count = 0
        for char in string:
            if char == '(': count += 1
            elif char == ')':
                count -= 1
                if count < 0: return False
        return count == 0

    level = {s}
    while True:
        # Check current level for valid strings
        valid = list(filter(is_valid, level))
        if valid:
            return valid
        
        # Generate next level by removing one bracket from each string
        next_level = set()
        for string in level:
            for i in range(len(string)):
                if string[i] in '()':
                    next_level.add(string[:i] + string[i+1:])

        level = next_level
        if not level: return [""]
        

public List<String> removeInvalidParentheses(String s) {
    List<String> res = new ArrayList<>();
    if (s == null) return res;

    Set<String> visited = new HashSet<>();
    Queue<String> queue = new LinkedList<>();

    queue.add(s);
    visited.add(s);

    boolean found = false;

    while (!queue.isEmpty()) {
        int size = queue.size();
        // Process current level
        for (int i = 0; i < size; i++) {
            String cur = queue.poll();

            if (isValid(cur)) {
                res.add(cur);
                found = true;
            }

            if (found) continue; // If found, don't generate next level from this string

            // Generate next level: try removing each bracket once
            for (int j = 0; j < cur.length(); j++) {
                if (cur.charAt(j) != '(' && cur.charAt(j) != ')') continue;

                // Equivalent to Python's cur[:j] + cur[j+1:]
                String next = cur.substring(0, j) + cur.substring(j + 1);

                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.add(next);
                }
            }
        }
        if (found) break; // Found min removals, stop BFS
    }
    return res;
}


===================================================================================================
Method 1:

Method:

	-	



Stats:

	- time: 2^N
	- space: N

DFS 
public List<String> removeInvalidParentheses(String s) {
    int leftRemove = 0, rightRemove = 0;
    // 1. Calculate min removals needed
    for (char c : s.toCharArray()) {
        if (c == '(') leftRemove++;
        else if (c == ')') {
            if (leftRemove > 0) leftRemove--;
            else rightRemove++;
        }
    }
    Set<String> res = new HashSet<>();
    dfs(s, 0, 0, 0, leftRemove, rightRemove, new StringBuilder(), res);
    return new ArrayList<>(res);
}

private void dfs(String s, int index, int leftCount, int rightCount, int lRem, int rRem, StringBuilder path, Set<String> res) {
    if (index == s.length()) {
        if (lRem == 0 && rRem == 0) res.add(path.toString());
        return;
    }
    char c = s.charAt(index);
    int len = path.length();

    // Option 1: Remove the character (if it's a bracket and we still need to remove)
    if (c == '(' && lRem > 0) dfs(s, index + 1, leftCount, rightCount, lRem - 1, rRem, path, res);
    if (c == ')' && rRem > 0) dfs(s, index + 1, leftCount, rightCount, lRem, rRem - 1, path, res);

    // Option 2: Keep the character
    path.append(c);
    if (c != '(' && c != ')') {
        dfs(s, index + 1, leftCount, rightCount, lRem, rRem, path, res);
    } 
    else if (c == '(') {
        dfs(s, index + 1, leftCount + 1, rightCount, lRem, rRem, path, res);
    } 
    else if (rightCount < leftCount) { // Only keep ')' if it doesn't break validity, Pruning
        dfs(s, index + 1, leftCount, rightCount + 1, lRem, rRem, path, res);
    }
    path.setLength(len); // Backtrack
}


The Purpose of leftCount and rightCount
These two variables track the balance of the parentheses currently being built in the path (the "Keep" branch). They serve two vital roles in industry-grade recursion:
1. Preventing Invalid Prefixes (Pruning)
A string can only be valid if, at any point from left to right, the number of closing brackets ) does not exceed the number of opening brackets (.
The Constraint: We only allow the DFS to "keep" a ) if rightCount < leftCount.
Why? If we added a ) when they were already equal (e.g., path is ()), the string would become ()). No matter what we add later, this string is "permanently broken." By checking this now, we prune thousands of invalid recursive branches early.


