import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

public class File_Entropy {
    private static String prifix = "BadFiles/";
    private  static String ransomName="";
    private String badFile="";
    public File_Entropy(){

    }

    public static float calculateEntropy(String targetRansom) throws IOException {
        ransomName=targetRansom;
        String filePath=prifix+ransomName;
        File folder=new File(filePath);
        String[] files=folder.list();
        if (folder.list()==null){
            System.out.println("No record for "+targetRansom);
        }
        float avrg = 0;
        float total=0;
        for (String file : files){

            total+=calculateFileEntropy(filePath+'/'+file);
        }
        avrg=total/files.length;
        System.out.println("The average entropy of file infected by "+ransomName+" is: "+avrg);
        return  avrg;
    }
    public static void  calculateEntropy() throws IOException {
        File ranFolder=new File(prifix);
        String[] ranFiles=ranFolder.list();
        HashMap<String,Float> list=new HashMap<>();
        System.out.println(prifix);
        for (String file:ranFiles){
            list.put(file,calculateEntropy(file));
            System.out.println("===================Next ransomware=========================");
        }

    }

    private static float log2(float value){
        return (float)(Math.log(value)/Math.log(2));
    }

    public static float calculateFileEntropy(String file) throws IOException {
        int fileSize;
        int[] freqList=new int[256];
        float result=0;

        Arrays.fill(freqList,0);
        Path filePath= Paths.get(file);
        //System.out.println(file);
        byte[] byteArr = Files.readAllBytes(filePath);
        fileSize=byteArr.length;
        //System.out.println("File size is: " +fileSize);

        for(byte b :byteArr){
            //System.out.println("byte value: "+b);
            int ptr=b & 0xff;
            freqList[ptr]+=1;
        }


        for (int i :freqList){
            if(i>0){
                float freq=(float)i/fileSize;
                result=result+freq*log2(freq);
            }
        }
        result= -result;
        //System.out.println("Entropy of file: "+file+" is :"+result);


        return result;

    }
}
