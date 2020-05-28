import java.util.*;

public class Challenage_14 {
    public static void main(String[] args){
        Challenage_14 ch14 = new Challenage_14();
        Challenage_12 ch12 = new Challenage_12();
        byte[] key = ch12.generateKey();
        String plainText = "Um9sbGluJyBpbiBteSA1LjAKV2l0aCBteSByYWctdG9wIGRvd24gc28gbXkg" +
                "aGFpciBjYW4gYmxvdwpUaGUgZ2lybGllcyBvbiBzdGFuZGJ5IHdhdmluZyBq" +
                "dXN0IHRvIHNheSBoaQpEaWQgeW91IHN0b3A/IE5vLCBJIGp1c3QgZHJvdmUg" +
                "YnkK";
        String prefix = "12345";
        String input = prefix + plainText;
       // System.out.print(plainText);
        int prefixNum = ch14.getPrefixLen(plainText, prefix, key);
        System.out.println("length of prefix: "+ prefixNum);
        ch12.getPlainText(input.substring(prefixNum), key);

    }
    public int getPrefixLen(String plain, String prefix, byte[] key){
        Challenage_9 ch9 = new Challenage_9();
        Challenage_11 ch11 = new Challenage_11();

        int blockSize = 16;
        byte[] preBlock = new byte[16];
        for(int i = 1; i < blockSize+1; i++)
        {
            String tmp = new String(prefix);
            String ptmp = "";

            for(int j = 0; j < i; j++)
            {
                tmp += "A";
            }
            //System.out.println(tmp);
            ptmp = tmp + plain;
           // System.out.println(ptmp);
            byte[] padded = ch9.pkcs7Pad(ptmp.getBytes(), 16);
            byte[] cipherText = ch11.ECBEncrypt(padded, key);
            byte[] curFirstBlock = Arrays.copyOfRange(cipherText, 0, 16);
//            for(int k = 0; k < 16; k++)
//                System.out.print(preBlock[k] + " ");
           // System.out.println();
            if(i != 1 && Arrays.equals(curFirstBlock, preBlock)){
                return blockSize - i + 1;
            }
            preBlock = Arrays.copyOfRange(curFirstBlock, 0, 16);
        }
        return 0;
    }
}
