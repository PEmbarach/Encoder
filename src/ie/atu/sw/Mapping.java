package ie.atu.sw;

import java.io.*;
import java.util.*;

/**
 * Utility class for loading and processing CSV mapping files. Provides methods
 * to create mapping dictionaries for both encoding and decoding operations.
 */
public class Mapping {

	/**
	 * Loads a CSV file and returns a mapping dictionary for encoding purposes. The
	 * resulting map uses words as keys and their corresponding numerical values.
	 * 
	 * CSV format expected: "word,number" on each line Example: "hello,42"
	 * 
	 * @param csvPath The file path to the CSV mapping file
	 * @return Map<String, Integer> where key is the word and value is its numerical
	 *         code
	 */
	public static Map<String, Integer> loadEncodingCSV(String csvPath) {
		// Create a HashMap to store word-to-number mappings
		Map<String, Integer> encodeMap = new HashMap<>();

		// Use try-with-resources for automatic resource management
		try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
			String line;

			// Read the CSV file line by line
			while ((line = reader.readLine()) != null) {
				// Remove leading and trailing whitespace from the line
				line = line.trim();

				// Skip empty lines
				if (line.isEmpty())
					continue;

				// Split the line into maximum 2 parts using comma as delimiter
				// This handles cases where words might contain commas
				String[] parts = line.split(",", 2); // Limit to 2 parts to preserve commas in values

				// Ensure the line has both word and number parts
				if (parts.length >= 2) {
					try {
						// Extract and clean the word (first part)
						String word = parts[0].trim();

						// Extract, clean and parse the numerical value (second part)
						Integer value = Integer.parseInt(parts[1].trim());

						// Add the mapping to the dictionary
						encodeMap.put(word, value);

					} catch (NumberFormatException e) {
						// Handle lines with invalid numerical values
						System.out.println("[WARNING] Skipping invalid line: " + line);
					}
				}
			}

			// Print success message with count of loaded entries
			System.out.println("[INFO] Loaded " + encodeMap.size() + " entries for encoding.");

		} catch (IOException e) {
			// Handle file reading errors
			System.out.println("[ERROR] Could not read CSV file: " + e.getMessage());
		}

		return encodeMap;
	}

	/**
	 * Loads a CSV file and returns a mapping dictionary for decoding purposes. The
	 * resulting map uses numerical values as keys and their corresponding words.
	 * 
	 * CSV format expected: "word,number" on each line (same as encoding file)
	 * Example: "hello,42" becomes key:42, value:"hello"
	 * 
	 * @param csvPath The file path to the CSV mapping file
	 * @return Map<Integer, String> where key is the numerical code and value is the
	 *         word
	 */
	public static Map<Integer, String> loadDecodingCSV(String csvPath) {
		// Create a HashMap to store number-to-word mappings
		Map<Integer, String> decodeMap = new HashMap<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
			String line;

			// Process each line of the CSV file
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty())
					continue; // Skip blank lines

				// Split into maximum 2 parts to handle commas in values
				String[] parts = line.split(",", 2);

				if (parts.length >= 2) {
					try {
						// Extract the word and numerical value
						String word = parts[0].trim();
						Integer value = Integer.parseInt(parts[1].trim());

						// Store with number as key and word as value (reverse of encoding map)
						decodeMap.put(value, word);

					} catch (NumberFormatException e) {
						// Handle invalid numerical values gracefully
						System.out.println("[WARNING] Skipping invalid line: " + line);
					}
				}
			}

			// Success notification with entry count
			System.out.println("[INFO] Loaded " + decodeMap.size() + " entries for decoding.");

		} catch (IOException e) {
			// Error handling for file access issues
			System.out.println("[ERROR] Could not read CSV file: " + e.getMessage());
		}

		return decodeMap;
	}
}