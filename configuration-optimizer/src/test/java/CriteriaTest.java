import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.configuration.optimizer.Criteria;

/**
 * @author Anna
 */
public class CriteriaTest {

    private static Criteria criteria;

    @BeforeClass
    public static void initialization() {
        String filepath = "D:\\test\\";
        criteria = new Criteria(filepath);
    }

    @Test
    public void test_get_criteria() {
        double[] expectedCriterias = {0.0, 0.0, 0.0, 0.0, 0.0};
        double[] criteriaList = criteria.getCriteriaList();

        int access = 1;
        for (int i=1; i < criteriaList.length; i++) {
            if (criteriaList[i] != expectedCriterias[i]) {
                access = 0;
            }
        }
        Assert.assertEquals(1, access);
    }

    @Test
    public void test_run() {
        double[] expectedCriterias = {897.3648637372542,
                11.092968328652873, 288.0, 1.651785714285714,
                41.2466516494751};
        criteria.run();

        double[] criterias = criteria.getCriteriaList();

        int access = 1;
        for (int i=1; i < criterias.length; i++) {
            if (criterias[i] != expectedCriterias[i]) {
                access = 0;
            }
        }
        Assert.assertEquals(1, access);
    }

    @Test
    public void test_report() {
        String expectedString = "\nReceived criteria\n" +
                "Cpu usage % = 0.0\n" +
                "Memory usage = 0.0\n" +
                "Tps = 0.0\n" +
                "Avg responce time = 0.0\n" +
                "Max responce time = 0.0\n";
        String string = criteria.report();
        Assert.assertEquals(expectedString, string);
    }
}
