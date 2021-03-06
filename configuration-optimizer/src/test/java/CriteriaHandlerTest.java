import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.configuration.optimizer.CriteriaHandler;

/**
 * @author Anna
 */
public class CriteriaHandlerTest {

    private static CriteriaHandler criteriaHandler;
    private static double[] vector;
    private static double[] coefficients;

    private final static double EPS =  1e-9;

    @BeforeClass
    public static void initialization() {
        vector = new double[]{10.0, 0.0, 0.0, 0.0, 0.0};
        coefficients = new double[]{ 0.2, 0.2, 0.2, 0.2, 0.2 };
        criteriaHandler = new CriteriaHandler(vector);

    }

    @Test
    public void test_get_current_vector() {
        double[] expectedVector =  { 0.0,0.0,0.0,0.0,0.0};
        double[] currentVector = criteriaHandler.getCurrentVector();

        int access = 1;
        for (int i = 0; i <  currentVector.length; i++) {
            if ( currentVector[i] != expectedVector[i]) {
                access = 0;
            }
        }
        Assert.assertEquals(1,access);
    }

    @Test
    public void test_targetFunction() {
        double expectedF = 1.0;
        double f = criteriaHandler.targetFunction(vector, coefficients);

        Assert.assertEquals(expectedF, f, EPS);
    }

    @Test
    public void test_findGradient() {
        double[] gradient;
        double[] expectedGradient = { 0.0, 0.08333333333333333,
                0.01666666666666667,
                0.06666666666666668,
                0.06666666666666668};

        gradient = criteriaHandler.findGradient(vector);

        int access = 1;
        for (int i = 0; i < gradient.length; i++) {
            if (gradient[i] != expectedGradient[i]) {
                access = 0;
            }
        }
        Assert.assertEquals(1,access);
    }

    @Test
    public void test_findDerivative() {
        double expectedDerivative = 0.09999999999999998;
        double derivative = criteriaHandler.findDerivative(vector, coefficients, 1);

        Assert.assertEquals(expectedDerivative, derivative, EPS);
    }

    @Test
    public void test_run() {
        double[] expectedVector = new double[] { -0.6775728696099721,
                -1.3016499239533606E-7,
                -2.6032998574041886E-8,
                -1.0413199405873105E-7,
                -1.0163282074833648};

        try {
            criteriaHandler.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double[] vector = criteriaHandler.getCurrentVector();

        int access = 1;
        for(int i = 0; i < vector.length; i++) {
            if (vector[i] != expectedVector[i]) {
                access = 0;
            }
        }
        Assert.assertEquals(1,access);
    }

    @Test
    public void test_report() {
        String expectedString = "\nThe criteria vector obtained by the gradient descent method:\n" +
                "Cpu usage % = 0.0\n" +
                "Heap size = 0.0\n" +
                "Tps = 0.0\n" +
                "Avg responce time = 0.0\n" +
                "Max responce time = 0.0\n";
        String string = criteriaHandler.report();

        Assert.assertEquals(expectedString, string);
    }
}
