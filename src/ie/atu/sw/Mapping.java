package ie.atu.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Mapping {
	public static Map<String, Integer> loadCSV(String csvFilePath) {
		Map<String, Integer> wordToNumberMap = new HashMap<>();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFilePath)))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				// To split each line
				String[] parts = line.split(",");

				if (parts.length >= 2) // Verify if CVS has the correct format
				{
					String word = parts[0].trim(); // Removes extra spaces
					int number = Integer.parseInt(parts[1].trim());
					wordToNumberMap.put(word, number);
				}
			}
		} catch (Exception e) {
			System.out.println("[ERROR] Couldn't read CSV: " + e.getMessage());
		}
		return wordToNumberMap;
	}
}