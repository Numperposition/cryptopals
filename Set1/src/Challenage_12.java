import java.util.*;
public class Challenage_12 {
    public static void main(String[] args){
       // String plainText = "1111111111111111111111111";
        String plainText = "Um9sbGluJyBpbiBteSA1LjAKV2l0aCBteSByYWctdG9wIGRvd24gc28gbXkg" +
                "aGFpciBjYW4gYmxvdwpUaGUgZ2lybGllcyBvbiBzdGFuZGJ5IHdhdmluZyBq" +
                "dXN0IHRvIHNheSBoaQpEaWQgeW91IHN0b3A/IE5vLCBJIGp1c3QgZHJvdmUg" +
                "YnkK";

     //   plainText += tmp;
        Challenage_12 ch12 = new Challenage_12();
        byte[] key = ch12.generateKey();
        ch12.getPlainText(plainText, key);
//        int blockSize = ch12.getBlockSize(plainText, key);
//        System.out.print(blockSize);
//        int payloadLen = ch12.getPayloadSize(plainText, key);
//        System.out.print(payloadLen); //the same as plainText.length, but actually, we dont know the length of plaintext.

    }

    public ArrayList<Byte> getOneByte(String plain, byte[] key, ArrayList<Byte> knownBytes){
        Challenage_9 ch9 = new Challenage_9();
        Challenage_11 ch11 = new Challenage_11();
        byte[] pl = plain.getBytes();
        int blockSize = 16;
        int payloadSize = getPayloadSize(plain, key);
        //the first unknown character is at the end of a block
        int paddingSize = blockSize-1 - (knownBytes.size()%blockSize);
        String padding = "";
        for(int i = 0; i < paddingSize; i++)
            padding += "A";
        byte[] pd = padding.getBytes();
        int interestBlock = knownBytes.size() / 16;
        String text = padding + plain;
        byte[] c1 = ch11.ECBEncrypt(ch9.pkcs7Pad(text.getBytes(), 16),key);
        byte[] block = new byte[16];
        for(int i = 0; 16*interestBlock+i<c1.length &&i < 16; i++)
        {
            block[i] = c1[16*interestBlock+i];
        }

        for(int i = 0; i < 256; i++)
        {
            byte[] msg = new byte[paddingSize+knownBytes.size()+1+pl.length];
            for(int j =0; j < msg.length; )
            {
                for(int k = 0; k <paddingSize; k++)
                {
                    msg[j] = pd[k];
                    j++;
                }
                for(int k = 0; k < knownBytes.size(); k++)
                {
                    msg[j] = knownBytes.get(k);
                    j++;
                }
                msg[j++] = (byte)i;
                for(int k = 0; k < pl.length; k++)
                {
                    msg[j] = pl[k];
                    j++;
                }
            }
            boolean flag = true;
            byte[] paded = ch9.pkcs7Pad(msg, 16);
            byte[] cipher = ch11.ECBEncrypt(paded, key);
            for(int t = 0; 16*interestBlock+t < cipher.length && t < 16; t++){
                if(block[t] != cipher[16*interestBlock+t])
                {
                    flag = false;
                    break;
                }
            }
            if(flag)
            {
                knownBytes.add((byte)i);
                break;
            }
        }
        return knownBytes;
    }

    public ArrayList<Byte> getPlainText(String plain, byte[] key){
        ArrayList<Byte> known = new ArrayList<>();
        int payloadNum = getPayloadSize(plain, key);
        for(int i = 0; i < payloadNum; i++)
        {
            known = getOneByte(plain, key, known);
        }
        byte[] result = new byte[known.size()];
        for(int i = 0; i < known.size(); i++)
        {
            result[i] = known.get(i);
        }
        System.out.println(new String(Base64.getDecoder().decode(result)));
        //System.out.print(new String(result));
        return known;
    }

    public int getPayloadSize(String plain, byte[] key){
        Challenage_11 ch11 = new Challenage_11();
        Challenage_9 ch9 = new Challenage_9();
        byte[] plainText = plain.getBytes();
        byte[] padding_text = ch9.pkcs7Pad(plainText, 16);
        int cipherTextLen = ch11.ECBEncrypt(padding_text, key).length;
        int payloadLen = cipherTextLen;
        while(true){
            plain += "A";
            padding_text = ch9.pkcs7Pad(plain.getBytes(), 16);
            int tmp = ch11.ECBEncrypt(padding_text, key).length;
            if(tmp == cipherTextLen)
                payloadLen--;
            else
                return payloadLen;
        }
//        System.out.println(cipherTextLen);
//        System.out.println(plainText.length);
//        return cipherTextLen;
    }

    public int getBlockSize(String plain, byte[] key){
        Challenage_11 ch11 = new Challenage_11();
        Challenage_9 ch9 = new Challenage_9();
        byte[] padding_plain = ch9.pkcs7Pad(plain.getBytes(), 16);
        int len1 = ch11.ECBEncrypt(padding_plain, key).length;
        plain += 'A';
        padding_plain = ch9.pkcs7Pad(plain.getBytes(), 16);
        int len2 = ch11.ECBEncrypt(padding_plain, key).length;
        while(len1 == len2){
            plain += 'A';
            padding_plain = ch9.pkcs7Pad(plain.getBytes(), 16);
            len2 = ch11.ECBEncrypt(padding_plain, key).length;
        }
        return len2 - len1;
    }


    public byte[] generateKey(){
        //Random random = new Random();
        byte[] key = new byte[16];
        for(int i = 0; i < 16; i++)
        {
            int num = 3;
            key[i] = (byte)num;
            //System.out.print(key[i] + " ");
        }
        return key;
    }
}
