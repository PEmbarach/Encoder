package ie.atu.sw;

import static java.lang.System.out;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;


public class Menu {
	private Scanner s;
	private boolean keepRunning = true;
	private static final String DEFAULT_PATH = "./Test.csv";//Specify the default path.
	
	private String filePath = DEFAULT_PATH;//Save Path of selected file.

	public Menu() {
		s = new Scanner(System.in);
	}

	public void start() {
		while (keepRunning) {
			showOption();

			int choice = Integer.parseInt(s.next());
			switch (choice) {
			case 1 -> specifyText();
			case 2 -> mappingText();
			case 3 -> specifyOutput();
			case 4 -> encodeText();
			case 5 -> dencodeText();
			case 6 -> {
				System.out.println("[INFO] Shutting down...please wait...");
				keepRunning = false;
			}
			default -> out.print("[ERROR] Invalid Selection");
			}
		}
		out.println("[INFO] Exiting...Bye!");
	}

	// SpecifyText allow user to give a path or use the default .csv file.
	public String specifyText() {
		System.out.println("Please enter the path to your mapping file.");
		System.out.println("Or press Enter to use the default: " + DEFAULT_PATH);
		System.out.print("Mapping file path: ");

		s = new Scanner(System.in);// Read user input path or default.
		String input = s.nextLine().trim();// Read all line and remove blank spaces.
		String selectedPath = input.isEmpty() ? DEFAULT_PATH : input;// Verify if user put a path or press Enter for the
																	// default path.
		File readingFile = new File(selectedPath);

		// Validation
		if (!readingFile.exists() || !readingFile.isFile() || !readingFile.canRead()) {
			System.out.println("[ERROR] Invalid file. Using default: " + DEFAULT_PATH);
			return filePath;
		}

		filePath = readingFile.getAbsolutePath();
		System.out.println("File accepted: " + filePath);
		return filePath;
	}

	// MappingText converts .csv in to 2D array.
	private void mappingText() {
		String[][] data = Converter.convertFileToArray(filePath);//Read the CSV.

		System.out.println("[INFO] CSV data loaded into 2D array:");
		for (String[] row : data) {
			System.out.println(Arrays.toString(row));//Print each line.
		}

	}

	private void specifyOutput() {
		System.out.println("[INFO] output");
	}

	private void encodeText() {
		System.out.println("[INFO] encode");
	}

	private void dencodeText() {
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
