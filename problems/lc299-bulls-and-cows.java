299. Bulls and Cows - Easy

You are playing the following Bulls and Cows game with your friend: You write down a number and 
ask your friend to guess what the number is. Each time your friend makes a guess, you provide a 
hint that indicates how many digits in said guess match your secret number exactly in both digit 
and position (called "bulls") and how many digits match the secret number but locate in the wrong 
position (called "cows"). 

Your friend will use successive guesses and hints to eventually derive the secret number.

Write a function to return a hint according to the secret number and friend's guess, use A to 
indicate the bulls and B to indicate the cows. 

Please note that both secret number and friend's guess may contain duplicate digits.

Example 1:

Input: secret = "1807", guess = "7810"

Output: "1A3B"

Explanation: 1 bull and 3 cows. The bull is 8, the cows are 0, 1 and 7.

Example 2:

Input: secret = "1123", guess = "0111"

Output: "1A1B"

Explanation: The 1st 1 in friend's guess is a bull, the 2nd or 3rd 1 is a cow.
Note: You may assume that the secret number and your friend's guess only contain digits, and their lengths are always equal.


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- iterate over the numbers in secret and in guess and count all bulls right away. For cows 
	  maintain an array that stores count of the number appearances in secret and in guess. 
	  Increment cows when either number from secret was already seen in guest or vice versa.
	- numbers[secret.charAt(i)-'0'] is negative only if this character appeared in the guess 
	  more times then in the secret which means that this character in the secret can be 
	  matched with one of the previous characters in the guest. 
	 - 我们其实可以用一次循环就搞定的，在处理不是bulls的位置时，我们看如果secret当前位置数字的映射值小于0，
	   则表示其在guess中出现过，cows自增1，然后映射值加1，如果guess当前位置的数字的映射值大于0，则表示其
	   在secret中出现过，cows自增1，然后映射值减1，参见代码如下：



stats:

	- 
	- 


public String getHint(String secret, String guess) {
    int bulls = 0;
    int cows = 0;
    int[] numbers = new int[10];
    for (int i = 0; i<secret.length(); i++) {
        int s = Character.getNumericValue(secret.charAt(i));
        int g = Character.getNumericValue(guess.charAt(i));
        if (s == g) bulls++;
        else {
            if (numbers[s] < 0) cows++;
            if (numbers[g] > 0) cows++;
            numbers[s] ++;
            numbers[g] --;
        }
    }
    return bulls + "A" + cows + "B";
}


=======================================================================================================
method 2:

method:

	- 用两次遍历，第一次遍历找出所有位置相同且值相同的数字，即bulls，并且记录secret中不是bulls的数字出现的次数。
	  然后第二次遍历我们针对guess中不是bulls的位置，如果在哈希表中存在，cows自增1，然后映射值减1，
	- 

stats:

	- 
	- 
public class Solution {
    public String getHint(String secret, String guess) {
        int bull = 0, cow = 0;
        
        int[] sarr = new int[10];
        int[] garr = new int[10];
        
        for(int i = 0; i < secret.length(); i++){
            if(secret.charAt(i) != guess.charAt(i)){
                sarr[secret.charAt(i)-'0']++;
                garr[guess.charAt(i)-'0']++;
            }else{
                bull++;
            }
        }
        
        // go through 2 buckets
        for(int i = 0; i <= 9; i++){
            cow += Math.min(sarr[i], garr[i]);
        }
        
        return (bull + "A" + cow + "B");
    }
}
=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



