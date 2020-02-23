import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class Challenage_7 {
    public static void main(String args[])
    {
        Challenage_6 ch6 = new Challenage_6();
        Challenage_7 ch7 = new Challenage_7();
        byte[] cipherText = ch6.readBase64("7.txt");
        char[] keyStr = "YELLOW SUBMARINE".toCharArray();
        byte[] key = new byte[16];
        for(int i = 0; i < 16; i++)
            key[i] = (byte)keyStr[i];
        byte[] output = ch7.decrypt(cipherText, key);
        System.out.print(new String(output));
    }

    public byte[] decrypt(byte[] cipherText, byte[] key)
    {
        try{
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] original = cipher.doFinal(cipherText);
            return original;

        }catch (Exception ex){
            ex.printStackTrace();}
            return null;
    }
}

