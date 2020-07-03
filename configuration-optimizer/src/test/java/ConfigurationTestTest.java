import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.configuration.optimizer.ConfigurationSetting;
import ru.configuration.optimizer.ConfigurationTest;

/**
 * @author Anna
 */
public class ConfigurationTestTest {

    private static ConfigurationTest configurationTest;
    private static String filepath;

    @BeforeClass
    public static void initialization() {
        String utilfilepath = "D:\\test\\";
        filepath = "E:\\IdeaProjects\\simple-url-shortener-master\\target\\shortener-0.0.1-SNAPSHOT.jar";
        String filename = "shortener-0.0.1-SNAPSHOT.jar";
        configurationTest = new ConfigurationTest(filename, filepath, utilfilepath);
    }

    @Test
    public void test_set_work_thread_sleep_time() {
        int expectedTime = 5;
        configurationTest.setWorkThreadSleepTimeMs(expectedTime);
        int time = configurationTest.getWorkThreadSleepTime();
        Assert.assertEquals(expectedTime, time);
    }

    @Test
    public void test_set_xmx() {
        int expectedXmx = 512;
        configurationTest.setWorkThreadSleepTimeMs(expectedXmx);
        int Xmx = configurationTest.getWorkThreadSleepTime();
        Assert.assertEquals(Xmx, Xmx);
    }

    @Test
    public void test_report() {
        String expectedString = "Application launch " + filepath + "\n" +
                "with parameters: Xmx" + configurationTest.getXmx() + ", workThreadSleepTimeMs="+ configurationTest.getWorkThreadSleepTime() + "\n";
        String string = configurationTest.report();
        Assert.assertEquals(expectedString, string);
    }
}
