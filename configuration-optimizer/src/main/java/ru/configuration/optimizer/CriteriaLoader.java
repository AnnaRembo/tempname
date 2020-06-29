package ru.configuration.optimizer;

import java.util.List;

/**
 * @author Anna
 */
public class CriteriaLoader {
    private final static int BYTES_ = 1024;
    private FileHandler fileHandler;
    private int dumpCounter = 6;
    private String utilsfilepath;
    private String cpuusagePath;
    private String heapdumpPath;
    private String resptimePath;

    public CriteriaLoader(String utilsfilepath) {
        this.fileHandler = new FileHandler(utilsfilepath);
        this.utilsfilepath = utilsfilepath;
        this.cpuusagePath = utilsfilepath + "cpuusage\\cpuusage.txt";
        this.heapdumpPath = utilsfilepath + "heap\\";
        this.resptimePath = utilsfilepath + "jmeterreport\\report\\stat\\statistics.json";
    }

    public int getPid(String stringPath, String name) {
        int pid = 0;
        String[] match;
        List<String> strings = fileHandler.readFile(stringPath);

        for (String s: strings) {
            match = s.split(" ", 2);
            if (match[1].equals(name)){
                System.out.println(match[0]);
                pid = Integer.parseInt(match[0]);
                break;
            }
        }
        return pid;
    }

    public double getCpuUsage() {
        int processors = Runtime.getRuntime().availableProcessors(); // available processors
        double cpuUsage = 0.0;
        String[] match;
        List<String> strings = fileHandler.readFile(cpuusagePath);
        int i = 1;
        for (String s: strings) {
            s = s.replaceAll("\"", "");
            match = s.split(",", 3);
            if (i % 2 == 0){
                cpuUsage += Double.parseDouble(match[2]) / (double)(dumpCounter * 7) / (double)processors;
            }
            i++;
        }
        return cpuUsage;
    }

    public double[] getResponceTime() {
        double[] respTime = new double[] { 0.0, 0.0, 0.0 };
        String[] match;
        List<String> strings = fileHandler.readFile(resptimePath);

        for (String s: strings) {

            s = s.replaceAll("\"", "");
            s = s.replaceAll(",", "");
            s = s.replaceAll(" ", "");

            match = s.split(":", 2);
            if (match[0].equals("meanResTime")){
               // System.out.println("meanResTime " + match[1]);
                respTime[0] = Double.parseDouble(match[1]);
            } else if (match[0].equals("maxResTime")) {
               // System.out.println("maxResTime " + match[1]);
                respTime[1] = Double.parseDouble(match[1]);
            } else if (match[0].equals("throughput")) {
                //System.out.println("throughput " + match[1]);
                respTime[2] = Double.parseDouble(match[1]);
                break;
            }
        }
        return respTime;
    }

    public double getHeapSize() {
        double heapSize = 0.0;
        long temp = 0;

        for (int i = 0; i < dumpCounter; i++) {
            temp = fileHandler.readHprofFile(heapdumpPath + "dump" + i + ".hprof");
            heapSize += ((double)temp)/BYTES_/BYTES_/dumpCounter;
        }
        return heapSize;
    }
}
