import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.configuration.optimizer.CriteriaLoader;

/**
 * @author Anna
 */
public class CriteriaLoaderTest {

    private static CriteriaLoader criteriaLoader;
    private static String filepath;
    private static String filename;
    private final static int dumpCounter = 42;
    private final static double EPS =  1e-9;
    private final static int BYTES_ = 1024;

    @BeforeClass
    public static void initialization() {
        filename = "shortener-0.0.1-SNAPSHOT.jar";
        filepath = "D:\\test\\";
        criteriaLoader = new CriteriaLoader(filepath);
    }

    @Test
    public void test_get_pid() {
        int expectedPid = 10864;
        int pid = criteriaLoader.getPid(filepath+"jpstest\\jps.txt", filename);
        Assert.assertEquals(expectedPid, pid);
    }

    @Test
    public void test_get_cpuUsage() {
        double expectedCpuUsage = 1.651785714285714;
        double cpuUsage = criteriaLoader.getCpuUsage();

        Assert.assertEquals(expectedCpuUsage,cpuUsage, EPS);
    }

    @Test
    public void test_get_responceTime() {
        double[] expectedRespTime = { 11.092968328652873, 288.0, 897.3648637372542};
        double[] respTime = criteriaLoader.getResponceTime();

        int access = 1;

        for (int i =0; i < respTime.length; i++) {
            if (expectedRespTime[i] != respTime[i]) {
                access = 0;
            }
        }

        Assert.assertEquals(1, access);
    }

    @Test
    public void test_get_Memory() {
        double expectedMemory = 366.4594494047619;
        double memory = criteriaLoader.getMemory();
        Assert.assertEquals(expectedMemory, memory, EPS);
    }

    @Test
    public void test_get_From_Powershell() {
        double expectedMemory = 366.4594494047619;
        String memoryPath = filepath + "memory\\memory.txt";
        double[] divider = new double[] {(double)BYTES_, (double)BYTES_, (double)dumpCounter};
        double memory = criteriaLoader.getFromPowershell(memoryPath, divider);
        Assert.assertEquals(expectedMemory, memory, EPS);
    }
}
