package ru.configuration.optimizer;

import java.io.IOException;

/**
 * @author Anna
 */
public class CmdExecutor {

    public static void execute(String command) {
        try {
            String cmd = "cmd /c ";

            Process process = Runtime.getRuntime().exec(cmd + command);
            new Thread(new SyncPipe(process.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(process.getInputStream(), System.out)).start();
            process.waitFor();
            process.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
