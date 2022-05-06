package com.tsystems.autotestuni.basic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IoNetworkBasic {

    /**
     * Write a method which creates a file with a given name on your Desktop.
     * Place into the file any random text of your choosing with 50 lines.
     * If the file already exists, then delete it first.
     *
     * @param filename a name of the file
     * @return created file
     * @throws IOException throws if an I/O error happened
     */
    public static File createFile(final String filename) throws IOException {
        final String desktop = System.getProperty("user.home")
                + File.separator
                + "Desktop"
                + File.separator;
        final File file = new File(desktop + filename);

        if (file.exists()) {
            file.delete();
        }

        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            for (int i = 0; i < 50; i++) {
                writer.write("The line number " + i + System.lineSeparator());
            }
        }
        return file;
    }

    /**
     * Write a method which reads a file with a given name and print its content out the next way:
     *      - All odd lines (0, 2, 4, ...) should be printed out to the standard output stream
     *      - All even lines (1, 3, 5, ...) should be printed out to the standard error stream
     * If the file already exists, then delete it first.
     *
     * @param filename a name of the file
     * @throws IOException throws if an I/O error happened
     */
    public static void readFile(String filename) throws IOException {
        File file = new File(filename);

        try (FileReader fileReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String nextLine = "";
            for (int i = 0; ; i++) {
                nextLine = reader.readLine();

                if (nextLine == null) {
                    break;
                }

                if (i % 2 == 0) {
                    System.out.println(nextLine);
                } else {
                    System.err.println(nextLine);
                }
            }
        }
    }
}
