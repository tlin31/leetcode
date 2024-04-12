1125. Smallest Sufficient Team - Hard


In a project, you have a list of required skills req_skills, and a list of people.  The i-th person 
people[i] contains a list of skills that person has.

Consider a sufficient team: a set of people such that for every required skill in req_skills, there 
is at least one person in the team who has that skill.  We can represent these teams by the index 
of each person: for example, team = [0, 1, 3] represents the people with skills people[0], people[1], 
and people[3].

Return any sufficient team of the smallest possible size, represented by the index of each person.

You may return the answer in any order.  It is guaranteed an answer exists.

 

Example 1:

Input: req_skills = ["java","nodejs","reactjs"], people = [["java"],["nodejs"],["nodejs","reactjs"]]
Output: [0,2]


Example 2:

Input: req_skills = ["algorithms","math","java","reactjs","csharp","aws"], 
people = [["algorithms","math","java"],["algorithms","math","reactjs"],["java","csharp","aws"],
          ["reactjs","csharp"],["csharp","math"],["aws","java"]]
Output: [1,2]
 

Constraints:

1 <= req_skills.length <= 16
1 <= people.length <= 60
1 <= people[i].length, req_skills[i].length, people[i][j].length <= 16
Elements of req_skills and people[i] are (respectively) distinct.
req_skills[i][j], people[i][j][k] are lowercase English letters.
Every skill in people[i] is a skill in req_skills.
It is guaranteed a sufficient team exists.


******************************************************
key:
	- set cover problem --> backtrack, DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time  O(people * 2^skill)
	- Space O(2^skill)



Method:

	-	
	-	

dp[skill_set] is a sufficient team to cover the skill_set.
For each people, update his_skill with all current combinations of skill_set in dp.

optimization:
	- use bit to store state of skills, 16 bits used in total
	- use ansLen to do pruning, we just need to update ans if we got a shorter length
	- use dfs( int start_people) to do combination of people, the time complexity of people combination 
	    is O(2^n) instead of O(n^n)
	- hire people with current required skills, if we hire people[i], but the required skills doesnt 
	    change, we should avoid hiring this people
	- compare the relationship between people O(n^2) time complexity. If one people can cover all skills 
	     of another people, we should avoid hiring him. 
	     We can use a boolean[] array to record that information and do it before recursion.



class Solution {
    public int[] smallestSufficientTeam(String[] req_skills, List<List<String>> people) {
        int ns = req_skills.length, 
            np = people.size();
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < ns; ++i) 
        	map.put(req_skills[i], i);

        // store the already calculated people's skill 
        List<Integer>[] suff = new List[1 << ns];
        suff[0] = new ArrayList<>();

        for (int i = 0; i < np; ++i) {

        	// skill is the compiled skill list of person[i]
            int skill = 0;
            for (String s : people.get(i)) 
            	skill |= (1 << map.get(s));


            for (int prev = 0; prev < suff.length; ++prev) {
                if (suff[prev] == null) 
                	continue;

                // comb is the last person's skill + this person's skill
                int comb = prev | skill;

                // if this comb skillset is not seen or after adding the current person, skill set
                // is larger than before, meaning it's necessary to add this new person
                // prune the case of adding new person but not adding new skill
                if (suff[comb] == null || suff[prev].size() + 1 < suff[comb].size()) {
                    suff[comb] = new ArrayList<>(suff[prev]);
                    suff[comb].add(i);
                }
            }
        }

        List<Integer> res = suff[(1 << ns) - 1];
        int[] arr = new int[res.size()];
        for (int i = 0; i < arr.length; ++i) 
        	arr[i] = res.get(i);
        return arr;
    }
}


ex. Input: req_skills = ["java","nodejs","reactjs"], people = [["java"],["nodejs"],["nodejs","reactjs"]]

