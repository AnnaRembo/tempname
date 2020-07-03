import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.configuration.optimizer.ConfigurationSetting;
import ru.configuration.optimizer.ConfigurationTest;

/**
 * @author Anna
 */
public class ConfigurationSettingTest {

    private static ConfigurationSetting configurationSetting;
    private static ConfigurationTest configurationTest;
    private static String filepath;
    private static double[] vector;

    @BeforeClass
    public static void initialization() {
        String utilfilepath = "D:\\test\\";
        filepath = "E:\\IdeaProjects\\simple-url-shortener-master\\target\\shortener-0.0.1-SNAPSHOT.jar";
        String filename = "shortener-0.0.1-SNAPSHOT.jar";
        vector = new double[]{ 0.0, 0.0, 0.0, 0.0, 0.0};
        configurationSetting = new ConfigurationSetting(filename, filepath, utilfilepath, vector, vector);
        configurationTest = new ConfigurationTest(filename, filepath, utilfilepath);
    }

    @Test
    public void test_report_setting() {
        String expectedStringTrue = "\nThe app is optimized with new settings\n" +
                "with parameters: Xmx" + configurationTest.getXmx() +
                ", workThreadSleepTimeMs=" + configurationTest.getWorkThreadSleepTime() + "\n" +
                "Received criteria\n" +
                "Cpu usage % = " + vector[3] + "\n" +
                "Memory usage = " + vector[4] + "\n" +
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

    @Test
    public void test_get_old_vector() {
        double[] expectedOldVector = {1.0,2.0,3.0,4.0,5.0};
        configurationSetting.setOldVector(expectedOldVector);
        double[] oldVector = configurationSetting.getOldVector();

        int access = 1;
        for(int i = 0; i < oldVector.length; i++) {
            if (oldVector[i] != expectedOldVector[i]) {
                access = 0;
            }
        }
        Assert.assertEquals(1,access);
    }

    @Test
    public void test_is_set_false_default() {
        boolean expectedSet = false;
        boolean set = configurationSetting.isSet();
        Assert.assertEquals(expectedSet, set);
    }
}
