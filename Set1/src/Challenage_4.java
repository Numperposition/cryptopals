import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Challenage_4 {
    public static void main(String args[])
    {
        Hex2Base64 hex2Base64 = new Hex2Base64();
        Challenage_3 ch3 = new Challenage_3();
        Challenage_4 ch4 = new Challenage_4();
        List<String> hexList = ch4.readFromFile("4.txt");
        double max = 0.0;
        String encry_str = "";
        for(int i = 0 ; i < hexList.size(); i++)
        {
            //System.out.println(hexList.get(i));
            byte[] hexBytes = hex2Base64.hexstr2byte(hexList.get(i));

            for(int j = 0; j < 256; j++)
            {
                byte b = (byte)j;

                byte[] res = ch3.FixXOR(hexBytes, b);
                //ch3.getScore(new String(res));  //byte2string function can convert each byte to character based on ASCII
                String tmp = new String(res);

               // tmp = tmp.replaceAll("[\\p{Cntrl}]", "");
                double tmpScore = ch3.getScore2(tmp);
                //System.out.println(tmp + " ------ " + tmpScore);
                if(tmpScore > max)
                {
                    max = tmpScore;
                    encry_str = tmp;
                }
            }


        }
        System.out.println(encry_str + " score: " + max);
    }

    private List<String> readFromFile(String filename)
    {
        List<String> strList = new ArrayList<String>();
        try
        {
            //String encoding = "GBK";
            File file = new File(filename);
            if (file.isFile() && file.exists())
            {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                while ((lineTxt = bufferedReader.readLine()) != null)
                {
                    strList.add(lineTxt);
                }
                bufferedReader.close();
                read.close();
            }
            else
            {
                System.out.println("can't find file");
            }
        }
        catch (Exception e)
        {
            System.out.println("fail to read");
            e.printStackTrace();
        }

        return strList;
    }
}
