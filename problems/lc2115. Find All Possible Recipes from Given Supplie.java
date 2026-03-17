2115. Find All Possible Recipes from Given Supplies - Medium

You have information about n different recipes. You are given a string array recipes and a 2D string array ingredients. The ith recipe has the name recipes[i], and you can create it if you have all the needed ingredients from ingredients[i]. A recipe can also be an ingredient for other recipes, i.e., ingredients[i] may contain a string that is in recipes.

You are also given a string array supplies containing all the ingredients that you initially have, and you have an infinite supply of all of them.

Return a list of all the recipes that you can create. You may return the answer in any order.

Note that two recipes may contain each other in their ingredients.

 

Example 1:

Input: recipes = ["bread"], ingredients = [["yeast","flour"]], supplies = ["yeast","flour","corn"]
Output: ["bread"]
Explanation:
We can create "bread" since we have the ingredients "yeast" and "flour".
Example 2:

Input: recipes = ["bread","sandwich"], ingredients = [["yeast","flour"],["bread","meat"]], supplies = ["yeast","flour","meat"]
Output: ["bread","sandwich"]
Explanation:
We can create "bread" since we have the ingredients "yeast" and "flour".
We can create "sandwich" since we have the ingredient "meat" and can create the ingredient "bread".
Example 3:

Input: recipes = ["bread","sandwich","burger"], ingredients = [["yeast","flour"],["bread","meat"],["sandwich","meat","bread"]], supplies = ["yeast","flour","meat"]
Output: ["bread","sandwich","burger"]
Explanation:
We can create "bread" since we have the ingredients "yeast" and "flour".
We can create "sandwich" since we have the ingredient "meat" and can create the ingredient "bread".
We can create "burger" since we have the ingredient "meat" and can create the ingredients "bread" and "sandwich".
 

Constraints:

n == recipes.length == ingredients.length
1 <= n <= 100
1 <= ingredients[i].length, supplies.length <= 100
1 <= recipes[i].length, ingredients[i][j].length, supplies[k].length <= 10
recipes[i], ingredients[i][j], and supplies[k] consist only of lowercase English letters.
All the values of recipes and supplies combined are unique.
Each ingredients[i] does not contain any duplicate values.


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************


topological sorting:

统计每个 recipe 的 入度（还缺多少 ingredient）

建立 ingredient → recipe 的反向图

把所有 supplies 放入队列（初始可用）

BFS：

用一个 ingredient 解锁它依赖的 recipe

recipe 入度降到 0 → 可以被制作 → 加入队列

所有被解锁的 recipe 就是答案


class Solution {
    public List<String> findAllRecipes(
            String[] recipes,
            List<List<String>> ingredients,
            String[] supplies) {

        // ingredient -> list of recipes depending on it
        Map<String, List<String>> graph = new HashMap<>();

        // recipe -> remaining required ingredients
        Map<String, Integer> indegree = new HashMap<>();

        // 初始化入度
        for (int i = 0; i < recipes.length; i++) {
            indegree.put(recipes[i], ingredients.get(i).size());
            for (String ing : ingredients.get(i)) {
                graph.computeIfAbsent(ing, k -> new ArrayList<>())
                     .add(recipes[i]);
            }
        }

        // store list of supplies, based on supply, get ingredients, then calculate recipe
        Queue<String> q = new ArrayDeque<>();
        for (String s : supplies) {
            q.offer(s);
        }

        List<String> res = new ArrayList<>();

        while (!q.isEmpty()) {
            String cur = q.poll();
            if (!graph.containsKey(cur)) continue; // 如果没有由他组成的菜，跳过

            for (String recipe : graph.get(cur)) {
                indegree.put(recipe, indegree.get(recipe) - 1);
                if (indegree.get(recipe) == 0) {
                    res.add(recipe);
                    q.offer(recipe); // recipe 本身也可作为 ingredient
                }
            }
        }

        return res;
    }
}


例子：
    Supplies: ["flour", "yeast", "meat"]
    Recipes: ["bread", "burger"]
    Ingredients:
        Bread needs: ["flour", "yeast"]
        Burger needs: ["bread", "meat"]



Data Structure Snapshots (After Initialization)
    adj (Adjacency List)
    Maps an ingredient to the recipes that need it.  inverted index
    This allows us to "push" progress when an ingredient becomes available.
        "flour" → ["bread"]
        "yeast" → ["bread"]
        "meat" → ["burger"]
        "bread" → ["burger"]

    inDegree (Wait List)
    Maps a recipe to the count of ingredients it is still waiting for.
    "bread" : 2 (Waiting for flour and yeast)
    "burger": 2 (Waiting for bread and meat)


    queue (Available Items)
    Starts with your basic supplies.
    ["flour", "yeast", "meat"]


Step-by-Step Execution
Step    Item Pulled from Queue (u)  Action  i            nDegree    Update New to Queue?
1       "flour"              Look at adj["flour"]    bread: 2 → 1    No
2       "yeast"              Look at adj["yeast"]    bread: 1 → 0    Yes: "bread"
3       "meat"               Look at adj["meat"]     burger: 2 → 1   No
4       "bread"              Look at adj["bread"]    burger: 1 → 0   Yes: "burger"
5       "burger"             No recipes need burger  -                No


Final State
Result List: ["bread", "burger"]
inDegree: All values are 0.
queue: Empty.


===================================================================================================
Method 1:dfs

Method:

	-	



Stats:

	- 
	- 

class Solution {
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        List<String> ans = new ArrayList<>();
        Map<String, Integer> visited = new HashMap<>();
        Map<String, List<String>> recipeIngredients = new HashMap<>();
        Set<String> available = new HashSet<>(Arrays.asList(supplies));

        for(int i = 0; i<recipes.length; i++){
            recipeIngredients.put(recipes[i], ingredients.get(i));
        }

        for(String s:recipes){
            canMake(s, ans, recipeIngredients, visited, available);
        }

        return ans;
    }

    private boolean canMake(String s, List<String> ans, Map<String, List<String>> recipeIngredients, Map<String, Integer> visited, Set<String> available){
        if(visited.containsKey(s)){
            return visited.get(s) == 1;
        }

        if(available.contains(s)){
            return true;
        }

        if(!recipeIngredients.containsKey(s)){
            return false;
        }

        visited.put(s,0);

        for(String ingredient:recipeIngredients.get(s)){
            if(!canMake(ingredient, ans, recipeIngredients, visited, available)){
                visited.put(s,-1);
                return false;
            }
        }

        visited.put(s,1);
        ans.add(s);
        return true;

    }
}