people index = 0
skill of this person = 1
prev skill = 0
comb = 1
--- at the end suff = [[], [0], null, null, null, null, null, null]
prev skill = 1
comb = 1
--- at the end suff = [[], [0], null, null, null, null, null, null]
prev skill = 2
prev skill = 3
prev skill = 4
prev skill = 5
prev skill = 6
prev skill = 7


people index = 1
skill of this person = 2
prev skill = 0
comb = 2
--- at the end suff = [[], [0], [1], null, null, null, null, null]
prev skill = 1
comb = 3
--- at the end suff = [[], [0], [1], [0, 1], null, null, null, null]
prev skill = 2
comb = 2
--- at the end suff = [[], [0], [1], [0, 1], null, null, null, null]
prev skill = 3
comb = 3
--- at the end suff = [[], [0], [1], [0, 1], null, null, null, null]
prev skill = 4
prev skill = 5
prev skill = 6
prev skill = 7


people index = 2
skill of this person = 6
prev skill = 0
comb = 6
--- at the end suff = [[], [0], [1], [0, 1], null, null, [2], null]
prev skill = 1
comb = 7
--- at the end suff = [[], [0], [1], [0, 1], null, null, [2], [0, 2]]
prev skill = 2
comb = 6
--- at the end suff = [[], [0], [1], [0, 1], null, null, [2], [0, 2]]
prev skill = 3
comb = 7
--- at the end suff = [[], [0], [1], [0, 1], null, null, [2], [0, 2]]
prev skill = 4
prev skill = 5
prev skill = 6
comb = 6
--- at the end suff = [[], [0], [1], [0, 1], null, null, [2], [0, 2]]
prev skill = 7
comb = 7
--- at the end suff = [[], [0], [1], [0, 1], null, null, [2], [0, 2]]

--- final: 
suff = [[], [0], [1], [0, 1], null, null, [2], [0, 2]]
res = [0, 2]




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def smallestSufficientTeam(self, req_skills, people):
        n, m = len(req_skills), len(people)
        key = {v: i for i, v in enumerate(req_skills)}
        dp = {0: []}
        for i, p in enumerate(people):
            his_skill = 0
            for skill in p:
                if skill in key:
                    his_skill |= 1 << key[skill]
            for skill_set, need in dp.items():
                with_him = skill_set | his_skill
                if with_him == skill_set: continue
                if with_him not in dp or len(dp[with_him]) > len(need) + 1:
                    dp[with_him] = need + [i]
        return dp[(1 << n) - 1]

=======================================================================================================
method 2:   DFS + backtracking

Stats:

	- 
	- 


Method:

	-	
	-	

Class Solution{
    int teamSize;
    HashSet<Integer> resTeam;
    public int[] smallestSufficientTeam(String[] req_skills, List<List<String>> people) {
        teamSize = people.size()+1;
        resTeam = new HashSet();
        HashMap<String,List<Integer>> skillMap = new HashMap();
        for(int i=0; i<people.size(); i++){
            for(String skill : people.get(i)){
                if(!skillMap.containsKey(skill)){
                    skillMap.put(skill, new ArrayList<Integer>());
                }
                skillMap.get(skill).add(i);
            }
        }
        find(skillMap, req_skills, 0, new HashSet());
        int[] res = new int[resTeam.size()];
        int index = 0;
        for(int person : resTeam){
            res[index++] = person;
        }
        return res;
    }
    
    public void find(HashMap<String,List<Integer>> skillMap, String[] req_skills, int cur, HashSet<Integer> team){
        if(team.size()>teamSize) return;
        if(cur==req_skills.length){
            if(team.size()<teamSize){
                teamSize = team.size();
                resTeam = new HashSet<Integer>();
                resTeam.addAll(team);
            }
            return;
        }
        for(int person : skillMap.get(req_skills[cur])){
            boolean isRemove = !team.contains(person);
            team.add(person);
            find(skillMap, req_skills, cur+1, team);
            if(isRemove) team.remove(person);
        }
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
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

