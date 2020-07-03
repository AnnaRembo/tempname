package ru.configuration.optimizer;

/**
 * @author Anna
 */
public class Criteria implements RunnableInterface {

    private CriteriaLoader criteriaLoader;
    private double[] criteriaList;
    private double cpuUsage;
    private double memory;
    private double[] respTime; // [0] - avg, [1] - max, [2] - tps

    public Criteria(String utilfilepath) {
        this.criteriaLoader = new CriteriaLoader(utilfilepath);
        this.criteriaList = new double[] {0.0, 0.0, 0.0, 0.0, 0.0};
        this.cpuUsage = 0.0;
        this.memory = 0.0;
        this.respTime = new double[] {0.0, 0.0, 0.0};
    }

    public double[] getCriteriaList() {
        return this.criteriaList;
    }

    @Override
    public void run() {
        cpuUsage = criteriaLoader.getCpuUsage();
        memory = criteriaLoader.getMemory();
        respTime = criteriaLoader.getResponceTime();

        criteriaList[0] = respTime[2];
        criteriaList[1] = respTime[0];
        criteriaList[2] = respTime[1];
        criteriaList[3] = cpuUsage;
        criteriaList[4] = memory;
    }

    @Override
    public String report() {
        String string = "\nReceived criteria\n";
        string += "Cpu usage % = " + cpuUsage + "\n";
        string += "Memory usage = " + memory + "\n";
        string += "Tps = " + respTime[2] + "\n";
        string += "Avg responce time = " + respTime[0] + "\n";
        string += "Max responce time = " + respTime[1] + "\n";
        return string;
    }
}
