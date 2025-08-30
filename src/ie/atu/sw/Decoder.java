package ie.atu.sw;

import java.io.*;
import java.util.*;

/**
 * Class responsible for decode a sequence of numbers into words using a
 * codification map pre-defined.
 */

public class Decoder {
	// Map linking each numerical value with the corresponding word
	private final Map<Integer, String> decodeMap;

	/**
	 * Decoder class construction
	 * 
	 * @param decodeMap Decode map containing the codes and corresponding words
	 */
	public Decoder(Map<Integer, String> decodeMap) {
		this.decodeMap = decodeMap;
	}

	/**
	 * Reads a file containing numeric codes, decodes it and saves the result.
	 * 
	 * @param filePath       Path to the input file with the codes
	 * @param outputFilePath Path to the output file for results
	 * @throws FileNotFoundException If the input file is not found
	 * @throws IOException           If a read/write error occurs
	 */
	public void readDecoded(String filePath, String outputFilePath) throws FileNotFoundException, IOException {

		// Use try-with-resources to ensure the BufferedReader is automatically closed
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			StringBuilder sb = new StringBuilder();
			String line;

			// Process the content read from the file
			while ((line = reader.readLine()) != null) {
				sb.append(line).append(" ");
			}

			// Parse the encoded numbers from the file
			String content = sb.toString().trim(); // Remove whitespace at beginning/end

			// Splits content using regex that detects commas and spaces as delimiters
			String[] numberStrings = content.split("[,\\s]+");

			List<Integer> codes = new ArrayList<>();// List to store the numeric codes

			// Convert each numeric string to an integer
			for (String numStr : numberStrings) {
				numStr = numStr.trim(); // Remove blank spaces
				if (!numStr.isEmpty()) { // Ignore empty strings
					try {
						codes.add(Integer.parseInt(numStr)); // Converts string to integers
					} catch (NumberFormatException e) {
						// Handles invalid numbers without interrupting execution
						System.out.println("[WARNING] Skipping invalid number: " + numStr);
					}
				}
			}

			// Decode the codes using the map
			List<String> decodedWords = extractWords(decodeMap, codes);

			// Displays results in the console
//			System.out.println("=== Decoding Results ===");
//			for (String item : decodedWords) {
//				System.out.println(item);
//			}

			// Save the results to a file
			saving(decodedWords, outputFilePath);
		}
		// The BufferedReader is automatically closed here by try-with-resources
	}

	/**
	 * Extracts words corresponding to numeric codes using the decode map.
	 *
	 * @param decodeMap Decode map
	 * @param codes     List of numeric codes to decode
	 * @return List of decoded words (or placeholders for unknown codes)
	 */
	public static List<String> extractWords(Map<Integer, String> decodeMap, List<Integer> codes) {
		List<String> decodeWords = new ArrayList<>(); // List to store decoded words

		// Iterate over each code in the list
		for (int code : codes) {
			if (decodeMap.containsKey(code)) {
				// If the code exists in the map, get the corresponding word
				String word = decodeMap.get(code);
				decodeWords.add(word);
			} else {
				// If the code is not found, add unknown marker
				decodeWords.add("[UNKNOWN:" + code + "]");
			}
		}
		return decodeWords;
	}

	/**
	 * Saves the decoded words to a text file.
	 *
	 * @param decodedWords List of decoded words
	 * @param nameFile     Name of the output file
	 */
	public static void saving(List<String> decodedWords, String nameFile) {
		// Use try-with-resources to ensure the FileWriter is automatically closed
		try (FileWriter writer = new FileWriter(nameFile)) {
			// Output file header
			// writer.write("=== Decoded Result ===\n\n");

			// Join words with spaces to form readable text
			StringBuilder decodedText = new StringBuilder();
			for (int i = 0; i < decodedWords.size(); i++) {
				if (i > 0)
					decodedText.append(" "); // Add space between words (except the first one)
				decodedText.append(decodedWords.get(i));
			}

			// Write the full decoded text
			writer.write(decodedText.toString());

			// Section with each word individually
			// writer.write("\n\n=== Individual Words ===\n");

			// Write each word on a separate line
//			for (String item : decodedWords) {
//				writer.write(item + "\n");
//			}

			// Confirmation message in the console
			System.out.println("\n[INFO] Decoded file saved as: " + nameFile);
		} catch (IOException e) {
			System.err.println("[ERROR] Could not save file: " + e.getMessage());
			e.printStackTrace();
		}
	}
}