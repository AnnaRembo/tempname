import org.junit.Assert;
import org.junit.Test;
import ru.configuration.optimizer.CmdExecutor;
import ru.configuration.optimizer.FileHandler;

import java.util.List;

/**
 * @author Anna
 */
public class CmdExecutorTest {

    @Test
    public void test_execute() {
        String filepath = "D:\\test\\testexecute.txt";
        String request = "echo Assert> " + filepath;
        CmdExecutor.execute(request);

        FileHandler fileHandler = new FileHandler("D:\\test\\");;
        List<String> strings = fileHandler.readFile(filepath);
        Assert.assertEquals("Assert", strings.get(0));
    }
}
