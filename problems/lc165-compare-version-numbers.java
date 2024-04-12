165. Compare Version Numbers - Medium


Compare two version numbers version1 and version2.
If version1 > version2 return 1; if version1 < version2 return -1;otherwise return 0.

You may assume that the version strings are non-empty and contain only digits and the . character.

The . character does not represent a decimal point and is used to separate number sequences.

For instance, 2.5 is not "two and a half" or "half way to version three", it is the fifth second-level 
revision of the second first-level revision.

You may assume the default revision number for each level of a version number to be 0. For example, 
version number 3.4 has a revision number of 3 and 4 for its first and second level revision number. Its 
third and fourth level revision number are both 0.

 

Example 1:

Input: version1 = "0.1", version2 = "1.1"
Output: -1
Example 2:

Input: version1 = "1.0.1", version2 = "1"
Output: 1
Example 3:

Input: version1 = "7.5.2.4", version2 = "7.5.3"
Output: -1
Example 4:

Input: version1 = "1.01", version2 = "1.001"
Output: 0
Explanation: Ignoring leading zeroes, both “01” and '001' represent the same number “1”


Example 5:

Input: version1 = "1.0", version2 = "1.0.0"
Output: 0
Explanation: The first version number does not have a third level revision number, which means 
its third level revision number is default to "0"
 

Note:

Version strings are composed of numeric strings separated by dots . and this numeric strings may 
have leading zeroes.
Version strings do not start or end with dots, and they will not be two consecutive dots.


******************************************************
key:
	- 2 pointers
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:  Split + Parse, Two Pass, Linear Space


Stats:

	- Time complexity : O(N+M+max(N,M)), where N and M are lengths of input strings.


	- Space complexity : O(N+M) to store arrays nums1 and nums2.



Method:

	-	
	-	Split both strings by dot character into two arrays.

		Iterate over the longest array and compare chunks one by one. 
		If one of the arrays is over, virtually add as many zeros as needed to continue the 
		comparison with the longer array.

		If two chunks are not equal, return 1 or -1.
		If we are here, the versions are equal. Return 0.


class Solution {
  public int compareVersion(String version1, String version2) {
    String[] nums1 = version1.split("\\.");
    String[] nums2 = version2.split("\\.");
    int n1 = nums1.length, n2 = nums2.length;

    // compare versions
    int i1, i2;
    for (int i = 0; i < Math.max(n1, n2); ++i) {
      i1 = i < n1 ? Integer.parseInt(nums1[i]) : 0;
      i2 = i < n2 ? Integer.parseInt(nums2[i]) : 0;
      if (i1 != i2) {
        return i1 > i2 ? 1 : -1;
      }
    }
    // the versions are equal
    return 0;
  }
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def compareVersion(self, version1: str, version2: str) -> int:
        nums1 = version1.split('.')
        nums2 = version2.split('.')
        n1, n2 = len(nums1), len(nums2)
        
        # compare versions
        for i in range(max(n1, n2)):
            i1 = int(nums1[i]) if i < n1 else 0
            i2 = int(nums2[i]) if i < n2 else 0
            if i1 != i2:
                return 1 if i1 > i2 else -1
        
        # the versions are equal
        return 0 


=======================================================================================================
method 2: Two Pointers, One Pass, Constant Space


Stats:

	- Time complexity : O(max(N,M)) It's a one-pass solution.

	- Space complexity : O(1), since we don't use any additional data structure.



Method:

	-	
	-	


public int compareVersion(String version1, String version2) {
    int temp1 = 0,temp2 = 0;
    int len1 = version1.length(),len2 = version2.length();
    int i = 0, j = 0;
    while(i<len1 || j<len2) {
        temp1 = 0;
        temp2 = 0;
        while(i<len1 && version1.charAt(i) != '.') {
            temp1 = temp1*10 + version1.charAt(i++)-'0';
            
        }
        while(j<len2 && version2.charAt(j) != '.') {
            temp2 = temp2*10 + version2.charAt(j++)-'0';
            
        }
        if(temp1>temp2) 
        	return 1;
        else if(temp1<temp2) 
        	return -1;
        else {
            i++;
            j++;
            
        }
        
    }
    return 0;
    
}

~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
    def compareVersion(self, version1: str, version2: str) -> int:
        def getnext(s):
            st, l = 0, len(s)
            for i in range(l):
                if s[i] == '.':
                    yield s[st:i]
                    st = i+1
            yield s[st: l]
            
        for i, j in itertools.zip_longest(getnext(version1), getnext(version2)):
            a, b = int(i) if i else 0, int(j) if j else 0
            if a > b: return 1
            elif a < b: return -1
        return 0

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

