package ie.atu.sw;

import static java.lang.System.out;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

public class Menu {
	private Scanner s;
	private boolean keepRunning = true;
	private Map<String, Integer> mapCSV; // Store the CSV map.
	private static final String CSV_PATH = "./Test.csv";// Specify the default path.
	private String filePath = CSV_PATH;// Save Path of selected file.

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
					System.out.println("[INFO] Shutting down...please wait...");
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
		System.out.println("File accepted: " + filePath);
		return filePath;
	}

	// MappingText converts .csv in to 2D array.
	private void mappingText() {
		this.mapCSV = Mapping.loadCSV(CSV_PATH);

		if (mapCSV.isEmpty()) {
			System.out.println("[ERROR] CSV not found or empty " + CSV_PATH + ".");
		} else {
			System.out.println("[INFO] CSV data loaded into 2D array:");
			for (Map.Entry<String, Integer> entry : mapCSV.entrySet()) {
				System.out.println(entry.getKey() + "->" + entry.getValue());
			}
		}

	}

	private void specifyOutput() {
		System.out.println("[INFO] output");
	}

	private void encodeText() {
		System.out.println("[INFO] encode");
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
