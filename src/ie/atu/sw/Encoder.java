package ie.atu.sw;

import java.io.*;
import java.util.*;

/**
 * Encoder class that converts text into numerical codes using a predefined
 * mapping. The encoder processes words by breaking them down into parts and
 * finding corresponding numerical values in the provided mapping dictionary.
 */
public class Encoder {
	// Mapping dictionary linking each word/part with its corresponding numerical value
	private final Map<String, Integer> mapCSV;

	/**
	 * Constructor for the Encoder class.
	 * 
	 * @param mapCSV The mapping dictionary containing word-to-number relationships
	 */
	public Encoder(Map<String, Integer> mapCSV) {
		this.mapCSV = mapCSV; // Initialize the mapping dictionary
	}

	/**
	 * Reads a text file, encodes its content using the mapping dictionary, and
	 * saves the encoded numerical sequence to an output file.
	 * 
	 * @param filePath       Path to the input text file
	 * @param outputFilePath Path to the output file for encoded results
	 */
	public void readText(String filePath, String outputFilePath) {
		// Use try-with-resources to ensure proper resource management
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			StringBuilder sb = new StringBuilder(); // Accumulates file content
			String line;

			// Read the file line by line and append to StringBuilder
			while ((line = reader.readLine()) != null) {
				sb.append(line).append(" "); // Add space between lines
			}

			// Split the content using regex that detects various word delimiters:
			// spaces, commas, periods, question marks, exclamation points,
			// semicolons, colons, quotes, and parentheses
			String[] words = sb.toString().split("[\\s,\\.?!;:\"()]+");

			// StringBuilder to accumulate all encoded text with comma separation
			StringBuilder fullOutput = new StringBuilder();

			// Process each word from the split array
			for (String word : words) {
				if (!word.isEmpty()) { // Skip empty strings
					List<Integer> encodedParts = new ArrayList<>(); // Stores encoded parts of current word
					String remaining = word.toLowerCase(); // Convert to lowercase for case-insensitive matching

					// Process the word from end to beginning using greedy matching
					while (remaining.length() > 0) {
						boolean found = false; // Flag to track if a match was found

						// Try to find the largest possible match from the end (greedy algorithm)
						// This ensures we get the longest matching substring first
						for (int i = remaining.length(); i > 0; i--) {
							String part = remaining.substring(0, i); // Get substring from start to position i
							Integer value = mapCSV.get(part); // Look up the substring in the mapping dictionary

							if (value != null) {
								// Found a match - add the numerical value and remove the matched part
								encodedParts.add(value);
								remaining = remaining.substring(i); // Remove the processed part
								found = true;
								break; // Exit the inner loop once a match is found
							}
						}

						if (!found) {
							// No match found for any part of the remaining string
							// Use the special token [???] which should be mapped to 0
							encodedParts.add(0); // Using 0 to represent unknown parts
							remaining = ""; // Clear the remaining string to exit the loop
						}
					}

					// Add the encoded parts to the full output with comma separation
					for (int i = 0; i < encodedParts.size(); i++) {
						if (fullOutput.length() > 0)
							fullOutput.append(", "); // Add comma separator
						fullOutput.append(encodedParts.get(i)); // Append the numerical code
					}

					// Debug output: print encoded parts for each word
					System.out.println(encodedParts);
				}
			}

			// Write the complete encoded output to the target file
			// Using try-with-resources for automatic resource management
			try (Writer targetFileWriter = new FileWriter(outputFilePath, false)) {
				targetFileWriter.write(fullOutput.toString()); // Write the encoded string
			}

			// Success message
			System.out.println("[INFO] Output saved to: " + outputFilePath);

		} catch (Exception e) {
			// Error handling for any exceptions during the encoding process
			e.printStackTrace(); // Print stack trace for debugging
			System.out.println("[ERROR] Failed to encode the file.");
		}
	}
}