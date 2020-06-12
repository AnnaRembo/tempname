package ru.trying.try1;

/**
 * @author Anna
 */
public class ConfigurationSetting implements RunnableInterface{
    private ConfigurationTest configurationTest;
    private double[] oldVector;
    private double[] newVector;
    private boolean set = false;
    private String utilsfilepath = "D:\\utils2\\";

    public ConfigurationSetting(String filename, String filepath, double[] oldVector, double[] newVector) {
        this.configurationTest = new ConfigurationTest(filename, filepath, utilsfilepath);
        this.oldVector = oldVector;
        this.newVector = newVector;
    }

    public void setOldVector(double[] oldVector) {
        this.oldVector = oldVector;
    }

    @Override
    public void run() {
        do {
            // whether to change the settings
            // only if you need to increase the response
            if (oldVector[0] < newVector[0] || oldVector[1] > newVector[1]) {// compare tps and avgRespTime
                if (configurationTest.getWorkThreadSleepTime() > 0) {
                    configurationTest.setWorkThreadSleepTimeMs(configurationTest.getWorkThreadSleepTime() - 1);
                }
                // only if you need to reduce the heap size
                if (oldVector[4] < newVector[4]) {
                    if (configurationTest.getXmx() > 256) {
                        configurationTest.setXmx(configurationTest.getXmx() - 128);
                    }
                }

                Main.createThread(configurationTest);

                Criteria criteria = new Criteria(utilsfilepath);
                Main.createThread(criteria);

                setOldVector(criteria.getCriteriaList());

                set = true;
                if (oldVector[0] >= newVector[0] && oldVector[1] <= newVector[1]) {
                    break;
                }
            } else {
                break;
            }
        } while (true);
    }

    @Override
    public String report() {
        String string = "";
        if (set) {
            string = "\nThe app is optimized with new settings\n";
            string += "with parameters: Xmx" + configurationTest.getXmx() + ", workThreadSleepTimeMs=" + configurationTest.getWorkThreadSleepTime() + "\n";
            string += "Received criteria\n";
            string += "Cpu usage %= " + oldVector[3] + "\n";
            string += "Heap size = " + oldVector[4] + "\n";
            string += "Tps = " + oldVector[0] + "\n";
            string += "Avg responce time = " + oldVector[1] + "\n";
            string += "Max responce time = " + oldVector[2] + "\n";
        } else {
            string = "Settings have not changed";
        }
        return string;
    }
}
