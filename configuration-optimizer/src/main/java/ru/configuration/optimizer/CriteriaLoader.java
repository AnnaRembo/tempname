package ru.configuration.optimizer;

import java.util.List;

/**
 * @author Anna
 */
public class CriteriaLoader {
    private final static int BYTES_ = 1024;
    private FileHandler fileHandler;
    private int dumpCounter = 42;
    private String cpuusagePath;
    private String memoryPath;
    private String resptimePath;

    public CriteriaLoader(String utilsfilepath) {
        this.fileHandler = new FileHandler(utilsfilepath);
        this.cpuusagePath = utilsfilepath + "cpuusage\\cpuusage.txt";
        this.memoryPath = utilsfilepath + "memory\\memory.txt";
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
        double[] divider = new double[] {(double)(dumpCounter), (double)processors};
        double cpuUsage = getFromPowershell(cpuusagePath, divider);

        return cpuUsage;
    }

    public double getMemory() {
        double[] divider = new double[] {(double)BYTES_, (double)BYTES_, (double)dumpCounter};
        double memory = getFromPowershell(memoryPath, divider);

        return memory;
    }

    // divider - for convert to the desired format
    public double getFromPowershell(String path, double[] divider) {
        double resource = 0.0;
        double dividen;
        String[] match;
        List<String> strings = fileHandler.readFile(path);
        int i = 1;
        for (String s: strings) {
            s = s.replaceAll("\"", "");
            match = s.split(",", 3);
            if (i % 2 == 0){
                dividen = Double.parseDouble(match[2]);
                for (int j = 0; j < divider.length; j++) {
                    dividen /= divider[j];
                }
                resource += dividen;
            }
            i++;
        }
        return resource;
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
}
