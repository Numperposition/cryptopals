
public class Challenage_6 {

    public int getHammingDist(String s1, String s2)
    {
        int len;
        byte[] bytes1, bytes2;
        byte[] tmp1 = s1.getBytes(), tmp2 = s2.getBytes();

        if(s1.length() >= s2.length())
        {
            len = s1.length();
            bytes1 = tmp1;
            bytes2 = new byte[len];
            for(int i = 0; i < tmp2.length; i++)
                bytes2[i] = tmp2[i];
        }
        else{
            len = s2.length();
            bytes2 = tmp2;
            bytes1 = new byte[len];
            for(int i = 0; i < tmp1.length; i++)
                bytes1[i] = tmp1[i];
        }



    }
}
