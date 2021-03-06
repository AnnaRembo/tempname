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
    private static String workDirectory;
    private static FileHandler fileHandler;
    private static int success;
    private static String filepath;

    @BeforeClass
    public static void CreateVariables() {
        filepath = "D:\\test\\";
        directoryForClean = "D:\\test\\clean\\";
        workDirectory = "D:\\test\\workdirectory\\";
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
    public void test_clear_work_directory() {
        Path path = Paths.get(workDirectory);
        fileHandler.clearWorkDirectory();

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

        Assert.assertEquals(1, success);
    }

    @Test
    public void test_read_file() {
        String expectedString = "Assert";
        List<String> strings = fileHandler.readFile(filepath + "readfile.txt");
        Assert.assertEquals(expectedString, strings.get(0));
    }

    @Test
    public void test_write_file() {
        String expectedString = "Assert";
        StringBuilder stringBuilder = new StringBuilder("Assert");
        fileHandler.writeFile(stringBuilder);
        List<String> strings = fileHandler.readFile(filepath + "summaryReport.txt");
        Assert.assertEquals(expectedString, strings.get(0));
    }
}
