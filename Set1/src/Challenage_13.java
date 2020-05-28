import java.util.*;
public class Challenage_13 {
    public static void main(String[] args){
        //String input = "foo=bar&baz=qux&zap=zazzle";
        String input = "foo@bar.com";
        Challenage_13 ch13 = new Challenage_13();
        Challenage_11 ch11 = new Challenage_11();
        Challenage_9 ch9 = new Challenage_9();
       // HashMap<String, String> map = ch13.k_vParsing(input);
        HashMap<String, String> map = ch13.profileFor(input);
        String output = ch13.encode(map);
        System.out.print(output);
        byte[] key = ch11.generateKey();
        byte[] padded = ch9.pkcs7Pad(output.getBytes(), 16);
        byte[] cipherText = ch11.ECBEncrypt(padded, key);
        System.out.print(new String(cipherText));

    }

    public HashMap<String, String> profileFor(String input){
        HashMap<String, String> map = new HashMap<>();
        map.put("email", input);
        map.put("uid", "10");
        map.put("role", "admin");
        return map;
    }

    public String encode(HashMap<String, String> map){
        Iterator iterator = map.keySet().iterator();
        String output = "";
        while(iterator.hasNext()){
            String key = (String)iterator.next();
            output += (key + "=");
            String value = map.get(key);
            output += (value + "&");
        }
        return output.substring(0, output.length()-1);
    }

    public HashMap<String, String> k_vParsing(String input){
        HashMap<String, String> map = new HashMap<>();
        String[] splitor = input.split("&");
        for(String str : splitor){
            String[] sub = str.split("=");
            map.put(sub[0], sub[1]);
        }
        return map;
    }
}
