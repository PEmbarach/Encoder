package ie.atu.sw;

import java.io.*;
import java.util.*;

public class Encoder {
    private final Map<String, Integer> mapCSV;
    
    public Encoder(Map<String, Integer> mapCSV) {
        this.mapCSV = mapCSV;
    }
    
    public void readText(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String line;

            // Read the file line by line and append to StringBuilder
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(" ");
            }

            // Split the string by various delimiters
            String[] words = sb.toString().split("[\\s,\\.?!;:\"()]+");

            for (String word : words) {
                if (!word.isEmpty()) {
                    List<Integer> encodedParts = new ArrayList<>();
                    String remaining = word.toLowerCase();
                    
                    // Process the word from end to beginning
                    while (remaining.length() > 0) {
                        boolean found = false;
                        
                        // Try to find the largest possible match from the end
                        for (int i = remaining.length(); i > 0; i--) {
                            String part = remaining.substring(0, i);
                            Integer value = mapCSV.get(part);
                            
                            if (value != null) {
                                encodedParts.add(value);
                                remaining = remaining.substring(i);
                                found = true;
                                break;
                            }
                        }
                        
                        if (!found) {
                            // No match found for any part - use the [???] token which is mapped to 0
                            if (remaining.length() > 0) {
                                encodedParts.add(0); // Using 0 to represent [???]
                                remaining = "";
                            }
                        }
                    }
                    
                    // Prepare the output
                    StringBuilder output = new StringBuilder();
                    for (int i = 0; i < encodedParts.size(); i++) {
                        if (i > 0) output.append(", ");
                        output.append(encodedParts.get(i));
                    }
                    String encodeString = output.toString();
                    
                    File file = new File("Exemple.txt");
                    file.createNewFile();
                    
                    Writer targetFileWriter = new FileWriter(file);
                    targetFileWriter.write(encodeString);
                    targetFileWriter.close();
                    
//                    try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
//                    	writer.append(output.toString());
//                    	if (writer != null) writer.close();
//                    } 
                    //System.out.println(output.toString());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[ERROR] Failed to encode the file.");
        }
    }
}