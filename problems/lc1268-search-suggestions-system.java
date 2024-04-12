1268. Search Suggestions System - Medium

Given an array of strings products and a string searchWord. We want to design a system that 
suggests at most 3 product names from products after each character of searchWord is typed.
Suggested products should have common prefix with the searchWord. If there are more than three 
products with a common prefix return the three lexicographically minimums products.

Return list of lists of the suggested products after each character of searchWord is typed. 

 

Example 1:

Input: products = ["mobile","mouse","moneypot","monitor","mousepad"], searchWord = "mouse"
Output: [
["mobile","moneypot","monitor"],
["mobile","moneypot","monitor"],
["mouse","mousepad"],
["mouse","mousepad"],
["mouse","mousepad"]
]
Explanation: products sorted lexicographically = ["mobile","moneypot","monitor","mouse","mousepad"]
After typing m and mo all products match and we show user ["mobile","moneypot","monitor"]
After typing mou, mous and mouse the system suggests ["mouse","mousepad"]
Example 2:

Input: products = ["havana"], searchWord = "havana"
Output: [["havana"],["havana"],["havana"],["havana"],["havana"],["havana"]]
Example 3:

Input: products = ["bags","baggage","banner","box","cloths"], searchWord = "bags"
Output: [["baggage","bags","banner"],["baggage","bags","banner"],["baggage","bags"],["bags"]]
Example 4:

Input: products = ["havana"], searchWord = "tatiana"
Output: [[],[],[],[],[],[],[]]
 

Constraints:

1 <= products.length <= 1000
There are no repeated elements in products.
1 <= Σ products[i].length <= 2 * 10^4
All characters of products[i] are lower-case English letters.
1 <= searchWord.length <= 1000
All characters of searchWord are lower-case English letters.


******************************************************
key:
	- Trie
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- In a sorted list of words,for any word A[i], all its sugested words must following this 
	  word in the list.

	  For example, if A[i] is a prefix of A[j], A[i] must be the prefix of A[i + 1], A[i + 2], ..., A[j]

	- Now we can binary search the position of each prefix of search word, and check if the next 
	  3 words is a valid suggestion.
	- ceiling is trying to get lowerBound of prefix, while floor is getting the upperbound of prefix

	 ex. ["mob","mou","men","mac"] & "mou"

		round 1: 	ceiling is: mac
					floor is: mou
		round 2:	ceiling is: mob
					floor is: mou
		round 3:	ceiling is: mou
					floor is: mou


stats:

	- O(n)
	- Runtime: 35 ms, faster than 42.90% of Java online submissions for Search Suggestions System.
	- Memory Usage: 44.8 MB, less than 100.00%

public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        List<List<String>> res = new ArrayList<>();

        // ordered hashmap
        TreeMap<String, Integer> map = new TreeMap<>();
        Arrays.sort(products);
        List<String> productsList = Arrays.asList(products);

        for (int i = 0; i < products.length; i++) {
            map.put(products[i], i);
        }

        String key = "";
        for (char c : searchWord.toCharArray()) {
            key += c;
            String ceiling = map.ceilingKey(key);
            String floor = map.floorKey(key + "~");
            if (ceiling == null || floor == null) break;
            res.add(productsList.subList(map.get(ceiling), 
            						Math.min(map.get(ceiling) + 3, map.get(floor) + 1)));
        }

        while (res.size() < searchWord.length()) 
        	res.add(new ArrayList<>());
        return res;
    }



=======================================================================================================
method 2:

method:

	- Trie
	- 

stats:

	- Sorting cost time O(m * n * logn), due to involving comparing String, which cost time O(m) 
	  for each comparison, building Trie cost O(m * n). 
	- Time: O(m * n * logn + L) 
	- space: O(m * n + L * m)
	- where m = average length of products, n = products.length, L = searchWord.length().


    class Trie {
        Trie[] sub = new Trie[26];
        LinkedList<String> suggestion = new LinkedList<>();
    }

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Arrays.sort(products); // sort products.
        
        // build Trie
        Trie root = new Trie();
        for (String p : products) { 
            Trie t = root;
            for (char c : p.toCharArray()) { // insert current product into Trie.
                if (t.sub[c - 'a'] == null)
                    t.sub[c - 'a'] = new Trie();
                t = t.sub[c - 'a'];
                if (t.suggestion.size() < 3) // maintain 3 lexicographically minimum strings.
                    t.suggestion.offer(p); // put products with same prefix into suggestion list.
            }
        }
        List<List<String>> ans = new ArrayList<>();
        for (char c : searchWord.toCharArray()) { // search product.
            if (root != null) // if there exist products with current prefix.
                root = root.sub[c - 'a'];
            ans.add(root == null ? Arrays.asList() : root.suggestion); // add it if there exist products with current prefix.
        }
        return ans;
    }

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



