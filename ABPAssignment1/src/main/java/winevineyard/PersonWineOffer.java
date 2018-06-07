package winevineyard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.springframework.util.ResourceUtils;

import winevineyard.utils.WineFileReader;

public class PersonWineOffer {
    public static void main(String[] args) throws FileNotFoundException {
        WineFileReader wineFileReader = new WineFileReader();
        File file = ResourceUtils.getFile("classpath:assignmentABPFile1.txt");
        String outputFilePath = "D:/WorkPending/outputAssignmentABPFile1.txt";
        Map<String, String> personWineMap = null;
        try {
            // Below map will have remaining each person with three wines separated with "|"
            personWineMap = wineFileReader.createPersonWineMapFromFile(file, outputFilePath);
            System.out.println(personWineMap.size());
            writePersonWineMapToFile(personWineMap, outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Here PersonWineMap will have single personId with value separated by "|" which containes unique wineid.
    public static boolean writePersonWineMapToFile(Map<String, String> personWineMap, String outputFilePath) throws IOException {
        BufferedWriter writer = null;
        File file = new File(outputFilePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            //writer = new BufferedWriter(new FileWriter(file));
            writer = new BufferedWriter(new FileWriter(file, true));
            for (Map.Entry<String, String> entryMap : personWineMap.entrySet()) {
                String personId = entryMap.getKey();
                String wineIdCollection = entryMap.getValue();
                String[] wineIdArr = wineIdCollection.split("\\|");
                for (String wineId : wineIdArr) {
                    writer.append(personId);
                    writer.append("\t");
                    writer.append(wineId);
                    writer.append("\n");
                }
            }
        } catch (IOException e) {
            throw new IOException();
        } finally {
            writer.close();
        }
        return true;
    }
}
