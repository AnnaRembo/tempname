package ru.configuration.optimizer;

/**
 * @author Anna
 */
public class Main {

    public static void main(String[] args) {

        String filename = "shortener-0.0.1-SNAPSHOT.jar"; // args[0]
        String filepath = "E:\\IdeaProjects\\simple-url-shortener-master\\target\\shortener-0.0.1-SNAPSHOT.jar"; // args[1]
        String utilfilepath = "D:\\utils\\";

        System.out.println("Application start");

        // first application start
        ConfigurationTest configurationTest = new ConfigurationTest(filename, filepath, utilfilepath);
        createThread(configurationTest);

        System.out.println("Loading criteria");

        // load criteria
        Criteria criteria = new Criteria(utilfilepath);
        createThread(criteria);

        System.out.println("Search for optimal criteria");

        // find optimal criteria
        CriteriaHandler criteriaHandler = new CriteriaHandler(criteria.getCriteriaList());
        createThread(criteriaHandler);

        System.out.println("The attempt to establish the optimal configuration");

        // set optimal configuration
        ConfigurationSetting configurationSetting = new ConfigurationSetting(filename, filepath, utilfilepath, criteria.getCriteriaList(), criteriaHandler.getCurrentVector());
        createThread(configurationSetting);

        System.out.println("Report generation");

        // create report
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(configurationTest.report());
        stringBuilder.append(criteria.report());
        stringBuilder.append(criteriaHandler.report());
        if (configurationSetting.isSet()) {
            stringBuilder.append(configurationSetting.report());
        } else {
            stringBuilder.append("\nOptimization is not necessary");
        }
        FileHandler fileHandler = new FileHandler(utilfilepath);
        fileHandler.writeFile(stringBuilder);

        System.out.println("Report created");
    }

    public static void createThread(RunnableInterface obj) {
        Thread thread = new Thread(() -> {
            try {
                obj.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
