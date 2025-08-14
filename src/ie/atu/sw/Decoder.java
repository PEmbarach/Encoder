package ie.atu.sw;

import java.io.*;
import java.util.*;

public class Decoder {
	// Make a map linking each word with the corresponding numerical value.
	private final Map<String, Integer> mapCSV;

	public Decoder(Map<String, Integer> mapCSV) {
		this.mapCSV = mapCSV;
	}

	public void readDecoded(String filePath) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			StringBuilder sb = new StringBuilder();
			String line;

			// Read the file line by line and append to StringBuilder
			while ((line = reader.readLine()) != null) {
				sb.append(line).append(" ");
			}
			// Split the string by various delimiters
			String[] codes = sb.toString().split("[\\s,\\.?!;:\"()]+");
			

			for (String code : codes) {
				if (!code.isEmpty()) {
					List<String> decodedPart = new ArrayList<>();
					Integer numbers = code;
					
					while (numbers> 0) {
						for(int i = numbers; i > 0; i--) {
							Integer value = numbers;
							String word = mapCSV.get(value);
						}
					}
				}	
			}		
	}
}
}


//REMINDER
/*My process to decoded is wrong. What I have to do is, convert the encoded .txt, which contains
 * only numbers into a array of INT, not into a array of Strings. Then compare Int vs Int, extract
 * the corresponding word and save this word into a new .txt.
 */







