import java.io.*;
import java.util.HashMap;

public class TypeToRangeMap implements Serializable {
    private static String variableName;
    private static HashMap<String, VectorRange> vectorRangeMap;
    private static String mapPath = "RangeMap.txt";

    public TypeToRangeMap(String variableName) throws IOException, ClassNotFoundException {
        this.variableName = variableName;
        this.vectorRangeMap = new HashMap<String, VectorRange>();
        prepareNewDataset();
    }

    private void readMap() throws IOException, ClassNotFoundException {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(mapPath))) {
            this.vectorRangeMap = (HashMap<String, VectorRange>) is.readObject();
        }

    }

    private void writeMap() throws IOException {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(mapPath))) {

            os.writeObject(vectorRangeMap);
        }
    }

    public void prepareNewDataset() throws IOException, ClassNotFoundException {

        File mapFile = new File(mapPath);


        if (mapFile.exists()) {

            readMap();

        } else {

            BufferedReader br = new BufferedReader(new FileReader(variableName));
            String currentLine;
            int currentLineNo = 1;
            String previousVectorType = "";
            String currentVectorType;
            VectorRange currentRange = new VectorRange();

            while ((currentLine = br.readLine()) != null) {
                if (currentLineNo > 3) {
                    currentVectorType = currentLine.substring(currentLine.indexOf(";") + 1, currentLine.indexOf(":"));
                    if (!currentVectorType.equals(previousVectorType)) {
                        if (previousVectorType == "") {
                            //System.out.println(previousVectorType+" "+currentVectorType);
                            //vectorRangeMap.put(currentVectorType,currentRange);
                            currentRange.setStartPoint(currentLineNo);
                            currentRange.setOrder(3);
                            previousVectorType = currentVectorType;
                            //System.out.println(previousVectorType+" "+currentVectorType);

                        } else {
                            currentRange.setEndPoint(currentLineNo - 1);
                            currentRange.setTotal();
                            vectorRangeMap.put(previousVectorType, currentRange);
                            int order = currentRange.getOrder();
                            System.out.println(currentRange);
                            currentRange = new VectorRange();
                            currentRange.setOrder(order + 1);
                            currentRange.setStartPoint(currentLineNo);
                            previousVectorType = currentVectorType;
                        }

                    } else if (currentLineNo == 30970) {
                        currentRange.setEndPoint(currentLineNo);
                        currentRange.setTotal();
                        vectorRangeMap.put(currentVectorType, currentRange);
                    }
                }
                currentLineNo++;
            }
            mapFile.createNewFile();
            writeMap();
        }
    }

    public HashMap<String, VectorRange> getVectorRangeMap() {
        return vectorRangeMap;
    }

    public String toString() {
        String output = "";
        for (String key : vectorRangeMap.keySet()) {
            output += key + ": " + vectorRangeMap.get(key) + ".\n";
        }
        return output;
    }
}
