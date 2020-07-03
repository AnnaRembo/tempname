import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.configuration.optimizer.Criteria;
import ru.configuration.optimizer.Main;

/**
 * @author Anna
 */
public class MainThreadTest {
    private static Criteria criteria;

    @BeforeClass
    public static void initialization() {
        String filepath = "D:\\test\\";
        criteria = new Criteria(filepath);
    }

    @Test
    public void test_createThread() {
        double[] expectedCriterias = {897.3648637372542,
                11.092968328652873, 288.0, 1.651785714285714,
                41.2466516494751};

        Main.createThread(criteria);
        double[] criterias = criteria.getCriteriaList();

        int access = 1;
        for (int i=1; i < criterias.length; i++) {
            if (criterias[i] != expectedCriterias[i]) {
                access = 0;
            }
        }
        Assert.assertEquals(1, access);
    }
}
