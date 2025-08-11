package ie.atu.sw;

import static java.lang.System.out;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Scanner;

public class Menu {
	private Scanner s;
	private boolean keepRunning = true;
	private Map<String, Integer> mapCSV; // Store the CSV map.
	private static final String CSV_PATH = "./encodings-10000.csv";// Specify the default path.
	private String filePath = CSV_PATH;// Save Path of selected file.
	private Encoder encoder;
	private String outputFilePath = "./out.txt";//Default Value
	
	public Menu() {
		s = new Scanner(System.in);
	}
	
	
	public void start() {
		while (keepRunning) {
			showOption();

			try {
				int choice = Integer.parseInt(s.next());
				switch (choice) {
				case 1 -> specifyText();
				case 2 -> mappingText();
				case 3 -> specifyOutput();
				case 4 -> encodeText();
				case 5 -> decodeText();
				case 6 -> {
					out.println("[INFO] Shutting down...please wait...");
					keepRunning = false;
				}
				default -> out.print("[ERROR] Invalid Selection");
				}
			} catch (Exception e) {
				out.println("[ERROR] Please enter a valid number");
			}
		}
		out.println("[INFO] Exiting...Bye!");
	}

	// SpecifyText allow user to give a .txt path file.
	public String specifyText() {
		System.out.println("Please enter the path to your text file.");

		s = new Scanner(System.in);
		String input = s.nextLine().trim();// Read all line and remove blank spaces.
		File readingFile = new File(input);

		// Validation
		if (!readingFile.exists() || !readingFile.isFile() || !readingFile.canRead()) {
			System.out.println("[ERROR] Invalid file.");
			return null;
		}

		filePath = readingFile.getAbsolutePath();

		// Print the content of the loaded file.
		try {
			String text = Files.readString(Path.of(filePath));

			System.out.println("\n[TEXT]");
			System.out.println("----------------------------------------");
			System.out.println(text);
			System.out.println("----------------------------------------");
			System.out.println("\nFile accepted: " + filePath);

			return filePath;
		} catch (IOException e) {
			System.out.println("[ERROR] Could not read file: " + e.getMessage());
			return null;
		}
	}

	// MappingText map and converts .csv in to 2D array.
	private void mappingText() {
        this.mapCSV = Mapping.loadCSV(CSV_PATH);

        if (mapCSV.isEmpty()) {
            out.println("[ERROR] CSV not found or empty " + CSV_PATH + ".");
        } else {
            out.println("[INFO] CSV data loaded into 2D array:");
            mapCSV.forEach((key, value) -> out.println(key + "->" + value));
            
            this.encoder = new Encoder(this.mapCSV);
        }
    }

	private void specifyOutput() throws IOException {
		System.out.println("Please enter the name of you output file.");
		s = new Scanner(System.in);
		String output = s.nextLine().trim();// Read all line and remove blank spaces.
		if (!output.endsWith(".txt")) {
			output += ".txt";
		}
		outputFilePath = output;
		System.out.println("[INFO] Outpu file will be saved as: " + outputFilePath);
		
		
	}

	private void encodeText() {
		if(filePath == null || filePath.isEmpty()) {
			 System.out.println("[ERROR] No file specified. Please use option (1) first.");
		     return;
		}
		
		if(encoder == null) {
			System.out.println("[ERROR] CSV mapping not loaded. Please use option (2) first.");
	        return;
		}
		
		System.out.println("[INFO] Starting encoding of file: " + filePath);
	    encoder.readText(filePath, outputFilePath);
	    
	}

	private void decodeText() {
		System.out.println("[INFO] Decode");
	}

	private void showOption() {
		out.println("************************************************************");
		out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		out.println("*                                                          *");
		out.println("*              Encoding Words with Suffixes                *");
		out.println("*                                                          *");
		out.println("************************************************************");
		out.println("(1) Specify Text File to Encode");
		out.println("(2) Mapping Text");
		out.println("(3) Specify Output File (default: ./out.txt)");
		out.println("(4) Encode Text File");
		out.println("(5) Decode Text File");
		out.println("(6) Quit");
	}

}
