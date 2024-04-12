Google max time

https://leetcode.com/discuss/interview-question/396769/

You are given a string that represents time in the format hh:mm. Some of the digits are blank (represented by ?). Fill in ? such that the time represented by this string is the maximum possible. Maximum time: 23:59, minimum time: 00:00. You can assume that input string is always valid.

Example 1:

Input: "?4:5?"
Output: "14:59"
Example 2:

Input: "23:5?"
Output: "23:59"
Example 3:

Input: "2?:22"
Output: "23:22"
Example 4:

Input: "0?:??"
Output: "09:59"
Example 5:

Input: "??:??"
Output: "23:59"

=======================================================================================================
method 1:

method:

    - 
    - 

stats:

    - 
    - 

public static void giveMeMaxTime(String str){
    char[] charArr = str.toCharArray();

    if(charArr[0] == '?') {
        if (charArr[1] <= '3' || charArr[1] == '?') {
            charArr[0] = '2';
        } else {
            charArr[0] = '1';
        }  
    }


    if(charArr[1] == '?'){
        if(charArr[0] == '2'){
            charArr[1] = '3';
        } else {
            charArr[1] = '9';
        }
    }

    charArr[3] = (charArr[3] == '?') ? '5' : charArr[3];
    charArr[4] = (charArr[4] == '?') ? '9' : charArr[4];

    System.out.println(charArr);

}


public static void main(String[] args) {
    giveMeMaxTime("23:5?");// 23:59
    giveMeMaxTime("2?:22");// 23:22
    giveMeMaxTime("0?:??");// 09:59
    giveMeMaxTime("1?:??");// 19:59
    giveMeMaxTime("?4:??");// 14:59
    giveMeMaxTime("?3:??");// 23:59
    giveMeMaxTime("??:??");// 23:59
    giveMeMaxTime("?4:5?"); //14:59
    giveMeMaxTime("23:5?"); //23:59
    giveMeMaxTime("?9:4?"); //19:49
}

=========================================================
class Solution{

public  String maxTime(String time){
    char[] cTime = time.toCharArray();
    if (cTime[0] == '?') {
        if( (cTime[1] == '?' ||cTime[1] == '4') && ((cTime[3] == '?' ||cTime[3] == '0' )&&(cTime[4] == '?' ||cTime[4] == '0'))||
                cTime[1] >= '0' && cTime[1] < '4'){
            cTime[0] = '2';
        }else{
            cTime[0] = '1';
        }
    }

    if (cTime[1] == '?') {
        if(cTime[0] == '2'){
            if(cTime[3] != '?' && cTime[3] != '0' ||
                    cTime[4] != '?' && cTime[4] != '0')
                cTime[1] = '3';
            else
                cTime[1] = '4';
        }else{
            cTime[1] = '9';
        }
    }

    if (cTime[3] == '?') {
            cTime[3] = '5';
        
    }

    if (cTime[4] == '?') {
            cTime[4] = '9';
        
    }

    return new String(cTime);
}

public static void main(String[] args) {
    Solution s = new Solution();
    System.out.println(s.maxTime("?4:5?"));
    System.out.println(s.maxTime("?4:??"));
    System.out.println(s.maxTime("?3:??"));
    System.out.println(s.maxTime("23:5?"));
    System.out.println(s.maxTime("2?:22"));
    System.out.println(s.maxTime("0?:??"));
    System.out.println(s.maxTime("1?:??"));
    System.out.println(s.maxTime("??:??"));
    System.out.println(s.maxTime("?4:0?"));
    System.out.println(s.maxTime("?9:4?"));
}
}