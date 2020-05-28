import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import utils.AESCBC;

public class Challenage_10 {
    public static void main(String[] args){
        Challenage_6 ch6 = new Challenage_6();
        Challenage_10 ch10 = new Challenage_10();
        byte[] cipherText = ch6.readBase64("10.txt");
        char[] keyStr = "YELLOW SUBMARINE".toCharArray();
        byte[] key = new byte[16];
        for(int i = 0; i < 16; i++)
            key[i] = (byte)keyStr[i];

        String IV = "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
        byte[] painText = ch10.decrypt(cipherText, key, IV);
        System.out.println(new String(painText));
    }
    public byte[] decrypt(byte[] cipherBytes, byte[] key, String IV){
        //byte[] ivBytes = IV.getBytes();
        try{
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(cipherBytes);
            //System.out.print("ok");
            return original;
        }catch(Exception ex){return null;}

    }

}
