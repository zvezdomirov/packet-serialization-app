package com.personalprojects;

import appolica.main.utils.FileIOHelper;

import com.personalprojects.app.FileIoHandler;
import java.io.*;

public class Main {

    public static void main(String[] args) {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String[] currentLine = br.readLine().split("\\s+");
            String command = currentLine[0];
            String fileName = currentLine[1];
            FileIoHandler.runCommand(command, fileName, br);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
