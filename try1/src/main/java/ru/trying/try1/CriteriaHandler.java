package ru.trying.try1;

/**
 * @author Anna
 */
public class CriteriaHandler implements RunnableInterface{
    private final static double EPS_ =  1e-9;   // for quick results set a smaller condition and a larger Epsilon
    private final static double STOP_ = 1e-15;  // this way we get the direction where to shift the properties
    private double[] vector;
    double[] currentVector;
    private double[] coefficients;

    public CriteriaHandler(double[] criteriaList) {
        this.vector = criteriaList; // tps, avg, maxResp, cpuUsage, heapSize
        this.coefficients = new double[] { 0.3, 0.25, 0.05, 0.2, 0.2 };
    }

    public double[] getCurrentVector() {
        return this.currentVector;
    }

    @Override
    public void run() throws InterruptedException {
        double[] nextVector = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
        double[] vectorGradient;
        currentVector = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
        double difference;
        double currentTargetF;
        double nextTargetF;

        for (int i = 0; i < vector.length; i++) {
            currentVector[i] = vector[i];
        }

        //int counter = 0;
        do {
            vectorGradient = findGradient(currentVector);

            for (int i = 0; i < vector.length; i++) {
                nextVector[i] = currentVector[i] - EPS_ * vectorGradient[i];
            }

            currentTargetF = targetFunction(currentVector, coefficients);
            nextTargetF = targetFunction(nextVector, coefficients);
            difference = currentTargetF - nextTargetF;

            for (int i = 0; i < vector.length; i++) {
                currentVector[i] = nextVector[i];
            }
//            counter++;
//            if (counter == 1000) {
//                System.out.println("t1=" + t1 + " t2=" + t2 + " dif=" + difference + " v0=" + nextVector[0] + " v1=" + nextVector[1] +" v2=" + nextVector[2] +" v3=" + nextVector[3] + " v4=" + nextVector[4]);
//                counter = 0;
//            }
        } while (difference > STOP_);
    }

    public double[] findGradient(double[] vector) {

        double[] vector1 = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };

        for (int i = 0; i < vector.length; i++) {
            vector1[i] = findDerivative(vector, coefficients, i);
        }
        return vector1;
    }

    //derivative for each element of the vector
    public double findDerivative(double[] vector, double[] coefficients, int index) {
        double derivative = 0.0;
        double dividen;
        double divider;
        double sum = 0.0;
        double sum2 = 0.0;

        for (int i = 0; i < vector.length; i++) {
            sum += Math.pow(coefficients[i], 2) * Math.pow(vector [i], 2);
            sum2 += coefficients[i] * vector[i];
        }

        dividen = coefficients[index] * Math.sqrt(sum) - sum2 * (Math.pow(coefficients[index], 2)*vector[index] / Math.sqrt(sum));
        divider = sum;

        derivative = dividen/divider;

        return derivative;
    }

    public double targetFunction(double[] vector, double[] coefficients) {
        double result = 0.0;
        double dividen;
        double divider;
        double sum = 0.0;

        for (int i = 0; i < vector.length; i++) {
            sum += Math.pow(coefficients[i], 2) * Math.pow(vector [i], 2);
        }

        divider = Math.sqrt(sum);

        for (int i = 0; i < vector.length; i++) {
            dividen = coefficients[i] * vector [i];
            result += dividen/divider;
        }
        return result;
    }

    @Override
    public String report() {
        String string = "\nThe criteria vector obtained by the gradient descent method:\n";
        string += "Cpu usage % = " + currentVector[3] + "\n";
        string += "Heap size = " + currentVector[4] + "\n";
        string += "Tps = " + currentVector[0] + "\n";
        string += "Avg responce time = " + currentVector[1] + "\n";
        string += "Max responce time = " + currentVector[2] + "\n";
        return string;
    }
}
