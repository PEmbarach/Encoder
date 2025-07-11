package ie.atu.sw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

public class Encoder {
    private final Map<String, Integer> mapCSV;
    
    public Encoder(Map<String, Integer> mapCSV) {
        this.mapCSV = mapCSV;
    }
    
    public void encodeFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String line;

            // Read the file line by line and append to StringBuilder
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(" ");
            }

            // Split the string by comma, pipe, file, and dot
            // and print each word on a new line
            String[] words = sb.toString().split("[\\s,\\.?!;:\"()]+");

            for (String word : words) {
                if (!word.isEmpty()) {
                    Integer value = mapCSV.get(word.toLowerCase());
                    if (value != null) {
                        System.out.println(word + " -> " + value);
                    } else {
                        System.out.println(word + " -> [NOT FOUND]");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[ERROR] Failed to encode the file.");
        }
    }
}