Airbnb- guess number


要猜出一四位数字。

例子：
    # correct code: 3264
    # GUESS 1111 => 0 0 (no correct digits)
    # GUESS 1214 => 2 0 (digits 2 and 4 are correct and on correct position)
    # GUESS 6111 => 0 1 (digit 6 is present, but on a different position)
    # GUESS 6211 => 1 1 (digit 2 is not counted towards the second count!)

写的时候要连到一个server上。

    # START\n
    # GUESS 1111\n =>0 0
    # etc......

/*
Assumption
1. Given an API we can call, API takes a String from "1111" ~ "6666".
returns how many number is a match, correct position and value
2. Use this api to guess a number with as fewer API calls as possible

Approach:
1. So, we can first wrap the socket connection to a Connection class, then we will
use it to call the server.
2. We can first guess "1111", this way, we know how many 1 in the result.
we guess "2222" and we can know how many 2 in the result. and
so on. After this, for example, we know 1,2,2,4 are in the result, but we do not
know the order yet.
3. To get the order, we first fill an char array with the not exist
number, let's say 3. [3,3,3,3]. Then replace with [1,3,3,3], [3,1,3,3], [3,3,1,3] and
call api again, we will know the position of 1. We do the same for the rest.

Time: at most 12 times of API calls
6+3+2+1 = 12


Optimize:
从count最多的开始猜。比如cnt = 2， 就猜available位置放cnt最大digit的元素，遍历下去，AAXX, AXAX, AXXA...
这样下去avaialble的位置就减少到4 - cnt, 依次进行，但是每次只在available的位置遍历，减小搜索空间，
方法适用于n长的String.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BGuessNumber {

    public static void main(String[] args) {
        SocketClient client = new SocketClient("localhost", 8080);
        String[] pre = {"", "1111", "2222", "3333", "4444", "5555", "6666"};
        Queue<Character> exist = new LinkedList<>();

        char notExist = '0';

        // pre-process
        for (int i = 1; i < pre.length; i++) {
            client.sendRequest(pre[i]);
            int match = client.readResponse().charAt(0) - '0';
            if (match == 0) {
                notExist = (char)(i + '0');
            }
            // for number of match, push this amount of char into queue
            for (int j = 0; j < match; j++) {
                exist.offer((char)(i + '0'));
            }
        }
        // optimize, find the count of each number, then proceed by count

        // we already know which number exist and the count of it, so we now need to know the position
        char[] res = new char[4];
        Arrays.fill(res, notExist);

        // number of character that we already find is correct, need to take this as a threshold
        // when compare to "how many num are correct in position"
        int count = 0;     
        while (!exist.isEmpty()) {
            int countPos = exist.size();
            char c = exist.poll();
            for (int i = 0; i < res.length; i++) {
                if (res[i] == notExist) {

                    // if the last position, we do not need call api, just add to result
                    if (countPos == 1) { 
                        res[i] = c;
                        count++;
                        break;
                    }
                    res[i] = c;
                    client.sendRequest(new String(res));

                    //if at right position
                    if (client.readResponse().charAt(1) - '0' > count) {
                        count++;

                        // break from this for loop
                        break;
                    }
                    res[i] = notExist;
                    countPos--;
                }
            }
        }
        System.out.println(new String(res));
    }
}

class SocketClient {

    PrintWriter out;
    BufferedReader in;

    public SocketClient(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(String req) {
        out.println(req);
    }

    public String readResponse() {
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Fake API: for test only
//    public String guessAPI(String guess) {
//        int match = 0;
//        int exist = 0;
//        for (int i = 0; i < guess.length(); i++) {
//            if (guess.charAt(i) == secret.charAt(i)) {
//                match++;
//            }
//            if (secret.indexOf(guess.charAt(i)) != -1) {
//                exist++;
//            }
//        }
//        return "" + match + exist;
//    }
}





-----------------------------------------------------------------------


import java.util.*;

public class Solutions {

    private static int count = 0;
    private static List<Integer> target = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println(sendAndReceive("start"));
        System.out.println("Result: " + guess());
    }

    // Simulation method, to generate or reset the random number, don't have to focus on it
    public static void reset() {
        target.clear();
        for (int i = 0; i < 4; ++i) {
            target.add((int)(Math.random() * 6) + 1);
        }
        count = 0;
    }

    // Simulation method, don't have to focus it
    public static String sendAndReceive(String str) {
        if (str.toLowerCase().equals("start")) {
            reset();
            return "Ready, target # is " + target.get(0) + target.get(1) + target.get(2) + 
                target.get(3);
        }

        System.out.println("Times of method call: " + ++count + ", coming number: " + str);
        int a = 0;
        List<Integer> copyOfTarget = new ArrayList<>(target);
        List<Integer> t = new ArrayList<>();    //target
        List<Integer> g = new ArrayList<>();    //guess

        for (int i = 0; i < 4; ++i) {
            int digit = copyOfTarget.get(i);
            char c = str.charAt(i);

            if (digit == c - '0') ++a;
            else {
                t.add(digit);
                g.add(c - '0');
            }
        }

        int size = g.size();
        g.removeAll(t);
        int b = size - g.size();

        return a + " " + b;
    }

    // Solutions
    public static String guess() {
        String base = "1111";
        int first = Integer.parseInt(sendAndReceive(base).split(" ")[0]);
        if (4 == first) 
            return base;
        char[] res = new char[4];

        for (int i = 0; i < 4; ++i) {
            int lastResponse = first;
            char[] chBase = base.toCharArray();
            for (int j = 2; j < 6; ++j) {
                chBase[i] = (char)('0' + j);
                int response = Integer.parseInt(sendAndReceive(String.valueOf(chBase)).split(" ")[0]);
                if (4 == response) return String.valueOf(chBase);
                if (response != lastResponse) {
                    res[i] = lastResponse > response ? '1' : (char)('0' + j);
                    break;
                }
                else {
                    lastResponse = response;
                }
            }
            if (0 == res[i]) res[i] = '6';
        }

        return String.valueOf(res);
    }
}