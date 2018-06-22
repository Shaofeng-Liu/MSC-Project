
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class DataProcessor {
    private static String variableName="VariableNames.txt";
    private static String ransomwareData="RansomwareData.csv";
    private static  TypeToRangeMap trMap=null;
    private static HashMap<String, VectorRange> rMap;
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        trMap=new TypeToRangeMap(variableName);
        rMap=trMap.getVectorRangeMap();
        //System.out.println(rMap);
        formatDatafile();

    }

    private static void formatDatafile() throws IOException {
        rMap.remove("REG");
        rMap.remove("STR");

        CSVParser parser= new CSVParserBuilder().withSeparator(',').build();
        BufferedReader br = new BufferedReader(new FileReader(ransomwareData));
        CSVReader reader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();
        List<String[]> rows;
        for (String key : rMap.keySet()){
            for(int i = rMap.get(key).getStartPoint()-1; i<rMap.get(key).getEndPoint()-1;i++){
            }
        }

        }

    }

