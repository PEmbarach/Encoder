package ie.atu.sw;

import static java.lang.System.out;

import java.io.*;
import java.util.*;

/**
 * Menu class that provides a console-based user interface for the
 * encoding/decoding application. Handles user interactions, file management,
 * and coordinates between different components.
 */
public class Menu {
	private Scanner s; // Scanner for reading user input
	private boolean keepRunning = true; // Control flag for main application loop
	private static final String CSV_PATH = "./encodings-10000.csv"; // Default path to mapping CSV file
	private String filePath; // Path to the input text file for encoding/decoding
	private String outputFilePath = "./out.txt"; // Default output file path

	/**
	 * Constructor initializes the scanner for user input.
	 */
	public Menu() {
		s = new Scanner(System.in); // Initialize scanner with standard input
	}

	/**
	 * Main application loop that displays the menu and processes user choices.
	 * Continues running until the user selects the quit option.
	 */
	public void start() {
		// Main application loop
		while (keepRunning) {
			showOption(); // Display menu options

			try {
				// Read and parse user choice
				int choice = Integer.parseInt(s.next());

				// Process user selection using switch expression
				switch (choice) {
				case 1 -> specifyText(); // Specify input text file
				case 2 -> specifyOutput(); // Specify output file
				case 3 -> encodeText(); // Encode the specified file
				case 4 -> decodeText(); // Decode the specified file
				case 5 -> {
					// Quit application
					out.println("[INFO] Shutting down...please wait...");
					keepRunning = false; // Break the loop
				}
				default -> out.print("[ERROR] Invalid Selection"); // Handle invalid choices
				}
			} catch (Exception e) {
				// Handle non-numeric input
				out.println("[ERROR] Please enter a valid number");
			}
		}
		// Exit message
		out.println("[INFO] Exiting...Bye!");
	}

	/**
	 * Allows user to specify the path to a text file for encoding/decoding.
	 * Validates the file existence and readability, then displays its content.
	 * 
	 * @return Absolute path to the valid text file, or null if invalid
	 */
	public String specifyText() {
		System.out.println("Please enter the path to your text file.");

		// Reinitialize scanner to clear any previous input issues
		s = new Scanner(System.in);
		String input = s.nextLine().trim(); // Read and clean user input
		File readingFile = new File(input); // Create File object for validation

		// Comprehensive file validation
		if (!readingFile.exists() || !readingFile.isFile() || !readingFile.canRead()) {
			System.out.println("[ERROR] Invalid file.");
			return null; // Return null for invalid files
		}

		filePath = readingFile.getAbsolutePath(); // Store absolute path

		// Read and display file content for user confirmation
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			StringBuilder content = new StringBuilder();
			String line;

			// Read file line by line
			while ((line = reader.readLine()) != null) {
				content.append(line).append(System.lineSeparator()); // Preserve line breaks
			}

			// Display file content with formatting
			System.out.println("\n[TEXT]");
			System.out.println("----------------------------------------");
			System.out.println(content.toString()); // Show file content
			System.out.println("----------------------------------------");
			System.out.println("\nFile accepted: " + filePath);

			return filePath; // Return valid file path
		} catch (IOException e) {
			System.out.println("[ERROR] Could not read file: " + e.getMessage());
			return null; // Return null on read error
		}
	}

	/**
	 * Allows user to specify the output file name for encoded/decoded results.
	 * Automatically adds .txt extension if not provided.
	 * 
	 * @throws IOException If there are issues with file naming
	 */
	private void specifyOutput() throws IOException {
		System.out.println("Please enter the name of your output file.");
		s = new Scanner(System.in); // Reset scanner
		String output = s.nextLine().trim(); // Read user input

		// Ensure file has .txt extension
		if (!output.endsWith(".txt")) {
			output += ".txt"; // Append extension if missing
		}

		outputFilePath = output; // Update output file path
		System.out.println("[INFO] Output file will be saved as: " + outputFilePath);
	}

	/**
	 * Encodes the specified text file using the mapping dictionary. Validates that
	 * an input file has been specified before proceeding.
	 */
	private void encodeText() {
		// Validate that input file has been specified
		if (filePath == null || filePath.isEmpty()) {
			System.out.println("[ERROR] No file specified. Please use option (1) first.");
			return; // Exit if no file specified
		}

		try {
			// Load encoding mapping (words to numbers)
			Map<String, Integer> encodeMap = Mapping.loadEncodingCSV(CSV_PATH);

			// Validate that mapping was loaded successfully
			if (encodeMap.isEmpty()) {
				out.println("[ERROR] CSV not found or empty " + CSV_PATH + ".");
				return; // Exit if mapping is empty
			}

			out.println("[INFO] CSV data loaded for encoding.");

			// Create encoder instance and process the file
			Encoder encoder = new Encoder(encodeMap);
			System.out.println("[INFO] Starting encoding of file: " + filePath);
			encoder.readText(filePath, outputFilePath); // Perform encoding

		} catch (Exception e) {
			// Handle encoding errors
			System.out.println("[ERROR] Failed to encode: " + e.getMessage());
			e.printStackTrace(); // Print stack trace for debugging
		}
	}

	/**
	 * Decodes the specified encoded file using the mapping dictionary. Validates
	 * that an input file has been specified before proceeding.
	 */
	private void decodeText() {
		// Validate that input file has been specified
		if (filePath == null || filePath.isEmpty()) {
			System.out.println("[ERROR] No file specified. Please use option (1) first.");
			return; // Exit if no file specified
		}

		try {
			// Load decoding mapping (numbers to words)
			Map<Integer, String> decodeMap = Mapping.loadDecodingCSV(CSV_PATH);

			// Validate that mapping was loaded successfully
			if (decodeMap.isEmpty()) {
				out.println("[ERROR] CSV not found or empty " + CSV_PATH + ".");
				return; // Exit if mapping is empty
			}

			out.println("[INFO] CSV data loaded for decoding.");

			// Create decoder instance and process the file
			Decoder decoder = new Decoder(decodeMap);
			System.out.println("[INFO] Starting decoding of file: " + filePath);
			decoder.readDecoded(filePath, outputFilePath); // Perform decoding

		} catch (Exception e) {
			// Handle decoding errors
			System.out.println("[ERROR] Failed to decode: " + e.getMessage());
			e.printStackTrace(); // Print stack trace for debugging
		}
	}

	/**
	 * Displays the main menu options to the user. Shows application header and
	 * available functionality choices.
	 */
	private void showOption() {
		// Application header with formatting
		out.println("************************************************************");
		out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		out.println("*                                                          *");
		out.println("*              Encoding Words with Suffixes                *");
		out.println("*                                                          *");
		out.println("************************************************************");

		// Menu options
		out.println("(1) Specify Text File to Encode/Decode");
		out.println("(2) Specify Output File (default: ./out.txt)");
		out.println("(3) Encode Text File");
		out.println("(4) Decode Text File");
		out.println("(5) Quit");

		// Prompt for user input (implied)
	}
}