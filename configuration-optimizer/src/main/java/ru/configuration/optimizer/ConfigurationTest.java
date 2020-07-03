package ru.configuration.optimizer;

/**
 * @author Anna
 */
public class ConfigurationTest implements RunnableInterface{

    private String filename;
    private String filepath;
    private String utilsfilepath;
    private String jpsfilepath;

    private FileHandler fileHandler;
    private CriteriaLoader criteriaLoader;
    private int dumpCounter = 42;
    private int pid = 0;
    private int xmx = 1024; // Mbytes
    private int workThreadSleepTimeMs = 10;
    private int server_tomcat_maxthreads = 10;

    public ConfigurationTest(String filename, String filepath, String utilsfilepath) {
        this.filename = filename;
        this.filepath = filepath;
        this.utilsfilepath = utilsfilepath;
        this.jpsfilepath = utilsfilepath + "jps.txt";
        this.fileHandler = new FileHandler(utilsfilepath);
        this.criteriaLoader = new CriteriaLoader(utilsfilepath);
    }

    public void setWorkThreadSleepTimeMs(int workThreadSleepTimeMs) {
        this.workThreadSleepTimeMs = workThreadSleepTimeMs;
    }

    public int getWorkThreadSleepTime() {
        return this.workThreadSleepTimeMs;
    }

    public void setXmx(int xmx) {
        this.xmx = xmx;
    }

    public int getXmx() {
        return this.xmx;
    }

    @Override
    public void run() throws InterruptedException {

        // start application
        Thread threadStartApp = new Thread(this::startApp);

        // find pid
        Thread threadGetPid = new Thread(this::getPid);

        // jmeter test
        Thread threadLoadTest = new Thread(this::loadTest);

        // dump memory
        Thread threadDumpMemory = new Thread(this::dumpMemory);

        //  dump cpu usage
        Thread threadDumpCpuUsage = new Thread(this::dumpCpuUsage);

        // kill pid
        Thread threadKillProcess = new Thread(this::killProcess);

        threadStartApp.start();

        threadGetPid.start();
        threadGetPid.join();

        threadLoadTest.start();

        threadDumpMemory.start();
        threadDumpCpuUsage.start();

        threadLoadTest.join();
        threadKillProcess.join();
        threadDumpMemory.join();
        threadDumpCpuUsage.join();

        threadKillProcess.start();

        threadStartApp.join();
    }

    public void startApp() {
        fileHandler.clearWorkDirectory();

        String request = "java -Xmx"+ xmx +"m" +
                " -DworkThreadSleepTimeMs="+ workThreadSleepTimeMs +
                " -Dserver.tomcat.max-threads=" + server_tomcat_maxthreads +
                " -jar ";
        CmdExecutor.execute(request + filepath);
    }

    public void getPid() {
        do {
            sleep(10000);
            String request = "jps > " + jpsfilepath;
            CmdExecutor.execute(request);

            pid = criteriaLoader.getPid(jpsfilepath, filename);
        } while (pid == 0);
    }

    public void loadTest() {
        String request = utilsfilepath + "apache-jmeter-5.3\\bin\\jmeter.bat -n -t " +
               utilsfilepath +"apache-jmeter-5.3\\bin\\HTTP_Request.jmx" +
                " -l "+ utilsfilepath + "jmeterreport\\log.jtl -e -o "+ utilsfilepath + "jmeterreport\\report\\stat";
        CmdExecutor.execute(request);
    }

    public void dumpMemory() {
        String request;
        //sleep(10000); // test 1 min
        //sleep(240000); // test 5 min
        sleep(540000); // test 10 min
        for(int i = 0; i < dumpCounter; i++) {
            sleep(1000);
            request = "powershell " + utilsfilepath + "getMemory.ps1 \""+ pid +"\" >> " + utilsfilepath + "memory\\memory.txt";
            CmdExecutor.execute(request);
        }
    }

    public void dumpCpuUsage() {
        String request;
        //sleep(240000); // test 1 min
        //sleep(240000); // test 5 min
        sleep(540000); // test 10 min
        for(int i = 0; i < dumpCounter; i++) {
            sleep(1000);
            request = "powershell " + utilsfilepath + "getCpuUsage.ps1 \""+ pid +"\" >> " + utilsfilepath + "cpuusage\\cpuusage.txt";
            CmdExecutor.execute(request);
        }
    }

    public void killProcess() {
        String request = "taskkill -f /PID " + pid;
        CmdExecutor.execute(request);
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String report() {
        String string = "Application launch " + filepath + "\n";
        string += "with parameters: Xmx" + this.getXmx() + ", workThreadSleepTimeMs="+ this.getWorkThreadSleepTime() + "\n";
        return string;
    }
}
