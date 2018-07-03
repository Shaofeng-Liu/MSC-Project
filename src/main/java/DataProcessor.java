import com.opencsv.*;

import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataProcessor {
    private static final String formattedRandomwareData = "FormatedData.csv";
    private static String variableName = "VariableNames.txt";
    private static String ransomwareData = "RansomwareData.csv";
    private static TypeToRangeMap trMap = null;
    private static HashMap<String, VectorRange> rMap;

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        trMap = new TypeToRangeMap(variableName);
        rMap = trMap.getVectorRangeMap();
        System.out.println(rMap);
        //formatDatafile();
        addHeader(ransomwareData,variableName);

    }

    private static void formatDatafile() throws IOException, FileNotFoundException {
        for (String key : rMap.keySet()) {
            if (rMap.get(key).getOrder() == 9) continue;
            else if (rMap.get(key).getOrder() > 5) {
                rMap.get(key).setOrder((rMap.get(key).getOrder() - 1));
            }
        }
        rMap.remove("REG");
        rMap.remove("STR");

        CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
        BufferedReader br = new BufferedReader(new FileReader(ransomwareData));
        CSVReader csvRreader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();
        //List<String[]> rows = new ArrayList();
        List<String[]> newRecordList = new ArrayList();
        File formatedFile = new File(formattedRandomwareData);
        if (formatedFile.exists()) {
            System.out.println("Data already formated. \nDeleting exist file......");
            formatedFile.delete();
            //System.exit(0);
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(formattedRandomwareData));
        ICSVWriter csvWriter = new CSVWriterBuilder(bw).withParser(parser).build();
        //String orderOfRecord="ID    Label   RansomwareFamily    ";
        //int iterator=0;
        String[] raw;
        String[] newRec;
        while ((raw = csvRreader.readNext()) != null) {
            newRec = new String[8];
            newRec[0] = raw[0];
            newRec[1] = raw[1];
            newRec[2] = raw[2];
            for (String key : rMap.keySet()) {
                int start = rMap.get(key).getStartPoint() - 1;
                int end = rMap.get(key).getEndPoint() - 1;
                int total = 0;
                for (int i = start; i <= end; i++) {
                    total += Integer.parseInt(raw[i]);
                }
                newRec[rMap.get(key).getOrder()] = "" + total;
            }
            newRecordList.add(newRec);


        }
        String[] headers = {"ID", "Lable", "RansomwareFamily", "", "", "", "", ""};
        for (String key : rMap.keySet()) {
            headers[rMap.get(key).getOrder()] = key;
            //System.out.print(key+" "+rMap.get(key).getOrder()+" \n");
        }


        System.out.println("Adding record header to CSV file......");
        csvWriter.writeNext(headers);
        System.out.println("Adding all record to CSV file......");
        csvWriter.writeAll(newRecordList);
        csvWriter.flush();
        csvWriter.close();
        csvRreader.close();

    }
    private static void addHeader(String targetCSV,String hdrFile) throws IOException {
        String[] hdr=trMap.getVarNames(hdrFile);
        File ransomCSV = new File("RansomwareDataWithHeaders.csv");
        //Read all records and create new file with headers.
        CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
        BufferedReader br = new BufferedReader(new FileReader(ransomwareData));
        CSVReader csvRreader = new CSVReaderBuilder(br).withCSVParser(parser).build();
        System.out.println("Reading original dataset......");
        BufferedWriter bw = new BufferedWriter(new FileWriter(ransomCSV));
        ICSVWriter csvWriter = new CSVWriterBuilder(bw).withParser(parser).build();

        System.out.println("Writing header to file......");
        csvWriter.writeNext(hdr);
        System.out.println("Writing data to file......");
        csvWriter.writeAll(csvRreader.readAll());
        System.out.println("Update complete......");
        csvWriter.close();
        csvRreader.close();
    }


}

