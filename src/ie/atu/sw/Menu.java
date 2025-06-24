package ie.atu.sw;

import static java.lang.System.out;

import java.io.File;
import java.util.Scanner;

public class Menu {
	private Scanner s;
	private boolean keepRunning = true;
	
	
	private static final String DEFAULT_PATH = "./encodings-10000.csv";
	
	public Menu() {
		s = new Scanner(System.in);
	}

	public void start() {
		while(keepRunning) {
			showOption();
			
			int choice = Integer.parseInt(s.next());
			switch (choice) {
			case 1 -> specifyMapping();
			case 2 -> specifyText();
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
	
	public void specifyMapping() {
		System.out.println("Please enter the path to your mapping file.");
        System.out.println("Or press Enter to use the default: " + DEFAULT_PATH);
        System.out.print("Mapping file path: ");
        
        s = new Scanner(System.in);//Read user input path or default.
        String input = s.nextLine().trim();//Read all line and remove blank spaces.
        String filePath = input.isEmpty() ? DEFAULT_PATH : input;//Verify if user put a path or press Enter for the default path.
        
        File mappingFile = new File(filePath);
        
        //Validation
        if (!mappingFile.exists()) {
        	mappingFile = new File(DEFAULT_PATH);
        	System.out.println("[ERROR] File does not exist.");
        	return;
        }
        
        if (!mappingFile.isFile()) {
        	mappingFile = new File(DEFAULT_PATH);
        	System.out.println("[ERROR] The specified path is not a file.");
        	return;
        }
        
        if (!mappingFile.canRead()) {
        	mappingFile = new File(DEFAULT_PATH);
        	System.out.println("[ERROR] Can not read the specified file.");
        	return;
        }
        
        System.out.println("File accepted: " + mappingFile.getAbsolutePath());
        
        //s.close();
	}

	private void specifyText() {
		System.out.println("[INFO] text");
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
		out.println("(1) Specify Mapping File");
		out.println("(2) Specify Text File to Encode");
		out.println("(3) Specify Output File (default: ./out.txt)");
		out.println("(4) Encode Text File");
		out.println("(5) Decode Text File");
		out.println("(6) Quit");
	}

}
