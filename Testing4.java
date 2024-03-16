package osm4maps;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Testing4 {

    public static void main(String[] args) {
        String filePath = "src/main/java/osm4maps/data/alberta-latest.osm"; // File path
        try {
            // Open the file with RandomAccessFile in read mode.
            RandomAccessFile file = new RandomAccessFile(filePath, "r");

            long fileLength = file.length() - 1; // Start from the end of the file
            List<String> last10Lines = new ArrayList<>();
            StringBuilder line = new StringBuilder();

            // Iterate backwards through the file
            for (long filePointer = fileLength; filePointer != -1; filePointer--) {
                file.seek(filePointer); // Seek to a position in the file
                int readByte = file.readByte(); // Read the byte at this position

                // Check for newline and carriage return and build the line
                if (readByte == 0xA) {
                    if (filePointer < fileLength) {
                        last10Lines.add(line.reverse().toString());
                        line.setLength(0); // Reset the line
                    }
                } else if (readByte != 0xD) {
                    line.append((char) readByte);
                }

                if (last10Lines.size() == 50) {
                    break;
                }
            }

            // Reverse the list to display in correct order and print
            Collections.reverse(last10Lines);
            for (String lastLine : last10Lines) {
                System.out.println(lastLine);
            }

            // Close the file.
            file.close();
        } catch (IOException e) {
            // Print any exceptions that occur.
            e.printStackTrace();
        }
    }
}
