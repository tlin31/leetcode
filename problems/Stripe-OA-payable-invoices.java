Stripe OA.

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;



class Result {

    /*
     * Complete the 'calculate_total_owed' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING_ARRAY actions as parameter.
     */


    // store invoice id and the invoice
    public static Map<String, Invoice> map;

    public static String cleanNum(int index, String s) {
        char[] ch = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(int i = index; i<ch.length;i++) {
            if(Character.isDigit(ch[i])){
                sb.append(ch[i]);
            }
            else break;
        }
        return sb.toString();
    }

    public static int calculate_total_owed(List<String> actions) {
         
        int result = 0;
        map = new HashMap<>();
        processActions(actions);
        for(String invoice: map.keySet()) {
            Invoice in = map.get(invoice);
            int amount = Integer.valueOf(in.amount);
            result += amount;
        }
        return result;
         
    }
     
    private static void processActions(List<String> actions) {
        for(String s : actions) {
            String[] arr = s.split(":");
            String type = arr[0].trim();
            String details = arr[1].trim();
 
            if(type.equals("CREATE")) {
                Invoice in = getInvoice(details);
                if(in != null) {
                    map.putIfAbsent(in.id, in);   
                }
                   
            } else if(type.equals("FINALIZE")) {
                Invoice in = getInvoice(details);
                if(in != null) {
                    // update type
                    in.type = "FINALIZE";
                    if(map.containsKey(in.id)) {
                        Invoice record = map.get(in.id);

                        // update if it exists
                        if(record.type.equals("CREATE")) {
                            map.put(in.id, in);
                        }
                    }
                }
                 
            } else if(type.equals("PAY")) {
                String id = payInvoice(details);
                if(map.containsKey(id)) {
                    Invoice in = map.get(id);
                    if(in.type.equals("FINALIZE")){
                        //update type and reset amount
                        in.amount = "0";
                        in.type = "PAY";
                        map.put(id, in);
                    }
                }
            }
             
        }
    }
    
    public static Invoice getInvoice(String s) {
        String[] allInfo = s.split("&");
        String amount = null;
        String id = null;
        String currency =null;
        Invoice invoice = null;

        for(String info : allInfo) {
            if(info.contains("id=")) {
                int index = info.indexOf("id=");
                id = cleanNum(index+3,info);
            }else if(info.contains("amount=")) {
                int index = info.indexOf("amount=");
                amount = cleanNum(index+7,info);
            }else if(info.contains("currency=")) {
                // the last variable we expected
                int index = info.lastIndexOf("=");
                currency = info.substring(index+1);
            }
        }
        
        if(id !=null && amount !=null && currency.equals("USD")) {
            invoice = new Invoice(id,amount,currency, "CREATE");
        }
         
        return invoice;
    }
     
    public static String payInvoice(String s) {
         
        int index = s.indexOf("id=");
        StringBuilder sb = new StringBuilder();
        char[] ch = s.toCharArray();
        for(int i = index+3;i<ch.length;i++) {
            if(Character.isDigit(ch[i])) {
                sb.append(ch[i]);
            }
            else break;
        }
        return sb.toString();
    }
    

}

class Invoice {
    String id;
    String amount;
    String currency;
    String type;
    Invoice(String id, String amount, String currency, String type){
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.type = type;
    }
}

public class Solution {