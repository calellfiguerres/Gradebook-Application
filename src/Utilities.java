import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A set of utility methods
 */
public class Utilities {
    /**
     * Write the given contents to the given filename
     *
     * @param filename The name of the file to write to
     * @param contents The contents to write to that file
     */
    public static void writeToFile(String filename, String contents) {
        File file = new File(filename);
        try {
            if (file.createNewFile()) {
                FileWriter writer = new FileWriter(filename);
                writer.write(contents);
                writer.close();
                System.out.println("Successfully created and wrote file '" + filename + "'");
            } else {
                System.out.println("File '" + filename + "' already exists. Skipping...");
            }
        } catch (IOException e) {
            System.out.println("Unable to create file '" + filename + "'. Skipping...");
        }
    }

    /**
     * A quick method to capitalize the first letter of a String
     * @param str The String to capitalize
     * @return The capitalized String
     */
    public static String capitalize(String str) {
        if (str == null || str.equals("")) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * Takes in a line of values separated by commas and separates them into an array
     *
     * @param line A line of comma-separated values
     * @return The values in an array
     */
    public static String[] parseCsvLine(String line) {
        ArrayList<String> items = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder item = new StringBuilder();
        for (char c : line.toCharArray()) {
            if (c == ',' && !inQuotes) {
                items.add(item.toString());
                item = new StringBuilder();
            } else if (c == '"') {
                inQuotes = !inQuotes;
            } else {
                item.append(c);
            }
        }
        items.add(item.toString());
        String[] toReturn = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            toReturn[i] = items.get(i);
        }
        return toReturn;
    }
}
