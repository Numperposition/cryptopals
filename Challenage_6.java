import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Challenage_6 { //reference: https://trustedsignal.blogspot.com/2015/06/xord-play-normalized-hamming-distance.html
    public static void main(String[] args)
    {
//        String s1 = "this is a test";
//        String s2 = "wokka wokka!!!";
//        byte[] tmp1 = s1.getBytes(), tmp2 = s2.getBytes();
        Challenage_6 ch6 = new Challenage_6();
//        int count = ch6.getHammingDist(tmp1, tmp2);
//        System.out.print("count = " + count);
        byte[] cipherText = ch6.readBase64("6.txt");
        //System.out.print("size = " + cipherText.length);
        Map<Integer, Double> map = new HashMap<Integer, Double>();
        for(int keySize = 2; keySize <= 40; keySize++)
        {
            byte[] block1 = Arrays.copyOfRange(cipherText, 0, keySize);
            byte[] block2 = Arrays.copyOfRange(cipherText, keySize, 2*keySize);
            byte[] block3 = Arrays.copyOfRange(cipherText, 2*keySize, 3*keySize);
            byte[] block4 = Arrays.copyOfRange(cipherText, 3*keySize, 4*keySize);
            double totalDist = ch6.getHammingDist(block1, block2)+ch6.getHammingDist(block1, block3)
                    +ch6.getHammingDist(block1, block4)+ch6.getHammingDist(block2, block3)
                    +ch6.getHammingDist(block2, block4)+ch6.getHammingDist(block3, block4);
            map.put(keySize, totalDist / (4*keySize));
        }
        List<Map.Entry<Integer, Double>> infoIds = new ArrayList<Map.Entry<Integer, Double>>(map.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare(Map.Entry<Integer, Double> o1,
                               Map.Entry<Integer, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        //sorted result is as: (key size) 29:4.12, 5:4.35, 2:4.5, 24:4.53; My guess is the key length is 29.
        for (int i = 0; i < infoIds.size(); i++) {
            int id = infoIds.get(i).getKey();
            double sc = infoIds.get(i).getValue();
          //  System.out.println("key size: " + id + ", HMdist: " + sc);
        }

        List<List<Byte>> list = new ArrayList<List<Byte>>();

        for(int i = 0; i < 29; i++)
        {
            List<Byte> l = new ArrayList<Byte>();
            list.add(l);
        }

        for(int i = 0; i < cipherText.length; )
        {
            for(int j = 0; j < 29 && i < cipherText.length; j++)
            {
                list.get(j).add(cipherText[i]);
                i++;
            }
        }
        byte[] key = new byte[29];
        Challenage_3 ch3 = new Challenage_3();
        for(int i = 0; i < list.size(); i++)
        {
            //System.out.print("current list len = " + list.get(i).size());
            double maxScore = 0.0;
            byte posKey = 0x00;
            for(int j = 0; j < 256; j++)
            {
                int len = list.get(i).size();
                byte[] bytes = new byte[len];
                for(int k = 0; k < len; k++)
                    bytes[k] = list.get(i).get(k);
                bytes = ch3.FixXOR(bytes, (byte)j);
                String tmp = new String(bytes);
                double ss = ch3.getScore2(tmp);
                if(ss > maxScore)
                {
                    maxScore = ss;
                    posKey = (byte)j;
                }
            }
            key[i] = posKey;
        }
        System.out.println("key: " + new String(key));
        byte[] output = ch6.XORdecrypt(cipherText, key);
        System.out.print(new String(output));

    }

    public byte[] XORdecrypt(byte[] plainBytes, byte[] key)
    {

        byte[] res = new byte[plainBytes.length];
        for(int j = 0; j < plainBytes.length; )
        {
            for(int i = 0; i < key.length && j < plainBytes.length; i++)
            {
                byte b = key[i];
                res[j] = (byte) (plainBytes[j] ^ b);
                j++;
            }
        }
        return res;
    }

    public byte[] readBase64(String filename)
    {
        String filetext = "";
        String line;
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(new File(filename)));
            while ((line = input.readLine()) != null)
            {
                filetext += line;
            }
            input.close();
        } catch (IOException e){e.printStackTrace();}
        return  Base64.getDecoder().decode(filetext);
    }

    private int getHammingDist(byte[] tmp1, byte[] tmp2)
    {
        if(tmp1.length != tmp2.length)
            return -1;

        byte[] res = new byte[tmp1.length];
        int count = 0;
        for(int i = 0; i < tmp1.length; i++)
        {
            res[i] = (byte)(tmp1[i] ^ tmp2[i]);
            while(res[i] > 0)
            {
                count += res[i] & 0x01;
                res[i] = (byte)(res[i] >> 0x01);
            }
        }
        return count;

    }
}
