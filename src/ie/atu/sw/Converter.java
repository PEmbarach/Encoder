package ie.atu.sw;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Converter {

	public static String[][] convertFileToArray(String filePath) {
		List<String[]> lines = new ArrayList<>(); //Create a list where each element is a array of each CSV line split in columns.

		//Read each line of the CSV file, convert into a new array and split the columns.
		try (Scanner scanner = new Scanner(new File(filePath))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] row = line.split(",");
				lines.add(row);
			}
		} catch (Exception e) {
			System.out.println("[ERROR] Failed to read file: " + e.getMessage());
			return new String[0][0];
		}

		return lines.toArray(new String[0][]);//Convert all new arrays in one 2D array.
	}
}