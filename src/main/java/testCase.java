import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu on 18-6-23.
 */
public class testCase {
    private static String ransomwareData="RansomwareData.csv";

    public static void main(String args[]) throws IOException {
       // CSVParser parser= new CSVParserBuilder().withSeparator(',').build();
        BufferedReader br = new BufferedReader(new FileReader(ransomwareData));
       // CSVReader reader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();
        List<String> rows=new ArrayList<String>();
        rows.add(br.readLine());
        System.out.println(rows.size());
        int i=3;
        while(i<999){
           // System.out.println("b"+'\n');
            for (String j:rows){
                System.out.print(j);

            }
            System.out.println();
            i++;
            if (i>6) break;
        }
    }
}
