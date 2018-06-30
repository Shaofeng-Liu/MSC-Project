import java.io.IOException;

/**
 * Created by liu on 18-6-23.
 */
public class testCase {
    private static String ransomwareData = "RansomwareData.csv";

    public static void main(String args[]) throws IOException {
        String badFile="";
        //float entropy= File_Entropy.calculateEntropy(badFile);
        //System.out.println(badFile+" average entropy is "+entropy);
        File_Entropy.calculateEntropy();
    }
}
