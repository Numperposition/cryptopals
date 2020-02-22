import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Challenage_3 {
    public Map<Character, Double> dictionary = new HashMap<Character, Double>();
    public String highest_score_str = "";
    public double highest_score = 0;
    public Challenage_3(){
        dictionary.put('a', 0.0651738);  dictionary.put('u', 0.0225134);
        dictionary.put('b', 0.0124248);  dictionary.put('v', 0.0082903);
        dictionary.put('c', 0.0217339);  dictionary.put('w', 0.0171272);
        dictionary.put('d', 0.0349835);  dictionary.put('x', 0.0013692);
        dictionary.put('e', 0.1041442);  dictionary.put('y', 0.0145984);
        dictionary.put('f', 0.0197881);  dictionary.put('z', 0.0007836);
        dictionary.put('g', 0.0158610);  dictionary.put(' ', 0.1918182);
        dictionary.put('h', 0.0492888);
        dictionary.put('i', 0.0558094);
        dictionary.put('j', 0.0009033);
        dictionary.put('k', 0.0050529);
        dictionary.put('l', 0.0331490);
        dictionary.put('m', 0.0202124);
        dictionary.put('n', 0.0564513);
        dictionary.put('o', 0.0596302);
        dictionary.put('p', 0.0137645);
        dictionary.put('q', 0.0008606);
        dictionary.put('r', 0.0497563);
        dictionary.put('s', 0.0515760);
        dictionary.put('t', 0.0729357);

    }
    public static void main(String args[]){
        //Scanner sc = new Scanner(System.in);
        String hexStr = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736";
        Challenage_3 ch3 = new Challenage_3();
        Hex2Base64 hex2Base64 = new Hex2Base64();
        byte[] hexBytes = hex2Base64.hexstr2byte(hexStr);
        for(int i = 0; i < 256;i++)
        {
            byte b = (byte)i;
            byte[] res = ch3.FixXOR(hexBytes, b);
            //ch3.getScore(new String(res));  //byte2string function can convert each byte to character based on ASCII
            String tmp = new String(res);
            // strips off all non-ASCII characters
            //tmp = tmp.replaceAll("[^\\x00-\\x1F]+", "");
            // erases all the ASCII control characters
            tmp = tmp.replaceAll("[\\p{Cntrl}]", "");
            ch3.getScore(tmp);
            //tmp = tmp.replaceAll("\\p{C}", "");

        }
        System.out.println(ch3.highest_score_str);
    }

    public double getScore2(String str)
    {
        double score = 0.0;
        for(int i = 0; i < str.length(); i++)
        {
            char ch = str.charAt(i);
            if(Character.isLetter(ch) || ch == ' ')
            {
                if(Character.isUpperCase(ch))
                    ch = Character.toLowerCase(ch);
                if(dictionary.containsKey(ch))
                    score += dictionary.get(ch);
            }
        }
        return score;

    }

    public void getScore(String str)
    {
        double score = 0.0;
        for(int i = 0; i < str.length(); i++)
        {
            char ch = str.charAt(i);
            if(Character.isLetter(ch) || ch == ' ')
            {
                if(Character.isUpperCase(ch))
                    ch = Character.toLowerCase(ch);
                if(dictionary.containsKey(ch))
                    score += dictionary.get(ch);
            }
        }
        if(score >= highest_score)
        {
            highest_score = score;
            highest_score_str = str;
        }

    }

    public byte[] FixXOR(byte[] b1, byte b)
    {
        byte[] res = new byte[b1.length];
        for(int i = 0; i < res.length; i++)
            res[i] = (byte) (b1[i] ^ b);
        return res;
    }
}

