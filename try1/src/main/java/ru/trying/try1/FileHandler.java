package ru.trying.try1;

import org.springframework.util.FileSystemUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.gridkit.jvmtool.heapdump.HeapHistogram;
import org.gridkit.jvmtool.heapdump.HeapWalker;
import org.netbeans.lib.profiler.heap.Heap;
import org.netbeans.lib.profiler.heap.HeapFactory;
import org.netbeans.lib.profiler.heap.Instance;

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
        clearDirectory(utilsfilepath + "heap\\");
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
        File file = new File("D:\\utils\\summaryReport.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long readHprofFile(String filename) {
        Set<Instance> roots = new HashSet<>();
        Map<Instance, List<Instance>> links = new HashMap<>();

        try {
            File file = new File(filename);
            Heap heap = HeapFactory.createFastHeap(file);
            file.delete();
            // searching instances
            for(Instance i: heap.getAllInstances()) {

                Instance instance = HeapWalker.valueOf(i, "compositeParent");
                instance = instance != null ? instance : HeapWalker.valueOf(i, "parent");
                if (instance == null) {
                    roots.add(i);
                } else {
                    if (!links.containsKey(instance)) {
                        links.put(instance, new ArrayList<>());
                    }
                    links.get(instance).add(i);
                }
            }

            long heapSize = 0;

            for(Instance root: roots) {
                HeapHistogram heapHistogram = new HeapHistogram();
                collect(heapHistogram, root, links);
                heapSize += heapHistogram.getTotalSize();
            }

            return heapSize;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static void collect(HeapHistogram heapHistogram, Instance instance, Map<Instance, List<Instance>> links) {
        heapHistogram.feed(instance);
        List<Instance> list = links.get(instance);
        if (list != null) {
            for(Instance i: list) {
                collect(heapHistogram, i, links);
            }
        }
    }
}
