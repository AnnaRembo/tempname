package ru.configuration.optimizer;

import org.springframework.util.FileSystemUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Anna
 */
public class FileHandler {
    private String utilsfilepath;

    public FileHandler(String utilsfilepath) {
        this.utilsfilepath = utilsfilepath;
    }

    public void clearWorkDirectory() {
        clearDirectory(utilsfilepath+ "cpuusage\\");
        clearDirectory(utilsfilepath+ "memory\\");
        clearDirectory(utilsfilepath + "jmeterreport\\");
        clearDirectory(utilsfilepath + "jmeterreport\\report\\");
    }

    public void clearDirectory(String stringPath) {
        Path path = Paths.get(stringPath);
        try {
            if (!Files.notExists(path)) {
                FileSystemUtils.deleteRecursively(path);
                Files.createDirectory(path);
            } else {
                Files.createDirectory(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> readFile(String stringPath) {
        List<String> strings = new ArrayList<>();
        Path path = Paths.get(stringPath);
        try(BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)){
            String currentLine;
            while((currentLine = reader.readLine()) != null){
                 strings.add(currentLine);
            }
            return strings;
        } catch(IOException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public void writeFile(StringBuilder stringBuilder) {
        File file = new File(utilsfilepath + "summaryReport.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
