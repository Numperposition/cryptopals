import java.util.Base64;
import java.util.Scanner;

public class Hex2Base64 {
    private static final char[] table = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        String hexStr = sc.nextLine();
        //String hexStr2 = sc.nextLine();
        Hex2Base64 hex2Base64 = new Hex2Base64();
        byte[] hexBytes = hex2Base64.hexstr2byte(hexStr);
        //byte[] hexBytes2 = hex2Base64.hexstr2byte(hexStr2);
       // byte[] res = hex2Base64.FixXOR(hexBytes, hexBytes2);   //challenge 2
        //System.out.print(hex2Base64.byte2str(res));
        for(int i = 0; i < 256; i++)
        {
            //char ch = table[i];
            byte b = (byte)i;
            byte[] res = hex2Base64.FixXOR(hexBytes, b);
            System.out.println("key: "+ i + " msg: " + new String(res));  //key is X, msg is "Cooking MC's like a pound of bacon"
        }

        //System.out.print("hex bytes length: "+ hexBytes.length);
        //String result = Base64.getEncoder().encodeToString(hexBytes);  // challenge 1

        //System.out.print(result);
    }

    private byte[] FixXOR(byte[] b1, byte b)
    {
        byte[] res = new byte[b1.length];
        for(int i = 0; i < res.length; i++)
            res[i] = (byte) (b1[i] ^ b);
        return res;
    }

    private byte[] FixXOR(byte[] b1, byte[] b2)
    {
        byte[] res = new byte[b1.length];
        for(int i = 0; i < res.length; i++)
            res[i] = (byte) (b1[i] ^ b2[i]);
        return res;
    }

    public String byte2str(byte[] bytes)  //function that convert byte to hex string
    {
        String result = "";
        for(int i = 0; i < bytes.length; i++)
        {
            result += String.format("%02x", new Integer((bytes[i] & 0xff)));
        }
        return result;
    }

    public byte[] hexstr2byte(String hexStr)  //convert hex string to byte
    {
        byte[] bytes = new byte[hexStr.length() / 2];
        int j = 0;
        for(int i = 0; i < bytes.length; i++)
        {
            byte high = (byte) (Character.digit(hexStr.charAt(j), 16) & 0xff);
            byte low = (byte) (Character.digit(hexStr.charAt(j + 1), 16) & 0xff);
            bytes[i] = (byte) (high << 4 | low);
            j += 2;
        }
        return bytes;
    }
}
