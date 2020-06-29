import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.configuration.optimizer.ConfigurationSetting;
import ru.configuration.optimizer.ConfigurationTest;

/**
 * @author Anna
 */
public class AfterTestReport {

    private static ConfigurationTest configurationTest;
    private static ConfigurationSetting configurationSetting;
    private static String filepath;
    private static double[] vector;

    @BeforeClass
    public static void initialization() {
        String utilfilepath = "D:\\test\\";
        filepath = "E:\\IdeaProjects\\simple-url-shortener-master\\target\\shortener-0.0.1-SNAPSHOT.jar";
        String filename = "shortener-0.0.1-SNAPSHOT.jar";
        vector = new double[]{ 0.0, 0.0, 0.0, 0.0, 0.0};
        configurationTest = new ConfigurationTest(filename, filepath, utilfilepath);
        configurationSetting = new ConfigurationSetting(filename, filepath, vector, vector);
    }

    @Test
    public void test_report_test() {
        String expectedString = "Application launch " + filepath + "\n" +
         "with parameters: Xmx" + configurationTest.getXmx() + ", workThreadSleepTimeMs="+ configurationTest.getWorkThreadSleepTime() + "\n";
        String string = configurationTest.report();
        Assert.assertEquals(expectedString, string);
    }

    @Test
    public void test_report_setting() {
        String expectedStringTrue = "\nThe app is optimized with new settings\n" +
                "with parameters: Xmx" + configurationTest.getXmx() +
                ", workThreadSleepTimeMs=" + configurationTest.getWorkThreadSleepTime() + "\n" +
                "Received criteria\n" +
                "Cpu usage % = " + vector[3] + "\n" +
                "Heap size = " + vector[4] + "\n" +
                "Tps = " + vector[0] + "\n" +
                "Avg responce time = " + vector[1] + "\n" +
                "Max responce time = " + vector[2] + "\n";
        String expectedStringFalse = "Settings have not changed";
        String string = configurationSetting.report();

        int access = 0;

        if (string.equals(expectedStringTrue)) {
            access = 1;
        } else if (string.equals(expectedStringFalse)) {
            access = 1;
        }
        Assert.assertEquals(1, access);
    }
}
