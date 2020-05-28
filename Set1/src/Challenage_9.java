
public class Challenage_9 {
    public static void main(String[] args)
    {
        String str = "YELLOW SUBMARINE";
        Challenage_9 ch9 = new Challenage_9();
        byte[] output = ch9.pkcs7Pad(str.getBytes(), 20);

        System.out.print(new String(output));

    }
    public byte[] pkcs7Pad(byte[] input, int blockSize)
    //The value of each added byte is the number of bytes that are added
    {
        if(input.length % blockSize == 0)  //no padding needed
            return input;
        int remain = blockSize - (input.length % blockSize);
        //System.out.print("remain = " + remain);
        int len = input.length + remain;
        byte[] res = new byte[len];
        for(int i = 0; i < input.length; i++)
            res[i] = input[i];
        for(int i = input.length; i < res.length; i++)
            res[i] = (byte)remain;
        return res;
    }
}
