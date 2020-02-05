package com.personalprojects.app;

import java.io.*;

public class FileIoHandler {
    private FileIoHandler() {

    }

    private static void writeToFile(String fileName, String toWrite)
            throws IOException {
        File outFile = new File(fileName);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile))) {
            bw.write(toWrite);
        }
    }

    public static void runCommand(String command,
                                  String fileName,
                                  BufferedReader inReader)
            throws IOException {

        //Interpret commands
        if (command.equals("-i")) {
            String fileContent = readFileInString(fileName);
            String decoded = Decoder.decode(fileContent);
            System.out.println(decoded);
        } else if (command.equals("-o")) {
            String encoded = Encoder.encode(inReader.readLine());
            FileIoHandler.writeToFile(fileName, encoded);
        }
    }

    private static String readFileInString(String fileName) {
        File file = new File(fileName);
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            br.lines()
                    .forEach(content::append);
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}