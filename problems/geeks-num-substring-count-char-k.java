Number of substrings with count of each character as k


https://www.geeksforgeeks.org/number-substrings-count-character-k/

Given a string and an integer k, find number of substrings in which all the different characters occurs exactly k times.

Examples:

Input : s = "aabbcc"
        k = 2 
Output : 6
The substrings are aa, bb, cc,
aabb, bbcc and aabbcc.

Input : s = "aabccc"
        k = 2
Output : 3
There are three substrings aa, 
cc and cc

******************************************************
key:
	- sliding window
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- The idea is to traverse through all substrings. We fix a starting point, traverse through all 
	substrings starring with the picked point, we keep incrementing frequencies of all characters. 
	- If all frequencies become k, we increment result. If count of any frequency becomes more than k, 
	we break and change starting point.

stats:

	- Time Complexity : O(n^2) where n is length of input string.

	- 

 
class GFG { 

	static int MAX_CHAR = 26; 

	// Returns true if all values in freq[] are either 0 or k. 
	static boolean check(int freq[], int k) 
	{ 
		for (int i = 0; i < MAX_CHAR; i++) 
			if (freq[i] != 0 && freq[i] != k) 
				return false; 
		return true; 
	} 

	// Returns count of substrings where frequency of every present character is k 
	static int substrings(String s, int k) 
	{ 
		int res = 0; // Initialize result 

		// Pick a starting point 
		for (int i = 0; i< s.length(); i++) 
		{ 

			// Initialize all frequencies as 0 for this starting point 
			int freq[] = new int[MAX_CHAR]; 

			// One by one pick ending points 
			for (int j = i; j<s.length(); j++) 
			{ 

				// Increment frequency of current char 
				int index = s.charAt(j) - 'a'; 
				freq[index]++; 

				// If frequency becomes more than k, we can't have more substrings starting with i 
				if (freq[index] > k) 
					break; 

				// If frequency becomes k, then check other frequencies as well. 
				else if (freq[index] == k && check(freq, k) == true) 
					res++; 
			} 
		} 
		return res; 
	} 

	// Driver code 
	public static void main(String[] args) 
	{ 
		String s = "aabbcc"; 
		int k = 2; 
		System.out.println(substrings(s, k)); 

		s = "aabbc"; 
		k = 2; 
		System.out.println(substrings(s, k)); 
	} 
} 
