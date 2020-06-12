package ru.trying.try1;

/**
 * @author Anna
 */
public class Main {

    public static void main(String[] args) {

        String filename = "shortener-0.0.1-SNAPSHOT.jar"; // args[0]
        String filepath = "E:\\IdeaProjects\\simple-url-shortener-master\\target\\shortener-0.0.1-SNAPSHOT.jar"; // args[1]
        String utilfilepath = "D:\\utils\\";

        // first application start
        ConfigurationTest configurationTest = new ConfigurationTest(filename, filepath, utilfilepath);
        createThread(configurationTest);

        // load criteria
        Criteria criteria = new Criteria(utilfilepath);
        createThread(criteria);

        // find optimal criteria
        CriteriaHandler criteriaHandler = new CriteriaHandler(criteria.getCriteriaList());
        createThread(criteriaHandler);

        System.out.println("3rd place");

        // set optimal configuration
        ConfigurationSetting configurationSetting = new ConfigurationSetting(filename, filepath, criteria.getCriteriaList(), criteriaHandler.getCurrentVector());
        createThread(configurationSetting);

        // create report
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(configurationTest.report());
        stringBuilder.append(criteria.report());
        stringBuilder.append(criteriaHandler.report());
        stringBuilder.append(configurationSetting.report());
        FileHandler fileHandler = new FileHandler(utilfilepath);
        fileHandler.writeFile(stringBuilder);

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
