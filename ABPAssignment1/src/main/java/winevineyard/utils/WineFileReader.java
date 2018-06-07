package winevineyard.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import winevineyard.PersonWineOffer;

public class WineFileReader {
    // This method will remove the duplicate wine id if already bought by person. And Created a map of each person with three wineId.
    public Map<String, String> createPersonWineMapFromFile(File inputFile, String outputFilePath) throws IOException {
        Map<String, String> personWineMap = new LinkedHashMap<String, String>();
        List<String> uniqueWineList = new ArrayList<String>();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            while ((line = reader.readLine()) != null) {
                String[] lineSplit = line.split("\t");
                String personId = lineSplit[0].trim();
                // System.out.println("personId:"+personId);
                String wineId = lineSplit[1].trim();
                // System.out.println("wineId:"+wineId);
                // System.out.println(personId+":"+wineId);
                if (uniqueWineList.contains(wineId)) {
                    continue;
                }
                String wineIdAppend = "";
                if (personWineMap.containsKey(personId)) {
                    String personIdMaxThreeCheck = personWineMap.get(personId);
                    if (personIdMaxThreeCheck.split("\\|").length >= 3) {
                        // Found more than three wines bottle bought by a person. Hence continuing.
                        continue;
                    }
                    wineIdAppend = personWineMap.get(personId) + "|" + wineId;
                    personWineMap.put(personId, wineIdAppend); // Updating the same personId with max 3 windId using separator "|"
                } else {
                    // If the personId is new, hence adding directly to the map.s
                    personWineMap.put(personId, wineId);
                    wineIdAppend = wineId;
                }
                // Since each person will have 3 bottles. So for fast processing into file. Writing the 1000 records into file and taking new hashmap.
                if (personWineMap.size() == 1000) {
                    personWineMap.remove(personId);
                    PersonWineOffer.writePersonWineMapToFile(personWineMap, outputFilePath);
                    personWineMap = new LinkedHashMap<String, String>();
                    personWineMap.put(personId, wineIdAppend);
                }
                uniqueWineList.add(wineId);
            }
        } catch (IOException e) {
            throw new IOException();
        } finally {
            reader.close();
        }
        return personWineMap;
    }
}
