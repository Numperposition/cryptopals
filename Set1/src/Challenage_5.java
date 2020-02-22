
public class Challenage_5 {
    private static final char[] key = "ICE".toCharArray();

    public static void main(String[] args)
    {
        Challenage_5 ch5 = new Challenage_5();
        Hex2Base64 ch1 = new Hex2Base64();
        String plaintext = "Burning 'em, if you ain't quick and nimble\nI go crazy when I hear a cymbal";
        byte[] input = plaintext.getBytes();
        byte[] output = ch5.XORencrypt(input);
        System.out.print(ch1.byte2str(output));

    }

    public byte[] XORencrypt(byte[] plainBytes)
    {
        byte[] res = new byte[plainBytes.length];
        for(int j = 0; j < plainBytes.length; )
        {
            for(int i = 0; i < key.length && j < plainBytes.length; i++)
            {
                byte b = (byte)key[i];
                res[j] = (byte) (plainBytes[j] ^ b);
                j++;
            }
        }
        return res;
    }

}
