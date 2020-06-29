import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.FileSystemUtils;
import ru.configuration.optimizer.FileHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Anna
 */
public class FileHandlerTest {

    private static String directoryForClean;
    private static FileHandler fileHandler;
    private static int success;

    @BeforeClass
    public static void CreateVariables() {
        directoryForClean = "D:\\test\\clean\\";
        fileHandler = new FileHandler("D:\\test\\");
        success = 0;
    }

    @Test
    public void test_clear_exists_directory() {
        Path path = Paths.get(directoryForClean);

        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileHandler.clearDirectory(directoryForClean);

        if (!Files.notExists(path)) {
            success = 1;
            try {
                FileSystemUtils.deleteRecursively(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            success = 0;
        }

        Assert.assertEquals(1,success);
    }

    @Test
    public void test_clear_not_exists_directory() {
        Path path = Paths.get(directoryForClean);

        if (!Files.notExists(path)) {
            try {
                FileSystemUtils.deleteRecursively(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        fileHandler.clearDirectory(directoryForClean);

        if (!Files.notExists(path)) {
            success = 1;
            try {
                FileSystemUtils.deleteRecursively(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            success = 0;
        }

        Assert.assertEquals(1,success);
    }

    @Test
    public void test_read_file() {
        String expectedString = "Assert";
        List<String> strings = fileHandler.readFile("D:\\test\\readfile.txt");
        Assert.assertEquals(expectedString, strings.get(0));
    }

    @Test
    public void test_read_hprof() {
        long expectedBytes = 38420245;
        long bytes = fileHandler.readHprofFile("D:\\test\\dump0.hprof");
        Assert.assertEquals(expectedBytes, bytes);
    }

    @Test
    public void test_write_file() {
        String expectedString = "Assert";
        StringBuilder stringBuilder = new StringBuilder("Assert");
        fileHandler.writeFile(stringBuilder);
        List<String> strings = fileHandler.readFile("D:\\utils\\summaryReport.txt");
        Assert.assertEquals(expectedString, strings.get(0));
    }
}
