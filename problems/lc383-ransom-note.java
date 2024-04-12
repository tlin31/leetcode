383. Ransom Note - Easy


Given an arbitrary ransom note string and another string containing letters from all the magazines, 
write a function that will return true if the ransom note can be constructed from the magazines ; 
otherwise, it will return false.

Each letter in the magazine string can only be used once in your ransom note.

Note:
You may assume that both strings contain only lowercase letters.

canConstruct("a", "b") -> false
canConstruct("aa", "ab") -> false
canConstruct("aa", "aab") -> true


******************************************************
key:
	- hashmap
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

	-	use string
	-	


public boolean canConstruct(String ransomNote, String magazine) {

    for (char c : ransomNote.toCharArray()) {

        int index = magazine.indexOf(c);

        if (index == -1) {
            return false;
        }

        magazine = magazine.substring(0, index) + magazine.substring(index + 1);
    }

    return true;
}





~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

def canConstruct(self, ransomNote: str, magazine: str) -> bool:
    
    for c in ransomNote:

        if c not in magazine:
            return False
        location = magazine.index(c)

        magazine = magazine[:location] + magazine[location + 1:]

    return True

=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	

		magazineCounts = makeCountsMap(magazine)
		for each char in ransomNote:
		    countInMagazine = magazineCounts.get(char)
		    if countInMagazine == 0:
		        return False
		    magazineCounts.put(char, countInMagazine - 1)
		return True



class Solution {
    

    
    
    public boolean canConstruct(String ransomNote, String magazine) {
        
        // Check for obvious fail case.
        if (ransomNote.length() > magazine.length()) {
            return false;
        }

        // Make a counts map for the magazine.
        Map<Character, Integer> magazineCounts = makeCountsMap(magazine);
        
        for (char c : ransomNote.toCharArray()) {

            int countInMagazine = magazineCounts.getOrDefault(c, 0);

            if (countInMagazine == 0) {
                return false;
            }

            magazineCounts.put(c, countInMagazine - 1);
        }
        
        return true;
    }

    private Map<Character, Integer> makeCountsMap(String s) {
        Map<Character, Integer> counts = new HashMap<>();
        for (char c : s.toCharArray()) {
            int currentCount = counts.getOrDefault(c, 0);
            counts.put(c, currentCount + 1);
        }
        return counts;
    }
}




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

def canConstruct(self, ransomNote: str, magazine: str) -> bool:
    
    if len(ransomNote) > len(magazine): 
    	return False


    letters = collections.Counter(magazine)
    
    for c in ransomNote:

        if letters[c] <= 0:
            return False

        letters[c] -= 1

    return True


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

