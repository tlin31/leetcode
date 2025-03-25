433. Minimum Genetic Mutation - Medium

A gene string can be represented by an 8-character long string, with choices from 'A', 'C', 'G', 
and 'T'.

Suppose we need to investigate a mutation from a gene string startGene to a gene string endGene 
where one mutation is defined as one single character changed in the gene string.

For example, "AACCGGTT" --> "AACCGGTA" is one mutation.
There is also a gene bank that records all the valid gene mutations. A gene must be in 
bank to make it a valid gene string.

Given the two gene strings startGene and endGene and the gene bank bank, return the minimum 
number of mutations needed to mutate from startGene to endGene. If there is no such a mutation, 
return -1.

Note that the starting point is assumed to be valid, so it might not be included in the bank.

 

Example 1:

Input: startGene = "AACCGGTT", endGene = "AACCGGTA", bank = ["AACCGGTA"]
Output: 1

Example 2:

Input: startGene = "AACCGGTT", endGene = "AAACGGTA", bank = ["AACCGGTA","AACCGCTA","AAACGGTA"]
Output: 2
 

Constraints:

0 <= bank.length <= 10
startGene.length == endGene.length == bank[i].length == 8
startGene, endGene, and bank[i] consist of only the characters ['A', 'C', 'G', 'T'].


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

	-	



Stats:

	- 
	- 


class Solution {
    public int minMutation(String start, String end, String[] bank) {
        int level = 0;
        int len = bank.length;
        char[] chr = {'A','C','G','T'};
            
        Set<String> hset = new HashSet<String>();
        for(String s: bank){
            hset.add(s);
        }
        
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        
        while(true){
            level++;
            int n = queue.size();
            
            if(n == 0){
                return -1;
            }
            
            for(int i =0; i < n; i++){
                char[] ch = queue.poll().toCharArray();
                
                for(int j =0; j < 8; j++){
                    char org_char = ch[j];

                    for(int c = 0; c<4; c++){
                    	//将j位置的ch替换成A T G C
                        ch[j] = chr[c]; 
                        String str = String.valueOf(ch);

                        //达到目标
                        if(str.equals(end)
                          && hset.contains(str)){
                            return level;
                        }
                        
                        if(!hset.contains(str)){
                            continue;
                        }
                        
                        //是valid的，为了避免不同level的重复
                        hset.remove(str);
                        queue.add(str);
                    }
                    ch[j] = org_char;
                }
            }
        }
    }
}



