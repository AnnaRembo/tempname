package ru.trying.try1;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Anna
 */
public class SyncPipe implements Runnable {
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public SyncPipe(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }
    public void run() {
        try {
            final byte[] buffer = new byte[1024];
            for (int length = 0; (length = inputStream.read(buffer)) != -1; ) {
                outputStream.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
