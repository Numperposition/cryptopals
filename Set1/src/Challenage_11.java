import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;
public class Challenage_11 {
    public static void main(String[] args){
        Challenage_11 ch11 = new Challenage_11();
        byte[] key = ch11.generateKey();
        Random random = new Random();
        //System.out.print(choice);
        //Scanner sc = new Scanner(System.in);
        String input = "11111111111111111111111111111111111111111111111111" ;                   //sc.nextLine();
        byte[] in = input.getBytes();
        int preNum = 5 + random.nextInt(6);
        int lastNum = 5 + random.nextInt(6);
        int totalLen = preNum + lastNum + in.length;
        byte[] plainText = new byte[totalLen];
//        System.out.println(preNum);
//        System.out.println(lastNum);
//        System.out.println(totalLen);
        for(int i = 0; i < plainText.length; i++)
        {
            if(i < preNum || i >= totalLen-lastNum)
            {
                plainText[i] = (byte)random.nextInt(256);
            }
            else
                plainText[i] = in[i-preNum];
           // System.out.print(plainText[i] + " ");
        }
        byte[] cipherText;
        int choice = random.nextInt(2);
        if(choice == 0)
        {
            cipherText = ch11.ECBEncrypt(plainText, key);
        }
        else{
            String IV = "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
            cipherText = ch11.CBCencrypt(plainText, key, IV);
        }
        if(ch11.checker(cipherText))
            System.out.println("ECB mode.");
        else
            System.out.print("CBC mode.");
    }

    public boolean checker(byte[] cipherText) //if ECB mode, then the ciphertext of each block is the same.
    {
        byte[] tmp1 = new byte[16];
        byte[] tmp2 = new byte[16];
        for(int i = 0; i < cipherText.length; i++)
        {
            if(i >= 16 && i < 32)
                tmp1[i-16] = cipherText[i];
            else if(i >= 32 && i < 48)
                tmp2[i-32] = cipherText[i];
        }
        for(int i = 0; i < 16; i++)
        {
            if(tmp1[i] != tmp2[i])
                return false;
        }
        return true;
    }

    public byte[] generateKey(){
        Random random = new Random();
        byte[] key = new byte[16];
        for(int i = 0; i < 16; i++)
        {
            int num = random.nextInt(256);
            key[i] = (byte)num;
            //System.out.print(key[i] + " ");
        }
        return key;
    }

    public byte[] CBCencrypt(byte[] plainBytes, byte[] key, String IV){
        //byte[] ivBytes = IV.getBytes();
        try{
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] cipherText = cipher.doFinal(plainBytes);
            //System.out.print("ok");
            return cipherText;
        }catch(Exception ex){return null;}

    }

    public byte[] ECBEncrypt(byte[] plainBytes, byte[] key)
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
}
